/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.javenluo.ranger.sys.utils;

import java.util.List;
import java.util.Map;

import com.javenluo.ranger.sys.entity.SysDict;
import org.apache.commons.lang3.StringUtils;

import com.foresee.fbidp.common.utils.CacheUtils;
import com.foresee.fbidp.common.utils.SpringContextHolder;
import com.javenluo.ranger.sys.dao.ISysDictDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static ISysDictDao dictDao = SpringContextHolder.getBean(ISysDictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	/**
	 * 获取单一数据字典集合
	 * @param value 数据值
	 * @param type 数据类型
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (SysDict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	/**
	 * 获取多条数据字典集合
	 * @param values 多个数据值
	 * @param type 数据类型
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	/**
	 * 获取数据字典值
	 * @param label
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (SysDict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	/**
	 * 获取数据字典集合
	 * @param type 数据类型
	 * @return
	 */
	public static List<SysDict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<SysDict>> dictMap = (Map<String, List<SysDict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (SysDict dict : dictDao.findAllList(new SysDict())){
				List<SysDict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<SysDict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	
}
