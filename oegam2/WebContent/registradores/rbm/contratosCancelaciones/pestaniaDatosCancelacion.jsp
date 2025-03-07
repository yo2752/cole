<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="tramiteRegRbmDto.situacionJuridicaCancelacion" />

<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos cancelaci&oacute;n</span>
			</td>
		</tr>
	</table>	  

	<table class="tablaformbasica" >
 		<tr>
		   <td align="left" nowrap="nowrap"><label for="idTramiteRegistro">N&uacute;m. Expediente</label></td>
			<td> <s:textfield name="tramiteRegRbmDto.idTramiteRegistro" id="idTramiteRegistro" size="16" readonly="true" onfocus="this.blur();"></s:textfield></td>
	   		<td align="left" nowrap="nowrap"><label for="referenciaPropiaId" title="Referencia propia de cancelaci&oacute;n">Referencia propia</label></td>
			<td><s:textfield name="tramiteRegRbmDto.refPropia" id="refPropia" size="16" maxlength="50"></s:textfield></td>
		</tr> 
		<tr>
			<td align="left" nowrap="nowrap"><label for="modeloDocumento">Modelo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Modelo de documento"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.modeloContrato" id="modeloDocumento" size="16" ></s:textfield></td>
			<%-- <td align="left" nowrap="nowrap"><label for="tituloDocumento" title="T&iacute;tulo del documento de cancelaci&oacute;n">T&iacute;tulo documento</label></td>
			<td><s:textfield name="leasingRentingDossier.tituloDocumento" id="tituloDocumento" size="16" ></s:textfield></td> --%>
			
			<td align="left" nowrap="nowrap"><label for="importeComisionCancelacion" class="small">Importe comisi&oacute;n cancelaci&oacute;n</label></td>
			<td><s:textfield name="tramiteRegRbmDto.importeComisionCancelacion" id="importeComisionCancelacion" size="16" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
		
		</tr>
		<%-- <tr>

			<td align="left" nowrap="nowrap"><label for="clausulaLOPD" title="Cl&aacute;usula LOPD">Cl&aacute;usula LOPD</label></td>
			<td><s:textfield name="leasingRentingDossier.clausulaLOPD" id="clausulaLOPD" size="16" ></s:textfield></td>
		</tr> --%>
		<tr>
			<td align="left" nowrap="nowrap"><label for="numeroImpreso" class="small">N&uacute;mero impreso</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero &uacute;nico de impreso asignado por el Registro de Bienes Muebles Central"/>
			</td>
	   		<td><s:textfield name="tramiteRegRbmDto.numeroImpreso" id="numeroImpreso" size="16" maxlength="255"></s:textfield></td>

			<td align="left" nowrap="nowrap"><label for="causaCancelacion">Causas cancelaci&oacute;n<span class="naranja">*</span></label></td>
			<td>
				<s:select name="tramiteRegRbmDto.causaCancelacion" 
				                   list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCausaCancelacion()" 
				                   listKey="key"
				                   headerKey="" 
				                   headerValue="Seleccionar causa"
				                   id="causaCancelacion"/>
			</td>
		</tr>
		<tr>
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
			<td align="left" nowrap="nowrap"><label for="tipoOperacion"  >Tipo operaci&oacute;n<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de operaci&oacute;n para Bienes Muebles"/>
			</td>
			<td>
				<s:select name="tramiteRegRbmDto.tipoOperacion" 
				                   list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoOperacionCancelacion()" 
				                   listKey="key"
				                   headerKey="" 
				                   headerValue="Tipo operación"
				                   id="tramiteRegRbmDtoOperationType"/>
			</td>
		</tr>
	</table>
	
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Situaciones jur&iacute;dicas</span>
			</td>
		</tr>
	</table>
	
	<table class="tablaformbasica" >
 		<tr>
			<td align="left" nowrap="nowrap"><label for="situacionJuridicaCancelacion"  class="small">Situaci&oacute;n jur&iacute;dica<span class="naranja">*</span></label></td>
			<td>
				<s:select name="tramiteRegRbmDto.situacionJuridica" 
				                   list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListSituacionJuridica()" 
				                   listKey="key"
				                   headerKey="" 
				                   headerValue="Seleccionar situación"
				                   id="situacionJuridicaCancelacion"/>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:doPostValidate('formData', 'aniadirSituacionJuridicaCancelacion.action#tab1', '', '');" id="btnAniadirSituacionJuridicaCancelacion" class="button corporeo" value="Añadir situaci&oacute;n" />
				</s:if>
			</td>
			
		</tr>
	</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="situacionesJuridicasList.jsp" />
        	</td>
        </tr>
	</table>
	
</div>