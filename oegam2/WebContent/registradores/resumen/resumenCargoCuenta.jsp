<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="idNombreTitularDatBancariosResumen">Nombre Titular:</label></td>
		<td align="left" nowrap="nowrap" colspan = "2">
			<s:textfield name="tramiteRegistro.datosBancarios.nombreTitular" id="idNombreTitularDatBancariosResumen" size="31" maxlength="100" 
				onblur="this.className='input';" onfocus="this.className='inputfocus';" disabled="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="cuentaProvinciaId" class="small">Provincia:</label></td>
             	<td>
              	<s:select name="tramiteRegistro.datosBancarios.idProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
					headerKey="" headerValue="Seleccionar" listKey="idProvincia"
					listValue="nombre" id="cuentaSelectProvinciaId" disabled="true"/>
             </td>
             <td align="left" nowrap="nowrap"><label for="cuentaMunicipio" class="small">Municipio:</label></td>
             <td>       
			<s:select id="cuentaSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegistro.datosBancarios.idProvincia)"
					name="tramiteRegistro.datosBancarios.idMunicipio"
					headerValue="Seleccione Municipio" 
					listKey="idMunicipio"
					listValue="nombre" disabled="true"/>
             </td>
	</tr>
</table>
		
<table  class="tablaformbasica">
	<tr>
		<td align="left" nowrap="nowrap" width="15%">
			<label for="labelNumeroCuenta">N&uacute;mero de Cuenta:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<table>
				<tr>
					<td>
						<label for="idIbanResumen">IBAN</label>
						<s:textfield autocomplete="off" size="4" maxlength="4"
							id="idIbanResumen" name="tramiteRegistro.datosBancarios.iban"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" disabled="true"></s:textfield>
					</td>
					<td>
						<label for="idEntidadResumen">Entidad</label>
						<s:textfield autocomplete="off" size="4" maxlength="4"
							id="idEntidadResumen" name="tramiteRegistro.datosBancarios.entidad"
							onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" disabled="true"></s:textfield>
					</td>
					<td>
						<label for="idOficinaResumen">Oficina</label>
						<s:textfield autocomplete="off" size="4" maxlength="4"
							id="idOficinaResumen" name="tramiteRegistro.datosBancarios.oficina"
							onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" disabled="true"></s:textfield>
					</td>
					<td>
						<label for="idDCResumen">DC</label>
						<s:textfield autocomplete="off" size="2" maxlength="2"
							id="idDCResumen" name="tramiteRegistro.datosBancarios.dc"
							onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" disabled="true"></s:textfield>
					</td>
					<td>
						<label for="idNumeroCuentaPagoResumen">Cuenta</label>
						<s:textfield autocomplete="off" size="10"
							maxlength="10" id="idNumeroCuentaPagoResumen"
							name="tramiteRegistro.datosBancarios.numeroCuentaPago"
							onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" disabled="true"></s:textfield>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
