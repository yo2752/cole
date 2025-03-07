<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript" src="js/genericas.js"></script>
<script type="text/javascript" src="js/trafico/comunes.js"></script>
<script type="text/javascript" src="js/trafico/consultaTramites.js"></script>
<script type="text/javascript" src="js/registradores/registradores.js"></script> 

<script type="text/javascript">
	trimCamposInputs();

	function descargarAdjuntos() {
		if (numChecked() == 0) {
			alert("Debe seleccionar alg\u00fan tr\u00E1mite");
			return false;
		}
		iniciarProcesando('adjuntos','loadingImage');
		
		$('#formData').attr("action","descargarAdjuntosRecuperarTramite.action");
		$('#formData').submit();
		ocultarLoadingConsultarReg(document.getElementById('adjuntos'), "Adjuntos");
	}

	function buscarTramiteRegistro() {
		// Validamos el numero de expediente
		var idTramiteRegistro = document.getElementById("idTramiteRegistro").value;
		
		if (idTramiteRegistro != null) {		
			idTramiteRegistro = idTramiteRegistro.replace(/\s/g,'');
			
		if (isNaN(idTramiteRegistro)) {
			alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
				return false;
			}		
						
			document.getElementById("idTramiteRegistro").value = idTramiteRegistro;
		}
		// //Para que muestre el loading
		iniciarProcesando('bBuscar','loadingImage');
		document.formData.action = "buscarRecuperarTramite.action";
		document.formData.submit();
		ocultarLoadingConsultarReg(document.getElementById('bBuscar'), "Buscar");
	}

	function eliminarTramites(idBoton){
		if (numChecked() == 0) {
			alert("Debe seleccionar alg\u00FAn tr\u00E1mite");
			return false;
		}
		iniciarProcesando('btnEliminar','loadingImage');

		document.formData.action="eliminarTramitesRecuperarTramite.action";
		document.formData.submit();
	}

	function validarTramites(idBoton){
		if (numChecked() == 0) {
			alert("Debe seleccionar alg\u00FAn tr\u00E1mite");
			return false;
		}
		iniciarProcesando('btnValidar','loadingImage');
		
		document.formData.action="validarTramitesRecuperarTramite.action";
		document.formData.submit();
	}


	$(document).ready(function(){
	mostrarRegistro();
	});

	
	</script>


<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Trámite Registro</span>
			</td>
		</tr>
	</table>
