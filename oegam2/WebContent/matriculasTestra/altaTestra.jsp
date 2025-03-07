<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/testra.js" type="text/javascript"></script>


 <%@include file="../../includes/erroresYMensajes.jspf" %>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Alta Datos Testra
			</span></td>
		</tr>
	</table>
</div>


<s:form method="post" id="formData" name="formData">

	<s:hidden name="consultaTestra.id"/>
	<s:hidden name="consultaTestra.activo"/>
	<s:hidden name="vieneDeResumen" id="idVieneDeResumen"/>

	<div id="contenido">					
		<table class="subtitulo">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Alta Datos Testra</td>
			</tr>
		</table>
		<table class="tablaformbasica center" align="left" cellpadding="0" cellspacing="3">
			<s:if test="consultaTestra != null && consultaTestra.id != null &&  consultaTestra.activo != null" >	
				<tr>        	       			
					<td align="left" nowrap="nowrap">
			   			<label for="labelRestado">Estado: </label>
			   		</td>
		         	<td align="left" nowrap="nowrap">
		   				<s:label value="%{@org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo@convertirTexto(consultaTestra.activo)}"/>
		   			</td>	
	       		</tr><tr></tr>   	       		
	       	</s:if>       		
			<tr>    		
				<td align="left"><label for="tipo">Tipo de dato:</label></td>
				<td>
					<s:select name="consultaTestra.tipo" id="tipo"
						list="@org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoConsultaTestraEnum@values()"
						listKey="clave" listValue="descripcion"
						headerKey="" headerValue="Tipo de dato"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onchange="changeTipo()" />
				</td>
	       		<td align="left" nowrap="nowrap" >
        			<label id="labelDato" for="dato">Dato: </label>
      			</td>
   				<td align="left">
   					
   						<s:textfield name="consultaTestra.dato" 
		       					id="dato" 
		       					onblur="this.className='input2';" 
		       					onfocus="this.className='inputfocus';" 
		       					size="8" maxlength="9"
		       					/>				
   					

   				</td>
   				<td  align="left">
					<s:text name="Correo electrónico: "></s:text>
					</td>
					<td>
						<s:textfield id="correoElectronico" name="consultaTestra.correoElectronico" label="Correo electronico" size="30px"   />
					</td>
   				<s:if test="%{#isAdmin==true}">
   					<td align="left">
					<s:text name="Num Colegiado: "></s:text>
					</td>
					<td align="left">
						<s:textfield id="numColegiado" name="consultaTestra.numColegiado" label="Numero colegiado" size="7px" maxlength="4"  />
					</td>
   				</s:if>
	        </tr>
		</table>
		
		<table class="acciones center">		
			<tr>
				<td><input type="button" class="botonMasGrande" name="bGuardarDatoTestra" id="idGuardarDatoTestra" value="Guardar" onClick="guardarDatoTestra();" onKeyPress="this.onClick" /></td>
				<td><input type="button" class="botonMasGrande" name="bLimpiar" id="idGuardarDatoTestra" value="Limpiar" onclick="return limpiarFormularioAltaTestra();"/></td>
													
				<s:if test="consultaTestra != null && consultaTestra.id != null &&  consultaTestra.activo == 0"  >						
					<td><input type="button" value="Activar" class="boton" onkeypress="this.onClick" onclick="activar();" onKeyPress="this.onClick"   /></td>
				</s:if>				
				<s:if test="consultaTestra != null && consultaTestra.id != null &&  consultaTestra.activo == 1"  >			
					<td><input type="button" value="Desactivar" class="boton" onkeypress="this.onClick" onclick="desactivar();"  onKeyPress="this.onClick"  />	</td>
				</s:if>		
				<s:if test="vieneDeResumen">
					<td><input type="button" class="boton" name="bVolverT" id="idVolverT" value="Volver" onclick="volverConsultaTestra();" onkeypress="this.onClick"/></td>
			</s:if>
			</tr>
		</table>
		&nbsp;			
		
	 	<div id="bloqueLoadingMatriculasTestra" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>    
	</div>
	<b><font size="2">(La consulta sobre el sistema TESTRA se realizará el día siguiente, e informará con un correo electronico si se encuentran notificaciones)</font></b>

</s:form>