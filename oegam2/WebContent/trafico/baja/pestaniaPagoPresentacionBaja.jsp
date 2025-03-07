<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Pago/Presentación</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">PAGO/PRESENTACIÓN</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="codigoTasaPagoPresentacion">Código de Tasa: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="5">
				<s:if test="tramiteTraficoBaja.tasa != null && tramiteTraficoBaja.tasa.codigoTasa != null">
					<s:select id="codigoTasaPagoPresentacion" onblur="this.className='input2';" disabled="%{flagDisabled}"
						onfocus="this.className='inputfocus';" name="tramiteTraficoBaja.tasa.codigoTasa"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaBajas(tramiteTraficoBaja.tasa.codigoTasa, tramiteTraficoBaja.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
				</s:if>
				<s:else>
					<s:select id="codigoTasaPagoPresentacion" onblur="this.className='input2';" disabled="%{flagDisabled}"
						onfocus="this.className='inputfocus';" name="tramiteTraficoBaja.tasa.codigoTasa"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaBajas(tramiteTraficoBaja.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
				</s:else>
			</td>
			<!--<td align="left" nowrap="nowrap" >
				<input type="button" class="boton" id="botonCargaTasa" value = "Ver Todas"
					onclick="javascript:verTodasTasas('idContratoTramite','codigoTasaPagoPresentacion');"/>
			</td>-->
			<td width="10"></td>

			<td align="left">
				<label for="jefaturaProvincialPagoPresentacion">Jefatura Provincial: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="jefaturaProvincialPagoPresentacion" onblur="this.className='input2';" disabled="%{flagDisabled}"
					onfocus="this.className='inputfocus';" name="tramiteTraficoBaja.jefaturaTraficoDto.jefaturaProvincial"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getJefaturasTraficoTodas()"
					headerKey="-1" headerValue="Seleccione Jefatura Provincial" listKey="jefatura_provincial" listValue="descripcion"/>
			</td>
		</tr>

		<tr></tr>
		<tr></tr>

		<tr>
			<td align="left">
				<label for="fechaPagoPresentacionBaja">Fecha Presentación: </label>
			</td>

			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="diaPresentacion" name="tramiteTraficoBaja.fechaPresentacion.dia"
					onblur="this.className='input2';" onkeypress="return validarDia(this,event)"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="false"/>
			</td>

			<td>/</td>

			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="mesPresentacion" name="tramiteTraficoBaja.fechaPresentacion.mes"
					onblur="this.className='input2';" onkeypress="return validarMes(this,event)"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="false"/>
			</td>

			<td>/</td>

			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="anioPresentacion" name="tramiteTraficoBaja.fechaPresentacion.anio"
					onblur="this.className='input2';" onkeypress="return validarAnio(this,event)"
					onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="false" />
			</td>
			<td align="left" nowrap="nowrap">
				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacion, document.formData.mesPresentacion, document.formData.diaPresentacion);return false;" title="Seleccionar fecha">
					<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
				</a>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="width: 250px;" colspan="6">
				<label for="idCema">Código Electrónico de Matriculación Agrícola (CEMA)</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="idCema" name="tramiteTraficoBaja.cema"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="8" maxlength="8"/>
			</td>
		</tr>
	</table>
	<table align="center">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultableOGuardableBaja(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bGuardarBaja" name="bGuardarBaja" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
	</table>
	<script>
		function noNecesitaTasa(){
			// Cambios de tasa de baja en función del motivo de baja
			if (document.getElementById("motivoBaja").value==5){
				// Si está marcado el check de certificado medioambiental marcado, no tenemos tasa
				if (document.getElementById("certificadoMedioambiental").checked) {
					document.getElementById("codigoTasaPagoPresentacion").disabled=true;
				} else {
					document.getElementById("codigoTasaPagoPresentacion").disabled=false;
					quitarTasasSiMasDeQuince("anioMatriculacionVehiculo","mesMatriculacionVehiculo","diaMatriculacionVehiculo");
				}
			} else if (document.getElementById("motivoBaja").value==7 || document.getElementById("motivoBaja").value==8){
				quitarTasasSiMasDeQuince("anioMatriculacionVehiculo","mesMatriculacionVehiculo","diaMatriculacionVehiculo");
			} else if (document.getElementById("motivoBaja").value==2 || document.getElementById("motivoBaja").value==12){
				document.getElementById("codigoTasaPagoPresentacion").disabled=true;
			}else{
				document.getElementById("codigoTasaPagoPresentacion").disabled=false;
			}

			// Cambios de tasa de duplicado en función del motivo de la baja
			if(document.getElementById("motivoBaja").value == 10 || document.getElementById("motivoBaja").value == 11){
				sitexComprobarCTITPrevio(document.getElementById('matriculaVehiculo').value);
			} else {
				document.getElementById("mensajeMotivo").style.display = 'none';
				document.getElementById("mensajeMotivo").innerHTML = '';
			}
		}

		noNecesitaTasa();
		habilitarCEMA();

		if (document.getElementById("permisoEspecial").value == "true") {
			document.getElementById("codigoTasaPagoPresentacion").disabled =true;
			document.getElementById("diaPresentacion").disabled =true;
			document.getElementById("mesPresentacion").disabled =true;
			document.getElementById("anioPresentacion").disabled =true;
			document.getElementById("idCema").disabled =true;
		}
	</script>
</div>