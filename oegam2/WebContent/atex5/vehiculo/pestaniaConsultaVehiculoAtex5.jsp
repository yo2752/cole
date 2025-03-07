<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta de Vehículo</td>
			<td style="border: 0px;" align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  	onclick="abrirEvolucionAlta('idNumExpConsultaVehiculo','divEmergenteEvolucionAltaConsultaVehiculoAtex5');" title="Ver evolución"/>
			</td>
		</tr>
	</table>
	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExp">Num.Expediente:</label>
			</td>
			<td align="left" nowrap="nowrap">
		 		<table style="align:left;" >
			 		<tr>
				 		<td>
							<s:textfield id="idNumExpConsultaVehiculo" name="consultaVehiculoAtex5Dto.numExpediente"  onblur="this.className='input2';" 
       							onfocus="this.className='inputfocus';" size="20" maxlength="10" disabled="true"/>
       					</td>
       					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && consultaVehiculoAtex5Dto.idConsultaVehiculoAtex5 != null}">
       						<td>
								<label for="idConsultaVehiculoLabel">Id:</label>
							</td>
							<td>
								<s:textfield id="idConsultaVehiculoAtex5" value="%{consultaVehiculoAtex5Dto.idConsultaVehiculoAtex5}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="10" disabled="true"/>
							</td>
						</s:if>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelEstadoConsulta">Estado Consulta:</label>
			</td>
			<td align="left" nowrap="nowrap">
			    <s:select list="@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().getEstados()" onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
			    	name="consultaVehiculoAtex5Dto.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoConsultaVehiculo" disabled="true"/>
			</td>
		</tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato:</label>
					</td>
					<td  align="left">
						<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:220px" name="consultaVehiculoAtex5Dto.contrato.idContrato"
							onchange="cargarTasas(this,'idCodigoTasa');">
						</s:select>
					</td>
				</tr>
		</s:if>
		<s:else>
			<s:hidden name="consultaVehiculoAtex5Dto.contrato.idContrato"/>
		</s:else>
		<tr>
		<td align="left" nowrap="nowrap">
				<label for="labelNIF">Matricula:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idMatriculaConsultaVehiculo" name="consultaVehiculoAtex5Dto.matricula"  onblur="this.className='input2';" 
    				onfocus="this.className='inputfocus';" size="20" maxlength="7" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelBastidor">Bastidor:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idBastidorConsultaVehiculo" name="consultaVehiculoAtex5Dto.bastidor"  onblur="this.className='input2';" 
      				onfocus="this.className='inputfocus';" size="30" maxlength="21"/>
			</td>
		</tr>
	</table>
</div>