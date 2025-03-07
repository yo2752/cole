<%@ taglib prefix="s" uri="/struts-tags" %>

<script src="js/ivtm.js" type="text/javascript"></script>

<s:set var="finalizado" value="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esConsultableOGuardableMATW(tramiteTrafMatrDto)}"/>
<s:set var="admin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"/>
<s:set var="permisoIVTM" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAutoliquidarIvtm()}"/>
<s:set var="IVTMEstadoPendiente" value="%{@escrituras.utiles.UtilesVista@getInstance().estadoPendienteIvtm(ttramiteTrafMatrDto.ivtmMatriculacionDto.estadoIvtm)}"/>
<s:set var="IVTMEstadoFin" value="%{@escrituras.utiles.UtilesVista@getInstance().estadoFinIvtm(tramiteTrafMatrDto.ivtmMatriculacionDto.estadoIvtm)}"/>
<s:set var="pagoIVTM" value="%{@escrituras.utiles.UtilesVista@getInstance().pagoIvtm(tramiteTrafMatrDto.ivtmMatriculacionDto.estadoIvtm)}"/>

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
	<tr>
		<td class="tabular">
			<span class="titulo">DECLARACIÓN DE ALTA EN EL IMPUESTO SOBRE VEHÍCULOS DE TRACCIÓN MECÁNICA</span>
		</td>
	</tr>
</table>

