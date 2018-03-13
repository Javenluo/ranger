<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputStyle" type="java.lang.String" required="false" description="样式"%>
<%@ attribute name="readOnly" type="java.lang.String" required="false" description="是否只读"%>
<%@ attribute name="required" type="java.lang.Boolean" required="false" description="是否必填"%>
<c:if test="${not empty readOnly and readOnly!='' }">
<textarea id="${id}" name="${id}"  style="background-color:#E3E3E3;${inputStyle}"  
	readonly="${readOnly}" class="layui-textarea" lay-verify="${required?'required':''}" ></textarea>
</c:if>
<c:if test="${empty readOnly or readOnly=='' }">
<textarea id="${id}" name="${id}" style="${inputStyle}" class="layui-textarea" lay-verify="${required?'required':''}" ></textarea>
</c:if>