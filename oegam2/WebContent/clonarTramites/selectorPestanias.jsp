<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="css/estilos.css" />
<link rel="stylesheet" type="text/css" href="css/global.css" />
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramiteTrafico.js" type="text/javascript"></script>

<s:form method="post" id="formData" name="formData">
	<s:hidden name="clonarTramitesDto.numExpediente"/>
	<s:hidden name="clonarTramitesDto.tipoTramite"/>
	<s:hidden key="clonarTramitesDto.estado"/>
	<s:hidden key="clonarTramitesDto.bastidor"/>
	<div id="contenido" class="contentTabs" style="display: block;">	
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Tr&aacute;mites seleccionados</td>
			</tr>
		</table>
		<script type="text/javascript">
			$(function() {
				$("#displayTagDiv").displayTagAjax();
			})
		</script>
		<div id="displayTagDiv" style="width:104%">
			<display:table name="lista" 
				requestURI="navegar${action}" class="tablacoin" excludedParams="*"
				uid="tablaConsultaTramite" summary="Listado de Tramites"
				cellspacing="0" cellpadding="0" decorator="${decorator}">
				
				<display:column property="numExpediente" title="Num Expediente" defaultorder="descending" style="width:4%;" paramId="numExpediente" />
				<display:column property="bastidor" title="Bastidor" defaultorder="descending" style="width:4%;"/>
				<display:column property="tipoTramite" title="Tramite" defaultorder="descending" maxLength="15" style="width:4%" />
				<display:column property="estado" title="Estado" defaultorder="descending" style="width:4%" />
			</display:table>
		</div>
		<div class="contenido"  style="margin-left: 1em;">
			<s:if test="hasActionErrors() || hasActionMessages()">
				<table class="subtitulo" cellSpacing="0" cellspacing="0">
					<tr>
						<td>Resultado:</td>
					</tr>
				</table>
			</s:if>
			<%@include file="../../includes/erroresMasMensajes.jspf" %>	
			<s:if test="clonarTramitesDto.tipoTramite.equals('T1')"> 
				<%@include file="clonarMatriculacion.jspf" %>
			</s:if>
		</div>
		<table class="acciones">
			<tr>
				<td align="center">
					<input id="bClonar" type="submit" value="Clonar" class="boton" onClick="return clonarTramite();" />
					<input id="bLimpiar" type="button" value="Limpiar Campos" class="boton" onClick="return limpiarCampos();" />           
				</td>
			</tr>
		</table>
	</div>
</s:form>
<script type="text/javascript">
function marcarAnularTodo(){
	var marcado = document.getElementById('checkTodo').checked;
	if(document.getElementById('checkTitular') != null)
		document.getElementById('checkTitular').checked = marcado;

	if(document.getElementById('checkVehiculo') != null)	
	document.getElementById('checkVehiculo').checked = marcado;

	if(document.getElementById('checkModelo576') != null)
	document.getElementById('checkModelo576').checked = marcado;

	if(document.getElementById('checkPagoPresentacion') != null)
		document.getElementById('checkPagoPresentacion').checked = marcado;

	if(document.getElementById('checkLiberarEEFF') != null)
	document.getElementById('checkLiberarEEFF').checked = marcado;

	if(document.getElementById('checkRenting') != null)
		document.getElementById('checkRenting').checked = marcado;

	if(document.getElementById('checkCondHabitual') != null)
		document.getElementById('checkCondHabitual').checked = marcado;

	if(document.getElementById('checkRefPropia') != null)
		document.getElementById('checkRefPropia').checked = marcado;	
}

function limpiarCampos(){
	document.getElementById('checkTodo').checked = false;

	if(document.getElementById('checkTitular') != null)
		document.getElementById('checkTitular').checked = false;

	if(document.getElementById('checkVehiculo') != null)	
	document.getElementById('checkVehiculo').checked = false;

	if(document.getElementById('checkModelo576') != null)
	document.getElementById('checkModelo576').checked = false;

	if(document.getElementById('checkPagoPresentacion') != null)
		document.getElementById('checkPagoPresentacion').checked = false;

	if(document.getElementById('checkLiberarEEFF') != null)
	document.getElementById('checkLiberarEEFF').checked = false;

	if(document.getElementById('checkRenting') != null)
		document.getElementById('checkRenting').checked = false;

	if(document.getElementById('checkCondHabitual') != null)
	document.getElementById('checkCondHabitual').checked = false;

	if(document.getElementById('checkRefPropia') != null)
		document.getElementById('checkRefPropia').checked = false;
}

function clonarTramite(){
	var marcadoTodo = document.getElementById('checkTodo').checked;
	var mensaje  = null;
	var error = false;
	var existeCheck = false;

	if(marcadoTodo){
		existeCheck = true;
	}else{
		if(document.getElementById('checkTitular') != null && document.getElementById('checkTitular').checked){
			existeCheck = true;
		}else if(document.getElementById('checkVehiculo') != null && document.getElementById('checkVehiculo').checked){
			existeCheck = true;
		}else if(document.getElementById('checkModelo576') != null && document.getElementById('checkModelo576').checked){
			existeCheck = true;
		}else if(document.getElementById('checkPagoPresentacion') != null && document.getElementById('checkPagoPresentacion').checked){
			existeCheck = true;
		}else if(document.getElementById('checkLiberarEEFF') != null && document.getElementById('checkLiberarEEFF').checked){
			existeCheck = true;
		}else if(document.getElementById('checkRenting') != null && document.getElementById('checkRenting').checked){
			existeCheck = true;
		}else if(document.getElementById('checkCondHabitual') != null && document.getElementById('checkCondHabitual').checked){
			existeCheck = true;
		}else if(document.getElementById('checkRefPropia') != null && document.getElementById('checkRefPropia').checked){
			existeCheck = true;
		}
	}
	if(existeCheck){
		if(!error){
			document.formData.action = "clonarClonarTramites.action";
			document.formData.submit();
		}else{
			alert(mensaje);
		}
	}else{
		mensaje="Debe seleccionar algún campo para Clonar.";
		alert(mensaje);
	}
}

function activarCifras(valor){
	document.getElementById('idDigitosBastidor').disabled = !(document.getElementById('idDigitosBastidor').disabled);
	document.getElementById('idDigitosBastidor').value = "0";
}

</script>

