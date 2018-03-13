<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
	<body>
		<sys:panel algin="left" width="63%">	
			<div class="layui-tab layui-tab-card">
				<ul class="layui-tab-title" style="margin-bottom:10px">
		    		<li class="layui-this" id="ttTitle">第一步：选中一个用户</li>
			  	</ul>			
				<sys:form id="queryForm">
					<input type="hidden" id="fullOrgCodes" name="fullOrgCodes" value="${fullOrgCodes}" />
			        <sys:formRow>
			        	<sys:formItem lable="用户编码" lableStyle="width:60px;">
			        		<sys:input id="loginId" inputStyle="width:90px;"></sys:input>
			        	</sys:formItem>
			            <sys:formItem lable="用户名称" lableStyle="width:60px;">
			            	<sys:input id="name" inputStyle="width:90px;"></sys:input>
			        	</sys:formItem>
				        <sys:formItem lable="所属机构" lableStyle="width:60px;">
							<sys:treeSelect id="orgId" type="orgTree" width="80" panelHeight="203" panelWidth="170"
								value="${orgId}" formId="queryForm" ></sys:treeSelect>
						</sys:formItem>
			        	<sys:formItem>	
			        		<div class="layui-btn-group">
								<button class="layui-btn layui-btn-normal" onclick="query();;return false;">
									<i class='layui-icon'>&#xe615;</i>查询
								</button>
								<button class="layui-btn layui-btn-primary" onclick="resetAll();return false;">
									<i class='icon iconfont'>&#xe68b;</i>重置
								</button>
							</div>
				        </sys:formItem>
			        </sys:formRow>
				</sys:form>
				
				<sys:hline/>
				
				
			  	<div class="layui-tab-content">
			  		<table id="tt"  style="height:295px"> 
						<thead frozen="true"> 
						    	<tr>
									<th data-options="field:'loginId',width:100,halign:'center',align:'left',sortable:true">用户编码</th>
									<th data-options="field:'name',width:100,halign:'center',align:'left',sortable:true">用户名称</th>
								</tr>
						</thead>
						<thead>
					    	<tr>
								<th data-options="field:'fullOrgNames',width:'320',halign:'center',align:'left',sortable:true">所属机构</th>
								<th data-options="field:'remarks',width:'100',halign:'center',align:'left',sortable:true">备注</th>
					        </tr> 
					    </thead> 
					</table>	
			  	</div>
			</div>
			
		</sys:panel>
		
		<sys:panel algin="left" width="32%" style="margin-left:5px">
			<!-- 角色权限查看 -->
			<div id="userView">
				<div class="layui-tab layui-tab-card" lay-filter="demo">
			  		<ul class="layui-tab-title">
			    		<li class="layui-this">第二步：查看授予的角色</li>
						<li>第三步：查看授权机构</li>
				  	</ul>
				  	<!--  -->
			  		<div class="layui-tab-content">
			  		<!-- 用户角色 -->
				    	<div class="layui-tab-item layui-show">
					      	<table id="jscd" style="height:138px"> 
								<thead>	
							    	<tr>
										<th data-options="field:'code',width:80,halign:'center',align:'left',sortable:true">角色编码</th>
										<th data-options="field:'name',width:80,halign:'center',align:'left',sortable:true">角色名称</th>
										<th data-options="field:'fullOrgNames',width:113,halign:'center',align:'center',sortable:true">所属机构</th>
										<th data-options="field:'remarks',width:150,halign:'center',align:'left',sortable:true,hidden:true">备注</th>
							        </tr> 
							    </thead> 
							</table>
							<sys:hline/>
							<b>温馨提示：<span>选中某个角色可查看其关联的菜单和操作权限</span></b>
							<!-- 拥有的菜单权限 -->
							<div class="layui-tab layui-tab-card">
								<ul class="layui-tab-title">
						    		<li class="layui-this" id="menuTreeTitle">拥有的菜单权限</li>
							  	</ul>	
								<sys:treePanel type="userMenuTree" panelHeight="93" title="" 
									onClick="" id="menuTree" checkEnable="false" async="true">
								</sys:treePanel>
							</div>
					    </div>
					    <!-- 授权机构 -->
					    <div class="layui-tab-item">
					    	<table id="yhGrantOrg" style="height:353px">
								<thead>
									<tr>
										<th data-options="field:'orgCode',width:'110',halign:'center',align:'left',sortable:true">机构编码</th>
										<th data-options="field:'fullOrgNames',width:'163',halign:'center',align:'left',sortable:true">机构名称</th>
									</tr>
								</thead>
							</table>
					    </div>
				 	</div>
				</div>
			</div>
		</sys:panel>
		
		
		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysUserAuthority/sysUserAuthorityList.js"></script>
	</body>
</html>