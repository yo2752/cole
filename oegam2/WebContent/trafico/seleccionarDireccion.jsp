<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
<head>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/seleccionarDireccion.js" type="text/javascript"></script>
<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.displaytag-ajax-oegam.js" type="text/javascript"></script>

<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />

<title>Consulta Dirección:</title>

</head>

<body class="popup">

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo"> Consulta Dirección </span></td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresYMensajes.jspf"%>
<s:form method="post" id="formData" name="formData">
<div class="busqueda">
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
	
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO_DIRECCIONES}">
			<table width="90%">
				<tr>
					<td align="right">
						<table width="50%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										title="Resultados por página" onchange="cambiarElementosPorPaginaConsulta('navegarSeleccionarDireccion.action', 'displayTagDiv', this.value);" />
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
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" sort="external" class="tablacoin" uid="row" summary="Listado de Direcciones"
			cellspacing="0" cellpadding="0" requestURI="navegarSeleccionarDireccion.action">
				<display:column property="direccion.idDireccion" media="none"/>
				<display:column property="direccion.idProvincia" media="none"/>
				<display:column property="direccion.idMunicipio" media="none"/>
			
				<display:column style="width:10%" property="direccion.nombreProvincia" title="Provincia" />
				<display:column style="width:10%" property="direccion.nombreMunicipio" title="Municipio" />
				<display:column style="width:10%" property="direccion.idTipoVia" title="Tipo Via" />
				<display:column style="width:41%" title="Nombre vía" >
					<a href="javascript:actualizarPadre('${row.direccion.idDireccion}','${row.direccion.idProvincia}','${row.direccion.idMunicipio}','${row.direccion.idTipoVia}','${row.direccion.nombreVia}',
														'${row.direccion.numero}','${row.direccion.pueblo}','${row.direccion.codPostal}','${row.direccion.puebloCorreos}','${row.direccion.codPostalCorreos}','${row.direccion.letra}',
														'${row.direccion.escalera}','${row.direccion.bloque}','${row.direccion.planta}','${row.direccion.puerta}','${row.direccion.km}','${row.direccion.hm}',
														'${row.direccion.idDireccion}', '<%=request.getParameter("pestania") %>');">${row.direccion.nombreVia}</a>
				</display:column>
				<display:column style="width:4%" property="direccion.numero" title="Número" />
				<display:column style="width:4%" property="direccion.codPostal" title="Código Postal" />
				<display:column style="width:10%" property="direccion.pueblo" title="Pueblo" />
				<display:column style="width:4%" property="direccion.codPostalCorreos" title="Código Postal Correos" />
				<display:column style="width:10%" property="direccion.puebloCorreos" title="Pueblo Correos" />
				<display:column style="width:3%" property="direccion.letra" title="Letra" />
				<display:column style="width:3%" property="direccion.escalera" title="Escalera" />
				<display:column style="width:3%" property="direccion.bloque" title="Bloque" />
				<display:column style="width:3%" property="direccion.planta" title="Planta" />
				<display:column style="width:3%" property="direccion.puerta" title="Puerta" />
				<display:column style="width:3%" property="direccion.km" title="KM" />
				<display:column style="width:3%" property="direccion.hm" title="HM" />
		</display:table>

		<table class="acciones">
   			<tr>
   				<td>
    				<div>
    					<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="window.close();" onKeyPress="this.onClick" />
					</div>
    			</td>
    		</tr>
		</table>
	</div>
</div>
</s:form>
</body>
</html>