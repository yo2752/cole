<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
		<script src="js/tabs.js" type="text/javascript"></script>
		<script src="js/contrato.js" type="text/javascript"></script>
		<script src="js/validaciones.js" type="text/javascript"></script>
		<script src="js/genericas.js" type="text/javascript"></script>
		<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>

		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />

		<title>Evolución del vehículo: <s:property value="consultaEvolucionVehiculoBean.idVehiculo" /></title>

		<script type="text/javascript">
			function mostrarVentana(evento, indice, lineas) {
				var targ;
				var e = evento;
				if (!evento) {
					e = window.event;
				}
				if (e.target) {
					targ = e.target;
				} else {
					targ = e.srcElement;
				}
				document.getElementById("ventana").innerHTML = document.getElementById("detalles" + indice).innerHTML;
				document.getElementById("ventana").style.top = (targ.y + 23) + "px";
				document.getElementById("ventana").style.left = (targ.x - 18) + "px";
				document.getElementById("ventana").style.height = "auto";
				document.getElementById("ventana").style.visibility = "visible";
			}

			function ocultarVentana() {
				document.getElementById("ventana").style.visibility = "hidden";
				document.getElementById("ventana").innerHTML = "";
			}

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
							<span class="titulo">Evolución del vehículo <s:property value="consultaEvolucionVehiculoBean.idVehiculo" /></span>
						</td>
					</tr>
				</table>
			</div>
		</div>

		<%@include file="../../includes/erroresYMensajes.jspf"%>

		<s:form method="post" id="formData" name="formData">
			<div class="busqueda">
				<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
					scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
				</iframe>

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
											<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
										</td>
										<td width="10%" align="right">
											<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
												onblur="this.className='input2';" onfocus="this.className='inputfocus';"
												id="idResultadosPorPagina" value="resultadosPorPagina" title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsulta('navegarConsultaEvVehiculo.action', 'displayTagDiv', this.value);" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:if>

				<script type="text/javascript">
					$(function() {
						$("#displayTagDiv").displayTagAjax();
					})
				</script>

				<div id="displayTagDiv">
					<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
						<%@include file="../../includes/bloqueLoading.jspf"%>
					</div>

					<display:table name="lista" excludedParams="*" sort="external" class="tablacoin" uid="tablaEvolucionVehiculo"
						summary="Listado de Estados de un Vehículo" cellspacing="0" cellpadding="0" requestURI="navegarConsultaEvVehiculo.action"
						decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorEvolucionVehiculo">

						<display:column property="fechaHora" title="FECHA" style="width:140px;" sortable="true" headerClass="sortable"
							defaultorder="descending" maxLength="22" sortProperty="id.fechaHora"/>
						<display:column property="tipoTramite" title="Tipo de trámite" style="width:160px;" sortable="true" headerClass="sortable"
							defaultorder="descending" maxLength="40"/>
						<display:column property="numExpediente" title="Número de expediente" sortable="true"
							headerClass="sortable" defaultorder="descending" maxLength="22" style="width:110px;" />
						<display:column title="Tipo Actualización" sortable="true" headerClass="sortable" sortProperty="tipoActualizacion"
							defaultorder="descending" maxLength="500" style="width:140px;">
							<s:property value="#attr.tablaEvolucionVehiculo.tipoActualizacion" />
						</display:column>
						<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true" headerClass="sortable" defaultorder="descending"
							maxLength="40" style="width:300px;" />
						<display:column property="idVehiculoOrigen" title="ID ORIGEN" style="width:110px;" sortable="true" headerClass="sortable"
							defaultorder="descending" maxLength="22" href="inicioConsultaEvVehiculo.action" paramId="idVehiculoOrigen"/>

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
				<div id="ventana" style="position: absolute; left: 450px; top: 260px; filter: alpha(opacity = 100); opacity: 1.00; width: 340px; height: auto; border: 1px solid black; background-color: #FFDDDD; text-align: left; padding-left: 0px; visibility: hidden">Datos de entrada</div>
			</div>
		</s:form>
	</body>
</html>