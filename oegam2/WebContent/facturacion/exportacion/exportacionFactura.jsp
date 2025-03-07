
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
<script src="js/trafico/duplicados.js" type="text/javascript"></script>
<script  type="text/javascript"></script>      		
      		
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Altas de Tramitación de Duplicados</span>
			</td>
		</tr>
	</table>
</div>

<ol id="toc">
		<li><a href="#Duplicado">Duplicado</a></li>
		<li><a href="#Vehiculo">Vehículo</a></li>
	    <li><a href="#Titular">Titular</a></li> 
	    <li><a href="#Cotitular">Cotitular</a></li>    
	    <li><a href="#PagoPresentacion">Pago/Presentación</a></li>
	    <s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esFacturableLaTasaDuplicado(tramiteTraficoDuplicado)}">
	    	<li><a href="#Facturacion">Facturación</a></li>
	    </s:if>
	    <s:if test="%{tramiteTraficoDuplicado.tramiteTrafico.numExpediente!=null}">
	    	<li><a href="#Resumen">Resumen</a></li>  
	    </s:if>
</ol>

<s:form method="post" id="formData" name="formData">

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div class="contentTabs" id="Duplicado" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  

	</div>
	
	<div class="contentTabs" id="Titular" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">

	</div>
	
	<div class="contentTabs" id="Cotitular" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">

	</div>
	
	<div class="contentTabs" id="Vehiculo" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">

	</div>
	
	<div class="contentTabs" id="PagoPresentacion" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  

	</div>
	
	<div class="contentTabs" id="Facturacion" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  

	</div>
	
	<s:if test="%{tramiteTraficoDuplicado.tramiteTrafico.numExpediente!=null}">
		<div class="contentTabs" id="Resumen" 	
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  

		</div>
	</s:if>
	
	<table>
		<s:if test="%{mensajeErrorFormulario!=null}">
			<tr>
				<td colspan="8" class="mensajeTabla">
					<div class="cajaMensaje">
						<ul><li><s:property value="mensajeErrorFormulario"/></li></ul>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="8">&nbsp;</td>
			</tr>
		</s:if>
	</table>
	
	
	<!-- CAMPOS HIDDEN PARA QUE NO SE PIERDAN DE CAMINO A/DESDE LA BD -->
	
	<s:hidden id="idHiddenNumeroExpediente" name="tramiteTraficoDuplicado.tramiteTrafico.numExpediente"/>
	<!--<s:hidden name="tramiteTraficoDuplicado.rowId"></s:hidden>-->
	<s:hidden name="tramiteTraficoDuplicado.tramiteTrafico.idUsuario"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.tramiteTrafico.tipoTramite"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.tramiteTrafico.numColegiado"></s:hidden>
	<s:hidden id="idContratoTramite" name="tramiteTraficoDuplicado.tramiteTrafico.idContrato"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.estado"></s:hidden>
	<s:hidden id="idVehiculo" name="tramiteTraficoDuplicado.tramiteTrafico.vehiculo.idVehiculo"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.tramiteTrafico.vehiculo.numColegiado"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.titular.idUsuario"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.titular.numColegiado"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.titular.idContrato"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.titular.tipoInterviniente"></s:hidden>
		<!--<s:hidden name="tramiteTraficoDuplicado.titular.persona.rowIdPersona"></s:hidden>-->
	<s:hidden name="tramiteTraficoDuplicado.titular.persona.tipoPersona"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.titular.persona.direccion.idDireccion"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.titular.persona.anagrama"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.nifInterviniente"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.idUsuario"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.numColegiado"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.idContrato"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.tipoInterviniente"></s:hidden>
		<!--<s:hidden name="tramiteTraficoDuplicado.representante.persona.rowIdPersona"></s:hidden>-->
	<s:hidden name="tramiteTraficoDuplicado.representante.persona.tipoPersona"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.persona.direccion.idDireccion"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.representante.persona.anagrama"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.nifInterviniente"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.idUsuario"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.numColegiado"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.idContrato"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.tipoInterviniente"></s:hidden>
		<!--<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.rowIdPersona"></s:hidden>-->
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.tipoPersona"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.direccion.idDireccion"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.anagrama"></s:hidden>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.matricula"></s:hidden>	
	<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
	
	<!--fin de formulario-->
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
</s:form>