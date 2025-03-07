<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/consultaDev/consultaDev.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Consulta DEV</span>
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
		<li id="Consulta"><a href="#Consulta">Consulta Dev</a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().mostrarResultadoConsulta(consultaDev)}">
			<li id="SuscripcionesDev"><a href="#SuscripcionesDev">Resultados Consulta Dev</a></li>
		</s:if>
		<s:if test="consultaDev != null && consultaDev.estado != null">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>
	
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="consultaDev.idConsultaDev"/>
		<s:hidden name="consultaDev.fechaAlta"/>
		<div class="contentTabs" id="Consulta" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaConsultaDev.jsp" %>
		</div>
		
		<div class="contentTabs" id="SuscripcionesDev" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaSuscripcionesDev.jsp" %>
		</div>
		
		<div class="contentTabs" id="Resumen" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaResumen.jsp" %>
		</div>
		<br/>
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().esEstadoGuardable(consultaDev)}">
				<input type="button" class="boton" name="bGuardarDev" id="idGuardarDev" value="Guardar" onClick="javascript:guardarConsultaDev();"
		 			onKeyPress="this.onClick"/>
		 	</s:if>
		 	<s:if test="%{@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().esEstadoConsultable(consultaDev)}">	
				<input type="button" class="boton" name="bConsultarDev" id="idConsultarDev" value="Consultar" onClick="javascript:consultarDev();"
			 		onKeyPress="this.onClick"/>	
			 </s:if>
		 	<input type="button" class="boton" name="bVolverDev" id="idVolverDev" value="Volver" onClick="javascript:volverConsultaDev();"
		 		onKeyPress="this.onClick"/>	
		</div>
	</s:form>
</div>
<script type="text/javascript">
	inicializarVentanasConsultaDev();
</script>	