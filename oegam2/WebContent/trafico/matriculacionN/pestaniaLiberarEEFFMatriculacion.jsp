<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!--  No está claro que la referencia al javascript vaya aquí. Dependiendo de la funcionalidad debe ir aquí o en altasMatriculacion.jsp -->
<script src="js/trafico/eeff/eeff.js" type="text/javascript"></script>

<div class="contentTabs" id="LiberarEEFF" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<s:hidden name="tramiteTrafMatrDto.liberacionEEFF.numExpediente"/>
	<s:hidden name="tramiteTrafMatrDto.liberacionEEFF.numColegiado"/>
	<div class="contenido">
		<div class="subtitulo">
			<table>
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>LIBERACIÓN DE LA EITV</td>
				</tr>
			</table>
		</div>
		<div class="nav">
			<table style="width:100%">
				<tr>
					<td class="tabular">
						<span class="titulo">EXENTO DE LIBERAR: </span>
		 			</td>
				</tr>
			</table>
		</div>
		<div id="dfExento" aligN="left">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:15%;">
						<label for="labelExentoLiberarEEFF" >A este vehículo no hay que realizar la Liberación de EEFF: </label>
					</td>
					<td align="left">
						<s:if test="#liberacionRealizada">
							<s:set var="valorOnClik" value="'return false;'" />
						</s:if>
						<s:else>
							<s:set var="valorOnClik" value="'ocultarCamposEEFFLiberacion()'" />
						</s:else>
						<s:checkbox name="tramiteTrafMatrDto.liberacionEEFF.exento" id="exentoLiberarEEFF"
							fieldValue="true" onclick="%{valorOnClik}"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="nav">
			<table style="width:100%">
				<tr>
					<td class="tabular">
						<span class="titulo">ESTADO DE LA LIBERACIÓN</span>
					</td>
				</tr>
			</table>
		</div>
		<div  id="estadoLiberacion" aligN="left">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:15%;">
						<label for="labelRealizadaLiberarEEFF" >Realizada: </label>
					</td>
					<td align="left">
						<s:checkbox name="tramiteTrafMatrDto.liberacionEEFF.realizado" id="realizadaLiberarEEFF"
							onclick="return false"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMensajeLiberarEEFF">Mensaje: </label>
					</td>
					<td align="left">
						<s:property value="tramiteTrafMatrDto.liberacionEEFF.respuesta"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de realización: </label>
					</td>
					<td>
						<table style="width:20%">
							<tr>
								<td align="left">
									<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.fechaRealizacion.dia" id="diaRealizacionLiberarEEFF"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="true" style="background:#EEEEEE"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.fechaRealizacion.mes" id="mesRealizacionLiberarEEFF"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="true" style="background:#EEEEEE"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.fechaRealizacion.anio" id="anioRealizacionLiberarEEFF"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" readonly="true" style="background:#EEEEEE"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>	
		<div class="nav" id="tituloDatosLiberacionEEFF">
			<table style="width:100%">
				<tr>
					<td class="tabular">
						<span class="titulo">DATOS PARA LIBERAR</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="datosLiberacionEEFF" aligN="left">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:15%;">
						<label for="labelTarjetaNiveLiberarEEFF">NIVE: </label>
					</td>
					<td align="left">
						<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.tarjetaNive"  id="tarjetaNiveLiberarEEFF" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="35" maxlength="32" readonly="true" style="background:#EEEEEE"/>
					</td>
					<td align="left">
						<label for="labelTarjetaBastidorLiberarEEFF">Bastidor: </label>
					</td>
					<td align="left">
						<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.tarjetaBastidor" id="tarjetaBastidorLiberarEEFF"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="32" readonly="true" style="background:#EEEEEE"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelmarcaLiberarEEFF">FIR Marca: </label>
						<img id="info_Fir_Marca" src="img/botonDameInfo.gif"
							onmouseover="dameInfoSinAjax('mostrar', this.id, 'firMarca')" 
							onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
					</td>
					<td align="left">
						<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.firMarca" id="marcaLiberarEEFF"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="32" readonly="#liberacionRealizada"/>
					</td>
					<td align="left">
						<label for="labelfircifLiberarEEFF">FIR CIF: </label>
						<img id="info_Fir_Cif" src="img/botonDameInfo.gif"
							onmouseover="dameInfoSinAjax('mostrar', this.id, 'firCif')" 
							onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
					</td>
					<td align="left">
						<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.firCif" id="fircifLiberarEEFF"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="10" readonly="#liberacionRealizada"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelmarcaLiberarEEFF"> CIF Concesionario Representado:</label>
					</td>
					<td align="left">
						<s:textfield disabled="%{flagDisabled}"  name="tramiteTrafMatrDto.liberacionEEFF.nifRepresentado" id="cifRepresentado"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="10" readonly="#liberacionRealizada"/>
					</td>
					<td align="left">
						<label for="labelnumFacturaLiberarEEFF">Nº de Factura: </label>
					</td>
					<td align="left">
						<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.numFactura" id="numFacturaLiberarEEFF"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="35" readonly="#liberacionRealizada"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaFacturaLiberarEEFF">Fecha Factura: </label>
					</td>
					<td>
						<table style="width:20%">
							<tr>
								<td align="left">
									<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.fechaFactura.dia"  id="diaFechaFacturaLiberarEEFF"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="#liberacionRealizada"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.fechaFactura.mes" id="mesFechaFacturaLiberarEEFF"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="#liberacionRealizada"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.fechaFactura.anio" id="anioFechaFacturaLiberarEEFF"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="#liberacionRealizada"/>
								</td>
								<s:if test="%{!flagDisabled && !#liberacionRealizada}">
									<td align="left">
										<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaFacturaLiberarEEFF, document.formData.mesFechaFacturaLiberarEEFF, document.formData.diaFechaFacturaLiberarEEFF);return false;" 
											title="Seleccionar fecha">
											<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} 
												width="15" height="16"border="0" alt="Calendario"/>
										</a>
									</td>
								</s:if>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelImporteLiberarEEFF">Importe Factura: </label>
					</td>
					<td align="left">
						<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.liberacionEEFF.importe" id="importeLiberarEEFF"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosDecimales(event)" size="10" maxlength="18" readonly="#liberacionRealizada"/>
					</td>
				</tr>
			</table>
		</div>
		<s:if test="tramiteTrafMatrDto.liberacionEEFF.realizado">
			<div class="nav">
				<table style="width:100%">
					<tr>
						<td class="tabular">
							<span class="titulo">DATOS DE REALIZACIÓN</span>
						</td>
					</tr>
				</table>
			</div>
			<div  id="datosLiberacionRealizadaEEFF" aligN="left">
				<table class="tablaformbasica">
					<tr>
						<td align="left" nowrap="nowrap" style="width:15%;">
							<label for="labelNifLiberarEEFF">NIF / CIF: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.persona.nif" id="nifLiberarEEFF"
								size="10" maxlength="9" readonly="true" style="background:#EEEEEE"/>
						</td>
						<td align="left">
							<label for="labelApellido1RazonSocialLiberarEEFF">Primer Apellido / Razón Social: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.persona.apellido1RazonSocial"
								id="apellido1RazonSocialLiberarEEFF" size="40" maxlength="32" readonly="true" style="background:#EEEEEE"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelNombreLiberarEEFF">Nombre: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.persona.nombre"
								id="nombreLiberarEEFF" size="30" maxlength="32" readonly="true" style="background:#EEEEEE"/>
						</td>
						<td align="left">
							<label for="labelApellido2LiberarEEFF">Segundo Apellido: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.persona.apellido2"
								id="apellido2LiberarEEFF" size="40" maxlength="32" readonly="true" style="background:#EEEEEE"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelTipoViaDireccionLiberarEEFF">Tipo vía: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.tipoViaDescripcion"
								id="tipoViaDireccionLiberarEEFF" size="30" maxlength="100" readonly="true" style="background:#EEEEEE"/>
						</td>
						<td align="left">
							<label for="labelNombreViaDireccionLiberarEEFF">Nombre vía: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.nombreVia" 
								id="nombreViaDireccionLiberarEEFF" size="40" maxlength="100" readonly="true" style="background:#EEEEEE"/>
						</td>
					</tr>
					<tr>
						<td>
							<table style="width:20%">
								<tr>
									<td align="left">
										<label for="labelNumeroDireccionLiberarEEFF">Número: </label>
										<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.numero" 
											id="numeroDireccionLiberarEEFF" size="5" maxlength="4" readonly="true" style="background:#EEEEEE"/>
									</td>
									<td align="left">
										<label for="labelBloqueDireccionLiberarEEFF">Bloque: </label>
										<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.bloque" 
											id="bloqueDireccionLiberarEEFF" size="5" maxlength="5" readonly="true" style="background:#EEEEEE"/>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="width:20%">
								<tr>
									<td align="left">
										<label for="labelEscaleraDireccionLiberarEEFF">Escalera: </label>
										<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.escalera"
											id="escaleraDireccionLiberarEEFF" size="5" maxlength="100" readonly="true" style="background:#EEEEEE"/>
									</td>
									<td align="left">
										<label for="labelPisoDireccionLiberarEEFF">Piso: </label>
										<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.planta"
											id="pisoDireccionLiberarEEFF" size="5" maxlength="100" readonly="true" style="background:#EEEEEE"/>
									</td>
									<td align="left">
										<label for="labelPuertaDireccionLiberarEEFF">Puerta: </label>
										<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.puerta" 
											id="puertaDireccionLiberarEEFF" size="5" maxlength="100" readonly="true" style="background:#EEEEEE"/>
									</td>
								</tr>
							</table>
						</td>
						<td align="left">
							<label for="labelCodPostalLiberarEEFF">Código Postal: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.codPostal"
								id="codPostalLiberarEEFF" maxlength="5" size="5" readonly="true" style="background:#EEEEEE"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelMunicipioLiberarEEFF">Municipio: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.nombreMunicipio"
								id="municipioLiberarEEFF" size="30" maxlength="100" readonly="true" style="background:#EEEEEE"/>
						</td>
						<td align="left">
							<label for="labelProvinciaLiberarEEFF">Provincia: </label>
						</td>
						<td align="left">
							<s:textfield name="tramiteTrafMatrDto.liberacionEEFF.titular.direccion.nombreProvincia"
								id="provinciaLiberarEEFF" size="40" maxlength="100" readonly="true" style="background:#EEEEEE"/>
						</td>
					</tr>
				</table>
			</div>
		</s:if>
		<s:if test="%{tramiteTrafMatrDto.listaConsultaEEFF != null && tramiteTrafMatrDto.listaConsultaEEFF.getFullListSize() > 0}">
			<div class="nav" id="datosDeLasConsultasEEFF">
				<table style="width:100%">
					<tr>
						<td class="tabular">
							<span class="titulo">DATOS DE LAS CONSULTAS EEFF ASOCIADAS</span>
						</td>
					</tr>
				</table>
			</div>
			<div  id="consultasEEFFAsociadas" aligN="left">
				<s:if test="%{tramiteTrafMatrDto.listaConsultaEEFF != null && tramiteTrafMatrDto.listaConsultaEEFF.getFullListSize() > 0}">
					<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
						<tr>
							<td>
								<display:table name="tramiteTrafMatrDto.listaConsultaEEFF" class="tablacoin"
											uid="tablaConsultasEEFFMatr" 
											id="tablaConsultasEEFFMatr" summary="" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaEEFFMatriculacion"
											excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">

									<display:column title="Num.Expedeiente" property="numExpediente" sortable="false" paramId="numExpediente"
										headerClass="sortable" defaultorder="descending" style="width:4%"/>

									<display:column property="fechaRealizacion" title="Fecha Realización"
										sortable="false" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:4%"
										format="{0,date,dd/MM/yyyy}" />

									<display:column title="Estado" property="estado" sortable="false"
										headerClass="sortable" defaultorder="descending" style="width:4%"/>

									<display:column property="respuesta" title="Respuesta" sortable="false" headerClass="sortable"
										defaultorder="descending" style="width:4%" />

									<display:column title="" style="width:4%">
										<table align="center">
											<tr>
												<td style="border: 0px;">
													<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
														onclick="obtenerDetalleConsultaEEFF(<s:property value="#attr.tablaConsultasEEFFMatr.numExpediente"/>);" title="Ver Detalle"/>
												</td>
											</tr>
										</table>
									</display:column>
								</display:table>
							</td>
						</tr>
					</table>
				</s:if>	
			</div>
		</s:if>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		ocultarCamposEEFFLiberacion();
	});
</script>