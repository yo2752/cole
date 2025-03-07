<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/trafico/vehiculosPrematriculados.js" type="text/javascript"></script>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData" action="consultarConsultaTarjeta.action">

	<div id="busqueda">
	
	<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align:left;">Alta Consulta de Vehículos Prematriculados</td>
			</tr>
		</table>

		<table class="tablaformbasica" border="0">

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;" width="5%" align="right">
					<label for="textFieldNive">Nive:</label>
				</td>

				<td align="left" width="50px"><s:textfield name="vehiculoPrematriculado.nive"
						id="textFieldNive" size="40" maxlength="32"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="center">
					<label> Datos Técnicos </label>
					<input type="checkbox" id="checkDatosTecnicos" name="vehiculoPrematriculado.datosTecnicos" value="true" />
				</td>
							
			</tr>
			<tr>
			<td nowrap="nowrap" style="vertical-align: middle;" width="5%" align="right">
					<label for="textFieldBastidor">Bastidor:</label>
				</td>
				<td align="left"><s:textfield name="vehiculoPrematriculado.bastidor"
						id="textFieldBastidor" size="40" maxlength="17"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="center">
					<label>Ficha técnica</label>
					<input type="checkbox" id="checkFichaTecnica" name="vehiculoPrematriculado.fichaTecnica" value="true"/>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>
		</table>
		
		<table class="acciones">
			<tr>
				<td align="right"><input type="submit" class="boton" name="bBuscar"
					id="botonConsultar" value="Consultar"
					onClick="return consultarTarjetaEitv();"
					onKeyPress="this.onClick" />
				</td>
				<td align="left"><input type="button" class="boton" name="bBuscar"
					id="botonLimpiar" value="Limpiar"
					onClick="return limpiarFormularioConsultaTarjetaEitv();"
					onKeyPress="this.onClick" />
				</td>
			</tr>
		</table>



	</div>

	<div id="bloqueLoadingVehiculoPrematriculado"
		style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
		<%@include file="../../includes/bloqueLoading.jspf"%>
	</div>

</s:form>


