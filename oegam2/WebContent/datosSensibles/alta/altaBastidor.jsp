<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/datosSensibles.js" type="text/javascript"></script>
<script type="text/javascript"></script>
<!-- Se hace referencia a la parte de errores de los mensajes -->
<%@include file="../../includes/erroresMasMensajes.jspf" %>

<!--<div id="contenido" class="contentTabs" style="display: block;">-->
<s:form method="post" id="formData" name="formData">
	<s:hidden id="datosSensiblesBean.grupo" name="datosSensiblesBean.grupo" />
	<s:hidden id="idTipoAgrupacion" name="datosSensiblesBean.tipoAgrupacion" value="Bastidor" />
	<s:hidden key="contrato.idContrato" />
	<s:hidden id="textoLegal" name="propTexto" />
	<s:hidden id="idExisteBastidor" name="existeBastidor" />
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Alta de Bastidor</span>
				</td>
			</tr>
		</table>
	</div>
	<div>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="labelIdAgrupacion">Bastidor: </label>
				</td>
				<td align="left">
					<s:textfield name="datosSensiblesBean.textoAgrupacion" id="idTextoAgrupacion" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="17" maxlength="17" />
				</td>
				<td align="left" style="width:40%;">
					<label for="labelTiempo" >Tiempo de caducidad del dato:</label>
					<img id="info_caducidad_datos_sensibles" src="img/botonDameInfo.gif" alt="Info - Caducidad datos sensibles"
						onmouseover="dameInfoSinAjax('mostrar', this.id, 'caducidadDatosSensibles')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" 
						class="botonesInfo" />
				</td>
				<td align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboBajaDatosSensibles()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="datosSensiblesBean.tiempoDatosSensibles"
							listKey="valorEnum" listValue="nombreEnum"
							id="idTiempo"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoDatosSensible">Tipo de Control: </label>
				</td>
				<td>
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoControlDatoSensible()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
							headerValue="Seleccione Tipo Control"
							name="datosSensiblesBean.tipoControl"
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoControl"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bGuardarDatosSensibles" id="idBotonGuardarDatosSensibles" value="Guardar"
			onClick="return altaNuevoBastidor();" onKeyPress="this.onClick" />
		<input type="button" class="boton" name="bLimpiar" id="idBotonLimpiar" value="Limpiar"
			onkeypress="this.onClick" onclick="return limpiarNuevoBastidor();" />
	</div>
	<div id="bloqueLoadingDatosSensibles" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
</s:form>
<script type="text/javascript">
	cargarMensageExisteBastidor();
</script>