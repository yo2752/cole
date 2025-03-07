<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Monitor de Procesos</span></td>
		</tr>
	</table>
</div>
<div>
	<table align="center" style="width: 90%; padding-top: 30px;">
		<tr>
			<td style="font-weight: bold; font-size: 1.1em; text-align: left;">
				&Uacute;ltima actualización&nbsp; ${fechaActualizacion}</td>
		</tr>
	</table>
</div>

<h3 class="formularios">Procesos de Tr&aacute;fico</h3>
<s:form id="formData" name="formData" action="procesosMonitorizacion.action">

	<display:table name="listaProcesosTrafico" excludedParams="*"
		list="listaProcesos" requestURI="listaProcesos.action"
		class="tablacoin" summary="Listado de Procesos" id="tabla"
		style="width:90%;margin-left:auto" cellspacing="0" cellpadding="0"
		uid="listaProcesosTable">mostrarVentana

		<display:column property="nombre" title="Proceso" headerClass="centro"
			style="width:16%;text-align:left;padding-left:10px;font-weight: bold;" />

		<display:column property="ultimaOK" title="Ultima OK" headerClass="centro"
			style="width:15%;" />

		<display:column property="ultimaKO" title="Ultima Error"
			style="text-align:center;width:15%;" headerClass="centro" />

		<display:column property="ultimaExcepcion" title="Ultima Excepci&oacute;n"
			style="text-align:center;width:15%;" />

		<display:column property="solicitudesCola" title="Solicitudes en cola"
			headerClass="centro" style="width:15%;" />

		<display:column property="estado" title="Estado"
			headerClass="centro" style="width:15%;" />

	</display:table>

	<h3 class="formularios">Procesos de Gestión Documental</h3>

	<display:table name="listaProcesosGDoc" excludedParams="*"
		list="listaProcesos" requestURI="listaProcesos.action"
		class="tablacoin" summary="Listado de Procesos" id="tabla"
		style="width:90%;margin-left:auto" cellspacing="0" cellpadding="0"
		uid="listaProcesosTable">mostrarVentana

		<display:column property="nombre" title="Proceso" headerClass="centro"
			style="width:16%;text-align:left;padding-left:10px;font-weight: bold;" />

		<display:column property="ultimaOK" title="Ultima OK" headerClass="centro"
			style="width:15%;" />

		<display:column property="ultimaKO" title="Ultima Error"
			style="text-align:center;width:15%;" headerClass="centro" />

		<display:column property="ultimaExcepcion" title="Ultima Excepci&oacute;n"
			style="text-align:center;width:15%;" />

		<display:column property="solicitudesCola" title="Solicitudes en cola"
			headerClass="centro" style="width:15%;" />

		<display:column property="estado" title="Estado"
			headerClass="centro" style="width:15%;" />

	</display:table>

	<table class="acciones">
		<tr>
			<td>
				<div>
					<input type="submit" class="boton" name="bActualizar"
						id="bActualizar" value="Actualizar"  />
				</div>
			</td>
		</tr>
	</table>
</s:form>