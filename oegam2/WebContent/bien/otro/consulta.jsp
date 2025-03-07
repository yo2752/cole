<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
		<script src="js/bien/bienFunction.js" type="text/javascript"></script>
		<script src="js/remesas/remesasFunction.js" type="text/javascript"></script>
		<script src="js/trafico/comunes.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
	</head>
	<body class="popup">
		<s:form method="post" id="formData" name="formData">
			<s:hidden name="codigo" id="codigo"/>
			<s:hidden name="tipoModelo" id="tipoModelo"/>
			<div class="nav">
				<table width="100%" >
					<tr>
						<td class="tabular">
							<span class="titulo">Consulta de Otros Bienes</span>
						</td>
					</tr>
				</table>
			</div>
			<%@include  file="../../includes/erroresYMensajes.jspf" %>
		
		<s:hidden name="bienFilter.idTipoBien" value="OTRO"/>
			
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelRefCatrastal">Referencia Catastral:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="bienFilter.refCatrastal" id="idRefCatrastal" size="25" maxlength="30" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';"/>
					</td>	
					<td align="left" nowrap="nowrap">
						<label for="idufirBienOtro">IDUFIR/CRU</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="bienFilter.idufir" id="idIdufirConsulta" size="15" maxlength="14" 
			       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';"/>
					</td>	
				</tr>
				<s:hidden name="idTipoBien" id="idTipoBien" value="OTRO" />
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
				<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarBienesOtros();" />			
				<input type="button" class="boton" name="bLimpiar" onclick="javascript:limpiarConsultaBienOtro();" value="Limpiar"/>		
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
													onchange="cambiarElementosPorPaginaConsulta('inicioConsultaBnS.action?esPopup=S','displayTagConsu', this.value);">
										</s:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
			<div id="displayTagConsu" class="divScroll">
				<div
					style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../../../includes/bloqueLoading.jspf"%>
				</div>
				<display:table name="lista" class="tablacoin"
						uid="tablaConsultaOtrosBienes" requestURI= "inicioConsultaBnS.action?esPopup=S"
						id="tablaConsultaOtrosBienes" summary="Listado de Otros Bienes"
						excludedParams="*" sort="list"				
						cellspacing="0" cellpadding="0"
						decorator="${decorator}">	
					
					<display:column property="idBien" title="" media="none" paramId="id"/>
										
					<display:column property="refCatrastal" title="Ref.Catrastal" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:30%" />
						
					<display:column property="idufir" title="IDUFIR/CRU" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:30%" />
						
							
					<display:column property="fechaAlta" title="Fecha de Alta"
					sortable="true" headerClass="sortable" defaultorder="descending" format="{0,date,dd/MM/yyyy}"
					style="width:30%;" />
					
					
				
					<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosSeleccionados(this)' 
						onKeyPress='this.onClick'/>" style="width:4%" >
						<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaOtrosBienes.idBien"/>" 
							value='<s:property value="#attr.tablaConsultaOtrosBienes.idBien"/>' />
				</display:column>
				</display:table>
			</div>
			<s:if test="%{lista.getFullListSize()>0}">
				<div class="acciones center">
					<input type="button" class="boton" name="bSeleccionarBienes" id="idSeleccionarBienes" 
				  		value="Seleccionar Bien" onClick="javascript:bienesSeleccionadosOtros();"onKeyPress="this.onClick"/>	
			  	</div>
			</s:if>
		</s:form>
		
		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-1000px; left:-500px;">
		</iframe>
	</body>
	
	<script>
		window.onunload = OnClose;
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

