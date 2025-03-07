<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Listado de edificios</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td>
			<display:table name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificiosAlta" class="tablacoin"
						uid="tablaEdificiosAlta" 
						id="tablaEdificiosAlta" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
						
				<display:column title="Clase Vía" property="lcDirEdificacionAlta.tipoVia" style="width:23%"/>
				<display:column title="Nombre vía" property="lcDirEdificacionAlta.nombreVia" style="width:23%"/>
				<display:column title="Tipo Numeración" property="lcDirEdificacionAlta.tipoNumeracion" style="width:23%"/>
				<display:column title="Número Calle" property="lcDirEdificacionAlta.numCalle" style="width:23%"/>
				
				<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
					<display:column title="Eliminar" style="width:10%">
						 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este Edificio Alta?', 'formData', 'borrarInfoEdificioAltaLicenciaAltaSolicitudLicencia.action?identificador=${tablaEdificiosAlta.idInfoEdificioAlta}\u0023AltaEdificacionLC', null);">
						 Eliminar </a>
					</display:column>
					<display:column style="width:3%">
						<a href="javascript:doPost('formData', 'cargarDivInfoEdificioAltaLicenciaAltaSolicitudLicencia.action?identificador=${tablaEdificiosAlta.idInfoEdificioAlta}\u0023AltaEdificacionLC');">
							<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
						</a>
					</display:column>
				</s:if>
			</display:table>
		</td>
	</tr>
</table>

