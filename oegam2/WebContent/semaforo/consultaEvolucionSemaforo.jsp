<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>
<html>
<head>
	<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen"/>
	<script src="js/semaforo/gestionSemaforos.js" type="text/javascript"></script>
	<script type="text/javascript">
		function cerrar() {
			this.close();
		}
	</script>
</head>
<body class="popup">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Evolución Sem&oacute;foro <s:property value="idSemaforo"/></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<s:form method="post" id="formData" name="formData">
	
		<div class="busqueda">
			<div id="resultado" style="width: 100%; background-color: transparent;">
				<table class="subtitulo" cellSpacing="0" style="width: 100%;">
					<tr>
						<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
					</tr>
				</table>
			</div>
		
			<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
				<table width="90%">
					<tr>
						<td align="right">
							<table width="50%">
								<tr>
									<td width="90%" align="right">
										<label for="idResultadosPorPaginaEvolucionSemaforo">&nbsp;Mostrar resultados</label>
									</td>
									<td width="10%" align="right">
										<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
											onblur="this.className='input2';" onfocus="this.className='inputfocus';"
											id="idResultadosPorPaginaEvolucionSemaforo" value="resultadosPorPagina" title="Resultados por página"
											onchange="cambiarElementosPorPaginaConsultaEvolucionSemaforos('navegarEvolucionSemaforos.action', 'displayTagDivEvolSemaforo', this.value);" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>

			<script type="text/javascript">
				$(function() {
					$("#displayTagDivEvolSemaforo").displayTagAjax();
				})
			</script>
		
			<div id="displayTagDivEvolSemaforo">
				<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../includes/bloqueLoading.jspf"%>
				</div>
		
				<display:table name="lista" excludedParams="*" sort="external" class="tablacoin" uid="tablaEvolucionSemaforo"
					summary="Listado de Evolucion Semáforo" cellspacing="0" cellpadding="0" requestURI="navegarEvolucionSemaforos.action"
						id="tablaConsultaSemaforos" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorEvolSemaforo">
					<display:column title="Operación" sortable="true" headerClass="sortable" property="operacion"
						defaultorder="descending" style="width:4%;"/>
					<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true" headerClass="sortable" defaultorder="descending"
						style="width:8%;" />
					<display:column property="fecha" title="Fecha" style="width:2%;" sortable="true" headerClass="sortable"
						defaultorder="descending" format="{0, date, dd/MM/yyyy}"/>
				</display:table>
				
				<table class="acciones">
					<tr>
						<td>
							<div>
								<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar"
									onClick="return cerrar();" onKeyPress="this.onClick" />
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</s:form>
</body>
</html>