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

<script  type="text/javascript">

function buscarRespuestaSeguridadSocial() {
	document.formData.action = "buscarRespuestaSeguridadSocial.action";
	document.formData.submit();
}

function limpiarRespuestaSeguridadSocial(){
	
	document.getElementById("codigoNotificacion").value = "";

	document.getElementById("diaAltaNotiIni").value = "";
	document.getElementById("mesAltaNotiIni").value = "";
	document.getElementById("anioAltaNotiIni").value = "";

	document.getElementById("diaAltaNotiFin").value = "";
	document.getElementById("mesAltaNotiFin").value = "";
	document.getElementById("anioAltaNotiFin").value = "";

}

</script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Respuesta - Seguridad Social</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
	       		<td align="left" nowrap="nowrap">
	       			<label for="codigoNotificacion">Código Notificación</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
					<s:textfield name="respuestaSeguridadSocialBean.codigoNotificacion" id="codigoNotificacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="20" />
       			</td>
       		</tr>
       		<tr>
       			<td align="left" nowrap="nowrap">
	       			<label>Fecha Notificación</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<TABLE>
							<tr>
								<td align="right">
									<label for="diaAltaNotiIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="respuestaSeguridadSocialBean.fechaNotificacion.diaInicio" 
										id="diaAltaNotiIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="respuestaSeguridadSocialBean.fechaNotificacion.mesInicio" 
										id="mesAltaNotiIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="respuestaSeguridadSocialBean.fechaNotificacion.anioInicio" 
										id="anioAltaNotiIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4"/>
								</td>
	
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaNotiIni, document.formData.mesAltaNotiIni, document.formData.diaAltaNotiIni);return false;" 
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
						<TABLE>
							<tr>
								<td align="left">
									<label for="diaAltaNotiFin">hasta:</label>
								</td>
						
								<td>
									<s:textfield name="respuestaSeguridadSocialBean.fechaNotificacion.diaFin" 
										id="diaAltaNotiFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="respuestaSeguridadSocialBean.fechaNotificacion.mesFin" 
										id="mesAltaNotiFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="respuestaSeguridadSocialBean.fechaNotificacion.anioFin" 
										id="anioAltaNotiFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
								
								<td>
						    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaNotiFin, document.formData.mesAltaNotiFin, document.formData.diaAltaNotiFin);return false;" 
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
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarRespuestaSeguridadSocial();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarRespuestaSeguridadSocial()" value="Limpiar"  />			
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
										onchange="cambiarElementosPorPaginaConsulta('navegarRespuestaSeguridadSocial.action', 'displayTagDiv', this.value);" /> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>	

		<script type="text/javascript">
			$(function() {
				$("#displayTagDiv").displayTagAjax();
			})
		</script>
		
		<div id="displayTagDiv" class="divScroll">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*" requestURI="navegarRespuestaSeguridadSocial.action" class="tablanotifi"
				uid="tablaRespuestaSS" summary="Respuestas Seguridad Social" cellspacing="0" cellpadding="0" sort="external"
				decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorRespuestaSeguridadSocial">
				
				<display:column property="notificacion.codigo" title="Código" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" />
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />				
				<display:column property="fechaNotificacion" title="Fecha Notificación" sortable="true" headerClass="sortable" sortProperty="fechaNotificacion" style="width:4%"/>	
			</display:table>	
		</div>
		
<!-- 		<table class="acciones"> -->
<!-- 			<tr> -->
<!-- 				<td> -->
<!-- 					<input type="button" class="boton" name="bRefrescaResult" id="bRefrescaResult" value="Refrescar Resultados" onkeypress="this.onClick" onclick="return aceptarConsultaNotificacion(this);"/>		 -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
</div>
</s:form>