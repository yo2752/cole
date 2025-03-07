 
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/576.js" type="text/javascript"></script>
<script src="js/pagosEImpuestos.js" type="text/javascript"></script>
<script src="js/trafico/eeff/eeff.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>

<s:hidden id="textoLegal" name="propTexto"/>
<s:hidden id="permisoEspecial" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"/>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Altas de Tramitación de Matriculación
			</span>
			<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
			<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
			<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
			<s:set var="liberacionRealizada" value="traficoTramiteMatriculacionBean.eeffLiberacionDTO.realizado" />
			<s:set var="esLiberableEEFF" value="%{@trafico.utiles.UtilesVistaTrafico@esLiberableEEFF(traficoTramiteMatriculacionBean.tramiteTraficoBean, traficoTramiteMatriculacionBean.eeffLiberacionDTO)}"/>
			<s:set var="numExpediente" value="traficoTramiteMatriculacionBean.tramiteTraficoBean.numExpediente"/>
			</td>
		</tr>
	</table>
</div>


<!-- Pestañas -->

<ol id="toc">	    
	    <li><a href="#Tramite">Trámite</a></li>    
	    <li><a href="#Titular">Titular </a></li>
	    <li id="pestaniaConductorHabitual" style="display:none"><a href="#ConductorHabitual">Conductor Habitual</a></li>
	     <s:if test="%{traficoTramiteMatriculacionBean.tramiteTraficoBean.renting.equals('true')}">
	    	<li id="pestaniaRentingMO"><a href="#Renting">Renting</a></li>
	    </s:if>
	    <s:else>
	    	<li id="pestaniaRentingMO" style="display:none"><a href="#Renting">Renting</a></li>
	    </s:else>	    
	    <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
	    	<li><a href="#Presentador">Presentador</a></li>
	    </s:if>
	    <li><a href="#Vehiculo">Vehículo</a></li>	    
	    <li><a href="#576">576,05 y 06</a></li>	  
	    <li onclick="recargarDireccionIVTM();checkIvtm();"><a href="#PagosEImpuestos">Pago/Presentación </a></li>
	    <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoLiberarEEFF()}">
	    	<li id="pestaniaEnlaceLiberacionEEFF" <s:if test="%{traficoTramiteMatriculacionBean.tramiteTraficoBean.vehiculo.nive==null||traficoTramiteMatriculacionBean.tramiteTraficoBean.vehiculo.nive.equals('')}">style="display:none;"</s:if> <s:if test="!#liberacionRealizada"> onclick="actualizarCamposEEFFLiberacion();" </s:if> ><a href="#LiberarEEFF">LiberarEEFF</a></li>
	    </s:if>
	    <s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esFacturableLaTasaMatriculacionMATW(traficoTramiteMatriculacionBean)}">
	    	<li><a href="#Facturacion">Facturación</a></li>
	    </s:if>
	    <s:if test="%{traficoTramiteMatriculacionBean.tramiteTraficoBean.numExpediente!=null}">
	    	<li><a href="#Resumen">Resumen</a></li>  
	    </s:if>
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">


<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
<s:hidden id="idHiddenNumeroExpediente" name="traficoTramiteMatriculacionBean.tramiteTraficoBean.numExpediente"/>
<s:hidden id="idHiddenEstado" name="traficoTramiteMatriculacionBean.tramiteTraficoBean.estado"/>
<s:hidden id="idHiddenEstadoIvtm" name="traficoTramiteMatriculacionBean.estadoIvtm"/>
<s:hidden id="idHiddenMensajeIvtm" name="traficoTramiteMatriculacionBean.mensajeIvtm"/>
<s:hidden id="idHiddenDireccionVehiculo" name = "traficoTramiteMatriculacionBean.tramiteTraficoBean.vehiculo.direccionBean.idDireccion"/>
<s:hidden id="idHiddenDireccionTitular" name = "traficoTramiteMatriculacionBean.titularBean.persona.direccion.idDireccion"/>
<s:hidden id="idHiddenDireccionRepresentante" name = "traficoTramiteMatriculacionBean.representanteTitularBean.persona.direccion.idDireccion"/>
<s:hidden id="idHiddenDireccionArrendatario" name = "traficoTramiteMatriculacionBean.arrendatarioBean.persona.direccion.idDireccion"/>

