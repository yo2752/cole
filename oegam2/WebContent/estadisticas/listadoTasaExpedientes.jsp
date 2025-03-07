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
					<span class="titulo">ESTADISTICAS: Listado de Historico de Tasa en Expedientes</span>
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
			        	 name="consultaEstadisticas.password">		
			        </td>						
					<td align="left" nowrap="nowrap" colspan="2">
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Verificar Contraseña"  
							onkeypress="this.onClick" 
							onclick="return comprobarPasswordEvolucionTasa();"/>			
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
								<label for="idNumExpediente">Numero Expediente: </label> 
				   			</td>     
				   			<td align="left"  width="50%" style="padding-top:.5em;vertical-align:middle">
								<s:textfield name="consultaEstadisticas.numExpediente" 
			        					id="idNumExpediente" 
			        					onblur="this.className='input2';" 
			        					onfocus="this.className='inputfocus';" 
			        					size="15" maxlength="15"/>
							</td>
							<td align="left" nowrap="nowrap" style="padding-top:.75em;vertical-align:middle"> 
								<label for="idCodigoTasa">Codigo Tasa: </label> 
				   			</td>     
				   			<td align="left"  width="50%" style="padding-top:.5em;vertical-align:middle">
								<s:textfield name="consultaEstadisticas.codigoTasa" 
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
									name="bListarEvolucionTasa" 
									id="bListarEvolucionTasa" 
									value="Listar Tasas "  
									onkeypress="this.onClick" 
									onclick="return generarInformeEvolucionTasa();"/>			
								&nbsp;
								<input type="button" 
									class="boton" 
									name="bLimpiar" 
									id="bLimpiar" 
									value="Limpiar"  
									onkeypress="this.onClick" 
									onclick="return limpiarFormEvolucionTasa();"/>			
								&nbsp;								
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
			<!--</div>		-->
			<div id="resultado" style="width:100%;background-color:transparent;" >
				<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
					<tr>
						<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
					</tr>
				</table>
				
				<s:if test="%{listaConsultaEstadisticasEvolucionTasa.size()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
											onchange="return cambiarElementosPorPaginaConsultaEvolucionTasa();" 
											/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:if>
			</div>
		
		<div class="divScroll">
			<display:table name="listaConsultaEstadisticasEvolucionTasa" excludedParams="*"
							requestURI="navegarTasaEstadisticas.action"
							class="tablacoin"
							uid="tablaConsultaEstadisticasEvolucionTasa"
							summary="Listado de Tasas"
							cellspacing="0" cellpadding="0"
							pagesize="${resultadosPorPagina}">		
					
				<display:column property="id.codigoTasa" title="Codigo Tasa"
						sortable="true" headerClass="sortable"
						defaultorder="descending" maxLength="15"
						style="width:6%" />
				
				<display:column property="numExpediente" title="Numero Exp"
						sortable="true" headerClass="sortable"
						defaultorder="descending" maxLength="15"
						style="width:7%" />
						
				<display:column property="tasa.tipoTasa" title="Tipo Tasa"
						sortable="true" headerClass="sortable"
						defaultorder="descending" maxLength="8"
						style="width:3%" />
							
		        <display:column property="id.fechaHora" title="Fecha Hora"
						sortable="true" headerClass="sortable"
						defaultorder="descending" format="{0,date,dd-MM-yyyy HH:mm:ss}"
						style="width:10%" />							
				
				<display:column property="accion" title="Accion"
						sortable="true" headerClass="sortable"
						defaultorder="descending"
						style="width:5%" />			
						
				<display:column property="usuario.apellidosNombre" title="Usuario"
						sortable="true" headerClass="sortable" 
						defaultorder="descending"
						style="width:19%;text-align:center" >
				</display:column>
											
							
				<input type="hidden" name="resultadosPorPagina"/>
			</display:table>
		</div>
		</s:form>
	</s:if>
	
<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordEvolucionTasa();
	    }
	});
});
</script>	