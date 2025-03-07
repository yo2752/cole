 
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/576.js" type="text/javascript"></script>
<script src="js/pagosEImpuestos.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/matriculacion.js" type="text/javascript"></script>


<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo"> Modificacion de Factura </span></td>
		</tr>
	</table>
</div>


<!-- Pestañas -->

<ol id="toc">
	    <li> <a href="#Cliente">Cliente </a></li>
	    <li> <a href="#Tasa">Tasa</a></li>
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

<%@include file="pestaniaClienteModFactura.jspf" %>
<%@include file="pestaniaTasaModFactura.jspf" %>

<table class="acciones">
	<tr>
		<td>
			<img id="loadingImage" style="display:none; margin-left:auto; margin-right:auto; text-align:center;" src="img/loading.gif">
		</td>
	</tr>
</table>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>

</s:form>