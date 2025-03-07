<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div id="certificanteFisico" style="width: 100%;;display:none">
	<div class="nav">
		<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style=""><span class="titulo">Alta de certificante:</span></td>
			</tr>
		</table>
	</div>
	<table border="0" class="tablaformbasicaTC">
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="nombre">Nombre<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield name="sociedadCargo.personaCargo.nombre" id="nombre" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
			</td>
			<td align="right" nowrap="nowrap">
				<label for="apellido1">apellido 1<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="sociedadCargo.personaCargo.apellido1RazonSocial" id="apellido1" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
			</td>
			<td align="right" nowrap="nowrap">
				<label for="apellido2">apellido 2:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="sociedadCargo.personaCargo.apellido2" id="apellido2" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
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
				<s:textfield name="sociedadCargo.personaCargo.nif" id="nif" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="correoElectronico">Correo Electrónico<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="4">
				<s:textfield  name="sociedadCargo.personaCargo.correoElectronico" id="correoElectronico" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="40" maxlength="40"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="cargo">Cargo<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoCargos()" 
					id="cargoCer" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="sociedadCargo.codigoCargo" listValue="descCargo" listKey="codigoCargo" title="Cargo del certificante" headerKey="" headerValue="Seleccionar" />
			</td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		</table>
		<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
			<div id="botonBusqueda">
				<table width="100%">
					<tr>
						<td width="50%" align="right">
							<input type="button" class="boton" value="Alta" id="botonAltaCertificante" onclick="validacionAltaCertificante();"/>
						</td>
						<td>
							<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="50%" align="left"> 
							<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposCerFis();"/>
						</td>
					</tr>
				</table>
			</div>
		</s:if>
</div>