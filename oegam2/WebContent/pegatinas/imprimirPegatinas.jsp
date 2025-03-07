<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/pegatinasBotones.js" type="text/javascript"></script>

<script src="js/imprimir.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Imprimir Distintivos Medioambientales</span>
				</td>
			</tr>
		</table>
	</div>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<s:form method="post" id="formData" name="formData">
		<table class="subtitulo" cellSpacing="0">
			<tr>
				<td>Tr&aacute;mites seleccionados</td>
			</tr>
		</table>

		<div id="displayTagDiv" class="divScroll">
			<display:table name="listaNotificacion"  class="tablacoin" excludedParams="*" uid="tablaPegatinas" summary="Listado de Tramites" cellspacing="0" cellpadding="0"
					decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorJefatura">

				<display:column property="numExpediente" title="Num. Expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="tipo" title="Tipo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="jefatura" title="Jefatura" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="descrEstado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="fechaAlta" title="Fecha Alta" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" format="{0,date,dd-MM-yyyy}"/>
				<display:column style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksPegatina);' onKeyPress='this.onClick'>">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucion(<s:property value="#attr.tablaPegatinas.idPegatina"/>);" title="Ver evolución"/>
							</td>
							<td>
								<input
									type="checkbox"
									name="listaChecksPegatina"
									id="checkNotificacion"
									value='<s:property value="#attr.tablaPegatinas.numExpediente"/> , <s:property value="#attr.tablaPegatinas.matricula"/> ,
										<s:property value="#attr.tablaPegatinas.tipo"/>'/>
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.numExpediente"/>' name="listaNotificacion[<s:property value="#attr.tablaPegatinas_rowNum"/> - 1].numExpediente" />
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.matricula"/>' name="listaNotificacion[<s:property value="#attr.tablaPegatinas_rowNum"/> - 1].matricula" />
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.tipo"/>' name="listaNotificacion[<s:property value="#attr.tablaPegatinas_rowNum"/> - 1].tipo" />
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.jefatura"/>' name="listaNotificacion[<s:property value="#attr.tablaPegatinas_rowNum"/> - 1].jefatura" />
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.descrEstado"/>' name="listaNotificacion[<s:property value="#attr.tablaPegatinas_rowNum"/> - 1].descrEstado" />
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.fechaAlta"/>' name="listaNotificacion[<s:property value="#attr.tablaPegatinas_rowNum"/> - 1].fechaAlta" />
								<input type="hidden" value='<s:property value="#attr.tablaPegatinas.idPegatina"/>,<s:property value="#attr.tablaPegatinas.matricula"/>,<s:property value="#attr.tablaPegatinas.tipo"/>,Marcada ' name="idPegatina" />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>

		<div id="cuerpoImpresion">
			<%@include file="/pegatinas/includeOpcionesNotificar.jsp" %>
			<%@include file="../../includes/stockPegatinasDisponible.jspf" %>

			<table class="acciones">
				<tr>
					<td>
						<div>
							<input class="boton" type="submit" id="bNotificar" name="bNotificar" value="Notificar" onClick="return notificarTodos();" onKeyPress="this.onClick" />
							&nbsp;
							<input class="boton" type="button" id="bVolver" name="bVolver" value="Volver" onClick="return volverNotificarImpresion(this);" onKeyPress="this.onClick" />
							&nbsp;
						</div>
						<div id="bloqueLoading" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
							<%@include file="../../includes/bloqueLoading.jspf" %>
						</div>
					</td>
				</tr>
			</table>
		</div>

	</s:form>
</div>