var SystemMenu = [];
$.ajax({
	url : ctx + "/sys/sysMenu/getMenus",
	type : 'POST',
	async : false,
	dataType : "json",
	success : function(data) {
		SystemMenu = data;
	}
});

//打开新窗口，title显示标题，dataurl 内容地址 index top菜单位置，从0开始
function openMenu(title,dataUrl,index){
	
   var easytabs=".easyui-tabs1[arrindex="+index+"]";
   
   if (dataUrl == undefined || $.trim(dataUrl).length == 0)
		return false;
   
    if(!$(easytabs).tabs('exists',title)){
	   $(easytabs).tabs('add',{
			title: title,
			content: '<iframe class="page-iframe" src="'+ dataUrl + '" frameborder="no" border="no" height="100%" width="100%" scrolling="auto"></iframe>',
			closable: true
		});
   }else{
	   $(easytabs).tabs('select',title);
   }
	   
}