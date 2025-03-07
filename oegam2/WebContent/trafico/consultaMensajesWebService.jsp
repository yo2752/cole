<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>

<script type="text/javascript">
	function buscarConsultaMensajesWebServices() {
		document.formData.action = "buscarConsultaMensajesWebService.action";
		document.formData.submit();
	}
	function limpiarConsultaMensajesWebServices(){
		document.getElementById("recuperable").selectedIndex = 0;
		document.getElementById("codigo").value = "";
		document.getElementById("mensaje").value = "";
		document.getElementById("descripcion").value = "";
	}
	function recuperarMensajeWebService(codigo){
		document.location.href='detalleDetalleMensajesWebService.action?codigo='+codigo;
	}
</script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Errores devueltos por los servicios web</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../includes/erroresMasMensajes.jspf" %>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="codigo">C&oacute;digo:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaMensajesProcesoBean.codigo" id="codigo" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="35" maxlength="20" />
				</td>
					<td align="left" nowrap="nowrap">
					<label for="mensaje">Mensaje:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaMensajesProcesoBean.mensaje" id="mensaje"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="55" maxlength="65" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="descripcion">Descripci&oacute;n:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaMensajesProcesoBean.descripcion" id="descripcion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="recuperable">Recuperable:</label>
				</td>
				<td align="left">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoPresentacionJPT()"
						name="consultaMensajesProcesoBean.recuperable" id="recuperable" headerKey="" headerValue="Todos" listKey="nombreEnum" listValue="nombreEnum" />
				</td>
			</tr>
	</table>
	<table class="acciones">
		<tr>
			<td>
				<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaMensajesWebServices();"/>
			</td>
			<td>
				<input type="button" class="boton" name="bLimpiar" onclick="limpiarConsultaMensajesWebServices()" value="Limpiar" />
			</td>
		</tr>
	</table>

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
									id="idResultadosPorPagina" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaMensajesWebService.action', 'displayTagDiv', this.value);" />
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

	<div id="displayTagDiv" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" requestURI="navegarConsultaMensajesWebService.action" class="tablacoin"
			uid="tablaConsultaWebService" summary="Listado de Mensajes Web Services" cellspacing="0" cellpadding="0">

			<display:column property="codigo" title="Código" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%;"/>

			<display:column property="mensaje" title="Mensaje" sortable="true" headerClass="sortable" defaultorder="descending" style="width:25%" />

			<display:column property="descripcion" title="Descripción" sortable="true" headerClass="sortable" defaultorder="descending" style="width:40%" />

			<display:column property="recuperable" title="Recuperable" sortable="true" headerClass="sortable" defaultorder="descending" style="width:1%" />

			<display:column  style="width:5%">
				<img src="img/mostrar.gif" alt="Consulta Mensaje Web Service" style="height:20px;width:20px;"
					onclick="recuperarMensajeWebService('${tablaConsultaWebService.codigo}');" title="Consulta Mensaje Web Service"/>
		</display:column>
		</display:table>
	</div>
	</div>
</s:form> 