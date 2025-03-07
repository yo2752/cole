<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcIdDirEmplazamiento.idDireccion"/>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Dirección de la Licencia</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td  align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');" title="Ver evolución"/> 					
				</td>
			</s:if>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección Licencia</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
 		<tr>
       		<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="claseVia">Tipo de vía: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="claseVia" name="lcTramiteDto.lcIdDirEmplazamiento.tipoVia" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="nombreVia">Nombre de Vía:<span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="nombreVia" name="lcTramiteDto.lcIdDirEmplazamiento.nombreVia" size="18" maxlength="48"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codPostal">Código Postal:</label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="codPostal" name="lcTramiteDto.lcIdDirEmplazamiento.codigoPostal" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
        </tr>
       	<tr>   		
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoNumeracion">Tipo Numeración:<span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap">
   				<s:select id="tipoNumeracion" name="lcTramiteDto.lcIdDirEmplazamiento.tipoNumeracion" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>	
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numeroCalle">Número:<span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numeroCalle" name="lcTramiteDto.lcIdDirEmplazamiento.numCalle" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="calificador">Portal: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="calificador" name="lcTramiteDto.lcIdDirEmplazamiento.calificador" size="18" maxlength="2"/></td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" class="small"><label for="pisoDireccionNotLc">Planta: </label></td>
   			<td align="left" nowrap="nowrap">
				<s:select id="pisoDireccionNotLc" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcIdDirEmplazamiento.planta"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Planta"/>
			</td>
   			<td align="left" nowrap="nowrap" style="vertical-align:middle"><label for="puertaDireccionNotLc">Puerta: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="puertaDireccionNotLc" name="lcTramiteDto.lcIdDirEmplazamiento.puerta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"/></td>
   			<td align="left" nowrap="nowrap" class="small"><label for="escaleraDireccionNotLc">Escalera: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="escaleraDireccionNotLc" name="lcTramiteDto.lcIdDirEmplazamiento.escalera" size="4" maxlength="100"/></td>
   		</tr>
      	<tr>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="claseDireccion">Clase Dirección: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="claseDireccion" name="lcTramiteDto.lcIdDirEmplazamiento.claseDireccion" size="18" maxlength="1"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codDireccion">Código Dirección: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="codDireccion" name="lcTramiteDto.lcIdDirEmplazamiento.codDireccion" size="18" maxlength="8" onkeypress="return soloNumeroDecimal(event, this, '8', '0')"/></td>
      	</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
      	<tr>
			<td align="center">
				<input id="bValDirEmpl" type="button" class="botonGrande" name="bValDirEmpl" value="Validar Dirección" onClick="validarDireccion('')" onkeyPress="this.onClick"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección NO Normalizada</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="emplazamientoNoNormalizadoLc">Emplazamiento no normalizado: <span class="naranja">*</span></label> </td>
            <td colspan="3">
              <s:textfield name="lcTramiteDto.lcIdDirEmplazamiento.emplNoNormalizado" id="emplazamientoNoNormal" size="99" maxlength="210"></s:textfield>
            </td>
         </tr> 
         <tr>
            <td align="left" nowrap="nowrap"><label for="distritoLc">Distrito: </label> </td>
            <td>
              <s:textfield name="lcTramiteDto.lcIdDirEmplazamiento.distrito" id="distrito" size="40" maxlength="48"></s:textfield>
            </td>
            <td align="left" nowrap="nowrap"><label for="refCatastralLc">Ref.Catastral: </label> </td>
            <td>
              <s:textfield name="lcTramiteDto.lcIdDirEmplazamiento.referenciaCatastral" id="refCatastral" size="40" maxlength="48"></s:textfield>
            </td>
         </tr>   
         <tr>
            <td align="left" nowrap="nowrap"><label for="coordenadaXLc">Coordenada X: </label> </td>
            <td>
              <s:textfield name="lcTramiteDto.lcIdDirEmplazamiento.coordenadaX" id="coordenadaX" size="40" maxlength="48"></s:textfield>
            </td>
            <td align="left" nowrap="nowrap"><label for="coordenadaYLc">Coordenada Y: </label> </td>
            <td>
              <s:textfield name="lcTramiteDto.lcIdDirEmplazamiento.coordenadaY" id="coordenadaY" size="40" maxlength="48"></s:textfield>
            </td>
         </tr> 
         <tr>
            <td align="left" nowrap="nowrap"><label for="ambitoLc">Ámbito: </label> </td>
            <td>
              <s:textfield name="lcTramiteDto.lcIdDirEmplazamiento.ambito" id="ambito" size="40" maxlength="48"></s:textfield>
            </td>
         </tr>      
	</table>
      
 </div>
