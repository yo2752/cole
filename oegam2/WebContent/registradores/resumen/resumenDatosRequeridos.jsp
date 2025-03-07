<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap" width="23%" style="vertical-align: middle;">
			<label for="datosEscritura">Datos escritura:</label>
		</td>
		<td>
		<s:if test="tramiteRegistro.cifTitularCuenta != null && !''.equals(tramiteRegistro.cifTitularCuenta)">
			<s:textfield name="datosEscritura" id="datosEscritura" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="15" maxlength="15" disabled="true"
				cssStyle="color:red" value="CORRECTO"/>
		</s:if>
		<s:else>
			<s:textfield name="datosEscritura" id="datosEscritura" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="15" maxlength="15"
				disabled="true" cssStyle="color:red" value="INCOMPLETOS"/>
		</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap" width="23%" style="vertical-align: middle;">
			<label for="destinatarioRes">Datos cliente/destinatario:</label>
		</td>
		<s:if test="tramiteRegistro.sociedad != null && tramiteRegistro.sociedad.nif != null && !''.equals(tramiteRegistro.sociedad.nif)">
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield name="destinatarioRes" id="destinatarioRes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15"
					value="CORRECTO" disabled="true" cssStyle="color:red" />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield name="destinatarioRes" id="destinatarioRes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15" value="INCOMPLETOS" 
					disabled="true" cssStyle="color:red"/>
			</td>
		</s:else>	
	</tr>	
</table>
