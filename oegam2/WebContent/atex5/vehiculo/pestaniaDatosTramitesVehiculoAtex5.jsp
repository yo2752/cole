<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de los Tramites del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaTransferenciasDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaTransferenciasDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Transferencias</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaTransferenciasDto" class="tablacoin"
								uid="tablaTransferenciasVehAtex5" 
								id="tablaTransferenciasVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="idDocumentoAnterior" title="Id.Documento Anterior" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="jefatura" title="Jefatura" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="sucursal" title="Sucursal" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaTransferencia" title="Fecha Transferencia" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaMatriculacionTemporalDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaMatriculacionTemporalDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Matriculaciones Temporales</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaMatriculacionTemporalDto" class="tablacoin"
								uid="tablaMatriculacionTempVehAtex5" 
								id="tablaMatriculacionTempVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="anotacion" title="Anotacion" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="fecha" title="fecha" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="matriculaAnterior" title="Matricula Anterior" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaTransferencia" title="Fecha Transferencia" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaBajasDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaBajasDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Bajas</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaBajasDto" class="tablacoin"
								uid="tablaTramBajasVehAtex5" 
								id="tablaTramBajasVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="tipoBaja" title="Tipo" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="jefatura" title="Jefatura" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="sucursal" title="Sucursal" sortable="false" headerClass="sortable"
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
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaDuplicadosDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaDuplicadosDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Duplicado</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaDuplicadosDto" class="tablacoin"
								uid="tablaTramDuplicadoVehAtex5" 
								id="tablaTramDuplicadoVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="razonDuplicado" title="Razon Duplicado" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="jefatura" title="Jefatura" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="sucursal" title="Sucursal" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaDuplicado" title="Fecha Duplicado" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaProrrogasDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaProrrogasDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Prorrogas</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaProrrogasDto" class="tablacoin"
								uid="tablaTramProrrogaVehAtex5" 
								id="tablaTramProrrogaVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
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
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaRematriculacionesDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaRematriculacionesDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Rematriculaciones</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaRematriculacionesDto" class="tablacoin"
								uid="tablaRematriculacionesVehAtex5" 
								id="tablaRematriculacionesVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="antotacion" title="Anotacion" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="fechaRematriculacion" title="Fecha Rematriculacion" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="razonRematriculacion" title="Razon" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
</div>