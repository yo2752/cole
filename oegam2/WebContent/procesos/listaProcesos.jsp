<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/procesos/procesos.js" type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">LISTADO DE PROCESOS</span>
				</td>
			</tr>
		</table>
	</div>
	
<s:if test="#isLogged">
<c:out value="${isLogged}"/>
	<s:form method="post" id="formData" name="formData">
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="idPassword">Introduzca el usuario:</label> 
				</td>
				<td align="center" nowrap="nowrap">					
		        	<input type="text" autocomplete="off" onblur="this.className='input2';"
		        	 onfocus="this.className='inputfocus';" id="idUsuario" 
		        	 value="" maxlength="40" size="20" 
		        	 name="usuario">		
		        </td>
		        <td align="right" nowrap="nowrap">
					<label for="idPassword">Introduzca la clave:</label> 
				</td>
				<td align="center" nowrap="nowrap">					
		        	<input type="password" autocomplete="off" onblur="this.className='input2';"
		        	 onfocus="this.className='inputfocus';" id="idPassword" 
		        	 value="" maxlength="40" size="20" 
		        	 name="password">		
		        </td>								
				<td align="center" nowrap="nowrap" colspan="2">
					<input type="button" 
						class="boton" 
						name="bVerificar" 
						id="bVerificar" 
						value="Verificar Contraseña"  
						onkeypress="this.onClick" 
						onclick="return comprobarPassword();"/>			
				</td>
			</tr>
		</table>
	</s:form>	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
</s:if>

<s:else>
	<div>

	
		<ol id="toc" >
			<li id="maquina1"><a href="#maquina1">Procesos 1</a></li>		
			<li id="maquina2"><a href="#maquina2">Procesos 2</a></li>
	
		</ol>
		
		<s:form method="post" id="formData" name="formData">
	
			<table class="subtitulo" cellSpacing="0" align="left">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Procesos</td>
				</tr>
			</table>
			<%@include file="../../includes/erroresMasMensajes.jspf" %>
			
			<div class="contentTabs" id="maquina1" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			</div>
			
			<div class="contentTabs" id="maquina2" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			</div>
			
			&nbsp;
			<div class="acciones center">
			 	<input type="button" class="boton" name="bArrancar" id="idArrancar" value="Arrancar" onClick="javascript:arrancar();"
			 		onKeyPress="this.onClick"/>
			 	<input type="button" class="boton" name="bParar" id="idParar" value="Parar" onClick="javascript:parar();"
			 		onKeyPress="this.onClick"/>	
			</div>
		</s:form>
	</div>

</s:else>
