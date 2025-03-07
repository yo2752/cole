<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Fecha y el lugar de la certificaci&oacute;n</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
			</td>
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
									<s:textfield name="tramiteRegistro.fechaCertif.dia" id="diaCertif" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="tramiteRegistro.fechaCertif.mes" id="mesCertif" onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';" size="2" maxlength="2"
										onkeypress="return validarNumerosEnteros(event)" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="tramiteRegistro.fechaCertif.anio" id="anioCertif" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>
									<a id="botonFechaReunion" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCertif, document.formData.mesCertif, document.formData.diaCertif);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
								<td colspan="3" style="text-align:left;">&nbsp;</td>
							</tr>
						</table>
					</div>
				</td>
				<td align="right" nowrap="nowrap" width="2%">
					<label for="lugarCertif">Lugar<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield name="tramiteRegistro.lugar" id="lugarCertif" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="30" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap" width="5%">
					<label for="referenciaPropia">Referencia Propia:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:textfield name="tramiteRegistro.refPropia" id="referenciaPropia" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="55" maxlength="50"/>
				</td>
			</tr>	
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
	</div>
