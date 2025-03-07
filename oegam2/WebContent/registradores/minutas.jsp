<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

	<s:hidden name="tramiteRegistro.minutaRegistro.idMinuta"/>	

   	<div id = "divMinutas" >
   		
   		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos minuta</span>
				</td>
			</tr>
		</table>
		
		<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
			 <tr>
			 
				<td align="left" nowrap="nowrap"><label for="aceptada">Aceptada:<span class="naranja">*</span></label></td>
		      	<td align="left" width="10%"><s:checkbox name="tramiteRegistro.minutaRegistro.aceptada" fieldValue="true" label="ejemplo" id="aceptada"/></td>

				<td align="left" nowrap="nowrap"><label for="numMinuta" class="small">N&uacute;mero de minuta:<span class="naranja">*</span></label></td>
				<td>
					<s:textfield name="tramiteRegistro.minutaRegistro.numeroMinuta" id="numMinuta" size="18" maxlength="255" ></s:textfield>
				</td>

	 			<td  colspan="2" align="left" nowrap="nowrap"><label for="fechaMinuta">Fecha minuta:<span class="naranja">*</span></label></td>
		  					<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegistro.minutaRegistro.fechaMinutaRegistro.dia" id="diafechaMinuta"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diafechaMinuta'), document.getElementById('mesfechaMinuta'), document.getElementById('aniofechaMinuta'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.minutaRegistro.fechaMinutaRegistro.mes" id="mesfechaMinuta"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diafechaMinuta'), document.getElementById('mesfechaMinuta'), document.getElementById('aniofechaMinuta'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.minutaRegistro.fechaMinutaRegistro.anio" id="aniofechaMinuta"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diafechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('aniofechaDocumento'));"/>
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.aniofechaMinuta, document.formData.mesfechaMinuta, document.formData.diafechaMinuta);  return false;" 
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
					<span class="titulo">Lista minutas</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
	        		<jsp:include page="minutasList.jsp" />
	        	</td>
	        </tr>
		</table>
		
	</div>