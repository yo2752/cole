<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/registradores/registradores.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<div id="divContenidoFinanciador">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Edici&oacute;n/Alta
						de Financiador</span></td>
			</tr>
		</table>
	</div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div class="contenido">
		<%@include file="../../../includes/erroresMasMensajes.jspf"%>
		<s:form id="formDG" name="formDG"
			action="actualizarFinanciadores.action" cssClass="transformable">
			<s:hidden name="financiador.idInterviniente" id = "idFinanciador"/>
			<s:hidden name="financiador.numColegiado"/>
			<s:hidden name="financiador.persona.tipoPersona" id="ocultarInputsPorTipoPersona"/>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
				align="left">
				<tr>
					<td class="tabular"><span class="titulo">Financiador:</span></td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
				align="left">
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financialBackerCorpmePersonType">Tipo de persona<span class="naranja">*</span></label></td>
					<td width="30%"><s:select
							name="financiador.tipoPersona"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
							headerKey="" headerValue="Selecionar Tipo"
							listValue="nombreEnum" listKey="valorXML"
							id="financialBackerCorpmePersonType" /></td>
					<td align="left" nowrap="nowrap"><label for="financiadorNif">Nif/Cif.<span class="naranja">*</span></label></td>
					<td width="35%">
						<table style="align:left;">
							<tr>
								<td>
									<s:textfield name="financiador.nif"
										id="financiadorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
								</td>
								<td align="left" nowrap="nowrap"><input type="button"
									id="idBotonBuscarNifFinanciador" class="boton-persona"
									style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
									onclick="javascript:buscarFinanciadores()" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label for="financiadorNombre" >Nombre</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
					</td>
					<td><s:textfield name="financiador.persona.nombre" id="financiadorNombre" size="18" maxlength="100"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label for="financiadorApellido" >1er apellido / Raz&oacute;n Social<span class="naranja">*</span></label>
						<img src="img/botonDameInfo.gif" alt="Info" title="Apellido o raz&oacute;n social"/>
					</td>
					<td><s:textfield name="financiador.persona.apellido1RazonSocial" id="financiadorApellido" size="18" maxlength="100"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label for="financiadorApellido2">2do apellido</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
					</td>
					<td><s:textfield name="financiador.persona.apellido2" id="financiadorApellido2" size="18" maxlength="100"></s:textfield></td>
					
				
					<td align="left" nowrap="nowrap"><label for="financiadorEmail"
						class="small">Email</label></td>
					<td><s:textfield name="financiador.persona.correoElectronico"
							id="financiadorEmail" size="18" onblur="validaEmail(this.value)" maxlength="100"></s:textfield></td>
					
				</tr>
				<tr>	
					<td align="left" nowrap="nowrap"><label
						for="financiadorTelefono" class="small">N&uacute;mero
							tel&eacute;fono</label></td>
					<td><s:textfield name="financiador.persona.telefonos"
							id="financiadorTelefono" size="18" maxlength="20"></s:textfield></td>
				</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
				align="left">
				<tr>
					<td class="tabular"><span class="titulo">Direcci&oacute;n
							del financiador:</span></td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap"><label for="financiadorRegion" >Regi&oacute;n</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
					</td>
					<td><s:textfield name="financiador.direccion.regionExtranjera" id="financiadorRegion" size="18"></s:textfield></td>
				
				
					<td align="left" nowrap="nowrap"><label for="financiadorPais"
						class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
					<td colspan="3"><s:select
							name="financiador.direccion.pais"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
							listKey="codigo" listValue="nombre" headerKey=""
							headerValue="Selecionar Tipo" id="financiadorPais" /></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorProvinciaId">Provincia<span class="naranja">*</span></label></td>
					<td width="30%"><s:select
							name="financiador.direccion.idProvincia"
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="financiadorSelectProvinciaId"
							onchange="cargarListaMunicipios(this,'financiadorSelectMunicipioId','financiadorOcultoMunicipioId');
							inicializarTipoVia('financiadorCorpmeTipoViaId','financiadorNombreCalle', viaFinanciador);"/>
					</td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorMunicipio">Municipio<span class="naranja">*</span></label></td>
					<td width="35%"><s:select id="financiadorSelectMunicipioId"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(financiador)"
							onchange="javascript:seleccionMunicipio(this, 'financialBacker');
							inicializarTipoVia('financiadorCorpmeTipoViaId','financiadorNombreCalle', viaFinanciador);"
							name="financiador.direccion.idMunicipio"
							headerValue="Seleccione Municipio" listKey="idMunicipio"
							listValue="nombre" /> <s:hidden
							name="financiador.direccion.nombreMunicipio"
							id="financialBackerHiddenMunicipality" /> <s:hidden
							id="financiadorOcultoMunicipioId" /></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorPueblo">Entidad local</label></td>
					<td><s:textfield
							name="financiador.direccion.pueblo"
							id="financiadorPueblo" size="25" maxlength="70"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorCodigoPostal">C&oacute;digo postal<span class="naranja">*</span></label></td>
					<td><s:textfield
							name="financiador.direccion.codPostal"
							id="financiadorCodigoPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorCorpmeTipoViaId">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
					<td><s:select
							name="financiador.direccion.idTipoVia"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
							headerKey="" headerValue="Selecionar tipo" listKey="key"
							listValue="name" id="financiadorCorpmeTipoViaId" 
							onchange ="cargarListaNombresVia('financiadorSelectProvinciaId', 'financiadorSelectMunicipioId', this, 'financiadorNombreCalle', viaFinanciador);"/>
					</td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorNombreCalle">Nombre calle<span class="naranja">*</span></label></td>
					<td><s:textfield name="financiador.direccion.nombreVia" id="financiadorNombreCalle" size="35" maxlength="100"
					 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
				</tr>
			</table>	
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorNumeroCalle">N&uacute;m. calle<span class="naranja">*</span></label></td>
					<td><s:textfield
							name="financiador.direccion.numero"
							id="financiadorNumeroCalle" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorBloque">Bloque</label></td>
					<td><s:textfield
							name="financiador.direccion.bloque"
							id="financiadorBloque" size="5" maxlength="5"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorPlanta">Planta</label></td>
					<td><s:textfield
							name="financiador.direccion.planta"
							id="financiadorPlanta" size="5" maxlength="100"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorPuerta">Puerta</label></td>
					<td><s:textfield
							name="financiador.direccion.puerta"
							id="financiadorPuerta" size="5" maxlength="100"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorNumeroBis">N&uacute;m. bis</label></td>
					<td><s:textfield
							name="financiador.direccion.numeroBis"
							id="financiadorNumeroBis" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorEscalera">Escalera</label></td>
					<td><s:textfield
							name="financiador.direccion.escalera"
							id="financiadorEscalera" size="5" maxlength="100"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label for="financiadorKm">Punto
							KM</label></td>
					<td><s:textfield name="financiador.direccion.km" id="financiadorKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label for="financiadorPortal" class="small">Portal</label></td>
					<td><s:textfield name="financiador.direccion.portal" id="financiadorPortal" size="5"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" ><label for="financiadorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
					</td>
			        <td colspan="3"><s:textfield name="financiador.direccion.lugarUbicacion" id="financiadorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
			    </tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
				align="left">
				<tr>
					<td class="tabular"><span class="titulo">Datos del
							registro mercantil del financiador:</span></td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
				align="left">
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorDireccionRegistroOficinaId">Registro
							mercantil</label></td>
					<td width="30%"><s:select
							name="financiador.datRegMercantil.codRegistroMercantil"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()"
							listKey="idRegistro" listValue="nombre" headerKey=""
							headerValue="Selecionar Tipo"
							id="financiadorDatosDireccionRegistroOficinaId" /></td>
					<td align="left" nowrap="nowrap"><label for="financiadorTomo">Tomo
							inscrip. </label></td>
					<td width="35%"><s:textfield
							name="financiador.datRegMercantil.tomo" id="financiadorTomo"
							size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label for="financiadorLibro">Libro
							inscrip.</label></td>
					<td><s:textfield name="financiador.datRegMercantil.libro"
							id="financiadorLibro" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label for="financiadorFolio">Folio
							inscrip.</label></td>
					<td><s:textfield name="financiador.datRegMercantil.folio"
							id="financiadorFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label
						for="financiadorHojaInscripcion">Hoja inscrip.</label></td>
					<td><s:textfield name="financiador.datRegMercantil.hoja"
							id="financiadorHojaInscripcion" size="5" maxlength="255"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label
						for="financiadorNumeroInscripcion">Numero inscrip.</label></td>
					<td><s:textfield
							name="financiador.datRegMercantil.numInscripcion"
							id="financiadorNumeroInscripcion" size="5" maxlength="255"></s:textfield></td>
				</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
				align="left">
				<tr>
					<td class="tabular"><span class="titulo">Representantes
							del financiador:</span></td>
				</tr>
			</table>

			<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
				align="left">
				<tr>
					<td><display:table name="representantes" uid="representantes"
							class="tablacoin" id="representantes" summary="Listado de deudas"
							cellspacing="0" cellpadding="0" style="width:95%">
							
							<display:column title="Tipo" style="width:30%">
								<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro@convertirTextoDesdeXML(#attr.representantes.tipoPersona)}" />
							</display:column>
							<display:column title="Nif/Cif" property="persona.nif"
								style="width:30%" />
							<display:column title="Apellido"
								property="persona.apellido1RazonSocial" style="width:30%" />
							<display:column title="Eliminar" style="width:10%">
								<a
									href="javascript:doConfirmPost('¿Está seguro de que desea borrar este representante?', 'formDG', 'borrarRepresentanteFinanciadores.action?identificador=${representantes.idInterviniente}', null);">
									Eliminar </a>
							</display:column>
							<display:column style="width:3%">
								<a
									href="javascript:aniadeRepresentanteFinanciador('formDG','cargarPopUpRepresentanteFinanciadorRegistroFinanciadores.action?idRepresentante=${representantes.idInterviniente}')">
									<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
								</a>
							</display:column>
						</display:table></td>
				</tr>
			</table>

			<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
				align="left">
				<tr>
					<td align="center" nowrap="nowrap" colspan="6"><input
						type="button"
						onclick="javascript:aniadeRepresentanteFinanciador('formDG','cargarPopUpRepresentanteFinanciadorRegistroFinanciadores.action');"
						id="btnAniadirRepresentanteFinanciador" class="button corporeo"
						value="Añadir representante" /></td>
				</tr>
			</table>
			&nbsp;
			<center>
				<input type="button"
					onclick="javascript:doPostValidate('formDG', 'actualizarFinanciadores.action', null, null);"
					id="btn" class="button corporeo" value="Guardar" />
				<div id="bloqueLoadingRegistro"
					style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../../includes/bloqueLoading.jspf"%>
				</div>

			</center>
			<div id="divEmergentePopUpFinanciador"
				style="display: none; background: #f4f4f4;"></div>
		</s:form>

	</div>
</div>
<script>
	corregirInputs();
	
	function corregirInputs(){

		if ($("#ocultarInputsPorTipoPersona").val() != null && $("#ocultarInputsPorTipoPersona").val() != ""){
			$("#financiadorNif",parent.document).prop("readonly", true);
			$("#idBotonBuscarNifFinanciador").prop("disabled", true);
			$('#financiadorApellido').prop("readonly", true);
			$('#financiadorNombre').prop("readonly", true);
			$('#financialBackerCorpmePersonType').prop("readonly", true);		
		} 
	}

	var viaFinanciador = new BasicContentAssist(document.getElementById('financiadorNombreCalle'), [], null, true); 
	recargarNombreVias('financiadorSelectProvinciaId', 'financiadorSelectMunicipioId', 'financiadorCorpmeTipoViaId','financiadorNombreCalle',viaFinanciador);


</script>
