<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" height="300px" id="PopFlo">

	<iframe  height="300"
		name="gToday:normal:agenda.js:Finalizar"
		id="gToday:normal:agenda.js:Finalizar" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px; ">

	</iframe>

	<div id="busqueda">
		<table>
			<tr>
				<td align="left" nowrap="nowrap"><label for="labelTipoPersona">Fecha
						Inicio:</label></td>

				<td align="left" nowrap="nowrap" width="5%"><s:textfield
						name="fechaIniModificacion.dia" id="Ini_Dia" onblur="this.className='input2';"
						onkeypress="return validarNumerosEnteros(event)"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" />

				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%"><s:textfield
						name="fechaIniModificacion.mes" id="Ini_Mes" onblur="this.className='input2';"
						onkeypress="return validarNumerosEnteros(event)"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%"><s:textfield
						name="fechaIniModificacion.anio" id="Ini_Anio" onblur="this.className='input2';"
						onkeypress="return validarNumerosEnteros(event)"
						onfocus="this.className='inputfocus';" size="4" maxlength="4" />
				</td>

				<td align="left" nowrap="nowrap"><a href="javascript:;"
					onClick="if(self.Finalizar)Finalizar.fPopCalendarSplit(Ini_Anio, Ini_Mes, Ini_Dia);resizeMe();return false;"
					title="Seleccionar fecha"> <img class="PopcalTrigger"
						align="middle" src="img/ico_calendario.gif" width="15" height="16"
						border="0" alt="Calendario" />
				</a></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="labelTipoPersona">Fecha
						fin:</label></td>

				<td align="left" nowrap="nowrap" width="5%"><s:textfield
						name="fechaFinModificacion.dia" id="Fin_Dia" onblur="this.className='input2';"
						onkeypress="return validarNumerosEnteros(event)"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%"><s:textfield
						name="fechaFinModificacion.mes" id="Fin_Mes" onblur="this.className='input2';"
						onkeypress="return validarNumerosEnteros(event)"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" />
				</td>
				<td width="1%">/</td>
				<td align="left" nowrap="nowrap" width="5%"><s:textfield
						name="fechaFinModificacion.anio" id="Fin_Anio" onblur="this.className='input2';"
						onkeypress="return validarNumerosEnteros(event)"
						onfocus="this.className='inputfocus';" size="4" maxlength="4" />
				</td>

				<td align="left" nowrap="nowrap"><a href="javascript:;"
					onClick="if(self.Finalizar)Finalizar.fPopCalendarSplit(Fin_Anio, Fin_Mes, Fin_Dia);resizeMe();return false;"
					title="Seleccionar fecha"> <img class="PopcalTrigger"
						align="middle" src="img/ico_calendario.gif" width="15" height="16"
						border="0" alt="Calendario" />
				</a></td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
	function resizeMe(){ 			
		var $cal = $('iframe:eq(2)',parent.document);
		var $Div = $('#PopFlo',parent.document);
		
		var position = $("#Ini_Dia").position();
		$cal.css({
		    left:  position.left - 30 + "px",
		    top: (position.top + 50) + "px",
		    width: "100px"
		  
		});
		$('#divEmergenteModificarFechaConductor').css({ 'width':'370px'});
	
		
		
		$Div.css({
			
		    height:"210px"
		});
	}
</script>