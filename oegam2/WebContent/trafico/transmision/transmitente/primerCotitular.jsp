<%@ taglib prefix="s" uri="/struts-tags" %>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del primer cotitular</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">PRIMER COTITULAR TRANSMITENTE</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersonaPrimerCotitularTransmitente">Tipo de persona:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="3">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaPrimerCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						value="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.tipoPersona}"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Tipo Persona"
						headerKey="-1" headerValue="-"
						disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idNifPrimerCotitularTransmitente">NIF / CIF: </label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:if test="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.nifInterviniente != null && !tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.nifInterviniente.equals('')}">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.nif"
									id="idNifPrimerCotitularTransmitente"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';"
									onchange="limpiarFormularioPrimerCotitularTransmitenteTransmision()"
									size="9" maxlength="9"/>
							</s:if>
							<s:else>
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.nif"
									id="idNifPrimerCotitularTransmitente"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';"
									size="9" maxlength="9"/>
							</s:else>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteTransmision('Cotitular Transmisión','Transmitente')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>

			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Fecha Caducidad NIF:<span class="naranja">*</span></label>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaCaducidadNif.dia" 
								id="primerCotDiaCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaCaducidadNif.mes" 
								id="primerCotMesCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaCaducidadNif.anio" 
								id="primerCotAnioCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="5" maxlength="4"/>
						</td>

						<td>
							<div id="primerCotIdFechaDni" <s:if test="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.indefinido != null &&
									tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.indefinido == true}">
									style="display:none"
									</s:if>
									>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.primerCotAnioCadNif, document.formData.primerCotMesCadNif, document.formData.primerCotDiaCadNif);return false;"
									title="Seleccionar fecha">
									<img class="PopcalTrigger"
										align="left"
										src="img/ico_calendario.gif"
										width="15" height="16"
										border="0" alt="Calendario"/>
								</a>
							</div>
						</td>

						<td width="2%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:hidden name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.tipoPersona"></s:hidden>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="primerCotOtroDocIdentidad">Documento Alternativo:</label>
			</td>
			<td>
				<s:checkbox
					id="primerCotOtroDocumentoIdentidad"
					disabled="%{flagDisabled}"
					onclick=""
					onkeypress="this.onClick"
					onchange="habilitarDocumentoAlternativo('primerCotOtroDocumentoIdentidad','primerCotTipoDocumentoAlternativo','primerCotDiaCadAlternativo','primerCotMesCadAlternativo','primerCotAnioCadAlternativo','primerCotTdFecha','primerCotIndefinido');"
					name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad">
				</s:checkbox>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="primerCotIndefinido">Indefinido:</label>
			</td>
			<td>
				<s:checkbox id="primerCotIndefinido" disabled="%{flagDisabled}"
					onclick="" onkeypress="this.onClick"
					onchange="documentoIndefinido('primerCotIndefinido', 'primerCotOtroDocumentoIdentidad', 'primerCotTipoDocumentoAlternativo', 'primerCotDiaCadNif', 'primerCotMesCadNif', 'primerCotAnioCadNif', 'primerCotDiaCadAlternativo', 'primerCotMesCadAlternativo', 'primerCotAnioCadAlternativo','primerCotIdFechaDni','primerCotTdFecha');"
					name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.indefinido">
				</s:checkbox>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="primerCotTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
			</td>
			<td>
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="primerCotTipoDocumentoAlternativo"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.tipoDocumentoAlternativo"
							listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" disabled="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
			</td>

				<td align="left" nowrap="nowrap" colspan="1">
					<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
				</td>
				<td>
					<table width=100%>
						<tr>
							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.dia" 
									id="primerCotDiaCadAlternativo"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
									!tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.mes" 
									id="primerCotMesCadAlternativo"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
									!tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.anio" 
									id="primerCotAnioCadAlternativo"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="5" maxlength="4" disabled="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
									!tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
							</td>

							<td>
								<div id="primerCotTdFecha" <s:if test="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}">
								style="display:none"
										</s:if>
										>
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.primerCotAnioCadAlternativo, document.formData.primerCotMesCadAlternativo, document.formData.primerCotDiaCadAlternativo);return false;" 
										title="Seleccionar fecha">

										<img class="PopcalTrigger"
											align="left"
											src="img/ico_calendario.gif"
											width="15" height="16"
											border="0" alt="Calendario" />
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
				<label for="sexoPrimerCotitularTransmitente">Sexo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						id="sexoPrimerCotitularTransmitente"
						onblur="this.className='input2';"
						disabled="%{flagDisabled}"
						onfocus="this.className='inputfocus';"
						name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.sexo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idApellidoRazonSocialPrimerCotitularTransmitente">Apellido o Razón Social: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.apellido1RazonSocial" 
					id="idApellidoRazonSocialPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="40" maxlength="70"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idApellido2PrimerCotitularTransmitente">Segundo Apellido: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.apellido2" 
					id="idApellido2PrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="26"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idNombrePrimerCotitularTransmitente">Nombre: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.nombre" 
					id="idNombrePrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="18"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="10%">
				<label for="diaNacPrimerCotitularTransmitente">Fecha de Nacimiento: </label>
			</td>

			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaNacimientoBean.dia" 
					id="diaNacPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2"/>
			</td>

			<td width="2">/</td>

			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaNacimientoBean.mes" 
					id="mesNacPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"
					size="2" maxlength="2"/>
			</td>

			<td width="2">/</td>

			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.fechaNacimientoBean.anio" 
					id="anioNacPrimerCotitularTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="4" maxlength="4"/>
			</td>

			<td align="left" nowrap="nowrap" width="3%">
				<a href="javascript:;" 
					onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacPrimerCotitularTransmitente, document.formData.mesNacPrimerCotitularTransmitente, document.formData.diaNacPrimerCotitularTransmitente);return false;" 
						title="Seleccionar fecha">
					<img class="PopcalTrigger" 
						align="middle" 
						src="img/ico_calendario.gif" ${displayDisabled} 
						width="15" height="16" 
						border="0" alt="Calendario"/>
				</a>
			</td>

			<td align="left" nowrap="nowrap" width="130">
				<label for="idTelefonoPrimerCotitularTransmitente">Teléfono: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.telefonos" 
					id="idTelefonoPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="9" maxlength="9"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

		<tr>
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.idDireccion != null">
				<td>
					<label>ID direccion</label>
					<span><s:textfield cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.idDireccion}"></s:textfield> </span>
				</td>
			</s:if>
		<tr>

			<td align="left" nowrap="nowrap">
				<label for="idProvinciaPrimerCotitularTransmitente">Provincia: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTransmitente()"
					headerKey="-1"
					headerValue="Seleccione Provincia"
					disabled="%{flagDisabled}"
					name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.municipio.provincia.idProvincia" 
					listKey="idProvincia" listValue="nombre"
					id="idProvinciaPrimerCotitularTransmitente"
					onchange="cargarListaMunicipios(this,'idMunicipioPrimerCotitularTransmitente','municipioSeleccionadoPrimerCotitularTransmitente');
					cargarListaTipoVia(this,'tipoViaPrimerCotitularTransmitente','tipoViaSeleccionadaPrimerCotitularTransmitente');
					inicializarTipoVia('tipoViaPrimerCotitularTransmitente','viaPrimerCotitularTransmitente',viaPrimerCotitularTransmitenteTransmision);
					borrarComboPueblo('idPuebloPrimerCotitularTransmitente');"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idMunicipioPrimerCotitularTransmitente">Municipio:</label>	
			</td>

			<td align="left" nowrap="nowrap">
				<select id="idMunicipioPrimerCotitularTransmitente"
					${stringDisabled}
					onchange="cargarListaPueblos('idProvinciaPrimerCotitularTransmitente', this, 'idPuebloPrimerCotitularTransmitente', 'puebloSeleccionadoPrimerCotitularTransmitente');
					seleccionarCampo('municipioSeleccionadoPrimerCotitularTransmitente','idMunicipioPrimerCotitularTransmitente');
					obtenerCodigoPostalPorMunicipio('idProvinciaPrimerCotitularTransmitente', this, 'codPostalPrimerCotitularTransmitente');
					inicializarTipoVia('tipoViaPrimerCotitularTransmitente','viaPrimerCotitularTransmitente',viaPrimerCotitularTransmitenteTransmision);" 
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" >
					<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoPrimerCotitularTransmitente"
					name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.municipio.idMunicipio"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="codPostalPrimerCotitularTransmitente">Código Postal: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.codPostal" 
					id="codPostalPrimerCotitularTransmitente" 
					maxlength="5" size="5" 
					onblur="this.className='input';"
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloPrimerCotitularTransmitente">Pueblo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="idPuebloPrimerCotitularTransmitente" onchange="seleccionarCampo('puebloSeleccionadoPrimerCotitularTransmitente','idPuebloPrimerCotitularTransmitente');"
					onblur="this.className='input2';"
					${stringDisabled}
					onfocus="this.className='inputfocus';">
					<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoPrimerCotitularTransmitente" 
					name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.pueblo"/>
			</td>
		</tr>

		<tr>

			<td align="left" nowrap="nowrap">
				<label for="tipoViaPrimerCotitularTransmitente">Tipo de vía: </label>
			</td>

			<td align="left" nowrap="nowrap">

				<select id="tipoViaPrimerCotitularTransmitente"
						onblur="this.className='input2';"
						${stringDisabled}
						onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('tipoViaSeleccionadaPrimerCotitularTransmitente','tipoViaPrimerCotitularTransmitente');
						cargarListaNombresVia('idProvinciaPrimerCotitularTransmitente', 'municipioSeleccionadoPrimerCotitularTransmitente', this, 'viaPrimerCotitularTransmitente', viaPrimerCotitularTransmitenteTransmision);" >
					<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaPrimerCotitularTransmitente"
					name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.tipoVia.idTipoVia"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="viaPrimerCotitularTransmitente">Nombre v&iacute;a: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="viaPrimerCotitularTransmitente" 
			 			onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						cssStyle="width:200px;position:relative;"
						maxlength="50"
						name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.nombreVia" autocomplete="off"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="numeroPrimerCotitularTransmitente">N&uacute;mero: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.numero" 
					id="numeroPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)"
					size="5" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="letraPrimerCotitularTransmitente">Letra: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.letra" 
					id="letraPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="escaleraPrimerCotitularTransmitente">Escalera: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.escalera" 
					id="escaleraPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="pisoPrimerCotitularTransmitente">Piso: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.planta" 
					id="pisoPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaPrimerCotitularTransmitente">Puerta: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.puerta" 
					id="puertaPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="bloquePrimerCotitularTransmitente">Bloque: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.bloque" 
					id="bloquePrimerCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="kmPrimerCotitularTransmitente">Km: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.puntoKilometrico" 
					id="kmPrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="3" maxlength="3"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="hmPrimerCotitularTransmitente">Hm: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.primerCotitularTransmitenteBean.persona.direccion.hm" 
					id="hmPrimerCotitularTransmitente"
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>
		</tr>
	</table>