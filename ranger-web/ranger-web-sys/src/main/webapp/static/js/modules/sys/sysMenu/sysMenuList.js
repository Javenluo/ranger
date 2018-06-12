layui.use(['form','jquery'], function(){
	var form = layui.form;
	var $= layui.jquery;

	//菜单树控件
	$('#tt').treegrid({ 
	    url:ctx+'/sys/sysMenu/getMenuListByParentId', 
	    idField:'id', 
	    treeField:'name',
	    animate:true,
	    rownumbers:true
	});
	
	
});

/**
 * 格式化visible字段
 */
function formatVisible(value){
    if (value=="1"){
        return "<span class='layui-icon'>&#xe618;</span>";
    } else if(value == '0'){
    	return "<span class='layui-icon'>&#x1006;</span>";
    }else{
    	return "";
    }
}
/**
 * 格式化图标字段
 */
function formatIocn(value){
	if (value!=null && value!=''){
		return "<span class='iconfont'>"+ value +"</span>";
	} else{
		return "";
	}
}

/**
 * 格式化menuType字段
 */
function formatMenuType(value){
    if (value=="1"){
        return "菜单";
    } else if(value=="2") {
    	return "按钮";
    } else {
    	return "";
    }
}

/**
 * 树表格每行添加操作栏
 */
function formatOperation(value,row,index){
	if(value=='0'){
		return "<shiro:hasAnyPermissions name='sys:sysLogList:view,sys:sysLogList:edit,'>"
				+"<button class='layui-btn layui-btn-normal layui-btn-mini' title='添加下级' onclick='add(\""+value+"\",\""+row.parentIds+"\")'>添加下级</button>"
				+"<button class='layui-btn layui-btn-disabled layui-btn-mini'>修改</button>"
				+"<button class='layui-btn layui-btn-disabled layui-btn-mini'>删除</button>"
				+"<button class='layui-btn layui-btn-disabled layui-btn-mini'>帮助说明编辑</button></shiro:hasAnyPermissions>";
	}else {
		if(row.menuType == "2"){
			return "<button class='layui-btn layui-btn-disabled layui-btn-mini' title='添加下级' >添加下级</button>"
				   +"<button class='layui-btn layui-btn-normal layui-btn-mini' onclick='edit(\""+value+"\",\""+row.parentIds+"\")'>修改</button>"
				   +"<button class='layui-btn layui-btn-danger layui-btn-mini' onclick='deleteMenu(\""+value+"\",\""+row.parentIds+"\")'>删除</button>"
				   +"<button class='layui-btn layui-btn-disabled layui-btn-mini' >帮助说明编辑</button>";
		}else if(row.bzsmFlag > '0'){
			return  "<button class='layui-btn layui-btn-normal layui-btn-mini' title='添加下级' onclick='add(\""+value+"\",\""+row.parentIds+"\")'>添加下级</button>"
				   +"<button class='layui-btn layui-btn-normal layui-btn-mini' onclick='edit(\""+value+"\",\""+row.parentIds+"\")'>修改</button>"
				   +"<button class='layui-btn layui-btn-danger layui-btn-mini' onclick='deleteMenu(\""+value+"\")'>删除</button>"
				   +"<button class='layui-btn layui-btn-disabled layui-btn-mini' >帮助说明编辑</button>";
		}else{
			return  "<button class='layui-btn layui-btn-normal layui-btn-mini' title='添加下级' onclick='add(\""+value+"\",\""+row.parentIds+"\")'>添加下级</button>"
			   +"<button class='layui-btn layui-btn-normal layui-btn-mini' onclick='edit(\""+value+"\",\""+row.parentIds+"\")'>修改</button>"
			   +"<button class='layui-btn layui-btn-danger layui-btn-mini' onclick='deleteMenu(\""+value+"\")'>删除</button>"
			   +"<button class='layui-btn layui-btn-mini' onclick='openUeditor(\""+value+"\")'>帮助说明编辑</button>";
	}
	}
}

/**
 * 添加下级菜单按钮
 */
