package com.yijiajiao.oss.service.impl;


import com.yijiajiao.oss.mapper.MessageMapper;
import com.yijiajiao.oss.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements  MessageService{
  
  
  
  @Autowired
  MessageMapper mapper;

  /**
   * id查询
   */
  @Override
  public Map<String,Object> get(int id)    {
	/*  ResultBean resultBean = new ResultBean();
	    MessageEntity entity =messageDao.getById(id);
	   if(entity==null){
	      resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
	      return resultBean;
	    }
	    resultBean.setSucResult(entity);
	    return resultBean;*/
	return mapper.getById(id);
  }
  /**
   * 保存添加数据
   */
  @Override
  public Map<String, Object> save(Map<String,Object> param) {
   /* ResultBean resultBean = new ResultBean();
    try {
    	messageDao.save(entity);
        resultBean.setSucResult("群发成功");
    } catch (Exception e) {
      resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
    }
    return resultBean;*/
   return mapper.save(param);
  }
  
  /**
   * 保存添加数据
   */
  @Transactional
  @Override
  public Map<String, Object> saves(Map<String, Object> param) {
	 /* ResultBean resultBean = new ResultBean();
	  try {
		  messageDao.saves(entity);
		  resultBean.setSucResult("单发成功");
	  } catch (Exception e) {
		  resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		  e.printStackTrace();
	  }
	  return resultBean;*/
	  return mapper.saves(param);
  }

	@Override
	public pub.tbc.mybatis.plugin.PageWrapper<Map<String, Object>> query(Integer pageNum, Integer pageSize, String objecter, String status) {
		return null;
	}


	@Override
	public List<Map<String, Object> > List() {
    /*	ResultBean resultbean=new ResultBean();
    	try {
    		List<MessageEntity> entity=messageDao.findAll();
    		if(entity==null||entity.size()==0){
    			resultbean.setFailMsg(SystemStatus.UNAUTHORIZED);
    		    return resultbean;
    	    }
    		resultbean.setSucResult(entity);
		} catch (Exception e) {
			// TODO: handle exception
			resultbean.setFailMsg(SystemStatus.SERVER_ERROR);
			e.printStackTrace();
		}
	    return resultbean;*/
    return mapper.findAll();
	}

    /**
     * 更新操作
     */
	@Override
	public Map<String,Object> saveorupdate(Map<String,Object> param) {
		/*ResultBean resultbean=new ResultBean();
		try {
			messageDao.saveOrUpdate(entity);
			resultbean.setSucResult(entity);
		    return resultbean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultbean.setFailMsg(SystemStatus.SERVER_ERROR);
			e.printStackTrace();
		}
	    return resultbean;*/
		return mapper.saveOrUpdate(param);
	}
	
	@Override
	public List<Map<String,Object>> getdisbale(int id) {
		/*ResultBean resultBean = new ResultBean();
		try {
			MessageEntity entity =messageDao.getById(id);
		    if(entity==null){
		      resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
		      return resultBean;
		    }
		    resultBean.setSucResult(entity);
		} catch (Exception e) {
			// TODO: handle exception
			resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
			e.printStackTrace();
		}

	    return resultBean;*/
		return mapper.querybystatus(id+"");
	}

	@Override
	public List<Map<String, Object>> save(String objecter, String title, String content, int marks) {
		return null;
	}

	@Override
	public PageWrapper<Map<String, Object>> pageList(Integer pageNum, Integer pageSize, String marks) {
		return null;
	}


	@Override
	public List<Map<String,Object>> querybystatus(String status) {
		/*ResultBean resultBean = new ResultBean();
		try {
			List<?> entity =messageDao.querybystatus(status);
		    if(entity==null||entity.size()==0){
		      resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
		      return resultBean;
		    }else{
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
	
	/*@Override
	public Map<String,Object> save(Map<String,Object> param) {
		*//*ResultBean resultBean = new ResultBean();
		//测试发送短信
		// SolutionUtil.send2(objecter, content);
	    try {
	    	messageDao.save(objecter,title,content,marks);
	        resultBean.setSucResult("发送成功");
	    } catch (Exception e) {
	      resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
	      e.printStackTrace();
	    }
	    return resultBean;*//*
		return mapper.save(param);
	}*/
/*	@Override
	public ResultBean saveall(String objecter,String title, String  content,int marks) {
		ResultBean resultBean = new ResultBean();
		//测试发送短信
		// SolutionUtil.send2(objecter, content);
	    try {
	    	messageDao.saveall(objecter,title,content,marks);
	        resultBean.setSucResult("发送成功");
	    } catch (Exception e) {
	      resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
	      e.printStackTrace();
	    }
	    return resultBean;
	}*/


}
