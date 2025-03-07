<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolucion Consulta Inteve</span>
				</td>
			</tr>
		</table>
	</div>
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											id="idResultadosPorPaginaEvolInteve"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvolucionInteve();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagEvolucionInteveDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvolucionInteve" requestURI= "navegarEvolucionInteve.action"
				id="tablaEvolucionInteve" summary="Listado Evolucion Consulta Inteve"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0">	
				
			<display:column property="id.numExpediente" title="Num.Expediente" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:2%"/>
				
			<display:column title="Tipo Actuacion" property="tipoActuacion" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%"/>		
			<display:column property="id.fechaCambio" title="Fecha Cambio" sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" 
				style="width:2%" format="{0,date,dd-MM-yyyy HH:mm:ss}" sortProperty="fechaCambio"/>
			
			<display:column title="Estado anterior" sortProperty="estadoAnt" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%">
					<s:property 
						value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.EstadoTramiteSolicitudInformacion@convertirTexto(#attr.tablaEvolucionInteve.estadoAnt)}" />
			</display:column>
			
			<display:column title="Estado nuevo" sortProperty="estadoNuevo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%">
				<s:property 
						value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.EstadoTramiteSolicitudInformacion@convertirTexto(#attr.tablaEvolucionInteve.estadoNuevo)}" />
			</display:column>
			
			<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true"  headerClass="sortable" 
				defaultorder="descending" style="width:5%"/>

		</display:table>
	</div>
</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvolucionInteveDiv").displayTagAjax();
		});
	</script>
	<script type="text/javascript">
	
	function cambiarElementosPorPaginaEvolucionInteve(){
		var $dest = $("#displayTagEvolucionInteveDiv");
		$.ajax({
			url:"navegarEvolucionInteve.action",
			data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolInteve").val(),
			type:'POST',
			success: function(data){
				filteredResponse =  $(data).find($dest.selector);
				if(filteredResponse.size() == 1){
					$dest.html(filteredResponse);
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar las consulta de tramites.');
		    }
		});
	}
	
</script>	