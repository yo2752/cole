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
	<div class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular">
					<span class="titulo">GENERACIÓN ESTADÍSTICAS JPT</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="dfGenerarEstadisticas">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="labelFecha">Fecha Estadística:</label>
				</td>
				<td align="left">
					<table width="30%">
						<tr align="left">
							<td align="left">
								<s:textfield name="fechaEstadistica.dia" id="dia" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2" onkeypress="return controlNumeros(event);" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="fechaEstadistica.mes" id="mes" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2" onkeypress="return controlNumeros(event);" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="fechaEstadistica.anio" id="anio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="4" maxlength="4" onkeypress="return controlNumeros(event);" />
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
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelJefaturaProvJpt">Jefatura Provincial:</label>
				</td>
				<td align="left">
					<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
						<s:hidden id="idJefaturaJPT" name="jefaturaJpt"/>
						<s:textfield id="idDescJefaturaJPT" value="%{@escrituras.utiles.UtilesVista@getInstance().getJefaturaProvincial(jefaturaJpt)}"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25" onkeypress="return controlNumeros(event);"/>
					</s:if>
					<s:else>
						<s:select id="idJefaturaJPTSelect"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							disabled="%{flagDisabled}"
							list="@escrituras.utiles.UtilesVista@getInstance().getJefaturasJPTEnums()"
							headerKey=""
							headerValue="Seleccione Jefatura Provincial"
							name="jefaturaJpt"
							listKey="jefatura"
							listValue="descripcion" />
					</s:else>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bGenerarEstadisticaJPT" id="bGenerarEstadisticaJPT" value="Generar Estadística" onclick="return generarEstadisticas();" />
	</div>
</s:form>
<script type="text/javascript">
	function generarEstadisticas() {
		deshabilitarGenerar();
		var esOk = true;
		var diaFecha = document.getElementById("dia").value;
		var mesFecha = document.getElementById("mes").value;
		var anioFecha = document.getElementById("anio").value;
		var jefaturaProv = document.getElementById("idJefaturaJPTSelect");
		var descJefatura = document.getElementById("idDescJefaturaJPT");

		if (diaFecha == "" || mesFecha == "" || anioFecha == "") {
			esOk = false;
			alert("Debe rellenar la fecha de la estadística correctamente.");
		}

		if (descJefatura == null && jefaturaProv.value == "") {
			esOk = false;
			mensaje += "Debe rellenar la Jefatura Provincial para las estadísticas JPT.\n";
		}

		if (esOk) {
			document.formData.action = "generarGenerarEstadisticaJPT.action";
			document.formData.submit();
		}
		habilitarGenerar();
	}

	function habilitarGenerar() {
		var input = document.getElementById("bGenerarEstadisticaJPT");
		input.disabled = false;
		input.style.color = '#000000';
	}

	function deshabilitarGenerar() {
		var input = document.getElementById("bGenerarEstadisticaJPT");
		input.disabled = true;
		input.style.color = '#6E6E6E';
	}
</script>