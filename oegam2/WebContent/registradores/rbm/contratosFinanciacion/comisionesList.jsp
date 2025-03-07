<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.datosFinanciero.comisiones" uid="tablaComisiones" class="tablacoin" id="tablaComisiones"
	summary="Listado de comisiones" cellspacing="0" cellpadding="0" style="width:95%">
	
	<display:column title="Tipo">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.TipoComision@convertirTexto(#attr.tablaComisiones.tipoComision)}" />
	</display:column>
	
	<display:column title="Porcentaje" property="porcentaje" />
	<display:column title="Importe min." property="importeMinimo" />
	<display:column title="Importe max." property="importeMaximo" />
	<display:column title="Financiado">
		<s:property value="#attr.tablaComisiones.financiado=='true'?'Si':'No'"/>
	</display:column>
	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar esta comisión?', 'formData', 'borrarComisionFinanciacion.action?identificador=<s:property value="#attr.tablaComisiones.idComision"/>', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:aniadirComision('Financiacion', 'formData','cargarPopUpComisionRegistroFinanciacion.action?identificador=${tablaComisiones.idComision}')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
