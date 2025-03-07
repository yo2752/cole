<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del trámite de Licencia</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td  align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');" title="Ver evolución"/> 					
				</td>
			</s:if>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular" style="">
				<span class="titulo">DATOS GENERALES:</span>
			</td>
		</tr>
	</table>
	
	<table border="0" class="tablaformbasicaTC">
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Estado:</label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:if test="%{lcTramiteDto.estado != null && !(lcTramiteDto.estado.equals(''))}">
					<s:textfield name="lcTramiteDto.estado" id="estado" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"
						disabled="true" value="%{@org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam@convertirTexto(lcTramiteDto.estado)}"/>
				</s:if>
				<s:else>
					<s:textfield name="estado" id="estado"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"
						value="Sin iniciar" disabled="true"  style="color:red"/>
				</s:else>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="numExpediente">Nº Expediente:</label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:if test="%{lcTramiteDto.numExpediente != null && !(lcTramiteDto.numExpediente.equals(''))}">
					<s:textfield name="lcTramiteDto.numExpediente" id="numExpediente" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"
						disabled="true"/>
				</s:if>
				<s:else>
					<s:textfield id="numExpediente"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"
						value="Sin generar" disabled="true"  style="color:red"/>
				</s:else>
			</td>
		</tr>		
		
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="refPropia">Referencia propia:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="lcTramiteDto.refPropia" id="referenciaPropia" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="50"
					disabled="true"  />
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="numSolicitud">Número de solicitud:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:if test="%{lcTramiteDto.idSolicitud != null && !(lcTramiteDto.idSolicitud.equals(''))}">
					<s:textfield name="lcTramiteDto.idSolicitud" id="idSolicitud" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"
						readonly="true"/>
				</s:if>
				<s:else>
					<s:textfield id="idSolicitud"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"
						value="Sin generar" readonly="true"  style="color:red"/>
				</s:else>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Respuesta presentación:</label>
			</td>
			<s:if test="lcTramiteDto.respuestaReg != null && lcTramiteDto.respuestaReg != ''">
				<td align="left" nowrap="nowrap">
					<s:textfield name="lcTramiteDto.respuestaReg" id="respuestaReg" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="90"
						readonly="true"  />
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap">
					<s:textfield id="respuestaReg"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="90"
						value="Sin respuesta" readonly="true"  style="color:red"/>
				</td>
			</s:else>
			<td align="left" nowrap="nowrap">
   				<label for="labelFechaPresentacion">Fecha presentación: </label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<table width="95%">
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaPresentacion.dia" id="idDiaPresent" onblur="this.className='input2';" 
								onkeypress="return validarDia(this, event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaPresentacion.mes" id="idMesPresent" onblur="this.className='input2';" 
								onkeypress="return validarMes(this, event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaPresentacion.anio" id="idAnioPresent" onblur="this.className='input2';" onkeypress="return validarAnio(this, event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="true"/>
						</td>
					</tr>
				</table>
   			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Respuesta envío:</label>
			</td>
			<s:if test="lcTramiteDto.respuestaEnv != null && lcTramiteDto.respuestaEnv != ''">
				<td align="left" nowrap="nowrap">
					<s:textfield name="lcTramiteDto.respuestaEnv" id="respuestaEnv" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="90"
						readonly="true"  />
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap">
					<s:textfield id="respuestaEnv"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="90"
						value="Sin respuesta" readonly="true"  style="color:red"/>
				</td>
			</s:else>
			<td align="left" nowrap="nowrap">
   				<label for="labelFechaEnvio">Fecha envío: </label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<table width="95%">
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaEnvio.dia" id="idDiaEnvio" onblur="this.className='input2';" 
								onkeypress="return validarDia(this, event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaEnvio.mes" id="idMesEnvio" onblur="this.className='input2';" 
								onkeypress="return validarMes(this, event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaEnvio.anio" id="idAnioEnvio" onblur="this.className='input2';" onkeypress="return validarAnio(this, event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="true"/>
						</td>
					</tr>
				</table>
   			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Respuesta documentación:</label>
			</td>
			<s:if test="lcTramiteDto.respuestaDoc != null && lcTramiteDto.respuestaDoc != ''">
				<td align="left" nowrap="nowrap">
					<s:textfield name="lcTramiteDto.respuestaDoc" id="respuestaDoc" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="90"
						readonly="true"  />
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap">
					<s:textfield id="respuestaDoc"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="90"
						value="Sin respuesta" readonly="true"  style="color:red"/>
				</td>
			</s:else>
			<td align="left" nowrap="nowrap">
   				<label for="labelFechaEnvioDoc">Fecha documentación: </label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<table width="95%">
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaEnvioDoc.dia" id="idDiaEnvioDoc" onblur="this.className='input2';" 
								onkeypress="return validarDia(this, event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaEnvioDoc.mes" id="idMesEnvioDoc" onblur="this.className='input2';" 
								onkeypress="return validarMes(this, event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="lcTramiteDto.fechaEnvioDoc.anio" id="idAnioEnvioDoc" onblur="this.className='input2';" onkeypress="return validarAnio(this, event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="true"/>
						</td>
					</tr>
				</table>
   			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Respuesta validación:</label>
			</td>
			<s:if test="lcTramiteDto.respuestaVal != null && lcTramiteDto.respuestaVal != ''">
				<td align="left" nowrap="nowrap" colspan="3">
   					<s:textfield name="lcTramiteDto.respuestaVal" id="respuestaVal" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="105"
						readonly="true"  />
			</td>
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield id="respuestaVal"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="105"
						value="Sin respuesta" readonly="true"  style="color:red"/>
				</td>
			</s:else>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Respuesta consulta:</label>
			</td>
			<s:if test="lcTramiteDto.respuestaCon != null && lcTramiteDto.respuestaCon != ''">
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield name="lcTramiteDto.respuestaCon" id="respuestaCon" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="105"
						readonly="true"  />
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield id="respuestaCon"  
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="105"
						value="Sin respuesta" readonly="true"  style="color:red"/>
				</td>
			</s:else>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular" style="">
				<span class="titulo">INTERESADO PRINCIPAL:</span>
			</td>
		</tr>
	</table>
	 <table border="0" class="tablaformbasicaTC">
		<tr>
			<s:if test="lcTramiteDto.intervinienteInteresado.lcPersona.nif != null && lcTramiteDto.intervinienteInteresado.lcPersona.nif != ''">
				<td align="right">
					<display:table name="lcTramiteDto.intervinienteInteresado" excludedParams="*" class="tablacoin" uid="intervinienteInteresado" summary="Interviniente Interesado" style="width:98%"
						cellspacing="0" cellpadding="0">
							<display:column title="Tipo de sujeto"  style="width:18%;text-align:left;" sortable="false">
								<s:property value="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().obtenerDescripcionTipoSujeto(#attr.intervinienteInteresado.lcPersona.tipoSujeto)}" />
							</display:column>
							<display:column property="lcPersona.nif" title="NIF / CIF" style="width:10%;text-align:left;" sortable="false"/>
							<display:column property="lcPersona.nombre" title="Nombre" style="width:24%;text-align:left;" sortable="false"/>
							<display:column property="lcPersona.apellido1RazonSocial" title="1er apellido/Razón social" style="width:24%;text-align:left;" sortable="false"/>
							<display:column property="lcPersona.apellido2" title="2do apellido" style="width:24%;text-align:left;" sortable="false"/>
					</display:table>
				</td>
			</s:if>
	 		
			<s:else>
				<td align="left" nowrap="nowrap" >
					<label for="solicitante" style="color:red;font-size:12px">
						Sin interesado principal
					</label>
				</td>
			</s:else> 
		</tr>		
	</table> 
	</div>