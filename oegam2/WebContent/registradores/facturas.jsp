<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

	<s:hidden name="tramiteRegistro.facturaRegistro.idFactura"/>	

   	<div id = "divFactura" >
   	
   		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos factura</span>
				</td>
			</tr>
		</table>
		
		<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
			<tr>
	 			<td  colspan="2" align="left" nowrap="nowrap"><label for="fechaPago">Fecha de pago:<span class="naranja">*</span></label></td>
		  					<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegistro.facturaRegistro.fechaPagoFacturaRegistro.dia" id="diafechaPago"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diafechaPago'), document.getElementById('mesfechaPago'), document.getElementById('aniofechaPago'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.facturaRegistro.fechaPagoFacturaRegistro.mes" id="mesfechaPago"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diafechaPago'), document.getElementById('mesfechaPago'), document.getElementById('aniofechaPago'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.facturaRegistro.fechaPagoFacturaRegistro.anio" id="aniofechaPago"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diafechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('aniofechaDocumento'));"/>
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.aniofechaPago, document.formData.mesfechaPago, document.formData.diafechaPago);  return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
				
				<td align="left" nowrap="nowrap"><label for="identificadorTransferencia" class="small">Identificador de la transferencia:<span class="naranja">*</span></label></td>
				<td>
					<s:textfield name="tramiteRegistro.facturaRegistro.idTransferencia" id="idTransferencia" size="18" maxlength="255" ></s:textfield>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="2">
					<label for="cifEntidad">CIF de la entidad:<span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.facturaRegistro.cifEmisor" id="cifEmisor" size="9" maxlength="9" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
				
				<td align="left" nowrap="nowrap"><label for="serieFactura" class="small">Serie de la factura:<span class="naranja">*</span></label></td>
				<td>
					<s:textfield name="tramiteRegistro.facturaRegistro.numSerie" id="serieFactura" size="18" maxlength="255" ></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td align="left" nowrap="nowrap" colspan="2">
					<label for="ejercicioFactura">Ejercicio de la factura:<span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.facturaRegistro.ejercicio" id="ejercicioCuenta" size="18" maxlength="255" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
				
				<td align="left" nowrap="nowrap"><label for="numFactura" class="small">N&uacute;mero de la factura:<span class="naranja">*</span></label></td>
				<td>
					<s:textfield name="tramiteRegistro.facturaRegistro.numFactura" id="numFactura" size="18" maxlength="255" ></s:textfield>
				</td>
			</tr>
 			<tr>
	 			<td  colspan="2" align="left" nowrap="nowrap"><label for="fechaFirma">Fecha de la factura:<span class="naranja">*</span></label></td>
		  		<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegistro.facturaRegistro.fechaFacturaRegistro.dia" id="diaFecFactura"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecFactura'), document.getElementById('mesFecFactura'), document.getElementById('anioFecFactura'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.facturaRegistro.fechaFacturaRegistro.mes" id="mesFecFactura"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecFactura'), document.getElementById('mesFecFactura'), document.getElementById('anioFecFactura'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.facturaRegistro.fechaFacturaRegistro.anio" id="anioFecFactura"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diaFecFactura'), document.getElementById('mesFecFactura'), document.getElementById('anioFecFactura'));"/>
						</td>
						
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('anioFecFactura'), document.getElementById('mesFecFactura'), document.getElementById('diaFecFactura')); return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
			</tr> 
			
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Lista facturas</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
	        		<jsp:include page="facturasList.jsp" />
	        	</td>
	        </tr>
		</table>
		
	</div>