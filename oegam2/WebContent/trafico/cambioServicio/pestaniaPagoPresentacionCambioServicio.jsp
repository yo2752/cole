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
				<label id ="labelcodigoTasaPagoPresentacion" for="codigoTasaPagoPresentacion">Código de Tasa: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="6">
				<s:if test="tramiteTraficoCambServ.tasa != null && tramiteTraficoCambServ.tasa.codigoTasa != null">
					<s:select id="codigoTasaPagoPresentacion"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoCambServ.tasa.codigoTasa" disabled="%{flagDisabled}"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaCambServicio(tramiteTraficoCambServ.tasa.codigoTasa, tramiteTraficoCambServ.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
				</s:if>
				<s:else>
					<s:select id="codigoTasaPagoPresentacion"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoCambServ.tasa.codigoTasa" disabled="%{flagDisabled}"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCodigosTasaCambServicio(tramiteTraficoCambServ.idContrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
				</s:else>
			</td>

			<td width="10"></td>

			<td align="left">
				<label for="jefaturaProvincialPagoPresentacion">Jefatura Provincial: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select id="jefaturaProvincialPagoPresentacion"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoCambServ.jefaturaTraficoDto.jefaturaProvincial"
					disabled="%{flagDisabled}" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getJefaturasTraficoTodas()"
					headerKey="-1" headerValue="Seleccione Jefatura Provincial" listKey="jefatura_provincial" listValue="descripcion"/>
			</td>
		</tr>

		<tr>
			<td align="left">
				<label for="fechaPagoPresentacionDuplicado">Fecha Presentación:</label>
			</td>
			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="diaPresentacion"
					name="tramiteTraficoCambServ.fechaPresentacion.dia" disabled="%{flagDisabled}"
					onblur="this.className='input2';" onkeypress="return validarDia(this,event)"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" />
			</td>
			<td>/</td>
			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="mesPresentacion"
					name="tramiteTraficoCambServ.fechaPresentacion.mes" onblur="this.className='input2';"
					disabled="%{flagDisabled}" onkeypress="return validarMes(this,event)"
					onfocus="this.className='inputfocus';" size="2" maxlength="2" />
			</td>
			<td>/</td>
			<td align="left" nowrap="nowrap" width="5">
				<s:textfield id="anioPresentacion"
					name="tramiteTraficoCambServ.fechaPresentacion.anio" disabled="%{flagDisabled}"
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
</div>