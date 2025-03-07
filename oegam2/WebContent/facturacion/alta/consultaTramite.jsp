<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<!-- <div id="contenido" class="contentTabs" style="display: block;"> -->

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de facturas</span>
				</td>
			</tr>
		</table>
	</div>

	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="idEstadoTramite">Forma de Pago: </label>
					</td>

					<td align="left">
						<s:select list="@facturacion.utiles.UtilesVistaFacturacion@getComboFormaPago()"
							id="codFormaPago"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="traficoTramiteMatriculacionBean.titularBean.persona.sexo"
							listValue="nombreEnum"
							listKey="valorEnum"
							title="Forma de Pago"
							headerKey="-1" headerValue="TODOS"/>
					</td>

					<td align="left" nowrap="nowrap">
						<label for="dniTitular">Tipo Trámite:</label>
					</td>

					<td align="left">
						<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramite()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						headerKey="-1"
						headerValue="TODOS"
						name="consultaTramiteTrafico.tipoTramite"
						listKey="valorEnum" listValue="nombreEnum"
						id="idTipoTramite"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="apellido1RazonSocialProveedor">Num Factura </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield size="10" maxlength="25"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="apellido2Proveedor">Num Colegiado</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield size="10" maxlength="25"/>
					</td>
				</tr>
			</table>

			<table class="tablaformbasica">
				<tr>
					<td align="right" nowrap="nowrap">
						<label>Fecha de modificacion:</label>
					</td>

					<td align="left">
						<table width=100%>
							<tr>
								<td align="right">
									<label for="diaAltaIni">desde:</label>
								</td>

								<td>
									<s:textfield name="consultaTramiteTrafico.fechaAlta.diaInicio"
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2"/>
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaTramiteTrafico.fechaAlta.mesInicio"
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2"/>
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaTramiteTrafico.fechaAlta.anioInicio"
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
									<s:textfield name="consultaTramiteTrafico.fechaAlta.diaFin"
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2" />
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaTramiteTrafico.fechaAlta.mesFin"
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2" />
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaTramiteTrafico.fechaAlta.anioFin"
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
						</table>
					</td>
				</tr>
			</table>

			<table class="acciones">
				<tr>
					<td>
						<input type="button"
							class="boton"
							name="bBuscar"
							id="bBuscar"
							value="Buscar"
							onkeypress="this.onClick"
							onclick="return buscarConsulta();"/>
						&nbsp;
						<input type="button"
							class="boton"
							name="bLimpiar"
							id="bLimpiar"
							value="Limpiar"
							onkeypress="this.onClick"
							onclick="return limpiarFormConsulta();"/>
					</td>
				</tr>
			</table>
		</div>
				<!--<div id="bloqueLoadingBuscar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>-->

<s:hidden key="contrato.idContrato"/>

<iframe width="174"
	height="189"
	name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js"
	src="calendario/ipopeng.htm"
	scrolling="no"
	frameborder="0"
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

	<!--</div>-->
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0" style="width:100%;">
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>

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
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								id="idResultadosPorPagina"
								value="resultadosPorPagina"
								title="Resultados por página"
								onchange="return cambiarElementosPorPaginaConsultaTrafico();"
								/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

<div class="divScroll">
	<display:table name="listaConsultaTramiteTrafico" excludedParams="*"
					requestURI="navegarConsultaTramiteTrafico.action"
					class="tablacoin"
					uid="tablaConsultaTramite"
					summary="Listado de Tramites"
					cellspacing="0" cellpadding="0"
					decorator="trafico.utiles.DecoradorTablas"
	>

	<display:column property="numExpediente" title="Num Expediente"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%;"
			href="detalleConsultaTramiteTrafico.action"
			paramId="numExpediente"
			/>

	<display:column property="referenciaPropia" title="Referencia Propia"
			sortable="true" headerClass="sortable"
			defaultorder="descending" maxLength="15"
			style="width:4%" />

	<display:column property="vehiculo.bastidor" title="Bastidor"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />

	<display:column property="vehiculo.matricula" title="Matricula"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:2%" />

	<display:column property="tasa.codigoTasa" title="Codigo Tasa"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />

	<display:column title="Tipo Tramite" property="tipoTramite.nombreEnum"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:2%">
	</display:column>

	<display:column title="Estado" property="estado.nombreEnum"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:2%">
	</display:column>

	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick'/>" 
			style="width:1%">
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="listaChecksConsultaTramite" id="<s:property value="#attr.tablaConsultaTramite.tipoTramite.valorEnum"/>-<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
				value='<s:property value="#attr.tablaConsultaTramite.numExpediente"/>' />
			</td>
			<td style="border: 0px;">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
				onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numExpediente"/>);" title="Ver evolución"/>
			</td>
		</tr>
		</table>

	</display:column>

	<input type="hidden" name="resultadosPorPagina"/>
</display:table>
</div>

<table class="tablacoin" cellSpacing="0" cellPadding="5" align="left" style="width:100%;">
			<thead>
			<tr>
				<th class="sortable">Num Colegiado</th>
				<th class="sortable">Num Factura</th>
				<th class="sortable">Forma Pago</th>
				<th class="sortable">Total</th>
				<th class="sortable">Fecha</th>
				<th class="sortable"></th>
			</tr>
			</thead>

			<tbody>
			<tr class="odd">
				<td align="left" nowrap="nowrap">
					<s:textfield size="20" maxlength="255" disabled="true"/>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield size="20" maxlength="255" disabled="true"/>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield size="20" maxlength="255" disabled="true"/>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield size="10" maxlength="255" disabled="true"/>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield size="10" maxlength="255" disabled="true"/>
				</td>
				<td align="left" nowrap="nowrap">
					<input type="checkbox" onkeypress="this.onClick" onclick="marcarTodos(this, document.formData.listaChecksConsultaTramite);" name="checkAll">
				</td>
			</tr>
			</tbody>
		</table>

	<table class="acciones">
		<tr>
			<td>
				<input class="boton" type="button" id="bValidarTramites" name="bValidarTramites" value="GenerarPDF" onClick="return validarTramite(this);" onKeyPress="this.onClick" />
			</td>
			<td>
				<input class="boton" type="button" id="bComprobar" name="bComprobar" value="Exportacion" onClick="return comprobarBloque(this);" onKeyPress="this.onClick" />
			</td>
			<td>
				<input class="boton" type="button" id="bDuplicar" name="bDuplicar" value="Modificar Factura" onClick="return duplicarTramite(this);" onKeyPress="this.onClick" />
			</td>
			<td>
				<input class="boton" type="button" id="bDuplicar" name="bDuplicar" value="Borrar Factura" onClick="return duplicarTramite(this);" onKeyPress="this.onClick" />
			</td>
		</tr>
	</table>

	<div id="bloqueLoadingConsultar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>

</s:form>

<s:if test="%{impresoEspera}">
		<script type="text/javascript">muestraDocumento();</script>
</s:if>