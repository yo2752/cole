<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">					
	<table class="subtitulo" cellSpacing="0" cellspacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la embarcaci&oacute;n</td>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">EMBARCACI&Oacute;N</span>
			</td>
		</tr>
	</table>	
	
	<table class="tablaformbasica" border="0">
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
		<tr>
     		<td nowrap="nowrap" style="vertical-align: middle;" width="30px" align="right">
				<label for="him">Hin:</label>
			</td>		    				    				
     		<td align="left" style="vertical-align: middle;" colspan="3">
      			<s:textfield name="embarcacion.hin" 
      				id="hin" size="50" maxlength="22" 
      				onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
     		</td>	        		
		</tr>
		<tr>
     		<td nowrap="nowrap" style="vertical-align: middle;" width="30px" align="right">
				<label for="fabricante">Metros de eslora:</label>
			</td>		    				    				
     		<td align="left" style="vertical-align: middle;" colspan="3">
      			<s:textfield name="embarcacion.metrosEslora" 
      				id="metrosEslora" size="10" maxlength="4" 
      				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
      				onkeydown="cambiarComa(this);" onkeyup="cambiarComa(this);" onkeypress="return soloNumeros(event)"/>
     		</td>	        		
		</tr>
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
	</table>
    	
</div>		    
 

