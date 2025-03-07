<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/trafico/materiales/paquetesPedidos.js" type="text/javascript"></script>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Paquetización Pedido</span>
				</td>
			</tr>
		</table>
	</div>

	<%@include file="../../../includes/erroresYMensajes.jspf" %>

	<s:hidden name="paquetePedidoFilterBean.pedidoId" id="idpedidoId" />
	
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelpedPaqueteId">Paquete:</label>
				</td>
				<td  align="left">
					<s:textfield name="paquetePedidoFilterBean.pedPaqueteId"
								 type="number" 
								 id="labelpedPaqueteId" 
					             size="10" maxlength="10"
					             onkeypress="return isNumber(event)" 
					             onblur="this.className='input';" 
						         onfocus="this.className='inputfocus';"/>
				</td>
				
       			<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelEstadoPaquete">Estado Paquete: </label>
				</td>
				<td  align="left" style="width: 25%;">
					<s:select id="labelEstadoPaquete"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getEstadoPedPaquete()"
						headerKey=""
				   		headerValue="Seleccione Estado Paquete"
						name="paquetePedidoFilterBean.estado" 
						listKey="valorEnum" 
						listValue="nombreEnum"/>	
				</td>
			</tr>
		</table>
	
	</div>

	<table class="acciones">
		<tr>
			<td><input type="button" class="boton" name="bBuscar"
				id="bBuscar" value="Buscar" onkeypress="this.onClick"
				onclick="javascript:abrirPaquetesPedido();" /></td>
			<td><input type="button" class="boton" name="bLimpiar"
				onclick="javascript:limpiarPaquetePedidos();" value="Limpiar" />
			</td>
		</tr>
	</table>

	
	<div id="resultado"
		style="width: 95%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 90%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>

	
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="90%">
			<tr>
				<td align="right">
					<table width="90%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPaginaPedidos">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											id="idResultadosPorPaginaPedidos"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaPaquetes();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	
	<div id="displayTagPaquetesDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaPaquetePedido" requestURI= "navegarPaquetesPedido.action"
				id="tablaPaquetesPedido" summary="Listado de Paquetes de un Pedido"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" >

				<display:column property="pedidoId" title="Id" class="hidden" headerClass="hidden" />

				<display:column property="pedPaqueteId" title="Paquete" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%"/>

				<display:column property="numSerieIncial" title="Serial Inicial" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%"/>

				<display:column property="numSerieFin" title="Serial Final" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%"/>

				<display:column property="numSerieActual" title="Serial Actual" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%"/>

				<display:column property="estadoStr" title="Estado" style="width:1%"/>

				<display:column property="fechaBaja" title="Fecha Consumido" style="width:1%"/>

				<display:column property="usuarioBaja" title="Usuario Consumido" style="width:1%"/>
				
		</display:table>
	</div>
</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagPaquetesDiv").displayTagAjax();
		});
	</script>	