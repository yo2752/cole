<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="datosFacturacion.codTasa"></s:hidden>
<s:hidden name="datosFacturacion.tipoTramite"></s:hidden>
<s:hidden name="datosFacturacion.matricula"></s:hidden>
<s:hidden id="datosFacturacionIdDireccion" name="datosFacturacion.direccion.idDireccion"></s:hidden>
<s:hidden name="datosFacturacion.numExpediente" />
<s:hidden name="datosFacturacion.numColegiado" />

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Facturación
			<s:set var="identificacion" value="datosFacturacion.nif"/>
			<s:set var="colegiado" value="datosFacturacion.numColegiado"/>
			<td align="right">
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="datosFacturacion.nif"/>', '<s:property value="datosFacturacion.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</s:if>
			</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del titular de la facturación</span></td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="etNif">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align: left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="nifFacturacion" name="datosFacturacion.nif"
								onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" style="text-transform : uppercase" 
								onchange="limpiarFormularioFacturacion()" size="9" maxlength="9" />
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
									onclick="javascript:buscarIntervinienteFacturacionSolicitudInformacion()" />
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="tipoPersona">Tipo de persona:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaFac" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="datosFacturacion.persona.tipoPersona" listValue="nombreEnum"
					listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
       			<label for="sexoAdquiriente">Sexo: <span class="naranja">*</span></label>
       		</td>
         	<td align="left" nowrap="nowrap" >
	         	<s:select id="sexoFac" 
	         		list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
	         		disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="datosFacturacion.persona.sexo"
					listValue="nombreEnum" listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"/>
       		</td>
       		<td align="left" nowrap="nowrap" colspan="2">
				<label for="apellido1Facturacion">Primer Apellido / Razón Social:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="apellido1Facturacion" name="datosFacturacion.persona.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="etSegundoApellido">Segundo Apellido:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<s:textfield id="apellido2Fac" name="datosFacturacion.persona.apellido2"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="100" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="etNombre">Nombre:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="nombreFact" name="datosFacturacion.persona.nombre"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="100" />
			</td>
		</tr>
	</table>

	<s:if test="datosFacturacion.direccion.idDireccion != null " >	
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
										onclick="javascript:seleccionarDireccion('<s:property value="datosFacturacion.persona.nif"/>','<s:property value="datosFacturacion.numColegiado"/>','Titular');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>ID direccion</label>
									<span><s:textfield id="idDireccionFact" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{datosFacturacion.direccion.idDireccion}"></s:textfield> </span>
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
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="etProvincia">Provincia: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idProvinciaFacturacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="datosFacturacion.direccion.idProvincia"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
					headerKey="-1" headerValue="Seleccione Provincia"
					listKey="idProvincia" listValue="nombre" onchange="cargarListaMunicipios(this,'idMunicipioFacturacion','municipioSeleccionadoFacturacion');
					cargarListaTipoVia(this,'tipoViaFacturacion','tipoViaSeleccionadaFacturacion');
					inicializarTipoVia('tipoViaFacturacion','nombreViaFacturacion',viaFacturacionDuplicado);
					borrarComboPueblo('idPuebloFacturacion');" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="idMunicipioFacturacion">Municipio: <span
					class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idMunicipioFacturacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" onchange="cargarListaPueblos('idProvinciaFacturacion', this, 'idPuebloFacturacion', 'puebloSeleccionadoFacturacion');
					seleccionarCampo('municipioSeleccionadoFacturacion','idMunicipioFacturacion');
					obtenerCodigoPostalPorMunicipio('idProvinciaFacturacion', this, 'cpFacturacion'); 
					inicializarTipoVia('tipoViaFacturacion','nombreViaFacturacion',viaFacturacionDuplicado);" style="width: 200px;">
						<option value="-1">Seleccione Municipio</option> 
				</select> 
				<s:hidden id="municipioSeleccionadoFacturacion" name="datosFacturacion.direccion.idMunicipio" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="idPuebloFacturacion">Pueblo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloFacturacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" onchange="seleccionarCampo('puebloSeleccionadoFacturacion','idPuebloFacturacion');"
					style="width: 200px;">
						<option value="-1">Seleccione Pueblo</option>
				</select> 
				<s:hidden id="puebloSeleccionadoFacturacion" name="datosFacturacion.direccion.pueblo" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="cpFacturacion">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="cpFacturacion" name="datosFacturacion.direccion.codPostal"
					onkeypress="return validarNumerosEnteros(event)"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="tipoViaFacturacion">Tipo de vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="tipoViaFacturacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onchange="seleccionarCampo('tipoViaSeleccionadaFacturacion','tipoViaFacturacion');
					cargarListaNombresVia('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', this, 'nombreViaFacturacion',viaFacturacionDuplicado);">
						<option value="-1">Seleccione Tipo Via</option> 
				</select> 
				<s:hidden id="tipoViaSeleccionadaFacturacion" name="datosFacturacion.direccion.idTipoVia" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="nombreViaFacturacion">Nombre de vía:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="6">
				<s:textfield id="nombreViaFacturacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="datosFacturacion.direccion.nombreVia"
					cssStyle="width:200px;position:relative;" autocomplete="off" />
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="">
		<tr>
			<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
				<label for="numeroDireccionFacturacion">Número: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<s:textfield id="numeroDireccionFacturacion" name="datosFacturacion.direccion.numero"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarSN2(event,this)" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
				<label 	for="letraDireccionFacturacion">Letra: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="letraDireccionFacturacion" name="datosFacturacion.direccion.letra"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
				<label for="escaleraDireccionFacturacion">Escalera: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="escaleraDireccionFacturacion" name="datosFacturacion.direccion.escalera"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
				<label for="pisoDireccionFacturacion">Piso: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="pisoDireccionFacturacion" name="datosFacturacion.direccion.planta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="puertaDireccionFacturacion">Puerta: </label>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<s:textfield id="puertaDireccionFacturacion" name="datosFacturacion.direccion.puerta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="bloqueDireccionFacturacion">Bloque: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="bloqueDireccionFacturacion" name="datosFacturacion.direccion.bloque"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="kmDireccionFacturacion">Km: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="kmDireccionFacturacion" name="datosFacturacion.direccion.km"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle">
				<label for="hmDireccionFacturacion">Hm: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionFacturacion" name="datosFacturacion.direccion.hm"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table class="acciones">
		<tr>
			<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
				<input 	type="button" class="boton" name="bGuardarTitular" id="bGuardarTitular" value="Guardar Titular"
				onClick="return guardarFacturacionSolicitudInformacion();" onKeyPress="this.onClick" />
			</td>
		</tr>
		<tr>
			<td colspan="2"><img id="loadingImageFac" style="display: none; margin-left: auto; margin-right: auto;" src="img/loading.gif"></td>
		</tr>
	</table>
	
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	
</div>
<script>
	var viaFacturacionDuplicado = new BasicContentAssist(document.getElementById('nombreViaFacturacion'), [], null, true);
	recargarComboMunicipios('idProvinciaFacturacion', 'idMunicipioFacturacion', 'municipioSeleccionadoFacturacion'); 
	recargarComboTipoVia('idProvinciaFacturacion', 'tipoViaFacturacion', 'tipoViaSeleccionadaFacturacion');
	recargarComboPueblos('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', 'idPuebloFacturacion', 'puebloSeleccionadoFacturacion');
	recargarNombreVias('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', 'tipoViaSeleccionadaFacturacion', 'nombreViaFacturacion', viaFacturacionDuplicado);
</script>
