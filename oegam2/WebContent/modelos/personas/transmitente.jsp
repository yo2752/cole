<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Transmitente:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNifSujPasico">N.I.F<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNifTransmitente" name="modeloDto.transmitente.persona.nif"  onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" style="text-transform : uppercase"
								size="9" maxlength="9"/>
								<s:hidden name="modeloDto.transmitente.persona.numColegiado"/>
								<s:hidden name="modeloDto.transmitente.idInterviniente"/>
								<s:hidden name="modeloDto.transmitente.tipoInterviniente"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGeneradaRemesa(modeloDto) &&
									@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
								<input type="button" id="idBotonBuscarNifTransmitente" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteTransmitente()"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoPersona">Tipo de persona<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getComboTipoPersonas()"
					id="idTipoPersonaTransmitente" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="modeloDto.transmitente.persona.tipoPersona"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoSujetoPasivo">Sexo: </label>
			</td>

			<td align="left" nowrap="nowrap" >
				<s:select id="idSexoTransmitente" name="modeloDto.transmitente.persona.sexo"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>

			<td align="left" nowrap="nowrap" >
				<label for="labelPrimerApeTitularAujPasivo">Primer Apellido / Razón Social<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPrimerApeRazonSocialTransmitente"  name="modeloDto.transmitente.persona.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSegundoApeTransmitente">Segundo Apellido:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idSegundoApeTransmitente" name="modeloDto.transmitente.persona.apellido2" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="30" maxlength="100" />
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelNombreTransmitente">Nombre<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="idNombreTransmitente" name="modeloDto.transmitente.persona.nombre" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="100" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelFechaNacTransmitente">Fecha Nacimiento:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="modeloDto.transmitente.persona.fechaNacimiento.dia" id="idDiaNacTransmitente" onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="modeloDto.transmitente.persona.fechaNacimiento.mes" id="idMesNacTransmitente" onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="modeloDto.transmitente.persona.fechaNacimiento.anio" id="idAnioNacTransmitente" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioNacTransmitente, document.formData.idMesNacTransmitente, document.formData.idDiaNacTransmitente);return false;" 
								title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTelefonoSujPasivo">Tel&eacute;fono: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.transmitente.persona.telefonos" id="idTelefonoTransmitente" size="10" maxlength="9" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Direccion Transmitente:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && modeloDto.transmitente.direccion.idDireccion != null}">
			<tr id="trDireccionTransmitente">
				<td align="left" nowrap="nowrap">
					<label for="labelIdDir">Id direccion:</label>
				</td>
				<td>
					<s:textfield name="modeloDto.transmitente.direccion.idDireccion" id="idDireccionTransmitente" size="25" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idDireccionTransmitenteHidden" name="modeloDto.transmitente.direccion.idDireccion"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaTransmitente">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaProvincias()" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
						name="modeloDto.transmitente.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaTransmitente"
						onchange="cargarListaMunicipiosCam('idProvinciaTransmitente','idMunicipioTransmitente'); 
						cargarListaTipoViaCam('idProvinciaTransmitente','idTipoViaTransmitente');
						borrarComboPuebloCam('idPuebloTransmitente');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipioTransmitente">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioTransmitente"
					headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
					name="modeloDto.transmitente.direccion.idMunicipio"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaMunicipiosPorProvincia(modeloDto,'003')"
					onchange="obtenerCodigoPostalPorMunicipio('idProvinciaTransmitente', this, 'idCodPostalTransmitente'); 
					cargarListaPueblosCam('idProvinciaTransmitente', 'idMunicipioTransmitente', 'idPuebloTransmitente');" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelPuebloTransmitente">Pueblo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="idPuebloTransmitente" onblur="this.className='input2';"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaPueblosPorMunicipioYProvincia(modeloDto,'003')"
					headerKey="-1" headerValue="Seleccione Pueblo" listKey="pueblo" listValue="pueblo"
					name="modeloDto.transmitente.direccion.pueblo"
					onfocus="this.className='inputfocus';"
					style="width:200px;"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoViaTransmitente">Tipo Via<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaTiposVias(modeloDto,'003')"
					headerKey="-1" headerValue="Seleccione Tipo Via" listKey="idTipoViaCam" listValue="nombre"
					name="modeloDto.transmitente.direccion.idTipoVia"
					id="idTipoViaTransmitente"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNombreTransmitente">Nombre Vía Pública<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.transmitente.direccion.nombreVia" id="idNombreViaTransmitente" size="25" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelCodPostalTransmitente">Cod. Postal<span class="naranja">*</span>:</label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="modeloDto.transmitente.direccion.codPostal" id="idCodPostalTransmitente" size="5" maxlength="5" onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';" />
						</td>
						<td align="left" nowrap="nowrap">
							<input type="button" class="boton-persona" id="botonCorreos" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
								onclick="javascript:abrirVentanaCorreosPopUp();"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<tr>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelNumeroDireccionTransmitente">Número<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumeroDireccionTransmitente" name="modeloDto.transmitente.direccion.numero" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5" />
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelLetraDireccionTransmitente">Letra: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idLetraDireccionTransmitente" name="modeloDto.transmitente.direccion.letra" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10" />
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelEscaleraDireccionTransmitente">Escalera: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idEscaleraDireccionTransmitente" name="modeloDto.transmitente.direccion.escalera"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" />
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelPisoDireccionTransmitente">Piso: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPisoDireccionTransmitente" name="modeloDto.transmitente.direccion.planta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelPuertaDireccionTransmitente">Puerta: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPuertaDireccionTransmitente" name="modeloDto.transmitente.direccion.puerta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" />
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idBloqueDireccionTransmitente">Bloque: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idBloqueDireccionTransmitente" name="modeloDto.transmitente.direccion.bloque"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" />
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelKmDireccionTransmitente">Km: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idKmDireccionTransmitente" name="modeloDto.transmitente.direccion.km"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionTransmitente">Hm: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionTransmitente" name="modeloDto.transmitente.direccion.hm"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
			</td>
		</tr>
	</table>
	<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGeneradaRemesa(modeloDto) &&
				@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
		<div class="acciones center">
			<input type="button" class="boton" name="blimpiarTransmitente" id="idLimpiarTransmitente"
						value="Limpiar Transmitente" onClick="javascript:limpiarFormularioTransmitente();" onKeyPress="this.onClick"/>
		</div>
	</s:if>
</div>
<script type="text/javascript">
	datosInicialesTransmitente();
</script>