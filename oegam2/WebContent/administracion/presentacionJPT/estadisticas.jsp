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

<script type="text/javascript">
	$(function() {
		$("#displayTagDiv").displayTagAjax();
	});

	function cargarTotalCantidad() {
		var cantidadJPT = document.formData.cantidadEstadisticas;
		var total = 0;
		for (var i=0; i<cantidadJPT.length; i++) {
			total = parseInt(total) + parseInt(cantidadJPT[i].value);
		}

		document.getElementById("totalCant").value = total;
	}
</script>
<body onload="cargarTotalCantidad()"></body>
<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData">
	<div class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular" >
					<span class="titulo">DATOS DE LAS ESTADÍSTICAS JPT</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="dfEstadisticas" align="center" style="background-color: #f4f4f4;">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="labelNumColegiado">Num.	Colegiado: </label>
				</td>
				<td align="left">
					<s:textfield name="estadisticasJPTBean.numColegiado" id="idNumColegiadoJPT" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="4" maxlength="4" onkeypress="return controlNumeros(event);"/>
				</td>
			</tr>
			<tr>
				<td align="left" >
					<label for="labelFecha">Fecha: </label>
				</td>
				<td align="left">
					<table width="90%">
						<tr>
							<td>
								<s:textfield name="estadisticasJPTBean.fecha.diaInicio" id="idDiaJPT" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2" onkeypress="return controlNumeros(event);"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="estadisticasJPTBean.fecha.mesInicio" id="idMesJPT" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2" onkeypress="return controlNumeros(event);"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="estadisticasJPTBean.fecha.anioInicio" id="idAnioJPT" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="4" maxlength="4" onkeypress="return controlNumeros(event);"/>
							</td>
							<td align="left">
								<a href="javascript:;"onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioJPT, document.formData.idMesJPT, document.formData.idDiaJPT);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
							<td align="left">
								<s:checkbox name="estadisticasJPTBean.incidencias" id="existeIncidencias" fieldValue="true"
											onclick="mostrarIncidencias();"/>
							</td>
						</tr>
					</table>
				</td>
				<td align="left">
					<label for="labelIncidencias">Existen incidencias de documentos base</label>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelJefaturaProvJpt">Jefatura Provincial: </label>
				</td>
				<td  align="left">
					<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
						<s:textfield id="idDescJefaturaJPT" value="%{@escrituras.utiles.UtilesVista@getInstance().getJefaturaProvincial(estadisticasJPTBean.jefaturaJPT)}" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25" onkeypress="return controlNumeros(event);"/>
						<s:hidden id="idJefaturaJPT" name="estadisticasJPTBean.jefaturaJPT"/>
					</s:if>
					<s:else>
						<s:select id="idJefaturaJPTSelect"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							disabled="%{flagDisabled}"
							list="@escrituras.utiles.UtilesVista@getInstance().getJefaturasJPTEnums()"
							headerKey=""
							headerValue="Seleccione Jefatura Provincial"
							name="estadisticasJPTBean.jefaturaJPT"
							listKey="jefatura"
							listValue="descripcion"/>
					</s:else>
				</td>
			</tr>
		</table>
		<div id="displayTagDiv" class="divScroll" style="width:85%;">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="estadisticasJPTBean.listadoTipoEstadisticasJPTBean"
					excludedParams="*" requestURI="navegar${action}" class="tablacoin"
					uid="tablaEstadisticasJPT" summary="Listado de Estadísticas JPT"
					cellspacing="0" cellpadding="0" varTotals="totalCantidad" >

				<display:column property="idTipoEstadistica" media="none" />

				<display:column property="descripcion" title="Tipo EstadísticaJPT" sortable="false" headerClass="sortable" defaultorder="descending"
						style="width:10%; text-align: justify; padding:4px;">
				</display:column>

				<display:column style="width:0.2%;" title="Cantidad">
					<input type="text" name="estadisticasJPTBean.listadoTipoEstadisticasJPTBean[<s:property value="#attr.tablaEstadisticasJPT_rowNum"/> - 1].cantidad"
							id="cantidadEstadisticas" value=<s:property value="#attr.tablaEstadisticasJPT.cantidad"/> onBlur="cargarTotalCantidad();" onkeypress="return controlNumeros(event);" style="text-align:center;">
				</display:column>

				<display:footer>
					<tr>
						<td style="font-weight: bold;" align="right">Cantidad Total: </td>
						<td>
							<input type="text" name="total" id="totalCant" value="0" style="text-align:center;" readOnly>
						</td>
					</tr>
				</display:footer>
			</display:table>
			<s:iterator value="estadisticasJPTBean.listadoTipoEstadisticasJPTBean" status="row">
				<s:hidden name="estadisticasJPTBean.listadoTipoEstadisticasJPTBean[%{#row.index}].idTipoEstadistica" />
				<s:hidden name="estadisticasJPTBean.listadoTipoEstadisticasJPTBean[%{#row.index}].descripcion" />
			</s:iterator>
		</div>
	</div>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		<div class="acciones center">
			<input type="button" class="boton" name="bGuardar" id="bGuardar" value="Guardar" onclick="return guardarEstadisticas();" />
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar" onclick="return limpiarPantalla();" />
		</div>
	</s:if>
