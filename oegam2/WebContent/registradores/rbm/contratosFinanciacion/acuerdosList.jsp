<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.pactos" uid="tablaPactos" class="tablacoin" id="tablaPactos"
	summary="Listado de acuerdos" cellspacing="0" cellpadding="0" style="width:95%">
	
	<display:column title="Tipo">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.TipoPacto@convertirTexto(#attr.tablaPactos.tipoPacto)}" />
	</display:column>
	<display:column title="Pactado">
		<s:property value="#attr.tablaPactos.pactado=='true'?'Si':'No'"/>
	</display:column>
	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este acuerdo?', 'formData', 'borrarAcuerdoFinanciacion.action?identificador=<s:property value="#attr.tablaPactos.idPacto"/>\u0023tab7', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:doPost('formData','modificarAcuerdoFinanciacion.action?identificador=${tablaPactos.idPacto}\u0023tab7')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
