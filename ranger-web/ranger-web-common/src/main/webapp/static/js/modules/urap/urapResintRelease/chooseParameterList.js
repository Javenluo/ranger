var idString="";
var nameString="";

$(document).ready(function () {
	$('#tt').datagrid({ 
	    url:ctx+'/urap/urapParameter/query', 
	    queryParams : $("#queryForm").serializeObject(),
	    idField:'id', 
	    rownumbers:true,
		pagination:true,
		onCheck:function(index, row){
			/**
			 * 确定选择的参数
			 */
				var rows = $("#tt").datagrid('getSelections');
				if (rows.length > 0) {
					for(var i=0;i<rows.length;i++) {
						idString += (rows[i].id+',');
						nameString +=(rows[i].cname+',');
					}
					$("#queryForm #idString").val(idString);
					$("#queryForm #nameString").val(nameString);
				}
		}
	});
	
});

/**
 * 表格加载
 * @returns
 */
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


