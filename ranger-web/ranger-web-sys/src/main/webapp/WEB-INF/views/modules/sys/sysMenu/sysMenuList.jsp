<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
<body>
	<sys:panel algin="left" width="98%">
		<table id="tt" style="height:425px" > 
		    <thead frozen="true"> 
		        <tr> 
		        	<th data-options="field:'id',width:280,halign:'center'" align="center" formatter="formatOperation">操作</th> 
	        	</tr>
	        </thead>
		    <thead> 
		        <tr> 
		            <th data-options="field:'name',width:200,halign:'center'">菜单名称</th> 
		            <th data-options="field:'url',width:180,halign:'center'" >输入菜单路径</th> 
		            <th data-options="field:'imageUrl',width:80,halign:'center'" align="center" formatter="formatIocn">菜单图标</th> 
		            <th data-options="field:'sortNo',width:80,halign:'center'" align="center">同级顺序</th> 
		            <th data-options="field:'visible',width:80,halign:'center'" align="center"  formatter="formatVisible">是否可视</th> 
		        	<th data-options="field:'menuType',width:80,halign:'center'" align="center"  formatter="formatMenuType">菜单类型</th> 
		        	<th data-options="field:'permission',width:140,halign:'center'" >权限标识</th> 
		        </tr> 
		    </thead> 
		</table>
	</sys:panel>
	
	<div id="dialog"  style="padding: 10px 5px 0 15px; display: none; ">
		<sys:form id="dialogForm">
			<sys:formRow>
				<sys:formItem lable="上级菜单">
					<input type="hidden" id="parentIds" name="parentIds" />
					<input type="hidden" id="parentId" name="parentId" />
					<input type="hidden" id="currentId" name="id" />
					<input type="text" id="higher" name="higher" disabled="disabled"
	           			autocomplete="off" style="width:300px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="菜单名称" isRequired="true">
					<input type="text" id="name" name="name" placeholder="请输入菜单名称" 
						lay-verify="required"
	           			autocomplete="off" style="width:300px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="菜单路径 ">
					<input type="text" id="url" name="url" placeholder="请输入菜单路径" 
	           			autocomplete="off" style="width:200px;" class="layui-input"/>
				</sys:formItem>
           		<sys:button onclick="doS();" btnClass="layui-btn-small">从报表生成</sys:button>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="菜单图标">
					<input type="text" id="imageUrl" name="imageUrl" 
	           			autocomplete="off" style="width:200px;" class="layui-input"/>
				</sys:formItem>
           		<sys:button onclick="openIconWin();" btnClass="layui-btn-small">选择</sys:button>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="同级顺序">
					<input type="number" id="sortNo" name="sortNo"
	           			autocomplete="off" style="width:300px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="是否可视">
					 <input type="checkbox" name="visible" id="visible" lay-skin="switch"
					 	lay-text="是|否" checked value="1">
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="菜单类型">
					 <input id="type1" style=" border: none;" name="menuType" checked="checked" 
					 	type="radio" value="1" title="菜单"/>
	                 <input id="type2" style=" border: none;" name="menuType" 
	                 	type="radio" value="2" title="按钮"/>
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="权限标识">
					 <input type="text" id="permission" name="permission"
	           			autocomplete="off" style="width:300px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
		</sys:form>
	</div>
	
	<div id="iconWin"  style="display: none; ">
		<iframe src="${ctxStatic}/uimaker/green/fonts/demo_unicode.html"
			width="100%" height="250"></iframe>
	</div>
	
	
	
	<script>
		layui.use('form', function(){
		  var form = layui.form;
		  
		});
	</script>
	
	<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysMenu/sysMenuList.js"></script>
</body>
</html>