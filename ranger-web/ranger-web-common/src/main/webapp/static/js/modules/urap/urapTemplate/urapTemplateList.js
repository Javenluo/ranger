$(document).ready(function () {
	$('#tt').datagrid({ 
	    url:ctx+'/urap/urapTemplate/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
	//参数表
	$('#parameterTable').datagrid({ 
	    /*url:ctx+'/urap/urapTemplate/queryParameterList',*/
		url:ctx+'/urap/urapParameter/query',
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
	
});

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
/**
 * 新增
 */
function add(){
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增参数模板表",
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
		title : "修改参数模板表",
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
			$.post(ctx+"/urap/urapTemplate/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					fbidp.utils.alertInfo("操作成功！", function(){
						fbidp.utils.close();
						query();
					});
				} else {
					fbidp.utils.alertError("操作失败，请联系系统管理员");
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
		varUrl = ctx+'/urap/urapTemplate/insert';
	}else{
		varUrl = ctx+'/urap/urapTemplate/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//TODO: 请根据实际情况编写
	if($.trim(formData.name)==""){
		fbidp.utils.alertWarn("参数模板名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.dirFlag)==""){
		fbidp.utils.alertWarn("是否目录：0否，1是不能为空，请填写！");
		return false;
	}
	
	$.post(varUrl,formData,function(data){
		if(data.success){
			fbidp.utils.alertInfo("操作成功！",function(){
				fbidp.utils.close();
				query();
			});
		}else{
			fbidp.utils.alertError("操作失败，请联系系统管理员！");
		}
	},"json");

}
