<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos Del Titular</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Titular</span>
			</td>
		</tr>
	</table>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto != null && consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.nif != null
			&& !consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.nif.isEmpty()}">
		<jsp:include page="personaFisicaVehiculoAtex5.jsp" flush="false"></jsp:include>
		&nbsp;
	</s:if>
	<s:elseif test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto != null && consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.cif != null
			&& !consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.cif.isEmpty()}">
		<jsp:include page="personaJuridicaVehiculoAtex5.jsp" flush="false"></jsp:include>
		&nbsp;
	</s:elseif>
	<s:if test="%{consultaVehiculoAtex5Dto.vehiculoAtex5.listaCotitularesDto != null && !consultaVehiculoAtex5Dto.vehiculoAtex5.listaCotitularesDto.isEmpty()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Cotitulares</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaVehiculoAtex5Dto.vehiculoAtex5.listaCotitularesDto" class="tablacoin"
								uid="tablaCotitularesVehAtex5" 
								id="tablaCotitularesVehAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
								
						<display:column property="nifCotitular" title="Nif" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="filiacionCotitular" title="Filiación" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" />
							
					</display:table>
				</td>
			</tr>
		</table>
	</s:if>
</div>