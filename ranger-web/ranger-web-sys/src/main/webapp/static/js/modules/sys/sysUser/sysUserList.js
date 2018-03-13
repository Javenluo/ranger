layui.use(['form','jquery','element'], function(){
	var form = layui.form;
	var $= layui.jquery
	,element = layui.element;

	initUser();
});

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}

// 初始化用户表
function initUser(){
	//菜单树控件
	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysUser/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    singleSelect:false
	});
}

/**
 * 添加用户
 */
function add(){
	$("#tjyh #addForm")[0].reset();
	$("#tjyh #addForm #loginId").attr('readonly',false);
	$("#tjyh #addForm #loginId").css('background-color','#ffffff');
	setDefaultValue_orgId();
	layui.form.render();
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增用户",
		maxmin : true,
		area : [ '660px', '425px' ],
		content : $('#tjyh'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
}

/**
 * 修改按钮
 */
function edit(){
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if (rows.length > 1) {
		fbidp.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}
	
	$("#addForm")[0].reset();
	$("#tjyh #addForm #loginId").attr('readonly',true);
	$("#tjyh #addForm #loginId").css('background-color','#E3E3E3');
	$('#addForm').form('load',rows[0]);
	$('#addForm #orgId').combo("clear");
	$('#addForm #orgId').combo("setValue",rows[0].orgId).combo("setText",rows[0].fullOrgNames);
	$("#addForm #oldLoginId").val(rows[0].loginId);
	if(rows[0].bmfzr == "1"){
		$('#bmfzr').prop("checked",true);
		$('#bmfzr').val("1");
		
	}else{
		$('#bmfzr').prop("checked",false);
		$('#bmfzr').val("0");
	}
	
	layui.form.render();
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改用户",
		maxmin : true,
		area : [ '660px', '425px' ],
		content : $('#tjyh'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
}

/**
 * 删除按钮触发的事件
 */
function delete_() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	fbidp.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx+"/sys/sysUser/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					fbidp.utils.alertInfo("操作成功！",function(){
						fbidp.utils.close();
						query();
					});
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});		 
}

/**
 * dialog确定按钮
 */
function okFunction(){
	var varUrl = '';
	if($("#addForm #id").val() == '' ){
		varUrl = ctx+'/sys/sysUser/insert';
	}else{
		varUrl = ctx+'/sys/sysUser/update';
	}
	
	var formData = $("#tjyh #addForm").serializeObject();
	if(formData.enableFlag==null){
		formData.enableFlag = "0";
	}
	if($.trim(formData.loginId)==""){
		fbidp.utils.alertWarn("登录账号不能为空，请填写！");
		return false;
	}
	if($.trim(formData.name)==""){
		fbidp.utils.alertWarn("用户名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.orgId)==""){
		fbidp.utils.alertWarn("所属机构不能为空，请选择！");
		return false;
	}
	if($.trim(formData.showOrder)==""){
		fbidp.utils.alertWarn("排列顺序不能为空，请填写！");
		return false;
	}
	
	if($('#bmfzr').prop("checked")){
		formData.bmfzr = "1";
	}else{
		formData.bmfzr = "0";
	}
	
	saveData(varUrl,formData);
}

function saveData(varUrl,formData){
	fbidp.utils.confirm("确定要提交数据吗?", function(r) {
		if (r) {
			$.post(varUrl,formData,function(data){
				if(data.success){
					fbidp.utils.alertInfo("操作成功！", function(){
						fbidp.utils.close();
						query();
					});
				}else{
					fbidp.utils.alertError(data.msg);
				}
			},"json");
		}
	});
}

function formatSatus(value){
    if (value=="1"){
        return "<span class='layui-icon'>&#xe618;</span>";
    } else {
    	return "<span class='layui-icon'>&#x1006;</span>";
    }
}

function treeNodeClick(e,treeId,treeNode){
	$("#queryForm #fullOrgCodes").val(treeNode.otherParam.fullOrgCodes);
	query();
}

//===============指定角色=================
/**
 * 指点角色
 */
function zdjs(){
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if (rows.length > 1) {
		fbidp.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}
	
	$('#roleForm #userId').val(rows[0].id);
	
	$("#kxjslb").datagrid("options").url = ctx+'/sys/sysUser/queryNotGrantRoleById';
	$("#kxjslb").datagrid("load",{userId : rows[0].id });
	
	$("#yxjslb").datagrid("options").url = ctx+'/sys/sysUser/queryGrantRoleById';
	$("#yxjslb").datagrid("load",{userId : rows[0].id });
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "指定角色【登录账号："+rows[0].loginId+"】",
		maxmin : true,
		area : [ '700px', '438px' ],
		content : $('#roleWin'), //这里content是一个DOM
		btn : [ '取消' ],
	});
}

//===============指定授权机构=================
/**
 * 指点授权机构
 */
function zdscjg(){
	
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if (rows.length > 1) {
		fbidp.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}
	
	$('#grantOrgForm #userId').val(rows[0].id);
	
	$("#yxGrantOrg").datagrid("options").url = ctx+'/sys/sysUser/queryGrantOrgById';
	$("#yxGrantOrg").datagrid("load",{userId : rows[0].id });
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "指定授权机构【登录账号："+rows[0].loginId+"】",
		maxmin : true,
		area : [ '700px', '438px' ],
		content : $('#grantOrgWin'), //这里content是一个DOM
		btn : ['取消' ]
	});
}
//==========================
/**
 * 重置密码
 */
