<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/trafico/justificantes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script  type="text/javascript">
	function cambiarElementosPorPagina(){
		document.formData.action="navegarJustificantesProfesionales.action?resultadosPorPagina=" + document.formData.idResultadosPorPagina.value;
		document.formData.submit();
		return true;
	}

	function numChecked() {
		var checks = document.getElementsByName("numExpedientes");
		var numChecked = 0;
		for(var i=0; i<checks.length; i++) {
			if(checks[i].checked) numChecked++;
		}
		return numChecked;
	}

	function imprimirJustificante(){
		if(numChecked() == 0) {
			alert("Debe seleccionar alg\u00fan tr\u00E1mite");
			return false;
		}

		$("#divError").remove();
		document.formData.action="imprimirJustificantesProfesionales.action";
		document.formData.submit();
		return true;
	}

	function generarJPb(){
		var codigos = "";
		var contCodigos = 0;
		var contCodigosExp = 0;
		var internos;
		var numeroCheckExp = null;
		var numeroExp = null;

		if(numChecked() == 0) {
			alert("Debe seleccionar alg\u00fan justificante");
			return false;
		}
		$("input[name='numExpedientes']:checked").each(function() {
			if (this.checked) {
				if (codigos == "") {
					codigos += this.value;
				} else {
					codigos += "-" + this.value;
				}
				contCodigos++;
				numeroCheckExp = this.id;
			}
		});

		var datosExp = codigos.split("-");
		var descExp = $("#ListCheckInternos" + numeroCheckExp).val();

		input = $("<input>").attr("type", "hidden").attr("name","codSeleccionados").val(datosExp);

		document.formData.action="generarJustificanteProfesionalForzadoJustificantesProfesionales.action";
		document.formData.submit();
		return true;
	}

	function anularJustificante(){
		var codigos = "";
		var contCodigos = 0;
		var contCodigosExp = 0;
		var internos;
		var numeroCheckExp = null;
		var numeroExp = null;

		if(numChecked() == 0) {
			alert("Debe seleccionar alg\u00fan elemento de la tabla");
			return false;
		}

		$("input[name='numExpedientes']:checked").each(function() {
			if (this.checked) {
				if (codigos == "") {
					codigos += this.value;
				} else {
					codigos += "-" + this.value;
				}
				contCodigos++;
				numeroCheckExp = this.id;
			}
		});

		var datosExp = codigos.split("-");
		var descExp = $("#ListCheckInternos" + numeroCheckExp).val();

		input = $("<input>").attr("type", "hidden").attr("name","codSeleccionados").val(datosExp);

		document.formData.action="anularJustificanteProfesionalJustificantesProfesionales.action";
		document.formData.submit();
		return true;
	}

	function buscar(){
		var numExpediente = document.getElementById("numExpediente").value;

		var matricula = new String(document.getElementById("matricula").value);

		matricula=Limpia_Caracteres_Matricula(matricula);

		document.getElementById("matricula").value = matricula.toUpperCase();

		// validamos el numero de expediente
		var numExpediente = document.getElementById("numExpediente").value;

		if (numExpediente != null) {
			numExpediente = numExpediente.replace(/\s/g,'');

			if (isNaN(numExpediente)) {
				alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
				return false;
			}

			document.getElementById("numExpediente").value = numExpediente;
		}

		// validamos el numero de expediente
		var idJustificante = document.getElementById("idJustificante").value;

		if (idJustificante != null) {
			idJustificante = idJustificante.replace(/\s/g,'');

			if (isNaN(idJustificante)) {
				alert("El campo n\u00FAmero de justificante debe contener solo n\u00FAmeros");
				return false;
			}

			document.getElementById("idJustificante").value = idJustificante;
		}

		document.formData.action="buscarJustificantesProfesionales.action";
		document.formData.submit();
		return true;
	}

	function pendienteAutorizacionColegio(){
		var codigos = "";
		var contCodigos = 0;
		var contCodigosExp = 0;
		var internos;
		var numeroCheckExp = null;
		var numeroExp = null;

		if(numChecked() == 0) {
			alert("Debe seleccionar alg\u00fan justificante");
			return false;
		}

		$("input[name='numExpedientes']:checked").each(function() {
			if (this.checked) {
				if (codigos == "") {
					codigos += this.value;
				} else {
					codigos += "-" + this.value;
				}
				contCodigos++;
				numeroCheckExp = this.id;
			}
		});

		input = $("<input>").attr("type", "hidden").attr("name","codSeleccionados").val(codigos);

		document.formData.action="pendienteAutorizacionColegioJustificantesProfesionales.action";
		document.formData.submit();
		return true;
	}

	function abrirEvolucionJustificante(numExpediente, idJustificante, fechaInicio){
		if (null == numExpediente) {
			alert('No se puede referenciar a ning\u00FAn tr\u00E1mite.');
			return false;
		}
		var opciones_ventana = 'width=850,height=400,top=150,left=280';
		var ventana_evolucion = window.open(
				'cargarListaEvolucionJustificanteEvolucionJustificanteProf.action?numExpediente=' + numExpediente + '&idJustificante=' + idJustificante + '&fechaInicio=' + fechaInicio, 'popup',
				opciones_ventana);
	}
