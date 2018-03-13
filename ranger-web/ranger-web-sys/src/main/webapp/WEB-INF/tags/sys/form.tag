<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="id" rtexprvalue="true" %>
<%@ attribute name="formClass" type="java.lang.String" required="false" description="表单样式" rtexprvalue="true" %>
<%@ attribute name="method" type="java.lang.String" required="false" description="method 属性设置" rtexprvalue="true" %>
<%@ attribute name="enctype" type="java.lang.String" required="false" description="表单数据编码设置" rtexprvalue="true" %>

<form id="${id}" class="layui-form ${formClass}" method="${method}" enctype="${enctype}">
	 <jsp:doBody></jsp:doBody>
</form>