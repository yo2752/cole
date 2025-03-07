<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">
	
	<%@include file="vendedor.jsp"%>	 
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Vendedores</span>
			</td>
		</tr>
	</table>
	 
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="center">
		<tr>
			<td>
				<display:table name="tramiteRegRbmDto.vendedores" uid="vendedores" class="tablacoin" id="vendedores"
					summary="Listado de vendedores" cellspacing="0" cellpadding="0" style="width:95%">
					<display:column title="Tipo" style="width:23%">
						<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro@convertirTextoDesdeXML(#attr.vendedores.tipoPersona)}" />
					</display:column>
					<display:column title="Nif/Cif" property="persona.nif" style="width:23%"/>
					<display:column title="Nombre" property="persona.nombre" style="width:23%"/>
					<display:column title="Apellido" property="persona.apellido1RazonSocial" style="width:23%" />
					<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
						<display:column title="Eliminar" style="width:10%">
							 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este vendedor?', 'formData', 'borrarVendedorFinanciacion.action?idInterviniente=${vendedores.idInterviniente}\u0023tab4', null);">
							 Eliminar </a>
						</display:column>
						<display:column style="width:3%">
							<a href="javascript:doPost('formData', 'modificarVendedorFinanciacion.action?idInterviniente=${vendedores.idInterviniente}\u0023tab4');">
								<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
							</a>
						</display:column>
					</s:if>
				</display:table>
			</td>
		</tr>
	</table>
</div>
