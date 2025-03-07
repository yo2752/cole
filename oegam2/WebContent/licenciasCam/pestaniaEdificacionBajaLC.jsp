<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcEdificacionBaja.idEdificacion"/>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos para el Alta de una Edificación</td>
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
				<span class="titulo">Edificación Baja Licencia </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
       	 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblNumEdificios">Núm. edificios: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numEdificios" name="lcTramiteDto.lcEdificacionBaja.numEdificios" size="5" maxlength="2" onkeypress="return soloNumeroDecimal(event, this, '2', '0')"/></td>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoDemolicion">Tipo Demolición: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="tipoDemolicion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionBaja.tipoDemolicion"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoEdificacion()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Demolición"/>
			</td>
       	</tr>
       	 <tr> 	
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="industrial">Industrial: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="industrial" name="lcTramiteDto.lcEdificacionBaja.industrial" size="5" maxlength="1"/></td>
       	</tr>
    </table>
	<div id="divListadoInfoEdificioBaja">
		<jsp:include page="listInfoEdificioBajaLic.jsp" flush="false"></jsp:include>
	</div>
	
	<s:if test="lcTramiteDto.numExpediente">
		<br/>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
			<div class="acciones center">
				<input type="button" class="boton" name="bAniadirEdificioBaja" id="idAniadirEdificioBaja" value="Añadir edificio" onClick="javascript:mostrarDivInfoEdificioBajaLic();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
		<br/>
	</s:if>
	
	<s:if test="mostrarDivInfoEdificioBajaLicencia">
		<div id="divInfoEdificioBaja">
			<jsp:include page="infoEdificioBajaLic.jsp" flush="false"></jsp:include>
		</div>
	</s:if>

</div>