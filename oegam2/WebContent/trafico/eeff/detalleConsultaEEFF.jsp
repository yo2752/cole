<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/colas.js" type="text/javascript"></script>
<link href="css/global.css" rel="stylesheet" type="text/css" media="screen" />
<%@include file="../../includes/erroresYMensajes.jspf" %>

<div id="contenido" class="contentTabs" style="display: block;text-align: center;">	
	<div class="navPopUp">
		<table width="100%" >
			<tr>
				<td class="tabularSupPopUp">
					<span class="tituloSupPopUp">DETALLE DE LA CONSULTA EEFF</span>
				</td>
			</tr>
		</table>
	</div>
</div>
<s:form method="post" id="formData" name="formData">
	<s:if test="%{detalleConsultaEEFFBean.codigoError != null}">
		<div  class="navPopUp">
			<table width="100%">
				<tr>
					<td class="tabularPopUp" >
						<span class="tituloPopUp">INFORMACIÓN DEL ERROR</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="dfError">
			<table class="tablaformbasicaEEFF" style="height:200px;">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelCodError">Código Error: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.codigoError" id="codigoError" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
					</td>
					<td align="left">
						<label for="labelDescError">Descripción del Error: </label>
					</td>
					<td align="left">
						<s:textarea readonly="true" name="detalleConsultaEEFFBean.descripcionError" id="descripcionError" 
		   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" rows="8" cols="50"/>
					</td>
				</tr>
			</table>
		</div>
	</s:if>
	<s:else>
		<div  class="navPopUp">
			<table width="100%">
				<tr>
					<td class="tabularPopUp" >
						<span class="tituloPopUp">DATOS DEL CLIENTE FINAL</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="dfClienteFinal" aligN="left">
			<table class="tablaformbasicaEEFF">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelNifCliente">NIF: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.nifCliente" id="nifCliente" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
					</td>
					<td align="left">
						<label for="labelNonmbreApe">Nombre y Apellidos: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.nombreApellidosCliente" id="nombreApellidosCliente" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="true" size="40" maxlength="50"></s:textfield>
					</td>
				</tr>
				<s:if test="%{detalleConsultaEEFFBean.direccionCliente != null}">
					<tr>
						<td align="left" nowrap="nowrap" style="width:10%;">
							<label for="labelDirecciop">Direccion: </label>
						</td>
						<td align="left">
							<s:textfield name="detalleConsultaEEFFBean.direccionCliente" id="nombreApellidosCliente" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="true" size="40" maxlength="78"></s:textfield>
						</td> 
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="detalleConsultaEEFFBean.direccionCliente" status="row">
						<td align="left" nowrap="nowrap" style="width:10%;">
							<label for="labelDirecciop">Direccion: </label>
						</td>
						<td align="left">
							<s:textfield name="detalleConsultaEEFFBean.direccionCliente" id="nombreApellidosCliente" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="true" size="40" maxlength="78"></s:textfield>
						</td> 
					</s:iterator>
				</s:else>
			</table>
		</div>
		<div  class="navPopUp">
			<table width="100%">
				<tr>
					<td class="tabularPopUp" >
						<span class="tituloPopUp">DATOS DEL VEHICULO</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="dfVehiculo" aligN="left">
			<table class="tablaformbasicaEEFF">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelNive">Nive: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.tarjetaNive" id="tarjetaNive" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="35" maxlength="32"></s:textfield>
					</td>
					<td align="left">
						<label for="labelBastidor">Bastidor: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.tarjetaBastidor" id="tarjetaBastidor" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="30" maxlength="21"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelEstadoBastidor">Estado Bastidor: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.estadoBastidor" id="estadoBastidor" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="32" maxlength="25"></s:textfield>
					</td>
					<td align="left">
						<label for="labelBastidor">Estado Financiero: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.estadoFinanciero" id="estadoFinanciero" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelFirCif">Fir CIF: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.firCif" id="firCif" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="15" maxlength="10"></s:textfield>
					</td>
					<td align="left">
						<label for="labelFirMarca">Fir Marca: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.firMarca" id="firMarca" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelConcesionario">Concesionario Cliente: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.concesionarioComercial" id="concesionarioComercial" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="50" maxlength="50"></s:textfield>
					</td>
				</tr>
			</table>
		</div>
		<div  class="navPopUp">
			<table width="100%">
				<tr>
					<td class="tabularPopUp" >
						<span class="tituloPopUp">DATOS FINANCIEROS</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="dfDatosFinancieros" aligN="left">
			<table class="tablaformbasicaEEFF">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelEntidadCredito">Entidad Credito: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.entidadCredito" id="entidadCredito" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="35" maxlength="35"></s:textfield>
					</td>
					<td align="left">
						<label for="labelNumeroFactura">Numero Factura Final: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.numeroFacturaFinal" id="numeroFacturaFinal" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="20" maxlength="15"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelFechaFactura">Fecha Factura: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.fechaFacturaFinal" id="fechaFacturaFinal" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
					</td>
					<td align="left">
						<label for="labelNumeroFactura">Importe Factura: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.importeFacturaFinal" id="importeFacturaFinal" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
					</td>
				</tr>
			</table>
		</div>
		<div  class="navPopUp">
			<table width="100%">
				<tr>
					<td class="tabularPopUp" >
						<span class="tituloPopUp">DATOS CUSTODIO</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="dfDatosCustodio" aligN="left">
			<table class="tablaformbasicaEEFF">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelCustodioActual">Custodio Actual: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodiaActual" id="custodiaActual" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioSiguiente">Custodio Siguiente: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioSiguiente" id="custodioSiguiente" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioAnterior">Custodio Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioAnterior" id="custodioAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioFinal">Custodio Final: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioFinal" id="custodioFinal" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
			</table>
		</div>
		<div  class="navPopUp">
			<table width="100%">
				<tr>
					<td class="tabularPopUp" >
						<span class="tituloPopUp">DATOS HISTORICOS ITV</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="dfDatosHistoricosItv" aligN="left">
			<table class="tablaformbasicaEEFF">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelCustodioActualAnterior">Custodio Actual Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioActualAnterior" id="custodioActualAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioAnteriorAnterior">Custodio Anterior Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioAnteriorAnterior" id="custodioAnteriorAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioFinalAnterior">Custodio Final Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioFinalAnterior" id="custodioFinalAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioSiguienteAnterior">Custodio Siguiente Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.custodioSiguienteAnterior" id="custodioSiguienteAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelDenomEstadoFinanAnterior">Estado Financiero Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="detalleConsultaEEFFBean.denominacioNEstadoFinancieroAnterior" id="denominacioNEstadoFinancieroAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
			</table>
		</div>
	</s:else>
</s:form>