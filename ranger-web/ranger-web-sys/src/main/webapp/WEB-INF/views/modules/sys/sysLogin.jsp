<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html> 
<html>
<head>
	<meta charset="utf-8"> 
	<meta name="viewport" content="width=device-width, initial-scale=0.7"> 
	<title>${fns:getConfig('productName')} 登录</title>
	<link href="${ctxStatic}/uimaker/green/css/login/login.css" rel="stylesheet">
	<link href="${ctxStatic}/uimaker/green/css/base.css" rel="stylesheet">
	<link href="${ctxStatic}/jquery-easyui-1.5/themes/default/easyui.css" rel="stylesheet" >
	<link href="${ctxStatic}/jquery-easyui-1.5/themes/icon.css" rel="stylesheet" >
	<link href="${ctxStatic}/layui/css/layui.css" rel="stylesheet" type="text/css">
	
	<script src="${ctxStatic}/jquery-easyui-1.5/jquery.min.js"></script>
	<script src="${ctxStatic}/jquery-easyui-1.5/jquery.easyui.all.js"></script>
	<script src="${ctxStatic}/layer/layer.js"></script>
	<script src="${ctxStatic}/layui/layui.js"></script>
	<script src="${ctxStatic}/js/fbidp.js"></script>
	<script src="${ctxStatic}/js/fbidp-ui.js"></script>
	
	<script type="text/javascript">
		var ctx = '${ctx}';
		var ctxStatic='${ctxStatic}';
	</script>
	
	<script type="text/javascript">
		var falg = true;
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
		
		function validateForm(){
			$("#errorInfo").hide();
			$("#errorInfo").empty();
			
			var isValid = true;
			var username = $("#loginForm #username").val();
			if(username==null || $.trim(username)==''){
				isValid = false;
				$("#errorInfo").show();
				$("#errorInfo").append($("<li><i class=\"iconfont\">&#xe62e;</i><span>请输入用户名</span></li>"));	
			}
			
			var password = $("#loginForm #password").val();
			if(password==null || $.trim(password)==''){
				isValid = false;
				$("#errorInfo").show();
				$("#errorInfo").append($("<li><i class=\"iconfont\">&#xe62e;</i><span>请输入密码</span></li>"));
			}
			
			<c:if test="${isValidateCodeLogin}">
				var validateCode = $("#loginForm #validateCode").val();
				if(validateCode==null || $.trim(validateCode)==''){
					isValid = false;
					$("#errorInfo").show();
					$("#errorInfo").append($("<li><i class=\"iconfont\">&#xe62e;</i><span>请输入验证码</span></li>"));
				}else{
					$.ajax({
						url:"${pageContext.request.contextPath}/servlet/validateCodeServlet?validateCode="+validateCode,
						type:"GET",
						async:false,
						success:function(data){
							data = eval("("+data+")")
							isValid = data;
							if(!data){
								$("#errorInfo").show();
								$("#errorInfo").append($("<li><i class=\"iconfont\">&#xe62e;</i><span>验证码输入错误</span></li>"));
							}
							
						}
					});
				}
			</c:if>
			
			if(isValid){
				$.post(ctx+"/checkUsernamePwd",$("#loginForm").serializeObject(),function(data){
					if(data.success){
						var userList = data.userList;
						if(userList.length == 1){
							login(userList[0].id,userList[0].loginId);
						} else {
							layer.open({
								type : 1,
								closeBtn : 1,
								title : "用户身份选择",
								maxmin : true,
								area : [ '500px', '300px' ],
								content : $('#usersf'), //这里content是一个DOM
								btn : [ '确定', '取消' ],
								btn1 : function() {
									var users = $("#userTable").datagrid('getSelections');
									if(users.length == 1){
										login(users[0].id,users[0].loginId);
									} else {
										fbidp.utils.alertWarn("请选取一条数据进行操作！");
										return;
									}
								},
								end : function() {
									falg = true;
								}
							});
							falg = false;
							$("#userTable").datagrid({
								height : 200,
								pagination:false,
								data:userList
							});
						}
					} else {
						fbidp.utils.alertError(data.message);
					}
				},"json");
			}
		}
		
		function keyLogin(){
			if(event.keyCode == 13 && falg){
				$("#sub").click();
			}
		}
		
		//更新缓存
		function login(id,name){
			$.ajax({
				url : ctx+"/login",
				type:"post",
				data : {userId:id,username:name,password:'1',mobileLogin:true},
				async:false,
				success:function(data){
					//location.reload();
					location.href =  ctx + "/login?username="+name;
				}
			});
		}
	</script>
