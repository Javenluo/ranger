<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
	<body>
		<sys:panel algin="left" width="22%">
			<sys:treePanel type="orgTree"  title="组织机构列表" async="true"
					onClick="treeNodeClick" id="org" panelHeight="363" value="${orgId}"></sys:treePanel>
		</sys:panel>
		
		<sys:panel algin="right" width="73%">				
			<sys:form id="queryForm">
				<input type="hidden" id="fullOrgCodes" name="fullOrgCodes" value="${fullOrgCodes}" />
		        <sys:formRow>
		        	<sys:formItem lable="登录账号">
		        		<sys:input id="loginId" inputStyle="width:120px;"></sys:input>
		        	</sys:formItem>
		            <sys:formItem lable="用户姓名" lableStyle="width:90px;">
		            	<sys:input id="name" inputStyle="width:120px;"></sys:input>
		        	</sys:formItem>
		        	<sys:formItem>
			        	<sys:queryButton queryMethod="query();" formId="queryForm">
			        	</sys:queryButton>
			        </sys:formItem>
		        </sys:formRow>
			</sys:form>
			
			<sys:hline/>
			
			<shiro:hasAnyPermissions name="sys:sysUser:edit">
				<sys:toolbar showExportExcel="true" exportFileName="用户列表"
					url="${ctx}/sys/sysUser/query" formId="queryForm">
		        	<div class="layui-btn-group">
			        	<sys:button onclick="add();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe654;</i>新增</sys:button>
						<sys:button onclick="edit();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe642;</i>修改</sys:button>
						<sys:button onclick="delete_();" btnClass="layui-btn-small layui-btn-danger"><i class='layui-icon'>&#xe640;</i>删除</sys:button>
			       	</div>
			       	<div class="layui-btn-group">
			       		<sys:button onclick="zdjs();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe614;</i>指定角色</sys:button>
			       		<sys:button onclick="zdscjg();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe614;</i>授权机构</sys:button>
			       		<sys:button onclick="czmm();" btnClass="layui-btn-small layui-btn-normal"><i class='icon iconfont'>&#xe68b;</i>重置密码</sys:button>
			       	</div>
			       	<div  class="layui-btn-group">
						<sys:button onclick="downup();" btnClass="layui-btn-small layui-btn-small"><i class='icon iconfont'>&#xe67c;</i>导入</sys:button>
					</div>
				</sys:toolbar>
			</shiro:hasAnyPermissions>
		
			<table id="tt" > 
			    <thead frozen="true"> 
			    	<tr>
			            <th data-options="field:'id',align:'center',checkbox:true">id</th>
						<th data-options="field:'loginId',width:100,halign:'center',align:'left',sortable:true">登录账号</th>
						<th data-options="field:'name',width:100,halign:'center',align:'left',sortable:true">用户姓名</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'sex',width:80,halign:'center',align:'center',sortable:true, 
							formatter:function(value,row,index){if(value=='0') return '女';else if(value=='1'){return '男';} else return '';}">性别</th>
						<th data-options="field:'mobileTel',width:120,halign:'center',align:'left',sortable:true">电话</th>
						<th data-options="field:'email',width:150,halign:'center',align:'left',sortable:true">邮箱</th>
						<th data-options="field:'enableFlag',width:80,halign:'center',align:'center'" formatter="formatSatus">有效</th>
						<th data-options="field:'fullOrgNames',width:'250',halign:'center',align:'left',sortable:true">所属机构</th>
						<th data-options="field:'orgCode',width:'95',halign:'center',align:'left',sortable:true,hidden:true">所属机构代码</th>
						<th data-options="field:'showOrder',width:'80',halign:'center',align:'center',sortable:true">排列顺序</th>
						<th data-options="field:'tel',width:'95',halign:'center',align:'left',sortable:true,hidden:true">办公电话</th>
						<th data-options="field:'remarks',width:'95',halign:'center',align:'left',sortable:true,hidden:true">备注</th>
			        </tr> 
			    </thead> 
			</table>
		</sys:panel>
		
		<div id="tjyh"  style="padding: 8px;padding-left:15px; display: none; ">
			<sys:form id="addForm">
				<input type="hidden" id="id" name="id" />
				<input type="hidden" id="oldLoginId" name="oldLoginId" />
				<sys:formRow>
					<sys:formItem lable="登录帐号" isRequired="true">
	            		<sys:input id="loginId" inputStyle="width:180px;" required="true" readOnly="true"></sys:input>
					</sys:formItem>
					<sys:formItem lable="用户名称" isRequired="true">
						<sys:input id="name" inputStyle="width:180px;" required="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow> 
					<sys:formItem lable="性别" itemStyle="width:180px;">
						<input type="radio" name="sex" style=" border: none;" value="1" title="男">
      					<input type="radio" name="sex" style=" border: none;" value="0" title="女" checked>
					</sys:formItem>
					<sys:formItem lable="所属机构" isRequired="true">
						<sys:treeSelect id="orgId" type="orgTree" width="180" panelHeight="203" panelWidth="180"
							value="${orgId}" formId="addForm" ></sys:treeSelect>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="手机号码" >
						<sys:input id="mobileTel" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
					<sys:formItem lable="办公电话">
						<sys:input id="tel" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="邮箱" >
						<sys:input id="email" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
					<sys:formItem lable="排列顺序" isRequired="true">
						<sys:input id="showOrder" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="是否启用" itemStyle="width:180px;">
						<sys:checkbox id="enableFlag" checked="true" text="是|否" />
					</sys:formItem>
					<!-- <span style="margin-left: 140px;">
						<input type="checkbox" id="bmfzr" name="bmfzr" title="是否部门负责人" value="1" 
						lay-skin="primary">
					</span> -->
					<sys:formItem lable="是否部门负责人" itemStyle="width:100px;">
						<sys:checkbox id="bmfzr" checked="false" text="是|否" />
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="备注" >
						<sys:textarea id="remarks" inputStyle="width:490px;height:60px;"></sys:textarea>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
			<sys:hline/>
			<b style="font-weight: bold;">业务规则说明：</b>
			<ol style="line-height: 20px;font-size: 12px;padding-left: 10px;" >
				<li>
					1、“排列顺序”填入为数字，数字越大排列越靠后；用于角色与首页绑定时，按排序显示首页；
				</li>
				<li>
					2、新增用户密码默认为“111111”；用户默认为启用；
				</li>
			</ol>
		</div>
		
		<div id="roleWin"  style="padding: 8px;padding-left:15px; display: none; ">
			<sys:form id="roleForm">
				<input type="hidden" id="userId" name="userId" />
			</sys:form>
			<table width="100%">
				<tr>
					<td style="width: 45%;vertical-align: top;">
						<table id="kxjslb" style="width:300px;height:305px">
							<thead>
								<tr>
								    <th data-options="field:'id',align:'center',checkbox:true">id</th>
									<th data-options="field:'code',width:'90',align:'left',sortable:true">角色编码</th>
									<th data-options="field:'name',width:'170',align:'left',sortable:true">角色名称</th>
								</tr>
							</thead>
						</table>
						<script>
							$(function(){
	 						 	var $t = $("#kxjslb").datagrid({
	 						 		title:"可选角色",
	 						 		pagination:true,
	 						 		singleSelect:false,
	 						 		onBeforeLoad :function(){
	 						 			var $id = $(this).attr("id");
	 						 			var p = $("#"+$id).datagrid("getPager");
	 						 			$(p).pagination({
	 						 				showRefresh : true,
	 						 				displayMsg : "共{total}条",
	 						 				layout : [ 'first', 'prev', 'links','next', 'last', 'refresh' ],
	 						 				onBeforeRefresh :function(){
	 		 						 			$('#kxjslb').datagrid("options").queryParams = {userId : $('#roleForm #userId').val()};
	 		 						 			return true;
	 		 						 		},
	 						 				buttons: [{
												iconCls:'icon-search',
												handler:function(){
													cktj();
												}
											}]
	 						 			});
	 						 		}
	 						 	});
							});
						</script>
					</td>
					<td style="width: 10%;vertical-align: middle; text-align: center;padding: 5px;">
						<sys:button onclick="addRole()" btnClass="layui-btn-small">添加>></sys:button>
						<sys:hline/>
						<sys:button onclick="removeRole()" btnClass="layui-btn-small">&lt;&lt;移除</sys:button>
					</td>
					<td style="width: 45%;vertical-align: top;">
						<table id="yxjslb" style="width:300px;height:305px">
							<thead>
								<tr>
								    <th data-options="field:'id',align:'center',checkbox:true">id</th>
									<th data-options="field:'code',width:'90',align:'left',sortable:true">角色编码</th>
									<th data-options="field:'name',width:'170',align:'left',sortable:true">角色名称</th>
								</tr>
							</thead>
						</table>
						<script>
							 $(function(){
							 	$("#yxjslb").datagrid({
							 		title:"已选角色",
							 		pagination:false,
							 		singleSelect:false,
							 		url:''
							 	});
							}); 
						</script>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="grantOrgWin"  style="padding: 8px;padding-left:15px; display: none;">
			<sys:form id="grantOrgForm">
				<input type="hidden" id="userId" name="userId" />
			</sys:form>
			<table width="100%">
				<tr>
					<td style="width: 45%;vertical-align: top;background-color: #F1F4F5;">
						<sys:treePanel type="orgTree"  title="待选授权组织" checkEnable="true"
							onClick="" id="org2" panelHeight="270" chkStyle="radio" async="true"></sys:treePanel>
					</td>
					<td style="width: 10%;vertical-align: middle; text-align: center;padding: 5px;">
						<sys:button onclick="addGrantOrg()" btnClass="layui-btn-small">添加>></sys:button>
						<sys:hline/>
						<sys:button onclick="removeGrantOrg()" btnClass="layui-btn-small">&lt;&lt;移除</sys:button>
					</td>
					<td style="width: 45%;vertical-align: top;">
						<table id="yxGrantOrg">
							<thead>
								<tr>
								    <th data-options="field:'id',align:'center',checkbox:true">id</th>
									<th data-options="field:'orgCode',width:'90',halign:'center',align:'left',sortable:true">机构编码</th>
									<th data-options="field:'fullOrgNames',width:'170',halign:'center',align:'left',sortable:true">机构名称</th>
								</tr>
							</thead>
						</table>
						<script>
							 $(function(){
							 	$("#yxGrantOrg").datagrid({
							 		title:"已选授权组织",
							 		pagination:false,
							 		singleSelect:false,
							 		width:300,
							 		height:295,
							 		url:''
							 	});
							}); 
						</script>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="resetPwdWin"  style="padding: 8px;display: none; ">
			<sys:form id="resetPwdForm">
				<input type="hidden" id="id" name="id" />
				<sys:formRow>
					<sys:formItem lable="登录帐号" lableStyle="width:120px;">
	            		<sys:input id="loginId" inputStyle="width:180px;" readOnly="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="用户名称" lableStyle="width:120px;">
						<sys:input id="name" inputStyle="width:180px;" readOnly="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:hline/>
				<sys:formRow>
					<sys:formItem lable="新密码" isRequired="true" lableStyle="width:120px;">
						<sys:input type="password" id="newPasswd" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="确认密码" isRequired="true" lableStyle="width:120px;">
						<sys:input type="password" id="confirmNewPwd" inputStyle="width:180px;"></sys:input>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
		
		<div id="cktjWin"  style="padding: 8px;display: none; ">
			<sys:form id="cktjForm">
				<input type="hidden" id="userId" name="userId" />
				<sys:formRow>
					<sys:formItem lable="角色编码" lableStyle="width:120px;">
	            		<sys:input id="code" inputStyle="width:180px;" ></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="角色名称" lableStyle="width:120px;">
						<sys:input id="name" inputStyle="width:180px;" ></sys:input>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
		
		<div id="saveDialog"  style="display: none;padding: 10px 10px 0 40px; ">
			<sys:form id="saveForm" method="post" enctype="multipart/form-data">
				<sys:formRow>
					<sys:formItem lable="模板" lableStyle="width:80px;">
						<sys:button onclick="download();" ><i class='icon iconfont'>&#xe67b;</i>模板下载</sys:button>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="上传" lableStyle="width:80px;">
						<input class="easyui-filebox" id="file" name="file" style="width: 180px;height: 30px;" data-options="buttonText:'选择'" /> 
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
		
		<!-- 角色权限查看 -->
		<div id="userView" style="display: none;padding: 0 10px; ">
			<div class="layui-tab layui-tab-brief" lay-filter="demo">
				<ul class="layui-tab-title">
					<li class="layui-this">用户角色</li>
					<li>授权机构</li>
				</ul>
			</div>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">1</div>
				<div class="layui-tab-item">2</div>
			</div>
		</div>
		
		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysUser/sysUserList.js"></script>
	</body>
</html>