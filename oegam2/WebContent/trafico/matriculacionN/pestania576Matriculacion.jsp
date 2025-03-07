<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contentTabs" id="576" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos de Modelos (576, 05 y 06)</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE LIQUIDACIÓN (MODELO 576)</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="idBaseImponible576">Base Imponible</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="idBaseImponible576"
						name="tramiteTrafMatrDto.baseImponible576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="right" nowrap="nowrap" style="vertical-align: middle;">
								<label for="reduccion">Reducción</label>
							</td>
							<td align="left" nowrap="nowrap">
								<s:checkbox disabled="%{flagDisabled}" name="tramiteTrafMatrDto.reduccion576"
									onclick="javascript:borrarCampos576();" onkeypress="this.onClick" cssStyle="display:inline" id="reduccion576"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="baseImponibleReducida">Base Imp. Reducida</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="baseImponibleR576"
						name="tramiteTrafMatrDto.baseImpoReducida576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="tipoGravamen">Tipo Gravamen</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield disabled="%{flagDisabled}" id="tipoGravamen576"
						name="tramiteTrafMatrDto.tipoGravamen576"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosDecimales(event)" size="7" maxlength="7"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="cuota">Cuota</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="cuota576"
						name="tramiteTrafMatrDto.cuota576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="deduccionLineal">Deducción Lineal</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield disabled="%{flagDisabled}" id="deduccionlineal576"
						name="tramiteTrafMatrDto.deduccionLineal576"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosDecimales(event)" size="10" maxlength="10"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="cuotaIngresar">Cuota a ingresar</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="cuotaIngresar576"
						name="tramiteTrafMatrDto.cuotaIngresar576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="aDeducir">A deducir</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield disabled="%{flagDisabled}" id="aDeducir576"
						name="tramiteTrafMatrDto.deducir576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="importeTotal576">Resultado de la liquidación</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="liquidacion576"
						name="tramiteTrafMatrDto.liquidacion576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="numDeclaracionComplementaria">Nº Declaración Complementaria</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield disabled="%{flagDisabled}" id="numDeclaracionComplementaria"
						name="tramiteTrafMatrDto.numDeclaracionComp576" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosDecimales(event)" size="30" maxlength="30"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="importeTotal576">Importe Total</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="importeTotal576"
						name="tramiteTrafMatrDto.importe576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)"
						size="10" maxlength="10"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="ejercicioDevengo">Ejercicio de Devengo</label>
				</td>
				<td align="left">
					<s:select id="ejercicioDevengo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
						name="tramiteTrafMatrDto.ejercicio576"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEjerciciosDevengo()"
						headerKey="-1" headerValue="TODOS">
					</s:select>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="causaHechoImponible">Causa Hecho Imponible</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
					<s:select id="causaHechoImponible" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 	disabled="%{flagDisabled}"
						name="tramiteTrafMatrDto.causaHechoImpon576"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCausasHechoImponible()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="TODOS"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="observaciones">Observaciones</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
					<s:select id="observaciones" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
						name="tramiteTrafMatrDto.observaciones576"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getObservaciones()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="TODOS"/>
				</td>
				<td style="display:none">
					<s:select id="observacionesOculto" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getObservaciones()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="TODOS"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="right">
					<input id="bPresentarAEAT" type="button" class="botonGrande" name="bPresentarAEAT" value="Presentar en la AEAT"
						onClick="presentarAEATMatriculacion()" onkeyPress="this.onClick"
					<s:if test="%{readOnly}">
						disabled="disabled"
					</s:if>/>
				</td>
				<td>
					<input id="idBDescargarPresentacionAEAT" type="button" class="botonGrande" name="bDescargarPresentacionAEAT" value="Descargar AEAT"
						onClick="descargarPresentacionAEAT()" onkeyPress="this.onClick"/>
				</td>
				<td align="left">
					<img id="loadingImagePresentarAEAT" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<s:if test="%{pdf576}">
					<td nowrap="nowrap" style="vertical-align: middle;">
						<div id="respuesta576Link" style="cursor:pointer;" onclick="descargaRespuesta576Matw();">
							<u>Descargar justificante de la presentación en pdf</u>
						</div>
					</td>
				</s:if>
			</tr>
			<tr><td>&nbsp;</td></tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="respuesta576">Respuesta 576</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="respuesta576"
						name="tramiteTrafMatrDto.respuesta576" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="100" maxlength="100" readonly="true"/>
					<s:hidden id="respuesta" name="tramiteTrafMatrDto.respuesta"/>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS ADICIONALES DEL IMPUESTO ESPECIAL SOBRE DETERMINADOS MEDIOS DE TRANSPORTE</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="reduccionNoexencion">Reducción, no sujeción o exención solicitada (05)</label>
				</td>
				<td align="left">
					<s:select id="idReduccionNoSujeccion05" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}" name="tramiteTrafMatrDto.idReduccion05"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getReduccionesNoSujeccionOExencion05()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="TODOS"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="NoSujeccion">No sujeción o exención solicitada (06)</label>
				</td>
				<td align="left" >
					<s:select id="idNoSujeccionOExencion06" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}" name="tramiteTrafMatrDto.idNoSujeccion06"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getNoSujeccionesOExencion06()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="TODOS"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="7">
					<label for="NoSujeccion">LIMITACIÓN DE DISPOSICIÓN (en caso de exención del IEDTM)</label>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td nowrap="nowrap" align="left" colspan="3">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<label for="tipoLimitacionDisposicion">Tipo de Limitación</label>
							</td>
							<td align="left">
								<s:select id="idTipoLimitacionDisposicionIEDTM" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									disabled="%{flagDisabled}" name="tramiteTrafMatrDto.iedtm"
									list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposLimitacionDisposicionIEDTM()"
									listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue="NO HAY"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="22%" align="left" width="14%">
					<label for="diaAltaIni">Fecha Inicio de la Exención:</label>
				</td>
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.fechaIedtm.dia" onkeypress="return validarDia(this,event)"
						id="diaInicioExencion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.fechaIedtm.mes" onkeypress="return validarMes(this,event)"
						id="mesInicioExencion" 	onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.fechaIedtm.anio" onkeypress="return validarAnio(this,event)"
						id="anioInicioExencion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="5" maxlength="4" />
				</td>
				<td align="left" nowrap="nowrap">
					<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioExencion, document.formData.mesInicioExencion, document.formData.diaInicioExencion);return false;" title="Seleccionar fecha">
						<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
					</a>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="20%">
					<label for="nombre">Número de registro</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="numRegistro" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onchange="javascript:exentoIEDTM();" disabled="%{flagDisabled}"
						name="tramiteTrafMatrDto.regIedtm" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoMotivoExencion()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1" headerValue=""/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nombre">Nombre de la financiera</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="idNombreFinanciera"
						name="tramiteTrafMatrDto.financieraIedtm" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="50" maxlength="100" readonly="true"/>
				</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE PAGO DEL MODELO 576</span>
				</td>
			</tr>
		</table>
		<table class="tablaformbasica" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="18%">
					<label for="exentoNRC">Exento o cuota 0</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox id="exentoNRC" name="tramiteTrafMatrDto.exento576"
						value="%{tramiteTrafMatrDto.exento576}" onclick="javascript:deshabilitarNRC(this.checked);actualizarNRCHidden();"
						onkeypress="this.onClick" disabled="%{readOnly}" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="nrc">NRC</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="6">
					<s:textfield disabled="%{flagDisabled}" id="nrc576"
						name="tramiteTrafMatrDto.nrc576" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="24" maxlength="24"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="12%">
					<label for="diaAltaIni">Fecha de Pago:</label>
				</td>
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield disabled="%{flagDisabled}" id="idDiaPago576" onkeypress="return validarDia(this,event)"
						name="tramiteTrafMatrDto.fechaPago576.dia" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield disabled="%{flagDisabled}" id="idMesPago576" onkeypress="return validarMes(this,event)"
						name="tramiteTrafMatrDto.fechaPago576.mes" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield disabled="%{flagDisabled}" id="idAnioPago576" onkeypress="return validarMes(this,event)"
						name="tramiteTrafMatrDto.fechaPago576.anio" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="4" />
				</td>
				<td>
					<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioPago576, document.formData.idMesPago576, document.formData.idDiaPago576);return false;" title="Seleccionar fecha">
						<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled}
							width="15" height="16" border="0" alt="Calendario"/>
					</a>
				</td>
			</tr>
		</table>
	</div>
</div>

<!-- FIXME: Puesto para que funcione el JavaScript de calcular. Identificar name para el action? -->
<s:hidden id="hiddenExentoNRC"/>

<script type="text/javascript">
	actualizarTitlesObservaciones();
	//JMC : Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
	if (document.getElementById("permisoEspecial").value == "true") {
		document.getElementById("exentoNRC").disabled = true;
	}
	// Mantis 11763. David Sierra: Desahabilitar o habilitar campos en función de la selección del checkbox exentoNRC
	if (document.getElementById("exentoNRC").checked) {
		document.getElementById("nrc576").disabled = true;
		document.getElementById("idDiaPago576").disabled = true;
		document.getElementById("idMesPago576").disabled = true;
		document.getElementById("idAnioPago576").disabled = true;
	}
	if (!document.getElementById("exentoNRC").checked) {
		document.getElementById("nrc576").disabled = false;
		document.getElementById("idDiaPago576").disabled = false;
		document.getElementById("idMesPago576").disabled = false;
		document.getElementById("idAnioPago576").disabled = false;
	}
</script>