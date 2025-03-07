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
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/matriculacionBotones.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo"> Altas de Tramitación de Matriculación	</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
				<s:set var="liberacionRealizada" value="tramiteTrafMatrDto.liberacionEEFF.realizado" />
				<s:set var="esLiberableEEFF" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@esLiberableEEFF(tramiteTrafMatrDto, tramiteTrafMatrDto.liberacionEEFF)}"/>
				<s:set var="numExpediente" value="tramiteTrafMatrDto.numExpediente"/>
			</td>
		</tr>
	</table>
</div>

<!-- Pestañas -->

<div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<ol id="toc">
		<li id="Tramite"><a href="#Tramite">Trámite</a></li>
		<li id="Titular"><a href="#Titular">Titular</a></li>
		<li id="ConductorHabitual" style="display:none"><a href="#ConductorHabitual">Conductor Habitual</a></li>
		<s:if test="%{tramiteTrafMatrDto.renting}">
			<li id="Renting"><a href="#Renting">Renting</a></li>
		</s:if>
		<s:else>
			<li id="Renting" style="display:none"><a href="#Renting">Renting</a></li>
		</s:else>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<li id="Presentador"><a href="#Presentador">Presentador</a></li>
		</s:if>
		<li id="Vehiculo"><a href="#Vehiculo">Vehículo</a></li>
		<li id="576"><a href="#576">576,05 y 06</a></li>
		<li id="PagosEImpuestos" onclick="recargarDireccionIVTM();checkIvtm();"><a href="#PagosEImpuestos">Pago/Presentación </a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoLiberarEEFF()}">
			<li id=LiberarEEFF <s:if test="%{tramiteTrafMatrDto.vehiculoDto == null || tramiteTrafMatrDto.vehiculoDto.nive==null || tramiteTrafMatrDto.vehiculoDto.nive.equals('')}">style="display:none;"</s:if> <s:if test="!#liberacionRealizada"> onclick="actualizarCamposEEFFLiberacion();" </s:if> ><a href="#LiberarEEFF">LiberarEEFF</a></li>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esFacturableLaTasaMatriculacion(tramiteTrafMatrDto)}">
			<li id=Facturacion><a href="#Facturacion">Facturación</a></li>
		</s:if>
		<s:if test="%{tramiteTrafMatrDto.numExpediente!=null}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<s:hidden id="permisoEspecial" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"/>

	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

		<s:hidden id="textoLegal" name="propTexto"/>
		<s:hidden id="flagDisabledHidden" name="flagDisabledHidden" value="%{flagDisabled}"/>
		<s:hidden key="contrato.idContrato"/>

		<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
		<s:hidden id="idHiddenNumeroExpediente" name="tramiteTrafMatrDto.numExpediente"/>
		<s:hidden id="idHiddenEstado" name="tramiteTrafMatrDto.estado"/>
		<s:hidden id="idContratoTramite" name="tramiteTrafMatrDto.idContrato"/>
		<s:hidden id="idHiddenEstadoIvtm" name="tramiteTrafMatrDto.estadoIvtm"/>
		<s:hidden id="idHiddenMensajeIvtm" name="tramiteTrafMatrDto.mensajeIvtm"/>

		<s:hidden id="idTipoLimitacionDisposicionIEDTM" name = "tramiteTrafMatrDto.tipoLimitacionDisposicionIEDTM"/>

		<s:hidden id="numColegiadoVehiculo" name = "tramiteTrafMatrDto.vehiculoDto.numColegiado"/>
		<s:hidden id="idVehiculo" name = "tramiteTrafMatrDto.vehiculoDto.idVehiculo"/>
		<s:hidden id="idHiddenDireccionVehiculo" name = "tramiteTrafMatrDto.vehiculoDto.direccion.idDireccion"/>

		<s:hidden id="nifBusqueda" name="nifBusqueda"/>

		<s:hidden name="tramiteTrafMatrDto.fechaUltModif.dia"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaUltModif.mes"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaUltModif.anio"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaUltModif.hora"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaUltModif.minutos"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaUltModif.segundos"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaAlta.dia"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaAlta.mes"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaAlta.anio"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaAlta.hora"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaAlta.minutos"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaAlta.segundos"></s:hidden>

		<s:hidden name="tramiteTrafMatrDto.tipoTramiteMatr"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.npasos"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.tipoTramite"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.idTipoCreacion"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.nre"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaRegistroNRE.dia"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaRegistroNRE.mes"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaRegistroNRE.anio"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaRegistroNRE.hora"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaRegistroNRE.minutos"></s:hidden>
		<s:hidden name="tramiteTrafMatrDto.fechaRegistroNRE.segundos"></s:hidden>

		<div class="contentTabs" id="Tramite" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTramiteMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="Titular" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitularMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="ConductorHabitual" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaConductorHabitualMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="Renting" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaRentingMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="Presentador" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaPresentadorMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="Vehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaVehiculoMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="576" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestania576Matriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="PagosEImpuestos" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaPagosEImpuestosMatriculacion.jsp" %>
		</div>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoLiberarEEFF()}">
			<div class="contentTabs" id="LiberarEEFF" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
				<%@include file="pestaniaLiberarEEFFMatriculacion.jsp" %>
			</div>
		</s:if>

		<div class="contentTabs" id="Facturacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaFacturacionMatriculacion.jsp" %>
		</div>

		<div class="contentTabs" id="Resumen" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumenMatriculacion.jsp" %>
		</div>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermiso(@org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado@PERMISO_MANTENIMIENTO_BAJAS)}">
			<div id="bloqueBotones" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<table align="center">
				<tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esConsultableOGuardableMatriculacion(tramiteTrafMatrDto)}">
						<td>
							<input type="button" class="boton" name="idBotonGuardar" id="idBotonGuardar" value="Guardar" onClick="return guardarAltaTramiteTraficoMatriculacion();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<s:if test="#esLiberableEEFF">
						<td>
							<input type="button" class="botonGrande" name="bLiberarEEFF" id="idBotonLiberarEEFF" value="Liberar EEFF"
								onClick="return liberarEEFFMatriculacion();" onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<s:else>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esMatriculableTelematicamente(tramiteTrafMatrDto)
									&& @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermiso(@org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado@PERMISO_MATRICULACION_MATW_ALTAS)}">
								<td>
									<input type="button" class="botonMasGrande" name="bMatriculacionTelematica" id="idBotonMatriculacionTelematica"
										value="Matriculación Telemática" onClick="return matricularTelematicamenteMatriculacion();" onKeyPress="this.onClick"/>
								</td>
						</s:if>
					</s:else>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esValidableMatriculacion(tramiteTrafMatrDto)}">
						<td align="center" style="text-align:center; list-style:none;">
							<input type="button" class="boton" name="bValidarMatricular" id="idBotonValidarMatricular"
								value="Validar" onClick="return validarMatw();" onKeyPress="this.onClick" />
						</td>
					</s:if>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esClonable(tramiteTrafMatrDto)}">
						<td>
							<input type="button" class="boton" name="bClonar" id="idBotonClonar" value="Clonar Trámite" onClick="return clonarExpedienteMatriculacion();" onKeyPress="this.onClick"/>
							&nbsp;
						</td>
					</s:if>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esImprimible(tramiteTrafMatrDto)}">
						<td>
							<input type="button" class="boton" name="bImprimir" id="idBotonImprimir" value="ImprimirPdf" onClick="return imprimirPdfMatriculacion('idExpediente');" onKeyPress="this.onClick"/>
							&nbsp;
						</td>
					</s:if>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esValidoIntroMatriculaManual(tramiteTrafMatrDto)}">
						<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
							<input type="button" class="botonGrande" value="Introducir Matrícula" onclick="generarPopPupMatriculaNueva(<s:property value="%{tramiteTrafMatrDto.numExpediente}"/>);" id="botonIdentificacion"/>
						</td>
					</s:if>
				</tr>
			</table>
		</s:if>

		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
	</s:form>

	<div id="divEmergenteMatriculacionConsultaEEFF" style="display: none; background: #f4f4f4;"></div>
	<s:if test="%{(tramiteTrafMatrDto.conductorHabitual.persona.nif!=null)&&(tramiteTrafMatrDto.conductorHabitual.persona.nif!='')}">
		<script type="text/javascript">marcarConductorHabitual();</script>
	</s:if>
	<script type="text/javascript">mostrarConductorHabitual(); eliminarAsteriscosObligatorios(); exentoItV();</script>
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
</div>