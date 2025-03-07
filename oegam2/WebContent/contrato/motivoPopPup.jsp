<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>
<div class="contenido">
	<s:if test="accion.equlas('habilitar')">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Motivo Habilitar Contratos</span>
				</td>
			</tr>
		</table>
	</s:if>
	<s:elseif test="accion.equlas('deshabilitar')">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Motivo Deshabilitar Contratos</span>
				</td>
			</tr>
		</table>
	</s:elseif>
	<s:elseif test="accion.equlas('eliminar')">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Motivo Eliminar Contratos</span>
				</td>
			</tr>
		</table>
	</s:elseif>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" width="150px">
				<label for="labelMotivo">Motivo:</label>
			</td>
			<td align="left"  nowrap="nowrap">
				<s:textarea name="motivo" id="idMotivo" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';"  rows="5" cols="50"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSolicitante">Solicitante:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="solicitante" id="idSolicitante" size="30" maxlength="50" 
						onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
	</table>
</div>
