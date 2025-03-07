<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/licenciasCam/licencias.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Alta Solicitud Licencia</span>
			</td>
		</tr>
	</table>
</div>
<ol id="toc">
	<li id="Licencia"><a href="#Licencia">Licencia</a></li>
	<li id="IntervinienteLC"><a href="#IntervinienteLC">Interesado Principal</a></li>
	<li id="PresentadorLC"><a href="#PresentadorLC">Presentador</a></li>
	<li id="NotificacionLC"><a href="#NotificacionLC">Notificación</a></li>
	<li id="DireccionLC"><a href="#DireccionLC">Dirección Licencia</a></li>
	<li id="DatosActuacionLC"><a href="#DatosActuacionLC">Datos Actuación</a></li>
	<li id="DatosObraLC"><a href="#DatosObraLC">Datos Obra</a></li>
	<li id="AltaEdificacionLC"><a href="#AltaEdificacionLC">Alta Edificación</a></li>
	<li id="BajaEdificacionLC"><a href="#BajaEdificacionLC">Baja Edificación</a></li>
	<li id="DatosSuministros"><a href="#DatosSuministros">Datos Suministros</a></li>
	<li id="InformacionLocal"><a href="#InformacionLocal">Local Licencia</a></li>
	<li id="Documentacion"><a href="#Documentacion">Documentación aportada</a></li>
	<s:if test="lcTramiteDto.numExpediente!=null">
		<li id="Resumen"><a href="#Resumen">Resumen</a></li>
	</s:if>	
</ol>
<%@include file="../../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<s:hidden id="numColegiadoLic" name="lcTramiteDto.numColegiado"/>
	<s:hidden name="lcTramiteDto.idContrato"/>
	<s:hidden name="lcTramiteDto.estado" id="idEstado"/>
	<s:hidden name="lcTramiteDto.fechaAlta.dia"/>
	<s:hidden name="lcTramiteDto.fechaAlta.mes"/>
	<s:hidden name="lcTramiteDto.fechaAlta.anio"/>
	<s:hidden name="lcTramiteDto.fechaAlta.hora"/>
	<s:hidden name="lcTramiteDto.fechaAlta.minutos"/>
	<s:hidden name="lcTramiteDto.fechaAlta.segundos"/>
	<s:hidden id="estadoCerrarPopUp" name="estadoCerrarPopUp"/>
	
	<div class="contentTabs" id="Licencia" style="width: 760px; boarder: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaLicencia.jsp" %>
	</div>
	<div class="contentTabs" id="IntervinienteLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaIntervinienteLC.jsp" %>
	</div>
	<div class="contentTabs" id="PresentadorLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaPresentadorLC.jsp" %>
	</div>
	<div class="contentTabs" id="NotificacionLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaNotificacionLC.jsp" %>
	</div>
	<div class="contentTabs" id="DireccionLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaDireccionLC.jsp" %>
	</div>
	<div class="contentTabs" id="DatosActuacionLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaDatosActuacionLC.jsp" %>
	</div>
	<div class="contentTabs" id="DatosObraLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="pestaniaDatosObraLC.jsp" %>
	</div>
	<div class="contentTabs" id="AltaEdificacionLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaEdificacionAltaLC.jsp" %>
	</div>
	<div class="contentTabs" id="BajaEdificacionLC" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaEdificacionBajaLC.jsp" %>
	</div>
	<div class="contentTabs" id="DatosSuministros" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaDatosSuministros.jsp" %>
	</div>
	<div class="contentTabs" id="InformacionLocal" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaInformacionLC.jsp" %>
	</div>
	<div class="contentTabs" id="Documentacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="listaFicheros.jsp" %>
	</div>
	 <s:if test="lcTramiteDto.numExpediente!=null">
		<div class="contentTabs" id="Resumen" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumenTramiteLC.jsp" %>
		</div>
	</s:if>
	
	&nbsp;
	
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
				<input type="button" class="boton" name="bGuardar" id="idGuardar" value="Guardar" onClick="javascript:guardarSolicitudLC();" onKeyPress="this.onClick"/>	
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esValidable(lcTramiteDto)}">
				<input class="boton" type="button" id="bValidarLC" name="bValidarLC" value="Validar" onClick="javascript:validarAltaSolicitudLicencia();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esComprobable(lcTramiteDto)}">
				<input class="boton" type="button" id="bComprobarLC" name="bComprobarLC" value="Comprobar" onClick="javascript:comprobar();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEnviable(lcTramiteDto)}">
				<input class="boton" type="button" id="bEnviarLC" name="bEnviarLC" value="Enviar" onClick="javascript:enviar();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esConsultable(lcTramiteDto)}">
				<input class="boton" type="button" id="bConsultarLC" name="bConsultarLC" value="Consultar" onClick="javascript:consultar();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esPresentable(lcTramiteDto)}">
				<input class="boton" type="button" id="bPresentarLC" name="bPresentarLC" value="Presentar" onClick="javascript:presentar();" onKeyPress="this.onClick"/>
			</s:if>
		</div>
	
		<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div> 
</s:form>


<script type="text/javascript">
	var estadoCerrarPopUp = document.getElementById("estadoCerrarPopUp").value
	if (estadoCerrarPopUp == "1") {
		$("#divEmergentePopUp").dialog("close");
	}
</script>
