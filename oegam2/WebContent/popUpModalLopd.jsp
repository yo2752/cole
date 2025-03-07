<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="opcMenu" value="%{null}"/>
<div>
	<s:form method="post" id="formData" name="formData" action="firmarLopdRegistrar.action">
		<div id="dialog" style="display:none" title="Documentacion LOPD"></div> 
	</s:form>
</div> 
<script language="javascript" type="text/javascript">
	$(document).ready(function() {
		$("#dialog").dialog({
			modal: true,
			show : {
				effect : "blind"
			},
			dialogClass : 'no-close',
			width: 900,
			height: 800,
			open: function(event, ui) {
				$(this).parent().find(".ui-dialog-titlebar-close").remove();
				var object = "<object data=\"http://"+ jQuery(location).attr('host')+"/oegam2/cargarLopd\" type=\"application/pdf\" width=\"750px\" height=\"650px\">";
				$("#dialog").html(object);
			},
			buttons: {
				Firmar: function(){
					$("#formData").submit();
					$(this).parent().find("boton ui-state-hover ui-state-focus").css("color","#BDBDBD");
					$(this).parent().find("boton ui-state-hover ui-state-focus").prop("disabled",true);
					$(this).parent().find("boton ui-state-hover ui-state-focus").val("Firmando.....");
				}
			}
		});
	});
</script>