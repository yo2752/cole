<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div id="divRepresentanteTRI">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">Representante</span>
				</td>
			</tr>
		</table>
		<s:hidden name="tramiteInteve.representante.persona.nif"/>
		<s:hidden name="tramiteInteve.representante.persona.numColegiado"/>
		<s:hidden name="tramiteInteve.representante.tipoInterviniente"/>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNifRepresentanteTRI">NIF/CIF<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align:left;">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="idNifRepresentanteTRI" name="tramiteInteve.representante.nifInterviniente" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esGuardable(tramiteInteve)}">
									<input type="button" id="idBotonBuscarNifSolTRI" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
											onclick="javascript:buscarRepresentante()"/>
								</s:if>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelPrApeRzsRepresentanteTRI">Primer Ape./Raz&oacute;n Social<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idPrApeRzsRepresentanteTRI" name="tramiteInteve.representante.persona.apellido1RazonSocial" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="40" maxlength="100" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelApe2RepresentanteTRI">Apellido 2:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idApellido2RepresentanteTRI" name="tramiteInteve.representante.persona.apellido2" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="40" maxlength="100" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelNombreRepresentanteTRI">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNombreRepresentanteTRI" name="tramiteInteve.representante.persona.nombre" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="40" maxlength="100" />
				</td>
			</tr>
		</table>
	</div>
</div>