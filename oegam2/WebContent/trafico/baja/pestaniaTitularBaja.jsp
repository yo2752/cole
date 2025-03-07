<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tramiteTraficoBaja.titular.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoBaja.titular.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoBaja.titular.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoBaja.titular.persona.tipoDocumentoAlternativo" />
<s:hidden name="tramiteTraficoBaja.titular.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoBaja.titular.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoBaja.titular.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoBaja.titular.persona.estado" />
<s:hidden id ="idHiddenDireccionTitularBaja" name="tramiteTraficoBaja.titular.direccion.idDireccion" />
<s:hidden name="tramiteTraficoBaja.titular.fechaInicio.dia" />
<s:hidden name="tramiteTraficoBaja.titular.fechaInicio.mes" />
<s:hidden name="tramiteTraficoBaja.titular.fechaInicio.anio" />
<s:hidden name="tramiteTraficoBaja.titular.fechaFin.dia" />
<s:hidden name="tramiteTraficoBaja.titular.idDireccion" />
<s:hidden name="tramiteTraficoBaja.titular.fechaFin.mes" />
<s:hidden name="tramiteTraficoBaja.titular.fechaFin.anio" />

<s:hidden name="tramiteTraficoBaja.titular.numColegiado" />
<s:hidden name="tramiteTraficoBaja.titular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@Titular}" />
<s:hidden name="tramiteTraficoBaja.titular.autonomo" />
<s:hidden name="tramiteTraficoBaja.titular.cambioDomicilio" />
<s:hidden name="tramiteTraficoBaja.titular.numInterviniente" />
<s:hidden name="tramiteTraficoBaja.titular.nifInterviniente"/>
<s:hidden name="tramiteTraficoBaja.titular.numExpediente"/>
<s:hidden name="tramiteTraficoBaja.titular.idContrato"/>
<s:hidden name="tramiteTraficoBaja.titular.persona.anagrama"/>

