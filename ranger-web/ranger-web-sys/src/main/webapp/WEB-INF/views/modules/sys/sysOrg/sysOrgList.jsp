<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default" />
	</head>
	<body>
		<sys:panel algin="left" width="98%">
			<table id="tt"  style="height:425px"> 
				<thead frozen="true"> 
			        <tr> 
			            <th data-options="field:'id',width:200,halign:'center'" align="center" formatter="formatOperation">操作</th> 
			        </tr>
			    </thead>
			    <thead> 
			        <tr> 
			            <th data-options="field:'orgName',width:180,halign:'center',align:'left'">机构名称</th> 
			            <th data-options="field:'orgShortName',width:100,halign:'center',align:'left'" >简称</th> 
			            <th data-options="field:'orgCode',width:120,halign:'center'" align="left">机构代码</th> 
			            <th data-options="field:'leaderName',width:90,halign:'center'">负责人</th> 
			            <th data-options="field:'phone',width:100,halign:'center'">办公室电话</th> 
			            <th data-options="field:'enableFlag',width:70,halign:'center'" align="center"  formatter="formatSatus">有效</th> 
			            <th data-options="field:'showOrder',width:60,halign:'center'"  align="center">排序</th> 
			            <th data-options="field:'remark',width:180,halign:'center',align:'left'">备注</th> 
			        </tr> 
			    </thead> 
			</table>
		</sys:panel>
		
		<div id="tjjg"  style="padding: 8px 10px;display: none; " >
			<sys:form id="addForm">
				<input type="hidden"  name="oldOrgId" id="oldOrgId"/>
				<input type="hidden"  name="oldOrgCode" id="oldOrgCode"/>
				<input type="hidden"  name="oldOrgName" id="oldOrgName"/>
				<input type="hidden"  name="oldOrgShortName" id="oldOrgShortName"/>
				<input type="hidden"  name="parentOrgId" id="parentOrgId"/>
				<input type="hidden"  name="parentOrgCode" id="parentOrgCode"/>
				<input type="hidden"  name="fullOrgIds" id="fullOrgIds"/>
				<input type="hidden"  name="fullOrgCodes" id="fullOrgCodes"/>
				<input type="hidden"  name="fullOrgNames" id="fullOrgNames"/>
				<input type="hidden"  name="oldFullOrgCodes" id="oldFullOrgCodes"/>
				<input type="hidden"  name="oldFullOrgNames" id="oldFullOrgNames"/>
				<input type="hidden"  name="id" id="id"/>
				
				<sys:formRow>
					<sys:formItem lable="机构编码" isRequired="true">
	            		<sys:input id="orgCode" inputStyle="width:180px;" required="true" />
					</sys:formItem>
					<sys:formItem lable="机构名称" isRequired="true">
						<sys:input id="orgName" inputStyle="width:180px;" required="true" />
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="机构简称" isRequired="true">
						<sys:input id="orgShortName" inputStyle="width:180px;" required="true" />
					</sys:formItem>
					<sys:formItem lable="上级机构">
						<sys:input id="parentOrgName" inputStyle="width:180px;" readOnly="true"></sys:input>
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<%-- <sys:formItem lable="负责人">
						<!-- <f:selectTree id="leaderId" path="leaderId" type="user" width="180" delay="2000" /> -->
	            		<sys:input id="leader" inputStyle="width:180px;" />
					</sys:formItem> --%>
					<sys:formItem lable="办公室电话">
	            		<sys:input id="phone" inputStyle="width:180px;" />
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="办公地址">
						<sys:input id="address" inputStyle="width:180px;"  />
					</sys:formItem>
					<sys:formItem lable="排列顺序" isRequired="true">
						<sys:input id="showOrder" inputStyle="width:180px;"  />
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="是否启用" itemStyle="width:180px;">
						<sys:checkbox id="enableFlag" checked="true" text="是|否" />
					</sys:formItem>
					<sys:formItem lable="组织属性" >
						<input type="radio" name="orgAttribute" value="0" title="机关"checked="checked">
      					<input type="radio" name="orgAttribute" value="1" title="部门">
					</sys:formItem>
				</sys:formRow>
				<sys:formRow>
					<sys:formItem lable="备注" >
						<sys:textarea id="remarks" inputStyle="width:490px;height:75px;"  />
					</sys:formItem>
				</sys:formRow>
			</sys:form>
		</div>
		
		<script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysOrg/sysOrgList.js"></script>
		
	</body>
</html>