<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 600</td>
		</tr>
	</table>
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<div id="divListSujetoPasivo">
		<jsp:include page="../personas/listSujetoPasivo.jsp" flush="false"></jsp:include>
	</div>
	<br/>
	<div id="bloqueBotonesListSujeto" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;" align="center">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
	<div id="botonesListaSujPasivo">
		<div class="acciones center">
			<input type="button" class="boton" name="bModificarSujPasivo" id="idModificarSujPasivo" value="Modificar" onClick="javascript:modificarSujPasivo();"onKeyPress="this.onClick"/>	
			<input type="button" class="boton" name="bEliminarSujPasivo" id="idEliminarSujPasivo" value="Eliminar" onClick="javascript:eliminarSujetosPasivos();"onKeyPress="this.onClick"/>
		</div>
	</div>
	<div id="divSujetoPasivo">
		<jsp:include page="../personas/sujetoPasivo.jsp" flush="false"></jsp:include>
	</div>
	<br/>
	<div id="bloqueBotonesSujeto" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;" align="center">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
    <div class="acciones center">
		<input type="button" class="boton" name="bLimpiarSujPasivo" id="idLimpiarSujPasivo" value="Limpiar" onClick="javascript:limpiarSujetoPasivo();"onKeyPress="this.onClick"/>
	</div>
	<br/>
</div>
<script type="text/javascript">
	datosInicialesSujpasivo();
</script>