<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evoluci&oacute;n datos sensibles</span>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvolucionDatos").displayTagAjax();
		});
	</script>
	<div id="displayTagEvolucionDatos" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvolucion"
				id="tablaEvolucion" summary="Listado evolucion datos sensibles"
				excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0"
				decorator="${decorator}">

			<display:column property="fechaCambio" title="Fecha Cambio" defaultorder="descending" maxLength="22" style="width:2%"
				format="{0,date,dd-MM-yyyy HH:mm:ss}" />

			<display:column property="estadoAnterior" title="Estado anterior" defaultorder="descending" style="width:1.5%" />

			<display:column property="estadoNuevo" title="Estado nuevo" defaultorder="descending" style="width:1%" />

			<display:column property="tipoCambio" title="Tipo cambio" defaultorder="descending" style="width:2%" />

			<display:column property="origen" title="Origen cambio" defaultorder="descending" style="width:2%" />

			<display:column property="apellidosNombre" title="Apellidos nombre" defaultorder="descending" style="width:4%" />

		</display:table>
	</div>
</div>