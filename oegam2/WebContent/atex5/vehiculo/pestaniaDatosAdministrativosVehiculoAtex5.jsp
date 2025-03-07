<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos Administrativos del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Indicadores</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<s:label>Baja Definitiva</s:label><s:checkbox id="idBajaDefVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.bajaDefinitiva" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
			   <s:label>Baja Temporal</s:label><s:checkbox id="idBajaTempVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.bajaTemporal" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Carga EEFF</s:label><s:checkbox id="idCargaEEFFVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.cargaEEFF" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Embargo</s:label><s:checkbox id="idEmbargoVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.embargo" disabled="true" label=""/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<s:label>Exceso Peso Dimension</s:label><s:checkbox id="idExcesoPesoDimVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.excesoPesoDimension" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
			  	<s:label>Importación</s:label><s:checkbox id="idImportacionVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.importacion" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Incidencias</s:label><s:checkbox id="idIncidenciasVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.incidencias" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Limitacion De Disposición</s:label><s:checkbox id="idLimDisposicionVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionDisposicion" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<s:label>Posesión</s:label><s:checkbox id="idPosesionDimVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.posesion" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
			   <s:label>Precinto</s:label><s:checkbox id="idPrecintoVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.precinto" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Puerto Franco</s:label><s:checkbox id="idPuertoFrancoVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.puertoFranco" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Reformas</s:label><s:checkbox id="idReformasVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.reformas" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<s:label>Renting</s:label><s:checkbox id="idRentingDimVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.renting" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
			   <s:label>Subasta</s:label><s:checkbox id="idSubastaVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.subasta" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Sustracción</s:label><s:checkbox id="idSustraccionVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.sustraccion" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label>Tutela</s:label><s:checkbox id="idTutelaVehAtex5" name="consultaVehiculoAtex5Dto.vehiculoAtex5.tutela" disabled="true"/>
			</td>
		</tr>
	</table>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.existenDatosAdministrativos}">
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaAvisosDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaAvisosDto.isEmpty()}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Avisos</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td>
						<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaAvisosDto" class="tablacoin"
									uid="tablaAvisosVehAtex5" 
									id="tablaAvisosVehAtex5" summary=""
									excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
									
							<display:column property="anotacion" title="anotacion" sortable="false" headerClass="sortable"
									defaultorder="descending" style="width:4%" />
									
							<display:column property="jefatura" title="Jefatura" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="sucursal" title="Sucursal" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="fecha" title="Fecha" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
								
						</display:table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaDenegatoriasDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaDenegatoriasDto.isEmpty()}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Denegatorias</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td>
						<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaDenegatoriasDto" class="tablacoin"
									uid="tablaDenegatoriasVehAtex5" 
									id="tablaDenegatoriasVehAtex5" summary=""
									excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
									
							<display:column property="anotacion" title="anotacion" sortable="false" headerClass="sortable"
									defaultorder="descending" style="width:4%" />
									
							<display:column property="jefatura" title="Jefatura" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="sucursal" title="Sucursal" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="fecha" title="Fecha" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
								
						</display:table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaEmbargosDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaEmbargosDto.isEmpty()}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Embargos</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td>
						<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaEmbargosDto" class="tablacoin"
									uid="tablaEmbargosVehAtex5" 
									id="tablaEmbargosVehAtex5" summary=""
									excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
									
							<display:column property="autoridad" title="Autoridad" sortable="false" headerClass="sortable"
									defaultorder="descending" style="width:4%" />
									
							<display:column property="expediente" title="Expediente" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="fechaMaterializacion" title="Fecha Materialización" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
								
							<display:column property="fechaTramite" title="Fecha Tramite" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
								
						</display:table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaImpagosDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaImpagosDto.isEmpty()}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Impagos</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td>
						<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaImpagosDto" class="tablacoin"
									uid="tablaTramDuplicadoVehAtex5" 
									id="tablaTramDuplicadoVehAtex5" summary=""
									excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
									
							<display:column property="doi" title="Nif" sortable="false" headerClass="sortable"
									defaultorder="descending" style="width:4%" />
									
							<display:column property="anyoImpago" title="Año Impago" sortable="false" headerClass="sortable"
									defaultorder="descending" style="width:4%" />
									
							<display:column property="provincia" title="Provincia" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="municipio" title="Municipio" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						</display:table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaPrecintosDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaPrecintosDto.isEmpty()}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Precintos</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td>
						<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaPrecintosDto" class="tablacoin"
									uid="tablaPrecintosVehAtex5" 
									id="tablaPrecintosVehAtex5" summary=""
									excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
									
							<display:column property="autoridad" title="Autoridad" sortable="false" headerClass="sortable"
									defaultorder="descending" style="width:4%" />
									
							<display:column property="expediente" title="Expediente" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
							<display:column property="fechaMaterializacion" title="Fecha Materialización" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
								
							<display:column property="fechaTramite" title="Fecha Tramite" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
								
						</display:table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionesDisposicionDto != null}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Limitacion de Disposición</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoLimDisposicion">Tipo Limitación:</label>
					</td>
					<td align="left" nowrap="nowrap">
					 <s:textfield id="idTipoLimDispVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionesDisposicionDto.tipoLimitacion}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelAnotacion">Anotación:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield id="idAnotacionLimDispVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionesDisposicionDto.anotacion}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFinanciera">Financiera:</label>
					</td>
					<td align="left" nowrap="nowrap">
					 <s:textfield id="idFinancieraLimDispVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionesDisposicionDto.financieraDomicilio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelRegistro">Registro:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield id="idRegistroLimDispVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionesDisposicionDto.registro}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaLimDisp">Fecha:</label>
					</td>
					<td align="left" nowrap="nowrap">
					 <s:textfield id="idFechaLimDispVehAtex5" value="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().convertirFecha(consultaVehiculoAtex5Dto.vehiculoAtex5.limitacionesDisposicionDto.fecha)}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
					</td>
				</tr>
			</table>
		</s:if>
	</s:if>
</div>