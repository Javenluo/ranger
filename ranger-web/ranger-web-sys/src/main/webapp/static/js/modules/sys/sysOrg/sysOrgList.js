layui.use(['form','jquery'], function(){
	var form = layui.form;
	var $= layui.jquery;

	//机构树控件
	$('#tt').treegrid({ 
	    url : ctx+'/sys/sysOrg/getOrgListByParentId', 
	    idField:'id', 
	    treeField:'orgName',
	    rownumbers:true,
	    animate:true
	});
	
});

/**
 * 格式化status字段
 */
function formatSatus(value){
    if (value=="1"){
        return "<span class='layui-icon'>&#xe618;</span>";
    } else {
    	return "<span class='layui-icon'>&#x1006;</span>";
    }
}

/**
 * 树表格每行添加操作栏
 */
function formatOperation(value,row,index){
	return "<button class='layui-btn layui-btn-normal layui-btn-mini'  onclick='add(\""+value+"\")'>添加下级</button>"
				+"<button class='layui-btn layui-btn-normal layui-btn-mini' onclick='edit(\""+value+"\")'>修改</button>"
				+"<button class='layui-btn layui-btn-danger layui-btn-mini' onclick='delete_(\""+value+"\")'>删除</button>";
}

/**
 * 添加下级机构按钮
 */
function add(id){
	$("#tjjg #addForm")[0].reset();
	
	 var row = $("#tt").treegrid('find',id);
	
	$("#tjjg #addForm #parentOrgName").val(row.orgName);
	$("#tjjg #addForm #parentOrgCode").val(row.orgCode);
	$("#tjjg #addForm #parentOrgId").val(row.id);
	$("#tjjg #addForm #fullOrgIds").val(row.fullOrgIds);
	$("#tjjg #addForm #fullOrgCodes").val(row.fullOrgCodes);
	$("#tjjg #addForm #fullOrgNames").val(row.fullOrgNames);
	$("#tjjg #addForm #id").val(null);
	
	layer.open({
		type : 1,
		closeBtn : 1,
		title : "新增机构",
		maxmin : true,
		area : [ '660px', '375px' ],
		content : $('#tjjg'), //这里content是一个DOM
		btn : [ '确定', '取消' ],
		btn1 : function() {
			okFunction();
		}
	});
}

/**
 * 修改按钮
 */
 function edit(orgId){
	 $("#tjjg #addForm")[0].reset();
	 
	 var row = $("#tt").treegrid('find',orgId);
	 $("#tjjg #addForm").form('load',row);
	 $("#addForm #oldOrgCode").val(row.orgCode);
	 
	 layer.open({
		type : 1,
		closeBtn : 1,
		title : "修改机构",
		maxmin : true,
		area : [ '660px', '375px' ],
		content : $('#tjjg'), //这里content是一个DOM
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
	if($("#addForm #id").val() == ''){
		varUrl = ctx+'/sys/sysOrg/insert';
	}else{
		varUrl = ctx+'/sys/sysOrg/update';
	}


	var formData = $("#addForm").serializeObject();
	if(formData.enableFlag==null){
		formData.enableFlag = "0";
	}
	if($.trim(formData.orgCode)==""){
		ranger.utils.alertWarn("机构编码不能为空，请填写！");
		return false;
	}
	if($.trim(formData.orgName)==""){
		ranger.utils.alertWarn("机构名称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.orgShortName)==""){
		ranger.utils.alertWarn("机构简称不能为空，请填写！");
		return false;
	}
	if($.trim(formData.showOrder)==""){
		ranger.utils.alertWarn("排列顺序不能为空，请填写！");
		return false;
	}
	
	//需要验证编码是否重复
	if($.trim(formData.orgCode)!=$.trim(formData.oldOrgCode)){
		$.post(ctx+"/sys/sysOrg/checkExsit4OrgCode",formData,function(data){
			if(data){
				ranger.utils.alertWarn("机构编码已经存在，请修改！");
			}else{
				saveData(varUrl,formData)
			}
		});
	}else{
		saveData(varUrl,formData);
	}
}

function saveData(varUrl,formData){
	ranger.utils.confirm("确定要提交数据吗?", function(r) {
		if (r) {
			$.post(varUrl,formData,function(data){
				if(data.success){
					ranger.utils.alertInfo("操作成功！",function(){
						ranger.utils.close();
						
						var row = $('#tt').treegrid('find',formData.parentOrgId);
						if(row.state == 'open' && row.children.length == 0 ){
							var parent = $("#tt").treegrid('getParent',formData.parentOrgId);
							$("#tt").treegrid('reload',parent.id);
						}else{
							$("#tt").treegrid('reload',formData.parentOrgId);
						}
						
						/*if(formData.id!=null && formData.id!=''){
							var row = $('#tt').treegrid('find',formData.id);
							row
							$("#tt").treegrid('reload',formData.id);
						}*/
						
					});
				}else{
					ranger.utils.alertError("操作失败,请联系系统管理员！");
				}
			},"json");
		}
	});
}

/**
 * 删除按钮
 */
function delete_(orgId){
	var child = $('#tt').treegrid('getChildren',orgId);
	var row = $('#tt').treegrid('find',orgId);
	if(row.state != 'open' || child.length >0 ){
		ranger.utils.alertWarn("包含子机构，不允许删除！");
	}else{
		ranger.utils.confirm("确定要删除选中的数据吗?", function(r) {
			if (r) {
				$.post(ctx+"/sys/sysOrg/delete",{id:orgId},function(data){
					if(data.success){
						ranger.utils.alertInfo("删除机构成功！");
						var parent = $("#tt").treegrid('getParent',orgId);
						var children = $("#tt").treegrid('getChildren',parent.id);
						if(children.length == 1){ // 判断刷新树表格
							$("#tt").treegrid('reload');
						} else {
							$("#tt").treegrid('reload',parent.id);
						}
					}else{
						ranger.utils.alertError("操作失败，请联系系统管理员！");
					}
				});
			 }
		});
	}
}