<s:hidden value="%{finalizado}" name="asasas" id="we"></s:hidden>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="idProvinciaIVTM">Provincia de Pago<span class="naranja">*</span></label>
			<s:textfield disabled="%{flagDisabled}" id="idProvinciaIVTM" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" readonly="true" name="provinciaIVTM" style="background:#EEEEEE"/>
		</td>

		<td align="left" nowrap="nowrap">
			<label for="idMunicipioIVTM">Municipio de Pago <span class="naranja">*</span></label>
			<s:textfield disabled="%{flagDisabled}" id="idMunicipioIVTM" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" readonly="true" name="municipioIVTM" style="background:#EEEEEE"/>
		</td>

		<td align="left" nowrap="nowrap">
			<label for="idPuebloIVTM">Pueblo<span class="naranja">*</span></label>
			<s:textfield disabled="%{flagDisabled}" id="idPuebloIVTM" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" readonly="true" name="puebloIVTM" style="background:#EEEEEE"
				value="%{tramiteTrafMatrDto.titular.direccion.pueblo}"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="importeIvtm">Importe</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.importe"
				id="importeIvtm" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="9" maxlength="9"
				onkeypress="return validarNumerosDecimales(event)"/>
		</td>
		<td>
			<table>
				<tr>
					<td align="left" nowrap="nowrap" >
						<span id="spanLabelIdExentoIvtm" style="display:block">
							<label for="idExentoIvtm">Exento</label>
						</span>
					</td>
					<td align="left">
						<span id="spanIdExentoIvtm" style="display:block">
							<s:checkbox name="ivtmMatriculacionDto.exento" id="idExentoIvtm" onChange="bloquearImporteIVTM();" />
						</span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<s:if test="#permisoIVTM">
		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelIdImporteAnual" style="display:none">
					<label for="importeAnual">Importe Anual</label>
				</span>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanIdImporteAnual" style="display:none">
					<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.importeAnual"
						id="importeAnualIvtm" onblur="this.className='input2';" readonly="true"
						onfocus="this.className='inputfocus';" size="9" maxlength="9"
						onkeypress="return validarNumerosDecimales(event)"
						style="background:#EEEEEE"/>
				</span>
			</td>
		</tr>
	</s:if>
	<tr>
		<td width="22%" align="left" width="14%">
			<label for="idDiaPagoIVTM">Fecha de Pago <span class="naranja">*</span></label>
		</td>
		<td>
			<table>
				<tr>
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.fechaPago.dia" id="idDiaPagoIVTM"
							onblur="this.className='input2';" onkeypress="return validarDia(this,event)"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td width="3%" style="font-size:15px;vertical-align:middle;">/</td>
					<td width="5%" nowrap="nowrap" align="left">
						<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.fechaPago.mes" id="idMesPagoIVTM"
							onblur="this.className='input2';" onkeypress="return validarMes(this,event)"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td width="3%" style="font-size:15px;vertical-align:middle;">/</td>
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.fechaPago.anio" id="idAnioPagoIVTM"
							onblur="this.className='input2';" onkeypress="return validarAnio(this,event)"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" />
					</td>
					<td align="left" nowrap="nowrap" width="5%">
						<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioPagoIVTM, document.formData.idMesPagoIVTM, document.formData.idDiaPagoIVTM);return false;" HIDEFOCUS title="Seleccionar fecha">
							<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
						</a>
					</td>
					<td width="auto" nowrap="nowrap" align="left">	</td>
				</tr>
			</table>
		</td>
	</tr>
	<s:if test="#permisoIVTM">
		<tr>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<span id="spanLabelBonMedAmbIvtm" style="display:none">
								<label for="idBonMedAmbIvtm">Bon. Medio Ambiente</label>
							</span>
						</td>
						<td align="left">
							<span id="spanBonMedAmbIvtm" style="display:none">
								<s:checkbox name="ivtmMatriculacionDto.bonmedam" id="idBonMedAmbIvtm" onChange="activarMedioAmbienteIVTM();"/>
							</span>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelPorcentajeBonMedAmbIvtm" style="display:none">
					<label for="idPorcentajeBonMedAmbIvtm">Porcentaje de la Bon. Medio Ambiente</label>
				</span>
			</td>
			<td>
				<span id="spanPorcentajeBonMedAmbIvtm" style="display:none">
					<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.porcentajebonmedam" id="idPorcentajeBonMedAmbIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" onkeypress="return validarNumerosDecimales(event)" onChange="return validarPorcentajeBonificacionMedioAmbiente(event)"/>
				</span>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelIdCCCIvtm" style="display:none">
					<label for="idCCIvtm">CCC (formato IBAN - Empieza por ES) <span class="naranja">*</span></label>
				</span>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanIdCCCIvtm" style="display:none">
					<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.iban" id="idCCIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" onchange="comprobarIBANEs();" size="30" maxlength="24"/>
				</span>
			</td>
			<td>
				<s:hidden id="idIbanTitular" name="ibanTitular" />
				<span id="spanIdBotonCargarIban" style="display:none">
					<input id="idBotonCargarIban" type="button" onClick="$('#idCCIvtm').val($('#idIbanTitular').val())" value="Cargar el IBAN del Titular" />
				</span>
			</td>
		</tr>
	</s:if>

	<tr>
		<td align="left" nowrap="nowrap">
			<label for="idNrcIvtm">Nº Autoliquidación</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="2">
			<table>
				<tr>
					<td>
						<s:textfield name="ivtmMatriculacionDto.nrc" id="idNrcIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
					</td>
					<td style="border: 0px;vertical-align:top">
						<s:if test="#permisoIVTM">
							<span id="spanBotonIvtm" style="display:none">
								<s:if test="#finalizado || #IVTMEstadoPendiente || (!#admin && #IVTMEstadoFin)">
									<input type="button" class="botonMasMasGrande" name="bConsultarIVTM" id="idConsultarIVTM" value="Consulta Nº de Autoliquidación" disabled="disabled" onKeyPress="this.onClick"/>
								</s:if>
								<s:else>
									<input type="button" class="botonMasMasGrande" name="bConsultarIVTM" id="idConsultarIVTM" value="Consulta Nº de Autoliquidación" onClick="return autoliquidarIVTMMatriculacion();" onKeyPress="this.onClick"/>
								</s:else>
							</span>
						</s:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<s:if test="#permisoIVTM">
		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelIdDigControlIvtm" style="display:none">
					<label for="digitoControlIvtm">Dígito de Control</label>
				</span>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanIdDigControlIvtm" style="display:none">
					<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.digitoControl" id="digitoControlIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="true" style="background:#EEEEEE"/>
				</span>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelIdCodGestIvtm" style="display:none">
					<label for="idCodGestIvtm">Código Gestor</label>
				</span>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanIdCodGestIvtm" style="display:none">
					<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.codGestor" id="idCodGestIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9" readonly="true" style="background:#EEEEEE"/>
				</span>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelEmisorIvtm" style="display:none">
					<label for="emisorIvtm">Emisor</label>
				</span>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanEmisorIvtm" style="display:none">
					<s:textfield disabled="%{flagDisabled}" name="ivtmMatriculacionDto.emisor" id="emisorIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="25" maxlength="25" readonly="true" style="background:#EEEEEE"/>
				</span>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelCodigoRespuestaPago" style="display:none">
					<label for="idCodigoRespuestaPago">Respuesta Pago:</label>
				</span>
			</td>
			<td align="left" nowrap="nowrap">
				<span id="spanCodigoRespuestaPago" style="display:none">
					<s:if test="%{ivtmMatriculacionDto.codigoRespuestaPago != null}">
						<s:label value="%{ivtmMatriculacionDto.codigoRespuestaPago.nombreEnum}"/>
					</s:if>
				</span>
				</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<table>
					<tr>
						<td style="border: 0px;vertical-align:top">
							<s:if test="#permisoIVTM">
							<span id="spanBotonPagoIvtm" style="display:none">
								<s:if test="#pagoIVTM">
									<input type="button" class="botonMasMasGrande" name="bPagoIVTM" id="idPagoIVTM" value="Realizar Pago IVTM" onClick="return pagoIVTMMatriculacion();" onKeyPress="this.onClick"/>
								</s:if>
								<s:else>
									<input type="button" class="botonMasMasGrande" name="bPagoIVTM" id="idPagoIVTM" value="Realizar Pago IVTM" disabled="disabled" onKeyPress="this.onClick"/>
								</s:else>
							</span>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</s:if>
</table>

<script type="text/javascript">
	$(document).ready(function(){recargarDireccionIVTM()});
</script>