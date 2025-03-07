<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Bien Rustico</td>
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
					<s:textfield name="bien.direccion.idDireccion" id="idDireccionRustico" size="25" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idDireccionRusticoHidden" name="bien.direccion.idDireccion"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvincia">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaProvincias()" onblur="this.className='input2';" 
		    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
		    		name="bien.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienRustico" 
					onchange="cargarListaMunicipiosCam('idProvinciaBienRustico','idMunicipioRustico');"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
			    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioRustico"
					headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
					name="bien.direccion.idMunicipio" onchange="obtenerCodigoPostalPorMunicipio('idProvinciaBienRustico', this, 'idCodPostalRustico');"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaMunicipiosPorProvincia(bien)"
				/>
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
							<s:textfield name="bien.direccion.codPostal" id="idCodPostalRustico" size="5" maxlength="5" onblur="this.className='input2';" 
				       			onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
	       					<input type="button" class="boton-persona" id="botonCorreos" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
								onclick="javascript:abrirVentanaCorreosPopUp();"/>
	       				</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelParaje">Paraje o sitio:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.paraje" id="idParajeRustico" size="25" maxlength="30" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelCodPoligono">Polígono:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.poligono" id="idPoligonoRustico" size="25" maxlength="30" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelParaje">Parcela:</label>
		</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.parcela" id="idParcelaRustico" size="25" maxlength="30" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSubParcela">SubParcela:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.subParcela" id="idSubParcelaRustico" size="25" maxlength="30" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelRefCatrastal">Referencia Catastral:</label>
			</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="bien.refCatrastal" id="idRefCatrastalRustico" size="25" maxlength="20" onblur="this.className='input2';" 
					       			onfocus="this.className='inputfocus';"/>
							</td>
							<td align="left" nowrap="nowrap">
		       					<input type="button" class="boton-persona" id="botonCatastro" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:abrirVentanaCatastroPopUp();"/>
		       				</td>
						</tr>
					</table>
				</td>
		</tr>
	</table>
</div>
