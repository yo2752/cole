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
<script src="js/impresionesMasivas.js" type="text/javascript"></script>

<script type="text/javascript">

function descargarFichero(nombreFichero) {
	if(confirm("Este fichero se borrará en 5 minutos una vez impreso ¿Continuar?")) {
		document.formData.nombreFicheroDescargar.value = nombreFichero;
		document.formData.action="imprimirConsultaImpresionMasiva.action";
		document.formData.submit();
	}
}

</script>

<s:set var="isAdmin"
	value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin().booleanValue()}"></s:set>
<s:set var="visible" value="none"></s:set>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo"> Consulta Impresiones Masivas </span></td>
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
							<td align="left"><label for="diaMatrIni">Fecha Enviado Proceso desde: </label></td>

							<td><s:textfield
									name="impresionMasivaBean.fechaFiltradoEnviadoProceso.diaInicio"
									id="diaInicioEnviadoProceso" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="impresionMasivaBean.fechaFiltradoEnviadoProceso.mesInicio"
									id="mesInicioEnviadoProceso" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="impresionMasivaBean.fechaFiltradoEnviadoProceso.anioInicio"
									id="anioInicioEnviadoProceso" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioEnviadoProceso, document.formData.mesInicioEnviadoProceso, document.formData.diaInicioEnviadoProceso);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td> 
				<td colspan="3">
					<table>
						<tr>
							<td align="left"><label for="diaMatrIni">Fecha Enviado Proceso Hasta: </label></td>

							<td><s:textfield
									name="impresionMasivaBean.fechaFiltradoEnviadoProceso.diaFin"
									id="diaFinEnviadoProceso" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="impresionMasivaBean.fechaFiltradoEnviadoProceso.mesFin"
									id="mesFinEnviadoProceso" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="impresionMasivaBean.fechaFiltradoEnviadoProceso.anioFin"
									id="anioFinEnviadoProceso" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinEnviadoProceso, document.formData.mesFinEnviadoProceso, document.formData.diaFinEnviadoProceso);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left"><label for="diaMatrIni">Nombre Fichero:</label></td>

				<td><s:textfield name="impresionMasivaBean.nombreFichero" id="nombreFichero" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="30"
						maxlength="50" /></td>
						
				<td align="left"><label for="diaMatrIni">Tipo Documento: </label></td>
				<td><s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoDocumentoImpresionesMasivas()"
						name="impresionMasivaBean.tipoDocumento" id="tipoDocumento" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" /></td>
			</tr>
			<tr>
				<td align="left"><label for="diaMatrIni">Estado Impresión: </label></td>
				<td><s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoImpresion()"
						name="impresionMasivaBean.estadoImpresion" id="estadoImpresion" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" /></td>
				<s:if test="%{#isAdmin==true}">
					<td align="left"><label for="diaMatrIni">Num Colegiado: </label></td>
					<td><s:textfield name="impresionMasivaBean.numColegiado" id="numColegiado"
							align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="false" size="10"
							maxlength="25" /></td>
				</s:if>
			</tr>
		</table>
		<div class="centrado espArriba">
			<table align="center">
				<tr>
					<td>
						<input type="button" class="boton" name="bListarImpresionesMasivas"
							id="bListarImpresionesMasivas" value="Buscar" onkeypress="this.onClick"
							onclick="return obtenerListaImpresionMasivas();" />
					</td>
					<td>
						<input type="button" id="limpiarCampos" name="bLimpiarCampos" onkeypress="this.onClick" value="Limpiar campos" onClick="return limpiarCamposConsultaImpresionMasiva();" class="boton"/>
					</td>
				</tr>
			</table>
		</div>

	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaImpresionMasiva.action', 'displayTagDiv', this.value);" />
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
		 class="tablacoin"	uid="tablaConsultaImpresionMasivas" summary="Listado de Impresiones Masivas"
		 cellspacing="0" cellpadding="0" requestURI="navegarConsultaImpresionMasiva.action">

			<display:column property="nombreFichero" title="Nombre Fichero" style="width:4%;" sortable="true"
				paramId="nombreFichero" paramProperty="nombreFichero" headerClass="sortable" defaultorder="descending"/>

			<display:column property="tipoDocumento" title="Tipo Documento" style="width:4%;"	sortable="true" headerClass="sortable" defaultorder="descending" paramId="tipo"/>
				
			<display:column property="fechaEnviadoProceso" title="Fecha Enviado Proceso" style="width:4%;" sortable="true" headerClass="sortable" defaultorder="descending" paramId="fechaEnviadoProceso"/>
			
			<display:column title="Estado Impresión" style="width:4%;" sortable="true" headerClass="sortable" defaultorder="descending">
				<s:property	value="%{@org.gestoresmadrid.core.impresion.model.enumerados.EstadoImpresion@convertir(#attr.tablaConsultaImpresionMasivas.estadoImpresion).nombreEnum}" />
			</display:column>
				
			<s:if test="%{#isAdmin==true}">
				<display:column property="numColegiado" title="Colegiado" style="width:4%;"	sortable="true" headerClass="sortable" defaultorder="descending" paramId="numColegiado"/>
			</s:if>	
			
			<display:column style="width:4%" title="" >
				<a onclick="" href="javascript:descargarFichero('${tablaConsultaImpresionMasivas.nombreFichero}')"> Descargar </a>
			</display:column>
			
		</display:table>
	</div>
	</div>
</s:form>