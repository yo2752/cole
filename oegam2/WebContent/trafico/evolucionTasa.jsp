<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
<head>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evolución de la tasa: <s:property value="codTasa"/></title>
</head>

<script type="text/javascript">

function mostrarVentana(evento, indice, lineas) {
	var targ;
	var e = evento;
	if (!evento) {
	e = window.event;
	}
	if (e.target){
	targ = e.target;
	} else {
	targ = e.srcElement;
	}
	document.getElementById("ventana").innerHTML = document.getElementById("detalles" + indice).innerHTML;
	//document.getElementById("ventana").style.top = (90 + (24 * indice)) +"px";
	document.getElementById("ventana").style.top = (targ.y + 23) +"px";
	//document.getElementById("ventana").style.left = "535px";
	document.getElementById("ventana").style.left = (targ.x - 168) + "px";
	//document.getElementById("ventana").style.height = (12 * (lineas + 2)) +"px";
	document.getElementById("ventana").style.height = "auto";
	//document.getElementById("ventana").style.fontWeight = "bold";
	document.getElementById("ventana").style.visibility = "visible";
	}
	function ocultarVentana() {
	document.getElementById("ventana").style.visibility = "hidden";
	document.getElementById("ventana").innerHTML = "";
	} 
</script>




<body class="popup">

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Evolución de la tasa: <s:property value="codTasa"/> </span>
			</td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresYMensajes.jspf" %>

		<s:if test="%{listaEstadosEvolucion.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADOS}"></s:if>


<div>
	<display:table name="listaEstadosEvolucion" excludedParams="*"
					requestURI="navegarTasa.action"
					class="tablacoin"
					uid="tablaEstadosTasa"
					summary="Listado de Estados de una Tasa"
					cellspacing="0" cellpadding="0" >		

	<display:column property="FECHA_HORA" title="FECHA"
			style="width:160px;" 
			sortable="true" headerClass="sortable"
			defaultorder="descending" maxLength="22" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>	
			
	<display:column property="APELLIDOS_NOMBRE" title="Usuario"
			sortable="true" headerClass="sortable"
			defaultorder="descending" maxLength="40"
			style="width:400px;" />
	
	<display:column property="ACCION_RESUMEN" title="Acción"
			sortable="true" headerClass="sortable"
			sortProperty="ACCION"
			defaultorder="descending" maxLength="500"
			style="width:200px;" />
			
	
	
	
	
	<input type="hidden" name="resultadosPorPagina"/>
	<input type="hidden" name="codTasa"/>
</display:table>

<table class="acciones">
   	<tr>
   		<td>
    		<div>
    		<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="return cerrarEvolucionTramite();" onKeyPress="this.onClick" />
			</div>
    	</td>
    </tr>
</table>

</div>

<div id="ventana" style="position:absolute;left:640px;top:260px;filter:alpha(opacity=100);opacity:1.00;width:240px;height:auto;border:1px solid black;background-color:#FFDDDD;text-align:left;padding-left:0px;visibility:hidden">Datos de entrada</div>

</body>
</html>