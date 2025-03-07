<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="tramiteRegRbmDto.datosFinanciero.idDatosFinancieros" />

<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos generales</span>
			</td>
		</tr>
	</table>	  

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="idTramiteRegistro">N&uacute;m. Expediente</label></td>
			<td> <s:textfield name="tramiteRegRbmDto.idTramiteRegistro" id="idTramiteRegistro" size="16" readonly="true" onfocus="this.blur();"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="refPropia" >Referencia propia</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de expediente de la Gestor&iacute;a. Referencia propia"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.refPropia" id="refPropia" size="16" maxlength="50"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="email" >Email</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Direcci&oacute;n email para el env&iacute;o de notificaciones"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.email" id="email" size="16" onblur="validaEmail(this.value)" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="telefono" >N&uacute;m. Tel&eacute;fono</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de tel&eacute;fono para el env&iacute;o de SMS de notificaci&oacute;n"/>
			</td>
			<td>  <s:textfield name="tramiteRegRbmDto.telefono" id="telefono" size="16" maxlength="20"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="email2" >Otro email</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Segunda direcci&oacute;n email para el env&iacute;o de notificaciones"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.email2" id="email2" size="16" onblur="validaEmail(this.value)" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="telefono2" >Otro n&uacute;m. tel&eacute;fono</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Segundo n&uacute;mero de telefono para el env&iacute;o de SMS de notificaci&oacute;n"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.telefono2" id="telefono2" size="16" maxlength="20"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="nombreAsociacion" >Nombre Asociaci&oacute;n<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Nombre de la asociaci&oacute;n que representa a la entidad (ASNEF, AEL,...)"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.nombreAsociacion" id="nombreAsociacion" size="16" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="numeroOperacion" >N&uacute;mero operaci&oacute;n<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de operaci&oacute;n asignado por la entidad de financiaci&oacute;n. En el modelo ASNEF se etiqueta como N en la cabecera"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.numeroOperacion" id="numeroOperacion" size="16" maxlength="255"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="numeroEjemplar" >N&uacute;m. ejemplar<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de ejemplar o n&uacute;mero de copia"/>
			</td>
			<td> <s:textfield name="tramiteRegRbmDto.numeroEjemplar" id="numeroEjemplar" size="16" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="modeloContrato">Modelo contrato<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo de modelo de contrato"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.modeloContrato" id="modeloContrato" size="16" maxlength="255"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="nombreDocumento" >Nombre doc.<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Nombre del documento"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.nombreDocumento" id="nombreDocumento" size="16" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="numeroImpreso" >N&uacute;mero impreso R.B.M.<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero &uacute;nico de impreso asignado por el Registro de Bienes Muebles Central"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.numeroImpreso" id="numeroImpreso" size="16" maxlength="255"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="aprobadoDgrn" >Referencia DGRN<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Referencia al texto que aprueba el modelo por la DGRN"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.aprobadoDgrn" id="aprobadoDgrn" size="16" maxlength="255"></s:textfield></td>
			
			<td align="left" nowrap="nowrap"><label for="registryOfficeId">Registro<span class="naranja">*</span></label></td>
			<td>
				<s:select name="tramiteRegRbmDto.idRegistro" 
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroBienesMuebles()" 
					listKey="id"
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar registro"
					id="tramiteRegRbmDto.RegistryOfficeId"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="documentDate">Fecha documento<span class="naranja">*</span></label></td>
			<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fechaDocumento.dia" id="diafechaDocumento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diafechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('aniofechaDocumento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fechaDocumento.mes" id="mesfechaDocumento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diafechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('aniofechaDocumento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.fechaDocumento.anio" id="aniofechaDocumento"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diafechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('aniofechaDocumento'));"/>
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('aniofechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('diafechaDocumento'));return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>

			<td align="left" nowrap="nowrap"><label for="tramiteRegRbmDtoContractType">Tipo contrato<span class="naranja">*</span></label></td>
			<td>
				<s:select name="tramiteRegRbmDto.tipoContrato" 
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListLeasingContractType()" 
					listKey="key"
					headerKey="" 
					headerValue="Tipo contrato"
					id="tramiteRegRbmDtoContractType"/>
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
			<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoImpNetoMat" >Imp. neto bienes<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Importe neto de los bienes que forman parte del contrato"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impNetoMat" id="datosFinancierosDtoImpNetoMat" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>			
			
			<td align="left" nowrap="nowrap"> <label for="datosFinancierosDtoImpTotalArrend" class="small">Total arrendamiento<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impTotalArrend" id="datosFinancierosDtoImpTotalArrend" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
			
			<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoImpOpcionCompra" >Opci&oacute;n compra<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Importe opci&oacute;n de compra"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impOpcionCompra" id="datosFinancierosDtoPurchaseImpOpcionCompra" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
		</tr>
		<tr> 
			<td align="left" nowrap="nowrap"> <label for="datosFinancierosDtoImpEntregaCuenta" class="small">Entrega a cuenta<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impEntregaCuenta" id="datosFinancierosDtoImpEntregaCuenta" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td> 
			
			<td align="left" nowrap="nowrap"> <label for="datosFinancierosDtoImpFianza" class="small">Importe fianza<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impFianza" id="datosFinancierosDtoImpFianza" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>  
			
			 <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTipoInteresNominalAnual" >Tipo nominal<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de inter&eacute;s nominal anual por aplazamiento"/>
			 </td>
			 <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.tipoInteresNominalAnual" id="datosFinancierosDtoTipoInteresNominalAnual" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTasaAnualEquiv" >TAE<span class="naranja">*</span></label>
               	<img src="img/botonDameInfo.gif" alt="Info" title="Tasa anual equivalente"/>
            </td>
            <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.tasaAnualEquiv" id="datosFinancierosDtoTasaAnualEquiv" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')" ></s:textfield></td>	 
			<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoNumeroMeses" >Meses aplaz.<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de meses de intereses por aplazamiento"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.numeroMeses" id="datosFinancierosDtoNumeroMeses" size="14" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
			
			<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoFecUltVencimiento" >&Uacute;ltimo vencimiento<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Fecha &uacute;ltimo vencimiento"/>
			</td>
			<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datosFinanciero.fecUltVencimientoDatFinancieros.dia" id="diaFecUltVencimiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecUltVencimiento'), document.getElementById('mesFecUltVencimiento'), document.getElementById('anioFecUltVencimiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datosFinanciero.fecUltVencimientoDatFinancieros.mes" id="mesFecUltVencimiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecUltVencimiento'), document.getElementById('mesFecUltVencimiento'), document.getElementById('anioFecUltVencimiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datosFinanciero.fecUltVencimientoDatFinancieros.anio" id="anioFecUltVencimiento"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecUltVencimiento'), document.getElementById('mesFecUltVencimiento'), document.getElementById('anioFecUltVencimiento'));"/>
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('anioFecUltVencimiento'), document.getElementById('mesFecUltVencimiento'), document.getElementById('diaFecUltVencimiento'));return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
     </table>
     
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Variabilidad tipo inter&eacute;s</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

         <tr>
              	<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoFecRevision" >Fecha revisi&oacute;n Inter.</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Primera fecha en que se revisar&aacute; el tipo de inter&eacute;s"/>
				</td>
	            <td align="left">
					<table style="width:20%">
						<tr>
							<td align="left">
								<s:textfield name="tramiteRegRbmDto.datosFinanciero.fecRevisionDatFinancieros.dia" id="diaFecRevision"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecRevision'), document.getElementById('mesFecRevision'), document.getElementById('anioFecRevision'));"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="tramiteRegRbmDto.datosFinanciero.fecRevisionDatFinancieros.mes" id="mesFecRevision"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecRevision'), document.getElementById('mesFecRevision'), document.getElementById('anioFecRevision'));"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="tramiteRegRbmDto.datosFinanciero.fecRevisionDatFinancieros.anio" id="anioFecRevision"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecRevision'), document.getElementById('mesFecRevision'), document.getElementById('anioFecRevision'));"/>
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.getElementById('anioFecRevision'), document.getElementById('mesFecRevision'), document.getElementById('diaFecRevision'));return false;" 
		  								title="Seleccionar fecha">
		  								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		  							</a>
							</td>
						</tr>
					</table>
				</td>			  
               
               
           	<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoPeriodoRevision" >N&uacute;m. meses revis.</label>
           		<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de meses en los que se revisar&aacute; el tipo de inter&eacute;s de forma sucesiva"/>
           	</td>
           	<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.periodoRevision" id="datosFinancierosDtoPeriodoRevision" size="14" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>      
        </tr>
        <tr>
            <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTipoInteresRef" >Tipo inter&eacute;s ref.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de inter&eacute;s de referencia"/>            
            </td>
            <td colspan="4">
               		<s:select name="tramiteRegRbmDto.datosFinanciero.tipoInteresRef" 
                          list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListInterestTypeReference()"
                          listKey="key" headerKey=""
                          headerValue="Selecionar Tipo"
                          id="datosFinancierosDtoInterestTypeReference"/>
            </td>
         </tr>
         <tr>
               <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoDiferencialFijo" >Diferencial fijo</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de inter&eacute;s de referencia sustitutivo. Diferencial fijo en puntos porcentuales"/>
               </td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.diferencialFijo" id="datosFinancierosDtoDiferencialFijo" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')" ></s:textfield></td>
               
               <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTopeMaxIntNominal" >M&aacute;x. inter&eacute;s nom.</label>
       				<img src="img/botonDameInfo.gif" alt="Info" title="Tope m&aacute;ximo de inter&eacute;s nominal anual, a efectos de variaci&oacute;n de inter&eacute;s en perjuicio de tercero"/>
               </td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.topeMaxIntNominal" id="datosFinancierosDtoTopeMaxIntNominal" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')" ></s:textfield></td>
          </tr>
     </table>
     
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Domicilio de pago</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
         <tr>
               <td align="left" nowrap="nowrap"> <label for="datosFinancierosDtoDomicilioEntidadPago" class="small">Domicilio entidad</label></td>
               <td colspan="2"><s:textfield name="tramiteRegRbmDto.datosFinanciero.domicilioEntidadPago" id="datosFinancierosDtoDomicilioEntidadPago" size="50" maxlength="255"></s:textfield></td>
         </tr>
         <tr>     
                <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoCodProvinciaPago" >C&oacute;digo INE provincia</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia entidad de pago"/>
               </td>
               <td> <s:select name="tramiteRegRbmDto.datosFinanciero.codProvinciaPago" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="maquinariaSelectProvinciaId"
 						  onchange="cargarListaMunicipios(this,'financieroSelectMunicipioId','financieroHiddenMunicipioId');"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoCodMunicipioPago" >C&oacute;digo INE municipio</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio entidad de pago"/>
               </td>
               <td><s:select id="financieroSelectMunicipioId"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.datosFinanciero.codProvinciaPago)"
							onchange="javascript:seleccionMunicipio(this, 'financiero');"
							name="tramiteRegRbmDto.datosFinanciero.codMunicipioPago"
							headerValue="Seleccione Municipio" 
							listKey="idMunicipio"
							listValue="nombre" />
					<s:hidden id="financieroHiddenMunicipioId"/>
					<s:hidden id="financieroHiddenMunicipality" />  
               </td>
          </tr>
          <tr>
          	<td align="left" nowrap="nowrap"> <label for="datosFinancierosDtoEntidadPago" class="small">Entidad de pago</label></td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.entidadPago" id="datosFinancierosDtoEntidadPago" size="5" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')" ></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoCccPago" class="small">N&uacute;mero cuenta corriente</label></td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.cccPago" id="datosFinancierosDtoCccPago" size="20" maxlength="20"></s:textfield></td>
          </tr>
     </table>
    
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos de la firma</span>
			</td>
		</tr>
	</table>
	
    <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirFirma('Leasing','formData','cargarPopUpDatosFirmaRegistroLeasing.action');" id="btnAniadirFirmaLeasing" class="button corporeo" value="Añadir"/>
				</s:if>
			</td>
		</tr>
	   
	</table> 
			
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<jsp:include page="firmasList.jsp" />
			</td>
		</tr>
	</table>
  
    <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Comisiones</span>
			</td>
		</tr>
	</table>
		
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	
	  	 <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirComision('Leasing','formData', 'cargarPopUpComisionRegistroLeasing.action');" id="btnAniadirComisionLeasing" class="button corporeo" value="Añadir" />
				</s:if>
			</td>
		</tr>
	</table> 
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="comisionesList.jsp" flush="true" />
        	</td>
        </tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Otros importes</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
    <tr>
		<td align="center" nowrap="nowrap" colspan="6">
			<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
				<input type="button" onclick="javascript:aniadirImporte('Leasing','formData', 'cargarPopUpOtrosImportesRegistroLeasing.action');" id="btnAniadirImporteLeasing" class="button corporeo" value="Añadir" />
			</s:if>
		</td>
	</tr>
