<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
		<s:hidden name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.idPropiedadIndustrial"/>
		<s:hidden name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.idPropiedad"/>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Propiedad industrial:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      			<td align="left" nowrap="nowrap"><label for="propiedadIndustrialNombreMarca" >Nombre marca<span class="naranja">*</span></label>
      				<img src="img/botonDameInfo.gif" alt="Info" title="Nombre de la marca o patente" />
      			</td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.nombreMarca" id="propiedadIndustrialTipo" size="18" maxlength="255"></s:textfield>	</td>
               <td align="left" nowrap="nowrap"><label for="propiedadIndustrialTipoMarca" class="small" >Tipo marca</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de marca o patente" />
               </td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.tipoMarca" id="propiedadIndustrialTipoMarca" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="propiedadIndustrialNumRegAdm" class="small">N&uacute;mero registro administrativo<span class="naranja">*</span></label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.numRegAdm" id="propiedadIndustrialNumRegAdm" size="18" maxlength="255"></s:textfield></td>
      
      		  <td align="left" nowrap="nowrap"><label for="propiedadIndustrialClaseMarca" >Clase marca</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.claseMarca" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListClasePropiedadIndustrial()" 
                         listKey="key"
                		 headerKey="" 
                		 headerValue="Seleccionar clase"
                         id="propiedadIndustrialClaseMarca"/>
               </td>
            </tr>
            <tr>
            	<td align="left" nowrap="nowrap"><label for="propiedadIndustrialSubClaseMarca" >Subclase marca</label></td>
              	<td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.subClaseMarca" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListSubClasePropiedadIndustrial()" 
                         listKey="key"
                		 headerKey="" 
                		 headerValue="Seleccionar subclase"
                         id="propiedadIndustrialSubClaseMarca"/>
               	</td>
            	<td align="left" nowrap="nowrap"><label for="propiedadIndustrialDescripcion" >Descripci&oacute;n<span class="naranja">*</span></label>
            		<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n general de la marca o patente" />
            	</td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.propiedadIndustrial.descripcion" id="propiedadIndustrialDescripcion" size="18" maxlength="255"></s:textfield>	</td>
            </tr>
  		</table>
