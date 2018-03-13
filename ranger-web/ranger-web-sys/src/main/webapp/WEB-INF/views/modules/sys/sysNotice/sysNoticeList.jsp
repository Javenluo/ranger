<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="bpm" tagdir="/WEB-INF/tags/bpm"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
  <body>
	 <sys:panel>
		<sys:form id="queryForm">
			<sys:formRow>
				<sys:formItem lable="公告标题" lableStyle="width:80px;">
		        	<sys:input id="noticeTitle" inputStyle="width:120px;"></sys:input>
		        </sys:formItem>
				<sys:formItem lable="公告类型" lableStyle="width:80px;" itemStyle="width:130px">
					<select id ="noticeType" name="noticeType">
							<option value=" ">--请选择--</option>
				            <option value="0">系统公告</option>
				            <option value="1">系统通知</option>
				            <option value="2">消息</option>
					</select>
		        </sys:formItem>
		        <sys:formItem lable="阅读状态" lableStyle="width:80px;" itemStyle="width:130px">
					<select name="checked" >
				  		     <option value=" ">--请选择--</option>
				  			 <option value="0">未阅</option>
				  			 <option value="1">已阅</option>
		  		    </select>
				</sys:formItem>
			    <sys:formItem >
			        <sys:queryButton queryMethod="query();" formId="queryForm"></sys:queryButton>
			    </sys:formItem>
			</sys:formRow>
			
			<sys:formRow>
				<sys:formItem lable="发起人" lableStyle="width:80px;">
			        	<sys:input id="createBy" inputStyle="width:120px;"></sys:input>
		        </sys:formItem>
		        <sys:formItem lable="发起时间起" lableStyle="width:80px;">
			        <sys:inputDate id="fqsjq" format="YYYYMMDD" 
							istime="false" required="true" inputStyle="width:130px;">
							</sys:inputDate>
				</sys:formItem>
			    <sys:formItem lable="发起时间止" lableStyle="width:80px;">
			        <sys:inputDate id="fqsjz" format="YYYYMMDD" 
							istime="false" required="true" inputStyle="width:130px;">
							</sys:inputDate>
			     </sys:formItem>
		    </sys:formRow>
		</sys:form>
		
		<sys:hline/>
		<%-- <sys:toolbar showExportExcel="true" exportFileName="系统消息表数据列表"
			url="${ctx}/sys/sysNotice/query" formId="queryForm">
			<div class="layui-btn-group">
				<sys:button onclick="add();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe654;</i>新增</sys:button>
				 <sys:button onclick="edit();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe642;</i>修改</sys:button>
				<sys:button onclick="delete_();" btnClass="layui-btn-small layui-btn-danger"><i class='layui-icon'>&#xe640;</i>删除</sys:button> 
			</div>
		</sys:toolbar> --%>
	
		<table id="tt"  > 
	       <thead>
				<tr>
					<th data-options="field:'noticeTitle',sortable:true,halign:'center',align:'left',width:220,formatter:formatNoticeTitle,styler:function(value,row,index){
							if(row.checked=='0'){
					   return'font-weight:bold';
					  }else if(row.checked=='1'){
					  return'font-weight:none';
					  }
						}">主题</th>
					<th data-options="field:'noticeType',sortable:true,halign:'center',align:'center',width:110,formatter:formatNoticeType,styler:function(value,row,index){
							if(row.checked=='0'){
					   return'font-weight:bold';
					  }else if(row.checked=='1'){
					  return'font-weight:none';
					  }
						}">公告类型</th>
					<th data-options="field:'content',sortable:true,halign:'center',align:'left',width:300,formatter:formatContent,styler:function(value,row,index){
							if(row.checked=='0'){
					   return'font-weight:bold';
					  }else if(row.checked=='1'){
					  return'font-weight:none';
					  }
						}">公告内容</th>
					<th data-options="field:'createBy',sortable:true,halign:'center',align:'left',width:120,styler:function(value,row,index){
							if(row.checked=='0'){
					   return'font-weight:bold';
					  }else if(row.checked=='1'){
					  return'font-weight:none';
					  }
						}">发起人</th>
					<th data-options="field:'createDate',sortable:true,halign:'center',align:'center',width:130,formatter:formatCreateDate,styler:function(value,row,index){
							if(row.checked=='0'){
					   return'font-weight:bold';
					  }else if(row.checked=='1'){
					  return'font-weight:none';
					  }
						}">发起时间</th>
				</tr>
		   	<thead>
		</table>
	</sys:panel>
	
		<div id="dialog"  style="display: none;padding: 10px 10px 0 40px; ">
			<sys:form id="dialogForm">
				<input type="hidden" id="id" name="id" />
				<sys:formRow>
					<sys:formItem lable="公告标题" lableStyle="width:85px;">
						<sys:input id="noticeTitle" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
   					<sys:formItem lable="公告类型,0-系统公告,1-系统通知,2-消息" lableStyle="width:85px;">
						<sys:input id="noticeType" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
			   </sys:formRow>
			   <sys:formRow>
   					<sys:formItem lable="该公告有效的截止日期" lableStyle="width:85px;">
						<sys:input id="validDate" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
   					<sys:formItem lable="公告是否有效,1-有效,2,待清理" lableStyle="width:85px;">
						<sys:input id="validFlag" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
			   </sys:formRow>
			   <sys:formRow>
   					<sys:formItem lable="公告内容" lableStyle="width:85px;">
						<sys:input id="content" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
   					<sys:formItem lable="要通知的ID，带@的是机构ID，否则是人员ID" lableStyle="width:85px;">
						<sys:input id="contactId" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
			   </sys:formRow>
			   <sys:formRow>
   					<sys:formItem lable="公告备注信息" lableStyle="width:85px;">
						<sys:input id="remark" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
   					<sys:formItem lable="是否查看（0：未查看，1：已查看）" lableStyle="width:85px;">
						<sys:input id="checked" inputStyle="width:150px;"></sys:input>
			   		</sys:formItem>
			   </sys:formRow>
			   <sys:formRow>
   			  </sys:formRow>
			</sys:form>
		</div>
	<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysNotice/sysNoticeList.js"></script>
	</body>
</html>