<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div id="destinatario">
	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Datos de escritura</td>
				<s:if test="tramiteRegistro.idTramiteRegistro">
					<td  align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
		</tbody>
	</table>
	
	
	<table class="nav" cellspacing="0" cellpadding="5" width="100%">
		<tr>
			<td class="tabular" >
				<span class="titulo">Cliente o destinatario</span>
			</td>
			<s:if test="tramiteRegistro.sociedad.nif != null && tramiteRegistro.numColegiado != null">
				<td class="tabular" width="1%" align="right">
					<img onclick="consultaEvolucionPersona('<s:property value="tramiteRegistro.sociedad.nif"/>','<s:property value="tramiteRegistro.numColegiado"/>');" 
						style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución"> 
				</td>
			</s:if>
		</tr>
	</table>
	
	<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%">
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >		
				<table cellPadding="1" cellSpacing="3">
					<tr>
						<td align="left" nowrap="nowrap" colspan="2">
							<label for="nifDes">NIF/CIF<span class="naranja">*</span></label>
						</td>
					</tr>										
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="tramiteRegistro.sociedad.nif" 
								id="nifDes" size="9" maxlength="9" onblur="this.className='input';this.value=this.value.toUpperCase();" 
								onfocus="this.className='inputfocus';" onkeyup="this.value=this.value.toUpperCase()"/>
						</td>
						<td	align="left" nowrap="nowrap" >
							<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" onclick="javascript:buscarIntervinienteEscritura('nifDes','destinatario')"/>
							</s:if>
						</td>
					</tr>
				</table>								
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="apellido1RazonSocDestinatario">1.er Apellido o Razón Social<span class="naranja">*</span></label>
			    <s:textfield name="tramiteRegistro.sociedad.apellido1RazonSocial" id="apellido1RazonSocDestinatario" size="25" maxlength="75"
			       onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>							
			<td align="left" nowrap="nowrap"  colspan="3">
				<label for="apellido2Destinatario">2º Apellido</label>
				<s:textfield name="tramiteRegistro.sociedad.apellido2" id="apellido2Destinatario" size="25" maxlength="25"
					onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarLetrasEspacios(event)"/>
			</td>
			<td align="left" nowrap="nowrap"  colspan="2">
				<label for="nombreDestinatario">Nombre</label>
				<s:textfield name="tramiteRegistro.sociedad.nombre" id="nombreDestinatario" size="25" maxlength="25"
					onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarLetrasEspacios(event)"/>
			</td>
		</tr>
	</table>	
<%-- 		<s:if test="tramiteRegistro.sociedad.direccionDto.idDireccion != null">
			<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap" width="100%">
						<table  style="align:left;">
							<tr>
								<td  align="left" style="vertical-align:middle" width="10em">
									<label>Seleccionar Dirección</label>
								</td>
								<td align="left" nowrap="nowrap" width="5em">
									<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
										<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
											<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
												onclick="javascript:seleccionarDireccion('<s:property value="tramiteRegistro.sociedad.nif"/>','<s:property value="tramiteRegistro.numColegiado"/>', 'destinatario');"/>
										</s:if>
									</s:if>
								</td>
								<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
									<td>
										<label>Id dirección</label>
										<span><s:textfield id="idDireccionDestinatario" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteRegistro.sociedad.direccionDto.idDireccion}"></s:textfield></span>
									</td>
								</s:if>
							</tr>
						</table>
					</td>
				</tr>	
			</table>
		</s:if> --%>
		
	<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%">	
		<tr>
			<td align="left" nowrap="nowrap"  colspan="1">
				<label for="idProvinciaDestinatario">Provincia<span class="naranja">*</span></label>
				<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasPresentador()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					headerKey="-1" headerValue="Seleccione Provincia" name="tramiteRegistro.sociedad.direccionDto.idProvincia" 
					listKey="idProvincia" listValue="nombre" id="idProvinciaDestinatario" 
					onchange="cargarListaMunicipios(this,'idMunicipioDestinatario','municipioSeleccionadoDestinatario'); 
					cargarListaTipoVia(this,'tipoViaDestinatario','tipoViaSeleccionadaDestinatario');
					inicializarTipoVia('tipoViaDestinatario','nombreViaDestinatario', viaDestinatario);"/>		
			</td>
			<td align="left" nowrap="nowrap"  colspan="5">
				<label for="idMunicipioDestinatario">Municipio<span class="naranja">*</span></label>
				<select id="idMunicipioDestinatario" onblur="this.className='input2';" onblur="this.className='input2';"
					onchange="seleccionarCampo('municipioSeleccionadoDestinatario','idMunicipioDestinatario'); 
						inicializarTipoVia('tipoViaDestinatario','nombreViaDestinatario',viaDestinatario);">
						<option value="-1">Seleccione Municipio</option>
				</select>
				<s:hidden id="municipioSeleccionadoDestinatario" name="tramiteRegistro.sociedad.direccionDto.idMunicipio"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="tipoViaDestinatario">Tipo Vía<span class="naranja">*</span></label>
			    <select id="tipoViaDestinatario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					onchange="seleccionarCampo('tipoViaSeleccionadaDestinatario','tipoViaDestinatario');
						cargarListaNombresVia('idProvinciaDestinatario', 'municipioSeleccionadoDestinatario', this, 'nombreViaDestinatario', viaDestinatario);">
						<option value="-1">Seleccione Tipo Vía</option>
				</select>
			    <s:hidden id="tipoViaSeleccionadaDestinatario" name="tramiteRegistro.sociedad.direccionDto.idTipoVia"/>
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="nombreViaDestinatario">Nombre de la Vía Pública<span class="naranja">*</span></label>
				<s:textfield id="nombreViaDestinatario"	name="tramiteRegistro.sociedad.direccionDto.nombreVia" 
					size="40" maxlength="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="numeroDestinatario">Número<span class="naranja">*</span></label>
				<s:textfield name="tramiteRegistro.sociedad.direccionDto.numero" id="numeroDestinatario" size="5" maxlength="5" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="escaleraDestinatario">Esc.</label>
				<s:textfield name="tramiteRegistro.sociedad.direccionDto.escalera" id="escaleraDestinatario" size="2" maxlength="2" 
					onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="plantaDestinatario">Piso</label>
	      			<s:textfield name="tramiteRegistro.sociedad.direccionDto.planta" id="plantaDestinatario" size="2" maxlength="2" 
	           			onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="puertaDestinatario">Puerta</label>
          			<s:textfield name="tramiteRegistro.sociedad.direccionDto.puerta" id="puertaDestinatario" size="2" maxlength="2" 
               			onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="codPostalDestinatario">Código Postal<span class="naranja">*</span></label>
			    <s:textfield name="tramiteRegistro.sociedad.direccionDto.codPostal" id="codPostalDestinatario" maxlength="5" size="5" 
			       onkeypress="return validarNumeros(event)" onblur="this.className='input';" onfocus="this.className='inputfocus';" />
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="telefonosDestinatario">Tel&eacute;fono</label>
				<s:textfield name="tramiteRegistro.sociedad.telefonos" id="telefonosDestinatario" size="9" maxlength="9" 
					onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)"/>
			</td>
		</tr>		
	</table>
</div>
	&nbsp;
	
	<div id="botonBusqueda">
	<table width="100%">
		<tr>
			<td>
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
					<input type="button" class="boton" value="Guardar" style="size: 100%; TEXT-ALIGN: center;" onclick="javascript:guardarEscritura();" id="botonGuardarEscritura"/>
				</s:if>
			</td>
			<td>
				<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
			</td>
		</tr>
	</table>
</div>
