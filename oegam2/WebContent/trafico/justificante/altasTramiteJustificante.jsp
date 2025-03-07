<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/justificantes.js" type="text/javascript"></script>
<script src="js/trafico/transmision.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>

<script  type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Altas de Justificantes Profesionales</span>
			</td>
		</tr>
	</table>
</div>

<ol id="toc">
		<li><a href="#TramiteJustificante">Trámite</a></li>
		<li id="pestaniaTitularJustificante"><a href="#Titular">Titular</a></li>
		<li><a href="#Vehiculo">Vehículo</a></li>

		<s:if test="%{tramiteTraficoBean.numExpediente!=null}">
			<li><a href="#Resumen">Resumen</a></li>
		</s:if>
</ol>
<s:form method="post" id="formData" name="formData">
<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div class="contentTabs" id="TramiteJustificante" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaTramiteJustificante.jspf" %>
	</div>

	<div class="contentTabs" id="Titular" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaTitularJustificante.jspf" %>
	</div>

	<div class="contentTabs" id="Vehiculo" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<%@include file="pestaniaVehiculoJustificante.jspf" %>
	</div>

	<div class="contentTabs" id="Resumen" 	
	style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<%@include file="pestaniaResumenJustificante.jspf" %>
	</div>

	<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esGenerableJustificante(tramiteTraficoBean)}">
		<table class="acciones" width="95%" align="left">
			<tr>
				<td align="center" style="size: 100%; text-align: center;list-style:none;">
					<input type="button" class="boton" name="bGenerarJustificantesPro"
					id="idBotonGenerarJustificantesPro" value="GenerarJustificante"
					onclick="altaJustificante(this.id);" onkeypress="this.onClick"/>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage5" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage5" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage10" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esComprobableTransmision(tramiteTraficoTransmisionBean.tramiteTraficoBean)}">
					<td>
						<img id="loadingImage12" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
				</s:if>
			</tr>
		</table>
		<div id="bloqueLoadingConsultar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
	</s:if>

	<!-- Campos ocultos para que no se pierdan -->

	<s:hidden id="idHiddenNumeroExpediente" name="tramiteTraficoBean.numExpediente"/>
	<!--<s:hidden key="tramiteTraficoBean.rowIdTraficoTramite"/>-->
	<s:hidden id="nifTitular" key="titular.nifInterviniente"/>

	<!--<s:hidden id="rowIdVehiculo" key="tramiteTraficoBean.vehiculo.rowIdVehiculo"/>-->

	<s:hidden id="idDireccionTitular" key="titular.persona.direccion.idDireccion"/>
	<s:hidden id="idVehiculo" key="tramiteTrafico.vehiculo.idVehiculo"/>
	<!--<s:hidden id="rowIdPersonaTitular" key="titular.persona.rowIdPersona"/>-->
	<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>

	<s:hidden id="idContratoTramite" name="tramiteTraficoBean.idContrato"/>
	<s:hidden id="respuesta" name="tramiteTraficoBean.respuesta"></s:hidden>
	<!--fin de formulario-->
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
		src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>

</s:form>