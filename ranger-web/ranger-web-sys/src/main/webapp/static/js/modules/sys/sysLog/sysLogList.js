var form;
layui.use(['form','jquery'], function(){
	form = layui.form;
	var $= layui.jquery;

	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysLog/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    rownumbers:true,
		pagination:true
	});
});

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
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
		title : "新增",
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
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if (rows.length > 1) {
		fbidp.utils.alertWarn("只能选择一条数据进行操作！");
		return;
	}
	
	$("#dialog #dialogForm")[0].reset();
	$('#dialog #dialogForm').form('load',rows[0]);

	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改",
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
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	fbidp.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx+"/sys/sysLog/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					fbidp.utils.alertInfo("操作成功！", function(){
						fbidp.utils.close();
						query();
					});
				} else {
					fbidp.utils.alertError("操作失败,请联系系统管理员");
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
		varUrl = ctx+'/sys/sysLog/insert';
	}else{
		varUrl = ctx+'/sys/sysLog/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//TODO: 请根据实际情况编写
	if($.trim(formData.type)==""){
		fbidp.utils.alertWarn("类型不能为空，请填写！");
		return false;
	}
	
	$.post(varUrl,formData,function(data){
		if(data.success){
			fbidp.utils.alertInfo("操作成功！",function(){
				fbidp.utils.close();
				query();
			});
		}else{
			fbidp.utils.alertError("操作失败,请联系系统管理员！");
		}
	},"json");

}

/**
 * 格式化日志类型
 */
function formatType(value){
	if (value == '1'){
		return '正常';
	}else if(value == '2'){
		return '异常';
	}else{
		return "";
	}
}
