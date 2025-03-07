<%@ taglib prefix="s" uri="/struts-tags" %>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del segundo cotitular</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">SEGUNDO COTITULAR TRANSMITENTE</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="tipoPersonaSegundoCotitularTransmitente">Tipo de persona: </label>
       		</td>

         	<td align="left" nowrap="nowrap" colspan="3">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaSegundoCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						value="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.tipoPersona}"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Tipo Persona"
						headerKey="-1" headerValue="-"
						disabled="true"/>
       		</td>
		</tr>
		<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="idNifSegundoCotitularTransmitente">NIF / CIF: </label>
       		</td>
       	    <td align="left" nowrap="nowrap" width="24%">
       	    	<table >
					<tr>
						<td align="left" nowrap="nowrap">
			       			<s:if test="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.nifInterviniente != null && !tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.nifInterviniente.equals('')}">
				       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.nif" 
				       				id="idNifSegundoCotitularTransmitente" 
				       				onblur="this.className='input2';calculaLetraNIF(this)"
				       				onfocus="this.className='inputfocus';" 
				       				onchange="limpiarFormularioSegundoCotitularTransmitenteTransmision()"
				       				size="9" maxlength="9"/>
				       		</s:if>
				       		<s:else>
				       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.nif" 
				       				id="idNifSegundoCotitularTransmitente"
				       				onblur="this.className='input2';calculaLetraNIF(this)"
				       				onfocus="this.className='inputfocus';"
				       				size="9" maxlength="9"/>
				       		</s:else>
       					</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteTransmision('Segundo Cotitular Transmision','Transmitente')"/>
							</s:if>
						</td>
					</tr>
				</table>
       	    </td>

       		<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Fecha Caducidad NIF:<span class="naranja">*</span></label>
			</td>
			<td>
      				<TABLE WIDTH=100%>
						<tr>
							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaCaducidadNif.dia" 
									id="segundoCotDiaCadNif"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaCaducidadNif.mes" 
									id="segundoCotMesCadNif"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaCaducidadNif.anio" 
									id="segundoCotAnioCadNif"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="5" maxlength="4"/>
							</td>

							<td>
							<div id="segundoCotIdFechaDni" <s:if test="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.indefinido != null &&
									tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.indefinido == true}">
							 		style="display:none"
									</s:if>
									>
			    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.segundoCotAnioCadNif, document.formData.segundoCotMesCadNif, document.formData.segundoCotDiaCadNif);return false;" 
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
       	<s:hidden name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.tipoPersona"></s:hidden>
       		<td align="left" nowrap="nowrap" colspan="1">
		        <label for="segundoCotitularOtroDocIdentidad">Documento Alternativo:</label>		 
		    </td>
		    <td colspan="1">
		        		<s:checkbox
		        		id="segundoCotitularOtroDocumentoIdentidad"
						disabled="%{flagDisabled}"
						onclick=""
						onkeypress="this.onClick"
						onchange="habilitarDocumentoAlternativo('segundoCotitularOtroDocumentoIdentidad','segundoCotitularTipoDocumentoAlternativo','segundoCotDiaCadAlternativo','segundoCotMesCadAlternativo','segundoCotAnioCadAlternativo','segundoCotitularIdFecha','segundoCotIndefinido');"
		        		name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad">
		        		</s:checkbox>
		     </td>
		     <td align="left" nowrap="nowrap" colspan="1">
		        <label for="segundoCotIndefinido">Indefinido:</label>
			</td>
			<td>
		    	<s:checkbox
		       		id="segundoCotIndefinido"
					disabled="%{flagDisabled}"
					onclick="" 
					onkeypress="this.onClick" 
					onchange="documentoIndefinido('segundoCotIndefinido', 'segundoCotitularOtroDocumentoIdentidad', 'segundoCotitularTipoDocumentoAlternativo', 'segundoCotDiaCadNif', 'segundoCotMesCadNif', 'segundoCotAnioCadNif', 'segundoCotDiaCadAlternativo', 'segundoCotMesCadAlternativo', 'segundoCotAnioCadAlternativo', 'segundoCotIdFechaDni', 'segundoCotitularIdFecha');"
		       		name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.indefinido">
		    	</s:checkbox>
		    </td>
       	</tr>
       	
       	 	<tr>
       
       		<td align="left" nowrap="nowrap" colspan="1">
       					<label for="segundoCotitularTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
       		</td>
       		<td>
       		         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="segundoCotitularTipoDocumentoAlternativo"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.tipoDocumentoAlternativo"
							listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" disabled="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
       		</td>

       			<td align="left" nowrap="nowrap" colspan="1">
						<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
			</td>
			<td>
       				<TABLE WIDTH=100%>
							<tr>
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.dia" 
										id="segundoCotDiaCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.mes" 
										id="segundoCotMesCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.anio" 
										id="segundoCotAnioCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" disabled="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>
	
								<td>
								<div id="segundoCotitularIdFecha" <s:if test="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}">
								 style="display:none"
								 </s:if>
								 >
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.segundoCotAnioCadAlternativo, document.formData.segundoCotMesCadAlternativo, document.formData.segundoCotDiaCadAlternativo);return false;" 
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
       			<label for="sexoSegundoCotitularTransmitente">Sexo: </label>
       		</td>

         	<td align="left" nowrap="nowrap">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						id="sexoSegundoCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.sexo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
       		</td>	
       			
    	   	<td align="left" nowrap="nowrap">
       			<label for="idApellidoRazonSocialSegundoCotitularTransmitente">Apellido o Razón Social: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.apellido1RazonSocial" 
       				id="idApellidoRazonSocialSegundoCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="40" maxlength="70"/>
       		</td>
       	</tr>
		
		<tr>	
			<td align="left" nowrap="nowrap">
       			<label for="idApellido2SegundoCotitularTransmitente">Segundo Apellido: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.apellido2" 
       				id="idApellido2SegundoCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="26"/>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<label for="idNombreSegundoCotitularTransmitente">Nombre: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.nombre" 
       				id="idNombreSegundoCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="18"/>
       		</td>
       	</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="10%">
				<label for="diaNacSegundoCotitularTransmitente">Fecha de Nacimiento: </label>
			</td>
		
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaNacimientoBean.dia" 
					id="diaNacSegundoCotitularTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2"/>
			</td>
			
			<td width="2">/</td>
			
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaNacimientoBean.mes" 
					id="mesNacSegundoCotitularTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2"/>
			</td>
			
			<td width="2">/</td>
			
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.fechaNacimientoBean.anio" 
					id="anioNacSegundoCotitularTransmitente"
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="4" maxlength="4"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="3%">
	    		<a href="javascript:;" 
	    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacSegundoCotitularTransmitente, document.formData.mesNacSegundoCotitularTransmitente, document.formData.diaNacSegundoCotitularTransmitente);return false;" 
	    				title="Seleccionar fecha">
	    			<img class="PopcalTrigger" 
	    				align="middle" 
	    				src="img/ico_calendario.gif" ${displayDisabled} 
	    				width="15" height="16" 
	    				border="0" alt="Calendario"/>
	    		</a>
			</td>
			
			<td align="left" nowrap="nowrap" width="130">
				<label for="idTelefonoSegundoCotitularTransmitente">Teléfono: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.telefonos" 
					id="idTelefonoSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="9" maxlength="9"/>
			</td>
		</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	
		<tr>
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.idDireccion != null">
				<td>
					<label>ID direccion</label>
					<span><s:textfield cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.idDireccion}"></s:textfield> </span>
				</td>
			</s:if>
		</tr>
	
		<tr>
       		
       		<td align="left" nowrap="nowrap">
				<label for="idProvinciaSegundoCotitularTransmitente">Provincia: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTransmitente()"
					headerKey="-1"
	           		headerValue="Seleccione Provincia"
	           		disabled="%{flagDisabled}"
					name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.municipio.provincia.idProvincia" 
					listKey="idProvincia" listValue="nombre"
					id="idProvinciaSegundoCotitularTransmitente"
					onchange="cargarListaMunicipios(this,'idMunicipioSegundoCotitularTransmitente','municipioSeleccionadoSegundoCotitularTransmitente');
					cargarListaTipoVia(this,'tipoViaSegundoCotitularTransmitente','tipoViaSeleccionadaSegundoCotitularTransmitente');
					inicializarTipoVia('tipoViaSegundoCotitularTransmitente','viaSegundoCotitularTransmitente',viaSegundoCotitularTransmitenteTransmision);
					borrarComboPueblo('idPuebloSegundoCotitularTransmitente');"
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';"/>	
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioSegundoCotitularTransmitente">Municipio: </label>	
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">			
				<select id="idMunicipioSegundoCotitularTransmitente" 
					${stringDisabled}
					onchange="cargarListaPueblos('idProvinciaSegundoCotitularTransmitente', this, 'idPuebloSegundoCotitularTransmitente', 'puebloSeleccionadoSegundoCotitularTransmitente');
					seleccionarCampo('municipioSeleccionadoSegundoCotitularTransmitente','idMunicipioSegundoCotitularTransmitente');
					obtenerCodigoPostalPorMunicipio('idProvinciaSegundoCotitularTransmitente', this, 'codPostalSegundoCotitularTransmitente');
					inicializarTipoVia('tipoViaSegundoCotitularTransmitente','viaSegundoCotitularTransmitente',viaSegundoCotitularTransmitenteTransmision);" 
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" >
					<option value="-1">Seleccione Municipio</option>
				</select>						  
		    	<s:hidden id="municipioSeleccionadoSegundoCotitularTransmitente" 
		    		name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.municipio.idMunicipio"/>
			</td>
		</tr>
		
		<tr>	
					
			<td align="left" nowrap="nowrap">
				<label for="codPostalSegundoCotitularTransmitente">Código Postal: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
	        	<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.codPostal" 
	        		id="codPostalSegundoCotitularTransmitente" 
	        		maxlength="5" size="5" 
	            	onblur="this.className='input';"
	               	onkeypress="return validarNumerosEnteros(event)"
	            	onfocus="this.className='inputfocus';"/>
			</td>
										
			<td align="left" nowrap="nowrap">
				<label for="idPuebloSegundoCotitularTransmitente">Pueblo: </label>			
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">				
				<select id="idPuebloSegundoCotitularTransmitente" onchange="seleccionarCampo('puebloSeleccionadoSegundoCotitularTransmitente','idPuebloSegundoCotitularTransmitente');"
					onblur="this.className='input2';" 					
					${stringDisabled}
       				onfocus="this.className='inputfocus';" >
					<option value="-1">Seleccione Pueblo</option>
				</select>						  
			   	<s:hidden id="puebloSeleccionadoSegundoCotitularTransmitente" 
			   		name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.pueblo"/>
			</td>				
		</tr>
		
		<tr>			
      
      		<td align="left" nowrap="nowrap">
       			<label for="tipoViaSegundoCotitularTransmitente">Tipo de vía: </label>
       		</td>

			<td align="left" nowrap="nowrap">
         	
	         <select id="tipoViaSegundoCotitularTransmitente"
	         			${stringDisabled}
						onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('tipoViaSeleccionadaSegundoCotitularTransmitente','tipoViaSegundoCotitularTransmitente');
						cargarListaNombresVia('idProvinciaSegundoCotitularTransmitente', 'municipioSeleccionadoSegundoCotitularTransmitente', this, 'viaSegundoCotitularTransmitente',viaSegundoCotitularTransmitenteTransmision);" >
					<option value="-1">Seleccione Tipo Via</option>
				</select>
		    	<s:hidden id="tipoViaSeleccionadaSegundoCotitularTransmitente"
		    		name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.tipoVia.idTipoVia"/>
         	
       		</td>

       		<td align="left" nowrap="nowrap">
				<label for="viaSegundoCotitularTransmitente">Nombre v&iacute;a: <span class="naranja">*</span></label>
			</td>
       		
			<td align="left" nowrap="nowrap" colspan="2">
	       			<s:textfield disabled="%{flagDisabled}" id="viaSegundoCotitularTransmitente" 
		       			onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						cssStyle="width:200px;position:relative;"
						maxlength="50"
		       			name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.nombreVia" autocomplete="off"/>
						
       		</td>        
		</tr>
	</table>
		
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap">
				<label for="numeroSegundoCotitularTransmitente">N&uacute;mero:</label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.numero" 
					id="numeroSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarSN2(event,this)"
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="letraSegundoCotitularTransmitente">Letra: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.letra" 
					id="letraSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="escaleraSegundoCotitularTransmitente">Escalera: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.escalera" 
					id="escaleraSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="pisoSegundoCotitularTransmitente">Piso: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.planta" 
					id="pisoSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaSegundoCotitularTransmitente">Puerta: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.puerta" 
					id="puertaSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="bloqueSegundoCotitularTransmitente">Bloque: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.bloque" 
					id="bloqueSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="kmSegundoCotitularTransmitente">Km: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.puntoKilometrico" 
					id="kmSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="3" maxlength="3"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="hmSegundoCotitularTransmitente">Hm: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.segundoCotitularTransmitenteBean.persona.direccion.hm" 
					id="hmSegundoCotitularTransmitente" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="5" maxlength="5"/>
			</td>
		</tr>
	</table>