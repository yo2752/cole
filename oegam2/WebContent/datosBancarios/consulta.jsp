<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/datosBancarios/datosBancariosFunction.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Datos Bancarios</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div>
	<s:form method="post" id="formData" name="formData">
		<s:hidden id="esAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"/>
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
							<label for="labelFormaPago">Forma de Pago:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getListaFormaPago()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Forma Pago"
							name="datosBancarios.formaPago" listKey="codigo" listValue="descripcion" id="idFormaPago"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato:</label>
					</td>
					<td  align="left">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
								listValue="descripcion" cssStyle="width:220px"
								name="datosBancarios.idContrato"></s:select>
						</s:if>
						<s:else>
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" headerKey=""
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:220px"
								name="datosBancarios.idContrato" disabled="true"></s:select>
						</s:else>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Nif Pagador:</label>
					</td>
					<td  align="left">
						<s:textfield name="datosBancarios.nif" id="idNIf" size="10" maxlength="10" onblur="this.className='input';"
									onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select id="idEstado"
							list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getComboEstados()"
							onblur="this.className='input2';" headerValue="Seleccione Estado"
							onfocus="this.className='inputfocus';" listKey="valorEnum" headerKey=""
							listValue="nombreEnum" cssStyle="width:220px"
							name="datosBancarios.estado"></s:select>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="datosBancarios.fechaAlta.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="datosBancarios.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="datosBancarios.fechaAlta.anioInicio" id="anioFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIni, document.formData.mesFechaAltaIni, document.formData.diaFechaAltaIni);return false;" 
										title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="datosBancarios.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="datosBancarios.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="datosBancarios.fechaAlta.anioFin" id="anioFechaAltaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFin, document.formData.mesFechaAltaFin, document.formData.diaFechaAltaFin);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<div id="bloqueLoadingConsultaDtBancarios" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<input type="button" class="boton" name="bConsulta" id="bConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarDatosBancarios();"/>
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsultaDatosBancarios();" value="Limpiar"/>
		</div>
		<br/>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;">
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
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
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPagina"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsulta();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaDtBancarios" requestURI= "navegarConsultaDtB.action"
					id="tablaConsultaDtBancarios" summary="Listado de Datos Bancarios"
					excludedParams="*" sort="list"
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">

				<display:column property="idDatosBancarios" sortable="true" headerClass="sortable"
					defaultorder="descending" paramId="idDatosBancarios" media="none"/>

				<display:column property="descripcion" title="Descripcion" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="numColegiado" title="Num.Colegiado" style="width:2%" />

				<display:column property="provContrato" title="Provincia" style="width:6%" />

				<display:column property="nifPagador" title="NIF Pagador" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column title="Forma Pago" sortable="true" headerClass="sortable" sortProperty="formaPago" defaultorder="descending" style="width:4%">
					<s:property value="%{@org.gestoresmadrid.core.tasas.model.enumeration.FormaPago@convertirTexto(#attr.tablaConsultaDtBancarios.formaPago)}" />
				</display:column>

				<display:column property="numCuenta" title="Num.Cuenta" style="width:4%" />

				<display:column property="fechaAlta" title="Fecha Alta"
						sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:4%"
						format="{0,date,dd/MM/yyyy}" />

				<display:column title="" style="width:4%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirDetalleDtBancario('<s:property value="#attr.tablaConsultaDtBancarios.idDatosBancarios"/>');" title="Ver Detalle"/>
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="consultaEvolucionDatosBancarios(<s:property value="#attr.tablaConsultaDtBancarios.idDatosBancarios"/>);" title="Ver evolución"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<div class="acciones center">
				<div id="bloqueLoadingAccionesDtBancarios" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>
				<input type="button" class="boton" name="bEliminar" id="bEliminar" onclick="javascript:eliminarConsultaDatosBancarios();" value="Eliminar"/>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteEvolucionDatosBancarios" style="display: none; background: #f4f4f4;"></div>
</div>