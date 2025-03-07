<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">
	<div id="idTasaDPC">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Duplicado Permiso Conducir</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DUPLICADO PERMISO CONDUCIR</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="nombre">N� Expediente</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="duplicadoPermisoConducirDto.numExpediente" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="17" maxlength="17" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="estadoId">Estado:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="estadoId" name="duplicadoPermisoConducirDto.estado"
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getEstadosInterga()"
					headerKey="-1" headerValue="Iniciado" listKey="valorEnum" listValue="nombreEnum"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="refPropia">Referencia Propia:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="refPropia" name="duplicadoPermisoConducirDto.refPropia"
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="35" maxlength="50"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label id="labelTasaDuplPermCond" for="tasaDuplPermCond">Tasa: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="tasaDuplPermCond" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="duplicadoPermisoConducirDto.codigoTasa" 
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicadoPermCond(duplicadoPermisoConducirDto)"
					listKey="codigoTasa" listValue="codigoTasa"	headerKey="" headerValue="Seleccione C�digo de Tasa" />
			</td>
			<!--<td align="left" nowrap="nowrap" >
				<input type="button" class="boton" id="botonCargaTasa" value = "Ver Todas"
					onclick="javascript:verTodasTasas('idContratoDuplPermCond','tasaDuplPermCond');"/>
			</td>-->
		</tr>

		<tr></tr>
		<tr></tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="estadoId">Tipo Duplicado: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idTipoDuplicado" name="duplicadoPermisoConducirDto.tipoDuplicado"
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().getTiposDuplicado()"
					headerKey="" headerValue="Seleccione Tipo Duplicado" listKey="valorEnum" listValue="nombreEnum"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="nombre">Email AUTE:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idEmailAUTE" name="duplicadoPermisoConducirDto.emailAUTE" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="diaPresentacionId">Fecha de Presentaci�n:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<s:textfield readonly="true" name="duplicadoPermisoConducirDto.fechaPresentacion.dia" id="diaPresentacionId"
								size="2" maxlength="2" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)" />
						</td>
						<td>/</td>
						<td>
							<s:textfield readonly="true" name="duplicadoPermisoConducirDto.fechaPresentacion.mes" id="mesPresentacionId"
								size="2" maxlength="2" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)" />
						</td>
						<td>/</td>
						<td>
							<s:textfield readonly="true" name="duplicadoPermisoConducirDto.fechaPresentacion.anio" id="anioPresentacionId"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4"
								onkeypress="return validarNumerosEnteros(event)" />
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="jefaturaId">Jefatura provincial:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idJefaturaPI" name="duplicadoPermisoConducirDto.jefatura"
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getJefaturasJPTEnum()"
					headerKey="" headerValue="Seleccione jefatura provincial" listKey="jefatura" listValue="descripcion"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true" />
			</td>
		</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="anotaciones">Observaciones:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textarea name="duplicadoPermisoConducirDto.observaciones"
						disabled="%{flagDisabled}" id="observaciones" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" rows="5" cols="50" />
				</td>
			</tr>
		</table>
	</div>
</div>