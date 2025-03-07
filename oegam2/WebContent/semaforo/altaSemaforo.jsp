<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/semaforo/gestionSemaforos.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Semáforo</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresMasMensajes.jspf" %>
		<div class="contenido">	
			<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Datos Alta Semáforo</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelProcesoSemaforo">Proceso<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap" width="92%">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getListaProcesos()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Proceso" 
					    		name="semaforo.proceso" listKey="valorEnum" listValue="nombreEnum" id="idProceso"/>
			    	</td>	       			
		      	</tr>
		      	<tr>        	       			
		   			<td align="left" nowrap="nowrap">
		   				<label for="labelEstadoSemaforo">Estado<span class="naranja">*</span>:</label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosSemaf()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
					    		name="semaforo.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
		   			</td>
		      	</tr>
		     </table>
		</div>
		<div class="acciones center">
	 		<input type="button" class="boton" name="bAltaSemaforo" id="idAltaSemaforo" value="Alta" onClick="javascript:nuevoSemaforo();"
	 			onKeyPress="this.onClick"/>
	 		<input type="button" class="boton" name="bLimpiarPoder" id="idLimpiarPoder" value="Limpiar" onClick="javascript:limpiarSemaforo();"
	 			onKeyPress="this.onClick"/>
		</div>
	</s:form>
</div>
