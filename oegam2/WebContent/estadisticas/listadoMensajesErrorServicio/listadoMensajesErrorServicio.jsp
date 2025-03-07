<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/estadisticas/estadisticas.js" type="text/javascript"></script>
<script type="text/javascript"></script>


<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">ESTADÍSTICAS: Listado Mensajes Error Servicio</span>
			</td>
		</tr>
	</table>
</div>

<s:if test="%{passValidado=='false' or passValidado=='error'}">
	<s:form method="post" id="formData" name="formData">
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="idPassword">Introduzca la clave:</label> 
				</td>
				<td align="center" nowrap="nowrap">					
		        	<input type="password" autocomplete="off" onblur="this.className='input2';"
		        	 onfocus="this.className='inputfocus';" id="idPassword" 
		        	 value="" maxlength="40" size="20" 
		        	 name="password">		
		        </td>						
				<td align="left" nowrap="nowrap" colspan="2">
					<input type="button" 
						class="botonMasGrande" 
						id="bPassword" 
						value="Verificar Contraseña"  
						onkeypress="this.onClick" 
						onclick="return comprobarPasswordListadoMensajesErrorServicio();"/>			
				</td>
			</tr>
		</table>
	</s:form>	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
</s:if>

<s:if test="%{passValidado=='true'}">
	<s:form method="post" id="formData" name="formData">
	
	<s:hidden name="listadoMensajesErrorServicioBean.tipoInterviniente"/>	
		<div id="busqueda">				
			<table class="tablaformbasica" border="0">	
	        	<tr>
	        		<td align="left" nowrap="nowrap" style="vertical-align: middle;width:20%">
        				<label>Fecha de mensaje:</label>
       				</td>
       				
					<td align="left" style="vertical-align: middle;">
						<table style="width:20%">
							<tr>
							    <td align="left" style="vertical-align: middle;">
									<label for="diaMatrIni">desde: </label>
								</td>
								<td>
									<s:textfield name="listadoMensajesErrorServicioBean.fecha.diaInicio" 
										id="diaMatrIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										readonly="false"
										size="2" maxlength="2"/>
								</td>	
								<td style="vertical-align: middle;">/</td>	
								<td>
									<s:textfield name="listadoMensajesErrorServicioBean.fecha.mesInicio" 
										id="mesMatrIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										readonly="false"
										size="2" maxlength="2"/>
								</td>	
								<td style="vertical-align: middle;">/</td>	
								<td>
									<s:textfield name="listadoMensajesErrorServicioBean.fecha.anioInicio" 
										id="anioMatrIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										readonly="false"
										size="5" maxlength="4"/>
								</td>	
								<td style="vertical-align: middle;">
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrIni, document.formData.mesMatrIni, document.formData.diaMatrIni);return false;" 
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
						</table>
					</td>
					
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="left" style="vertical-align: middle;">
									<label for="diaMatrFin">hasta:</label>
								</td>
								<td>
									<s:textfield name="listadoMensajesErrorServicioBean.fecha.diaFin" 
										id="diaMatrFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2" />
								</td>						
								<td style="vertical-align: middle;">/</td>						
								<td>
									<s:textfield name="listadoMensajesErrorServicioBean.fecha.mesFin" 
										id="mesMatrFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2" />
								</td>						
								<td style="vertical-align: middle;">/</td>								
								<td>
									<s:textfield name="listadoMensajesErrorServicioBean.fecha.anioFin" 
										id="anioMatrFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="5" maxlength="4" />
								</td>								
								<td style="vertical-align: middle;">
						    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrFin, document.formData.mesMatrFin, document.formData.diaMatrFin);return false;" 
						    			title="Seleccionar fecha">
						    			<img class="PopcalTrigger" 
						    				align="left" 
						    				src="img/ico_calendario.gif" 
						    				width="15" height="16" 
						    				border="0" alt="Calendario"/>
				   			    	</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		
			<table class="acciones" border="0">
				<tr>
					<td>
						<input type="button" 
							class="boton" 
							name="bBuscar" 
							id="bBuscar" 
							value="Buscar"  
							onkeypress="this.onClick" 
							onclick="return buscarListadoMensajesErrorServicio();"/>				
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="limpiarFormulario(this.form.id);"/>			
					</td>
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
	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
		
		<div id="resultado" style="width:100%;background-color:transparent;" >
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
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina"
										value="resultadosPorPagina"
										title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarListadoMensajesErrorServicio.action', 'displayTagDivConsulta', this.value);" /> 
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
		</div>
		
		<script type="text/javascript">
			$(function() {
				$("#displayTagDivConsulta").displayTagAjax();
			});
		</script>
	
		<div id="displayTagDivConsulta" class="divScroll">
		
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			
			<display:table name="lista" excludedParams="*"
							requestURI="navegarListadoMensajesErrorServicio.action"
							class="tablacoin"
							uid="tablaListadoGeneralYPersonalizado"
							summary="Listado de estadisticas de tramite"
							cellspacing="0" cellpadding="0" style="width:100%">
							
				<display:column property="fecha" title="Fecha"
						sortable="true" headerClass="sortable"
						defaultorder="descending" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>
	
				<display:column  property="proceso" sortable="true" headerClass="sortable"
						defaultorder="descending" title="Proceso" />	
						
				<display:column property="idEnvio" title="ID Envío"
						sortable="true" headerClass="sortable"
						defaultorder="descending"/>
						
				<display:column property="cola" title="Cola"
						sortable="true" headerClass="sortable"
						defaultorder="descending"/>
				
				<display:column property="descripcion" title="Descripción"
						sortable="true" headerClass="sortable"
						defaultorder="descending"/>
							
			</display:table>
	 		
		</div>
		
		<s:if test="%{lista.getFullListSize() > 0}">
			<table class="acciones">
				<tr>
					<td>
						<div>
							<input type="button" class="boton" name="bExcel" id="bExcel" value="Consultar Excel" onClick="return generarExcelListadoMensajesErrorServicio();" onKeyPress="this.onClick" />
						</div>			
					</td>
				</tr>
			</table>
		</s:if>
		<div id="bloqueLoadingConsultarEst" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>	
	</s:form>
</s:if>

<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoMensajesErrorServicio();
	    }
	});
});
</script>