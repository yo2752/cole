<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="tramiteRegRbmDto.situacionJuridicaCancelacion" />

<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos desistimiento</span>
			</td>
		</tr>
	</table>	  

	<table class="tablaformbasica" >
 		<tr>
		   <td align="left" nowrap="nowrap"><label for="idTramiteRegistro">N&uacute;m. Expediente</label></td>
			<td> <s:textfield name="tramiteRegRbmDto.idTramiteRegistro" id="idTramiteRegistro" size="16" readonly="true" onfocus="this.blur();"></s:textfield></td>
	   		<td align="left" nowrap="nowrap"><label for="referenciaPropiaId" title="Referencia propia de cancelaci&oacute;n">Referencia propia</label></td>
			<td><s:textfield name="tramiteRegRbmDto.refPropia" id="refPropia" size="16" maxlength="50"></s:textfield></td>
		</tr> 

		<tr>
			<td align="left" nowrap="nowrap"><label for="registryOfficeId">Registro<span class="naranja">*</span></label></td>
			<td colspan="2">
				<s:select name="tramiteRegRbmDto.idRegistro" 
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroBienesMuebles()" 
					listKey="id"
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar registro"
					id="tramiteRegRbmDto.RegistryOfficeId"/>
			</td>
		</tr>

		<tr>
		  <td align="left" nowrap="nowrap" width="18%"><label for="fechaEntrada">Fecha entrada</label></td>
		  <td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fecEntradaOrigenDesistimim.dia" id="diaFecEntrada"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecEntrada'), document.getElementById('mesFecEntrada'), document.getElementById('anioFecEntrada'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fecEntradaOrigenDesistimim.mes" id="mesFecEntrada" 	onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecEntrada'), document.getElementById('mesFecEntrada'), document.getElementById('anioFecEntrada'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fecEntradaOrigenDesistimim.anio" id="anioFecEntrada"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diaFecEntrada'), document.getElementById('mesFecEntrada'), document.getElementById('anioFecEntrada'));"/>
						</td>
						
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('anioFecEntrada'), document.getElementById('mesFecEntrada'), document.getElementById('diaFecEntrada'));return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="left" nowrap="nowrap"><label for="numEntradaOrigen">N&uacute;mero entrada<span class="naranja">*</span></label></td>
			<td align="left"><s:textfield name="tramiteRegRbmDto.numEntradaOrigen" id="numEntradaOrigen" size="16" maxlength="50" onkeypress="return soloNumeroDecimal(event, this, '15', '0')"></s:textfield></td>
		</tr>
		
		<tr>
		  <td align="left" nowrap="nowrap" width="18%"><label for="fechaPresentacion">Fecha presentaci&oacute;n</label></td>
		  <td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fecPresentacionOrigenDesistimim.dia" id="diaFecPresentacion"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecPresentacion'), document.getElementById('mesFecPresentacion'), document.getElementById('anioFecPresentacion'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fecPresentacionOrigenDesistimim.mes" id="mesFecPresentacion"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecPresentacion'), document.getElementById('mesFecPresentacion'), document.getElementById('anioFecPresentacion'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fecPresentacionOrigenDesistimim.anio" id="anioFecPresentacion"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diaFecPresentacion'), document.getElementById('mesFecPresentacion'), document.getElementById('anioFecPresentacion'));"/>
						</td>
						
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('anioFecPresentacion'), document.getElementById('mesFecPresentacion'), document.getElementById('diaFecPresentacion'));return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap"><label for="numPresentacionOrigen">N&uacute;mero presentaci&oacute;n<span class="naranja">*</span></label></td>
			<td align="left"><s:textfield name="tramiteRegRbmDto.numPresentacionOrigen" id="numPresentacionOrigen" size="16" maxlength="50" onkeypress="return soloNumeroDecimal(event, this, '15', '0')"></s:textfield></td>
		</tr>
		
	</table>
	
</div>