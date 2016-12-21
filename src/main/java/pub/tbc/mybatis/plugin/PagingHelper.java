package pub.tbc.mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author tbc on 2016/10/15 16:55:18.
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
})
@Slf4j
public class PagingHelper implements Interceptor {

    private Integer defaultPageNum;
    private Integer defaultPageSize;
    private Boolean defaultUseFlag;
    private Boolean defaultCheckFlag;


    /**
     * 分解分页参数，在这里下面三种方式都是支持使用的
     * 1. Map
     * 2. @Param注解传递参数
     * 3. POJO继承PageParams
     *
     * @param parameterObject .
     * @return 分页参数
     */
    private PageParams getPageParams(Object parameterObject) {
        if (Objs.isEmpty(parameterObject))
            return null;
        PageParams pageParams = null;
        //支持Map参数和MyBatis的@Param注解的参数
        if (parameterObject instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> paramMap = (Map) parameterObject;
            Set<String> keys = paramMap.keySet();
            // 循环一遍，如果有对象是PageParams的实例或者子类实例，就是分页参数了，返回去
            for (String k : keys) {
                Object value = paramMap.get(k);
                if (value instanceof PageParams)
                    return (PageParams) value;
            }
        } else if (parameterObject instanceof PageParams) { // 如果用的继承方式
            pageParams = (PageParams) parameterObject;
        }
        return null;
    }

    /**
     * 获取记录总数
     *
     * @param invocation           Invocation
     * @param metaStatementHandler statementHandler
     * @param boundSql             sql
     * @return sql查询到的记录总数
     */
    private int getTotal(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql) throws Exception {
        // 获取当前mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 获取配置对象
        Configuration configuration = mappedStatement.getConfiguration();
        // 获取原始SQL
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        // 改写为统计总数的SQL，这里暂时只支持MYSQL数据库
        String countSql = "select count(*) as total from (" + sql + ") $_paging";
        // 获取拦截方法参数， 我们知道是Connection对象[为什么是参数数组的第一个元素]
        Connection conn = (Connection) invocation.getArgs()[0];
//        PreparedStatement statement = null;
        int total = 0;
        // Try-with-resources @since jdk1.7 自动关闭资源,Connection不能关(否则后续的SQL就没法执行了)，所以定义在外面
        try (PreparedStatement statement = conn.prepareStatement(countSql)) {
            // 预编译统计总数SQL

            // 构建统计总数BoundSql
            BoundSql countBoundSql = new BoundSql(
                    configuration,
                    countSql,
                    boundSql.getParameterMappings(),
                    boundSql.getParameterObject()
            );
            // 构建MyBatis的ParameterHandler用来设置countSql的参数
            ParameterHandler parameterHandler = new DefaultParameterHandler(
                    mappedStatement,
                    boundSql.getParameterObject(),
                    countBoundSql
            );
            // 设置countSql的参数
            parameterHandler.setParameters(statement);
            // exec
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        }
        log.debug("总记录数：{}", total);
        return total;
    }

    private void setTotalPageParams(PageParams pageParams, Integer total, Integer pageSize) {
        pageParams.setTotal(total);
        // 计算总页数
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        pageParams.setPages(totalPage);
        Pages.add(new PageWrapper(pageParams));
    }

    private void checkPage(Boolean checkFlag, Integer pageNum, Integer totalPages) {
        if (checkFlag) {
            // 检查页码page是否合法
            if (pageNum > totalPages && totalPages != 0) {
                throw new RuntimeException("查询失败， 查询页码[" + pageNum + "]大于总页数[" + totalPages + "]!!");
            }
        }
    }

