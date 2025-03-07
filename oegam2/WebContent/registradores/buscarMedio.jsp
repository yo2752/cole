<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">

	function actualizarPadre(idMedio,tipoMedio,descripcion){
		window.document.formData.idMedio.value = idMedio;
		window.document.formData.tipoMedio.value = tipoMedio;
		window.document.formData.descripcionMed.value=descripcion;
		$("#divEmergentePopUp").dialog("close");
	}

</script>

	
		<table cellSpacing="0" cellPadding="5" width="100%">
			<tr>
				<td class="tabular">
				<span class="titulo">Medios: B&uacute;squeda de Medios</span>
				</td>
			</tr>
		</table>


	<div id="busqueda" class="busqueda">
		<table border="0" class="tablaformbasicaTC">
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="tipoMedioBuscar">Tipo de Medio:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTiposMedio()"
						id="tipoMedioBuscar" onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="consultaMedioBean.tipoMedio"
						listValue="nombreEnum" listKey="valorEnum" 	title="Tipos de Medio" headerKey="-1" headerValue="TODOS"
						cssStyle="text-align:left;"/>
				</td>
				<td align="right" nowrap="nowrap">
					<label for="descripcion">Descripci&oacute;n:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:textfield name="consultaMedioBean.descMedio" id="descripcion" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						size="40" maxlength="40" cssStyle="text-align:left;" />
				</td>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
		</table>
		
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarMedio();"/>			
				</td>
				<td>
					<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposMedios();"/>			
				</td>
			</tr>
		</table>
	
	
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
	
	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 94%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>
	
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%" class="tablaformbasicaTC">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									id="idResultadosPorPagina" value="resultadosPorPagina"
									title="Resultados por página" onchange="cambiarElementosPorPaginaConsulta('navegarMedio.action', 'displayTagDiv', this.value);" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
		
	<div id="displayTagDiv" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" requestURI="navegarMedio.action"
			class="tablacoin" uid="row" summary="Listado de Medios" cellspacing="0" cellpadding="0">
				<display:column property="descMedio" title="Descripción" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:8%" />
				<display:column property="tipoMedio" title="Tipo Medio" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:8%" />
				<display:column style="width:8%">
					<a onclick="" href="javascript:actualizarPadre('${row.idMedio}','${row.tipoMedio}','${row.descMedio}');"> Añadir </a>
				</display:column>
		</display:table>
	</div>
</div>
