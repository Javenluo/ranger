<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
	<body>
		
		<sys:panel algin="left" width="98%">
			<sys:form id="queryForm">
		        <sys:formRow>
		        	<sys:formItem lable="操作菜单" >
		        		<input id="title" name="title"  autocomplete="off" 
		        		autocomplete="off" style="width:150px;" class="layui-input"></input>
		        	</sys:formItem>
		            <sys:formItem lable="操作用户" >
	           	 		<input id="createByName" name="createByName"  autocomplete="off" 
		        		autocomplete="off" style="width:150px;" class="layui-input"></input>
		        	</sys:formItem>
		        	<sys:formItem lable="查询范围" lableStyle="width:100px;">
	           	 		<div class="layui-input-block">
	           	 			<input type="checkbox" id="type" name="type" value="2" title="只显示异常" style="margin-bottom: 6px;" lay-skin="primary">
					    </div>
		        	</sys:formItem>
		        	<sys:formItem>
			        	<sys:queryButton queryMethod="query();" formId="queryForm">
			        	</sys:queryButton>
			        </sys:formItem>
	       		</sys:formRow>
			</sys:form>
			
			<sys:hline/>
			
			<shiro:hasAnyPermissions name="sys:sysLog:edit">
				<sys:toolbar showExportExcel="true" exportFileName="数据列表"
					url="${ctx}/sys/sysLog/query" formId="queryForm">
				</sys:toolbar>
			</shiro:hasAnyPermissions>
		
			<table id="tt" > 
			   	<thead frozen="true">
					<tr>
						<th data-options="field:'id',checkbox:true">id</th>
						<th data-options="field:'title',sortable:true,halign:'center',align:'left',width:140">日志标题</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'orgName',sortable:true,halign:'center',align:'left',width:150">所属税务机关</th>
						<th data-options="field:'requestUri',sortable:true,halign:'center',align:'left',width:170">请求URI</th>
						<th data-options="field:'method',sortable:true,halign:'center',align:'center',width:90">操作方式</th>
						<th data-options="field:'remoteAddr',sortable:true,halign:'center',align:'center',width:120">操作IP地址</th>
						<th data-options="field:'exception',sortable:true,halign:'center',align:'left',width:180">异常信息</th>
						<th data-options="field:'createByName',sortable:true,halign:'center',align:'center',width:120">操作用户</th>
						<th data-options="field:'createStr',sortable:true,halign:'center',align:'center',width:120">操作日期</th>
					</tr>
				</thead>
			</table>
		</sys:panel>
		
		<div id="dialog"  style="display: none;padding: 10px 25px 0 0px; ">
			<sys:form id="dialogForm">
				<input type="hidden" id="id" name="id" />
				<sys:formRow>
					<sys:formItem lable="角色编码" isRequired="true">
						<sys:input id="code" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
					<sys:formItem lable="角色名称" isRequired="true">
						<sys:input id="name" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="所属机构" isRequired="true">
						<sys:treeSelect id="orgId" type="orgTree" width="180" panelHeight="300" panelWidth="280"
							value="${orgId}" formId="dialogForm"></sys:treeSelect>
					</sys:formItem>
					<sys:formItem lable="排列顺序" isRequired="true">
						<sys:input id="showOrder" inputStyle="width:180px;" ></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="是否有效" >
						<sys:checkbox checked="true" text="是|否" id="enableFlag"></sys:checkbox>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="备注">
						<sys:textarea id="remarks" inputStyle="width:490px;height:30px;"></sys:textarea>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
		
		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysLog/sysLogList.js"></script>
 	
	</body>
</html>