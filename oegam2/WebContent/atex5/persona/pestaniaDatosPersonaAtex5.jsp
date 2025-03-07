<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Persona</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Persona</span>
			</td>
		</tr>
	</table>
	<s:if test="%{consultaPersonaAtex5.personaAtex5.personaFisicaDto != null && consultaPersonaAtex5.personaAtex5.personaFisicaDto.nif != null
			&& !consultaPersonaAtex5.personaAtex5.personaFisicaDto.nif.isEmpty()}">
		<jsp:include page="personaFisicaPersonaAtex5.jsp" flush="false"></jsp:include>
		&nbsp;
	</s:if>
	<s:elseif test="%{consultaPersonaAtex5.personaAtex5.personaJuridicaDto != null && consultaPersonaAtex5.personaAtex5.personaJuridicaDto.cif != null
			&& !consultaPersonaAtex5.personaAtex5.personaJuridicaDto.cif.isEmpty()}">
		<jsp:include page="personaJuridicaPersonaAtex5.jsp" flush="false"></jsp:include>
		&nbsp;
	</s:elseif>
</div>