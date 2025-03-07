<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script type="text/javascript">

function cambiarElementosPorPagina(){

	document.location.href='navegar${action}?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function numChecked() {
	var checks = document.getElementsByName("listaChecksConsultaTasa");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function numCheckedPegatina() {
	var checks = document.getElementsByName("listaChecksConsultaTasaPegatina");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function eliminar() {
	if(numChecked() == 0 && numCheckedPegatina() == 0) {
		alert("Debe seleccionar alguna tasa");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaTasa");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			//Pasamos al action todos los id de los checks marcados, separados por guiones
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+checks[i].value;
			}
		}
		i++;
	}
	var checksPegatina = document.getElementsByName("listaChecksConsultaTasaPegatina");
	var codigosPegatina = "";
	i = 0;
	while(checksPegatina[i] != null) {
		if(checksPegatina[i].checked) {
			//Pasamos al action todos los id de los checks marcados, separados por guiones
			if(codigosPegatina==""){
				codigosPegatina += checksPegatina[i].value;
			}else{
				codigosPegatina += "-"+checksPegatina[i].value;
			}
		}
		i++;
	}
	if (confirm("¿Está seguro de que desea eliminar las tasas?")){
		document.forms[0].action="eliminar${action}?idCodigoTasaSeleccion="+codigos+"&idCodigoTasaPegatinaSeleccion="+codigosPegatina;
		document.forms[0].submit();
		return true;
	}else{
		return false;
	}
}

function cambiarFormato() {
	var codigos = "";
	var formato = "";
	var electronicos = numChecked();
	var pegatinas = numCheckedPegatina();
	if(electronicos == 0 && pegatinas == 0) {
		alert("Debe seleccionar alguna tasa");
		return false;
	} else if (electronicos > 0 && pegatinas > 0) {
		alert("Se debe seleccionar sólo tasas de un sólo formato");
		return false;
	} else if (electronicos > 0) {
		formato = 0;
		var checks = document.getElementsByName("listaChecksConsultaTasa");
		var i = 0;
		while(checks[i] != null) {
			if(checks[i].checked) {
				//Pasamos al action todos los id de los checks marcados, separados por guiones
				if(codigos==""){
					codigos += checks[i].value;
				}else{
					codigos += "-"+checks[i].value;
				}
			}
			i++;
		}
	} else if (pegatinas > 0) {
		formato = 1;
		var checksPegatina = document.getElementsByName("listaChecksConsultaTasaPegatina");
		i = 0;
		while(checksPegatina[i] != null) {
			if(checksPegatina[i].checked) {
				//Pasamos al action todos los id de los checks marcados, separados por guiones
				if(codigos==""){
					codigos += checksPegatina[i].value;
				}else{
					codigos += "-"+checksPegatina[i].value;
				}
			}
			i++;
		}
	}
	if (confirm("¿Está seguro de que desea cambiar el formato de las tasas?")){
		document.forms[0].action="cambiarFormato${action}?idCodigoTasaSeleccion="+codigos+"&formato="+formato;
		document.forms[0].submit();
		return true;
	}else{
		return false;
	}
}

function bloquearTasa(){
	var codigos = "";
	var electronicos = numChecked();
	var pegatinas = numCheckedPegatina();
	if (pegatinas > 0) {
		alert("No se pueden bloquear tasas de pegatina");
		return false;
	} else if(electronicos == 0) {
		alert("Debe seleccionar alguna tasa electrónica");
		return false;
	} else if (electronicos > 0) {
		var checks = document.getElementsByName("listaChecksConsultaTasa");
		var i = 0;
		while(checks[i] != null) {
			if(checks[i].checked) {
				//Pasamos al action todos los id de los checks marcados, separados por guiones
				if(codigos==""){
					codigos += checks[i].value;
				}else{
					codigos += "-"+checks[i].value;
				}
			}
			i++;
		}
	}
	if (confirm("¿Está seguro de que desea bloquear las tasas?")) {
		generarPopPupMotivoTasa("divEmergenteMotivoTasa",codigos);
	}
	
}

