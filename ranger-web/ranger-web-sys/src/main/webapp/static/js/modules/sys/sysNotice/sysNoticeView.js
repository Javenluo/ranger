var form;
layui.use(['form','jquery'], function(){
	var temp=$("#noticeType").val();
	if(temp=="0"){
		$("#noticeType").val("系统公告");
	}else if(temp=="1"){
		$("#noticeType").val("系统通知");
	}else if(temp=="2"){
		$("#noticeType").val("消息");
	}
	
});


	 
