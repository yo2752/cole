<%@ taglib prefix="s" uri="/struts-tags" %>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante del primer cotitular
				<s:set var="identificacion" value="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.numColegiado"/>
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nif"/>','<s:property value="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE DEL PRIMER COTITULAR</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="nifRepresentantePrimerCotitularTransmitente">NIF / CIF:</label>
			</td>

			<td align="left" nowrap="nowrap" width="24%" colspan="1">
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<s:if test="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nif != null && !tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nif.equals('')}">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nif"
									id="nifRepresentantePrimerCotitularTransmitente"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';"
									onchange="limpiarFormularioRepresentanteTransmitenteTransmision('RepresentantePrimerCotitular')"
									size="9" maxlength="9"/>
							</s:if>
							<s:else>
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nif"
									id="nifRepresentantePrimerCotitularTransmitente"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';"
									size="9" maxlength="9"/>
							</s:else>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteTransmision('Representante Primer Cotitular Transmisión','Transmitente')"/>
							</s:if>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Fecha Caducidad NIF:<span class="naranja">*</span></label>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.fechaCaducidadNif.dia" 
								id="represCot1DiaCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.fechaCaducidadNif.mes" 
								id="represCot1MesCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.fechaCaducidadNif.anio" 
								id="represCot1AnioCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="5" maxlength="4"/>
						</td>

						<td>
							<div id="represCot1IdFechaDni" <s:if test="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.indefinido != null &&
									tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.indefinido == true}">
									style="display:none"
									</s:if>
									>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.represCot1AnioCadNif, document.formData.represCot1MesCadNif, document.formData.represCot1DiaCadNif);return false;"
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
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="transtroDocIdentidad">Documento Alternativo:</label>
			</td>
			<td>
				<s:checkbox
					id="represCot1OtroDocumentoIdentidad"
					disabled="%{flagDisabled}"
					onclick=""
					onkeypress="this.onClick"
					onchange="habilitarDocumentoAlternativo('represCot1OtroDocumentoIdentidad','represCot1TipoDocumentoAlternativo','represCot1DiaCadAlternativo','represCot1MesCadAlternativo','represCot1AnioCadAlternativo','represCot1IdFecha','represCot1Indefinido');"
					name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad">
				</s:checkbox>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="represCot1Indefinido">Indefinido:</label>
			</td>
			<td>
				<s:checkbox
					id="represCot1Indefinido"
					disabled="%{flagDisabled}"
					onclick=""
					onkeypress="this.onClick"
					onchange="documentoIndefinido('represCot1Indefinido', 'represCot1OtroDocumentoIdentidad', 'represCot1TipoDocumentoAlternativo', 'represCot1DiaCadNif', 'represCot1MesCadNif', 'represCot1AnioCadNif', 'represCot1DiaCadAlternativo', 'represCot1MesCadAlternativo', 'represCot1AnioCadAlternativo', 'represCot1IdFechaDni', 'represCot1IdFecha');"
					name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.indefinido">
				</s:checkbox>
			</td>
		</tr>
			<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="represCot1TipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
			</td>
			<td>
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="represCot1TipoDocumentoAlternativo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.tipoDocumentoAlternativo"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" disabled="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
			</td>

			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.dia" 
								id="represCot1DiaCadAlternativo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.mes" 
								id="represCot1MesCadAlternativo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" 
								size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.fechaCaducidadAlternativo.anio" 
								id="represCot1AnioCadAlternativo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" 
								size="5" maxlength="4" disabled="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}"/>
						</td>

						<td>
							<div id="represCot1IdFecha" <s:if test="%{tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad != null &&
									!tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.otroDocumentoIdentidad}">
							style="display:none"
							</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.represCot1AnioCadAlternativo, document.formData.represCot1MesCadAlternativo, document.formData.represCot1DiaCadAlternativo);return false;" 
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
				</table>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="sexoTransmitente">Sexo:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						id="sexoTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.sexo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="apellido1RepresentantePrimerCotitularTransmitente">Apellido o Razón Social:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.apellido1RazonSocial" 
					id="apellido1RepresentantePrimerCotitularTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="40" maxlength="70"/>
			</td>
       	</tr>

		<tr>
			<td align="left" nowrap="nowrap">
       			<label for="apellido2RepresentantePrimerCotitularTransmitente">Segundo Apellido: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.apellido2" 
       				id="apellido2RepresentantePrimerCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="26"/>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<label for="idNombreRepresentantePrimerCotitularTransmitente">Nombre: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.persona.nombre" 
       				id="idNombreRepresentantePrimerCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';"
       				size="20" maxlength="18"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap">
       			<label for="conceptoRepresentantePrimerCotitularTransmitente">Concepto: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
						id="conceptoRepresentantePrimerCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.conceptoRepre"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Concepto tutela"
						headerKey="-1" headerValue="-"
						onchange="cambioConceptoRepreTransmitente('conceptoRepresentantePrimerCotitularTransmitente','motivoRepresentantePrimerCotitularTransmitente')"/>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<label for="motivoRepresentantePrimerCotitularTransmitente">Motivo: </label>
       		</td>

       		<td align="left" nowrap="nowrap">
       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentantePrimerCotitularTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.idMotivoTutela"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Concepto tutela"
						headerKey="-1" headerValue="-"
						disabled="true"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" colspan="3">
       			<label for="datosAcrediteRepresentantePrimerCotitularTransmitente">Datos del documento que acredite la facultad para actuar en nombre del primer cotitular: </label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.datosDocumento" 
       				id="datosAcrediteRepresentantePrimerCotitularTransmitente" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="20" maxlength="100"/>
       		</td>
       	</tr>
       	<tr>
			<td align="left" nowrap="nowrap" style="width:10%;">
				<label for="diaInicioRepresentantePrimerCotitularTransmitente">Fecha inicio Tutela: </label>
			</td>
			
			<td align="left" nowrap="nowrap" width="20">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.fechaInicio.dia" 
					id="diaInicioRepresentantePrimerCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>
				
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.fechaInicio.mes" 
					id="mesInicioRepresentantePrimerCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>
				
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.fechaInicio.anio" 
					id="anioInicioRepresentantePrimerCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="4"
					maxlength="4"
					cssClass="izq input2" />

				<span class="izq">&nbsp;&nbsp;</span>

				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRepresentantePrimerCotitularTransmitente, document.formData.mesInicioRepresentantePrimerCotitularTransmitente, document.formData.diaInicioRepresentantePrimerCotitularTransmitente);return false;" 
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
				<label for="diaFinRepresentantePrimerCotitularTransmitente">Fecha fin Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="20">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.fechaFin.dia" 
					id="diaFinRepresentantePrimerCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />

				<span class="izq">&nbsp;/&nbsp;</span>

				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.fechaFin.mes" 
					id="mesFinRepresentantePrimerCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />
					
				<span class="izq">&nbsp;/&nbsp;</span>

				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representantePrimerCotitularTransmitenteBean.fechaFin.anio" 
					id="anioFinRepresentantePrimerCotitularTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';" 
					onkeypress="return validarNumerosEnteros(event)"
					size="4"
					maxlength="4"
					cssClass="izq input2" />

				<span class="izq">&nbsp;&nbsp;</span>

	    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRepresentantePrimerCotitularTransmitente, document.formData.mesFinRepresentantePrimerCotitularTransmitente, document.formData.diaFinRepresentantePrimerCotitularTransmitente);return false;" 
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