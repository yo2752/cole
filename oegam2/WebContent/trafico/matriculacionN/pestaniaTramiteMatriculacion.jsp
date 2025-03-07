<%@ taglib prefix="s" uri="/struts-tags" %>
 
<s:hidden id="idNumColegiado" name="tramiteTrafMatrDto.numColegiado"/> 

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Matriculación</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">MATRICULACIÓN</span>
				<s:set var="numExpediente" value="tramiteTrafMatrDto.numExpediente"/>
				<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && %{(#numExpediente!=null && #numExpediente!='')}">
					<img title="Ver evolución JPT" onclick="abrirEvolucionJPT('<s:property value="tramiteTrafMatrDto.numExpediente"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</s:if>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="referenciaPropia">Referencia Propia</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.refPropia" id="referenciaPropia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxlength="50"/>
			</td>
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
				<td align="left" nowrap="nowrap" width="5%">
					<label>Origen Trámite:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield cssClass="campo_disabled" name="origenTramite" readonly="true" value="%{@general.utiles.TipoCreacionEnum@getEnum(tramiteTrafMatrDto.idTipoCreacion).getNombre()}"></s:textfield>
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left">
				<label for="observacionesId">Anotaciones:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="6">
				<s:textarea name="tramiteTrafMatrDto.anotaciones"
					id="observacionesId" disabled="%{flagDisabled}" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" rows="5" cols="50" maxlength="500"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="rentingId">Renting:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTrafMatrDto.renting" id="rentingId" onclick="cambioRentingMatriculacion()"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="carshingId">Carsharing:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTrafMatrDto.carsharing" id="carshingId"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="nombre">Nº Expediente</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield value="%{tramiteTrafMatrDto.numExpediente}" id="idExpediente" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="17" maxlength="17" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="estadoId">Estado trámite:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="estadoId" name="tramiteTrafMatrDto.estado"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosTramiteTrafico()"
					headerKey="-1" headerValue="Iniciado" listKey="valorEnum" listValue="nombreEnum"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idEntFact">Entrega Factura:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTrafMatrDto.entregaFactMatriculacion" id="idEntFact"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoTasaMatriculacionId">Tipo Tasa:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTasaMatriculacion()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey="-1"
					headerValue="-" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.tasa.tipoTasa"
					listKey="valorEnum" listValue="nombreEnum" id="tipoTasaMatriculacionId"
					onchange="habilitarCodigoTasa('tipoTasaMatriculacionId','codigoTasaMatriculacionId');cargarCodigosTasaMatr(this,'codigoTasaMatriculacionId')"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="codigoTasaMatriculacionId">Código Tasa:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="codigoTasaMatriculacionId" onblur="this.className='input2';" disabled="%{flagDisabled}"
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaMatriculacion(tramiteTrafMatrDto)"
					onfocus="this.className='inputfocus';" name="tramiteTrafMatrDto.tasa.codigoTasa"
					listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
			</td>
			<!--<td align="left" nowrap="nowrap">
				<input type="button" class="boton" id="botonCargaTasa" value = "Ver Todas" align="left"
					onclick="javascript:verTodasTasas('idContratoTramite','tipoTasaMatriculacionId','codigoTasaMatriculacionId');"/>
			</td>-->
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="2">
				<label for="diaAltaIniModif">Fecha de Última Modificación:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="4">
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="tramiteTrafMatrDto.fechaUltModif.dia"
								id="diaAltaIniModif" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								onkeypress="return validarDia(event)" size="2" maxlength="2" disabled="true"/>
						</td>
						<td >/</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="tramiteTrafMatrDto.fechaUltModif.mes"
								id="mesAltaIniModif" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								onkeypress="return validarMes(event)" size="2" maxlength="2" disabled="true"/>
						</td>
						<td >/</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="tramiteTrafMatrDto.fechaUltModif.anio"
								id="anioAltaIniModif" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								onkeypress="return validarAnio(event)" size="5" maxlength="4" disabled="true"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<script>
	//JMC : Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
	if (document.getElementById("permisoEspecial").value == "false") {
		habilitarCodigoTasa('tipoTasaMatriculacionId','codigoTasaMatriculacionId');
	}
</script>