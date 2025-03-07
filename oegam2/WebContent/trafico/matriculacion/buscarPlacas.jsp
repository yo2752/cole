<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/placas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
</iframe>
<s:form method="post" id="formData" name="formData">
	<div class="nav" style="margin-left: 0.9em">
		<table style="width:100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta de solicitudes de placas de matriculación</span></td>
			</tr>
		</table>
	</div>
	
	<div id="divError">
		<s:if test="hasActionMessages() || hasActionErrors()">
			<table class="tablaformbasica" style="width:93.8%;margin-left:1.3em">
				<tr>
					<td align="left">						
						<ul id="mensajesInfo" style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						<s:if test="hasActionMessages()">
							<s:iterator value="actionMessages">
								<li><span><s:property /></span></li>
								<li id="mostrarDocumentoLink" style="display:none;"><a href="#" onclick="window.open('mostrarDocumentoTrafico.action','Documento_de_impresión_generado',''); muestraYOculta();" title="Documento Generado (Link para imprimir">Documento generado</a> (Link para imprimir)</li>
							</s:iterator>
						</s:if>
						</ul>							
						<ul id="mensajesError" style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						<s:if test="hasActionErrors()">
							<s:iterator value="actionErrors">
								<li><span><s:property /></span></li>
								
							</s:iterator>
						</s:if>	
						</ul>							
					</td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<ul id="mensajesInfo"
				style="color: green; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;"></ul>
			<ul id="mensajesError"
				style="color: red; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;"/></ul>
		</s:else>
	</div>
	
	<table class="tablaformbasica" style="width:100%" class="tablaformbasica" >
		<tr>
			<td align="left" style="vertical-align: middle;">
				<label for="numExpediente">Estado: </label>
			</td>
			<td align="left">
				<s:select id="estado"
					list="@escrituras.utiles.UtilesVista@getInstance().getEstadoSolicPlacas()"
					onblur="this.className='input2';" headerValue="Seleccione Estado"
					onfocus="this.className='inputfocus';" headerKey="" cssStyle="width:220px"
					name="solicitudPlacasFilterBean.estado"></s:select>
			</td>
			<td align="left" style="vertical-align: middle;">
				<label for="numExpediente">Número de Expediente: </label>
			</td>
			<td align="left">
				<s:textfield name="solicitudPlacasFilterBean.numExpediente" id="numExpediente"
					onblur="validarContenidoCamposConsulta(this, event);"
					onfocus="validarContenidoCamposConsulta(this, event);"
					onchange="validarContenidoCamposConsulta(this, event);"
					cssStyle="color:#555; font-size:9px;" size="15"
					maxlength="15" />
			</td>			
		</tr>
		<tr> 
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="matricula">Matrícula: </label>
			</td>
			<td align="left">
				<s:textfield name="solicitudPlacasFilterBean.matricula" id="matricula"
					onblur="validarContenidoCampos(this, event);"
					onfocus="validarContenidoCampos(this, event);"
					onchange="validarContenidoCampos(this, event);return validarMatricula_alPegar(event)" size="7"
					maxlength="10"
					onkeypress="return validarMatricula(event)" onmousemove="return validarMatricula_alPegar(event)" 
					 />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="matricula">Referencia Propia: </label>
			</td>
			<td align="left">
				<s:textfield name="solicitudPlacasFilterBean.referenciaPropia" id="referenciaPropia"
					size="50"
					maxlength="500"
					 />
			</td>
		</tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="numColegiado">Contrato: </label>
				</td>
				<td>
					<s:select id="idContrato"
						list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" headerValue="Seleccione Contrato"
						onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
						listValue="descripcion" cssStyle="width:220px"
						name="solicitudPlacasFilterBean.idContrato"></s:select>
				</td>
				<td colspan="2"> </td>
		</s:if>
		<s:else>
			<tr>
				<td colspan="4">
					<!-- <s:hidden name="solicitudPlacasFilterBean.numColegiado" value = "%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNumColegiado()}"/> -->
					<s:hidden name="solicitudPlacasFilterBean.idContrato" value = "%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContrato()}"/>
				</td>
			</tr>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap" style="width:15%;vertical-align: middle;">
				<label for="labelFechaInicio">Fecha Solicitud de Inicio: </label>
			</td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="solicitudPlacasFilterBean.fechaSolicitud.diaInicio" id="fechaInicioDay"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left" style="vertical-align: middle;">/</td>
						<td align="left">
							<s:textfield name="solicitudPlacasFilterBean.fechaSolicitud.mesInicio"
								id="fechaInicioMonth" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left" style="vertical-align: middle;">/</td>
						<td align="left">
							<s:textfield name="solicitudPlacasFilterBean.fechaSolicitud.anioInicio"
								id="fechaInicioYear" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left" style="vertical-align: middle;">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.fechaInicioYear, document.formData.fechaInicioMonth, document.formData.fechaInicioDay);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="left" src="img/ico_calendario.gif" width="15" height="16"
								border="0" alt="Calendario" />
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" style="vertical-align: middle;">
				<label for="labelFechaFin">Fecha Solicitud de Fin: </label>
			</td>
			<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="solicitudPlacasFilterBean.fechaSolicitud.diaFin" id="fechaFinDay"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left" style="vertical-align: middle;">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="solicitudPlacasFilterBean.fechaSolicitud.mesFin" id="fechaFinMonth"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left" style="vertical-align: middle;">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="solicitudPlacasFilterBean.fechaSolicitud.anioFin" id="fechaFinYear"
								 onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left" style="vertical-align: middle;">
							<a href="javascript:;"onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.fechaFinYear, document.formData.fechaFinMonth, document.formData.fechaFinDay);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="left" src="img/ico_calendario.gif" width="15" height="16"
								border="0" alt="Calendario" />
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div class="acciones center">
		<input type="button" value="Buscar" class="boton" onclick="validarFormularioConsulta(this.form.id)" />
		<input type="button" value="Limpiar Formulario" onclick="limpiarFormulario(this.form.id);" class="boton" />
	</div>
	<br/>
	<div id="resultado"	style="width: 100%; background-color: transparent;">
		<h2 class="subtitulo">Resultado de la b&uacute;squeda</h2>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table style="width:100%">
			<tr>
				<td align="right">
					<table style="width:100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right">
								<s:select list="{'5','10','15','20','50','100','200','500'}"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina" value="resultadosPorPagina"
									title="Resultados por página"
									onchange="return cambiarElementosPorPaginaConsulPlacas();" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>		
	<div id="displayTagDiv" class="divScroll" style="overflow:hidden;">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*"
			requestURI="navegarConsultaPlacas.action" sort="list" class="tablacoin"
			uid="tablaConsultaTramite" summary="Listado de Solicicitudes Placas" cellspacing="1"
			cellpadding="0" pagesize="${resultadosPorPagina}" style="margin:.5em 0;">
				
			<display:column property="matricula" title="Matrícula"
				sortable="true" headerClass="sortable" defaultorder="descending"/>
				
			<display:column property="usuario.apellidosNombre" title="Usuario"
				sortable="true" headerClass="sortable" defaultorder="descending"/>

			<display:column property="fechaSolicitud" title="Fecha Solicitud"
				sortable="true" headerClass="sortable" defaultorder="descending"/>
									
			<display:column title="Estado" sortProperty="estado"
				sortable="true" headerClass="sortable" defaultorder="descending">
					
				<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && #attr.tablaConsultaTramite.estado.valorEnum==@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('3').valorEnum}">
						<s:set var="estadoMostrar" value="@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('2').nombreEnum" />
				</s:if>
				<s:else>
					<s:set var="estadoMostrar" value="#attr.tablaConsultaTramite.descEstado" />
				</s:else>
					<s:property value="estadoMostrar" />
			</display:column>
			<display:column property="numExpediente" title="Número de Expediente"
				sortable="true" headerClass="sortable" defaultorder="descending" />
			<display:column property="refPropia" title="Referencia Propia"
				sortable="true" headerClass="sortable" defaultorder="descending"/>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<display:column property="descContrato" title="Contrato" sortable="true" headerClass="sortable" defaultorder="descending"
					sortProperty="idContrato" >
				</display:column>
			</s:if>
			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick' style='display:inline-block'/>" 
				style="width:2%" >
				<table>
					<tr>
						<td style="border: 0px;">
							<input type="checkbox" name="listaChecksConsultaTramite" value='<s:property value="#attr.tablaConsultaTramite.idSolicitud"/>' />
							<input type="hidden" name="listaEstadoConsultaTramite" id="estado_<s:property value="#attr.tablaConsultaTramite.idSolicitud"/>" value='<s:property value="#attr.tablaConsultaTramite.estado"/>' />
						</td>
					</tr>
				</table>
			</display:column>
		</display:table>
	</div>
	<s:if test="%{lista.list.size()>0}">	
		<div class="acciones center">
			<input class="botonMasGrande" type="button" value="Editar Solicitudes" onclick="editarSolicitudesPlacas(this.form)" />
			<input class="botonMasGrande" type="button" value="Borrar Solicitudes" onclick="borrarSolicitudesPlacas(this.form)" />
		</div>	
	 </s:if>
</s:form>

