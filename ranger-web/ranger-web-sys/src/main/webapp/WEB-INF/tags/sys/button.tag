<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="onclick" type="java.lang.String" required="true" description="点击事件"%>
<%@ attribute name="btnClass" type="java.lang.String" required="false" description="按钮样式"%>
<%@ attribute name="style" type="java.lang.String" required="false" description="样式"%>
<%@ attribute name="id" type="java.lang.String" required="false" description="id"%>

<c:if test="${not empty btnClass and btnClass!='' }">
<button class="layui-btn ${btnClass}" onclick="${onclick};return false;" style="${style}" id="${id }"><jsp:doBody></jsp:doBody></button>
</c:if>
<c:if test="${empty btnClass or btnClass=='' }">
<button class="layui-btn layui-btn-normal" onclick="${onclick};return false;" style="${style}" id="${id }"><jsp:doBody></jsp:doBody></button>
</c:if>