function czmm(){
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if (rows.length > 1) {
		fbidp.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}
	
	$("#resetPwdForm")[0].reset();
	$('#resetPwdForm').form('load',rows[0]);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "重置用户密码",
		maxmin : true,
		area : [ '400px', '270px' ],
		content : $('#resetPwdWin'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			var formData = $("#resetPwdWin #resetPwdForm").serializeObject();
			if($.trim(formData.newPasswd)==""){
				fbidp.utils.alertWarn("新密码不能为空，请填写！");
				return false;
			}
			if($.trim(formData.confirmNewPwd)==""){
				fbidp.utils.alertWarn("确认密码不能为空，请填写！");
				return false;
			}
			if(formData.newPasswd != formData.confirmNewPwd){
				fbidp.utils.alertWarn("两次输入的密码不一致，请确认！");
				return false;
			}

			var varUrl = ctx+'/sys/sysUser/resetPwd';
			$.post(varUrl,formData,function(data){
				if(data.success){
					fbidp.utils.alertInfo("操作成功！", function(){
						fbidp.utils.close();
					});
				}else{
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			},"json");
			
		}
	});
}

//==========================
/**
 * 打开过滤条件框
 */
function cktj(){
	var rows = $("#tt").datagrid('getSelections');
	
	$('#cktjForm #userId').val($('#roleForm #userId').val());
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "过滤角色",
		maxmin : true,
		area : [ '400px', '220px' ],
		content : $('#cktjWin'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function(index) {
			$('#kxjslb').datagrid("reload",$("#cktjForm").serializeObject());
			$('#cktjForm')[0].reset();
			fbidp.utils.close(index);
		}
	});
}

function addRole(){
	var rows = $("#kxjslb").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请在左侧选择要添加的角色！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	fbidp.utils.confirm("确定要添加选中的角色吗?", function(r) {
		if (r) {
			var userId = $('#roleForm #userId').val();
			$.post(ctx+"/sys/sysUser/addUserRoles", {userId : userId, ids : ids.join('&') }, function(result) {
				if (result.success) {
					$('#kxjslb').datagrid("reload",$("#roleForm").serializeObject());
					$('#yxjslb').datagrid("reload",$("#roleForm").serializeObject());
					fbidp.utils.alertInfo("操作成功！");
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});		 
}

function removeRole(){
	var rows = $("#yxjslb").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请在右侧选择要移除的角色！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	fbidp.utils.confirm("确定要移除选中的角色吗?", function(r) {
		if (r) {
			var userId = $('#roleForm #userId').val();
			$.post(ctx+"/sys/sysUser/removeUserRoles", {userId : userId, ids : ids.join('&') }, function(result) {
				if (result.success) {
					$('#kxjslb').datagrid("reload",$("#roleForm").serializeObject());
					$('#yxjslb').datagrid("reload",$("#roleForm").serializeObject());
					fbidp.utils.alertInfo("操作成功！");
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});		
}

//========================================================
function addGrantOrg(){
	var treeObj = $.fn.zTree.getZTreeObj("org2_treex");
	var nodes = treeObj.getCheckedNodes();
	
	if (nodes.length == 0) {
		fbidp.utils.alertWarn("请在左侧选择要授权的组织机构！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<nodes.length;i++) {
		ids.push(nodes[i].id);
	}
	
	fbidp.utils.confirm("确定要添加选中的组织机构吗?", function(r) {
		if (r) {
			var userId = $('#grantOrgForm #userId').val();
			$.post(ctx+"/sys/sysUser/addGrantOrg", {userId : userId, ids : ids.join('&') }, function(result) {
				if (result.success) {
					$('#yxGrantOrg').datagrid("reload",$("#grantOrgForm").serializeObject());
					fbidp.utils.alertInfo("操作成功！");
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});		
	
}

function removeGrantOrg(){
	var rows = $("#yxGrantOrg").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请在右侧选择要移除的组织机构！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	fbidp.utils.confirm("确定要移除选中的组织机构吗?", function(r) {
		if (r) {
			var userId = $('#grantOrgForm #userId').val();
			$.post(ctx+"/sys/sysUser/removeGrantOrg", {userId : userId, ids : ids.join('&') }, function(result) {
				if (result.success) {
					$('#yxGrantOrg').datagrid("reload",$("#grantOrgForm").serializeObject());
					fbidp.utils.alertInfo("操作成功！");
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});		
}


/**
 * 下载模板
 */
function download() {
	var form = $("<form>");
	form.attr("target", "");
	form.attr("method", "post");
	form.attr("style", "display:none");
	form.attr("action", ctx + "/sys/sysUser/download");
	$("body").append(form);
	form.submit().remove();
}

/**
 * 上传
 */
function downup() {

	layer.open({
		type : 1,
		closeBtn : 1,
		title : "用户信息导入",
		maxmin : true,
		area : [ '450px', '250px' ],
		content : $('#saveDialog'), // 这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			saveFunction();
		}
	});
}

/** 
 * savedialog确定按钮
 */
function saveFunction() {
	var file = $("#saveForm #file").filebox('getValue');
	if (file == null || file == '' || file == 'undifined') {
		layer.alert("请选择导入文件！", {
			icon : 0
		});
		return;
	}
	
	$("#saveForm").form('submit',{
		url:ctx + '/sys/sysUser/import',
		success:function(data){
			fbidp.utils.alertInfo(data, function() {
  				layer.closeAll();
  				query();
			});   
		}
	});
}