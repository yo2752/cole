<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/pegatinasBotones.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script type="text/javascript">

function limpiarPegatinasPeticionStock() {
	var comboEstado = document.getElementById("descrEstado");
	var defaultEstadoOption = comboEstado.options[0];
	comboEstado.value=defaultEstadoOption.value;

	document.getElementById("diaIni").value = "";
	document.getElementById("mesIni").value = "";
	document.getElementById("anioIni").value = "";
	document.getElementById("diaFin").value = "";
	document.getElementById("mesFin").value = "";
	document.getElementById("anioFin").value = "";
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
					<label for="descripcionEstado">Estado:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select
						list="@org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas@getEstadoPeticiones()"
						id="descrEstado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="pegatinasStockPeticionesBean.estado"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Estado"
						disabled="false"/>
				</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label>Fecha: </label>
				</td>

				<td align="left">
					<table width=100%>
						<tr>
							<td align="right">
								<label for="diaAltaIni">desde: </label>
							</td>

							<td>
								<s:textfield name="pegatinasStockPeticionesBean.fecha.diaInicio"
									id="diaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="pegatinasStockPeticionesBean.fecha.mesInicio"
									id="mesIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="pegatinasStockPeticionesBean.fecha.anioInicio"
									id="anioIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="5" maxlength="4"/>
							</td>

							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioIni, document.formData.mesIni, document.formData.diaIni);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger"
										align="left" 
										src="img/ico_calendario.gif"
										width="15" height="16"
										border="0" alt="Calendario"/>
								</a>
							</td>

							<td width="2%"></td>
						</tr>
					</table>
				</td>

				<td align="left">
					<table width=100%>
						<tr>
							<td align="left">
								<label for="diaAltaFin">hasta:</label>
							</td>

							<td>
								<s:textfield name="pegatinasStockPeticionesBean.fecha.diaFin"
									id="diaFin"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2" />
							</td>

							<td>/</td>

							<td>
								<s:textfield name="pegatinasStockPeticionesBean.fecha.mesFin"
									id="mesFin"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2" />
							</td>

							<td>/</td>

							<td>
								<s:textfield name="pegatinasStockPeticionesBean.fecha.anioFin"
									id="anioFin"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="5" maxlength="4" />
							</td>

							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger"
										align="left" 
										src="img/ico_calendario.gif"
										width="15" height="16" 
										border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</TABLE>
				</td>
			</tr>
		</table>

		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarPegatinasPeticionStock();"/>
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarPegatinasPeticionStock()" value="Limpiar" />
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

		<%@include file="../../includes/erroresYMensajes.jspf"%>

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
										onchange="cambiarElementosPorPaginaConsulta('navegarPegatinasPeticionStock.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>	

		<div id="displayTagDiv" class="divScroll">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*" requestURI="navegarPegatinasPeticionStock.action" class="tablanotifi"
				uid="tablaStockPegatinas" summary="Peticiones Stock de Pegatinas" cellspacing="0" cellpadding="0" sort="external">

				<display:column property="tipo" title="Tipo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="descrEstado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="identificador" title="Identificador Peticion" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="numPegatinas" title="Número Distintivos" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="fecha" title="Fecha Creacion" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
				<display:column style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksPeticion);' onKeyPress='this.onClick'">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucionPeticion(<s:property value="#attr.tablaStockPegatinas.idPeticion"/>);" title="Ver evolución"/>
							</td>
							<td>
								<input
									type="checkbox"
									name="listaChecksPeticion"
									id="checkPeticion"
									value='<s:property value="#attr.tablaStockPegatinas.identificador"/>, <s:property value="#attr.tablaStockPegatinas.estado"/>,
										<s:property value="#attr.tablaStockPegatinas.idPeticion"/>, <s:property value="#attr.tablaStockPegatinas.idStock"/>'/>
							</td>
						</tr>
					</table>
				</display:column>

			</display:table>
		</div>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bConfirmarRecepcion" id="bConfirmarRecepcion" value="Confirmar Recepción" onkeypress="this.onClick" onclick="return confirmarRecepcion(this);"/>				
					<input type="button" class="boton" name=bAnularPeticion id="bAnularPeticion" value="Anular Petición" onkeypress="this.onClick" onclick="return anularPeticion(this);"/>
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onkeypress="this.onClick" onclick="return volverConsulta();"/>
				</td>
			</tr>
		</table>
	</div>

</s:form>