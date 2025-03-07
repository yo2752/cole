<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Sujeto Pasivo:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNifSujPasico">N.I.F<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNifSujPasivo" name="modeloDto.sujetoPasivo.persona.nif"  onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" style="text-transform : uppercase"
								size="9" maxlength="9"/>
							<s:hidden name="modeloDto.sujetoPasivo.persona.numColegiado"/>
							<s:hidden name="modeloDto.sujetoPasivo.idInterviniente"/>
							<s:hidden name="modeloDto.sujetoPasivo.tipoInterviniente"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGeneradaRemesa(modeloDto) &&
									@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
										<input type="button" id="idBotonBuscarNifSujPasivo" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
												onclick="javascript:buscarIntervinienteSujetoPasivo()"/>
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
					id="idTipoPersonaSujPasivo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="modeloDto.sujetoPasivo.persona.tipoPersona"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoSujetoPasivo">Sexo: </label>
			</td>

			<td align="left" nowrap="nowrap" >
				<s:select id="idSexoSujPasivo" name="modeloDto.sujetoPasivo.persona.sexo"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-"  disabled="true"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelPrimerApeTitularAujPasivo">Primer Apellido / Razón Social<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPrimerApeRazonSocialSujPasivo"  name="modeloDto.sujetoPasivo.persona.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;"
					size="65"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSegundoApeSujPasivo">Segundo Apellido:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idSegundoApeSujPasivo" name="modeloDto.sujetoPasivo.persona.apellido2" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="30" maxlength="100"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelNombreSujPasivo">Nombre<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="idNombreSujPasivo" name="modeloDto.sujetoPasivo.persona.nombre" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelFechaNacSujPasivo">Fecha Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="modeloDto.sujetoPasivo.persona.fechaNacimiento.dia" id="idDiaNacSujPasivo" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="modeloDto.sujetoPasivo.persona.fechaNacimiento.mes" id="idMesNacSujPasivo" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="modeloDto.sujetoPasivo.persona.fechaNacimiento.anio" id="idAnioNacSujPasivo" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>

						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioNacSujPasivo, document.formData.idMesNacSujPasivo, document.formData.idDiaNacSujPasivo);return false;" 
								title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTelefonoSujPasivo">Tel&eacute;fono:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.sujetoPasivo.persona.telefonos" id="idTelefonoSujPasivo" size="10" maxlength="9" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Direccion Sujeto Pasivo:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && modeloDto.sujetoPasivo.direccion.idDireccion != null}">
			<tr id="trDireccionSujPasivo">
				<td align="left" nowrap="nowrap">
					<label for="labelIdDir">Id direccion:</label>
				</td>
				<td>
					<s:textfield name="modeloDto.sujetoPasivo.direccion.idDireccion" id="idDireccionSujPasivo" size="25" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idDireccionSujPasivoHidden" name="modeloDto.sujetoPasivo.direccion.idDireccion"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaSujPasivo">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaProvincias()"
						onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
						name="modeloDto.sujetoPasivo.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaSujPasivo"
						onchange="cargarListaMunicipiosCam('idProvinciaSujPasivo','idMunicipioSujPasivo'); 
						cargarListaTipoViaCam('idProvinciaSujPasivo','idTipoViaSujetoPasivo');
						borrarComboPuebloCam('idPuebloSujPasivo');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipioSujPasivo">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioSujPasivo"
					headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
					name="modeloDto.sujetoPasivo.direccion.idMunicipio"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaMunicipiosPorProvincia(modeloDto,'001')"
					onchange="obtenerCodigoPostalPorMunicipio('idProvinciaSujPasivo', this, 'idCodPostalSujPasivo'); 
					cargarListaPueblosCam('idProvinciaSujPasivo', 'idMunicipioSujPasivo', 'idPuebloSujPasivo');" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelPuebloSujPasivo">Pueblo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="idPuebloSujPasivo" onblur="this.className='input2';"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaPueblosPorMunicipioYProvincia(modeloDto,'001')"
					headerKey="-1" headerValue="Seleccione Pueblo" listKey="pueblo" listValue="pueblo"
					name="modeloDto.sujetoPasivo.direccion.pueblo"
					onfocus="this.className='inputfocus';"
					style="width:200px;"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelTipoViaSujPasivo">Tipo Via<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaTiposVias(modeloDto,'001')"
					headerKey="-1" headerValue="Seleccione Tipo Via" listKey="idTipoViaCam" listValue="nombre" 
					name="modeloDto.sujetoPasivo.direccion.idTipoVia"
					id="idTipoViaSujetoPasivo"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNombreSujPasivo">Nombre Vía Pública<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.sujetoPasivo.direccion.nombreVia" id="idNombreViaSujPasivo" size="25" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelCodPostalSujPasivo">Cod. Postal<span class="naranja">*</span>:</label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="modeloDto.sujetoPasivo.direccion.codPostal" id="idCodPostalSujPasivo" size="5" maxlength="5" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"/>
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
				<label for="labelNumeroDireccionSujPasivo">Número<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumeroDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.numero"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelLetraDireccionSujPasivo">Letra: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idLetraDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.letra"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelEscaleraDireccionSujPasivo">Escalera: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idEscaleraDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.escalera"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10"/>
			</td>

			<td align="left" nowrap="nowrap" width="7%">
				<label for="labelPisoDireccionSujPasivo">Piso: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPisoDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.planta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelPuertaDireccionSujPasivo">Puerta: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPuertaDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.puerta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idBloqueDireccionSujPasivo">Bloque: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idBloqueDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.bloque"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelKmDireccionSujPasivo">Km:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idKmDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.km"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionTitular">Hm:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionSujPasivo" name="modeloDto.sujetoPasivo.direccion.hm"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
			</td>
		</tr>
	</table>
	<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGeneradaRemesa(modeloDto) &&
				@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
		<div class="acciones center">
			<input type="button" class="boton" name="blimpiarSujetoPasivo" id="idLimpiarSujPasivo"
						value="Limpiar Sujeto Pasivo" onClick="javascript:limpiarFormularioSujetoPasivo();" onKeyPress="this.onClick"/>
		</div>
	</s:if>
</div>
<script type="text/javascript">
	datosInicialesSujpasivoModelo();
</script>