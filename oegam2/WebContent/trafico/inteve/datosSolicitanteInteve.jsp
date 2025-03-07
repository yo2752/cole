<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div id="divSolicitanteTRI">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">Solicitante</span>
				</td>
			</tr>
		</table>
		<s:hidden name="tramiteInteve.solicitante.persona.nif"/>
		<s:hidden name="tramiteInteve.solicitante.persona.numColegiado"/>
		<s:hidden name="tramiteInteve.solicitante.tipoInterviniente"/>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNifSolicitanteTRI">NIF/CIF<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align:left;">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="idNifSolicitanteTRI" name="tramiteInteve.solicitante.nifInterviniente" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esGuardable(tramiteInteve)}">
									<input type="button" id="idBotonBuscarNifSolTRI" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
												onclick="javascript:buscarSolicitante()"/>
								</s:if>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelPrApeRzsSolicitanteTRI">Primer Ape./Raz&oacute;n Social<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPrApeRzsSolicitanteTRI" name="tramiteInteve.solicitante.persona.apellido1RazonSocial" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="40" maxlength="100" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelApe2SolicitanteTRI">Apellido 2:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idApellido2SolicitanteTRI" name="tramiteInteve.solicitante.persona.apellido2" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="40" maxlength="100" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelNombreSolicitanteTRI">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNombreSolicitanteTRI" name="tramiteInteve.solicitante.persona.nombre" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="40" maxlength="100" />
				</td>
			</tr>
		</table>
	</div>
</div>