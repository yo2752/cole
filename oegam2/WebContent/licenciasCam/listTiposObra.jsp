<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="lcTramiteDto.lcObra.tiposObra" uid="tablaTiposObra" class="tablacoin" id="tablaTiposObra"
	summary="Listado de tipos obra" cellspacing="0" cellpadding="0" style="width:65%">
	
	<display:column title="Tipo obra" style="width:60%">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().obtenerDescripcionTipoObra(#attr.tablaTiposObra)}" />
	</display:column>
	<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
		<display:column title="Eliminar" style="width:10%">
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este tipo?', 'formData', 'borrarTipoObraAltaSolicitudLicencia.action?identificadorTipoObra=${tablaTiposObra}\u0023DatosObraLC');">
			 Eliminar </a>
		</display:column>
	</s:if>
</display:table>
