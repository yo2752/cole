<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/consultaNotificacionBotones.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script  type="text/javascript">

function buscarConsultaNotificacion() {
	if (validaCodigoNotificacion()) {
		document.formData.action = "buscarConsultaNotificacion.action";
		document.formData.submit();
	}
}

function actualizarConsultaNotificacion() {
	if (validaCodigoNotificacion()) {
		document.formData.action = "actualizarConsultaNotificacion.action";
		document.formData.submit();
	}
}

function limpiarConsultaNotificacion(){
	var comboTipoRol = document.getElementById("tipoRolNotificacion");
	var defaultRolOption = comboTipoRol.options[0];
	comboTipoRol.value = defaultRolOption.value;
	
	var comboTipoEstado = document.getElementById("tipoEstadoNotificacion");
	var defaultEstadoOption = comboTipoEstado.options[0];
	comboTipoEstado.value=defaultEstadoOption.value;
	
	document.getElementById("destinatario").value = "";
	document.getElementById("codigo").value = "";

	document.getElementById("diaAltaDispIni").value = "";
	document.getElementById("mesAltaDispIni").value = "";
	document.getElementById("anioAltaDispIni").value = "";

	document.getElementById("diaAltaDispFin").value = "";
	document.getElementById("mesAltaDispFin").value = "";
	document.getElementById("anioAltaDispFin").value = "";

	document.getElementById("diaAltaIni").value = "";
	document.getElementById("mesAltaIni").value = "";
	document.getElementById("anioAltaIni").value = "";

	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
	
	document.getElementById("numColegiado").value = "";
}

function validaCodigoNotificacion() {
	if (document.getElementById("codigo").value != null
			&& document.getElementById("codigo").value != "") {
		if (!validaNumeroEntero(document.getElementById("codigo").value)) {
			alert("Código de Notificación ha de ser numérico");
			return false;
		}
	}
	return true;
}

</script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de notificaciones - Seguridad Social</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<s:form  method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
	       		<td align="left" nowrap="nowrap">
	       			<label for="rol">Rol:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
