<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/estadisticas/estadisticas.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->
	
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">ESTADÍSTICAS: Listado de Histórico de Tasa en Expedientes</span>
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
							name="bLimpiar" 
							id="bLimpiar" 
							value="Verificar Contraseña"  
							onkeypress="this.onClick" 
							onclick="return comprobarPasswordListadoTasasPorExpediente();"/>			
					</td>
				</tr>
			</table>
		</s:form>	
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
	</s:if>

	<s:if test="%{passValidado=='true'}">		
		<s:form method="post" id="formData" name="formData">
			<div id="busqueda">
			  	<table class="tablaformbasica">		 
		        	<tr>
						<td align="left" nowrap="nowrap" style="padding-top:.75em;vertical-align:middle"> 
							<label for="idNumExpediente">Número Expediente: </label> 
			   			</td>     
			   			<td align="left"  width="50%" style="padding-top:.5em;vertical-align:middle">
							<s:textfield name="listadoTasasPorExpedienteBean.numExpediente" 
		        					id="idNumExpediente" 
		        					onblur="this.className='input2';" 
		        					onfocus="this.className='inputfocus';" 
		        					size="15" maxlength="15"/>
						</td>
						<td align="left" nowrap="nowrap" style="padding-top:.75em;vertical-align:middle"> 
							<label for="idCodigoTasa">Código Tasa: </label> 
			   			</td>     
			   			<td align="left"  width="50%" style="padding-top:.5em;vertical-align:middle">
							<s:textfield name="listadoTasasPorExpedienteBean.codigoTasa" 
		        					id="idCodigoTasa" 
		        					onblur="this.className='input2';" 
		        					onfocus="this.className='inputfocus';" 
		        					size="12" maxlength="12"/>
						</td>										
					</tr>		
				</table>				
			    
				<table class="acciones">
					<tr>
						<td>
							<input type="button" 
								class="boton" 
								name="bBuscar" 
								id="bBuscar" 
								value="Listar Tasas"  
								onkeypress="this.onClick" 
								onclick="return buscarListadoTasasPorExpediente();"/>
							&nbsp;
							<input type="button" 
								class="boton" 
								name="bLimpiar" 
								id="bLimpiar" 
								value="Limpiar"  
								onkeypress="this.onClick" 
								onclick="limpiarFormulario(this.form.id);"/>
							&nbsp;								
						</td>
					</tr>
				</table>
			</div>
		
		
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
											onchange="cambiarElementosPorPaginaConsulta('navegarListadoTasasPorExpediente.action', 'displayTagDivConsulta', this.value);" /> 
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
								requestURI="navegarListadoTasasPorExpediente.action"
								class="tablacoin"
								uid="tablaConsultaEstadisticasEvolucionTasa"
								summary="Listado de Tasas"
								cellspacing="0" cellpadding="0" style="width:100%">		
						
					<display:column property="id.codigoTasa" title="Código Tasa"
							sortable="true" headerClass="sortable"
							defaultorder="descending"/>
					
					<display:column property="numExpediente" title="Núm Expediente"
							sortable="true" headerClass="sortable"
							defaultorder="descending"/>

					<display:column property="tasa.tipoTasa" title="Tipo Tasa"
							sortable="true" headerClass="sortable"
							defaultorder="descending" />

			        <display:column property="id.fechaHora" title="Fecha Hora"
							sortable="true" headerClass="sortable"
							defaultorder="descending" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>							
					
					<display:column property="accion" title="Acción"
							sortable="true" headerClass="sortable"
							defaultorder="descending"/>			
							
					<display:column property="usuario.apellidosNombre" title="Usuario"
							sortable="true" headerClass="sortable" 
							defaultorder="descending" />
												
								
				</display:table>
			</div>
			<div id="bloqueLoadingConsultarEst" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>	
		</s:form>
	</s:if>
	
<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoTasasPorExpediente();
	    }
	});
});
</script>	