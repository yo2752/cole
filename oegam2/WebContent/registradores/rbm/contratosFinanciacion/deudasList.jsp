<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.datosFinanciero.reconocimientoDeudas" uid="tablaReconocimientoDeuda" class="tablacoin" id="tablaReconocimientoDeuda"
	summary="Listado de deudas" cellspacing="0" cellpadding="0" style="width:95%">
	<display:column title="Importe" property="impReconocido" />
	<display:column title="Núm. plazos" property="numPlazos" />
	<display:column title="Imp. plazos" property="impPlazos" />
	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este Reconocimiento de Deuda?', 'formData', 'borrarReconocimientoDeudaFinanciacion.action?identificador=<s:property value="#attr.tablaReconocimientoDeuda.idReconocimientoDeuda"/>', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:aniadirDeuda('Financiacion', 'formData','cargarPopUpReconocimientoDeudaRegistroFinanciacion.action?identificador=${tablaReconocimientoDeuda.idReconocimientoDeuda}')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
