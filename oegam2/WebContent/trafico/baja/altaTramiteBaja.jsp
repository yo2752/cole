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
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>

<s:hidden id="permisoEspecial" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"/>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Altas de Tramitación de Bajas</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
				<s:set var="numExpediente" value="tramiteTraficoBaja.numExpediente"/>
			</td>
		</tr>
	</table>
</div>

<div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<ol id="toc">
		<li id="Baja"><a href="#Baja">Baja</a></li>
		<li id="Vehiculo"><a href="#Vehiculo">Vehículo</a></li>
		<li id="Titular"><a href="#Titular">Titular</a></li>
		<li id="Adquiriente"><a href="#Adquiriente">Adquiriente / Acredite Propiedad</a></li>
		<li id="PagoPresentacion"><a href="#PagoPresentacion">Pago/Presentación</a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esFacturableLaTasaBaja(tramiteTraficoBaja)}">
			<li id="Facturacion"><a href="#Facturacion">Facturación</a></li>
		</s:if>
		<s:if test="%{tramiteTraficoBaja.numExpediente!=null}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<s:form method="post" id="formData" name="formData">
		<div class="contentTabs" id="Baja"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTramiteBaja.jsp" %>
		</div>

		<div class="contentTabs" id="Vehiculo"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaVehiculoBaja.jsp" %>
		</div>

		<div class="contentTabs" id="Titular"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitularBaja.jsp" %>
		</div>

		<div class="contentTabs" id="Adquiriente"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaAdquirienteBaja.jsp" %>
		</div>

		<div class="contentTabs" id="PagoPresentacion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaPagoPresentacionBaja.jsp" %>
		</div>

		<div class="contentTabs" id="Facturacion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaFacturacionBaja.jsp" %>
		</div>
		<s:if test="%{tramiteTraficoBaja.numExpediente!=null}">
			<div class="contentTabs" id="Resumen"
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
				<%@include file="pestaniaResumenBaja.jsp" %>
			</div>
		</s:if>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoBaja()}">
			<div id="bloqueBotones" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<%-- <table align="center">
				<tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultableOGuardableBaja(tramiteTraficoBaja)}">
						<td>
							<input class="boton" type="button" id="bGuardarBaja" name="bGuardarBaja" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<td id="idTdValidar">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esValidable(tramiteTraficoBaja)}">
							<input class="boton" type="button" id="bValidarBaja" name="bValidarBaja" value="Validar" onClick="javascript:validarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
						</s:if>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultaBTV(tramiteTraficoBaja)}">
						<td>
							<input class="boton" type="button" id="bComprobarBaja" name="bComprobarBaja" value="Comprobar" onClick="javascript:comprobarBaja();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esEnvioExcelBajas(tramiteTraficoBaja)}">
						<td>
							<input class="boton" type="button" id="bPdteExcel" name="bPdteExcel" value="Envío a Excel" onClick="return pendientesEnvioAExcelAltaBaja();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esTramitableTelematicamente(tramiteTraficoBaja)}">
						<td>
							<input class="boton" type="button" id="bTramTelematicamenteBaja" name="bTramTelematicamenteBaja" value="Baja Telemática" onClick="return tramTelematicamenteTramiteTraficoBaja();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esImprimible(tramiteTraficoBaja)}">
						<td>
							<input type="button" class="boton" name="bImprimir" id="idBotonImprimir" value="Imprimir PDF" onClick="return imprimirPdf();" onKeyPress="this.onClick"/>
							&nbsp;
						</td>
					</s:if>
				</tr>
			</table> --%>
		</s:if>

		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0"
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>

		<s:hidden id="idHiddenNumeroExpediente" name="tramiteTraficoBaja.numExpediente"/>
		<s:hidden name="tramiteTraficoBaja.usuarioDto.idUsuario"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.numColegiado"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.idTipoCreacion"></s:hidden>
		<s:hidden id="idContratoTramite" name="tramiteTraficoBaja.idContrato"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.estado"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.tipoTramite"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.idFirmaDoc"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaUltModif.dia"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaUltModif.mes"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaUltModif.anio"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaUltModif.hora"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaUltModif.minutos"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaUltModif.segundos"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaAlta.dia"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaAlta.mes"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaAlta.anio"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaAlta.hora"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaAlta.minutos"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.fechaAlta.segundos"></s:hidden>
		<s:hidden name="tramiteTraficoBaja.vehiculoDto.numColegiado"></s:hidden>
		<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
		<s:hidden id="nifBusqueda" name="nifBusqueda"></s:hidden>
		<s:hidden id="matriculaBusqueda" name="matriculaBusqueda"></s:hidden>
	</s:form>
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
</div>