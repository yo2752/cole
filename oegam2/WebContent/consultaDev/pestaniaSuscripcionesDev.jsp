<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta Dev</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Resultado Consulta Dev</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<display:table name="consultaDev.suscripciones" class="tablacoin"
							uid="tablaSuscripcionesDev" id="tablaSuscripcionesDev" summary="" 
							excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
							
					<display:column property="idSuscripcion" title="" media="none" paramId="idSuscripcionDev"/>
					
					<display:column title="Datos Personales" property="datosPersonales" sortable="false"
						headerClass="sortable" defaultorder="descending" style="width:6%"/>
					
					<display:column title="Emisor" property="emisor" sortable="false"
						headerClass="sortable" defaultorder="descending" style="width:6%"/>
						
					<display:column title="Cod.Procedimiento" property="codProcedimiento" sortable="false"
						headerClass="sortable" defaultorder="descending" style="width:1%"/>
						
					<display:column title="Desc.Procedimiento" property="descProcedimiento" sortable="false"
						headerClass="sortable" defaultorder="descending" style="width:8%"/>
								
					<display:column property="fechaSuscripcion" title="Fecha Suscripción" sortable="false" 
					headerClass="sortable" defaultorder="descending" maxLength="22" style="width:4%" format="{0,date,dd/MM/yyyy}" />
					
					<display:column title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%">
						<s:property value="%{@org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoSuscripcion@convertirTexto(#attr.tablaSuscripcionesDev.codEstado)}" />
					</display:column>
					
				</display:table>
			</td>
		</tr>
	</table>
</div>