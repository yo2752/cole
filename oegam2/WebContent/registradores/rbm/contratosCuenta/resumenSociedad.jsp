<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="nav">
	<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
		<tr>
			<td class="tabular" style="">
				<span class="titulo">Sociedad</span>
			</td>
		</tr>
	</table>
</div>
	
<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="cif">CIF:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.nif != null && tramiteRegistro.sociedad.nif != ''">
				<s:textfield name="tramiteRegistro.sociedad.nif" id="resumenCif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="9" readonly="true"/>
			</s:if>
			<s:else>
				<s:textfield name="cif" id="resumenCif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="9"
					value="REQUERIDO" readonly="true" cssStyle="color:red"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="denominacionSocial">Denominaci&oacute;n social:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="6">
			<s:if test="tramiteRegistro.sociedad.apellido1RazonSocial != null && tramiteRegistro.sociedad.apellido1RazonSocial != ''">
				<s:textfield name="tramiteRegistro.sociedad.apellido1RazonSocial" id="resumenDenominacionSocial" 
					onblur="this.className='input2';" readonly="true"
					onfocus="this.className='inputfocus';" size="40" maxlength="40"/>
			</s:if>
			<s:else>
				<s:textfield name="denominacionSocial" id="resumenDenominacionSocial" 
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="40" maxlength="40"
					value="REQUERIDO" readonly="true" cssStyle="color:red"/>
			</s:else>		
		</td>
	</tr>
</table>
