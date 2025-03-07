<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:form id="formData" name="formData" action="presentacionPresentacionJPT.action">
<s:iterator value="resumenExpedientes" status="iterador">
<div style="cursor: pointer;" onclick="showExpedientes(<s:property value="%{#iterador.index}"/>)">
<table width="100%" class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td colspan="4" style="width:50%">Resumen Expediente: <s:property value="idDocumento" /> </td>
			<s:if test="(expedientes != null && expedientes.size() > 0)">
				<td><img alt="Ampliar" src="img/find.png"> </td>
			</s:if>
		</tr>
</table>
</div>
<s:hidden name="listadoIds" value="%{idDocumento}"/>
<s:if test="(expedientes != null && expedientes.size() > 0)">
	<div class="contentTabs" id="AltaJPT" style="font-size:110%; border: none; background-color: rgb(235, 237, 235);">
		<div class="contenido">
			<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">

				<tr>
					<td>
						<table>
						<tr>
							<td style="vertical-align:middle; padding-right:2em; width: 20%">
								<label for="value">Tipo de documento:</label>
							</td>
							<td>
								<s:property value="tipoTramiteTrafico" />
							</td>
							<td style="vertical-align:middle; padding-right:2em; width: 25%">
								<label for="value">Número de colegiado:</label>
							</td>
							<td>
								<s:property value="numeroColegiado" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; padding-right:2em; width: 30%">
								<label for="value">Fecha de Presentación en Tráfico:</label>
							</td>
							<td>
								<s:property value="fechaPresentacion" />
							</td>

							<td style="vertical-align:middle; padding-right:2em; width: 20%">
								<label for="value">Presentados: </label>
							</td>
							<td>
								<s:property value="numeroDeExpedientesPresentados" />
								<label for="value">de </label>
								<s:property value="numeroDeExpedientesTotal" />
							</td>
						</tr>

						<tr>
							<td colspan="4">
								<span style="font-size: 120%;"><strong><s:property value="balancePresentados"/> </strong></span>
							</td>
						</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="<s:property value="%{'tabla_'+#iterador.index}"/>" style="display:none; max-height:343px; overflow-y: auto; width:100%" >
	<display:table name="expedientes" uid="iterador.index" requestURI="" sort="page" class="tablacoin" style="border-collapse:collapse">

		<display:column property="numExpediente" title="Nº Expediente" headerClass="centro"
			style="width:16%;text-align:left;padding-left:10px;font-weight: bold;" />

		<display:column property="matricula" title="Matricula" headerClass="centro"
			style="width:10%;" />

		<display:column property="bastidor" title="Nº Bastidor"
			style="text-align:center;width:15%;" headerClass="centro" />

		<display:column property="presentado.nombreEnum" title="Presentado JPT"
			style="text-align:center;width:15%;"/>

		<display:column property="fechaPresentacionJpt" title="Fecha Presentación JPT"
			headerClass="centro" format="{0,date,dd-MM-yyyy HH:mm}" style="width:20%;" />

	</display:table>
	</div>

</s:if>
<s:else>

	<table class="tablaformbasica">
		<tr>
			<td style="text-align: left;">
				<label><s:property value="motivoNoHayExpedientes" /></label>
			</td>
		</tr>
	</table>

</s:else>
	<div style="clear: both; height: 3em;"></div>
	</s:iterator>

	<table class="acciones">
		<tr>
			<td>
				<div>
					<input type="button" class="boton" name="bVolver"
						id="bVolver" value="Volver" onClick="window.location='PresentacionJPT.action'" />
				</div>
			</td>
			<s:if test="(resumenExpedientes != null && resumenExpedientes.size() > 0)">
			<%--<s:if test="true">--%>
				<td>
					<div>
						<input type="submit" class="boton" name="bConfirmar"
							id="bConfirmar" value="Confirmar" />
					</div>
				</td>
			</s:if>
			
		</tr>
	</table>
</s:form>

<script type="text/javascript">
function showExpedientes(id) {
	var visibilidad = document.getElementById('tabla_'+id).style.display;
	if (visibilidad == 'none') {
		document.getElementById('tabla_'+id).style.display="inline-block";
	} else {
		document.getElementById('tabla_'+id).style.display="none";
	}
}
</script>