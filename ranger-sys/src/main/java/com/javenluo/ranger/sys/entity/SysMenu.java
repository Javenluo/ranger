/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import java.util.ArrayList;
import java.util.List;

import com.javenluo.ranger.common.persistence.DataEntity;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(Fbidp_sys_menu)的实体类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysMenu extends DataEntity<SysMenu>{
	private static final long serialVersionUID = 1L;
	
	/** 菜单名称 */
	private String name;  //菜单名称
	/** 菜单访问地址 */
	private String url;  //菜单访问地址
	/** 上级菜单ID */
	private String parentId;  //上级菜单ID
	/** 菜单显示时的排列次序，按照其值的大小进行排序，序号小的显示时排在前面，序号不必是连续的 */
	private java.math.BigDecimal sortNo;  //菜单显示时的排列次序，按照其值的大小进行排序，序号小的显示时排在前面，序号不必是连续的
	/** 菜单图标 */
	private String imageUrl;  //菜单图标
	/** 是否相对路径 */
	private String contextRelative;  //是否相对路径
	/** 菜单路径所在框架名，_blank、_self、_parent、_top */
	private String target;  //菜单路径所在框架名，_blank、_self、_parent、_top
	/** 菜单是否可见 (1-可见,0-隐藏) */
	private String visible;  //菜单是否可见 (1-可见,0-隐藏)
	/** 菜单提示信息 */
	private String tooltip;  //菜单提示信息
	/** 菜单类型（1菜单；2按钮或链接） */
	private String menuType;  //菜单类型（1菜单；2按钮或链接）
	/** 按钮操作权限标识 */
	private String permission;  //按钮操作权限标识
	
	private String bzsm; //帮助说明
	
	private String userId;
	
	private boolean hasChild;
	
	private List<SysMenu> children = new ArrayList<SysMenu>();
	
	private SysMenu parent;
	
	private String parentIds;
	
	private String state;
	
	private int childNum;
	
	private String checked;
	
	private String roleId;
	
	private int bzsmFlag; //帮助说明启用标识
	
	public static String getRootId(){
		return "1";
	}

}
