<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="infoEdificioBajaLic">
	
		<s:hidden name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.idInfoEdificioBaja"/>
		<s:hidden name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.idDireccion"/>
	
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Edificio Baja Licencia </td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
				<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numEdificio">Número Edificio: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="numEdificio" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.numEdificio" size="5" maxlength="2" onkeypress="return soloNumeroDecimal(event, this, '2', '0')"/></td>
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
		   				<s:select id="claseViaInfoBaja" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.tipoVia" 
		   					headerKey="" headerValue="Seleccione Tipo Vía" 
		   					listKey="codigo" listValue="descripcion"
		   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		   			</td>
		       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="nombreViaInfo">Nombre de Vía:<span class="naranja">*</span></label></td>
		       		<td align="left" nowrap="nowrap"><s:textfield id="nombreViaInfo" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.nombreVia" size="18" maxlength="48"/></td>
		       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codPostalInfo">Código Postal:</label></td>
		       		<td align="left" nowrap="nowrap"><s:textfield id="codPostalInfo" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.codigoPostal" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
		    </tr>
			<tr>   		
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoNumeracionInfo">Tipo Numeración:<span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap">
	   				<s:select id="tipoNumeracionInfoBaja" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.tipoNumeracion" 
	   					headerKey="" headerValue="Seleccione Tipo Vía" 
	   					listKey="codigo" listValue="descripcion"
	   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
	   			</td>	
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numeroCalleInfo">Número:<span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="numeroCalleInfo" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.numCalle" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="calificadorInfo">Portal: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="calificadorInfo" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.calificador" size="18" maxlength="2"/></td>
       		</tr>
       		<tr>
	       		<td align="left" nowrap="nowrap" class="small"><label for="pisoDireccionNotLc">Planta: </label></td>
	   			<td align="left" nowrap="nowrap">
					<s:select id="pisoDireccionNotLc" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.planta"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Planta"/>
				</td>
	   			<td align="left" nowrap="nowrap" style="vertical-align:middle"><label for="puertaDireccionNotLc">Puerta: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="puertaDireccionNotLc" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.puerta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"/></td>
	   			<td align="left" nowrap="nowrap" class="small"><label for="escaleraDireccionNotLc">Escalera: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="escaleraDireccionNotLc" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.escalera" size="4" maxlength="100"/></td>
   			</tr>
      		<tr>
      			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="claseDireccion">Clase Dirección: </label></td>
       			<td align="left" nowrap="nowrap"><s:textfield id="claseDireccion" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.claseDireccion" size="18" maxlength="1"/></td>
       			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codDireccion">Código Dirección: </label></td>
       			<td align="left" nowrap="nowrap"><s:textfield id="codDireccion" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDirEdificacion.codDireccion" size="18" maxlength="8" onkeypress="return soloNumeroDecimal(event, this, '8', '0')"/></td>
      		</tr>
	     </table>
	     <div id="divListadoDatosPlantasBaja">
			<jsp:include page="listDatosPlantasBaja.jsp" flush="false"></jsp:include>
		</div>
		
		<br/>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
			<div class="acciones center">
				<input type="button" class="boton" name="bAniadirDatosPlantasBaja" id="idAniadirDatosPlantasBaja" value="Añadir planta" onClick="javascript:mostrarDivDatosPantasBaja();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
		<br/>

		<s:if test="mostrarDivPlantaBaja">
			<div id="divDatosPlantasBaja">
				<jsp:include page="datosPlantasBaja.jsp" flush="false"></jsp:include>
			</div>
		</s:if>
	</div>
</div>
