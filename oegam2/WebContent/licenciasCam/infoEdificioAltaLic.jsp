<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="infoEdificioAltaLic">
	
		<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.idInfoEdificioAlta"/>
		<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.idDireccion"/>
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Edificio Alta Licencia </td>
			</tr>
		</table>

	     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Dirección Edificio </span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
		       		<td align="left" nowrap="nowrap" style="vertical-align:middle">
		   				<label for="claseViaInfo">Tipo de vía: <span class="naranja">*</span></label>
		   			</td>
		   			<td align="left" nowrap="nowrap">
		   				<s:select id="claseViaInfoAlta" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.tipoVia" 
		   					headerKey="" headerValue="Seleccione Tipo Vía" 
		   					listKey="codigo" listValue="descripcion"
		   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		   			</td>
		       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="nombreViaInfo">Nombre de Vía:<span class="naranja">*</span></label></td>
		       		<td align="left" nowrap="nowrap"><s:textfield id="nombreViaInfo" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.nombreVia" size="18" maxlength="48"/></td>
		       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codPostalInfo">Código Postal:</label></td>
		       		<td align="left" nowrap="nowrap"><s:textfield id="codPostalInfo" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.codigoPostal" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
		        </tr>
			<tr>   		
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoNumeracionInfo">Tipo Numeración:<span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap">
	   				<s:select id="tipoNumeracionInfoAlta" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.tipoNumeracion" 
	   					headerKey="" headerValue="Seleccione Tipo Vía" 
	   					listKey="codigo" listValue="descripcion"
	   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
	   			</td>	
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numeroCalleInfo">Número:<span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="numeroCalleInfo" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.numCalle" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="calificadorInfo">Portal: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="calificadorInfo" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.calificador" size="18" maxlength="2"/></td>
       		</tr>
       		<tr>
	       		<td align="left" nowrap="nowrap" class="small"><label for="pisoDireccionNotLc">Planta: </label></td>
	   			<td align="left" nowrap="nowrap">
					<s:select id="pisoDireccionNotLc" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.planta"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Planta"/>
				</td>
	   			<td align="left" nowrap="nowrap" style="vertical-align:middle"><label for="puertaDireccionNotLc">Puerta: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="puertaDireccionNotLc" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.puerta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"/></td>
	   			<td align="left" nowrap="nowrap" class="small"><label for="escaleraDireccionNotLc">Escalera: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="escaleraDireccionNotLc" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.escalera" size="4" maxlength="100"/></td>
   			</tr>
      		<tr>
      			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="claseDireccion">Clase Dirección: </label></td>
       			<td align="left" nowrap="nowrap"><s:textfield id="claseDireccion" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.claseDireccion" size="18" maxlength="1"/></td>
       			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codDireccion">Código Dirección: </label></td>
       			<td align="left" nowrap="nowrap"><s:textfield id="codDireccion" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDirEdificacionAlta.codDireccion" size="18" maxlength="8" onkeypress="return soloNumeroDecimal(event, this, '8', '0')"/></td>
      		</tr>
	     </table>
	     
 	     <div id="divListadoDatosPortalAlta">
			<jsp:include page="listDatosPortalesAlta.jsp" flush="false"></jsp:include>
		</div>
		
		<br/>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
				<div class="acciones center">
					<input type="button" class="boton" name="bAniadirDatosPortalesAlta" id="idAniadirDatosPortalesAlta" value="Añadir portal" onClick="javascript:mostrarDivDatosPortalAlta();"onKeyPress="this.onClick"/>
				</div>
		</s:if>
		<br/>
		
		<s:if test="mostrarDivPortalAlta">
			<div id="divDatosPortalAlta">
				<jsp:include page="datosPortalAlta.jsp" flush="false"></jsp:include>
			</div> 
		</s:if>
	</div>
</div>
