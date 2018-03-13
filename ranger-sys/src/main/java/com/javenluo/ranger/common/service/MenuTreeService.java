package com.javenluo.ranger.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javenluo.ranger.sys.entity.SysMenu;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.service.SysMenuService;
import com.javenluo.ranger.sys.utils.UserUtils;

/**
 * <pre>
 * 机构树节点数据的加载
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class MenuTreeService implements ITreeService {
	
	@Autowired
	private SysMenuService sysMenuService;

	@Override
	public String getType() {
		return "menuTree";
	}

	/**
	 * 加载树子节点
	 */
	@Override
	public List<Map<String, Object>> loadChildren(String pId, boolean withSelf, Map<String, String> params) {

		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		
		SysUser currUser = UserUtils.getUser();
		
		//2:找出子机构
		if(StringUtils.isEmpty(pId)){
			ret.add(buildRoot());
			
			buildTreeNodes("0",withSelf,true,ret);
		}else{
			buildTreeNodes(pId,withSelf,false,ret);
		}
		
		return ret;
	}
	
	private Map<String, Object> buildRoot(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "0");
		map.put("pId", "");
		map.put("name", "菜单树");
		map.put("title", "菜单树");
		map.put("childNum", 100);
		map.put("showorder", 0);
		map.put("open", true);
		map.put("isParent", "true");
		return map;
	}

	/**
	 * 获取树所有子节点
	 */
	@Override
	public List<Map<String, Object>> loadAll(String rootId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 菜单树节点初始化
	 */
	@Override
	public Map<String, Object> config() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clearable", false);
		return map;
	}
	
	/**
	 * 构建树节点结果集
	 * @param parentId String
	 * @param withSelf boolean
	 * @param findRoot boolean
	 * @param ret List<Map<String, Object>>
	 */
	private void buildTreeNodes(String parentId,boolean withSelf, boolean findRoot, List<Map<String, Object>> ret){
		SysMenu entity = new SysMenu();
		//entity.setWithSelf(withSelf);
		entity.setParentId(parentId);
		List<SysMenu> menuList = sysMenuService.findList(entity);
		for (SysMenu sysMenu : menuList) {
			Map<String, Object> map = buildTreeNodeMap(sysMenu, findRoot);
			ret.add(map);
		}
	}
	
	/**
	 * 树节点数据组装
	 * @param menu SysMenu
	 * @param findRoot boolean
	 * @return Map<String, Object>
	 */
	private Map<String, Object> buildTreeNodeMap(SysMenu menu, boolean findRoot){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", menu.getId());
		map.put("pId", menu.getParentId());
		map.put("name", menu.getName());
		map.put("fullName", menu.getName());
		map.put("childNum", menu.getChildNum());
		map.put("showorder", menu.getSortNo());
		
		if(findRoot){
			map.put("open", true);
		}
							
		if(menu.getChildNum()>0){
			map.put("isParent", "true");
		} else {
			map.put("isParent", "false");
		}
		return map;
	}

}
