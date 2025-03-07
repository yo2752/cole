<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap" width="165px">
			<label for="fecha">Fecha:</label>
		</td>
		<td width="120px">
			<s:if test="tramiteRegistro.fechaCertif != null">
				<s:textfield name="fechaCertif" id="fechaCertif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"
					value="%{tramiteRegistro.fechaCertif.toString()}"/>
			</s:if>
			<s:else>
					<s:textfield name="fechaCertif" id="fechaCertif" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="10" maxlength="10"
						disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap" width="2%">
			<label for="lugarCertif">Lugar:</label>
		</td>
		<s:if test="tramiteRegistro.lugar != null && tramiteRegistro.lugar != ''">
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield name="tramiteRegistro.lugar" id="lugarCertif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="35" maxlength="30" disabled="true" />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap" colspan="2">
				<s:textfield name="lugar" id="lugarCertif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="35" maxlength="30" value="REQUERIDO" 
					disabled="true" cssStyle="color:red"/>
			</td>
		</s:else>	
	</tr>	
</table>
