<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="id" %>
<%@ attribute name="text" type="java.lang.String" required="true" description="自定义文本" %>
<%@ attribute name="checked" type="java.lang.Boolean" description="设定默认选中 " %>
<%@ attribute name="disabled" type="java.lang.String" description="启用/禁用" %>
<input  type="checkbox" ${disabled} name="${id}" id="${id}"  ${checked?'checked':''} lay-skin="switch" lay-text="${text}" 
	value="1">