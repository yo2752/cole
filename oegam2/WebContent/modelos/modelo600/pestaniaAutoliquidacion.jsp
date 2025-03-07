<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Modelo 600</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">AutoLiquidación</span>
			</td>
		</tr>
	</table>
	<table class="tablaformbasica">
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="labelValorDeclarado">Valor Declarado<span
					class="naranja">*</span>:
			</label></td>
			<td align="left" nowrap="nowrap"><s:textfield
					id="idValorDeclarado" name="modeloDto.valorDeclarado"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10"
					onkeyup="return cambiarComa(this);"
					onchange="javascript:calcularBaseImponibleDerReal();" /></td>
			<td align="left" nowrap="nowrap">
				<!-- 		<input type="button" class="boton" name="bCaluclar" id="idVerEscritura"
					value="Calcular" onClick="javascript:calcularLiquidacion();"onKeyPress="this.onClick"/> -->
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="labelBonificacionCuota">Bonificación</label></td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<s:select list="modeloDto.modelo.bonificaciones"
								id="idBonificaciones" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								name="modeloDto.idBonificacion"
								listValue="descripcion" listKey="bonificacion"
								title="Bonificación" headerKey=""
								headerValue="Seleccione Bonificacion"
								onchange="javascript:cargarMonto(this,'idBonificacionMonto');" />
						</td>
						<td>
							<s:textfield name="modeloDto.bonificacion.monto"
								id="idBonificacionMonto" size="3" maxlength="3"
								onblur="this.className='input';"
								onfocus="this.className='inputfocus';" disabled="true" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td style="vertical-align: middle;"><label for="labelExento">Exento</label>
						</td>
						<td><s:checkbox name="modeloDto.exento" id="idExento"
								onblur="this.className='input';"
								onfocus="this.className='inputfocus';"
								onchange="javascript:cambiarExento('idExento','idNoSujeto', 'idFundamentoExento','idFundamentoNoSujeto');" />
						</td>
						<td style="vertical-align: middle;"><label
							for="labelNoSujeto">No Sujeto</label></td>
						<td><s:checkbox name="modeloDto.noSujeto" id="idNoSujeto"
								onblur="this.className='input';"
								onfocus="this.className='inputfocus';"
								onchange="javascript:cambiarNoSujeto('idNoSujeto','idExento','idFundamentoNoSujeto','idFundamentoExento');" />
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap"><label for="labelFundamento">Fundamento
								legal:</label></td>
						<td align="left" nowrap="nowrap"><s:textfield
								id="idFundamentoNoSujeto" name="modeloDto.fundamentoNoSujeccion"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="30" disabled="true"
								tooltip="Fundamento legal de la exención o no sujeción" /> <s:select
								list="modeloDto.modelo.fundamentosExencion"
								id="idFundamentoExento" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								name="modeloDto.idFundamentoExencion"
								listValue="descripcion" listKey="fundamentoExcencion"
								title="Fundamento legal de la exención o no sujeción"
								headerKey="" headerValue="Seleccione Fundamento" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td style="vertical-align: middle;"><label
							for="labelliquidCompl">Liquidación Complementaria:</label></td>
						<td><s:checkbox name="modeloDto.liquidacionComplementaria"
								id="idLiquiComp" onblur="this.className='input';"
								onfocus="this.className='inputfocus';"
								onchange="javascript:habilitarCamposLiq('idDiaFechaPrimLiq','idMesFechaPrimLiq', 'idAnioFechaPrimLiq','idCalendarFechaPrimLiq','idLiquiComp','idNumJustPrimLiq','idNumPrimLiq');" />
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap"><label for="labelFecha">Fecha:</label>
						</td>
						<td align="left" nowrap="nowrap" colspan="2">
							<table>
								<tr>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
											name="modeloDto.fechaPrimLiquidacion.dia"
											id="idDiaFechaPrimLiq" onblur="this.className='input2';"
											onkeypress="return validarNumerosEnteros(event)"
											onfocus="this.className='inputfocus';" size="2" maxlength="2"
											disabled="true" /></td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
											name="modeloDto.fechaPrimLiquidacion.mes"
											id="idMesFechaPrimLiq" onblur="this.className='input2';"
											onkeypress="return validarNumerosEnteros(event)"
											onfocus="this.className='inputfocus';" size="2" maxlength="2"
											disabled="true" /></td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
											name="modeloDto.fechaPrimLiquidacion.anio"
											id="idAnioFechaPrimLiq" onblur="this.className='input2';"
											onkeypress="return validarNumerosEnteros(event)"
											onfocus="this.className='inputfocus';" size="4" maxlength="4"
											disabled="true" /></td>

									<td align="left" nowrap="nowrap"><a
										id="idCalendarFechaPrimLiq" href="javascript:;"
										onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaPrimLiq, document.formData.idMesFechaPrimLiq, document.formData.idDiaFechaPrimLiq);return false;"
										title="Seleccionar fecha"> <img class="PopcalTrigger"
											align="middle" src="img/ico_calendario.gif" width="15"
											height="16" border="0" alt="Calendario" />
									</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="labelNJustiPrimLiq">Número
					de primera Autoliquidación:</label></td>
			<td align="left" nowrap="nowrap"><s:textfield
					name="modeloDto.numeroPrimPresentacion" id="idNumJustPrimLiq"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="13"
					disabled="true" /></td>
			<%-- 	<td align="left" nowrap="nowrap">
				<label for="labelNumPresentacion">Número de presentación:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.numeroPrimPresentacion" id="idNumPrimLiq" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="25" disabled="true"/>
			</td> --%>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="4">
				<table>
					<tr>
						<td align="left"><s:checkbox
								name="modeloDto.liquidacionManual" id="idLiqManual"
								onblur="this.className='input';"
								onfocus="this.className='inputfocus';"
								onchange="javascript:habilitarCamposLiquidacion();" /></td>
						<td colspan="3" style="vertical-align: middle;">El Gestor
							Administrativo Colegiado manifiesta bajo su responsabilidad que
							los datos de la autoliquidación han sido introducidos
							manualmente.</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div id="divRenta">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
			align="left">
			<tr>
				<td class="tabular"><span class="titulo">Renta</span></td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap"><label for="labelTipoRenta">Tipo
						de Renta:</label></td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap"><s:radio id="duracionRenta"
									cssStyle="display:inline;" labelposition="left"
									name="modeloDto.duracionRenta"
									list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getDuracionRenta()"
									listKey="valorEnum" listValue="nombreEnum"
									onchange="javascript:deshabilitarPeriodoRenta()">
								</s:radio></td>
						</tr>
						<tr>
							<td align="left" nowrap="nowrap"><s:radio id="periodoRenta"
									cssStyle="display:inline;" labelposition="left"
									name="modeloDto.tipoPeriodoRenta"
									list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getPeriodosRenta()"
									listKey="valorEnum" listValue="nombreEnum" disabled="true">
								</s:radio></td>
							<td><label for="labelNperiodos">Nº de periodos (años
									o meses):</label></td>
							<td align="left" nowrap="nowrap"><s:textfield
									id="idNumPeriodo" name="modeloDto.numPeriodoRenta"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="10"
									disabled="true"
									onchange="javascript:calcularBaseImponibleRenta();" />
							<td>
						<tr>
						<tr>
							<td align="left" nowrap="nowrap"><label
								for="labelArrendamientoDestVivienda">Arrendamiento
									destinado a vivienda:</label></td>
							<td align="left" nowrap="nowrap"><s:select
									label="Seleccione" list="#{'1':'Si', '0':'No'}"
									name="modeloDto.destVivienda" value="0" /></td>
						</tr>
						<tr>
							<td align="left" nowrap="nowrap"><label for="labelTipoRenta">Importe
									Renta:</label></td>
							<td align="left" nowrap="nowrap"><s:textfield
									id="idImporteRenta" name="modeloDto.importeRenta"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="10"
									onchange="javascript:calcularBaseImponibleRenta();" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="divDerechosReales">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
			align="left">
			<tr>
				<td class="tabular"><span class="titulo">Derechos Reales</span></td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap"><label for="labelDerReal">Derecho
						Real:</label></td>
				<td align="left" nowrap="nowrap">
					<s:select
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaDerechosReales()"
						id="idDerechoReal" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="modeloDto.descDerechoReal" listValue="nombreEnum"
						listKey="valorEnum" headerKey="-1"
						headerValue="Seleccione Derecho Real"
						onchange="javascript:calcularBaseImponibleDerReal();" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<s:radio id="duracion"
						cssStyle="display:inline;" labelposition="left"
						name="modeloDto.duracionDerechoReal"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getDuracionDerechoReal()"
						listKey="valorEnum" listValue="nombreEnum"
						onchange="javascript:habilitarCamposDerReal();javascript:calcularBaseImponibleDerReal();">
					</s:radio>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap"><label for="labelNumAnios">Número
									de años:</label></td>
							<td align="left" nowrap="nowrap"><s:textfield id="idNumAnio"
									name="modeloDto.numAnio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="10"
									disabled="true"
									onchange="javascript:calcularBaseImponibleDerReal();" /></td>
							<td align="left" nowrap="nowrap">
								<label for="labelNumAnios">Fecha de nacimiento del usufructuario:</label>
							</td>
							<td align="left" nowrap="nowrap">
								<table>
									<tr>
										<td align="left" nowrap="nowrap" width="5%"><s:textfield
												name="modeloDto.nacimientoUsufructuario.dia"
												id="idDiaNacUsufructurario"
												onblur="this.className='input2';"
												onkeypress="return validarNumerosEnteros(event)"
												onfocus="this.className='inputfocus';" size="2"
												maxlength="2" disabled="true" />
										</td>
										<td width="1%">/</td>
										<td align="left" nowrap="nowrap" width="5%"><s:textfield
												name="modeloDto.nacimientoUsufructuario.mes"
												id="idMesNacUsufructurario"
												onblur="this.className='input2';"
												onkeypress="return validarNumerosEnteros(event)"
												onfocus="this.className='inputfocus';" size="2"
												maxlength="2" disabled="true" />
										</td>
										<td width="1%">/</td>
										<td align="left" nowrap="nowrap" width="5%"><s:textfield
												name="modeloDto.nacimientoUsufructuario.anio"
												id="idAnioNacUsufructurario"
												onblur="this.className='input2';"
												onkeypress="return validarNumerosEnteros(event)"
												onfocus="this.className='inputfocus';" size="4"
												maxlength="4" disabled="true"
												onchange="javascript:calcularBaseImponibleDerReal();" />
										</td>
										<td align="left" nowrap="nowrap"><a
											id="idCalendarNacUsufructurario" href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioNacUsufructurario, document.formData.idMesNacUsufructurario, document.formData.idDiaNacUsufructurario);calcularBaseImponibleDerReal();return false;"
											title="Seleccionar fecha"> <img class="PopcalTrigger"
												align="middle" src="img/ico_calendario.gif" width="15"
												height="16" border="0" alt="Calendario" />
										</a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="divLiquidaciones">
		<jsp:include page="../liquidacion.jsp" flush="false"></jsp:include>
	</div>
</div>
<script type="text/javascript">
	inicializarCamposAutoLiq('idExento', 'idNoSujeto', 'idFundamentoExento',
			'idFundamentoNoSujeto', 'idDiaFechaPrimLiq', 'idMesFechaPrimLiq',
			'idAnioFechaPrimLiq', 'idCalendarFechaPrimLiq', 'idLiquiComp',
			'idNumJustPrimLiq', 'idNumPrimLiq');
</script>