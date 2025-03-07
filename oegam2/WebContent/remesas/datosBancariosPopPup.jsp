<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>
<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Datos Bancarios</span>
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
					listKey="valorEnum" listValue="nombreEnum" name="datosBancarios.tipoDatoBancario"
					onchange="javascript:habilitarPorTipoDatoBancarioConsulta()">
				</s:radio>
			</td>
		</tr>
		<tr id="idSeleccionDatosBancFavoritos">
			<td align="left" nowrap="nowrap">
				<label for="labelSelecccionFavoritos">Cuenta Bancaria Favorita:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idComboExistente"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaDatosBancariosFavoritosDesdeConsulta()" 
			   		headerKey="" headerValue="Seleccione Datos Bancarios Favoritos" onchange="cargarDatosBancariosFavoritosPopPup();"
			   		onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					listKey="idDatosBancarios" listValue="descripcion" ></s:select>
				<s:hidden id="idDatosBancarios" name="datosBancarios.idDatosBancarios"/>
			</td>
		</tr>
	</table>
	<div id="idDivDatosBancariosPopUp"> 
		<table  class="tablaformbasica" id="tablaDatosBancarios">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNifPagador">Nif Pagador:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosBancarios.nifPagador" id="idNifPagadorDatBancarios" size="10" maxlength="10" 
						onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelDescripcionDatBanc">Descripcion Datos Bancarios:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosBancarios.descripcion" id="idDescripcionDatBancarios" size="25" maxlength="25" 
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
									id="idEntidad" name="datosBancarios.entidad"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
							<td>
								<label for="idOficina">oficina</label>
								<s:textfield autocomplete="off" size="4" maxlength="4"
									id="idOficina" name="datosBancarios.oficina"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
							<td>
								<label for="idDC">dc</label>
								<s:textfield autocomplete="off" size="2" maxlength="2"
									id="idDC" name="datosBancarios.dc"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
							<td>
								<label for="idNumeroCuentaPago">cuenta</label>
								<s:textfield autocomplete="off" size="10"
									maxlength="10" id="idNumeroCuentaPago"
									name="datosBancarios.numeroCuentaPago"
									onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"></s:textfield>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	habilitarPorTipoDatoBancarioConsulta();
</script>
