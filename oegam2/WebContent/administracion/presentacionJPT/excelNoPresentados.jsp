<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/colas.js" type="text/javascript"></script>

<iframe width="174"
	height="189"
	name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js"
	src="calendario/ipopeng.htm"
	scrolling="no"
	frameborder="0"
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData">
	<div  class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular" >
					<span class="titulo">Consulta de expedientes no presentados en JPT</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="dfGenerarEstadisticas" >
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="labelFecha">Fecha:</label>
				</td>
				<td align="left">
					<table width="30%">
						<tr align="left">
							<td align="left">
								<s:textfield name="fechaNoPresentados.dia" id="dia" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2" onkeypress="return controlNumeros(event);"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="fechaNoPresentados.mes" id="mes" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2" onkeypress="return controlNumeros(event);"/>
							</td>
							<td  align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="fechaNoPresentados.anio" id="anio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="4" maxlength="4" onkeypress="return controlNumeros(event);"/>
							</td>
							<td align="left">
								<a href="javascript:;"onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anio, document.formData.mes, document.formData.dia);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bDescargarExcelJPT" id="bDescargarExcelJPT" value="Descargar Excel" onclick="return descargarExcel();"/>
	</div>
</s:form>

<script type="text/javascript">
	function descargarExcel() {
		var esOk = true;
		var diaFecha = document.getElementById("dia").value;
		var mesFecha = document.getElementById("mes").value;
		var anioFecha = document.getElementById("anio").value;

		if (diaFecha == "" || mesFecha == "" || anioFecha == "") {
			esOk = false;
			alert("Debe rellenar la fecha correctamente.");
		}

		if (esOk) {
			document.formData.action = "recuperarExcelDescargarExcelJPT.action";
			document.formData.submit();
		}
	}
</script>