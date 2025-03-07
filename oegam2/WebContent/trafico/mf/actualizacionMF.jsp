 
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">
				Actualización Marcas y Fabricantes
			</span>
			</td>
		</tr>
	</table>
</div>

<!-- Pestañas -->

<ol id="toc">
	<li><a href="#Tramite">Fichero</a></li>
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
<%@include file="../../includes/erroresMasMensajes.jspf" %>

<table class="acciones">
	<tr>
		<td>
			<img id="loadingImage" style="display:none; margin-left:auto; margin-right:auto; text-align:center;" src="img/loading.gif">
		</td>
		<td>
			Seleccione un fichero con el contenido de las marcas/fabricantes que desea actualizar
				<s:file id="fichero" size="50" name="fichero" value="fichero" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
			<input type="button" class="boton" name="bSubirFichero" id="idSubirFichero" value="Subir Fichero" onClick="javascript:subir();"
				onKeyPress="this.onClick"/>
		</td>
	</tr>
</table>
</s:form>
<script type="text/javascript">
	function subir() {
		document.formData.action="subirActualizacionMF.action";
		document.formData.submit();
	}
</script>