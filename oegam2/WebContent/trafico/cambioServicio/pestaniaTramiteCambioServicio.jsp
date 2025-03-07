<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">			
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Cambio de Servicio</td>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Cambio Servicio</span>				
			</td>
		</tr>
	</table>
									
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>  
			<td align="left" nowrap="nowrap">
				<label for="labelNumExpediente">Num. Expediente:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumExpediente" name="tramiteTraficoCambServ.numExpediente"  onblur="this.className='input2';" 
			       				onfocus="this.className='inputfocus';" size="20" maxlength="20" disabled="%{flagDisabled}" readonly="true"/>
			</td>
		      	       			
			<td align="left" nowrap="nowrap">
   				<label for="refPropiaResumen">Referencia Propia: </label>
   			</td>
         	<td align="left" nowrap="nowrap">
       			<s:textfield id="refPropiaResumen" name="tramiteTraficoCambServ.refPropia" 
       				disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	       			size="20" maxlength="20"/>
   			</td>	
   			<td>
				<s:checkbox disabled="%{flagDisabled}" name="tramiteTraficoCambServ.imprPermisoCirculacion" 
					id="checkBoxPermisoCirculacion"></s:checkbox>
			</td>
			<td align="left" nowrap="nowrap">
   				<label id="checkBoxPermisoCirculacionLabel" for="checkBoxPermisoCirculacion">Impresión Permiso de Circulación</label>
   			</td>	
      	</tr>
		
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	<tr>        	       			
			<td align="left">
   				<label for="motivo">Motivos del cambio de servicio:</label>
   			</td>
       		<td align="left" nowrap="nowrap">
	   			<s:textarea name="tramiteTraficoCambServ.motivoCambioServicio" 
   					disabled="%{flagDisabled}" id="motivo" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" rows="5" cols="50"/>
			</td>
      	</tr>
	</table>
	
</div>
<style type="text/css">
	textarea {
		border-style: inset;
	  	border-color: silver;
	  	overflow: auto;
	  	outline: none;
	  	resize: none;
	}
</style>