<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- Including CryptoJS required libraries -->

<div class="contenido">
	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<s:if test="%{empresaDIReDto.numExpediente == null}">

					<tr>
						<td align="left" nowrap="nowrap"><label for="labelContrato">Contrato:</label>
						</td>
						<td align="left"><s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';"
								headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo"
								headerKey="" listValue="descripcion" cssStyle="width:220px"
								name="empresaDIReDto.contrato.idContrato">
							</s:select></td>
					</tr>
				</s:if>
			</s:if>
			<s:else>

			</s:else>

			<s:if test="%{empresaDIReDto.numExpediente != null}">
				<tr>
					<td align="left" nowrap="nowrap" width="120"><label
						for="labelRBastidorConsulta">Num Expediente: </label></td>
					<td align="left" nowrap="nowrap"><s:label
							value="%{empresaDIReDto.numExpediente}" /></td>
					<s:hidden id="idContrato" name="empresaDIReDto.contrato.idContrato" />
				</tr>
			</s:if>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="1" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos de la
					Empresa:</span></td>
		</tr>
	</table>

	<table cellSpacing="1" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="labelEmpresa"><span
					class="naranja">*</span>Nombre Empresa:</label></td>
			<td align="left" nowrap="nowrap"><s:textfield id="idnombre"
					name="empresaDIReDto.nombre" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="80" maxlength="80" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="labelDireccion">Dirección:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield id="iddireccion"
					name="empresaDIReDto.direccion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="80" maxlength="80" />
			</td>

			<td style="border: 0px;" align="left"><img
				src="img/direccion-icono_orig.png" alt="Buscar Direccion"
				style="margin-left: -170px; height: 20px; width: 20px; cursor: pointer;"
				onclick="BuscarDireccion(iddireccion.value);"
				title="Buscar Direccion" /></td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap"></td>
			<td align="left" nowrap="nowrap"><select id="Select"
				onclick="Actualiza_Direccion();">
					<option value="">Posibles direcciones</option>
			</select></td>
		</tr>

	</table>
	<table cellSpacing="4" class="tablaformbasica" cellPadding="2"
		align="left">

		<tr>
			<td align="left" nowrap="nowrap"><label for="labeltelefono">Telefono:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield id="idtelefono"
					name="empresaDIReDto.telefono" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15" />
			</td>

			<td align="left" nowrap="nowrap"><label for="labelemail">Email:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield id="idmail"
					name="empresaDIReDto.email" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="60" maxlength="60" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="labeltelefono">NIF:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield id="idNif"
					name="empresaDIReDto.nif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="9" maxlength="9" />
			</td>
			<td align="left" nowrap="nowrap"><label for="labeltelefono">Pais:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield id="idNif"
					name="empresaDIReDto.pais" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" />
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap"><label for="labelcodigoDIRe"><span
					class="naranja"></span>Código DIRe:</label></td>
			<td align="left" nowrap="nowrap"><s:textfield id="idcodigoDIRe"
					name="empresaDIReDto.codigoDire" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15" readonly="true" />
			</td>

			<td align="left" nowrap="nowrap"><label
				for="labelcodigoDIRePadre">Código DIRe padre:</label></td>
			<td align="left" nowrap="nowrap"><s:textfield
					id="idcodigoDIRePadre" name="empresaDIReDto.codigoDirePadre"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15" />
			</td>

			<s:hidden id="idnumExpediente" name="empresaDIReDto.numExpediente" />
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Fechas:</span></td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="labelFechaCreacion">Fecha
					Creación: </label></td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="empresaDIReDto.fechaCreacion.dia"
								id="idDiaIniFechaCreacion" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="empresaDIReDto.fechaCreacion.mes"
								id="idMesIniFechaCreacion" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="empresaDIReDto.fechaCreacion.anio"
								id="idIniAnioFechaCreacion" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap"><a href="javascript:;"
							onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idIniAnioFechaCreacion, document.formData.idMesIniFechaCreacion, document.formData.idDiaIniFechaCreacion);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="middle" src="img/ico_calendario.gif" width="15"
								height="16" border="0" alt="Calendario" />
						</a></td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap"><label
				for="labelFechaActualizacion">Actualizacion: </label></td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="empresaDIReDto.fechaActualizacion.dia"
								id="idDiaInifechaActualizacion"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="empresaDIReDto.fechaActualizacion.mes"
								id="idMesInifechaActualizacion"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								name="empresaDIReDto.fechaActualizacion.anio"
								id="idannioInifechaActualizacion"
								onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap"><a href="javascript:;"
							onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idannioInifechaActualizacion, document.formData.idMesInifechaActualizacion, document.formData.idDiaInifechaActualizacion);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="middle" src="img/ico_calendario.gif" width="15"
								height="16" border="0" alt="Calendario" />
						</a></td>
					</tr>
				</table>
			</td>
	</table>
</div>