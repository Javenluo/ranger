var form;
var userId;
layui.use(['form','jquery','element'], function(){
	form = layui.form;
	var $= layui.jquery
	,element = layui.element;

	element.on('tab(demo)',function(elem){
		 if(elem.index == 0){
			 initRole(userId);
		 }
		 if(elem.index == 1){
			 initOrg(userId);
		 }
	});
	
	
	initUser();
	initRole();
});

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}

// 初始化用户表
function initUser(){
	//菜单树控件
	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysUserAuthority/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    singleSelect:true,
		onClickRow:function(index,row){
			initRole(row.id);
			initOrg(row.id);
			userId = row.id;
			
			//改变用户表标题
			$("#ttTitle").text("已选择【" + row.name +"】");
			//改变菜单树标题
			$("#menuTreeTitle").text("用户【"+row.name+"】拥有的菜单权限");
			
			//加载功能树
			var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex"); 
			zTreeObj.setting.async.otherParam = {userId : row.id};
			zTreeObj.reAsyncChildNodes(null, "refresh");
			zTreeObj.expandAll(true);
		}
	});
}


function initRole(value){
	$("#jscd").datagrid({
 		pagination:false,
 		singleSelect:true,
 		url:ctx+'/sys/sysUser/queryGrantRoleById',
 		queryParams : {userId :value},
		onClickRow:function(index,row){
			//改变菜单树标题
			$("#menuTreeTitle").text("角色【"+row.name+"】拥有的菜单权限");
			
			//加载功能树
			var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex");
			zTreeObj.setting.async.otherParam = {roleId : row.id};
			zTreeObj.reAsyncChildNodes(null, "refresh");
			zTreeObj.expandAll(true);
		}
 	});
}

function initOrg(value){
	$("#yhGrantOrg").datagrid({
 		pagination: true,
 		singleSelect: false,
 		url:ctx+'/sys/sysUser/queryGrantOrgById',
 		queryParams : {userId :value}
 	});
}

function resetAll(){
	//清空条件
	$('#queryForm').form('reset');
	
	//初始化
	initUser();
	initRole();
	initOrg();
	resetTree();
	userId = null;
	
	//初始化标题
	$("#ttTitle").text("第一步：选中一个用户");
	$("#menuTreeTitle").text("拥有的菜单权限");
}

function resetTree(){
	//加载右侧功能树
	var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex");
	zTreeObj.checkAllNodes(false); //取消勾选全部节点
}



