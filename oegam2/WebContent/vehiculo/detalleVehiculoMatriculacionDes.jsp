<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript"></script>

<div class="contentTabs" id="Vehiculo">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del Vehiculo</td>
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionVehiculo('idVehiculo','<s:property value="vehiculoDto.numColegiado"/>');"
						style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">VEHÍCULO</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="bastidor">Número de Bastidor: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idBastidor" name="vehiculoDto.bastidor" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="22" maxlength="21" disabled="true"/>
				</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idVehiculoLabel">Id Vehículo: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield id="idVehiculoText" name="vehiculoDto.idVehiculo" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="5" maxlength="10" disabled="true" />
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idMatricula">Matrícula: </label>
				</td>
				<td>
					<s:textfield name="vehiculoDto.matricula" id="idMatricula" onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  size="13" maxlength="12" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="diaMatriculacion">Fecha de Matriculación: </label>
				</td>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="vehiculoDto.fechaMatriculacion.dia" id="diaMatriculacion" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="vehiculoDto.fechaMatriculacion.mes" id="mesMatriculacion" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="vehiculoDto.fechaMatriculacion.anio" id="anioMatriculacion" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idTipoVehiculo">Tipo de Vehículo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idTipoVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T1')"
						headerKey="-1" headerValue="Seleccione Tipo" name="vehiculoDto.tipoVehiculo"
						listKey="tipoVehiculo" listValue="descripcion" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="idFichaTecnicaRD750">Ficha técnica RD 750/2010:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox disabled="true" id="idFichaTecnicaRD750" name="vehiculoDto.fichaTecnicaRD750"/>
					<img id="info_Ficha_tecnica" src="img/botonDameInfo.gif" onmouseover="dameInfoSinAjax('mostrar', this.id, 'fichaTecnicaRD750')" 
						onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="codigoMarca">Marca: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="codigoMarca" name="codigoMarca" onblur="this.className='input2'; validarMarca(this, document.getElementById('idMarca'), marcaVehiculoMatriculacion, document.getElementById('checkCodigoMarca'), 'MATW');" 
						onfocus="this.className='inputfocus';" size="23" maxlength="22" autocomplete="off" cssStyle="position:relative; float:left;" disabled="true"/>
					&nbsp;
					<span id="checkCodigoMarca"></span>
					<s:hidden id="idMarca" name="vehiculoDto.codigoMarca" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="codigoMarcaBase">Marca Base: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield  id="codigoMarcaBase" name="codigoMarcaBase" 
						onblur="this.className='input2'; validarMarca(this, document.getElementById('idMarcaBase'), marcaBaseVehiculoMatriculacion, document.getElementById('checkCodigoMarcaBase'), 'MATW');" 
						onfocus="this.className='inputfocus';" size="23" maxlength="22" autocomplete="off" cssStyle="position:relative; float:left;" disabled="true"/>
					&nbsp;
					<span id="checkCodigoMarcaBase"></span>
					<s:hidden id="idMarcaBase" name="vehiculoDto.codigoMarcaBase" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="modelo">Modelo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="modelo" name="vehiculoDto.modelo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="23" maxlength="22" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idColorVehiculo">Color: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idColor" 	onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.color"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getColores()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idServicioTrafico">Servicio: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idServicioTrafico" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.servicioTrafico.idServicio"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getServiciosTrafico('T1')"
						listKey="idServicio" listValue="descripcion" headerKey="-1"
						headerValue="Servicio" onchange="habilitarCEMA();" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idCarburanteVehiculo">Carburante: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idCarburanteVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.carburante"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCarburantes()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="Carburante" disabled="true"/>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idComboFabricacion">País fabricación (Nueva matriculación): </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idComboFabricacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.paisFabricacion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getPaisesFabricacion()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="País fabricación" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idComboFabricacion">Fabricación: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idComboFabricacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.fabricacion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getFabricacion()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Fabricación" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idClasificacionCriteriosConstruccionVehiculo">Criterios de construcción:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="idClasificacionCriteriosConstruccion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="vehiculoDto.criterioConstruccion" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioConstruccion()"
						listKey="idCriterioConstruccion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Construcción" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idClasificacionCriteriosUtilizacionVehiculo">Criterios de Utilización:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="idClasificacionCriteriosUtilizacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.criterioUtilizacion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioUtilizacion()"
						listKey="idCriterioUtilizacion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Utilización" disabled="true"/>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idDiplomatico">Diplomático:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox id="idDiplomatico" name="vehiculoDto.diplomatico" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idExcesoPeso">Exceso de Peso: <span></span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox id="idExcesoPeso" name="vehiculoDto.excesoPeso" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="plazas">Nº plazas de asiento: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPlazas" name="vehiculoDto.plazas" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3"
						onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="potenciaFiscal">Potencia Fiscal: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPotenciaFiscal" name="vehiculoDto.potenciaFiscal"
						onblur="t his.className='input2';" onfocus="this.className='inputfocus';"
						onchange="document.getElementById('idPotenciaFiscalInfo').value=document.getElementById('idPotenciaFiscal').value"
						size="14" maxlength="13" onkeypress="return validarDecimal(event, 2)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="cilindrada">Cilindrada: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNcilindros" name="vehiculoDto.cilindrada" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="9" maxlength="8" onkeypress="return validarDecimal(event, 2)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="co2">CO2: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idCo2" name="vehiculoDto.co2" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="8" maxlength="7" onkeypress="return validarDecimal(event, 3)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tara">TARA: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idtara" name="vehiculoDto.tara" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="8" maxlength="7" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="mma">MMA: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idMma" name="vehiculoDto.pesoMma" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="7" maxlength="6" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="mma"> Evoluci&oacute;n Kilometraje: </label>
				</td>
				<td style="border: 0px;">
					<img title="Ver evolución" onclick="consultaEvolucionKmVehiculo('<s:property value="vehiculoDto.idVehiculo"/>','<s:property value="vehiculoDto.numColegiado"/>');" style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
						alt="Ver evolución" src="img/history.png">
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">DATOS TARJETA ITV</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idCodigoItv">C&oacute;digo de ITV</label>
				</td>
				<td align="left" nowrap="nowrap" width="20%">
					<table>
						<tr>
							<td>
								<s:textfield id="idCodigoItv" name="vehiculoDto.codItv" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="10" maxlength="9" disabled="true"/>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap="nowrap" style="vertical-align: middle;" width="10%">
					<label for="idComboCategoriaEU">Categoría EU: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<table>
						<tr>
							<td>
								<s:select id="idComboCategoriaEU" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" name="vehiculoDto.idDirectivaCee"
									list="@trafico.utiles.UtilesVistaTrafico@getInstance().getListaDirectivasCEEVehiculo(vehiculoDto.criterioConstruccion)"
									listKey="idDirectivaCEE" listValue="idDirectivaCEE" headerKey="-1" headerValue="Categoría EU"/>
							</td>
							<td style="display: none">
								<s:select id="idComboCategoriaEUOculto" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getListaDirectivasCEEVehiculo(vehiculoDto.criterioConstruccion)"
									listKey="idDirectivaCEE" listValue="descripcion" headerKey="-1" headerValue="Homologación UE" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNive">NIVE:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNive" name="vehiculoDto.nive" onfocus="this.className='inputfocus';"
						size="33" maxlength="32" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNumSerie">Nºde Serie: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNumSerie" name="vehiculoDto.numSerie" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="12" maxlength="12" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idTipoTarjetaITV">Tipo de tarjeta ITV: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idTipoTarjetaITV" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.tipoTarjetaITV"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTarjetaITV()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="-" onchange="editarFechaCaducidad(this.value);" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="idEpigrafe">Ep&iacute;grafe: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:select id="idEpigrafe" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.epigrafe"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEpigrafes()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="Epigrafe" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idClasificacionItvPrever">Clasificaci&oacute;n: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idClasificacionItvPrever" name="vehiculoDto.clasificacionItv"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idVariante">Variante: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idVariante" name="vehiculoDto.variante" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="26" maxlength="25" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idVarianteBase">Variante Base: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idVarianteBase" name="vehiculoDto.varianteBase" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="26" maxlength="25" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idVersion">Versi&oacute;n: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idVersion" name="vehiculoDto.version" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="36" maxlength="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idVersionBase">Versi&oacute;n Base: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idVersionBase" name="vehiculoDto.versionBase" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="36" maxlength="35" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNumHomologacion">N&uacute;mero de	homologaci&oacute;n: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNumHomologacion" name="vehiculoDto.numHomologacion"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNumHomologacionBase">N&uacute;mero de homologaci&oacute;n Base: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNumHomologacionBase" name="vehiculoDto.numHomologacionBase"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoItv">Tipo ITV: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="tipoItv" name="vehiculoDto.tipoItv" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="26" maxlength="25" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoItv">Tipo ITV Base: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="tipoItvBase" name="vehiculoDto.tipoBase" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="26" maxlength="25" disabled="true"/>
				</td>
			</tr>
			<tr>	
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idTipo">Tipo Industria: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idTipo" name="vehiculoDto.tipoIndustria"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idMtaMma">MTA/MMA: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idMtaMma" name="vehiculoDto.mtmaItv" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="7" maxlength="6" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPotenciaPeso">Potencia/Peso: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPotenciaPeso" name="vehiculoDto.potenciaPeso"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="8" maxlength="7"
						onkeypress="return validarDecimal(event, 2)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPlazasPie">N&uacute;mero de plazas de pie: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPlazasPie" name="vehiculoDto.numPlazasPie" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="4" maxlength="3" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>

				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNumRuedas">N&uacute;mero de ruedas: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNumRuedas" name="vehiculoDto.numRuedas" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="3" maxlength="2" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPotenciaNeta">Potencia Neta (Kw): </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPotenciaNeta" name="vehiculoDto.potenciaNeta"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="14" maxlength="13"
						onkeypress="return validarDecimal(event, 3)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idConsumo">Consumo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idConsumo" name="vehiculoDto.consumo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="4" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idComboCarroceria">Carroceria: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idComboCarroceria" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.carroceria"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCarrocerias()"
						listKey="idCarroceria" listValue="descripcion" headerKey="" headerValue="Carroceria"
						cssStyle="position:relative; width:20em;" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idDistanciaEntreEjes">Distancia entre ejes: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idDistanciaEntreEjes" name="vehiculoDto.distanciaEjes"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
						onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idViaAnterior">Via anterior: </label>
				</td>
				<td align="left" nowrap="nowrap">
				<s:textfield id="idViaAnterior" name="vehiculoDto.viaAnterior"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
						onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idViaPosterior">Via posterior: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idViaPosterior" name="vehiculoDto.viaPosterior"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
						onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="idComboAlimentacion">Tipo alimentación: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idComboAlimentacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.tipoAlimentacion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposAlimentacion()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Tipo alimentación" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idSubasta">Subastado: <span></span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox id="idSubasta" name="vehiculoDto.subastado" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idFabricante">Fabricante: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idFabricante" name="vehiculoDto.fabricante" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="70" autocomplete="off" cssStyle="position:relative;" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idFabricante">Fabricante Base: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idFabricanteBase" name="vehiculoDto.fabricanteBase" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="70" autocomplete="off" cssStyle="position:relative;" disabled="true"/>
				</td>
			</tr>
			<tr>	
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idMom">MOM:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idMom" name="vehiculoDto.mom" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="8" maxlength="7" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idMomBase">MOM Base:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idMomBase" name="vehiculoDto.momBase" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="8" maxlength="7" onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNivelEmisiones">Nivel de emisiones: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNivelEmisiones" name="vehiculoDto.nivelEmisiones"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="16" maxlength="15" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idEcoInnovacion">ECO innovación: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idEcoInnovacion" name="vehiculoDto.ecoInnovacion" list="#{'Si':'Si', 'No':'No'}"
						headerKey="" headerValue="Eco innovación" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="idCategoriaElectrica">Categoría eléctrica: </label>
				</td>
				<td align="left" nowrap="nowrap">
						<s:select id="idCategoriaElectrica" name="vehiculoDto.categoriaElectrica"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCategoriaElectrica()"
							listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Categoria Electrica" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="idAutonomiaElectrica">Autonomía eléctrica: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idAutonomiaElectrica"
						name="vehiculoDto.autonomiaElectrica" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						size="7" maxlength="6" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idReduccionEco">Reducción ECO: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idReduccionEco" name="vehiculoDto.reduccionEco"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
						onkeypress="return validarNumerosEnteros(event)" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idCodigoEco">Código ECO: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idCodigoEco" name="vehiculoDto.codigoEco" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="11" maxlength="10" disabled="true"/>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">INTRODUCTOR DEL VEHÍCULO EN ESPAÑA</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="nifIntegrador">Nif</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNifIntegrador" name="vehiculoDto.nifIntegrador.nif"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="nombreIntroductor">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="nombreIntroductor" name="vehiculoDto.nifIntegrador.nombre"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="apellido1Introductor">Apellido o razón social:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="apellido1Introductor" name="vehiculoDto.nifIntegrador.apellido1RazonSocial"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="nombreIntroductor">Segundo apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="nombreIntroductor" name="vehiculoDto.nifIntegrador.apellido2" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="25" maxlength="25" disabled="true"/>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">DATOS VEHÍCULO	USADO </span></td>
				<td class="tabular">
					<s:checkbox id="idUsado" name="vehiculoDto.vehiUsado" disabled="true"/>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
			<tr>
				<td align="left" nowrap="nowrap" width="20%" style="vertical-align: middle;">
					<label for="idMatriculaAyuntamiento">Matr&iacute;cula Ayuntamiento: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idMatriculaAyuntamiento" name="vehiculoDto.matriAyuntamiento"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9" disabled="true"/>
					</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="dialimiteMatrTuris">L&iacute;mite de vigencia de matr&iacute;cula tur&iacute;stica: </label>
				</td>
				<td align="left" nowrap="nowrap" width="1%">
					<table>
						<tr>
							<td>
								<s:textfield id="dialimiteMatrTuris" name="vehiculoDto.limiteMatrTuris.dia"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true" />
								</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="meslimiteMatrTuris" name="vehiculoDto.limiteMatrTuris.mes"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true" />
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="aniolimiteMatrTuris" name="vehiculoDto.limiteMatrTuris.anio"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="140" style="vertical-align: middle;">
					<label for="diaPrimMatri">Fecha de Primera Matriculación: </label>
				</td>
				<td align="left" nowrap="nowrap" width="1%">
					<table>
						<tr>
							<td>
								<s:textfield id="diaPrimMatri" name="vehiculoDto.fechaPrimMatri.dia"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true" />
								</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="mesPrimMatri" name="vehiculoDto.fechaPrimMatri.mes"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true" /></td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="anioPrimMatri" name="vehiculoDto.fechaPrimMatri.anio"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idLugarPrimeraMatriculacion">Lugar Primera Matriculaci&oacute;n: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idLugarPrimeraMatriculacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.lugarPrimeraMatriculacion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getLugaresPrimeraMatriculacion()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="seleccione lugar" disabled="true" />
				</td>
				<td align="left" nowrap="nowrap" width="20%" style="vertical-align: middle;">
					<label for="idMatriculaOrigen">Matr&iacute;cula Origen: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idMatriculaOrigen"
						name="vehiculoDto.matriculaOrigen" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" size="10"
						maxlength="9" disabled="true" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="kmUso">nº kilometros de uso: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="kmUso" name="vehiculoDto.kmUso" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="7" maxlength="6" disabled="true" onkeypress="return validarNumerosEnteros(event)" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="horasUso">Horas de Uso: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="horasUso" name="vehiculoDto.horasUso" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="7" maxlength="6" disabled="true" onkeypress="return validarNumerosEnteros(event)" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idImportado">Importado:</label>
				</td>
				<td align="left" nowrap="nowrap" width="10%">
					<s:checkbox id="idImportado" onchange="desbloquearImportado(this.checked)" disabled="true" name="vehiculoDto.importado" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="10%">
					<label for="idLabelProcedencia">Procedencia: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:select id="idComboProcedencia" disabled="true" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.procedencia"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProcedencias()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Procedencia" />
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">LOCALIZACION DEL VEHICULO(Si es distinta a la del titular)</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idProvinciaVehiculo">Provincia: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
						headerKey="-1" headerValue="Seleccione Provincia" name="vehiculoDto.direccion.idProvincia"
						listKey="idProvincia" listValue="nombre" id="idProvinciaVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						onchange="cargarListaMunicipios(this,'idMunicipioVehiculo','municipioSeleccionadoVehiculo');
								  cargarListaTipoVia(this,'tipoViaVehiculo','tipoViaSeleccionadaVehiculo');
								  inicializarTipoVia('tipoViaVehiculo','nombreViaDireccionVehiculo',viaVehiculoMatriculacion);
								  borrarComboPueblo('idPuebloVehiculo');borrarComboPuebloCorreos('idPuebloVehiculoCorreos');" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idMunicipioVehiculo">Municipio: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="idMunicipioVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="disabled"
						onchange="cargarListaPueblos('idProvinciaVehiculo', this, 'idPuebloVehiculo', 'puebloSeleccionadoVehiculo');
									seleccionarCampo('municipioSeleccionadoVehiculo','idMunicipioVehiculo');
									obtenerCodigoPostalPorMunicipio('idProvinciaVehiculo', this, 'codPostalVehiculo');
									inicializarTipoVia('tipoViaVehiculo','nombreViaDireccionVehiculo',viaVehiculoMatriculacion);
									cargarListaPueblosDGT('idProvinciaVehiculo', this, 'idPuebloVehiculoCorreos', 'puebloSeleccionadoVehiculoCorreos');">
							<option value="-1">Seleccione Municipio</option> 
					</select> 
					<s:hidden id="municipioSeleccionadoVehiculo" name="vehiculoDto.direccion.idMunicipio" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPuebloVehiculo">Pueblo: </label>
				</td>
				<td align="left" nowrap="nowrap">
 					<select id="idPuebloVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onchange="seleccionarCampo('puebloSeleccionadoVehiculo','idPuebloVehiculo');"
						style="width: 200px;" disabled="disabled">
							<option value="-1">Seleccione Pueblo</option> 
					</select> 
					<s:hidden id="puebloSeleccionadoVehiculo" name="vehiculoDto.direccion.pueblo" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="codPostalVehiculo">Código Postal: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="codPostalVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.direccion.codPostal"
						onkeypress="return validarNumerosEnteros(event)" maxlength="5" size="5" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idPuebloVehiculoCorreos">Pueblo Correos: </label>
				</td>
				<td align="left" nowrap="nowrap">
 					<select id="idPuebloVehiculoCorreos" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onchange="seleccionarCampo('puebloSeleccionadoVehiculoCorreos','idPuebloVehiculoCorreos');"
						style="width: 200px;" disabled="disabled">
							<option value="-1">Seleccione Pueblo</option> 
					</select> 
					<s:hidden id="puebloSeleccionadoVehiculoCorreos" name="vehiculoDto.direccion.puebloCorreos" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="codPostalVehiculoCorreos">Código Postal Correos: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="codPostalVehiculoCorreos" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.direccion.codPostalCorreos"
						onkeypress="return validarNumerosEnteros(event)" maxlength="5" size="5" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoViaVehiculo">Tipo de vía: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="tipoViaVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="disabled"
						onchange="seleccionarCampo('tipoViaSeleccionadaVehiculo','tipoViaVehiculo'); 
								cargarListaNombresVia('idProvinciaVehiculo', 'municipioSeleccionadoVehiculo', this, 'nombreViaDireccionVehiculo',viaVehiculoMatriculacion);">
								<option value="-1">Seleccione Tipo Via</option> 
					</select>
					<s:hidden id="tipoViaSeleccionadaVehiculo" name="vehiculoDto.direccion.idTipoVia" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="nombreViaDireccionVehiculo">Nombre vía: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield id="nombreViaDireccionVehiculo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoDto.direccion.nombreVia"
						cssStyle="width:200px;position:relative;" size="40" maxlength="120" autocomplete="off" disabled="true"/>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="numeroDireccionVehiculo">Número: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="numeroDireccionVehiculo" name="vehiculoDto.direccion.numero"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="letraDireccionVehiculo">Letra: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="letraDireccionVehiculo" name="vehiculoDto.direccion.letra"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="escaleraDireccionVehiculo">Escalera: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="escaleraDireccionVehiculo" name="vehiculoDto.direccion.escalera"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="pisoDireccionVehiculo">Piso: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="pisoDireccionVehiculo" name="vehiculoDto.direccion.planta"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="puertaDireccionVehiculo">Puerta: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="puertaDireccionVehiculo" name="vehiculoDto.direccion.puerta"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="bloque">Bloque: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="bloqueDireccionVehiculo" name="vehiculoDto.direccion.bloque"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="kmDireccionVehiculo">Km: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="kmDireccionVehiculo" name="vehiculoDto.direccion.km"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="hmDireccionVehiculo">Hm: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="hmDireccionVehiculo" name="vehiculoBean.direccionBean.hm"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" disabled="true"/>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">PROGRAMA PREVER (MODELO 576)</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="bastidorPrever">Numero de Bastidor: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="bastidorPrever" name="vehiculoPreverDto.bastidor" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="19" maxlength="17" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" width="10%"
					style="vertical-align: middle;"><label for="matriculaPrever">Matricula:
				</label></td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="matriculaPrever" name="vehiculoPreverDto.matricula"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9" disabled="true" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="codigoMarcaPrever">Marca: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="codigoMarcaPrever" name="codigoMarcaPrever"
						onblur="this.className='input2'; validarMarca(this, document.getElementById('marcaPrever'), marcaVehiculoPreverMatriculacion, document.getElementById('checkCodigoMarcaPrever'), 'MATW');"
						onfocus="this.className='inputfocus';" size="23" maxlength="22"	autocomplete="off"
						cssStyle="position:relative; float:left;" disabled="true"/>
					&nbsp;
					<span id="checkCodigoMarcaPrever"></span>
					<s:hidden id="marcaPrever" name="vehiculoPreverDto.codigoMarca" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="modeloPrever">Modelo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="modeloPrever" name="vehiculoPreverDto.modelo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="9" maxlength="9" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idClasificacionCriteriosConstruccionVehiculoPrever">Criterios de construccion Prever: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="idClasificacionCriteriosConstruccionPrever" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoPreverDto.criterioConstruccion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioConstruccion()"
						listKey="idCriterioConstruccion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Construcción" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idClasificacionCriteriosUtilizacionVehiculoPrever">Criterios de Utilizacion Prever:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="idClasificacionCriteriosUtilizacionPrever" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="vehiculoPreverDto.criterioUtilizacion"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioUtilizacion()"
						listKey="idCriterioUtilizacion" listValue="descripcion" headerKey="-1" headerValue="Criterio de Utilización" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="clasificacionITVPrever">Clasificacion ITV: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="clasificacionITVPrever" name="vehiculoPreverDto.clasificacionItv"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9" readonly="true" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoItvPrever">Tipo Itv: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="tipoItvPrever" name="vehiculoPreverDto.tipoItv" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="9" maxlength="9" disabled="true"/>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">DATOS CORRESPONDIENTES A LA ITV</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idServicioTraficoAnterior">Servicio anterior:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:select id="idServicioTraficoAnterior" name="vehiculoDto.servicioTraficoAnterior.idServicio"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getServiciosTrafico('T2')"
						listKey="idServicio" listValue="descripcion" headerKey="-1" headerValue="Seleccione servicio anterior"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="vehiculoAgricola">Vehículos Agrícolas: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:checkbox id="vehiculoAgricola" name="vehiculoDto.vehiculoAgricola" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="motivosITV">Motivos ITV: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:select id="motivosITV" name="vehiculoDto.motivoItv"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getMotivosITV()"
						listKey="idMotivo" listValue="descripcion" headerKey="-1" headerValue="Seleccione motivo ITV"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="conceptoITV">Concepto / Resultado ITV: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:select id="conceptoITV" name="vehiculoDto.conceptoItv"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getResultadoITV()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="Seleccione resultado ITV"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
					<label for="diaInspeccion">Fecha de ITV: </label>
				</td>
				<td colspan="2">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="vehiculoDto.fechaInspeccion.dia" id="diaInspeccion"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="vehiculoDto.fechaInspeccion.mes" id="mesInspeccion"
									onblur="t his.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
							</td>
							<td>/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="vehiculoDto.fechaInspeccion.anio" id="anioInspeccion"
									onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="140" style="vertical-align: middle;">
					<label for="diaCaducidadTarjetaITV">Fecha de Caducidad ITV: </label>
				</td>
				<td>
					<table border="0">
						<tr>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="diaCaducidadTarjetaITV"
									name="vehiculoDto.fechaItv.dia" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="mesCaducidadTarjetaITV"
									name="vehiculoDto.fechaItv.mes" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="1%">
								<s:textfield id="anioCaducidadTarjetaITV"
									name="vehiculoDto.fechaItv.anio" onblur="this.className='input2';"
									onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estacionITV">Estación ITV: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="estacionITV" name="vehiculoDto.estacionItv" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstacionesITV()"
						listKey="estacionITV" listValue="municipio" headerKey="-1" headerValue="Seleccione estacion ITV"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>

		<table class="acciones" width="95%" align="left">
			<tr>
				<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
					<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaVehiculo.action';"
						onKeyPress="this.onClick" />
				</td>
			</tr>
		</table>

		<script>
			var viaVehiculoMatriculacion = new BasicContentAssist(document.getElementById('nombreViaDireccionVehiculo'), [], null, true);
			var fabricanteVehiculoMatriculacion = new BasicContentAssist(document.getElementById('idFabricante'), [], null, true);
			var fabricanteBaseVehiculoMatriculacion = new BasicContentAssist(document.getElementById('idFabricanteBase'), [], null, true);
			var marcaVehiculoMatriculacion = new BasicContentAssist(document.getElementById('codigoMarca'), [], null, true);
			var marcaVehiculoPreverMatriculacion = new BasicContentAssist(document.getElementById('codigoMarcaPrever'), [], null, true);
			var marcaBaseVehiculoMatriculacion = new BasicContentAssist(document.getElementById('codigoMarcaBase'), [], null, true);
			recargarComboMunicipios('idProvinciaVehiculo', 'idMunicipioVehiculo', 'municipioSeleccionadoVehiculo');
			recargarComboTipoVia('idProvinciaVehiculo', 'tipoViaVehiculo', 'tipoViaSeleccionadaVehiculo');
			recargarComboPueblos('idProvinciaVehiculo', 'municipioSeleccionadoVehiculo', 'idPuebloVehiculo', 'puebloSeleccionadoVehiculo');
			recargarComboLocalidadesDGT('idProvinciaVehiculo','municipioSeleccionadoVehiculo','idPuebloVehiculoCorreos', 'puebloSeleccionadoVehiculoCorreos');
			recargarNombreVias('idProvinciaVehiculo', 'municipioSeleccionadoVehiculo', 'tipoViaSeleccionadaVehiculo', 'nombreViaDireccionVehiculo', viaVehiculoMatriculacion);
			actualizarTitlesHomologacion();

			var listasMarcas = new Array(marcaVehiculoMatriculacion, marcaVehiculoPreverMatriculacion);
			var listasMarcasBase = new Array(marcaBaseVehiculoMatriculacion, marcaVehiculoPreverMatriculacion);
			var camposMarcas = new Array('codigoMarca', 'codigoMarcaPrever');
			var camposMarcasBase = new Array('codigoMarcaBase', 'codigoMarcaPrever');
			var hiddenMarcas = new Array('idMarca', 'marcaPrever');
			var hiddenMarcasBase = new Array('idMarcaBase', 'marcaPrever');

			for(var i = 0; i < camposMarcas.length; i++){
				cargarListaMarcasVehiculo(listasMarcas[i], camposMarcas[i], hiddenMarcas[i], "true");
			}

			for(var i = 0; i < camposMarcasBase.length; i++){
				cargarListaMarcasVehiculo(listasMarcasBase[i], camposMarcasBase[i], hiddenMarcasBase[i], "true");
			}

			cargarListaFabricantes('idMarca', 'idFabricante', fabricanteVehiculoMatriculacion);
			cargarListaFabricantes('idMarcaBase', 'idFabricanteBase', fabricanteBaseVehiculoMatriculacion);
		</script>
	</div>
</div>