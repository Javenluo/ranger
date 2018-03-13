<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="style" type="java.lang.String" required="false"  description="样式"%>
<%@ attribute name="algin" type="java.lang.String" required="false"  description="浮动属性"%>
<%@ attribute name="width" type="java.lang.String" required="false"  description="宽度"%>
<%@ attribute name="id" type="java.lang.String" required="false"  description="id"%>

<div id="${id}" style="float: ${algin};width:${width};padding: 11px;background: #FFFFFF;${style}" width="${width}">
	<jsp:doBody></jsp:doBody>
</div>