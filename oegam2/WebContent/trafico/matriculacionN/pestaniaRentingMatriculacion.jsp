<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tramiteTrafMatrDto.arrendatario.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@Arrendatario}" />
<s:hidden name="tramiteTrafMatrDto.arrendatario.nifInterviniente" />
<s:hidden name="tramiteTrafMatrDto.arrendatario.numInterviniente" />
<s:hidden name="tramiteTrafMatrDto.arrendatario.numExpediente"></s:hidden>
<s:hidden name="tramiteTrafMatrDto.arrendatario.idContrato"></s:hidden>
<s:hidden id="idHiddenDireccionArrendatario" name = "tramiteTrafMatrDto.arrendatario.direccion.idDireccion"/>
<s:hidden id="numColegiadoArrendatario" name = "tramiteTrafMatrDto.arrendatario.numColegiado"/>

<s:hidden name="tramiteTrafMatrDto.representanteArrendatario.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@RepresentanteArrendatario}" />
<s:hidden name="tramiteTrafMatrDto.representanteArrendatario.nifInterviniente" />
<s:hidden name="tramiteTrafMatrDto.representanteArrendatario.numInterviniente" />
<s:hidden name="tramiteTrafMatrDto.representanteArrendatario.numExpediente"/>
<s:hidden name="tramiteTrafMatrDto.representanteArrendatario.idContrato"/>
<s:hidden name="tramiteTrafMatrDto.representanteArrendatario.numColegiado"/>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del arrendatario
			<s:set var="identificacion" value="tramiteTrafMatrDto.arrendatario.persona.nif"/>
			<s:set var="colegiado" value="tramiteTrafMatrDto.numColegiado"/>
			<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTrafMatrDto.arrendatario.persona.nif"/>','<s:property value="tramiteTrafMatrDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">ARRENDATARIO</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersonaTitular">Tipo de persona: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaArrendatario" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					value="%{tramiteTrafMatrDto.arrendatario.persona.tipoPersona}" listValue="nombreEnum"
					listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idNifArrendatario">NIF / CIF:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.nif"
								id="idNifArrendatario" onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" onchange="limpiarFormularioRenting()"
								size="9" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" onclick="javascript:buscarIntervinienteMatriculacion('Arrendatario','Renting','idNifArrendatario')"/>
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
							<s:textfield name="tramiteTrafMatrDto.arrendatario.persona.fechaCaducidadNif.dia"
								id="arrendatarioDiaCadNif" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.arrendatario.persona.fechaCaducidadNif.mes"
								id="arrendatarioMesCadNif" onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.arrendatario.persona.fechaCaducidadNif.anio"
								id="arrendatarioAnioCadNif" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								size="5" maxlength="4"/>
						</td>
						<td>
							<div id="arrendatarioIdFechaDni"
								<s:if test="%{tramiteTrafMatrDto.arrendatario.persona.indefinido != null && tramiteTrafMatrDto.arrendatario.persona.indefinido == true}">
									style="display:none"
								</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.arrendatarioAnioCadNif, document.formData.arrendatarioMesCadNif, document.formData.arrendatarioDiaCadNif);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</div>
						</td>
						<td width="2%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<s:hidden name="tramiteTrafMatrDto.arrendatario.persona.tipoPersona"></s:hidden>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="arrendatarioOtroDocIdentidad">Documento Alternativo:</label>
			</td>
			<td>
				<s:checkbox id="arrendatarioOtroDocumentoIdentidad" disabled="%{flagDisabled}" onclick=""
					onkeypress="this.onClick" onchange="habilitarDocumentoAlternativo('arrendatarioOtroDocumentoIdentidad','arrendatarioTipoDocumentoAlternativo','arrendatarioDiaCadAlternativo','arrendatarioMesCadAlternativo','arrendatarioAnioCadAlternativo','arrendatarioIdFecha','arrendatarioIndefinido');"
					name="tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad">
				</s:checkbox>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="arrendatarioIndefinido">Indefinido:</label>
			</td>
			<td>
				<s:checkbox id="arrendatarioIndefinido" disabled="%{flagDisabled}" onclick="" 
					onkeypress="this.onClick" onchange="documentoIndefinido('arrendatarioIndefinido', 'arrendatarioOtroDocumentoIdentidad', 'arrendatarioTipoDocumentoAlternativo', 'arrendatarioDiaCadNif', 'arrendatarioMesCadNif', 'arrendatarioAnioCadNif', 'arrendatarioDiaCadAlternativo', 'arrendatarioMesCadAlternativo', 'arrendatarioAnioCadAlternativo', 'arrendatarioIdFechaDni','arrendatarioIdFecha');"
					name="tramiteTrafMatrDto.arrendatario.persona.indefinido">
				</s:checkbox>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="arrendatarioTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
			</td>
			<td>
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="arrendatarioTipoDocumentoAlternativo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.arrendatario.persona.tipoDocumentoAlternativo"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" 
					disabled="%{tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad}"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
			</td>
			<td>
				<TABLE WIDTH=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.arrendatario.persona.fechaCaducidadAlternativo.dia"
								id="arrendatarioDiaCadAlternativo" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
								disabled="%{tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.arrendatario.persona.fechaCaducidadAlternativo.mes"
								id="arrendatarioMesCadAlternativo" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
								disabled="%{tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.arrendatario.persona.fechaCaducidadAlternativo.anio"
								id="arrendatarioAnioCadAlternativo" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
								disabled="%{tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>
							<div id="arrendatarioIdFecha"
								<s:if test="%{tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.arrendatario.persona.otroDocumentoIdentidad}">
									style="display:none"
								</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.arrendatarioAnioCadAlternativo, document.formData.arrendatarioMesCadAlternativo, document.formData.arrendatarioDiaCadAlternativo);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</div>
						</td>
						<td width="2%"></td>
					</tr>
				</TABLE>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoArrendatario">Sexo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					id="sexoArrendatario" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.sexo"
					listValue="nombreEnum" listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="apellido1">Primer Apellido / Razón Social: </label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.apellido1RazonSocial"
					id="apellido1RazonSocialArrendatario" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="70" cssStyle="width:18em;" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="apellido2">Segundo Apellido: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.apellido2"
						id="apellido2Arrendatario" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="30" maxlength="20"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="nombre">Nombre: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.nombre"
						id="nombreArrendatario" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="30" maxlength="20"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="numero">Fecha de Nacimiento: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
						 	<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.fechaNacimiento.dia"
									id="diaNacArrendatario" onkeypress="return validarDia(this,event)"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
							</td>
							<td width="1%">/</td>

							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.fechaNacimiento.mes"
									id="mesNacArrendatario" onkeypress="return validarMes(this,event)"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.persona.fechaNacimiento.anio"
									id="anioNacArrendatario" onkeypress="return validarAnio(this,event)"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacArrendatario, document.formData.mesNacArrendatario, document.formData.diaNacArrendatario);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table>

	<s:if test="tramiteTrafMatrDto.arrendatario.direccion.idDireccion != null">
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
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTrafMatrDto.arrendatario.persona.nif"/>','<s:property value="tramiteTrafMatrDto.arrendatario.numColegiado"/>','Renting');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>ID direccion</label>
									<span><s:textfield cssClass="campo_disabled" id="idDireccionAdminRenMatr" name="idDireccion_admin" readonly="true" value="%{tramiteTrafMatrDto.arrendatario.direccion.idDireccion}"></s:textfield> </span>
								</td>
							</s:if>
							<s:else>
								<s:hidden id="idDireccionAdminRenMatr" name="%{tramiteTrafMatrDto.arrendatario.direccion.idDireccion}"/>
							</s:else>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaRenting">Provincia: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasArrendatario()"
					headerKey="-1" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					headerValue="Seleccione Provincia" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.arrendatario.direccion.idProvincia"
					listKey="idProvincia" listValue="nombre" id="idProvinciaRenting"
					onchange="cargarListaMunicipios(this,'idMunicipioRenting','municipioSeleccionadoRenting');
					cargarListaTipoVia(this,'tipoViaDireccionRenting','tipoViaSeleccionadaDireccionRenting');
					inicializarTipoVia('tipoViaDireccionRenting','nombreViaDireccionRenting',viaRenting);"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioRenting">Municipio: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idMunicipioRenting" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="cargarListaPueblosDGT('idProvinciaRenting', this, 'idPuebloRenting', 'puebloSeleccionadoRenting');
					seleccionarCampo('municipioSeleccionadoRenting','idMunicipioRenting');
					inicializarTipoVia('tipoViaDireccionRenting','nombreViaDireccionRenting',viaRenting);">
						<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoRenting" name="tramiteTrafMatrDto.arrendatario.direccion.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloRenting">Pueblo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloRenting" onblur="this.className='input2';" ${stringDisabled}
					onfocus="this.className='inputfocus';" onchange="seleccionarCampo('puebloSeleccionadoRenting','idPuebloRenting');">
						<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoRenting" name="tramiteTrafMatrDto.arrendatario.direccion.puebloCorreos"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="codPostalRenting">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.codPostalCorreos"
					id="codPostalRenting" maxlength="5" size="5" onblur="this.className='input';"
					onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionRenting">Tipo de vía: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="tipoViaDireccionRenting" onblur="this.className='input2';" ${stringDisabled}
					onfocus="this.className='inputfocus';"
					onchange="seleccionarCampo('tipoViaSeleccionadaDireccionRenting','tipoViaDireccionRenting');
					cargarListaNombresVia('idProvinciaRenting', 'municipioSeleccionadoRenting', this, 'nombreViaDireccionRenting',viaRenting);">
					<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaDireccionRenting" name="tramiteTrafMatrDto.arrendatario.direccion.idTipoVia"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreViaDireccionRenting">Nombre vía pública: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="nombreViaDireccionRenting"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.arrendatario.direccion.nombreVia" cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionRenting">Número: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.numero"
					id="numeroDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionRenting">Letra/Puerta: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.letra"
					id="letraDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionRenting">Escalera: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.escalera"
					id="escaleraDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionRenting">Piso: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.planta"
					id="pisoDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionRenting">Portal: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.puerta"
					id="puertaDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionRenting">Bloque: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.bloque"
					id="bloqueDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="kmDireccionRenting">Km: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.km"
					id="kmDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionRenting">Hm: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.direccion.hm"
					id="hmDireccionRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="8" maxlength="7"/>
			</td>
		</tr>
		<s:if test="tramiteTrafMatrDto.estado == null || tramiteTrafMatrDto.estado == '' || tramiteTrafMatrDto.estado == '1'">
			<tr>
				<td align="left" nowrap="nowrap" width="12%">
					<label for="asignarPrincipalArrendatario">Asignar principal: </label>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<s:select id="asignarPrincipalArrendatario" name="asignarPrincipalArrendatario" disabled="%{flagDisabled}"
						list="#{'SI':'SI', 'NO':'NO'}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<img id="info_Asignar_Principal_Renting" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'asignarPrincipal')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cambioDomicilioArrendatario">Cambio de domicilio:</label>
			</td>	
			<td align="left" nowrap="nowrap" width="5%" style="vertical-align: middle;">&nbsp;
			<table>
				<tr>
					<td>
						<s:checkbox name="tramiteTrafMatrDto.arrendatario.cambioDomicilio"
							id="idCambioDomicilioArrendatario" disabled="%{flagDisabled}" onkeypress="this.onClick"/>
					</td>
					<td align="left" style="vertical-align: middle;">
						<img id="infoCambioDomicilioArrendatario" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'cambioDomicilio')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
					</td>
				</tr>
			</table>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="numero">Fecha de Inicio: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="4">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.fechaInicio.dia"
								id="diaInicioRenting" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.fechaInicio.mes"
								id="mesInicioRenting" onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.fechaInicio.anio"
								id="anioInicioRenting" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRenting, document.formData.mesInicioRenting, document.formData.diaInicioRenting);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="horaInicioRenting">Hora Inicio Renting </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.horaInicio"
					id="horaInicioRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="numero">Fecha de Fin: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="4">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.fechaFin.dia"
								id="diaFinRenting" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.fechaFin.mes"
								id="mesFinRenting" onkeypress="return validarMes(this,event)" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.fechaFin.anio"
								id="anioFinRenting" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRenting, document.formData.mesFinRenting, document.formData.diaFinRenting);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="horaFinRenting">Hora Fin Renting </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.arrendatario.horaFin"
					id="horaFinRenting" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
		</tr>
	</table>

	<!-- Representante del arrendatario -->

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante del arrendatario
				<s:set var="identificacion" value="tramiteTrafMatrDto.representanteArrendatario.persona.nif"/>
				<s:set var="colegiado" value="tramiteTrafMatrDto.numColegiado"/>
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right">
						<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTrafMatrDto.representanteArrendatario.persona.nif"/>','<s:property value="tramiteTrafMatrDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
					</td>
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE DEL ARRENDATARIO</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width	="10%">
				<label for="idNifRepresentanteArrendatario">NIF/NIE:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteArrendatario.persona.nif"
								id="idNifRepresentanteArrendatario" onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" size="10" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteMatriculacion('Representante Arrendatario', 'Renting', 'idNifRepresentanteArrendatario')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idNombreRepresentanteArrendatario">Nombre:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteArrendatario.persona.nombre"
					id="idNombreRepresentanteArrendatario" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:20em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idApellido1RepresentanteArrendatario">Apellido 1:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteArrendatario.persona.apellido1RazonSocial" 
					id="idApellido1RepresentanteArrendatario" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:20em;" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idApellido2RepresentanteArrendatario">Apellido 2:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteArrendatario.persona.apellido2"
					id="idApellido2RepresentanteArrendatario" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:20em;" />
			</td>
		</tr>
	</table>
	<script>
		var viaRenting = new BasicContentAssist(document.getElementById('nombreViaDireccionRenting'), [], null, true);
		recargarComboMunicipios('idProvinciaRenting','idMunicipioRenting','municipioSeleccionadoRenting');
		recargarComboTipoVia('idProvinciaRenting','tipoViaDireccionRenting','tipoViaSeleccionadaDireccionRenting');
		recargarComboLocalidadesDGT('idProvinciaRenting','municipioSeleccionadoRenting','idPuebloRenting', 'puebloSeleccionadoRenting');
		recargarNombreVias('idProvinciaRenting', 'municipioSeleccionadoRenting', 'tipoViaSeleccionadaDireccionRenting','nombreViaDireccionRenting',viaRenting);
	</script>
</div>