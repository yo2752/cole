<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/impr/gestionImpr.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Gestión IMPR</span>
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
						<label for="labelMatricula">Matricula:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaImpr.matricula" id="idMatriculaImpr" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Bastidor:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaImpr.bastidor" id="idBastidorImpr" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpr">Jefatura:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getJefatura()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Jefatura" 
				    		name="consultaImpr.jefatura" listKey="jefatura" listValue="descripcion" id="idJefatura"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNive">NIVE:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaImpr.nive" id="idNiveImpr" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
       					<label for="labelCarpeta">Carpeta: </label>        				
   					</td>
   					<td align="left">
   						<s:textfield name="consultaImpr.carpeta" id="idCarpetaImpr" onblur="this.className='input2';" 
	       				onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
       				</td>
       			</tr>
       			<tr>
					<td align="left" nowrap="nowrap">
						<label for="labeldocId">Doc Id:</label>
					</td>
					<td align="left">
   						<s:textfield name="consultaImpr.docId" id="idDocIdImpr" onblur="this.className='input2';" 
	       				onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
       				</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Trámite:</label>
					</td>
					<td  align="left">
						<s:select id="idTipoTramiteImpr" list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getListaTipoTramitesIMPR()"
							onblur="this.className='input2';" headerValue="TODOS" onfocus="this.className='inputfocus';" listKey="valorEnum" headerKey="" 
							listValue="nombreEnum" cssStyle="width:95px" name="consultaImpr.tipoTramite"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoSol">Estado Solicitud:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getEstadosSolicitudDocImpr()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaImpr.estadoSolicitud" listKey="valorEnum" listValue="nombreEnum" id="idEstadoSolImpr"/>
					</td>	
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Num. Expediente:</label>
					</td>
					<td align="left">
   						<s:textfield name="consultaImpr.numExpediente" id="idNumExpedienteImpr" onblur="this.className='input2';" 
	       				onfocus="this.className='inputfocus';" size="15" maxlength="15"/>
       				</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdImpr">Id IMPR:</label>
					</td>
					<td align="left">
   						<s:textfield name="consultaImpr.id" id="idIdImpr" onblur="this.className='input2';" 
	       				onfocus="this.className='inputfocus';" size="10" maxlength="10"/>
       				</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoImpr">Tipo IMPR:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getListaTipoImprConsulta()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaImpr.tipoImpr" listKey="valorEnum" listValue="nombreEnum" id="idTipoImpr"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpr">Estado IMPR:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().listadoOrdenado()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaImpr.estadoImpr" listKey="valorEnum" listValue="nombreEnum" id="idTipoImpr"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpr">Estado Impresión:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getEstadosImpresionesImpr()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaImpr.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idTipoImpr"/>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaImpr.fechaAlta.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaImpr.fechaAlta.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaImpr.fechaAlta.anioInicio" id="anioFechaPrtIni"
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
						<label for="labelFechaH">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaImpr.fechaAlta.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaImpr.fechaAlta.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaImpr.fechaAlta.anioFin" id="anioFechaPrtFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFin, document.formData.mesFechaPrtFin, document.formData.diaFechaPrtFin);return false;" 
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
			<input type="button" class="boton" name="bConsultaImpr" id="idBConsultaImpr" value="Consultar"  onkeypress="this.onClick" onclick="javascript:buscarImpr();"/>			
			<input type="button" class="boton" name="bLimpiarImpr" id="idBLimpiarImpr" onclick="javascript:limpiarConsultaImpr();" value="Limpiar"/>		
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaImpr" style="text-align: center;">
				<%@include file="resumenErroresImpr.jspf" %>
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
										id="idResultadosPorPaginaConsImpr" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaImpr();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaImpr" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaImpr" requestURI= "navegarGestImpr.action"
				id="tablaConsultaImpr" summary="Listado de Impr" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="id" title="ID" sortable="true" headerClass="sortable"
					defaultorder="descending"/>
				
				<display:column property="numExpediente" title="Num.Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending"/>		
					
			   <display:column property="matricula" title="Matricula" sortable="false"  headerClass="sortable" 
					defaultorder="descending"/>
					
			   <display:column property="bastidor" title="Bastidor" sortable="false"  headerClass="sortable" 
				defaultorder="descending"/>			
				
			   <display:column property="carpeta" title="Carpeta" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
			
			    <display:column property="tipoImpr" title="Tipo IMPR" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
			
			    <display:column property="tipoTramite" title="Tipo Tramite" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
				
				<display:column property="estadoImpr" title="Estado IMPR" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
				
				<display:column property="estadoSolicitud" title="Estado Solicitud" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
				
				<display:column property="docId" title="Doc.Id" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
				
				<display:column property="fechaAlta" title="Fecha Alta" sortable="true" headerClass="sortable"
						defaultorder="descending"/>								
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosImpr(this)' onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaImpr.id"/>" 
									value='<s:property value="#attr.tablaConsultaImpr.id"/>' />
							</td>
						</tr>
					</table>
				</display:column>
		
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaImpr" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<input type="button" class="boton" name="bCambiarEstadoImpr" id="idCambiarEstadoImpr" value="Cambiar Estado" onClick="javascript:cambiarEstadoImpr();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bModificarCarpetaImpr" id="idModificarCarpetaImpr" value="Modificar Carpeta" onClick="javascript:modificarCarpeta();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bSolicitarImpr" id="idSolicitarImpr" value="Solicitar Impr" onClick="javascript:solicitarImpr();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bGenerarKoImpr" id="idGenerarKoImpr" value="Generar KO" onClick="javascript:generarKoImpr();"onKeyPress="this.onClick"/>
				<input type="button" class="botonGrande" name="bDesasignarDocumentoImpr" id="idDesasignarDocumentoImpr" value="Desasignar Documento" onClick="javascript:desasignardocumentoImpr();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaImpr" style="display: none; background: #f4f4f4;"></div>
</div>
