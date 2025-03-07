<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td colspan="3">Datos del presentador
			<s:set var="identificacion" value="presentador.nif"/>
			<s:set var="colegiado" value="presentador.numColegiado"/>
			<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="presentador.nif"/>','<s:property value="presentador.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">PRESENTADOR</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idNifPresentador">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<table cellPadding="1" cellSpacing="3">
					<tr>
						<td align="left" nowrap="nowrap" width="24%">
							<s:textfield readonly="true" name="presentador.nif"
								id="idNifPresentador" onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" size="9" maxlength="9" />
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersonaPresentador">Tipo de persona: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						value="%{presentador.tipoPersona}" listValue="nombreEnum"
						listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-"/>
			</td>
		</tr>
		<s:hidden name="presentador.tipoPersona"></s:hidden>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoPresentador">Sexo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					id="sexoPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="presentador.sexo" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idApellidoRazonSocialPresentador">Apellido o Razón Social: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.apellido1RazonSocial"
					id="idApellidoRazonSocialPresentador" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="50" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idApellido2Presentador">Segundo Apellido: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.apellido2"
					id="idApellido2Presentador" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idNombrePresentador">Nombre: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.nombre"
					id="idNombrePresentador" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idAnagramaPresentador">Anagrama</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.anagrama"
					id="idAnagramaPresentador" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5" />
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="10%">
				<label for="diaNacPresentador">Fecha de Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield readonly="true" name="presentador.fechaNacimiento.dia"
					id="diaNacPresentador" onkeypress="return validarDia(this,event)"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
			</td>
			<td width="2">/</td>
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield readonly="true" name="presentador.fechaNacimiento.mes"
					id="mesNacPresentador" onkeypress="return validarMes(this,event)"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
			</td>
			<td width="2">/</td>
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield readonly="true" name="presentador.fechaNacimiento.anio"
					id="anioNacPresentador" onkeypress="return validarAnio(this,event)"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
			</td>
			<td align="left" nowrap="nowrap" width="11%"></td>
			<td align="left" nowrap="nowrap" width="130">
				<label for="idTelefonoPresentador">Teléfono: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.telefonos"
					id="idTelefonoPresentador" 	onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="9" maxlength="9"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaPresentador">Provincia: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasPresentador()"
					headerKey="-1" headerValue="Seleccione Provincia"
					name="presentador.direccionDto.idProvincia" 
					listKey="idProvincia" listValue="nombre" id="idProvinciaPresentador"
					onchange="cargarListaMunicipios(this,'idMunicipioPresentador','municipioSeleccionadoPresentador');
					cargarListaTipoVia(this,'tipoViaPresentador','tipoViaSeleccionadaPresentador');
					inicializarTipoVia('tipoViaPresentador','viaPresentador',viaPresentadorTransmision);
					borrarComboPueblo('idPuebloPresentador');"
					cssStyle="width:170px;" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioPresentador">Municipio: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="idMunicipioPresentador"
					onchange="cargarListaPueblos('idProvinciaPresentador', this, 'idPuebloPresentador', 'puebloSeleccionadoPresentador');
					seleccionarCampo('municipioSeleccionadoPresentador','idMunicipioPresentador');
					obtenerCodigoPostalPorMunicipio('idProvinciaPresentador', this, 'codPostalPresentador');
					inicializarTipoVia('tipoViaPresentador','viaPresentador',viaPresentadorTransmision);"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';">
					<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoPresentador" name="presentador.direccionDto.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloPresentador">Pueblo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloPresentador"
					onchange="seleccionarCampo('puebloSeleccionadoPresentador','idPuebloPresentador');"
					style="width:200px;" onblur="this.className='input2';" onfocus="this.className='inputfocus';">
					<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoPresentador" name="presentador.direccionDto..pueblo"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="codPostalPresentador">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.codPostal"
					id="codPostalPresentador" maxlength="5" size="5" onblur="this.className='input';"
					onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaPresentador">Tipo de vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="tipoViaPresentador" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('tipoViaSeleccionadaPresentador','tipoViaPresentador');
						cargarListaNombresVia('idProvinciaPresentador', 'municipioSeleccionadoPresentador', this, 'viaPresentador',viaPresentadorTransmision);">
					<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaPresentador" name="presentador.direccionDto.idTipoVia"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="viaPresentador">Nombre v&iacute;a: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="6">
				<s:textfield readonly="true" id="viaPresentador" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="presentador.direccionDto.nombreVia"
					cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="numeroPresentador">N&uacute;mero: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.numero"
					id="numeroPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="letraPresentador">Letra/Puerta: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.letra" 
					id="letraPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="escaleraPresentador">Escalera: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.escalera"
					id="escaleraPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="pisoPresentador">Piso: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.planta"
					id="pisoPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaPresentador">Portal: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.puerta"
					id="puertaPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="bloquePresentador">Bloque: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.bloque"
					id="bloquePresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="kmPresentador">Km: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.km"
					id="kmPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="hmPresentador">Hm: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield readonly="true" name="presentador.direccionDto.hm"
					id="hmPresentador" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>
		</tr>
	</table>
	<script>
		var viaPresentadorMatriculacion = new BasicContentAssist(document.getElementById('viaPresentador'), [], null, true);
		recargarComboMunicipios('idProvinciaPresentador','idMunicipioPresentador','municipioSeleccionadoPresentador');
		recargarComboTipoVia('idProvinciaPresentador','tipoViaPresentador','tipoViaSeleccionadaPresentador');
		recargarComboLocalidadesDGT('idProvinciaPresentador','municipioSeleccionadoPresentador','idPuebloPresentador', 'puebloSeleccionadoConductorHabitual');
		recargarNombreVias('idProvinciaPresentador', 'municipioSeleccionadoPresentador', 'tipoViaSeleccionadaPresentador','viaPresentador',viaPresentadorMatriculacion);
	</script>
</div>