function add(menuId,parentIds){
	$("#dialog #dialogForm").form('reset');
	if (parentIds == 'undefined'){
		parentIds = "";
	}
	if (menuId != '0'){
		parentIds = parentIds + "," + menuId;
	}
	$("#dialog #dialogForm #parentIds").val(parentIds);
	$("#dialog #dialogForm #parentId").val(menuId);
	$("#dialog #dialogForm #currentId").val(null);
	
	var selectM = $("#tt").treegrid('find',menuId);
	$("#dialog #dialogForm #higher").val(selectM.name);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增菜单",
		maxmin : true,
		area : [ '480px', '390px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
	
}

/**
 * dialog确定按钮
 */
function okFunction(){
	var varUrl = '';
	if($("#dialogForm #currentId").val() == '' ){
		varUrl = ctx+'/sys/sysMenu/insert';
	}else {
		varUrl = ctx+'/sys/sysMenu/update';
	}
	
	var formData = $("#dialogForm").serializeObject();
	if(formData.visible==null){
		formData.visible = "0";
	}
	if($.trim(formData.name)==""){
		ranger.utils.alertWarn("菜单名称不能为空，请填写！");
		return false;
	}

	$.post(varUrl,formData,function(data){
		if(data.success){
			ranger.utils.alertInfo("操作成功！",function(){
				ranger.utils.close();
				
				var parentId = $("#dialog #dialogForm #parentId").val();
				if(parentId =="0"){
					$("#tt").treegrid('reload',parentId);
				}else{
					var parent = $("#tt").treegrid('getParent',parentId);
					$("#tt").treegrid('reload',parent.id);
				}
			});
		}else{
			ranger.utils.alertError(data.msg);
		}
	},"json");
	
}

///===================================
/**
 * 修改按钮
 */
function edit(menuId,parentIds){
	$("#dialog #dialogForm").form('reset');
	var selectM = $("#tt").treegrid('find',menuId);
	var parent = $("#tt").treegrid('getParent',selectM.id);
	$("#dialog #dialogForm #higher").val(parent.name);
	
	$.extend(selectM, {parentId: parent.id, currentId:menuId});
	
	$('#dialog #dialogForm').form('load',selectM);
	if(parentIds == 'undefined'){
		parentIds = '';
	}
	$("#dialog #dialogForm #parentIds").val(parentIds);

	var selectMType = document.getElementsByName("menuType");
	if(selectM.menuType == "1"){         	
		selectMType[0].checked = true;
	}else{
		selectMType[1].checked = true;
	}
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改菜单",
		maxmin : true,
		area : [ '480px', '390px' ],
		content : $('#dialog'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
}

//========================================================
/**
 * 删除按钮
 */
function deleteMenu(menuId){
	var child = $('#tt').treegrid('getChildren',menuId);
	var row = $('#tt').treegrid('find',menuId);
	if(row.state != 'open' || child.length >0 ){
		ranger.utils.alertWarn("包含子菜单，不允许删除！");
	}else{
		ranger.utils.confirm("确定要删除选中的菜单吗？", function(r){	
			 if(r){
					$.post(ctx+"/sys/sysMenu/delete",{id:menuId},function(data){
						if(data.success){
							ranger.utils.alertInfo("操作成功！", function(){
								var parent = $("#tt").treegrid('getParent',menuId);
								if(parent.id == "0"){
									$("#tt").treegrid('reload',parent.id);
								}else{
									var grandP = $("#tt").treegrid('getParent',parent.id);
									$("#tt").treegrid('reload',grandP.id);
								}
								ranger.utils.close();
							});
							
						}else{
							ranger.utils.alertError("操作失败,请联系系统管理员！");
						}
					},"json");
			 }
		});
	}
}
//==============================

/**
 * 修改按钮
 */
function openIconWin(){
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "选择图标",
		maxmin : false,
		area : [ '650px', '360px' ],
		content : $('#iconWin'), //这里content是一个DOM
		btn : ['取消' ]
	});
}

function setIconTxt(value){
	$("#dialogForm #imageUrl").val(value);
	
	ranger.utils.close(layer.index);
}

//打开UEditor编辑器
var ue;
function openUeditor(menuId){
	var ueditor = $("<div id='uEditor' style='width:605px;display:none;margin-top:10px;margin-left:10px;margin-right:10px'></div>")
	ueditor.appendTo($("body"));
	
	layer.open({
		type : 1,
		title : "帮助说明编辑",
		maxmin : true,
		zIndex: -1,
		area : [ '650px', '420px' ],
		content : $('#uEditor'), //这里content是一个DOM
		btn: ['确定','取消'],
		success : function(){
			createEditor('uEditor',menuId);
		},
		end:function(){
			//层销毁回调
			ue.destroy();
			ue = null;
			$("#uEditor").remove();
		},
		yes:function(index,dom){
			//btn1回调 
			//获取编辑器内容，保存到数据库
			var content = UE.getEditor('uEditor').getContent();
			//根据menuId保存到数据库
			saveBzsm(menuId,content);
			//resetEditor('uEditor');
			layer.close(index);
		}
	});
}

//实例化编辑器
function createEditor(id,menuId) {
	var content;
	//根据menuId查询帮助说明
	$.ajax({
		url : ctx+"/sys/sysMenu/queryBzsm",
		type : 'POST',
		data : "id="+menuId,
		async : false,
		dataType : "json",
		success : function(data) {
			content = data;
		}
	});
	
    ue = UE.getEditor(id, {
    	//取消自动保存到localstorage、这里修改了源码
    	enableAutoSave : false
    })
    //等待编辑器加载完成
    ue.ready(function(){
    	//赋值
	   	UE.getEditor('uEditor').execCommand('insertHtml', content);
    });
}

//销毁编辑器
function deleteEditor(id) {
    UE.getEditor(id).destroy();
}

function saveBzsm(menuId,value){
	$.ajax({
		url : ctx+"/sys/sysMenu/updateBzsm",
		type : 'POST',
		data : {id:menuId,bzsm:value},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data == true){
				ranger.utils.alertInfo("操作成功！", function(){
					ranger.utils.close();
				});
			}else{
				ranger.utils.alertError("操作失败,请联系系统管理员！");
			}
		}
	});
}

//获取编辑器所有内容
function getContent(id) {
	return UE.getEditor(id).getContent();
}

//重置编辑器，用于多个tab使用同一个编辑器
function resetEditor(id){
	UE.getEditor(id).reset();
}
