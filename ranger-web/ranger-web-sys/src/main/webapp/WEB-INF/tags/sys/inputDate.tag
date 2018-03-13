<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputStyle" type="java.lang.String" required="false" description="输入框样式"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false" description="是否只读"%>
<%@ attribute name="required" type="java.lang.Boolean" required="false" description="是否必填"%>
<%@ attribute name="value" type="java.lang.String" required="false" description="默认值"%>
<%@ attribute name="istime" type="java.lang.Boolean" required="false" description="是否开启时间选择"%>
<%@ attribute name="format" type="java.lang.String" required="false" description="自定义格式"%>
<c:if test="${not empty readOnly and readOnly!='' }">
<input type="text" id="${id}" name="${id}" autocomplete="off" value="${value}"
	style="background-color:#E3E3E3;${inputStyle}"   readonly="readonly" class="layui-input"
	lay-verify="${required?'required':''}" 
	onFocus="laydate({istime: ${istime}, format: '${format}'})">
</c:if>
<c:if test="${empty readOnly or readOnly=='' }">
<input type="text" id="${id}" name="${id}" autocomplete="off" value="${value}"
	style="${inputStyle}" class="layui-input"
	lay-verify="${required?'required':''}" 
	onFocus="laydate({istime: ${istime}, format: '${format}'})">
</c:if>