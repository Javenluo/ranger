<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="ywgzCode" type="java.lang.String" required="true" description="业务规划编号" rtexprvalue="true"%>

<blockquote class="layui-elem-quote"><p>规则说明：</p>
<%int i=1; %>
<c:forEach items="${fns:getYwgzSm(ywgzCode)}" var="ywgz">
	${ywgz }
</c:forEach>
</blockquote>