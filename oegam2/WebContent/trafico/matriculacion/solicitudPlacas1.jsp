<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/placas1.js" type="text/javascript"></script>

<s:set var="mostrarSeccionPersona" value="mostrarSeccionPersona" />

<div id="contenido" class="contentTabs" style="display: block;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Matriculación:
						Solicitud de placas de matrícula</span></td>
			</tr>
		</table>
	</div>

	<table class="subtitulo" cellspacing="0">
		<tr>
			<td>Tr&aacute;mites seleccionados</td>
		</tr>
	</table>

	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	
	<div id="mostrarDocumentoLink" style="display: none; cursor: pointer;"
		onclick="muestraYOculta();">
		<u>Documento generado (Link para imprimir)</u>
	</div>

	<s:form method="post" id="formData" name="formData">

		<div class="divScroll">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tablacoin">
				<tr>
					<th scope="col">N&ordm; Expediente</th>
					<th scope="col">Matr&iacute;cula</th>
					<th scope="col">Solicitante</th>
					<th scope="col">Fecha Solicitud</th>
					<th scope="col">Estado</th>
					<!--<th scope="col" style="padding-right: 6px;"><input type='checkbox' name='checkAll' onClick='marcarTodosYCalculaCreditos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick'/></th>-->
				</tr>

				<s:iterator value="listaSolicitudes" var="tablaSeleccionados"
					status="statusMatricula">
					
					<s:set var="individual_#statusMatricula.index"
						value="individual" />
					<s:set var="individual" value="individual_#statusMatricula.index" />
					<s:set var="matriculaPreview_#statusMatricula.index"
						value="matricula" />

					<s:if test="#statusMatricula.last">
						<input type="hidden" name="noElementos" id="noElementos"
							value="<s:property value='#statusMatricula.index' />" />
					</s:if>

					<s:if
						test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && estado==@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('3')}">
						<s:set var="estadoMostrar" 
							value="@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('2').nombreEnum" />
					</s:if>
					<s:else>
						<s:set var="estadoMostrar" 
							value="estado.nombreEnum" />
					</s:else>

					<input type="hidden"
						id="matriculaPreview_<s:property value='#statusMatricula.index' />"
						value='<s:property value="matricula" />' />
					<input type="hidden"
						id="hiddenTipoPlacaDelantera_<s:property value='#statusMatricula.index' />"
						value='<s:property value="tipoPlacaDelantera.valorEnum" />' />
					<input type="hidden"
						id="hiddenTipoPlacaAdicional_<s:property value='#statusMatricula.index' />"
						value='<s:property value="tipoPlacaAdicional.valorEnum" />' />
					<input type="hidden"
						id="hiddenTipoPlacaTrasera_<s:property value='#statusMatricula.index' />"
						value='<s:property value="tipoPlacaTrasera.valorEnum" />' />
					<input type="hidden"
						id="hiddenDuplicada_<s:property value='#statusMatricula.index' />"
						value='<s:property value="duplicada" />' />	
					<tr>
						<td><s:property value="numExpediente" /></td>
						<td><s:property value="matricula" /></td>
						<td><s:property value="nombreUsuario" /></td>
						<td><s:property value="fechaSolicitud" /></td>
						<td><s:property value="estadoMostrar" /></td>
					</tr>
				</s:iterator>
			</table>

		</div>

	</s:form>

	<s:form method="post" id="formDataMatriculas" name="formDataMatriculas"
		theme="simple">

		<div class="contenido fondoFormularios">

			<table class="subtitulo" onmousemove="return actualizaRadios();"
				onmouseover="return actualizaRadios();">
				<tr>
					<td>Tipos de placas de matriculación</td>
				</tr>
			</table>

			<s:if test="listaSolicitudes.size() > 1">
				<div style="text-align: center;">
					<input type="radio" id="aplicarTodasTrue" name="aplicarTodas"
						value="true" style="display: inline;"
						onchange="configurarPlacas(this.value), calculoCreditosPlacas()" /><label
						for="aplicarTodasTrue">Misma configuración</label> <input
						type="radio" id="aplicarTodasFalse" name="aplicarTodas"
						value="false" style="display: inline;"
						onchange="configurarPlacas(this.value), calculoCreditosPlacas()" checked="checked" /><label
						for="aplicarTodasFalse">De manera individual</label>
				</div>
			</s:if>

			<s:set var="editables" value="true" />
			<s:set var="confirmables" value="true" />
			<s:set var="tramitables" value="true" />
			<s:set var="finalizables" value="true" />

			<div id="contenedorGeneralSolicitudes">
			
				<%@include file="solicitudContent1.jsp"%>
				
			</div>
			<div style="clear: both"></div>

			<!--<div style="width:48%; float:left;">
			<table class="datosTB Insideform">
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="placa" id="turismoOrdinariaLarga" value="turismoOrdinariaLarga" onclick="dibujarMatricula('<s:property value="matriculaPreview" />', this.id)" onchange="return calculoCreditosSolicitadosChecks();" checked="checked" />
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="turismoOrdinariaLarga">Turismo Ordinaria Larga</label>
	     			</td>
	    		</tr>
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="placa" id="turismoOrdinariaCorta" value="turismoOrdinariaCorta" onclick="dibujarMatricula('<s:property value="matriculaPreview" />', this.id)" onchange="return calculoCreditosSolicitadosChecks();" />
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="turismoOrdinariaCorta">Turismo Ordinaria Corta</label>
	     			</td>
	    		</tr>
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="placa" id="motocicleta" value="moticicleta" onclick="dibujarMatricula('<s:property value="matriculaPreview" />', this.id)" onchange="return calculoCreditosSolicitadosChecks();" />
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="motocicleta">Motocicleta</label>
	     			</td>
	    		</tr>
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="placa" id="tractor" value="tractor" onclick="dibujarMatricula('<s:property value="matriculaPreview" />', this.id)" onchange="return calculoCreditosSolicitadosChecks();" />
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="tractor">Tractor</label>
	     			</td>
	    		</tr>
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="placa" id="ciclomotor" value="ciclomotor" onclick="dibujarMatricula('<s:property value="matriculaPreview" />', this.id)" onchange="return calculoCreditosSolicitadosChecks();" />
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="ciclomotor">Ciclomotor</label>
	     			</td>
	    		</tr>
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="placa" id="motoCorta" value="motoCorta" onclick="dibujarMatricula('<s:property value="matriculaPreview" />', this.id)" onchange="return calculoCreditosSolicitadosChecks();" />
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="motoCorta">Moto Corta</label>
	     			</td>
	    		</tr>
			</table>
		</div>
		<div style="position:relative; float:right; width:50%; min-height:200px; text-align:center;">
			<img id="imagenMatricula" style="position:relative; margin-top:-25px;" src="/oegam2/img/matriculas/matricula_normal.png" alt="Matricula Normal" />
			<br />
			<span id="letrasMatricula" style="font-size:3.8em; font-weight:bold; position:absolute;"><s:property value="matriculaPreview" /></span>
		</div>
		
		<div style="clear:both"></div>-->

			<%@include file="../../includes/creditosPlacasMatricula.jspf"%>

			<table class="acciones">
				<tr>
					<td>
						<div>
							<s:if test="editables">
								<input class="boton" type="button" id="bGuardar" name="bGuardar"
									value="Guardar Solicitud"
									onClick="return realizarSolicitudPlacas(this.form);"
									onKeyPress="this.onClick" />
							&nbsp;
							<s:if test="confirmables">
									<input class="boton" type="button" id="bSolicitar"
										name="bSolicitar" value="Confirmar Solicitud"
										onClick="return confirmarSolicitudPlacas(this.form);"
										onKeyPress="this.onClick" />
								&nbsp;
							</s:if>
							</s:if>
							<s:if
								test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
								<s:if test="tramitables">
									<input class="boton" type="button" id="bTramitar"
										name="bTramitar" value="Tramitar Solicitud"
										onClick="return cambiarEstadoSolicitudPlacas(this.form, '<s:property value="@org.gestoresmadrid.placas.utilities.enumerados.EstadoSolicitudPlacasEnum@convertir('3')" />');"
										onKeyPress="this.onClick" />
								&nbsp;
							</s:if>
								<s:if test="finalizables">
									<input class="boton" type="button" id="bFinalizar"
										name="bFinalizar" value="Finalizar Solicitud"
										onClick="return finalizarSolicitudPlacas(this.form);"
										onKeyPress="this.onClick" />
								&nbsp;
							</s:if>
							</s:if>
							<input class="botonGrande" type="button" id="bVolver"
								name="bVolver" value="Volver a Consulta"
								onClick="return volverConsultaSolicitudesPlaca(this);" onKeyPress="this.onClick" />
							&nbsp;
						</div>
						<div id="bloqueLoading"
							style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
							<%@include file="../../includes/bloqueLoading.jspf"%>
						</div>
					</td>
				</tr>
			</table>

		</div>

		<input type="hidden" id="numsExpedientes" name="numsExpedientes"
			value="<s:property value='numsExpedientes' />" />
			
			<input type="hidden" id="idUsuario" name="idUsuario"
			value="<s:property value='listaSolicitudes[0].usuario.idUsuario' />" />

	</s:form>