<%--        				<s:textfield name="consultaNotificacionBean.rol" id="rol" onblur="this.className='input2';" --%>
<%-- 						onfocus="this.className='inputfocus';" size="35" maxlength="9" /> --%>
					<s:select 
						list="@org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados.UtilesNotificacion@getTipoRolesNotificacion()"
						id="tipoRolNotificacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="consultaNotificacionBean.rol"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Rol"
						disabled="false"/>
       			</td>
       			<td align="left" nowrap="nowrap">
	       			<label for="codigo">Código Notificación:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<s:textfield name="consultaNotificacionBean.codigo" id="codigo" onblur="this.className='input2';"
					onchange="javascript:validaCodigoNotificacion();"	onfocus="this.className='inputfocus';" size="35" maxlength="20" />
       			</td>
       			<td align="left" nowrap="nowrap">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<label for="numColegiado">Num Colegiado: </label> 
       				</s:if>
       			</td>     
       			<td align="left">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
       					<s:textfield name="consultaNotificacionBean.numColegiado" id="numColegiado" onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" size="10" maxlength="9"/>
        			</s:if>
       			</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
	       			<label for="descripcionEstado">Estado:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<s:select 
						list="@org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados.UtilesNotificacion@getTipoEstadosNotificacion()"
						id="tipoEstadoNotificacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="consultaNotificacionBean.estado"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Estado"
						disabled="false"/>
       			</td>
       			<td align="left" nowrap="nowrap">
	       			<label for="destinatario">Destinatario:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<s:textfield name="consultaNotificacionBean.destinatario" id="destinatario" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="20" />
       			</td>
			</tr>
			</table>
			<table>
			<tr>
				<td align="right" nowrap="nowrap">
        				<label>Fecha puesta disposición: </label>
       				</td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaAltaDispIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="consultaNotificacionBean.fechaPuestaDisposicion.diaInicio" 
										id="diaAltaDispIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="consultaNotificacionBean.fechaPuestaDisposicion.mesInicio" 
										id="mesAltaDispIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="consultaNotificacionBean.fechaPuestaDisposicion.anioInicio" 
										id="anioAltaDispIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4"/>
								</td>
	
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaDispIni, document.formData.mesAltaDispIni, document.formData.diaAltaDispIni);return false;" 
				    					title="Seleccionar fecha">
					    				<img class="PopcalTrigger" 
					    					align="left" 
					    					src="img/ico_calendario.gif" 
					    					width="15" height="16" 
					    					border="0" alt="Calendario"/>
				    				</a>
								</td>
	
								<td width="2%"></td>
							</tr>
						</TABLE>
					</td>
					
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaAltaDispFin">hasta:</label>
								</td>
						
								<td>
									<s:textfield name="consultaNotificacionBean.fechaPuestaDisposicion.diaFin" 
										id="diaAltaDispFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="consultaNotificacionBean.fechaPuestaDisposicion.mesFin" 
										id="mesAltaDispFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="consultaNotificacionBean.fechaPuestaDisposicion.anioFin" 
										id="anioAltaDispFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
								
								<td>
						    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaDispFin, document.formData.mesAltaDispFin, document.formData.diaAltaDispFin);return false;" 
						    			title="Seleccionar fecha">
						    			<img class="PopcalTrigger" 
						    				align="left" 
						    				src="img/ico_calendario.gif" 
						    				width="15" height="16" 
						    				border="0" alt="Calendario"/>
				   			    	</a>
								</td>
							</tr>
						</TABLE>
					</td>
			</tr>
    		<tr>
    			<td align="right" nowrap="nowrap">
        				<label>Fecha fin disponibilidad: </label>
       				</td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaAltaIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="consultaNotificacionBean.fechaFinDisponibilidad.diaInicio" 
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="consultaNotificacionBean.fechaFinDisponibilidad.mesInicio" 
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="consultaNotificacionBean.fechaFinDisponibilidad.anioInicio" 
										id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4"/>
								</td>
	
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" 
				    					title="Seleccionar fecha">
					    				<img class="PopcalTrigger" 
					    					align="left" 
					    					src="img/ico_calendario.gif" 
					    					width="15" height="16" 
					    					border="0" alt="Calendario"/>
				    				</a>
								</td>
	
								<td width="2%"></td>
							</tr>
						</TABLE>
					</td>
					
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaAltaFin">hasta:</label>
								</td>
						
								<td>
									<s:textfield name="consultaNotificacionBean.fechaFinDisponibilidad.diaFin" 
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="consultaNotificacionBean.fechaFinDisponibilidad.mesFin" 
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="consultaNotificacionBean.fechaFinDisponibilidad.anioFin" 
										id="anioAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
								
								<td>
						    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
						    			title="Seleccionar fecha">
						    			<img class="PopcalTrigger" 
						    				align="left" 
						    				src="img/ico_calendario.gif" 
						    				width="15" height="16" 
						    				border="0" alt="Calendario"/>
				   			    	</a>
								</td>
							</tr>
						</TABLE>
					</td>
    		</tr>
		</table>
		
		
		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
			
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaNotificacion();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bActualizar" id="bActualizar" value="Descargar" onkeypress="this.onClick" onclick="return actualizarConsultaNotificacion();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarConsultaNotificacion()" value="Limpiar"  />			
				</td>
			</tr>
		</table>
			
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>
			
		<div id="resultado" style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
				</tr>
			</table>
		</div>
		
		<%@include file="../../includes/erroresYMensajes.jspf"%>
			
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
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaNotificacion.action', 'displayTagDiv', this.value);" /> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>	
		
		<div id="displayTagDiv" class="divScroll">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*" requestURI="navegarConsultaNotificacion.action" class="tablanotifi"
				uid="tablaNotificaciones" summary="Listado de Notificaciones" cellspacing="0" cellpadding="0" sort="external"
				decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaNotificacion">
				
				<%-- <display:column property="rol" title="Rol" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" /> --%>
				<display:column property="id.codigo" title="Codigo Notificación" sortable="true" headerClass="sortable" defaultorder="descending" style="width:1%" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="id.numColegiado" title="Num. Colegiado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:1%" />
				</s:if>
				<display:column property="descripcionEstado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:1%" />
				<%-- <display:column property="descripcionTipoDestinatario" title="Descripción Destinatario" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" /> --%>
				<display:column property="destinatario" title="Destinatario" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<%-- <display:column property="procedimiento" title="Procedimiento" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" /> --%>
				<display:column property="nombreAppRazonSocial" title="Razón Social" sortable="true" headerClass="sortable" sortProperty="nombreAppRazonSocial" style="width:22%"/>
				<display:column property="fechaPuestaDisposicion" title="Fecha Puesta Disposición" sortable="true" headerClass="sortable" sortProperty="fechaPuestaDisposicion" style="width:10%"/>		
				<display:column property="fechaFinDisponibilidad" title="Fecha Fin Disponibilidad" sortable="true" headerClass="sortable" sortProperty="fechaFinDisponibilidad" style="width:8%" />
				<display:column style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksNotificacion);' onKeyPress='this.onClick'">
					<table align="center">
						<tr>
							<td style="border: 0px;">
			  					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
			  						onclick="abrirEvolucion(<s:property value="#attr.tablaNotificaciones.id.codigo"/>, <s:property value="#attr.tablaNotificaciones.id.numColegiado"/>);" title="Ver evolución"/>
			  				</td>
							<td><input 
									type="checkbox" 
									name="listaChecksNotificacion" 
									id="checkNotificacion" 
									value='<s:property value="#attr.tablaNotificaciones.rol"/>,<s:property value="#attr.tablaNotificaciones.id.codigo"/>,
										<s:property value="#attr.tablaNotificaciones.estado"/>,<s:property value="#attr.tablaNotificaciones.id.numColegiado"/>'/></td>
						</tr>
					</table>
				</display:column>
				
			</display:table>	
		</div>
		
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bAceptaNoti" id="bAceptaNoti" value="Aceptar Notificación" onkeypress="this.onClick" onclick="return aceptarConsultaNotificacion(this);"/>			
					<input type="button" class="botonGrande" name="bRechazaNoti" id="bRechazaNoti" value="Rechazar Notificación" onkeypress="this.onClick" onclick="return rechazarConsultaNotificacion(this);"/>			
					<input type="button" class="boton" name="bImprimeNoti" id="bImprimeNoti" value="Imprimir Notificación" onkeypress="this.onClick" onclick="return imprimirConsultaNotificacion(this)" />			
				</td>
			</tr>
		</table>
		
	</div>
</s:form>

