<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/consultaKo/consultaKo.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Documentos KO</span>
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
		<s:hidden name="nombreFichero"/>
		<s:hidden name="fechaGen"/>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Matricula:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaKo.matricula" id="idMatricula" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"
								onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Trámite:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.consultaKo.utiles.UtilesVistaConsultaKo@getInstance().getTipoTramite()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Trámite" 
				    		name="consultaKo.tipoTramite" listKey="valorEnum" listValue="nombreEnum" id="idTipoTramite" onChange="activarTipTran()"/>
					</td>
					
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Documento:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.consultaKo.utiles.UtilesVistaConsultaKo@getInstance().getTipoDocumento()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Trámite" 
				    		name="consultaKo.tipo" listKey="valorEnum" listValue="nombreEnum" id="idTipo" onChange="activarTipTran()"/>
					</td>	
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
					</td>
					<td  align="left">
							<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.consultaKo.utiles.UtilesVistaConsultaKo@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="consultaKo.idContrato"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Jefatura:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.consultaKo.utiles.UtilesVistaConsultaKo@getInstance().getJefaturasJPTEnum()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Jefatura" 
				    		name="consultaKo.jefatura" listKey="jefatura" listValue="descripcion" id="idTipoJefatura"/>
				    </td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.consultaKo.utiles.UtilesVistaConsultaKo@getInstance().getEstados()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
				    		name="consultaKo.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
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
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaKo.fecha.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaKo.fecha.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaKo.fecha.anioInicio" id="anioFechaAltaIni"
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
									<s:textfield name="consultaKo.fecha.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaKo.fecha.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaKo.fecha.anioFin" id="anioFechaAltaFin"
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
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="javascript: buscarKo();"/>			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsulta();" value="Limpiar"/>		
		</div>
		<br/>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaKo" style="text-align: center;">
				<%@include file="resumenConsultaKo.jspf" %>
			</div>
			<br><br>
		</s:if>
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
										id="idResultadosPorPaginaConsKo" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaKo();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaKo" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaKo" requestURI= "navegarConsMatrKo.action"
				id="tablaConsultaKo" summary="Listado de Consultas Matriculas Ko" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="matricula" title="Matrícula" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" />	
			
				<display:column property="descContrato" title="Colegiado" sortable="false" headerClass="sortable " 
					defaultorder="descending" style="width:3%" />
					
				<display:column property="tipoTramite" title="Tipo Trámite" sortable="true" headerClass="sortable " 
					defaultorder="descending" style="width:2%"/>
				
				<display:column property="tipo" title="Tipo Documento" sortable="true" headerClass="sortable " 
					defaultorder="descending" style="width:2%"/>	
				
				<display:column property="fecha" title="Fecha Alta"	sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:2%" format="{0,date,dd/MM/yyyy}" />
					
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
					<display:column property="jefatura" title="Jefatura" sortable="true" headerClass="sortable " 
					defaultorder="descending" style="width:2%" />	
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaKo.id"/>" 
									value='<s:property value="#attr.tablaConsultaKo.id"/>' />
							</td>
							<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucion(<s:property value="#attr.tablaConsultaKo.id"/>,'divEmergenteConsultaKo');" title="Ver evolución"/>
				  			</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
			<div id="bloqueLoadingConsultaKo" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{lista.getFullListSize()>0}">
					<input class="boton" type="button" id="idBCambiarEstadoKo" name="bCambiarEstadoKo" value="Cambiar Estado" onClick="javascript:cambiarEstadoCKO();" onKeyPress="this.onClick"/>
					<input class="boton" type="button" id="bGenerarExcelKo" name="bGenerarExcelKo" value="Generar Excel" onClick="javascript: generarExcelKo();" onKeyPress="this.onClick"/>
					<input class="boton" type="button" id="bValidarKo" name="bValidarKo" value="Validar" onClick="javascript: validarKo();" onKeyPress="this.onClick"/>
			    	<input type="button" class="boton" name="bDescargarExcel" id="idBDescargExcel" value="Descargar Excel" onClick="javascript:descargarExcel();"onKeyPress="this.onClick"/>
			
			    </s:if>
			</div>
	</s:form>
	<div id="divEmergenteConsultaKo" style="display: none; background: #f4f4f4;"></div>

</div>
