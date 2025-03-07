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
<script src="js/remesas/remesasFunction.js" type="text/javascript"></script>
<script src="js/notario/notarioFunction.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Remesa Modelo 600</span>
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
		<li id="Remesa600"><a href="#Remesa600">Remesa 600</a></li>
		<li id="Documento"><a href="#Documento">Documento</a></li>
		<li id="SujetoPasivo" ><a href="#SujetoPasivo">Sujeto Pasivo</a></li>
	    <li id="Transmitente"><a href="#Transmitente">Transmitente</a></li>
	    <li id="Presentador"><a href="#Presentador">Presentador</a></li>
		<li id="Bienes"><a href="#Bienes">Bienes</a></li>
		<li id="CoefParticipacion"><a href="#CoefParticipacion">Coeficientes de Participación</a></li>
		<li id="Autoliquidacion"><a href="#Autoliquidacion">Autoliquidación</a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esGenerada(remesa)}">
			<li id="Liquidaciones"><a href="#Liquidaciones">Liquidaciones</a></li>
		</s:if>
		<li id="Resumen"><a href="#Resumen">Resumen</a></li>
	</ol>
	
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="remesa.modelo.version"/>
		<s:hidden id="tipoModelo" name="remesa.modelo.modelo"/>
		<s:hidden id="idContrato" name="remesa.contrato.idContrato"/>
		<s:hidden name="remesa.fechaAlta.dia"/>
		<s:hidden name="remesa.fechaAlta.mes"/>
		<s:hidden name="remesa.fechaAlta.anio"/>
		<s:hidden name="remesa.numColegiado"/>
		<s:hidden id="idRemesa" name="remesa.idRemesa"/>
		<s:hidden id="tipoImpuestoExento" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esTipoImpuestoExento(remesa)}"/>
		<s:hidden id="tipoImpuestoNoSujeto" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esTipoImpuestoNoSujeto(remesa)}"/>
		
		<div class="contentTabs" id="Remesa600" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaRemesa600.jsp" %>
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
		
		<div class="contentTabs" id="Presentador"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaPresentador.jsp" %>
		</div>
		
		<div class="contentTabs" id="Bienes"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaBienes.jsp" %>
		</div>
		
		<div class="contentTabs" id="CoefParticipacion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaCoefParticipacion.jsp" %>
		</div>
		
		<div class="contentTabs" id="Autoliquidacion" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaAutoliquidacion.jsp" %>
		</div>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esGenerada(remesa)}">
			<div class="contentTabs" id="Liquidaciones" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			  <%@include file="pestaniaLiquidacionesRemesa.jsp" %>
			</div>
		</s:if>
		<div class="contentTabs" id="Resumen" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaResumenRemesa.jsp" %>
		</div>
		<br/>
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esGuardableRemesa(remesa)}">
				<input type="button" class="boton" name="bGuardarRemesa" id="idGuardarRemesa" 
		  			value="Guardar" onClick="javascript:guardarRemesa();"onKeyPress="this.onClick"/>	
		  	</s:if>
		  	<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esValidableModelos(remesa)}">
		  		<input type="button" class="boton" name="bValidarRemesa" id="idValidarRemesa" 
		  			value="Validar" onClick="javascript:validarRemesa();"onKeyPress="this.onClick"/>	
		  	</s:if>		
		  	<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esGenerable(remesa)}">
		  		<input type="button" class="boton" name="bGenerarRemesa" id="idGenerarRemesa" 
		  			value="Generar Modelos" onClick="javascript:generarRemesa();"onKeyPress="this.onClick"/>	
		  	</s:if>		
		</div>
	</s:form>
</div>
<script type="text/javascript">
	inicializarVentanas('idConceptoDocumento','tipoModelo');
</script>	
	
	