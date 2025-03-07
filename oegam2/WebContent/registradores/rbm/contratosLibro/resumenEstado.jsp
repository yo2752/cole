<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table class="subtitulo" align="left" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
		<td>Tr&aacute;mite</td>
		<td align="right">
			<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
		</td>
	</tr>
</table>
<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="estado">Estado:</label>
		</td>
		<td align="left" nowrap="nowrap" width="60px" colspan="2">
			<s:label value="%{@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@convertirTexto(tramiteRegistro.estado)}"/>
		</td>
		<td align="right" nowrap="nowrap" colspan="2">
			<label for="numExpediente">Nº Expediente:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="3">
			<div id="boxNumExp"><s:property value="%{tramiteRegistro.idTramiteRegistro}"/></div>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="numExpediente">Nº Expediente Registro:</label>
		</td>
		<td align="left" nowrap="nowrap" width="60px" colspan="2">
			<div id="boxNumExp"><s:property value="%{tramiteRegistro.idTramiteCorpme}"/></div>
		</td>
		<s:if test='"S".equals(tramiteRegistro.subsanacion) || "SI".equals(tramiteRegistro.subsanacion)'>
			<td align="right" nowrap="nowrap" colspan="2">
				<label for="subsanado">Subsanado:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:textfield name="subsanado" id="subsanado"  
					onblur="this.className='input2';" size="30" maxlength="30" onfocus="this.className='inputfocus';" value="Tipo trámite de subsanación" disabled="true"  />
			</td>
		</s:if>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="cif">Referencia propia:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="2">
			<s:textfield name="tramiteRegistro.refPropia" id="referenciaPropia" 
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="30"/>
		</td>
		<td align="right" nowrap="nowrap" colspan="2">
			<label for="tipoDestinatario">Datos de facturación a nombre de<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
				<s:select label="tipo Destinatario:" name="tramiteRegistro.tipoDestinatario" id="tipoDestinatario" 
					list="#{'':'-','PRESENTADOR':'PRESENTADOR', 'SOCIEDAD':'SOCIEDAD'}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					required="true"/>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap" width="5%">
			<label for="aplicaIrpf">Aplicar IRPF:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test='tramiteRegistro.aplicarIrpf=="S"'>
				<s:textfield value="SI" id="aplicaIrpf" size="2" maxlength="2" readonly="true"  />
			</s:if>
			<s:else>
				<s:textfield  value="NO" id="aplicaIrpf" size="2" maxlength="2" readonly="true"  />
			</s:else>
		</td>

		<td align="right" nowrap="nowrap" id="labelPorcentajeIrpf">
			<label for="porcentajeIrpf">Porcentaje IRPF<span class="naranja">*</span>:</label>
		</td>
		<td>
			<s:textfield name="tramiteRegistro.porcentajeIrpf" id="porcentajeIrpf" size="14"  maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"/>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="respuesta">Mensajes:</label>
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
		<s:if test="tramiteRegistro.numReg != null && tramiteRegistro.numReg != ''">
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
		<td align="right" nowrap="nowrap">
			<label for="respuesta">Libro:</label>
		</td>
		<s:if test="tramiteRegistro.libroReg != null && tramiteRegistro.libroReg != ''">
			<td>
				<s:textfield name="tramiteRegistro.libroReg" id="libroReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td>
				<s:textfield name="libroReg" id="libroReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="-" disabled="true" cssStyle="color:red"/>
			</td>
		</s:else>
		<td>
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
		<td align="right">
			<label for="respuesta">Hora:</label>
		</td>
		<s:if test="tramiteRegistro.horaEntradaReg != null && tramiteRegistro.horaEntradaReg != ''">
			<td>
				<s:textfield name="tramiteRegistro.horaEntradaReg" id="horaEntradaReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td>
				<s:textfield name="horaEntradaReg" id="horaEntradaReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="-" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
	</tr>
	
	<s:if test='"S".equals(tramiteRegistro.subsanacion) || "SI".equals(tramiteRegistro.subsanacion)'>
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="numRegSub">N&uacute;mero de entrada subsanación:</label>
			</td>
			<s:if test="tramiteRegistro.numRegSub != null && tramiteRegistro.numRegSub != ''">
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.numRegSub" id="numRegSub" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
						disabled="true"  />
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap">
					<s:textfield name="numRegSub" id="numRegSub" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
						value="Sin recibir" disabled="true" cssStyle="color:red" />
				</td>
			</s:else>
			<td>
				<label for="libroRegSub">Libro subsanación:</label>
			</td>
			<s:if test="tramiteRegistro.libroRegSub != null && tramiteRegistro.libroRegSub != ''">
				<td>
					<s:textfield name="tramiteRegistro.libroRegSub" id="libroRegSub" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
						disabled="true"  />
				</td>
			</s:if>
			<s:else>
				<td>
					<s:textfield name="libroRegSub" id="libroRegSub" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
						value="-" disabled="true" cssStyle="color:red"/>
				</td>
			</s:else>
			<td>
				<label for="anioRegSub">Año subsanación:</label>
			</td>
			<s:if test="tramiteRegistro.anioRegSub != null && tramiteRegistro.anioRegSub != ''">
				<td>
					<s:textfield name="tramiteRegistro.anioRegSub" id="anioRegSub" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"
						disabled="true"  />
				</td>
			</s:if>
			<s:else>
				<td>
					<s:textfield name="anioRegSub" id="anioRegSub" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"
						value="-" disabled="true" cssStyle="color:red" />
				</td>
			</s:else>
		</tr>
	</s:if>
</table>
