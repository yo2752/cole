<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>
<div class="contenido">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Motivo bloqueo tasa</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left">
					<label for="labelMotivo">Motivo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textarea name="motivo" id="idMotivo" onblur="this.className='input2';" 
			   			onfocus="this.className='inputfocus';" rows="5" cols="40"/>
				</td>
			</tr>
		</table>
</div>
