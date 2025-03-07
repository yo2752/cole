<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/permisosFichasDistintivosDgt/facturacionDstv.js" type="text/javascript"></script>
<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Alta Incidencia Distintivo</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="left">
					<label for="observacionesId">Motivo:<span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap" colspan="3">
					<s:textarea name="facturacionDstvInc.motivoIncidencia"
					id="idMotivo" 
					disabled="%{flagDisabled}"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					rows="5"
					cols="40"/>
				</td>
			</tr>
			<tr>
				<td align="left"><br>
					<label for="labelFechaInc">Fecha Incidencia:<span class="naranja">*</span></label>
				</td>
				<td id = "fechaInc"><br>
				<table style="width:20%" >
					<tr>
						<td>
							<s:textfield name="facturacionDstv.fecha.diaInicio" id="diaIncidenciaId"
								size="2" maxlength="2" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" />
						</td>
						<td>
							<label class="labelFecha">/</label>
						</td>
						<td>
							<s:textfield name="facturacionDstv.fecha.mesInicio" id="mesIncidenciaId"
								size="2" maxlength="2" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" />
						</td>
						<td>
							<label class="labelFecha">/</label>
						</td>
						<td>
							<s:textfield name="facturacionDstv.fecha.anioInicio" id="anioIncidenciaId"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td>
							<a href="javascript:;" onClick="if(self.agendapup)agendapup.fPopCalendarSplit(anioIncidenciaId, mesIncidenciaId, diaIncidenciaId);resizeMeFechaIncPopUp();return false;" 
											title="Seleccionar fecha">
											<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="left">
				<label for="observacionesId">Cantidad:<span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" colspan="3">
				<s:textarea name="facturacionDstvInc.cantidad"
				id="idCantidad" 
				disabled="%{flagDisabled}"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';"
				rows="1"
				cols="10"/>
			</td>
			<td align="left" nowrap="nowrap"><label for="esDupli" class="small">Duplicado:<span class="naranja">*</span></label></td>
              <td width="30%">  
              <s:select list="@facturacion.utiles.UtilesVistaFacturacion@getComboEsDuplicado()"
						id="comboEsDuplicado"
						name = "facturacionDstvInc.duplicado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						listValue="nombreEnum"
						listKey="valorEnum"
						headerValue="Indicar si es duplicado"
						headerKey=""
						/>
			</td>
      	</tr>
</table>
</div>
</div>
<iframe width="174" height="189" name="gToday:normal:agenda.js:agendapup"
		id="gToday:normal:agenda.js:agendapup" src="calendario/ipopeng.htm" class="prueba"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		
</iframe>
