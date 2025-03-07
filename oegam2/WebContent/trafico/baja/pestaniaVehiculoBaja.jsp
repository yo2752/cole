<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">

	<s:hidden name="tramiteTraficoBaja.vehiculoDto.fechaMatriculacion.hora" />
	<s:hidden
		name="tramiteTraficoBaja.vehiculoDto.fechaMatriculacion.minuto" />
	<s:hidden
		name="tramiteTraficoBaja.vehiculoDto.fechaMatriculacion.segundo" />
	<s:hidden id="idDireccion"
		name="tramiteTraficoBaja.vehiculoDto.direccion.idDireccion" />
	<s:hidden id="idVehiculo"
		name="tramiteTraficoBaja.vehiculoDto.idVehiculo" />

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Vehiculo</td>
			<td align="right"><img title="Ver evolución"
				onclick="consultaEvolucionVehiculo('idVehiculo', '<s:property value="tramiteTraficoBaja.vehiculoDto.numColegiado"/>');"
				style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
				alt="Ver evolución" src="img/history.png"></td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">VEHÍCULO</span></td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="11%"><label
				for="matriculaVehiculo">Matrícula: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<table style="align: left;">
					<tr>
						<td align="left" nowrap="nowrap"><s:textfield
								disabled="%{flagDisabled}"
								name="tramiteTraficoBaja.vehiculoDto.matricula" id="idMatricula"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								onkeypress="return validarMatricula(event)"
								style="text-transform : uppercase"
								onChange="return validarMatricula_alPegar(event)"
								onmousemove="return validarMatricula_alPegar(event)" size="15"
								maxlength="9"
								readonly="%{tramiteTraficoBaja.vehiculoDto.idVehiculo != null}" />
						</td>
						<td align="left" nowrap="nowrap"><input type="button"
							class="boton-persona"
							style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
							onclick="javascript:buscarVehiculoBaja('idMatricula')" /></td>

						<s:if
							test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<td align="left" nowrap="nowrap"><label
								for="idVehiculoLabel">Id Vehículo: </label></td>

							<td align="left" nowrap="nowrap">
								<s:textfield
									id="idVehiculoText"
									value="%{tramiteTraficoBaja.vehiculoDto.idVehiculo}"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="10"
									cssClass="campo_disabled" name="idDireccion_admin" />
							</td>
						</s:if>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="fechaMatriculacionVehiculo">Fecha de Matriculación: <span
					class="naranja">*</span></label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								disabled="%{flagDisabled}" id="diaMatriculacionVehiculo"
								name="tramiteTraficoBaja.vehiculoDto.fechaMatriculacion.dia"
								onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>

						<td width="1%">/</td>

						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								disabled="%{flagDisabled}" id="mesMatriculacionVehiculo"
								name="tramiteTraficoBaja.vehiculoDto.fechaMatriculacion.mes"
								onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>

						<td width="1%">/</td>

						<td align="left" nowrap="nowrap" width="5%"><s:textfield
								disabled="%{flagDisabled}" id="anioMatriculacionVehiculo"
								name="tramiteTraficoBaja.vehiculoDto.fechaMatriculacion.anio"
								onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap"><a href="javascript:;"
							onClick="self.gfPop.calendarioSeleccionado = 'top.quitarTasasSiMasDeQuince(\'anioMatriculacionVehiculo\',\'mesMatriculacionVehiculo\',\'diaMatriculacionVehiculo\');';if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatriculacionVehiculo, document.formData.mesMatriculacionVehiculo, document.formData.diaMatriculacionVehiculo);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="middle" src="img/ico_calendario.gif" ${displayDisabled}
								width="15" height="16" border="0" alt="Calendario" />
						</a></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="declaracionNOResiduoSolido">Es siniestro total:</label>
			</td>
			<td>
				<s:checkbox name="tramiteTraficoBaja.vehiculoDto.esSiniestro"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap" width="140"
				style="vertical-align: middle;"><label
				for="diaCaducidadTarjetaITV">Fecha caducidad ITV:</label></td>
			<td>
				<table border="0">
					<tr>
						<td align="left" nowrap="nowrap" width="1%"><s:textfield
								disabled="%{flagDisabled}" id="diaCaducidadTarjetaITV"
								name="tramiteTraficoBaja.vehiculoDto.fechaItv.dia"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%"><s:textfield
								disabled="%{flagDisabled}" id="mesCaducidadTarjetaITV"
								name="tramiteTraficoBaja.vehiculoDto.fechaItv.mes"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%"><s:textfield
								disabled="%{flagDisabled}" id="anioCaducidadTarjetaITV"
								name="tramiteTraficoBaja.vehiculoDto.fechaItv.anio"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td id="calendarioVehiculoITV" align="left" nowrap="nowrap"><a
							href="javascript:;"
							onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCaducidadTarjetaITV, document.formData.mesCaducidadTarjetaITV, document.formData.diaCaducidadTarjetaITV);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="middle" src="img/ico_calendario.gif" ${displayDisabled}
								width="15" height="16" border="0" alt="Calendario" />
						</a></td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelBastidor">Bastidor:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield
					disabled="%{flagDisabled}"
					name="tramiteTraficoBaja.vehiculoDto.bastidor" id="idBastidor"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarBastidor(event)"
					style="text-transform : uppercase" size="22" maxlength="21" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="idTipoVehiculo">Tipo de Vehículo:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idTipoVehiculo"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T7')"
					headerKey="-1" headerValue="Seleccione Tipo"
					name="tramiteTraficoBaja.vehiculoDto.tipoVehiculo"
					listKey="tipoVehiculo" listValue="descripcion" />
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left" style="">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idServicioTrafico">Servicio:
					<span class="naranja">*</span>
				</label>
			</td>

			<td align="left" nowrap="nowrap"><s:select
					id="idServicioTrafico" disabled="%{flagDisabled}"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="tramiteTraficoBaja.vehiculoDto.servicioTrafico.idServicio"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getServiciosTrafico('T1')"
					listKey="idServicio" listValue="descripcion" headerKey="-1"
					headerValue="Servicio" onchange="habilitarCEMA();" /></td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="idMma">MMA: </label>
			</td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="idMma"
					name="tramiteTraficoBaja.vehiculoDto.pesoMma"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="7" maxlength="6"
					onkeypress="return validarNumerosEnteros(event)" /></td>
		</tr>

		<s:if
			test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && tramiteTraficoBaja.vehiculoDto.direccion.idDireccion != null">
			<tr>
				<td><label>ID Direción vehículo</label> <span><s:textfield
							cssClass="campo_disabled" name="idDireccion_admin_vehiculo"
							readonly="true"
							value="%{tramiteTraficoBaja.vehiculoDto.direccion.idDireccion}"></s:textfield>
				</span></td>
				<td>
					<label>Id Dirección titular</label>
					<span><s:textfield
							id="idDireccionTitular" cssClass="campo_disabled"
							value="%{tramiteTraficoBaja.titular.direccion.idDireccion}"
							readonly="true"
							name="tramiteTraficoBaja.titular.direccion.idDireccion}"></s:textfield>
					</span>
				</td>
			</tr>
		</s:if>

		<tr>
			<td align="left" nowrap="nowrap"><label
				for="idProvinciaVehiculo">Provincia: </label></td>

			<td align="left" nowrap="nowrap">
				<s:select
					id="idProvinciaVehiculo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTraficoBaja.vehiculoDto.direccion.idProvincia"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasVehiculo()"
					headerKey="-1" headerValue="Seleccione Provincia"
					listKey="idProvincia" listValue="nombre"
					onchange="cargarListaMunicipios(this,'idMunicipioVehiculo','municipioSeleccionadoVehiculo');
					cargarListaTipoVia(this,'tipoViaVehiculo','tipoViaSeleccionadaVehiculo');
					inicializarTipoVia('tipoViaVehiculo','nombreViaVehiculo',viaVehiculoBaja);
					borrarComboPueblo('idPuebloVehiculo');
					borrarRestoCampos('codigoPostalVehiculo','numeroDireccionVehiculo','letraDireccionVehiculo','escaleraDireccionVehiculo','pisoDireccionVehiculo','puertaDireccionVehiculo','bloqueDireccionVehiculo','kmDireccionVehiculo','hmDireccionVehiculo');" />
			</td>

			<td align="left" nowrap="nowrap"><label
				for="idMunicipioVehiculo">Municipio: </label></td>

			<td align="left" nowrap="nowrap" colspan="6">
				<select
					id="idMunicipioVehiculo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="cargarListaPueblos('idProvinciaVehiculo', this, 'idPuebloVehiculo', 'puebloSeleccionadoVehiculo');
						seleccionarCampo('municipioSeleccionadoVehiculo','idMunicipioVehiculo');
						obtenerCodigoPostalPorMunicipio('idProvinciaVehiculo', this, 'codigoPostalVehiculo');
						inicializarTipoVia('tipoViaVehiculo','nombreViaVehiculo',viaVehiculoBaja);
						borrarRestoCampos('codigoPostalVehiculo','numeroDireccionVehiculo','letraDireccionVehiculo','escaleraDireccionVehiculo','pisoDireccionVehiculo','puertaDireccionVehiculo','bloqueDireccionVehiculo','kmDireccionVehiculo','hmDireccionVehiculo');"
					style="width: 200px;">
					<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoVehiculo" name="tramiteTraficoBaja.vehiculoDto.direccion.idMunicipio" />
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloVehiculo">Pueblo:</label>
			</td>

			<td align="left" nowrap="nowrap" width="42%"><select
				id="idPuebloVehiculo" ${stringDisabled}
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';"
				onchange="seleccionarCampo('puebloSeleccionadoVehiculo','idPuebloVehiculo');"
				style="width: 200px;">
					<option value="-1">Seleccione Pueblo</option>
			</select> <s:hidden id="puebloSeleccionadoVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.pueblo" /></td>

			<td align="left" nowrap="nowrap"><label
				for="codigoPostalVehiculo">Código Postal: </label></td>

			<td align="left" nowrap="nowrap" colspan="6"><s:textfield
					disabled="%{flagDisabled}" id="codigoPostalVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.codPostal"
					onkeypress="return validarNumeros(event)"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5" />
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap"><label for="tipoViaVehiculo">Tipo
					de vía: </label></td>

			<td align="left" nowrap="nowrap"><select id="tipoViaVehiculo"
				onblur="this.className='input2';" ${stringDisabled}
				onfocus="this.className='inputfocus';"
				onchange="seleccionarCampo('tipoViaSeleccionadaVehiculo','tipoViaVehiculo');
								 cargarListaNombresVia('idProvinciaVehiculo', 'municipioSeleccionadoVehiculo', this, 'nombreViaVehiculo',viaVehiculoBaja);">
					<option value="-1">Seleccione Tipo Via</option>
			</select> <s:hidden id="tipoViaSeleccionadaVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.idTipoVia" /></td>

			<td align="left" nowrap="nowrap">
				<label for="nombreViaVehiculo">Nombre vía:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="6"><s:textfield
					disabled="%{flagDisabled}" id="nombreViaVehiculo"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					cssStyle="width:200px;position:relative;" maxlength="50"
					name="tramiteTraficoBaja.vehiculoDto.direccion.nombreVia"
					autocomplete="off" /></td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left" style="">
		<tr>
			<td align="left" nowrap="nowrap" width="7%"><label
				for="numeroDireccionVehiculo">Número: </label></td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="numeroDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.numero"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="3" /></td>

			<td align="left" nowrap="nowrap" width="7%"><label
				for="letraDireccionVehiculo">Letra: </label></td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="letraDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.letra"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarLetras(event)"
					style="text-transform : uppercase" size="4" maxlength="3" /></td>

			<td align="left" nowrap="nowrap" width="7%"><label
				for="escaleraDireccionVehiculo">Escalera: </label></td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="escaleraDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.escalera"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="3" /></td>

			<td align="left" nowrap="nowrap" width="7%"><label
				for="pisoDireccionVehiculo">Piso: </label></td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="pisoDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.planta"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="3" /></td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap"><label
				for="puertaDireccionVehiculo">Puerta: </label></td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="puertaDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.puerta"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="3" /></td>

			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionVehiculo">Bloque:</label>
			</td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="bloqueDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.bloque"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="4" maxlength="3" /></td>

			<td align="left" nowrap="nowrap"><label
				for="kmDireccionVehiculo">Km: </label></td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="kmDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.km"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4"
					maxlength="3" /></td>

			<td align="left" nowrap="nowrap">
				<label for="hmDireccionVehiculo">Hm: </label>
			</td>

			<td align="left" nowrap="nowrap"><s:textfield
					disabled="%{flagDisabled}" id="hmDireccionVehiculo"
					name="tramiteTraficoBaja.vehiculoDto.direccion.hm"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4"
					maxlength="3" /></td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<s:if
				test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esValidable(tramiteTraficoBaja)}">
				<td><input type="button" class="boton" name="bLimpiarVehiculo"
					id="bLimpiarVehiculo" value="Limpiar Formulario"
					onClick="return limpiarVehiculoAltaTramiteTraficoBaja('Vehiculo');"
					onKeyPress="this.onClick" /></td>
				<td nowrap="nowrap" width="20%"></td>
			</s:if>
		</tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultableOGuardableBaja(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bGuardarBaja" name="bGuardarBaja" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
	</table>
	<script>
		deshabilitaBasti();
		var viaVehiculoBaja = new BasicContentAssist(document
				.getElementById('nombreViaVehiculo'), [], null, true);
		recargarComboMunicipios('idProvinciaVehiculo', 'idMunicipioVehiculo',
				'municipioSeleccionadoVehiculo');
		recargarComboTipoVia('idProvinciaVehiculo', 'tipoViaVehiculo',
				'tipoViaSeleccionadaVehiculo');
		recargarComboPueblos('idProvinciaVehiculo',
				'municipioSeleccionadoVehiculo', 'idPuebloVehiculo',
				'puebloSeleccionadoVehiculo');
		recargarNombreVias('idProvinciaVehiculo',
				'municipioSeleccionadoVehiculo', 'tipoViaSeleccionadaVehiculo',
				'nombreViaVehiculo', viaVehiculoBaja);
	</script>

</div>