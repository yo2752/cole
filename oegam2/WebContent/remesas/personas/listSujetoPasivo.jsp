<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Listado Sujetos Pasivos</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td>
			<display:table name="remesa.listaSujetosPasivos" class="tablacoin"
						uid="tablaSujetosPasivos" 
						id="tablaSujetosPasivos" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
				
				<display:column property="idInterviniente" title="" media="none" paramId="idInterviniente"/>		
						
				<display:column property="persona.numColegiado" title="" media="none" paramId="numColegiado"/>
				
				<display:column property="persona.nif" title="NIF/CIF" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
						
				<display:column property="persona.apellido1RazonSocial" title="1er Apellido/Razón Social" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
						
				<display:column property="persona.apellido2" title="2do Apellido" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
						
				<display:column property="persona.nombre" title="Nombre" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<s:if test="%{!@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esConceptoBienes(remesa)}">
					<display:column property="porcentaje" title="Porcentaje" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>
											
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosSujetoPasivo(this)' 
					onKeyPress='this.onClick'/>" style="width:4%" >
					<input type="checkbox" name="listaChecksSujPasivo" id="check<s:property value="#attr.tablaSujetosPasivos.idInterviniente"/>" 
						value='<s:property value="#attr.tablaSujetosPasivos.idInterviniente"/>' />
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<br/>
