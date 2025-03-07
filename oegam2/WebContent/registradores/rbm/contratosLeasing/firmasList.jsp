<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="tramiteRegRbmDto.datoFirmas" uid="tablaDatosFirma" class="tablacoin" id="tablaDatosFirma"
	summary="Listado de datos de firma" cellspacing="0" cellpadding="0" style="width:95%">
	<display:column title="Tipo intervención"  style="width:20%">
		<s:property value="%{@org.gestoresmadrid.oegam2comun.registradores.enums.TipoIntervencion@convertirTexto(#attr.tablaDatosFirma.tipoIntervencion)}" />
	</display:column>
	<display:column title="Lugar" property="lugar" style="width:50%" />
	<display:column title="Fecha" property="fecFirma" format="{0, date, dd/MM/yyyy}" />
	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		<display:column title="Eliminar" style="width:10%">						
			 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar estos datos de firma?', 'formData', 'borrarDatosFirmaLeasing.action?identificador=<s:property value="#attr.tablaDatosFirma.idDatoFirma"/>', null);">
				Eliminar
			</a>
		</display:column>					
		<display:column style="width:3%">
			<a href="javascript:aniadirFirma('Leasing', 'formData','cargarPopUpDatosFirmaRegistroLeasing.action?identificador=${tablaDatosFirma.idDatoFirma}')" >
				<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
			</a>
		</display:column>
	</s:if>
</display:table>
