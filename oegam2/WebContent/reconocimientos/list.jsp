<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script  type="text/javascript">
// Mover estos escripts a un js

	function limpiarBusquedaReconocimientos(){
		if ($('#idClinica').length>0){
			$('#idClinica').attr("value", "");
		}
		if ($('#idContrato').length>0){
			$('#idContrato').attr("value", "");
		}
		$('#idEstado').attr("value", "-1");
		$('#nif').attr("value", "");
		$('#diaAltaIni').attr("value", "");
		$('#mesAltaIni').attr("value", "");
		$('#anioAltaIni').attr("value", "");
		$('#diaAltaFin').attr("value", "");
		$('#mesAltaFin').attr("value", "");
		$('#anioAltaFin').attr("value", "");
		$('#diaPresentacionIni').attr("value", "");
		$('#mesPresentacionIni').attr("value", "");
		$('#anioPresentacionIni').attr("value", "");
		$('#diaPresentacionFin').attr("value", "");
		$('#mesPresentacionFin').attr("value", "");
		$('#anioPresentacionFin').attr("value", "");
	}

function buscarReconocimientos() {
	$('#formData').attr("action", "buscar_ReconocimientoMedico.action").trigger("submit");
}

function cambiarElementosPorPaginaConsulta() {
	document.location.href = 'navegar_ReconocimientoMedico.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

//Método para imprimir PDF.
function imprimirPDF(boton){
	doPostWithChecked("imprimirPDF_ReconocimientoMedico.action", null);
}

//Método para imprimir Excel resumen.
function resumenXLS(boton){
	$('#formData').attr("action", "resumenXLS_ReconocimientoMedico.action").trigger("submit");
}

// Nueva petición de reconocimiento médico
function nuevoReconocimiento(boton){
	$('#formData').attr("action", "editar_ReconocimientoMedico.action").trigger("submit");
}

var ventanaEstados;
function abrirVentanaSeleccionEstadosReconocimiento(idBoton){
	var checks =  $("input[name='listaChecksReconocimientos']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alguna petición");
		return false;
	}
	ventanaEstados = window.open('cargarPopUp_ReconocimientoMedico.action','popup','width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

//Función invocada desde el padre del pop up de cambios de estados de tramites
function invokeCambiarEstadoReconocimiento(estado) {
	ventanaEstados.close();
	doPostWithChecked("cambiarEstados_ReconocimientoMedico.action?valorEstadoCambio=" + estado);
}

var ventanaNoAsistio;
function abrirVentanaFechaVisita(idBoton){
	var checks =  $("input[name='listaChecksReconocimientos']:checked");
	if(checks.size() != 1) {
		alert("Debe seleccionar una petición");
		return false;
	}
	ventanaNoAsistio = window.open('cargarPopUpFechaVisita_ReconocimientoMedico.action','popup','width=325,height=280,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}
//Función invocada desde el padre del pop up de asistió con distinta fecha
function invokecambiarEstadosAsistioOtraFecha(anyo, mes, dia, hora, minuto) {
	ventanaNoAsistio.close();
	doPostWithChecked("cambiarEstadosAsistioOtraFecha_ReconocimientoMedico.action?fechaVisita.anio="+ anyo
			+"&fechaVisita.mes="+ mes+"&fechaVisita.dia="+ dia+"&fechaVisita.hora="+ hora+"&fechaVisita.minutos="+ minuto);
}

function eliminar () {
	if (confirm("¿Está seguro de que desea borrar la/s peticion/es seleccionada/s permanentemente?")){
		doPostWithChecked("eliminar_ReconocimientoMedico.action");	
	}
}

function cambiarEstadosAsistio() {
	doPostWithChecked("cambiarEstadosAsistio_ReconocimientoMedico.action");
}

function cambiarEstadosNoAsistio() {
	doPostWithChecked("cambiarEstadosNoAsistio_ReconocimientoMedico.action");
}

function doPostWithChecked(url, boton, dest){
	var checks =  $("input[name='listaChecksReconocimientos']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alguna petición");
		return false;
	}
    var arrayCodigos = new Array();
	checks.each(function() {
        arrayCodigos.push($(this).val());
	});
	if (url.indexOf("?") >= 0){
		url += "&idsReconocimientos=" + arrayCodigos;
	} else {
		url += "?idsReconocimientos=" + arrayCodigos;
	}
	if(dest!=null && dest.length>0){
		$.post(url+"?idsReconocimientos=" + arrayCodigos,
				$("#formData").serialize(),function(data) {
			  	dest.html(data);
			  //ocultarLoadingConsultar(boton);
			});
		
	}else{
		//$.post(url+"?numsExpediente=" + arrayCodigos, $("#formData").serialize());
		var input = $("<input>").attr("type", "hidden").attr("name", "numsExpediente").val(arrayCodigos);
		$('#formData').append($(input)).attr("action", url).trigger("submit");
	}
	mostrarLoadingConsultar(boton);
}

function muestraDocumentoReconocimiento(){

	var actionPoint = "mostrarDocumento_ReconocimientoMedico.action";
	var IE='\v'=='v';
	
	if(IE){
		alert("Est\u00E1 utilizando Internet Explorer, este navegador no permite la descarga autom\u00E1tica de ficheros por " +
				"la aplicaci\u00F3n.\n Pulse en el link que aparecer\u00E1 para imprimir el documento generado.\n\n" +
				"Recomendamos la instalaci\u00F3n de otro navegador para un uso m\u00E1s \u00F3ptimo de la aplicaci\u00F3n.");
		var mostrarLink = document.getElementById("mostrarDocumentoLink");
		mostrarLink.style.display = "block";
	}
	else {
		$('#formData').attr("action", actionPoint).trigger("submit");
	
	}
}

function muestraYOcultaReconocimientos(){
	var mostrarLink = document.getElementById("mostrarDocumentoLink");
	mostrarLink.style.display = "none";
	var actionPoint = "mostrarDocumento_ReconocimientoMedico.action";
	$('#formData').attr("action", actionPoint).trigger("submit");
}


</script>


	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de solicitudes de reconocimientos médicos</span>
				</td>
			</tr>
		</table>
	</div>	
	
	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">					
			<table class="tablaformbasica">
				<tr>
        				
	        		<td align="left" nowrap="nowrap">
        				<label for="numExpediente">Clínica: </label>
       				</td>

					<td align="left">
       				<s:if test="%{clinicas.size()==1}">
       					<s:hidden name="filtro.idClinica" value="%{clinicas.get(0).getIdContrato()}"></s:hidden>
       					<s:property value="%{clinicas.get(0).getRazonSocial()}" />
       				</s:if>
       				<s:else>
						<s:select list="clinicas"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" listKey="idContrato"
							listValue="razonSocial" cssStyle="width:150px" id="idContrato"
							name="filtro.idClinica" emptyOption="true"></s:select>
       				</s:else>
					</td>
	
					<td align="left" nowrap="nowrap">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<label for="numColegiado">Contrato: </label> 
       				</s:if>
       				</td>     
       				<td align="left">
    				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
    					<s:select  list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()" onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idContrato" listKey="codigo" listValue="descripcion" cssStyle="width:150px" name="filtro.idContrato"
    					emptyOption="true"></s:select>
    				</s:if>
    				<s:else>
	    				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
	    					<s:select  list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegio(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal())" onblur="this.className='input2';" onfocus="this.className='inputfocus';" listKey="indice" listValue="descripcion" cssStyle="width:150px" id="idContrato" name="filtro.idContrato"
	    					emptyOption="true" ></s:select>
	    				</s:if>
   					</s:else>
       				</td>							        	
		        </tr>
		        	
	        	<tr>
       				<td align="left" nowrap="nowrap">
						<label for="idEstado">Estado: </label>
					</td>			    				    				
 
					<td align="left">
						<s:select	list="comboEstados"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
	           				headerValue="TODOS"
							name="filtro.estado" 
							listKey="valorEnum" listValue="nombreEnum"
							id="idEstado"/>

					</td>
       				
				    <td align="left" nowrap="nowrap">
        				<label for="nif">NIF Cliente: </label>
       				</td>
    
       				<td align="left">
        				<s:textfield name="filtro.nif" 
        					id="nif" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="9" maxlength="9"/>
       				</td>
	        	</tr>

			</table>
		    
		    <table class="tablaformbasica">	
	        	<tr>
	        		<td align="right" nowrap="nowrap">
        				<label>Fecha de alta: </label>
       				</td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaAltaIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="filtro.periodoAlta.diaInicio" 
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="filtro.periodoAlta.mesInicio" 
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="filtro.periodoAlta.anioInicio" 
										id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4"/>
								</td>
	
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" 
				    					title="Seleccionar fecha">
					    				<img class="PopcalTrigger" 
					    					align="left" 
					    					src="img/ico_calendario.gif" 
					    					width="15" height="16" 
					    					border="0" alt="Calendario"/>
				    				</a>
								</td>
	
								<td width="2%"></td>
							</tr>
						</TABLE>
					</td>
					
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaAltaFin">hasta:</label>
								</td>
						
								<td>
									<s:textfield name="filtro.periodoAlta.diaFin" 
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="filtro.periodoAlta.mesFin" 
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="filtro.periodoAlta.anioFin" 
										id="anioAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
								
								<td>
						    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
						    			title="Seleccionar fecha">
						    			<img class="PopcalTrigger" 
						    				align="left" 
						    				src="img/ico_calendario.gif" 
						    				width="15" height="16" 
						    				border="0" alt="Calendario"/>
				   			    	</a>
								</td>
							</tr>
						</TABLE>
					</td>
				</tr>

				<tr>
	        		<td align="right" nowrap="nowrap">
       					<label>Fecha de la cita:</label>
       				</td>
						
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaPresentacionIni">desde: </label>
								</td>
								
								<td>
									<s:textfield name="filtro.periodoPresentacion.diaInicio" 
										id="diaPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>

								<td>/</td>

								<td>
									<s:textfield name="filtro.periodoPresentacion.mesInicio" 
										id="mesPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
				
								<td>/</td>
						
								<td>
									<s:textfield name="filtro.periodoPresentacion.anioInicio" 
										id="anioPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
						
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionIni, document.formData.mesPresentacionIni, document.formData.diaPresentacionIni);return false;" 
				    					title="Seleccionar fecha">
				    					<img class="PopcalTrigger" 
				    						align="left" 
				    						src="img/ico_calendario.gif" 
				    						width="15" height="16" 
				    						border="0" alt="Calendario"/>
				    				</a>
								</td>
							
								<td width="2%"></td>
							</tr>
						</TABLE>
					</td>
						
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaPresentacionFin">hasta:</label>
								</td>
									
								<td>
									<s:textfield name="filtro.periodoPresentacion.diaFin" 
										id="diaPresentacionFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
									
								<td>/</td>
									
								<td>
									<s:textfield name="filtro.periodoPresentacion.mesFin" 
										id="mesPresentacionFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
									
								<td>/</td>
									
								<td>
									<s:textfield name="filtro.periodoPresentacion.anioFin"
										id="anioPresentacionFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
									
								<td>
						   			<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionFin, document.formData.mesPresentacionFin, document.formData.diaPresentacionFin);return false;" 
						   				title="Seleccionar fecha">
						   				<img class="PopcalTrigger" 
						   					align="left" 
						   					src="img/ico_calendario.gif" 
						   					width="15" height="16" 
						   					border="0" alt="Calendario"/>
				   			    	</a>
								</td>
							</tr>
						</TABLE>
					</td>
				</tr>
			</table>
					
			<table class="acciones">
				<tr>
					<td>
						<input type="button" 
							class="boton" 
							name="bBuscar" 
							id="bBuscar" 
							value="Buscar"  
							onkeypress="this.onClick" 
							onclick="return buscarReconocimientos();"/>			
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="return limpiarBusquedaReconocimientos();"/>			
					</td>
				</tr>
			</table>
		</div>
				<!--<div id="bloqueLoadingBuscar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>-->

<s:hidden key="contrato.idContrato"/>

<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div id="mostrarDocumentoLink" style="display: none;cursor:pointer;" onclick="muestraYOcultaReconocimientos();">
		<u>Documento generado (Link para imprimir)</u>
	</div>
		
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		<s:if test="%{listaReconocimiento.size()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
									value="resultadosPorPagina"
									title="Resultados por página"
									onchange="return cambiarElementosPorPaginaConsulta();" 
									/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>


<script  type="text/javascript">
$( function(){
	   $("#displayTagDiv").displayTagAjax();
})
</script>
<div id="displayTagDiv" class="divScroll">
    <div style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
<display:table name="listaReconocimiento" excludedParams="*"
					requestURI="navegar_ReconocimientoMedico.action"
					class="tablacoin"
					uid="tablaReconocimientos"
					summary="Listado de reconocimientos médicos"
					cellspacing="0" cellpadding="0"
					pagesize="${resultadosPorPagina}">

	<display:column property="persona.id.nif" title="NIF"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%"
			href="editar_ReconocimientoMedico.action" paramId="idReconocimiento" paramProperty="idReconocimiento" />
				
	<display:column property="clinica.razonSocial" title="Clínica"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" />
				
	<display:column property="fechaAlta" title="Fecha de alta"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%"
			format="{0, date, dd/MM/yyyy}"  />
				
	<display:column property="fechaReconocimiento" title="Fecha de cita"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%"
			format="{0, date, dd/MM/yyyy hh:mm}"  />

	<display:column title="Estado"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" >
			<s:property value="%{@general.utiles.enumerados.EstadoReconocimientoMedico@convertirTexto(#attr.tablaReconocimientos.estado)}" />
	</display:column>

	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksReconocimientos);' onKeyPress='this.onClick'/>" 
			style="width:1%" >
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="listaChecksReconocimientos" id="<s:property value="#attr.tablaReconocimientos.idReconocimiento"/>" 
				value='<s:property value="#attr.tablaReconocimientos.idReconocimiento"/>' />
			</td>
		</tr>
		</table>		

 	</display:column>
				
	<input type="hidden" name="resultadosPorPagina"/>
