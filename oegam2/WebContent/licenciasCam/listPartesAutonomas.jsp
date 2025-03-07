<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="lcTramiteDto.lcObra.partesAutonomas" uid="tablaPartesAutonomas" class="tablacoin" id="tablaPartesAutonomas"
	summary="Listado de Partes Autónomas" cellspacing="0" cellpadding="0"  style="width:95%">
	<display:column title="Número" property="numero" style="width:15%"/>
	<display:column title="Descripción" property="descripcion" style="width:85%"/>
	
	<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
		<display:column title="Eliminar" style="width:10%">
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar esta parte autónoma?', 'formData', 'borrarParteAutonomaAltaSolicitudLicencia.action?identificador=${tablaPartesAutonomas.idParteAutonoma}\u0023DatosObraLC', null);">
			 Eliminar </a>
		</display:column>
		<display:column style="width:3%">
			<a href="javascript:doPost('formData', 'modificarParteAutonomaAltaSolicitudLicencia.action?identificador=${tablaPartesAutonomas.idParteAutonoma}\u0023DatosObraLC');">
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
