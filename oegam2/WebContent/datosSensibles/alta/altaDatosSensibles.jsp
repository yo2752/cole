<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/datosSensibles.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<!-- Se hace referencia a la parte de errores de los mensajes -->
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

<!--<div id="contenido" class="contentTabs" style="display: block;">-->
<s:form method="post" id="formData" name="formData">
	<s:hidden id="grupoUsuario" name="grupoUsuario"/>
	<s:hidden id="textoLegal" name="propTexto"/>
	<s:hidden key="contrato.idContrato"/>
	<s:hidden id="flagDisabled" name="flagDisabled"/>
	<s:hidden id="idExisteBastidor" name="existeBastidor"/>
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Alta de Datos Sensibles</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="labelIdAgrupacion" >Tipo de Dato: </label>
				</td>
				<td align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboDatosSensibles()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							onchange="deshabilitarTipoControl(this)"
							headerKey="-1"
							headerValue="Seleccione Tipo Dato"
							name="datosSensiblesBean.tipoAgrupacion"
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoAgrupacion"/>
				</td>
				<td align="left">
					<label for="labelIdValor">Valor: </label>
				</td> 
				<td>
					<s:textfield name="datosSensiblesBean.textoAgrupacion"
							id="idTextoAgrupacion"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							size="17" maxlength="17"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
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
				<td align="left">
					<label for="labelTipoDatosSensible">Tipo de Control: </label>
				</td>
				<td align="left">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoControlDatoSensible()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
							headerValue="Seleccione Tipo Control"
							name="datosSensiblesBean.tipoControl"
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoControl"
							disabled="%{flagDisabled}"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label >Grupo: </label>
				</td>
				<td align="left">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboGrupoUsuarios()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
							headerValue="Seleccione Grupo"
							name="datosSensiblesBean.grupo"
							listKey="codigo" listValue="descripcion"
							id="idGrupo"/>
					</s:if>
					<s:else>
						<s:textfield name="descGrupo" id="idDescGrupo" onblur="this.className='input2';" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getDescripcionGrupo(datosSensiblesBean.grupo)}"
							onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"/>
						<s:hidden id="datosSensiblesBean.grupo" name="datosSensiblesBean.grupo"/>
					</s:else>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bGuardarDatosSensibles" id="idBotonGuardarDatosSensibles" value="Guardar"
			onClick="return altaNuevoDatosSensibles();" onKeyPress="this.onClick"/>
		<input type="button" class="boton"  name="bLimpiar" id="idBotonLimpiar" value="Limpiar"  onkeypress="this.onClick"
			onclick="return limpiarNuevoDatosSensibles();"/>
	</div>
	<div id="bloqueLoadingDatosSensibles" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
</s:form>
<script type="text/javascript">
	cargarMensageExisteBastidorDatoSensible();
</script>