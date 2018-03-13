<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="id"%>
<%@ attribute name="onClick" type="java.lang.String" required="true" description="点击事件"%>
<%@ attribute name="checkEnable" type="java.lang.Boolean" required="false" description="设置 zTree 是否开启异步加载模式"%>
<%@ attribute name="style" type="java.lang.String" required="false"  description="样式"%>
<%@ attribute name="type" type="java.lang.String" required="true"  description="数类型"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="标题"%>
<%@ attribute name="panelHeight" type="java.lang.String" required="true" description="面板高度"%>
<%@ attribute name="chkStyle" type="java.lang.String" required="false" description="勾选框类型"%>
<%@ attribute name="async" type="java.lang.Boolean" required="true" description="是否异步加载"%>
<%@ attribute name="value" type="java.lang.String" required="false" description="默认值"%>

<div style="height:{panelHeight}px;${style};">
    <h2><span id="layui-colla-title_${id}">${title}</span></h2>
    <sys:hline/>
    <div style="overflow: auto;height:${panelHeight}px;">
		<ul id="${id}_treex" class="ztree">
		</ul>
	</div>
	<sys:hline/>
	<jsp:doBody></jsp:doBody>
</div>

<script>
$(function(){
	var f = null;
	if("${onClick}"!="" && "${onClick}"!="null"){ 
		f = eval("(${onClick})");
	}
	
	var treeSetting = {
		check: {
			<c:if test="${not empty chkStyle}">
			chkStyle : '${chkStyle}' ,
			radioType : "all",
			</c:if>
			<c:if test="${empty chkStyle}">
			chkStyle : "checkbox" ,
			</c:if>
            enable: ${checkEnable?true:false}
        },
		data: {
			simpleData: {
				enable: true
			}
		},
		<c:if test="${async}">
		async:{
			enable:true,
			url:ctx+"/common/tree/loadChildren/${type}",
			autoParam:['id']
		},
		</c:if>
		callback:{
			onClick : f,
			onNodeCreated : function(e,treeId,treeNode){
				if("${value}"!='' && treeNode.id == "${value}"){
					$.fn.zTree.getZTreeObj(treeId).selectNode(treeNode);
					$("#dialogForm #ssqyDm").val(treeNode.otherParam.orgCode);
					$("#dialogForm #ssjgMc").val(treeNode.name);
				}	
			},
			beforeClick : function(treeId,treeNode){
				if(treeNode.otherParam.canSelect=='true'){
					return true;
				}
				return false;
			}
		}
	};
	<c:if test="${async}">
	$.fn.zTree.init($("#${id}_treex"), treeSetting);
	</c:if>
	<c:if test="${!async}">
		$.post(ctx+"/common/tree/loadChildren/${type}",function(nodes){
			var zTreeObj = $.fn.zTree.init($("#${id}_treex"), treeSetting, nodes);
		});
	</c:if>
});

function updateTitle_${id}(title){
	$("#layui-colla-title_${id}").html(title);
}

function reload_${id}(){
	var treeObj = $.fn.zTree.getZTreeObj("${id}_treex");
	treeObj.reAsyncChildNodes(null, "refresh");
}

function getSelectedNodes_${id}(){
	var treeObj = $.fn.zTree.getZTreeObj("${id}_treex");
	return treeObj.getSelectedNodes();
}

function reload4SelectedNodes_${id}(){
	var treeObj = $.fn.zTree.getZTreeObj("${id}_treex");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length>0) {
		treeObj.reAsyncChildNodes(nodes[0], "refresh");
	}
}

</script>