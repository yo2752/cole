
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:form method="post" id="formData" name="formData">
	<s:hidden name="resumenCompraBean.idCompra" />
	<s:hidden name="resumenCompraBean.idContrato" />
	<s:hidden name="resumenCompraBean.formaPago" />
	<s:hidden name="resumenCompraBean.datosBancarios" />
	<s:hidden name="resumenCompraBean.importeTotalTasas" />
	<s:hidden name="resumenCompraBean.estado" />
	<s:hidden name="resumenCompraBean.fechaAlta" />
	<div class="nav" style="margin-left: 0.3em">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Compra de tasas de DGT</span></td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	<s:if test="%{resumenCompraBean.idCompra != null}">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos generales</td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td  align="left" nowrap="nowrap">
					<label for="refPropia">Referencia propia:</label>
				</td>
				<td align="left">
					<s:textfield size="22" maxlength="50" id="refPropia" name="resumenCompraBean.refPropia" />
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" >
					<label for="numJustificante791">N&uacute;m. justificante 791:</label>
				</td>
				<td align="left">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() &&
					(resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@INICIADO.codigo or
						resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@FINALIZADO_ERROR.codigo)}">
						<s:textfield size="22" maxlength="13" name="resumenCompraBean.numJustificante791"  id="numJustificante791" />
					</s:if>
					<s:else>
						<s:label id="numJustificante791" value="%{resumenCompraBean.numJustificante791}" />
					</s:else>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="nrc">NRC:</label>
				</td>
				<td align="left">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() &&
					(resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@INICIADO.codigo or
						resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@FINALIZADO_ERROR.codigo)}">
						<s:textfield size="22" maxlength="22" name="resumenCompraBean.nrc"  id="nrc" />
					</s:if>
					<s:else>
						<s:label id="nrc" value="%{resumenCompraBean.nrc}" />
					</s:else>
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" >
					<label for="nAutoliquidacion">N&uacute;m. autoliquidaci&oacute;n:</label>
				</td>
				<td align="left">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() &&
					(resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@INICIADO.codigo or
						resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@FINALIZADO_ERROR.codigo)}">
						<s:textfield size="22" maxlength="12" name="resumenCompraBean.nAutoliquidacion"  id="nAutoliquidacion" />
					</s:if>
					<s:else>
						<s:label id="nAutoliquidacion" value="%{resumenCompraBean.nAutoliquidacion}" />
					</s:else>
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" >
					<label for="csv">CSV:</label>
				</td>
				<td align="left">
					<s:label id="csv" value="%{resumenCompraBean.csv}" />
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" >
					<label for="estado">Estado:</label>
				</td>
				<td align="left">
					<s:label id="estado" value="%{@org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@convertir(resumenCompraBean.estado)}" />
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" >
					<label for="respuesta">Respuesta:</label>
				</td>
				<td align="left">
					<s:label id="respuesta" value="%{resumenCompraBean.respuesta}" />
				</td>
			</tr>
			<s:if test="%{resumenCompraBean.fechaCompra != null}">
				<tr>
					<td  align="left" nowrap="nowrap" >
						<label for="idFecCompra">Fecha de compra:</label>
					</td>
					<td align="left">
						<s:date var="valorFechaCompra" name="resumenCompraBean.fechaCompra" format="dd/MM/yyyy" />
						<s:label id="idFecCompra" value="%{valorFechaCompra}" />
					</td>
				</tr>
			</s:if>
			<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() &&
			(resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@INICIADO.codigo or
				resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@FINALIZADO_ERROR.codigo)}">
				<tr>
					<td  align="left" nowrap="nowrap" >
						<label for="idFecCompra">Fecha de compra:</label>
					</td>
					<td align="left">
						<s:date var="valorFechaCompra" name="resumenCompraBean.fechaCompra" format="dd/MM/yyyy" />
						<s:textfield size="22"  name="resumenCompraBean.fechaCompra" id="idFecCompra"/>					
					</td>
				</tr>
			</s:elseif>	
		</table>
	</s:if>
	<s:else>
		<table class="tablaformbasica">
			<tr>
				<td  align="left" nowrap="nowrap" width="15%">
					<label for="refPropia">Referencia propia:</label>
				</td>
				<td align="left">
					<s:textfield size="22" maxlength="50" id="refPropia" name="resumenCompraBean.refPropia" />
				</td>
			</tr>
		</table>
	</s:else>
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Desglose de tasas</td>
		</tr>
	</table>
	<div class="divScroll">
		<display:table name="resumenCompraBean.listaResumenCompraBean"
			excludedParams="*" requestURI="" class="tablacoin"
			uid="tablaResumenCompraBean" summary="Resumen de compra de tasas"
			cellspacing="0" cellpadding="0">

			<display:column property="descripcion" title="Tasa" sortable="false" style="width:50%; text-align: justify; padding:4px;" />

			<display:column property="codigoTasa" title="Tipo tasa" sortable="false" style="width:3%" />

			<display:column property="importe" title="Precio" sortable="false" style="width:3%" />

			<display:column property="cantidad" title="Cantidad" sortable="false" style="width:0.2%" />

			<display:column title="Subtotal" sortable="false" style="width:0.2%">
				<s:property value="#attr.tablaResumenCompraBean.subTotal" />
				<input type="hidden"
						name="resumenCompraBean.listaResumenCompraBean[<s:property value="#attr.tablaResumenCompraBean_rowNum"/> - 1].grupo"
						value="<s:property value="#attr.tablaResumenCompraBean.grupo"/>" />
				<input type="hidden"
						name="resumenCompraBean.listaResumenCompraBean[<s:property value="#attr.tablaResumenCompraBean_rowNum"/> - 1].descripcion"
						value="<s:property value="#attr.tablaResumenCompraBean.descripcion"/>" />
				<input type="hidden"
						name="resumenCompraBean.listaResumenCompraBean[<s:property value="#attr.tablaResumenCompraBean_rowNum"/> - 1].codigoTasa"
						value="<s:property value="#attr.tablaResumenCompraBean.codigoTasa"/>" />
				<input type="hidden"
						name="resumenCompraBean.listaResumenCompraBean[<s:property value="#attr.tablaResumenCompraBean_rowNum"/> - 1].cantidad"
						value="<s:property value="#attr.tablaResumenCompraBean.cantidad"/>" />
				<input type="hidden"
						name="resumenCompraBean.listaResumenCompraBean[<s:property value="#attr.tablaResumenCompraBean_rowNum"/> - 1].subTotal"
						value="<s:property value="#attr.tablaResumenCompraBean.subTotal"/>" />
			</display:column>
		</display:table>
	</div>
	<div>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label>Importe total</label>
				</td>
				<td align="left">
					<s:property value="resumenCompraBean.importeTotalTasas" /> &euro;
				</td>
			</tr>
		</table>
		<s:if test="%{resumenCompraBean.formaPago == @org.gestoresmadrid.core.tasas.model.enumeration.FormaPago@CCC.getCodigo()}">
			<s:hidden name="resumenCompraBean.entidad" />
			<s:hidden name="resumenCompraBean.oficina" />
			<s:hidden name="resumenCompraBean.dc" />
			<s:hidden name="resumenCompraBean.numeroCuentaPago" />
			<table class="subtitulo" cellSpacing="0" cellspacing="0">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Pago mediante cargo en cuenta</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label>N&uacute;mero de cuenta</label>
					</td>
					<td align="left">
						<s:property value="resumenCompraBean.entidad" />&nbsp;
						<s:property value="resumenCompraBean.oficina" />&nbsp;
						<s:property value="resumenCompraBean.dc" />&nbsp;
						<s:property value="resumenCompraBean.numeroCuentaPago" />
					</td>
				</tr>
			</table>
		</s:if>
		<s:elseif test="%{resumenCompraBean.formaPago == @org.gestoresmadrid.core.tasas.model.enumeration.FormaPago@TARJETA.getCodigo()}">
			<s:hidden name="resumenCompraBean.tarjetaNum1" />
			<s:hidden name="resumenCompraBean.tarjetaNum2" />
			<s:hidden name="resumenCompraBean.tarjetaNum3" />
			<s:hidden name="resumenCompraBean.tarjetaNum4" />
			<s:hidden name="resumenCompraBean.tarjetaCcv" />
			<s:hidden name="resumenCompraBean.tarjetaMes" />
			<s:hidden name="resumenCompraBean.tarjetaAnio" />
			<s:hidden name="resumenCompraBean.tarjetaEntidad" />
			<table class="subtitulo" cellSpacing="0" cellspacing="0">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Pago mediante tarjeta</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label>N&uacute;mero de tarjeta</label>
					</td>
					<td align="left">
						<s:property value="resumenCompraBean.tarjetaNum1" />&nbsp;
						<s:property value="resumenCompraBean.tarjetaNum2" />&nbsp;
						<s:property value="resumenCompraBean.tarjetaNum3" />&nbsp;
						<s:property value="resumenCompraBean.tarjetaNum4" />
					</td>
					<td align="left">
						<label for="idNumTarjeta1">CCV</label>
					</td>
					<td align="left">
						<s:property value="resumenCompraBean.tarjetaCcv" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="idNumTarjeta1">Fecha de vencimiento</label>
					</td>
					<td align="left">
						<s:property value="resumenCompraBean.tarjetaMes" /> / 
						<s:property value="resumenCompraBean.tarjetaAnio" />
					</td>
				</tr>
			</table>
		</s:elseif>
		<s:elseif test="%{resumenCompraBean.formaPago == @org.gestoresmadrid.core.tasas.model.enumeration.FormaPago@IBAN.getCodigo()}">
			<s:hidden name="resumenCompraBean.iban" />
			<table class="subtitulo" cellSpacing="0" cellspacing="0">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Pago mediante cargo en cuenta</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label>Cuenta IBAN</label>
					</td>
					<td align="left">
						<s:property value="resumenCompraBean.iban" />
					</td>
				</tr>
			</table>
		</s:elseif>
	</div>
	<div class="acciones center">
		<s:if test="%{resumenCompraBean.idCompra eq null}">
			<input type="button" class="boton" name="bModificar" id="idModificar" value="Modificar" onClick="return modificar();" onKeyPress="this.onClick" />
			<input type="button" class="boton" name="bGuardarSinComprar" id="idGuardarSinComprar" value="Guardar sin comprar" onClick="guardarSinComprar();" onKeyPress="this.onClick" />
			<input type="button" class="boton" name="bConfirmarCompra" id="idConfirmarCompra" value="Realizar compra" onClick="return confirmarCompra();" onKeyPress="this.onClick" />
		</s:if>
		
		<s:elseif test="resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@INICIADO.codigo">
			<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver al listado" onClick="return volver();" onKeyPress="this.onClick" />
			<input type="button" class="boton" name="bModificar" id="idModificar" value="Modificar" onClick="return modificar();" onKeyPress="this.onClick" />
			<input type="button" class="boton" name="bGuardarSinComprar" id="idGuardarSinComprar" value="Guardar sin comprar" onClick="guardarSinComprar();" onKeyPress="this.onClick" />
			<input type="button" class="boton" name="bConfirmarCompra" id="idConfirmarCompra" value="Realizar compra" onClick="return confirmarCompra();" onKeyPress="this.onClick" />
		</s:elseif>
		<s:else>
			<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver al listado" onClick="return volver();" onKeyPress="this.onClick" />
			<s:if test="resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@FINALIZADO_ERROR.codigo">
				<input type="button" class="boton" name="bGuardarSinComprar" id="idGuardarSinComprar" value="Guardar sin comprar" onClick="guardarSinComprar();" onKeyPress="this.onClick" />
				<input type="button" class="boton" name="bConfirmarCompra" id="idConfirmarCompra" value="Tramitar de nuevo" onClick="return confirmarCompra();" onKeyPress="this.onClick" />
			</s:if>
			<s:elseif  test="resumenCompraBean.estado eq @org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra@FINALIZADO_OK.codigo">
				<input type="button" class="boton" name="bGuardarSinComprar" id="idGuardarSinComprar" value="Guardar" onClick="guardarSinComprar();" onKeyPress="this.onClick" />
				<input type="button" value="Descargar fichero tasas" class="botonMasGrande" onkeypress="this.onClick" onclick="return invokeDescargaFicheroTasas();"/>
				<input type="button" value="Descargar justificante pago" class="botonMasGrande" onkeypress="this.onClick" onclick="return invokeDescargaJustificantePago();"/>
			</s:elseif>
		</s:else>
	</div>
</s:form>
<script>
	$( document ).ready(function() {
		$.datepicker.setDefaults($.datepicker.regional["es"]);
		$("#idFecCompra").datepicker({
			monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
			'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
			monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
			'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
			dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
			dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié;', 'Juv', 'Vie', 'Sáb'],
			dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
			weekHeader: 'Sm',
			firstDay: 1,
			isRTL: false,
			showMonthAfterYear: false,
			dateFormat: 'dd/mm/yy'
				});
	});
	function confirmarCompra() {
		if (confirm("¿Está seguro de que desea realizar la compra de tasas por valor de <s:property value="resumenCompraBean.importeTotalTasas" /> euros?")) {
			doPost('formData', 'confirmarCompra${action}.action');
		}
	}

	function volver() {
		doPost('formData', 'volver${action}.action');
	}

	function modificar() {
		doPost('formData', 'modificar${action}.action');
	}

	function invokeDescargaFicheroTasas() {
		doPost('formData', 'descargarFicheroDetalle${action}.action', true);
	}

	function invokeDescargaJustificantePago() {
		doPost('formData', 'justificantePagoDetalle${action}.action', true);
	}

	function guardarSinComprar(){
		doPost('formData', 'guardarCompraSinComprar${action}.action');
	}

</script>