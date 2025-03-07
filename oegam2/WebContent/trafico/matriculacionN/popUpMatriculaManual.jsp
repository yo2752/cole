
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Introducir Matrícula Manual</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="left">
				<label for="matriculaManual">Matrícula<span class="naranja">*</span></label>
				<s:textfield name="matriculaManual" id="matriculaManual" size="15" maxlength="15" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"
					onblur="this.className='input';" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<label for="labelFechaMatriculacion">Fecha de Matriculación<span class="naranja">*</span></label>
					<table style="width:20%">
						<tr>
							<td align="left">
								<s:textfield name="fechaMatriculacion.diaFin" id="diaFechaMatriculacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="fechaMatriculacion.mesFin" id="mesFechaMatriculacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="fechaMatriculacion.anioFin" id="anioFechaMatriculacion"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.agendapup)agendapup.fPopCalendarSplit(anioFechaMatriculacion, mesFechaMatriculacion, diaFechaMatriculacion);resizeMe();return false;"
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

</div>
<iframe width="174" height="189" name="gToday:normal:agenda.js:agendapup"
	id="gToday:normal:agenda.js:agendapup" src="calendario/ipopeng.htm" class="prueba"
	scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
</iframe>

<script type="text/javascript">
	function resizeMe(){
		var $cal = $('iframe:eq(1)', parent.document);
		var position = $("#diaFechaMatriculacion").position();
		$cal.css({
			left: position.left + "px",
			top: (position.top + 20) + "px"
		});
	}
</script>