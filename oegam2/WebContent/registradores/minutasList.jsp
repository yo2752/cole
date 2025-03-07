<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegistro.minutasRegistro" uid="tablaMinutas" class="tablacoin" id="tablaMinutas"
	summary="Listado de minutas" cellspacing="0" cellpadding="0"  style="width:95%">
	<display:column title="Aceptada">
		<s:property value="#attr.tablaMinutas.aceptada=='true'?'Si':'No'"/>
	</display:column>
	<display:column title="Número minuta" property="numeroMinuta" style="width:15%" />
	<display:column title="Fecha minuta" property="fechaMinuta" format="{0, date, dd/MM/yyyy}" style="width:15%" />
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<display:column title="Eliminar" style="width:10%">
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar esta confirmacción de minuta?', 'formData', 'borrarMinutaTramiteRegistroMd6.action?idMinuta=${tablaMinutas.idMinuta}\u0023formaPago', null);">
			 Eliminar </a>
		</display:column>
		<display:column style="width:3%">
			<a href="javascript:doPost('formData', 'modificarMinutaTramiteRegistroMd6.action?idMinuta=${tablaMinutas.idMinuta}\u0023formaPago');">
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
