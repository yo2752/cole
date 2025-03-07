<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tramiteTrafMatrDto.titular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@Titular}" />
<s:hidden name="tramiteTrafMatrDto.titular.nifInterviniente" />
<s:hidden name="tramiteTrafMatrDto.titular.numInterviniente" />
<s:hidden name="tramiteTrafMatrDto.titular.numExpediente"></s:hidden>
<s:hidden id="numColegiadoTitular" name="tramiteTrafMatrDto.titular.numColegiado"/>
<s:hidden id="idHiddenDireccionTitular" name="tramiteTrafMatrDto.titular.direccion.idDireccion"/>
<s:hidden id="idContratoTitular" name="tramiteTrafMatrDto.titular.idContrato"/>

<s:hidden name="tramiteTrafMatrDto.representanteTitular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@RepresentanteTitular}" />
<s:hidden name="tramiteTrafMatrDto.representanteTitular.nifInterviniente" />
<s:hidden name="tramiteTrafMatrDto.representanteTitular.numInterviniente" />
<s:hidden name="tramiteTrafMatrDto.representanteTitular.numExpediente"></s:hidden>
<s:hidden id="idHiddenDireccionRepresentante" name = "tramiteTrafMatrDto.representanteTitular.direccion.idDireccion"/>
<s:hidden id="numColegiadoRepresentanteTitular" name = "tramiteTrafMatrDto.representanteTitular.numColegiado"/>
<s:hidden id="idContratoRepresentanteTitular" name = "tramiteTrafMatrDto.representanteTitular.idContrato"/>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del titular
			<s:set var="identificacion" value="tramiteTrafMatrDto.titular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTrafMatrDto.numColegiado"/>
			<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTrafMatrDto.titular.persona.nif"/>','<s:property value="tramiteTrafMatrDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
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
				<label for="tipoPersonaTitular">Tipo de persona: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					value="%{tramiteTrafMatrDto.titular.persona.tipoPersona}"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifTitular">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.nif"
								id="nifTitular" onblur="this.className='input2';calculaLetraNIF(this)" onfocus="this.className='inputfocus';"
								onchange="limpiarFormTitular()" size="9" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteMatriculacion('Titular', 'Titular', 'nifTitular')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Fecha Caducidad NIF:</label>
			</td>
			<td>
				<TABLE WIDTH=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.titular.persona.fechaCaducidadNif.dia"
								id="titularDiaCadNif" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.titular.persona.fechaCaducidadNif.mes"
								id="titularMesCadNif" onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.titular.persona.fechaCaducidadNif.anio"
								id="titularAnioCadNif" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td>
							<div id="titularIdFechaDni"
								<s:if test="%{tramiteTrafMatrDto.titular.persona.indefinido != null && tramiteTrafMatrDto.titular.persona.indefinido==true}">
									style="display:none"
								</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.titularAnioCadNif, document.formData.titularMesCadNif, document.formData.titularDiaCadNif);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</div>
						</td>
						<td width="2%"></td>
					</tr>
				</TABLE>
			</td>
		</tr>
		<s:hidden name="tramiteTrafMatrDto.titular.persona.tipoPersona"></s:hidden>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="titularOtroDocIdentidad">Documento Alternativo:</label>
			</td>
			<td>
				<s:checkbox id="titularOtroDocumentoIdentidad" disabled="%{flagDisabled}" onclick="" onkeypress="this.onClick"
					onchange="habilitarDocumentoAlternativo('titularOtroDocumentoIdentidad','titularTipoDocumentoAlternativo','titularDiaCadAlternativo','titularMesCadAlternativo','titularAnioCadAlternativo','titularIdFecha','titularIndefinido');"
					name="tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="titularIndefinido">Indefinido:</label>
			</td>
			<td>
				<s:checkbox id="titularIndefinido" disabled="%{flagDisabled}" onclick="" onkeypress="this.onClick"
					onchange="documentoIndefinido('titularIndefinido', 'titularOtroDocumentoIdentidad', 'titularTipoDocumentoAlternativo', 'titularDiaCadNif', 'titularMesCadNif', 'titularAnioCadNif', 'titularDiaCadAlternativo', 'titularMesCadAlternativo', 'titularAnioCadAlternativo', 'titularIdFechaDni','titularIdFecha');"
					name="tramiteTrafMatrDto.titular.persona.indefinido"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="titularTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
			</td>
			<td>
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="titularTipoDocumentoAlternativo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.titular.persona.tipoDocumentoAlternativo"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-"
					disabled="%{tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad}"/>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Caducidad Doc Alternativo:</label>
			</td>
			<td>
				<TABLE WIDTH=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.titular.persona.fechaCaducidadAlternativo.dia"
								id="titularDiaCadAlternativo" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" 
								disabled="%{tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.titular.persona.fechaCaducidadAlternativo.mes" 
								id="titularMesCadAlternativo" onkeypress="return validarMes(this,event)" 
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" 
								disabled="%{tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.titular.persona.fechaCaducidadAlternativo.anio"
								id="titularAnioCadAlternativo" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
								disabled="%{tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>
							<div id="titularIdFecha"
								<s:if test="%{tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.titular.persona.otroDocumentoIdentidad}">
								 	style="display:none"
								</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.titularAnioCadAlternativo, document.formData.titularMesCadAlternativo, document.formData.titularDiaCadAlternativo);return false;" title="Seleccionar fecha">
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
				<label for="sexoTitular">Sexo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					id="sexoTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.titular.persona.sexo" disabled="%{flagDisabled}" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="apellido1RazonSocialTitular">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.apellido1RazonSocial"
					id="apellido1RazonSocialTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					maxlength="70" cssStyle="width:20em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="apellido2Titular">Segundo Apellido: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.apellido2"
					id="apellido2Titular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="20" maxlength="20"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreTitular">Nombre: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.nombre"
					id="nombreTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="20" maxlength="20"/>
			</td>
		</tr>
		<tr>
      		<td align="left" nowrap="nowrap">
       			<label for="nombreTitular">Anagrama: <span class="naranja">*</span></label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:textfield name="tramiteTrafMatrDto.titular.persona.anagrama"
       				id="anagramaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
       				size="5" maxlength="4" disabled="true"/>
	       	</td>
       		<td align="left" nowrap="nowrap">
       			<label for="idCodigoMandatoAdquiriente">Código de Mandato:</label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<span class="formspan">
       				<s:property value="tramiteTrafMatrDto.titular.persona.codigoMandato" />
       			</span>
       			<s:hidden id="idCodigoMandatoAdquiriente" name="tramiteTrafMatrDto.titular.persona.codigoMandato" />
       		</td>
       	</tr>
	</table>
		
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="idFechaNacimientoTitular">Fecha de Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap" >
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.fechaNacimiento.dia"
								id="diaNacTitular" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.fechaNacimiento.mes"
								id="mesNacTitular" onkeypress="return validarMes(this,event)" 
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.fechaNacimiento.anio"
								id="anioNacTitular" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacTitular, document.formData.mesNacTitular, document.formData.diaNacTitular);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
       			<label for="estadoCivilTitular">Estado Civil: </label>
       		</td>
			<td align="left" nowrap="nowrap">
				<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.titular.persona.estadoCivil" id="estadoCivilTitular"
					list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosCivil()"
					disabled="%{flagDisabled}" listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="Estado Civil"/>							
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="telefonoTitular">Teléfono: </label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.persona.telefonos" 
					id="telefonoTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="correoElectronicoTitular">Correo Electrónico: </label>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="correoElectronicoTitular"
					name="tramiteTrafMatrDto.titular.persona.correoElectronico"  
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="40" maxlength="100"/>
			</td>
		</tr>
	</table>
	<s:if test="tramiteTrafMatrDto.titular.direccion.idDireccion != null">
		<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="100%">
					<table  style="align:left;">
						<tr>
							<td  align="left" style="vertical-align:middle" width="10em">
								<label>Seleccionar Dirección</label>
							</td>
							<td align="left" nowrap="nowrap" width="5em">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTrafMatrDto.titular.persona.nif"/>','<s:property value="tramiteTrafMatrDto.titular.numColegiado"/>','TitularMatriculacion');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>ID direccion</label>
									<span><s:textfield id="idDireccionTitular" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTrafMatrDto.titular.direccion.idDireccion}"></s:textfield> </span>
								</td>
							</s:if>
							<s:else>
								<s:hidden id="idDireccionTitular" value="%{tramiteTrafMatrDto.titular.direccion.idDireccion}"/>
							</s:else>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaTitular">Provincia: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTitular()"
					headerKey="-1" headerValue="Seleccione Provincia"
					name="tramiteTrafMatrDto.titular.direccion.idProvincia"
					listKey="idProvincia" listValue="nombre" id="idProvinciaTitular"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					onchange="cargarListaMunicipios(this,'idMunicipioTitular','municipioSeleccionadoTitular');
					document.getElementById('idProvinciaIVTM').value=this.options[this.selectedIndex].text;
					cargarListaTipoVia(this,'tipoViaDireccionTitular','tipoViaSeleccionadaDireccionTitular');
					inicializarTipoVia('tipoViaDireccionTitular','nombreViaDireccionTitular',viaTitularMatriculacion);
					borrarComboPueblo('idPuebloTitular');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioTitular">Municipio: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<select id="idMunicipioTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled} 
					onchange="cargarListaPueblosDGT('idProvinciaTitular', this, 'idPuebloTitular', 'puebloSeleccionadoTitular');
					seleccionarCampo('municipioSeleccionadoTitular','idMunicipioTitular');						
					inicializarTipoVia('tipoViaDireccionTitular','nombreViaDireccionTitular',viaTitularMatriculacion);">
						<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoTitular" name="tramiteTrafMatrDto.titular.direccion.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idPuebloTitular">Pueblo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled} 
					onchange="seleccionarCampo('puebloSeleccionadoTitular','idPuebloTitular');
					document.getElementById('idPuebloIVTM').value=this.options[this.selectedIndex].text;"
					style="width:200px;">
						<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoTitular" name="tramiteTrafMatrDto.titular.direccion.puebloCorreos"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="codPostalTitular">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.codPostalCorreos"
					id="codPostalTitular" maxlength="5" size="5" onblur="this.className='input';"
					onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionTitular">Tipo vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="tipoViaDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled} 
					onchange="seleccionarCampo('tipoViaSeleccionadaDireccionTitular','tipoViaDireccionTitular');
					cargarListaNombresVia('idProvinciaTitular', 'municipioSeleccionadoTitular', this, 'nombreViaDireccionTitular', viaTitularMatriculacion);">
						<option value="-1">Seleccione Tipo Via</option>
				</select>
		    	<s:hidden id="tipoViaSeleccionadaDireccionTitular" name="tramiteTrafMatrDto.titular.direccion.idTipoVia"/>
       		</td>
			<td align="left" nowrap="nowrap">
				<label for="nombreViaDireccionTitular">Nombre vía: <span class="naranja">*</span></label>
			</td>				        		
			<td align="left" nowrap="nowrap" colspan="3">
       			<s:textfield disabled="%{flagDisabled}" id="nombreViaDireccionTitular" 
       				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.titular.direccion.nombreVia"
	       			cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
       		</td>        
		</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionTitular">N&uacute;mero: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.numero" 
					id="numeroDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					onkeypress="return validarSN2(event,this)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionTitular">Letra/Puerta: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.letra" 
					id="letraDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionTitular">Escalera: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.escalera" 
					id="escaleraDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionTitular">Piso: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.planta" 
					id="pisoDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionTitular">Portal: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.puerta" 
					id="puertaDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionTitular">Bloque: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.bloque" 
					id="bloqueDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="kmDireccionTitular">Km: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.km" 
					id="kmDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionTitular">Hm: </label>
			</td>				        		
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.titular.direccion.hm" 
					id="hmDireccionTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)" size="8" maxlength="7"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cambioDomicilioTitular">Cambio de domicilio:</label>
			</td>	
			<td align="left" style="vertical-align: middle;">&nbsp;
				<table>
					<tr>
						<td>
							<s:checkbox name="tramiteTrafMatrDto.titular.cambioDomicilio" id="idCambioDomicilioTitular" disabled="%{flagDisabled}" onkeypress="this.onClick"/>
						</td>
						<td align="left" style="vertical-align: middle;">
							<img id="infoCambioDomicilioTitular" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'cambioDomicilio')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
						</td>	
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="autonomo">Autónomo:</label>
			</td>	
			<td align="left" nowrap="nowrap" width="5%">
				<s:checkbox name="tramiteTrafMatrDto.titular.autonomo" id="idAutonomo" 	onclick="" 
					disabled="%{flagDisabled}" onkeypress="this.onClick" onchange="habilitarAutonomo('idAutonomo','iae');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="epigrafe">IAE</label>
			</td>				        		
	       	<td align="left" nowrap="nowrap">
				<s:select name="tramiteTrafMatrDto.titular.codigoIAE" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCodigosIAE()"
					headerKey="-1" disabled="%{flagDisabled}" headerValue="Seleccione Código" listKey="Codigo_IAE" 
					listValue="descripcion" id="iae" onblur="this.className='input2';" onfocus="this.className='inputfocus';" style="width:250px"/>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>

	<table class="tablaformbasica">
		<tr>
			<td align="right">
				<s:checkbox name="marcadoConductorHabitual" id="marcadoConductorHabitual" onclick="mostrarConductorHabitual();"/>
			</td>
			<td style="vertical-align: middle;" align="left" colspan="3">
				<label for="mate">Marque para especificar datos de un conductor habitual distinto del titular</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante del titular
			<s:set var="identificacion" value="tramiteTrafMatrDto.representanteTitular.persona.nif"/>
			<s:set var="colegiado" value="tramiteTrafMatrDto.numColegiado"/>
			<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTrafMatrDto.representanteTitular.persona.nif"/>','<s:property value="tramiteTrafMatrDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
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
			<td align="left" nowrap="nowrap">
       			<label for="tipoPersonaRepresentante">Tipo de persona: </label>
       		</td>

         	<td align="left" nowrap="nowrap">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaRepresentante" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					value="%{tramiteTrafMatrDto.representanteTitular.persona.tipoPersona}"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
       		</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifRepresentante">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td  align="left" nowrap="nowrap" width="24%">
       	    	<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.nif" 
		    				id="nifRepresentante" onblur="this.className='input2';calculaLetraNIF(this)" 
		    				onfocus="this.className='inputfocus';" onchange="limpiarFormularioRepresentanteTitular()"
		    				size="10" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" onclick="javascript:buscarIntervinienteMatriculacion('Representante Titular','Titular','nifRepresentante')"/>
							</s:if>
						</td>
					</tr>
				</table>
       	    </td>
       	    <td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Fecha Caducidad Nif:<span class="naranja">*</span></label>
			</td>
			<td>
       			<TABLE WIDTH=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.representanteTitular.persona.fechaCaducidadNif.dia" 
								id="representanteTitularDiaCadNif" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.representanteTitular.persona.fechaCaducidadNif.mes" 
								id="representanteTitularMesCadNif" onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.representanteTitular.persona.fechaCaducidadNif.anio" 
								id="representanteTitularAnioCadNif" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td>
							<div id="representanteIdFechaDni" 
								<s:if test="%{tramiteTrafMatrDto.representanteTitular.persona.indefinido != null && tramiteTrafMatrDto.representanteTitular.persona.indefinido == true}">
									 style="display:none"
								</s:if>
							>
				    			<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.representanteTitularAnioCadNif, document.formData.representanteTitularMesCadNif, document.formData.representanteTitularDiaCadNif);return false;" title="Seleccionar fecha">
					    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
			    				</a>
		    				</div>
						</td>
						<td width="2%"></td>
					</tr>
				</TABLE>
			</td>
   		</tr>
   		<s:hidden name="tramiteTrafMatrDto.representanteTitular.persona.tipoPersona"></s:hidden>
		<tr>
       		<td align="left" nowrap="nowrap" colspan="1">
		        <label for="representanteTitularOtroDocIdentidad">Documento Alternativo:</label>
		    </td>
		    <td>		        			
        		<s:checkbox id="representanteTitularOtroDocumentoIdentidad" disabled="%{flagDisabled}" onclick="" 
					onkeypress="this.onClick" onchange="habilitarDocumentoAlternativo('representanteTitularOtroDocumentoIdentidad','representanteTitularTipoDocumentoAlternativo','representanteTitularDiaCadAlternativo','representanteTitularMesCadAlternativo','representanteTitularAnioCadAlternativo','representanteTitularIdFecha','representanteIndefinido');"
		        	name="tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad"/>
		    </td>
		    <td align="left" nowrap="nowrap" colspan="1">
		    	<label for="representanteIndefinido">Indefinido:</label>	
			</td>
		    <td>	        			
		        <s:checkbox id="representanteIndefinido" disabled="%{flagDisabled}" onclick="" onkeypress="this.onClick" 
					onchange="documentoIndefinido('representanteIndefinido', 'representanteTitularOtroDocumentoIdentidad', 'representanteTitularTipoDocumentoAlternativo', 'representanteTitularDiaCadNif', 'representanteTitularMesCadNif', 'representanteTitularAnioCadNif', 'representanteTitularDiaCadAlternativo', 'representanteTitularMesCadAlternativo', 'representanteTitularAnioCadAlternativo', 'representanteIdFechaDni','representanteTitularIdFecha');"
		        	name="tramiteTrafMatrDto.representanteTitular.persona.indefinido"/>
		     </td>
       </tr>
       <tr>
       		<td align="left" nowrap="nowrap" colspan="1">
       			<label for="representanteTitularTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
       		</td>
       		<td>
       		    <s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="representanteTitularTipoDocumentoAlternativo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.persona.tipoDocumentoAlternativo"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" 
					disabled="%{tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad}"/>
       		</td>
   			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
			</td>
			<td>
    			<TABLE WIDTH=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTrafMatrDto.representanteTitular.persona.fechaCaducidadAlternativo.dia" 
								id="representanteTitularDiaCadAlternativo" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" 
								disabled="%{tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.representanteTitular.persona.fechaCaducidadAlternativo.mes" 
								id="representanteTitularMesCadAlternativo" onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" 
								disabled="%{tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield name="tramiteTrafMatrDto.representanteTitular.persona.fechaCaducidadAlternativo.anio" 
								id="representanteTitularAnioCadAlternativo" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" 
								disabled="%{tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad != null && !tramiteTrafMatrDto.representanteTitular.persona.otroDocumentoIdentidad}"/>
						</td>
						<td>
							<div id="representanteTitularIdFecha" 
								<s:if test="%{tramiteTrafMatrDto.representanteTitular.persona.indefinido != null && tramiteTrafMatrDto.representanteTitular.persona.indefinido == true}">
									style="display:none"
								</s:if>
							>
				    			<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.representanteTitularAnioCadAlternativo, document.formData.representanteTitularMesCadAlternativo, document.formData.representanteTitularDiaCadAlternativo);return false;" title="Seleccionar fecha">
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
		       	<label for="sexoTitular">Sexo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					id="sexoRepresentante" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.persona.sexo" disabled="%{flagDisabled}"
					listValue="nombreEnum" listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
			 </td>
       	</tr>
       	<tr>
   			<td align="left" nowrap="nowrap">
    			<label for="apellido1Representante">Primer Apellido / Razon Social: <span class="naranja">*</span></label>
    		</td>	
   			
   			<td align="left" nowrap="nowrap" colspan="4">
      			<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.apellido1RazonSocial" 
      				id="apellido1Representante" onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:20em;" maxlength="70"/>
      		</td>       					        	
       	</tr>
        <tr>
        	<td align="left" nowrap="nowrap">
      			<label for="apellido2Representante">Segundo Apellido: </label>
      		</td>			    				    					        		
   			        	       			
    		<td align="left" nowrap="nowrap">
      			<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.apellido2" 
      				id="apellido2Representante" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
      		</td>
      		<td align="left" nowrap="nowrap">
    			<label for="nombreRepresentante">Nombre: </label>
    		</td>			    				    					        		
    		<td align="left" nowrap="nowrap">
    			<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.nombre" 
    				id="nombreRepresentante" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
    		</td>
      	</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="idFechaNacimientoRepresentante">Fecha de Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap" width="32%">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.fechaNacimiento.dia" 
								id="diaNacRepresentante" onkeypress="return validarDia(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.fechaNacimiento.mes" 
								id="mesNacRepresentante" onkeypress="return validarMes(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.persona.fechaNacimiento.anio" 
								id="anioNacRepresentante" onkeypress="return validarAnio(this,event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacRepresentante, document.formData.mesNacRepresentante, document.formData.diaNacRepresentante);return false;" title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
				    		</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="12%">
				<label for="estadoCivilRepresentante">Estado Civil: </label>
			</td>				        	       	       			
			<td align="left" nowrap="nowrap">
				<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.persona.estadoCivil" 
			       	id="estadoCivilRepresentante" list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosCivil()"
					disabled="%{flagDisabled}" listKey="valorEnum" listValue="nombreEnum" headerKey="-1"
					headerValue="Estado Civil"/>							
			</td>
		</tr>
	</table>
    <s:if test="tramiteTrafMatrDto.representanteTitular.direccion.idDireccion != null " >
		<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="100%">
					<table  style="align:left;">
						<tr>
							<td  align="left" style="vertical-align:middle" width="10em">
								<label>Seleccionar Dirección</label>
							</td>
							<td align="left" nowrap="nowrap" width="5em">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:seleccionarDireccion('<s:property value="tramiteTrafMatrDto.representanteTitular.persona.nif"/>','<s:property value="tramiteTrafMatrDto.representanteTitular.numColegiado"/>','RepresentanteTitularMatriculacion');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>ID direccion</label>
									<span><s:textfield id="idDireccionRepresentanteTitular" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTrafMatrDto.representanteTitular.direccion.idDireccion}"></s:textfield> </span>
								</td>
							</s:if>
							<s:else>
								<s:hidden id="idDireccionRepresentanteTitular" value="%{tramiteTrafMatrDto.representanteTitular.direccion.idDireccion}"/>
							</s:else>
						</tr>
					</table>
				</td>
			</tr>			
		</table>
	</s:if>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	<tr>        	       			
	      	<td align="left" nowrap="nowrap">
				<label for="idProvinciaRepresentante">Provincia: <span class="naranja">*</span></label>
			</td>
    		<td align="left" nowrap="nowrap">
				<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasRepresentante()"
					headerKey="-1" headerValue="Seleccione Provincia" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.direccion.idProvincia" 
					listKey="idProvincia" listValue="nombre" id="idProvinciaRepresentante" disabled="%{flagDisabled}"
					onchange="cargarListaMunicipios(this,'idMunicipioRepresentante','municipioSeleccionadoRepresentante');
					cargarListaTipoVia(this,'tipoViaRepresentanteTitular','tipoViaSeleccionadaRepresentanteTitular');
					inicializarTipoVia('tipoViaRepresentanteTitular','nombreViaRepresentanteTitular',viaRepresentante);
					borrarComboPueblo('idPuebloRepresentante');"/>	
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioRepresentante">Municipio: <span class="naranja">*</span></label>	
			</td>				        		
       		<td align="left" nowrap="nowrap">			
				<select id="idMunicipioRepresentante" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 	${stringDisabled}
					onchange="cargarListaPueblosDGT('idProvinciaRepresentante', this, 'idPuebloRepresentante', 'puebloSeleccionadoRepresentante');
					seleccionarCampo('municipioSeleccionadoRepresentante','idMunicipioRepresentante');
					inicializarTipoVia('tipoViaRepresentanteTitular','nombreViaRepresentanteTitular',viaRepresentante);">
						<option value="-1">Seleccione Municipio</option>
				</select>						  
		    	<s:hidden id="municipioSeleccionadoRepresentante" name="tramiteTrafMatrDto.representanteTitular.direccion.idMunicipio"/>
			</td>
		</tr>
		<tr> 
			<td align="left" nowrap="nowrap">
				<label for="idPuebloRepresentante">Pueblo: </label>			
			</td>				        		
       		<td align="left" nowrap="nowrap">				
				<select id="idPuebloRepresentante" style="width:200px;" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('puebloSeleccionadoRepresentante','idPuebloRepresentante');">
						<option value="-1">Seleccione Pueblo</option>
				</select>						  
			   	<s:hidden id="puebloSeleccionadoRepresentante" name="tramiteTrafMatrDto.representanteTitular.direccion.puebloCorreos"/>
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="codPostalRepresentante">Código Postal: <span class="naranja">*</span></label>
			</td>			    				    					        		
    		<td align="left" nowrap="nowrap">
             	<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.codPostalCorreos" 
             		id="codPostalRepresentante" maxlength="5" size="5" onblur="this.className='input';"
             		onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" />
			</td>		
        </tr>
        <tr>        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="tipoViaRepresentanteTitular">Tipo vía: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap" >
   				<select id="tipoViaRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('tipoViaSeleccionadaRepresentanteTitular','tipoViaRepresentanteTitular');
					cargarListaNombresVia('idProvinciaRepresentante', 'municipioSeleccionadoRepresentante', this, 'nombreViaRepresentanteTitular',viaRepresentante);">
						<option value="-1">Seleccione Tipo Via</option>
				</select>
		    	<s:hidden id="tipoViaSeleccionadaRepresentanteTitular"	name="tramiteTrafMatrDto.representanteTitular.direccion.idTipoVia"/>
       		</td>
			<td align="left" nowrap="nowrap">
   				<label for="nombreViaRepresentanteTitular">Nombre vía: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap" colspan="2">
       			<s:textfield disabled="%{flagDisabled}" id="nombreViaRepresentanteTitular"
       				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.direccion.nombreVia"
	       			cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
       		</td>        
		</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
        <tr>    			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="numeroDireccionRepresentanteTitular">Número: </label>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.numero" 
	   				id="numeroDireccionRepresentanteTitular" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)"
	   				size="6" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="letraDireccionRepresentanteTitular">Letra: </label>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.letra" 
	   				id="letraDireccionRepresentanteTitular" onblur="this.className='input2';" 
   					onfocus="this.className='inputfocus';" size="6" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="escaleraDireccionRepresentanteTitular">Escalera: </label>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.escalera" 
	   				id="escaleraDireccionRepresentanteTitular" onblur="this.className='input2';" 
   					onfocus="this.className='inputfocus';" size="6" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="pisoDireccionRepresentanteTitular">Piso: </label>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.planta" 
   					id="pisoDireccionRepresentanteTitular" onblur="this.className='input2';" 
   					onfocus="this.className='inputfocus';" size="6" maxlength="5"/>
   			</td>
      	</tr>
      	<tr>    			
   			<td align="left" nowrap="nowrap">
   				<label for="puertaDireccionRepresentanteTitular">Puerta: </label>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.puerta" 
   					id="puertaDireccionRepresentanteTitular" onblur="this.className='input2';" 
   					onfocus="this.className='inputfocus';" size="6" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="bloqueDireccionRepresentanteTitular">Bloque: </label>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.bloque" 
	   				id="bloqueDireccionRepresentanteTitular" onblur="this.className='input2';" 
   					onfocus="this.className='inputfocus';" size="6" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="kmDireccionRepresentanteTitular">Km: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.km" 
	   				id="kmDireccionRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				onkeypress="return validarNumerosEnteros(event)" size="6" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="hmDireccionRepresentanteTitular">Hm: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.representanteTitular.direccion.hm" 
	   				id="hmDireccionRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
   					onkeypress="return validarNumerosEnteros(event)" size="8" maxlength="7"/>
   			</td>
      	</tr>
	</table>
	
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	
       	<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="conceptoRepresentanteTitular">Concepto: </label>
       		</td>				        		
       		<td align="left" nowrap="nowrap">
       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
					id="conceptoRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.conceptoRepre" disabled="%{flagDisabled}"
					listValue="nombreEnum" listKey="valorEnum" title="Concepto tutela"
					headerKey="-1" headerValue="-" onchange="cambioConceptoRepresentanteTitular()"/>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<label for="motivoRepresentanteTitular">Motivo: </label>
       		</td>				        		
       		<td align="left" nowrap="nowrap">
       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
					id="motivoRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.representanteTitular.idMotivoTutela" listValue="nombreEnum"
					listKey="valorEnum" title="Motivo tutela" headerKey="-1" headerValue="-" disabled="true"/>
       		</td>
       	</tr> 			        			        				        	
	</table>
		
