<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
	<s:hidden name="tramiteRegRbmDto.propiedadDto.vehiculo.idVehiculo"/>
	<s:hidden name="tramiteRegRbmDto.propiedadDto.vehiculo.idPropiedad"/>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Veh&iacute;culo:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            
             
             <!-- Campos para contratos cancelación -->
	       	<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_10.getValorEnum() ">
	      		 <tr>	
			      	<td align="left" nowrap="nowrap"><label for="vehiculoDtoTipo" >Clase</label></td>
	                <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.tipo" id="vehiculoDtoTipo" size="18" maxlength="255"></s:textfield></td>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoCodigoMarca" class="small">Marca</label></td>
	                <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.marca" id="vehiculoDtoCodigoMarca" size="18" maxlength="255"></s:textfield></td>
	             </tr>
	             <tr>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoModelo" class="small">Modelo</label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.modelo" id="vehiculoDtoModelo" size="18" maxlength="255"></s:textfield></td>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoMatricula" class="small">Matr&iacute;cula<span class="naranja">*</span></label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.matricula" id="vehiculoDtoMatricula" size="18" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"   ></s:textfield>	</td>
	             </tr>
	             <tr>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoBastidor" class="small">Bastidor<span class="naranja">*</span></label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.bastidor" id="vehiculoDtoBastidor" size="18" maxlength="17"></s:textfield></td>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoNive" class="small">N&uacute;mero de fabricaci&oacute;n</label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.nive" id="vehiculoDtoNive" size="18" maxlength="255"></s:textfield></td>
	             </tr>		
	      	</s:if>
	      	
	      	 <!-- Campos para contratos distintos de cancelación -->
	      	<s:if test="tramiteRegRbmDto.tipoTramite != @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_10.getValorEnum() ">
		      	 <tr>	
			      	<td align="left" nowrap="nowrap"><label for="vehiculoDtoTipo" >Tipo</label>
	      				<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de veh&iacute;culo" />
	      			</td>
	                <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.tipo" id="vehiculoDtoTipo" size="18" maxlength="255"></s:textfield>	</td>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoCodigoMarca" class="small">Marca</label></td>
	                <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.marca" id="vehiculoDtoCodigoMarca" size="18" maxlength="255"></s:textfield></td>
	             </tr>
	             <tr>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoModelo" class="small">Modelo</label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.modelo" id="vehiculoDtoModelo" size="18" maxlength="255"></s:textfield></td>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoMatricula" class="small">Matr&iacute;cula<span class="naranja">*</span></label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.matricula" id="vehiculoDtoMatricula" size="18" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  ></s:textfield>	</td>
	             </tr>
	             <tr>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoBastidor" class="small">Bastidor<span class="naranja">*</span></label></td>
	                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.vehiculo.bastidor" id="vehiculoDtoBastidor" size="18" maxlength="17"></s:textfield></td>
	                <td align="left" nowrap="nowrap"><label for="vehiculoDtoPropertyGroupId" >Grupo</label>
	               		<img src="img/botonDameInfo.gif" alt="Info" title="Grupo del bien" />
	                </td>
	                <td colspan="3">
	               	 <s:select name="tramiteRegRbmDto.propiedadDto.vehiculo.grupo" 
	                          list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListGrupoBien()" 
	                          listKey="codigo"
	                          listValue="nombre"
	                          headerKey="" 
	                          headerValue="Selecionar grupo"
	                          id="vehiculoDtoPropertyGroupId"/>
	                </td>
	             </tr>
	         </s:if>    			
             
  		</table>
