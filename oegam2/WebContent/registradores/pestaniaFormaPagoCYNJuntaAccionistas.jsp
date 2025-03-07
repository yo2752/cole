<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">

	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Datos pago</td>
			
			<s:if test="tramiteRegistro.idTramiteRegistro">
				<td  align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
				</td>
			</s:if>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			
			<td align="left" width="150px">
				<label for="labelSelecccionCuenta">Seleccione forma de pago:<span class="naranja">*</span></label>
			</td>
			<td align="left"  nowrap="nowrap" >
				<s:radio id="idFormaPago" cssStyle="display:inline; width: 25px;" labelposition="left"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListFormaPago()"
					listKey="valorEnum" listValue="nombreEnum" name="tramiteRegistro.formaPago"
					onchange="habilitarPorFormaDePago();">
				</s:radio>
			</td>
		</tr>
	</table>
	<div class="idCuentaBancaria" id = "divCuentaBancaria">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" width="150px">
					<label for="labelSelecccionCuenta">Seleccione Dato Bancario:<span class="naranja">*</span></label>
				</td>
				<td align="left"  nowrap="nowrap">
					<s:radio id="idTipoDatoBancario" cssStyle="display: inline;" labelposition="left"
						list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTiposCuentasBancarias()"
						listKey="valorEnum" listValue="nombreEnum" name="tramiteRegistro.datosBancarios.tipoDatoBancario"
						onchange="javascript:habilitarPorTipoDatoBancario()">
					</s:radio>
				</td>
			</tr>
			<tr id="idSeleccionDatosBancFavoritos">
				<td align="left" nowrap="nowrap">
					<label for="labelSelecccionFavoritos">Cuenta Bancaria Favorita:<span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idComboExistente"
						list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListaDatosBancariosFavoritos(tramiteRegistro)" 
				   		headerKey="" headerValue="Seleccione Datos Bancarios Favoritos" onchange="cargarDatosBancariosFavoritos();"
				   		onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						listKey="idDatosBancarios" listValue="descripcion" ></s:select>
					<s:hidden id="idDatosBancarios" name="tramiteRegistro.datosBancarios.idDatosBancarios"/>
				</td>
			</tr>
		</table>
		<div id="divDatosBancarios">
			<table  class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" width="15%"><label for="labelTitular">Nombre Titular:<span class="naranja">*</span></label></td>
					<td align="left" nowrap="nowrap" colspan = "2">
						<s:textfield name="tramiteRegistro.datosBancarios.nombreTitular" id="idNombreTitularDatBancarios" size="31" maxlength="100" 
							onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label for="cuentaProvinciaId" class="small">Provincia:<span class="naranja">*</span></label></td>
	               	<td>
	                	<s:select name="tramiteRegistro.datosBancarios.idProvincia" 
								list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
								headerKey="" headerValue="Seleccionar" listKey="idProvincia"
								listValue="nombre" id="cuentaSelectProvinciaId"
	 						    onchange="cargarListaMunicipios(this,'cuentaSelectMunicipioId','cuentaHiddenMunicipioId');"/>
	               </td>
	               <td align="left" nowrap="nowrap"><label for="cuentaMunicipio" class="small">Municipio:<span class="naranja">*</span></label></td>
	               <td>       
						<s:select id="cuentaSelectMunicipioId"
								list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegistro.datosBancarios.idProvincia)"
								onchange="javascript:seleccionMunicipio(this, 'cuenta');"
								name="tramiteRegistro.datosBancarios.idMunicipio"
								headerValue="Seleccione Municipio" 
								listKey="idMunicipio"
								listValue="nombre" />
					<s:hidden id="cuentaHiddenMunicipioId"/>
					<s:hidden id="cuentaHiddenMunicipality" />  
	               </td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumeroCuenta">N&uacute;mero de Cuenta:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<table>
							<tr>
								<td>
									<label for="idEntidad">IBAN</label>
									<s:textfield autocomplete="off" size="4" maxlength="4"
										id="idIban" name="tramiteRegistro.datosBancarios.iban"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"></s:textfield>
								</td>
								<td>
									<label for="idEntidad">Entidad<span class="naranja">*</span></label>
									<s:textfield autocomplete="off" size="4" maxlength="4"
										id="idEntidad" name="tramiteRegistro.datosBancarios.entidad"
										onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"></s:textfield>
								</td>
								<td>
									<label for="idOficina">Oficina<span class="naranja">*</span></label>
									<s:textfield autocomplete="off" size="4" maxlength="4"
										id="idOficina" name="tramiteRegistro.datosBancarios.oficina"
										onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"></s:textfield>
								</td>
								<td>
									<label for="idDC">DC<span class="naranja">*</span></label>
									<s:textfield autocomplete="off" size="2" maxlength="2"
										id="idDC" name="tramiteRegistro.datosBancarios.dc"
										onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"></s:textfield>
								</td>
								<td>
									<label for="idNumeroCuentaPago">Cuenta<span class="naranja">*</span></label>
									<s:textfield autocomplete="off" size="10"
										maxlength="10" id="idNumeroCuentaPago"
										name="tramiteRegistro.datosBancarios.numeroCuentaPago"
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
						<s:checkbox name="tramiteRegistro.datosBancarios.esFavorita" id="idFavoritoDatBancario"
							onblur="this.className='input';" onfocus="this.className='inputfocus';" onchange="habilitarAliasCuentaBancaria(); borrarCuentaSiFavorito();"/>	
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" id="labelDescripcionDatBanc">
						<label for="labelDescripcionDatBanc">Alias Cuenta Bancaria:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="tramiteRegistro.datosBancarios.descripcion" id="idDescripcionDatBancarios" size="25" maxlength="25" 
							onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	
	
	<div id = "divIdentificacionCorpme" >
	 	
		<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
			<tr>
				<td colspan = "6">
					<s:if test="tramiteRegistro.presentador.passwordCorpme == null || tramiteRegistro.presentador.usuarioCorpme == null">
					 	<label for="identificacionCorpme" style="color:red;font-size:12px">
							Para utilizar esta forma de pago debe introducir la identificación Corpme en el apartado Contratos
						</label>
					</s:if>
				</td>
			</tr>		
			<tr>
				<td align="left" nowrap="nowrap" width="20%" style="vertical-align: middle;">
					<label for="ncorpmeCliente">Código usuario abonado<span class="naranja">*</span>:</label>
				</td>
				<td>
					<s:textfield  value="%{tramiteRegistro.presentador.ncorpme}" id="codigoCorpme" size="10" maxlength="10" 
		       			readonly="true" />
				</td>
				<td align="right" nowrap="nowrap" style="vertical-align: middle;">
					<label for="ncorpmeClientePass">Usuario / Contraseña<span class="naranja">*</span>:</label>
				</td>
				<td align="right" nowrap="nowrap">
		   			<s:textfield  value="%{tramiteRegistro.presentador.usuarioCorpme}" id="usuarioCorpme" size="15" maxlength="25" 
		   				readonly="true" />
				</td>
				<td>/</td>
				<td>
		  			<s:password  value="%{tramiteRegistro.presentador.passwordCorpme}" id="passwordCorpme" size="15" maxlength="25" 
		      			 readonly="true" showPassword="true"/>
				</td>
			</tr>
			
		</table>
	</div>

</div>

  &nbsp; &nbsp;

  &nbsp; &nbsp;
	
 <div class="acciones center">	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<input type="button" value="Guardar" class="boton" id="botonGuardar" onclick="javascript:doPost('formData', 'guardarTramiteRegistroMd3.action\u0023formaPago');"/>
	</s:if>
  </div>

<script type="text/javascript">
	//habilitarPorFormaDePago();
	//habilitarAliasCuentaBancaria()
	
	$("#idFormaPago3").prop("checked", true);
	$("#divCuentaBancaria").hide();
	$("#divIdentificacionCorpme").show();
	limpiarCamposCuentaBancaria();
	$("#idFormaPago1").prop("disabled", true);
	$("#idFormaPago2").prop("disabled", true);
	
			 
</script>
