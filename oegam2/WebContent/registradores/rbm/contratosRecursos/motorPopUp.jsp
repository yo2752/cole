<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		

	<s:hidden name="tramiteRegRbmDto.propiedadDto.buque.motor.idMotor"/>
	<s:hidden name="tramiteRegRbmDto.propiedadDto.buque.motor.idBuque"/>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Motores:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
          <tr>
    		 <td align="left" nowrap="nowrap"><label for="motorTipo" >Tipo<span class="naranja">*</span></label>
    		 	<img src="img/botonDameInfo.gif" alt="Info" title="Tipo de motor"/>
    		 </td>
             <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.tipo" id="motorTipo" size="18" maxlength="255"></s:textfield>	</td>
             <td align="left" nowrap="nowrap"><label for="motorMarca" class="small">Marca<span class="naranja">*</span></label></td>
             <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.marca" id="motorMarca" size="18" maxlength="255"></s:textfield></td>
          </tr>
          <tr>
             <td align="left" nowrap="nowrap"><label for="motorModelo" class="small">Modelo<span class="naranja">*</span></label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.modelo" id="motorModelo" size="18" maxlength="255"></s:textfield></td>
             <td align="left" nowrap="nowrap"><label for="buqueNumSerie" class="small">Serie</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.numSerie" id="buqueNumSerie" size="18" maxlength="255" ></s:textfield>	</td>
          </tr>
          <tr>
              <td align="left" nowrap="nowrap"><label for="motorAnioConstruccion" class="small">Año construcci&oacute;n</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.anioConstruccion" id="motorAnioConstruccion" size="18" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"></s:textfield></td>
             <td align="left" nowrap="nowrap"><label for="motorPotenciaKw">Potencia KW</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.PotenciaKw" id="motorPotenciaKw" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')" ></s:textfield>	</td>
          </tr>
           <tr>
             <td align="left" nowrap="nowrap"><label for="motorPotenciaCV" >Potencia CV</label>
             		<img src="img/botonDameInfo.gif" alt="Info" title="Potencia en caballos de vapor"/>
             </td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.motor.PotenciaCv" id="motorPotenciaCv" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')" ></s:textfield>	</td>
          </tr>
            
 	</table>
