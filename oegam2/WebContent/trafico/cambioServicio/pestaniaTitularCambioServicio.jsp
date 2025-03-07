<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 

<s:hidden name="tramiteTraficoCambServ.titular.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoCambServ.titular.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoCambServ.titular.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoCambServ.titular.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoCambServ.titular.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoCambServ.titular.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoCambServ.titular.persona.tipoDocumentoAlternativo" />

<s:hidden name="tramiteTraficoCambServ.titular.persona.estado" />
<s:hidden id ="idHiddenDireccionTitularCambioServicio" name="tramiteTraficoCambServ.titular.direccion.idDireccion" />
<s:hidden name="tramiteTraficoCambServ.titular.fechaInicio.dia" />
<s:hidden name="tramiteTraficoCambServ.titular.fechaInicio.mes" />
<s:hidden name="tramiteTraficoCambServ.titular.fechaInicio.anio" />
<s:hidden name="tramiteTraficoCambServ.titular.fechaFin.dia" />
<s:hidden name="tramiteTraficoCambServ.titular.idDireccion" />
<s:hidden name="tramiteTraficoCambServ.titular.fechaFin.mes" />
<s:hidden name="tramiteTraficoCambServ.titular.fechaFin.anio" />

<s:hidden name="tramiteTraficoCambServ.titular.numColegiado" />
<s:hidden name="tramiteTraficoCambServ.titular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@Titular}" />
<s:hidden name="tramiteTraficoCambServ.titular.autonomo" />
<s:hidden name="tramiteTraficoCambServ.titular.cambioDomicilio" />
<s:hidden name="tramiteTraficoCambServ.titular.numInterviniente" />
<s:hidden name="tramiteTraficoCambServ.titular.nifInterviniente"/>
<s:hidden name="tramiteTraficoCambServ.titular.numExpediente"/>
<s:hidden name="tramiteTraficoCambServ.titular.idContrato"/>
<s:hidden name="tramiteTraficoCambServ.titular.persona.anagrama"/>

<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.fechaCaducidadNif.dia" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.fechaCaducidadNif.mes" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.fechaCaducidadNif.anio" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.tipoDocumentoAlternativo" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.fechaCaducidadAlternativo.anio" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.nifInterviniente"/>
<s:hidden name="tramiteTraficoCambServ.representanteTitular.numColegiado"/>
<s:hidden name="tramiteTraficoCambServ.representanteTitular.numExpediente"/>
<s:hidden name="tramiteTraficoCambServ.representanteTitular.idContrato"/>
<s:hidden name="tramiteTraficoCambServ.representanteTitular.tipoInterviniente" value="%{@org.gestoresmadrid.core.model.enumerados.TipoInterviniente@RepresentanteTitular}" />
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.direccion.idDireccion"/>
<s:hidden name="tramiteTraficoCambServ.representanteTitular.persona.anagrama"/>

