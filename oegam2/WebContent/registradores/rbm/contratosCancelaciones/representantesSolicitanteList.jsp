<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.representantesSolicitante" uid="representantesSolicitante" class="tablacoin" id="representantesSolicitante"
	summary="Listado de deudas" cellspacing="0" cellpadding="0" style="width:95%">
	
	<display:column title="Tipo" style="width:22%">
		<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro@convertirTextoDesdeXML(#attr.representantesSolicitante.tipoPersona)}" />
	</display:column>
	
	<display:column title="Nif/Cif" property="persona.nif" style="width:22%"/>
	<display:column title="Nombre" property="persona.nombre" style="width:22%"/>
	<display:column title="Apellido" property="persona.apellido1RazonSocial" style="width:22%"/>
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">
			<a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este representante?', 'formData', 'borrarRepresentanteSolicitanteCancelacion.action?idRepresentanteInterviniente=${representantesSolicitante.idInterviniente}\u0023tab2', null);">
			Eliminar </a>
		</display:column>
		<display:column style="width:3%">
			<a href="javascript:aniadirRepresentanteSolicitante('Cancelacion','formData','cargarPopUpRepresentanteSolicitanteRegistroCancelacion.action?idRepresentanteInterviniente=${representantesSolicitante.idInterviniente}\u0023tab2');">
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