<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.tipoDocumentoAlternativo" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.nifInterviniente"/>
<s:hidden name="tramiteTraficoBaja.representanteTitular.numColegiado"/>
<s:hidden name="tramiteTraficoBaja.representanteTitular.numExpediente"/>
<s:hidden name="tramiteTraficoBaja.representanteTitular.idContrato"/>
<s:hidden name="tramiteTraficoBaja.representanteTitular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@RepresentanteTitular}" />
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.direccion.idDireccion"/>
<s:hidden name="tramiteTraficoBaja.representanteTitular.persona.anagrama"/>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del titular</td>
			<s:set var="identificacion" value="tramiteTraficoBaja.titular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTraficoBaja.titular.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoBaja.titular.persona.nif"/>', '<s:property value="tramiteTraficoBaja.titular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">TITULAR</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="dniTitular">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" id="dniTitular"
								name="tramiteTraficoBaja.titular.persona.nif" onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" style="text-transform : uppercase"
								onchange="limpiarFormularioTitular()" size="9" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteBaja('Titular','Titular','dniTitular')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersonaRepresentante">Tipo de persona: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaTitular" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="tramiteTraficoBaja.titular.persona.tipoPersona"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-"
					disabled="true"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoTitular">Sexo:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="sexoTitular" name="tramiteTraficoBaja.titular.persona.sexo"
					disabled="%{flagDisabled}" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<label for="primerApellidoTitular">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="primerApellidoTitular"
					name="tramiteTraficoBaja.titular.persona.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					maxlength="100" cssStyle="width:18em;" />
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="segundoApellidoTitular">Segundo Apellido: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="segundoApellidoTitular"
					name="tramiteTraficoBaja.titular.persona.apellido2"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="30" maxlength="100"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="nombreTitular">Nombre:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="nombreTitular"
					name="tramiteTraficoBaja.titular.persona.nombre"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="100"/>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="idFechaNacimientoTitular">Fecha de Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.titular.persona.fechaNacimiento.dia" 
								id="diaNacTitular"
								onblur="this.className='input2';"
								onkeypress="return validarDia(this, event)"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td width="1%">/</td>

						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.titular.persona.fechaNacimiento.mes"
								id="mesNacTitular"
								onblur="this.className='input2';"
								onkeypress="return validarMes(this, event)"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td width="1%">/</td>

						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.titular.persona.fechaNacimiento.anio"
								id="anioNacTitular"
								onblur="this.className='input2';"
								onkeypress="return validarAnio(this, event)"
								onfocus="this.className='inputfocus';"
								size="4" maxlength="4"/>
						</td>

						<td align="left" nowrap="nowrap">
							<a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacTitular, document.formData.mesNacTitular, document.formData.diaNacTitular);return false;"
									title="Seleccionar fecha">
								<img class="PopcalTrigger"
									align="middle" 
									src="img/ico_calendario.gif" ${displayDisabled}
									width="15" height="16" 
									border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<s:if test="tramiteTraficoBaja.titular.direccion.idDireccion != null">
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
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTraficoBaja.titular.persona.nif"/>','<s:property value="tramiteTraficoBaja.titular.numColegiado"/>', 'TitularBaja');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>Id dirección</label>
									<span><s:textfield id="idDireccionTitular" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoBaja.titular.direccion.idDireccion}"></s:textfield></span>
								</td>
							</s:if>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaTitular">Provincia: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="idProvinciaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTraficoBaja.titular.direccion.idProvincia" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTitular()"
					headerKey="-1" headerValue="Seleccione Provincia" listKey="idProvincia" listValue="nombre"
					onchange="cargarListaMunicipios(this,'idMunicipioTitular','municipioSeleccionadoTitular');
					cargarListaTipoVia(this,'tipoViaTitular','tipoViaSeleccionadaTitular');
					inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitularBaja);
					borrarComboPueblo('idPuebloTitular');
					borrarRestoCampos('cpTitular','numeroDireccionTitular','letraDireccionTitular','escaleraDireccionTitular','pisoDireccionTitular','puertaDireccionTitular','bloqueDireccionTitular','kmDireccionTitular','hmDireccionTitular');"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idMunicipioTitular">Municipio: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="idMunicipioTitular" onblur="this.className='input2';"
					${stringDisabled}
					onfocus="this.className='inputfocus';"
					onchange="cargarListaPueblos('idProvinciaTitular', this, 'idPuebloTitular', 'puebloSeleccionadoTitular');
					seleccionarCampo('municipioSeleccionadoTitular','idMunicipioTitular');
					obtenerCodigoPostalPorMunicipio('idProvinciaTitular', this, 'cpTitular');
					inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitularBaja);
					borrarRestoCampos('cpTitular','numeroDireccionTitular','letraDireccionTitular','escaleraDireccionTitular','pisoDireccionTitular','puertaDireccionTitular','bloqueDireccionTitular','kmDireccionTitular','hmDireccionTitular');"
					style="width:200px;" >
					<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoTitular" name="tramiteTraficoBaja.titular.direccion.idMunicipio"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloTitular">Pueblo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="idPuebloTitular" onblur="this.className='input2';" 	${stringDisabled}
					onfocus="this.className='inputfocus';" onchange="seleccionarCampo('puebloSeleccionadoTitular','idPuebloTitular');"
					style="width:200px;">
					<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoTitular" name="tramiteTraficoBaja.titular.direccion.pueblo"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="cpTitular">Código Postal: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="cpTitular" name="tramiteTraficoBaja.titular.direccion.codPostal" disabled="%{flagDisabled}"
					onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaTitular">Tipo de vía: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="tipoViaTitular" onblur="this.className='input2';"
					${stringDisabled} onfocus="this.className='inputfocus';"
					onchange="seleccionarCampo('tipoViaSeleccionadaTitular','tipoViaTitular');
					cargarListaNombresVia('idProvinciaTitular', 'municipioSeleccionadoTitular', this, 'nombreViaTitular',viaTitularBaja);">
						<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaTitular" name="tramiteTraficoBaja.titular.direccion.idTipoVia"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="nombreViaTitular">Nombre de vía: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" colspan="6">
				<s:textfield disabled="%{flagDisabled}" id="nombreViaTitular"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoBaja.titular.direccion.nombreVia"
					cssStyle="width:200px;position:relative;" autocomplete="off" maxlength="50"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionTitular">Número: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="numeroDireccionTitular" name="tramiteTraficoBaja.titular.direccion.numero"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					onkeypress="return validarSN2(event,this)" size="4" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionTitular">Letra:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="letraDireccionTitular" name="tramiteTraficoBaja.titular.direccion.letra"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionTitular">Escalera:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="escaleraDireccionTitular" name="tramiteTraficoBaja.titular.direccion.escalera" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionTitular">Piso:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="pisoDireccionTitular" name="tramiteTraficoBaja.titular.direccion.planta" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionTitular">Puerta:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="puertaDireccionTitular" name="tramiteTraficoBaja.titular.direccion.puerta" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionTitular">Bloque:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="bloqueDireccionTitular" name="tramiteTraficoBaja.titular.direccion.bloque" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="kmDireccionTitular">Km:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="kmDireccionTitular" name="tramiteTraficoBaja.titular.direccion.km" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="hmDireccionTitular">Hm:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionTitular" name="tramiteTraficoBaja.titular.direccion.hm" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">COTITULARES</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left">
				<label for="dniCotitulares">Dni Cotitulares:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textarea name="tramiteTraficoBaja.dniCotitulares" id="dniCotitulares"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					rows="2" cols="40"/>
			</td>
		</tr>
	</table>

	<table align="left" class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante del titular</td>
			<s:set var="identificacion" value="tramiteTraficoBaja.representanteTitular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTraficoBaja.representanteTitular.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoBaja.representanteTitular.persona.nif"/>', '<s:property value="tramiteTraficoBaja.representanteTitular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE TITULAR</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifRepresentante">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" id="dniRepresentante"
								name="tramiteTraficoBaja.representanteTitular.persona.nif"
								onblur="this.className='input2';calculaLetraNIF(this)" onchange="limpiarFormularioRepresentanteTitular()"
								onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteBaja('Representante Titular','Titular','dniRepresentante')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersonaRepresentante">Tipo de persona:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaRepresentante" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="tramiteTraficoBaja.representanteTitular.persona.tipoPersona"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona"
					headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoRepresentante">Sexo:</label>
			</td>

			<td align="left" nowrap="nowrap" >
				<s:select id="sexoRepresentante" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()" 
					onblur="this.className='input2';" disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
					name="tramiteTraficoBaja.representanteTitular.persona.sexo" listValue="nombreEnum" listKey="valorEnum"
					title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<label for="primerApellidoRepresentante" >Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="primerApellidoRepresentante"
					name="tramiteTraficoBaja.representanteTitular.persona.apellido1RazonSocial"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="40" maxlength="100"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="segundoApellidoRepresentante">Segundo Apellido:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="segundoApellidoRepresentante"
					name="tramiteTraficoBaja.representanteTitular.persona.apellido2"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="100"/>
			</td>

			<td align="left">
				<label for="nombreRepresentante">Nombre:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="nombreRepresentante"
					name="tramiteTraficoBaja.representanteTitular.persona.nombre"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="100"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="conceptoRepresentante">Concepto:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="conceptoTutela" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}" 	name="tramiteTraficoBaja.representanteTitular.conceptoRepre"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="TODOS"/>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaAltaIni">Fecha Inicio Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteTitular.fechaInicio.dia"
					id="diaInicioRepresentanteTitular"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteTitular.fechaInicio.mes"
					id="mesInicioRepresentanteTitular"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteTitular.fechaInicio.anio"
					id="anioInicioRepresentanteTitular"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="4" />
			</td>

			<td align="left" nowrap="nowrap">
				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRepresentanteTitular, document.formData.mesInicioRepresentanteTitular, document.formData.diaInicioRepresentanteTitular);return false;"
					title="Seleccionar fecha">
					<img class="PopcalTrigger"
						align="middle"
						src="img/ico_calendario.gif" ${displayDisabled}
						width="15" height="16"
						border="0" alt="Calendario"/>
				</a>
			</td>
		</tr>
		<tr>
			<td width="22%" align="left" width="14%">
				<label for="diaAltaIni">Fecha fin Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteTitular.fechaFin.dia"
					id="diaFinRepresentanteTitular"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteTitular.fechaFin.mes"
					id="mesFinRepresentanteTitular"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteTitular.fechaFin.anio"
					id="anioFinRepresentanteTitular"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="4" />
			</td>

			<td align="left" nowrap="nowrap">
				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRepresentanteTitular, document.formData.mesFinRepresentanteTitular, document.formData.diaFinRepresentanteTitular);return false;"
					title="Seleccionar fecha">
					<img class="PopcalTrigger"
						align="middle"
						src="img/ico_calendario.gif" ${displayDisabled}
						width="15" height="16"
						border="0" alt="Calendario"/>
				</a>
			</td>
		</tr>
	</table>
		<table align="center">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultableOGuardableBaja(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bGuardarBaja" name="bGuardarBaja" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
	</table>
</div>
<script>
	var viaTitularBaja = new BasicContentAssist(document.getElementById('nombreViaTitular'), [], null, true);
	recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
	recargarComboTipoVia('idProvinciaTitular','tipoViaTitular','tipoViaSeleccionadaTitular');
	recargarComboPueblos('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
	recargarNombreVias('idProvinciaTitular', 'municipioSeleccionadoTitular', 'tipoViaSeleccionadaTitular','nombreViaTitular', viaTitularBaja);
</script>