function desbloquearTasa() {
	var codigos = "";
	var electronicos = numChecked();
	var pegatinas = numCheckedPegatina();
	if (pegatinas > 0) {
		alert("No se pueden desbloquear tasas de pegatina");
		return false;
	} else if(electronicos == 0) {
		alert("Debe seleccionar alguna tasa electrónica");
		return false;
	} else if (electronicos > 0) {
		var checks = document.getElementsByName("listaChecksConsultaTasa");
		var i = 0;
		while(checks[i] != null) {
			if(checks[i].checked) {
				//Pasamos al action todos los id de los checks marcados, separados por guiones
				if(codigos==""){
					codigos += checks[i].value;
				}else{
					codigos += "-"+checks[i].value;
				}
			}
			i++;
		}
	}
	if (confirm("¿Está seguro de que desea desbloquear las tasas?")){
		document.forms[0].action="desbloquearTasa${action}?idCodigoTasaSeleccion="+codigos;
		document.forms[0].submit();
		return true;
	}else{
		return false;
	}
}

function desasignar() {
	var pegatinas = numCheckedPegatina();
	if(numChecked() == 0) {
		if (pegatinas >0) {
			alert("Las tasas de pegatinas no se pueden desasignar");
		} else {
			alert("Debe seleccionar alguna tasa");
		}
		return false;
	}
	if (pegatinas >0) {
		if (!confirm("Las tasas de pegatinas no se desasignarán, únicamente las tasas electrónicas")){
			return false;
		}
	}
	var checks = document.getElementsByName("listaChecksConsultaTasa");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			//Pasamos al action todos los id de los checks marcados, separados por guiones
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+checks[i].value;
			}
		}
		i++;
	}
	if (confirm("¿Está seguro de que desea desasignar las tasas?")){
		document.forms[0].action="desasignar${action}?idCodigoTasaSeleccion="+codigos;
		document.forms[0].submit();
		return true;
	}else{
		return false;
	}
}

function comprobarTasasElectronicas(){
	var electronica = numChecked();
	if(electronica > 0){
		alert("Actualmente, las tasas electrónicas no se pueden generar en pdf, únicamente las tasas de pegatinas.");
		return false;
	}else if(numCheckedPegatina() == 0) {
		alert("Debe seleccionar alguna tasa.");
		return false;
	}
	return true;
}

function exportar() {
	var pegatinas = numCheckedPegatina();
	if(numChecked() == 0) {
		if (pegatinas >0) {
			alert("Las tasas de pegatinas no se pueden exportar");
		} else {
			alert("Debe seleccionar alguna tasa");
		}
		return false;
	}
	if (pegatinas >0) {
		if (!confirm("Actualmente, las tasas de pegatinas no se pueden exportar, únicamente las tasas electrónicas")){
			return false;
		}
	}
	var checks = document.getElementsByName("listaChecksConsultaTasa");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
		//Pasamos al action todos los id de los checks marcados, separados por guiones
		if(codigos==""){
			codigos += checks[i].value;
		}else{
			codigos += "-"+checks[i].value;
		}
	}
	i++;
	}

	var checksPegatina = document.getElementsByName("listaChecksConsultaTasaPegatina");
	var codigosPegatina = "";
	i = 0;
	while(checksPegatina[i] != null) {
		if(checksPegatina[i].checked) {
			//Pasamos al action todos los id de los checks marcados, separados por guiones
			if(codigosPegatina==""){
				codigosPegatina += checksPegatina[i].value;
			}else{
				codigosPegatina += "-"+checksPegatina[i].value;
			}
		}
		i++;
	}

	
		document.forms[0].action="exportar${action}?idCodigoTasaSeleccion="+codigos+"&idCodigoTasaPegatinaSeleccion="+codigosPegatina;
		document.forms[0].submit();
		return true;

} 


function cargarFicheroTasas() {
	if (document.getElementById("ficheroOK").value=="true") {
		document.forms[0].action="descargarFichero${action}";
		document.forms[0].submit();
	}
	document.getElementById("ficheroOK").value="false";
}


