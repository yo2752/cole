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
		<s:form method="post" id="formDataNotario" name="formData">
			<s:hidden name="codigo" id="codigo"/>
			<s:hidden name="tipoModelo" id="tipoModelo"/>
			<div class="nav">
				<table width="100%" >
					<tr>
						<td class="tabular">
							<span class="titulo">Consulta de Bienes Rústicos</span>
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
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaProvincias()" onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Provincia" 
					    		name="bien.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvincia" 
								onchange="cargarListaMunicipiosBien(this,'idMunicipio','municipioSeleccionado');"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipio"
								onchange="seleccionarCampo('municipioSeleccionado','idMunicipio');" >
						    		<option value="">Seleccione Municipio</option>
						    </select>
						    <s:hidden id="municipioSeleccionado" name="bien.idMunicipio"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelCodPostal">Código Postal:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.codPostal" id="idCodPostal" size="5" maxlength="5" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelParaje">Paraje o sitio:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.paraje" id="idParaje" size="25" maxlength="30" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelPoligono">Polígono:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.poligono" id="idPoligono" size="25" maxlength="30" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelPoligono">Parcela:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.parcela" id="idParcela" size="25" maxlength="30" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelSubParcela">Sub.Parcela:</label>
						</td>
						<td  align="left">
							<s:textfield name="bien.subParcela" id="idSubParcela" size="25" maxlength="30" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelRefCatrastal">Referencia Catrastal:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield name="bien.refCatrastal" id="idRefCatrastal" size="25" maxlength="30" onblur="this.className='input2';" 
				       			onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelTipoGanaderia">Tipo Ganadería<span class="naranja">*</span>:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Ganaderia)"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Ganadería" 
						    	name="bien.idUsoRusticoGanaderia" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoGanaderia" />
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelTipoCultivo">Tipo Cultivo<span class="naranja">*</span>:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Cultivo)"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
						    	name="bien.idUsoRusticoCultivo" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoCultivo" />
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelOtros">Otros<span class="naranja">*</span>:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Otros)"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
						    	name="bien.idUsoRusticoOtros" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoOtros"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelSistemaExpl">Sistema de Explotación<span class="naranja">*</span>:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaSistemaExplotacion()"
								onblur="this.className='input2';" 
						    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
						    	name="bien.idSistemaExplotacion" listKey="idSistemaExplotacion" listValue="descSistema" id="idSistemaExplotacion" />
						</td>
					</tr>
					<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoInmueble">Tipo Bien<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Rustico)"
							onblur="this.className='input2';" 
					    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
					    	name="bien.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleRustico" />
					</td>
					</tr>
				</table>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarBienRustico();"/>			
				<input type="button" class="boton" name="bLimpiar" onclick="javascript:limpiarBienRustico();" value="Limpiar"/>		
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
													onchange="cambiarElementosPorPaginaBienRustico();">
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
						uid="tablaConsultaBienesRusticos" requestURI= "navegarConsultaBienRustico.action"
						id="tablaConsultaBienesRusticos" summary="Listado de Bienes Rusticos"
						excludedParams="*" sort="list"				
						cellspacing="0" cellpadding="0"
						decorator="${decorator}">	
					
					<display:column property="idBien" title="" media="none" paramId="id"/>
					
					<display:column property="provincia" title="Provincia" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
						
					<display:column property="municipio" title="Municipio" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
						
					<display:column property="codPostal" title="Cód.Postal" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
					
					<display:column property="descUsoRustico" title="Tipo de Uso" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />		
					
					<display:column property="refCatrastal" title="Ref.Catrastal" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />	
					
					<display:column property="poligono" title="Polígono" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
						
					<display:column property="parcela" title="Parcela" sortable="true" headerClass="sortable " 
						defaultorder="descending" style="width:4%"/>
						
					<display:column property="subParcela" title="Sub.Parcela" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%"/>
						
					<display:column property="paraje" title="Paraje" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
					<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosSeleccionados(this)' 
						onKeyPress='this.onClick'/>" style="width:4%" >
						<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaBienesRusticos.idBien"/>" 
							value='<s:property value="#attr.tablaConsultaBienesRusticos.idBien"/>' />
				</display:column>
				</display:table>
			</div>
			<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
				<div class="acciones center">
					<input type="button" class="boton" name="bSeleccionarBienes" id="idSeleccionarBienes" 
				  		value="Seleccionar Bien" onClick="javascript:bienesSeleccionados();" onKeyPress="this.onClick"/>	
			  	</div>
			</s:if>
		</s:form>
	</body>
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

