<%@ taglib prefix="s" uri="/struts-tags" %>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td colspan="3">Datos del transmitente
				<s:set var="identificacion" value="tramiteTraficoTransmisionBean.transmitenteBean.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoTransmisionBean.transmitenteBean.numColegiado"/>
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoTransmisionBean.transmitenteBean.persona.nif"/>','<s:property value="tramiteTraficoTransmisionBean.transmitenteBean.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">TRANSMITENTE</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
       		
       		<td align="left" nowrap="nowrap">
       			<label for="tipoPersonaTransmitente">Tipo de persona: </label>
       		</td>

         	<td align="left" nowrap="nowrap" colspan="3">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						value="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.tipoPersona}"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Tipo Persona"
						headerKey="-1" headerValue="-"
						disabled="true"/>
       		</td>
       	</tr>

		<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="idNifTransmitente">NIF / CIF: <span class="naranja">*</span></label>
       		</td>
       	     <td align="left" nowrap="nowrap"  width="24%" >
       	    	<table >
					<tr>
						<td align="left" nowrap="nowrap" >
			       			<s:if test="%{tramiteTraficoTransmisionBean.transmitenteBean.nifInterviniente != null && !tramiteTraficoTransmisionBean.transmitenteBean.nifInterviniente.equals('')}">
				       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.nif" 
				       				id="idNifTransmitente" 
				       				onblur="this.className='input2';calculaLetraNIF(this)" 
				       				onfocus="this.className='inputfocus';" 
				       				onchange="limpiarFormularioTransmitenteTransmision()"
				       				size="9" maxlength="9"/>
				       		</s:if>
				       		<s:else>
				       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.nif" 
				       				id="idNifTransmitente" 
				       				onblur="this.className='input2';calculaLetraNIF(this)" 
				       				onfocus="this.className='inputfocus';" 
				       				size="9" maxlength="9"/>
				       		</s:else>
       					</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteTransmision('Transmitente','Transmitente')"/>
							</s:if>
						</td>
					</tr>
				</table>
       	    </td>

       		<td align="left" nowrap="nowrap" colspan="1">
							<label for="Nif">Fecha Caducidad NIF:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
       				<TABLE style="width: 100%">
							<tr>
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaCaducidadNif.dia" 
										id="transDiaCadNif"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaCaducidadNif.mes" 
										id="transMesCadNif"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaCaducidadNif.anio" 
										id="transAnioCadNif"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4"/>
								</td>
	
								<td>
								<div id="transIdFechaDni" <s:if test="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.indefinido != null &&
										tramiteTraficoTransmisionBean.transmitenteBean.persona.indefinido == true}">
								 		style="display:none"
										</s:if>
										>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.transAnioCadNif, document.formData.transMesCadNif, document.formData.transDiaCadNif);return false;" 
				    					title="Seleccionar fecha">
					    				<img class="PopcalTrigger" 
					    					align="left" 
					    					src="img/ico_calendario.gif" 
					    					width="15" height="16" 
					    					border="0" alt="Calendario"/>
				    				</a>
				    			</div>
								</td>
	
								<td width="2%"></td>
							</tr>
						</TABLE>
		</td>
       	</tr>
       	
       	<tr>
       	<s:hidden name="tramiteTraficoTransmisionBean.transmitenteBean.persona.tipoPersona"></s:hidden>
       		<td align="left" nowrap="nowrap" colspan="1">
		        		<label for="transOtroDocIdentidad">Documento Alternativo:</label>
		    </td>
		    <td>		        			
		        		<s:checkbox 
		        		id="transOtroDocumentoIdentidad"		
						disabled="%{flagDisabled}"		
						onclick="" 
						onkeypress="this.onClick" 
						onchange="habilitarDocumentoAlternativo('transOtroDocumentoIdentidad','transTipoDocumentoAlternativo','transDiaCadAlternativo','transMesCadAlternativo','transAnioCadAlternativo','transIdFecha','transIndefinido');"
		        		name="tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad">
		        		</s:checkbox>
		     </td>
		    <td align="left" nowrap="nowrap" colspan="1">
		        <label for="transIndefinido">Indefinido:</label>	
			</td>
			<td>	        			
		    	<s:checkbox 
		       		id="transIndefinido"		
					disabled="%{flagDisabled}"		
					onclick="" 
					onkeypress="this.onClick" 
					onchange="documentoIndefinido('transIndefinido', 'transOtroDocumentoIdentidad', 'transTipoDocumentoAlternativo', 'transDiaCadNif', 'transMesCadNif', 'transAnioCadNif', 'transDiaCadAlternativo', 'transMesCadAlternativo', 'transAnioCadAlternativo', 'transIdFechaDni', 'transIdFecha');"
		       		name="tramiteTraficoTransmisionBean.transmitenteBean.persona.indefinido">
		    	</s:checkbox>
		    </td>
       	</tr>
       	<tr>
       
       		<td align="left" nowrap="nowrap" colspan="1">
       					<label for="transTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
       		</td>
       		<td>
       		         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="transTipoDocumentoAlternativo"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="tramiteTraficoTransmisionBean.transmitenteBean.persona.tipoDocumentoAlternativo"
							listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" disabled="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad}"/>
       		</td>

       			<td align="left" nowrap="nowrap" colspan="1">
						<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
				</td>
				<td>
       				<TABLE WIDTH=100%>
							<tr>
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaCaducidadAlternativo.dia" 
										id="transDiaCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaCaducidadAlternativo.mes" 
										id="transMesCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaCaducidadAlternativo.anio" 
										id="transAnioCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" disabled="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>
	
								<td>
								<div id="transIdFecha" <s:if test="%{tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.transmitenteBean.persona.otroDocumentoIdentidad}">
								 style="display:none"
								 </s:if>
								 >
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.transAnioCadAlternativo, document.formData.transMesCadAlternativo, document.formData.transDiaCadAlternativo);return false;" 
				    					title="Seleccionar fecha">
				    				
					    				<img class="PopcalTrigger" 
					    					align="left" 
					    					src="img/ico_calendario.gif" 
					    					width="15" height="16" 
					    					border="0" alt="Calendario" />
				    				</a>
				    			</div>
								</td>
	
								<td width="2%"></td>
							</tr>
						</TABLE>
       					</td>
       			</tr>
       	
       	<tr>	        	
       		<td align="left" nowrap="nowrap">
       			<label for="sexoTransmitente">Sexo: </label>
       		</td>

         	<td align="left" nowrap="nowrap">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						id="sexoTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="tramiteTraficoTransmisionBean.transmitenteBean.persona.sexo"
						listValue="nombreEnum"
						disabled="%{flagDisabled}"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
       		</td>
       			
    	   	<td align="left" nowrap="nowrap">
       			<label for="idApellidoRazonSocialTransmitente">Apellido o Razón Social: <span class="naranja">*</span></label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.apellido1RazonSocial" 
       				id="idApellidoRazonSocialTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="40" maxlength="70"/>
       		</td>
       	</tr>
		
		<tr>	
			<td align="left" nowrap="nowrap">
       			<label for="idApellido2Transmitente">Segundo Apellido: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.apellido2" 
       				id="idApellido2Transmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="26"/>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<label for="idNombreTransmitente">Nombre: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.nombre" 
       				id="idNombreTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="18"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="idAnagramaTransmitente">Anagrama</label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.anagrama" 
       				id="idAnagramaTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="5" maxlength="5"
       				readonly="true"/>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<label for="idCodigoMandatoTransmitente">Código de Mandato</label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<span class="formspan"><s:property value="tramiteTraficoTransmisionBean.transmitenteBean.persona.codigoMandato" /></span>
       		</td>
       		
       	</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="10%">
				<label for="diaNacTransmitente">Fecha de Nacimiento: </label>
			</td>
		
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaNacimientoBean.dia" 
					id="diaNacTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2"/>
			</td>
			
			<td width="2">/</td>
			
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaNacimientoBean.mes" 
					id="mesNacTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2"/>
			</td>
			
			<td width="2">/</td>
			
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.fechaNacimientoBean.anio" 
					id="anioNacTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="4" maxlength="4"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="3%">
	    		<a href="javascript:;" 
	    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacTransmitente, document.formData.mesNacTransmitente, document.formData.diaNacTransmitente);return false;" 
	    				title="Seleccionar fecha">
	    			<img class="PopcalTrigger" 
	    				align="middle" 
	    				src="img/ico_calendario.gif" ${displayDisabled} 
	    				width="15" height="16" 
	    				border="0" alt="Calendario"/>
	    		</a>
			</td>
			
			<td align="left" nowrap="nowrap" width="130">
				<label for="idTelefonoTransmitente">Teléfono: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.telefonos" 
					id="idTelefonoTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="9" maxlength="9"/>
			</td>
		</tr>
	</table>
	
	<s:if test="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.idDireccion != null " >
		<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="100%">
					<table  style="align:left;">
						<tr>
							<td  align="left" style="vertical-align:middle" width="10em">
								<label>Seleccionar Dirección</label>
							</td>
							<td align="left" nowrap="nowrap" width="5em">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:consultaDireccion('<s:property value="tramiteTraficoTransmisionBean.transmitenteBean.persona.nif"/>','<s:property value="tramiteTraficoTransmisionBean.transmitenteBean.numColegiado"/>','<s:property value="tramiteTraficoTransmisionBean.tramiteTraficoBean.numExpediente"/>','TransmitenteTransmision');"/>
								</s:if>
							</td>
							<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
								<td>
									<label>ID direccion</label>
									<span><s:textfield disabled="true" readonly="true" id="idDireccionAdminTraTran" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.idDireccion"></s:textfield> </span>
								</td>
							</s:if>
						</tr>
					</table>
				</td>
			</tr>				
		</table>
	</s:if>
	
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
				
       		
       		<td align="left" nowrap="nowrap">
				<label for="idProvinciaTransmitente">Provincia: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTransmitente()"
					headerKey="-1"
	           		headerValue="Seleccione Provincia"
	           		disabled="%{flagDisabled}"
					name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.municipio.provincia.idProvincia" 
					listKey="idProvincia" listValue="nombre"
					id="idProvinciaTransmitente"
					onchange="cargarListaMunicipios(this,'idMunicipioTransmitente','municipioSeleccionadoTransmitente');
					cargarListaTipoVia(this,'tipoViaTransmitente','tipoViaSeleccionadaTransmitente');
					inicializarTipoVia('tipoViaTransmitente','viaTransmitente',viaTransmitenteTransmision);
					borrarComboPueblo('idPuebloTransmitente');"
					cssStyle="width:170px;"
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"/>	
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioTransmitente">Municipio: <span class="naranja">*</span></label>	
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">			
				<select id="idMunicipioTransmitente" 
					${stringDisabled}
					onchange="cargarListaPueblos('idProvinciaTransmitente', this, 'idPuebloTransmitente', 'puebloSeleccionadoTransmitente');
					seleccionarCampo('municipioSeleccionadoTransmitente','idMunicipioTransmitente');
					obtenerCodigoPostalPorMunicipio('idProvinciaTransmitente', this, 'codPostalTransmitente');
					inicializarTipoVia('tipoViaTransmitente','viaTransmitente',viaTransmitenteTransmision);" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" >
					<option value="-1">Seleccione Municipio</option>
				</select>						  
		    	<s:hidden id="municipioSeleccionadoTransmitente" 
		    		name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.municipio.idMunicipio"/>
			</td>
		</tr>
		
		<tr>
				
			<td align="left" nowrap="nowrap">
				<label for="codPostalTransmitente">Código Postal: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
	        	<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.codPostal" 
	        		id="codPostalTransmitente" 
	        		maxlength="5" size="5" 
	            	onblur="this.className='input';"
	               	onkeypress="return validarNumerosEnteros(event)"
	            	onfocus="this.className='inputfocus';"/>
			</td>								
			<td align="left" nowrap="nowrap">
				<label for="idPuebloTransmitente">Pueblo: </label>			
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">				
				<select id="idPuebloTransmitente" onchange="seleccionarCampo('puebloSeleccionadoTransmitente','idPuebloTransmitente');"
						onblur="this.className='input2';"
						${stringDisabled}
						onfocus="this.className='inputfocus';"
						style="width:200px;" >
					<option value="-1">Seleccione Pueblo</option>
				</select>						  
			   	<s:hidden id="puebloSeleccionadoTransmitente" 
			   		name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.pueblo"/>
			</td>				
		</tr>
		
		<tr>			
      
      		<td align="left" nowrap="nowrap">
       			<label for="tipoViaTransmitente">Tipo de vía: <span class="naranja">*</span></label>
       		</td>

			<td align="left" nowrap="nowrap">
         	
	         	<select id="tipoViaTransmitente" 
						onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';"
						${stringDisabled}
						onchange="seleccionarCampo('tipoViaSeleccionadaTransmitente','tipoViaTransmitente');
						cargarListaNombresVia('idProvinciaTransmitente', 'municipioSeleccionadoTransmitente', this, 'viaTransmitente',viaTransmitenteTransmision);" >
					<option value="-1">Seleccione Tipo Via</option>
				</select>
		    	<s:hidden id="tipoViaSeleccionadaTransmitente" 
		    		name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.tipoVia.idTipoVia"/>
         	
       		</td>

			<td align="left" nowrap="nowrap">
				<label for="viaTransmitente">Nombre v&iacute;a: <span class="naranja">*</span></label>
			</td>				        		
       		
			<td align="left" nowrap="nowrap" colspan="2">
	       			<s:textfield disabled="%{flagDisabled}" id="viaTransmitente" 
	       				onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.nombreVia"
		       			cssStyle="width:200px;position:relative;" size="40" maxlength="50" autocomplete="off"/>
						
       		</td>        
		</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap">
				<label for="numeroTransmitente">N&uacute;mero: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.numero" 
					id="numeroTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarSN2(event,this)"
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="letraTransmitente">Letra: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.letra" 
					id="letraTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="escaleraTransmitente">Escalera: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.escalera" 
					id="escaleraTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="pisoTransmitente">Piso: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.planta" 
					id="pisoTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaTransmitente">Puerta: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.puerta" 
					id="puertaTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="bloqueTransmitente">Bloque: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.bloque" 
					id="bloqueTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="kmTransmitente">Km: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.puntoKilometrico" 
					id="kmTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="3" maxlength="3"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="hmTransmitente">Hm: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.transmitenteBean.persona.direccion.hm" 
					id="hmTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>
		</tr>
		<tr>			
			
			
			<td align="left" nowrap="nowrap">
				<label for="autonomo">Autónomo </label>
			</td>	
				<td align="left" nowrap="nowrap" width="5%">
				<s:checkbox
					id="autonomoIdTransmitente"
					disabled="%{flagDisabled}"
					name="tramiteTraficoTransmisionBean.transmitenteBean.autonomo"				
					onclick="" 
					onkeypress="this.onClick" 
					onchange="habilitarAutonomo('autonomoIdTransmitente','codigoIAETransmitente');"
					>
				</s:checkbox>
			</td>
	
	
			<td align="left" nowrap="nowrap">
				<label for="epigrafe">IAE</label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:select name="tramiteTraficoTransmisionBean.transmitenteBean.codigoIAE" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCodigosIAE()"
					headerKey="-1"
	           		headerValue="Seleccione Código" 
					listKey="Codigo_IAE" 
					listValue="descripcion"
					id="codigoIAETransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"
					style="width:250px"
					/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="epigrafe">N&uacute;mero de titulares</label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:select name="tramiteTraficoTransmisionBean.numTitulares" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboNumeroTitulares()"
					id="numTitulares"
					listKey="indice" 
					listValue="descripcion"
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"
					/>
			</td>
			
		</tr>
	</table>
	
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>