<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Información del Error</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelCodError">Código Error: </label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
	     		<s:textfield name="consultaEEFF.resultadoConsultaEEFF.codigoError" id="idCodigoErrorEEFF" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
	    	</td>
	    	 <td align="left" nowrap="nowrap">
       			<label for="labelDescError">Descripción del Error: </label>
       		</td>
         	<td align="left" nowrap="nowrap">
	         	<s:textarea readonly="true" name="consultaEEFF.resultadoConsultaEEFF.descripcionError" id="idDescripcionErrorEEFF" 
		   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" rows="8" cols="50"/>
       		</td>
		</tr>
	</table>
</div>
