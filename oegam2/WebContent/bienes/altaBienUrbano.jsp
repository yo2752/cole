<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/bien/bienFunction.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Bien Urbano</span>
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
		<li id="BienUrbano"><a href="#BienUrbano">Bien Urbano</a></li>
		<li id="Direccion"><a href="#Direccion">Dirección</a></li>
		<s:if test="%{bien.listaModelos != null && !bien.listaModelos.isEmpty()}">
			<li id="Modelos"><a href="#Modelos">Modelos</a></li>
		</s:if>
		<s:if test="%{bien.listaRemesas != null && !bien.listaRemesas.isEmpty()}">
			<li id="Remesas"><a href="#Remesas">Remesas</a></li>
		</s:if>
		<s:if test="%{bien.listaInmuebles != null && !bien.listaInmuebles.isEmpty()}">
			<li id="Inmuebles"><a href="#Inmuebles">Inmuebles</a></li>
		</s:if>
	</ol>
	<s:form method="post" id="formData" name="formData">
		<s:hidden id="idTipoBien" name="bien.tipoInmueble.idTipoBien" />
		<s:hidden name="bien.fechaAlta" />
		<div class="contentTabs" id="BienUrbano" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaBienUrbano.jsp" %>
		</div>
		
		<div class="contentTabs" id="Direccion"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDireccionUrbano.jsp" %>
		</div>
		
		<div class="contentTabs" id="Modelos"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaListModelosBien.jsp" %>
		</div>
		
		<div class="contentTabs" id="Remesas"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaListRemesasBien.jsp" %>
		</div>
		
		<div class="contentTabs" id="Inmuebles"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaListInmueblesBien.jsp" %>
		</div>
		
		<br/>
		<div class="acciones center">
			<input type="button" class="boton" name="bGuardarBien" id="idGuardarBien" value="Guardar" 
				onClick="javascript:guardarBien();"onKeyPress="this.onClick"/>	
			<s:if test="%{bien.idBien != null}">
				<input type="button" class="boton" name="bEliminarBien" id="bEliminarBien" value="Eliminar" 
					onClick="javascript:eliminarBien();"onKeyPress="this.onClick"/>	
			</s:if>
		</div>
	</s:form>
</div>
