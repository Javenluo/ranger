<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="showExportExcel" type="java.lang.Boolean" required="显示/隐藏导出按钮"  rtexprvalue="true"%>
<%@ attribute name="url" type="java.lang.String" required="查询导出数据url"  rtexprvalue="true"%>
<%@ attribute name="formId" type="java.lang.String" required="表单ID"  rtexprvalue="true"%>
<%@ attribute name="exportFileName" type="java.lang.String" required="导出文件名" rtexprvalue="true"%>

<div style="margin-top: 5px;margin-bottom: 8px;">
	<jsp:doBody></jsp:doBody>
	<c:if test="${showExportExcel}">
		<div class="layui-btn-group">
			<sys:button onclick="exportExcel_${formId}();" btnClass="layui-btn layui-btn-small"><i class='icon iconfont'>&#xe61b;</i>导出</sys:button>
		</div>
		<script>
			function exportExcel_${formId}(){
				//显示加载框
				layer.load(0,{shade: [0.3, '#393D49'] });//0.1透明度的白色背景
				
				//创建用于隐式提交用的form
				var f = $("#table_export_form");	
				if(!f[0]){
					f = $("<form id=\"table_export_form\" method=\"post\" style='display:none'></form>");
					f.appendTo(document.body);
				}
				f.html(""); 
				
				//组装查询条件
				var p = $("#${formId}").serializeObject();
				p.table_export_flag = "yes";
				p.table_exportFileName = "${exportFileName}";
				p.page = 1;
				p.rows = 2000; //公用下载，只支持小数据量
				
				//ajax调用生成文件
				$.post("${url}",p,function(fileId){
					//隐藏加载框
					layer.closeAll('loading');
					
					//弹出文件下载框
					$("<input type='hidden' name='fileId'>").val(fileId).appendTo(f);
					$("<input type='hidden' name='table_exportFileName'>").val("${exportFileName}").appendTo(f);
					f.attr("action",ctx+"/common/filedownload/downloadExcel4Tmpdir");
					f.submit(); 
				});
				
			}
		</script>
	</c:if>
</div>