<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="formId" type="java.lang.String" required="true" description="绑定表单ID" rtexprvalue="true" %>
<%@ attribute name="queryMethod" type="java.lang.String" required="true" description="绑定查询方法"%>
<%@ attribute name="display" type="java.lang.String" required="false" description="显示/隐藏"%>
<div class="layui-btn-group">
	<button class="layui-btn layui-btn-normal" onclick="${queryMethod};return false;"><i class='layui-icon'>&#xe615;</i>查询</button>
	<c:if test="${display == null || display ==''}">
		<button class="layui-btn layui-btn-primary" onclick="$('#${formId}').form('reset');${queryMethod};layui.form.render();return false;"><i class='icon iconfont'>&#xe68b;</i>重置</button>
	</c:if>
</div>