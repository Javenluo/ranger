<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html> 
<html> 
<head> 
    <meta charset="utf-8"> 
    <meta http-equiv="X-UA-Compatible" content="IE=8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    <title>${fns:getConfig('productName')}</title> 
	<link href="${ctxStatic}/uimaker/darkblue/css/base.css" rel="stylesheet">
	<link href="${ctxStatic}/uimaker/darkblue/css/platform.css" rel="stylesheet">
	<link rel="stylesheet" href="${ctxStatic}/easyui/custom/darkblue/uimaker/easyui.css">
	<link href="${ctxStatic}/layui/css/layui.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/layui/css/layui-ui.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${ctxStatic}/easyui/jquery.min.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <script src="${ctxStatic}/layui/layui.js"></script>
    <script type="text/javascript" src="${ctxStatic}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript">
		var ctx = '${ctx}';
		var ctxStatic='${ctxStatic}';
		var webFirstUrl = ctx+"${fns:getConfig('web.first.url')}";
		
	</script>
   
</head> 
<body>
    <div class="container">
        <div id="pf-hd">
            <div class="pf-logo">
                <img style="padding-bottom: 10px;width: 48px;height: 48px;" src="${ctxStatic}/uimaker/green/images/s01.png" alt="logo">
                <span style="display: inline-block;position: relative;color: #f1f1f1;font-size: 28px;">${fns:getConfig('productName')}</span>
            </div>
            
            <div class="pf-nav-wrap">
              <div class="pf-nav-ww">
                <ul class="pf-nav">
                  
                </ul>
              </div>
              

              <a href="javascript:;" class="pf-nav-prev disabled iconfont">&#xe606;</a>
              <a href="javascript:;" class="pf-nav-next iconfont">&#xe607;</a>
            </div>
            

          <!--   换肤功能，暂时屏蔽！
          <div class="pf-skin">
              <i class="iconfont">&#xe708;</i>
              <ul class="skin-list">
                <li class="skin-item" data-color="green"></li>
                <li class="skin-item" data-color="blue"></li>
                <li class="skin-item" data-color="lightblue"></li>
                <li class="skin-item" data-color="orange"></li>
                <li class="skin-item" data-color="red"></li>
                <li class="skin-item" data-color="darkblue"></li>
                <li class="skin-item" data-color="black"></li>
                <li class="skin-item" data-color="darkgrey"></li>
              </ul>
            </div> -->
              
            <div class="pf-user">
            	<!-- <span class="msgts">10</span> -->
            	
                <div class="pf-user-photo">
                    <img src="${ctxStatic}/uimaker/green/images/main/user.png" alt="">                   
                </div>
                <h4 class="pf-user-name ellipsis">&nbsp;${fns:getUser().name}&nbsp;</span></h4>
                <i class="iconfont xiala">&#xe607;</i>

                <div class="pf-user-panel">
                    <ul class="pf-user-opt">
                        <li onclick="openUserInfoWin('${fns:getUser().id}')">
                            <a href="javascript:;">
                                <i class="iconfont">&#xe60d;</i>
                                <span class="pf-opt-name">用户信息</span>
                            </a>
                        </li>
                        <li class="pf-modify-pwd" onclick="openPwdWin('${fns:getUser().id}')">
                            <a href="javascript:;">
                                <i class="iconfont">&#xe634;</i>
                                <span class="pf-opt-name">修改密码</span>
                            </a>
                        </li> 
                        <li class="pf-logout">
                            <a href="javascript:;">
                                <i class="iconfont">&#xe60e;</i>
                                <span class="pf-opt-name">退出</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="pf-user" onclick="openMenu('系统消息',ctx+'${adminPath}/sys/sysNotice/list','0')">
            	<div class="pf-user-photo">
                   <img src="${ctxStatic}/uimaker/green/images/notice.png" alt="">  
                </div>
                <h4 class="pf-user-name ellipsis">&nbsp;系统消息&nbsp;<span class="layui-badge" id="zc"></span></h4>
            </div> 
        </div>

        <div id="pf-bd">
            <div class="pf-sider-wrap">
               <!-- 左侧菜单 -->
            </div>
            

            <div id="pf-page">
               <!-- 主工作空间 -->
            </div>
            
            
            
            
            
            
            
        </div>

        <div id="pf-ft">
            <div class="system-name">
              <i class="iconfont">&#xe6fe;</i>
              <span>${fns:getConfig('productName')}&nbsp;v1.0</span>
            </div>
            <div class="copyright-name">
              <!-- <span>CopyRight&nbsp;2016&nbsp;&nbsp;www.foresee.com&nbsp;版权所有</span> -->
              <i class="iconfont" >&#xe6ff;</i>
            </div>
        </div>
    </div>

    <div id="mm" class="easyui-menu tabs-menu" style="width:120px;display:none;">
         <div id="mm-tabclose">关闭</div>
         <div id="mm-tabcloseall">关闭所有</div>
         <div id="mm-tabcloseother">关闭其他</div> 
    </div>
    
    <!-- 用户信息查看 -->
  	<div id="userInfoWin"  style="padding: 8px; display: none; ">
		<sys:form id="userInfoForm">
			<input type="hidden" id="id" name="id"/>
			<sys:formRow>
				<sys:formItem lable="登录帐号">
            		<sys:input id="loginId" inputStyle="width:170px;" readOnly="true"></sys:input>
				</sys:formItem>
				<sys:formItem lable="用户名称">
					<sys:input id="name" inputStyle="width:170px;" readOnly="true"></sys:input>
				</sys:formItem>
			</sys:formRow>
			<sys:formRow> 
				<sys:formItem lable="所属机构">
					<sys:input id="fullOrgNames" inputStyle="width:450px;" readOnly="true"></sys:input>
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="手机号码" >
					<sys:input id="mobileTel" inputStyle="width:170px;" readOnly="true">></sys:input>
				</sys:formItem>
				<sys:formItem lable="办公电话">
					<sys:input id="tel" inputStyle="width:170px;" readOnly="true">></sys:input>
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="邮箱" >
					<sys:input id="email" inputStyle="width:170px;" readOnly="true">></sys:input>
				</sys:formItem>
			</sys:formRow>
		</sys:form>
	</div>
	
	 <!-- 密码重置 -->
	<div id="resetPwdWin"  style="padding: 8px; display: none; ">
		<sys:form id="resetPwdForm">
			<input type="hidden" id="id" name="id" />
			<sys:formRow>
				<sys:formItem lable="登录帐号" lableStyle="width:100px">
            		<sys:input id="loginId" inputStyle="width:170px;" readOnly="true"></sys:input>
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="用户名称" lableStyle="width:100px">
					<sys:input id="name" inputStyle="width:170px;" readOnly="true"></sys:input>
				</sys:formItem>
			</sys:formRow>
			<sys:hline/>
			<sys:formRow>
				<sys:formItem lable="旧密码" isRequired="true" lableStyle="width:100px">
					<input type="password" id="passwd" name="passwd"  lay-verify="required"
            			autocomplete="off" style="width:170px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="新密码" isRequired="true" lableStyle="width:100px">
					<input type="password" id="newPasswd" name="newPasswd"  lay-verify="required"
            			autocomplete="off" style="width:170px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
			<sys:formRow>
				<sys:formItem lable="确认新密码" isRequired="true" lableStyle="width:100px">
					<input type="password" id="newPassword2" name="newPassword2"  lay-verify="required"
            			autocomplete="off" style="width:170px;" class="layui-input">
				</sys:formItem>
			</sys:formRow>
		</sys:form>
	</div>
    
    <!--[if IE 7]>
      <script type="text/javascript">
        $(window).resize(function(){
          $('#pf-bd').height($(window).height()-76);
        }).resize();
        
      </script>
    <![endif]-->  
 	<script type="text/javascript" src="${ctxStatic}/uimaker/menu.js"></script>
    <script type="text/javascript" src="${ctxStatic}/uimaker/main.js"></script>
    <script src="${ctxStatic}/js/fbidp.js"></script>
	<script src="${ctxStatic}/js/fbidp-ui.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/modules/sys/sysIndex.js"></script>
    
    <script type="text/javascript">
    $(window).resize(function(){
          $('.tabs-panels').height($("#pf-page").height()-46);
          $('.panel-body').not('.messager-body').height($(".easyui-dialog").height)
    }).resize();

    var page = 0,
        pages = ($('.pf-nav').height() / 70) - 1;

    if(pages === 0){
      $('.pf-nav-prev,.pf-nav-next').hide();
    }
    $(document).on('click', '.pf-nav-prev,.pf-nav-next', function(){


      if($(this).hasClass('disabled')) return;
      if($(this).hasClass('pf-nav-next')){
        page++;
        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
        if(page == pages){
          $(this).addClass('disabled');
          $('.pf-nav-prev').removeClass('disabled');
        }else{
          $('.pf-nav-prev').removeClass('disabled');
        }
        
      }else{
        page--;
        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
        if(page == 0){
          $(this).addClass('disabled');
          $('.pf-nav-next').removeClass('disabled');
        }else{
          $('.pf-nav-next').removeClass('disabled');
        }
        
      }
    })

    function replace(doc, style) {
      $('link', doc).each(function(index, one) {
        var path = $(one).attr('href')
        				.replace(/(uimaker\/)\w+(\/css)/g, '$1' + style + '$2')
        				.replace(/(custom\/)\w+(\/)/g, '$1' + style + '$2');
        var sheet;
        
        if(doc.createStyleSheet) {
          sheet = doc.createStyleSheet(path);
          setTimeout(function() {
            $(one).remove();
          }, 500);
        }else{
          sheet = $('<link rel="stylesheet" type="text/css" href="' + path + '" />').appendTo($('head', doc));
          sheet = sheet[0];
          sheet.onload = function() {
            $(one).remove();
          }
        }
      })

      $('img', doc).each(function(index, one) {

        var path = $(one).attr('src').replace(/(static\/)\w+(\/images)/g, '$1' + style + '$2');

        $(one).attr('src', path);

      })
    }
    
    $('.skin-item').click(function() {
      var color = $(this).data('color');
      replaceAll(color);
    })
    
    function replaceAll(style) {
      $('iframe').each(function(index, one) {
        try {
          replace(one.contentWindow.document, style)
        } catch(e) {
          console.warn('origin cross');
        }
      });
      replace(document, style)
    }
    
   function xtxx(){
	   $.ajax({
			url:ctx+"/sys/sysNotice/getUncheck",
			type:"post",
			async:false,
			success:function(data){
				 $("#zc").html(data);
			}
		});
   }
    
    </script>
</body> 
</html>
