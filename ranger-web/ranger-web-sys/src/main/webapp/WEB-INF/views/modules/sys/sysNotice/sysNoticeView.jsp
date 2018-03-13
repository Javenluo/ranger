<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="bpm" tagdir="/WEB-INF/tags/bpm"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
<body>
		
	<!-- 查看通知公告信息弹窗 start -->
      <div id="check" style="padding: 0px 0px 0px 0px; background-color: white;height:440px;width:100%;">
		<sys:form id="checkForm">
			<sys:formRow>
				<sys:formItem itemStyle="font-size:20px;font-weight:bold;width:100%;height:40px;padding-top:20px;font-family:'宋体' ">
					<sys:input id="noticeTitle"   inputStyle="border:0px;text-align:center;vertical-align:middle;" value="${sysNotice.noticeTitle}"></sys:input>
				</sys:formItem>
				</sys:formRow>
			    <sys:formRow>
				<sys:formItem itemStyle="width:40%;">
				<sys:input id="noticeType" inputStyle="border:0px;text-align:right;font-size:12px;color:gray;" value="${sysNotice.noticeType}"></sys:input>
				</sys:formItem>
				<sys:formItem itemStyle="width:20%;">
					<sys:input id="createBy" inputStyle="border:0px;text-align:center;font-size:14px;color:gray;" value="${sysNotice.createBy}"></sys:input>
				</sys:formItem>
				<sys:formItem itemStyle="width:40%;text-align:left;color:gray;padding-top:8px">
					  <fmt:formatDate pattern="yyyy-MM-dd" value="${sysNotice.createDate}"/>
				</sys:formItem>
			</sys:formRow>
		</sys:form>
		<sys:hline/>
		<div style="width:92%;padding-left: 50px;">
		
	         <span id='content' style="font-family:'仿宋_FB2312';font-size:16px;line-height:30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${sysNotice.content}
	         
	         </span>
		</div>
	 </div>
	<!-- 查看通知公告信息弹窗 end -->
	<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysNotice/sysNoticeView.js"></script>
</body>
</html>