</script>
<!--<div class="contentTabs" id="ConsultaTramitesMatriculacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">-->
			<div class="nav">
				<table width="100%" >
					<tr>
						<td class="tabular"><span class="titulo">
							Consulta de tasas
						</span></td>
					</tr>
				</table>
			</div>	
		<s:form method="post" id="formData" name="formData" action="buscarConsultaTasa.action">
			<div id="busqueda">					
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
					<tr>
						<td align="left" nowrap="nowrap">
        					<label for="TipoTasa">Tipo de Tasa:</label>
        				</td>
        				<td align="left">       
		        			<s:select
								list="@org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT@values()"
								headerKey="" headerValue="" name="consultaTasaBean.tipoTasa"
								listKey="valorEnum" listValue="nombreEnum" id="TipoTasa"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" />
						</td>
        				<td align="left" nowrap="nowrap">
        					<label for="CodigoTasa">C&oacute;digo de Tasa:</label>
        				</td>
        				<td align="left">
					    		<s:textfield id="CodigoTasa"
								onblur="this.className='input2';" 
			       				onfocus="this.className='inputfocus';" name="consultaTasaBean.codigoTasa">
			       				</s:textfield>
        				</td>
        			</tr>
        			<tr>
						<td align="left" nowrap="nowrap">
							<label for="idAsignada">Asignada:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@escrituras.utiles.UtilesVista@getInstance().getComboDecisionTrafico()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue=""
									name="consultaTasaBean.asignada" 
									listKey="valorEnum" listValue="nombreEnum"
									id="idAsignada"/>
						</td>	        	
		        	 
		        		<td align="left" nowrap="nowrap">
							<label for="idTipoTramite">Tipo de tr&aacute;mite:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().obtenerTipoTramite()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="TODOS"
									name="consultaTasaBean.tipoTramite" 
									listKey="tipoTramite" listValue="descripcion"
									id="idTipoTramite"/>
						</td>
					</tr>
					<tr>
		        		<td align="left" nowrap="nowrap">
        					<label for="NumeroExpediente">Nº Expediente:</label>
        				</td>
        				<td align="left">
        					<s:textfield name="consultaTasaBean.numExpediente" id="NumeroExpediente" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"/>
        				</td>       	       			
        				<td align="left" nowrap="nowrap">
        					<label for="ReferenciaPropia">Referencia Propia:</label>        				
        				</td>
        				<td align="left">
        					<s:textfield name="consultaTasaBean.refPropia" id="ReferenciaPropia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"/>
        				</td>
        			</tr>
        			<tr>
		        		<td align="left" nowrap="nowrap">
							<label for="idFormato">Formato:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa@values()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="TODOS"
									name="consultaTasaBean.formato" 
									listKey="codigo" listValue="descripcion"
									id="idFormato"/>
						</td>
        			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
        				<td align="left" nowrap="nowrap">
        					<label for="NumeroColegiado">N&uacute;mero de colegiado:</label>        				
        				</td>
        				<td align="left" nowrap="nowrap">
        					<s:textfield name="consultaTasaBean.numColegiado" id="numColegiado" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"></s:textfield>
        				</td>
        			</s:if>
        			</tr>
        			<tr>
		        		<td align="left" nowrap="nowrap">
							<label for="idFormato">Bloqueada:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo@values()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="CUALQUIERA"
									name="consultaTasaBean.bloqueada" 
									listKey="valorEnum" listValue="nombreEnum"
									id="idEstadoTasaBloqueo"/>
						</td>
						        		<td align="left" nowrap="nowrap">
							<label for="idAlmacen">Tipo Almac&eacute;n Tasa:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getAlmacenTasa()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="Seleccionar Tipo Almacén"
									name="consultaTasaBean.tipoAlmacen" 
									listKey="valorEnum" listValue="nombreEnum"
									id="idTipoAlmacenTasa"/>
						</td>
					</tr>
					
        			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().esGenerarCertificadoTasasNuevo()">
	        			<tr>
							<td align="left">
			   					<label for="conNive">Tasa ICOGAM: </label>
			   				</td>
			   				<td align="left">
			   					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getImportadoColegio()"
									name="consultaTasaBean.importadoIcogam" id="esImportColegio" headerKey="" headerValue="Todos" listKey="valorEnum"
									listValue="nombreEnum" />
			       			</td>
						</tr>
					</s:if>
		        </table>
		        <table cellSpacing="3" class="tablaformbasica" cellPadding="0">
		        	
		        	<tr>
		        		<td align="right" nowrap="nowrap">
        				<label>Fecha de alta:</label>
        				</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
								<td align="right"><label for="diaAltaIni">desde: </label></td>
								<td>
									<s:textfield name="consultaTasaBean.fechaAlta.diaInicio" id="diaAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaTasaBean.fechaAlta.mesInicio" id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaTasaBean.fechaAlta.anioInicio" id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" HIDEFOCUS title="Seleccionar fecha">
				    				<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    				</a>
								</td>
								<td width="2%"></td>
							</tr></TABLE>
						</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
						<td align="left"><label for="diaAltaFin">hasta:</label></td>
						<td>
							<s:textfield name="consultaTasaBean.fechaAlta.diaFin" id="diaAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="consultaTasaBean.fechaAlta.mesFin" id="mesAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="consultaTasaBean.fechaAlta.anioFin" id="anioAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td>
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" HIDEFOCUS title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		   			    	</a>
						</td>
						
							</tr></TABLE></td>
					</tr>



					<tr>
		        		<td align="right" nowrap="left">
        				<label>Fecha de asignación:</label>
        				</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
								<td align="right"><label for="diaAsignacionIni">desde: </label></td>
								<td>
									<s:textfield name="consultaTasaBean.fechaAsignacion.diaInicio" id="diaAsignacionIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaTasaBean.fechaAsignacion.mesInicio" id="mesAsignacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaTasaBean.fechaAsignacion.anioInicio" id="anioAsignacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAsignacionIni, document.formData.mesAsignacionIni, document.formData.diaAsignacionIni);return false;" HIDEFOCUS title="Seleccionar fecha">
				    				<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    				</a>
								</td>
								<td width="2%"></td>
							</tr></TABLE>
						</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
						<td align="left"><label for="diaAsignacionFin">hasta:</label></td>
						<td>
							<s:textfield name="consultaTasaBean.fechaAsignacion.diaFin" id="diaAsignacionFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="consultaTasaBean.fechaAsignacion.mesFin" id="mesAsignacionFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="consultaTasaBean.fechaAsignacion.anioFin" id="anioAsignacionFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td>
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAsignacionFin, document.formData.mesAsignacionFin, document.formData.diaAsignacionFin);return false;" HIDEFOCUS title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		   			    	</a>
						</td>
