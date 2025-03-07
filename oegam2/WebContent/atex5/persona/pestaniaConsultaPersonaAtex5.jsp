<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta de Persona</td>
			<td style="border: 0px;">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucionAlta('idNumExpConsultaPersona','divEmergenteEvolucionAltaConsultaPersonaAtex5');" title="Ver evolución"/>
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
							<s:textfield id="idNumExpConsultaPersona" name="consultaPersonaAtex5.numExpediente"  onblur="this.className='input2';" 
       							onfocus="this.className='inputfocus';" size="20" maxlength="10" disabled="true"/>
       					</td>
       					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && consultaPersonaAtex5.idConsultaPersonaAtex5 != null}">
       						<td>
								<label for="idConsultaPersonaLabel">Id:</label>
							</td>
							<td>
								<s:textfield id="idConsultaPersonaAtex5" value="%{consultaPersonaAtex5.idConsultaPersonaAtex5}" onblur="this.className='input2';"
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
			    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado" 
			    		name="consultaPersonaAtex5.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoConsultaPersona" disabled="true"/>
			</td>
		</tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato:</label>
					</td>
					<td  align="left">
						<s:if test="consultaPersonaAtex5.contrato == null || consultaPersonaAtex5.contrato.idContrato == null">
							<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="consultaPersonaAtex5.contrato.idContrato"
								onchange="cargarTasas(this,'idCodigoTasa');">
							</s:select>
						</s:if>
						<s:else>
							<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="consultaPersonaAtex5.contrato.idContrato" disabled="true">
							</s:select>
							<s:hidden name="consultaPersonaAtex5.contrato.idContrato"/>
						</s:else>
					</td>
				</tr>
		</s:if>
		<s:else>
			<s:hidden name="consultaPersonaAtex5.contrato.idContrato"/>
		</s:else>
		<tr>
		<td align="left" nowrap="nowrap">
				<label for="labelNIF">NIF:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNifConsultaPersona" name="consultaPersonaAtex5.nif"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="10"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelRazonSocial">Razón Social:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idRznSocialConsultaPersona" name="consultaPersonaAtex5.razonSocial"  onblur="this.className='input2';" 
      							onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelPrimerApe">1º Apellido:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idPrimerApellidoConsultaPersona" name="consultaPersonaAtex5.apellido1"  onblur="this.className='input2';" 
      							onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelSegundoApe">2º Apellido:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idSegundoApellidoConsultaPersona" name="consultaPersonaAtex5.apellido2"  onblur="this.className='input2';" 
      							onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNombre">Nombre:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNombreConsultaPersona" name="consultaPersonaAtex5.nombre"  onblur="this.className='input2';" 
      							onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelAnioNac">Año Nac.:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idAnioNacConsultaPersona" name="consultaPersonaAtex5.anioNacimiento"  onblur="this.className='input2';" 
      							onfocus="this.className='inputfocus';" size="5" maxlength="4"/>
			</td>
		</tr>
		<tr>
			<td align="left">
   				<label for="labelFechaNac">Fecha Nac.: </label>
   			</td>
			<td align="left" nowrap="nowrap" colspan="2">
   				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="idDiaFecNac" name="consultaPersonaAtex5.fechaNacimiento.dia" 
								onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event);"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="idMesFecNac" name="consultaPersonaAtex5.fechaNacimiento.mes" 
								onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="idAnioFecNac" name="consultaPersonaAtex5.fechaNacimiento.anio"
								onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left" nowrap="nowrap">
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFecNac, document.formData.idMesFecNac, document.formData.idDiaFecNac);return false;" title="Seleccionar fecha nacimiento">
				    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    		</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>