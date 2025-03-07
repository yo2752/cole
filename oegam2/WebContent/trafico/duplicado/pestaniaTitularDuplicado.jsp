<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tramiteTraficoDuplicado.titular.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoDuplicado.titular.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoDuplicado.titular.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoDuplicado.titular.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoDuplicado.titular.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoDuplicado.titular.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoDuplicado.titular.persona.tipoDocumentoAlternativo" />

<s:hidden name="tramiteTraficoDuplicado.titular.persona.estado" />
<s:hidden id ="idHiddenDireccionTitularDuplicado" name="tramiteTraficoDuplicado.titular.direccion.idDireccion" />
<s:hidden name="tramiteTraficoDuplicado.titular.fechaInicio.dia" />
<s:hidden name="tramiteTraficoDuplicado.titular.fechaInicio.mes" />
<s:hidden name="tramiteTraficoDuplicado.titular.fechaInicio.anio" />
<s:hidden name="tramiteTraficoDuplicado.titular.fechaFin.dia" />
<s:hidden name="tramiteTraficoDuplicado.titular.idDireccion" />
<s:hidden name="tramiteTraficoDuplicado.titular.fechaFin.mes" />
<s:hidden name="tramiteTraficoDuplicado.titular.fechaFin.anio" />

<s:hidden name="tramiteTraficoDuplicado.titular.numColegiado" />
<s:hidden name="tramiteTraficoDuplicado.titular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@Titular}" />
<s:hidden name="tramiteTraficoDuplicado.titular.autonomo" />
<s:hidden name="tramiteTraficoDuplicado.titular.cambioDomicilio" />
<s:hidden name="tramiteTraficoDuplicado.titular.numInterviniente" />
<s:hidden name="tramiteTraficoDuplicado.titular.nifInterviniente"/>
<s:hidden name="tramiteTraficoDuplicado.titular.numExpediente"/>
<s:hidden name="tramiteTraficoDuplicado.titular.idContrato"/>
<s:hidden name="tramiteTraficoDuplicado.titular.persona.anagrama"/>

