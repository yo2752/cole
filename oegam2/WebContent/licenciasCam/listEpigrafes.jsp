<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="lcTramiteDto.lcInfoLocal.lcEpigrafes" uid="tablaEpigrafes" class="tablacoin" id="tablaEpigrafes"
	summary="Listado de epígrafes" cellspacing="0" cellpadding="0"  style="width:95%">
	<display:column title="Sección" property="seccion"/>
	<display:column title="Epígrafe" sortable="true" headerClass="sortable" defaultorder="descending">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().obtenerDescripcionEpigrafe(#attr.tablaEpigrafes.epigrafe)}" />
	</display:column>
	
	<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
		<display:column title="Eliminar" style="width:10%">
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este epígrafe?', 'formData', 'borrarEpigrafeAltaSolicitudLicencia.action?identificador=${tablaEpigrafes.idEpigrafe}\u0023InformacionLocal', null);">
			 Eliminar </a>
		</display:column>
		<display:column style="width:3%">
			<a href="javascript:doPost('formData', 'modificarEpigrafeAltaSolicitudLicencia.action?identificador=${tablaEpigrafes.idEpigrafe}\u0023InformacionLocal');">
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
