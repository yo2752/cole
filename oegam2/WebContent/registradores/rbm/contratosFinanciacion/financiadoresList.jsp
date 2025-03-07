<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	<display:table name="tramiteRegRbmDto.financiadores" uid="financiadores" class="tablacoin" id="financiadores"
		summary="Listado de financiadores" cellspacing="0" cellpadding="0" style="width:95%">
		<display:column title="Nombre" property="persona.nombre" style="width:20%"/>
		<display:column title="Apellido / Razón social" property="persona.apellido1RazonSocial" style="width:45%"/>
		<display:column title="NIF" property="nif" style="width:45%"/>
		<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			<display:column title="Eliminar" style="width:10%">
			 	<a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este Financiador?', 'formData', 'borrarFinanciadorFinanciacion.action?idFinanciadorPadre=${financiadores.idInterviniente}\u0023tab9', null);">
				 Eliminar </a>
			</display:column>
		</s:if>
	</display:table>


