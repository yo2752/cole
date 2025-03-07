<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.cuadroAmortizacionDto.idCuadroAmortizacion" />
<s:hidden name="tramiteRegRbmDto.cuadroAmortizacionDto.idDatosFinancieros" />
<s:hidden name="tramiteRegRbmDto.cuadroAmortizacionDto.fecCreacion" />
<s:hidden name="tramiteRegRbmDto.cuadroAmortizacionDto.tipoPlazo" id="tipoPlazoCuadro" value="INSURANCE_FINANCING"/>

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Cuadros de amortizaci&oacute;n F.S.:</span>
		</td>
	</tr>
</table>

<iframe width="174"  
	height="189" 
	name="gToday:normal:agenda.js:cuadroAmort" 
	id="gToday:normal:agenda.js:cuadroAmort" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
	
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

	<tr>
        <td align="left" nowrap="nowrap"><label for="cuadroAmortizacionDtoFechaVencimiento" class="small">Fecha vencimiento<span class="naranja">*</span></label></td>
        <td>
			<table style="width:20%">
				<tr>
					<td align="left">
						<s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.fechaVencimientoCuadroAmort.dia" id="diaFechaVencimiento"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td align="left">
						<label class="labelFecha">/</label>
					</td>
					<td align="left">
						<s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.fechaVencimientoCuadroAmort.mes" id="mesFechaVencimiento"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFechaVencimiento'), document.getElementById('mesFechaVencimiento'), document.getElementById('anioFechaVencimiento'));"/>
					</td>
					<td align="left">
						<label class="labelFecha">/</label>
					</td>
					<td align="left">
						<s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.fechaVencimientoCuadroAmort.anio" id="anioFechaVencimiento"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="validaInputFecha(document.getElementById('diaFechaVencimiento'), document.getElementById('mesFechaVencimiento'), document.getElementById('anioFechaVencimiento'));"/>
					</td>
					
					<td align="left" nowrap="nowrap" width="5%" >
			    		<a href="javascript:;" 
			    			onClick="if(self.cuadroAmort)cuadroAmort.fPopCalendarSplit(anioFechaVencimiento, mesFechaVencimiento, diaFechaVencimiento); resizeMe();  return false;" 
			    				title="Seleccionar fecha">
			    			<img class="PopcalTrigger" 
			    				align="left" 
			    				src="img/ico_calendario.gif" 
			    				width="15" height="16" 
			    				border="0" alt="Calendario"/>
			    		</a>
					</td>
				</tr>
			</table>
		</td>
         
	</tr>
	<tr>
		 <td align="left" nowrap="nowrap"><label for="cuadroAmortizacionDtoImpCapitalAmortizado" >Importe amortizaci&oacute;n<span class="naranja">*</span></label></td>
        <td><s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.impAmortizacion" id="cuadroAmortizacionDtoImpAmortizacion" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
        <td align="left" nowrap="nowrap"><label for="cuadroAmortizacionDtoImpCapitalPendiente" >Capital pendiente<span class="naranja">*</span></label>
        	<img src="img/botonDameInfo.gif" alt="Info" title="Importe capital pendiente"/>
        </td>
        <td><s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.impCapitalPendiente" id="cuadroAmortizacionDtoImpCapitalPendiente" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="cuadroAmortizacionDtoImpCargaFinan" >Carga financiera<span class="naranja">*</span></label>
         	<img src="img/botonDameInfo.gif" alt="Info" title="Importe carga financiera"/>
         </td>
         <td><s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.impCargaFinan" id="cuadroAmortizacionDtoImpCargaFinan" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
         
         <td align="left" nowrap="nowrap"><label for="cuadroAmortizacionDtoImpTotalCuota" >Total cuota<span class="naranja">*</span></label>
         	<img src="img/botonDameInfo.gif" alt="Info" title="Importe total cuota, importe neto + impuesto"/>
         </td>
         <td><s:textfield name="tramiteRegRbmDto.cuadroAmortizacionDto.impTotalCuota" id="cuadroAmortizacionDtoImpTotalCuota" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
	</tr>
    
</table>


<script type="text/javascript">

function resizeMe(){
	var $cal = $('iframe:eq(1)',parent.document);
	var position = $("#diaFechaVencimiento").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top + 20) + "px"
	});
}

</script> 
    		 
