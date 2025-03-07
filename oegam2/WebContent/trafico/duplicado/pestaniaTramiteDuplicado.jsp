<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Duplicado</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DUPLICADO</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
			<tr>
				<td>
					<label>Origen Tr&aacute;mite:</label>
				</td>
				<td>
					<s:textfield cssClass="campo_disabled" name="origenTramite" readonly="true" value="%{@general.utiles.TipoCreacionEnum@getEnum(tramiteTraficoDuplicado.idTipoCreacion).getNombre()}"></s:textfield>
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left" nowrap="nowrap" width="14%">
				<label for="motivoDuplicado">Motivos del duplicado:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="motivoDuplicado" onblur="this.className='input2';" disabled="%{flagDisabled}"
					onfocus="this.className='inputfocus';" name="tramiteTraficoDuplicado.motivoDuplicado"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getMotivosDuplicado()"
					headerKey="-1" headerValue="Seleccione Motivo" listKey="valorEnum" listValue="nombreEnum"
					onchange="cargaPestaniasDuplicCambDomicConduc();checkImpresoPermiso();checkHabilitarPestaniaDocumento();"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="refPropiaResumen">Referencia Propia:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="refPropiaResumen" name="tramiteTraficoDuplicado.refPropia" 
					disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					size="50" maxlength="50"/>
			</td>
		</tr>
		<tr id="idTipoDocumento">
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()">
				<td align="left" nowrap="nowrap">
					<label for="tipoDocumentoId">Documento a solicitar a DGT:</label><span class="naranja">*</span>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoDocumentoDuplicado()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey="-1"
						headerValue="-" disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.tipoDocumento"
						listKey="valorEnum" listValue="nombreEnum" id="tipoDocumentoId"
						onchange="tasaTipoDocumento()"/>
				</td>
			</s:if>
		</tr>

		<%-- <s:if test="!@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()"> --%>
			<tr>
				<td>
					<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.imprPermisoCirculacion"
						id="checkBoxPermisoCirculacion"></s:checkbox>
				</td>
				<td align="left" nowrap="nowrap">
					<label id="checkBoxPermisoCirculacionLabel" for="checkBoxPermisoCirculacion">Impresión Permiso de Circulación</label>
				</td>
			</tr>
		<%-- </s:if> --%>
		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.importacion"
					id="importacion" onchange="tasaImportacionHabilitar();checkHabilitarPestaniaDocumento()"></s:checkbox>
			</td>
			<td align="left" nowrap="nowrap">
				<label id="importacionLabel" for="importacion">Vehículo Importación.<sup>(1)</sup></label>
			</td>
			<td>
				<label id="labelTasaImportacion" for="tasaImportacion">Tasa Vehículo Importación: </label>
			</td>
			<td>
				<s:select id="tasaImportacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="tramiteTraficoDuplicado.tasaImportacion" 
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaBajas(tramiteTraficoDuplicado.tasaImportacion,tramiteTraficoDuplicado.idContrato)"
					listKey="codigoTasa" listValue="codigoTasa"	headerKey="-1" headerValue="Seleccione Código de Tasa" />
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left">
				<label for="anotaciones">Anotaciones/Observaciones DGT:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textarea name="tramiteTraficoDuplicado.anotaciones"
					disabled="%{flagDisabled}" id="anotaciones" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" rows="5" cols="50"/>
			</td>
		</tr>
		<tr id="importacionLabeltext">
			<td align="left">
				<label><sup>(1)</sup>Cambio de domicilio de vehículos procedentes de Canarias, Ceuta y Melilla</label>
			</td>
		</tr>
	</table>

	<script>
		function tasaImportacionHabilitar(){
			if(document.getElementById("importacion").checked){
				document.getElementById("tasaImportacion").disabled=false;
			}else{
				document.getElementById("tasaImportacion").disabled=true;
			}
		}
		tasaImportacionHabilitar();
		checkImpresoPermiso();
	</script>
</div>