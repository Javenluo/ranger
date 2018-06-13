$(document).ready(function () {
	$('#tt').datagrid({ 
	    url:ctx+'/urap/urapParameter/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
	//初始化左侧树
	initTreeData();
	//表单各种基于事件的操作
	layui.use('form', function(){
		  var form = layui.form;
		  form.on('select(valueTypeOnchange)', function(data){
				/*$("#dialogForm #xlMc").val(this.innerText);*/
			  valueTypeOnchange(data.value);
		  }); 
		  form.on('select(controlTypeOnchange)', function(data){
			  controlTypeOnchange(data.value);
		  });
		  
	});
	
});
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
			url : ctx+"/urap/urapParameter/getMenus",
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
			$.post(ctx + "/urap/urapParameter/delete", {
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
	var varUrl = ctx + '/urap/urapParameter/saveMenuItem';
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
 * 表格加载
 * @returns
 */
function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}
//表单列数据格式化
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
//选择框下拉事件
function controlTypeOnchange(value){
	if(value=='5'){
		$('#valueTypeDiv').show();
	}else{
		$('#valueTypeDiv').hide();
	}
}

function valueTypeOnchange(value){
	if(value=='1'){
		$('#parameterValue').val('值1,名称1###值2,名称2');
	}else if(value=='2'){
		$('#parameterValue').val('select 1 as 值, 2 as 名称  from table');
	}else{
		$('#parameterValue').val('');
	}
	
}

/**
 * 新增
 */
function add(){
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增参数表",
		maxmin : true,
		area : [ '650px', '350px' ],
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
		title : "修改参数表",
		maxmin : true,
		area : [ '650px', '350px' ],
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
			$.post(ctx+"/urap/urapParameter/delete", {ids : ids.join('&') }, function(result) {
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
		varUrl = ctx+'/urap/urapParameter/insert';
	}else{
		varUrl = ctx+'/urap/urapParameter/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//TODO: 请根据实际情况编写
	if($.trim(formData.name)==""){
		ranger.utils.alertWarn("参数名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.cname)==""){
		ranger.utils.alertWarn("参数中文名称不能为空，请填写！");
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
