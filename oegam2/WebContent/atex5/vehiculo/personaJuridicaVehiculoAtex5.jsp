<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCif">Cif:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCifJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.cif}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="10" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelRazonSocial">Razon Social:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idRazonSocialJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.razonSocial}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoPersona">Tipo Persona:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoPersonaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.tipoPersona}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCalleDomTitJurVeh">Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaDomTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilio.via}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCodPostalDomTitJurVeh">Cod.Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodPostalDomTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilio.codPostal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelProvinciaDomTitJurVeh">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProvinciaDomTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilio.provincia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelMunicipioDomTitJurVeh">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMunicipioDomTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilio.municipio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPuebloDomTitJurVeh">Pueblo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuebloDomTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilio.pueblo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección Ine.</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoViaDomIneTitJurVeh">Tipo Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoViaDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.tipoVia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCalleDomIneTitJurVeh">Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.via}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
				<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCodPostalDomIneTitJurVeh">Cod.Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodPostalDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.codPostal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelProvinciaDomIneTitJurVeh">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProvinciaDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.provincia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMunicipioDomIneTitJurVeh">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMunicipioDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.municipio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPuebloDomIneVeh">Pueblo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuebloDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.pueblo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelNumeroDomIneTitJurVeh">Numero:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNumeroDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.numeroVia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelBloqueDomIneTitJurVeh">Bloque:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idBloqueDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.bloque}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelEscaleraDomIneTitJurVeh">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idEscaleraDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.escalera}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPlantaDomIneVeh">Planta:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPlantaDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.planta}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPortalDomIneTitJurVeh">Portal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPortalDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.portal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPuertaDomIneTitJurVeh">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuertaDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.puerta}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelHmDomIneVeh">Hm:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idHmDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.hm}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelKmDomIneVeh">Km:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idKmDomIneTitJurVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaJuridicaDto.domicilioIne.km}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
	</table>
	
</div>