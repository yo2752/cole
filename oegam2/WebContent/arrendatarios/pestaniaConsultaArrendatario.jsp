<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="contenido">
	<div id="divDatosPersona">
		<jsp:include page="../arrendatarios/datosPersonaArrendatario.jsp"
			flush="false"></jsp:include>
	</div>
	<div class="acciones center">
		<s:if
			test="%{@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().esEstadoGuardable(arrendatarioDto)}">
			<input type="button" class="boton" name="blimpiarSujetoPasivo"
				id="idLimpiarArrendatario" value="Limpiar Persona"
				onClick="javascript:limpiarFormularioPersonaArrendatario();"
				onKeyPress="this.onClick" style='display: none;' />
		</s:if>
	</div>

</div>