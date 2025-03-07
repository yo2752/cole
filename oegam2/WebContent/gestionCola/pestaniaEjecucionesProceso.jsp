<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de las Ejecuciones</td>
		</tr>
	</table>
	&nbsp;
	<display:table name="consultaGestionCola.gestionCola.listaEjecucionesProceso" excludedParams="*"
		list="listaEjecucionesProceso" class="tablacoin" summary="Listado de Ejecuciones" 
		style="width:90%;margin-left:auto" cellspacing="0" cellpadding="0" uid="tablaEjecuciones">
  
  		<display:column property="correcta" title="Ejecucion" headerClass="centro"	style="width:5%;" />
  		
  		<display:column property="cola" title="Hilo" headerClass="centro"	style="width:2%;" />
  
		<display:column property="fechaEnvio" title="Fecha/Hora" headerClass="centro"	style="width:5%;" 
			format="{0,date,dd/MM/yyyy HH:mm:ss}"/>
	
		<display:column property="numExpediente" title="Id Tramite" headerClass="centro" style="width:5%;"/>
 
		<display:column title="Respuesta" property="respuesta" headerClass="centro" style="width:10%;" />
		
	</display:table>
	&nbsp;