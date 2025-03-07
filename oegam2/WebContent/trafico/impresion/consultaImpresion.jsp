<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/impresion/impresion.js" type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo"> Consulta de Impresiones </span></td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf"%>
<s:form method="post" id="formData" name="formData">
	<s:hidden name="nombreFicheroDescargar"/>
	<div class="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td colspan="3">
					<table>
						<tr>
							<td align="left"><label for="lbFechaDesde">Fecha Creación desde: </label></td>
							<td>
								<s:textfield name="impresionFilterBean.fechaFiltrado.diaInicio"
									id="diaInicioCreacion" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="impresionFilterBean.fechaFiltrado.mesInicio"
									id="mesInicioCreacion" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="impresionFilterBean.fechaFiltrado.anioInicio"
									id="anioInicioCreacion" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioCreacion, document.formData.mesInicioCreacion, document.formData.diaInicioCreacion);return false;" title="Seleccionar fecha"> 
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td align="left"><label for="lbFechaHasta">Fecha Creación Hasta: </label></td>

							<td>
								<s:textfield name="impresionFilterBean.fechaFiltrado.diaFin"
									id="diaFinCreacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" /></td>
							<td>/</td>
							<td>
								<s:textfield name="impresionFilterBean.fechaFiltrado.mesFin"
									id="mesFinCreacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="impresionFilterBean.fechaFiltrado.anioFin"
									id="anioFinCreacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinCreacion, document.formData.mesFinCreacion, document.formData.diaFinCreacion);return false;" title="Seleccionar fecha"> 
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left"><label for="lbNombreFich">Nombre Documento:</label></td>
				<td>
					<s:textfield name="impresionFilterBean.nombreDocumento" id="nombreDocumento" align="left"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" readonly="false" size="30" maxlength="50" />
				</td>

				<td align="left"><label for="lbTipoDoc">Tipo Documento: </label></td>
				<td>
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoDocumentoImpresiones()"
						name="impresionFilterBean.tipoDocumento" id="tipoDocumento" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Cualquier Tipo" listKey="valorEnum" listValue="nombreEnum" />
				</td>
			</tr>
			<tr>
				<td align="left"><label for="lbEstadoImpr">Estado: </label></td>
				<td>
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoImprimir()"
						name="impresionFilterBean.estado" id="estado" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Cualquier Tipo" listKey="valorEnum" listValue="nombreEnum" />
				</td>
				<s:if test="%{#isAdmin==true}">
					<td align="left"><label for="lbIdContr">Contrato: </label></td>
					<td>
						<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
							listValue="descripcion" cssStyle="width:220px" name="impresionFilterBean.idContrato"/>
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left"><label for="lbTipoTramiteImpr">Tipo Trámite: </label></td>
				<td>
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramiteImpresion()"
						name="impresionFilterBean.tipoTramite" id="tipoTramite" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Cualquier Tipo" listKey="valorEnum" listValue="nombreEnum" />
				</td>
				<td align="left"><label for="lbNombreFich">NºExpediente:</label></td>
				<td>
					<s:textfield name="impresionFilterBean.numExpediente" id="numExpediente" align="left"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" readonly="false" size="30"	maxlength="50" />
				</td>
			</tr>
		</table>
		<div class="centrado espArriba">
			<table align="center">
				<tr>
					<td>
						<input type="button" class="boton" name="bListarImpresiones" id="bListarImpresiones" value="Buscar" onkeypress="this.onClick"
							onclick="return obtenerListaImpresiones();" />
					</td>
					<td>
						<input type="button" id="limpiarCampos" name="bLimpiarCampos" onkeypress="this.onClick" value="Limpiar campos" onClick="return limpiarCamposConsultaImpresion();" class="boton"/>
					</td>
				</tr>
			</table>
		</div>

	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width:100%;background-color:transparent;">
		<table class="subtitulo" cellSpacing="0" style="width:100%;">
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>

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
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaImpresion.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" sort="external"
			class="tablacoin" uid="tablaConsultaImpresion" summary="Listado de Impresiones"
			cellspacing="0" cellpadding="0" requestURI="navegarConsultaImpresion.action"
			decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorImpresion">

			<display:column property="nombreDocumento" title="Nombre Documento" style="width:4%;" sortable="true"
				paramId="nombreDocumento" paramProperty="nombreDocumento" headerClass="sortable" defaultorder="descending"/>

			<display:column property="tipoDocumento" title="Tipo Documento" style="width:4%;" sortable="true" headerClass="sortable" defaultorder="descending" paramId="tipo"/>

			<display:column property="tipoTramite" title="Tipo Tramite" style="width:4%;" sortable="true" headerClass="sortable" defaultorder="descending"/>

			<display:column property="fechaCreacion" title="Fecha Creación" style="width:4%;" sortable="true" headerClass="sortable" defaultorder="descending" paramId="fechaCreacion"
				format="{0,date,dd-MM-yyyy HH:mm:ss}"/>

			<display:column property="fechaGenerado" title="Fecha Generación" style="width:4%;" sortable="true" headerClass="sortable" defaultorder="descending" paramId="fechaGenerado"
				format="{0,date,dd-MM-yyyy HH:mm:ss}"/>

			<display:column property="estado" title="Estado" style="width:5%;" sortable="true" headerClass="sortable" defaultorder="descending"/>

			<s:if test="%{#isAdmin==true}">
				<display:column property="numColegiado" title="Contrato" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" sortProperty="numColegiado"/>
			</s:if>

			<display:column style="width:4%" title="">
				<s:if test="#attr.tablaConsultaImpresion.estado == 'Generado' || #attr.tablaConsultaImpresion.estado == 'Descargado'">
					<a onclick="" href="javascript:descargarFichero('${tablaConsultaImpresion.nombreDocumento}')"> Descargar </a>
				</s:if>
			</display:column>

		</display:table>
	</div>
	</div>
</s:form>