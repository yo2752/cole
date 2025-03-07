<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/transmision.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>

<s:hidden id="textoLegal" name="propTexto" />
<s:hidden id="permisoEspecial"
	value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}" />

<div class="nav">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="tabular"><span class="titulo">Altas de
					Tramitación de Transmisión</span> <s:set var="flagDisabled"
					value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled"
					value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled"
					value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
				<s:set var="numExpediente"
					value="tramiteTraficoTransmisionBean.tramiteTraficoBean.numExpediente" />
			</td>
		</tr>
	</table>
</div>

<ol id="toc">
	<li><a href="#TramiteTransmision">Trámite</a></li>

	<s:if
		test="%{tramiteTraficoTransmisionBean.tipoTransferencia.valorEnum==5}">
		<li id="pestaniaAdquirienteTransmision" style="display: none"><a
			href="#Adquiriente">Adquiriente</a></li>
	</s:if>
	<s:else>
		<li id="pestaniaAdquirienteTransmision"><a href="#Adquiriente">Adquiriente</a></li>
	</s:else>

	<li><a href="#Transmitente">Transmitente</a></li>
	<!--Mantis  11046 Presentador solo visible para el administrador -->
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		<li><a href="#Presentador">Presentador</a></li>
	</s:if>
	<li><a href="#Vehiculo">Vehículo</a></li>
	<s:if
		test="%{tramiteTraficoTransmisionBean.tipoTransferencia.valorEnum!=4}">
		<li id="pestaniaCompraventaMO"><a href="#Compraventa">Compraventa</a></li>
	</s:if>
	<s:else>
		<li id="pestaniaCompraventaMO" style="display: none"><a
			href="#Compraventa">Compraventa</a></li>
	</s:else>
	<s:if
		test="%{tramiteTraficoTransmisionBean.tramiteTraficoBean.renting.equals('true')}">
		<li id="pestaniaRentingMO"><a href="#Renting">Renting</a></li>
	</s:if>
	<s:else>
		<li id="pestaniaRentingMO" style="display: none"><a
			href="#Renting">Renting</a></li>
	</s:else>
	<li><a href="#Modelo620">620</a></li>
	<li><a href="#PagoImpuestos">Pago Impuestos</a></li>

	<s:if
		test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esFacturableLaTasaTransmision(tramiteTraficoTransmisionBean)}">
		<li><a href="#Facturacion">Facturación</a></li>
	</s:if>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && 
					@trafico.utiles.UtilesVistaTrafico@getInstance().esAcreditacionPagoIvtm(tramiteTraficoTransmisionBean) &&
					@trafico.utiles.UtilesVistaTrafico@getInstance().estaIvtmActivo()}">
		<li id="Documentacion"><a href="#Documentacion">Documentacion Aportada</a></li>
	</s:if>
	<s:if
		test="%{tramiteTraficoTransmisionBean.tramiteTraficoBean.numExpediente!=null}">
		<li><a href="#Resumen">Resumen</a></li>
	</s:if>
