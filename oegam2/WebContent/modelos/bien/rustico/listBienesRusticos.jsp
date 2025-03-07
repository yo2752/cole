<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Listado de Bienes Rústicos</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td>
			<display:table name="modeloDto.listaBienesRusticos" class="tablacoin"
						uid="tablaBienesRusticos" 
						id="tablaBienesRusticos" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
						
				<display:column property="bien.idBien" title="" media="none" paramId="idBien"/>
				
				<display:column property="bien.nombreBien" title="Nombre Bien" style="width:4%" />

				<display:column property="bien.paraje" title="Paraje" style="width:4%" />
				<display:column property="bien.poligono" title="Polígono" style="width:4%" />
					
				<display:column property="bien.parcela" title="Parcela" style="width:4%"/>
				
				<display:column property="bien.refCatrastal" title="Ref.Catrastal" style="width:4%" />
				
				<display:column property="valorDeclarado" title="Valor Declarado" style="width:4%" />
				
				<display:column property="transmision" title="Porctj. Transmisión" style="width:4%" />
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosBienesRusticos(this)' 
					onKeyPress='this.onClick'/>" style="width:4%" >
					<input type="checkbox" name="listaChecksBienRustico" id="check<s:property value="#attr.tablaBienesRusticos.bien.idBien"/>" 
						value='<s:property value="#attr.tablaBienesRusticos.bien.idBien"/>' />
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<br/>
<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGeneradaRemesa(modeloDto) &&
				@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esGuardable(modeloDto)}">
	<div class="acciones center">
		<input type="button" class="boton" name="bConsultarBienesRustico" id="idConsultarBienesRustico" value="Buscar Bien" onClick="javascript:buscarBienes('RUSTICO');"onKeyPress="this.onClick"/>	
		<input type="button" class="boton" name="bEliminarBienesRustico" id="idEliminarBienesRustico" value="Eliminar" onClick="javascript:eliminarBien('RUSTICO');"onKeyPress="this.onClick"/>
		<input type="button" class="boton" name="bModificarBienesRustico" id="idModificarBienesRustico" value="Modificar" onClick="javascript:modificarBienes('bienesRusticos','RUSTICO');"onKeyPress="this.onClick"/>	
	</div>
</s:if>
<br/>
