<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Bien Urbano</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección:</span>
			</td>
		</tr>
	</table>	
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && bien.direccion.idDireccion != null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelIdDir">Id dirección:</label>
				</td>
				<td>
					<s:textfield name="bien.direccion.idDireccion" id="idDireccionUrbano" size="25" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idDireccionUrbanoHidden" name="bien.direccion.idDireccion"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvincia">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaProvincias()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
			    		name="bien.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienUrbano" 
						onchange="cargarListaMunicipiosCam('idProvinciaBienUrbano','idMunicipioBienUrbano'); 
							cargarListaTipoVia('idProvinciaBienUrbano','idTipoViaUrbano');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
			    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienUrbano"
					headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
					name="bien.direccion.idMunicipio" onchange="obtenerCodigoPostalPorMunicipio('idProvinciaBienUrbano', this, 'idCodPostalUrbano');"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaMunicipiosPorProvincia(bien)"
				/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoVia">Tipo Vía<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposVias()"
					headerKey="-1" headerValue="Seleccione Tipo Via" listKey="idTipoViaCam" listValue="nombre" 
					name="bien.direccion.idTipoVia"
					id="idTipoViaUrbano"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelNombre">Nombre Vía Pública<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.direccion.nombreVia" id="idNombreViaUrbano" size="25" maxlength="100" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelCodPostal">Cód. Postal<span class="naranja">*</span>:</label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="bien.direccion.codPostal" id="idCodPostalUrbano" size="5" maxlength="5" onblur="this.className='input2';" 
				       			onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
	       					<input type="button" class="boton-persona" id="botonCorreos" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
								onclick="javascript:abrirVentanaCorreosPopUp();"/>
	       				</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelNumDireccion">Nº<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.direccion.numero" id="idNumeroViaUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelDupTri">Dup/Trip:</label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:select  name="bien.dupTri"  id="idDupTriUrbano"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getDupleTri()" 
		    		headerKey="" headerValue="Seleccione Dup/Trip" 
		    		onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					listKey="valorEnum" listValue="nombreEnum" />
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelNLocal">Nº Local:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.direccion.numLocal" id="idNumLocalUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelNPiso">Piso:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.direccion.planta" id="idPisoUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelPuerta">Puerta:</label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:textfield name="bien.direccion.puerta" id="idPuertaUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<label for="labelNLocal">Escalera:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.direccion.escalera" id="idEscaleraUrbano" size="10" maxlength="10" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
	</table>
</div>
