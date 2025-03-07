<%@ taglib prefix="s" uri="/struts-tags" %>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante del segundo cotitular
				<s:set var="identificacion" value="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.numColegiado"/>
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nif"/>','<s:property value="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE DEL SEGUNDO COTITULAR</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

		<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="nifRepresentanteSegundoCotitularTransmitente">NIF / CIF:</label>
       		</td>

       	    <td align="left" nowrap="nowrap" width="24%" colspan="1">
       	    	<table >
					<tr>
						<td align="left" nowrap="nowrap">
			       			<s:if test="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nif != null && !tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nif.equals('')}">
				       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nif" 
				       				id="nifRepresentanteSegundoCotitularTransmitente" 
				       				onblur="this.className='input2';calculaLetraNIF(this)" 
				       				onfocus="this.className='inputfocus';" 
				       				onchange="limpiarFormularioRepresentanteTransmitenteTransmision('RepresentanteSegundoCotitular')"
				       				size="9" maxlength="9"/>
				       		</s:if>
				       		<s:else>
				       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nif" 
				       				id="nifRepresentanteSegundoCotitularTransmitente" 
				       				onblur="this.className='input2';calculaLetraNIF(this)" 
				       				onfocus="this.className='inputfocus';"
				       				size="9" maxlength="9"/>
				       		</s:else>
       					</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteTransmision('Representante Segundo Cotitular Transmisión','Transmitente')"/>
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
								<s:textfield name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.fechaCaducidadNif.dia" 
									id="represCot2DiaCadNif"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" 
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.fechaCaducidadNif.mes" 
									id="represCot2MesCadNif"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" 
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.fechaCaducidadNif.anio" 
									id="represCot2AnioCadNif"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" 
									size="5" maxlength="4"/>
							</td>

							<td>
							<div id="represCot2IdFechaDni" <s:if test="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.indefinido != null &&
									tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.indefinido == true}">
							 		style="display:none"
									</s:if>
									>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.represCot2AnioCadNif, document.formData.represCot2MesCadNif, document.formData.represCot2DiaCadNif);return false;" 
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
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="transtroDocIdentidad">Documento Alternativo:</label>
			</td>
			<td>
		        		<s:checkbox
		        		id="represCot2OtroDocumentoIdentidad"
						disabled="%{flagDisabled}"
						onclick=""
						onkeypress="this.onClick"
						onchange="habilitarDocumentoAlternativo('represCot2OtroDocumentoIdentidad','represCot2TipoDocumentoAlternativo','represCot2DiaCadAlternativo','represCot2MesCadAlternativo','represCot2AnioCadAlternativo','represCot2IdFecha','represCot2Indefinido');"
		        		name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad">
		        		</s:checkbox>
		     </td>
		     <td align="left" nowrap="nowrap" colspan="1">
		        <label for="represCot2Indefinido">Indefinido:</label>	
			</td>
			<td>
		    	<s:checkbox 
		       		id="represCot2Indefinido"
					disabled="%{flagDisabled}"
					onclick="" 
					onkeypress="this.onClick"
					onchange="documentoIndefinido('represCot2Indefinido', 'represCot2OtroDocumentoIdentidad', 'represCot2TipoDocumentoAlternativo', 'represCot2DiaCadNif', 'represCot2MesCadNif', 'represCot2AnioCadNif', 'represCot2DiaCadAlternativo', 'represCot2MesCadAlternativo', 'represCot2AnioCadAlternativo', 'represCot2IdFechaDni', 'represCot2IdFecha');"
		       		name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.indefinido">
		    	</s:checkbox>
		    </td>
		</tr>
			<tr>
       		<td align="left" nowrap="nowrap" colspan="1">
       			<label for="represCot2TipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
       		</td>
       		<td>
       		         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="represCot2TipoDocumentoAlternativo"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.tipoDocumentoAlternativo"
							listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" disabled="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
       		</td>

       			<td align="left" nowrap="nowrap" colspan="1">
						<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
				</td>
				<td>
       				<TABLE WIDTH=100%>
							<tr>
								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.dia" 
										id="represCot2DiaCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>

								<td>/</td>

								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.mes" 
										id="represCot2MesCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>

								<td>/</td>

								<td>
									<s:textfield name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.anio" 
										id="represCot2AnioCadAlternativo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" disabled="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
								</td>

								<td>
								<div id="represCot2IdFecha" <s:if test="%{tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
										!tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.otroDocumentoIdentidad}">
								 style="display:none"
								 </s:if>
								 >
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.represCot2AnioCadAlternativo, document.formData.represCot2MesCadAlternativo, document.formData.represCot2DiaCadAlternativo);return false;" 
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
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.sexo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
       		</td>
    		<td align="left" nowrap="nowrap">
       			<label for="apellido1RepresentanteSegundoCotitularTransmitente">Apellido o Razón Social:</label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.apellido1RazonSocial" 
       				id="apellido1RepresentanteSegundoCotitularTransmitente" 
       				onblur="this.className='input2';"
       				onfocus="this.className='inputfocus';"
       				size="40" maxlength="70"/>
       		</td>
       	</tr>

		<tr>
			<td align="left" nowrap="nowrap">
       			<label for="apellido2RepresentanteSegundoCotitularTransmitente">Segundo Apellido: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.apellido2" 
       				id="apellido2RepresentanteSegundoCotitularTransmitente" 
       				onblur="this.className='input2';"
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="26"/>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<label for="idNombreRepresentanteSegundoCotitularTransmitente">Nombre: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.persona.nombre" 
       				id="idNombreRepresentanteSegundoCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="18"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="conceptoRepresentanteSegundoCotitularTransmitente">Concepto: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
						id="conceptoRepresentanteSegundoCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.conceptoRepre"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Concepto tutela"
						headerKey="-1" headerValue="-"
						onchange="cambioConceptoRepreTransmitente('conceptoRepresentanteSegundoCotitularTransmitente','motivoRepresentanteSegundoCotitularTransmitente')"/>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<label for="motivoRepresentanteSegundoCotitularTransmitente">Motivo: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentanteSegundoCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.idMotivoTutela"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Concepto tutela"
						headerKey="-1" headerValue="-"
						disabled="true"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" colspan="3">
       			<label for="datosAcrediteRepresentanteSegundoCotitularTransmitente">Datos del documento que acredite la facultad para actuar en nombre del segundo cotitular: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.datosDocumento" 
       				id="datosAcrediteRepresentanteSegundoCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="100"/>
       		</td>
       	</tr>
       	<tr>
			<td align="left" nowrap="nowrap" style="width:10%;">
				<label for="diaInicioRepresentanteSegundoCotitularTransmitente">Fecha inicio Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="20">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.fechaInicio.dia" 
					id="diaInicioRepresentanteSegundoCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>
				
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.fechaInicio.mes" 
					id="mesInicioRepresentanteSegundoCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>
				
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.fechaInicio.anio" 
					id="anioInicioRepresentanteSegundoCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="4"
					maxlength="4"
					cssClass="izq input2" />	
				
				<span class="izq">&nbsp;&nbsp;</span>
				
	    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRepresentanteSegundoCotitularTransmitente, document.formData.mesInicioRepresentanteSegundoCotitularTransmitente, document.formData.diaInicioRepresentanteSegundoCotitularTransmitente);return false;" 
	    			title="Seleccionar fecha">
	    			<img class="PopcalTrigger" 
	    				align="middle" 
	    				src="img/ico_calendario.gif" ${displayDisabled} 
	    				width="15"
	    				height="16" 
	    				border="0"
	    				alt="Calendario" />
	    		</a>
	    	</td>

			<td align="left" nowrap="nowrap">
				<label for="diaFinRepresentanteSegundoCotitularTransmitente">Fecha fin Tutela: </label>
			</td>
			
			<td align="left" nowrap="nowrap" width="20">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.fechaFin.dia" 
					id="diaFinRepresentanteSegundoCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>
					
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.fechaFin.mes" 
					id="mesFinRepresentanteSegundoCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>
				
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteSegundoCotitularTransmitenteBean.fechaFin.anio" 
					id="anioFinRepresentanteSegundoCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="4"
					maxlength="4"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;&nbsp;</span>
					
	    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRepresentanteSegundoCotitularTransmitente, document.formData.mesFinRepresentanteSegundoCotitularTransmitente, document.formData.diaFinRepresentanteSegundoCotitularTransmitente);return false;" 
	    			title="Seleccionar fecha">
	    			<img class="PopcalTrigger" 
	    				align="middle" 
	    				src="img/ico_calendario.gif" ${displayDisabled} 
	    				width="15"
	    				height="16" 
	    				border="0"
	    				alt="Calendario" />
	    		</a>
			</td>
		</tr>
	</table>
	
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>