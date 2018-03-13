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
		        	<sys:formItem lable="规则编码">
		        		<input id="ywgzBm" name="ywgzBm"  autocomplete="off" 
		        		autocomplete="off" style="width:150px;" class="layui-input"></input>
		        	</sys:formItem>
		        	<sys:formItem>
			        	<sys:queryButton queryMethod="query();" formId="queryForm">
			        	</sys:queryButton>
			        </sys:formItem>
		        </sys:formRow>
			</sys:form>
			
			<sys:hline/>
			
			<shiro:hasAnyPermissions name="sys:sysYwgzsm:edit,">
				<sys:toolbar showExportExcel="true" exportFileName="数据列表"
					url="${ctx}/sys/sysYwgzsm/query" formId="queryForm">
					<div class="layui-btn-group">
						<sys:button onclick="add();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe654;</i>新增</sys:button>
						<sys:button onclick="edit();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe642;</i>修改</sys:button>
						<sys:button onclick="delete_();" btnClass="layui-btn-small layui-btn-danger"><i class='layui-icon'>&#xe640;</i>删除</sys:button>
					</div>
				</sys:toolbar>
			</shiro:hasAnyPermissions>
		
			<table id="tt" > 
			   	<thead frozen="true">
					<tr>
						<th data-options="field:'id',checkbox:true"">id</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'ywgzBm',sortable:true,halign:'center',align:'left',width:150">规则编码</th>
						<th data-options="field:'ywgzSm',sortable:true,halign:'center',align:'left',width:500">规则说明</th>
						<th data-options="field:'remarks',sortable:true,halign:'center',align:'left',width:300">备注</th>
					</tr>
				</thead>
			</table>
		</sys:panel>
			
		<div id="dialog"  style="display: none;padding: 10px 25px 0 0px; ">
			<sys:form id="dialogForm">
				<input type="hidden" id="id" name="id" />
				<sys:formRow>
					<sys:formItem lable="规则编码" isRequired="true" lableStyle="width:100px">
						<sys:input id="ywgzBm" inputStyle="width:410px;" required="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem formId="uEditorContent" lable="规则说明" isRequired="true" lableStyle="width:100px">
						<%-- <sys:textarea id="ywgzSm" inputStyle="width:390px;height:100px;"></sys:textarea> --%>
							<!-- <textarea id="ywgzSmFwb" style="width:500px;height:100px;" ></textarea>
							<textarea id="ywgzSm" style="display: none;" ></textarea> -->
							
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注" lableStyle="width:100px">
						<sys:textarea id="remarks" inputStyle="width:410px;height:40px;"></sys:textarea>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
 		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysYwgzsm/sysYwgzsmList.js"></script>
	</body>
</html>