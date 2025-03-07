<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
		<s:hidden name="tramiteRegRbmDto.propiedadDto.aeronave.idAeronave"/>
		<s:hidden name="tramiteRegRbmDto.propiedadDto.aeronave.idPropiedad"/>
		
	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Aeronave:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
	      		   <td align="left" nowrap="nowrap"><label for="aeronaveTipo" >Tipo<span class="naranja">*</span></label>
	      		   		<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de aeronave" />
	      		   </td>
	               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.aeronave.tipo" id="aeronaveTipo" size="18" maxlength="255"></s:textfield>	</td>
	               <td align="left" nowrap="nowrap"><label for="aeronaveMarca" class="small">Marca<span class="naranja">*</span></label></td>
	               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.aeronave.marca" id="aeronaveMarca" size="18" maxlength="255"></s:textfield></td>
             </tr>
             <tr>
	               <td align="left" nowrap="nowrap"><label for="aeronaveModelo" class="small">Modelo<span class="naranja">*</span></label></td>
	               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.aeronave.modelo" id="aeronaveModelo" size="18" maxlength="255"></s:textfield></td>
	               <td align="left" nowrap="nowrap"><label for="aeronaveMatricula" class="small">Matr&iacute;cula<span class="naranja">*</span></label></td>
	               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.aeronave.matricula" id="aeronaveMatricula" size="18" maxlength="255" ></s:textfield>	</td>
             </tr>
             <tr>
            	   <td align="left" nowrap="nowrap"><label for="aeronaveNumSerie" >Serie<span class="naranja">*</span></label>
            	   		<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de serie o fabricaci&oacute;n de la aeronave"/>
            	   </td>
                   <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.aeronave.numSerie" id="aeronaveNumSerie" size="18" maxlength="255"></s:textfield>	</td>
            </tr>
  		</table>