</div>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<!-- Almacena una marca para no ejecutarse el action en caso de 'refresh' de la pagina -->
	<s:hidden name="dobleSubmit" id="dobleSubmit"/>
	<s:hidden name="idSession" id="idSession"/>
	<s:hidden name="idTramite"></s:hidden>
	
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
        		<td align="left" nowrap="nowrap" style="vertical-align: middle;">
        			<label for="tipoTramite">Tipo Tr&aacute;mite:</label>
      			</td>
        		<td align="left" nowrap="nowrap" colspan="3">
	      			<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getComboTipoTramites()"
						id="tipoTramite" onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="consultaTramiteRegistroBean.tipoTramite"
						listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="TODOS"/>
        		</td>

        	</tr>
        	<tr>
        		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
		        	<td align="left" nowrap="nowrap" style="vertical-align: middle;">
		        		<label for="contrato">Contrato:</label>
		      		</td>
					<td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<s:select
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:250px"
								headerValue="TODOS"
								headerKey=""
								id="idContrato"
								name="consultaTramiteRegistroBean.idContrato"></s:select>
						</s:if>
						<s:else>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
								<s:select
									list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegio(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal())"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" listKey="codigo"
									listValue="descripcion" cssStyle="width:150px"
									headerValue="TODOS"
									headerKey=""
									id="idContrato"
									name="consultaTramiteRegistroBean.idContrato"></s:select>
							</s:if>
						</s:else>
					</td>
		        </s:if>
		        <s:else>
		        	<s:hidden id="numColegiadoConsulta" name="consultaTramiteRegistroBean.numColegiado"/>
		        </s:else>
      		</tr>
        	<tr>
        		<td align="left" nowrap="nowrap" style="vertical-align: middle;">
		        	<label for="expediente">Nº Expediente:</label>
		      	</td>
		        <td align="left" >
			      	<s:textfield id="idTramiteRegistro" name="consultaTramiteRegistroBean.idTramiteRegistro" maxlength="16" size="16" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		        </td>
		        
		        <td align="left" nowrap="nowrap" style="vertical-align: middle;">
		        	<label for="expedienteRegistro">Nº Expediente Registro:</label>
		      	</td>
		        <td align="left" >
			      	<s:textfield id="idTramiteCorpme" name="consultaTramiteRegistroBean.idTramiteCorpme" maxlength="27" size="27" onblur="this.className='input2';this.value=this.value.toUpperCase();" onfocus="this.className='inputfocus';" onkeyup="this.value=this.value.toUpperCase()"/>
		        </td>

		      </tr>
		      
		      <tr>
        		<td align="left" nowrap="nowrap" style="vertical-align: middle;">
        			<label for="estado">Estado:</label>
      			</td>
        		<td align="left" >
	      			<s:select id="estadoTramite" name="consultaTramiteRegistroBean.estado" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getComboEstadoTramite()" 
		      			listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="TODOS"/>
        		</td>
        		<td align="left" nowrap="nowrap" style="vertical-align: middle;" >
					<label for="referenciaPropia">Ref. propia:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaTramiteRegistroBean.refPropia" id="referenciaPropia" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="40" maxlength="40"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
        			<label for="cif">CIF:</label>
      			</td>
        		<td align="left">
	      			<s:textfield id="cifTramite" name="consultaTramiteRegistroBean.cif" size="9" maxlength="9" onblur="this.className='input2';this.value=this.value.toUpperCase();" onfocus="this.className='inputfocus';" onkeyup="this.value=this.value.toUpperCase()" /> 
        		</td>

			    <td align="left" nowrap="nowrap" style="vertical-align: middle;">
        			<label for="registro">Registro:</label>
      			</td>
				<td  align="left" >
					<table>
						<tr>
			        		<td align="left" >
				      			<s:select id="tipoRegistro" name="consultaTramiteRegistroBean.tipoRegistro" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" list="#{'':'TODOS','RB':'Registro bienes muebles', 'RM':'Registro mercantil', 'RP':'Registro propiedad'}"
									onchange="buscarRegistro();"/>
			        		</td>
			        		
			        		<td align="left" nowrap="nowrap">
				      			<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroConsulta(consultaTramiteRegistroBean.tipoRegistro)"
									id="registro" onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="consultaTramiteRegistroBean.idRegistro"
									listKey="id" listValue="nombre" headerKey="" headerValue="TODOS"/>
			        		</td>
			        	</tr>
		        	</table>
	        	</td>
			</tr>
			
			<tr>
				<td align="left" style="vertical-align: middle;">
					<label for="diaCreacionIni">Fecha Creación:</label>
				</td>
				<td>
					<table style="width:20%">
						<tr>
							<td width="10%" align="left" style="vertical-align: middle;">
								<label for="diaCreacionIni">desde:</label>
							</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaCreacion.diaInicio" id="diaCreacionIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaCreacion.mesInicio" id="mesCreacionIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaCreacion.anioInicio" id="anioCreacionIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
					    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCreacionIni, document.formData.mesCreacionIni, document.formData.diaCreacionIni);return false;" title="Seleccionar fecha">
					    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
					    		</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table style="width:20%">
						<tr>
							<td width="10%" align="left" style="vertical-align: middle;">
								<label for="diaCreacionFin">hasta:</label>
							</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaCreacion.diaFin" id="diaCreacionFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaCreacion.mesFin" id="mesCreacionFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaCreacion.anioFin" id="anioCreacionFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
					    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCreacionFin, document.formData.mesCreacionFin, document.formData.diaCreacionFin);return false;" title="Seleccionar fecha">
					    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
						    	</a>
							</td>
						</tr>
					</table>
				</td>

			</tr>
			<tr>
				<td align="left" style="vertical-align: middle;">
					<label for="diaEnvioIni">Fecha Env&iacute;o:</label>
				</td>
				<td>
					<table style="width:20%">
						<tr>
							<td width="10%" align="left" style="vertical-align: middle;">
								<label for="diaEnvioIni">desde:</label>
							</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaEnvio.diaInicio" id="diaEnvioIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaEnvio.mesInicio" id="mesEnvioIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaEnvio.anioInicio" id="anioEnvioIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
					    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioEnvioIni, document.formData.mesEnvioIni, document.formData.diaEnvioIni);return false;" title="Seleccionar fecha">
					    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
					    		</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table style="width:20%">
						<tr>
							<td width="10%" align="left" style="vertical-align: middle;">
								<label for="diaEnvioFin">hasta:</label>
							</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaEnvio.diaFin" id="diaEnvioFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaEnvio.mesFin" id="mesEnvioFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td style="vertical-align: middle;">/</td>
							<td>
								<s:textfield name="consultaTramiteRegistroBean.fechaEnvio.anioFin" id="anioEnvioFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
					    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioEnvioFin, document.formData.mesEnvioFin, document.formData.diaEnvioFin);return false;" title="Seleccionar fecha">
					    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
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
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarTramiteRegistro();"/>			
				</td>
				<td>
					<input type="button" class="boton" value="Limpiar campos" id="bLimpiar" onclick="limpiarFormulario(this.form.id);"/>			
				</td>
			</tr>
		</table>
		
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
	
	<br/>
	<s:if test="%{resumenAdjuntos}">
		<br>
		<div id="resumenAdjuntosConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenAdjuntosConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if test="%{resumenEliminacion}">
		<br>
		<div id="resumenEliminacionConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenEliminacionConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if test="%{resumenValidacion}">
		<br>
		<div id="resumenValidacionConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenValidacionConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if test="%{resumenFirmaCertificante}">
		<br>
		<div id="resumenFirmaCertificanteConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenFirmaCertificanteConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if test="%{resumenFirmaAcuse}">
		<br>
		<div id="resumenFirmaAcuseConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenFirmaAcuseConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if test="%{resumenEnvioDpr}">
		<br>
		<div id="resumenEnvioDprConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenEnvioDprConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if test="%{resumenCambEstado}">
		<br>
		<div id="resumenCambEstadoConsultaRegistradores" style="text-align: center;">
			<%@include file="resumen/resumenCambEstadoConsultaRegistradores.jspf" %>
		</div>
		<br><br>
	</s:if>
	
	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>
	
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
									onchange="cambiarElementosPorPaginaConsulta('navegarRecuperarTramite.action', 'displayTagDivConsulta', this.value);" /> 
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	
	<script type="text/javascript">
		$(function() {
			$("#displayTagDivConsulta").displayTagAjax();
		});
	</script>
	
	<div id="displayTagDivConculta" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" requestURI="navegarRecuperarTramite.action"
			class="tablacoin" summary="Listado de Tramites" cellspacing="0" cellpadding="0" uid="row"
			decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorTramiteRegistro">
			
				<display:column title="Nº expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%">
					<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esAnulado(#attr.row.estado)">
						<s:property value="#attr.row.idTramiteRegistro"/>
					</s:if>
					<s:else>
						<s:if test="#attr.row.tipoTramite == 'R1'">
							<a href="recuperarTramiteRegistroMd1.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							 </a>
						</s:if>
						<s:elseif test="#attr.row.tipoTramite == 'R2'">
							<a href="recuperarTramiteRegistroMd2.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R3'">
							<a href="recuperarTramiteRegistroMd3.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R4'">
							<a href="recuperarTramiteRegistroMd4.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R5'">
							<a href="recuperarTramiteRegistroMd5.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R6'">
							<a href="recuperarTramiteRegistroMd6.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R7'">
							<a href="recuperarContratoFinanciacion.action?identificador=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R8'">
							<a href="recuperarContratoLeasing.action?identificador=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R9'">
							<a href="recuperarContratoRenting.action?identificador=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R10'">
							<a href="recuperarContratoCancelacion.action?identificador=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R11'">
							<a href="recuperarCuenta.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R12'">
							<a href="recuperarLibro.action?numExpediente=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
						<s:elseif test="#attr.row.tipoTramite == 'R13'">
							<a href="recuperarContratoDesistimiento.action?identificador=${row.idTramiteRegistro}">	
								<s:property value="#attr.row.idTramiteRegistro"/> 
							</a>
						</s:elseif>
					</s:else>
				</display:column>
				<display:column property="sociedad.apellido1RazonSocial" title="Razón Social" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />
				<display:column property="cif" title="Cif" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="refPropia" title="Referencia Propia" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />
				<display:column property="fechaEnvio" title="Fecha Envío" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="tipoTramite" title="Tipo Trámite" sortable="true" headerClass="sortable" style="width:8%"  />
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable" style="width:8%"  />
				<display:column property="registro.nombre" title="Registro" sortable="true" headerClass="sortable" style="width:4%"  />
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksRegistradores);' 
					onKeyPress='this.onClick'/>" style="width:1%">
						<table align="center">
							<tr>
								<td style="border: 0px;">
									<input type="checkbox" name="listaChecksRegistradores" id="idTramiteRegistro" value='<s:property value="#attr.row.idTramiteRegistro"/>' />
								</td>
								<td style="border: 0px;">
		  							<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  								onclick="consultaEvTramiteRegistro(<s:property value="#attr.row.idTramiteRegistro"/>);" title="Ver evolución"/>
		  						</td>
							</tr>
						</table>
				</display:column>
		</display:table>
	 </div>
	<s:if test="%{lista.getFullListSize()>0}">
		<table align="center">
			<tr>
				<td>
					<input type="button" class="boton" name="adjuntos" id="adjuntos"
							value="Adjuntos" onkeypress="this.onClick" onclick="javascript:descargarAdjuntos();" />
				</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoTrafico() && !@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
					<td>
						<input type="button" class="boton" name="btnEliminar" id="btnEliminar"
								value="Eliminar" onkeypress="this.onClick" onclick="javascript:eliminarTramites();" />
						<input type="button" class="boton" name="btnValidar" id="btnValidar"
								value="Validar" onkeypress="this.onClick" onclick="javascript:validarTramites();" />
						
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEnvioDprRegistradores()}">	
							<input type="button" id="btnFirmarAcuse" value="Firmar acuse" class="boton" onclick="construirAcuseLista();" />
						</s:if>	
						
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEnvioDprRegistradores()}">		
							<input type="button" class="boton" name="btnEnviarDpr" id="btnEnviarDpr" 
							 		value="Enviar Dpr" onkeypress="this.onClick" onclick="javascript:construirDprLista();" />
	
						</s:if>
					</td>
				</s:if>
			
				<td>
					<input type="button" class="boton" name="btnExportarTramite" id="btnExportarTramite"
						value="Exportar" onkeypress="this.onClick" onclick="javascript:exportarTramites();" />
				</td>
			<tr>
		</table>
		<table align="center">
			<tr>
				<td align="center">
						<input type="button" class="boton" name="btnCambiarAFinalizado" id="btnCambiarAFinalizado"
								value="Cambiar a Finalizado" onkeypress="this.onClick" onclick="javascript:cambiarAFinalizadoLista();" />
				</td>
				<td align="center">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<input type="button" class="boton" name="cambiarEstadoRegistradores" id="cambiarEstadoRegistradores"
									value="Cambiar Estado" onkeypress="this.onClick" onclick="javascript:abrirVentanaSeleccionEstados();" />
					</s:if>
				</td>
			</tr>
		</table>
	</s:if>
	
	<div id="bloqueLoadingConsultarReg" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
	
	<div id="bloqueLoadingRegistro" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../../includes/bloqueLoading.jspf" %>
	</div>
			
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	</div>
</s:form>


<script type="text/javascript">
	trimCamposInputs();
</script>