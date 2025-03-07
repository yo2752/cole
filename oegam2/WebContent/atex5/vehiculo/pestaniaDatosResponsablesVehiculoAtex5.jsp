<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de los Responsables del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaConductoresHabitualesDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaConductoresHabitualesDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Conductor Habitual</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaConductoresHabitualesDto" class="tablacoin"
								uid="tablaConductorHabitualVehAtex5" 
								id="tablaConductorHabitualVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="nif" title="Nif" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="filiacion" title="Filiación" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaInicio" title="Fecha Inicio" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="fechaFin" title="Fecha Fin" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaPoseedoresDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaPoseedoresDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Poseedor</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaPoseedoresDto" class="tablacoin"
								uid="tablaPoseedorlVehAtex5" 
								id="tablaPoseedorVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="nif" title="Nif" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="filiacion" title="Filiación" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="tipo" title="Tipo" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="situacionPosesion" title="Situación Posesion" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaPosesion" title="Fecha Posesion" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="jefatura" title="Jefatura" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="sucursal" title="Sucursal" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaTutoresDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaTutoresDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Tutores</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaTutoresDto" class="tablacoin"
								uid="tablaTutoresVehAtex5" 
								id="tablaTutoresVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="nif" title="Nif" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="filiacion" title="Filiación" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="tramite" title="Tramite" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaTramite" title="Fecha Tramite" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaArrendatariosDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaArrendatariosDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Arrendatarios</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaArrendatariosDto" class="tablacoin"
								uid="tablaArrendatariosehAtex5" 
								id="tablaArrendatariosVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="nif" title="Nif" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="filiacion" title="Filiación" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaInicio" title="Fecha Inicio" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="fechaFin" title="Fecha Fin" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
</div>