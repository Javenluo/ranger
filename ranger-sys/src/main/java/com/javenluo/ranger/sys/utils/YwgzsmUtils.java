/**
 * Copyright &copy; 2017 <a href="">JeeSite</a> All rights reserved.
 */
package com.javenluo.ranger.sys.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javenluo.ranger.sys.entity.SysYwgzsm;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.foresee.fbidp.common.utils.CacheUtils;
import com.foresee.fbidp.common.utils.SpringContextHolder;
import com.javenluo.ranger.sys.dao.ISysYwgzsmDao;
import com.google.common.collect.Maps;

/**
 * 业务规则工具类
 * @author wujinghui
 * @version 2017-6-22
 */
public class YwgzsmUtils {
	
	private static ISysYwgzsmDao ywgzsmDao = SpringContextHolder.getBean(ISysYwgzsmDao.class);

	public static final String CACHE_YWGZSM_MAP = "ywgzsmMap";
	
	/**
	 * 根据业务规则编码获取业务规则集合
	 * @param ywgzCode 业务规则编码
	 * @return
	 */
	public static List<String> getYwgzSm(String ywgzCode){
		List<String> smList  = new ArrayList<String>();
		if(StringUtils.isEmpty(ywgzCode)){
			return smList;
		}
		@SuppressWarnings("unchecked")
		Map<String, SysYwgzsm> ywgzsmMap = (Map<String, SysYwgzsm>)CacheUtils.get(CACHE_YWGZSM_MAP);
		ywgzsmMap = Maps.newHashMap();
		for (SysYwgzsm ywgzsm : ywgzsmDao.findAllList(new SysYwgzsm())){
			if(ywgzsm.getYwgzBm().equals(ywgzCode)){
				SysYwgzsm sm = ywgzsmMap.get(ywgzsm.getYwgzBm());
				if (sm != null){
				}else{
					ywgzsmMap.put(ywgzsm.getYwgzBm(), ywgzsm);
				}
			}
		}
		CacheUtils.put(CACHE_YWGZSM_MAP, ywgzsmMap);
		SysYwgzsm sm = ywgzsmMap.get(ywgzCode);
		
		if (sm != null && StringUtils.isNoneEmpty(sm.getYwgzSmFwb())){
			//分行以及加上序号
			String[] arr = StringEscapeUtils.unescapeHtml4(sm.getYwgzSmFwb()).split("</p>");
			for(int i=0;i<arr.length;i++){
				String msg = arr[i];
				msg = msg + "</p>";
				int index = msg.indexOf("<p");
				if(index < 0){
					continue;
				}
				String firMsg = msg.substring(0,index);
				String secMsg = msg.substring(index,msg.length());
				int num = secMsg.indexOf(">") + 1;
				if(num < 0){
					continue;
				}
				String thrMsg = secMsg.substring(num,secMsg.length());
				secMsg = secMsg.substring(0,num);
				
				int a = i + 1;
				msg = firMsg + secMsg + a + "、" + thrMsg;
				smList.add(msg);
			}
		} 
		return smList;
	}
	
	
}
