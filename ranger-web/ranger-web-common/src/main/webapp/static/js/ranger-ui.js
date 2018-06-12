$.extend($.fn.menu.defaults, {    
	zIndex:99999999
});  

/**
 * 扩展datagrid组件的默认参数值
 */
$.extend($.fn.treegrid.defaults, {
	rownumbers:true,
	striped:true,
	scrollbarSize:10,
	loadMsg:false
});
/**
 * 扩展datagrid组件的默认参数值
 */
$.extend($.fn.datagrid.defaults, {
	singleSelect:true,
	rownumbers:true,
	striped:true,
	resizable:false,
	fitColumns:false,
	autoSizeColumn:false,
	autoRowHeight:false,
	pagination:true,
	height:332,
	emptyMsg:'没有找到任何数据',
	loadMsg:false,
	scrollbarSize:10,
	remoteSort:false,
	multiSort:false,	
	onLoadSuccess :function(){
		var $id = $(this).attr("id");
		var p = $("#"+$id).datagrid("getPager");
		$(p).pagination({
			showRefresh : true,
			displayMsg : "显示{from}到{to}，共{total}条",
			layout : [ 'list', 'sep', 'first', 'prev', 'links','next', 'last', 'refresh','sep' ]
		});
	},
	onHeaderContextMenu : function(e, field) {
		var $id = $(this).attr("id");
		e.preventDefault();
		
		var cmenu = $('<div/>').appendTo('body');
		cmenu.menu({
			onClick : function(item) {
				if (item.iconCls == 'icon-ok') {
					$('#'+$id).datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', { target : item.target, iconCls : 'icon-empty' });
				} else {
					$('#'+$id).datagrid('showColumn', item.name);
					cmenu.menu('setIcon', { target : item.target, iconCls : 'icon-ok' });
				}
			}
		});
		
		var fields = $('#'+$id).datagrid('getColumnFields');
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var col = $('#'+$id).datagrid('getColumnOption', field);
			if(col.hidden){
				cmenu.menu('appendItem', { text : col.title, name : field, iconCls : 'icon-empty' });
			}else{
				cmenu.menu('appendItem', { text : col.title, name : field, iconCls : 'icon-ok' });
			}
		}
		
		cmenu.menu('show', { left : e.pageX, top : e.pageY });
	}
});

var ranger = {
	utils : {
		alertWarn : function(msg, f ){
			if(f!=null && "function" == typeof f){
				layer.alert(msg,{icon:0},f);
			}else{
				layer.alert(msg,{icon:0});
			}
		},
		alertError : function(msg, f){
			if(f!=null && "function" == typeof f){
				layer.alert(msg,{icon:2},f);
			}else{
				layer.alert(msg,{icon:2});
			}
		},
		alertInfo : function(msg, f){
			if(f!=null && "function" == typeof f){
				layer.alert(msg,{icon:1},f);
			}else{
				layer.alert(msg,{icon:1});
			}
		},
		confirm : function(msg, f){
			if(f!=null && "function" == typeof f){
				layer.confirm(msg,{icon:3},f)
			}else{
				
			}
		},
		close: function(index){
			if(index != null){
				layer.close(index);
			}else{
				layer.closeAll();
			}
		}
	}
}

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
