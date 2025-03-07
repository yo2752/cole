<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Libro de Taller del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.datosLibroTallerDto.listaDetalleIncidencias != null 
		&& !consultaVehiculoAtex5Dto.vehiculoAtex5.datosLibroTallerDto.listaDetalleIncidencias.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Lista de Incidencias</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.datosLibroTallerDto.listaDetalleIncidencias" class="tablacoin"
								uid="tablaIncidenciasVehAtex5" 
								id="tablaIncidenciasVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="tipo" title="Tipo Incidencia" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="pieza" title="Pieza" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fecha" title="Fecha" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%"  format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="concepto" title="Concepto" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="descripcion" title="Descripcion" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="anotador" title="Anotador" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaLegalizacion" title="Fecha Legalización" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%"  format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="kms" title="Kms" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
		&nbsp;
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.datosLibroTallerDto.detalleIncidenciaSeleccionada != null }">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Detalle Incidencia</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoIncidencia">Tipo Incidencia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				 <s:textfield id="idTipoIncidenciaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.tipoIncidenciaTaller}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelFechaInci">Fecha:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idFechaehAtex5" 
					value="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().convertirFecha(consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.fecha)}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelElemIncidencia">Elemento Incidencia:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idElemtIncidenciaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.elementoIncidenciaTaller}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelPieza">Pieza:</label>
				</td>
				<td align="left" nowrap="nowrap">
				 <s:textfield id="idPiezaIncidenciaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.pieza}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipo">Tipo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idTipoInciVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.tipo}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelConcetoInci">Concepto:</label>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idConceptoInciVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.concepto}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelDescripCionInci">Descripcion:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idDescripcionInciVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.descricpcion}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelAnotadorInci">Anotador:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idAnotadorInciVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.anotador}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFechaLegalInci">Fecha Legalización:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idFechaLegalInciVehAtex5" 
					value="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().convertirFecha(consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.fechaLegalizacion)}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelKmsInci">Kms:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idKmsInciVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.kms}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelImportaciasInci">Importancias:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idImportanciasInciVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.detalleIncidenciaSeleccionada.importancias}" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
		</table>
	</s:if>
</div>