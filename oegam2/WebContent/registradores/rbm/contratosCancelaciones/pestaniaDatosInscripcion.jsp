<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="tramiteRegRbmDto.datosInscripcion.idDatosInscripcion" />
<s:hidden name="tramiteRegRbmDto.datosInscripcion.fecCreacion" />

<div class="contenido">
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos inscripci&oacute;n</span>
			</td>
		</tr>
	</table>	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		 <tr>

			<td align="left" nowrap="nowrap"><label for="codigoRbm">C&oacute;digo R.B.M.<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Código Registro Bienes Muebles"/>
			</td>
			<td>
				<s:select name="tramiteRegRbmDto.datosInscripcion.codigoRbm" 
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroBienesMuebles()"
					listKey="idRegistro"
					listValue="nombre"
					headerKey="" 
					headerValue="Seleccionar Código"
					id="codigoRbm"/>
			</td>
		
			<td align="left" nowrap="nowrap"><label for="numeroRegistralBien" title="N&uacute;mero Registral del Bien">N&uacute;mero registral bien<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.datosInscripcion.numeroRegistralBien" id="numeroRegistralBien" size="16" maxlength="15" onkeypress="return soloNumeroDecimal(event, this, '15', '0')"></s:textfield></td>
		
		</tr>
		<tr>	
			<td align="left" nowrap="nowrap"><label for="numeroInscripcionBien" >N&uacute;mero inscripci&oacute;n bien<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.datosInscripcion.numeroInscripcionBien" id="numeroInscripcionBien" size="16" maxlength="15" onkeypress="return soloNumeroDecimal(event, this, '15', '0')"></s:textfield></td>
		
			<td align="left" nowrap="nowrap"><label for="fechaInscripcion">Fecha inscripci&oacute;n<span class="naranja">*</span></label></td>
			<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datosInscripcion.fechaInscripcionDatInscrip.dia" id="diaFechaInscripcion"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFechaInscripcion'), document.getElementById('mesFechaInscripcion'), document.getElementById('anioFechaInscripcion'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datosInscripcion.fechaInscripcionDatInscrip.mes" id="mesFechaInscripcion"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFechaInscripcion'), document.getElementById('mesFechaInscripcion'), document.getElementById('anioFechaInscripcion'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datosInscripcion.fechaInscripcionDatInscrip.anio" id="anioFechaInscripcion"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFechaInscripcion'), document.getElementById('mesFechaInscripcion'), document.getElementById('anioFechaInscripcion'));"/>
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('anioFechaInscripcion'), document.getElementById('mesFechaInscripcion'), document.getElementById('diaFechaInscripcion'));return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>	
</div>		
		
