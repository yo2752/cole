<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<tr>
		<td align="right" nowrap="nowrap" width="165px">
			<label for="fecha">Fecha:</label>
		</td>
		<s:if test="tramiteRegistro.reunion != null">
			<td align="left" nowrap="nowrap" width="100px">
				<s:textfield name="fechaJunta" id="fechaJunta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="12" maxlength="12" disabled="true"
					value="%{tramiteRegistro.reunion.fecha.toString()}"/>
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap" width="100px">
				<s:textfield name="fechaJunta" id="fechaJunta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="12" maxlength="12"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</td>
		</s:else>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="lugarReunion">Lugar:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="3">
			<s:if test="tramiteRegistro.reunion != null">
				<s:textfield name="tramiteRegistro.reunion.lugar" id="lugarJunta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="60" maxlength="60" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="lugarJunta" id="lugarJunta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="60" maxlength="60"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
	</tr>
</table>
