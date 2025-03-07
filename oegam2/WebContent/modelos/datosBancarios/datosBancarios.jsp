<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<table class="tablaformbasica">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNifPagador">NIF Pagador:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.datosBancarios.nifPagador" id="idNifPagadorDatBancarios" size="10" maxlength="10"
					onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelDescripcionDatBanc">Descripcion Datos Bancarios:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="modeloDto.datosBancarios.descripcion" id="idDescripcionDatBancarios" size="25" maxlength="25"
					onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumeroCuenta">Número de Cuenta:</label>
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
<script type="text/javascript">
	habilitarPorTipoDatoBancario();
</script>