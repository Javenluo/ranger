<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="字典类型"%>
<%@ attribute name="filter" type="java.lang.String" required="false" description="下拉选中监听"%>
<%@ attribute name="children" type="java.lang.String" required="false" description="级联ID"%>

<select id="${id}" name="${id}" type="${type}" lay-filter="${filter}" children="${children}">
</select>

<script>
	layui.use([ 'form', 'jquery' ], function() {
		var form = layui.form;
		var $ = layui.jquery;
		var $form = $('form');

		var id = '${id}';
		var filter = '${filter}';
		var children = '${children}';
		if (filter != null && filter != '') {
			form.on('select(${filter})', function(data) {
				queryChildren(data.value);
				form.render("select");
			});
		}
		
		dictList();
		//按类型查询
		function dictList() {
			var html = '';
			$.post(ctx + "/sys/sysDict/dictList", {type : '${type}'}, function(data) {
				$("#" + id).empty();
				$.each(data, function(n, eitm) {
					html += '<option value="'+eitm.value+'">' + eitm.label + '</option>';
				});
				$form.find('select[name=' + id + ']').append(html);
				form.render();
			});
		}
		
		//按类型查询
		function queryChildren(value) {
			$("#" + children).empty();
			var html = '<option value=" ">--请选择--</option>';
			if(value != null && $.trim(value) !=''){
				$.post(ctx + "/sys/sysDict/dictList", {type : value}, function(data) {
					$.each(data, function(n, eitm) {
						html += '<option value="'+eitm.value+'">' + eitm.label + '</option>';
					});
					$form.find('select[name=' + children + ']').append(html);
					form.render();
				});
			}
		}
	});
</script>
