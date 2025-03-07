<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/trafico/vehiculosPrematriculados.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<s:if test="%{!resumenImportacionFicheroEitvFlag}">
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
</s:if>

<s:form method="post" id="formData" name="formData" action="importarConsultaTarjetaMulti.action" enctype="multipart/form-data">

	<div id="busqueda">

		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: left;">Multi consulta de
					tarjetas EITV mediante importación de datos en fichero</td>
			</tr>
		</table>


		<table class="tablaformbasica" cellspacing="3" cellpadding="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="left" nowrap="nowrap"><s:file size="50" tooltip="Seleccione el fichero .csv a importar..." label="Texto de la label"
						name="fileUpload" value="fileUpload">* Recuerde que el fichero a importar debe tener extensión <b>'.csv'</b> y estar compuesto por líneas de pares bastidor, nive separados por un punto y coma<br/><br/></s:file></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="center">
					<label><input type="checkbox" id="checkDatosTecnicos" name="vehiculoPrematriculado.datosTecnicos" value="true" /> Datos Técnicos </label>
				</td>
				<td align="center">
					<label><input type="checkbox" id="checkFichaTecnica" name="vehiculoPrematriculado.fichaTecnica" value="true"/> Ficha técnica</label>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" width="8%" colspan="2">
					<input type="submit" class="button" id="idBotonExportarPopUp" value="Importar" />
				</td>

			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>

		<s:if test="%{resumenImportacionFicheroEitvFlag}">
			<br>
			<div id="estadisticasImportacionFicheroEitvEeff" style="text-align: center;">
				<%@include file="resumenImportacion.jspf"%>
			</div>
			<br>
			<br>
		</s:if>


	</div>

</s:form>


