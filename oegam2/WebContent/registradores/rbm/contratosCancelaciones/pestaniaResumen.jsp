<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Tr&aacute;mite</span>
			</td>
		</tr>
	</table>	 
	<table border="0" class="tablaformbasicaTC">
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Estado:</label>
			</td>
			<td align="left" nowrap="nowrap" width="60px" colspan="2">
				<s:label value="%{@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@convertirTexto(tramiteRegRbmDto.estado)}"/>
			</td>
			<td align="right" nowrap="nowrap" colspan="2">
				<label for="numExpediente">Nº Expediente:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="2" >
				<s:if test="%{tramiteRegRbmDto.idTramiteRegistro != null && !(tramiteRegRbmDto.idTramiteRegistro.equals(''))}">
					<div id="boxNumExp"><s:property value="%{tramiteRegRbmDto.idTramiteRegistro}"/></div>
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
				<s:if test="%{tramiteRegRbmDto.idTramiteCorpme != null && !(tramiteRegRbmDto.idTramiteCorpme.equals(''))}">
					<div id="boxNumExp"><s:property value="%{tramiteRegRbmDto.idTramiteCorpme}"/></div>
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
				<s:textfield name="tramiteRegRbmDto.refPropia" id="referenciaPropia" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxlength="50"
					disabled="true"  />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Tipo de operaci&oacute;n:</label>
			</td>
			
			<td align="left" nowrap="nowrap" colspan="5">
				<s:textfield value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.TipoOperacion@convertirTexto(#attr.tramiteRegRbmDto.tipoOperacion)}" id="tipoOperacion" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="65" maxlength="65"
					disabled="true"  />
			</td>
		</tr>
	
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Situaci&oacute;n jur&iacute;dica:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="5">
				<s:textfield value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.SituacionJuridica@convertirTexto(#attr.tramiteRegRbmDto.situacionJuridicaCancelacion)}" id="tipoOperacion" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="60"
					disabled="true"  />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Causas cancelaci&oacute;n:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="5">
				<s:textfield value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.CausaCancelacion@convertirTexto(#attr.tramiteRegRbmDto.causaCancelacion)}" id="tipoOperacion" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="60"
					disabled="true"  />
			</td>
		</tr>
	
	
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">C&oacute;digo registro destino:</label>
			</td>
			<td align="left" nowrap="nowrap" width="15%">
				<s:textfield name="tramiteRegRbmDto.registro.idRegistro" id="codigoRegistro" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="10"
					disabled="true"  />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="cif">Mensajes:</label>
			</td>
			<s:if test="tramiteRegRbmDto.respuesta != null && tramiteRegRbmDto.respuesta != ''">
				<td align="left" nowrap="nowrap" colspan="7">
					<s:textfield name="tramiteRegRbmDto.respuesta" id="respuesta" 
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
			<s:if test="%{tramiteRegRbmDto.fechaEnvio != null}">
				<td align="left" nowrap="nowrap">
					<s:textfield name="fechaEnvio" id="fechaEnvio" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
						value="%{tramiteRegRbmDto.fechaEnvio.toString()}" disabled="true"  />
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
			<s:if test="%{tramiteRegRbmDto.localizadorReg!= null}">
				<td align="left" nowrap="nowrap">
					<s:textfield name="localizador" id="localizador" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"
						value="%{tramiteRegRbmDto.localizadorReg}" disabled="true"  />
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
			<s:if test="tramiteRegRbmDto.numReg != null">
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegRbmDto.numReg" id="numReg" 
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
				<label for="respuesta">Hora:</label>
			</td>
			<s:if test="tramiteRegRbmDto.horaEntradaReg != null && tramiteRegRbmDto.horaEntradaReg != ''">
				<td>
					<s:textfield name="tramiteRegRbmDto.horaEntradaReg" id="horaEntradaReg" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="10"
						disabled="true"  />
				</td>
			</s:if>
			<s:else>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegRbmDto.horaEntradaReg" id="horaEntradaReg" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"
						value="Sin hora" disabled="true" cssStyle="color:red" />
				</td>
			</s:else>
		</tr>
		<s:if test='"S".equals(tramiteRegRbmDto.subsanacion) || "SI".equals(tramiteRegRbmDto.subsanacion)'>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="numRegSub">N&uacute;mero de entrada subsanación:</label>
				</td>
				<s:if test="tramiteRegRbmDto.numRegSub != null && tramiteRegRbmDto.numRegSub != ''">
					<td align="left" nowrap="nowrap">
						<s:textfield name="tramiteRegRbmDto.numRegSub" id="numRegSub" 
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
			</tr>
		</s:if>
	</table>
	
	<s:if test="tramiteRegRbmDto.formaPago == 2">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular" style="">
					<span class="titulo">Forma de pago por transferencia</span></td>
			</tr>
		</table>
	</s:if>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular" style="">
				<span class="titulo">Identificaci&oacute;n Corpme (código usuario abonado o usuario + contraseña)</span></td>
		</tr>
	</table>
	
	<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
		<tr>
			<td align="left" nowrap="nowrap" width="20%" style="vertical-align: middle;">
				<label for="ncorpmeCliente">Código usuario abonado<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:textfield value="%{tramiteRegRbmDto.presentador.ncorpme}" id="codigoCorpme" size="10" maxlength="10" 
	       			readonly="true" />
			</td>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="ncorpmeClientePass">Usuario / Contraseña<span class="naranja">*</span>:</label>
			</td>
			<td align="right" nowrap="nowrap">
	   			<s:textfield value="%{tramiteRegRbmDto.presentador.usuarioCorpme}" id="usuarioCorpme" size="15" maxlength="25" 
	   				readonly="true" />
			</td>
			<td>/</td>
			<td>
	  			<s:password value="%{tramiteRegRbmDto.presentador.passwordCorpme}" id="passwordCorpme" size="15" maxlength="25" 
	      			 readonly="true" showPassword="true"/>
			</td>
		</tr>		
	</table>


	<!-- Resumen si el bien seleccionado es un vehículo -->
	<s:if test="tramiteRegRbmDto.propiedadDto.categoria != null && tramiteRegRbmDto.propiedadDto.categoria == '01'">	
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular" style="">
					<span class="titulo">Bien cancelado:</span>
				</td>
			</tr>
		</table>
		 <table border="0" class="tablaformbasicaTC">
			<tr>
				<s:if test="tramiteRegRbmDto.propiedadDto.vehiculo.matricula != null && tramiteRegRbmDto.propiedadDto.vehiculo.matricula != '' 
					&& tramiteRegRbmDto.propiedadDto.vehiculo.bastidor != null && tramiteRegRbmDto.propiedadDto.vehiculo.bastidor != ''">
					<td align="right">
						<display:table name="tramiteRegRbmDto.propiedadDto" excludedParams="*" 	class="tablacoin" uid="propiedad" summary="Vehículo" style="width:98%"
							cellspacing="0" cellpadding="0">
								<display:column title="Categoría"  style="width:13%;text-align:left;" sortable="false">
									<s:property value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.TipoCategoria@convertirTexto(#attr.propiedad.categoria)}" />
								</display:column>
								<display:column property="vehiculo.matricula" title="Matrícula" style="width:13%;text-align:left;" sortable="false"/>
								<display:column property="vehiculo.bastidor" title="Bastidor" style="width:13%;text-align:left;" sortable="false"/>
								<display:column property="vehiculo.tipo" title="Clase" style="width:13%;text-align:left;" sortable="false"/>
								<display:column property="vehiculo.marca" title="Marca" style="width:13%;text-align:left;" sortable="false"/>
								<display:column property="vehiculo.modelo" title="Modelo" style="width:13%;text-align:left;" sortable="false"/>
								
						</display:table>
					</td>
				</s:if>
		 		
				<s:else>
					<td align="left" nowrap="nowrap" >
						<label for="vehiculo" style="color:red;font-size:12px">
							Sin informar
						</label>
					</td>
				</s:else> 
			</tr>		
		</table> 
	</s:if>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular" style="">
				<span class="titulo">Solicitante:</span>
			</td>
		</tr>
	</table>
	 <table border="0" class="tablaformbasicaTC">
		<tr>
			<s:if test="tramiteRegRbmDto.solicitante.nif != null && tramiteRegRbmDto.solicitante.nif != ''">
				<td align="right">
					<display:table name="tramiteRegRbmDto.solicitante" excludedParams="*" 	class="tablacoin" uid="solicitante" summary="Solicitante" style="width:98%"
						cellspacing="0" cellpadding="0">
							<display:column title="Tipo de persona"  style="width:13%;text-align:left;" sortable="false">
								<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro@convertirTextoDesdeXML(#attr.solicitante.tipoPersona)}" />
							</display:column>
							<display:column property="nif" title="NIF" style="width:13%;text-align:left;" sortable="false"/>
							<display:column property="persona.nombre" title="Nombre" style="width:13%;text-align:left;" sortable="false"/>
							<display:column property="persona.apellido1RazonSocial" title="1er apellido/Razón social" style="width:15%;text-align:left;" sortable="false"/>
							<display:column property="persona.apellido2" title="2do apellido" style="width:13%;text-align:left;" sortable="false"/>
					</display:table>
				</td>
			</s:if>
	 		
			<s:else>
				<td align="left" nowrap="nowrap" >
					<label for="solicitante" style="color:red;font-size:12px">
						Sin solicitante
					</label>
				</td>
			</s:else> 
		</tr>		
	</table> 
</div>
