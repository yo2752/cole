<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.clausulas" uid="tablaClausulas" class="tablacoin" id="tablaClausulas"
	summary="Listado de clausulas" cellspacing="0" cellpadding="0" style="width:95%">
	<display:column title="Tipo">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.TipoClausula@convertirTexto(#attr.tablaClausulas.tipoClausula)}" />
	</display:column>
	<display:column title="Número" property="numero" />
	<display:column title="Nombre" property="nombre" />
	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar esta cláusula?', 'formData', 'borrarClausulaLeasing.action?identificador=<s:property value="#attr.tablaClausulas.idClausula"/>\u0023tab5', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:aniadirClausula('Leasing', 'formData','cargarPopUpClausulaRegistroLeasing.action?identificador=${tablaClausulas.idClausula}\u0023tab5')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
