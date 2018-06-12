var form;
layui.use(['form','jquery'], function(){
	form = layui.form;
	var $= layui.jquery;
	
	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysNotice/query', 
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
		title : "新增系统消息表",
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
		title : "修改系统消息表",
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
			$.post(ctx+"/sys/sysNotice/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					ranger.utils.alertInfo("操作成功！", function(){
						layer.closeAll();
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
		varUrl = ctx+'/sys/sysNotice/insert';
	}else{
		varUrl = ctx+'/sys/sysNotice/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//TODO: 请根据实际情况编写
	
	$.post(varUrl,formData,function(data){
		if(data.success){
			ranger.utils.alertInfo("操作成功！",function(){
				layer.closeAll();
				query();
			});
		}else{
			ranger.utils.alertError("操作失败，请联系系统管理员！");
		}
	},"json");

}

function formatCreateDate(value,row,index){
	var date = new Date(value);
	return date.toLocaleDateString();
	 };

//为表格每行添加操作栏
function formatNoticeTitle(value,row,index){
	return "<a href='#' onclick='check(\"" + index + "\")'>"+value+"</a>&nbsp;&nbsp;";
}



function formatNoticeType(value, row, index){
	if (row.noticeType == '0') {
		return "系统公告";
	} else if (row.noticeType == '1') {
		return "系统通知";
	}else if (row.noticeType == '2') {
		return "消息";
	}
}

function check(index){
	var rows = $("#tt").datagrid('getRows');
	var row = rows[index];
	var url=ctx+'/sys/sysNotice/queryByID?id='+row.id;
	window.parent.openMenu(row.noticeTitle,url,"0");
	query();
	window.parent.xtxx();
}

function formatContent(value,row,index){
	if(value != null && value != "" && value != undefined)
	return "<span title=\""+value+"\">"+value+"</span> ";
} 
