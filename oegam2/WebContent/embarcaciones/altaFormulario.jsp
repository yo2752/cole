<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/embarcaciones/embarcaciones.js" type="text/javascript"></script>
<script  type="text/javascript"></script>
<s:hidden id="textoLegal" name="propTexto"/>

<%@include file="../../includes/erroresMasMensajes.jspf" %>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta formulario embarcaciones 06</span>
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
		<li id="embarcacionTab"><a href="#embarcacionTab">Embarcacion</a></li>
		<li id="titularTab"><a href="#titularTab">Titular</a></li>
		<s:if test="%{embarcacion.titular.tipoPersona == 'JURIDICA'}">
			<li id="representanteTab"><a href="#representanteTab">Representante</a></li>
		</s:if>
		<li id="resumenTab"><a href="#resumenTab" onclick="javascript:guardarDatos()">Resumen</a></li>
	</ol>

	<s:form method="post" id="formData" name="formData">
	
		<div class="contentTabs" id="embarcacionTab" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaEmbarcacion.jsp" %>
		</div>
		
		<div class="contentTabs" id="titularTab"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitular.jsp" %>
		</div>
		
		<div class="contentTabs" id="representanteTab"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaRepresentante.jsp" %>
		</div>
		    	
		<div class="contentTabs" id="resumenTab"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumen.jsp" %>
		</div>
    
	</s:form>
 </div>

