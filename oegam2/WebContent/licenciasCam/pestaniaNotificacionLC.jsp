<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div id="divDatosNotificacion">
<div class="contenido">

	<s:hidden name="lcTramiteDto.intervinienteNotificacion.idInterviniente"/>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos para Notificar</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td align="right"><img src="img/history.png"
					alt="ver evolución"
					style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');"
					title="Ver evolución" /></td>
			</s:if>
		</tr>
	</table>
	<table class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="right" nowrap="nowrap">
				<s:checkbox name="utilizarTitular" id="utilizarTitular" onkeypress="this.onClick" onclick="comprobarTitular();"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<b id="etCheckDatosTitular" for="etCheckDatosTitular"> El Gestor
					Administrativo Colegiado manifiesta bajo su responsabilidad que
					considera</br> que los datos de Notificación son diferentes de la del
					Interviniente Interesado. *
			</b>
			</td>
		</tr>
	</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
			align="left">

			<tr>
				<td class="tabular"><span class="titulo">Persona</span></td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
			align="left">
			<tr>
				<td align="left" nowrap="nowrap"><label for="tipoDocumentoNot">Tipo
						de documento: <span class="naranja">*</span>
				</label></td>
				<td width="30%"><s:select
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoDocumento()"
						id="tipoDocumentoNot" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" listValue="descripcion"
						listKey="codigo" title="Tipo Documento" headerKey=""
						headerValue="-"
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.tipoDocumento" />
				</td>

				<td align="left" nowrap="nowrap"><label for="etNif">NIF
						/ CIF: <span class="naranja">*</span>
				</label></td>
				<td width="24%">
					<table cellSpacing="0">
						<tr>
							<td align="left"><s:textfield id="nifNot"
									name="lcTramiteDto.intervinienteNotificacion.lcPersona.nif"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';" size="9" maxlength="9" />
							</td>
							<td align="left"><s:if
									test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona"
										style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarpersona('037', 'divDatosNotificacion', 'nifNot')" />
								</s:if></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="tipoSujetoNot">Tipo
						de sujeto: <span class="naranja">*</span>
				</label></td>
				<td width="30%"><s:select
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoSujeto()"
						id="tipoSujetoNot" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" listValue="descripcion"
						listKey="codigo" title="Tipo Sujeto" headerKey="" headerValue="-"
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.tipoSujeto" />
				</td>
				<td align="left" nowrap="nowrap"><label
					for="interesadoNombreNot">Nombre: <span class="naranja">*</span></label>
				</td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.nombre"
						id="interesadoNombreNot" size="18" maxlength="20"></s:textfield></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label
					for="notApellido1RazonSocial">1er apellido / Raz&oacute;n
						social: <span class="naranja">*</span>
				</label></td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.apellido1RazonSocial"
						id="notApellido1RazonSocial" size="18" maxlength="70"></s:textfield>
				</td>
				<td align="left" nowrap="nowrap"><label for="notApellido2">2do
						apellido: <span class="naranja">*</span>
				</label></td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.apellido2"
						id="notApellido2" size="18" maxlength="25"></s:textfield></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="telefono1not">N&uacute;m.
						Tel&eacute;fono Principal: <span class="naranja">*</span>
				</label></td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.numTelefono1"
						id="telefono1not" size="18" maxlength="15"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="telefono2not">N&uacute;m.
						Tel&eacute;fono Secundario:</label></td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.numTelefono2"
						id="telefono2not" size="18" maxlength="15"></s:textfield></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="faxNot">N&uacute;m.
						Fax:</label></td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.numFax"
						id="faxNot" size="18" maxlength="15"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label
					for="notCorreoElectronico" class="small">Email:</label></td>
				<td><s:textfield
						name="lcTramiteDto.intervinienteNotificacion.lcPersona.correoElectronico"
						id="notCorreoElectronico" size="18"
						onblur="validaEmail(this.value)" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
			align="left">
			<tr>
				<td class="tabular"><span class="titulo">Dirección</span></td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
			align="left">
			<tr>
				<td align="left" nowrap="nowrap"><label for="etPais"
					class="small">País: <span class="naranja">*</span></label></td>
				<td><s:select id="idPaisNot" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.pais"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().paises()"
						headerKey="" headerValue="Seleccione País" listKey="codigo"
						listValue="descripcion" /></td>

				<td align="left" nowrap="nowrap"><label for="etProvincia"
					class="small">Provincia: <span class="naranja">*</span></label></td>
				<td><s:select id="idProvinciaNot"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.provincia"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().provincias()"
						headerKey="" headerValue="Seleccione Provincia" listKey="codigo"
						listValue="descripcion" /></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="idMunicipioNot">Municipio:</label>
				</td>

				<td align="left" nowrap="nowrap"><s:textfield
						id="idMunicipioNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.municipio"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="70" />
				</td>

				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="idPueblonot">Población: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap"><s:textfield id="idPueblonot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.poblacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="70" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="cpNot">Código Postal: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap"><s:textfield id="cpNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.codigoPostal"
						onkeypress="return validarNumerosEnteros(event)"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="5" /></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="tipoViaNot">Tipo de vía: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap"><s:select id="tipoViaNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.tipoVia"
						headerKey="" headerValue="Seleccione Tipo Vía" listKey="codigo"
						listValue="descripcion"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" /></td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="nombreViaNot">Nombre de vía:<span
						class="naranja">*</span>
				</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="6"><s:textfield
						id="nombreViaNot" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.nombreVia"
						tyle="text-transform : uppercase" /></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="tipoNumNot">Tipo Numeración: <span
						class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap"><s:select id="tipoNumNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.tipoNumeracion"
						headerKey="" headerValue="Seleccione Tipo Vía" listKey="codigo"
						listValue="descripcion"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" /></td>
				<td align="left" nowrap="nowrap" class="small"><label
					for="numeroDireccionNot">Número: <span class="naranja">*</span></label></td>
				<td align="left" nowrap="nowrap" width="2%"><s:textfield
						id="numeroDireccionNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.numCalle"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="5" /></td>
				<td align="left" nowrap="nowrap" class="small"><label
					for="portalDireccionNot">Portal: </label></td>
				<td align="left" nowrap="nowrap"><s:textfield
						id="portalDireccionNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.calificador"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="2" /></td>

			</tr>
			<tr>
				<td align="left" nowrap="nowrap" class="small"><label
					for="plantaDireccionNot">Planta: </label></td>
				<td align="left" nowrap="nowrap"><s:select
						id="plantaDireccionNot" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.planta"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
						listKey="codigo" listValue="descripcion" headerKey=""
						headerValue="Seleccionar Piso" /></td>
				<td align="left" nowrap="nowrap" class="small"><label
					for="puertaDireccionNot">Puerta: </label></td>
				<td><s:textfield id="puertaDireccionNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.puerta"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="4" /></td>
				<td align="left" nowrap="nowrap" class="small"><label
					for="escaleraDireccionNot">Escalera: </label></td>
				<td><s:textfield id="escaleraDireccionNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.escalera"
						size="4" maxlength="4" /></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="localNot">Local:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield id="localNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.local"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="4" />
				</td>
				<td align="left" nowrap="nowrap"><label for="codLocalNot">Código
						Local:</label></td>
				<td align="left" nowrap="nowrap"><s:textfield id="codLocalNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.codLocal"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="9" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="claseDirNot">Clase Dirección:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield id="claseDirNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.claseDireccion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="4" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="codDirNot">Código Dirección:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield id="codDirNot"
						name="lcTramiteDto.intervinienteNotificacion.lcDireccion.codDireccion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="9" />
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
			align="left">
			<tr>
				<td align="center"><input id="bValDirNot" type="button"
					class="botonGrande" name="bValDirNot" value="Validar Dirección"
					onClick="validarDireccion('037')" onkeyPress="this.onClick" /></td>
				<td align="center"><input id="bLimpNot" type="button"
					class="botonGrande" name="bLimpNot" value="Limpiar"
					onClick="limpiarDiv('divDatosNotificacion')"
					onkeyPress="this.onClick" /></td>
			</tr>
		</table>
	</div>
</div>
