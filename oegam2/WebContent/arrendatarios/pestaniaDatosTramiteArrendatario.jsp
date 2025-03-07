<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="contenido">
	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap">
					<label for="labelContrato">Contrato:</label>
				</td>
				<td  align="left">
					<s:select id="idContrato" list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
						listValue="descripcion" cssStyle="width:220px" name="arrendatarioDto.contrato.idContrato">
					</s:select>
				</td>
			</s:if>
			<s:else>
				<s:hidden id="idContrato" name="arrendatarioDto.contrato.idContrato"/>
			</s:else>
		</tr>
		<tr>
			<s:if test="%{arrendatarioDto.numExpediente != null}">
				<td align="left" nowrap="nowrap" width="120"><label
					for="labelRBastidorConsulta">Num Expediente: </label></td>
				<td align="left" nowrap="nowrap"><s:label
					value="%{arrendatarioDto.numExpediente}" /></td>
			</s:if>
			<td align="left" nowrap="nowrap">
				<label for="referenciaPropia">Referencia Propia:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield  name="arrendatarioDto.refPropia" id="referenciaPropia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxLength="50"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Fechas
					Comunicación:</span></td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="labelFechaNacArrendatario">Fecha Inicio Comunicación: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="arrendatarioDto.fechaIni.dia" id="idDiaNacIniArrendatario"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="arrendatarioDto.fechaIni.mes" id="idMesNacIniArrendatario"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="arrendatarioDto.fechaIni.anio"
								id="idAnioNacIniArrendatario" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap"><a href="javascript:;"
							onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioNacIniArrendatario, document.formData.idMesNacIniArrendatario, document.formData.idDiaNacIniArrendatario);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="middle" src="img/ico_calendario.gif" width="15"
								height="16" border="0" alt="Calendario" />
						</a></td>
					</tr>

				</table>
			</td>
			<td align="left" nowrap="nowrap"><label
				for="labelFechaNacArrendatario">Fecha Fin Comunicación: </label></td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="arrendatarioDto.fechaFin.dia" id="idDiaNacFinArrendatario"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="arrendatarioDto.fechaFin.mes" id="idMesNacFinArrendatario"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="arrendatarioDto.fechaFin.anio"
								id="idAnioFinNacArrendatario" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap"><a href="javascript:;"
							onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFinNacArrendatario, document.formData.idMesNacFinArrendatario, document.formData.idDiaNacFinArrendatario);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="middle" src="img/ico_calendario.gif" width="15"
								height="16" border="0" alt="Calendario" />
						</a></td>
					</tr>
				</table>

			</td>
		</tr>

	</table>
</div>