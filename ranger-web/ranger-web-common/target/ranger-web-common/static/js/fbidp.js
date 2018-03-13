$(document).ready(function () {
	$(document).ready(function () {
		$(document).ajaxStart(function(){
			layer.load(0,{shade: [0.3, '#393D49'] });//0.1透明度的白色背景
		}).ajaxStop(function(){
			layer.closeAll('loading');
		}).ajaxError(function(){
			layer.alert("系统错误，请联系系统管理员！", { icon: 2});
		}).ajaxComplete(function(event,xhr,options){
			console.log(xhr);
			var sessionStatus = xhr.getResponseHeader('sessionstatus');  
	        if(sessionStatus == 'timeout') {  
	            window.location.href = ctx+'/login';  
	        }
		});
	});
});

/**
 * 工具方法
 */
$.fn.serializeObject = function(){
	var o= {};
	var a = this.serializeArray();
	$.each(a, function(){
		if(o[this.name]){
			if(!o[this.name].push){
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		}else{
			o[this.name] = this.value || '';
		}
	
	});
	return o;
};