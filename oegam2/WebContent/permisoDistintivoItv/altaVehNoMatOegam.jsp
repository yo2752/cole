<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/vehiculoNoMatrOegam.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Duplicado de Distintivo</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Alta Vehiculo</td>
		</tr>
	</table>
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="vehiculoNoMatriculadoDto.numColegiado" id="numColegiado"/>
		<div class="contenido">	
			<table class="tablaformbasica">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelId">ID:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idVNMO" name="vehiculoNoMatriculadoDto.id" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="6" maxlength="6" disabled="true"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato: <span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select id="idContratoVNMO" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getComboContratosHabilitados()"
									onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo"
									headerKey="" listValue="descripcion" cssStyle="width:220px" name="vehiculoNoMatriculadoDto.contrato.idContrato">
							</s:select>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="vehiculoNoMatriculadoDto.id" id="idVNMO"/>
					<s:hidden name="vehiculoNoMatriculadoDto.contrato.idContrato" id="idContratoVNMO"/>
				</s:else>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="modelo">Matrícula: <span class="naranja">*</span></label>
					</td>

					<td align="left" nowrap="nowrap"> 
						<s:textfield id="idMatriculaVNMO" name="vehiculoNoMatriculadoDto.matricula" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
					</td>
					<td align="left" nowrap="nowrap" style="vertical-align:middle;">
						<label for="modelo">Bastidor: <span class="naranja">*</span></label>
					</td>

					<td align="left" nowrap="nowrap"> 
						<s:textfield id="idBastidorVNMO" name="vehiculoNoMatriculadoDto.bastidor" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="32" maxlength="17"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="modelo">NIVE: </label>
					</td>

					<td align="left" nowrap="nowrap"> 
						<s:textfield id="idNiveVNMO" name="vehiculoNoMatriculadoDto.nive"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="32" maxlength="32"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelDistintivo">Tipo Distintivo:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getTiposDistintivos()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Tipo Distintivo" disabled="true"
							name="vehiculoNoMatriculadoDto.tipoDistintivo" listKey="valorEnum" listValue="nombreEnum" id="idTipoDistintivoVNMO"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoDstv">Estado Peticion Distintivo:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getEstadosDistintivoConsulta()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" disabled="true"
							name="vehiculoNoMatriculadoDto.estadoSolicitud" listKey="valorEnum" listValue="nombreEnum" id="idEstadoSolVNMO"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpresion">Estado Peticion Impresión:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getEstadosImpresion()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey=""
							headerValue="Seleccione Estado" name="vehiculoNoMatriculadoDto.estadoImpresion" disabled="true"
							listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresionVNMO"/>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" width="150px">
						<label for="labelRespuesta">Respuesta:</label>
					</td>
					<td>
						<table>
							<tr>
								<td align="left" nowrap="nowrap">
									<s:textarea name="vehiculoNoMatriculadoDto.respuestaSol" id="idRespuestaSolVNMO" disabled="true"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" rows="5" cols="50"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table class="acciones" width="95%" align="left">
				<tr>
					<td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().esEstadoGuardableVNMO(vehiculoNoMatriculadoDto)}">
							<input type="button" class="boton" name="bGuardarVehNoMatr" id="idGuardarVehNoMatr" value="Guardar" onClick="javascript:guardarVehNoMatr();"
								onKeyPress="this.onClick"/>
						</s:if>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().esEstadoSolicitableVNMO(vehiculoNoMatriculadoDto)}">
							<input type="button" class="boton" name="bSolicitarVehNoMatr" id="idSolicitarVehNoMatr" value="Solicitar"
								onClick="javascript:solicitarVehNoMatr();" onKeyPress="this.onClick"/>
						</s:if>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().esEstadoGenerableVNMO(vehiculoNoMatriculadoDto)}">
							<input type="button" class="boton" name="bSolImpresionVehNoMatr" id="idSolImpresionVehNoMatr" value="Generar"
								onClick="javascript:generarVehNoMatr();" onKeyPress="this.onClick"/>
						</s:if>
						<input type="button" class="boton" name="bVolverVehNoMatr" id="idVolverVehNoMatr" value="Volver" onClick="javascript:volverVehNoMatr();"
							onKeyPress="this.onClick"/>	
					</td>
				</tr>
			</table>
		</div>
	</s:form>
</div>