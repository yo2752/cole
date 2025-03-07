
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="formularios">

<s:set var="totalTasas" value="3"></s:set>
<s:hidden id="idTotalTasasCirculacion" name="totalTasasCirculacion"/>
<s:hidden id="idTotalTasasConduccion" name="totalTasasConduccion"/>
<s:hidden id="idTotalTasasFormacion" name="totalTasasFormacion"/>
<s:hidden id="idTotalTasasTarifas" name="totalTasasTarifas"/>


<%-- <script src="js/tasas/tasas.js" type="text/javascript"></script> --%>
<%@include file="../../includes/erroresMasMensajes.jspf" %>

<s:form method="post" id="formData" name="formData" onload="calcularSubtotales()">

	<s:hidden name="resumenCompraBean.idCompra" />
	<s:hidden name="resumenCompraBean.idContrato" />
	<s:hidden name="resumenCompraBean.refPropia" />
	<s:hidden name="resumenCompraBean.estado" />
	<s:hidden name="resumenCompraBean.fechaAlta" />
	<s:hidden name="resumenCompraBean.fechaCompra" />
	<s:hidden name="resumenCompraBean.numJustificante791" />
	<s:hidden name="resumenCompraBean.nrc" />
	<s:hidden name="resumenCompraBean.nAutoliquidacion" />

	<div class="nav" style="margin-left: 0.3em">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Compra de tasas de DGT</span>
				</td>
			</tr>
		</table>
	</div>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Grupo I.- Permisos de circulación</td>
		</tr>
	</table>
	<div class="divScroll">
	
			<display:table name="resumenCompraBean.listaCompraTasasCirculacion"
				excludedParams="*" requestURI=""
				class="tablacoin" uid="tablaCompraTasasCirculacion"
				summary="Listado de tasas para permisos de circulacion" cellspacing="0" cellpadding="0" >

				<display:column property="descripcion" title="Tasa"
					sortable="false" 
 					style="width:50%; text-align: justify; padding:4px;"/> 

				<display:column property="codigoTasa" title="Tipo tasa" 
					sortable="false" 
					style="width:3%" />

				<display:column property="importe" title="Precio"
					sortable="false" 
					style="width:3%" />

				<display:column style="width:0.2%" title="Cantidad">
					<input type="text" autocomplete="off" name="resumenCompraBean.listaCompraTasasCirculacion[<s:property value="#attr.tablaCompraTasasCirculacion_rowNum"/> - 1].cantidad"
						id="idCantidadCirulacion_<s:property value="#attr.tablaCompraTasasCirculacion_rowNum"/>" 
						onkeypress="return validarNumerosEnteros(event)" onChange="calcularSubTotal('idCantidadCirulacion_<s:property value="#attr.tablaCompraTasasCirculacion_rowNum"/>', 
							<s:property value="#attr.tablaCompraTasasCirculacion.importe"/>,
							'subtotalCirculacion_<s:property value="#attr.tablaCompraTasasCirculacion.codigoTasa"/>')"
						value= <s:property value="#attr.tablaCompraTasasCirculacion.cantidad"/>
						size="9" maxlength="9" />
				 </display:column>	
				 
				 <display:column style="width:0.2%" title="Subtotal">
					<input disabled="disabled" readonly="readonly" type="text" id="subtotalCirculacion_<s:property value="#attr.tablaCompraTasasCirculacion.codigoTasa"/>"
						name="resumenCompraBean.listaCompraTasasCirculacion[<s:property value="#attr.tablaCompraTasasCirculacion_rowNum"/> - 1].subTotal" 
						value=<s:property value="#attr.tablaCompraTasasCirculacion.subTotal"/>
						size="9">
	   			 </display:column>
			</display:table>
			
			<s:iterator value="resumenCompraBean.listaCompraTasasCirculacion" status="row">
			    <s:hidden name="resumenCompraBean.listaCompraTasasCirculacion[%{#row.index}].descripcion" />
			    <s:hidden name="resumenCompraBean.listaCompraTasasCirculacion[%{#row.index}].codigoTasa" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasCirculacion[%{#row.index}].importe" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasCirculacion[%{#row.index}].subTotal" />
			</s:iterator>
			
			
		</div>
		
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Grupo II.- Permisos para conducción </td>
		</tr>
	</table>
	
	<div class="divScroll">
	
			<display:table name="resumenCompraBean.listaCompraTasasConduccion"
				excludedParams="*" requestURI=""
				class="tablacoin" uid="tablaCompraTasasConduccion"
				summary="Listado de tasas para permisos de conduccion" cellspacing="0" cellpadding="0" >

				<display:column property="descripcion" title="Tasa"
					sortable="false" 
 					style="width:50%; text-align: justify; padding:4px;" /> 

				<display:column property="codigoTasa" title="Tipo tasa"
					sortable="false"
					style="width:3%" />

				<display:column property="importe" title="Precio"
					sortable="false"
					style="width:3%" />

				<display:column style="width:0.2%" title="Cantidad">
					<input type="text" autocomplete="off" name="resumenCompraBean.listaCompraTasasConduccion[<s:property value="#attr.tablaCompraTasasConduccion_rowNum"/> - 1].cantidad"
						id="idCantidadConduccion_<s:property value="#attr.tablaCompraTasasConduccion_rowNum"/>" 
						onkeypress="return validarNumerosEnteros(event)" onChange="calcularSubTotal('idCantidadConduccion_<s:property value="#attr.tablaCompraTasasConduccion_rowNum"/>', 
							<s:property value="#attr.tablaCompraTasasConduccion.importe"/>,
							'subtotalConduccion_<s:property value="#attr.tablaCompraTasasConduccion.codigoTasa"/>')"
						value= <s:property value="#attr.tablaCompraTasasConduccion.cantidad"/>
						size="9" maxlength="9"  />
	   			 </display:column>
	   			 
				 <display:column style="width:0.2%" title="Subtotal">
					<input  disabled="disabled" readonly="readonly" type="text" id="subtotalConduccion_<s:property value="#attr.tablaCompraTasasConduccion.codigoTasa"/>" 
						name="resumenCompraBean.listaCompraTasasConduccion[<s:property value="#attr.tablaCompraTasasConduccion_rowNum"/> - 1].subTotal"
						value=<s:property value="#attr.tablaCompraTasasConduccion.subTotal"/>
						size="9" >
	   			 </display:column>

				<input type="hidden" name="resultadosPorPagina" />
			</display:table>
		
			<s:iterator value="resumenCompraBean.listaCompraTasasConduccion" status="row">
			    <s:hidden name="resumenCompraBean.listaCompraTasasConduccion[%{#row.index}].descripcion" />
			    <s:hidden name="resumenCompraBean.listaCompraTasasConduccion[%{#row.index}].codigoTasa" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasConduccion[%{#row.index}].importe" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasConduccion[%{#row.index}].subTotal" />
			</s:iterator>
			
		</div>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Grupo III.- Centros de formación y reconocimiento de conductores</td>
		</tr>
	</table>
	
	<div class="divScroll">
	
			<display:table name="resumenCompraBean.listaCompraTasasFormacionReconocimiento"
				excludedParams="*" requestURI=""
				class="tablacoin" uid="tablaCompraTasasFormacionReconocimiento"
				summary="Listado de tasas para centros de formacion y reconocimiento de conductores" cellspacing="0" cellpadding="0" >
 
				<display:column property="descripcion" title="Tasa"
					sortable="false"
 					style="width:50%; text-align: justify; padding:4px;" /> 

				<display:column property="codigoTasa" title="Tipo tasa"
					sortable="false"
					style="width:3%" />

				<display:column property="importe" title="Precio"
					sortable="false"
					style="width:3%" />

				<display:column style="width:0.2%" title="Cantidad">
					<input type="text" autocomplete="off" name="resumenCompraBean.listaCompraTasasFormacionReconocimiento[<s:property value="#attr.tablaCompraTasasFormacionReconocimiento_rowNum"/> - 1].cantidad"
						id="idCantidadFormacion_<s:property value="#attr.tablaCompraTasasFormacionReconocimiento_rowNum"/>" 
						onkeypress="return validarNumerosEnteros(event)" onChange="calcularSubTotal('idCantidadFormacion_<s:property value="#attr.tablaCompraTasasFormacionReconocimiento_rowNum"/>', 
							<s:property value="#attr.tablaCompraTasasFormacionReconocimiento.importe"/>,
							'subtotalReconocimiento_<s:property value="#attr.tablaCompraTasasFormacionReconocimiento.codigoTasa"/>')"
						value= <s:property value="#attr.tablaCompraTasasFormacionReconocimiento.cantidad"/>
						size="9" maxlength="9"  />
	   			 </display:column>
				
				 <display:column style="width:0.2%" title="Subtotal">
					<input  disabled="disabled" readonly="readonly" type="text" id="subtotalReconocimiento_<s:property value="#attr.tablaCompraTasasFormacionReconocimiento.codigoTasa"/>" 
						name="resumenCompraBean.listaCompraTasasFormacionReconocimiento[<s:property value="#attr.tablaCompraTasasFormacionReconocimiento_rowNum"/> - 1].subTotal"
						value=<s:property value="#attr.tablaCompraTasasFormacionReconocimiento.subTotal"/>
						size="9">
	   			 </display:column>
 
				<input type="hidden" name="resultadosPorPagina" />
				
			</display:table>
		
			<s:iterator value="resumenCompraBean.listaCompraTasasFormacionReconocimiento" status="row">
			    <s:hidden name="resumenCompraBean.listaCompraTasasFormacionReconocimiento[%{#row.index}].descripcion" />
			    <s:hidden name="resumenCompraBean.listaCompraTasasFormacionReconocimiento[%{#row.index}].codigoTasa" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasFormacionReconocimiento[%{#row.index}].importe" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasFormacionReconocimiento[%{#row.index}].subTotal" />
			</s:iterator>
			
		</div>
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Grupo IV.- Otras tarifas</td>
		</tr>
	</table>
	<div class="divScroll">
	
			<display:table name="resumenCompraBean.listaCompraTasasOtrasTarifas"
				excludedParams="*" requestURI=""
				class="tablacoin" uid="tablaCompraTasasOtrasTarifas"
				summary="Listado de tasas con otras tarifas" cellspacing="0" cellpadding="0" >

				<display:column property="descripcion" title="Tasa"
					sortable="false" 
 					style="width:50%; text-align: justify; padding:4px;" /> 

				<display:column property="codigoTasa" title="Tipo tasa"
					sortable="false"
					style="width:3%" />

				<display:column property="importe" title="Precio"
					sortable="false"
					style="width:3%" />

				<display:column style="width:0.2%" title="Cantidad">
					<input autocomplete="off" type="text" name="resumenCompraBean.listaCompraTasasOtrasTarifas[<s:property value="#attr.tablaCompraTasasOtrasTarifas_rowNum"/> - 1].cantidad" 
					id="idCantidadTarifas_<s:property value="#attr.tablaCompraTasasOtrasTarifas_rowNum"/>"
					onkeypress="return validarNumerosEnteros(event)" onChange="calcularSubTotal('idCantidadTarifas_<s:property value="#attr.tablaCompraTasasOtrasTarifas_rowNum"/>', 
							<s:property value="#attr.tablaCompraTasasOtrasTarifas.importe"/>,
							'subtotalTarifas_<s:property value="#attr.tablaCompraTasasOtrasTarifas.codigoTasa"/>')"
					value= <s:property value="#attr.tablaCompraTasasOtrasTarifas.cantidad"/>
						size="9" maxlength="9"  />
	   			 </display:column>
				
				 <display:column style="width:0.2%" title="Subtotal">
					<input  disabled="disabled" readonly="readonly" type="text" id="subtotalTarifas_<s:property value="#attr.tablaCompraTasasOtrasTarifas.codigoTasa"/>" 
						name="resumenCompraBean.listaCompraTasasOtrasTarifas[<s:property value="#attr.tablaCompraTasasOtrasTarifas_rowNum"/> - 1].subTotal"
						value=<s:property value="#attr.tablaCompraTasasOtrasTarifas.subTotal"/>
						size="9">
	   			 </display:column>
	   			 
				<input type="hidden" name="resultadosPorPagina" />
			</display:table>
		
			<s:iterator value="resumenCompraBean.listaCompraTasasOtrasTarifas" status="row">
				<s:hidden name="resumenCompraBean.listaCompraTasasOtrasTarifas[%{#row.index}].descripcion" />
			    <s:hidden name="resumenCompraBean.listaCompraTasasOtrasTarifas[%{#row.index}].codigoTasa" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasOtrasTarifas[%{#row.index}].importe" />
		    	<s:hidden name="resumenCompraBean.listaCompraTasasOtrasTarifas[%{#row.index}].subTotal" />
			</s:iterator>
			
		</div>
		<div>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label for="idPrecioTotal">Importe total</label>
					</td>
					<td align="left">
						<s:textfield disabled="true" readonly="true" id="idPrecioTotal" name="resumenCompraBean.importeTotalTasas" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label for="contrato">Forma de pago</label>
					</td>
					<td align="left">
						<s:radio onchange="checkFormaPago()" cssStyle="display: inline;"
							name="resumenCompraBean.formaPago"
							list="@org.gestoresmadrid.core.tasas.model.enumeration.FormaPago@values()"
							listKey="codigo" listValue="descripcion" labelposition="none" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<div id="divContenidoIbanLabel" style="display: none;">
							<label for="idcuentaIban">Cuenta IBAN</label>
						</div>
						<div id="divContenidoTarjetaLabel" style="display: none;">
							<label for="idNumTarjeta">Tarjeta</label>
						</div>
						
						<div id="divContenidoCuentaLabel" style="display: none;">
							<label for="idNumTarjeta">N&uacute;mero cuenta</label>
						</div>
					</td>
					<td align="left">
						<div id="divContenidoIban" style="display: none;">
							<s:textfield autocomplete="off" size="24"
								maxlength="24" id="idcuentaIban"
								name="resumenCompraBean.iban"></s:textfield>
						</div>
						<div id="divContenidoTarjeta" style="display: none;">
							<table>
								<tr>
									<td colspan="7" align="left">
										<label for="idTarjetaEntidad">entidad emisora</label>
										<s:select name="resumenCompraBean.tarjetaEntidad" id="idTarjetaEntidad"
											onblur="this.className='input2';" 
											onfocus="this.className='inputfocus';"
											list="entidadesBancarias" headerKey="" emptyOption="true"
											listKey="codigo" listValue="descripcion" />
									</td>
								</tr>
								<tr>
									<td>
										<label for="idTarjeta1">n&uacute;m.</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idTarjeta1" name="resumenCompraBean.tarjetaNum1"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjeta2">&nbsp;</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idTarjeta2" name="resumenCompraBean.tarjetaNum2"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjeta3">&nbsp;</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idTarjeta3" name="resumenCompraBean.tarjetaNum3"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjeta4">&nbsp;</label>
										<s:textfield autocomplete="off" size="4"
											maxlength="4" id="idTarjeta4"
											name="resumenCompraBean.tarjetaNum4"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idCCV">ccv</label>
										<s:textfield autocomplete="off" size="1" maxlength="3"
											id="idCCV" name="resumenCompraBean.tarjetaCcv"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idTarjetaMes">mes cad.</label>
										<s:select name="resumenCompraBean.tarjetaMes" id="idTarjetaMes"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											list="#{'01':'Enero','02':'Febrero','03':'Marzo','04':'Abril','05':'Mayo','06':'Junio',
											'07':'Julio','08':'Agosto','09':'Septiembre','10':'Octubre','11':'Noviembre','12':'Diciembre' }"
											headerKey="" emptyOption="true" />
									</td>
									<td>
										<label for="idTarjetaAnio">a&ntilde;o cad.</label>
										<s:select name="resumenCompraBean.tarjetaAnio" id="idTarjetaAnio"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											list="anios" headerKey="" emptyOption="true" />
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
											id="idEntidad" name="resumenCompraBean.entidad"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idOficina">oficina</label>
										<s:textfield autocomplete="off" size="4" maxlength="4"
											id="idOficina" name="resumenCompraBean.oficina"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idDC">dc</label>
										<s:textfield autocomplete="off" size="2" maxlength="2"
											id="idDC" name="resumenCompraBean.dc"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
									<td>
										<label for="idNumeroCuentaPago">cuenta</label>
										<s:textfield autocomplete="off" size="10"
											maxlength="10" id="idNumeroCuentaPago"
											name="resumenCompraBean.numeroCuentaPago"
											onkeypress="return validarNumerosEnteros(event)"></s:textfield>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>	
			</table>
		</div>
	
	<div class="acciones center">
		<input type="button" class="boton" name="bBorrar" id="idBorrar" value="Borrar todo" onClick="borrarFormulario();" onKeyPress="this.onClick" />
		<input type="button" class="boton" name="bRealizarCompra" id="idRealizarCompra" value="Siguiente" onClick="realizarCompra();" onKeyPress="this.onClick" />
	</div>	
</s:form>			
</div>

<script type="text/javascript">
$( document ).ready(function() {
	checkFormaPago();
});

function realizarCompra(){
	doPost('formData', 'realizarCompra${action}.action');
}

function borrarFormulario(){
	$('input[name$=cantidad]').attr('value', '0');
	$('input[name$=subTotal]').attr('value', '0');
	$('input[name=resumenCompraBean\\.importeTotalTasas]').attr('value', '');
	$('input[name=resumenCompraBean\\.entidad]').attr('value', '');
	$('input[name=resumenCompraBean\\.oficina]').attr('value', '');
	$('input[name=resumenCompraBean\\.dc]').attr('value', '');
	$('input[name=resumenCompraBean\\.numeroCuentaPago]').attr('value', '');
	$(':radio[name=resumenCompraBean\\.formaPago]').attr('checked', false);
	$('input[name^=resumenCompraBean\\.tarjeta]').attr('value', '');
	$('#idTarjetaEntidad option:first').attr('selected', true);
	$('#idTarjetaMes option:first').attr('selected', true);
	$('#idTarjetaAnio option:first').attr('selected', true);
	$('[id^=divContenidoCuenta]').hide("slow");
	$('[id^=divContenidoIban]').hide("slow");
	$('[id^=divContenidoTarjeta]').hide("slow");
}

function checkFormaPago() {
	var formaPago = $('input[name=resumenCompraBean\\.formaPago]:checked').attr('value');
	if (formaPago == '1') {
		$('[id^=divContenidoCuenta]').hide("slow");
		$('[id^=divContenidoIban]').hide("slow");
		$('[id^=divContenidoTarjeta]').show("slow");
	} else if (formaPago == '0') {
		$('[id^=divContenidoTarjeta]').hide("slow");
		$('[id^=divContenidoIban]').hide("slow");
		$('[id^=divContenidoCuenta]').show("slow");
	} else if (formaPago == '2') {
		$('[id^=divContenidoCuenta]').hide("slow");
		$('[id^=divContenidoTarjeta]').hide("slow");
		$('[id^=divContenidoIban]').show("slow");
	} else {
		$('[id^=divContenidoCuenta]').hide("slow");
		$('[id^=divContenidoTarjeta]').hide("slow");
		$('[id^=divContenidoIban]').hide("slow");
	}
}


function calcularSubTotal(elementoId, importe, subTotal){
 	var cantidad = document.getElementById(elementoId).value;
 	var subtotalCal = cantidad * importe;
 	var subTotalRestar = 0;
 	var importeTotalTasas2 = 0;
 	
 	if (document.getElementById(subTotal)!=null && document.getElementById(subTotal).value){
 		subTotalRestar = document.getElementById(subTotal).value;
 	}
 	subtotalCal = subtotalCal.toFixed(2);
 	document.getElementById(subTotal).value = subtotalCal;
	
	if (document.getElementById("idPrecioTotal")!=null && document.getElementById("idPrecioTotal").value!=null
			&& document.getElementById("idPrecioTotal").value!=''){
		importeTotalTasas2 = document.getElementById("idPrecioTotal").value;
	}
	
	importeTotalTasas2 = parseFloat(importeTotalTasas2) - parseFloat(subTotalRestar);
	importeTotalTasas2 = parseFloat(importeTotalTasas2) + parseFloat(subtotalCal);
	importeTotalTasas2 = importeTotalTasas2.toFixed(2);
	
	document.getElementById("idPrecioTotal").value = importeTotalTasas2;
}

</script>
