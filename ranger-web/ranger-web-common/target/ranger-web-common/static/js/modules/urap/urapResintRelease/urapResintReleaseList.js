$(document).ready(function () {
	$('#tt').datagrid({ 
	    url:ctx+'/urap/urapResintRelease/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
	
	//初始化左侧树
	initTreeData();
});

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}
/**
 * 左侧树相关方法
 * @returns
 */
var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : function(event, treeId,
					treeNode) {
					clickItem(treeNode.id);
			}
		}
	};
	

	//加载树的方法
	function initTreeData(){
		$.ajax({
			url : ctx+"/urap/urapResintRelease/getMenus",
			type : 'GET',
			async : false,
			dataType : "json",
			success : function(data) {
				var treeObj = $.fn.zTree.init($("#_treex"),
						setting,
						data);
				var nodes = treeObj.getNodes();
				if (nodes.length > 0) {
					treeObj.selectNode(nodes[0]);
				}
			}
		});
	}

//点击树节点
function clickItem(itemId) {
	$("#queryForm #parentId").val(itemId);
	$("#dialogForm #parentId").val(itemId);
	query();
}
//添加目录
function addItem(){
	$("#lbDialog #lbDialogForm")[0].reset();
	var zTree = $.fn.zTree.getZTreeObj("_treex");
	var nodes = zTree.getSelectedNodes();
	$("#lbDialogForm #parentName").val(nodes[0].name);
	$("#lbDialogForm #parentId").val(nodes[0].id);
	//打开对话框		
	layer.open({
			type : 1,
			closeBtn : 1,
			title : "新增类别",
			maxmin : true,
			area : [ '650px', '350px' ],
			content : $('#lbDialog'), //这里content是一个DOM
			btn : [ '确定', '取消' ],
			btn1 : function() {
				saveMenuItem();
			}
		});

}
//修改类别
function editItem(){
	$("#lbDialog #lbDialogForm")[0].reset();
	var zTree = $.fn.zTree.getZTreeObj("_treex");
	var nodes = zTree.getSelectedNodes();
	$("#lbDialogForm #name").val(nodes[0].name);
	$("#lbDialogForm #id").val(nodes[0].id);
	$("#lbDialogForm #sortNo").val(nodes[0].sortNo);
	//打开对话框		
	layer.open({
			type : 1,
			closeBtn : 1,
			title : "修改类别",
			maxmin : true,
			area : [ '650px', '350px' ],
			content : $('#lbDialog'), //这里content是一个DOM
			btn : [ '确定', '取消' ],
			btn1 : function() {
				saveMenuItem();
			}
		});

}

//删除目录
function deleteItem() {
	var zTree = $.fn.zTree.getZTreeObj("_treex");
	var nodes = zTree.getSelectedNodes();
	var id = nodes[0].id;
	ranger.utils.confirm("确定要删除选中的目录吗?", function(r) {
		if (r) {
			$.post(ctx + "/urap/urapResintRelease/delete", {
				ids : id
			}, function(result) {
				if (result.success) {
					ranger.utils.alertInfo("操作成功！", function() {
						ranger.utils.close();
						initTreeData();
					});
				} else {
					ranger.utils.alertError("操作失败，请联系系统管理员");
				}
			});
		}
	});
}
/**
 * lbDialog确定按钮
 */
function saveMenuItem() {
	var varUrl = ctx + '/urap/urapResintRelease/saveMenuItem';
	var formData = $("#lbDialogForm").serializeObject();
	//验证表单
	if ($.trim(formData.name) == "") {
		ranger.utils.alertWarn("目录名称不能为空，请填写！");
		return false;
	}
	if ($.trim(formData.sortNo) == "") {
		ranger.utils.alertWarn("同级排序号不能为空，请填写！");
		return false;
	}

	$.post(varUrl, formData, function(data) {
		if (data.success) {
			ranger.utils.alertInfo("操作成功！", function() {
				ranger.utils.close();
				$("#lbDialogForm #id").val(data.id);
				initTreeData();
			});
		} else {
			ranger.utils.alertError("操作失败，请联系系统管理员！");
		}
	}, "json");

}

/**
 * 新增报表
 */
function add(){
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增报表",
		maxmin : true,
		area : [ '650px', '310px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
}

/**
 * 修改
 */
function edit(){
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		ranger.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if (rows.length > 1) {
		ranger.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}
	
	$("#dialog #dialogForm")[0].reset();
	$('#dialog #dialogForm').form('load',rows[0]);

	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改报表",
		maxmin : true,
		area : [ '650px', '310px' ],
		content : $('#dialog'), //这里content是一个DOM
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
		ranger.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	ranger.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx+"/urap/urapResintRelease/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					ranger.utils.alertInfo("操作成功！", function(){
						ranger.utils.close();
						query();
					});
				} else {
					ranger.utils.alertError("操作失败，请联系系统管理员");
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
	if($("#dialogForm #id").val() == ''){
		varUrl = ctx+'/urap/urapResintRelease/insert';
	}else{
		varUrl = ctx+'/urap/urapResintRelease/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//表单验证
	if($.trim(formData.name)==""){
		ranger.utils.alertWarn("报表名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.url)==""){
		ranger.utils.alertWarn("报表索引不能为空，请填写！");
		return false;
	}
	if($.trim(formData.paramIds)==""){
		ranger.utils.alertWarn("参数不能为空，请填写！");
		return false;
	}
	
	$.post(varUrl,formData,function(data){
		if(data.success){
			ranger.utils.alertInfo("操作成功！",function(){
				ranger.utils.close();
				query();
			});
		}else{
			ranger.utils.alertError("操作失败，请联系系统管理员！");
		}
	},"json");
}
//参数对话框的表格数据格式化
function formatSfbz(value,row, index){
	if (value == "1") {
		return "<span style=''>是</span>";
	} else if (value == "0") {
		return "<span style=''>否</span>";
	}
}
function formatQzfs(value,row, index){
	if (value == "1") {
		return "<span style=''>数组</span>";
	} else if (value == "2") {
		return "<span style=''>SQL语句</span>";
	} else if (value == "3") {
		return "<span style=''>内置控件</span>";
	}
}

function formatKjlx(value,row, index){
	if (value == "1") {
		return "<span style=''>单选按钮</span>";
	} else if (value == "2") {
		return "<span style=''>树控件</span>";
	} else if (value == "3") {
		return "<span style=''>日期控件</span>";
	} else if (value == "4") {
		return "<span style=''>文本输入框</span>";
	} else if (value == "5") {
		return "<span style=''>单选下拉控件</span>";
	} else if (value == "6") {
		return "<span style=''>隐藏域</span>";
	} else if (value == "7") {
		return "<span style=''>内置控件</span>";
	}
}

//参数选择对话框
function openParameterDialog(){
	var url=ctx+'/urap/urapResintRelease/chooseParameterList';
	$("#parameterIframe").attr("src",url);
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "选择参数",
		maxmin : true,
		area : [ '700px', '380px' ],
		content : $('#parameterDialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function(index) {
			//从子级的页面取所选择的参数
			var obj=document.getElementById("parameterIframe").contentWindow;  
			var ifmObj=obj.document.getElementById("idString");  
			var ifmObj2=obj.document.getElementById("nameString"); 
			var idString =ifmObj.value;
			var nameString = ifmObj2.value;
			idString = idString.substr(0,idString.length-1);
			nameString = nameString.substr(0,nameString.length-1);
			$("#dialogForm #paramIds").val(idString);
			$("#dialogForm #tmpltName").val(nameString);
			ranger.utils.close(index);
		}
	});
}
