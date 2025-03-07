<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Modificar">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos de la Persona</td>
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('${personaModificar.nif}','${personaModificar.numColegiado}');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">PERSONA</span></td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="Nif">NIF:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:textfield name="personaModificar.nif" id="Nif" size="9" maxlength="9" onblur="this.className='input';" onfocus="this.className='inputfocus';" readonly="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaCadNif">Fecha Caducidad NIF:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="personaModificar.fechaCaducidadNif.dia" id="diaCadNif" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									onkeypress="return validarNumerosEnteros(event)" size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="personaModificar.fechaCaducidadNif.mes" id="mesCadNif" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="personaModificar.fechaCaducidadNif.anio" id="anioCadNif" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="5" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
								<div id="idFechaDni">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCadNif, document.formData.mesCadNif, document.formData.diaCadNif);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
									</a>
								</div>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoPersona">Tipo de persona:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()" id="tipoPersona"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" value="%{personaModificar.tipoPersona}"
						listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1"
						headerValue="-" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="indefinido">Indefinido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox id="indefinido" onclick="" onkeypress="this.onClick"
						onchange="documentoIndefinidoPersona('indefinido', 'diaCadNif', 'mesCadNif', 'anioCadNif', 'idFechaDni');"
						name="personaModificar.indefinido"/>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="otroDocumentoIdentidad">Documento Alternativo:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:checkbox id="otroDocumentoIdentidad" onclick="" onkeypress="this.onClick"
						onchange="documentoAlternativoPersona('otroDocumentoIdentidad','tipoDocumentoAlternativo','diaCadAlternativo','mesCadAlternativo','anioCadAlternativo','idFecha','indefinido');"
						name="personaModificar.otroDocumentoIdentidad"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoDocumentoAlternativo">Tipo de Documento Alternativo:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="tipoDocumentoAlternativo"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="personaModificar.tipoDocumentoAlternativo"
						listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="fechaCaducidadAlternativo">Fecha Caducidad Documento Alternativo:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="personaModificar.fechaCaducidadAlternativo.dia" id="diaCadAlternativo" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="personaModificar.fechaCaducidadAlternativo.mes" id="mesCadAlternativo" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="personaModificar.fechaCaducidadAlternativo.anio" id="anioCadAlternativo" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
								<div id="idFecha">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCadAlternativo, document.formData.mesCadAlternativo, document.formData.diaCadAlternativo);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
									</a>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="apellido1RazonSoc">Anagrama:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.anagrama" id="anagrama" size="5" maxlength="5" onblur="this.className='input';" onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="apellido1RazonSoc">1º Apellido o Razón Social:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.apellido1RazonSocial" id="apellido1RazonSoc" maxlength="100" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<s:if test="personaModificar.tipoPersona != 'JURIDICA'">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="apellido2">2º Apellido:</label>
					</td> 
					<td align="left" nowrap="nowrap">
						<s:textfield name="personaModificar.apellido2" id="apellido2" size="30" maxlength="100" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="nombre">Nombre:</label>
					</td> 
					<td align="left" nowrap="nowrap">
						<s:textfield name="personaModificar.nombre" id="nombre" size="20" maxlength="100" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" />
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="correoElectronico">Correo Electrónico:</label>
				</td>
				<td align="left" nowrap="nowrap">
				<s:textfield name="personaModificar.correoElectronico" id="correoElectronico" size="30" maxlength="50" onblur="this.className='input';"
					onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="telefono">Teléfono:</label>
				</td>
				<td align="left" nowrap="nowrap"> 
					<s:textfield name="personaModificar.telefonos" id="telefono" size="9" maxlength="9" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<tr>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idCodigoMandato">Código de Mandato:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="personaModificar.codigoMandato" id="idCodigoMandato" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" />
					</td>

				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<s:checkbox name="checkIdFuerzasArmadas" id="idCheckFa" onchange="checkFA()"/>
					<label id="idLabelFa" for="idFa" style="color:#6E6E6E">Identificador Fuerzas Armadas.FA:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.fa"
						id="idFa" disabled="true"
						size="7" maxlength="6"
						onkeypress="return validarNumerosEnteros(event)"/>
				</td>
			</tr>
			</s:if>
			<s:elseif test="%{personaModificar.codigoMandato!=null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<ul class="listtypenone">
							<li>Código de Mandato:</li>
							<li>
								<span class="border"><s:property value="personaModificar.codigoMandato" /></span>
								<s:hidden name="personaModificar.codigoMandato" id="idCodigoMandato" />
							</li>
						</ul>
					</td>
				</tr>
			</s:elseif>
			<s:if test="personaModificar.tipoPersona == 'JURIDICA'">
				<tr>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="subtipo">Subtipo:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoSubtipoPersonas()" id="subtipo" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" name="personaModificar.subtipoPersona" listValue="nombreEnum" listKey="valorEnum" title="Subtipo Persona" headerKey="-1" headerValue="-"/>
					</td>
				</tr>
			</s:if>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<s:if test="personaModificar.tipoPersona == 'JURIDICA'">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="seccion">Sección:</label>
					</td>
					<td align="left" nowrap="nowrap" >
						<s:textfield name="personaModificar.seccion" id="seccion" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" />
					</td>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="hoja">Hoja:</label>
					</td>
					<td align="left" nowrap="nowrap" >
						<s:textfield name="personaModificar.hoja" id="hoja" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="hojaBis">HojaBis:</label>
					</td>
					<td align="left" nowrap="nowrap" >
						<s:textfield name="personaModificar.hojaBis" id="hojaBis" size="5" maxlength="5" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="ius">Ius:</label>
					<td align="left" nowrap="nowrap" >
						<s:textfield name="personaModificar.ius" id="ius" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
				</s:if>
				<s:else>
					<td align="left" nowrap="nowrap">
						<label for="fechaNacimiento">Fecha Nacimiento:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<table>
							<tr>
								<td align="left" nowrap="nowrap">
									<s:textfield name="personaModificar.fechaNacimiento.dia" id="diaFechaNacimiento" size="2" maxlength="2"
										onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" />
								</td>
								<td>/</td>
								<td align="left" nowrap="nowrap">
									<s:textfield name="personaModificar.fechaNacimiento.mes" id="mesFechaNacimiento" size="2" maxlength="2"
									 	onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" />
								</td>
								<td>/</td>
								<td align="left" nowrap="nowrap">
									<s:textfield name="personaModificar.fechaNacimiento.anio" id="anioFechaNacimiento" size="4" maxlength="4"
										onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" />
								</td>
								<td align="left" nowrap="nowrap">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaNacimiento, document.formData.mesFechaNacimiento, document.formData.diaFechaNacimiento);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="sexo">Sexo:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()" id="sexo" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" name="personaModificar.sexo" listValue="nombreEnum" listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
					</td>
				</s:else>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="iban">Cuenta Bancaria (en formato IBAN):</label>
				</td>
				<td align="left" nowrap="nowrap">	
					<s:textfield name="personaModificar.iban" id="iban" size="30" maxlength="24"
						onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>	
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="ncorpme">Usuario Abonado Corpme:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.ncorpme" id="ncorpme" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="pirpf">Porcentaje IRPF:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.pirpf" id="pirpf" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"/>
				</td>
			</tr>
			<tr>
				<s:if test="personaModificar.tipoPersona != 'JURIDICA'">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="personaModificar.estadoCivil">Estado Civil:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosCivil()" id="personaModificar.estadoCivil"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="personaModificar.estadoCivil" listValue="nombreEnum" listKey="valorEnum" title="Estado Civil"
							headerKey="-1" headerValue="-"/>
					</td>
				</s:if>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="baja">Dar de Baja:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="baja"/>
				</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">DIRECCIÓN</span></td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr style="text-align: left;">
				<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label>Id dirección:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<span><s:textfield cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{personaModificar.direccionDto.idDireccion}"></s:textfield> </span>
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idProvincia">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getProvinciasSujetoPasivo()" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="-1"	headerValue="Seleccione Provincia"
						name="personaModificar.direccionDto.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvincia"
						onchange="cargarListaMunicipios(this,'idMunicipio','municipioSeleccionado'); cargarListaTipoVia(this,'tipoVia','tipoViaSeleccionada'); inicializarTipoVia('tipoVia','nombreVia',viaPersona); borrarComboPueblo('idPueblo'); borrarComboPuebloCorreos('idPuebloCorreos');"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idMunicipio">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="idMunicipio" onchange="cargarListaPueblos('idProvincia', this, 'idPueblo', 'puebloSeleccionado');
						seleccionarCampo('municipioSeleccionado','idMunicipio'); obtenerCodigoPostalPorMunicipio('idProvincia', this, 'codPostal');
						inicializarTipoVia('tipoVia','nombreVia',viaPersona); cargarListaPueblosDGT('idProvincia', this, 'idPuebloCorreos', 'puebloCorreosSeleccionado');"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';">
							<option value="-1">Seleccione Municipio</option>
					</select>
					<s:hidden id="municipioSeleccionado" name="personaModificar.direccionDto.idMunicipio"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPueblo">Pueblo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="idPueblo" onchange="seleccionarCampo('puebloSeleccionado','idPueblo');" style="width:200px;" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';">
							<option value="-1">Seleccione Pueblo</option>
					</select>
					<s:hidden id="puebloSeleccionado" name="personaModificar.direccionDto.pueblo"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="codPostal">Código Postal:</label> 
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.codPostal" id="codPostal" maxlength="5" size="5" onblur="this.className='input';"
						onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPuebloCorreos">Pueblo Correos:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="idPuebloCorreos" onchange="seleccionarCampo('puebloCorreosSeleccionado','idPuebloCorreos');"
						style="width:200px;" onblur="this.className='input2';" onfocus="this.className='inputfocus';">
							<option value="-1">Seleccione Pueblo</option>
					</select>
					<s:hidden id="puebloCorreosSeleccionado" name="personaModificar.direccionDto.puebloCorreos"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="codPostalCorreos">Código Postal Correos:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.codPostalCorreos" id="codPostalCorreos" maxlength="5" size="5" onblur="this.className='input';"
						onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoVia">Tipo de vía:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="tipoVia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" onchange="seleccionarCampo('tipoViaSeleccionada','tipoVia');cargarListaNombresVia('idProvincia', 'municipioSeleccionado', this, 'nombreVia',viaPersona);">
						<option value="-1">Seleccione Tipo Vía</option>
					</select>
					<s:hidden id="tipoViaSeleccionada" name="personaModificar.direccionDto.idTipoVia"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="nombreVia">Nombre vía:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.nombreVia" id="nombreVia" size="30" maxlength="50" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;" autocomplete="off"/>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="numero">Número:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.numero" id="numero" size="4" maxlength="4" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="letra">Letra:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.letra" id="letra" size="3" maxlength="3" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="escalera">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.escalera" id="escalera" size="10" maxlength="10" onblur="this.className='input';"
						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="planta">Piso:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.planta" id="planta" size="2" maxlength="2" onblur="this.className='input';"
						onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="puerta">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.puerta" id="puerta" size="2" maxlength="2" onblur="this.className='input';"
						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="bloque">Bloque:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.bloque" id="bloque" size="5" maxlength="5" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="km">Km:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.km" id="km" size="4" maxlength="4" onblur="this.className='input';"
						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="hm">Hm:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="personaModificar.direccionDto.hm" id="hm" size="4" maxlength="4" onblur="this.className='input';"
						onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
		</table>
		<table class="acciones" width="95%" align="left">
			<tr>
				<td align="center" style="size: 100%; text-align: center; list-style: none;">
					<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Actualizar Persona" onClick="return guardarPersona();"
						onKeyPress="this.onClick" />
					&nbsp;&nbsp;&nbsp; 
					<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaPersona.action';"
						onKeyPress="this.onClick" />
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
		<script>
			var viaPersona = new BasicContentAssist(document.getElementById('nombreVia'), [], null, true);
			recargarComboMunicipios('idProvincia','idMunicipio','municipioSeleccionado');
			recargarComboTipoVia('idProvincia','tipoVia','tipoViaSeleccionada');
			recargarComboPueblos('idProvincia','municipioSeleccionado','idPueblo', 'puebloSeleccionado');
			recargarNombreVias('idProvincia', 'municipioSeleccionado', 'tipoViaSeleccionada','nombreVia',viaPersona);
			recargarComboLocalidadesDGT('idProvincia','municipioSeleccionado','idPuebloCorreos', 'puebloCorreosSeleccionado');
			documentoIndefinidoPersona('indefinido', 'diaCadNif', 'mesCadNif', 'anioCadNif', 'idFechaDni');
			documentoAlternativoPersona('otroDocumentoIdentidad', 'tipoDocumentoAlternativo', 'diaCadAlternativo', 'mesCadAlternativo', 'anioCadAlternativo', 'idFecha');
			faConValor();
		</script>
	</div>
</div>