<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="DatosdelContrato" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
	<div class="contenido">		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del contrato</td>
			</tr>
		</table>

		<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
			<tr>			
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap"><br/>
								<label style="text-aling:left;" for="contratoEstado" >Estado</label>
								<s:textfield name="contratoDto.estadoContrato" value="%{@org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato@convertirTexto(contratoDto.estadoContrato)}"
	       							id="contratoEstado" onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssClass="inputview" readonly="true" disabled="true"/>
							</td>
							<td align="left" nowrap="nowrap"><br/>
								<label for="fechaInicio">Fecha Inicio</label>			                        					                      		
								<s:textfield name="contratoDto.fechaInicio" id="fechaInicio" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
		       						cssClass="inputview" readonly="true" disabled="true"/>
		       					<s:hidden id="idFechaInicioDia" name="contratoDto.fechaInicio.dia"></s:hidden>
				       			<s:hidden id="idFechaInicioMes" name="contratoDto.fechaInicio.mes"></s:hidden>
				       			<s:hidden id="idFechaInicioAnio" name="contratoDto.fechaInicio.anio"></s:hidden>
							</td>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
								<td align="left" nowrap="nowrap"><br/>
									<label for="fechaFin">Fecha Fin</label>			                        					                      		
									<s:textfield name="contratoDto.fechaFin" id="fechaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					       				cssClass="inputview" readonly="true" disabled="true"/>
					       			<s:hidden id="idFechaFinDia" name="contratoDto.fechaFin.dia"></s:hidden>
				       				<s:hidden id="idFechaFinMes" name="contratoDto.fechaFin.mes"></s:hidden>
				       				<s:hidden id="idFechaFinAnio" name="contratoDto.fechaFin.anio"></s:hidden>
								<td align="left" nowrap="nowrap"><br/>
									<label for="fechaFin">Id Usuario</label>			                        					                      		
									<s:textfield name="contratoDto.colegiadoDto.usuario.idUsuario" id="idUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					       				cssClass="inputview" readonly="true" disabled="true"/>
					       		<td align="left" nowrap="nowrap"><br/>
									<label for="fechaFin">Id Contrato</label>			                        					                      		
									<s:textfield name="contratoDto.idContrato" id="idContrato" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					       				cssClass="inputview" readonly="true" disabled="true"/>
								</td>
							</s:if>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<label for="cif">CIF<span class="naranja">*</span></label>			
								<s:textfield name="contratoDto.cif" id="cif" onblur="this.className='input2';calculaLetraDni('cif');" 
				       				onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
							</td>
							<td  align="left" nowrap="nowrap">
								<label for="razonSocial">Raz&oacute;n Social<span class="naranja">*</span></label>			
								<s:textfield name="contratoDto.razonSocial" id="razonSocial" onblur="this.className='input2';"
								 	onfocus="this.className='inputfocus';" size="70" maxlength="120"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<label for="provincia">Provincia<span class="naranja">*</span></label>	
								<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" headerKey="-1"
					           		headerValue="Seleccione Provincia" name="contratoDto.idProvincia" 
									listKey="idProvincia" listValue="nombre" id="idProvincia"
									onchange="cargarListaMunicipios(this,'idMunicipio','municipioSeleccion'); cargarListaColegios(this,'idColegio','colegioSeleccionado');
									cargarListaJefaturas(this,'idJefatura','jefaturaSeleccionado'); cargarListaTipoVia(this,'idTipoVia','idTipoViaSeleccionada');
									inicializarTipoVia('idTipoVia','via',viaContrato);" cssStyle="width:170px;"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
								
							</td>
							<td align="left" nowrap="nowrap">
								<label for="municipio">Localidad/Municipio<span class="naranja">*</span></label>	
								<select id="idMunicipio" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									onchange="seleccionarCampo('municipioSeleccion','idMunicipio'); obtenerCodigoPostalPorMunicipio('idProvincia', this, 'codPostal');
									inicializarTipoVia('tipoViaDireccion','nombreViaDireccion',viaContrato);">
										<option value="-1">Seleccione Municipio</option>
								</select>
								<s:hidden id="municipioSeleccion" name="contratoDto.idMunicipio"/>				
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
	   							<label for="idTipoVia">Tipo de vía: <span class="naranja">*</span></label>
		         				<select id="idTipoVia" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									onchange="seleccionarCampo('idTipoViaSeleccionada','idTipoVia'); cargarListaNombresVia('idProvincia', 'municipioSeleccion', this, 'via',viaContrato);">
									<option value="-1">Seleccione Tipo Via</option>
								</select>
			    				<s:hidden id="idTipoViaSeleccionada" name="contratoDto.idTipoVia"/>
	       					</td>
							<td  align="left" nowrap="nowrap">
								<label for="via">Nombre v&iacute;a p&uacute;blica<span class="naranja">*</span></label>				
								<s:textfield name="contratoDto.via" id="via" cssStyle="width:200px;position:relative;"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" autocomplete="off"
									size="40" maxlength="120"/>
							</td>
							<td align="left" nowrap="nowrap">
								<label for="numero">N&uacute;mero<span class="naranja">*</span></label>				
								<s:textfield name="contratoDto.numero" id="numero" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
							</td>
							<td  align="left" nowrap="nowrap">
								<label for="letra">Letra</label>			
								<s:textfield name="contratoDto.letra" id="letra" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
							</td>
							<td align="left" nowrap="nowrap">
								<label for="escalera">Escalera</label>				
								<s:textfield name="contratoDto.escalera" id="escalera" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
							</td>
							<td align="left" nowrap="nowrap">
								<label for="piso">Piso</label>			
								<s:textfield name="contratoDto.piso" id="piso" 
 									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
							</td>
							<td align="left" nowrap="nowrap">
								<label for="puerta">Puerta</label>				
								<s:textfield name="contratoDto.puerta" id="puerta" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>	
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<label for="codPostal">C&oacute;digo Postal<span class="naranja">*</span></label>		
								<s:textfield name="contratoDto.codPostal" id="codPostal" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="5"/>
							</td>
							<td align="left" nowrap="nowrap">
								<label for="telefono">Tel&eacute;fono<span class="naranja">*</span></label>			
								<s:textfield name="contratoDto.telefono" id="telefono" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
							</td>
							<td align="left" nowrap="nowrap">
								<label for="correoElectronico">Correo Electr&oacute;nico<span class="naranja">*</span></label>			
								<s:textfield name="contratoDto.correoElectronico" id="correoElectronico" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>		
							<td align="left" nowrap="nowrap">
								<label for="idColegio">Colegios<span class="naranja">*</span></label>
								<select id="idColegio" onchange="seleccionarCampo('colegioSeleccionado','idColegio');">
									<option value="-1">Seleccione Colegio</option>
								</select>
								<s:hidden id="colegioSeleccionado" name="contratoDto.colegioDto.colegio"/>	
							</td>
							<td align="left" nowrap="nowrap">
								<label for="idJefatura">Jefaturas<span class="naranja">*</span></label>
								<select id="idJefatura" onchange="seleccionarCampo('jefaturaSeleccionado','idJefatura');">
									<option value="-1">Seleccione Jefatura</option>
								</select>
								<s:hidden id="jefaturaSeleccionado" name="contratoDto.jefaturaTraficoDto.jefaturaProvincial"/>	
							</td>
						</tr>	
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" width="150px">
								<label for="labelObservaciones">Observaciones:</label>
							</td>
							<td align="left"  nowrap="nowrap">
								<s:textarea name="contratoDto.observaciones" id="idObservaciones" onblur="this.className='input2';" 
						   				onfocus="this.className='inputfocus';"  rows="5" cols="50"/>
							</td>
						</tr>					
					</table>
				</td>
			</tr>
		</table>	
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos identificación corpme</td>
			</tr>
		</table>

		<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap" width="20%" style="vertical-align: middle;">
								<label for="ncorpmeCliente">Código usuario abonado</span>:</label>
							</td>
							<td>
								<s:textfield name="contratoDto.ncorpme" id="codigoCorpme" size="10" maxlength="10"/>
							</td>
							<td align="right" nowrap="nowrap" style="vertical-align: middle;">
								<label for="ncorpmeClientePass">Usuario / Contraseña</span>:</label>
							</td>
							<td align="right" nowrap="nowrap">
					   			<s:textfield name="contratoDto.usuarioCorpme" id="usuarioCorpme" size="15" maxlength="25"/>
							</td>
							<td>/</td>
							<td>
					  			<s:password name="contratoDto.passwordCorpme" id="passwordCorpme" size="15" maxlength="25" showPassword="true"/>
							</td>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().esUsuarioDelContrato(contratoDto.idContrato)}">
								<td align="right" nowrap="nowrap" width="50%">
									<input type="button" class="boton" name="bGuardarCorpme" id="idBotonGuardarCorpme" value="Guardar Corpme" onClick="return modificacionCorpme('DatosdelContrato');" onKeyPress="this.onClick"/>
								</td>
							</s:if>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="acciones">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td>
						<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return modificacionContrato('DatosdelContrato');" onKeyPress="this.onClick"/>
						&nbsp;	
					</td>
				</s:if>
				<td align="center">
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volverContrato();" onKeyPress="this.onClick" />&nbsp;                     
			     </td>
			</tr>
		</table>	
	</div>
</div>

<script>	
	var viaContrato = new BasicContentAssist(document.getElementById('via'), [], null, true); 
	recargarComboMunicipios('idProvincia','idMunicipio','municipioSeleccion');
	recargarComboTipoVia('idProvincia','idTipoVia','idTipoViaSeleccionada');
	recargarComboColegios('idProvincia','idColegio','colegioSeleccionado');
	recargarComboJefaturas('idProvincia','idJefatura','jefaturaSeleccionado');
	recargarNombreVias('idProvincia', 'municipioSeleccion', 'tipoViaSeleccionada','via',viaContrato);
</script>