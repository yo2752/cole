<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- Evitamos que se pierdan la caducidad de la documentación -->
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.tipoDocumentoAlternativo" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoBaja.adquiriente.nifInterviniente"/>
<s:hidden name="tramiteTraficoBaja.adquiriente.numColegiado"/>
<s:hidden name="tramiteTraficoBaja.adquiriente.numExpediente"/>
<s:hidden name="tramiteTraficoBaja.adquiriente.idContrato"/>
<s:hidden name="tramiteTraficoBaja.adquiriente.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@Adquiriente}" />
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.tipoPersona"/>
<s:hidden name="tramiteTraficoBaja.adquiriente.persona.anagrama"/>
<s:hidden id ="idHiddenDireccionAdquirienteBaja" name="tramiteTraficoBaja.adquiriente.direccion.idDireccion" />

<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.tipoDocumentoAlternativo" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.nifInterviniente"/>
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.numColegiado"/>
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.numExpediente"/>
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.idContrato"/>
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@RepresentanteAdquiriente}" />
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.tipoPersona"/>
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.direccion.idDireccion"/>
<s:hidden name="tramiteTraficoBaja.representanteAdquiriente.persona.anagrama"/>
<!-- Fin de evitar que se pierda la caducidad de la documentación -->

<div class="contenido">
	<table align="left" class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Adquiriente / Acredite Propiedad</td>
				<s:set var="identificacion" value="tramiteTraficoBaja.adquiriente.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoBaja.adquiriente.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoBaja.adquiriente.persona.nif"/>', '<s:property value="tramiteTraficoBaja.adquiriente.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">ADQUIRIENTE / ACREDITE PROPIEDAD</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="dniAdquiriente">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" id="dniAdquiriente"
								name="tramiteTraficoBaja.adquiriente.persona.nif"
								onblur="this.className='input2';calculaLetraNIF(this)" onfocus="this.className='inputfocus';" style="text-transform : uppercase"
								onchange="limpiarFormularioAdquiriente()" size="9" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteBaja('Adquiriente','Adquiriente','dniAdquiriente')"/>
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
					id="tipoPersonaAdquiriente" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" value="%{tramiteTraficoBaja.adquiriente.persona.tipoPersona}"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoAdquiriente">Sexo: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:select id="sexoAdquiriente" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoBaja.adquiriente.persona.sexo" listValue="nombreEnum" listKey="valorEnum"
					title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<label for="primerApellidoAdquiriente">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield disabled="%{flagDisabled}" id="primerApellidoAdquiriente"
					name="tramiteTraficoBaja.adquiriente.persona.apellido1RazonSocial"
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"
					maxlength="100"
					cssStyle="width:18em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="segundoApellidoAdquiriente">Segundo Apellido: </label>
			</td>

			<td align="left" nowrap="nowrap" colspan="1">
				<s:textfield disabled="%{flagDisabled}" id="segundoApellidoAdquiriente"
					name="tramiteTraficoBaja.adquiriente.persona.apellido2"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="30" maxlength="100"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="nombreAdquiriente">Nombre: </label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="nombreAdquiriente"
					name="tramiteTraficoBaja.adquiriente.persona.nombre"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="30" maxlength="100"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="fechaNacAdquiriente">Fecha de Nacimiento: </label>
			</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" id="diaNacAdquiriente"
					name="tramiteTraficoBaja.adquiriente.persona.fechaNacimiento.dia"
					onblur="this.className='input2';"
					onkeypress="return validarDia(this,event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" id="mesNacAdquiriente"
					name="tramiteTraficoBaja.adquiriente.persona.fechaNacimiento.mes"
					onblur="this.className='input2';"
					onkeypress="return validarMes(this,event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" id="anioNacAdquiriente"
					name="tramiteTraficoBaja.adquiriente.persona.fechaNacimiento.anio"
					onblur="this.className='input2';"
					onkeypress="return validarAnio(this,event)"
					onfocus="this.className='inputfocus';"
					size="4" maxlength="4" />
			</td>

			<td align="left" nowrap="nowrap">
				<a href="javascript:;"
					onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacAdquiriente, document.formData.mesNacAdquiriente, document.formData.diaNacAdquiriente);return false;" 
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

	<s:if test="tramiteTraficoBaja.adquiriente.direccion.idDireccion != null">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="100%">
					<table style="align:left;">
						<tr>
							<td align="left" style="vertical-align:middle" width="10em">
								<label>Seleccionar Dirección</label>
							</td>
							<td width="5em" align="left" nowrap="nowrap">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTraficoBaja.adquiriente.persona.nif"/>','<s:property value="tramiteTraficoBaja.representante.numColegiado"/>', 'AdquirienteBaja');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>Id Dirección</label>
									<span><s:textfield id="idDireccionAdquiriente" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoBaja.adquiriente.direccion.idDireccion}"></s:textfield></span>
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
				<label for="idProvinciaAdquiriente">Provincia: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="idProvinciaAdquiriente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}"
					name="tramiteTraficoBaja.adquiriente.direccion.idProvincia"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasAdquiriente()"
					headerKey="-1"
					headerValue="Seleccione Provincia"
					listKey="idProvincia" listValue="nombre"
					onchange="cargarListaMunicipios(this,'idMunicipioAdquiriente','municipioSeleccionadoAdquiriente');
							cargarListaTipoVia(this,'tipoViaAdquiriente','tipoViaSeleccionadaAdquiriente');
							inicializarTipoVia('tipoViaAdquiriente','nombreViaAdquiriente',viaAdquirienteBaja);
							borrarComboPueblo('idPuebloAdquiriente');
							borrarRestoCampos('cpAdquiriente','numeroDireccionAdquiriente','letraDireccionAdquiriente','escaleraDireccionAdquiriente','pisoDireccionAdquiriente','puertaDireccionAdquiriente','bloqueDireccionAdquiriente','kmDireccionAdquiriente','hmDireccionAdquiriente');"/>	
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idMunicipioAdquiriente">Municipio: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" colspan="6">
				<select id="idMunicipioAdquiriente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="cargarListaPueblos('idProvinciaAdquiriente', this, 'idPuebloAdquiriente', 'puebloSeleccionadoAdquiriente');
							seleccionarCampo('municipioSeleccionadoAdquiriente','idMunicipioAdquiriente');
							obtenerCodigoPostalPorMunicipio('idProvinciaAdquiriente', this, 'cpAdquiriente');
							inicializarTipoVia('tipoViaAdquiriente','nombreViaAdquiriente',viaAdquirienteBaja);
							borrarRestoCampos('cpAdquiriente','numeroDireccionAdquiriente','letraDireccionAdquiriente','escaleraDireccionAdquiriente','pisoDireccionAdquiriente','puertaDireccionAdquiriente','bloqueDireccionAdquiriente','kmDireccionAdquiriente','hmDireccionAdquiriente');"
					style="width:200px;">
					<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoAdquiriente" name="tramiteTraficoBaja.adquiriente.direccion.idMunicipio"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloAdquiriente">Pueblo: </label>
			</td>

			<td align="left" nowrap="nowrap" style="width=200px;">
				<select id="idPuebloAdquiriente" 
					onblur="this.className='input2';"
					${stringDisabled}
					onfocus="this.className='inputfocus';"
					onchange="seleccionarCampo('puebloSeleccionadoAdquiriente','idPuebloAdquiriente');"
					style="width:200px;">
					<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoAdquiriente" name="tramiteTraficoBaja.adquiriente.direccion.pueblo"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="cpAdquiriente">Código Postal: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="cpAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.codPostal"
					onkeypress="return validarNumeros(event)"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaAdquiriente">Tipo de vía:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="tipoViaAdquiriente"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('tipoViaSeleccionadaAdquiriente','tipoViaAdquiriente');
					cargarListaNombresVia('idProvinciaAdquiriente', 'municipioSeleccionadoAdquiriente', this, 'nombreViaAdquiriente',viaAdquirienteBaja);">
					<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaAdquiriente" name="tramiteTraficoBaja.adquiriente.direccion.idTipoVia"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="nombreViaAdquiriente">Nombre de vía: </label>
			</td>

			<td align="left" nowrap="nowrap" colspan="6">
				<s:textfield disabled="%{flagDisabled}" id="nombreViaAdquiriente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="tramiteTraficoBaja.adquiriente.direccion.nombreVia" autocomplete="off"
					maxlength="50"
					cssStyle="width:200px;position:relative;"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionAdquiriente">Número: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="numeroDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.numero"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionAdquiriente">Letra: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="letraDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.letra"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarLetras(event)"
					style="text-transform : uppercase"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionAdquiriente">Escalera:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="escaleraDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.escalera"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionAdquiriente">Piso:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="pisoDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.planta"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionAdquiriente">Puerta: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="puertaDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.puerta"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionAdquiriente">Bloque: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="bloqueDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.bloque"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="kmDireccionAdquiriente">Km:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="kmDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.km"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="hmDireccionAdquiriente">Hm:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="hmDireccionAdquiriente"
					name="tramiteTraficoBaja.adquiriente.direccion.hm"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="4" maxlength="3"/>
			</td>
		</tr>
	</table>

	<script>
		var viaAdquirienteBaja = new BasicContentAssist(document.getElementById('nombreViaAdquiriente'), [], null, true);
		recargarComboMunicipios('idProvinciaAdquiriente','idMunicipioAdquiriente','municipioSeleccionadoAdquiriente');
		recargarComboTipoVia('idProvinciaAdquiriente','tipoViaAdquiriente','tipoViaSeleccionadaAdquiriente');
		recargarComboPueblos('idProvinciaAdquiriente','municipioSeleccionadoAdquiriente','idPuebloAdquiriente', 'puebloSeleccionadoAdquiriente');
		recargarNombreVias('idProvinciaAdquiriente', 'municipioSeleccionadoAdquiriente', 'tipoViaSeleccionadaAdquiriente','nombreViaAdquiriente',viaAdquirienteBaja);
	</script>

	<table align="left" class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante del adquiriente</td>
			<s:set var="identificacion" value="tramiteTraficoBaja.representanteAdquiriente.persona.nif"/>
			<s:set var="colegiado" value="tramiteTraficoBaja.representanteAdquiriente.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoBaja.representanteAdquiriente.persona.nif"/>', '<s:property value="tramiteTraficoBaja.representanteAdquiriente.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE ADQUIRIENTE</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="nifRepresentante">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" id="dniRepresentanteAdquiriente"
								name="tramiteTraficoBaja.representanteAdquiriente.persona.nif"
								onblur="this.className='input2';calculaLetraNIF(this)" onfocus="this.className='inputfocus';"
								style="text-transform : uppercase"
								onchange="limpiarFormularioRepresentanteAdquiriente()" size="9" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteBaja('Representante Adquiriente','Adquiriente','dniRepresentanteAdquiriente')"/>
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
						id="tipoPersonaRepresentanteAdquiriente" onblur="this.className='input2';"
						onfocus="this.className='input focus';" value="%{tramiteTraficoBaja.representanteAdquiriente.persona.tipoPersona}"
						listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoRepresentante">Sexo:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="sexoRepresentanteAdquiriente" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					disabled="%{flagDisabled}" 	onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoBaja.representanteAdquiriente.persona.sexo" listValue="nombreEnum" 	listKey="valorEnum"
					title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<label for="primerApellidoRepresentante" >Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="primerApellidoRepresentanteAd"
					name="tramiteTraficoBaja.representanteAdquiriente.persona.apellido1RazonSocial"
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
				<s:textfield disabled="%{flagDisabled}" id="segundoApellidoRepresentanteAd"
					name="tramiteTraficoBaja.representanteAdquiriente.persona.apellido2"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="100"/>
			</td>

			<td align="left">
				<label for="nombreRepresentante">Nombre:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="nombreRepresentanteAd"
					name="tramiteTraficoBaja.representanteAdquiriente.persona.nombre"
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
				<s:select id="conceptoTutelaAd"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}"
					name="tramiteTraficoBaja.representanteAdquiriente.conceptoRepre"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
					listKey="valorEnum"
					listValue="nombreEnum"
					headerKey="-1"
					headerValue="TODOS">
				</s:select>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaAltaIni">Fecha Inicio Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteAdquiriente.fechaInicio.dia"
					id="diaInicioRepresentanteAdquiriente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteAdquiriente.fechaInicio.mes"
					id="mesInicioRepresentanteAdquiriente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteAdquiriente.fechaInicio.anio"
					id="anioInicioRepresentanteAdquiriente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="4" />
			</td>

			<td align="left" nowrap="nowrap">
				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRepresentanteAdquiriente, document.formData.mesInicioRepresentanteAdquiriente, document.formData.diaInicioRepresentanteAdquiriente);return false;" 
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
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteAdquiriente.fechaFin.dia"
					id="diaFinRepresentanteAdquiriente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteAdquiriente.fechaFin.mes"
					id="mesFinRepresentanteAdquiriente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2" />
			</td>

			<td width="1%">/</td>

			<td align="left" nowrap="nowrap" width="5%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoBaja.representanteAdquiriente.fechaFin.anio"
					id="anioFinRepresentanteAdquiriente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="4" />
			</td>

			<td align="left" nowrap="nowrap">
				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRepresentanteAdquiriente, document.formData.mesFinRepresentanteAdquiriente, document.formData.diaFinRepresentanteAdquiriente);return false;"
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