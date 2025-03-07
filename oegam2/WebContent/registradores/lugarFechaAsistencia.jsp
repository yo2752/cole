<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Fecha y el lugar de la asistencia</td>
		</tr>
	</table>
	&nbsp;

	<div id="busqueda" class="busqueda">
		<table border="0" class="tablaformbasicaTC">
			<tr>
				<td align="right" nowrap="nowrap" width="5%">
					<label for="fecha">Fecha<span class="naranja">*</span>:</label>
				</td>
				<td colspan="3">
					<div>
						<table cellSpacing="3" cellPadding="0" border="0">
							<tr>
								<td>
									<s:textfield name="tramiteRegistro.reunion.fecha.dia" id="diaAsis" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="tramiteRegistro.reunion.fecha.mes" id="mesAsis" onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';" size="2" maxlength="2"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="tramiteRegistro.reunion.fecha.anio" id="anioAsis" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>
									<a id="botonFechaReunion" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAsis, document.formData.mesAsis, document.formData.diaAsis);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td align="right" nowrap="nowrap" width="2%">
					<label for="lugarReunion">Lugar<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield name="tramiteRegistro.reunion.lugar" id="lugarAsis" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="60" maxlength="55"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
	</div>
