<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="contenido">


	<div id="divDatosPersona">
		<jsp:include page="../conductor/datosPersonaConductor.jsp"
			flush="false"></jsp:include>
	</div>
	<div class="acciones center">

		<s:if
			test="%{@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().esEstadoGuardable(conductorDto)}">


			<input type="button" class="boton" name="blimpiarSujetoPasivo"
				id="idLimpiarConductor" value="Limpiar Persona"
				onClick="javascript:limpiarFormularioPersonaConductor();"
				onKeyPress="this.onClick" style='display: none;' />
		</s:if>
	</div>
</div>



