<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html> 
<html>
	<head>
		<title>
			<sitemesh:title/>
		</title>
		<%@include file="/WEB-INF/views/include/head.jsp" %>		
		<sitemesh:head/>
	</head>
	<body class="default" style="padding: 3px;background-color: #F1F4F5;position: relative;">
		<sitemesh:body/>
		<!-- 菜单功能帮助说明弹出框div -->
		<div id="bzsmDiv" style="padding:5px;display: none;">
			
		</div>
	</body>
</html>
<script>
var ueData;
var content;
var contentMenuId = "<%=request.getParameter("menuId")%>";
var util;
layui.use(['util','laydate','layer'],function(){
	util = layui.util
	,laydate = layui.laydate
	,layer = layui.layer;
	
	getContent();
})

function getContent(){
	//根据menuId查询帮助说明
	$.ajax({
		url : ctx+"/sys/sysMenu/queryBzsm",
		type : 'POST',
		data : "id="+contentMenuId,
		async : false,
		dataType : "json",
		success : function(data) {
			if(data == null || $.trim(data) == ''){
				//没数据
				
			}else{
				content = data;
				//固定块
				util.fixbar({
					bar1 : false,
					bar2 : true,
					css : {right:0,bottom:0},
					bgcolor:'#393D49',
					click:function(type){
						if(type=='bar1'){
							layer.msg('1111');
						} else if(type=='bar2'){
							openBzsm();
						}
					}
				})
			}
		}
	});
}

function openBzsm(){
	var ueditor = $("<div id='uEditorData' style='height:230px;width:560px;margin-top:5px'></div>")
	ueditor.appendTo($("#bzsmDiv"));
	$(ueditor).css('margin-left','15px');
	
	layer.open({
		type : 1,
		title : "帮助说明",
		maxmin : true,
		zIndex: -1,
		area : [ '600px', '350px' ],
		content : $('#bzsmDiv'), //这里content是一个DOM
		btn: ['关闭'],
		yes:function(index,dom){
			//btn1回调 
			layer.close(index);
		},
		success: function(){
			createEditorData('uEditorData',content);
		},
		end:function(){
			//层销毁回调
			ueData.destroy();
			ueData = null;
			$("#uEditorData").remove();
		}
	});
	
}

//实例化编辑器
function createEditorData(id,content) {
    ueData = UE.getEditor(id, {
    	toolbars:[],
    	//取消自动保存到localstorage、这里修改了源码
    	enableAutoSave : false ,
    	wordCount : false  
    })
  	//等待编辑器加载完成
    ueData.ready(function(){
    	//赋值
	    ueData.execCommand('insertHtml', content);
	    ueData.setDisabled();
    });
}

</script>