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
		            <sys:formItem lable="系统编码" >
	           	 		<sys:input id="xtbm" inputStyle="width:120px;"></sys:input>
		        	</sys:formItem>
	       	      	<sys:formItem lable="系统名称" >
	           	 		<sys:input id="xtmc" inputStyle="width:120px;"></sys:input>
		        	</sys:formItem>
		        	
		        	<sys:formItem>
			        	<sys:queryButton queryMethod="query();" formId="queryForm">
			        	</sys:queryButton>
			        </sys:formItem>
		        </sys:formRow>
			</sys:form>
			
			<sys:hline/>
			
			<shiro:hasAnyPermissions name="sys:sysWbxt:edit">
				<sys:toolbar showExportExcel="true" exportFileName="数据列表"
					url="${ctx}/sys/sysWbxt/query" formId="queryForm">
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
						<th data-options="field:'id',checkbox:true">id</th>
						<th data-options="field:'xtbm',sortable:true,halign:'center',align:'left',width:90">系统编码</th>
						<th data-options="field:'xtmc',sortable:true,halign:'center',align:'left',width:120">系统名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'fullOrgNames',sortable:true,halign:'center',align:'left',width:150">所属机构</th>
						<th data-options="field:'xtwz',sortable:true,halign:'center',align:'left',width:150">系统网址</th>
						<th data-options="field:'xtfzr',sortable:true,halign:'center',align:'left',width:100">负责人</th>
						<th data-options="field:'fxrlxdh',sortable:true,halign:'center',align:'center',width:120">联系电话</th>
					</tr>
				</thead>
			</table>
		</sys:panel>
		
		<div id="dialog"  style="display: none;padding: 10px 25px 0 0px; ">
			<sys:form id="dialogForm">
				<input type="hidden" id="id" name="id" />
				<sys:formRow>
					<sys:formItem lable="系统编码" isRequired="true">
						<sys:input id="xtbm" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
					<sys:formItem lable="系统名称" isRequired="true">
						<sys:input id="xtmc" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="所属机构" isRequired="true">
						<sys:treeSelect id="orgId" type="orgTree" width="470" panelHeight="250" panelWidth="480"
							value="${orgId}" formId="dialogForm"></sys:treeSelect>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
				<sys:formItem lable="网址" isRequired="true">
					<sys:input id="xtwz" inputStyle="width:470px;" required="true"></sys:input>
				</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="待办接口">
						<sys:input id="dbjk" inputStyle="width:470px;"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="接口账号">
						<sys:input id="jkzh" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
					<sys:formItem lable="接口密码">
						<sys:input id="jkmm" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="负责人" isRequired="true">
						<sys:input id="xtfzr" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
					<sys:formItem lable="联系方式" isRequired="true">
						<sys:input id="fxrlxdh" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="密码">
						<sys:input id="xtmm" inputStyle="width:180px;" required="true" readOnly="true"></sys:input>
					</sys:formItem>
						<%-- <sys:formItem>
						  <span>由系统生成</span>
					</sys:formItem>
					 --%>
				</sys:formRow>
			</sys:form>
		</div>
 	
 		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysWbxt/sysWbxtList.js"></script>
	</body>
</html>