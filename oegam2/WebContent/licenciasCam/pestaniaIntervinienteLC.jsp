<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de los Intervinientes</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td  align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');" title="Ver evolución"/> 					
				</td>
			</s:if>
		</tr>
	</table>
	<div id="divDatosInteresadoP">
		<jsp:include page="../licenciasCam/datosInteresadoPrincipal.jsp"
			flush="false"></jsp:include>
	</div>
	<div id="divDatosRepresentante">
		<jsp:include page="../licenciasCam/datosRepresentante.jsp"
			flush="false"></jsp:include>
	</div>
	<div id="divDatosOtrosInteresados">
		<jsp:include page="../licenciasCam/datosOtrosInteresados.jsp"
			flush="false"></jsp:include>
	</div>
</div>
