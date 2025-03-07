<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.datosFinanciero.cuadrosAmortizacionGE" uid="tablaCuadroAmortizacion" class="tablacoin" id="tablaCuadroAmortizacion"
	summary="Listado de cuadros de amortización" cellspacing="0" cellpadding="0" style="width:95%">
	
	<display:column title="Fec. vencimiento" property="fechaVencimiento" format="{0, date, dd/MM/yyyy}" />
	<display:column title="Imp. plazo" property="impPlazo" />
	<display:column title="Cap. Amortizado" property="impCapitalAmortizado" />
	<display:column title="Int. plazo" property="impInteresesPlazo" />
	<display:column title="Devengados" property="impInteresDevengado" />
	<display:column title="Pendiente" property="impCapitalPendiente" />

	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este cuadro de amortización?', 'formData', 'borrarCuadroAmortizacionFinanciacion.action?identificador=<s:property value="#attr.tablaCuadroAmortizacion.idCuadroAmortizacion"/>', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:aniadirCuadro('Financiacion', 'formData','cargarPopUpCuadroAmortizacionRegistroFinanciacion.action?identificador=${tablaCuadroAmortizacion.idCuadroAmortizacion}', 'btnAniadirCuadroFinanciacion')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