</s:form>

<script type="text/javascript">
	function mostrarIncidencias() {
		document.formData.action = "mostrarIncidenciasEstadisticasJPT.action";
		document.formData.submit();
	}

	function deshabilitarGenerar() {
		var color = "#6E6E6E";
		document.getElementById("bGuardar").disabled = "true";
		document.getElementById("bGuardar").style.color = color;

		document.getElementById("bLimpiar").disabled = "true";
		document.getElementById("bLimpiar").style.color = color;
	}

	function limpiarPantalla(){
		var cantidadJPT = document.formData.cantidadEstadisticas;

		document.getElementById("idNumColegiadoJPT").value="";
		document.getElementById("idDiaJPT").value = "";
		document.getElementById("idMesJPT").value = "";
		document.getElementById("idAnioJPT").value = "";
		document.getElementById("totalCant").value = "0";

		for (var i=0; i < cantidadJPT.length; i++) {
			cantidadJPT[i].value = "0";
		}

		var descJefatura = document.getElementById("idDescJefaturaJPT");

		if (descJefatura.value == undefined) {
			document.getElementById("idJefaturaJPTSelect").value = "";
		}
	}

	function guardarEstadisticas() {
		var esOk = true;
		var numColegiado = document.getElementById("idNumColegiadoJPT").value;
		var diaFecha = document.getElementById("idDiaJPT").value;
		var mesFecha = document.getElementById("idMesJPT").value;
		var anioFecha = document.getElementById("idAnioJPT").value;
		var totalCant = document.getElementById("totalCant");
		var jefaturaProv = document.getElementById("idJefaturaJPTSelect");
		var descJefatura = document.getElementById("idDescJefaturaJPT");
		var mensaje = "";

		if (numColegiado == "") {
			esOk = false;
			mensaje = "Debe rellenar el numero de colegiado. \n";
		} else if (numColegiado.match(/^[0-9]+$/) == null) {
			esOk = false;
			mensaje = "El numero de colegiado debe ser numérico\n";
		}

		if (diaFecha == "" || mesFecha == "" || anioFecha == "") {
			esOk = false;
			mensaje += "Debe rellenar la fecha de la estadística correctamente.\n";
		} else {
			var formato = comprobarFormatoFecha(parseInt(diaFecha), parseInt(mesFecha), anioFecha);
			if (!formato) {
				mensaje += "El formato introducido para la fecha es incorrecto.\n";
				esOk = false;
			}
		}

		if (totalCant.value == 0) {
			esOk = false;
			mensaje += "Debe rellenar la cantidad para algún Tipo de Estadística JPT.\n";
		}

		if (descJefatura == null && jefaturaProv.value == "") {
			esOk = false;
			mensaje += "Debe rellenar la Jefatura Provincial para las estadísticas JPT.\n";
		}

		if (esOk) {
			deshabilitarGuardar();
			document.formData.action = "guardarEstadisticasJPT.action";
			document.formData.submit();
		} else {
			alert(mensaje);
		}
	}

	function deshabilitarGuardar() {
		var inputs = document.getElementsByTagName('input');
		for ( var i = 0; i < inputs.length; i++) {
			var input = inputs[i];

			if (input.type == 'button') {
				input.disabled = true;
				input.style.color = '#6E6E6E';
			}

		}
	}

	function comprobarFormatoFecha(dia, mes, anio) {
		var formatoOk = true;

		if (dia < 1 || dia > 31) {
			formatoOk = false;
		}

		if (mes < 1 || mes > 12) {
			formatoOk = false;
		}

		if (anio.length == 1) {
			formatoOk = false;
		}

		return formatoOk;
	}

	function controlNumeros(e){
		var charCode = event.keyCode;
		if ((charCode >= 48 && charCode <= 57) || charCode == 9) {
			return true;
		} else {
			return false;
		}
	}
</script>