<s:hidden id="idVehiculo" name = "traficoTramiteMatriculacionBean.tramiteTraficoBean.vehiculo.idVehiculo"/>
<s:hidden id="idTipoLimitacionDisposicionIEDTM" name = "traficoTramiteMatriculacionBean.TipoLimitacionDisposicionIEDTM"/>

<s:hidden id="numColegiadoVehiculo" name = "traficoTramiteMatriculacionBean.tramiteTraficoBean.vehiculo.numColegiado"/>
<s:hidden id="numColegiadoTitular" name = "traficoTramiteMatriculacionBean.titularBean.numColegiado"/>
<s:hidden id="numColegiadoConductorHabitual" name = "traficoTramiteMatriculacionBean.conductorHabitualBean.numColegiado"/>
<s:hidden id="numColegiadoRepresentanteTitular" name = "traficoTramiteMatriculacionBean.representanteTitularBean.numColegiado"/>
<s:hidden id="numColegiadoArrendatario" name = "traficoTramiteMatriculacionBean.arrendatarioBean.numColegiado"/>
<s:hidden id="idContratoTramite" name="traficoTramiteMatriculacionBean.tramiteTraficoBean.idContrato"/>

<s:hidden id="idContratoTitular" name = "traficoTramiteMatriculacionBean.titularBean.idContrato"/>
<s:hidden id="idContratoConductorHabitual" name = "traficoTramiteMatriculacionBean.conductorHabitualBean.idContrato"/>
<s:hidden id="idContratoRepresentanteTitular" name = "traficoTramiteMatriculacionBean.representanteTitularBean.idContrato"/>

<s:hidden id="flagDisabledHidden" name="flagDisabledHidden" value="%{flagDisabled}"/>

<%@include file="../../includes/erroresMasMensajes.jspf" %>
<%@include file="pestaniaTramiteMatriculacion.jspf" %>
<%@include file="pestaniaTitularMatriculacion.jspf" %>
<%@include file="pestaniaConductorHabitualMatriculacion.jspf" %>
<%@include file="pestaniaVehiculoMatriculacion.jspf" %>
<%@include file="pestaniaRentingMatriculacion.jspf" %>
<%@include file="pestania576Matriculacion.jspf" %>
<%@include file="pestaniaPagosEImpuestosMatriculacion.jspf" %>


<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoLiberarEEFF()}">
	<%@include file="../matriculacion/pestaniaLiberarEEFF.jspf" %>
</s:if>

<div class="contentTabs" id="Facturacion" 
	style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
	<%@include file="../facturacion/pestaniaFacturacion.jspf" %>
</div>

<%@include file="pestaniaResumen.jspf" %>
<%@include file="pestaniaPresentadorMatriculacion.jspf" %>

<s:hidden key="contrato.idContrato"/>

<table class="acciones">
	<tr>
		<td>
			<img id="loadingImage" style="display:none; margin-left:auto; margin-right:auto; text-align:center;" src="img/loading.gif">
		</td>
	</tr>
</table>


<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>


</s:form>

<!-- Mantis 7841 -->
<s:if test="%{(traficoTramiteMatriculacionBean.conductorHabitualBean.persona.nif!=null)&&(traficoTramiteMatriculacionBean.conductorHabitualBean.persona.nif!='')}">
		<script type="text/javascript">marcarConductorHabitual();</script>
</s:if>
<script type="text/javascript">mostrarOcultarPestaniaConductorHabitual(); eliminarAsteriscosObligatorios(); exentoItV();</script>

<!-- Fin Mantis 7841 -->
<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>