<!--						</td>-->
							</tr></TABLE></td>
					</tr>


					<tr>
		        		<td align="right" nowrap="nowrap">
        				<label>Fecha de vigencia:</label>
        				</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
								<td align="right"><label for="diaFinVigencia">hasta:&nbsp; </label></td>
								<td>
									<s:textfield name="consultaTasaBean.fechaFinVigencia.dia" id="diaFinVigencia"
										size="2" maxlength="2" value="31" disabled="true" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaTasaBean.fechaFinVigencia.mes" id="mesFinVigencia"
										size="2" maxlength="2" value="12" disabled="true" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaTasaBean.fechaFinVigencia.anio" id="anioFinVigencia"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td width="2%"></td>
							</tr></TABLE>
						</td>
					</tr>

				</table>
				<div id="botonBusqueda">
				<table width="100%">
					<tr>
						<td>
							<s:submit value="Buscar" cssClass="boton" onclick="javascript:buscarTasa();"/>
							<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="return limpiarFormConsultaTasas();"/>			
						</td>
					</tr>
				</table>
				</div>
			</div>		 <%-- /Div recuadro de búsqueda --%>
			
	<s:if test="%{resumenCertificadoTasasFlag}">
		<br>
		<div id="estadisticasCertificadoTasas" style="text-align: center;">
			<s:set var="funcionDescargarCertificado" value ="'descargarCertificadoTasas()'" />
			<s:hidden  name="ficheroDescarga" id ="ficheroDescarga"/>
			<%@include file="../../includes/resumenCertificadoTasas.jspf" %>
		</div>
		<br><br>
	</s:if>
	
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>
	
	<div id="resultado" >
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
								<td align="right" width="90%">
									<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina"
									value="resultadosPorPagina"
									title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaTasa.action', 'displayTagDiv', this.value);"
									/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<s:if test="%{!resumenValidacion && !resumenTramitacion && !resumenPendienteEnvioExcel && !resumenCertificadoTasasFlag && !resumenCambiosEstadoFlag}">
				<%@include file="../../includes/erroresMasMensajes.jspf" %>
		</s:if>
	</div>	
	
	     	
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		});
	</script>		
		<div id="displayTagDiv" class="divScroll" >
	    <div style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
	<display:table name="lista" excludedParams="*" sort="external"
					requestURI="navegar${action}"
					class="tablacoin"
					uid="tablaConsultaTasa"
					summary="Listado de Tasas"
					cellspacing="0" cellpadding="0" decorator="${decorator}">

		<display:column title="Código Tasa"
				sortProperty="codigoTasa"
				sortable="true" headerClass="sortable"
				defaultorder="descending" 
				style="width:4%">
			<a href="detalle${action}?idCodigoTasaSeleccion=<s:property value='#attr.tablaConsultaTasa.codigoTasa'/>&formato=<s:property value='#attr.tablaConsultaTasa.formato.codigo'/>"><s:property value="#attr.tablaConsultaTasa.codigoTasa"/></a>
		</display:column>

		<display:column property="tipoTasa" title="Tipo Tasa"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:4%" />
					
		<display:column property="fechaAlta" title="Fecha Alta"
				sortable="true" headerClass="sortable" format="{0,date,dd-MM-yyyy}"
				defaultorder="descending"
				style="width:4%" />
				
		<display:column property="numExpediente" title="Núm. Expediente"
				sortable="true" sortProperty="tramiteTrafico.numExpediente" headerClass="sortable"
				defaultorder="descending"
				style="width:4%" />
		
		<display:column property="fechaAsignacion" title="Fecha Asignación"
				sortable="true" headerClass="sortable" format="{0,date,dd-MM-yyyy}"
				defaultorder="descending"
				style="width:4%" />
		
		<display:column property="formato" title="Formato"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:4%" />
		<display:column property="tipoAlmacen" title="Almacén Tasa"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:4%" />		
		<display:column property="bloqueada" title="Estado"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:4%" />
		<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().esGenerarCertificadoTasasNuevo()">
			<display:column property="importadoIcogam" title="Tasa ICOGAM" sortable="true" headerClass="sortable"
					defaultorder="descending"
					style="width:4%">
			</display:column>
		</s:if>
		
		<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodasTasas(this)' onKeyPress='this.onClick'/>" 
				style="width:4%" >
			<table align="center">
				<tr>
					<td style="border: 0px;">
						<s:if test="#attr.tablaConsultaTasa.formato.codigo != 1">
							<input type="checkbox" name="listaChecksConsultaTasa" id="check<s:property value="#attr.tablaConsultaTasa.codigoTasa"/>" 
								value='<s:property value="#attr.tablaConsultaTasa.codigoTasa"/>' />
						</s:if>
						<s:else>
							<input type="checkbox" name="listaChecksConsultaTasaPegatina" id="check<s:property value="#attr.tablaConsultaTasa.codigoTasa"/>" 
								value='<s:property value="#attr.tablaConsultaTasa.codigoTasa"/>' />
						</s:else>	
					</td>
					<td style="border: 0px;">
						<s:if test="#attr.tablaConsultaTasa.formato.codigo != 1">
							<img title="Ver evolución" onclick="abrirEvolucionTasa('<s:property value="#attr.tablaConsultaTasa.codigoTasa"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
						</s:if>
						<s:else>
							<img title="Tasa creada por <s:property value='#attr.tablaConsultaTasa.apellidosNombre'/>" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Tasa creada por <s:property value='#attr.tablaConsultaTasa.apellidosNombre'/>" src="img/history.png">
						</s:else>	
					</td>
				</tr>
			</table>
	 	</display:column>
	 	
	</display:table>
	</div>

	<s:if test="%{lista.getFullListSize()>0 }">
	<div id="bloqueLoadingConsultaTasa" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
  	<table class="acciones">
    	<tr>
    		<td>
	    		<div>
	    			<input class="boton" type="button" id="bEliminar" name="bEliminar" value="Eliminar" onClick="eliminar();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bDesasignar" name="bDesasignar" value="Desasignar" onClick="desasignar();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bExportar" name="bExportar" value="Exportar" onClick="exportar();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bGenerar" name="bGenerar" value="Generar certificado" onClick="generarCertificadosTasas();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bGenerarTasas" name="bGenerarTasas" value="Generar Tasas" onClick="generarTasas();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bCambioFormato" name="bCambioFormato" value="Cambiar Formato" onClick="cambiarFormato();return false;" onKeyPress="this.onClick" />
	    		</div>
    		</td>
    	</tr>
    	<tr>
    		<td>
	    		<div>
	    			<input class="boton" type="button" id="bBloquearTasa" name="bBloquearTasa" value="Bloquear Tasa" onClick="bloquearTasa();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bDesbloquearTasa" name="bDesbloquearTasa" value="Desbloquear Tasa" onClick="desbloquearTasa();return false;" onKeyPress="this.onClick" />
	    			&nbsp;
	    			<input class="boton" type="button" id="bAlmacenTasa" name="bAlmacenTasa" value="Almacén Tasa" onClick="javascript:almacenTasas();" onKeyPress="this.onClick" />
	    		</div>
    		</td>
    	</tr>
    </table>
    </s:if>
    <s:hidden name="ficheroOK" id="ficheroOK"></s:hidden>
