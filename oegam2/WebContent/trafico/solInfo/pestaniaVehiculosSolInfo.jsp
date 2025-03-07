<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">		
	<div id="divDetalleSolInfoVehiculo">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos de los Vehículos de la Solicitud de Información</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<table style="align:left;" >
					 	<tr>
<%-- 		       				<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esAdmin() && tramiteInteveDto.solInfoVehiculo.idTramiteSolInfo != null}">
			       				<td>
									<label for="idConsultaVehiculoLabel">Id:</label>
								</td>
								<td>
									<s:textfield id="idTramSolInfoVehiculo" value="%{tramiteInteveDto.solInfoVehiculo.idTramiteSolInfo}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="10" disabled="true"/>
								</td>
							</s:if> --%>
							<td>
								<label for="labelMatricula">Matricula:</label>
								<s:hidden name="tramiteInteveDto.solicitud.idTramiteSolInfo"/>
								<s:hidden name="tramiteInteveDto.solicitud.tramiteTrafico.numExpediente"/>
								<s:hidden name="tramiteInteveDto.solicitud.idContrato"/>
							</td>
						</tr>
					</table>
				</td>
				<td>
					<s:textfield id="idMatriculaTramSolInfo" name="tramiteInteveDto.solicitud.matricula"  onblur="this.className='input2';" 
	       				onfocus="this.className='inputfocus';" size="9" maxlength="9" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
	       		</td>
	       		<td align="left" nowrap="nowrap">
					<label for="labelBastidor">Bastidor:</label>
				</td>
				<td>
					<s:textfield id="idBastidorTramSolInfo" name="tramiteInteveDto.solicitud.bastidor"  onblur="this.className='input2';" 
	       				onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
	       		</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoTasa">Tipo de Tasa:</label>
				</td>
				<td>
					<label for="labelDescTipoTasa">4.1</label>
					<s:hidden name="tramiteInteveDto.solicitud.tasa.tipoTasa"/>
	       		</td>
	       		<td align="left" nowrap="nowrap">
					<label for="labelCódigo de Tasa">Código de Tasa<span class="naranja">*</span>:</label>
				</td>
				<td>
					<s:select id="idCodTasaTramSolInfo" list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getListaTasas(tramiteInteveDto)"
						onblur="this.className='input2';" headerValue="Seleccione Código de Tasa" onfocus="this.className='inputfocus';" listKey="codigoTasa" headerKey="" 
						listValue="codigoTasa" cssStyle="width:220px" name="tramiteInteveDto.solicitud.tasa.codigoTasa">
					</s:select>
	       		</td>
			</tr>
			<tr> 
				<td align="left" nowrap="nowrap">
	  					<label for="labelTipoInforme">Tipo Informe<span class="naranja">*</span>:</label>
	  				</td>
	       		<td>
	       			<s:select list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getTipoInforme()"
						name="tramiteInteveDto.solicitud.tipoInforme" id="idTipoInformeTramSolInfo" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Seleccionar Tipo Informe" listKey="valorEnum" listValue="nombreEnum" />
				</td>       	       			
				<td align="left" nowrap="nowrap">
	   				<label for="labelMotivoInforme">Motivo Informe<span class="naranja">*</span>:</label>
	   			</td>
	       		<td>
	       			<table style="width:20%">
						<tr>
							<td align="left">
				       			<s:select list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getMotivoInforme()"
									name="tramiteInteveDto.solicitud.motivoInforme" id="idMotivoInformeTramSolInfo" headerKey=""
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									headerValue="Seleccionar Motivo" listKey="valorEnum" listValue="nombreEnum" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().existeListaSolInfoVehiculos(tramiteInteveDto)
	               && @org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().estadoIniciado(tramiteInteveDto) }">
		<div class="acciones center">
			<input type="button" class="boton" name="bLimpiarSolInfoVehiculo" id="idLimpiarSolInfoVehiculo" value="Limpiar Formulario" onClick="javascript:limpiarSolInfoVehiculo();"
				onKeyPress="this.onClick"/>
		</div>
		</s:if>	
	</div>
	<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().existeListaSolInfoVehiculos(tramiteInteveDto)
	              }">
		&nbsp;
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Listado de Solicitudes de Informaciones de Vehículos</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="tramiteInteveDto.solicitudes" class="tablacoin"
								uid="tablaSolicitudesInfoVehiculo" 
								id="tablaSolicitudesInfoVehiculo" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="idTramiteSolInfo" title="" media="none" paramId="idTramiteSolInfo"/>
								
						<display:column property="matricula" title="Matricula" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
		
						<display:column property="bastidor" title="Bastidor" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
						
						<display:column property="tasa.codigoTasa" title="Código Tasa" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
						<display:column property="resultado" title="Resultado" sortable="false" headerClass="sortable " 
							defaultorder="descending" style="width:4%"/>
						
						<display:column title="Estado" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%">
							
							<s:property value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.EstadoTramiteSolicitudInformacion@convertirTexto(#attr.tablaSolicitudesInfoVehiculo.estado)}" />
						</display:column>
						
						<display:column title="Motivo" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" >
							<s:property value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.MotivoInforme@convertir(#attr.tablaSolicitudesInfoVehiculo.motivoInforme)}" />
						</display:column>
						
						<display:column title="Tipo Informe" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" >
							<s:property value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.TipoInforme@convertir(#attr.tablaSolicitudesInfoVehiculo.tipoInforme)}" />
						</display:column>
							
						<display:column title="<input type='checkbox' name='checkTodasSolInfoVehiculos' id='checkTodasSolInfoVehiculos' onClick='marcarTodosSolInfoVehiculos(this)' 
							onKeyPress='this.onClick'/>" style="width:4%" >
							<input type="checkbox" name="listaChecksSolInfoVehiculos" id="check<s:property value="#attr.tablaSolicitudesInfoVehiculo.idTramiteSolInfo"/>" 
								value='<s:property value="#attr.tablaSolicitudesInfoVehiculo.idTramiteSolInfo"/>' />
						</display:column>
					</display:table>
				</td>
			</tr>
		</table>
		<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().existeListaSolInfoVehiculos(tramiteInteveDto)
					  && @org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoGuardable(tramiteInteveDto)}">	
			<div class="acciones center">
				<input type="button" class="boton" name="bEliminarSolInfoVehiculo" id="idEliminarSolInfoVehiculo" value="Eliminar Solicitud" onClick="javascript:eliminarSolInfoVehiculo();"
					onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bModificarSolInfoVehiculo" id="idModificarSolInfoVehiculo" value="Modificar Solicitud" onClick="javascript:modificarSolInfoVehiculo();"
					onKeyPress="this.onClick"/>	
 				<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoReiniciar(tramiteInteveDto)}">	
					<input type="button" class="boton" name="bReinEstadoTramSolInfo" id="idReinEstadoTramSolInfo" value="Reiniciar Estado" onClick="javascript:reiniciarEstadoTramSolInfo();"
				 		onKeyPress="this.onClick"/>	
				</s:if>
					<input type="button" class="boton" name="bDesasignarTasa" id="idDesasignarTasa" value="Desasignar Tasa" onClick="javascript:desasignarTasa();"
			 			onKeyPress="this.onClick"/>	
			</div>
		</s:if>
		
	</s:if>
</div>
<script type="text/javascript">
	inicializarSolInfo();
</script>