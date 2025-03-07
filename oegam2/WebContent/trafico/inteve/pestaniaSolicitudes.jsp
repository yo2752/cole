<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Trámite Inteve</td>
			<s:if test="%{tramiteInteve !=null && tramiteInteve.numExpediente != null}">
				<td align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
					onclick="consultaEvolucionTramiteInteve(<s:property value="%{tramiteInteve.numExpediente}"/>);" title="Ver evolución"/>
				</td>
			</s:if>
		</tr>
	</table>
	<div id="divListadoSolicitudes">
		<jsp:include page="listadoSolicitudes.jsp" flush="false"></jsp:include>
	</div>

	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esGuardable(tramiteInteve)}">
		<jsp:include page="datosSolicitudInteve.jsp" flush="false"></jsp:include>
	</s:if>
</div>