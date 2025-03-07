<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Trámite Inteve</td>
			<s:if test="%{tramiteInteve !=null && tramiteInteve.numExpediente != null}">
				<td align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
					onclick="consultaEvolucionTramiteInteve(<s:property value="%{tramiteInteve.numExpediente}"/>);" title="Ver evolución"/>
				</td>
			</s:if>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Inteve</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExpedienteTRI">Num. Expediente:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumExpedienteTRI" value="%{tramiteInteve.numExpediente}"  onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="20" disabled="true"/>
			</td>
			<s:if test="%{tramiteInteve.estado != null && !tramiteInteve.estado.isEmpty()}">
				<td align="left" nowrap="nowrap">
					<label for="labelEstadoTRI">Estado:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getEstadosTramiteInteve()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado"
							name="tramiteInteve.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoTRI" disabled="true"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRefPropia">Ref. Propia:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idRefPropiaTRI" name="tramiteInteve.refPropia"  onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="20" maxlength="20" />
			</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap">
					<label for="labelContratoTRI">Contrato:</label>
				</td>
				<td align="left">
						<s:select id="idContratoTRI"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato" onchange="cargarTasas();"
							onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
							listValue="descripcion" cssStyle="width:220px"
							name="tramiteInteve.contrato.idContrato"></s:select>
				</td>
			</s:if>
			<s:else>
				<s:hidden name="tramiteInteve.contrato.idContrato" id="idContratoTRIHidden"/>
			</s:else>
		</tr>
	</table>
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Solicitante</td>
		</tr>
	</table>
	<jsp:include page="datosSolicitanteInteve.jsp" flush="false"></jsp:include>
	<jsp:include page="datosRepresentanteSolicitanteInteve.jsp" flush="false"></jsp:include>
	&nbsp;
	<div class="acciones center">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esGuardable(tramiteInteve)}">
			<input type="button" class="boton" name="bLimpiarSolicitanteTR" id="idBLimpiarSolicitanteTR" 
				value="Limpiar Solicitante" onClick="javascript:limpiarSolicitanteTRI();" onKeyPress="this.onClick"/>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esGuardable(tramiteInteve)}">
			<input type="button" class="boton" name="bLimpiarRepresentanteTR" id="idBLimpiarRepresentanteTR"
				value="Limpiar Representante" onClick="javascript:limpiarRepresentanteTRI();" onKeyPress="this.onClick"/>
		</s:if>
	</div>
</div>