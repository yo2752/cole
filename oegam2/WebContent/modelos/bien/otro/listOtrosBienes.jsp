<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Listado de otros bienes</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td>
			<display:table name="modeloDto.listaOtrosBienes" class="tablacoin"
						uid="tablaOtrosBienes" 
						id="tablaOtrosBienes" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
						
				<display:column property="bien.idBien" title="" media="none" paramId="idBien"/>
				
				<display:column property="transmision" title="Porctj. transmisión" style="width:4%" />

				<display:column property="bien.refCatrastal" title="Ref.Catrastal" style="width:4%" />
				
				<display:column property="valorDeclarado" title="Valor declarado" style="width:4%" />
				
				<s:if test="modeloDto.modelo.modelo == '601'">
					<display:column property="valorTasacion" title="Valor tasación" style="width:4%" />
				</s:if>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosOtrosBienes(this)' 
					onKeyPress='this.onClick'/>" style="width:4%" >
					<input type="checkbox" name="listaChecksOtroBien" id="check<s:property value="#attr.tablaOtrosBienes.bien.idBien"/>" 
						value='<s:property value="#attr.tablaOtrosBienes.bien.idBien"/>' />
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<br/>
<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGeneradaRemesa(modeloDto) &&
				@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
	<div class="acciones center">
		<input type="button" class="boton" name="bConsultarOtrosBienes" id="idConsultarOtrosBienes" value="Buscar Bien" onClick="javascript:buscarBienes('OTRO');"onKeyPress="this.onClick"/>
		<input type="button" class="boton" name="bEliminarOtrosBienes" id="idEliminarOtrosBienes" value="Eliminar" onClick="javascript:eliminarBien('OTRO');"onKeyPress="this.onClick"/>
		<input type="button" class="boton" name="bModificarOtrosBienes" id="idModificarOtrosBienes" value="Modificar" onClick="javascript:modificarBienes('divOtroBien','OTRO');"onKeyPress="this.onClick"/>	
	</div>
</s:if>
<br/>
