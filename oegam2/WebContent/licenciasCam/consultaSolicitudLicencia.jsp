<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/licenciasCam/licencias.js" type="text/javascript"></script>

<script type="text/javascript">
	trimCamposInputs();


	function buscarLicenciasCam() {
		// Validamos el numero de expediente
		var numExpediente = document.getElementById("numExpediente").value;
		
		if (numExpediente != null) {		
			numExpediente = numExpediente.replace(/\s/g,'');
			
		if (isNaN(numExpediente)) {
			alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
				return false;
			}		
						
			document.getElementById("numExpediente").value = numExpediente;
		}
		// //Para que muestre el loading
		iniciarProcesando('bConsConsulta','loadingImage');
		document.formData.action = "buscarConsultaSolicitudLicencia.action";
		document.formData.submit();
		ocultarLoadingConsultarLic(document.getElementById('bConsConsulta'), "Consultar");
	}

</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Consulta Solicitud Licencia
			</span></td>
		</tr>
	</table>
</div>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">

				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Núm. Expediente:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaLicenciasCamBean.numExpediente" id="numExpediente" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" 	onkeypress="return validarNumerosEnteros(event)"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().getEstadoLicencias()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
				    		name="consultaLicenciasCamBean.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
					
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap"><label for="labelNumColegiado">Núm. Colegiado:</label></td>
						<td  align="left">
							<s:textfield name="consultaLicenciasCamBean.numColegiado" id="idNumColegiado" size="5" maxlength="4" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';" 	onkeypress="return validarNumerosEnteros(event)"/>
						</td>
						
						<td align="left" nowrap="nowrap" style="vertical-align: middle;" ><label for="referenciaPropia">Ref. propia:</label></td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="consultaLicenciasCamBean.refPropia" id="referenciaPropia" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="50" maxlength="50"/>
						</td>
						
					</tr>
				</s:if>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaAlta.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaAlta.anioInicio" id="anioFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIni, document.formData.mesFechaAltaIni, document.formData.diaFechaAltaIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaAlta.anioFin" id="anioFechaAltaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFin, document.formData.mesFechaAltaFin, document.formData.diaFechaAltaFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPresentacionDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaPresentacion.diaInicio" id="diaFechaPresentacionIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaPresentacion.mesInicio" id="mesFechaPresentacionIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaPresentacion.anioInicio" id="anioFechaPresentacionIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPresentacionIni, document.formData.mesFechaPresentacionIni, document.formData.diaFechaPresentacionIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPresentacionHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaPresentacion.diaFin" id="diaFechaPresentacionFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaPresentacion.mesFin" id="mesFechaPresentacionFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaLicenciasCamBean.fechaPresentacion.anioFin" id="anioFechaPresentacionFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPresentacionFin, document.formData.mesFechaPresentacionFin, document.formData.diaFechaPresentacionFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return buscarLicenciasCam();"/>			
			
			<input type="button" class="boton" value="Limpiar campos" id="bLimpiar" onclick="limpiarFormulario(this.form.id);"/>			
		</div>

		<s:if test="%{resumenCambEstado}">
			<br>
			<div id="resumenCambEstadoConsultaLicencias" style="text-align: center;">
				<%@include file="resumenCambEstadoConsultaLicencias.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenValidacion}">
			<br>
			<div id="resumenValidacionConsultaRegistradores" style="text-align: center;">
				<%@include file="resumenValidacionConsultaLicencias.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenEliminacion}">
			<br>
			<div id="resumenEliminacionConsultaLicencias" style="text-align: center;">
				<%@include file="resumenEliminacionConsultaLicencias.jspf" %>
			</div>
			<br><br>
		</s:if>
		
		<br/>	
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right">
									<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaSolicitudLicencia.action', 'displayTagDivconsultaLicenciasCam', this.value);" > 
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivconsultaLicenciasCam" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaSolicitudLicencia" requestURI= "navegarConsultaSolicitudLicencia.action"
				id="tablaConsultaSolicitudLicencia" summary="Listado de Consultas de Licencias Cam" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorTramiteLicencia">	

				<display:column title="Núm. Expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:15%" >
					<s:if test="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esAnulado(#attr.tablaConsultaSolicitudLicencia.estado)">
						<s:property value="#attr.tablaConsultaSolicitudLicencia.numExpediente"/>
					</s:if>	
					<s:else>
						<a href="recuperarLicenciaAltaSolicitudLicencia.action?identificadorTramite=${tablaConsultaSolicitudLicencia.numExpediente}">	
							<s:property value="#attr.tablaConsultaSolicitudLicencia.numExpediente"/> 
						</a>
					</s:else>	
				</display:column>
				
				<display:column property="refPropia" title="Ref. Propia" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:15%"/>
				
				<display:column property="fechaAlta" title="Fecha Alta"	sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:15%" format="{0,date,dd/MM/yyyy}" />
					
				<display:column property="fechaPresentacion" title="Fecha Presentación"	sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:15%" format="{0,date,dd/MM/yyyy}" />
					
				<display:column title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:15%">
					<s:property value="%{@org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam@convertirTexto(#attr.tablaConsultaSolicitudLicencia.estado)}" />
				</display:column>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="numColegiado" title="Núm. Colegiado" sortable="true" headerClass="sortable" 
						defaultorder="descending" style="width:15%"/>
				</s:if>
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksLicencias);' 
					onKeyPress='this.onClick'/>" style="width:1%">
						<table align="center">
							<tr>
								<td style="border: 0px;">
									<input type="checkbox" name="listaChecksLicencias" id="numExpediente" value='<s:property value="#attr.tablaConsultaSolicitudLicencia.numExpediente"/>' />
								</td>
								<td style="border: 0px;">
		  							<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  								onclick="abrirEvolucion(<s:property value="#attr.tablaConsultaSolicitudLicencia.numExpediente"/>,'divEmergenteconsultaLicenciasCamEvolucion');" title="Ver evolución"/>
		  						</td>
							</tr>
						</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingconsultaLicenciasCam" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bCambiarEstado" id="idCambiarEstado" value="Cambiar Estado" onClick="javascript:abrirVentanaSeleccionEstados();"
			 			onKeyPress="this.onClick"/>
			 	</s:if>
		
					<input class="boton" type="button" id="bValidarLC" name="bValidarLC" value="Validar" onClick="javascript:validarConsultaSolicitudLicencia();" onKeyPress="this.onClick"/>
		
				
				<input type="button" class="boton" name="bEliminar" id="idEliminar" value="Eliminar" onClick="javascript:eliminarLicencia();"onKeyPress="this.onClick"/>			
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteconsultaLicenciasCam" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteconsultaLicenciasCamEvolucion" style="display: none; background: #f4f4f4;"></div>
	<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
</div>

<script type="text/javascript">
	trimCamposInputs();
</script>