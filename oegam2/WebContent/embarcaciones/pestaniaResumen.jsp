<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">					
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen formulario embarcaciones</td>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LA EMBARCACI&Oacute;N</span>
			</td>
		</tr>
	</table>
		<table class="tablaformbasica" border="0">
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
		<tr>
     		<td nowrap="nowrap" style="vertical-align: middle;" width="30px" align="left">
				<label >Hin: </label>
			</td>		    				    				
     		<td align="left" style="vertical-align: middle;">
      			<s:label name="embarcacion.hin" />
     		</td>	        		
		</tr>
		<tr>
     		<td nowrap="nowrap" style="vertical-align: middle;" width="30px" align="left">
				<label>Metros de eslora: </label>
			</td>		    				    				
     		<td align="left" style="vertical-align: middle;">
      			<s:label name="embarcacion.metrosEslora" />
     		</td>	        		
		</tr>
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DEL TITULAR</span>
			</td>
		</tr>
	</table>
	<table class="tablaformbasica" border="0">
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="30px">
				<label>NIF / CIF: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.titular.nif" />
       	    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="30px">
				<label>Primer Apellido / Razón Social: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.titular.apellido1RazonSocial" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="30px">
				<label>Segundo Apellido: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.titular.apellido2" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="30px">
				<label>Nombre: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.titular.nombre" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
	</table>
	<s:if test="%{embarcacion.titular.tipoPersona == 'JURIDICA'}">   	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DEL REPRESENTANTE</span>
			</td>
		</tr>
	</table>
	<table class="tablaformbasica" border="0">
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="30px">
				<label>NIF / CIF: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.representante.nif" />
       	    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label>Primer Apellido / Razón Social: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.representante.apellido1RazonSocial" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label>Segundo Apellido: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.representante.apellido2" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label>Nombre: </label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:label name="embarcacion.representante.nombre" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
	</table>
	</s:if>
	<table class="acciones">
		<tr>
			<td>
				<input type="button" 
					class="boton"
				 	name="bGenerarPDF"
				  	id="idBotonGenerarPDF" 
				  	value="Generar PDF" 
				  	onClick="return generarPDF();"
					onKeyPress="this.onClick"/>		
				&nbsp;
				<input type="button" 
					class="boton"
				 	name="bGenerarTXT"
				  	id="idBotonGenerarTXT" 
				  	value="Generar TXT" 
				  	onClick="return generarTXT();"
					onKeyPress="this.onClick"/>		
				&nbsp;
				<input type="button" 
					class="boton" 
					name="bLimpiar" 
					id="idBotonLimpiarMarcaDgt" 
					value="Limpiar"  
					onkeypress="this.onClick" 
					onclick="return limpiarFormularioEmbarcaciones();"/>			
			</td>
		</tr>
	</table>
	<s:hidden id="numExpediente" name="numExpediente"/>

</div>		    
  
