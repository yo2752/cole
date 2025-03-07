<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="lcTramiteDto.otrosInteresados" uid="tablaOtrosInteresados" class="tablacoin" id="tablaOtrosInteresados"
	summary="Listado de otros interesados" cellspacing="0" cellpadding="0"  style="width:95%">
	<display:column title="Tipo de sujeto"  style="width:18%;text-align:left;" sortable="false">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().obtenerDescripcionTipoSujeto(#attr.tablaOtrosInteresados.lcPersona.tipoSujeto)}" />
	</display:column>
	<display:column property="lcPersona.nif" title="NIF/CIF" style="width:10%;text-align:left;" sortable="false"/>
	<display:column property="lcPersona.nombre" title="Nombre" style="width:20%;text-align:left;" sortable="false"/>
	<display:column property="lcPersona.apellido1RazonSocial" title="1er apellido/Razón social" style="width:26%;text-align:left;" sortable="false"/>
	<display:column property="lcPersona.apellido2" title="2do apellido" style="width:20%;text-align:left;" sortable="false"/>
	<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
		<display:column title="Eliminar" style="width:10%">
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este interviniente interesado?', 'formData', 'borrarOtroInteresadoAltaSolicitudLicencia.action?identificador=${tablaOtrosInteresados.idInterviniente}\u0023IntervinienteLC', null);">
			 Eliminar </a>
		</display:column>
		<display:column style="width:3%">
			<a href="javascript:doPost('formData', 'modificarOtroInteresadoAltaSolicitudLicencia.action?numDocOtroInteresado=${tablaOtrosInteresados.lcPersona.nif}\u0023IntervinienteLC');">
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
