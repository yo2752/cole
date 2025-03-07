<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="tramiteRegRbmDto.entidadSuscriptora.idEntidad" id="idEntidadSuscriptora"/>
<s:hidden name="tramiteRegRbmDto.entidadSuscriptora.fecCreacion" id="fechSuscriptora"/>

<s:hidden name="tramiteRegRbmDto.entidadSucesora.idEntidad"  id="idEntidadSucesora"/>
<s:hidden name="tramiteRegRbmDto.entidadSucesora.fecCreacion" id="fechSucesora"/>

<div class="contenido">	

<%-- 	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos entidad titular suscriptora</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
        <tr>
   		    <td align="left" nowrap="nowrap"><label for="entidadSuscriptoraCodigoIdentificacionFiscal" >C&oacute;digo identificaci&oacute;n fiscal<span class="naranja">*</span></label></td>
            <td width="15%" align="right"><s:textfield name="tramiteRegRbmDto.entidadSuscriptora.codigoIdentificacionFiscal" id="entidadSuscriptoraCodigoIdentificacionFiscal" size="18"  maxlength="9" ></s:textfield>	</td>
            <td align="left" nowrap="nowrap" width="15%">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
              		<input type="button" id="idBotonBuscarCif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
						onclick="javascript:buscarEntidad('entidadSuscriptoraCodigoIdentificacionFiscal', 'Suscriptora')" />
				</s:if>
			</td>
            <td align="left" nowrap="nowrap"><label for="entidadSuscriptoraRazonSocial" class="small">Raz&oacute;n social<span class="naranja">*</span></label></td>
            <td width="35%"><s:textfield name="tramiteRegRbmDto.entidadSuscriptora.razonSocial" id="entidadSuscriptoraRazonSocial" size="18" maxlength="255"></s:textfield></td>
        </tr>
	</table>
 --%>	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos entidad sucesora</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
        <tr>
   		    <td align="left" nowrap="nowrap"><label for="entidadSucesoraCodigoIdentificacionFiscal" >C&oacute;digo identificaci&oacute;n fiscal</label></td>
            <td width="15%" align="right"><s:textfield name="tramiteRegRbmDto.entidadSucesora.codigoIdentificacionFiscal" id="entidadSucesoraCodigoIdentificacionFiscal" size="18" maxlength="9" ></s:textfield>	</td>
            <td align="left" nowrap="nowrap" width="15%">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
              		<input type="button" id="idBotonBuscarCif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
						onclick="javascript:buscarEntidad('entidadSucesoraCodigoIdentificacionFiscal', 'Sucesora')" />
				</s:if>
			</td>
            <td align="left" nowrap="nowrap"><label for="entidadSucesoraRazonSocial" class="small">Raz&oacute;n social</label></td>
            <td width="35%"><s:textfield name="tramiteRegRbmDto.entidadSucesora.razonSocial" id="entidadSucesoraRazonSocial" size="18" maxlength="255"></s:textfield></td>
        </tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del registro Mercantil de la Entidad Sucesora</span>
			</td>
		</tr>
	</table>	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     		  <td align="left" nowrap="nowrap"><label for="entidadRegistoMercantil" class="small">Registro mercantil</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.entidadSucesora.datRegMercantil.codRegistroMercantil" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                         listKey="idRegistro"
                         listValue="nombre"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         id="entidadRegistoMercantil"/></td>
              
     		  <td align="left" nowrap="nowrap"><label for="entidadTomo" class="small">Tomo inscrip.</label></td>
              <td width="35%"><s:textfield name="tramiteRegRbmDto.entidadSucesora.datRegMercantil.tomo" id="entidadTomo" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="entidadFolio" class="small">Folio inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.entidadSucesora.datRegMercantil.folio" id="entidadFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="entidadNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.entidadSucesora.datRegMercantil.numInscripcion" id="entidadNumInscripcion" size="5" maxlength="255"></s:textfield></td>
            </tr>
	</table>
	

</div>