<div class="contenido">
	<div id="divTitularCambServ">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del titular</td>
				<s:set var="identificacion" value="tramiteTraficoCambServ.titular.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoCambServ.titular.numColegiado"/>
				<td align="right">
					<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
						<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoCambServ.titular.persona.nif"/>','<s:property value="tramiteTraficoCambServ.titular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
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
				<td align="left" nowrap="nowrap" >
					<label for="dniTitular">NIF / CIF: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align:left;">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="dniTitular" disabled="%{flagDisabled}"
									name="tramiteTraficoCambServ.titular.persona.nif" onblur="this.className='input2';calculaLetraNIF(this)" 
									onchange="limpiarFormularioTitular()" onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" id="botonBuscarTitular" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarIntervinienteCambioServ('Titular','Titular')"/>
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
						name="tramiteTraficoCambServ.titular.persona.tipoPersona" listValue="nombreEnum"
						listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="sexoTitular">Sexo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="sexoTitular" name="tramiteTraficoCambServ.titular.persona.sexo"
						disabled="%{flagDisabled}" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
						listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<label for="primerApellidoTitular">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="primerApellidoTitular" name="tramiteTraficoCambServ.titular.persona.apellido1RazonSocial"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:16em;" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="segundoApellidoTitular">Segundo Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="segundoApellidoTitular" name="tramiteTraficoCambServ.titular.persona.apellido2"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="30" maxlength="100"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="nombreTitular">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield id="nombreTitular" name="tramiteTraficoCambServ.titular.persona.nombre"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
				</td>
			</tr>
		</table>

		<s:if test="tramiteTraficoCambServ.titular.direccion.idDireccion != null">
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
											onclick="javascript:seleccionarDireccion('<s:property value="tramiteTraficoCambServ.titular.persona.nif"/>','<s:property value="tramiteTraficoCambServ.titular.numColegiado"/>','TitularCambServ');"/>
									</s:if>
								</td>
								<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
									<td>
										<label>Id dirección</label>
										<span><s:textfield id="idDireccionTitular" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoCambServ.titular.direccion.idDireccion}"></s:textfield></span>
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
						name="tramiteTraficoCambServ.titular.direccion.idProvincia"
						disabled="%{flagDisabled}" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTitular()"
						headerKey="-1" headerValue="Seleccione Provincia"
						listKey="idProvincia" listValue="nombre"
						onchange="cargarListaMunicipios(this,'idMunicipioTitular','municipioSeleccionadoTitular');
							cargarListaTipoVia(this,'tipoViaTitular','tipoViaSeleccionadaTitular');
							inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitular);
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
					<s:hidden id="municipioSeleccionadoTitular" name="tramiteTraficoCambServ.titular.direccion.idMunicipio"/>
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
					<s:hidden id="puebloSeleccionadoTitular" name="tramiteTraficoCambServ.titular.direccion.pueblo"/>
				</td>

				<td align="left" nowrap="nowrap">
					<label for="cpTitular">Código Postal: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield id="cpTitular" disabled="%{flagDisabled}"
						name="tramiteTraficoCambServ.titular.direccion.codPostal"
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
						<option value="-1">Seleccione Tipo Via</option>
					</select>
					<s:hidden id="tipoViaSeleccionadaTitular" name="tramiteTraficoCambServ.titular.direccion.idTipoVia"/>

				</td>

				<td align="left" nowrap="nowrap">
					<label for="nombreViaTitular">Nombre de vía: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap" colspan="6">
					<s:textfield id="nombreViaTitular" onblur="this.className='input2';"
						disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
						name="tramiteTraficoCambServ.titular.direccion.nombreVia"
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
						name="tramiteTraficoCambServ.titular.direccion.numero"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)"
						size="4" maxlength="4"/>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="letraDireccionTitular">Letra:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="letraDireccionTitular"
						name="tramiteTraficoCambServ.titular.direccion.letra"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarLetras(event)"
						style="text-transform : uppercase" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="escaleraDireccionTitular">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="escaleraDireccionTitular"
						name="tramiteTraficoCambServ.titular.direccion.escalera"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="pisoDireccionTitular">Piso: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="pisoDireccionTitular"
						name="tramiteTraficoCambServ.titular.direccion.planta"
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
						name="tramiteTraficoCambServ.titular.direccion.puerta"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="bloqueDireccionTitular">Bloque:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="bloqueDireccionTitular"
						name="tramiteTraficoCambServ.titular.direccion.bloque"
						disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="kmDireccionTitular">Km:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="kmDireccionTitular"
						name="tramiteTraficoCambServ.titular.direccion.km"
						disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="hmDireccionTitular">Hm:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="hmDireccionTitular"
						name="tramiteTraficoCambServ.titular.direccion.hm"
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
				<s:set var="identificacion" value="tramiteTraficoCambServ.representanteTitular.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoCambServ.representanteTitular.numColegiado"/>
				<td align="right">
					<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
						<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoCambServ.representanteTitular.persona.nif"/>','<s:property value="tramiteTraficoCambServ.representanteTitular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
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
					<label for="dniRepresentante">NIF / CIF:</label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align:left;">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="tramiteTraficoCambServ.representanteTitular.persona.nif"
									id="dniRepresentante" onblur="this.className='input2';calculaLetraNIF(this)"
									disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
									onchange="limpiarFormularioRepresentanteTitular()" size="9" maxlength="9" style="text-transform : uppercase"/>
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
										onclick="javascript:buscarIntervinienteCambioServ('Representante Titular','Titular')"/>
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
						onfocus="this.className='inputfocus';" name="tramiteTraficoCambServ.representanteTitular.persona.tipoPersona"
						listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona"
						headerKey="-1" headerValue="-" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="sexoRepresentante">Sexo:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:select id="sexoRepresentante" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						onblur="this.className='input2';" disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
						name="tramiteTraficoCambServ.representanteTitular.persona.sexo" listValue="nombreEnum" listKey="valorEnum"
						title="Sexo Persona" headerKey="-1" headerValue="-"/>
				</td>

				<td align="left" nowrap="nowrap" colspan="2">
					<label for="apellido1RepresentanteTitular">Primer Apellido / Razón Social:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteTraficoCambServ.representanteTitular.persona.apellido1RazonSocial"
						id="apellido1RepresentanteTitular" onblur="this.className='input2';"
						disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
						maxlength="100"	/>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap">
					<label for="apellido2RepresentanteTitular">Segundo Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteTraficoCambServ.representanteTitular.persona.apellido2"
						id="apellido2RepresentanteTitular" disabled="%{flagDisabled}"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="20" maxlength="100"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="idNombreRepresentanteTitular">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield name="tramiteTraficoCambServ.representanteTitular.persona.nombre"
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
							onfocus="this.className='inputfocus';" name="tramiteTraficoCambServ.representanteTitular.conceptoRepre"
							disabled="%{flagDisabled}" listValue="nombreEnum" listKey="valorEnum"
							title="Concepto tutela" headerKey="-1" headerValue="-" onchange="cambioConceptoRepreTitular()"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="motivoRepresentanteTitular">Motivo:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
				<s:if test="tramiteTraficoCambServ.representanteTitular.conceptoRepre == 'TUTELA O PATRIA POTESTAD'">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoCambServ.representanteTitular.idMotivoTutela" listValue="nombreEnum"
						listKey="valorEnum" title="Motivo tutela" headerKey="-1" headerValue="-"/>
				</s:if>
				<s:else>
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentanteTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoCambServ.representanteTitular.idMotivoTutela" listValue="nombreEnum"
						listKey="valorEnum" title="Motivo tutela" headerKey="-1" headerValue="-" disabled="true"/>
				</s:else>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="3">
					<label for="datosAcrediteRepresentanteTitular">Datos del documento que acredite la facultad para actuar en nombre del Titular:</label>

				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.datosDocumento"
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
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.fechaInicio.dia"
									id="diaInicioRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.fechaInicio.mes"
									id="mesInicioRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.fechaInicio.anio"
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
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.fechaFin.dia"
									id="diaFinRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.fechaFin.mes"
									id="mesFinRepresentanteTitular" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
									size="2" maxlength="2"/>
							</td>
							<td width="2">/</td>
							<td align="left" nowrap="nowrap" width="20">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoCambServ.representanteTitular.fechaFin.anio"
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
	var viaTitularCambioServ = new BasicContentAssist(document.getElementById('nombreViaTitular'), [], null, true);
	recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
	recargarComboTipoVia('idProvinciaTitular','tipoViaTitular','tipoViaSeleccionadaTitular');
	recargarComboPueblos('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
	recargarNombreVias('idProvinciaTitular', 'municipioSeleccionadoTitular', 'tipoViaSeleccionadaTitular','nombreViaTitular', viaTitularCambioServ);
</script>