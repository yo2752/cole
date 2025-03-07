
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
<script src="js/trafico/cambioServicio.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Altas de Tramitación de Cambio de Servicio</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
				<s:set var="numExpediente" value="tramiteTraficoCambServ.tramiteTrafico.numExpediente"/>
			</td>
		</tr>
	</table>
</div>

<div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<ol id="toc">
		<li id="CambioServicio"><a href="#CambioServicio">Cambio Servicio</a></li>
		<li id="Vehiculo"><a href="#Vehiculo">Vehículo</a></li>
		<li id="Titular"><a href="#Titular">Titular</a></li> 
		<li id="PagoPresentacion"><a href="#PagoPresentacion">Pago/Presentación</a></li>
<%-- 		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esFacturableLaTasaDuplicado(tramiteTraficoDuplicado)}">
			<li id="Facturacion"><a href="#Facturacion">Facturación</a></li>
		</s:if> --%>
		<s:if test="%{tramiteTraficoCambServ.numExpediente!=null}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<s:form method="post" id="formData" name="formData">

<s:hidden id="permisoEspecial" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"/>
<s:hidden id="idContratoTramite" name="tramiteTraficoCambServ.idContrato"></s:hidden>
<s:hidden name="tramiteTraficoCambServ.usuarioDto.idUsuario"></s:hidden>
<s:hidden name="tramiteTraficoCambServ.tipoTramite"></s:hidden>
<s:hidden name="tramiteTraficoCambServ.numColegiado"></s:hidden>
<s:hidden name="tramiteTraficoCambServ.estado"></s:hidden>
	<s:hidden name="tramiteTraficoCambServ.fechaUltModif.dia"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaUltModif.mes"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaUltModif.anio"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaUltModif.hora"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaUltModif.minutos"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaUltModif.segundos"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaAlta.dia"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaAlta.mes"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaAlta.anio"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaAlta.hora"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaAlta.minutos"></s:hidden>
		<s:hidden name="tramiteTraficoCambServ.fechaAlta.segundos"></s:hidden>
<s:hidden name="tramiteTraficoCambServ.vehiculoDto.numColegiado"></s:hidden>
<s:hidden id = "idHiddenNumeroExpediente" name="tramiteTraficoCambServ.tramiteTrafico.numExpediente"></s:hidden>

		<div class="contentTabs" id="CambioServicio" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTramiteCambioServicio.jsp" %>
		</div>

		<div class="contentTabs" id="Vehiculo" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaVehiculoCambioServicio.jsp" %>
		</div>

		<div class="contentTabs" id="Titular" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitularCambioServicio.jsp" %>
		</div>

		<div class="contentTabs" id="PagoPresentacion" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaPagoPresentacionCambioServicio.jsp" %>
		</div>

	<%-- <div class="contentTabs" id="Facturacion" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaFacturacionCambioServicio.jsp" %>
		</div> --%>

		<s:if test="%{tramiteTraficoCambServ.numExpediente!=null}">
			<div class="contentTabs" id="Resumen" 	
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
				<%@include file="pestaniaResumenCambioServicio.jsp" %>
			</div>
		</s:if>

			<div id="bloqueBotones" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<table align="center">
				<tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaCambioServicio@getInstance().esConsultableOGuardableCambioServicio(tramiteTraficoCambServ)}">
						<td>
							<input class="boton" type="button" id="bGuardarCambServ" name="bGuardarCambServ" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoCambServ();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<td id="idTdValidar">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaCambioServicio@getInstance().esValidable(tramiteTraficoCambServ)}">
							<input class="boton" type="button" id="bValidarCambServ" name="bValidarCambServ" value="Validar" onClick="javascript:validarAltaTramiteTraficoCambServ();" onKeyPress="this.onClick"/>
						</s:if>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaCambioServicio@getInstance().esEnvioExcelCambioServicio(tramiteTraficoCambServ)}">
						<td>
							<input class="boton" type="button" id="bPdteExcelCambServ" name="bPdteExcelCambServ" value="Envio a Excel" onClick="return pendientesEnvioAExcelCambServ();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
				</tr>
			</table>

		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
	</s:form>
</div>
