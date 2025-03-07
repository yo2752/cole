<%@page import="org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<div>
	<s:form method="post" id="formData" name="formData" action="#session['lastAction'].action">
		<s:hidden name="idCircular"/>
    	<div id="dialog" > 
    		<center><h1>Circular OEGAM</h1></center>
			 <s:textarea label="Circular" value="%{#session['textoCircular']}" cols="108" rows="37" 
			 style="font-size:11px; font-type:Calibri;border:0px;resize: none;" readonly="true" />
    	</div> 
	</s:form>
	
</div> 
<style>
textarea {
  border-style: none;
  border-color: Transparent;
  overflow: auto;
  outline: none;
}
</style>
<script language="javascript" type="text/javascript">
  $(document).ready(function() {
	  $("#dialog").dialog({
		  open:function () {
			  $(".ui-dialog-titlebar").hide();
			}, 
		modal: true,
		width: 710,
		height: 595,
		buttons : {
			Aceptar : function(){
				$("#formData").submit();
			}
		}
	  });
  });             
 
  
</script>
