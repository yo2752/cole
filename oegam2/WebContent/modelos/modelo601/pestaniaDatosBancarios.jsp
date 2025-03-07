<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Modelo 601</td>
		</tr>
	</table>
	<%@include file="../../../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Datos Bancarios Modelo 601</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" width="150px">
				<label for="labelSelecccionCuenta">Seleccione Dato Bancario:</label>
			</td>
			<td align="left"  nowrap="nowrap">
				<s:radio id="idTipoDatoBancario" cssStyle="display: inline;" labelposition="left"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getTiposCuentasBancarias()"
					listKey="valorEnum" listValue="nombreEnum" name="modeloDto.datosBancarios.tipoDatoBancario"
					onchange="javascript:habilitarPorTipoDatoBancario()">
				</s:radio>
			</td>
		</tr>
		<tr id="idSeleccionDatosBancFavoritos">
			<td align="left" nowrap="nowrap">
				<label for="labelSelecccionFavoritos">Cuenta Bancaria Favorita:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idComboExistente"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaDatosBancariosFavoritos(modeloDto)" 
					headerKey="" headerValue="Seleccione Datos Bancarios Favoritos" onchange="cargarDatosBancariosFavoritos('divDatosBancarios601');"
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					listKey="idDatosBancarios" listValue="descripcion" ></s:select>
				<s:hidden id="idDatosBancarios" name="modeloDto.datosBancarios.idDatosBancarios"/>
			</td>
		</tr>
	</table>
	<div id="divDatosBancarios601">
		<table  class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNifPagador">Nif Pagador:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.datosBancarios.nifPagador" id="idNifPagadorDatBancarios" size="10" maxlength="10" 
						onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNumeroCuenta">Numero de Cuenta:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td>
								<label for="idEntidad">entidad</label>
								<s:textfield autocomplete="off" size="4" maxlength="4"
									id="idEntidad" name="modeloDto.datosBancarios.entidad"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
							<td>
								<label for="idOficina">oficina</label>
								<s:textfield autocomplete="off" size="4" maxlength="4"
									id="idOficina" name="modeloDto.datosBancarios.oficina"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
							<td>
								<label for="idDC">dc</label>
								<s:textfield autocomplete="off" size="2" maxlength="2"
									id="idDC" name="modeloDto.datosBancarios.dc"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
							<td>
								<label for="idNumeroCuentaPago">cuenta</label>
								<s:textfield autocomplete="off" size="10"
									maxlength="10" id="idNumeroCuentaPago"
									name="modeloDto.datosBancarios.numeroCuentaPago"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelAniadirFavoritos">Añadir Favoritos:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="modeloDto.datosBancarios.esFavorita" id="idFavoritoDatBancario"
						onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
		</table>
	</div>
	
</div>
<script type="text/javascript">
	habilitarPorTipoDatoBancario();
</script>