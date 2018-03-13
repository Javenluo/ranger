package com.javaluo.ranger.rest.authorization.manager.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaluo.ranger.rest.dao.ISysTokenDao;
import com.javaluo.ranger.rest.entity.SysToken;

/**
 * 使用数据库存储Token
 * @author gulong 
 * @version 2017-10-16
 */
/**
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly = false)
public class DataBaseTokenManagerService extends AbstractTokenManager {
	
	@Autowired
	ISysTokenDao sysTokenDao;

	/**
	 * 一个用户只能绑定一个Token时通过Key删除关联关系
	 * @param key
	 */
    @Override
    public void delSingleRelationshipByKey(String key) {
    	sysTokenDao.deleteByKey(key);
    }

    /**
     * 通过token删除关联关系
     * @param token
     */
    @Override
    public void delRelationshipByToken(String token) {
    	sysTokenDao.deleteByToken(token);
    }

    /**
     * 通过token删除关联关系
     * @param key
     * @param token
     */
    @Override
    protected void createMultipleRelationship(String key, String token) {
    	SysToken sysToken = new SysToken();
    	sysToken.preInsert();
    	sysToken.setKey(key);
    	sysToken.setToken(token);
    	sysToken.setExpireTime(new Timestamp(System.currentTimeMillis() + tokenExpireSeconds * 1000));
    	sysTokenDao.insert(sysToken);
    }

    /**
     * 一个用户只能绑定一个Token时创建关联关系
     * @param key
     * @param token
     */
    @Override
    protected void createSingleRelationship(String key, String token) {
    	SysToken sysToken = new SysToken();
    	sysToken.setKey(key);
    	Long count = sysTokenDao.findCount(sysToken);
    	
        if (count != null && count.intValue() > 0) {
        	sysToken.setToken(token);
        	sysToken.setExpireTime(new Timestamp(System.currentTimeMillis() + tokenExpireSeconds * 1000));
        	sysTokenDao.updateByKey(sysToken);
        } else {
        	sysToken.preInsert();
        	sysToken.setToken(token);
        	sysToken.setExpireTime(new Timestamp(System.currentTimeMillis() + tokenExpireSeconds * 1000));
        	sysTokenDao.insert(sysToken);
        }
    }

    /**
     * 通过Token获得Key
     * @param token
     */
    @Override
    public String getKeyByToken(String token) {
    	SysToken sysToken = new SysToken();
    	sysToken.setToken(token);
    	sysToken.setExpireTime(new Timestamp(System.currentTimeMillis()));
    	
    	List<String> list = sysTokenDao.getKeyByToken(sysToken);
    	if(!list.isEmpty()){
    		return list.get(0);
    	}
    	return "";
    }

    /**
     * 在操作后刷新Token的过期时间
     * @param key
     * @param token
     */
    @Override
    protected void flushExpireAfterOperation(String key, String token) {
    	SysToken sysToken = new SysToken();
    	sysToken.setToken(token);
    	sysToken.setExpireTime(new Timestamp(System.currentTimeMillis() + tokenExpireSeconds * 1000));
    	sysTokenDao.updateByToken(sysToken);
    }
   
   /**
    * 查询token
    * @param sysToken
    * @return
    */
   public List<SysToken> findList(SysToken sysToken){
	   return sysTokenDao.findList(sysToken);
   }
	
}
