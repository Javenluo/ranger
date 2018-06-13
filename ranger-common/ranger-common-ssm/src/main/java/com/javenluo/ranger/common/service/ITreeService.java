package com.javenluo.ranger.common.service;

import java.util.List;
import java.util.Map;


/**
 * 节点树服务接口。
 * @author gulong
 * @version 2013-8-28
 */
public interface ITreeService{
	
	/**
	 * 获取type。
	 * @return String
	 */
	public String getType();

	/**
	 * 载入孩子节点。
	 * @param pId 父节点
	 * @param withSelf 是否查询本节点
	 * @param params 返回JSON
	 * @return List
	 */
	public List<Map<String, Object>> loadChildren(String pId, boolean withSelf,
			Map<String, String> params);

	/**
	 * 载入全部。
	 * @param rootId 根节点Id
	 * @return List
	 */
	public List<Map<String, Object>> loadAll(String rootId);

	/**
	 * 设置。
	 * @return Map
	 */
	public Map<String, Object> config();
}
