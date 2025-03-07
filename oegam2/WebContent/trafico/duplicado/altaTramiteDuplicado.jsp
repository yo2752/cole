
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
<script src="js/trafico/duplicados.js" type="text/javascript"></script>
<script src="js/duplicadoBotones.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>

<s:hidden id="permisoEspecial" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"/>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Altas de Tramitación de Duplicados</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
				<s:set var="numExpediente" value="tramiteTraficoDuplicado.tramiteTrafico.numExpediente"/>
			</td>
		</tr>
	</table>
</div>

<div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<ol id="toc">
		<li id="Duplicado"><a href="#Duplicado">Duplicado</a></li>
		<li id="Vehiculo"><a href="#Vehiculo">Vehículo</a></li>
		<li id="Titular"><a href="#Titular">Titular</a></li> 
		<li id="Cotitular"><a href="#Cotitular">Cotitular</a></li>
		<li id="Documentacion"><a href="#Documentacion">Documentación DUA</a></li>
		<li id="PagoPresentacion"><a href="#PagoPresentacion">Pago/Presentación</a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esFacturableLaTasaDuplicado(tramiteTraficoDuplicado)}">
			<li id="Facturacion"><a href="#Facturacion">Facturación</a></li>
		</s:if>
		<s:if test="%{tramiteTraficoDuplicado.numExpediente!=null}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

		<div class="contentTabs" id="Duplicado"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTramiteDuplicado.jsp" %>
		</div>

		<div class="contentTabs" id="Vehiculo"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaVehiculoDuplicado.jsp" %>
		</div>

		<div class="contentTabs" id="Titular"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitularDuplicado.jsp" %>
		</div>

		<div class="contentTabs" id="Cotitular"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaCotitularDuplicado.jsp" %>
		</div>

		<div class="contentTabs" id="Documentacion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaImportacionDUA.jsp" %>
		</div>

		<div class="contentTabs" id="PagoPresentacion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaPagoPresentacionDuplicado.jsp" %>
		</div>

		<div class="contentTabs" id="Facturacion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaFacturacionDuplicado.jsp" %>
		</div>

		<s:if test="%{tramiteTraficoDuplicado.numExpediente!=null}">
			<div class="contentTabs" id="Resumen"
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
				<%@include file="pestaniaResumenDuplicado.jsp" %>
			</div>
		</s:if>

			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantinimientoDuplicados()}">
				<div id="bloqueBotones" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>
				<table align="center">
					<tr>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esConsultableOGuardableDuplicado(tramiteTraficoDuplicado)}">
							<td>
								<input class="boton" type="button" id="bGuardarDuplicado" name="bGuardarDuplicado" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoDuplicado();" onKeyPress="this.onClick"/>
							</td>
						</s:if>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()}">
							<td id="idTdComprobar">
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esComprobable(tramiteTraficoDuplicado)}">
									<input class="boton" type="button" id="bComprobarDuplicado" name="bComprobarDuplicado" value="Comprobar" onClick="javascript:comprobarAltaTramiteTraficoDuplicado();" onKeyPress="this.onClick"/>
								</s:if>
							</td>
						</s:if>
						<td id="idTdValidar">
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esValidable(tramiteTraficoDuplicado)}">
								<input class="boton" type="button" id="bValidarDuplicado" name="bValidarDuplicado" value="Validar" onClick="javascript:validarAltaTramiteTraficoDuplicado();" onKeyPress="this.onClick"/>
							</s:if>
						</td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esEnvioExcelDuplicados(tramiteTraficoDuplicado)}">
							<td>
								<input class="boton" type="button" id="bPdteExcelDuplicado" name="bPdteExcelDuplicado" value="Envio a Excel" onClick="javascript:pendientesEnvioAExcelAltaDuplicado();" onKeyPress="this.onClick"/>
							</td>
						</s:if>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esTramitableDuplicados(tramiteTraficoDuplicado)}">
							<td>
								<input class="boton" type="button" id="bTramitarDuplicado" name="bTramitarDuplicado" value="Tramitar Tele." onClick="javascript:tramitarAltaTramiteTraficoDuplicado();" onKeyPress="this.onClick"/>
							</td>
						</s:if>

						<!-- Se quita este botón tras comprobar que lleva tiempo sin que se ejecute el JavaScript que lleva a la impresion del PDF. -->
						<%-- <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esImprimible(tramiteTraficoDuplicado)}">
							<td>
								<input type="button" class="boton" name="bImprimir" id="idBotonImprimir" value="ImprimirPdf" onClick="return imprimirPdf();" onKeyPress="this.onClick"/>
								&nbsp;
							</td>
						</s:if> --%>
					</tr>
				</table>
			</s:if>
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0"
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>

		<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
		<s:hidden id="nifBusqueda" name="nifBusqueda"></s:hidden>
		<s:hidden id="matriculaBusqueda" name="matriculaBusqueda"></s:hidden>
		<s:hidden id="idContratoTramite" name="tramiteTraficoDuplicado.idContrato"></s:hidden>
		<s:hidden id="idHiddenNumeroExpediente" name="tramiteTraficoDuplicado.numExpediente"/>
		<s:hidden name="tramiteTraficoDuplicado.usuarioDto.idUsuario"></s:hidden>

		<s:hidden name="tramiteTraficoDuplicado.tipoTramite"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.numColegiado"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.estado"></s:hidden>

		<s:hidden name="tramiteTraficoDuplicado.idFirmaDoc"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaUltModif.dia"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaUltModif.mes"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaUltModif.anio"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaUltModif.hora"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaUltModif.minutos"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaUltModif.segundos"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaAlta.dia"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaAlta.mes"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaAlta.anio"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaAlta.hora"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaAlta.minutos"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.fechaAlta.segundos"></s:hidden>
		<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.numColegiado"></s:hidden>

		<s:hidden name="tramiteTraficoDuplicado.idTipoCreacion"></s:hidden>
	</s:form>

	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
</div>

<script type="text/javascript">
	cargaPestaniasDuplicCambDomicConduc();
	checkHabilitarPestaniaDocumento();
	tasaTipoDocumento();
	//window.onload = tasaTipoDocumento();
</script>