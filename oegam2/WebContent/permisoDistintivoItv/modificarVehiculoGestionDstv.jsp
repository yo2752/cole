<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/permisoDistintivo/permisoDistintivo.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<div class="contentTabs" id="Vehiculo">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del Vehiculo</td>
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionVehiculo('idVehiculo','<s:property value="vehiculoDto.numColegiado"/>');"
						style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">VEHÍCULO</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idTipoVehiculoDstv">Tipo de Vehículo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idTipoVehiculoDstv" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T1')"
						headerKey="-1" headerValue="Seleccione Tipo" name="vehiculoDto.tipoVehiculo"
						listKey="tipoVehiculo" listValue="descripcion"/>
				</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idVehiculoLabel">Id Vehículo: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield id="idVehiculoTextDstv" name="vehiculoDto.idVehiculo" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="5" maxlength="10" readonly="true" />
					</td>
				</s:if>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="bastidor">Número de Bastidor: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield id="idBastidorDstv" name="vehiculoDto.bastidor" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="22" maxlength="21" readonly="true" />
				</td>
			</tr>

		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">DATOS TARJETA ITV</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idNivelEmisionesDstv">Nivel de emisiones: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNivelEmisionesDstv" name="vehiculoDto.nivelEmisiones"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="16" maxlength="15"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="idCategoriaElectrica">Categoría eléctrica: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idCategoriaElectrica" name="vehiculoDto.categoriaElectrica"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCategoriaElectrica()"
							listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="Categoria Electrica"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="idAutonomiaElectrica">Autonomía eléctrica: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idAutonomiaElectrica"
						name="vehiculoDto.autonomiaElectrica" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="7" maxlength="6" />
				</td>
			</tr>
		</table>

		<table class="acciones" width="95%" align="left">
			<tr>
				<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
					<input type="button" class="boton" name="bActualizar" id="idBotonActualizar" value="Actualizar Vehículo" onClick="javascript:actualizarVehiculo();"
						onKeyPress="this.onClick" />
					&nbsp;&nbsp;&nbsp; 
					<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaPDI.action';"
						onKeyPress="this.onClick" />
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
</div>