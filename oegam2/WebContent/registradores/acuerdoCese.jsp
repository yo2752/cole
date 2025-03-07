<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table class="subtitulo" align="left" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
		<td>Ceses</td>
		<td align="right">
			<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
		</td>
	</tr>
</table>


<s:if test="tramiteRegistro.ceses && tramiteRegistro.ceses.size > 0">
	<div id="busqueda" class="busqueda">
		<table border="0" class="tablaformbasicaTC">
			<tr>
				<td colspan="2">
					<div id="resultado" style="width: 100%; background-color: transparent;">
						<table class="subtitulo" cellSpacing="0" style="width: 97%;">
							<tr>
								<td style="width: 100%; text-align: center;">Ceses acordados</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">		
					<div class="divScroll">
						<display:table name="tramiteRegistro.ceses" excludedParams="*" class="tablacoin" uid="row" summary="Listado de Ceses" cellspacing="0" cellpadding="0">
								<display:column property="sociedadCargo.personaCargo.nombre" title="Nombre" style="width:1%;text-align:left;" />
								<display:column property="sociedadCargo.personaCargo.apellido1RazonSocial" title="Apellido1" style="width:1%;text-align:left;"/>
								<display:column property="sociedadCargo.personaCargo.apellido2" title="Apellido2" style="width:1%;text-align:left;"/>
								<display:column property="sociedadCargo.personaCargo.nif" title="Nif" style="width:1%;text-align:left;"/>
								<display:column title="Cesa como" style="width:10%;text-align:left;">
									<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.sociedadCargo.codigoCargo)}" />
								</display:column>
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
				  					<display:column title="Eliminar" style="width:1%;">
				  						<a onclick="" href="javascript:eliminarAcuerdo(${row.idAcuerdo})">Eliminar</a>
				  					</display:column>
				  				</s:if>
			  					<display:column style="width:1%;">
			  						<img onclick="consultaEvolucionPersona('${row.sociedadCargo.personaCargo.nif}','<s:property value="tramiteRegistro.numColegiado"/>');" 
										style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución">
			  					</display:column>
						</display:table>
					</div>	
				</td>
			</tr>
		</table>
	</div>
</s:if>
<s:else>
	<table width="95%" class="tablaformbasicaTC">
		<tr>
			<td>
				<span>No existen ceses</span>
			</td>
		</tr>
	</table>	
</s:else>
&nbsp;

<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">			
	<table border="0" class="tablaformbasicaTC">
		<tr>
			<td colspan="6">
				<div id="resultado" style="width: 100%; background-color: transparent;">
					<table class="subtitulo" cellSpacing="0" style="width: 97%;">
						<tr>
							<td style="width: 100%; text-align: center;">Alta Ceses</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
		<td align="right" nowrap="nowrap">
			<label for="nombreC">Nombre<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoCese.sociedadCargo.personaCargo.nombre" id="nombreC" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="apellido1C">Apellido 1<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoCese.sociedadCargo.personaCargo.apellido1RazonSocial" id="apellido1C" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="apellido2C">Apellido 2:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoCese.sociedadCargo.personaCargo.apellido2" id="apellido2C" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="tipoDocumento">Nif<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoCese.sociedadCargo.personaCargo.nif" id="nifC" onblur="this.className='input2';" onfocus="this.className='inputfocus';this.value=this.value.toUpperCase();" size="12" maxlength="10" onkeyup="this.value=this.value.toUpperCase()"/>	
		</td>
		<td align="right" nowrap="nowrap">
			<label for="cargo">Cesa como<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="5">
			<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoCargos()" 
				id="cargoC" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
				name="tramiteRegistro.acuerdoCese.sociedadCargo.codigoCargo" listValue="descCargo" listKey="codigoCargo" title="Cargo del certificante" headerKey="" headerValue="Seleccionar" />
		</td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
</table>

	<div id="botonBusqueda">
		<table width="100%" class="tablaformbasicaTC">
			<tr>
				<td> 
					<input type="button" id="botonAltaCese" value="Alta/Modificación" class="boton" onclick="javascript:validacionCese();" />
				</td>
				<td>
					<img id="loading6Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<td>  
					<input type="button" value="Buscar" class="boton" onclick="javascript:abrirVentanaBusquedaCargos('cese');" />
				</td>
				<td>
					<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposCese();"/>
				</td>
			</tr>
		</table>
	</div>
</s:if>