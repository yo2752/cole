<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/bien/bienFunction.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Bienes</span>
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
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
							<label for="labelProvincia">Provincia:</label>
						</td>
						<td  align="left">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaProvincias()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Provincia" 
					    		name="bienFilter.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvincia" 
								onchange="cargarListaMunicipiosCam('idProvincia','idMunicipio');"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelMunicipio">Municipio:</label>
						</td>
						<td align="left" nowrap="nowrap">
							 <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipio"
								headerKey=""	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
								name="bienFilter.idMunicipio"
								list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaMunicipiosPorProvinciaConsulta(bienFilter.idProvincia)"/>
						</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCodPostal">Código Postal:</label>
					</td>
					<td  align="left">
						<s:textfield name="bienFilter.codPostal" id="idCodPostal" size="5" maxlength="5" onblur="this.className='input';" 
									onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoBien">Tipo Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select list="@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@values()"
							onblur="this.className='input2';" onchange="cargarListaTiposInmuebles('idTipoBien','idTipoInmueble');"
					    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Bien" 
					    	name="bienFilter.idTipoBien" listKey="valorEnum" listValue="nombreEnum" id="idTipoBien" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoInmueble">Tipo Inmueble:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmueblesConsulta(bienFilter)"
							onblur="this.className='input2';" 
					    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
					    	name="bienFilter.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmueble" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelRefCatrastal">Referencia Catastral:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="bienFilter.refCatrastal" id="idRefCatrastal" size="25" maxlength="30" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>	
					<td align="left" nowrap="nowrap">
						<label for="idufirBien">IDUFIR/CRU:</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="bienFilter.idufir" id="idIdufirConsulta" size="15" maxlength="14" 
			       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="seccionRegistralBien">Sección Registral:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="bienFilter.seccion" id="idSeccion" size="11" maxlength="10" 
			       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
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
									<label for="labelFechaAltaDesde">Desde:</label>
								</td>
								<td align="left">
									<s:textfield name="bienFilter.fechaAlta.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="bienFilter.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="bienFilter.fechaAlta.anioInicio" id="anioFechaAltaIni"
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
									<label for="labelFechaAltaHasta">Hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="bienFilter.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="bienFilter.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="bienFilter.fechaAlta.anioFin" id="anioFechaAltaFin"
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
			<div id="bloqueLoadingConsultaBien" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<input type="button" class="boton" name="bConsulta" id="bConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarBienes();"/>			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsultaBienes();" value="Limpiar"/>		
		</div>
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
									id="idResultadosPorPagina" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaBnS.action', 'displayTagDiv', this.value);" /> 
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
					uid="tablaConsultaBienes" requestURI= "navegarConsultaBnS.action"
					id="tablaConsultaBienes" summary="Listado de Bienes"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	
				
				<display:column property="idBien" title="" media="none" paramId="idBien"/>	
						
				<display:column title="Tipo Bien" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" sortProperty="tipoInmueble" >
					<s:property value="%{@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@convertirTexto(#attr.tablaConsultaBienes.tipoBien)}" />
				</display:column>
				
				<display:column property="tipoInmueble" title="Tipo Inmueble" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column property="refCatrastal" title="Ref.Catrastal" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column property="provincia" title="Provincia" sortable="true" headerClass="sortable"
					sortProperty="direccion.idProvincia"   defaultorder="descending" style="width:4%" />	
					
				<display:column property="municipio" title="Municipio" sortable="true" headerClass="sortable" 
					sortProperty="direccion.idMunicipio" defaultorder="descending" style="width:3%"/>
				
				<display:column property="nombreVia" title="Nombre Vía" sortable="true" headerClass="sortable " 
					sortProperty="direccion.nombreVia" defaultorder="descending" style="width:4%"/>
				
				<display:column property="idufir" title="IDUFIR/CRU" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />	
				
				<display:column property="seccion" title="Sección Registral" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />	
					
				<display:column property="parcela" title="Parcela" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column property="poligono" title="Polígono" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosCBienes(this)' 
					onKeyPress='this.onClick'/>" style="width:4%" >
					<table align="center">
						<tr>
							<td style="border: 0px;">
				  				<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirDetalleBien(<s:property value="#attr.tablaConsultaBienes.idBien"/>);" title="Ver Detalle"/>
				  			</td>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaBienes.idBien"/>" 
									value='<s:property value="#attr.tablaConsultaBienes.idBien"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize()>0}">
			<div class="acciones center">
				<div id="bloqueLoadingBien" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>
				<input type="button" class="boton" name="bEliminar" id="bEliminar" value="Eliminar"  onkeypress="this.onClick" onclick="return eliminarBloque();"/>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaBienes" style="display: none; background: #f4f4f4;"></div>
</div>