</ol>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	<div class="contentTabs" id="TramiteTransmision"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaTramiteTransmisionTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Adquiriente"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaAdquirienteTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Transmitente"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaTransmitenteTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Presentador"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaPresentadorTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Vehiculo"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaVehiculoTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Compraventa"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaCompraventaTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Renting"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaRentingTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="Modelo620"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaModelo620Transmision.jspf"%>
	</div>

	<div class="contentTabs" id="PagoImpuestos"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaPagoImpuestosTransmision.jspf"%>
	</div>

	<div class="contentTabs" id="JustificantesPro"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaJustificantePro.jspf"%>
	</div>

	<div class="contentTabs" id="Facturacion"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="../facturacion/pestaniaFacturacion.jspf"%>
	</div>
	
	<div class="contentTabs" id="Documentacion"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaSubirDocumentacion.jsp"%>
	</div>
	<div class="contentTabs" id="Resumen"
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaResumenTransmision.jspf"%>
	</div>
	<s:hidden id="idHiddenNumeroExpediente"
		name="tramiteTraficoTransmisionBean.tramiteTraficoBean.numExpediente" />
	<s:hidden id="idHiddenHistoricoCheckImpresionPermiso"
		name="tramiteTraficoTransmisionBean.historicoCheckImpresionPermiso" />
	<s:hidden id="nifAdquiriente"
		key="tramiteTraficoTransmisionBean.adquirienteBean.nifInterviniente" />
	<s:hidden id="transmisionElectronica"
		key="tramiteTraficoTransmisionBean.electronica" />
	<s:hidden id="nifTransmitente"
		key="tramiteTraficoTransmisionBean.transmitenteBean.nifInterviniente" />
	<s:hidden id="nifRepresentanteAdquiriente"
		key="tramiteTraficoTransmisionBean.representanteAdquirienteBean.nifInterviniente" />
	<s:hidden id="nifRepresentanteTransmitente"
		key="tramiteTraficoTransmisionBean.representanteTransmitenteBean.nifInterviniente" />
	<s:hidden id="nifConductorHabitual"
		key="tramiteTraficoTransmisionBean.conductorHabitualBean.nifInterviniente" />
	<s:hidden id="nifPrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.nifInterviniente" />
	<s:hidden id="nifRepresentantePrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.nifInterviniente" />
	<s:hidden id="nifSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.nifInterviniente" />
	<s:hidden id="nifRepresentanteSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.nifInterviniente" />
	<s:hidden id="nifPresentador"
		key="tramiteTraficoTransmisionBean.presentadorBean.nifInterviniente" />
	<s:hidden id="nifPoseedor"
		key="tramiteTraficoTransmisionBean.poseedorBean.nifInterviniente" />
	<s:hidden id="nifRepresentantePoseedor"
		key="tramiteTraficoTransmisionBean.representantePoseedorBean.nifInterviniente" />
	<s:hidden id="nifArrendatario"
		key="tramiteTraficoTransmisionBean.arrendatarioBean.nifInterviniente" />
	<s:hidden id="nifRepresentanteArrendatario"
		key="tramiteTraficoTransmisionBean.representanteArrendatarioBean.nifInterviniente" />

	<s:hidden id="idDireccionAdquiriente"
		key="tramiteTraficoTransmisionBean.adquirienteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionTransmitente"
		key="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionRepresentanteAdquiriente"
		key="tramiteTraficoTransmisionBean.representanteAdquirienteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionRepresentanteTransmitente"
		key="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionConductorHabitual"
		key="tramiteTraficoTransmisionBean.conductorHabitualBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionPrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionRepresentantePrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionRepresentanteSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionPresentador"
		key="tramiteTraficoTransmisionBean.presentadorBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionArrendatario"
		key="tramiteTraficoTransmisionBean.arrendatarioBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionPoseedor"
		key="tramiteTraficoTransmisionBean.poseedorBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionRepresentanteArrendatario"
		key="tramiteTraficoTransmisionBean.representanteArrendatarioBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionRepresentantePoseedor"
		key="tramiteTraficoTransmisionBean.representantePoseedorBean.persona.direccion.idDireccion" />
	<s:hidden id="idDireccionVehiculo"
		key="tramiteTraficoTransmisionBean.tramiteTraficoBean.vehiculo.direccionBean.idDireccion" />
	<s:hidden id="idVehiculo"
		key="tramiteTraficoTransmisionBean.tramiteTraficoBean.vehiculo.idVehiculo" />
	<s:hidden
		key="tramiteTraficoTransmisionBean.adquirienteBean.numColegiado" />
	<s:hidden
		key="tramiteTraficoTransmisionBean.transmitenteBean.numColegiado" />
	<s:hidden id="idNumColegiado"
		key="tramiteTraficoTransmisionBean.tramiteTraficoBean.numColegiado" />

	<s:hidden id="numColegiadoRepresentanteAdquiriente"
		key="tramiteTraficoTransmisionBean.representanteAdquirienteBean.numColegiado" />
	<s:hidden id="numColegiadoRepresentanteTransmitente"
		key="tramiteTraficoTransmisionBean.representanteTransmitenteBean.numColegiado" />
	<s:hidden id="numColegiadoConductorHabitual"
		key="tramiteTraficoTransmisionBean.conductorHabitualBean.numColegiado" />
	<s:hidden id="numColegiadoPrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.numColegiado" />
	<s:hidden id="numColegiadoRepresentantePrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.numColegiado" />
	<s:hidden id="numColegiadoSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.numColegiado" />
	<s:hidden id="numColegiadoRepresentanteSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.numColegiado" />
	<s:hidden id="numColegiadoPresentador"
		key="tramiteTraficoTransmisionBean.presentadorBean.numColegiado" />
	<s:hidden id="numColegiadoPoseedor"
		key="tramiteTraficoTransmisionBean.poseedorBean.numColegiado" />
	<s:hidden id="numColegiadoRepresentantePoseedor"
		key="tramiteTraficoTransmisionBean.representantePoseedorBean.numColegiado" />
	<s:hidden id="numColegiadoArrendatario"
		key="tramiteTraficoTransmisionBean.arrendatarioBean.numColegiado" />
	<s:hidden id="numColegiadoRepresentanteArrendatario"
		key="tramiteTraficoTransmisionBean.representanteArrendatarioBean.numColegiado" />

	<s:hidden id="idContratoRepresentanteAdquiriente"
		key="tramiteTraficoTransmisionBean.representanteAdquirienteBean.idContrato" />
	<s:hidden id="idContratoRepresentanteTransmitente"
		key="tramiteTraficoTransmisionBean.representanteTransmitenteBean.idContrato" />
	<s:hidden id="idContratoConductorHabitual"
		key="tramiteTraficoTransmisionBean.conductorHabitualBean.idContrato" />
	<s:hidden id="idContratoPrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.idContrato" />
	<s:hidden id="idContratoRepresentantePrimerCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.idContrato" />
	<s:hidden id="idContratoSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.idContrato" />
	<s:hidden id="idContratoRepresentanteSegundoCotitularTransmitente"
		key="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.idContrato" />
	<s:hidden id="idContratoPresentador"
		key="tramiteTraficoTransmisionBean.presentadorBean.idContrato" />
	<s:hidden id="idContratoPoseedor"
		key="tramiteTraficoTransmisionBean.poseedorBean.idContrato" />
	<s:hidden id="idContratoRepresentantePoseedor"
		key="tramiteTraficoTransmisionBean.representantePoseedorBean.idContrato" />
	<s:hidden id="idContratoArrendatario"
		key="tramiteTraficoTransmisionBean.arrendatarioBean.idContrato" />
	<s:hidden id="idContratoRepresentanteArrendatario"
		key="tramiteTraficoTransmisionBean.representanteArrendatarioBean.idContrato" />

	<s:hidden id="idContratoTramite"
		name="tramiteTraficoTransmisionBean.tramiteTraficoBean.idContrato" />
	<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
	<s:hidden id="respuesta"
		name="tramiteTraficoTransmisionBean.tramiteTraficoBean.respuesta"></s:hidden>
	<s:hidden id="resCheckCtit"
		name="tramiteTraficoTransmisionBean.resCheckCtit"></s:hidden>
			<s:hidden id="acreditPago"
		name="tramiteTraficoTransmisionBean.acreditacionPago"></s:hidden>
	<s:hidden id="idHiddenEstado"
		name="tramiteTraficoTransmisionBean.tramiteTraficoBean.estado" />
	<s:hidden id="nre06" name="tramiteTraficoTransmisionBean.nre06"/>
	<s:hidden id="fechaRegistro" name="tramiteTraficoTransmisionBean.fechaRegistroNRE"/>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
	<div style="clear: both;"></div>
</s:form>
<div id="divEmergenteJustfProfTrans"
	style="display: none; background: #f4f4f4;"></div>
