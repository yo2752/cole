<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.situacionesJuridicas" uid="tablaSituacionesJuridicas" class="tablacoin" id="tablaSituacionesJuridicas"
	summary="Listado de situaciones jurídicas" cellspacing="0" cellpadding="0"  style="width:50%">
	<display:column title="Situaci&oacute;n" style="width:40%">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.SituacionJuridica@convertirTexto(#attr.tablaSituacionesJuridicas)}" />
	</display:column>
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar esta situación jurídica?', 'formData', 'borrarSituacionJuridicaCancelacion.action?identificador=${tablaSituacionesJuridicas}\u0023tab1', null);">
			 Eliminar </a>
		</display:column>
	</s:if>
</display:table>
