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
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>
<script src="js/notario/notarioFunction.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Modelo 600</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js"
		src="calendario/ipopeng.htm"
		scrolling="no"
		frameborder="0"
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<ol id="toc">
		<li id="Modelo600" onclick="mostrarBotonGuardar()"><a href="#Modelo600">Modelo 600</a></li>
		<li id="Documento" onclick="mostrarBotonGuardar()"><a href="#Documento">Documento</a></li>
		<li id="SujetoPasivo" onclick="mostrarBotonGuardar()"><a href="#SujetoPasivo">Sujeto Pasivo</a></li>
		<li id="Transmitente" onclick="mostrarBotonGuardar()"><a href="#Transmitente">Transmitente</a></li>
		<li id="Presentador" onclick="mostrarBotonGuardar()"><a href="#Presentador">Presentador</a></li>
		<li id="Bienes" onclick="mostrarBotonGuardar()"><a href="#Bienes">Bienes</a></li>
		<li id="Autoliquidacion" onclick="mostrarBotonGuardar()"><a href="#Autoliquidacion">Autoliquidación</a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().mostrarDatosBancarios(modeloDto)}">
			<li id="DatosBancarios600" onclick="ocultarBotonGuardar()"><a href="#DatosBancarios600">Datos Presentación</a></li>
		</s:if>
		<s:if test="%{modeloDto.listaResultadoModelo != null && modeloDto.listaResultadoModelo.size() > 0}">
			<li id="resultadoPresentacion" onclick="mostrarBotonGuardar()"><a href="#resultadoPresentacion">Resultado Presentación</a></li>
		</s:if>
		<li id="Resumen" onclick="mostrarBotonGuardar()"><a href="#Resumen">Resumen</a></li>
	</ol>

	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
		<s:hidden name="modeloDto.modelo.version"/>
		<s:hidden id="tipoModelo" name="modeloDto.modelo.modelo"/>
		<s:hidden id="idContrato" name="modeloDto.contrato.idContrato"/>
		<s:hidden name="modeloDto.fechaAlta.dia"/>
		<s:hidden name="modeloDto.fechaAlta.mes"/>
		<s:hidden name="modeloDto.fechaAlta.anio"/>
		<s:hidden name="modeloDto.numColegiado"/>
		<s:hidden id="idModelo" name="modeloDto.idModelo"/>
		<s:hidden id="idRemesa" name="modeloDto.remesa.idRemesa"/>
		<s:hidden id="tipoImpuestoExento" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esTipoImpuestoExento(modeloDto)}"/>
		<s:hidden id="tipoImpuestoNoSujeto" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esTipoImpuestoNoSujeto(modeloDto)}"/>
		<s:hidden id="nombreEscritura" name="modeloDto.nombreEscritura"></s:hidden>
		<s:hidden id="estado" name="estado"></s:hidden>

		<div class="contentTabs" id="Modelo600" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaModelo600.jsp" %>
		</div>

		<div class="contentTabs" id="Documento"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDocumento.jsp" %>
		</div>

		<div class="contentTabs" id="SujetoPasivo"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaSujetoPasivo.jsp" %>
		</div>

		<div class="contentTabs" id="Transmitente"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTransmitente.jsp" %>
		</div>

		<div class="contentTabs" id="Presentador" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaPresentador.jsp" %>
		</div>

		<div class="contentTabs" id="Bienes"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaBienes.jsp" %>
		</div>

		<div class="contentTabs" id="Autoliquidacion" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaAutoliquidacion.jsp" %>
		</div>

		<div class="contentTabs" id="DatosBancarios600" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDatosBancarios.jsp" %>
		</div>

		<div class="contentTabs" id="resultadoPresentacion" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResultadoPresentacion.jsp" %>
		</div>

		<div class="contentTabs" id="Resumen" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumenModelo.jsp" %>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
				<input type="button" class="boton" name="bGuardarModelo" id="idGuardarModelo"
					value="Guardar" onClick="javascript:guardarModelo();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esValidableModelos(modeloDto)}">
				<input type="button" class="boton" name="bValidarModelo" id="idValidarModelo"
					value="Validar" onClick="javascript:validarModelo();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esAutoLiquidable(modeloDto)}">
				<input type="button" class="boton" name="bAutoLiquidarModelo" id="idAutoLiquidarModelo"
					value="AutoLiquidar" onClick="javascript:autoLiquidarModelo();" onKeyPress="this.onClick"/>
			</s:if>	
			<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esPresentable(modeloDto)}">
				<input type="button" class="boton" name="bPresentarModelo" id="idPresentarModelo"
					value="Presentar" onClick="javascript:presentarModelo();" onKeyPress="this.onClick"/>
			</s:if>
		</div>
	</s:form>
</div>
<script type="text/javascript">
	inicializarVentanas('idConceptoDocumento','tipoModelo');
</script>	