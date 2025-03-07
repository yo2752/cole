<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/solicitudesplacas/placas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
</iframe>
<s:form method="post" id="formData" name="formData">
	<div class="nav" style="margin-left: 0.3em">
		<table style="width:100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta de solicitudes de placas de matriculación</span></td>
			</tr>
		</table>
	</div>
	<div id="divError">
		<s:if test="hasActionMessages() || hasActionErrors()">
			<table class="tablaformbasica" style="width:93.8%;margin-left:1.3em">
				<tr>
					<td align="left">						
						<ul id="mensajesInfo" style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						<s:if test="hasActionMessages()">
							<s:iterator value="actionMessages">
								<li><span><s:property /></span></li>
							</s:iterator>
						</s:if>
						</ul>							
						<ul id="mensajesError" style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						<s:if test="hasActionErrors()">
							<s:iterator value="actionErrors">
								<li><span><s:property /></span></li>
								
							</s:iterator>
						</s:if>	
						</ul>							
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	<table class="tablaformbasica">
		<tr>
			<td align="left" nowrap="nowrap" style="width:15%;">
				<label for="labelFechaInicio">Fecha de Inicio</label>
			</td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="consultaPlacasBean.fecha.diaInicio" id="fechaInicioDay"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left">/</td>
						<td align="left">
							<s:textfield name="consultaPlacasBean.fecha.mesInicio"
								id="fechaInicioMonth" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left">/</td>
						<td align="left">
							<s:textfield name="consultaPlacasBean.fecha.anioInicio"
								id="fechaInicioYear" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.fechaInicioYear, document.formData.fechaInicioMonth, document.formData.fechaInicioDay);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="left" src="img/ico_calendario.gif" width="15" height="16"
								border="0" alt="Calendario" />
							</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left">
				<label for="labelFechaFin">Fecha de Fin</label>
			</td>
			<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="consultaPlacasBean.fecha.diaFin" id="fechaFinDay"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="consultaPlacasBean.fecha.mesFin" id="fechaFinMonth"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="consultaPlacasBean.fecha.anioFin" id="fechaFinYear"
								 onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left">
							<a href="javascript:;"onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.fechaFinYear, document.formData.fechaFinMonth, document.formData.fechaFinDay);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="left" src="img/ico_calendario.gif" width="15" height="16"
								border="0" alt="Calendario" />
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap">
					<label for="numColegiado">Número de Colegiado</label>
				</td>
				<td align="left">
					<s:textfield name="consultaPlacasBean.numColegiado" id="numColegiado"
						onblur="validarContenidoCampos(this, event);"
						onfocus="validarContenidoCampos(this, event);"
						onchange="validarContenidoCampos(this, event);" size="4"
						maxlength="4" />
				</td> 
			</s:if>		
			<td align="left">
				<label for="numExpediente">Número de Expediente</label>
			</td>
			<td align="left">
				<s:textfield name="consultaPlacasBean.numExpediente" id="numExpediente"
					onblur="validarContenidoCampos(this, event);"
					onfocus="validarContenidoCampos(this, event);"
					onchange="validarContenidoCampos(this, event);"
					cssStyle="color:#555; font-size:9px;" size="30"
					maxlength="30" />
			</td>			
		</tr>
		<tr> 
			<td align="left" nowrap="nowrap">
				<label for="matricula">Matrícula</label>
			</td>
			<td align="left">
				<s:textfield name="consultaPlacasBean.matricula" id="matricula"
					onblur="validarContenidoCampos(this, event);"
					onfocus="validarContenidoCampos(this, event);"
					onchange="validarContenidoCampos(this, event);return validarMatricula_alPegar(event)" size="7"
					maxlength="10"
					onkeypress="return validarMatricula(event)" onmousemove="return validarMatricula_alPegar(event)" 
					 />
			</td>
		</tr>
	</table>
	<div class="acciones center">
		<input type="button" value="Buscar" class="boton" onclick="validarFormulario(this.form.id)" />
		<!--  <input type="reset" value="Restablecer" class="boton" /> -->
		<input type="button" value="Limpiar Formulario" onclick="limpiarFormulario(this.form.id);" class="boton" />
	</div>
	<br/>
	<div id="resultado"	style="width: 100%; background-color: transparent;">
		<h2 class="subtitulo">Resultado de la b&uacute;squeda</h2>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	
	<s:if test="%{listaSolicitudes.size()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table style="width:100%">
			<tr>
				<td align="right">
					<table style="width:100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina" value="resultadosPorPagina"
									title="Resultados por página"
									onchange="return cambiarElementosPorPaginaConsulPlacas();" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>		
	<div id="displayTagDiv" class="divScroll" style="overflow:hidden;">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="listaSolicitudes" excludedParams="*"
			requestURI="navegar_ConsultaSolicitudesPlacas.action" sort="list" class="tablacoin"
			uid="tablaConsultaTramite" summary="Listado de PDFs" cellspacing="1"
			cellpadding="0" pagesize="${resultadosPorPagina}" style="margin:.5em 0;">
				
			<display:column property="matricula" title="Matrícula"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%;" />
				
				
			<display:column property="nombreUsuario" title="Usuario"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%;" />

			<display:column property="fechaSolicitud" title="Fecha Solicitud"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%;" />
									
			<display:column title="Estado"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%;">
					
				<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && #attr.tablaConsultaTramite.estado.valorEnum==@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('3').valorEnum}">
						<s:set var="estadoMostrar" value="@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('2').nombreEnum" />
				</s:if>
				<s:else>
					<s:set var="estadoMostrar" value="#attr.tablaConsultaTramite.estado.nombreEnum" />
				</s:else>
					<s:property value="estadoMostrar" />
			</display:column>
			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick' style='display:inline-block'/>" 
				style="width:2%" >
				<table>
					<tr>
						<td style="border: 0px;">
							<input type="checkbox" name="listaChecksConsultaTramite" value='<s:property value="#attr.tablaConsultaTramite.idSolicitud"/>' />
							<input type="hidden" name="listaEstadoConsultaTramite" id="estado_<s:property value="#attr.tablaConsultaTramite.idSolicitud"/>" value='<s:property value="#attr.tablaConsultaTramite.estado"/>' />
						</td>
					</tr>
				</table>
			</display:column>
		</display:table>
	</div>
	<s:if test="%{(listaSolicitudes.size()>0)}">	
		<div class="acciones center">
			<input class="botonMasGrande" type="button" value="Editar Solicitudes" onclick="editarSolicitudesPlacas(this.form)" />
			<input class="botonMasGrande" type="button" value="Borrar Solicitudes" onclick="borrarSolicitudesPlacas(this.form)" />
		</div>	
	 </s:if>
