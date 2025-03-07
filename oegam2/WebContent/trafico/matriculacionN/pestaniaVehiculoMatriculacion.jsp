<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set var="flagDisabledFichaTecnica" value="false"></s:set>
<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Veh&iacute;culo</td>
			<td align="right"><img title="Ver evolución" onclick="consultaEvolucionVehiculo('idVehiculo','<s:property value="tramiteTrafMatrDto.vehiculoDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DEL VEHÍCULO</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idBastidor">Bastidor: <span id ="idAsteriscoBastidor" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idBastidor" name="tramiteTrafMatrDto.vehiculoDto.bastidor"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="22" maxlength="21"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for=idRevisado>Revisado: <span></span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox id="idRevisado" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.vehiculoDto.revisado"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idCodigoItv">C&oacute;digo ITV <span id ="idAsteriscoCodigoItv" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<s:textfield disabled="%{flagDisabled}" id="idCodigoItv" name="tramiteTrafMatrDto.vehiculoDto.codItv"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="12" maxlength="9"/>
						</td>
						<td style="border: 0px;vertical-align:top">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<img src="img/mostrar.gif" alt="Consulta CodigoITV" style="height:20px;width:20px;cursor:pointer;"
									onclick="consultaDatosITV();" title="Consulta CodigoITV"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idVehiculoLabel">Id Vehículo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idVehiculoText" name="tramiteTrafMatrDto.vehiculoDto.idVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="10" disabled="true" />
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="codigoMarca">Marca: <span id ="idAsteriscoCodigoMarca" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="codigoMarca" name="codigoMarca" 
					onblur="this.className='input2'; validarMarca(this, document.getElementById('idMarca'), marcaVehiculoMatriculacion, document.getElementById('checkCodigoMarca'), 'MATW'); cargarFabricantesMarca();"
					onfocus="this.className='inputfocus';" onchange="document.getElementById('idFabricante').value='';"
					size="23" maxlength="22" autocomplete="off"
					cssStyle="position:relative; float:left;" />
				&nbsp;
				<span id="checkCodigoMarca"></span>
				<s:hidden id="idMarca" name="tramiteTrafMatrDto.vehiculoDto.codigoMarca" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="codigoMarcaBase">Marca Base: <span id ="idAsteriscoCodigoMarcaBase" class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="codigoMarcaBase" name="codigoMarcaBase"
					onblur="this.className='input2'; validarMarca(this, document.getElementById('idMarcaBase'), marcaBaseVehiculoMatriculacion, document.getElementById('checkCodigoMarcaBase'), 'MATW'); cargarFabricantesMarcaBase();"
					onfocus="this.className='inputfocus';" onchange="document.getElementById('idFabricanteBase').value='';"
					size="23" maxlength="22" autocomplete="off" cssStyle="position:relative; float:left;"
					disabled="%{flagDisabled}"/>
				&nbsp;
				<span id="checkCodigoMarcaBase"></span>
				<s:hidden id="idMarcaBase" name="tramiteTrafMatrDto.vehiculoDto.codigoMarcaBase" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="modelo">Modelo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="modelo"
					name="tramiteTrafMatrDto.vehiculoDto.modelo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="23" maxlength="22"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idNiveLabel">NIVE:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idNive"
					name="tramiteTrafMatrDto.vehiculoDto.nive" onblur="this.className='input2'; eliminarAsteriscosObligatorios();"
					onmouseout ="eliminarAsteriscosObligatorios();" size="33" maxlength="32"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idTipoVehiculo">Tipo de Vehículo: <span id ="idAsteriscoTipoVehiculo" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idTipoVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					onchange="cargarCriteriosConstruccion(this.value); cargarCriteriosUtilizacion(this.value); checkVelMaxObligatoria();"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T1')"
					headerKey="" headerValue="Seleccione Tipo"
					name="tramiteTrafMatrDto.vehiculoDto.tipoVehiculo" listKey="tipoVehiculo" listValue="descripcion"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idFichaTecnicaRD750">Ficha técnica RD 750/2010: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox disabled="%{flagDisabledFichaTecnica}" id="idFichaTecnicaRD750" name="tramiteTrafMatrDto.vehiculoDto.fichaTecnicaRD750"/>
				<img id="info_Ficha_tecnica" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'fichaTecnicaRD750')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS TÉCNICOS</span>
			</td>
		</tr>
	</table>

	<table width="50%" cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idClasificacionCriteriosConstruccion">Criterio de construcci&oacute;n: <span id="idAsteriscoClasificacionCriteriosConstruccion" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idClasificacionCriteriosConstruccion" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.criterioConstruccion" onchange="cargarListaHomologaciones(this.value);checkVelMaxObligatoria();"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioConstruccion()"
					listKey="idCriterioConstruccion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Construcción"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idClasificacionCriteriosUtilizacion">Criterio de utilizaci&oacute;n: <span id="idClasificacionCriteriosUtilizacion"class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idClasificacionCriteriosUtilizacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.criterioUtilizacion" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioUtilizacion()"
					listKey="idCriterioUtilizacion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Utilización"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idClasificacionItvPrever">Clasificaci&oacute;n / Tipo Industria:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idClasificacionItvPrever"
					name="tramiteTrafMatrDto.vehiculoDto.clasificacionItv"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"
					onkeypress="return validarDecimal(event, 0)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idColor">Color: <span id="idAsteriscoColor" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idColor" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.color" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getColores_MATW()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="-"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="idEpigrafe">Ep&iacute;grafe:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idEpigrafe" onblur="this.className='input2';" onfocus="this.className='inputfocus';" isabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.epigrafe" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEpigrafes()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="Epigrafe"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idFabricante">Fabricante:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idFabricante"
					name="tramiteTrafMatrDto.vehiculoDto.fabricante"
					onblur="this.className='input2'; validarFabricante(this.value, fabricanteVehiculoMatriculacion, 'checkCodigoFabricante');"
					onfocus="this.className='inputfocus';" size="55" maxlength="52" autocomplete="off" cssStyle="position:relative; float:left;" />
				&nbsp;
				<span id="checkCodigoFabricante"></span>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idFabricanteBase">Fabricante Base: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idFabricanteBase" name="tramiteTrafMatrDto.vehiculoDto.fabricanteBase"
					onblur="this.className='input2'; validarFabricanteBase(this.value, fabricanteBaseVehiculoMatriculacion, 'checkCodigoFabricanteBase');"
					onfocus="this.className='inputfocus';" size="55" maxlength="52" autocomplete="off"
					cssStyle="position:relative; float:left;" disabled="%{flagDisabled}"/>
				&nbsp;
				<span id="checkCodigoFabricanteBase"></span>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idNumSerie">Nº de Serie:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idNumSerie"
					name="tramiteTrafMatrDto.vehiculoDto.numSerie" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="12" maxlength="12"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idNumHomologacion">Nº de homologaci&oacute;n:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idNumHomologacion"
					name="tramiteTrafMatrDto.vehiculoDto.numHomologacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="35" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idNumHomologacionBase">Nº de homologaci&oacute;n Base:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumHomologacionBase" name="tramiteTrafMatrDto.vehiculoDto.numHomologacionBase"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}" size="35" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idComboFabricacion">País fabricación: <span id="idAsteriscoComboFabricacion" class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idComboFabricacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 	disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.paisFabricacion" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getPaisesFabricacion()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="País fabricación"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idServicioTrafico">Servicio: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idServicioTrafico" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.servicioTrafico.idServicio" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getServiciosTrafico('T1')"
					listKey="idServicio" listValue="descripcion" headerKey="-1" headerValue="Servicio"
					onchange="habilitarCEMA();checkVelMaxObligatoria();"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idVariante">Variante:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idVariante"
					name="tramiteTrafMatrDto.vehiculoDto.variante"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="26" maxlength="25"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idVarianteBase">Variante Base:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idVarianteBase" name="tramiteTrafMatrDto.vehiculoDto.varianteBase"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}" size="26" maxlength="25"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idVersion">Versi&oacute;n:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idVersion"
					name="tramiteTrafMatrDto.vehiculoDto.version"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="36" maxlength="35"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idVersionBase">Versi&oacute;n Base: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idVersionBase" name="tramiteTrafMatrDto.vehiculoDto.versionBase"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}" size="36" maxlength="35"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">CARACTERÍSTICAS</span>
			</td>
		</tr>
	</table>

	<table width="50%" cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td nowrap="nowrap" style="vertical-align:middle;" width="10%">
				<label for="idComboCategoriaEU">Categor&iacute;a EU: <span id= "idAsteriscoComboCategoriaEU" class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<s:select id="idComboCategoriaEU" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
								name="tramiteTrafMatrDto.vehiculoDto.idDirectivaCee"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().getListaDirectivasCEE(tramiteTrafMatrDto)"
								listKey="idDirectivaCEE" listValue="idDirectivaCEE" headerKey="" headerValue="Categoría EU"
								onchange="checkVelMaxObligatoria()" />
								<!-- onchange="return activarCheckDocumentacion();" -->
						</td>
						<td style="display:none">
							<s:select id="idComboCategoriaEUOculto" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().getListaDirectivasCEE(tramiteTrafMatrDto)"
								listKey="idDirectivaCEE" listValue="descripcion" headerKey="-1" headerValue="Homologación UE"/>
								<!-- onchange="return activarCheckDocumentacion();"-->
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idComboCarroceria">Carrocería: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idComboCarroceria" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.carroceria"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCarrocerias()"
					listKey="idCarroceria" listValue="descripcion" headerKey="" headerValue="Carroceria" cssStyle="position:relative; width:20em;" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idNcilindros">Cilindrada: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idNcilindros"
					name="tramiteTrafMatrDto.vehiculoDto.cilindrada" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="9" maxlength="8" onkeypress="return validarDecimal(event, 2)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idCo2">CO2: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idCo2"
					name="tramiteTrafMatrDto.vehiculoDto.co2" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="8" maxlength="7" onkeypress="return validarDecimal(event, 3)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idCodigoEco">Código ECO: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idCodigoEco"
					name="tramiteTrafMatrDto.vehiculoDto.codigoEco"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="10"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idConsumo">Consumo: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idConsumo"
					name="tramiteTrafMatrDto.vehiculoDto.consumo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="4"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idDistanciaEntreEjes">Distancia entre ejes: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idDistanciaEntreEjes"
					name="tramiteTrafMatrDto.vehiculoDto.distanciaEjes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="4"
					onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idEcoInnovacion">ECO innovación: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idEcoInnovacion" name="tramiteTrafMatrDto.vehiculoDto.ecoInnovacion"
					list="#{'Si':'Si', 'No':'No'}" headerKey="" headerValue="Eco innovación" disabled="%{flagDisabled}" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idPotenciaFiscal">Potencia Fiscal: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idPotenciaFiscal"
					name="tramiteTrafMatrDto.vehiculoDto.potenciaFiscal"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onchange="document.getElementById('idPotenciaFiscalInfo').value=document.getElementById('idPotenciaFiscal').value"
					size="14" maxlength="13" onkeypress="return validarDecimal(event, 2)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="velocidadMaxima">Velocidad Máxima: <span id="asteriscoVelocidadMaxima" class="naranja" style="display:none;">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="velocidadMaxima"
					name="tramiteTrafMatrDto.vehiculoDto.velocidadMaxima"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="3" maxlength="3" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idMom">MOM:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idMom"
					name="tramiteTrafMatrDto.vehiculoDto.mom"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="8" maxlength="7"
					onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idMomBase">MOM Base:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idMomBase" name="tramiteTrafMatrDto.vehiculoDto.momBase"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="8" maxlength="7" disabled="%{flagDisabled}" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idMma">MMA: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idMma"
					name="tramiteTrafMatrDto.vehiculoDto.pesoMma"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="7" maxlength="6" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idMtaMma">MTA/MMA: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idMtaMma"
					name="tramiteTrafMatrDto.vehiculoDto.mtmaItv"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="7" maxlength="6" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idNivelEmisiones">Nivel emisiones:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idNivelEmisiones"
					name="tramiteTrafMatrDto.vehiculoDto.nivelEmisiones"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="16" maxlength="15"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idPlazas">Nº plazas de asiento:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idPlazas"
					name="tramiteTrafMatrDto.vehiculoDto.plazas" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="4" maxlength="3" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idPlazasPie">Nº plazas de pie:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idPlazasPie"
					name="tramiteTrafMatrDto.vehiculoDto.numPlazasPie"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"
					onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idSubasta">Origen subasta:<span></span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox id="idSubasta" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.vehiculoDto.subastado"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idPotenciaNeta">Potencia Neta (kW):</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idPotenciaNeta"
					name="tramiteTrafMatrDto.vehiculoDto.potenciaNeta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="14" maxlength="13"
					onkeypress="return validarDecimal(event, 3)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idPotenciaPeso">Potencia/Peso: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idPotenciaPeso"
					name="tramiteTrafMatrDto.vehiculoDto.potenciaPeso"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="8" maxlength="7"
					onkeypress="return validarDecimal(event, 4)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idReduccionEco">Reducción ECO:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idReduccionEco"
					name="tramiteTrafMatrDto.vehiculoDto.reduccionEco"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="4"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idtara">Tara: </label>
				<img id="info_carburante" src="img/botonDameInfo.gif" onmouseover="dameInfo('mostrar', this.id, 'textoInfoTara')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idtara" name="tramiteTrafMatrDto.vehiculoDto.tara"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="8" maxlength="7" onkeypress="return validarNumerosEnteros(event)">
				</s:textfield>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="idComboAlimentacion">Tipo alimentación: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idComboAlimentacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.tipoAlimentacion"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposAlimentacion()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Tipo alimentación"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idCarburanteVehiculo">Tipo de Carburante: <span id="idAsteriscoCarburanteVehiculo" class="naranja">*</span></label>
				<img id="info_carburante" src="img/botonDameInfo.gif" onmouseover="dameInfo('mostrar', this.id, 'textoInfoCombustible')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idCarburanteVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.carburante" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCarburantes()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Carburante"
					onchange="habilitarCamposElectrico(this.value, document.getElementById('flagDisabledHidden').value);"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idCategoriaElectrica">Categoría eléctrica: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idCategoriaElectrica" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.vehiculoDto.categoriaElectrica" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCategoriaElectrica()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Categoria Electrica" disabled="%{flagDisabled}"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idAutonomiaElectrica">Autonomía eléctrica:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idAutonomiaElectrica" name="tramiteTrafMatrDto.vehiculoDto.autonomiaElectrica"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="7" maxlength="6" onkeypress="return validarNumerosEnteros(event)" disabled="%{flagDisabled}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idViaAnterior">Vía anterior:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idViaAnterior"
					name="tramiteTrafMatrDto.vehiculoDto.viaAnterior" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="4" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idViaPosterior">Vía posterior:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idViaPosterior"
					name="tramiteTrafMatrDto.vehiculoDto.viaPosterior" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="5" maxlength="4" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="longitud">Longitud: <span id="asteriscoLongitud" class="naranja" style="display:none;">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="longitud"
					name="tramiteTrafMatrDto.vehiculoDto.longitud"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="15" maxlength="15" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="anchura">Anchura: <span id="asteriscoAnchura" class="naranja" style="display:none;">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="anchura"
					name="tramiteTrafMatrDto.vehiculoDto.anchura"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="15" maxlength="15" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="altura">Altura: <span id="asteriscoAltura" class="naranja" style="display:none;">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="altura"
					name="tramiteTrafMatrDto.vehiculoDto.altura"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="15" maxlength="15" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="numEjes">Num. ejes: <span id="asteriscoNumEjes class="naranja" style="display:none;">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="longitud"
					name="tramiteTrafMatrDto.vehiculoDto.numEjes"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="2" maxlength="2" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LA INSPECCIÓN TÉCNICA (ITV)</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="140" style="vertical-align:middle;">
				<label for="diaCaducidadTarjetaITV">Fecha caducidad ITV:</label>
			</td>
			<td>
				<table border="0">
					<tr>
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield disabled="%{flagDisabled}" id="diaCaducidadTarjetaITV" onkeypress="return validarDia(this,event)"
								name="tramiteTrafMatrDto.vehiculoDto.fechaItv.dia"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield disabled="%{flagDisabled}" id="mesCaducidadTarjetaITV" onkeypress="return validarMes(this,event)"
								name="tramiteTrafMatrDto.vehiculoDto.fechaItv.mes"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield disabled="%{flagDisabled}" id="anioCaducidadTarjetaITV" onkeypress="return validarAnio(this,event)"
								name="tramiteTrafMatrDto.vehiculoDto.fechaItv.anio"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td id="calendarioVehiculo" align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCaducidadTarjetaITV, document.formData.mesCaducidadTarjetaITV, document.formData.diaCaducidadTarjetaITV);return false;"	title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left">
				<s:checkbox id="idFechaCaducidadITV" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.vehiculoDto.checkFechaCaducidadITV"/>
				<img id="info_Fecha_Caducidad_ITV" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'calcularFechaCaducidadITV')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idTipoInspeccionItv">Tipo inspección ITV: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="5%">
				<s:select name="tramiteTrafMatrDto.vehiculoDto.motivoItv"
					id="motivosITV" disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposInspeccionITVMatriculacion()"
					listKey="valorEnum" listValue="nombreEnum" headerValue="Seleccione motivo ITV" headerKey="-1"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="tipoItv">Tipo ITV: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="tipoItv"
					name="tramiteTrafMatrDto.vehiculoDto.tipoItv"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="26" maxlength="25"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="tipoItvBase">Tipo ITV Base:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="tipoItvBase"
					name="tramiteTrafMatrDto.vehiculoDto.tipoBase"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="26" maxlength="25"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idTipoTarjetaITV">Tipo tarjeta ITV: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idTipoTarjetaITV" name="tramiteTrafMatrDto.vehiculoDto.tipoTarjetaITV"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTarjetaITV()"
					listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="-"
					onchange=" editarFechaCaducidad(this.value); habilitarCamposBase(this.value, document.getElementById('flagDisabledHidden').value);"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;" width="10%">
				<label for="idEstacionITV">Estación ITV: <span class="naranja">*</span></label>
			</td> 
			<td align="left" nowrap="nowrap" colspan="4"><s:select
					id="idEstacionITV"
					name="tramiteTrafMatrDto.vehiculoDto.estacionItv"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstacionesITV()"
					listKey="estacionITV" disabled="%{flagDisabled}"
					listValue="municipio" headerKey="-1"
					headerValue="Seleccione estacion ITV"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					style="background-color: white;"/></td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS VEHÍCULO USADO</span>
			</td>
			<td class="tabular">
				<s:checkbox id="idUsado" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.vehiculoDto.vehiUsado" onchange="desbloquearUsado(this.checked);"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="140" style="vertical-align:middle;">
				<label for="diaPrimMatri">Fecha de Primera Matriculación:</label>
			</td>
			<td align="left" nowrap="nowrap" width="1%">
				<table>
					<tr>
						<td>
							<s:textfield id="diaPrimMatri" onkeypress="return validarDia(this,event)"
								name="tramiteTrafMatrDto.vehiculoDto.fechaPrimMatri.dia"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield id="mesPrimMatri" onkeypress="return validarMes(this,event)"
								name="tramiteTrafMatrDto.vehiculoDto.fechaPrimMatri.mes"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield id="anioPrimMatri" onkeypress="return validarAnio(this,event)"
								name="tramiteTrafMatrDto.vehiculoDto.fechaPrimMatri.anio"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPrimMatri, document.formData.mesPrimMatri, document.formData.diaPrimMatri);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario" id="imgCalendarFechaPrimeraMatriculacion" />
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="20%" style="vertical-align:middle;">
				<label for="idMatriculaOrigen">Matr&iacute;cula Origen:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idMatriculaOrigen"
					name="tramiteTrafMatrDto.vehiculoDto.matriculaOrigen"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)"
					onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"
					size="10" maxlength="9" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="kmUso">Nº kilómetros de uso:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="kmUso"
					name="tramiteTrafMatrDto.vehiculoDto.kmUso"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
					size="7" maxlength="6" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="horasUso">Horas de Uso: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="horasUso"
					name="tramiteTrafMatrDto.vehiculoDto.horasUso" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="7" maxlength="6" disabled="true"
					onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idImportado">Importado:</label>
			</td>
			<td align="left" nowrap="nowrap" width="10%">
				<s:checkbox id="idImportado" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.vehiculoDto.importado" onchange="desbloquearImportado(this.checked);"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;" width="10%">
				<label for="idLabelProcedencia">Procedencia: <span class="naranja">*</span></label>
			</td> 

			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield disabled="%{flagDisabled}" id="sigla3PaisProcedencia" name="sigla3PaisProcedencia"
					onblur="this.className='input2'; validarPaisProcedencia(this, document.getElementById('sigla3'), paisProcedencia, document.getElementById('checksigla3PaisProcedencia'));"
					onfocus="this.className='inputfocus';"
					size="23" maxlength="22" autocomplete="off"
					cssStyle="position:relative; float:left;" />
				&nbsp;
				<span id="checksigla3PaisProcedencia" cssStyle="position:relative; float:left;"></span>
				<s:hidden id="sigla3" name="tramiteTrafMatrDto.vehiculoDto.procedencia" />
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap" width="20%" style="vertical-align:middle;">
				<label for="idMatriculaOrigenExtranjero">Matr&iacute;cula Origen Extranjero:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield id="idMatriculaOrigenExtranjero"
					name="tramiteTrafMatrDto.vehiculoDto.matriculaOrigExtr"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarMatricula(event)" size="10" maxlength="9" disabled="%{flagDisabled}"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">INTRODUCTOR DEL VEHÍCULO EN ESPAÑA</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="10%" style="vertical-align:middle;">
				<label for="idNifIntegrador">NIF</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idNifIntegrador"
					name="tramiteTrafMatrDto.vehiculoDto.nifIntegrador.nif"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
			<td align="left" nowrap="nowrap" width="10%" style="vertical-align:middle;">
				<label for="nombreIntroductor">Nombre:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="nombreIntroductor"
					name="tramiteTrafMatrDto.vehiculoDto.nifIntegrador.nombre"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="10%" style="vertical-align:middle;">
				<label for="apellido1Introductor">Apellido o raz&oacute;n social:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="apellido1Introductor"
					name="tramiteTrafMatrDto.vehiculoDto.nifIntegrador.apellido1RazonSocial"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="25" maxlength="25"/>
			</td>

			<td align="left" nowrap="nowrap" width="10%" style="vertical-align:middle;">
				<label for="nombreIntroductor">Segundo apellido:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="nombreIntroductor"
					name="tramiteTrafMatrDto.vehiculoDto.nifIntegrador.apellido2"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">LOCALIZACIÓN DEL VEHÍCULO (Si es distinta a la del titular)</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
			<tr>
				<td>
					<label>ID dirección Vehículo</label>
					<span><s:textfield cssClass="campo_disabled" id="id_direccV" name="idDireccion_admin_vehiculo" readonly="true" value="%{tramiteTrafMatrDto.vehiculoDto.direccion.idDireccion}"></s:textfield> </span>
				</td>
				<td>
					<label>ID dirección Titular</label>
					<span><s:textfield cssClass="campo_disabled" id="id_direccT" name="idDireccion_admin_titular" readonly="true" value="%{tramiteTrafMatrDto.titular.direccion.idDireccion}"></s:textfield> </span>
				</td>
			</tr>
		</s:if>

		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idProvinciaVehiculo">Provincia: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTitular()"
					headerKey="-1" headerValue="Seleccione Provincia" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.idProvincia" listKey="idProvincia"
					listValue="nombre" 	id="idProvinciaVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onchange="cargarListaMunicipios(this,'idMunicipioVehiculo','municipioSeleccionadoVehiculo');
					cargarListaTipoVia(this,'tipoViaVehiculo','tipoViaSeleccionadaVehiculo');
					inicializarTipoVia('tipoViaVehiculo','nombreViaDireccionVehiculo',viaVehiculoMatriculacion);
					borrarComboPueblo('idPuebloVehiculo');"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idMunicipioVehiculo">Municipio: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idMunicipioVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="cargarListaPueblosDGT('idProvinciaVehiculo', this, 'idPuebloVehiculo', 'puebloSeleccionadoVehiculo');
					seleccionarCampo('municipioSeleccionadoVehiculo','idMunicipioVehiculo');
					inicializarTipoVia('tipoViaVehiculo','nombreViaDireccionVehiculo',viaVehiculoMatriculacion);">
						<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idPuebloVehiculo">Pueblo:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="idPuebloVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					style="width:200px;" onchange="seleccionarCampo('puebloSeleccionadoVehiculo','idPuebloVehiculo');">
						<option value="-1">Seleccione Pueblo</option>
				</select>
				<s:hidden id="puebloSeleccionadoVehiculo" name="tramiteTrafMatrDto.vehiculoDto.direccion.puebloCorreos"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="codPostalVehiculo">Código Postal: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="codPostalVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.codPostalCorreos"
					onkeypress="return validarNumerosEnteros(event)" maxlength="5" size="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="tipoViaVehiculo">Tipo de vía:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<select id="tipoViaVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" ${stringDisabled}
					onchange="seleccionarCampo('tipoViaSeleccionadaVehiculo','tipoViaVehiculo');
					cargarListaNombresVia('idProvinciaVehiculo', 'municipioSeleccionadoVehiculo', this, 'nombreViaDireccionVehiculo',viaVehiculoMatriculacion);">
						<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaVehiculo" name="tramiteTrafMatrDto.vehiculoDto.direccion.idTipoVia"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="nombreViaDireccionVehiculo">Nombre vía: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield disabled="%{flagDisabled}" id="nombreViaDireccionVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.nombreVia"
					cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="numeroDireccionVehiculo">Número:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="numeroDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.numero"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumerosEnteros(event)" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="letraDireccionVehiculo">Letra/Puerta:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="letraDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.letra" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="escaleraDireccionVehiculo">Escalera:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="escaleraDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.escalera"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="pisoDireccionVehiculo">Piso:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="pisoDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.planta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="puertaDireccionVehiculo">Portal:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="puertaDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.puerta"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="bloqueDireccionVehiculo">Bloque:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="bloqueDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.bloque"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="kmDireccionVehiculo">Km:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="kmDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.km"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="hmDireccionVehiculo">Hm:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="hmDireccionVehiculo"
					name="tramiteTrafMatrDto.vehiculoDto.direccion.hm" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"
					size="8" maxlength="7"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">PROGRAMA PREVER (MODELO 576)</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="10%" style="vertical-align:middle;">
				<label for="bastidorPrever">N&uacute;mero de Bastidor: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="bastidorPrever"
					name="tramiteTrafMatrDto.vehiculoPrever.bastidor"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="19" maxlength="17"/>
			</td>
			<td align="left" nowrap="nowrap" width="10%" style="vertical-align:middle;">
				<label for="matriculaPrever">Matr&iacute;cula: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="matriculaPrever"
					name="tramiteTrafMatrDto.vehiculoPrever.matricula"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)"
					size="9" maxlength="9"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="codigoMarcaPrever">Marca: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="codigoMarcaPrever"
					name="codigoMarcaPrever" onblur="this.className='input2'; validarMarca(this, document.getElementById('marcaPrever'), marcaVehiculoPreverMatriculacion, document.getElementById('checkCodigoMarcaPrever'), 'MATW');" 
					onfocus="this.className='inputfocus';" size="23" maxlength="22" autocomplete="off"
					cssStyle="position:relative; float:left;" />
				&nbsp;
				<span id="checkCodigoMarcaPrever"></span>
				<s:hidden id="marcaPrever" name="tramiteTrafMatrDto.vehiculoPrever.codigoMarca" />
			</td>

			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="modeloPrever">Modelo:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="modeloPrever"
					name="tramiteTrafMatrDto.vehiculoPrever.modelo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idClasificacionCriteriosConstruccionVehiculoPrever">Criterios de construcci&oacute;n Prever: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idClasificacionCriteriosConstruccionPrever" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoPrever.criterioConstruccion" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioConstruccion()"
					listKey="idCriterioConstruccion" listValue="descripcion" headerKey="-1"
					headerValue="Criterio de Construcción" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idClasificacionCriteriosUtilizacionPrever">Criterios de Utilizaci&oacute;n Prever: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idClasificacionCriteriosUtilizacionPrever" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTrafMatrDto.vehiculoPrever.criterioUtilizacion"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioUtilizacion()"
					listKey="idCriterioUtilizacion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Utilización" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="clasificacionITVPrever">Clasificaci&oacute;n ITV:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="clasificacionITVPrever"
					name="tramiteTrafMatrDto.vehiculoPrever.clasificacionItv"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="tipoItvPrever">Tipo ITV: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="tipoItvPrever" name="tramiteTrafMatrDto.vehiculoPrever.tipoItv"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="15" maxlength="25" />
			</td>
		</tr>
	</table>

	<s:if test="%{((tramiteTrafMatrDto.numExpediente != null) && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()) && @org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esValidableMatriculacion(tramiteTrafMatrDto))}">
		<table class="acciones" align="left">
			<tr>
				<td align="center">
					<input id="bConsultaTarjetaEitv" type="button" class="botonGrande" name="bConsultaTarjetaEitv" value="Consulta Tarjeta eITV"
						onClick="consultarTarjetaEitvMatriculacion('Vehiculo')" onkeyPress="this.onClick" style="margin-right:10px">
				</td>
			</tr>
		</table>
	</s:if>

	<script type="text/javascript">
		var esElectrico = true;
		var esNoElectrico = false;

		var viaVehiculoMatriculacion = new BasicContentAssist(document.getElementById('nombreViaDireccionVehiculo'), [], null, true);
		var fabricanteVehiculoMatriculacion = new BasicContentAssist(document.getElementById('idFabricante'), [], null, true);
		var fabricanteBaseVehiculoMatriculacion = new BasicContentAssist(document.getElementById('idFabricanteBase'), [], null, true);
		var marcaVehiculoMatriculacion = new BasicContentAssist(document.getElementById('codigoMarca'), [], null, true);
		var marcaVehiculoPreverMatriculacion = new BasicContentAssist(document.getElementById('codigoMarcaPrever'), [], null, true);
		var marcaBaseVehiculoMatriculacion = new BasicContentAssist(document.getElementById('codigoMarcaBase'), [], null, true);
		var paisProcedencia = new BasicContentAssist(document.getElementById('sigla3PaisProcedencia'), [], null, true);
		recargarComboMunicipios('idProvinciaVehiculo','idMunicipioVehiculo','municipioSeleccionadoVehiculo');
		recargarComboTipoVia('idProvinciaVehiculo','tipoViaVehiculo','tipoViaSeleccionadaVehiculo');
		recargarComboLocalidadesDGT('idProvinciaVehiculo','municipioSeleccionadoVehiculo','idPuebloVehiculo', 'puebloSeleccionadoVehiculo');
		recargarNombreVias('idProvinciaVehiculo', 'municipioSeleccionadoVehiculo', 'tipoViaSeleccionadaVehiculo','nombreViaDireccionVehiculo', viaVehiculoMatriculacion);
		cargarListaFabricantes('idMarca', 'idFabricante', fabricanteVehiculoMatriculacion);

		var listasMarcas = new Array(marcaVehiculoMatriculacion, marcaVehiculoPreverMatriculacion);
		var listasMarcasBase = new Array(marcaBaseVehiculoMatriculacion, marcaVehiculoPreverMatriculacion);
		var camposMarcas = new Array('codigoMarca', 'codigoMarcaPrever');
		var camposMarcasBase = new Array('codigoMarcaBase');
		var hiddenMarcas = new Array('idMarca', 'marcaPrever');
		var hiddenMarcasBase = new Array('idMarcaBase');

		for (var i = 0; i < camposMarcas.length; i++) {
			cargarListaMarcasVehiculo(listasMarcas[i], camposMarcas[i], hiddenMarcas[i], "true");
		}

		for (var i = 0; i < camposMarcasBase.length; i++) {
			cargarListaMarcasVehiculo(listasMarcasBase[i], camposMarcasBase[i], hiddenMarcasBase[i], "true");
		}

		cargarListaPaisesProcedencia(paisProcedencia, 'sigla3PaisProcedencia', 'sigla3');

		// JMC: Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
		if (document.getElementById("permisoEspecial").value == "false") {
			//bloquearITVCamposITV(document.getElementById('idNive').value);
			desbloquearUsado(document.getElementById("idUsado").checked);
			desbloquearImportado(document.getElementById("idImportado").checked);
			actualizarTitlesHomologacion();
		}

		function cargarFabricantesMarca() {
			cargarListaFabricantes('idMarca', 'idFabricante', fabricanteVehiculoMatriculacion);
		}

		function cargarFabricantesMarcaBase() {
			cargarListaFabricantes('idMarcaBase', 'idFabricanteBase', fabricanteBaseVehiculoMatriculacion);
		}

		$(document).ready(function() {
			checkVelMaxObligatoria();
			deshabilitaCarroceriaParents();
		});

		desbloquearUsado(document.getElementById("idUsado").checked);
		var idTipoTarjetaITV = document.getElementById("idTipoTarjetaITV").value;
		var carburante = document.getElementById("idCarburanteVehiculo").value;
		var flagDisabledHidden = document.getElementById("flagDisabledHidden").value;
		habilitarCamposBase(idTipoTarjetaITV, flagDisabledHidden);
		habilitarCamposElectrico(carburante, flagDisabledHidden);

		// Mantis 14557. DS: Cuando se rellena el NIVE completo dejan de aparecer como obligatorios los campos
		// que no son necesarios en la matriculación con NIVE
		function eliminarAsteriscosObligatorios() {
			if (document.getElementById("idNive").value.length == 32) {
				document.getElementById("idAsteriscoCodigoMarca").innerHTML = "";
				document.getElementById("idAsteriscoCodigoMarcaBase").innerHTML = "";
				document.getElementById("idAsteriscoTipoVehiculo").innerHTML = "";
				document.getElementById("idAsteriscoClasificacionCriteriosConstruccion").innerHTML = "";
				document.getElementById("idClasificacionCriteriosUtilizacion").innerHTML = "";
				document.getElementById("idAsteriscoColor").innerHTML = "";
				document.getElementById("idAsteriscoComboFabricacion").innerHTML = "";
				document.getElementById("idAsteriscoComboCategoriaEU").innerHTML = "";
			} else {
				document.getElementById("idAsteriscoCodigoMarca").innerHTML = "*";
				document.getElementById("idAsteriscoCodigoMarcaBase").innerHTML = "*";
				document.getElementById("idAsteriscoTipoVehiculo").innerHTML = "*";
				document.getElementById("idAsteriscoClasificacionCriteriosConstruccion").innerHTML = "*";
				document.getElementById("idClasificacionCriteriosUtilizacion").innerHTML = "*";
				document.getElementById("idAsteriscoColor").innerHTML = "*";
				document.getElementById("idAsteriscoComboFabricacion").innerHTML = "*";
				document.getElementById("idAsteriscoComboCategoriaEU").innerHTML = "*";
			}
		}

		function exentoItV() {
			var motivoItv = document.getElementById("motivosITV").value;
			var diafechaItv = document.getElementById("diaCaducidadTarjetaITV");
			var mesfechaItv = document.getElementById("mesCaducidadTarjetaITV");
			var aniofechaItv = document.getElementById("anioCaducidadTarjetaITV");
			if (motivoItv == "E") {
				diafechaItv.value = "";
				diafechaItv.disabled = true;
				mesfechaItv.value="";
				mesfechaItv.disabled = true;
				aniofechaItv.value = "";
				aniofechaItv.disabled = true;
			} else {
				diafechaItv.disabled = false;
				mesfechaItv.disabled = false;
				aniofechaItv.disabled = false;
			}
		}
	</script>
</div>