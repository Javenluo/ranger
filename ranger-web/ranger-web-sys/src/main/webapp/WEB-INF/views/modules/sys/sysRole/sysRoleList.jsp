<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
		<script type="text/javascript">
			var orgId = '${orgId}';
		</script>
	</head>
	<body>
		<sys:panel algin="left" width="73%">				 	
			<sys:form id="queryForm">
		 		<%-- <input type="hidden" id="fullOrgCodes" name="fullOrgCodes" value="${fullOrgCodes}" /> --%>
		        <sys:formRow>
			        	<sys:formItem lable="角色编码" lableStyle="width:90px;">
		            	<input type="text" name="code" placeholder="请输入角色编码" 
		            		autocomplete="off" style="width:120px;" class="layui-input">
		        	</sys:formItem> 
		            <sys:formItem lable="角色名称" >
	           	 		<input type="text" name="name" placeholder="请输入角色名称" 
		            		autocomplete="off" style="width:120px;" class="layui-input">
		        	</sys:formItem>
		        	<sys:formItem>	
		        		<div class="layui-btn-group">
							<button class="layui-btn layui-btn-normal" onclick="query();;return false;">
								<i class='layui-icon'>&#xe615;</i>查询
							</button>
							<button class="layui-btn layui-btn-primary" onclick="resetRole();return false;">
								<i class='icon iconfont'>&#xe68b;</i>重置
							</button>
						</div>
			        </sys:formItem>
		        </sys:formRow>
			</sys:form>
	 	
	 		<sys:hline />
	 		
	 		<shiro:hasAnyPermissions name="sys:sysRole:edit">
		 		<sys:toolbar showExportExcel="true" exportFileName="角色信息列表"
					url="${ctx}/sys/sysRole/query" formId="queryForm">
					<div class="layui-btn-group">
						<sys:button onclick="add();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe654;</i>新增</sys:button>
						<sys:button onclick="edit();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe642;</i>修改</sys:button>
						<sys:button onclick="delete_();" btnClass="layui-btn-small layui-btn-danger"><i class='layui-icon'>&#xe640;</i>删除</sys:button>
					</div>
					<div class="layui-btn-group">
			       		<sys:button onclick="zdcy();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe614;</i>指定成员</sys:button>
			       	</div>
				</sys:toolbar>
			</shiro:hasAnyPermissions>
			
			<table id="tt" > 
				<thead frozen="true"> 
			        <tr> 
			            <th data-options="field:'id',align:'center',checkbox:true">id</th>
						<th data-options="field:'code',halign:'center',align:'left',width:80,sortable:true">角色编号</th>
						<th data-options="field:'name',halign:'center',align:'left',width:120,sortable:true">角色名称</th>
					</tr>
				</thead>
			    <thead> 
			        <tr> 
					<th data-options="field:'fullOrgNames',halign:'center',align:'left',width:150,sortable:true">所属机构</th>
					<th data-options="field:'enableFlag',halign:'center',align:'center',width:80,sortable:true" formatter="formatenableFlag">是否有效</th>
					<th data-options="field:'showOrder',halign:'center',align:'center',width:80,sortable:true">显示顺序</th>
					<th data-options="field:'remarks',halign:'center',align:'left',width:190">备注</th>
			        </tr> 
			    </thead> 
			</table>
			
			<blockquote class="layui-elem-quote" style="margin-top: 5px;">
				说明：<span style="color: red;">单击表中角色，右侧将列出其所拥有的功能权限，并可重新设置。</span>
			</blockquote>
		</sys:panel>
			
		<sys:panel algin="right" width="22%">			
				<sys:treePanel type="grantTree" panelHeight="340" title="角色拥有的功能权限【请选择】" 
					onClick="treeNodeClick" id="menuTree" checkEnable="true" async="true">
					<sys:button onclick="saveRoleMenu()" btnClass="layui-btn-small layui-btn-normal">保存</sys:button>
					<sys:button onclick="resetRoleMenu()" btnClass="layui-btn-small">重置</sys:button>
				</sys:treePanel>
				<form>
					<input type="hidden" id="roleId" />
				</form>
		</sys:panel>
		
		<div id="dialog" style="display: none;padding: 10px 15px 0 0px; ">
			<sys:form id="dialogForm">
				<sys:formRow>
					<sys:formItem lable="角色编码" isRequired="true">
						<input type="hidden" id="id" name="id" />
						<sys:input id="code" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
					<sys:formItem lable="角色名称" isRequired="true">
						<sys:input id="name" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="排列顺序" isRequired="true">
						<sys:input id="showOrder" inputStyle="width:180px;" ></sys:input>
					</sys:formItem>
					<sys:formItem lable="所属机构" isRequired="true">
						<sys:treeSelect id="orgId1" type="orgTree" width="180" panelHeight="200" panelWidth="180"
							value="${orgId}" formId="dialogForm"></sys:treeSelect>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="是否有效" >
						<sys:checkbox checked="true" text="是|否" id="enableFlag"></sys:checkbox>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="备注">
						<sys:textarea id="remarks" inputStyle="width:480px;height:90px;"></sys:textarea>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
		
		<div id="zdcyDialog" style="display: none;padding: 10px 0 0 15px; ">
			<sys:form id="zdcyForm">
				<input type="hidden" id="roleId" name="roleId">
				<sys:formRow>
					<sys:formItem lable="登录账号" lableStyle="width:60px;">
						<sys:input id="loginId" inputStyle="width:100px;"></sys:input>
					</sys:formItem>
					<sys:formItem lable="用户姓名" lableStyle="width:70px;">
						<sys:input id="name" inputStyle="width:100px;"></sys:input>
					</sys:formItem>
					<sys:formItem lable="所属机构" lableStyle="width:70px;">
						<sys:treeSelect id="orgId" type="orgTree" width="150" panelHeight="203" panelWidth="170"
							value="${orgId}" formId="zdcyForm" ></sys:treeSelect>
					</sys:formItem>
					<sys:formItem itemStyle="padding-left:2px;">
						<sys:queryButton queryMethod="userQuery()" formId="zdcyForm" display="true">
			        	</sys:queryButton>
					</sys:formItem>
				</sys:formRow>
			</sys:form>	
			
			<sys:hline/>
			
			<div class="layui-tab layui-tab-brief" lay-filter="user">
			  	<ul class="layui-tab-title">
				    <li class="layui-this" lay-id="yx">已关联的用户</li>
				    <li>未关联的用户</li>
			  	</ul>
			  	<div class="layui-tab-content" style="height: 100px;">
				    <div class="layui-tab-item layui-show">
				    	<table id="yxUserTable">   
						    <thead> 
						    	<tr>
						            <th data-options="field:'id',align:'center',checkbox:true">id</th>
									<th data-options="field:'loginId',width:120,halign:'center',align:'left',sortable:true">登录账号</th>
									<th data-options="field:'orgCode',width:'120',halign:'center',align:'left',sortable:true">所属机构代码</th>
									<th data-options="field:'fullOrgNames',width:'230',halign:'center',align:'left',sortable:true">所属机构</th>
									<th data-options="field:'name',width:120,halign:'center',align:'left',sortable:true">用户姓名</th>
						        </tr> 
						    </thead> 
						</table>
				    </div>
				    <div class="layui-tab-item">
				    	<table id="wxUserTable">   
						    <thead> 
						    	<tr>
						            <th data-options="field:'id',align:'center',checkbox:true">id</th>
									<th data-options="field:'loginId',width:120,halign:'center',align:'left',sortable:true">登录账号</th>
									<th data-options="field:'orgCode',width:'120',halign:'center',align:'left',sortable:true">所属机构代码</th>
									<th data-options="field:'fullOrgNames',width:'230',halign:'center',align:'left',sortable:true">所属机构</th>
									<th data-options="field:'name',width:120,halign:'center',align:'left',sortable:true">用户姓名</th>
						        </tr> 
						    </thead> 
						</table>
				    </div>
			  	</div>
			</div> 
		</div>
		
		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysRole/sysRoleList.js"></script>
	
	</body>
</html>