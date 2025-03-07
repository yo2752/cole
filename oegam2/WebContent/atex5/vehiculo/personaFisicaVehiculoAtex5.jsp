<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelNif">Nif:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNifVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.nif}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="10" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelNombre">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNombreVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.nombre}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelApe1">1er Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idApe1VehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.apellido1}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelApellido2">2do Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idApe2VehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.apellido2}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelSexo">Sexo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idSexoVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.sexo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelFechaNac">Fecha Nacimiento:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idFecNacVehAtex5" 
				   value="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().convertirFecha(consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.fechaNacimiento)}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoPersona">Tipo Persona:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoPersonaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.tipoPersona}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelDirElectronica">Dirección Electrónica:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idDirElectronicaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.dirElectronicaVial}" onblur="this.className='input2';"
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
					<label for="labelCalleDomTitVeh">Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaDomTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilio.via}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCodPostalDomTitVeh">Cod.Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodPostalDomTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilio.codPostal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelProvinciaDomTitVeh">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProvinciaDomTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilio.provincia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelMunicipioDomTitVeh">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMunicipioDomTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilio.municipio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPuebloDomTitVeh">Pueblo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuebloDomTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilio.pueblo}" onblur="this.className='input2';"
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
					<label for="labelTipoViaDomIneTitVeh">Tipo Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoViaDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.tipoVia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCalleDomIneTitVeh">Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.via}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
				<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCodPostalDomIneTitVeh">Cod.Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodPostalDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.codPostal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelProvinciaDomIneTitVeh">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProvinciaDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.provincia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMunicipioDomIneTitVeh">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMunicipioDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.municipio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPuebloDomIneTitVeh">Pueblo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuebloDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.pueblo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelNumeroDomIneTitVeh">Numero:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNumeroDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.numeroVia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelBloqueDomIneVeh">Bloque:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idBloqueDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.bloque}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelEscaleraDomIneTitVeh">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idEscaleraDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.escalera}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPlantaDomIneTitVeh">Planta:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPlantaDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.planta}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPortalDomIneTitVeh">Portal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPortalDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.portal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPuertaDomIneTitVeh">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuertaDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.puerta}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelHmDomIneTitVeh">Hm:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idHmDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.hm}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelKmDomIneTitVeh">Km:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idKmDomIneTitVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.personaFisicaDto.domicilioIne.km}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
	</table>
	
</div>