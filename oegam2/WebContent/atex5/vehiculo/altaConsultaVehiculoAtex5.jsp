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
<script src="js/atex5/atex5.js" type="text/javascript"></script>
<script src="js/atex5/vehiculoAtex5.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Consulta Vehiculo Atex5</span>
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
		scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> 
	</iframe>
	
	<ol id="toc" >
		<li id="Consulta"><a href="#Consulta">Consulta Vehiculo</a></li>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)}">
			<li id="DetalleVehiculo"><a href="#DetalleVehiculo">Detalle Vehículo</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)}">
			<li id="DatosItvsVehiculo"><a href="#DatosItvsVehiculo">Datos Itvs</a></li>
		</s:if>

		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculoTitular(consultaVehiculoAtex5Dto)}">
			<li id="DatosTitularVehiculo"><a href="#DatosTitularVehiculo">Datos Titular</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)
			&& consultaVehiculoAtex5Dto.vehiculoAtex5.existenDatosResponsables}">
			<li id="DatosResponsablesVehiculo"><a href="#DatosResponsablesVehiculo">Datos Responsables</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)
			&& consultaVehiculoAtex5Dto.vehiculoAtex5.existenDatosTramites}">
			<li id="DatosTramitesVehiculo"><a href="#DatosTramitesVehiculo">Datos Tramites</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)}">
			<li id="DatosAdministrativosVehiculo"><a href="#DatosAdministrativosVehiculo">Datos Administrativos</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)
			&& consultaVehiculoAtex5Dto.vehiculoAtex5.existenDatosSeguros}">
			<li id="DatosSeguroVehiculo"><a href="#DatosSeguroVehiculo">Datos Seguros</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)
			&& consultaVehiculoAtex5Dto.vehiculoAtex5.existenDatosSeguridad}">
			<li id="DatosSeguridadVehiculo"><a href="#DatosSeguridadVehiculo">Datos Seguridad</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosVehiculo(consultaVehiculoAtex5Dto)
			&& consultaVehiculoAtex5Dto.vehiculoAtex5.existenDatosLibroTalles}">
			<li id="DatosLibroTallerVehiculo"><a href="#DatosLibroTallerVehiculo">Datos Libro Taller</a></li>
		</s:if>
				
		<s:if test="consultaVehiculoAtex5Dto != null && consultaVehiculoAtex5Dto.estado != null">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>
	
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="consultaVehiculoAtex5Dto.idConsultaVehiculoAtex5" id="idConsultaVehiculoAtex5"/>
		<s:hidden name="consultaVehiculoAtex5Dto.fechaAlta"/>
		<s:hidden name="consultaVehiculoAtex5Dto.numColegiado"/>
		
		<div class="contentTabs" id="Consulta" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaConsultaVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DetalleVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosDetalleVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosItvsVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosItvVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosTitularVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosTitularVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosResponsablesVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosResponsablesVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosTramitesVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosTramitesVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosAdministrativosVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosAdministrativosVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosSeguroVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosSeguroVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosSeguridadVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosSeguridadVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosLibroTallerVehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosLibroTallerVehiculoAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="Resumen" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaResumenVehiculoAtex5.jsp" %>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaVehiculoAtex5@getInstance().esEstadoGuardable(consultaVehiculoAtex5Dto)}">
				<input type="button" class="boton" name="bGuardarVehAtex5" id="idGuardarVehAtex5" value="Guardar" onClick="javascript:guardarConsultaVehiculo();"
		 			onKeyPress="this.onClick"/>
		 	</s:if>
		 	<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaVehiculoAtex5@getInstance().esEstadoConsultable(consultaVehiculoAtex5Dto)}">	
				<input type="button" class="boton" name="bConsultarVehAtex5" id="idConsultarVehAtex5" value="Consultar" onClick="javascript:consultarVehiculo();"
			 		onKeyPress="this.onClick"/>	
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaVehiculoAtex5@getInstance().esEstadoFinalizadoOK(consultaVehiculoAtex5Dto)}">	
				<input type="button" class="boton" name="bDescargarVehAtex5" id="idDescargarVehAtex5" value="Imprimir" onClick="javascript:imprimirPdfVehiculo();"
			 		onKeyPress="this.onClick"/>	
			</s:if>
		 	<input type="button" class="boton" name="bVolverVehAtex5" id="idVolverVehAtex5" value="Volver" onClick="javascript:volverConsultaVehiculo();"
		 		onKeyPress="this.onClick"/>	
		</div>
		<div id="divEmergenteEvolucionAltaConsultaVehiculoAtex5" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>