</s:form>
<div id="divEmegergenteTasas" style="display: none; background: #f4f4f4;"></div>
<div id="divEmergenteMotivoTasa" style="display: none; background: #f4f4f4;"></div>
<script>
	cargarFicheroTasas();

function marcarTodasTasas(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksConsultaTasaPegatina) {
		if (!document.formData.listaChecksConsultaTasaPegatina.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksConsultaTasaPegatina.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksConsultaTasaPegatina.length; i++) {
			document.formData.listaChecksConsultaTasaPegatina[i].checked = marcar;
		}
	}
	if (document.formData.listaChecksConsultaTasa) {
		if (!document.formData.listaChecksConsultaTasa.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksConsultaTasa.checked = marcar;
		}
		for (var j = 0; j < document.formData.listaChecksConsultaTasa.length; j++) {
			document.formData.listaChecksConsultaTasa[j].checked = marcar;
		}
	}
}

function generarTasas(){
	if(comprobarTasasElectronicas()){
		var checksPegatina = document.getElementsByName("listaChecksConsultaTasaPegatina");
		var codigosPegatina = "";
		i = 0;
		while(checksPegatina[i] != null) {
			if(checksPegatina[i].checked) {
				//Pasamos al action todos los id de los checks marcados, separados por guiones
				if(codigosPegatina==""){
					codigosPegatina += checksPegatina[i].value;
				}else{
					codigosPegatina += "-"+checksPegatina[i].value;
				}
			}
			i++;
		}
		$('#formData').attr("action","generarTasasPegatinas${action}?idCodigoTasaPegatinaSeleccion="+codigosPegatina);
		$('#formData').submit();
		return true;
	}
}


function buscarTasa(){
	// Validamos el numero de expediente
	var tasa = document.getElementById("CodigoTasa").value;
	if (tasa != null) {		
		tasa = tasa.replace(/\s/g,'');

		document.getElementById("CodigoTasa").value = tasa;
	}
	var numExpediente = document.getElementById("NumeroExpediente").value;
	if (numExpediente != null) {		
		numExpediente = numExpediente.replace(/\s/g,'');

		document.getElementById("NumeroExpediente").value = numExpediente;
	}
	var numColegiado = document.getElementById("numColegiado").value;
	if (numColegiado != null) {		
		numColegiado = numColegiado.replace(/\s/g,'');

		document.getElementById("numColegiado").value = numColegiado;
	}
	var refPropia = document.getElementById("ReferenciaPropia").value;
	if (refPropia != null) {		
		refPropia = refPropia.replace(/\s/g,'');

		document.getElementById("ReferenciaPropia").value = refPropia;
	}
	
	document.formData.action = "buscarConsultaTasa.action";
	document.formData.submit();
}
	
</script>