<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="js/genericas.js" type="text/javascript"></script>

<s:set var="isAdmin"
	value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<s:hidden id="idListaCompraTasa" name="listaCompraTasa" />
<iframe width="174" height="189" name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
</iframe>
<s:form method="post" id="formData" name="formData">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Listado de
						compras de tasas DGT</span></td>
			</tr>
		</table>
	</div>
	<%@include file="../includes/erroresMasMensajes.jspf"%>
	<div id="busqueda">					
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="refPropia">Referencia propia</label>
				</td>
				<td align="left">
					<s:textfield name="compraTasasBean.refPropia"
						id="refPropia" align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="22"
						maxlength="50" cssStyle="display: inline;align=left;" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="justificante791">Justificante 791</label>
				</td>
				<td align="left">
					<s:textfield
						name="compraTasasBean.numJustificante791" id="numJustificante791"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="13"
						maxlength="13" cssStyle="display: inline;" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nrc">NRC</label>
				</td>
				<td align="left">
					<s:textfield name="compraTasasBean.nrc"
						id="nrc" align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="22"
						maxlength="22" cssStyle="display: inline;align=left;" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="autoliquidacion">Num autoliquidacion</label>
				</td>
				<td align="left">
					<s:textfield
						name="compraTasasBean.nAutoliquidacion" id="nAutoliquidacion"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="12"
						maxlength="12" cssStyle="display: inline;" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado</label>
				</td>
				<td align="left">
					<s:select
						list="@org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@values()"
						name="compraTasasBean.estado" id="estadoCompra" headerKey=""
						headerValue="Cualquier Estado" listKey="codigo"
						listValue="descripcion" cssStyle="display: inline;" />
				</td>
				<s:if test="%{#isAdmin==true}">
				<td align="left" nowrap="nowrap">
					<label for="contrato">Contrato</label>
				</td>
				<td align="left">
					<s:select id="contrato"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" listKey="codigo"
						listValue="descripcion" cssStyle="width:150px;display: inline;"
						name="compraTasasBean.idContrato" headerKey=""
						headerValue="Todos" />
				</td>
				</s:if>
			</tr>
		</table>
		    
	    <table class="tablaformbasica">	
        	<tr>
        		<td align="right" nowrap="nowrap">
        			<label>Fecha de alta: </label>
        		</td>
				<td align="left">
					<table WIDTH="100%" border="0">
						<tr>
							<td align="right"><label for="diaMatrIni">desde: </label></td>
							<td><s:textfield name="compraTasasBean.fechaAlta.diaInicio"
									id="diaMatrIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="compraTasasBean.fechaAlta.mesInicio"
									id="mesMatrIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="compraTasasBean.fechaAlta.anioInicio"
									id="anioMatrIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrIni, document.formData.mesMatrIni, document.formData.diaMatrIni);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
				<td align="left">
					<table WIDTH="100%">
						<tr>
							<td align="left"><label for="diaMatrIni">hasta: </label>
							</td>
							<td><s:textfield name="compraTasasBean.fechaAlta.diaFin"
									id="diaMatrFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="compraTasasBean.fechaAlta.mesFin"
									id="mesMatrFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="compraTasasBean.fechaAlta.anioFin"
									id="anioMatrFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrFin, document.formData.mesMatrFin, document.formData.diaMatrFin);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label>Fecha de compra: </label>
				</td>
				<td align="left">
					<table WIDTH="100%">
						<tr>
							<td align="right"><label for="diaMatrIni">desde: </label></td>
							<td><s:textfield
									name="compraTasasBean.fechaCompra.diaInicio" id="diaCompraIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="compraTasasBean.fechaCompra.mesInicio" id="mesCompraIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="compraTasasBean.fechaCompra.anioInicio"
									id="anioCompraIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCompraIni, document.formData.mesCompraIni, document.formData.diaCompraIni);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
				<td align="left">
					<table WIDTH="100%">
						<tr>
							<td align="left">
								<label for="diaMatrIni">hasta: </label>
							</td>
							<td><s:textfield name="compraTasasBean.fechaCompra.diaFin"
									id="diaCompraFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="compraTasasBean.fechaCompra.mesFin"
									id="mesCompraFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="compraTasasBean.fechaCompra.anioFin"
									id="anioCompraFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCompraFin, document.formData.mesCompraFin, document.formData.diaCompraFin);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" value="Buscar" class="boton" onClick="listarCompraTasas()" />
		<input type="button" value="Limpiar" class="boton" onClick="limpiarFiltro()" />
	</div>
	<br />
	<%@include file="./resumenImportacionTasas.jspf"%>
	<table class="subtitulo" cellSpacing="0" style="width: 100%;">
		<tr>
			<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
		</tr>
	</table>
	<s:if
		test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right"><label
								for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right"><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina" name="resultadosPorPagina"
									value="resultadosPorPagina" title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsulta('navegarCompraTasas', 'displayTagDiv', this.value);" />
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
		});
	</script>
	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" sort="external"
			requestURI="navegar${action}" class="tablacoin"
			uid="tablaCompraTasas" summary="Historico de compra de tasas"
			cellspacing="0" cellpadding="0">

			<display:column property="refPropia" title="Referencia"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:8%;" paramId="refPropia" maxLength="20" />

			<display:column property="importeTotalTasas" title="Importe"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:8%;" paramId="importeTotalTasas" format="{0, number, #, #,###,##0.00} €" />

			<display:column property="numJustificante791" title="Justificante791"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:8%;" paramId="src" />

			<display:column property="nrc" title="NRC" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:8%;"
				paramId="src" />

			<display:column property="nAutoliquidacion" title="Autoliquidacion"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:8%;" paramId="nAutoliquidacion" />

			<display:column property="fechaCompra" title="Fecha compra"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:8%;" paramId="src" format="{0,date,dd/MM/yyyy}" />

			<display:column property="estado.descripcion" title="Estado"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:8%;" paramId="importeTotalTasas" sortProperty="estado" />

			<display:column title="Detalle" href="detalle${action}.action"
				paramId="idCompras" paramProperty="idCompra" style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><img src="img/mostrar.gif"
							alt="Consulta detalle de la compra"
							style="height: 20px; width: 20px; cursor: pointer;"
							title="Consulta detalle de la compra" /></td>
					</tr>
				</table>
			</display:column>

			<display:column
				title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksCompraTasas);' 
				onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksCompraTasas"
							value='<s:property value="#attr.tablaCompraTasas.idCompra"/>' />
						</td>
					</tr>
				</table>
			</display:column>

		</display:table>
	</div>
	<!-- FIN TABLA DE RESULTADOS -->
		<div class="acciones center">
			<input type="button" value="Nueva compra" class="boton" onClick="nuevaCompra()" onKeyPress="this.onClick" />
			<s:if test="%{lista.getFullListSize()>0 }">
				<input type="button" value="Eliminar" class="boton" onclick="invokeEliminar();" onkeypress="this.onClick" />
				<input type="button" value="Descargar fichero tasas" class="botonMasGrande" onkeypress="this.onClick" onclick="invokeDescargaFicheroTasas();" />
				<input type="button" value="Descargar justificante pago" class="botonMasGrande" onkeypress="this.onClick" onclick="invokeDescargaJustificantePago();" />
			    <input type="button" value="Importar tasas" class="boton" onclick="abrirVentanaImportacionTasas();" onKeyPress="this.onClick"/>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" value="Cambiar estado" class="boton" onclick="abrirVentanaSeleccionEstadosCompra();" onKeyPress="this.onClick" />
				</s:if>
			</s:if>
		</div>
