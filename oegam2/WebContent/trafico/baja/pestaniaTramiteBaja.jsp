<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Baja</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">BAJA</span>
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
					<s:textfield cssClass="campo_disabled" name="origenTramite" readonly="true" value="%{@general.utiles.TipoCreacionEnum@getEnum(tramiteTraficoBaja.idTipoCreacion).getNombre()}"></s:textfield>
				</td>
			</tr>
		</s:if>	
		<tr>
			<td align="left" nowrap="nowrap" width="14%">
				<label for="motivoBaja">Motivos de la baja:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="motivoBaja"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					disabled="%{flagDisabled}"
					name="tramiteTraficoBaja.motivoBaja"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getMotivosBaja(tramiteTraficoBaja)"
					headerKey="-1"
					headerValue="Seleccione Motivo"
					listKey="valorEnum"
					listValue="nombreEnum"
					onchange="cargarListaPaisesBajasTelematicas(this,'%{tramiteTraficoBaja.pais.idPais}');noNecesitaTasa();cargaPestaniaAdquiriente();javascript:mostrarComprobarValidarCheckBTV(this,'%{tramiteTraficoBaja.estado}','%{tramiteTraficoBaja.numExpediente}');"/>
			</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
				<td align="left" nowrap="nowrap" width="14%">
					<label for="idPaisBaja">País destino de la baja:<span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idPaisBaja" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteTraficoBaja.pais.idPais" headerKey=""
						headerValue="Seleccione Pais Baja" listKey="idPais"
						listValue="nombre" onchange="" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().listaPaises(tramiteTraficoBaja)"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<td colspan="4">
				<div id="mensajeMotivo" style="display:none; padding:1em 0;"></div>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="2">
				<label for="datosTarjetaITV">Datos de la tarjeta de ITV:</label>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
					<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.permisoCircu" id="checkBoxPermisoCirculacion" onchange="mensajeCheckBTVPermiso(this.checked);"/>
				</s:if>
				<s:else>
					<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.permisoCircu" id="checkBoxPermisoCirculacion"/>
				</s:else>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="permisoCirculacion">Permiso de circulaci&oacute;n</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
					<s:checkbox id="idTarjeInspecc" disabled="%{flagDisabled}" name="tramiteTraficoBaja.tarjetaInspeccion" onchange="mensajeCheckBTVFichaTecnica(this.checked);"/>
				</s:if>
				<s:else>
					<s:checkbox id="idTarjeInspecc" disabled="%{flagDisabled}" name="tramiteTraficoBaja.tarjetaInspeccion" />
				</s:else>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="tarjetaInspeccionTecnica">Tarjeta de inspecci&oacute;n t&eacute;cnica</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
					<s:checkbox id="idDeclaracJurExt" disabled="%{flagDisabled}" name="tramiteTraficoBaja.declaracionJuradaExtravio" onchange="mensajeCheckBTVDeclaracionJuradaExt(this.checked);" />
				</s:if>
				<s:else>
					<s:checkbox id="idDeclaracJurExt" disabled="%{flagDisabled}" name="tramiteTraficoBaja.declaracionJuradaExtravio"/>
				</s:else>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="declaracionJuradaExtravio">Declaraci&oacute;n jurada extrav&iacute;o</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.bajaImpMunicipal"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="bajaImpuestoMunicipal">Baja del Impuesto Municipal sobre Veh&iacute;culos de Tracci&oacute;n Mec&aacute;nica</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.justificanteDenuncia"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="justificanteDenuncia">Justificante de denuncia por sustracci&oacute;n</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.propiedadDesguace"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="documentoAcreditativoPropiedadVehiculo">Documento acreditativo de la propiedad del veh&iacute;culo.</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.pagoImpMunicipal"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="justificantePagoImpuesto">Justificante del pago del Impuesto Municipal sobre Veh&iacute;culos de Tracci&oacute;n Mec&aacute;nica</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.declaracionResiduo"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="declaracionNOResiduoSolido">Declaraci&oacute;n NO es Residuo S&oacute;lido</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.declaracionNoEntregaCatV"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="declaracionNOCATV">Declaraci&oacute;n jurada No Existe No Circula / Declaraci&oacute;n No entrega CATV</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.cartaDGTVehiculoMasDiezAn"></s:checkbox>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="declaracionCartaDGT">Declaraci&oacute;n Carta DGT Veh&iacute;culo m&aacute;s de 10 años</label>
			</td>
		</tr>

		<tr>
			<td>
				<s:checkbox id="certificadoMedioambiental" disabled="%{flagDisabled}" name="tramiteTraficoBaja.certificadoMedioAmbiental"></s:checkbox>
			</td>
			<!-- id="certificadoMedioambiental" -->
			<td align="left" nowrap="nowrap">
				<label for="certificadoMedioambiental">Certificado Medioambiental</label>
			</td>
		</tr>
	</table>

	<table cellSpacing="1" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="19%">
				<label for="transferenciaId">Transferencia simult&aacute;nea:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTransferenciaSimultanea()"
						name="tramiteTraficoBaja.simultanea"
						listKey="valorEnum" listValue="nombreEnum"
						id="transferenciaSimultaneaId"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="refPropiaResumen">Referencia Propia: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" id="refPropiaResumen"
					name="tramiteTraficoBaja.refPropia"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="50" maxlength="50"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoBaja.imprPermisoCircu"
					id="duplicadoImpresoPermiso" onchange="desbloqueaTasaDuplicado();"></s:checkbox>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="duplicadoImpresoPermiso">Impresi&oacute;n duplicado del Permiso</label>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="tasaDuplicado">Tasa de duplicado: </label>
			</td>
			<td align="left" nowrap="nowrap" colspan="6">
				<s:select id="tasaDuplicado" onblur="this.className='input2';" disabled="%{flagDisabled}"
					onfocus="this.className='inputfocus';" name="tramiteTraficoBaja.tasaDuplicado"
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaDuplicados(tramiteTraficoBaja.tasaDuplicado,tramiteTraficoBaja.idContrato)"
					listKey="codigoTasa" listValue="codigoTasa" headerKey="-1" headerValue="Seleccione Código de Tasa"/>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left">
				<label for="anotaciones">Anotaciones/Observaciones DGT:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textarea disabled="%{flagDisabled}" name="tramiteTraficoBaja.anotaciones"
					id="anotaciones"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					rows="5" cols="50"/>
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
	
</div>

<script>
	desbloqueaTasaDuplicado();
	desbloqueaPaisBaja();
	cargaPestaniaAdquiriente();
</script>