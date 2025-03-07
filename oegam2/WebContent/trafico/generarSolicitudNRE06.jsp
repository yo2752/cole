<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div id="contenido" class="contentTabs" style="display: block;">	
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Generar Solicitud NRE-06</span>					
				</td>
			</tr>
		</table>
	</div>
	
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
		<display:table name="" 
			requestURI="navegar${action}" class="tablacoin" excludedParams="*"
			uid="tablaConsultaTramite" summary="Listado de Tramites"
			cellspacing="0" cellpadding="0" decorator="${decorator}">
			
			<display:column property="numExpediente" title="Num Expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%;" paramId="numExpediente" />			
			<display:column property="refPropia" title="Referencia Propia" 	sortable="true" headerClass="sortable" defaultorder="descending" maxLength="15" style="width:4%" />			
			<display:column property="bastidor" sortProperty="vehiculo.bastidor" title="Bastidor" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />	
			<display:column property="matricula" sortProperty="vehiculo.matricula" title="Matricula" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" />
			<display:column property="codigoTasa" sortProperty="tasa.codigoTasa" title="Codigo Tasa" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
			<display:column property="estado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" />
		</display:table>
	</div>	



	<div id="cuerpoGenerar">
	 
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Generacion y envío de NRE-06</td>
			</tr>
		</table>	

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
	    	<tr>
	    		<td width="2%"></td><td></td>
	    	</tr>
    		<tr>
				<td align="left" width="25%">1. Genere el PDF</td>
				<td align="left">
					<div>
						<form action="solicitudNRE06ImprimirAction.action">
		    				<input class="boton" type="button" id="bVolver" name="bVolver" value="Generar" onClick="submit();" onKeyPress="this.onClick" />
		    			</form>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left">2. Escriba el Email</td>
				<td align="left">
					<div>
						<a href="mailto:alvaro.segura@globaltms.es">
		    				<input class="boton" type="button" id="bVolver" name="bVolver" value="Email" />
		    			</a>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left">3. Adjunte el PDF</td>
			</tr>
			<tr>
				<td align="left">4. Incluya la documentacion adicional</td>
			</tr>
		</table>

		<table class="acciones">
	    	<tr>
	    		<td>
		    		<div>
		    			<input class="boton" type="button" id="bVolver" name="bVolver" value="Volver a Consulta" onClick="return volverConsulta(this);" onKeyPress="this.onClick" />
					</div>
	    		</td>
	    	</tr>
	    </table> 
	</div>


</div>
<script type="text/javascript">
	function imprimirNRE06(){
		function consultarImputaciones(){
			$('#formData').attr("action","consultarImputacionHoras.action");
			$('#formData').submit();
		}
	}
</script>
<s:if test="fileName!=null">
	<s:form id="recuperarFichero" method="post" action="descargarFicheroImprimirAction">
		<s:hidden name="fileName"></s:hidden>
	</s:form>
	<script> $('#recuperarFichero').submit(); </script>
</s:if>