</div>

<s:if test="%{impresoEspera}">
	<script type="text/javascript">muestraDocumento();</script>
</s:if>
<s:else>
	<script type="text/javascript">

	function rellenaPersona(){
		if (document.getElementById('idNombre') != null && document.getElementById('nombre')!=null){
			document.getElementById('idNombre').value = document.getElementById('nombre').value; }

		if (document.getElementById('idPrimerApellido') != null && document.getElementById('primerApellido')!=null){
			document.getElementById('idPrimerApellido').value = document.getElementById('primerApellido').value; }

		if (document.getElementById('idSegundoApellido') != null && document.getElementById('segundoApellido')!=null){
			document.getElementById('idSegundoApellido').value = document.getElementById('segundoApellido').value; }

		if (document.getElementById('idNif') != null && document.getElementById('nif')!=null){
			document.getElementById('idNif').value = document.getElementById('nif').value; }
		}
	
		if(document.getElementById(mostrarDocumentoLink)){
			document.getElementById(mostrarDocumentoLink).style.display = "none";
		}
		
		$(document).ready(function(){
			// Dibujamos las placas
			//dibujarMatriculaIndividual();
			dibujarMatriculas();
			// Calculamos el número de créditos a consumir por las solicitudes en pantalla
			calculoCreditosPlacas();
			// Activamos las placas adicionales de cada solicitud si procede
			habilitarAdicionales();
			// Marcamos el radio de duplicada con Si o No.
			habilitarDuplicado();
		});
		</script>
</s:else>

