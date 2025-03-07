<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Persona:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNif">N.I.F<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNifArrendatario" name="arrendatarioDto.persona.nif" onblur="this.className='input2';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" style="text-transform : uppercase"
								size="9" maxlength="9"/>
							<s:hidden name="arrendatarioDto.persona.numColegiado"/>
						</td>
						<td align="left" nowrap="nowrap">
							<input type="button" id="idBotonBuscarNifArrendatario" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
								onclick="javascript:buscarArrendatario()"/>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoPersona">Tipo de persona<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getComboTipoPersonas()"
					id="idTipoPersonaArrendatario" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="arrendatarioDto.persona.tipoPersona"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"
				/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoArrendatario">Sexo: </label>
			</td>

			<td align="left" nowrap="nowrap" >
				<s:select id="idSexoArrendatario" name="arrendatarioDto.persona.sexo"
					list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-" disabled="false"/>
			</td>

			<td align="left" nowrap="nowrap" >
				<label for="labelPrimerApeTitularArrendatario">Primer Apellido / Razón Social<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idPrimerApeRazonSocialArrendatario" name="arrendatarioDto.persona.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSegundoApeArrendatario">Segundo Apellido:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idSegundoApeArrendatario" name="arrendatarioDto.persona.apellido2" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="30" maxlength="100" />
			</td>

			<td align="left" nowrap="nowrap">
				<label for="labelNombreArrendatario">Nombre<span class="naranja">*</span>:</label>
			</td>

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="idNombreArrendatario" name="arrendatarioDto.persona.nombre" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="100" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelFechaNacArrendatario">Fecha Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="arrendatarioDto.persona.fechaNacimiento.dia" id="idDiaNacArrendatario" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="arrendatarioDto.persona.fechaNacimiento.mes" id="idMesNacArrendatario" onblur="this.className='input2';"
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="arrendatarioDto.persona.fechaNacimiento.anio" id="idAnioNacArrendatario" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioNacArrendatario, document.formData.idMesNacArrendatario, document.formData.idDiaNacArrendatario);return false;" 
								title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección Persona:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<tr id="trDireccionArrendatario">
				<td align="left" nowrap="nowrap">
					<label for="labelIdDir">Id direccion:</label>
				</td>
				<td>
					<s:textfield name="arrendatarioDto.persona.direccionDto.idDireccion" id="idDireccionArrendatario" size="25" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" disabled="true" />
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.idDireccion"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaArrendatario">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getListaProvincias()"
							onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" headerKey=""	headerValue="Seleccione Provincia"
							name="arrendatarioDto.persona.direccionDto.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaArrendatario"
						onchange="cargarListaMunicipiosCAYC('idProvinciaArrendatario','idMunicipioArrendatario');
						cargarListaTipoViaCAYC('idProvinciaArrendatario','idTipoViaArrendatario');
						borrarComboPuebloCAYC('idPuebloArrendatario');
						inicializarTipoVia('idTipoViaArrendatario','idNombreViaArrendatario',viaArrendatario);
						borrarRestoCampos('idCodPostalArrendatario','idNumeroArrendatario','idLetraDireccionArrendatario','idEscaleraDireccionArrendatario','idPisoDireccionArrendatario','idPuertaDireccionArrendatario','idBloqueDireccionArrendatario','idKmDireccionArrendatario','hmDireccionArrendatario');" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipioArrendatario">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioArrendatario"
					headerKey=""	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"
					name="arrendatarioDto.persona.direccionDto.idMunicipio"
					list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getListaMunicipiosPorProvincia(arrendatarioDto)"
					onchange="obtenerCodigoPostalPorMunicipioCAYC('idProvinciaArrendatario', 'idMunicipioArrendatario', 'idCodPostalArrendatario');
					cargarListaPueblosCAYC('idProvinciaArrendatario', 'idMunicipioArrendatario', 'idPuebloArrendatario');
					inicializarTipoVia('idTipoViaArrendatario','idNombreViaArrendatario',viaArrendatario);
					cargarListaTipoViaCAYC('idProvinciaArrendatario','idTipoViaArrendatario');
					borrarRestoCampos('idCodPostalArrendatario','idNumeroArrendatario','idLetraDireccionArrendatario','idEscaleraDireccionArrendatario','idPisoDireccionArrendatario','idPuertaDireccionArrendatario','idBloqueDireccionArrendatario','idKmDireccionArrendatario','hmDireccionArrendatario'); "/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelPuebloArrendatario">Pueblo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="idPuebloArrendatario" onblur="this.className='input2';"
					list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getListaPueblos(arrendatarioDto)"
					headerKey="" headerValue="Seleccione Pueblo" listKey="pueblo" listValue="pueblo"
					name="arrendatarioDto.persona.direccionDto.pueblo"
					onfocus="this.className='inputfocus';"
					onchange="obtenerCodigoPostalPorMunicipioCAYC('idProvinciaArrendatario', 'idMunicipioArrendatario', 'idCodPostalArrendatario');"
					style="width:200px;"/>
			</td>

				<td align="left" nowrap="nowrap">
					<label for="labelTipoViaArrendatario">Tipo Via<span class="naranja">*</span>:</label>
				</td>
				<td>
					<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getListaTipoVias(arrendatarioDto)"
						headerKey="" headerValue="Seleccione Tipo Via" listKey="idTipoVia" listValue="nombre"
						name="arrendatarioDto.persona.direccionDto.idTipoVia"
						id="idTipoViaArrendatario"
						onchange="cargarListaNombresVia_Arrendatario('idProvinciaArrendatario', 'idMunicipioArrendatario', this, 'idNombreViaArrendatario',viaArrendatario);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNombreArrendatario">Nombre Vía Pública<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="arrendatarioDto.persona.direccionDto.nombreVia" id="idNombreViaArrendatario" size="25" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" autocomplete="off" cssStyle="width:200px;position:relative;" maxlength="50" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelCodPostalArrendatario">Cod. Postal<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="arrendatarioDto.persona.direccionDto.codPostal" id="idCodPostalArrendatario" size="5" maxlength="5" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="7%">
					<label for="labelNumeroArrendatario">Número<span class="naranja">*</span>:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield id="idNumeroArrendatario" name="arrendatarioDto.persona.direccionDto.numero"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5" />
				</td>
					<td align="left" nowrap="nowrap" width="7%">
			<label for="labelLetraDireccionArrendatario">Letra: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="idLetraDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.letra"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
				onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10" />
		</td>

		<td align="left" nowrap="nowrap" width="7%">
			<label for="labelEscaleraDireccionArrendatario">Escalera: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="idEscaleraDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.escalera"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" />
		</td>

		<td align="left" nowrap="nowrap" width="7%">
			<label for="labelPisoDireccionArrendatario">Piso: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="idPisoDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.planta"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
		</td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap">
			<label for="labelPuertaDireccionArrendatario">Puerta: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="idPuertaDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.puerta"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" />
		</td>

		<td align="left" nowrap="nowrap">
			<label for="idBloqueDireccionArrendatario">Bloque: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="idBloqueDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.bloque"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" />
		</td>

		<td align="left" nowrap="nowrap">
			<label for="labelKmDireccionArrendatario">Km: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="idKmDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.km"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
		</td>
		<td align="left" nowrap="nowrap">
			<label for="hmDireccionTitular">Hm: </label>
		</td>

		<td align="left" nowrap="nowrap">
			<s:textfield id="hmDireccionArrendatario" name="arrendatarioDto.persona.direccionDto.hm"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
				onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
		</td>
	</tr>
	</table>
<script>
	var viaArrendatario = new BasicContentAssist(document.getElementById('idNombreViaArrendatario'), [], null, true);
</script>