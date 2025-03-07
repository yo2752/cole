<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos Del Vehículo</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Vehículo</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelBastidor">Bastidor:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idBastidorVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.bastidor}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelNive">Nive:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNiveVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.nive}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMarca">Marca:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMarcaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.marca}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelModelo">Modelo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idModeloVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.modelo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelFabricante">Fabricante:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idFabricanteVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.fabricante}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelFechaMatri">Fecha Matriculacion:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idFechaMatriVehAtex5" 
				   value="%{@org.gestoresmadrid.oegam2.atex5.utiles.UtilesVistaAtex5@getInstance().convertirFecha(consultaVehiculoAtex5Dto.vehiculoAtex5.fechaMatriculacion)}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPaisProcedencia">Pais de Procedencia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPaisProcVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.paisProcedencia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelServicio">Servicio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idServicioVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.servicio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelServicioComp">Servicio Complementario:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idServCompVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.servicioComplementario}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoIndustria">Tipo Industria:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoIndVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tipoIndustria}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoVeh">Tipo Vehículo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoVehVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tipoVehiculo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelColor">Color:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idColorVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.color}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelDistanciaEjes">Distancia de Ejes:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idDistanciaEjesVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.distanciaEjes}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelViaAnteriro">Via Anterior:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaAnteriorVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.viaAnterior}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelViaPosterior">Via Posterior:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaPosteriorVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.viaPosterior}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelMasaMaxTecnica">Masa Max. Técnica:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMasaMaxTecVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.masaMaxTecnica}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMasaMaxima">Masa Máxima:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMasaMaxVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.masaMaxima}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPesoMax">Peso Máximo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPesoMaxVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.pesoMaximo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTara">Tara:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTaraVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tara}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelNumHom">Num. Homologacion:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNumHomVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.numHomologacion}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPlazasMixt">Plazas Mixtas:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPlazasMixtVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.mixtas}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPlazasNorm">Plazas Normales:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPlazaNormVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.normales}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPlazasPie">Plazas de Pie:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPlazasPieVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.numPlazasPie}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCilindrada">Cilindrada:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCilindradaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.cilindrada}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPotenciaFiscal">Potencia Fiscal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPotenciaFiscalVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.potenciaFiscal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPoteciaNetaMax">Potencia Neta Max.:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPotenciaNetaMaxVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.potenciaNetaMax}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPotenciaPeso">Potencia peso:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPotenciaPesoVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.relPotenciaPeso}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelProcedencia">Procedencia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProcedenciaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.procedencia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelVariante">Variante:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idVarianteVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.variante}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelVersion">Versión:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idVersionVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.version}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCombustible">Combustible:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCombustibleVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.combustible}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCategoriaElec">Categoria Electrica:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCatgElecVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.categoriaElectrica}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelAutoElectrica">Autonomia Electrica:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idAutoElectVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.autonomiaElectrica}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCodigoEco">Codigo Eco:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodigoEcoVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.codigoEco}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCodos">Codos:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodosVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.codos}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelConsumo">Consumo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idConsumoVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.consumo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelEcoInno">Eco Innovacion:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idEcoInnVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.ecoInnovacion}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelNivelEmisiones">Nivel de Emisiones:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNivelEmisionesVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.nivelEmisiones}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPropulsion">Propulsion:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPropulsionVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.propulsion}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelReduccionEco">Reducción Eco:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idReduccionEcoVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.reduccionEco}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoAlimentacion">Tipo Alimentacion:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoAlimVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tipoAlimentacion}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelTipoRequisitoria">Tipo Requisitoria:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoRequisitoriaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.tipoRequisitoria}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelDescRequisitoria">Descripcion Requisitoria:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idDescRequisitoriaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.descripcionRequisitoria}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelFechaRequisitoria">Fecha Requisitoria:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idFechaRequisitoriaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.fechaVigorRequisitoria}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelAccionesRequisitoria">Acciones Requisitoria:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idAccionesRequisitoriaVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.accionesRequisitoria}" onblur="this.className='input2';"
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
					<label for="labelCalleDomVeh">Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaDomVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilio.via}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCodPostalDomVeh">Cod.Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodPostalDomVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilio.codPostal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelProvinciaDomVeh">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProvinciaDomVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilio.provincia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelMunicipioDomVeh">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMunicipioDomVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilio.municipio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPuebloDomVeh">Pueblo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuebloDomVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilio.pueblo}" onblur="this.className='input2';"
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
					<label for="labelTipoViaDomIneVeh">Tipo Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idTipoViaDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.tipoVia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelCalleDomIneVeh">Via:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idViaDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.via}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelNumeroDomIneVeh">Numero:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idNumeroDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.numeroVia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelBloqueDomIneVeh">Bloque:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idBloqueDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.bloque}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelEscaleraDomIneVeh">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idEscaleraDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.escalera}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPlantaDomIneVeh">Planta:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPlantaDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.planta}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelPortalDomIneVeh">Portal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPortalDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.portal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPuertaDomIneVeh">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuertaDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.puerta}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelHmDomIneVeh">Hm:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idHmDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.hm}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelKmDomIneVeh">Km:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idKmDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.km}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelCodPostalDomIneVeh">Cod.Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idCodPostalDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.codPostal}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelProvinciaDomIneVeh">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idProvinciaDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.provincia}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
					<label for="labelMunicipioDomIneVeh">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idMunicipioDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.municipio}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
					<label for="labelPuebloDomIneVeh">Pueblo:</label>
				</td>
				<td align="left" nowrap="nowrap">
				   <s:textfield id="idPuebloDomIneVehAtex5" value="%{consultaVehiculoAtex5Dto.vehiculoAtex5.domicilioIne.pueblo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" disabled="true"/>
			</td>
		</tr>
	</table>
</div>