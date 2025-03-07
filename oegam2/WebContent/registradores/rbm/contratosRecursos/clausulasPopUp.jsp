<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>


<s:hidden name="tramiteRegRbmDto.clausulaDto.idClausula" />
<s:hidden name="tramiteRegRbmDto.clausulaDto.idTramiteRegistro" />
<s:hidden name="tramiteRegRbmDto.clausulaDto.fecCreacion" />

<div class="contenido">	
	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Cl&aacute;usulas:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
               <td align="left" nowrap="nowrap">
               	<label for="clausulaTipoClausula" class="small">Tipo de cl&aacute;usula<span class="naranja">*</span></label>
               </td>
               <td colspan="3">
	                <s:select name="tramiteRegRbmDto.clausulaDto.tipoClausula" 
	                          list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListClauseType()"
							  listKey="key"
	                          headerKey="" 
	                          headerValue="Seleccione el tipo de cláusula"
	                          id="clausulaTipoClausula"/>   
              	</td>
              </tr>
              <tr>
              	<td align="left" nowrap="nowrap"><label for="clausulaNumero" class="small" >N&uacute;mero cl&aacute;usula<span class="naranja">*</span></label></td>
              	<td><s:textfield name="tramiteRegRbmDto.clausulaDto.numero" id="clausulaNumero" size="18" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
              	<td align="left" nowrap="nowrap"><label for="clausulaNombre" class="small" >Nombre cl&aacute;usula<span class="naranja">*</span></label></td>
              	<td><s:textfield name="tramiteRegRbmDto.clausulaDto.nombre" id="clausulaNombre" size="18" maxlength="255"></s:textfield></td>
             </tr>
        </table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
   			<tr>
               <td align="left" nowrap="nowrap"><label for="clausulaDescripcion" >Descripci&oacute;n<span class="naranja">*</span></label>
               	<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n literal del contenido de la cl&aacute;usula"/>
               </td>
               <td>&nbsp;</td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap">
					<s:textarea name="tramiteRegRbmDto.clausulaDto.descripcion" id="clausulaDescripcion" cols="100" rows="5"></s:textarea>
               </td>
               <td>&nbsp;</td>
             </tr>
		</table>
</div>
