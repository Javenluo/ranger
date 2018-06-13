$(document).ready(function () {
	$('#tt').datagrid({ 
	    url:ctx+'/urap/urapServer/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
});

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}
//格式化表单数据
function formatServerType(value,row, index){
	if (value == "1") {
		return "<span style=''>Cognos</span>";
	} else if (value == "2") {
		return "<span style=''>BI.Office</span>";
	} else if (value == "3") {
		return "<span style=''>BIEE</span>";
	} else if (value == "4") {
		return "<span style=''>BO</span>";
	} else if (value == "5") {
		return "<span style=''>FTP</span>";
	} else if (value == "6") {
		return "<span style=''>UPLOAD</span>";
	} else if (value == "0") {
		return "<span style=''>自定义</span>";
	}
}
function formatAuthType(value,row, index){
	if(value == "1"){
		return "<span style=''>配置用户</span>";
	}else if(value == "2"){
		return "<span style=''>当前用户</span>";
	}else if(value == "3"){
		return "<span style=''>匿名登录</span>";
	}
}
function formatSynFlag(value,row, index){
	if(value == "0"){
		return "<span style=''>否</span>";
	}else if(value == "1"){
		return "<span style=''>是</span>";
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
		title : "新增资源服务器表",
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
		title : "修改资源服务器表",
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
			$.post(ctx+"/urap/urapServer/delete", {ids : ids.join('&') }, function(result) {
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
function testConnection(){
	alert('待实现');
}
/**
 * dialog确定按钮
 */
function okFunction(){
	var varUrl = '';
	if($("#dialogForm #id").val() == ''){
		varUrl = ctx+'/urap/urapServer/insert';
	}else{
		varUrl = ctx+'/urap/urapServer/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//TODO: 请根据实际情况编写
	if($.trim(formData.name)==""){
		ranger.utils.alertWarn("服务器名不能为空，请填写！");
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
