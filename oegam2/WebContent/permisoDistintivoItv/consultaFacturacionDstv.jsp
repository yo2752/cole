<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/facturacionDstv.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Facturación Distintivos</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<div id="divError">
		<s:if test="hasActionMessages() || hasActionErrors()">
			<table class="tablaformbasica" width="100%">
				<tr>
					<td align="left">
						<ul id="mensajesInfo" style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						<s:if test="hasActionMessages()">
							<s:iterator value="actionMessages">
								<li><span><s:property /></span></li>
							</s:iterator>
						</s:if>
						</ul>
						<ul id="mensajesError" style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						<s:if test="hasActionErrors()">
							<s:iterator value="actionErrors">
								<li><span><s:property /></span></li>
							</s:iterator>
						</s:if>	
						</ul>
					</td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<ul id="mensajesInfo"
				style="color: green; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;"></ul>
			<ul id="mensajesError"
				style="color: red; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;"/></ul>
		</s:else>
		</div>
		<s:hidden name="nombreFichero"/>
		<s:hidden name="fechaGen"/>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelDocId">Id.Doc:</label>
					</td>
					<td align="left">
						<s:textfield name="facturacionDstv.docDistintivo" id="idDocIdFctDstv" size="16" maxlength="16" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Matricula:</label>
					</td>
					<td align="left">
						<s:textfield name="facturacionDstv.matricula" id="idMatriculaFctDstv" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Num.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="facturacionDstv.numExpediente" id="idNumExpedienteFctDstv" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato:</label>
					</td>
					<td align="left">
						<s:select id="idContratoFctDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:220px" name="facturacionDstv.idContrato"
							/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoDistintivo">Tipo Distintivo:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getTiposDistintivos()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Tipo Distintivo" 
							name="facturacionDstv.tipoDistintivo" listKey="valorEnum" listValue="nombreEnum" id="idTipoDistintivoFctDstv"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNifTitular">NIF Titular:</label>
					</td>
					<td align="left">
						<s:textfield name="facturacionDstv.nif" id="idNifTitularFctDstv" size="9" maxlength="9" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaFctDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="facturacionDstv.fecha.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="facturacionDstv.fecha.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="facturacionDstv.fecha.anioInicio" id="anioFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIni, document.formData.mesFechaPrtIni, document.formData.diaFechaPrtIni);return false;" 
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
									<label for="labelFechaFctHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="facturacionDstv.fecha.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="facturacionDstv.fecha.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="facturacionDstv.fecha.anioFin" id="anioFechaPrtFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFin, document.formData.mesFechaPrtFin, document.formData.diaFechaPrtFin);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bFactConsulta" id="idBFactConsulta" value="Consultar" onkeypress="this.onClick" onclick="javascript:buscarFactDstv();"/>
			<input type="button" class="boton" name="bFactLimpiar" id="idBFactLimpiar" onclick="javascript:limpiarFactDstv();" value="Limpiar"/>
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaPrmDstv" style="text-align: center;">
				<%@include file="resumenPermisoDistintivo.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0" style="width:100%;">
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
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPaginaFact" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsFactDstv();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivFactDstv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaFactDstv" requestURI= "navegarFactDstv.action"
				id="tablaFactDstv" summary="Listado de Facturacion de distintivos" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="idFact" title="" media="none" paramId="id"/>

				<display:column property="docId" title="docId" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" sortProperty="docDistintivo.docIdPerm"/>

				<display:column property="fecha" title="Fecha Alta"	sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" format="{0,date,dd/MM/yyyy}" />

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="contrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>
				<display:column property="totalFacturado" title="Total Facturado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="total"/>

				<display:column property="totalFacturadoDup" title="Total Facturado Duplicado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="totalDup"/>

				<display:column property="totalIncidencia" title="Total Incidencia" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="listadoIncidencias.cantidad"/>

				<display:column property="totalInciDuplicado" title="Total Incidencia Duplicado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="listadoIncidencias.duplicado"/>

				<display:column title="" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaFactDstv.idFact"/>"
									value='<s:property value="#attr.tablaFactDstv.idFact"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}"> 
			<div id="bloqueLoadingFacturacionDstv" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bNotificar" id="idNotificar" value="Notificar Incidencia" onClick="javascript:notificarIncidencia();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bGenerarExcel" id="idGenerarExcel" value="Generar Excel" onClick="javascript:generarExcel();"onKeyPress="this.onClick"/>
					<input type="button" class="botonGrande" name="bGenerarExcelDetallado" id="idGenerarExcelDetallado" value="Generar Excel Detallado" onClick="javascript:generarExcelDetallado();"onKeyPress="this.onClick"/>
					<s:if test="%{esDescargable}">
						<input type="button" class="boton" name="bDescargarExcel" id="idBDescargExcel" value="Descargar Excel" onClick="javascript:descargarExcel();"onKeyPress="this.onClick"/>
					</s:if>
				</s:if>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteFacturacionDstv" style="display: none; background: #f4f4f4;"></div>
</div>