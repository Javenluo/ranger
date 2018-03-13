var index;
var openId; // 弹出层ID
layui.use([ 'form', 'jquery', 'element' ], function() {
	var form = layui.form;
	var $ = layui.jquery, element = layui.element;

	element.on('tab(user)', function(data) {
		index = data.index;
		if (index == 0) {
			initYxUser();
		} else {
			initWxUser();
		}
		userQuery();
		editBtn();
	});

	$('#tt').datagrid({
		url : ctx + '/sys/sysRole/query',
		queryParams : $("#queryForm").serializeObject(),
		height : 270,
		rownumbers : true,
		pagination : true,
		onCheck : function(index, row) {
			// 更改右侧面板标题
			updateTitle_menuTree("角色【<span style='color: red'>" + row.name + "</span>】拥有的功能权限");
			$("#roleId").val(row.id);

			// 加载右侧功能树
			var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex");
			zTreeObj.setting.async.otherParam = {roleId : row.id};
			zTreeObj.reAsyncChildNodes(null, "refresh");
			zTreeObj.expandAll(true);
		}
	});
});

function query() {
	$('#tt').datagrid("reload", $("#queryForm").serializeObject());
}

/**
 * 新增角色
 */
function add() {
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	setDefaultValue_orgId();

	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增角色",
		maxmin : true,
		area : [ '620px', '315px' ],
		content : $('#dialog'), // 这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
}

/**
 * 修改按钮
 */
