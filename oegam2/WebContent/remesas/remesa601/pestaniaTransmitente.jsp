<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 601</td>
		</tr>
	</table>
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<div id="bloqueBotonesListTransmitente" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;" align="center">
	</div>
	<div id="divListTransmitente">
		<jsp:include page="../personas/listTransmitente.jsp" flush="false"></jsp:include>
	</div>
	<br/>
	<div id="botonesListaTransmitente">
		<div class="acciones center">
			<input type="button" class="boton" name="bModificarTransmitente" id="idModificarTransmitente" value="Modificar" onClick="javascript:modificarTransmitente();"onKeyPress="this.onClick"/>	
			<input type="button" class="boton" name="bEliminarSujPasivo" id="idEliminarTransmitente" value="Eliminar" onClick="javascript:eliminarTransmitente();"onKeyPress="this.onClick"/>
		</div>
	</div>
	<div id="divTransmitente">
		<jsp:include page="../personas/transmitente.jsp" flush="false"></jsp:include>
	</div>
	<br/>
     <div class="acciones center">
		<input type="button" class="boton" name="bLimpiarTransmitente" id="idLimpiarTransmitente" value="Limpiar" onClick="javascript:limpiarTransmitente();"onKeyPress="this.onClick"/>
	</div>
	<br/>
</div>
<script type="text/javascript">
	datosInicialesTransmitente();
</script>