</display:table>
</div>

	<table class="acciones">
	
		<s:if test="%{permisoClinica && (listaReconocimiento.size()>0)}">
    	<!--  Es clinica -->
    	
    	<tr>
    		<td width="20%">&nbsp;</td>
	    	<td>
	    		<input class="boton" type="button" id="bAsistio" name="bAsistio" value="Asistió" onClick="return cambiarEstadosAsistio(this);" onKeyPress="this.onClick" />
			</td>
			<td>
	    		<input class="botonMasGrande" type="button" id="bAsistioOtraFecha" name="bAsistioOtraFecha" value="Asistió en otra fecha" onClick="return abrirVentanaFechaVisita(this);" onKeyPress="this.onClick" />
			</td>
    		<td>
	    		<input class="boton" type="button" id="bNoAsistio" name="bNoAsistio" value="No asistió" onClick="return cambiarEstadosNoAsistio(this);" onKeyPress="this.onClick" />
	    	</td>
    		<td width="20%">&nbsp;</td>
    	</tr>
    	</s:if>
    	<s:elseif test="%{!permisoClinica}">
	    	<!-- Es Gestor -->
	    	<tr>
	    		<td width="20%">&nbsp;</td>
		    	<td>
		    		<input class="boton" type="button" id="bNueva" name="bNueva" value="Nueva petición" onClick="return nuevoReconocimiento(this);" onKeyPress="this.onClick" />
		    	</td>
		    	<s:if test="%{listaReconocimiento.size()>0}">
	    		<td>
		    		<input class="boton" type="button" id="bImprimir" name="bImprimir" value="Imprimir Pdf" onClick="return imprimirPDF(this);" onKeyPress="this.onClick" />
		    	</td>
		    	<td>
		    		<input class="boton" type="button" id="bResumen" name="bResumen" value="Generar Resumen" onClick="return resumenXLS(this);" onKeyPress="this.onClick" />
		    	</td>
		    	</s:if>
	    		<td width="20%">&nbsp;</td>
	    	</tr>
	    	<!-- End si tiene permisos de Gestor -->
	    	
	    	
			<s:if test="%{(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()) && (listaReconocimiento.size()>0) }">
	    	<!-- Si tiene permisos de administrador -->
	    	
	    	<tr>
	    		<td width="20%">&nbsp;</td>
	    		<td>
	    			<input class="botonMasGrande" type="button" id="bCambiarEstado" name="bCambiarEstado" value="Cambiar Estado" onclick="javascript:abrirVentanaSeleccionEstadosReconocimiento('bCambiarEstado');" onKeyPress="this.onClick"/>
		    	</td>
				<!--  end Si tiene permisos de administrador y si tiene permisos de clinica -->
				<td>
		    		<input class="boton" type="button" id="bEliminar" name="bEliminar" value="Eliminar" onClick="return eliminar(this);" onKeyPress="this.onClick" />
	    		</td>
	    	</tr>
	    	</s:if>
    	</s:elseif>
    </table>
	
    <div id="bloqueLoadingConsultar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>

</s:form>

<s:if test="%{impresoEspera}">
		<script type="text/javascript">muestraDocumentoReconocimiento();</script>
</s:if>
<s:else>
		<script type="text/javascript">
		if(document.getElementById("mostrarDocumentoLink")) {
			document.getElementById("mostrarDocumentoLink").style.display = "none";
		}
		</script>
</s:else>