</head>
<body class="default" onkeydown="keyLogin();">
	<div class="login-hd">
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo"></span>
			<span class="split"></span>
			<span class="sys-name">${fns:getConfig('productName')}</span>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<form id="loginForm">
							<input type="hidden" name="mobileLogin" value="true">
							<div class="lg-label"><h4>用户登录</h4></div>
							<ul id="errorInfo" class="alert alert-error" style="display: ${empty message?'none':''}">
				            	<li><i class="iconfont">&#xe62e;</i>${message}</li>
				            </ul>
							<div class="lg-username input-item clearfix">
								<i class="iconfont">&#xe60d;</i>
								<input type="text" id="username" name="username" 
									class="required" placeholder="请输入用户名" required="required"
									style="line-height: 35px;">
							</div>
							<div class="lg-password input-item clearfix">
								<i class="iconfont">&#xe634;</i>
								<input type="password" id="password" name="password"  
									class="required" placeholder="请输入密码" required="required"
									style="line-height: 35px;">
							</div>
							<c:if test="${isValidateCodeLogin}">
								<div class="lg-check clearfix">
									<div class="input-item">
										<i class="iconfont">&#xe633;</i>
										<input type="text" id="validateCode" name="validateCode"  required="required"
											class="required" placeholder="请输入验证码">
									</div>
									<span class="check-code">
										<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onclick="$('.validateCodeRefresh').click();" 
											class="mid validateCode" style="width: 116px;height: 42px;"/>
										<a href="javascript:" onclick="$('.validateCode').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" 
											class="mid validateCodeRefresh" style="display: none;">看不清</a>
									</span>
								</div> 
							</c:if>
							<div class="enter">
								<a href="javascript:;" class="purchaser supplier" id="sub"
									style="width:300px" onClick="javascript:validateForm();">登录</a>
							</div>
						</form>
					</div>
				</div>
				<div class="lg-poster" style="background-size: 100%;height: 455px;">
					<img style="padding:20px 0px 0px 120px;width: 500px;height: 420px;" src="${ctxStatic}/uimaker/green/images/login.png" alt="logo">
				</div>
			</div>
		</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
<!-- 			<div class="about-us">
				<a href="javascript:;">关于我们</a>
				<a href="javascript:;">法律声明</a>
				<a href="javascript:;">服务条款</a>
				<a href="javascript:;">联系方式</a>
			</div> -->
			<div class="other-info">Copyright&nbsp;©&nbsp;广东省地方税务局 &nbsp;&nbsp;建议使用IE8及以上版本浏览器</div>
		</div>
	</div>
	
	<div id="usersf" style="display: none;">
		<table id="userTable" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'id',align:'center',checkbox:true">id</th>
					<th data-options="field:'loginId',width:100,halign:'center',align:'left',sortable:true">登录账号</th>
					<th data-options="field:'name',width:100,halign:'center',align:'left',sortable:true">用户姓名</th>
					<th data-options="field:'fullOrgNames',width:'240',halign:'center',align:'left',sortable:true">所属机构</th>
					<th data-options="field:'orgCode',width:'95',halign:'center',align:'left',sortable:true,hidden:true">所属机构代码</th>
		        </tr> 
		    </thead> 
		</table>
	</div>
	
</body> 
</html>
    
<script type="text/javascript">

</script>
