<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tramiteTrafMatrDto.conductorHabitual.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@ConductorHabitual}" />
<s:hidden name="tramiteTrafMatrDto.conductorHabitual.nifInterviniente"/>
<s:hidden name="tramiteTrafMatrDto.conductorHabitual.numInterviniente"/>
<s:hidden name="tramiteTrafMatrDto.conductorHabitual.direccion.idDireccion"/>
<s:hidden name="tramiteTrafMatrDto.conductorHabitual.numExpediente"/>
<s:hidden id="numColegiadoConductorHabitual" name = "tramiteTrafMatrDto.conductorHabitual.numColegiado"/>
<s:hidden id="idContratoConductorHabitual" name = "tramiteTrafMatrDto.conductorHabitual.idContrato"/>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Conductor Habitual
			<s:set var="identificacion" value="tramiteTrafMatrDto.titular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTrafMatrDto.numColegiado"/>
			<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTrafMatrDto.conductorHabitual.persona.nif"/>','<s:property value="tramiteTrafMatrDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</s:if>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">CONDUCTOR HABITUAL</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersonaConductorHabitual">Tipo de persona: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					value="%{tramiteTrafMatrDto.conductorHabitual.persona.tipoPersona}"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifConductorHabitual">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.nif"
								id="nifConductorHabitual" onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" onchange="limpiarConductorHabitual()"
								size="10" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteMatriculacion('Conductor Habitual', 'ConductorHabitual', 'nifConductorHabitual')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>

			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Fecha Caducidad NIF:</label>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.conductorHabitual.persona.fechaCaducidadNif.dia" onkeypress="return validarDia(this,event)"
								id="conductorHabitualDiaCadNif" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.conductorHabitual.persona.fechaCaducidadNif.mes" onkeypress="return validarMes(this,event)"
								id="conductorHabitualMesCadNif" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.conductorHabitual.persona.fechaCaducidadNif.anio" onkeypress="return validarAnio(this,event)"
								id="conductorHabitualAnioCadNif" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4"/>
						</td>
						<td>
							<div id="conductorHabitualIdFechaDni"
								<s:if test="%{tramiteTrafMatrDto.conductorHabitual.persona.indefinido != null && tramiteTrafMatrDto.conductorHabitual.persona.indefinido==true}">
									 style="display:none"
								</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.conductorHabitualAnioCadNif, document.formData.conductorHabitualMesCadNif, document.formData.conductorHabitualDiaCadNif);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</div>
						</td>
						<td width="2%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="conductorHabitualOtroDocIdentidad">Documento Alternativo:</label>
			</td>
			<td>
				<s:checkbox id="conductorHabitualOtroDocumentoIdentidad" disabled="%{flagDisabled}" onclick="" onkeypress="this.onClick"
					onchange="habilitarDocumentoAlternativo('conductorHabitualOtroDocumentoIdentidad','conductorHabitualTipoDocumentoAlternativo','conductorHabitualDiaCadAlternativo','conductorHabitualMesCadAlternativo','conductorHabitualAnioCadAlternativo','conductorHabitualIdFecha','conductorHabitualIndefinido');"
					name="tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="conductorHabitualIndefinido">Indefinido:</label>
			</td>
			<td>
				<s:checkbox id="conductorHabitualIndefinido" disabled="%{flagDisabled}" onclick="" onkeypress="this.onClick"
					onchange="documentoIndefinido('conductorHabitualIndefinido', 'conductorHabitualOtroDocumentoIdentidad', 'conductorHabitualTipoDocumentoAlternativo', 'conductorHabitualDiaCadNif', 'conductorHabitualMesCadNif', 'conductorHabitualAnioCadNif', 'conductorHabitualDiaCadAlternativo', 'conductorHabitualMesCadAlternativo', 'conductorHabitualAnioCadAlternativo', 'conductorHabitualIdFechaDni','conductorHabitualIdFecha');"
					name="tramiteTrafMatrDto.conductorHabitual.persona.indefinido"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="conductorHabitualTipoDocumentoAlternativo">Tipo de Doc Alternativo:</label>
			</td>
			<td>
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="conductorHabitualTipoDocumentoAlternativo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.conductorHabitual.persona.tipoDocumentoAlternativo"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-"
					disabled="%{tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad}"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.conductorHabitual.persona.fechaCaducidadAlternativo.dia" onkeypress="return validarDia(this,event)"
								id="conductorHabitualDiaCadAlternativo" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"
								disabled="%{tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.conductorHabitual.persona.fechaCaducidadAlternativo.mes" onkeypress="return validarMes(this,event)"
								id="conductorHabitualMesCadAlternativo" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"
								disabled="%{tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.conductorHabitual.persona.fechaCaducidadAlternativo.anio" onkeypress="return validarAnio(this,event)"
								id="conductorHabitualAnioCadAlternativo" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4"
								disabled="%{tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>
							<div id="conductorHabitualIdFecha"
								<s:if test="%{tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.conductorHabitual.persona.otroDocumentoIdentidad}">
									style="display:none"
								</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.conductorHabitualAnioCadAlternativo, document.formData.conductorHabitualMesCadAlternativo, document.formData.conductorHabitualDiaCadAlternativo);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</div>
						</td>
						<td width="2%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoConductorHabitual">Sexo:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					id="sexoConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.conductorHabitual.persona.sexo" disabled="%{flagDisabled}"
					listValue="nombreEnum" listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="apellido1RazonSocialConductorHabitual">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.apellido1RazonSocial"
					id="apellido1RazonSocialConductorHabitual" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:20em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="apellido2ConductorHabitual">Segundo Apellido:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.apellido2"
					id="apellido2ConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="30" maxlength="100"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreConductorHabitual">Nombre:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.nombre"
					id="nombreConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="30" maxlength="100"/>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="idFechaNacimientoConductorHabitual">Fecha de Nacimiento:</label>
			</td>
			<td align="left" nowrap="nowrap" width="32%">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.fechaNacimiento.dia" onkeypress="return validarDia(this,event)"
								id="diaNacConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.fechaNacimiento.mes" onkeypress="return validarMes(this,event)"
								id="mesNacConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.persona.fechaNacimiento.anio" onkeypress="return validarAnio(this,event)"
								id="anioNacConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								size="4" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacConductorHabitual, document.formData.mesNacConductorHabitual, document.formData.diaNacConductorHabitual);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="12%">
				<label for="estadoCivilConductorHabitual">Estado Civil:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.conductorHabitual.persona.estadoCivil"
					id="estadoCivilConductorHabitual" list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosCivil()"
					disabled="%{flagDisabled}" listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="Estado Civil"/>
			</td>
		</tr>
	</table>

	<s:if test="tramiteTrafMatrDto.conductorHabitual.direccion.idDireccion != null">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="100%">
					<table style="align:left;">
						<tr>
							<td align="left" style="vertical-align:middle" width="10em">
								<label>Seleccionar Dirección</label>
							</td>
							<td align="left" nowrap="nowrap" width="5em">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTrafMatrDto.conductorHabitual.persona.nif"/>','<s:property value="tramiteTrafMatrDto.conductorHabitual.numColegiado"/>','ConductorHabitual');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>ID direccion</label>
									<span><s:textfield id="idDireccionConductorHabitual" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTrafMatrDto.conductorHabitual.direccion.idDireccion}"></s:textfield> </span>
								</td>
							</s:if>
							<s:else>
								<s:hidden id="idDireccionConductorHabitual" value="%{tramiteTrafMatrDto.conductorHabitual.direccion.idDireccion}"/>
							</s:else>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && tramiteTrafMatrDto.conductorHabitual.persona.direccion.idDireccion != null">
				<td>
					<label>ID direccion</label>
					<span><s:textfield cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTrafMatrDto.conductorHabitual.direccion.idDireccion}"></s:textfield> </span>
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaConductorHabitual">Provincia: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasConductorHabitual()"
					headerKey="-1" headerValue="Seleccione Provincia"
					name="tramiteTrafMatrDto.conductorHabitual.direccion.idProvincia"
					listKey="idProvincia" listValue="nombre" id="idProvinciaConductorHabitual"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					onchange="cargarListaMunicipios(this,'idMunicipioConductorHabitual','municipioSeleccionadoConductorHabitual');
					document.getElementById('idProvinciaIVTM').value=this.options[this.selectedIndex].text;
					cargarListaTipoVia(this,'tipoViaDireccionConductorHabitual','tipoViaSeleccionadaDireccionConductorHabitual');
					inicializarTipoVia('tipoViaDireccionConductorHabitual','nombreViaDireccionConductorHabitual',viaConductorHabitualMatriculacion);
					borrarComboPueblo('idPuebloConductorHabitual');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioConductorHabitual">Municipio: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idMunicipioConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="cargarListaPueblosDGT('idProvinciaConductorHabitual', this, 'idPuebloConductorHabitual', 'puebloSeleccionadoConductorHabitual');
					seleccionarCampo('municipioSeleccionadoConductorHabitual','idMunicipioConductorHabitual');
					inicializarTipoVia('tipoViaDireccionConductorHabitual','nombreViaDireccionConductorHabitual',viaConductorHabitualMatriculacion);">
						<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoConductorHabitual" name="tramiteTrafMatrDto.conductorHabitual.direccion.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloConductorHabitual">Pueblo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('puebloSeleccionadoConductorHabitual','idPuebloConductorHabitual');
					document.getElementById('idPuebloIVTM').value=this.options[this.selectedIndex].text;" style="width:200px;">
						<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoConductorHabitual" name="tramiteTrafMatrDto.conductorHabitual.direccion.puebloCorreos"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="codPostalConductorHabitual">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
			<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.codPostalCorreos"
				id="codPostalConductorHabitual" maxlength="5" size="5" onblur="this.className='input';"
				onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionConductorHabitual">Tipo vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="tipoViaDireccionConductorHabitual" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('tipoViaSeleccionadaDireccionConductorHabitual','tipoViaDireccionConductorHabitual');
					cargarListaNombresVia('idProvinciaConductorHabitual', 'municipioSeleccionadoConductorHabitual', this, 'nombreViaDireccionConductorHabitual',viaConductorHabitualMatriculacion);">
							<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaDireccionConductorHabitual" name="tramiteTrafMatrDto.conductorHabitual.direccion.idTipoVia"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreViaDireccionConductorHabitual">Nombre vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield disabled="%{flagDisabled}" id="nombreViaDireccionConductorHabitual"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.conductorHabitual.direccion.nombreVia"
					cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionConductorHabitual">N&uacute;mero: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.numero"
					id="numeroDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionConductorHabitual">Letra/Puerta: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.letra"
					id="letraDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionConductorHabitual">Escalera:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.escalera"
					id="escaleraDireccionConductorHabitual" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionConductorHabitual">Piso:</label>
			</td>
				<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.planta"
					id="pisoDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionConductorHabitual">Portal:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.puerta"
					id="puertaDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionConductorHabitual">Bloque:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.bloque"
					id="bloqueDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="kmDireccionConductorHabitual">Km:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.km"
					id="kmDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="hmDireccionConductorHabitual">Hm:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.direccion.hm"
					id="hmDireccionConductorHabitual" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="8" maxlength="7"/>
			</td>
		</tr>
		<s:if test="tramiteTrafMatrDto.estado == '' || tramiteTrafMatrDto.estado == '1'">
			<tr>
				<td align="left" nowrap="nowrap" width="12%">
					<label for="asignarPrincipalConductorHabitual">Asignar principal:</label>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<s:select id="asignarPrincipalConductorHabitual" name="asignarPrincipalConductorHabitual" disabled="%{flagDisabled}"
						list="#{'SI':'SI', 'NO':'NO'}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<img id="info_Asignar_Principal_Conductor_Habitual" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'asignarPrincipal')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cambioDomicilioConductorHabitual">Cambio de domicilio:</label>
			</td>
			<td align="left" nowrap="nowrap" width="5%" style="vertical-align: middle;">&nbsp;
				<table>
					<tr>
						<td>
							<s:checkbox name="tramiteTrafMatrDto.conductorHabitual.cambioDomicilio" id="idCambioDomicilioConductorHabitual" disabled="%{flagDisabled}" onkeypress="this.onClick"/>
						</td>
						<td align="left" style="vertical-align: middle;">
							<img id="infoCambioDomicilioConductorHabitual" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'cambioDomicilio')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="12%">
				<label for="diaAltaFin">Fecha Fin:</label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.fechaFin.dia" onkeypress="return validarDia(this,event)"
								id="diaAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.fechaFin.mes" onkeypress="return validarMes(this,event)"
								id="mesAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.fechaFin.anio" onkeypress="return validarAnio(this,event)"
								id="anioAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="12%">
				<label for="horaAltaFin">Hora fin:</label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.conductorHabitual.horaFin"
								id="horaAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<script>
		var viaConductorHabitualMatriculacion = new BasicContentAssist(document.getElementById('nombreViaDireccionConductorHabitual'), [], null, true);
		recargarComboMunicipios('idProvinciaConductorHabitual', 'idMunicipioConductorHabitual', 'municipioSeleccionadoConductorHabitual');
		recargarComboTipoVia('idProvinciaConductorHabitual', 'tipoViaDireccionConductorHabitual', 'tipoViaSeleccionadaDireccionConductorHabitual');
		recargarComboLocalidadesDGT('idProvinciaConductorHabitual', 'municipioSeleccionadoConductorHabitual', 'idPuebloConductorHabitual', 'puebloSeleccionadoConductorHabitual');
		recargarNombreVias('idProvinciaConductorHabitual', 'municipioSeleccionadoConductorHabitual', 'tipoViaSeleccionadaDireccionConductorHabitual', 'nombreViaDireccionConductorHabitual', viaConductorHabitualMatriculacion);
	</script>
</div>