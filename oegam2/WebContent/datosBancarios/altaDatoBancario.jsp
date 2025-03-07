<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/datosBancarios/datosBancariosFunction.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Datos Bancarios</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().noEsEstadoHabilitado(datosBancarios)}"></s:set>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<s:hidden id="esModificado" name="esModificado"/>
		<div id="busqueda">
			<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Datos Bancarios</td>
					<td align="right">
						<s:if test="%{datosBancarios.idDatosBancarios != null}">
							<img title="Ver evolución" onclick="consultaEvolucionDatosBancarios('<s:property value="datosBancarios.idDatosBancarios"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
						</s:if>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && datosBancarios.idDatosBancarios != null}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelIdDir">Id Dato Bancario:</label>
						</td>
						<td>
							<s:textfield name="datosBancarios.idDatosBancarios" id="idDatosBancario" size="15" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" disabled="true"/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden id="idDatosBancario" name="datosBancarios.idDatosBancarios"/>
				</s:else>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNifPagador">Nif Pagador<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="datosBancarios.nifPagador" id="idNifPagadorDatBancarios" size="10" maxlength="10"
							onblur="this.className='input';" onfocus="this.className='inputfocus';" disabled="flagDisabled"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelDescripcionDatBanc">Descripcion Datos Bancarios<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="datosBancarios.descripcion" id="idDescripcionDatBancarios" size="25" maxlength="25"
							onblur="this.className='input';" onfocus="this.className='inputfocus';" disabled="flagDisabled" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato<span class="naranja">*</span>:</label>
					</td>
					<td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px"
								name="datosBancarios.contrato.idContrato" disabled="flagDisabled">
							</s:select>
						</s:if>
						<s:else>
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" headerKey=""
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:220px"
								name="datosBancarios.contrato.idContrato" disabled="true"></s:select>
						</s:else>
					</td>
					<td align="left" nowrap="nowrap">
							<label for="labelFormaPago">Estado<span class="naranja">*</span>:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getComboEstados()"
							onblur="this.className='input2';" 
							onchange="gestionarEstadoDatoBancario();"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado"
							name="datosBancarios.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
							<label for="labelFormaPago">Forma de Pago<span class="naranja">*</span>:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getListaFormaPago()"
							onblur="this.className='input2';"
							onchange="verFormaPago();gestionarCamposFormaPago();"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Forma Pago" 
							name="datosBancarios.formaPago" listKey="codigo" listValue="descripcion" id="idFormaPago" disabled="flagDisabled"/>
					</td>
				</tr>
			</table>

			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<div id="divContenidoIbanLabel" style="display: none;">
							<label for="idcuentaIban">Cuenta IBAN<span class="naranja">*</span></label>
						</div>
						<div id="divContenidoTarjetaLabel" style="display: none;">
							<label for="idNumTarjeta">Tarjeta<span class="naranja">*</span></label>
						</div>

						<div id="divContenidoCuentaLabel" style="display: none;">
							<label for="idNumTarjeta">N&uacute;mero Cuenta<span class="naranja">*</span></label>
						</div>
					</td>
					<td align="left">
						<div id="divContenidoIban" style="display: none;">
							<table>
								<tr>
									<td>
										<s:textfield autocomplete="off" size="24"
											maxlength="24" id="idcuentaIban"
											name="datosBancarios.iban">
										</s:textfield>
									</td>
									<td>
										<img id="idEliminarIban" src="img/eliminar.png" alt="Limpiar" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
											onclick="javascript:limpiarIban();" title="Limpiar"/>
									</td>
								</tr>
							</table>
						</div>
						<div id="divContenidoTarjeta" style="display: none;">
							<table>
								<tr>
									<td colspan="7" align="left">
										<label for="idTarjetaEntidad">entidad emisora</label>
										<s:select name="datosBancarios.tarjetaEntidad" id="idTarjetaEntidad"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().getListaEntidadesEmisoras()"
											headerKey="" emptyOption="true"
											listKey="codigo" listValue="descripcion" />
									</td>
								</tr>
								<tr>
									<td>
										<label for="idTarjeta1">n&uacute;m.</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idTarjeta1" name="datosBancarios.tarjetaNum1"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjeta2">&nbsp;</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idTarjeta2" name="datosBancarios.tarjetaNum2"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjeta3">&nbsp;</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idTarjeta3" name="datosBancarios.tarjetaNum3"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjeta4">&nbsp;</label>
										<s:textfield autocomplete="off" size="4"
											maxlength="4" id="idTarjeta4"
											name="datosBancarios.tarjetaNum4"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idCCV">ccv</label>
										<s:textfield autocomplete="off" size="1" maxlength="3"
											id="idCCV" name="datosBancarios.tarjetaCcv"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjetaMes">mes cad.</label>
										<s:select name="datosBancarios.tarjetaMes" id="idTarjetaMes"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											list="#{'01':'Enero','02':'Febrero','03':'Marzo','04':'Abril','05':'Mayo','06':'Junio',
											'07':'Julio','08':'Agosto','09':'Septiembre','10':'Octubre','11':'Noviembre','12':'Diciembre' }"
											headerKey="" emptyOption="true" />
									</td>
									<td>
										<label for="idTarjetaAnio">a&ntilde;o cad.</label>
										<s:select name="datosBancarios.tarjetaAnio" id="idTarjetaAnio"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											list="@org.gestoresmadrid.oegam2comun.datosBancarios.utiles.UtilesDatosBancarios@getInstance().listaAnios()"
											headerKey="" emptyOption="true" />
									</td>
									<td>
										<img id="idEliminarTarjeta" src="img/eliminar.png" alt="Limpiar" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
											onclick="javascript:limpiarTarjeta();" title="Limpiar" />
									</td>
								</tr>
							</table>
						</div>
						<div id="divContenidoCuenta" style="display: none;">
							<table>
								<tr>
									<td>
										<label for="idEntidad">entidad</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idEntidad" name="datosBancarios.entidad"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idOficina">oficina</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idOficina" name="datosBancarios.oficina"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idDC">dc</label>
										<s:textfield autocomplete="off" size="2" maxlength="2"
											id="idDC" name="datosBancarios.dc"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idNumeroCuentaPago">cuenta</label>
										<s:textfield autocomplete="off" size="10"
											maxlength="10" id="idNumeroCuentaPago"
											name="datosBancarios.numeroCuentaPago"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<img id="idEliminarNumCuenta" src="img/eliminar.png" alt="Limpiar" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
											onclick="javascript:limpiarNumCuenta();" title="Limpiar"/>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<div id="bloqueLoadingAltaDtBancarios" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<input type="button" class="boton" name="bGuardar" id="bGuardar" onclick="javascript:guardarDatoBancario();" value="Guardar"/>
		</div>
	</s:form>
	<div id="divEmergenteEvolucionDatosBancarios" style="display: none; background: #f4f4f4;"></div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		iniciarVentanaDatosBancarios();
	});
</script>
<s:if test="hasActionErrors()">
	<script type="text/javascript">
		$( document ).ready(function() {
			habilitarCampos(); 
		});
	</script>
</s:if>