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
				<span class="titulo">ESTADISTICAS: Listado mensajes error servicio</span>
			</td>
		</tr>
	</table>
</div>

<s:if test="%{!passValidado}">
	<s:form method="post" id="formData" name="formData">	
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="idPassword">Introduzca la clave:</label> 
				</td>
				<td align="center" nowrap="nowrap">					
		        	<input type="password" autocomplete="off" onblur="this.className='input2';"
		        	 onfocus="this.className='inputfocus';" id="idPassword" 
		        	 maxlength="40" size="20" 
		        	 name="password">		
		        </td>						
				<td align="left" nowrap="nowrap" colspan="2">
					<input type="button" 
						class="boton" 
						name="bVerificar" 
						id="bVerificar" 
						value="Verificar Contraseña"  
						onclick="return comprobarPasswordListadoMensajes();"/>			
				</td>
			</tr>
		</table>
	</s:form>	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
</s:if>

<s:if test="%{passValidado}">
		<s:form method="post" id="formData" name="formData">
			<div id="busqueda">
				<table class="tablaformbasica" border="0">
		        	<tr>
						<td align="left"  nowrap="nowrap">
							<TABLE WIDTH="100%" border="0">
								<tr>								
					        		<td align="left">
				        				<label>Fecha: </label>
				       				</td>
									<td>
										<s:textfield name="fecha.dia" 
											id="dia"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td>/</td>	
									<td>
										<s:textfield name="fecha.mes" 
											id="mes"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td>/</td>	
									<td>
										<s:textfield name="fecha.anio" 
											id="anio"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="5" maxlength="4"/>
									</td>	
									<td>
					    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anio, document.formData.mes, document.formData.dia);return false;" 
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
						<td width="80%">&nbsp;
						</td>
					</tr>
				</table>
			    					
			<table class="acciones" border="0">
				<tr>
					<td>
						<input type="button" 
							class="boton" 
							name="bObtenerExcel" 
							id="bObtenerExcel" 
							value="Obtener excel"  
							onkeypress="this.onClick" 
							onclick="return obtenerExcel();"/>			
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="return limpiarFormMensajeErrorServicio();"/>								
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bGenerarExcel" 
							id="bGenerarExcel" 
							value="Generar excel"  
							onkeypress="this.onClick" 
							onclick="return generarExcel();"/>			
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
	</s:form>
</s:if>
<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoMensajes();
	    }
	});
});
</script>