<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.tipoDocumentoAlternativo" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.nifInterviniente"/>
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.numColegiado"/>
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.numExpediente"/>
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.idContrato"/>
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@RepresentanteTitular}" />
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.direccion.idDireccion"/>
<s:hidden name="tramiteTraficoDuplicado.representanteTitular.persona.anagrama"/>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del titular</td>
			<s:set var="identificacion" value="tramiteTraficoDuplicado.titular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTraficoDuplicado.titular.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoDuplicado.titular.persona.nif"/>','<s:property value="tramiteTraficoDuplicado.titular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
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
	<div id="divTitularDuplicado">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="dniTitular">NIF / CIF: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align:left;" >
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="dniTitular" disabled="%{flagDisabled}"
									name="tramiteTraficoDuplicado.titular.persona.nif" onblur="this.className='input2';calculaLetraNIF(this)" 
									onchange="limpiarFormularioTitular()" onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" id="botonBuscarTitular" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarIntervinienteDuplicado('Titular','Titular', 'dniTitular')"/>
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
						id="tipoPersonaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.titular.persona.tipoPersona" listValue="nombreEnum"
						listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="sexoTitular">Sexo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="sexoTitular" name="tramiteTraficoDuplicado.titular.persona.sexo"
						disabled="%{flagDisabled}" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
						listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<label for="primerApellidoTitular">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="primerApellidoTitular" name="tramiteTraficoDuplicado.titular.persona.apellido1RazonSocial"
						disabled="%{flagDisabled}" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:16em;" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="segundoApellidoTitular">Segundo Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="segundoApellidoTitular" name="tramiteTraficoDuplicado.titular.persona.apellido2" 
						disabled="%{flagDisabled}" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="30" maxlength="100"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="nombreTitular">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield id="nombreTitular" name="tramiteTraficoDuplicado.titular.persona.nombre"
						disabled="%{flagDisabled}" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
				</td>
			</tr>
		</table>

		<s:if test="tramiteTraficoDuplicado.titular.direccion.idDireccion != null">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
				<tr>
					<td align="left" nowrap="nowrap" width="100%">
						<table style="align:left;">
							<tr>
								<td align="left" style="vertical-align:middle" width="10%">
									<label>Seleccionar Dirección</label>
								</td>
								<td align="left" nowrap="nowrap" width="90%">
									<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
										<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
											onclick="javascript:seleccionarDireccion('<s:property value="tramiteTraficoDuplicado.titular.persona.nif"/>','<s:property value="tramiteTraficoDuplicado.titular.numColegiado"/>','TitularDuplicado');"/>
									</s:if>
								</td>
								<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
									<td>
										<label>Id dirección</label>
										<span><s:textfield id="idDireccionTitular" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoDuplicado.titular.direccion.idDireccion}"></s:textfield></span>
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
					<s:select id="idProvinciaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.titular.direccion.idProvincia"
						disabled="%{flagDisabled}" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTitular()"
						headerKey="-1" headerValue="Seleccione Provincia"
						listKey="idProvincia" listValue="nombre"
						onchange="cargarListaMunicipios(this,'idMunicipioTitular','municipioSeleccionadoTitular');
							cargarListaTipoVia(this,'tipoViaTitular','tipoViaSeleccionadaTitular');
							inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitularDuplicado);
							borrarComboPueblo('idPuebloTitular');
							borrarRestoCampos('cpTitular','numeroDireccionTitular','letraDireccionTitular','escaleraDireccionTitular','pisoDireccionTitular','puertaDireccionTitular','bloqueDireccionTitular','kmDireccionTitular','hmDireccionTitular');"/>	
				</td>

				<td align="left" nowrap="nowrap">
					<label for="idMunicipioTitular">Municipio: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
					<select id="idMunicipioTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						${stringDisabled}
						onchange="cargarListaPueblos('idProvinciaTitular', this, 'idPuebloTitular', 'puebloSeleccionadoTitular');
						seleccionarCampo('municipioSeleccionadoTitular','idMunicipioTitular');
						obtenerCodigoPostalPorMunicipio('idProvinciaTitular', this, 'cpTitular'); 
						inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitularDuplicado);
						borrarRestoCampos('cpTitular','numeroDireccionTitular','letraDireccionTitular','escaleraDireccionTitular','pisoDireccionTitular','puertaDireccionTitular','bloqueDireccionTitular','kmDireccionTitular','hmDireccionTitular');"
						style="width:200px;">
						<option value="-1">Seleccione Municipio</option>
					</select>
					<s:hidden id="municipioSeleccionadoTitular" name="tramiteTraficoDuplicado.titular.direccion.idMunicipio"/>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap">
					<label for="idPuebloTitular">Pueblo:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<select id="idPuebloTitular" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" ${stringDisabled}
						onchange="seleccionarCampo('puebloSeleccionadoTitular','idPuebloTitular');"
						style="width:200px;">
						<option value="-1">Seleccione Pueblo</option>
					</select>
					<s:hidden id="puebloSeleccionadoTitular" name="tramiteTraficoDuplicado.titular.direccion.pueblo"/>
				</td>

				<td align="left" nowrap="nowrap">
					<label for="cpTitular">Código Postal: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield id="cpTitular" disabled="%{flagDisabled}"
						name="tramiteTraficoDuplicado.titular.direccion.codPostal"
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
						cargarListaNombresVia('idProvinciaTitular', 'municipioSeleccionadoTitular', this, 'nombreViaTitular', viaTitularDuplicado);">
						<option value="-1">Seleccione Tipo Vía</option>
					</select>
					<s:hidden id="tipoViaSeleccionadaTitular" name="tramiteTraficoDuplicado.titular.direccion.idTipoVia"/>
				</td>

				<td align="left" nowrap="nowrap">
					<label for="nombreViaTitular">Nombre de vía: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap" colspan="6">
					<s:textfield id="nombreViaTitular" onblur="this.className='input2';"
						disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.titular.direccion.nombreVia"
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
					<s:textfield id="numeroDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.numero"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)"
						size="4" maxlength="4"/>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="letraDireccionTitular">Letra: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="letraDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.letra"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarLetras(event)"
						style="text-transform : uppercase" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="escaleraDireccionTitular">Escalera: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="escaleraDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.escalera" 
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="pisoDireccionTitular">Piso:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="pisoDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.planta"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="puertaDireccionTitular">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="puertaDireccionTitular" 
						name="tramiteTraficoDuplicado.titular.direccion.puerta" 
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="bloqueDireccionTitular">Bloque:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="bloqueDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.bloque"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="kmDireccionTitular">Km:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="kmDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.km"
						disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="hmDireccionTitular">Hm:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="hmDireccionTitular"
						name="tramiteTraficoDuplicado.titular.direccion.hm"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
				</td>
			</tr>
		</table>
	</div>
	<div id="idbarraCotitular">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del representante titular</td>
				<s:set var="identificacion" value="tramiteTraficoDuplicado.representanteTitular.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoDuplicado.representanteTitular.numColegiado"/>
				<td align="right">
					<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
						<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoDuplicado.representanteTitular.persona.nif"/>','<s:property value="tramiteTraficoDuplicado.representanteTitular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
					</s:if>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">REPRESENTANTE DEL TITULAR</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nifRepresentanteTitular">NIF / CIF:</label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align:left;" >
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="tramiteTraficoDuplicado.representanteTitular.persona.nif" 
									id="dniRepresentante" onblur="this.className='input2';calculaLetraNIF(this)"
									disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
									onchange="limpiarFormularioRepresentanteTitular()" size="9" maxlength="9" style="text-transform : uppercase"/>
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
										onclick="javascript:buscarIntervinienteDuplicado('Representante Titular','Titular', 'dniRepresentante')"/>
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
						onfocus="this.className='inputfocus';" name="tramiteTraficoDuplicado.representanteTitular.persona.tipoPersona"
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
						name="tramiteTraficoDuplicado.representanteTitular.persona.sexo" listValue="nombreEnum" listKey="valorEnum"
						title="Sexo Persona" headerKey="-1" headerValue="-"/>
				</td>

				<td align="left" nowrap="nowrap" colspan="2">
					<label for="apellido1RepresentanteTitular">Primer Apellido / Razón Social:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteTraficoDuplicado.representanteTitular.persona.apellido1RazonSocial"
						id="apellido1RepresentanteTitular" onblur="this.className='input2';"
						disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
						maxlength="100" />
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap">
					<label for="apellido2RepresentanteTitular">Segundo Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteTraficoDuplicado.representanteTitular.persona.apellido2"
						id="apellido2RepresentanteTitular" disabled="%{flagDisabled}"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="20" maxlength="100"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="idNombreRepresentanteTitular">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield name="tramiteTraficoDuplicado.representanteTitular.persona.nombre"
						id="idNombreRepresentanteTitular" onblur="this.className='input2';"
						disabled="%{flagDisabled}" onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="conceptoRepresentanteTitular">Concepto:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
							id="conceptoRepresentanteTitular" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" name="tramiteTraficoDuplicado.representanteTitular.conceptoRepre"
							disabled="%{flagDisabled}" listValue="nombreEnum" listKey="valorEnum"
							title="Concepto tutela" headerKey="-1" headerValue="-" onchange="cambioConceptoRepreTitular()"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="motivoRepresentanteTitular">Motivo:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
				<s:if test="tramiteTraficoDuplicado.representanteTitular.conceptoRepre == 'TUTELA O PATRIA POTESTAD'">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.representanteTitular.idMotivoTutela" listValue="nombreEnum"
						listKey="valorEnum" title="Motivo tutela" headerKey="-1" headerValue="-"/>
				</s:if>
				<s:else>
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.representanteTitular.idMotivoTutela" listValue="nombreEnum"
						listKey="valorEnum" title="Motivo tutela" headerKey="-1" headerValue="-" disabled="true"/>
				</s:else>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="3">
					<label for="datosAcrediteRepresentanteTitular">Datos del documento que acredite la facultad para actuar en nombre del Titular:</label>

				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.datosDocumento"
						id="datosAcrediteRepresentanteTitular" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="diaInicioRepresentanteTitular">Fecha inicio Tutela: </label>
				</td>
				<td colspan="6">
					<table style="align:left;">
						<tr>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.fechaInicio.dia"
									id="diaInicioRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.fechaInicio.mes"
									id="mesInicioRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.fechaInicio.anio"
									id="anioInicioRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="4" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRepresentanteTitular, document.formData.mesInicioRepresentanteTitular, document.formData.diaInicioRepresentanteTitular);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="diaFinRepresentanteTitular">Fecha fin Tutela: </label>
				</td>
				<td colspan="6">
					<table style="align:left;">
						<tr>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.fechaFin.dia"
									id="diaFinRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.fechaFin.mes"
									id="mesFinRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.representanteTitular.fechaFin.anio"
									id="anioFinRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="4" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRepresentanteTitular, document.formData.mesFinRepresentanteTitular, document.formData.diaFinRepresentanteTitular);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<script>
	var viaTitularDuplicado = new BasicContentAssist(document.getElementById('nombreViaTitular'), [], null, true);
	recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
	recargarComboTipoVia('idProvinciaTitular','tipoViaTitular','tipoViaSeleccionadaTitular');
	recargarComboPueblos('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
	recargarNombreVias('idProvinciaTitular', 'municipioSeleccionadoTitular', 'tipoViaSeleccionadaTitular','nombreViaTitular', viaTitularDuplicado);
</script>