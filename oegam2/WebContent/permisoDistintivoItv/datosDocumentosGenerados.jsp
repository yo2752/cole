<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/gestionPermDstvFichas.js" type="text/javascript"></script>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="page"/>
		<s:hidden name="docId" id="docId"/>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;">
			<tr>
				<td  style="width:100%;text-align:center;">Datos de los Documentos</td>
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
										id="idResultadosPorPaginaDetalleDocPrmDstv" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaDetalleDocumento();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivDetalleDocPrmDstv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tabladatosDocumentosGen" requestURI= "navegarDetalleDocumento.action"
				id="tabladatosDocumentosGen" summary="Datos de los documentos generados" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="numExpediente" title="Num. expediente" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<display:column property="matricula" title="Matricula" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="tipoDocumento" title="Tipo de documento"  headerClass="sortable"
				defaultorder="descending" style="width:1%"/>

				<display:column property="tipo" title="Tipo" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="numSerie" title="Núm. Serie" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>
			</display:table>
		</div>
		<div class="acciones">
			<input type="button" class="boton" name="bDatosVolver" id="idDatosVolver" value="Volver" onClick="javascript:volverDatosDocumentosGenerados();"onKeyPress="this.onClick"/>
		</div>
	</s:form>
</div>