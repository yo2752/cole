<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>
<script src="js/registradores/registradores.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Importaci&oacute;n de Contratos de Financiaci&oacute;n</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div class="contenido">	
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<s:form action="importar_ImportarFinancingDossier.action" id="formData" cssClass="transformable" enctype="multipart/form-data">
		<table><!-- Esta tabla se necesita para mostrar los mensajes de las validaciones --></table>
		<s:hidden name="idSession" />
	
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Selecci&oacute;n de fichero:</span>
				</td>
			</tr>
		</table>	
		<table class="insertDatosTb" id="datosTB">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="fichero" class="small">Fichero:</label></td>
				<td>
		    	<s:file id="fichero" size="50" name="fichero" value="fichero" />
				</td>
			</tr>
		</table>&nbsp;
		<a href="javascript:importarFichero()"
			id="bImportacion" class="button corporeo"><span id="iImportacion">Importar</span></a>
	</s:form>
</div>
