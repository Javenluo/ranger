<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="id" type="java.lang.String" required="false" %>

<div id="${id}" class="layui-form-item">
	<jsp:doBody></jsp:doBody>
</div>
