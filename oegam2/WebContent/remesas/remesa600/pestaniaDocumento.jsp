<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 600</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Documento</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labeltipoDoc">Tipo de Documento<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:radio id="tipoDoc" cssStyle="display: inline;" labelposition="left"
					name="remesa.tipoDocumento" 
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getTiposDocumentos(remesa.modelo)"
					listKey="tipoDocumento" listValue="descTipoDocEscr"
					onchange="javascript:deshabilitarNotarioProtocolo()">
				</s:radio>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumProtocolo">Numero de protocolo</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="remesa.numProtocolo" id="idNumProtocolo" size="6" maxlength="6" 
					onblur="this.className='input';" onfocus="this.className='inputfocus';"
					onkeypress="return validarNumeros(event)" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelProtocoloBis">Protocolo Bis</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox name="remesa.protocoloBis" id="idProtocoloBis"
					onblur="this.className='input';" onfocus="this.className='inputfocus';"/>	
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelConcepto">Concepto<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select  name="remesa.concepto.concepto"  id="idConceptoDocumento"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaConceptosPorModeloDto(remesa.modelo)" 
		    		headerKey="" headerValue="Seleccione Concepto" onchange="cargarExpAbreviadaConcepto(this,'idExprAbreviada','tipoModelo');"
		    		onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					listKey="concepto" listValue="descConcepto" ></s:select>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelExpAbreviada">Expresión Abreviada<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idExprAbreviada" size="3" maxlength="3" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" disabled="true" />
			</td>			
		</tr>
	</table>
	<div id="divNotario">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos del Notario</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelCodNotario">Cod. Notario<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="remesa.notario.codigoNotario" id="idCodNotario" size="25" maxlength="100" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';"/>
								<s:hidden name="remesa.notario.codigoNotaria" id="idCodigoNotaria"></s:hidden>
						</td>
						<td>
							<input type="button" class="boton-persona" id="botonNotario" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
								onclick="javascript:buscarNotario();"/>
						</td>
						<td align="left">
							<label id="idMensajeNotario" style="color: red"></label>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelNotarioNombreCompleto">Nombre y Apellidos<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="remesa.notario.nombre" id="idNotarioNombre" size="25" maxlength="100" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';" disabled="true" />
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="remesa.notario.apellidos" id="idNotarioApellidos" size="25" maxlength="100" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';" disabled="true" />
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<div id = "divEmergenteDocumento" style="position: fixed; z-index: 999; height: 100%; width: 100%; top: 0; left:0;
 							background-color: #f4f4f4; filter: alpha(opacity=60); opacity: 0.6; -moz-opacity: 0.8;display:none">
</div>
<script>
	habilitarNotario();
	recargarExpAbreviadaConcepto('idConceptoDocumento','idExprAbreviada');
</script>