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
		<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()">
			<td align="left" nowrap="nowrap">
				<label id ="labelcodigoTasaPagoPresentacion" for="codigoTasaPagoPresentacion">Código tasa permiso: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="5">
				<s:if test="tramiteTraficoDuplicado.tasaPermiso != null">
					<s:select id="codigoTasaPermiso"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.tasaPermiso" disabled="%{flagDisabled}"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicados(tramiteTraficoDuplicado.tasaPermiso, tramiteTraficoDuplicado.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
				</s:if>
				<s:else>
					<s:select id="codigoTasaPermiso"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.tasaPermiso" disabled="%{flagDisabled}"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicados(tramiteTraficoDuplicado.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
				</s:else>
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<label id ="labelcodigoTasaPagoPresentacion" for="codigoTasaPagoPresentacion">Código de Tasa: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="5">
				<s:if test="tramiteTraficoDuplicado.tasa != null && tramiteTraficoDuplicado.tasa.codigoTasa != null">
					<s:select id="codigoTasaPagoPresentacion"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.tasa.codigoTasa" disabled="%{flagDisabled}"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicados(tramiteTraficoDuplicado.tasa.codigoTasa, tramiteTraficoDuplicado.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa" />
				</s:if>
				<s:else>
					<s:select id="codigoTasaPagoPresentacion"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoDuplicado.tasa.codigoTasa" disabled="%{flagDisabled}"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicados(tramiteTraficoDuplicado.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa" />
				</s:else>
			</td>
		</s:else>
			<!--<td align="left" nowrap="nowrap" >
				<input type="button" class="boton" id="botonCargaTasa" value = "Ver Todas"
					onclick="javascript:verTodasTasas('idContratoTramite','codigoTasaPermiso');"/>
			</td>-->

			<td width="10"></td>

			<td align="left">
				<label for="jefaturaProvincialPagoPresentacion">Jefatura Provincial: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="jefaturaProvincialPagoPresentacion" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoDuplicado.jefaturaTraficoDto.jefaturaProvincial"
					disabled="%{flagDisabled}" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getJefaturasTraficoTodas()"
					headerKey="-1" headerValue="Seleccione Jefatura Provincial" listKey="jefatura_provincial" listValue="descripcion"
					onchange="checkHabilitarPestaniaDocumento();checkImpresoPermiso()" />
			</td>
		</tr>
		<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()">
			<tr>
				<td align="left" nowrap="nowrap">
					<label id ="labelcodigoTasaPagoPresentacion" for="codigoTasaPagoPresentacion">Código tasa Ficha: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:if test="tramiteTraficoDuplicado.tasaFichaTecnica != null">
						<s:select id="tasaFichaTecnica"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="tramiteTraficoDuplicado.tasaFichaTecnica" disabled="%{flagDisabled}"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicadosFT(tramiteTraficoDuplicado.tasaFichaTecnica, tramiteTraficoDuplicado.idContrato)"
							listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa" />
					</s:if>
					<s:else>
						<s:select id="tasaFichaTecnica"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="tramiteTraficoDuplicado.tasaFichaTecnica" disabled="%{flagDisabled}"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicadosFT(tramiteTraficoDuplicado.idContrato)"
							listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa" />
					</s:else>
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left">
				<label for="fechaPagoPresentacionDuplicado">Fecha Presentación: </label>
			</td>
			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="diaPresentacion"
					name="tramiteTraficoDuplicado.fechaPresentacion.dia" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onkeypress="return validarDia(this,event)"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" />
			</td>
			<td>/</td>
			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="mesPresentacion"
					name="tramiteTraficoDuplicado.fechaPresentacion.mes" onblur="this.className='input2';" 
					disabled="%{flagDisabled}" onkeypress="return validarMes(this,event)"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" />
			</td>
			<td>/</td>
			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="anioPresentacion"
					name="tramiteTraficoDuplicado.fechaPresentacion.anio" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onkeypress="return validarAnio(this,event)"
					onfocus="this.className='inputfocus';" size="4" maxlength="4" />
			</td>
			<td align="left" nowrap="nowrap">
				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacion, document.formData.mesPresentacion, document.formData.diaPresentacion);return false;" title="Seleccionar fecha">
					<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
				</a>
			</td>
		</tr>
	</table>

	<script>
		
		function noNecesitaTasa(){
			if (document.getElementById("motivoDuplicado").value==1 || document.getElementById("motivoDuplicado").value==2){
				document.getElementById("codigoTasaPagoPresentacion").disabled=true;
			}else{
				document.getElementById("codigoTasaPagoPresentacion").disabled=false;
			}
		}

		if (document.getElementById("permisoEspecial").value == "false") {
			noNecesitaTasa();
		}
		
	</script>
</div>