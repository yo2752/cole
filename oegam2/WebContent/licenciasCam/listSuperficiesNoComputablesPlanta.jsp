<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Listado de Superficies No Computables Alta</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td>
			<display:table name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.lcSupNoComputablesPlanta" class="tablacoin"
						uid="tablaSuperficiesNoComputablesPlanta" 
						id="tablaSuperficiesNoComputablesPlanta" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
						
				<display:column title="Tipo" sortable="true" headerClass="sortable" defaultorder="descending">
					<s:property value="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().obtenerDescripcionSuperficieNoComputable(#attr.tablaSuperficiesNoComputablesPlanta.tipo)}" />
				</display:column>
				
				<display:column title="Tamaño" property="tamanio"/>
				
				<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
					<display:column title="Eliminar" style="width:10%">
						 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar esta superficie no computable alta?', 'formData', 'borraSupNoComputablePlantaAltaSolicitudLicencia.action?identificador=${tablaSuperficiesNoComputablesPlanta.idSupNoComputablePlanta}\u0023AltaEdificacionLC', null);">
						 Eliminar </a>
					</display:column>
					<display:column style="width:3%">
						<a href="javascript:doPost('formData', 'cargarDivSupNoComputablePlantaAltaSolicitudLicencia.action?identificador=${tablaSuperficiesNoComputablesPlanta.idSupNoComputablePlanta}\u0023AltaEdificacionLC');">
							<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
						</a>
					</display:column>
				</s:if>
			</display:table>
		</td>
	</tr>
</table>
