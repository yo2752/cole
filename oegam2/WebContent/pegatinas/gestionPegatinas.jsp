<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/pegatinasBotones.js" type="text/javascript"></script>

<script type="text/javascript">

function buscarGestionPegatinasPeticiones() {
	document.formData.action = "buscarGestionPegatinas.action";
	document.formData.submit();
}

function limpiarGestionPegatinas() {
	var comboTipo = document.getElementById("tipo");
	var defaultTipoOption = comboTipo.options[0];
	comboTipo.value=defaultTipoOption.value;
}

function limpiarGestionPegatinasUsuarioJPT() {
	var comboTipo = document.getElementById("tipo");
	var defaultTipoOption = comboTipo.options[0];
	comboTipo.value=defaultTipoOption.value;
	
	var comboJefatura = document.getElementById("idJefaturaJPTSelect");
	var defaultJefaturaOption = comboJefatura.options[0];
	comboJefatura.value=defaultJefaturaOption.value;
}

</script>
<s:hidden key="idStock" name="idStock"/>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Stock de Distintivos Medioambientales</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tipo">Tipo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select 
						list="@org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas@getTipoPegatinas()"
						id="tipo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="pegatinasStockBean.tipo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Estado"
						disabled="false"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelJefaturaProvJpt">Jefatura Provincial: </label>
				</td>
				<td align="left">
					<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
						<s:hidden id="idJefaturaJPT" name="jefaturaJpt"/>
						<s:textfield id="idDescJefaturaJPT" value="%{@escrituras.utiles.UtilesVista@getInstance().getJefaturaProvincial(jefaturaJpt)}" 
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25" onkeypress="return controlNumeros(event);"/>
					</s:if>
					<s:else>
						<s:select id="idJefaturaJPTSelect"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							disabled="%{flagDisabled}"
							list="@escrituras.utiles.UtilesVista@getInstance().getJefaturaProvincialPegatinas()"
							headerKey=""
							headerValue="Seleccione Jefatura Provincial"
							name="jefaturaJpt" 
							listKey="jefatura" 
							listValue="descripcion"/>
					</s:else>
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarGestionPegatinasPeticiones();"/>
				</td>
				<td>
					<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
						<input type="button" class="boton" name="bLimpiar" onclick="limpiarGestionPegatinas()" value="Limpiar"  />	
					</s:if>
					<s:else>
						<input type="button" class="boton" name="bLimpiar" onclick="limpiarGestionPegatinasUsuarioJPT()" value="Limpiar" />
					</s:else>
				</td>
			</tr>
		</table>

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
										onchange="cambiarElementosPorPaginaConsulta('navegarGestionPegatinas.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<%@include file="../../includes/erroresYMensajes.jspf"%>
		<div id="displayTagDiv" class="divScroll">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*" requestURI="navegarGestionPegatinas.action" class="tablanotifi"
				uid="tablaGestionPegatinas" summary="Gestion de Pegatinas" cellspacing="0" cellpadding="0" sort="external"
				decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorJefatura">

				<display:column property="tipo" title="Tipo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="stock" title="Stock Actual" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
					<display:column property="jefatura" title="Jefatura" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				</s:if>
				<display:column style="width:4%" title="Peticiones Stock">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/mostrar.gif" alt="ver peticiones" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirPeticiones(<s:property value="#attr.tablaGestionPegatinas.idStock"/>);" title="Ver peticiones"/>
							</td>
						</tr>
					</table>
				</display:column>
				<display:column style="width:4%" title="Histórico Stock">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/mostrar.gif" alt="ver histórico" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirHistorico(<s:property value="#attr.tablaGestionPegatinas.idStock"/>);" title="Ver histórico"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bPedirStock" id="bPedirStock" value="Pedir Stock" onkeypress="this.onClick" onclick="abrirVentanaPedirStock();"/>
				</td>
			</tr>
		</table>

	</div>
	<div id="emergenteStock" style="width: 100%; background-color: transparent;">
	</div>
</s:form>