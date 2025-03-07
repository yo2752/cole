<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Importar tasas</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="left" nowrap="nowrap" width="8%">
					<label>Estados: </label>
				</td>
				<td align="left">
					<s:select
						list="@org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@values()"
						name="estado" id="nuevoEstadoCompra" headerKey=""
						headerValue="Seleccione estado" listKey="codigo"
						listValue="descripcion"  cssStyle="width:225px"/>
				</td>
			</tr>
		</table>
	</div>
</div>
