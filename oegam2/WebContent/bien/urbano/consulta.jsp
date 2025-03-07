<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<script src="js/bien/bienFunction.js" type="text/javascript"></script>
		<script src="js/modelos/remesasFunction.js" type="text/javascript"></script>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
		<script src="js/trafico/comunes.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
	</head>
	<body class="popup">
		<s:form method="post" id="formData" name="formData">
			<s:hidden name="codigo" id="codigo"/>
			<div class="nav">
				<table width="100%" >
					<tr>
						<td class="tabular">
							<span class="titulo">Consulta de Bienes Inmuebles de Naturaleza Urbana</span>
						</td>
					</tr>
				</table>
			</div>
			<%@include file="../../../../includes/erroresYMensajes.jspf" %>
			<div id="busqueda">
				<table class="tablaformbasica">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelProvincia">Provincia:</label>
						</td>
						<td  align="left">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaProvincias()" onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Provincia" 
					    		name="bien.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvincia" 
								onchange="cargarListaMunicipiosCam('idProvincia','idMunicipio');"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelMunicipio">Municipio:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipio"
								headerKey=""	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
								name="bien.idMunicipio"
								list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaMunicipiosPorProvinciaConsulta(bien.idProvincia)"
							/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelNombreVia">Nombre Vía Pública:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.nombreVia" id="idNombreVia" size="25" maxlength="50" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					
						<td align="left" nowrap="nowrap">
							<label for="labelCodPostal">Código Postal:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.codPostal" id="idCodPostal" size="5" maxlength="5" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelArrendamiento">Arrendamiento:</label>
						</td>
						<td  align="left">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getDecision()"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="TODOS" 
						    	name="bien.arrendamiento" listKey="valorEnum" listValue="nombreEnum" id="idArrendamiento" />
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelVPO">V.P.O:</label>
						</td>
						<td  align="left">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getDecision()"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="TODOS" 
						    	name="bien.viviendaProtOficial" listKey="valorEnum" listValue="nombreEnum" id="idVpo" />
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelTipoInmueble">Tipo Inmueble:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Urbano)"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
						    	name="bien.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleUrbano" />
						</td>
					
						<td align="left" nowrap="nowrap">
							<label for="labelAnioContrato">Año Contrato:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.anioContratacion" id="idAnioContrato" size="5" maxlength="4" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelSuperfCons">Superf. Construida:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="bien.superficieConst" id="idSuperfCons" size="7" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';"/>
						</td>
						
						<td align="left" nowrap="nowrap">
							<label for="idufirBien">IDUFIR/CRU</label>
							<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="bien.idufir" id="idIdufirConsulta" size="15" maxlength="14" 
				       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelRefCatrastal">Ref. Catrastal:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="bien.refCatrastal" id="idRefCatrastal" size="25" maxlength="30" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelEstado">Estado:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getComboEstados()"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="TODOS" 
						    	name="bien.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado" />
						</td>
					</tr>
					
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelFecha">Fecha Construcción:</label>
						</td>
						<td align="left">
							<table style="width:20%">
								<tr>
									<td align="right">
										<label for="labelFechaDesde">Desde: </label>
									</td>
									<td align="left">
										<s:textfield name="bien.fechaConstruccion.diaInicio" id="diaBusquedaIni"
											onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
									</td>
									<td align="left">
										<label class="labelFecha">/</label>
									</td>
									<td align="left">
										<s:textfield name="bien.fechaConstruccion.mesInicio" id="mesBusquedaIni" onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td align="left">
										<label class="labelFecha">/</label>
									</td>
									<td align="left">
										<s:textfield name="bien.fechaConstruccion.anioInicio" id="anioBusquedaIni"
											onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
									</td>
									<td align="left">
										<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaIni, document.formData.mesBusquedaIni, document.formData.diaBusquedaIni);return false;" 
		    								title="Seleccionar fecha">
		    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		    							</a>
									</td>
								</tr>
							</table>
						</td>
						<td align="left">
							<label for="labelFechaHasta">Hasta:</label>
						</td>
						<td align="left">
							<table style="width:20%">
								<tr>
									<td align="left">
										<s:textfield name="bien.fechaConstruccion.diaFin" id="diaBusquedaFin" onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td align="left">
										<label class="labelFecha">/</label>
									</td>
									<td align="left">
										<s:textfield name="bien.fechaConstruccion.mesFin" id="mesBusquedaFin" onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td align="left">
										<label class="labelFecha">/</label>
									</td>
									<td align="left">
										<s:textfield name="bien.fechaConstruccion.anioFin" id="anioBusquedaFin"
											onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
									</td>
									<td align="left">
										<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaFin, document.formData.mesBusquedaFin, document.formData.diaBusquedaFin);return false;" 
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
				<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarBienUrbano();"/>			
				<input type="button" class="boton" name="bLimpiar" onclick="javascript:limpiarConsultaBienUrbano();" value="Limpiar"/>		
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
													onblur="this.className='input2';"
													onfocus="this.className='inputfocus';"
													id="idResultadosPorPagina"
													name= "resultadosPorPagina"
													value="resultadosPorPagina"
													title="Resultados por página"
													onchange="cambiarElementosPorPaginaBienUrbano();">
										</s:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
			<div id="displayTagConsultaUrbanoDiv" class="divScroll">
				<div
					style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../../../includes/bloqueLoading.jspf"%>
				</div>
				<display:table name="lista" class="tablacoin"
						uid="tablaConsultaBienesUrbanos" requestURI= "navegarConsultaBienUrbano.action"
						id="tablaConsultaBienesUrbanos" summary="Listado de Bienes Inmuebles de Naturaleza Urbana"
						excludedParams="*" sort="list"				
						cellspacing="0" cellpadding="0"
						decorator="${decorator}">	
					
					<display:column property="idBien" title="" media="none" paramId="id"/>
					
					<display:column property="provincia" title="Provincia" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.idProvincia"/>
						
					<display:column property="municipio" title="Municipio" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.idMunicipio"/>
						
					<display:column property="codPostal" title="Cód.Postal" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.codPostal"/>
						
					<display:column property="nombreVia" title="Nombre Vía" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.nombreVia"/>
					
					<display:column property="numero" title="Número" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.numero"/>		
					
					<display:column property="escalera" title="Escalera" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.escalera"/>	
					
					<display:column property="planta" title="Planta" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="direccion.planta"/>
						
					<display:column property="idufir" title="IDUFIR/CRU" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
							
					<display:column property="superficieConst" title="Superf. Constr" sortable="true" headerClass="sortable " 
						defaultorder="descending" style="width:4%" sortProperty="superficieConst"/>
						
					<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosSeleccionados(this)' 
						onKeyPress='this.onClick'/>" style="width:4%" >
						<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaBienesUrbanos.idBien"/>" 
							value='<s:property value="#attr.tablaConsultaBienesUrbanos.idBien"/>' />
					</display:column>
				</display:table>
			</div>
			<s:if test="%{lista.getFullListSize()>0}">
				<div class="acciones center">
					<input type="button" class="boton" name="bSeleccionarBienes" id="idSeleccionarBienes" 
				  		value="Seleccionar Bien" onClick="javascript:bienesSeleccionadosUrbano();"onKeyPress="this.onClick"/>	
			  	</div>
			</s:if>
		</s:form>
	</body>
		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
	<script>
		window.onunload = OnClose;
		recargarComboMunicipios('idProvincia','idMunicipio','municipioSeleccionado');
		window.opener.document.getElementById("divEmergenteBienes").style.display = "block";
		function OnClose()
		{
		  if(window.opener != null && !window.opener.closed) 
		  {
			  window.opener.document.getElementById("divEmergenteBienes").style.display = "none";
		  }
		}
	</script>
</html>

