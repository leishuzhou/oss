package pub.tbc.mybatis.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页参数，线程不安全，只能在方法中使用
 *
 * @author tbc on 2016/10/15 16:52:25.
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class PageParams<T> {

    public PageParams(Integer pageNo, Integer pageSize) {
        this(true, true, true, pageNo, pageSize);
    }

    public PageParams(Boolean useFlag, Boolean checkFlag, Boolean cachePageWrapper, Integer pageNo, Integer pageSize) {
        this.useFlag = useFlag;
        this.checkFlag = checkFlag;
        this.cachePageWrapper = cachePageWrapper;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    private Boolean useFlag = true; // 是否使用插件
    private Boolean checkFlag = true;// 是否检测当前页码的有效性
    private Boolean cachePageWrapper = false;   // 是否将分页数据缓存到Pages中的ThreadLocal对象中

    private Integer pageNo;
    private Integer pageSize;
    private Integer total;
    private Integer pages;

    private List<T> list;
    private PageWrapper<T> pageWrapper;

    public PageWrapper<T> getPageWrapper() {
        if (pageWrapper == null) {
            pageWrapper = new PageWrapper<>(this);
            pageWrapper.setList(list);
        }
        return pageWrapper;
    }

    public PageWrapper<T> getPageWrapper(List<T> list) {
        this.list = list;
        if (pageWrapper == null) {
            pageWrapper = new PageWrapper<>(this);
            pageWrapper.setList(list);
        }
        return pageWrapper;
    }


}