function edit() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	} else if (rows.length > 1) {
		fbidp.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}

	$("#dialog #dialogForm")[0].reset();
	$('#dialog #dialogForm').form('load', rows[0]);
	$('#dialogForm #orgId1').combo("clear");
	$('#dialogForm #orgId1').combo("setValue", rows[0].orgId).combo("setText",rows[0].fullOrgNames);

	layui.form.render();
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改角色",
		maxmin : true,
		area : [ '650px', '310px' ],
		content : $('#dialog'), // 这里content是一个DOM
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
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].childNum > 0){
			fbidp.utils.alertWarn("该角色下已关联用户，不允许删除！");
			return;
		}
		ids.push(rows[i].id);
	}
	

	fbidp.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx + "/sys/sysRole/delete", {ids : ids.join('&')}, function(result) {
				if (result.success) {
					fbidp.utils.alertInfo("操作成功！", function() {
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
function okFunction() {
	var varUrl = '';
	if ($("#dialogForm #id").val() == '') {
		varUrl = ctx + '/sys/sysRole/insert';
	} else {
		varUrl = ctx + '/sys/sysRole/update';
	}

	var formData = $("#dialogForm").serializeObject();
	formData.orgId = formData.orgId1;
	if (formData.enableFlag == null) {
		formData.enableFlag = "0";
	}
	if ($.trim(formData.code) == "") {
		fbidp.utils.alertWarn("角色编码不能为空，请填写！");
		return false;
	}
	if ($.trim(formData.name) == "") {
		fbidp.utils.alertWarn("角色名称不能为空，请填写！");
		return false;
	}
	if ($.trim(formData.orgId) == "") {
		fbidp.utils.alertWarn("所属机构不能为空，请填写！");
		return false;
	}
	if ($.trim(formData.showOrder) == "") {
		fbidp.utils.alertWarn("排列顺序不能为空，请填写！");
		return false;
	}

	// TODO:需要验证编码是否重复

	$.post(varUrl, formData, function(data) {
		if (data.success) {
			fbidp.utils.alertInfo("操作成功！", function() {
				fbidp.utils.close();
				query();
			});
		} else {
			fbidp.utils.alertError("操作失败,请联系系统管理员！");
		}
	}, "json");

}

function treeNodeClick(e, treeId, treeNode) {

}

function formatenableFlag(value) {
	if (value == "1") {
		return "<span class='layui-icon'>&#xe618;</span>";
	} else {
		return "<span class='layui-icon'>&#x1006;</span>";
	}
}

function saveRoleMenu() {
	var roleId = $("#roleId").val();

	var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex");
	var nodes = zTreeObj.getCheckedNodes(true);

	var msg = "确定要执行此操作吗?";
	if (nodes.length == 0) {
		msg = "您未选择要授权的功能，" + msg;
	}

	var menuIds = new Array();
	$.each(nodes, function(index, node) {
		menuIds.push(node.id);
	});

	fbidp.utils.confirm(msg, function(r) {
		if (r) {
			var url = ctx + '/sys/sysRole/saveRoleMenu';
			$.post(url, {roleId : roleId, menuIds : menuIds.join('&')}, function(data) {
				if (data.success) {
					fbidp.utils.alertInfo("操作成功！", function() {
						fbidp.utils.close();
					});
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});
}

function resetRoleMenu() {
	var roleId = $("#roleId").val();

	// 加载右侧功能树
	var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex");
	zTreeObj.checkAllNodes(false); // 取消勾选全部节点
}

// 指定成员
function zdcy() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length != 1) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}
	$("#zdcyForm #roleId").val(rows[0].id);

	// 动态获取弹出层ID
	openId = layer.open({
		type : 1,
		closeBtn : 1,
		title : "请选择角色【" + rows[0].name + "】成员",
		maxmin : true,
		area : [ '700px', '430px' ],
		content : $('#zdcyDialog'), // 这里content是一个DOM
		btn : [ '添加用户', '移除用户', '取消' ],
		btn1 : function(i, layero) {
			addRoleUser();
		},
		btn2 : function(i, layero) {
			deleteRoleUser();
			return false;
		},
		end : function() {
			resetZdcy();
		}
	});
	editBtn();
	initYxUser();
}

// 动态操作layer弹出层按钮
function editBtn() {
	if (index == 1) {
		$("#layui-layer" + openId + " .layui-layer-btn0").show();
		$("#layui-layer" + openId + " .layui-layer-btn1").hide();
	} else {
		$("#layui-layer" + openId + " .layui-layer-btn0").hide();
		$("#layui-layer" + openId + " .layui-layer-btn1").show();
	}
	$("#layui-layer" + openId + " .layui-layer-btn1").css('background-color',
			'#FF5722');
	$("#layui-layer" + openId + " .layui-layer-btn1").css('color', '#fff');
}

// 初始化角色已选用户表
function initYxUser() {
	$('#yxUserTable').datagrid({
		url : ctx + '/sys/sysRole/roleYxUser',
		queryParams : $("#zdcyForm").serializeObject(),
		height : 220,
		singleSelect : false
	});
}
// 初始化角色未选用户表
function initWxUser() {
	$('#wxUserTable').datagrid({
		url : ctx + '/sys/sysRole/roleWxUser',
		queryParams : $("#zdcyForm").serializeObject(),
		height : 220,
		singleSelect : false
	});
}

// 添加角色用户
function addRoleUser() {
	var rows = $("#wxUserTable").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取至少一条数据进行操作！");
		return;
	}
	var ids = [];
	var roleId = $("#zdcyForm #roleId").val();
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}

	$.post(ctx + "/sys/sysRole/addRoleUser", {roleId : roleId, ids : ids.join('&')}, function(result) {
		if (result.success) {
			fbidp.utils.alertInfo("操作成功！", function(index) {
				fbidp.utils.close(index);
				userQuery();
			});
		} else {
			fbidp.utils.alertError("操作失败,请联系系统管理员！");
		}
	});
}

// 删除角色用户
function deleteRoleUser() {
	var rows = $("#yxUserTable").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取至少一条数据进行操作！");
		return;
	}
	var ids = [];
	var roleId = $("#zdcyForm #roleId").val();
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}

	fbidp.utils.confirm("确定要移除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx + "/sys/sysRole/deleteRoleUser", {roleId : roleId, ids : ids.join('&')}, function(result) {
				if (result.success) {
					fbidp.utils.alertInfo("操作成功！", function(index) {
						fbidp.utils.close(index);
						userQuery();
					});
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员！");
				}
			});
		}
	});
}

// 查询角色用户
function userQuery() {
	if (index == '1') { // 未选择用户查询
		$('#wxUserTable').datagrid("reload", $("#zdcyForm").serializeObject());
	} else { // 已选择用户查询
		$('#yxUserTable').datagrid("reload", $("#zdcyForm").serializeObject());
	}
}

// 重置
function resetZdcy() {
	index = 0;
	$('#zdcyForm').form('reset');
	$("#zdcyForm #roleId").val("");
	var n = $("#orgId_tree_sp").tree("find", orgId);
	$("#orgId").combo("setValue", n.id).combo("setText", n.text);
	editBtn();
	layui.element.tabChange('user', 'yx'); 
	layui.form.render();
}

function resetRole(){
	$('#zdcyForm').form('reset');
	query();
	resetTree();
}

function resetTree(){
	//加载右侧功能树
	var zTreeObj = $.fn.zTree.getZTreeObj("menuTree_treex");
	zTreeObj.checkAllNodes(false); //取消勾选全部节点
}
