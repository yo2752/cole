<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" cellSpacing="0" cellPadding="5">
		<tr>
			<td class="tabular">
				<span class="titulo">Intervinientes: Modificación de Intervinientes</span>
			</td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<s:hidden name="intervinienteTrafico.nifInterviniente"></s:hidden>
	<s:hidden name="intervinienteTrafico.numColegiado"></s:hidden>
	<s:hidden name="intervinienteTrafico.tipoInterviniente"></s:hidden>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<%@include file="../../persona/detalleIntervinienteGeneral.jsp" %>
	
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
</s:form>

<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>