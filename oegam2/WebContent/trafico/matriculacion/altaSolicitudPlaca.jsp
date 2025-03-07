<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/placas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<s:form method="post" id="formData" name="formData">
	<div class="nav" style="margin-left: 0.3em">
		<table style="width: 100%">
			<tr>
				<td class="tabular"><span class="titulo">Alta de
						solicitudes de placas de matrícula</span></td>
			</tr>
		</table>
	</div>

	<div id="divError">
		<s:if test="hasActionMessages() || hasActionErrors()">
			<table class="tablaformbasica" style="width: 100%">
				<tr>
					<td align="left">
						<ul id="mensajesInfo"
							style="color: green; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;">
							<s:if test="hasActionMessages()">
								<s:iterator value="actionMessages">
									<li><span><s:property /></span></li>
									<li id="mostrarDocumentoLink" style="display: none;"><a
										href="#"
										onclick="window.open('mostrarDocumentoTrafico.action','Documento_de_impresión_generado',''); muestraYOculta();"
										title="Documento Generado (Link para imprimir">Documento
											generado</a> (Link para imprimir)</li>
								</s:iterator>
							</s:if>
						</ul>
						<ul id="mensajesError"
							style="color: red; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;">
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
		<s:else>
			<ul id="mensajesInfo"
				style="color: green; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;"></ul>
			<ul id="mensajesError"
				style="color: red; font-family: tahoma, arial, sans-serif; font-size: 11px; font-weight: bold;"/></ul>
		</s:else>
	</div>
	<div style="background-color: #f4f4f4;">
		<table id="tablaAltas">
			<thead>
				<tr>
					<th id="th_bastidor">Bastidor</th>
					<th id="th_matricula">Matrícula</th>
					<th id="th_niftitular">NIF Titular</th>
					<th id="th_tipovehiculo">Tipo de Vehículo</th>
					<th class="misc">#</th>
					<th class="invisible"></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listaSolicitudes" var="tablaRegistros" status="statusMatricula">
					<tr>
						<td><s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].bastidor"
								id="bastidor_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);" maxlength="18" />
						</td>
						<td><s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].matricula"
								id="matricula_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);return validarMatricula_alPegar(event)"
								maxlength="10" onkeypress="return validarMatricula(event);"
								onmousemove="return validarMatricula_alPegar(event)" /></td>
						<td><s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].nifTitular"
								id="nif_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);" maxlength="9" />
						</td>
						<td><s:select
								name="listaSolicitudes[%{#statusMatricula.index}].tipoVehiculo"
								id="tipoVehiculo_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T1')"
								headerKey="" headerValue="Seleccione Tipo"
								listKey="tipoVehiculo" listValue="descripcion"
								cssStyle="max-width:20em;" /></td>
						<td class="misc"><a href="#"
							onclick="reindexRows('tablaAltas', 'add');"
							title="Añadir solicitud"> <img src="img/bmas.gif"
								alt="Añadir matrícula" />
						</a> <a href="#" onclick="reindexRows('tablaAltas', 'delete', this);"
							title="Eliminar solicitud" class="delete"> <img
								src="img/bmenos.gif" alt="Eliminar matrícula" />
						</a></td>
						<td class="invisible"><input type="hidden"
							id="numColegiado_0"
							name="listaSolicitudes[0].numColegiado"
							value="<s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNumColegiadoCabecera()"/>" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" value="Alta de solicitudes" class="boton"
			onclick="validaMatriculasIguales('tablaAltas',this.form.id)" />
			<input type="button" value="Limpiar Formulario"
			onclick="limpiarFormulario(this.form.id);" class="boton" />
	</div>

	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<!--  FIN FORMULARIO DE FILTROS -->

</s:form>
