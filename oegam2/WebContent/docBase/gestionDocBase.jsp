<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/docBase/docBase.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Gestión Documento Base</span>
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
						<label for="labelDocIdDB">Doc.Id:</label>
					</td>
					<td  align="left">
						<s:textfield name="gestionDocBase.docId" id="idDocIdDB" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatriculaDB">Matr&iacute;cula:</label>
					</td>
					<td  align="left">
						<s:textfield name="gestionDocBase.matricula" id="idMatriculaDB" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)"  onmousemove="return validarMatricula_alPegar(event)"  />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpedienteDB">N&uacute;m.Expediente:</label>
					</td>
					<td  align="left">
						<s:textfield name="gestionDocBase.numExpediente" id="idNumExpedienteDB" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoCarpeta">Tipo Carpeta:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDocBase@getInstance().getTiposCarpeta()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Carpeta" 
				    		name="gestionDocBase.tipoCarpeta" listKey="valorEnum" listValue="nombreEnum" id="idTipoCarpetaDB"/>
					</td>	
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoDB">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDocBase@getInstance().getEstadosGestionDocBase()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
				    		name="gestionDocBase.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoDB"/>
					</td>	
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContratoDB">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoDB" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDocBase@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="gestionDocBase.idContrato"
								/>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="gestionDocBase.idContrato"/>
					</s:else>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaDB">Fecha Presentaci&oacute;n:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaIniDBDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="gestionDocBase.fechaPresentacion.diaInicio" id="diaFechaPrtIniDB"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionDocBase.fechaPresentacion.mesInicio" id="mesFechaPrtIniDB" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionDocBase.fechaPresentacion.anioInicio" id="anioFechaPrtIniDB"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIniDB, document.formData.mesFechaPrtIniDB, document.formData.diaFechaPrtIniDB);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="gestionDocBase.fechaPresentacion.diaFin" id="diaFechaPrtFinDB" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionDocBase.fechaPresentacion.mesFin" id="mesFechaPrtFinDB" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionDocBase.fechaPresentacion.anioFin" id="anioFechaPrtFinDB"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFinDB, document.formData.mesFechaPrtFinDB, document.formData.diaFechaPrtFinDB);return false;" 
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
			<input type="button" class="boton" name="bBuscarDB" id="idBBuscarDB" value="Buscar"  onkeypress="this.onClick" onclick="return buscarGestionDB();"/>			
			<input type="button" class="boton" name="bLimpiarDB" id="idBLimpiarDB" onclick="javascript:limpiarGestionDB();" value="Limpiar"/>		
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenGestionDB" style="text-align: center;">
				<%@include file="resumenGestionDocBase.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
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
										id="idResultadosPorPaginaGestionDB" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaGestionDB();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivGestionDB" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaGestionDB" requestURI= "navegarGestionDB.action"
				id="tablaGestionDB" summary="Listado de Documentos Base" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="id" title="" media="none" paramId="id"/>	

				<display:column property="docId" title="docId" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />	
				<display:column property="carpeta" title="Carpeta" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>
				
				<display:column property="fechaPresentacion" title="Fecha Presentación" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>
				
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosDB(this)' onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecksDb" id="check<s:property value="#attr.tablaGestionDB.id"/>" 
									value='<s:property value="#attr.tablaGestionDB.id"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingGestionDB" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<input type="button" class="boton" name="bDbDescargar" id="idBDbDescargar" value="Descargar" onClick="javascript:descargarDB();"onKeyPress="this.onClick"/>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() 
					|| @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().usuariosTrafico()}">
					<input type="button" class="boton" name="bDbEliminar" id="idDbEliminar" value="Eliminar" onClick="javascript:eliminarDB();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bDbReimprimir" id="idDbReimprimir" value="Reimprimir" onClick="javascript:reimprimirDB();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bDbCambiarEstado" id="idDbCambiarEstado" value="Cambiar Estado" onclick="javascript:abrirVentanaSeleccionEstados();" onkeypress="this.onClick" />
				</s:if>
			</div>
		</s:if>
	</s:form>
</div>
<div id="divEmergenteGestionDB" style="display: none; background: #f4f4f4;"></div>
