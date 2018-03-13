$(document).ready(function () {
	$('#tt').datagrid({ 
	    url:ctx+'/sys/scheduler/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
});

function shili(){
	layer.tips(
		'例如：0 15 10 ? * 6L '+
		'表示每月的最后一个星期五上午10:15触发<br>'+
		'秒: ", - * /"或0-59的整数 <br>'+
		'分: ", - * /"或0-59的整数<br> '+
		'时: ", - * /"或0-23的整数<br> '+
		'日: ", - * / ? L W C"或0-31的整数<br>'+ 
		'月: ", - * /"或1-12的整数或JAN-DEC<br> '+
		'星期: ", - * / ? L C #"或1-7的整数或SUN-SAT。1表示星期天 <br>'+
		'年（可选）: ", - * /"或1970-2099年<br>'
		, '#ddd', {
	  tips: [2, '#3595CC'],
	  time: 6000
	});
}
function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}

/**
 * 新增
 */
function add(){
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	$('#dialog #dialogForm #rwmc').css('background-color','#ffffff');
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增",
		maxmin : true,
		area : [ '450px', '310px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction('new');
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
	$('#dialog #dialogForm #rwmc').attr('readonly','readonly');
	$('#dialog #dialogForm #rwmc').css('background-color','#E3E3E3');

	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改",
		maxmin : true,
		area : [ '450px', '310px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction('update');
		},
		end : function() {
			$('#dialog #dialogForm #rwmc').removeAttr('readonly');
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
	
	var jobNames = [];
	for(var i=0;i<rows.length;i++) {
		jobNames.push(rows[i].rwmc);
	}
	
	fbidp.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx+"/sys/scheduler/deleteJobs", {jobNames : jobNames.join('&') }, function(result) {
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
 * 恢复按钮触发的事件
 */
function update2resumeJob() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if(rows.length >1){
		fbidp.utils.alertWarn("每次只可对一条数据进行操作！");
		return;
	}
	$.post(ctx+"/sys/scheduler/update2resumeJob", {jobName : rows[0].rwmc }, function(result) {
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

/**
 * 暂停按钮触发的事件
 */
function update2pauseJob() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if(rows.length >1){
		fbidp.utils.alertWarn("每次只可对一条数据进行操作！");
		return;
	}
	$.post(ctx+"/sys/scheduler/update2pauseJob", {jobName : rows[0].rwmc }, function(result) {
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

/**
 * 立即执行一次按钮触发的事件
 */
function update2triggerJob() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取一条数据进行操作！");
		return;
	}else if(rows.length >1){
		fbidp.utils.alertWarn("每次只可对一条数据进行操作！");
		return;
	}
	$.post(ctx+"/sys/scheduler/update2triggerJob", {jobName : rows[0].rwmc }, function(result) {
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

/**
 * dialog确定按钮
 */
function okFunction(bz){
	var varUrl = '';
	if(bz == 'new'){
		varUrl = ctx+'/sys/scheduler/addJob';
	}else if(bz =='update'){
		varUrl = ctx+'/sys/scheduler/updateJob';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	if($.trim(formData.rwmc)==""){
		fbidp.utils.alertWarn("任务名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.zylm)==""){
		fbidp.utils.alertWarn("作业类名不能为空，请填写！");
		return false;
	}
	if($.trim(formData.sjbds)==""){
		fbidp.utils.alertWarn("时间表达式不能为空，请填写！");
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
 * 格式化时间戳
 *
 * @param value
 * @returns
 */
function formatDate(value){
	if(value != null && value != ''){
		var datetime = new Date(value);
		var year = datetime.getFullYear(),
	    month = (datetime.getMonth() + 1 < 10) ? '0' + (datetime.getMonth() + 1):datetime.getMonth() + 1,
	    day = datetime.getDate() < 10 ? '0' +  datetime.getDate() : datetime.getDate(),
	    hour = datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours(),
	    min = datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes(),
	    sec = datetime.getSeconds() < 10 ? '0' + datetime.getSeconds() : datetime.getSeconds();
	    return year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + sec;

	} else {
		return '';
	}
}
