<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/circularesOegam.js" type="text/javascript"></script>



<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta Circulares OEGAM</span></td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">


	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="right"><label for="tipo">Número Circular:</label></td>
				<td>
						<s:textfield name="consultaCircularFilterBean.numCircular" id="numCircular"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="25" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelEstadoCir">Estado:</label>
				</td>
				<td  align="left">
					<s:select list="@org.gestoresmadrid.oegam2.circulares.utiles.UtilesVistaCircular@getInstance().getEstados()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
			    		name="consultaCircularFilterBean.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoCirc"/>
				</td>	
			</tr>
		</table>
		
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha">Fecha de Inicio:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="consultaCircularFilterBean.fechaInicio.diaInicio" id="diaAltaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaCircularFilterBean.fechaInicio.mesInicio" id="mesAltaInicio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaCircularFilterBean.fechaInicio.anioInicio" id="anioAltaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaInicio, document.formData.mesAltaInicio, document.formData.diaAltaInicio);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>

				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaHasta">Hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="consultaCircularFilterBean.fechaFin.diaFin" id="diaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaCircularFilterBean.fechaFin.mesFin" id="mesAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaCircularFilterBean.fechaFin.anioFin" id="anioAltaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
				  					title="Seleccionar fecha">
				  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
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
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Consultar" onkeypress="this.onClick"	onclick="consultaCircular();" />					
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar"	id="bLimpiar" value="Limpiar" onclick="return limpiarDiv('busqueda');" />
				</td>

			</tr>

		</table>
	</div>
	<s:if test="%{resumen != null}">
			<br>
			<div id="resumenCirculares" style="text-align: center;">
				<%@include file="resumenCircular.jspf" %>
			</div>
			<br><br>
		</s:if>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultados de la Búsqueda:</td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresYMensajes.jspf"%>
	<s:if
		test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right"><label
								for="idResultadosPorPaginaCircular">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right"><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPaginaCircular" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaCircular();" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>


	<div id="displayTagDivCircular" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>

		 <display:table name="lista" excludedParams="*"
			requestURI="navegarConsultaCircular.action" class="tablacoin"
			uid="tablaConsultas" summary="Listado de Circulares" cellspacing="0"
			cellpadding="0" sort="external">
			
			<display:column property="idCircular" media="none" /> 
			
			<display:column property="numCircular" title="Num. Circular"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>
				
			<display:column property="estado" title="Estado"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>	
				
			<display:column property="fechaInicio" title="Fecha Inicio"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy}" />
				
			<display:column property="fechaFin" title="Fecha Fin"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy}" />
				
			<display:column property="dias" title="Num. Días"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>		
							
			<display:column property="texto" title="Texto"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>
			
			<display:column	title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksCircular);' 
				onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksCircular"
							value='<s:property value="#attr.tablaConsultas.idCircular"/>' />
						</td>
						<!-- <td style="border: 0px;">
					  			<img id = "infoTexto" src="img/botonDameInfo.gif" title = "#attr.tablaConsultas.idCircular" />
					  		</td> -->
					</tr>
				</table>
			</display:column>			
			
		</display:table>
	</div>
<s:if test="%{lista.getFullListSize() > 0 && @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		<div id="bloqueLoadingConsultaCircular" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
		<div class="acciones">
				<input type="button" class="boton" name="bCambiarEstado" id="idFinalizarCircular" value="Finalizar Circular" onClick="javascript:finalizarEstadoCircular();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bRevertir" id="idRevertirCircular" value="Revertir Circular" onClick="javascript:revertirCircular();"onKeyPress="this.onClick"/>	
		</div>
	</s:if>
</s:form>
<div id="divEmergenteConsultaCircular" style="display: none; background: #f4f4f4;"></div>

