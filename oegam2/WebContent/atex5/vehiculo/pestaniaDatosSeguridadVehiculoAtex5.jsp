<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de los Componentes de Seguridad del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaElementosSeguridad != null 
		&& !consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaElementosSeguridad.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Elementos de Seguridad</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaElementosSeguridad" class="tablacoin"
								uid="tablaElemSeguridadVehAtex5" 
								id="tablaElemSeguridadVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="nombre" title="Nombre" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="tipo" title="Tipo" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="valor" title="Valor" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaNcap != null 
		&& !consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaNcap.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Ncaps</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaNcap" class="tablacoin"
								uid="tablaNcapsVehAtex5" 
								id="tablaNcapsVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="anioEnsayo" title="Año Ensayo" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="adultos" title="Adultos" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="menores" title="Menores" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="peatones" title="Peatones" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
						
						<display:column property="seguridad" title="Seguridad" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="global" title="Global" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaHojaRescate != null 
		&& !consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaHojaRescate.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Hojas de Rescate</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.datosSeguridadDto.listaHojaRescate" class="tablacoin"
								uid="tablaHojaRescateVehAtex5" 
								id="tablaHojaRescateVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="strHojaRescate" title="Hoja de Rescate" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
</div>