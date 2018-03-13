<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
		<script type="text/javascript" src="${ctxStatic}/js/modules/scheduler/schedulerList.js"></script>
	</head>
	<body>
		<sys:panel algin="left" width="98%">
			<sys:form id="queryForm">
		        <sys:formRow>
		        	<sys:formItem lable="任务名称" >
		        		<input id="rwmc" name="rwmc"  autocomplete="off" 
		        		autocomplete="off" style="width:150px;" class="layui-input"></input>
		        	</sys:formItem>
		        	<sys:formItem>
			        	<sys:queryButton queryMethod="query();" formId="queryForm">
			        	</sys:queryButton>
			        </sys:formItem>
	       		</sys:formRow>
			</sys:form>
			
			<sys:hline/>
			
			<shiro:hasAnyPermissions name="sys:scheduler:edit">
				<sys:toolbar showExportExcel="true" exportFileName="数据列表"
					url="${ctx}/sys/scheduler/query" formId="queryForm">
					<div class="layui-btn-group">
						<sys:button onclick="add();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe654;</i>新增</sys:button>
						<sys:button onclick="edit();" btnClass="layui-btn-small layui-btn-normal"><i class='layui-icon'>&#xe642;</i>修改</sys:button>
						<sys:button onclick="delete_();" btnClass="layui-btn-small layui-btn-danger"><i class='layui-icon'>&#xe640;</i>删除</sys:button>
					</div>
					<div class="layui-btn-group">
						<sys:button onclick="update2resumeJob();" btnClass="layui-btn-small layui-btn layui-btn-radius"><i class='icon iconfont'>&#xe631;</i>恢复</sys:button>
						<sys:button onclick="update2pauseJob();" btnClass="layui-btn-small layui-btn-danger layui-btn-radius"><i class='icon iconfont'>&#xe662;</i>暂停</sys:button>
						<sys:button onclick="update2triggerJob();" btnClass="layui-btn-small layui-btn-warm layui-btn-radius"><i class='icon iconfont'>&#xe6ff;</i>立即执行一次</sys:button>
					</div>
				</sys:toolbar>
			</shiro:hasAnyPermissions>
		
			<table id="tt" > 
			   	<thead frozen="true">
					<tr>
						<th data-options="field:'id',checkbox:true">id</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'rwmc',sortable:true,halign:'center',align:'left',width:90">任务名称</th>
						<th data-options="field:'sjbds',sortable:true,halign:'center',align:'left',width:150">时间表达式</th>
						<th data-options="field:'zylm',sortable:true,halign:'center',align:'left',width:200">作业类名</th>
						<th data-options="field:'zt',sortable:true,halign:'center',align:'left',width:90">状态</th>
						<th data-options="field:'xczxsj',sortable:true,halign:'center',align:'center',width:170" formatter='formatDate'>下次执行时间</th>
						<th data-options="field:'sczxsj',sortable:true,halign:'center',align:'center',width:170" formatter='formatDate'>上次执行时间</th>
						<th data-options="field:'rwms',sortable:true,halign:'center',align:'left',width:180">任务描述</th>
					</tr>
				</thead>
			</table>
		</sys:panel>
		
		<div id="dialog"  style="display: none;padding: 10px 25px 0 0px; ">
			<sys:form id="dialogForm">
				<input type="hidden" id="id" name="id" />
				<sys:formRow>
					<sys:formItem lable="任务名称" isRequired="true">
						<input type="text" id="rwmc" name="rwmc" placeholder="请输入任务名称" 
		            			autocomplete="off" style="width:290px;background-color:#E3E3E3;" class="layui-input">
					</sys:formItem>
				</sys:formRow>	
				<sys:formRow>
					<sys:formItem lable="作业类名" isRequired="true">
						<input type="text" id="zylm" name="zylm" placeholder="格式：TestJob"
	            			autocomplete="off" style="width:290px;" class="layui-input">
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="时间表达式" isRequired="true">
						<input type="text" id="sjbds" name="sjbds" placeholder="格式：秒 分 时 日 月 星期 年"
	            			autocomplete="off" style="width:224px;float:left;" class="layui-input">
	            		<button id="ddd" class="layui-btn" style="float:right;" type="button" onclick="shili();return false;">示例</button>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="任务描述">
						<sys:textarea id="rwms" inputStyle="width:290px;height:80px;"></sys:textarea>
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
 	
	</body>
</html>