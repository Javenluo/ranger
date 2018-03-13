<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="width" type="java.lang.String" required="false" description="控件宽度"%>
<%@ attribute name="panelWidth" type="java.lang.String" required="false"  description="下拉面板宽度"%>
<%@ attribute name="panelHeight" type="java.lang.String" required="false"  description="下拉面板高度"%>
<%@ attribute name="formId" type="java.lang.String" required="true"  description="下拉面板高度"%>
<%@ attribute name="type" type="java.lang.String" required="true"  description="type"%>
<%@ attribute name="disabled" type="java.lang.String" required="false"  description="启用/禁用"%>
<%@ attribute name="onSelect" type="java.lang.String" required="false"  description="选择事件"%>

<input type="text" id="${id}" name="${id}" style="width:${width}px;height:30px;line-height:30px;disabled:${disabled}">
</input>
<div id="${id}_treepanel"  style="display: none;z-index: 99999999;" >
	<div style="overflow:auto;">
		<ul id="${id}_tree_sp" style="overflow: auto;height: ${panelHeight-2}px;width: ${panelWidth-2}px;"></ul>
	</div>
</div>		

<script>
$("#${id}",function(){
	$("#${formId} #${id}").combo({
		editable : false,
		panelWidth : ${panelWidth},
		panelHeight : ${panelHeight}
	});
	
	$("#${id}_treepanel").css("display","");
	$("#${id}_treepanel").appendTo($("#${formId} #${id}").combo('panel'));
	
	$("#${id}_tree_sp").tree({   
		url : ctx+"/common/tree/loadChildren/${type}",
		queryParams:{
			easyui_tree_node_flag : 'yes',
			id:'',
			withSelf:true
		},
		onBeforeExpand:function(node){
			$("#${id}_tree_sp").tree("options").queryParams.withSelf = false;
		},
		onClick:function(node){
			if(node.canSelect != undefined && (!node.canSelect || node.canSelect != "true") ){
				return ;
			}
			
			$("#${formId} #${id}").combo("setValue",node.id).combo("setText",node.text);
			$("#${formId} #${id}").combo("hidePanel");
			
			<c:if test="${not empty onSelect}">
				${onSelect}();
			</c:if>
		},
		onLoadSuccess:function(node, data){
			if("${value}"!='' && "${value}"!='null'){ //设置默认值
				var n = $("#${id}_tree_sp").tree("find","${value}");
				$(n.target).attr("title", n.title);
				$("#${id}_tree_sp").tree("expandTo",n.target).tree("select",n.target).tree("scrollTo",n.target);
				
				$("#${id}").combo("setValue",n.id).combo("setText",n.text);
			}
		}
	});
});

function reloadTree_${id}(){
	$("#${id}_tree_sp").tree("reload");
}

function setDefaultValue_${id}(){
	var n = $("#${id}_tree_sp").tree("find","${value}");
	$("#${id}").combo("setValue",n.id).combo("setText",n.text);
}
</script>