</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="importesList.jsp" />
        	</td>
        </tr>
	</table>
   
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Reconocimiento de deuda</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirDeuda('Leasing', 'formData', 'cargarPopUpReconocimientoDeudaRegistroLeasing.action');" id="btnAniadirDeudaLeasing" class="button corporeo" value="Añadir" />
				</s:if>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="deudasList.jsp"  />
        	</td>
        </tr>
	</table>
  
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Cuadros de amortizaci&oacute;n</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirCuadro('Leasing', 'formData', 'cargarPopUpCuadroAmortizacionRegistroLeasing.action', 'btnAniadirCuadroLeasing');" id="btnAniadirCuadroLeasing" class="button corporeo" value="Añadir cuadro" />
				</s:if>
			</td>
		</tr>
	</table>
			
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="cuadrosList.jsp" />
        	</td>
        </tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Financiación impuesto anticipado</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
		 	   <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoImpImpuesto" class="small">Importe impuesto</label></td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impImpuesto" id="datosFinancierosDtoImpImpuesto" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
              
              	<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTipoInteresNominalAnualFi" >Tipo nominal F.I.</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de inter&eacute;s nominal anual por aplazamiento de la Financiaci&oacute;n de Impuesto Anticipado"/>
				</td>
				<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.tipoInteresNominalAnualFi" id="datosFinancierosDtoTipoInteresNominalAnualFi" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"></s:textfield></td>   
			
               <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTasaAnualEquivFi" >TAE F.I.</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Tasa anual equivalente de la Financiaci&oacute;n de Impuesto Anticipado"/>
               </td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.tasaAnualEquivFi" id="datosFinancierosDtoTasaAnualEquivFi" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"></s:textfield></td> 
          </tr>
        
     </table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirCuadro('Leasing', 'formData', 'cargarPopUpCuadroAmortizacionFIRegistroLeasing.action', 'btnAniadirCuadroFILeasing');" id="btnAniadirCuadroFILeasing" class="button corporeo" value="Añadir cuadro F.I." />
				</s:if>
			</td>
		</tr>
	</table>
			
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="cuadrosFIList.jsp" />
        	</td>
        </tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Financiación seguro </span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		 <tr>   
		 	   <td align="left" nowrap="nowrap"><label for="datosFinancierosDtoImpSeguro" class="small">Importe seguro</label></td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.impSeguro" id="datosFinancierosDtoImpSeguro" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')" ></s:textfield></td>
          		
          		<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTipoInteresNominalAnualFs" >Tipo nominal F.S.</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de inter&eacute;s nominal anual por aplazamiento de la Financiaci&oacute;n de Seguro"/>
				</td>
				<td><s:textfield name="tramiteRegRbmDto.datosFinanciero.tipoInteresNominalAnualFs" id="datosFinancierosDtoTipoInteresNominalAnualFs" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"></s:textfield></td>
				
				<td align="left" nowrap="nowrap"><label for="datosFinancierosDtoTasaAnualEquivFs" >TAE F.S.</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Tasa anual equivalente de la Financiaci&oacute;n de Seguro"/>
               </td>
               <td><s:textfield name="tramiteRegRbmDto.datosFinanciero.tasaAnualEquivFs" id="datosFinancierosDtoTasaAnualEquivFs" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')" ></s:textfield></td>         
          </tr>
        
     </table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirCuadro('Leasing', 'formData', 'cargarPopUpCuadroAmortizacionFSRegistroLeasing.action', 'btnAniadirCuadroFSLeasing');" id="btnAniadirCuadroFSLeasing" class="button corporeo" value="Añadir cuadro F.S." />
				</s:if>
			</td>
		</tr>
	</table>
			
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="cuadrosFSList.jsp" />
        	</td>
        </tr>
	</table>

</div>