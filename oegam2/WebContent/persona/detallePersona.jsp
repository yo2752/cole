<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" cellSpacing="0" cellPadding="5">
		<tr>
			<td class="tabular">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
					<span class="titulo">Personas: Modificación de Personas</span>
				</s:if>
				<s:else>
					<span class="titulo">Personas: Detalle Persona</span>
				</s:else>
			</td>
		</tr>
	</table>
</div>

<!-- Pestañas -->

<ol id="toc">
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
		<li><a href="#Modificar">Modificación Persona</a></li>
	</s:if>
	<s:else>
		<li><a href="#Modificar">Detalle Persona</a></li>
	</s:else>
	<li class="current"><a href="#DireccionesExpedientes">Direcciones - Expedientes</a></li>
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<s:hidden name="personaModificar.direccion.idDireccion"></s:hidden>
	<s:hidden name="personaModificar.estado"></s:hidden>
	<s:hidden name="personaModificar.tipoPersona"></s:hidden>
	<s:hidden name="personaModificar.numColegiado"></s:hidden>

	<s:hidden name="personaModificar.fechaNacimiento.hora"></s:hidden>
	<s:hidden name="personaModificar.fechaNacimiento.minutos"></s:hidden>
	<s:hidden name="personaModificar.fechaNacimiento.segundos"></s:hidden>

	<s:hidden name="nif"></s:hidden>
	<s:hidden name="numColegiado"></s:hidden>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
		<%@include file="../../persona/detallePersonaGeneral.jsp" %>
	</s:if>
	<s:else>
		<%@include file="../../persona/detallePersonaGeneralDes.jsp" %>
	</s:else>

	<%@include file="../../persona/detalleDireccionesExpedientes.jsp" %>

	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
		src="calendario/ipopeng.htm" scrolling="no" frameborder="0"
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
</s:form>

<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>