</s:form>

<div id="divEmegergenteTasas"
	style="display: none; background: #f4f4f4;"></div>

<script>
	function limpiarFiltro() {
		cleanInputs("busqueda");
	}

	function listarCompraTasas() {
		doPost('formData', 'buscar${action}.action');
	}

	function nuevaCompra() {
		doPost('formData', 'nueva${action}.action');
	}

	function invokeDescargaFicheroTasas() {
		doPostWithChecked('formData', 'descargarFichero${action}.action', 'listaChecksCompraTasas', 'idCompras', null, 1, true);
	}

	function invokeDescargaJustificantePago() {
		doPostWithChecked('formData', 'justificantePago${action}.action', 'listaChecksCompraTasas', 'idCompras', null, 1, true);
	}

	function invokeEliminar() {
		if (confirm("¿Está seguro de que desea eliminar las compras?")) {
			doPostWithChecked('formData', 'eliminar${action}.action', 'listaChecksCompraTasas', 'idCompras', null, 1);
		}
	}

	function abrirVentanaSeleccionEstadosCompra() {
		var checks = $("input[name='listaChecksCompraTasas']:checked");
		if ((checks.size()) < 1) {
			alert("Debe seleccionar al menos una compra");
			return false;
		}
		var $dest = $("#divEmegergenteTasas");
		loading("loadingImage");
		$
				.post(
						"cargarPopUp${action}.action",
						function(data) {
							if (data.toLowerCase().indexOf("<html") < 0) {
								$dest
										.empty()
										.append(data)
										.dialog(
												{
													modal : true,
													show : {
														effect : "blind",
														duration : 300
													},
													dialogClass : 'no-close',
													width : 350,
													buttons : {
														Seleccionar : function() {
															var estado = $(
																	"#nuevoEstadoCompra option:selected")
																	.attr(
																			"value");
															if (estado == "") {
																alert("Seleccione un estado");
																return false;
															}
															$(
																	"#divEmegergenteTasas")
																	.dialog(
																			"close");
															var url = "cambiarEstados${action}.action?estado="
																	+ estado;
															doPostWithChecked(
																	'formData',
																	url,
																	'listaChecksCompraTasas',
																	'idCompras',
																	null, 1);
														},
														Cerrar : function() {
															$(this).dialog(
																	"close");
														}
													}
												});
							} else {
								// Viene <html>, así que no es un modal
								var newDoc = document.open("text/html",
										"replace");
								newDoc.write(data);
								newDoc.close();
							}
							$(".ui-dialog-titlebar").hide();
						}).always(function() {
					unloading("loadingImage");
				});
	}



	function abrirVentanaImportacionTasas(){
		var checks =  $("input[name='listaChecksCompraTasas']:checked");
		if(checks.size() < 1) {
			alert("Debe seleccionar una operación de compra de tasas");
			return false;
		}
		if(checks.size() != 1) {
			alert("Debe seleccionar una única operación de compra de tasas");
			return false;
		}
		var $dest = $("#divEmegergenteTasas");
		loading("loadingImage");
		$.post("cargarPopUpImportacion${action}.action", function(data) {
			if (data.toLowerCase().indexOf("<html")<0) {
				$dest.empty().append(data).dialog({
					modal : true,
					show : {
						effect : "blind",
						duration : 300
					},
					dialogClass : 'no-close',
					width: 350,
					buttons : {
						Importar : function() {
							var idContrato = $("#contratoImportacion option:selected").attr("value");
							if(idContrato == ""){
								alert("Seleccione un contrato");
								return false;
							}
							var formato = $('input:radio[name=formatoImportacion]:checked').attr("value");
							if (confirm("¿Está seguro que desea realizar la importación de las tasas al contrato seleccionado?")) {
								$("#divEmegergenteTasas").dialog("close");
								var url = "importarTasas${action}.action?idContrato=" + idContrato + "&formato=" + formato;
								doPostWithChecked('formData', url, 'listaChecksCompraTasas', 'idCompras', null, 1);
							}
						},
						Cerrar : function() {
							$(this).dialog("close");
						}
					}
				});
			} else {
				// Viene <html>, así que no es un modal
				var newDoc = document.open("text/html", "replace");
				newDoc.write(data);
				newDoc.close();
			}
			$(".ui-dialog-titlebar").hide();
		}).always(function() {
			unloading("loadingImage");
		});
	}
</script>
