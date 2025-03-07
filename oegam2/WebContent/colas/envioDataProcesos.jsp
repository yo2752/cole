<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script  type="text/javascript">

	function modificarFechaHoy(indice) {

		if(confirm("Se va a actualizar el proceso "+ indice + " de la tabla de Envio Data con la fecha de Hoy. \u00bfEst\u00e1 usted seguro?")){
			document.getElementById("nombreProceso").value = indice;
			document.getElementById("formData").action = "envioDataHoyProcesos.action";
			document.getElementById("formData").submit();
			return true; 
		}		
	}

	function modificarFechaAnterior(indice) {
		if(confirm("Se va a actualizar el proceso "+ indice + " de la tabla de Envio Data con la fecha del ultimo día Laborable. \u00bfEst\u00e1 usted seguro?")){
			document.getElementById("nombreProceso").value = indice;
			document.getElementById("formData").action = "envioDataAnteriorProcesos.action";
			document.getElementById("formData").submit();
			return true; 
		}		
	}
	
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Envio Data Procesos</span></td>
		</tr>
	</table>
</div>



<s:form method="post" id="formData" name="formData">


<div class="divScroll">

	<display:table name="listaEnvioData" excludedParams="*"
							requestURI="navegarEnvioDataProcesos.action"
							class="tablacoin"
							uid="tablaEnvioData"
							summary="Listado de Envio Data"
							cellspacing="0" cellpadding="0">	


	<display:column property="id.proceso" title="Proceso" headerClass="centro" style="width:30%;text-align:left;padding-left:10px;" />        		
	<display:column property="fechaEnvio" title="Fecha Ejecuci&oacute;n"  headerClass="centro" style="width:5%;"/>
	<display:column property="respuesta" title="Respuesta" headerClass="centro"  style="width:5%;"/>
	<display:column property="numExpediente" title="Num Expediente" headerClass="centro"  style="width:5%;"/>
	
	 <display:column title="Fecha Anterior"  style="text-align:center; width:10%;" >			
			<input class="boton" type="button" id="fechaAnterior" name="fechaAnterior" value="Ultima Fecha Laboral" onclick="return modificarFechaAnterior('<s:property value="#attr.tablaEnvioData.id.proceso" />');" onKeyPress="this.onClick"/>
	</display:column>
	
	<display:column title="Fecha Hoy" style="text-align:center; width:10%;">
			<input class="boton" type="button" id="fechaHoy" name="fechaHoy" value="Fecha de Hoy" onclick="return modificarFechaHoy('<s:property value="#attr.tablaEnvioData.id.proceso" />');" onKeyPress="this.onClick"/>
	</display:column>	

</display:table>

<s:hidden id="nombreProceso" name="nombreProceso" value=""/>
</div>

	
</s:form>

