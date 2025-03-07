<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Detalle Del Contrato</span></td>
		</tr>
	</table>
</div>

<ol id="toc">
    <li><a href="#DatosdelContrato">Datos del Contrato</a></li>
	<li><a href="#DatosdelColegiado">Datos del Colegiado</a></li>
	<li><a href="#AplicacionesAsociadas">Aplicaciones Asociadas al Contrato</a></li>
	<li><a href="#UsuariosAsociados">Usuarios Asociados al Colegiado</a></li>
	<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().gestionarUsuariosContrato()}">
	 	<li><a href="#UsuariosAsociadosContrato">Usuarios Asociados al Contrato</a></li> 
	</s:if>
	
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

	
	<s:hidden id="anagramaContrato" name="contratoDto.anagramaContrato"></s:hidden>
	<s:hidden id="idTipoContrato" name="contratoDto.idTipoContrato"></s:hidden>
	<s:hidden id="idContrato" name="contratoDto.idContrato"></s:hidden>
	<s:hidden id="idContratoProvinciaColegiado" name="contratoDto.contratoProvincia"></s:hidden>
	<s:hidden id="idEstadoContrato" name="contratoDto.estadoContrato"></s:hidden>
	<s:hidden id="provinciaColegiado" name="contratoDto.provinciaDto.idProvincia"></s:hidden>
	
	<s:hidden name="contratoDto.colegiadoDto.claveFacturacion"></s:hidden>
	
	<s:hidden id="estadoUsuario" name="contratoDto.colegiadoDto.usuario.estadoUsuario"></s:hidden>
	<s:hidden id="anagramaColegiado" name="contratoDto.colegiadoDto.usuario.anagrama"></s:hidden>
	<s:hidden id="fechaRenovacionDia" name="contratoDto.colegiadoDto.usuario.fechaRenovacion.dia"></s:hidden>
	<s:hidden id="fechaRenovacionMes" name="contratoDto.colegiadoDto.usuario.fechaRenovacion.mes"></s:hidden>
	<s:hidden id="fechaRenovacionAnio" name="contratoDto.colegiadoDto.usuario.fechaRenovacion.anio"></s:hidden>
	<s:hidden id="idUsuario" name="contratoDto.colegiadoDto.usuario.idUsuario"></s:hidden>
	<s:hidden id="fechaNotificacionDia" name="contratoDto.colegiadoDto.usuario.fechaNotificacion.dia"></s:hidden>
	<s:hidden id="fechaNotificacionMes" name="contratoDto.colegiadoDto.usuario.fechaNotificacion.mes"></s:hidden>
	<s:hidden id="fechaNotificacionAnio" name="contratoDto.colegiadoDto.usuario.fechaNotificacion.anio"></s:hidden>
	
	<s:hidden name="contratoDto.colegiadoDto.usuario.jefaturaJPT"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.usuario"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.password"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.fechaAlta.dia"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.fechaAlta.mes"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.fechaAlta.anio"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.fechaFin.dia"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.fechaFin.mes"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.fechaFin.anio"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.ultimaConexion.dia"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.ultimaConexion.mes"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.ultimaConexion.anio"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.ultimaConexion.hora"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.ultimaConexion.minutos"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.usuario.ultimaConexion.segundos"></s:hidden>
	<s:hidden name="contratoDto.colegiadoDto.fechaCaducidad"></s:hidden>
	<s:hidden name="passCorpme"></s:hidden>
	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<%@include file="detalleContratoDatosContrato.jsp" %>
	<%@include file="detalleContratoDatosColegiado.jsp" %>
	<%@include file="detalleContratoAplicaciones.jsp" %>
	<%@include file="detalleContratoUsuarios.jsp" %>
	<%@include file="detalleUsuariosContrato.jsp" %>
	
	
	
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
	
</s:form>