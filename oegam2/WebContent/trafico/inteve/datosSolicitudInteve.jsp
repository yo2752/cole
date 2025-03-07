<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div id="divAltaSolicitudInteve">
		<s:hidden name="tramiteInteve.solicitudInteve.estado"/>
		<s:hidden name="tramiteInteve.solicitudInteve.idTramiteInteve" id="idTramiteInteveHidden"/>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">Alta/Modificación Solicitud de Inteve</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelEstadoInteveTRI">Estado Solicitud:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getEstadosTramiteSolictudInteve()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado"
							name="tramiteInteve.solicitudInteve.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoInteveTRI" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoInmueble">Tipo Informe<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getListaTiposInformes()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Tipo Informe"
						name="tramiteInteve.solicitudInteve.tipoInforme" listKey="valorEnum" listValue="nombreEnum" id="idTipoInformeTRI"
						onchange="mostrarAceptacion()"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="tasaInteveTRI">Tasa<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idTasaSolicitudInteveTRI" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="tramiteInteve.solicitudInteve.codigoTasa"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getCodigosTasa(tramiteInteve.solicitudInteve, tramiteInteve.contrato)"
						listKey="codigoTasa" listValue="codigoTasa" headerKey="" headerValue="Seleccione Código de Tasa"/>
					
				</td>
				<!--<td align="left" nowrap="nowrap">
					<input type="button" class="botonpeque" id="botonCargaTasa" value = "Ver Todas"
						onclick="javascript:verTodasTasas('idContratoTRI','idTasaSolicitudInteveTRI');"/>
				</td>-->
			</tr>

			<tr></tr>
			<tr></tr>

			<tr>
				<td align="left" nowrap="nowrap" width="11%">
					<label for="labelMatriculaInteveTRI">Matr&iacute;cula:</label>
				</td>
					<td align="left" nowrap="nowrap">
					<s:textfield 
							name="tramiteInteve.solicitudInteve.matricula" id="idMatriculaInteveTRI"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							onkeypress="return validarMatricula(event)" style="text-transform : uppercase"
							onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"
							size="15" maxlength="12" />
				</td>
				<td align="left" nowrap="nowrap" width="11%">
					<label for="labelBastidorInteveTRI">Bastidor:</label>
				</td>
					<td align="left" nowrap="nowrap">
					<s:textfield 
							name="tramiteInteve.solicitudInteve.bastidor" id="idBastidorInteveTRI"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							style="text-transform : uppercase" size="23" maxlength="21" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="11%">
						<label for="labelNiveInteveTRI">NIVE:</label>
					</td>
					<td align="left" nowrap="nowrap">
					<s:textfield 
							name="tramiteInteve.solicitudInteve.nive" id="idNiveInteveTRI"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							style="text-transform : uppercase" size="34" maxlength="32" />
				</td>
			</tr>
			<tr id="trAceptacion" style="display:none">
				<td align="left" nowrap="nowrap" colspan="4">
					<table>
						<tr>
							<td align="left">
								<s:checkbox name="tramiteInteve.solicitudInteve.aceptacion" id="checkAceptacion" />
							</td>
							<td colspan="3" style="vertical-align: middle;">El Gestor
								Administrativo Colegiado manifiesta bajo su responsabilidad que
								los datos introducidos son correctos.</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRespuestaDgtTRI">Respuesta DGT:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textarea name="tramiteInteve.solicitudInteve.respuestaDgt"
						disabled="disabled" id="idRespuestaDgtTRI" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" rows="5" cols="50"/>
				</td>
			</tr>
		</table>
	</div>
</div>