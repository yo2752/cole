<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos Itv Del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos de la Tarjeta Técnica</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoTrajetaItv">Tipo Tarjeta Itv:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoTarjetaItvVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tipoTarjetaItv}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelFabricanteBase">Fabricante Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idFabricanteBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.fabricanteBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMasaServicioBase">Masa Servcio Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMasaServBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.masaServicioBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelMarcaBase">Marca Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMarcaBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.marcaBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelHomBase">Homologacion Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idHomoBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.chomologacionBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoBase">Tipo Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tipoBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelVarianteBase">Variante Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idVarianteBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.varianteBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelVersionBase">Version Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idVersionBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.versionBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMomBase">Mom.Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMomBaseVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.momBase}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
	</table>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaItvsDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaItvsDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Itvs</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaItvsDto" class="tablacoin"
								uid="tablaItvsVehAtex5" 
								id="tablaItvsVehAtex5" summary="" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorItvsVehiculoAtex5"
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="motivoItv" title="Motivo" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="kilometraje" title="Kilometraje" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="provincia" title="Provincia" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="estacion" title="Estacion" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="fechaItv" title="Fecha Itv" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="fechaCaducidad" title="Fecha Caducidad" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="resultadoItv" title="Resultado" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%"/>
						
						<display:column property="listaDefectos" title="Lista Defectos" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%"/>
												
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto != null}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Reforma</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
						<label for="labelNuevoPermiso">Nuevo Permiso:</label>
					</td>
					<td align="left" nowrap="nowrap">
					   <s:textfield id="idNuevoPermisoRefVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto.nuevoPermiso}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
						<label for="labelEstacionReforma">Estacón Reforma:</label>
					</td>
					<td align="left" nowrap="nowrap">
					   <s:textfield id="idEstacionRefVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto.estacionReforma}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
						<label for="labelProvinciaRef">Provincia Reforma:</label>
					</td>
					<td align="left" nowrap="nowrap">
					   <s:textfield id="idProvinciaRefVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto.provinciaReforma}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				<td align="left" nowrap="nowrap">
						<label for="labelFechaReforma">Fecha Reforma:</label>
					</td>
					<td align="left" nowrap="nowrap">
					   <s:textfield id="idFechaRefVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto.fechaReforma}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
						<label for="labelFechaValidez">Fecha Validez:</label>
					</td>
					<td align="left" nowrap="nowrap">
					   <s:textfield id="idFechaValidezRefVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto.fechaValidez}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="35" disabled="true"/>
				</td>
			</tr>
		</table>
		&nbsp;
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.reformaDto.listaMotivosReforma" class="tablacoin"
								uid="tablaMotivoReformaVehAtex5" 
								id="tablaMotivoReformaVehAtex5" summary="" 
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="datosReforma" title="Datos Reforma" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="observacionesReforma" title="Observaciones" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
</div>
