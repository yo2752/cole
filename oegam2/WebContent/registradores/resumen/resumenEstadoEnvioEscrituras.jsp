<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">Estado:</label>
		</td>
		<td align="left" nowrap="nowrap" width="60px" colspan="3">
			<s:label value="%{@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@convertirTexto(tramiteRegistro.estado)}"/>
		</td>
		<td align="right" nowrap="nowrap" colspan="2">
			<label for="numExpediente">Nº Expediente:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="2" >
			<s:if test="%{tramiteRegistro.idTramiteRegistro != null && !(tramiteRegistro.idTramiteRegistro.equals(''))}">
				<div id="boxNumExp"><s:property value="%{tramiteRegistro.idTramiteRegistro}"/></div>
			</s:if>
			<s:else>
				<p style="color:red">NO GENERADO</p>
			</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="numExpediente">Nº Expediente Registro:</label>
		</td>
		<td align="left" nowrap="nowrap" width="60px" colspan="2">
			<s:if test="%{tramiteRegistro.idTramiteCorpme != null && !(tramiteRegistro.idTramiteCorpme.equals(''))}">
				<div id="boxNumExp"><s:property value="%{tramiteRegistro.idTramiteCorpme}"/></div>
			</s:if>
			<s:else>
				<p style="color:red">NO GENERADO</p>
			</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">Referencia propia:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="3">
			<s:textfield name="tramiteRegistro.refPropia" id="referenciaPropia" 
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxlength="50"
				disabled="true"  />
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">Tipo de operaci&oacute;n:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="5">
			<s:textfield name="tramiteRegistro.operacionDes" id="tipoOperacion" 
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="65" maxlength="65"
				disabled="true"  />
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">C&oacute;digo registro destino:</label>
		</td>
		<td align="left" nowrap="nowrap" width="15%">
			<s:textfield name="tramiteRegistro.registro.idRegistro" id="codigoRegistro" 
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="10"
				disabled="true"  />
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">Mensajes:</label>
		</td>
		<s:if test="tramiteRegistro.respuesta != null && tramiteRegistro.respuesta != ''">
			<td align="left" nowrap="nowrap" colspan="7">
				<s:textfield name="tramiteRegistro.respuesta" id="respuesta" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="90" maxlength="90"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap" colspan="7">
				<s:textfield name="respuesta" id="respuesta"  
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="90" maxlength="90"
					value="Sin mensajes" disabled="true"  />
			</td>
		</s:else>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="respuesta">Fecha &uacute;ltimo env&iacute;o:</label>
		</td>
		<s:if test="%{tramiteRegistro.fechaEnvio != null}">
			<td align="left" nowrap="nowrap">
				<s:textfield name="fechaEnvio" id="fechaEnvio" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="%{tramiteRegistro.fechaEnvio.toString()}" disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<s:textfield name="fechaEnvio" id="fechaEnvio" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="No enviado" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
		
		<td align="right" nowrap="nowrap">
			<label for="respuesta">Número de localizador:</label>
		</td>
		<s:if test="%{tramiteRegistro.localizadorReg!= null}">
			<td align="left" nowrap="nowrap">
				<s:textfield name="localizador" id="localizador" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"
					value="%{tramiteRegistro.localizadorReg}" disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<s:textfield name="localizador" id="localizador" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"
					value="Sin localizador" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="respuesta">N&uacute;mero de entrada:</label>
		</td>
		<s:if test="tramiteRegistro.numReg != null">
			<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteRegistro.numReg" id="numReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<s:textfield name="numReg" id="numReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="Sin recibir" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
		<td align="right">
			<label for="respuesta">Año:</label>
		</td>
		<s:if test="tramiteRegistro.anioReg != null && tramiteRegistro.anioReg != ''">
			<td>
				<s:textfield name="tramiteRegistro.anioReg" id="anioReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td>
				<s:textfield name="anioReg" id="anioReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"
					value="-" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
	</tr>
</table>
