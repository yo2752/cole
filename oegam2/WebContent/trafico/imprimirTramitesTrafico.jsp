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
<script src="js/imprimir.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block;">	
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<s:if test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Transmision)}"> 
						<span class="titulo">Transmisión: Imprimir documentos</span>					
					</s:if>
					<s:if test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@TransmisionElectronica)}"> 
						<span class="titulo">Transmisión Electrónica: Imprimir documentos</span>					
					</s:if>
					<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Solicitud)}">
						<span class="titulo">Solicitud de datos: Imprimir documentos</span>	
					</s:elseif>
					<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Baja)}">
						<span class="titulo">Bajas: Imprimir documentos</span>
					</s:elseif>
					<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Duplicado)}">
						<span class="titulo">Duplicados: Imprimir documentos</span>
					</s:elseif>
					<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Matriculacion)}">
							<span class="titulo">Matriculación: Imprimir documentos</span>
					</s:elseif>
				</td>
			</tr>
		</table>
	</div>	


<%@include file="../../includes/erroresMasMensajes.jspf" %>

<s:form method="post" cssClass="fileDownloadForm" id="formData" name="formData" action="obtenerFicheroImprimirAction" 
 onsubmit="return mensajeInformacion(this);">


<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td>Tr&aacute;mites seleccionados</td>
	</tr>
</table>

	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	<div id="displayTagDiv" class="divScroll">
		<display:table name="lista" 
			requestURI="navegar${action}" class="tablacoin" excludedParams="*"
			uid="tablaConsultaTramite" summary="Listado de Tramites"
			cellspacing="0" cellpadding="0" decorator="${decorator}">
			
			<display:column property="numExpediente" title="Num Expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%;" paramId="numExpediente" />			
			<display:column property="refPropia" title="Referencia Propia" 	sortable="true" headerClass="sortable" defaultorder="descending" maxLength="15" style="width:4%" />						
			<display:column title="Tipo tramite" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" sortProperty="tipoTramite">
				<s:property value="%{@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@convertirTexto(#attr.tablaConsultaTramite.tipoTramite)}" />
			</display:column>
			<display:column property="vehiculoDto.bastidor" sortProperty="bastidor" title="Bastidor" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />	
			<display:column property="vehiculoDto.matricula" sortProperty="matricula" title="Matricula" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" />
			<display:column property="tasa.codigoTasa" sortProperty="codigoTasa" title="Codigo Tasa" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
			<display:column property="estado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" >
			</display:column>
			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosImprimir(this, document.formData.listaChecksConsultaTramite,\"${imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.valorEnum}\");' onkeypress='this.onClick'/>"  
								style="width:1%">	
					<table align="center">
						<tr>
							<td style="border: 0px;"><input type="checkbox" name="listaChecksConsultaTramite" id="listaChecksConsultaTramite" value='${tablaConsultaTramite.numExpediente}'
								checked="checked" onchange="return marcarImprimir('${tablaConsultaTramite.tipoTramite}')"/>
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									checked="checked" onchange="return marcarImprimir('@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@convertirNombreAValor(#attr.tablaConsultaTramite.tipoTramite)')"/>
		  					</td>
		  				</tr>
		  			</table>
		  			<input type="hidden" id="tipoTramite${tablaConsultaTramite.numExpediente}" value='${tablaConsultaTramite.tipoTramite}'/>
			</display:column>
		</display:table>
	</div>

	<div id="cueporImpresion">
	<s:if test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Transmision)||imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@TransmisionElectronica)}"> 
		<%@include file="transmision/imprimirTramiteTransmision.jsp" %>
	</s:if>
	<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Solicitud)}">
		<%@include file="solicitudDatos/imprimirSolicitudDatos.jsp" %>
	</s:elseif>
	<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Baja)}">
		<%@include file="baja/imprimirTramiteBaja.jsp" %>
	</s:elseif>
	<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Duplicado)}">
		<%@include file="duplicado/imprimirTramiteDuplicado.jsp" %>
	</s:elseif>
	<s:elseif test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Matriculacion)}">
		<s:if test="%{imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramiteMatriculacion==null}">
			<%@include file="matriculacion/imprimirMatriculacion.jsp" %>
		</s:if>
		<s:else>	
			<%@include file="matw/imprimirMatriculacion.jsp" %>
		</s:else>
	</s:elseif>
	
	<s:if test="%{!imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.equals(@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@Solicitud)}">
		<%@include file="../../includes/creditosTrafico.jspf" %>
	</s:if>
	<table class="acciones">
    	<tr>
    		<td>
	    		<div>
	    			<s:submit cssClass="boton" name="bImprimir" value="Imprimir"></s:submit>
					&nbsp;
	    			<s:if test="volverAntiguaConsulta" >
	    				<input class="boton" type="button" id="bVolver" name="bVolver" value="Volver a Consulta" onClick="return cancelarForm('inicioConsultaTramiteTrafico.action');" onKeyPress="this.onClick" />
	    			</s:if>
	    			<s:else>
	    				<input class="boton" type="button" id="bVolver" name="bVolver" value="Volver a Consulta" onClick="return cancelarForm('inicioConsTramTraf.action');" onKeyPress="this.onClick" />
	    			</s:else>
	    			&nbsp;
				</div>
				<div id="bloqueLoading" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>
    		</td>
    	</tr>
    </table> 
	</div>
	<s:hidden name="volverAntiguaConsulta"></s:hidden>
</s:form>
</div>
<script type="text/javascript">marcarImprimir('${imprimirTramiteTraficoDto.resultBeanImprimir.tipoTramite.valorEnum}');</script>
<s:if test="fileName!=null">
	<s:form id="recuperarFichero" method="post" action="descargarFicheroImprimirAction">
		<s:hidden name="fileName"></s:hidden>
	</s:form>
	<script> $('#recuperarFichero').submit(); </script>
</s:if>