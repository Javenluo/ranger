<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputStyle" type="java.lang.String" required="false" description="样式"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false" description="是否只读"%>
<%@ attribute name="required" type="java.lang.Boolean" required="false" description="是否必填"%>
<%@ attribute name="onfocus" type="java.lang.String" required="false" description="得到焦点事件"%>
<%@ attribute name="onblur" type="java.lang.String" required="false" description="失去焦点事件"%>
<%@ attribute name="value" type="java.lang.String" required="false" description="默认值"%>
<%@ attribute name="type" type="java.lang.String" required="false" description="类型"%>
<c:if test="${not empty readOnly and readOnly!='' }">
<input type="${empty type?'text':type}" id="${id}" name="${id}" autocomplete="off" class="layui-input"
	style="background-color:#E3E3E3;${inputStyle}" value="${value}" readonly="readonly" 
		lay-verify="${required?'required':''}" >
</c:if>
<c:if test="${empty readOnly or readOnly=='' }">
<input type="${empty type?'text':type}" id="${id}" name="${id}"  value="${value}"  autocomplete="off" class="layui-input" style="${inputStyle}"
	lay-verify="${required?'required':''}" >
</c:if>