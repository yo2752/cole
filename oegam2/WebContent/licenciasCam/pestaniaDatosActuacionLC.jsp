<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcActuacion.idActuacion"/>
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Actuación</td>
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
				<span class="titulo">Datos Actuación</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoObraLc">Tipo de actuación: </label></td>
			<td align="left" nowrap="nowrap">
				<s:select id="tipoActuacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.tipoActuacion"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoActividad()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Actuación"/>
			</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoObraLc">Núm. expediente consulta: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numExpedienteConsulta" name="lcTramiteDto.lcActuacion.numExpedienteConsulta" size="18" maxlength="14"/></td>
       	 </tr> 
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="presupuestoLc">Núm. expediente licencia: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numExpedienteLicencia" name="lcTramiteDto.lcActuacion.numExpedienteLicencia" size="18" maxlength="14"/></td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="superficieLc">Núm. cc licencia: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numCcLicencia" name="lcTramiteDto.lcActuacion.numCcLicencia" size="18" maxlength="13"/></td>
         </tr>   
         <tr>   
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="andamioLc">Núm. expediente cédula: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numExpedienteCedula" name="lcTramiteDto.lcActuacion.numExpedienteCedula" size="18" maxlength="14"/></td>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="vallaLc">Núm. expediente parcelación: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numExpedienteParcelacion" name="lcTramiteDto.lcActuacion.numExpedienteParcelacion" size="18" maxlength="14"/></td>
         </tr> 
   		 <tr>   
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="instGruaLc">Núm. expediente modificar: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numExpedienteModificar" name="lcTramiteDto.lcActuacion.numExpedienteModificar" size="18" maxlength="14"/></td>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="contenedorLc">Solicita ayuda rehabilitación: </label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcActuacion.solicitudAyudaRehabilitacion" id="solAyudReh" onkeypress="this.onClick"/>
       		</td>
         </tr> 
         <tr>
        	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="modificaActividad">Modificar actividad: </label></td> 
        	<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcActuacion.modificaActividad" id="modificaActividad" onkeypress="this.onClick"/>
       		</td>
   		 </tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Solicitud conjunta autorizaciones</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numeroLcPAutonoma">Solicita otra autorización:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.solicitaOtraAutorizacion" id="numeroPAutonoma" onkeypress="this.onClick"/>
       		</td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="superficieLcPAutonoma">Ocupación viario:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.ocupacionViario" id="ocuViario" onkeypress="this.onClick"/>
       		</td>
        	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numeroLcPAutonoma">Otra: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="númeroPAutonoma" name="lcTramiteDto.otra" size="18" maxlength="60"/></td>
         </tr>   
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Otros datos actuación licencia</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="normaZonalFiguraOrd">Norma zonal: <span class="naranja">*</span></label></td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="normaZonalLC" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcActuacion.normaZonalFiguraOrdenacion"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().normaZonal()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Norma Zonal"/>
			</td>
		</tr>
       	<tr>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="nivelProteccion">Nivel protección: <span class="naranja">*</span></label></td>
            <td align="left" nowrap="nowrap" colspan="3">
				<s:select id="nivelProteccion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcActuacion.nivelProteccion"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().nivelProteccion()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Nivel de Protección"/>
			</td>
		</tr>
       	<tr>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="dotacionalTransporte">Dotacional transporte: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="dotacionalTransporte" name="lcTramiteDto.lcActuacion.dotacionalTransporte" size="3" maxlength="3"/></td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="gestionadoAdminPublica">Gestionado Admin. Pública:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcActuacion.gestionadoAdminPublica" id="gestionadoAdminPublica" onkeypress="this.onClick"/>
       		</td>
         </tr>   
	</table>
</div>
