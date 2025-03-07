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
			<td align="right" nowrap="nowrap" width="165px">
				<label for="fecha">Orden convocatoria:</label>
			</td>
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.convocatoria != null && tramiteRegistro.reunion.convocatoria.numeroConvo != null && tramiteRegistro.reunion.convocatoria.numeroConvo != ''">
				<td align="left" nowrap="nowrap" width="100px">
					<s:textfield name="tramiteRegistro.reunion.convocatoria.numeroConvo" id="ordenConvocatoria" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="12" maxlength="12" disabled="true"/>
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap" width="100px">
					<s:textfield name="ordenConvocatoria" id="ordenConvocatoria" onblur="this.className='input2';"
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
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="porcentajeCapitalAsistente">Capital asistente(%):</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.convocatoria != null && tramiteRegistro.reunion.convocatoria.porcentajeCapital != null && tramiteRegistro.reunion.convocatoria.porcentajeCapital != ''">
				<s:textfield name="tramiteRegistro.reunion.convocatoria.porcentajeCapital" id="porcentajeCapitalAsistente" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="porcentajeCapitalAsistente" id="porcentajeCapitalAsistente" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="porcentajeCapitalAcuerdo">Capital acuerdo(%):</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.porcentajeCapital != null && tramiteRegistro.reunion.porcentajeCapital != ''">
				<s:textfield name="tramiteRegistro.reunion.porcentajeCapital" id="porcentajeCapitalAcuerdo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true" 
					value="%{tramiteRegistro.juntaAccionistas.porcentajeCapitalAcuerdo}" />
			</s:if>
			<s:else>
				<s:textfield name="porcentajeCapitalAcuerdo" id="porcentajeCapitalAcuerdo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="porcentajeSociosAsistentes">Socios asistentes(%):</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.convocatoria != null && tramiteRegistro.reunion.convocatoria.porcentajeSocios != null && tramiteRegistro.reunion.convocatoria.porcentajeSocios != ''">
				<s:textfield name="tramiteRegistro.reunion.convocatoria.porcentajeSocios" id="porcentajeSociosAsistentes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="porcentajeSociosAsistentes" id="porcentajeSociosAsistentes" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="porcentajeSociosAcuerdo">Socios acuerdo(%):</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.porcentajeSocios != null && tramiteRegistro.reunion.porcentajeSocios != ''">
				<s:textfield name="tramiteRegistro.reunion.porcentajeSocios" id="porcentajeSociosAcuerdo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="porcentajeSociosAcuerdo" id="porcentajeSociosAcuerdo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="fechaAprobacionActa">Fecha aprobaci&oacute;n acta:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.fechaAproActa != null">
				<s:textfield name="tramiteRegistro.reunion.fechaAproActa" id="fechaAprobacionActa" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="fechaAprobacionActa" id="fechaAprobacionActa" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="formaAprobacionActa">Forma aprobaci&oacute;n acta:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.reunion != null && tramiteRegistro.reunion.formaAproActa != null && tramiteRegistro.reunion.formaAproActa != ''">
				<s:textfield name="tramiteRegistro.reunion.formaAproActa" id="formaAprobacionActa" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="formaAprobacionActa" id="formaAprobacionActa" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true" cssStyle="color:red" value="REQUERIDO"/>
			</s:else>
		</td>
	</tr>
</table>
