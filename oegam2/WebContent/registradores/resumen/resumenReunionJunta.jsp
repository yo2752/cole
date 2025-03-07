<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap" width="165px">
			<label for="tipoReunion">Tipo:</label>
		</td>
		<td align="left" nowrap="nowrap" width="130px">
			<s:if test="tramiteRegistro.reunion != null">
				<s:textfield name="tramiteRegistro.reunion.tipoReunion" id="tipoJunta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="18" maxlength="18" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="tipoJunta" id="tipoJunta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="18" maxlength="18"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap" width="2%">
			<label for="caracter">Car&aacute;cter:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="2">
			<s:if test="tramiteRegistro.reunion == null">
				<s:textfield name="caracterRes" id="caracterRes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15"
					disabled="true" cssStyle="color:red" value="No requerido"/>
			</s:if>
			<s:elseif test="tramiteRegistro.reunion.caracter == 'noSeleccionado' || tramiteRegistro.reunion.caracter == '' || tramiteRegistro.reunion.caracter == ' '">
				<s:textfield name="caracterRes" id="caracterRes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15"
					disabled="true" cssStyle="color:red" value="No requerido"/>
			</s:elseif>
			<s:else>
				<s:textfield name="tramiteRegistro.reunion.caracter" id="caracterRes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="15" maxlength="15" disabled="true"/>
			</s:else>
		</td>
	</tr>
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
