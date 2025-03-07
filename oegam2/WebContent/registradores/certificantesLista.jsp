<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">
	function eliminarCertificante(nifCargo, codigoCargo) {
		if(confirm("Va a eliminar el certificante. ¿Desea continuar?")){
			document.formData.action='eliminarCertificante.action?nifCargo='+nifCargo+'&codigoCargo='+codigoCargo+'#Certificantes';
			document.formData.submit();
		}		
	}
</script>
	
<table class="subtitulo" align="left" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
		<td>Certificantes del tr&aacute;mite</td>
		<td align="right">
			<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
		</td>
	</tr>
</table>
	 

<s:if test="%{tramiteRegistro.certificantes.size > 0}">
	&nbsp;
	<display:table name="tramiteRegistro.certificantes" class="tablacoin" uid="row" summary="Listado de Certificantes" cellspacing="0" cellpadding="0">
		<display:column property="nifCargo" title="Nif" sortable="false" style="width:2%;text-align:left;" />
		<display:column property="sociedadCargo.personaCargo.nombre" title="Nombre" sortable="false" style="width:2%;text-align:left;" />
		<display:column property="sociedadCargo.personaCargo.apellido1RazonSocial" title="Apellido1" sortable="false" style="width:2%;text-align:left;" />
		<display:column property="sociedadCargo.personaCargo.apellido2" title="Apellido2" sortable="Apellido2" style="width:2%;text-align:left;" />

		<display:column property="sociedadCargo.personaCargo.correoElectronico" title="Email" sortable="false" style="width:2%;text-align:left;" />

		<display:column title="Cargo" sortable="false" style="width:3%;text-align:left;">
				<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.codigoCargo)}" />
		</display:column>
		
		<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
			<display:column title="Eliminar" style="width:1%;">
				<a onclick="" href="javascript:eliminarCertificante('${row.nifCargo}', '${row.codigoCargo}')">Eliminar</a>
			</display:column>
		</s:if>
		
		<display:column  style="width:1%" title="Editar">					  		
			<img src="img/mostrar.gif" alt="Editar" style="height:20px;width:20px;cursor:pointer" title="Editar" onclick="editarCertificante('${row.nifCargo}', '${row.codigoCargo}');"/>
		</display:column>

		<display:column  style="width:1%">					  		
			<img onclick="consultaEvolucionPersona('${row.nifCargo}','<s:property value="tramiteRegistro.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución">
		</display:column>
		
	</display:table>
	
</s:if>	
<s:else>
	<table width="95%" class="tablaformbasicaTC">
		<tr>
			<td>
				<span>No existen resultados</span>
			</td>
		</tr>
	</table>	
</s:else>

<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
	<div id="certificante" class="busqueda">
		<table border="0" class="tablaformbasicaTC" >
			<tr>
				<td colspan="6">
					<div id="resultado" style="width: 100%; background-color: transparent;">
						<table class="subtitulo" cellSpacing="0" style="width: 97%;">
							<tr>
								<td style="width: 100%; text-align: center;">Alta Certificante</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
			<table border="0" class="tablaformbasicaTC">
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="nombre">Nombre<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield name="tramiteRegistro.certificante.sociedadCargo.personaCargo.nombre" id="nombreCertificante"
					onfocus="this.className='inputfocus';" size="20" maxlength="20" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
				</td>
				<td align="right" nowrap="nowrap">
					<label for="apellido1">Apellido 1<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.certificante.sociedadCargo.personaCargo.apellido1RazonSocial" id="apellido1Certificante" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
				</td>
				<td align="right" nowrap="nowrap">
					<label for="apellido2">Apellido 2:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.certificante.sociedadCargo.personaCargo.apellido2" id="apellido2Certificante" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="numeroDocumento">Nif<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.certificante.sociedadCargo.personaCargo.nif" id="nifCertificante" onfocus="this.className='inputfocus';" size="10" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
				</td>
				<td align="right" nowrap="nowrap">
					<label for="correoElectronico">Correo Electrónico<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textfield  name="tramiteRegistro.certificante.sociedadCargo.personaCargo.correoElectronico" id="correoElectronicoCertificante" onfocus="this.className='inputfocus';" size="40" maxlength="40" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="cargo">Cargo<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoCargos()" 
						id="cargoCertificante" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="tramiteRegistro.certificante.sociedadCargo.codigoCargo" listValue="descCargo" listKey="codigoCargo" title="Cargo del certificante" headerKey="" headerValue="Seleccionar" />
				</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
		</table>
	</div>
</s:if>

<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<div id="botonBusqueda">
			<table width="100%" class="tablaformbasicaTC">
				<tr>
					<td>
						<input type="button" class="boton" value="Alta/Modificar" id="botonAltaCertificante" onclick="validacionAltaCertificante();"/>
					</td>
					<td> 
						<input type="button" value="Buscar" class="boton" onclick="javascript:abrirVentanaBusquedaCertificantes();" />&nbsp;
					</td>
					<td>
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
					<td>
						<input type="button" class="boton" value="Limpiar campos" onclick="limpiarFormulario('certificante');"/>
					</td>
				</tr>
			</table>
		</div>
	</s:if>