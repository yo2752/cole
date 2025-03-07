<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/creditos.js" type="text/javascript"></script>

<div class="nav">
	<table cellSpacing="0" cellPadding="5" width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Resumen mensual de carga de cr&eacute;ditos</span></td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="id_Contrato">Colegiado:</label>
					</td>
					<td align="left">
						<s:select id="idcontratoColegiado"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						listKey="codigo"
						listValue="descripcion"
						cssStyle="width:150px"
						name="resumenCargaCreditosBean.idContrato"
						headerKey=""
						headerValue="Todos"></s:select>
					</td>
				</s:if>
				<s:else>
					<s:hidden id="contratoColegiado" name="contratoColegiado"/>
				</s:else>
				<td align="left" style="vertical-align: middle;" >
					<label for="idLbTipoCredito" >Tipo de Cr&eacute;dito:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idTipoCredito"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					list="@org.gestoresmadrid.oegamCreditos.view.utiles.UtilesVistaCredito@getInstance().obtenerTiposCreditosIncrementales()"
					headerKey=""
					headerValue="Todos los Créditos Incrementales"
					name="resumenCargaCreditosBean.tipoCredito"
					listKey="tipoCredito"
					listValue="tipoCredito + ' - ' + descripcion"/>
				</td>

			</tr>
			<tr>
				<td align="left" style="vertical-align: middle;">
					<label for="idLbPrecioCredi">Precio Cr&eacute;dito:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="resumenCargaCreditosBean.precioCredito" id="idPrecioCredito" style="margin-left"
					value="0" size="21" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"/>
				</td>

				<td colspan="4" align="left">
					<table>
						<tr>
							<td align="left" style="vertical-align: middle;"><label for="mesAltaIni">Fecha:</label></td>

								<s:hidden name="resumenCargaCreditosBean.fechaAlta.diaInicio" id="diaAltaIni" value="01"/>
								<%-- <s:hidden name="resumenCargaCreditosBean.fechaAlta.mesFin" id="mesAltaFin" value="%{resumenCargaCreditosBean.fechaAlta.mesInicio}"/>
								<s:hidden name="resumenCargaCreditosBean.fechaAlta.anioFin" id="anioAltaFin" value="%{resumenCargaCreditosBean.fechaAlta.anioInicio}"/> --%>

							<td>
								<s:select name="resumenCargaCreditosBean.fechaAlta.mesInicio" id="mesAltaIni" style="margin-left: 22px"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									list="#{'1':'Enero','2':'Febrero','3':'Marzo','4':'Abril','5':'Mayo','6':'Junio',
									'7':'Julio','8':'Agosto','9':'Septiembre','10':'Octubre','11':'Noviembre','12':'Diciembre' }"
									headerKey="-1" emptyOption="false" />
							</td>
							<td>
								<s:select name="resumenCargaCreditosBean.fechaAlta.anioInicio" id="anioAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									list="anios" 
									headerKey="-1" emptyOption="false" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<div id="botonBusqueda">
			<table width="100%" align="center">
				<tr align="center">
					<td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<input class="botonGrande" type="button" id="bExportar" name="bExportar" value="Generar tabla excel" onClick="generarFichero();return false;" onKeyPress="this.onClick" />
						</s:if>
					</td>
					<td>
						<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar" onkeypress="this.onClick" onclick="return limpiarFormResumenMensualCreditos();" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</s:form>

<script type="text/javascript">
	function generarFichero() {
		var tipoCredito = document.getElementById("idTipoCredito");
		var num = document.getElementById("idPrecioCredito");
		if (!num.value || isNaN(num.value)) {
			alert("Debe introducir un valor num\u00e9rico para el precio de cr\u00e9dito");
			return false;
		}
		if (tipoCredito.value == "") {
			alert("Debe seleccionar un tipo de cr\u00e9dito");
			return false;
		} else {
			document.formData.action = "exportarResultadosMesResumenCargaCredito.action";
			document.formData.submit();
			return true;
		}
	}

	function limpiarFormResumenMensualCreditos() {
		document.getElementById("idcontratoColegiado").value = "";
		document.getElementById("idTipoCredito").value = "";
		document.getElementById("idPrecioCredito").value = "";
	}
</script>