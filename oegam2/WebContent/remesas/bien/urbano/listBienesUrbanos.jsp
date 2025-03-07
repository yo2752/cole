<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Listado Bienes Urbanos</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td>
			<display:table name="remesa.listaBienesUrbanos" class="tablacoin"
						uid="tablaBienesUrbanos" 
						id="tablaBienesUrbanos" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
						
				<display:column property="bien.idBien" title="" media="none" paramId="idBien"/>
				
				<display:column property="bien.nombreBien" title="Nombre Bien"  style="width:4%" />
						
				<display:column property="bien.direccion.nombreProvincia" title="Provincia" style="width:4%" />
					
				<display:column property="bien.direccion.nombreMunicipio" title="Municipio" style="width:4%" />
					
				<display:column property="bien.direccion.nombreVia" title="Nombre Vía" style="width:4%" />
					
				<display:column property="bien.direccion.numero" title="Número" style="width:4%" />
					
				<display:column property="bien.direccion.planta" title="Planta" style="width:4%" />
					
				<display:column property="bien.direccion.escalera" title="Escalera" style="width:4%" />
				
				<display:column property="valorDeclarado" title="Valor Declarado" style="width:4%" />
				
				<display:column property="transmision" title="Porctj. Transmisión" style="width:4%" />
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosBienesUrbanos(this)' 
					onKeyPress='this.onClick'/>" style="width:4%" >
					<input type="checkbox" name="listaChecksBienUrbano" id="check<s:property value="#attr.tablaBienesUrbanos.bien.idBien"/>" 
						value='<s:property value="#attr.tablaBienesUrbanos.bien.idBien"/>' />
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<br/>
<div class="acciones center">
	<input type="button" class="boton" name="bConsultarBienesUrbano" id="idConsultarBienesUrbano" value="Buscar Bien" onClick="javascript:buscarBienes('URBANO');"onKeyPress="this.onClick"/>	
	<input type="button" class="boton" name="bEliminarBienesUrbano" id="idEliminarBienesUrbano" value="Eliminar" onClick="javascript:eliminarBien('URBANO');"onKeyPress="this.onClick"/>
	<input type="button" class="boton" name="bModificarBienesUrbano" id="idModificarBienesUrbano" value="Modificar" onClick="javascript:modificarBienes('bienesUrbanos','URBANO');"onKeyPress="this.onClick"/>	
</div>
<br/>
