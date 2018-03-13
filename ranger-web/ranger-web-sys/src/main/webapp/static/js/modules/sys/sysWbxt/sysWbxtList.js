var form;
layui.use([ 'form', 'jquery' ], function() {
	form = layui.form;
	
	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysWbxt/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    singleSelect:false
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
	$('#dialog #xtbm').removeAttr("readOnly");
	
	//随机生成8位密码字符串
	var pwd="";
	var pwdArr=['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i',
		'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D',
		'E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
		for(var i=0;i<8;i++){
			var position =Math.round(Math.random()*(pwdArr.length-1));
			pwd += pwdArr[position];
		}
		
	$("#dialog #dialogForm #xtmm").val(pwd);
		
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "注册外部系统",
		maxmin : true,
		area : [ '630px', '320px' ],
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
	$('#dialog #xtbm').attr('readOnly',true);
	$('#dialog #xtbm').css('background-color','#E3E3E3');
	$('#dialog #orgId').combo("setValue",rows[0].orgId).combo("setText",rows[0].orgName);

	form.render();
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改",
		maxmin : true,
		area : [ '630px', '300px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		},
		end : function() {
			$('#dialog #orgId').combo('clear');
			$('#dialog #xtbm').removeAttr('readOnly');
			$('#dialog #xtbm').css('background-color','');
		}
	});
}

/**
 * 删除按钮触发的事件
 */
function delete_() {
	var rows = $("#tt").datagrid('getSelections');
	if (rows.length == 0) {
		fbidp.utils.alertWarn("请选取至少一条数据进行操作！");
		return;
	}
	
	var ids = [];
	for(var i=0;i<rows.length;i++) {
		ids.push(rows[i].id);
	}
	
	fbidp.utils.confirm("确定要删除选中的数据吗?", function(r) {
		if (r) {
			$.post(ctx+"/sys/sysWbxt/delete", {ids : ids.join('&') }, function(result) {
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
		varUrl = ctx+'/sys/sysWbxt/insert';
	}else{
		varUrl = ctx+'/sys/sysWbxt/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	
	//TODO: 请根据实际情况编写
	if($.trim(formData.xtbm)==""){
		fbidp.utils.alertWarn("系统编码不能为空，请填写！");
		return false;
	}
	if($.trim(formData.xtmc)==""){
		fbidp.utils.alertWarn("系统名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.orgId)==""){
		fbidp.utils.alertWarn("所属机构不能为空，请选择！");
		return false;
	}
	if($.trim(formData.xtwz)==""){
		fbidp.utils.alertWarn("系统网址不能为空，请填写！");
		return false;
	}
	if($.trim(formData.xtfzr)==""){
		fbidp.utils.alertWarn("负责人不能为空，请选择！");
		return false;
	}
	if($.trim(formData.fxrlxdh)==""){
		fbidp.utils.alertWarn("联系方式不能为空，请填写！");
		return false;
	}
	
	//手机号校验
	var reg = /^\d{11}$/; //定义正则表达式
	if(!reg.test(formData.fxrlxdh))  { 
	    fbidp.utils.alertWarn("请输入有效的手机号码！");
	    return false; 
	} 
	
	$.post(varUrl,formData,function(data){
		if (data.success) {
			fbidp.utils.alertInfo("操作成功！", function() {
				fbidp.utils.close();
				query();
			});
		} else if (!data.success) {
			fbidp.utils.alertError(data.msg);
		} else {
			fbidp.utils.alertError("操作失败,请联系系统管理员！");
		}
	},"json");

}