</div>

<script>
	if (document.getElementById("permisoEspecial").value == "false") {
		habilitarAutonomo('idAutonomo', 'iae');
	}
	var viaTitularMatriculacion = new BasicContentAssist(document.getElementById('nombreViaDireccionTitular'), [], null, true); 
	recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
	recargarComboTipoVia('idProvinciaTitular','tipoViaDireccionTitular','tipoViaSeleccionadaDireccionTitular');
	recargarComboLocalidadesDGT('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
	recargarNombreVias('idProvinciaTitular', 'municipioSeleccionadoTitular', 'tipoViaSeleccionadaDireccionTitular','nombreViaDireccionTitular',viaTitularMatriculacion);

	cambioConceptoRepresentanteTitular();
	var viaRepresentante = new BasicContentAssist(document.getElementById('nombreViaRepresentanteTitular'), [], null, true);
	recargarComboMunicipios('idProvinciaRepresentante','idMunicipioRepresentante','municipioSeleccionadoRepresentante');
	recargarComboTipoVia('idProvinciaRepresentante','tipoViaRepresentanteTitular','tipoViaSeleccionadaRepresentanteTitular');
	recargarComboLocalidadesDGT('idProvinciaRepresentante','municipioSeleccionadoRepresentante','idPuebloRepresentante', 'puebloSeleccionadoRepresentante');
	recargarNombreVias('idProvinciaRepresentante', 'municipioSeleccionadoRepresentante', 'tipoViaSeleccionadaRepresentanteTitular','nombreViaRepresentanteTitular', viaRepresentante);
</script>