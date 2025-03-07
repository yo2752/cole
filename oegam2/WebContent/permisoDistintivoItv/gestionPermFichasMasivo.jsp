<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/permisoFichasDgt.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Gestión Permisos Y Fichas Masivos</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Trámite:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getTipoTramite()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Trámite" 
							name="gestionPermFicha.tipoTramite" listKey="valorEnum" listValue="nombreEnum" id="idTipoTramitePrmFchMsv"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato:</label>
					</td>
					<td  align="left">
						<s:select id="idContratoPrmFchMsv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:220px" name="gestionPermFicha.idContrato"
							/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="left">
									<s:textfield name="gestionPermFicha.fechaPresentacion.diaInicio" id="diaFechaPrtPrmFchMsv"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermFicha.fechaPresentacion.mesInicio" id="mesFechaPrtPrmFchMsv" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermFicha.fechaPresentacion.anioInicio" id="anioFechaPrtPrmFchMsv"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtPrmFchMsv, document.formData.mesFechaPrtPrmFchMsv, document.formData.diaFechaPrtPrmFchMsv);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsultaPrmFchMsv" id="idBConsultaPrmFchMsv" value="Consultar"  onkeypress="this.onClick" onclick="return buscarGestionPrmFchMsv();"/>			
			<input type="button" class="boton" name="bLimpiarPrmFchMsv" id="idBLimpiarPrmFchMsv" onclick="javascript:limpiarGestionPrmFchMsv();" value="Limpiar"/>		
		</div>
		<br/>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
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
										id="idResultadosPorPaginaGestionPrmFchMsv" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaGestionPrmFchMsv();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivGestionPrmFchMsv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaGestionPrmFchMsv" requestURI= "navegarGPFMasivo.action"
				id="tablaGestionPrm" summary="Listado de Tramites" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="tipoTramite" title="Tipo Tramite" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="tipoTramite"/>

				<display:column property="numExpediente" title="Num.Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="numExpediente"/>

				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="matricula"/>

				<display:column property="estadoTramite" title="Estado" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="docPermiso" title="Doc.Permiso" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" sortProperty="docPermisoVO.docIdPerm"/>

				<display:column property="docFicha" title="Doc.Ficha" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" sortProperty="docFichaTecnicaVO.docIdPerm"/>	

			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingGestionPrmFchMsv" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<input type="button" class="boton" name="bSolPrmFchMsv" id="idBSolPrmFchMsv" value="Solicitar Permisos" onClick="javascript:solicitarPrmMsv('Solicitar Permisos');"
					onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bSolFchFchMsv" id="idBSolFchFchMsv" value="Solicitar Fichas" onClick="javascript:solicitarFchMsv('Solicitar Fichas');"
					onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form>
</div>
<div id="divEmergenteGestionPrmFchMsv" style="display: none; background: #f4f4f4;"></div>