<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<s:hidden name="tipoInterviniente"/>
	<s:hidden name="lcTramiteDto : LcTramiteDto.numExpediente"/>

	<s:if test="paises != null && paises.size() > 0">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Sugerencia Países</span>
					</td>
				</tr>
			</table>
		</div>
		<display:table name="paises" excludedParams="*" class="tablacoin" uid="row" summary="Listado posibles paises" cellspacing="0" cellpadding="0">
			<display:column title="Nombre País" style="width:8%">
				<a href="javascript:seleccionarPais('<s:property value='#attr.row.nompais'/>');" >
					<s:property value='#attr.row.nompais'/>
				</a>
			</display:column>>
		</display:table>
	</s:if>
	<s:elseif test="provincias != null && provincias.size() > 0">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Sugerencia Provincias</span>
					</td>
				</tr>
			</table>
		</div>
		<display:table name="provincias" excludedParams="*" class="tablacoin" uid="row" summary="Listado posibles provincias" cellspacing="0" cellpadding="0">
			<display:column title="Nombre Provincia" style="width:8%">
				<a href="javascript:seleccionarProvincia('<s:property value='#attr.row.nomProvincia'/>');" >
					<s:property value='#attr.row.nomProvincia'/>
				</a>
			</display:column>
		</display:table>
	</s:elseif>
	<s:elseif test="poblaciones != null && poblaciones.size() > 0">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Sugerencia Poblaciones</span>
					</td>
				</tr>
			</table>
		</div>
		<display:table name="poblaciones" excludedParams="*" class="tablacoin" uid="row" summary="Listado posibles poblaciones" cellspacing="0" cellpadding="0">
			<display:column title="Nombre Población" style="width:8%">
				<a href="javascript:seleccionarPoblacion('<s:property value='#attr.row.nomPoblacion'/>');" >
					<s:property value='#attr.row.nomPoblacion'/>
				</a>
			</display:column>
		</display:table>
	</s:elseif>
	<s:elseif test="viales != null && viales.size() > 0">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Sugerencia Nombre Vías</span>
					</td>
				</tr>
			</table>
		</div>
		<display:table name="viales" excludedParams="*" class="tablacoin" uid="row" summary="Listado posibles nombres de vía" cellspacing="0" cellpadding="0">
			<display:column title="Nombre Vía" style="width:8%">
				<a href="javascript:seleccionarVial('<s:property value='#attr.row.nomVial'/>');" >
					<s:property value='#attr.row.nomVial'/>
				</a>
			</display:column>
		</display:table>
	</s:elseif>
	<s:elseif test="numeros != null && numeros.size() > 0">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Sugerencia Números</span>
					</td>
				</tr>
			</table>
		</div>
		<display:table name="numeros" excludedParams="*" class="tablacoin" uid="row" summary="Listado posibles números" cellspacing="0" cellpadding="0">
			<display:column title="Número" style="width:8%">
				<a href="javascript:seleccionarNumero('<s:property value='#attr.row.numApp'/>');" >
					<s:property value='#attr.row.numApp'/>
				</a>
			</display:column>
		</display:table>
	</s:elseif>