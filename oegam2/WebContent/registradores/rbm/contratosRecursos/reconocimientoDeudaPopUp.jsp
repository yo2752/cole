<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.reconocimientoDeudaDto.idReconocimientoDeuda" />
<s:hidden name="tramiteRegRbmDto.reconocimientoDeudaDto.idDatosFinancieros" />
<s:hidden name="tramiteRegRbmDto.reconocimientoDeudaDto.fecCreacion" />		

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Reconocimiento de deuda:</span>
		</td>
	</tr>
</table>
<iframe width="174"  
	height="189" 
	name="gToday:normal:agenda.js:reconDeuda" 
	id="gToday:normal:agenda.js:reconDeuda" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="numGestoria8" class="small">Importe reconocido<span class="naranja">*</span></label></td>
		<td width="30%"><s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.impReconocido" id="reconocimientoDeudaDtoImpReconocido" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
		<td align="left" nowrap="nowrap"><label for="reconocimientoDeudaDtoNumPlazos" class="small">N&uacute;m. de plazos<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.numPlazos" id="reconocimientoDeudaDtoNumPlazos" size="14" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
		
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="reconocimientoDeudaDtoImpPlazos" class="small">Importe de los plazos<span class="naranja">*</span></label></td>
		<td width="35%"><s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.impPlazos" id="reconocimientoDeudaDtoImpPlazos" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
		
		<!-- Este campo se muestra si el contrato es de tipo Leasing o Renting -->
		<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_8.getValorEnum() 
			or tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_9.getValorEnum()">
			<td align="left" nowrap="nowrap"><label for="reconocimientoDeudaDtoDiaVencimiento" >D&iacute;a del mes de vencim.<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="D&iacute;a del mes de vencimiento"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.diaVencimiento" id="reconocimientoDeudaDtoDiaVencimiento" size="14" maxlength="2" onkeypress="return soloNumeroDecimal(event, this, '2', '0')"></s:textfield></td>
		</s:if>
	</tr>
	
	<!-- Estos campos se muestran si el contrato es de tipo Leasing o Renting -->
	<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_8.getValorEnum() 
		or tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_9.getValorEnum()">
		<tr>
			<td align="left" nowrap="nowrap"><label for="fecPrimerVencimiento" >Fecha 1er vencim.<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Fecha del primer vencimiento"/>
			</td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.fecPrimerVencimientoReconDeuda.dia" id="diafecPrimerVencimiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diafecPrimerVencimiento'), document.getElementById('mesfecPrimerVencimiento'), document.getElementById('aniofecPrimerVencimiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.fecPrimerVencimientoReconDeuda.mes" id="mesfecPrimerVencimiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diafecPrimerVencimiento'), document.getElementById('mesfecPrimerVencimiento'), document.getElementById('aniofecPrimerVencimiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.fecPrimerVencimientoReconDeuda.anio" id="aniofecPrimerVencimiento"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" onblur="validaInputFecha(document.getElementById('diafecPrimerVencimiento'), document.getElementById('mesfecPrimerVencimiento'), document.getElementById('aniofecPrimerVencimiento'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.reconDeuda)reconDeuda.fPopCalendarSplit(aniofecPrimerVencimiento, mesfecPrimerVencimiento, diafecPrimerVencimiento); resizeMe();  return false;" 
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
	
			<td align="left" nowrap="nowrap"><label for="reconocimientoDeudaDtoTiempoEntrePagos" >Tiempo entre pagos<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Tiempo expresado en meses entre pagos"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.reconocimientoDeudaDto.tiempoEntrePagos" id="reconocimientoDeudaDtoTiempoEntrePagos" size="14" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
		</tr>
	</s:if>
</table>

<script type="text/javascript">

function resizeMe(){
	var $cal = $('iframe:eq(1)',parent.document);
	var position = $("#diafecPrimerVencimiento").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top + 20) + "px"
	});
}

</script> 
