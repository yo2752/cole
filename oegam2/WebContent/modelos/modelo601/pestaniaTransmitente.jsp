<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Modelo 601</td>
		</tr>
	</table>
	<div id="divTransmitente">
		<jsp:include page="../personas/transmitente.jsp" flush="false"></jsp:include>
	</div>
</div>
