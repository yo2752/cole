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
	function buscarImpresionPegatinas() {
		document.formData.action = "buscarImpresionPegatinas.action";
		document.formData.submit();
	}

	function limpiarImpresionPegatinas(){
		document.getElementById("matricula").value = "";
		document.getElementById("numExpediente").value = "";

		document.getElementById("diaAltaIni").value = "";
		document.getElementById("mesAltaIni").value = "";
		document.getElementById("anioAltaIni").value = "";
		document.getElementById("diaAltaFin").value = "";
		document.getElementById("mesAltaFin").value = "";
		document.getElementById("anioAltaFin").value = "";

		var comboDescrEstado = document.getElementById("descrEstado");
		var defaultEstadoOption = comboDescrEstado.options[0];
		comboDescrEstado.value=defaultEstadoOption.value;

		var comboTipo = document.getElementById("tipo");
		var defaultTipoOption = comboTipo.options[0];
		comboTipo.value=defaultTipoOption.value;
	}

	function limpiarImpresionPegatinasUsuarioJPT() {
		document.getElementById("matricula").value = "";
		document.getElementById("numExpediente").value = "";

		document.getElementById("diaAltaIni").value = "";
		document.getElementById("mesAltaIni").value = "";
		document.getElementById("anioAltaIni").value = "";
		document.getElementById("diaAltaFin").value = "";
		document.getElementById("mesAltaFin").value = "";
		document.getElementById("anioAltaFin").value = "";

		var comboDescrEstado = document.getElementById("descrEstado");
		var defaultEstadoOption = comboDescrEstado.options[0];
		comboDescrEstado.value=defaultEstadoOption.value;

		var comboTipo = document.getElementById("tipo");
		var defaultTipoOption = comboTipo.options[0];
		comboTipo.value=defaultTipoOption.value;

		var comboJefatura = document.getElementById("idJefaturaJPTSelect");
		var defaultJefaturaOption = comboJefatura.options[0];
		comboJefatura.value=defaultJefaturaOption.value;
	}
</script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Impresion de Distintivos Medioambientales</span>
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
					<label for="matricula">Matricula:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="impresionPegatinasBean.matricula" id="matricula" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Num. Expediente:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="impresionPegatinasBean.numExpediente" id="numExpediente" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="20" />
				</td>
			</tr>
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
						name="impresionPegatinasBean.tipo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Estado"
						disabled="false"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="descripcionEstado">Estado:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select 
						list="@org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas@getEstadoPegatinas()"
						id="descrEstado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="impresionPegatinasBean.estado"
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
							list="@escrituras.utiles.UtilesVista@getInstance().getJefaturasJPTEnums()"
							headerKey=""
							headerValue="Seleccione Jefatura Provincial"
							name="jefaturaJpt" 
							listKey="jefatura" 
							listValue="descripcion"/>
					</s:else>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label>Fecha Alta:</label>
				</td>

				<td align="left">
					<table width=100%>
						<tr>
							<td align="right">
								<label for="diaAltaIni">desde: </label>
							</td>

							<td>
								<s:textfield name="impresionPegatinasBean.fechaAlta.diaInicio" 
									id="diaAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" 
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="impresionPegatinasBean.fechaAlta.mesInicio"
									id="mesAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2"/>
							</td>

							<td>/</td>

							<td>
								<s:textfield name="impresionPegatinasBean.fechaAlta.anioInicio"
									id="anioAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="5" maxlength="4"/>
							</td>

							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" 
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
								<s:textfield name="impresionPegatinasBean.fechaAlta.diaFin"
									id="diaAltaFin"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2" />
							</td>

							<td>/</td>

							<td>
								<s:textfield name="impresionPegatinasBean.fechaAlta.mesFin"
									id="mesAltaFin"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="2" maxlength="2" />
							</td>

							<td>/</td>

							<td>
								<s:textfield name="impresionPegatinasBean.fechaAlta.anioFin"
									id="anioAltaFin"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									size="5" maxlength="4" />
							</td>

							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
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
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarImpresionPegatinas();"/>
				</td>
				<td>
					<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
						<input type="button" class="boton" name="bLimpiar" onclick="limpiarImpresionPegatinas()" value="Limpiar" />
					</s:if>
					<s:else>
						<input type="button" class="boton" name="bLimpiar" onclick="limpiarImpresionPegatinasUsuarioJPT()" value="Limpiar" />
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
										onchange="cambiarElementosPorPaginaConsulta('navegarImpresionPegatinas.action', 'displayTagDiv', this.value);" />
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
			<display:table name="lista" excludedParams="*" requestURI="navegarImpresionPegatinas.action" class="tablanotifi"
				uid="tablaPegatinas" summary="Listado de Pegatinas" cellspacing="0" cellpadding="0" sort="external"
				decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorJefatura">

				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="numExpediente" title="Num. Expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="tipo" title="Tipo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="descrEstado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="fechaAlta" title="Fecha Alta" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" format="{0,date,dd-MM-yyyy}"/>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().esTipoUsuarioAdminJefaturaJpt()}">
					<display:column property="jefatura" title="Jefatura" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				</s:if>
				<display:column style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksPegatina);' onKeyPress='this.onClick'">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucion(<s:property value="#attr.tablaPegatinas.idPegatina"/>);" title="Ver evolución"/>
							</td>
							<td><input
									type="checkbox"
									name="listaChecksPegatina"
									id="checkNotificacion"
									value='<s:property value="#attr.tablaPegatinas.idPegatina"/>, <s:property value="#attr.tablaPegatinas.matricula"/>,
										<s:property value="#attr.tablaPegatinas.tipo"/>, <s:property value="#attr.tablaPegatinas.estado"/>'/></td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bMarcarImpreso" id="bMarcarImpreso" value="Marcar Impreso" onkeypress="this.onClick" onclick="return marcarImpresoPegatinas(this);"/>				
					<input type="button" class="boton" name="bDescargarPdf" id="bDescargarPdf" value="Descargar PDF" onkeypress="this.onClick" onclick="return descargarPdfPegatina(this);"/>			
				</td>
			</tr>
		</table>
	</div>
</s:form>