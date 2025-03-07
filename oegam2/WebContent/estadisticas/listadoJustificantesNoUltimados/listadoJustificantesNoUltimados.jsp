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
<script  type="text/javascript"></script>


	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">ESTADÍSTICAS: Listado de Justificantes Profesionales No Ultimados</span>
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
			        	 name="listadoJustificantesNoUltimadosBean.password">		
			        </td>						
					<td align="left" nowrap="nowrap" colspan="2">
						<input type="button" 
							class="botonMasGrande" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Verificar Contraseña"  
							onkeypress="this.onClick" 
							onclick="return comprobarPasswordListadoJustificantesNoUltimados();"/>			
					</td>
				</tr>
			</table>
		</s:form>	
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
	</s:if>

	<s:if test="%{passValidado=='true'}">

			<s:form method="post" id="formData" name="formData">
			
				<s:hidden name="numElementosSinAgrupar"/>
			
				<div id="busqueda">
				
				 <table class="tablaformbasica">		 
			        <tr>
						<td align="right"  width="50%" nowrap="nowrap" style="padding-top:.75em;vertical-align:middle"> 
							<label for="idNumcolegiado">Núm. Colegiado: </label> 
			   			</td>     
			   			<td align="left"  width="50%" style="padding-top:.5em;vertical-align:middle">
							<s:textfield name="listadoJustificantesNoUltimadosBean.numColegiado" 
		        					id="idNumColegiado" 
		        					onblur="this.className='input2';" 
		        					onfocus="this.className='inputfocus';" 
		        					size="5" maxlength="4"/>
						</td>
					</tr>		
				</table>				
				
				<table class="tablaformbasica">	

		        	<tr>
		        		<td align="left" nowrap="nowrap" style="vertical-align: middle;">
	        				<label>Fecha de Inicio: </label>
	       				</td>
	
						<td align="left">
							<table style="width:20%">
								<tr>
									<td align="right" style="vertical-align: middle;">
										<label for="diaMatrIni">desde: </label>
									</td>
		
									<td>
										<s:textfield name="listadoJustificantesNoUltimadosBean.fechaInicio.diaInicio" 
											id="diaMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2"/>
									</td>
		
									<td style="vertical-align: middle;">/</td>
		
									<td>
										<s:textfield name="listadoJustificantesNoUltimadosBean.fechaInicio.mesInicio" 
											id="mesMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2"/>
									</td>
		
									<td style="vertical-align: middle;">/</td>
		
									<td>
										<s:textfield name="listadoJustificantesNoUltimadosBean.fechaInicio.anioInicio" 
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
										<s:textfield name="listadoJustificantesNoUltimadosBean.fechaInicio.diaFin" 
											id="diaMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>
							
									<td style="vertical-align: middle;">/</td>
							
									<td>
										<s:textfield name="listadoJustificantesNoUltimadosBean.fechaInicio.mesFin" 
											id="mesMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>
							
									<td style="vertical-align: middle;">/</td>
									
									<td>
										<s:textfield name="listadoJustificantesNoUltimadosBean.fechaInicio.anioFin" 
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
								
				<table class="acciones">
						<tr>
							<td>
								<input type="button" 
									class="boton" 
									name="bBuscar" 
									id="bBuscar" 
									value="Buscar"  
									onkeypress="this.onClick" 
									onclick="return buscarListadoJustificantesNoUltimados();"/>			
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
										onchange="cambiarElementosPorPaginaConsulta('navegarListadoJustificantesNoUltimados.action', 'displayTagDivConsulta', this.value);" /> 
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
							requestURI="navegarListadoJustificantesNoUltimados.action"
							class="tablacoin"
							uid="tablaListadoJustificantesNoUltimados"
							summary="Listado de Justificantes No Ultimados"
							cellspacing="0" cellpadding="0">	
								

			 	
				<display:column property="[0]" sortable="true" headerClass="sortable"
						defaultorder="descending" title="Matrícula" sortProperty="matricula" style="width:6%"/>
						
				<display:column property="[1]" title="Número Justificantes Profesionales"
						sortable="true" headerClass="sortable" defaultorder="descending" sortProperty="cantidad"
						style="width:6%" />

			</display:table>
			
			<s:if test="%{lista.getFullListSize() > 0}">
				<table class="totalresults" summary="Numero total y paginacion" >
		 			<tr>
		 				<td align="left">
		 					<strong>Total de registros:&nbsp;${numElementosSinAgrupar}</strong>
		 				</td>
		 			</tr>
		 		</table>
	 		</s:if>
	 				
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<table class="acciones">
				<tr>
					<td>
						<div>
							<input type="button" class="boton" name="bExcelJustificantes" id="bExcelJustificantes" value="Consultar Excel" onClick="return generarExcelListadoJustificantesNoUltimados();" onKeyPress="this.onClick" />
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
	    	comprobarPasswordListadoJustificantesNoUltimados();
	    }
	});
});
</script>	