var form;
layui.use(['form','jquery'], function(){
	form = layui.form;
	var $= layui.jquery;

	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysDict/query', 
	    singleSelect:false
	});
});

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}

/**
 * 新增字典
 */
function add(){
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增字典",
		maxmin : true,
		area : [ '650px', '315px' ],
		content : $('#dialog'), //这里content是一个DOM
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
		title : "修改字典",
		maxmin : true,
		area : [ '650px', '315px' ],
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
		ranger.utils.alertWarn("请选取至少一条数据进行操作！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	ranger.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx+"/sys/sysDict/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					ranger.utils.alertInfo("操作成功！", function(){
						ranger.utils.close();
						query();
					});
				} else {
					ranger.utils.alertError("操作失败,请联系系统管理员");
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
		varUrl = ctx+'/sys/sysDict/insert';
	}else{
		varUrl = ctx+'/sys/sysDict/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	if($.trim(formData.type)==""){
		ranger.utils.alertWarn("类型不能为空，请填写！");
		return false;
	}
	if($.trim(formData.value)==""){
		ranger.utils.alertWarn("键值不能为空，请填写！");
		return false;
	}
	if($.trim(formData.label)==""){
		ranger.utils.alertWarn("标签不能为空，请填写！");
		return false;
	}
	if($.trim(formData.sort)==""){
		ranger.utils.alertWarn("排序不能为空，请填写！");
		return false;
	}
	
	$.post(varUrl,formData,function(data){
		if(data.success){
			ranger.utils.alertInfo("操作成功！",function(){
				ranger.utils.close();
				query();
			});
		}else{
			ranger.utils.alertError(data.msg);
		}
	},"json");

}

