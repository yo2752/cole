<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:iterator value="listaSolicitudes" var="lista" status="statusMatricula">
	
	<s:set var="individual" value="individual_#statusMatricula.index" />
	
	<s:if test="estado > 1">
		<s:set var="editables" value="false" />
		<s:set var="confirmables" value="false" />
	</s:if>			
	<s:if test="estado > 2">
		<s:set var="tramitables" value="false" />
	</s:if>
	<s:if test="estado > 3">
		<s:set var="finalizables" value="false" />
	</s:if>
	
	<s:if test="#statusMatricula.last">
		<input type="hidden" value="<s:property value='#statusMatricula.index + 1' />" />
	</s:if>
	
	<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && estado==@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('3')}">
		<s:set var="estadoMostrar" value="@trafico.utiles.enumerados.EstadoSolicitudPlacasEnum@convertir('2').nombreEnum" />
	</s:if>
	<s:else>
		<s:set var="estadoMostrar" value="estado.nombreEnum" />
	</s:else>
	
	<s:if test="tipoPlacaAdicional!=null">
		<s:set var="mostrarAdicional" value="true" />
	</s:if>
	<s:else>
		<s:set var="mostrarAdicional" value="false" />
	</s:else>
	
	<div id="contenedorSolicitudes_<s:property value='#statusMatricula.index' />" class="contenedorSolicitudes">
		
		<div id="contenedorSolicitud_<s:property value='#statusMatricula.index' />" class="contenedorSolicitud">
			<h3 class="tituloSolicitud">Matr&iacute;cula <s:property value="matricula" /><s:if test="estado!=1"><span style="color:yellow;">&nbsp;(Estado&nbsp;<s:property value="estadoMostrar" /> - No Editable)</span></s:if></h3>
			<s:if test="estado < 2">
				<div style="position:absolute; top:.25em; left:95%;">
					<!-- 
					<a href="javascript:eliminarYrefrescar('<s:property value="numExpediente" />');" title="Eliminar esta solicitud">
					-->
					<a href="javascript:eliminarYrefrescar('<s:property value='#statusMatricula.index' />','formDataMatriculas');" title="Eliminar esta solicitud">
					
						<img src="/oegam2/img/matriculas/trash-icon.png" alt="Eliminar" />
					</a>
				</div>
			</s:if>

			<s:if test="individual == false">
				<s:set var="clase" value="%{'contenedorSolicitudIzquierda'}" />	
			</s:if>
			<s:else>
				<s:set var="clase" value="%{'contenedorSolicitudCentro'}" />			
			</s:else>
			
			<div class="<s:property value='clase' />">
				<table>
					<tr>
						<td width="40%" style="text-align:right;">
							<label for="tipoPlacaDelantera_<s:property value='#statusMatricula.index' />"><strong>Placa delantera</strong></label>
						</td>
						<td>
						<s:select
								list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposPlacasMatriculacion('delantera')"								
								name="listaSolicitudes[%{#statusMatricula.index}].tipoPlacaDelantera"
								id="tipoPlacaDelantera_%{#statusMatricula.index}"
								headerKey=""
								headerValue="Tipo de Placa"
								listKey="valorEnum"
								listValue="nombreEnum + ' (' + ancho + 'x' + alto + ')'"
								onchange="dibujarMatricula('%{#statusMatricula.index}', '%{matricula}', this.value, 'delantera'); calculoCreditosPlacas();"
								/>
						</td>
					</tr>
				</table>
				<div class="placa">
					<img id="imagenMatricula_delantera_<s:property value='#statusMatricula.index' />" class="imagenPlaca" src="/oegam2/img/matriculas/matricula_normal.png" alt="Matricula Normal" />
					<br />
					<span id="letrasMatricula_delantera_<s:property value='#statusMatricula.index' />" class="textoPlaca"><s:property value="matriculaPreview" /></span>
				</div>
			</div>
			<s:if test="individual == false">
				<div class="contenedorSolicitudDerecha">
					<table>
						<tr>
							<td width="40%" style="text-align:right;">
								<label for="tipoPlacaTrasera_<s:property value='#statusMatricula.index' />"><strong>Placa trasera</strong></label>
							
							</td>
							<td>
								<s:select
									list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposPlacasMatriculacion('trasera')"
									name="listaSolicitudes[%{#statusMatricula.index}].tipoPlacaTrasera"
									id="tipoPlacaTrasera_%{#statusMatricula.index}"
									headerKey=""
									headerValue="Tipo de Placa"
									listKey="valorEnum"
									listValue="nombreEnum + ' (' + ancho + 'x' + alto + ')'"
									onchange="dibujarMatricula('%{#statusMatricula.index}', '%{matricula}', this.value, 'trasera'); calculoCreditosPlacas();"
									/>
							</td>
						</tr>
					</table>
					<div class="placa">
						<img id="imagenMatricula_trasera_<s:property value='#statusMatricula.index' />" class="imagenPlaca" src="/oegam2/img/matriculas/matricula_normal.png" alt="Matricula Normal" />
						<br />
						<span id="letrasMatricula_trasera_<s:property value='#statusMatricula.index' />" class="textoPlaca"><s:property value="matriculaPreview" /></span>
					</div>
				</div>
			</s:if>	
		</div>
		<div style="clear:both"></div>
			<div class="contenedorSolicitudInferior">
				<div id="preguntaPlacaDuplicada">
					Por favor. Indique si es un duplicado de placa porque ya existia una placa para esta matricula.
					<input type="radio" id="idDuplicadaSi_<s:property value='#statusMatricula.index' />" name="listaSolicitudes[<s:property value='#statusMatricula.index' />].duplicada" value="true"/><label for="listaSolicitudes[<s:property value='#statusMatricula.index' />].duplicada">S&iacute;</label>
					<input type="radio" id="idDuplicadaNo_<s:property value='#statusMatricula.index' />" name="listaSolicitudes[<s:property value='#statusMatricula.index' />].duplicada" value="false"/><label for="listaSolicitudes[<s:property value='#statusMatricula.index' />].duplicada">No;</label>
				</div>
				<s:if test="individual == false">	
					<div id="preguntaAdicional">
						&#191;Desea solicitar una placa adicional para su remolque o similar?
						<input type="radio" id="idAdicionalSi_<s:property value='#statusMatricula.index' />" name="idAdicional_<s:property value='#statusMatricula.index' />" onclick="showAdicional('<s:property value='#statusMatricula.index' />', true);" /><label for="idAdicional_%{#statusMatricula.index}">S&iacute;</label>
						<input type="radio" id="idAdicionalNo_<s:property value='#statusMatricula.index' />" name="idAdicional_<s:property value='#statusMatricula.index' />" onclick="showAdicional('<s:property value='#statusMatricula.index' />', false);" checked="checked" /><label for="idAdicional_%{#statusMatricula.index}">No</label>
					</div>
					<div id="contenedorSolicitudAdicional_<s:property value='#statusMatricula.index' />" class="contenedorSolicitudAdicional">
						<table>
							<tr>
								<td width="40%" style="text-align:right;">
									<label for="tipoPlacaAdicional_<s:property value='#statusMatricula.index' />"><strong>Placa adicional</strong></label>
								</td>
								<td>
									<s:select
										list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposPlacasMatriculacion(null,tipoVehiculo)"
										name="listaSolicitudes[%{#statusMatricula.index}].tipoPlacaAdicional"
										id="tipoPlacaAdicional_%{#statusMatricula.index}"
										headerKey=""
										headerValue="Tipo de Placa"
										listKey="valorEnum"
										listValue="nombreEnum + ' (' + ancho + 'x' + alto + ')'"
										onchange="calculoCreditosPlacas();"
										/>
								</td>
							</tr>
						</table>
					</div>
				</s:if>
				<div>
	<s:if test="mostrarSeccionPersona == 'true'">
		<div id="contenedorDatosPersona">
		<br>
		<table >
				<tr>
					<td align=left >
		    		   	<label for="nombre">nombre</label>
		    		</td>
					<td>
						<input type="text" size ="25" maxlength="25" style="position:relative;width:90%;color:#6E6E6E" id="nombre" 
							name="listaSolicitudes[<s:property value='#statusMatricula.index' />].nombre" />
					</td>
					<td style="width:30%" align="right">
						<s:text name="Primer Apellido / Razón Social"></s:text>
					</td>
					<td>
						<input type="text" size ="25" maxlength="25" id="primerApellido" name="listaSolicitudes[<s:property value='#statusMatricula.index' />].primerApellido"/>
					</td>
				</tr>
				<tr>	
					<td>
						<s:text name="segundo Apellido"></s:text>
					</td>
					<td>
						<input type="text" size ="25" maxlength="25" style="position:relative;width:90%;color:#6E6E6E" id="segundoApellido" 
							name="listaSolicitudes[<s:property value='#statusMatricula.index' />].segundoApellido"/>
					</td>
					<td align="right">
						<s:text name="NIF / CIF"></s:text>
					</td>
					<td>
						<input type="text" readonly="readonly" size ="12" maxlength="12" style="position:relative;width:90%;color:#6E6E6E" id="nif" 
							name="listaSolicitudes[<s:property value='#statusMatricula.index' />].nif" 
							value="<s:property value="listaSolicitudes[#statusMatricula.index].titular.nif"/>"
						/>
					</td>
				</tr>
			</table>	
		</div>
	</s:if>
	</div>
			</div>

		<s:hidden id="idSolicitud_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].idSolicitud" value="%{idSolicitud}" />
		
		<s:hidden id="idContrato_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].idContrato" value="%{idContrato}" />
		<s:hidden id="numExpediente_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].numExpediente" value="%{numExpediente}" />
		<s:hidden id="matricula_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].matricula" value="%{matricula}" />
		<s:hidden id="nif_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].nif" value="%{nif}" />
		<s:hidden id="bastidor_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].bastidor" value="%{bastidor}" />
		<s:hidden id="tipoVehiculo_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].tipoVehiculo" value="%{tipoVehiculo}" />
		<s:hidden id="numColegiado_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].numColegiado" value="%{numColegiado}" />
		<s:hidden id="fechaSolicitud_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].fechaSolicitud" value="%{fechaSolicitud}" />
		<s:hidden id="estado_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].estado" value="%{estado}" />
		<s:hidden id="nif_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].titular.nif" value="%{titular.nif}" />
		<s:hidden id="individual_%{#statusMatricula.index}" name="listaSolicitudes[%{#statusMatricula.index}].individual" value="listaSolicitudes[%{#statusMatricula.index}].individual" />
	</div>
	
</s:iterator>