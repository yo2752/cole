<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
	<s:hidden name="tramiteRegRbmDto.propiedadDto.propiedadIntelectual.idPropiedadIntelectual"/>
	<s:hidden name="tramiteRegRbmDto.propiedadDto.propiedadIntelectual.idPropiedad"/>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Propiedad intelectual:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
          <tr>
             <td align="left" nowrap="nowrap"><label for="propiedadIntelectualNumRegAdm" class="small">N&uacute;mero registro administrativo<span class="naranja">*</span></label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.propiedadIntelectual.numRegAdm" id="propiedadIntelectualNumRegAdm" size="18" maxlength="255"></s:textfield></td>
     
     		 <td align="left" nowrap="nowrap"><label for="propiedadIntelectualClase" >Clase</label></td>
             <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.propiedadIntelectual.clase" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListClasePropiedadIntelectual()" 
                        listKey="key"
               		 	headerKey="" 
               		 	headerValue="Seleccionar clase"
                        id="propiedadIntelectualClase"/>
             </td>
         </tr>
         <tr>
           	<td align="left" nowrap="nowrap"><label for="propiedadIntelectualDescripcion" >Descripci&oacute;n<span class="naranja">*</span></label>
           			<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n general propiedad intelectual" />
           	</td>
             <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.propiedadIntelectual.descripcion" id="propiedadIntelectualDescripcion" size="18" maxlength="255"></s:textfield>	</td>
         </tr>
 	</table>
