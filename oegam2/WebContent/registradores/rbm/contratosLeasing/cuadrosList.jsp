<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.datosFinanciero.cuadrosAmortizacionGE" uid="tablaCuadroAmortizacion" class="tablacoin" id="tablaCuadroAmortizacion"
	summary="Listado de cuadros de amortización" cellspacing="0" cellpadding="0" style="width:95%">

	<display:column title="Fec. vencimiento" property="fechaVencimiento" format="{0, date, dd/MM/yyyy}" />
	
	<display:column title="Recup. coste" property="impRecupCoste" />
	<display:column title="Coste Pend." property="impRecupCostePte" />
	<display:column title="Carga finac." property="impCargaFinan" />
	<display:column title="Carga neta" property="impCuotaNeta" />
	<display:column title="Imp. impuesto" property="impImpuesto" />

	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este cuadro de amortización?', 'formData', 'borrarCuadroAmortizacionLeasing.action?identificador=<s:property value="#attr.tablaCuadroAmortizacion.idCuadroAmortizacion"/>', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:aniadirCuadro('Leasing', 'formData','cargarPopUpCuadroAmortizacionRegistroLeasing.action?identificador=${tablaCuadroAmortizacion.idCuadroAmortizacion}', 'btnAniadirCuadroLeasing')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
