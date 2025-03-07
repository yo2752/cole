<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/gestionTasa.js" type="text/javascript"></script>
<script type="text/javascript"></script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta de la Tasa</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
		<ol id="toc">
			<li id="DetalleTasa"><a href="#DetalleTasa">Detalle Tasa</a></li>
			<li id="ExpedienteAsoc" onclick="mostrarBotonTasa()"><a href="#ExpedienteAsoc">Expedientes Asociados</a></li>
		</ol>
		
	<s:form method="post" id="formData" name="formData">	
	
		<div class="contentTabs" id="DetalleTasa" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDetalleTasa.jsp" %>
		</div>
		<div class="contentTabs" id="ExpedienteAsoc" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaExpedienteAsociado.jsp" %>
		</div>
	</s:form>
</div>
