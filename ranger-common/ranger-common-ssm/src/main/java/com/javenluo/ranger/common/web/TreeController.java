package com.javenluo.ranger.common.web;

import com.javenluo.ranger.common.service.ITreeService;
import com.javenluo.ranger.common.utils.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;


/**
 * 树Controller
 * @author gulong
 * @version 2017-12-26
 */
@Controller
@RequestMapping("${adminPath}/common/tree")
public class TreeController {

	/**
	 * 载入孩子节点。
	 * @param request  HttpServletRequest
	 * @param type 树类型
	 * @param id 父ID
	 * @return List
	 */
	@ResponseBody
	@RequestMapping("loadChildren/{type}")
	public List<Map<String, Object>> loadChildren(HttpServletRequest request,
			@PathVariable String type, String id) {
		ITreeService service = null;
		Map<String,ITreeService> beans = SpringContextHolder.getApplicationContext().getBeansOfType(ITreeService.class);
		for (ITreeService ser : beans.values()) {
			if(StringUtils.equals(type, ser.getType())){
				service = ser;
				break;
			}
		}
		
		Enumeration names = request.getParameterNames();
		Map<String, String> map2 = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			map2.put(name, value);
		}
		
		String withSelf = request.getParameter("withSelf");
		List<Map<String,Object>> list = null;
		if(withSelf!=null && withSelf.equals("true")){
			list = service.loadChildren(id,true,new HashMap<String,String>());
		}else{
			list = service.loadChildren(id,false,map2);
		}
		
		String easyui_tree_node_flag = request.getParameter("easyui_tree_node_flag");
		if(easyui_tree_node_flag!=null && easyui_tree_node_flag.equals("yes")){
			list = convert2EasyUINodes(list);
		}
		
		return list;
	}
	
	private List<Map<String, Object>> convert2EasyUINodes(List<Map<String, Object>> list){
	    Map<String, Map<String, Object>> nodeList = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> map : list) {
			map.put("text", map.get("name"));
			map.put("state", "open");
			if(map.get("childNum")!=null){
				Integer childNum = (Integer)map.get("childNum");
				if(childNum>0){
					Object open = map.get("open");
					if(open!=null && (Boolean)open){
						//TODO
						map.put("state", "open");
					}else{
						map.put("state", "closed");
					}
				}
			}
			String nodeid = (String)map.get("id");
			nodeList.put(nodeid, map);
		}
		
		List<Map<String, Object>> parentNodes = new ArrayList<Map<String, Object>>();
		 Set<Entry<String, Map<String, Object>>> entrySet = nodeList.entrySet();
		 for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
			 Map<String, Object> node = ((Entry<String, Map<String, Object>>) iterator.next()).getValue();
			if(nodeList.get(node.get("pId"))!=null){
				 Map<String, Object> map = nodeList.get(node.get("pId"));
				 List<Map<String, Object>> ss = null;
				 if(map.containsKey("children")){
					 ss = (List<Map<String, Object>>) map.get("children");
				 }else{
					 ss = new ArrayList<Map<String, Object>>();
				 }
				 ss.add(node);
				 
				 Collections.sort(ss, new Comparator<Map<String, Object>>(){
					public int compare(Map<String, Object> o1, Map<String, Object> o2) {
						Integer d1 = (Integer) o1.get("showorder");
						if(d1==null) d1=0;
						Integer d2 = (Integer) o2.get("showorder");
						if(d2==null) d2=0;
						return (int) (d1*100 - d2*100);
					}
					 
				 });
				 
				 map.put("children", ss);
			}else{
				parentNodes.add(node);
			}
		}
		
		
		return parentNodes;
	}
}
