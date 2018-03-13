layui.use(['form','jquery','layedit'], function(){
	var form = layui.form;
	var $= layui.jquery;

	$('#tt').datagrid({ 
	    url:ctx+'/sys/sysYwgzsm/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true
	});
});

var index;

function query(){
	$('#tt').datagrid("reload",$("#queryForm").serializeObject());
}

/**
 * 新增
 */
var ue;
function add(){
	/*$("#ywgzSmFwb").html('');
	$("#ywgzSm").html('');*/
	$("#dialog #dialogForm")[0].reset();
	$("#dialog #dialogForm #id").val(null);
	$("#dialogForm #ywgzBm").removeAttr("readOnly");
	
	var ueditor = $("<div id='uEditor' style='height:90px;width:500px;'></div>")
	ueditor.appendTo($("#uEditorContent"));
	$(ueditor).css('margin-left','38px');
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增",
		maxmin : true,
		area : [ '570px', '400px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		},
		success: function(){
			createEditor('uEditor');	
		},
		end:function(){
			//层销毁回调
			ue.destroy();
			ue = null;
			$("#uEditor").remove();
		}
	});
}

//实例化编辑器
function createEditor(id) {
    ue = UE.getEditor(id, {
    	toolbars:[
    		['fullscreen','source','undo','redo','underline','fontborder','strikethrough','forecolor','superscript','subscript','removeformat','formatmatch','autotypeset','pasteplain']
    	],
    	//取消自动保存到localstorage、这里修改了源码
    	enableAutoSave : false
    })
}

/**
 * 修改
 */
function edit(){
	var ueditor = $("<div id='uEditor' style='height:90px;width:480px;'></div>")
	ueditor.appendTo($("#uEditorContent"));
	$(ueditor).css('margin-left','38px');
	
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

	$("#dialogForm #ywgzBm").attr("readOnly","readonly");
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改",
		maxmin : true,
		area : [ '570px', '400px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		},
		success: function(){
			createEditor('uEditor');
		},
		end:function(){
			//层销毁回调
			ue.destroy();
			ue = null;
			$("#uEditor").remove();
		}
	});
	
	//等待编辑器加载完成
    ue.ready(function(){
    	//赋值
	   	UE.getEditor('uEditor').execCommand('insertHtml', rows[0].ywgzSmFwb);
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
			$.post(ctx+"/sys/sysYwgzsm/delete", {ids : ids.join('&') }, function(result) {
				if (result.success) {
					fbidp.utils.alertInfo("操作成功！", function(){
						layer.closeAll();
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
	var isFlag = '';
	if($("#dialogForm #id").val() == ''){
		varUrl = ctx+'/sys/sysYwgzsm/insert';
		isFlag = 'add';
	}else{
		varUrl = ctx+'/sys/sysYwgzsm/update';
		isFlag = 'update';
	}
	
	//表单验证
	if($.trim($("#dialogForm #ywgzBm").val())==""){
		fbidp.utils.alertWarn("规则编码不能为空，请填写！");
		return false;
	}
	if($("#dialogForm #ywgzBm").val().length>32){
		fbidp.utils.alertWarn("规则编码超出长度，请重新填写！");
		return false;
	}
	//获得编辑器带格式的文本
	var content = UE.getEditor('uEditor').getContent();
	//获取编辑器纯文本
	var text = UE.getEditor('uEditor').getContentTxt();
	
	if($.trim(content)==""){
		fbidp.utils.alertWarn("规则说明不能为空，请填写！");
		return false;
	}

	var formData = $("#dialogForm").serializeObject();
	var formData = $.extend(formData,{'ywgzSm':text, 'ywgzSmFwb':content});
	$.post(ctx+'/sys/sysYwgzsm/checkYwgzBm',formData,function(data){
		if(isFlag=='add'&& !data.success){
			fbidp.utils.alertWarn("已存在该规则编码，请从新填写！");
			return false;
		}
		$.post(varUrl,formData,function(data){  //表单提交
			if(data.success){
				fbidp.utils.alertInfo("操作成功！",function(){
					layer.closeAll();
					query();
				});
			}else{
				fbidp.utils.alertError("操作失败,请联系系统管理员！");
			}
		},"json");
	},"json");

}


