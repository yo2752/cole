<%@ taglib prefix="s" uri="/struts-tags" %>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td colspan="3">Datos del representante del transmitente
				<s:set var="identificacion" value="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nif"/>
				<s:set var="colegiado" value="tramiteTraficoTransmisionBean.representanteTransmitenteBean.numColegiado"/>
				<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nif"/>','<s:property value="tramiteTraficoTransmisionBean.representanteTransmitenteBean.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
				</s:if>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE DEL TRANSMITENTE</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="nifRepresentanteTransmitente">NIF / CIF:</label>
			</td>

			<td align="left" nowrap="nowrap" width="24%" colspan="1">
				<table >
					<tr>
						<td align="left" nowrap="nowrap" >
							<s:if test="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nif != null && !tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nif.equals('')}">
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nif"
									id="nifRepresentanteTransmitente"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';"
									onchange="limpiarFormularioRepresentanteTransmitenteTransmision('Representante')"
									size="9" maxlength="9"/>
							</s:if>
							<s:else>
								<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nif"
									id="nifRepresentanteTransmitente"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';"
									size="9" maxlength="9"/>
							</s:else>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;"
									onclick="javascript:buscarIntervinienteTransmision('Representante Transmitente','Transmitente')"/>
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
							<s:textfield name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.fechaCaducidadNif.dia" 
								id="represDiaCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.fechaCaducidadNif.mes" 
								id="represMesCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="2" maxlength="2"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.fechaCaducidadNif.anio" 
								id="represAnioCadNif"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								size="5" maxlength="4"/>
						</td>

						<td>
							<div id="represIdFechaDni" <s:if test="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.indefinido != null &&
									tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.indefinido == true}">
									style="display:none"
									</s:if>
									>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.represAnioCadNif, document.formData.represMesCadNif, document.formData.represDiaCadNif);return false;" 
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
					id="represOtroDocumentoIdentidad"
					disabled="%{flagDisabled}"
					onclick=""
					onkeypress="this.onClick"
					onchange="habilitarDocumentoAlternativo('represOtroDocumentoIdentidad','represTipoDocumentoAlternativo','represDiaCadAlternativo','represMesCadAlternativo','represAnioCadAlternativo','represIdFecha','represIndefinido');"
					name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad">
				</s:checkbox>
			</td>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="represIndefinido">Indefinido:</label>
			</td>
			<td>
				<s:checkbox
			 		id="represIndefinido"
					disabled="%{flagDisabled}"
					onclick=""
					onkeypress="this.onClick"
					onchange="documentoIndefinido('represIndefinido', 'represOtroDocumentoIdentidad', 'represTipoDocumentoAlternativo', 'represDiaCadNif', 'represMesCadNif', 'represAnioCadNif', 'represDiaCadAlternativo', 'represMesCadAlternativo', 'represAnioCadAlternativo', 'represIdFechaDni', 'represIdFecha');"
					name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.indefinido">
				</s:checkbox>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="1">
				<label for="represTipoDocumentoAlternativo">Tipo de Doc Alternativo: </label>
			</td>
			<td>
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoDocumentoAlternativo()" id="represTipoDocumentoAlternativo"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.tipoDocumentoAlternativo"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Documento Alternativo" headerKey="-1" headerValue="-" disabled="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad != null &&
							!tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad}"/>
			</td>

			<td align="left" nowrap="nowrap" colspan="1">
				<label for="Nif">Caducidad Doc Alternativo:<span class="naranja">*</span></label>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.fechaCaducidadAlternativo.dia" 
								id="represDiaCadAlternativo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" 
								size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad}"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.fechaCaducidadAlternativo.mes" 
								id="represMesCadAlternativo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" 
								size="2" maxlength="2" disabled="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad}"/>
						</td>

						<td>/</td>

						<td>
							<s:textfield name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.fechaCaducidadAlternativo.anio" 
								id="represAnioCadAlternativo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" 
								size="5" maxlength="4" disabled="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad != null &&
								!tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad}"/>
						</td>

						<td>
							<div id="represIdFecha" <s:if test="%{tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad != null &&
									!tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.otroDocumentoIdentidad}">
							style="display:none"
							</s:if>
							>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.represAnioCadAlternativo, document.formData.represMesCadAlternativo, document.formData.represDiaCadAlternativo);return false;" 
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
				<label for="sexoTransmitente">Sexo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						id="sexoTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.sexo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="apellido1RepresentanteTransmitente">Apellido o Razón Social:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.apellido1RazonSocial"
					id="apellido1RepresentanteTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="40" maxlength="70"/>
			</td>
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">
				<label for="apellido2RepresentanteTransmitente">Segundo Apellido: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.apellido2"
					id="apellido2RepresentanteTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="26"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="idNombreRepresentanteTransmitente">Nombre: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.persona.nombre"
					id="idNombreRepresentanteTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="18"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="conceptoRepresentanteTransmitente">Concepto: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
						id="conceptoRepresentanteTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						disabled="%{flagDisabled}"
						name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.conceptoRepre"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Concepto tutela"
						headerKey="-1" headerValue="-"
						onchange="cambioConceptoRepreTransmitente('conceptoRepresentanteTransmitente','motivoRepresentanteTransmitente')"/>
			</td>

			<td align="left" nowrap="nowrap">
				<label for="motivoRepresentanteTransmitente">Motivo: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
						id="motivoRepresentanteTransmitente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.idMotivoTutela"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Concepto tutela"
						headerKey="-1" headerValue="-"
						disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" colspan="3">
				<label for="datosAcrediteRepresentanteTransmitente">Datos del documento que acredite la facultad para actuar en nombre del transmitente:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.datosDocumento"
					id="datosAcrediteRepresentanteTransmitente"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					size="20" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="width:10%;">
				<label for="diaInicioRepresentanteTransmitente">Fecha inicio Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="20">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.fechaInicio.dia"
					id="diaInicioRepresentanteTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />

				<span class="izq">&nbsp;/&nbsp;</span>

				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.fechaInicio.mes"
					id="mesInicioRepresentanteTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />

				<span class="izq">&nbsp;/&nbsp;</span>

				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.fechaInicio.anio"
					id="anioInicioRepresentanteTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="4"
					maxlength="4"
					cssClass="izq input2" />

				<span class="izq">&nbsp;&nbsp;</span>

				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRepresentanteTransmitente, document.formData.mesInicioRepresentanteTransmitente, document.formData.diaInicioRepresentanteTransmitente);return false;"
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
				<label for="diaFinRepresentanteTransmitente">Fecha fin Tutela: </label>
			</td>

			<td align="left" nowrap="nowrap" width="20">
				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.fechaFin.dia"
					id="diaFinRepresentanteTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />

				<span class="izq">&nbsp;/&nbsp;</span>

				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.fechaFin.mes"
					id="mesFinRepresentanteTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="2"
					maxlength="2"
					cssClass="izq input2" />

				<span class="izq">&nbsp;/&nbsp;</span>

				<s:textfield disabled="%{flagDisabled}" name="tramiteTraficoTransmisionBean.representanteTransmitenteBean.fechaFin.anio"
					id="anioFinRepresentanteTransmitente"
					onblur="this.className='izq input2';"
					onfocus="this.className='izq inputfocus';"
					onkeypress="return validarNumerosEnteros(event)"
					size="4"
					maxlength="4"
					cssClass="izq input2" />

				<span class="izq">&nbsp;&nbsp;</span>

				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRepresentanteTransmitente, document.formData.mesFinRepresentanteTransmitente, document.formData.diaFinRepresentanteTransmitente);return false;"
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