package com.javenluo.ranger.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javenluo.ranger.sys.entity.SysOrg;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.service.SysOrgService;
import com.javenluo.ranger.sys.utils.UserUtils;

/**
 * <pre>
 * 机构树节点数据的加载(该机构树的实现不支持异步加载)
 * </pre>
 * 
 * @author gulong
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional(readOnly = true)
public class OrgTreeService implements ITreeService {

	@Value("${fbidp.top.orgId}")
	private String topOrgId;
	@Value("${fbidp.top.orgCode}")
	private String topOrgCode;
	@Value("${fbidp.top.manageOrgCode}")
	private String manageOrgCode;
	@Autowired
	private SysOrgService orgService;

	@Override
	public String getType() {
		return "orgTree";
	}

	/**
	 * 加载树子节点
	 */
	@Override
	public List<Map<String, Object>> loadChildren(String pId, boolean withSelf, Map<String, String> params) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		SysUser currUser = UserUtils.getUser();
		boolean isManageOrg = false;

		// 1:判断是否为管理部门
		for (String orgCode : currUser.getOrgCodeList()) {
			if (StringUtils.equals(manageOrgCode, orgCode)) {
				isManageOrg = true;
				break;
			}
		}

		if (StringUtils.isEmpty(pId)) {
			List<String> orgCodeList = currUser.getOrgCodeList();
			List<String> fullOrgCodeList = currUser.getFullOrgCodeList();
			if (currUser.isAdmin() || isManageOrg) {

				// 2：超级管理员和管理部门可以看见整棵组织机关树
				List<SysOrg> orgList = orgService.queryTopOrgListByAdminUser(topOrgId);
				for (SysOrg sysOrg : orgList) {
					Map<String, Object> map = buildTreeNodeMap(sysOrg,
							StringUtils.equals(sysOrg.getOrgCode(), topOrgCode), true);
					ret.add(map);
				}
			} else {
				// 3: 普通用户
				SysOrg entity = new SysOrg();

				List<String> list = new ArrayList<String>();
				for (String orgCode : orgCodeList) {
					for (String fullOrgCode : fullOrgCodeList) {
						String[] codes = fullOrgCode.split(",");
						if (fullOrgCode.contains(orgCode) && !StringUtils.equals(orgCode, codes[codes.length - 1])) {
							list.add(fullOrgCode);
						}
					}
				}
				for (String string : list) {
					fullOrgCodeList.remove(string);
				}

				entity.setFullOrgCodes(currUser.getFullOrgCodes()); // 用户的所属机构
				entity.setFullOrgCodeList(fullOrgCodeList); // 用户的授权机构

				List<SysOrg> orgList = orgService.queryTopOrgListByCommonUser(entity);
				for (SysOrg sysOrg : orgList) {
					Map<String, Object> map = buildTreeNodeMap(sysOrg, false, true);
					ret.add(map);
				}
			}
			if (!isManageOrg) {
				//根据授权机构拼接
				for (String str : orgCodeList) {
					Set<String> orgCodeSet = new HashSet<String>();
					SysOrg sysOrg = new SysOrg();
					sysOrg.setOrgCode(str);
					List<SysOrg> orgList = orgService.findList(sysOrg);
					for (SysOrg org : orgList) {
						String orgCode = org.getFullOrgCodes();
						String[] orgCodes = orgCode.split(",");
						
						for (int i = 0; i < orgCodes.length - 1; i++) {
							orgCodeSet.add(orgCodes[i]);
						}
						for (String string: orgCodeSet) {
							SysOrg orgcode = new SysOrg();
							orgcode.setOrgCode(string);
							List<SysOrg> tmpOrgList = orgService.findList(orgcode);
							for (SysOrg orgs : tmpOrgList) {
								Map<String, Object> map = buildTreeNodeMap(orgs, true, false);
								boolean isExsit = true;
								for (Map<String, Object> node : ret) {
									if (orgs.getId().equals(node.get("id").toString())) {
										map.put("canSelect", node.get("canSelect"));
										map.put("otherParam", node.get("otherParam"));
										map.put("open", (boolean) node.get("open"));
										ret.remove(node);
										ret.add(map);
										isExsit = false;
										break;
									}
								}
								if (isExsit) {
									ret.add(map);
								}
							}
						}
					}
				}
			}

			// 没有权限的上级节点也一并展现出来
			Set<String> orgCodeSet = new HashSet<String>();
			String[] orgCodes = currUser.getFullOrgCodes().split(",");
			for (int i = 0; i < orgCodes.length - 1; i++) {
				// if(!StringUtils.equals(orgCode, currUser.getOrgCode())){
				orgCodeSet.add(orgCodes[i]);
				// }
			}
			for (String orgCode : orgCodeSet) {
				SysOrg sysOrg = new SysOrg();
				sysOrg.setOrgCode(orgCode);
				List<SysOrg> tmpOrgList = orgService.findList(sysOrg);
				boolean falg = true;
				if (tmpOrgList.size() > 0) {
					for (String fullOrgCode : fullOrgCodeList) {
						String[] codes = fullOrgCode.split(",");
						if (StringUtils.equals(fullOrgCode, tmpOrgList.get(0).getFullOrgCodes())
								|| StringUtils.equals(topOrgCode, codes[codes.length - 1])) {
							SysOrg org = new SysOrg();
							org.setParentOrgId(tmpOrgList.get(0).getId());
							tmpOrgList = orgService.findList(org);
							falg = false;
							break;
						}
					}
				}

				for (SysOrg org : tmpOrgList) {
					Map<String, Object> map;
					if (!falg) {
						map = buildTreeNodeMap(org, false, true);
					} else {
						map = buildTreeNodeMap(org, true, false);
					}

					boolean isExsit = false;
					for (Map<String, Object> node : ret) {
						Map<String, Object> mapCode = (Map<String, Object>) node.get("otherParam");
						for (String code : orgCodeSet) {
							if (StringUtils.equals(code, mapCode.get("orgCode").toString())) {
								node.put("open", true);
							}
						}

						if (org.getId().equals(node.get("id").toString())) {
							map.put("canSelect", node.get("canSelect"));
							map.put("otherParam", node.get("otherParam"));
							ret.remove(node);
							ret.add(map);
							isExsit = true;
							break;
						}
					}

					if (!isExsit) {
						ret.add(map);
					}
				}
			}

		} else {

			SysOrg sysOrg = new SysOrg();
			sysOrg.setParentOrgId(pId);
			sysOrg.setWithSelf(false);
			List<SysOrg> orgList = orgService.findList(sysOrg);
			for (SysOrg entity : orgList) {
				Map<String, Object> map = buildTreeNodeMap(entity, false, true);
				ret.add(map);
			}
		}

		return ret;
	}

	/**
	 * 获取树所有子节点
	 */
	@Override
	public List<Map<String, Object>> loadAll(String rootId) {
		return null;
	}

	/**
	 * 机构树节点初始化
	 */
	@Override
	public Map<String, Object> config() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clearable", false);
		return map;
	}

	/**
	 * 树节点数据组装
	 * 
	 * @param org
	 *            SysOrg
	 * @param findRoot
	 *            boolean
	 * @return Map<String, Object>
	 */
	private Map<String, Object> buildTreeNodeMap(SysOrg org, boolean open, boolean canSelect) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", org.getId());
		map.put("pId", org.getParentOrgId());
		map.put("name", org.getOrgShortName());
		map.put("title", org.getOrgShortName() + "(" + org.getOrgCode() + ")");
		map.put("fullName", org.getOrgName());
		map.put("childNum", org.getChildNum());
		map.put("showorder", org.getShowOrder());

		Map<String, String> otherParam = new HashMap<String, String>();
		otherParam.put("fullOrgCodes", org.getFullOrgCodes());
		otherParam.put("orgCode", org.getOrgCode());
		otherParam.put("canSelect", String.valueOf(canSelect));
		map.put("otherParam", otherParam);
		map.put("canSelect", String.valueOf(canSelect));

		map.put("open", open);

		if (org.getChildNum() > 0) {
			map.put("isParent", "true");
		} else {
			map.put("isParent", "false");
		}
		return map;
	}

}
