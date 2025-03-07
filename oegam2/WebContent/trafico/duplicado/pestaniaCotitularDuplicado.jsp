<%@ taglib prefix="s" uri="/struts-tags" %>
  
<div class="contenido">

	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.fechaCaducidadNif.dia" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.fechaCaducidadNif.mes" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.fechaCaducidadNif.anio" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.fechaCaducidadAlternativo.dia" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.fechaCaducidadAlternativo.mes" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.fechaCaducidadAlternativo.anio" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.tipoDocumentoAlternativo" />

	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.estado" />
	<s:hidden id ="idHiddenDireccionCotitularDuplicado" name="tramiteTraficoDuplicado.cotitular.direccion.idDireccion" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.fechaInicio.dia" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.fechaInicio.mes" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.fechaInicio.anio" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.fechaFin.dia" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.idDireccion" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.fechaFin.mes" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.fechaFin.anio" />

	<s:hidden name="tramiteTraficoDuplicado.cotitular.numColegiado" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@CotitularTransmision}" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.autonomo" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.cambioDomicilio" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.numInterviniente" />
	<s:hidden name="tramiteTraficoDuplicado.cotitular.nifInterviniente"/>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.numExpediente"/>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.idContrato"/>
	<s:hidden name="tramiteTraficoDuplicado.cotitular.persona.anagrama"/>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del cotitular</td>
			<s:set var="identificacion" value="tramiteTraficoDuplicado.cotitular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTraficoDuplicado.cotitular.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoDuplicado.cotitular.persona.nif"/>','<s:property value="tramiteTraficoDuplicado.cotitular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Cotitular</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="dniCotitular">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" id="dniCotitular" name="tramiteTraficoDuplicado.cotitular.persona.nif"
								onblur="this.className='input2';calculaLetraNIF(this)" onfocus="this.className='inputfocus';"
								style="text-transform : uppercase" size="9" maxlength="9"
								onchange="limpiarFormularioCotitular()"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteDuplicado('Cotitular Transmisión','Cotitular', 'dniCotitular')"/>
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
					id="tipoPersonaCotitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoDuplicado.cotitular.persona.tipoPersona" listValue="nombreEnum"
					listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoCotitular">Sexo:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select disabled="%{flagDisabled}" id="sexoCotitular" name="tramiteTraficoDuplicado.cotitular.persona.sexo"
					list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" listValue="nombreEnum" listKey="valorEnum"
					title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<label for="primerApellidoCotitular">Primer Apellido / Razón Social:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="primerApellidoCotitular" disabled="%{flagDisabled}"
					name="tramiteTraficoDuplicado.cotitular.persona.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					maxlength="100" cssStyle="width:14em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="segundoApellidoCotitular">Segundo Apellido:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="segundoApellidoCotitular" disabled="%{flagDisabled}"
					name="tramiteTraficoDuplicado.cotitular.persona.apellido2" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="30" maxlength="100"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreCotitular">Nombre:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="nombreCotitular" name="tramiteTraficoDuplicado.cotitular.persona.nombre"
					disabled="%{flagDisabled}" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
		</tr>
	</table>

	<s:if test="tramiteTraficoDuplicado.cotitular.direccion.idDireccion != null " >
		<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap"  width="100%">
					<table  style="align:left;">
						<tr>
							<td  align="left" style="vertical-align:middle" width="10%">
								<label>Seleccionar Dirección</label>
							</td>
							<td align="left" nowrap="nowrap" width="90%">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTraficoDuplicado.cotitular.persona.nif"/>','<s:property value="tramiteTraficoDuplicado.cotitular.numColegiado"/>','CotitularDuplicado');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>Id dirección</label>
									<span><s:textfield id="idDireccionCotitular" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoDuplicado.cotitular.direccion.idDireccion}"></s:textfield></span>
								</td>
							</s:if>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaCotitular">Provincia: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="idProvinciaCotitular" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="tramiteTraficoDuplicado.cotitular.direccion.idProvincia"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
					disabled="%{flagDisabled}" headerKey="-1" headerValue="Seleccione Provincia"
					listKey="idProvincia" listValue="nombre"
					onchange="cargarListaMunicipios(this,'idMunicipioCotitular','municipioSeleccionadoCotitular');
					cargarListaTipoVia(this,'tipoViaCotitular','tipoViaSeleccionadaCotitular');
					inicializarTipoVia('tipoViaCotitular','nombreViaCotitular',viaCotitularDuplicado);
					borrarComboPueblo('idPuebloCotitular');
					borrarRestoCampos('cpCotitular','numeroDireccionCotitular','letraDireccionCotitular','escaleraDireccionCotitular','pisoDireccionCotitular','puertaDireccionCotitular','bloqueDireccionCotitular','kmDireccionCotitular','hmDireccionCotitular');"/>	
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioCotitular">Municipio: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idMunicipioCotitular"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="cargarListaPueblos('idProvinciaCotitular', this, 'idPuebloCotitular', 'puebloSeleccionadoCotitular');
					seleccionarCampo('municipioSeleccionadoCotitular','idMunicipioCotitular');
					obtenerCodigoPostalPorMunicipio('idProvinciaCotitular', this, 'cpCotitular'); 
					inicializarTipoVia('tipoViaCotitular','nombreViaCotitular',viaCotitularDuplicado);
					borrarRestoCampos('cpCotitular','numeroDireccionCotitular','letraDireccionCotitular','escaleraDireccionCotitular','pisoDireccionCotitular','puertaDireccionCotitular','bloqueDireccionCotitular','kmDireccionCotitular','hmDireccionCotitular');"
					style="width:200px;">
					<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloCotitular">Pueblo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloCotitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					${stringDisabled}
					onchange="seleccionarCampo('puebloSeleccionadoCotitular','idPuebloCotitular');" style="width:200px;">
					<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.pueblo"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="cpCotitular">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="cpCotitular" 
					name="tramiteTraficoDuplicado.cotitular.direccion.codPostal" 
					onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaCotitular">Tipo de vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="tipoViaCotitular"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('tipoViaSeleccionadaCotitular','tipoViaCotitular');
							cargarListaNombresVia('idProvinciaCotitular', 'municipioSeleccionadoCotitular', this, 'nombreViaCotitular',viaCotitularDuplicado);">
					<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaCotitular"	name="tramiteTraficoDuplicado.cotitular.direccion.idTipoVia"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreViaCotitular">Nombre de vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="6">
				<s:textfield id="nombreViaCotitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.cotitular.direccion.nombreVia"
					cssStyle="width:200px;position:relative;" autocomplete="off"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionCotitular">Número: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="numeroDireccionCotitular"  name="tramiteTraficoDuplicado.cotitular.direccion.numero"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)" size="4" maxlength="3"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionCotitular">Letra: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="letraDireccionCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.letra"
					onblur="this.className='input2';" disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
					onkeypress="return validarLetras(event)" style="text-transform : uppercase"
					size="4" maxlength="3"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionCotitular">Escalera:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="escaleraDireccionCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.escalera"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionCotitular">Piso:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="pisoDireccionCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.planta"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionCotitular">Puerta:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="puertaDireccionCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.puerta"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="4" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionCotitular">Bloque: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="bloqueDireccionCotitular" name="tramiteTraficoDuplicado.cotitular.direccion.bloque"
					disabled="%{flagDisabled}" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="kmDireccionCotitular">Km:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="kmDireccionCotitular"
					name="tramiteTraficoDuplicado.cotitular.direccion.km"
					disabled="%{flagDisabled}" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
					size="4" maxlength="3"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionCotitular">Hm:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionCotitular"
					name="tramiteTraficoDuplicado.cotitular.direccion.hm"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
			</td>
		</tr>
	</table>
</div>
<script>
	var viaCotitularDuplicado = new BasicContentAssist(document.getElementById('nombreViaCotitular'), [], null, true);
	recargarComboMunicipios('idProvinciaCotitular','idMunicipioCotitular','municipioSeleccionadoCotitular');
	recargarComboTipoVia('idProvinciaCotitular','tipoViaCotitular','tipoViaSeleccionadaCotitular');
	recargarComboPueblos('idProvinciaCotitular','municipioSeleccionadoCotitular','idPuebloCotitular', 'puebloSeleccionadoCotitular');
	recargarNombreVias('idProvinciaCotitular', 'municipioSeleccionadoCotitular', 'tipoViaSeleccionadaCotitular','nombreViaCotitular', viaCotitularDuplicado);
</script>