</script>

<!--<div id="contenido" class="contentTabs" style="display: block;">-->
 <!--%@include file="../../includes/erroresMasMensajes.jspf" %> -->
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Justificantes Profesionales</span>
				</td>
			</tr>
		</table>
	</div>

	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">

			<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="numExpediente">Nº Expediente:</label>
					</td>

					<td align="left" >
						<s:textfield name="consultaJustificanteProfBean.numExpediente" id="numExpediente"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="58" maxlength="15"
								onkeypress="return validarMatricula(event)" />
					</td>

					<td align="left" nowrap="nowrap">
						<label for="matricula">Matrícula: </label>
					</td>

					<td align="left">
						<s:textfield name="consultaJustificanteProfBean.matricula"
									id="matricula"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									onkeypress="return validarMatricula(event)"
									onchange="return validarMatricula_alPegar(event)"
									onmousemove="return validarMatricula_alPegar(event)"
									size="10" maxlength="10"/>
					</td>
				</tr>

				<tr>
					<td align="left" nowrap="nowrap">
						<label for="idJustificante">Nº Justificante:</label>
					</td>

					<td align="left" >
						<s:textfield name="consultaJustificanteProfBean.idJustificante" id="idJustificante"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="28" maxlength="20"
								onkeypress="return validarMatricula(event)"/>
					</td>

					<td align="left" nowrap="nowrap">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<label for="matricula">Estado: </label>
						</s:if>
					</td>

					<td align="left">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<s:select id="estadoId" name="consultaJustificanteProfBean.estado" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoJustificante()"
								headerKey="-1" headerValue="Elija un valor" listKey="valorEnum" listValue="nombreEnum" onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';" 
								onkeypress="return validarMatricula(event)"/>
						</s:if>
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td align="left">
							<s:select id="idContratoJustif"
								list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
								listValue="descripcion" cssStyle="width:220px"
								name="consultaJustificanteProfBean.idContrato"></s:select>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="numColegiado">Num Colegiado: </label>
						</td>
						<td align="left">
							<s:textfield name="consultaJustificanteProfBean.numColegiado" id="idNumColegiadoJust" onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';" 
								onkeypress="return validarMatricula(event)" size="10" maxlength="9"/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="consultaJustificanteProfBean.idContrato"/>
					<s:hidden name="consultaJustificanteProfBean.numColegiado"/>
				</s:else>
			</table>

			<table class="tablaformbasica">
				<tr>
					<td align="right" style="width:10%; vertical-align:middle" nowrap="nowrap">
						<label >Fecha de validez: </label>
					</td>

					<td align="left">
						<table WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaInicio">desde: </label>
								</td>

								<td>
									<s:textfield name="consultaJustificanteProfBean.fechaIni.diaInicio" 
										id="diaInicio"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaJustificanteProfBean.fechaIni.mesInicio" 
										id="mesInicio"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2"/>
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaJustificanteProfBean.fechaIni.anioInicio" 
										id="anioInicio"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="5" maxlength="4"/>
								</td>

								<td>
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;" 
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
						</table>
					</td>

					<td align="left">
						<table WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaFin">hasta:</label>
								</td>

								<td>
									<s:textfield name="consultaJustificanteProfBean.fechaFin.diaInicio" 
										id="diaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2" />
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaJustificanteProfBean.fechaFin.mesInicio" 
										id="mesFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="2" maxlength="2" />
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaJustificanteProfBean.fechaFin.anioInicio" 
										id="anioFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										size="5" maxlength="4" />
								</td>

								<td>
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger"
											align="left" 
											src="img/ico_calendario.gif"
										width="15" height="16" 
										border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		<div id="botonBusqueda">

			<table width="100%">
				<tr>
					<td>
					<input type="button"
						class="boton"
						name="bBuscar"
						id="idBuscar"
						value="Buscar"
						onClick="return buscar();"
						onKeyPress="this.onClick" />

					<input type="button"
						class="boton"
						name="bLimpiar"
						id="bLimpiar"
						value="Limpiar"
						onkeypress="this.onClick"
						onclick="return limpiarFormConsultaJustificantesProfesionales();"/>
					</td>
				</tr>
			</table>
		</div>
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

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
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
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idResultadosPorPagina"
									value="resultadosPorPagina"	title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsulta('navegarJustificantesProfesionales.action', 'displayTagDiv', this.value);" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<script type="text/javascript">
		$(function() { 
			$("#displayTagDiv").displayTagAjax();
		})
	</script>

	<div id="displayTagDiv">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>

	<display:table name="lista" excludedParams="*" requestURI="navegarJustificantesProfesionales.action"
		class="tablacoin" uid="listaJustificante" summary="Listado de Justificantes" cellspacing="0" cellpadding="0">
			
		<display:column property="idJustificante" title="Num Justificante" sortable="true" headerClass="sortable"
			defaultorder="descending" style="width:10%;"/>
		<display:column property="numExpediente" title="Num Expediente" sortable="true" headerClass="sortable"
			defaultorder="descending" style="width:15%" />
		<display:column property="tramiteTrafico.vehiculo.matricula" title="Matrícula" sortable="true" headerClass="sortable"
			defaultorder="descending" style="width:10%"  sortProperty="tramiteTrafico.vehiculo.matricula" />
		<display:column property="diasValidez" title="Dias Validez" sortable="true" headerClass="sortable"
			defaultorder="descending" style="width:10%" />				
		<display:column title="Tipo de Trámite" sortable="true" headerClass="sortable" defaultorder="descending" style="width:15%" sortProperty="tramiteTrafico.tipoTramite">
			<s:property value="%{@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@convertirTexto(#attr.listaJustificante.tramiteTrafico.tipoTramite)}" />
		</display:column>
		<display:column property="fechaInicio" title="Fecha Inicio" sortable="true" headerClass="sortable"
			defaultorder="descending" style="width:19%" format="{0,date,dd/MM/yyyy}"/>	
		<display:column property="fechaFin" title="Fecha Fin" sortable="true" headerClass="sortable"
			defaultorder="descending" style="width:19%" format="{0,date,dd/MM/yyyy}"/>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
			<display:column title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:19%" >
				<s:property value="%{@org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante@convertirTexto(#attr.listaJustificante.estado)}" />
			</display:column>
		</s:if>

	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.numExpedientes);' onKeyPress='this.onClick'/>"
			style="width:2%" >
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="numExpedientes" id="check<s:property value="#attr.listaJustificante.idJustificanteInterno"/>_<s:property value="#attr.listaJustificante.numExpediente"/>"
				value='<s:property value="#attr.listaJustificante.idJustificanteInterno"/>_<s:property  value="#attr.listaJustificante.numExpediente"/>' />
			</td>

			<td style="border: 0px;">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
				onclick="abrirEvolucionJustificante(<s:property value="#attr.listaJustificante.numExpediente"/>, 
				'<s:property value="#attr.listaJustificante.idJustificante"/>', '<s:property value="getText('{0,date,dd/MM/yyyy}',{#attr.listaJustificante.fechaInicio})"/>');" title="Ver evolución"/>
			</td>
		</tr>
	</table>

	</display:column>

</display:table>
<s:if test="%{lista.getFullListSize()>0}">
	<table align="center">
		<tr>
			<td>
				<input type="button"
						class="botonMasGrande"
						name="bImprimir"
						id="idImprimir"
						value="Imprimir Justificante"
						onClick="return imprimirJustificante();"
						onKeyPress="this.onClick"
						/>
				</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td>
					<input class="botonMasGrande" type="button" id="bGenerarJPb" name="bGenerarJPb" value="Forzar Generar JP" onclick="return generarJPb(this);" onKeyPress="this.onClick"/>
				</td>
				<td>
					<input class="botonMasGrande" type="button" id="bPenAutCol" name="bPenAutCol" value="Pte. autorización colegio" onclick="return pendienteAutorizacionColegio(this);" onKeyPress="this.onClick"/>
				</td>
				<td>
					<input class="botonMasGrande" type="button" id="idAnular " name="bAnular" value="Anular Justiticante" onclick="return anularJustificante(this);" onKeyPress="this.onClick"/>
				</td>
			</s:if>
		</tr>
	</table>
</s:if>

</div>
<!--<s:hidden key="rowid_Justificantes" name="rowid_Justificantes" ></s:hidden>-->
</s:form>