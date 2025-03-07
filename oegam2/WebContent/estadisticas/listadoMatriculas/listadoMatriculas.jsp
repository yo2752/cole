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
					<span class="titulo">ESTADÍSTICAS: Listado de Fecha de Primera Matriculación</span>
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
							onclick="return comprobarPasswordListadoMatriculas();"/>			
					</td>
				</tr>
			</table>
		</s:form>	
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
	</s:if>

	<s:if test="%{passValidado=='true'}">
		<s:form method="post" id="formData" name="formData">
			<div id="busqueda">
				<table class="tablaformbasica" border="0">					
					<tr>
						<td align="left" nowrap="nowrap" style="vertical-align: middle;">
							<label for="idMatricula">Matrícula (Válida para turismos):</label> 
		       			</td>   
						<td align="left" nowrap="nowrap" width="30%">
							<table>
								<tr>
									<td nowrap="nowrap" style="vertical-align: middle;">
										<s:textfield name="numMatricula" 
					        				id="idNumMatricula" onblur="this.className='input2';" 
					        				onfocus="this.className='inputfocus';" 
					        				size="4" maxlength="4"/>
				        			</td>
				        			<td nowrap="nowrap">
				        				&nbsp;-&nbsp;
				        			</td>
				        			<td nowrap="nowrap" style="vertical-align: middle;">
					        			<s:textfield name="letraMatricula" 
					        				id="idLetraMatricula" onblur="this.className='input2';" 
					        				onfocus="this.className='inputfocus';" 
					        				style="text-transform: uppercase;" 
					        				size="3" maxlength="3"/>
				        			</td>
				        		</tr>	
		        			</table>	
						</td>
						<td align="left" nowrap="nowrap" style="vertical-align: middle;">
							<input type="button" 
								class="boton" 
								name="bCalcularMatricula" 
								id="bCalcularMatricula" 
								value="Calcular Fecha"							
								onkeypress="this.onClick" 
								onclick="return calcularFechaPrimeraMatriculacion();"/>
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

		<%@include file="../../includes/erroresMasMensajes.jspf" %>
		
		<div id="bloqueLoadingConsultarEst" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>	
	</s:form>
</s:if>
	
<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoMatriculas();
	    }
	});
});
</script>	