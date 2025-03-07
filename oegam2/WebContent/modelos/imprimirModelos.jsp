<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Impresión Modelos</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js"
	src="calendario/ipopeng.htm"
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div>
	<s:form method="post" id="formData" name="formData">
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Listado Modelos 600/601</td>
			</tr>
		</table>
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<s:if test="%{lista.getFullListSize() > @escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
												id="idResultadosPorPagina"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaImprimirModelos();">
									</s:select>
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
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaImprimirModelos" requestURI= "navegarImpMd.action"
					id="tablaImprimirModelos" summary="Listado de Modelos 600/601"
					excludedParams="*" sort="list"
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">

				<display:column property="numExpediente" title="Nº Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" paramId="numExpediente"/>

				<display:column property="modelo.modelo" title="Modelo" sortable="true" headerClass="sortable "
					defaultorder="descending" style="width:4%"/>

				<display:column title="Estado" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%">
					<s:property value="%{@org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos@convertirEstadoBigDecimal(#attr.tablaImprimirModelos.estado)}" />
				</display:column>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosModelos(this)'
						onKeyPress='this.onClick'/>" style="width:4%" >
						<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaImprimirModelos.numExpediente"/>"
							value='<s:property value="#attr.tablaImprimirModelos.numExpediente"/>' checked="checked"/>
					</display:column>
			</display:table>
		</div>
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Impresos de Modelos 600/601</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td width="2%"></td>
				<td></td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="idRdCartaPago" value="1" checked/>
				</td>
				<td align="left" style="vertical-align:bottom">
					<label for="lCartaPago">Carta de Pago</label>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="idRdDiligencia" value="2"/>
				</td>
				<td align="left" style="vertical-align:bottom">
					<label for="lDiligencia">Diligencia</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="idRdEscritura" value="3"/>
				</td>
				<td align="left" style="vertical-align:bottom">
					<label for="lDiligencia">Escritura</label>
				</td>
			</tr>
		</table>
		<div class="acciones center">
			<input type="button" class="boton" name="bImprimir" id="bImprimir" value="Imprimir"  onkeypress="this.onClick" onclick="return imprimirModelos();"/>
			<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver"  onkeypress="this.onClick" onclick="return volverModelos();"/>
		</div>
	</s:form>
</div>