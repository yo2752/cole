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
<script src="js/atex5/atex5.js" type="text/javascript"></script>
<script src="js/atex5/personaAtex5.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta  Consulta Persona Atex5</span>
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
		<li id="Consulta"><a href="#Consulta">Consulta Persona</a></li>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosPersona(consultaPersonaAtex5)}">
			<li id="DetallePersona"><a href="#DetallePersona">Detalle Persona</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosPersona(consultaPersonaAtex5)
			&& consultaPersonaAtex5.personaAtex5.listaMatriculas != null && !consultaPersonaAtex5.personaAtex5.listaMatriculas.isEmpty()}">
			<li id="DatosMatriculas"><a href="#DatosMatriculas">Matriculas</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosPersona(consultaPersonaAtex5)
		&& consultaPersonaAtex5.personaAtex5.listaPermisos != null && !consultaPersonaAtex5.personaAtex5.listaPermisos.isEmpty()}">
			<li id="DatosPermisos"><a href="#DatosPermisos">Permisos</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().esOkVisualizarDatosPersona(consultaPersonaAtex5)
			&& consultaPersonaAtex5.personaAtex5.listaSanciones != null && !consultaPersonaAtex5.personaAtex5.listaSanciones.isEmpty()}">
			<li id="DatosSanciones"><a href="#DatosSanciones">Sanciones</a></li>
		</s:if>
		
		<s:if test="consultaPersonaAtex5 != null && consultaPersonaAtex5.estado != null">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>
	
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="consultaPersonaAtex5.idConsultaPersonaAtex5" id="idConsultaPersonaAtex5"/>
		<s:hidden name="consultaPersonaAtex5.fechaAlta"/>
		<s:hidden name="consultaPersonaAtex5.numColegiado"/>
		<div class="contentTabs" id="Consulta" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaConsultaPersonaAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DetallePersona" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosPersonaAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosMatriculas" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosMatriculasPersonaAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosPermisos" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosPermisosPersonaAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="DatosSanciones" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosSancionesPersonaAtex5.jsp" %>
		</div>
		
		<div class="contentTabs" id="Resumen" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaResumenPersonaAtex5.jsp" %>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaPersonaAtex5@getInstance().esEstadoGuardable(consultaPersonaAtex5)}">
				<input type="button" class="boton" name="bGuardarPerAtex5" id="idGuardarPerAtex5" value="Guardar" onClick="javascript:guardarConsultaPersona();"
		 			onKeyPress="this.onClick"/>
		 	</s:if>
		 	<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaPersonaAtex5@getInstance().esEstadoConsultable(consultaPersonaAtex5)}">	
				<input type="button" class="boton" name="bConsultarPerAtex5" id="idConsultarPerAtex5" value="Consultar" onClick="javascript:consultarPersona();"
			 		onKeyPress="this.onClick"/>	
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaPersonaAtex5@getInstance().esEstadoFinalizadoOK(consultaPersonaAtex5)}">	
				<input type="button" class="boton" name="bImprimirPerAtex5" id="idImprimirPerAtex5" value="Imprimir PDF" onClick="javascript:imprimirPdfPersona();"
			 		onKeyPress="this.onClick"/>	
			</s:if>
		 	<input type="button" class="boton" name="bVolverPerAtex5" id="idVolverPerAtex5" value="Volver" onClick="javascript:volverConsultaPersona();"
		 		onKeyPress="this.onClick"/>	
		</div>
		<div id="divEmergenteEvolucionAltaConsultaPersonaAtex5" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>
