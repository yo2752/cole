<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta EEFF</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:if test="%{consultaEEFF.resultadoConsultaEEFF.codigoError != null}">
		<jsp:include page="resultadoErrorEEFF.jsp" flush="false"></jsp:include>
	</s:if>
	<s:else>
		<jsp:include page="resultadoConsultaEEFF.jsp" flush="false"></jsp:include>
	</s:else>
</div>