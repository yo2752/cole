<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<style>
	.hidden { display: none; }
</style>

<script type="text/javascript">
$('document').ready(function(){
	var $tdRowGris = $('table#tablaEvolucionMateriales tr.gris td');
	$tdRowGris.css({
		   'background-color' : 'grey',
		   'color' : 'white'
		});

});

function mostrarEvoDescripcion(EvolucionMaterialId) {
	var url = '/oegam2/json/obtenerEvolucionAjax.action';
	var textToShow;
	
    $.ajax({
        data:  {'evolucion' : EvolucionMaterialId },
        url:   url,
        type:  'post',
        async: false,
        success:  function (response) {
        	//alert(response);
        	textToShow = response;
        }
    });
    
    $.alert({
    	boxWidth: '30%',
    	useBootstrap: false,
        title: 'Descripción',
        content: textToShow
    });

}


</script>
<s:set var="selEvoMatId" value="selEvoMatId"/>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolución Consulta Material </span>
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
								<label for="idResultadosPorPaginaEvolucion">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											id="idResultadosPorPaginaEvolucion"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvolucion();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagEvolucionDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvolucionMateriales" id="tablaEvolucionMateriales" 
				requestURI= "navegarEvolucionMateriales.action" 
				summary="Listado Evolucion Materiales"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0"
				decorator="${decorator}">	
				
				<display:column property="evolucionMaterialId" class="hidden" headerClass="hidden" />
					
				<display:column property="name" title="Nombre" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:3%"/>

				<display:column property="tipo" title="Tipo" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%"/>

				<display:column title="Descripción" style="width:1%">
					<input type="button" class="boton" name="bObtenerDescripcion" 
				           id="bBuscar_'<s:property value="#attr.tablaEvolucionMateriales.evolucionMaterialId"/>'" 
				           value="Ver Descripción" onkeypress="this.onClick"
					       onclick="javascript:mostrarEvoDescripcion(<s:property value="#attr.tablaEvolucionMateriales.evolucionMaterialId"/>);" />
   				</display:column>

				<display:column property="precio" title="Precio" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" 
					format="{0, number, #0.0000} €"/>

				<display:column property="color" title="Color" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />

				<display:column property="estado" title="Estado" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />

				<display:column property="fechaAlta" title="Fecha" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%"
					format="{0, date, dd/MM/yyyy}" />

		</display:table>
	</div>
</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvolucionDiv").displayTagAjax();
		});
	</script>	