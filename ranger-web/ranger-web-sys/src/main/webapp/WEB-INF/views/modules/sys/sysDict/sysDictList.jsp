<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysDict/sysDictList.js"></script>
	</head>
	<body>
	
		<sys:panel algin="left" width="98%">
			<sys:form id="queryForm">
		        <sys:formRow>
		        	<sys:formItem lable="类型" lableStyle="width:60px;">
		            	<input type="text" name="type" placeholder="请输入类型" 
		            		autocomplete="off" style="width:170px;" class="layui-input">
		        	</sys:formItem>
		            <sys:formItem lable="类型描述" >
	           	 		<input type="text" name="description" placeholder="请输入类型描述" 
		            		autocomplete="off" style="width:170px;" class="layui-input">
		        	</sys:formItem>
		        	<sys:formItem>
			        	<sys:queryButton queryMethod="query();" formId="queryForm">
			        	</sys:queryButton>
			        </sys:formItem>
		        </sys:formRow>
			</sys:form>
			
			<sys:hline/>
			
			<shiro:hasAnyPermissions name="sys:sysDict:edit">
				<sys:toolbar showExportExcel="true" exportFileName="数据字典列表"
					url="${ctx}/sys/sysDict/query" formId="queryForm">
						<div class="layui-btn-group">
							<sys:button onclick="add();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe654;</i>新增</sys:button>
							<sys:button onclick="edit();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe642;</i>修改</sys:button>
							<sys:button onclick="delete_();" btnClass="layui-btn-small layui-btn-danger"><i class='layui-icon'>&#xe640;</i>删除</sys:button>
						</div>
				</sys:toolbar>
			</shiro:hasAnyPermissions>
			
			<table id="tt" > 
			    <thead> 
			        <tr> 
			            <th data-options="field:'id',checkbox:true,halign:'center'"></th> 
			            <th data-options="field:'type',width:120,halign:'center',sortable:true">类型</th> 
			            <th data-options="field:'description',width:180,halign:'center',sortable:true">类型描述</th> 
			            <th data-options="field:'value',width:80,halign:'center'" >键值</th> 
			            <th data-options="field:'label',width:150,halign:'center'">标签</th> 
			            <th data-options="field:'sort',width:80,halign:'center',align:'center'">排序</th> 
			            <th data-options="field:'remarks',width:330,halign:'center'">备注</th> 
			        </tr> 
			    </thead> 
			</table>
		</sys:panel>
	
		<div id="dialog"  style="display: none;padding: 10px 20px 0 0px; ">
			<sys:form id="dialogForm">
				<sys:formRow>
					<sys:formItem lable="类型" isRequired="true">
						<input type="hidden" id="id" name="id" />
						<input type="text" id="type" name="type" placeholder="请输入类型" 
							lay-verify="required"
	            			autocomplete="off" style="width:180px;" class="layui-input">
					</sys:formItem>
					<sys:formItem lable="描述">
						<input type="text" id="description" name="description" placeholder="请输入类型描述" 
	            			autocomplete="off" style="width:180px;" class="layui-input">
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="键值" isRequired="true">
						<input type="text" id="value" name="value" 
							lay-verify="required"
	            			autocomplete="off" style="width:180px;" class="layui-input">
					</sys:formItem>
					<sys:formItem lable="标签" isRequired="true">
						<input type="text" id="label" name="label" 
							lay-verify="required"
	            			autocomplete="off" style="width:180px;" class="layui-input">
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="排序" isRequired="true">
						<input type="number" id="sort" name="sort" 
							lay-verify="required"
	            			autocomplete="off" style="width:180px;" class="layui-input">
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="备注">
						<textarea id="remarks" name="remarks"
						 	class="layui-textarea" style="width:470px;height:100px;" ></textarea>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
	</body>
</html>