<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" align="left">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos de la Consulta EEFF</td>
			</tr>
		</table>
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
		<s:if test="%{consultaEEFF.resultadoConsultaEEFF.codigoError != null}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Información del Error</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCodError">Código Error: </label>
					</td>
					<td align="left" nowrap="nowrap" width="24%">
			     		<s:textfield name="consultaEEFF.resultadoConsultaEEFF.codigoError" id="idCodigoErrorEEFF" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
			    	</td>
			    	 <td align="left" nowrap="nowrap">
		       			<label for="labelDescError">Descripción del Error: </label>
		       		</td>
		         	<td align="left" nowrap="nowrap">
			         	<s:textarea readonly="true" name="consultaEEFF.resultadoConsultaEEFF.descripcionError" id="idDescripcionErrorEEFF" 
				   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" rows="8" cols="50"/>
		       		</td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Datos del Cliente Final</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNifCliente">NIF: </label>
					</td>
					<td align="left" nowrap="nowrap" width="24%">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.nifCliente" id="idNifCliente" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"/>
			    	</td>
			    </tr>
				<tr>
			    	<td align="left" nowrap="nowrap">
		       			<label for="labelNonmbreApe">Nombre y Apellidos: </label>
		       		</td>
		         	<td align="left" nowrap="nowrap">
			         	<s:textfield name="consultaEEFF.resultadoConsultaEEFF.nombreApellidosCliente" id="idNombreApellidosCliente" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="true" size="40" maxlength="50"/>
		       		</td>
				</tr>
				<tr>
					<s:if test="%{consultaEEFF.resultadoConsultaEEFF.direccionCliente != null && !consultaEEFF.resultadoConsultaEEFF.direccionCliente.isEmpty()}">
						<s:iterator value="consultaEEFF.resultadoConsultaEEFF.direccionCliente" status="row">
							<td align="left" nowrap="nowrap" style="width:10%;">
								<label for="labelDirecciop">Direccion: </label>
							</td>
							<td align="left">
								<s:textfield name="consultaEEFF.resultadoConsultaEEFF.direccionCliente" id="idDireccionCliente" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="40" maxlength="78"></s:textfield>
							</td> 
						</s:iterator>
					</s:if>
					<s:else>
						<td align="left" nowrap="nowrap" style="width:10%;">
								<label for="labelDirecciop">Direccion: </label>
						</td>
						<td align="left">
							<s:textfield name="consultaEEFF.resultadoConsultaEEFF.direccionCliente" id="idDireccionCliente" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="true" size="40" maxlength="78"></s:textfield>
						</td> 
					</s:else>
				</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Datos del Vehículo</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNive">Nive: </label>
					</td>
					<td>
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.tarjetaNive" id="idTarjetaNive" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="35" maxlength="32"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Bastidor: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.tarjetaBastidor" id="idTarjetaBastidor" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="30" maxlength="21"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
		   				<label for="labelEstadoBastidor">Estado Bastidor: </label>
		   			</td>
		       		<td align="left" nowrap="nowrap">   				
		   				<s:textfield name="consultaEEFF.resultadoConsultaEEFF.estadoBastidor" id="idEstadoBastidor" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="32" maxlength="25"></s:textfield>				  
		   			</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Estado Financiero: </label>
					</td>
					<td>
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.estadoFinanciero" id="idEstadoFinanciero" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFirCif">Fir CIF: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.firCif" id="idFirCifResultado" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="15" maxlength="10"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFirMarca">Fir Marca: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.firMarca" id="idFirMarcaResultado" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"></s:textfield>
					</td>
				</tr>
		      	<tr>    			
		   			<td align="left" nowrap="nowrap" width="7%">
		   				<label for="labelConcesionario">Concesionario Cliente: </label>
		   			</td>
		       		<td align="left" nowrap="nowrap">
		   				<s:textfield name="consultaEEFF.resultadoConsultaEEFF.concesionarioComercial" id="idCSoncesionarioComercial" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="50" maxlength="50"></s:textfield>
		   			</td>
		   		</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Datos Financieros</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEntidadCredito">Entidad Credito: </label>
					</td>
					<td>
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.entidadCredito" id="idEntidadCredito" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="35" maxlength="35"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumeroFactura">Numero Factura Final: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.numeroFacturaFinal" id="idNumeroFacturaFinal" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="20" maxlength="15"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
		   				<label for="labelFechaFactura">Fecha Factura: </label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">   				
		   				<s:textfield name="consultaEEFF.resultadoConsultaEEFF.fechaFacturaFinal" id="idFechaFacturaFinal" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>				  
		   			</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumeroFactura">Importe Factura: </label>
					</td>
					<td>
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.importeFacturaFinal" id="idImporteFacturaFinal" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="10" maxlength="10"></s:textfield>
					</td>
				</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Datos Custodio</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCustodioActual">Custodio Actual: </label>
					</td>
					<td>
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodiaActual" id="idCustodiaActual" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCustodioSiguiente">Custodio Siguiente: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioSiguiente" id="idCustodioSiguiente" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
		   				<label for="labelCustodioAnterior">Custodio Anterior: </label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">   				
		   				<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioAnterior" id="idCustodioAnterior" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>			  
		   			</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCustodioFinal">Custodio Final: </label>
					</td>
					<td>
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioFinal" id="idCustodioFinal" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
				<tr>
					<td class="tabular">
						<span class="titulo">Datos Historico ITV</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="labelCustodioActualAnterior">Custodio Actual Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioActualAnterior" id="idCustodioActualAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioAnteriorAnterior">Custodio Anterior Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioAnteriorAnterior" id="idCustodioAnteriorAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioFinalAnterior">Custodio Final Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioFinalAnterior" id="idCustodioFinalAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelCustodioSiguienteAnterior">Custodio Siguiente Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.custodioSiguienteAnterior" id="idCustodioSiguienteAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"> 
						<label for="labelDenomEstadoFinanAnterior">Estado Financiero Anterior: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFF.resultadoConsultaEEFF.denominacioNEstadoFinancieroAnterior" id="idDenominacioNEstadoFinancieroAnterior" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="true" size="80" maxlength="78"></s:textfield>
					</td>
				</tr>
			</table>
		</s:else>
</div>
