package com.yijiajiao.oss.service.impl;

import com.google.common.collect.ImmutableMap;
import com.yijiajiao.oss.domain.vo.IntegralRuleBean;
import com.yijiajiao.oss.mapper.IntegralRuleMapper;
import com.yijiajiao.oss.service.IntegralRuleService;
import com.yijiajiao.oss.util.ResultWrapper;
import com.yijiajiao.oss.util.SystemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.PageParams;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

@Service
public class IntegralRuleServiceImpl implements IntegralRuleService {


    @Autowired
    IntegralRuleMapper mapper;

    /**
     * id查询
     */
    @Override
    public Map<String, Object> get(int id, Double price) {
        if (id==15||id==16)
            mapper.updatePrice(ImmutableMap.of(("id"),(Object)id,"price",price));
        return mapper.getById(id);
    }

    /**
     * 保存添加数据
     */
    @Transactional
    @Override
    public int save(Map<String, Object> param) {
        return mapper.save(param);
    }

    @Override
    public List<Map<String, Object>> getIntegral(int ruleType, int ruleGroup) {
        ResultWrapper resultbean = new ResultWrapper();

        IntegralRuleBean bean = new IntegralRuleBean();
        List<Map<String, Object>> entity = mapper.getIntegral(ruleType, ruleGroup);
        if (entity == null || entity.size() == 0) {
            resultbean.bad(SystemStatus.UNAUTHORIZED.toString());
        }
			/*bean.setId(entity.get(0).getId());
			bean.setIsDel(entity.get(0).getIsDel());
			bean.setRuleGroup(entity.get(0).getRuleGroup());
			bean.setRuleLimit(entity.get(0).getRuleLimit());
			bean.setRuleType(entity.get(0).getRuleType());
			bean.setValue(entity.get(0).getValue());
			

			bean.setCreateTime(entity.get(0).getCreateTime());
			bean.setUpdateTime(entity.get(0).getUpdateTime());
			
			resultbean.setSucResult(bean);*/
        resultbean.setResult(entity);
        return (List<Map<String, Object>>) resultbean;
    }

    @Override
    public Map<String, Object> saveorupdate(Map<String, Object> param) {
        //return mapper.saveOrUpdate(param);
        return null;
    }

    /**
     * 更新操作
     */
    @Transactional
    @Override
    public int saveOrUpdate(Map<String,Object> param) {
        return mapper.saveOrUpdate(param);
    }

    @Override
    public List<Map<String, Object>> getdisbale(int id) {
        //	ResultBean resultBean = new ResultBean();
		/*try {
			IntegralRuleEntity entity = integralRuleDao.getById(id);
			if (entity == null) {
				resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
				return resultBean;
			}
			resultBean.setSucResult(entity);
		} catch (Exception e) {
			// TODO: handle exception
			resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
			e.printStackTrace();
		}*/
       // return mapper.getById(id);
        return null;
    }

    @Override
    public List<Map<String,Object>> querybystatus(String status) {
        /*ResultBean resultBean = new ResultBean();
        try {
            List<?> entity = integralRuleDao.querybystatus(status);
            if (entity == null || entity.size() == 0) {
                resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
                return resultBean;
            } else {
                resultBean.setSucResult(entity);
            }
        } catch (Exception e) {
            // TODO: handle exception
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
            e.printStackTrace();
        }
        return resultBean;*/

        return mapper.querybystatus(status);
    }

    @Override
    public PageWrapper<Map<String, Object>> getList(Integer pageNo, Integer pageSize) {

        PageParams<Map<String, Object>> p = new PageParams<Map<String, Object>>(pageNo, pageSize);
        List<Map<String, Object>> data = mapper.getLists(ImmutableMap.of( "pageParams",(Object)p));
        return p.getPageWrapper(data);
    }



}