</s:form>
<script type="text/javascript">
	function validarContenidoCampos(campo, e) {
		var mensajesError = document.getElementById('mensajesError');
		
		switch (campo.id) {
		case "numColegiado":
			if(e){
				if (e.type == 'focus') {
					if (campo.value == '') {
						campo.style.color = '#000000';
						campo.value = '';
						return true;
					} else if (campo.value == 'Introduzca un número de colegiado...') {
						campo.style.color = '#000000';
						return true;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
				else if (e.type == 'blur') {
				}
				else if (e.type == 'change') {
					if ((campo.value != '' && isNaN(campo.value)) || campo.value.length != 4) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = "<li>El número de colegiado debe tener 4 caracteres y ser numérico</li>";
						//alert("El número de colegiado debe tener 4 caracteres y ser numérico");
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
			} else {
				if (campo.value != '' && campo.value!='Introduzca un número de colegiado...' && (isNaN(campo.value) || campo.value.length != 4)) {
					campo.style.color = '#ff0000';
					campo.focus();
					mensajesError.innerHTML = '<li>El número de colegiado debe tener 4 caracteres y ser numérico</li>';
					//alert("El número de colegiado debe tener 4 caracteres y ser numérico");
					return false;
				} else {
					campo.style.color = '#000000';
					return true;
				}
			}
			break;
		case "numExpediente":
			if(e && e.type != 'change'){
				if (e.type == 'focus') {
					if (campo.value == '') {
						campo.style.color = '#000000';
						campo.value = '';
						return true;
					} else if (campo.value == 'Introduzca un número de expediente...') {
						campo.style.color = '#555555';
						campo.value = '';
						return true;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
				else if (e.type == 'blur') {
					if (campo.value == '') {
						campo.style.color = '#000000';
						campo.value = 'Introduzca un número de expediente...';
						return true;
					} else if (campo.value == 'Introduzca un número de expediente...') {
						campo.style.color = '#555555';
						campo.value = '';
						return true;
					} else if ((campo.value != '' && isNaN(campo.value)) || campo.value.length != 15) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = '<li>El número de expediente debe tener 15 caracteres y ser numérico</li>';
						//alert("El número de expediente debe tener 15 caracteres y ser numérico");
						return false;
					} else if (campo.value != '' && campo.value!='Introduzca un número de expediente...' && (isNaN(campo.value) || campo.value.length != 15)) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = '<li>El número de expediente debe tener 15 caracteres y ser numérico</li>';
						//alert("El número de expediente debe tener 15 caracteres y ser numérico");
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
				else if (e.type == 'change') {
					if ((campo.value != '' && isNaN(campo.value)) || campo.value.length != 15) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = '<li>El número de expediente debe tener 15 caracteres y ser numérico</li>';
						//alert("El número de expediente debe tener 15 caracteres y ser numérico");
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
			} else {
				{
					campo.style.color = '#000000';
					return true;
				}
			}
			break;
		default:
			break;

		}
		return true;
	}

	function validarFormulario(idFormulario){

		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				//alert(elementos[i].tagName+" - "+elementos[i].type);
				if(!validarContenidoCampos(elementos[i], null)) {
					return false;
				}
			}
		}

		buscarSolicitudPlacas();
		
	}

	function limpiarFormulario(idFormulario) {
		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				//alert(elementos[i].tagName+" - "+elementos[i].type);
				elementos[i].value = "";
			}
		}
	}

	function buscarSolicitudPlacas() {
		$('#formData').attr("action", "boton_busqueda_ConsultaSolicitudesPlacas.action").trigger("submit");
	}

	function modificarSolicitudPlacas(idSolicitud) {
		$('#formData').attr("target","").attr("action", "modificarSolicitudPlacas.action");
	}

	function consultaPlacas() {

		if (isNaN(document.getElementById('numExpediente').value)) {
			document.getElementById('numExpediente').value = '';
		}

		document.getElementById('formData').action = 'buscarSolicitudPlacas.action';
		document.getElementById('formData').submit();

	}

	function generarEstadisticas() {

		var formulario = document.getElementById('formData');
		formulario.action = 'estadisticasSolicitudPlacas.action';
		formulario.submit();
	}
</script>