    /**
     * 修改当前查询的SQL，主要是添加分页，仅支持Mysql，使用Limit关键字
     *
     * @param invocation           Invocation
     * @param metaStatementHandler MetaObject
     * @param boundSql             BoundSql
     * @param pageNum              .
     * @param pageSize             .
     * @return .
     */
    private Object changeSql(Invocation invocation,
                             MetaObject metaStatementHandler,
                             BoundSql boundSql,
                             Integer pageNum,
                             Integer pageSize) throws InvocationTargetException, IllegalAccessException, SQLException {
        // 获取当前需要执行的SQL
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        // 修改SQL，仅支持MYSQL，用Limit进行分页
        String newSql = "select * from (" + sql + ") $_paging_table limit ?, ?";
        // 回填到对象中，修改要执行的SQL
        metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
        // 相当于调用StatementHandler的prepare方法，预编译了当前SQL并设置原有的参数，但是少了两个分页参数，返回一个PreparedStatement
        // throws InvocationTargetException, IllegalAccessException
        PreparedStatement ps = (PreparedStatement) invocation.proceed();
        // 计算SQL总参数个数
        // throws SQLException
        int count = ps.getParameterMetaData().getParameterCount();
        // 设置两个分页参数
        ps.setInt(count - 1, (pageNum - 1) * pageSize);
        ps.setInt(count, pageSize);
        return ps;
    }

    /**
     * 检查是否是select语句
     *
     * @param sql .
     * @return .
     */
    private boolean checkSelect(String sql) {
        if (Objs.isEmpty(sql))
            throw new RuntimeException("拿到的SQL怎么是空的");
        return sql.trim().toLowerCase().startsWith("select");
    }

    /**
     * 从代理对象中分离出真实对象
     *
     * @param invocation .
     * @return 非代理StatementHandler对象
     */
    private StatementHandler getUnProxyObject(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        // 分离代理对象链（由于目标类可能被多个拦截器拦截，从而形成多次代理，通过循环可以分离出原始的目标类）,
        // 只是，为什么是"h"
        Object object = null;
        while (metaStatementHandler.hasGetter("h"))
            object = metaStatementHandler.getValue("h");

        if (Objs.isEmpty(object))
            return statementHandler;
        return (StatementHandler) object;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = getUnProxyObject(invocation);
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        // 分页只拦截select语句，其它语句放行，直接执行原来的方法
        if (!checkSelect(sql)) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();
        PageParams pageParams = getPageParams(parameterObject);
        if (Objs.isEmpty(pageParams))// 没有分页参数
            return invocation.proceed();

        // 获取分页参数，获取不到的时候使用默认值
        Integer pageNum = Objs.isEmpty(pageParams.getPageNo()) ? this.defaultPageNum : pageParams.getPageNo();
        Integer pageSize = Objs.isEmpty(pageParams.getPageSize()) ? this.defaultPageSize : pageParams.getPageSize();

        Boolean useFlag = Objs.isEmpty(pageParams.getUseFlag()) ? this.defaultUseFlag : pageParams.getUseFlag();
        Boolean checkFlag = Objs.isEmpty(pageParams.getCheckFlag()) ? this.defaultCheckFlag : pageParams.getCheckFlag();

        if (!useFlag)  // 如果设置不使用插件
            return invocation.proceed();

        int total = getTotal(invocation, metaStatementHandler, boundSql);
        // 回填总记录数到分页参数里
        setTotalPageParams(pageParams, total, pageSize);
        // 检查当前页码的有效性
        checkPage(checkFlag, pageNum, pageParams.getPages());
        // 修改SQL
        return changeSql(invocation, metaStatementHandler, boundSql, pageNum, pageSize);
    }

    @Override
    public Object plugin(Object target) {
        log.debug("生产分页动态代理对象");
//        return Plugin.wrap(target, this);
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 在这里设置一些默认值
     *
     * @param properties 。
     */
    @Override
    public void setProperties(Properties properties) {
        String strDefaultPageNum = properties.getProperty("mybatis.page.pageNum", "1");
        String strDefaultPageSize = properties.getProperty("mybatis.page.pageSize", "12");
        String strDefaultUseFlag = properties.getProperty("mybatis.page.useFlag", "false");
        String strDefaultCheckFlag = properties.getProperty("mybatis.page.checkFlag", "false");

        this.defaultPageNum = CastUtil.castInt(strDefaultPageNum);
        this.defaultPageSize = CastUtil.castInt(strDefaultPageSize);
        this.defaultUseFlag = CastUtil.castBoolean(strDefaultUseFlag);
        this.defaultCheckFlag = CastUtil.castBoolean(strDefaultCheckFlag);
    }


}
