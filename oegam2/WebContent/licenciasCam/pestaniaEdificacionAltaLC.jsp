<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcEdificacionAlta.idEdificacion"/>
	
	<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.idResumenEdificacion"/>
	<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.idResumenEdificacion"/>
	<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.idResumenEdificacion"/>
	<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.idResumenEdificacion"/>
	
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
				<span class="titulo">Datos Edificación Alta</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblNumEdificios">Núm. edificios: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numEdificios" name="lcTramiteDto.lcEdificacionAlta.numEdificios" size="5" maxlength="2" onkeypress="return soloNumeroDecimal(event, this, '2', '0')"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipologia">Tipología: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="tipologia" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionAlta.tipologia"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoEdificacion()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipología"/>
			</td>
       	 </tr> 
       	 <tr>
       	 	<td align="left" nowrap="nowrap"><br><br><br>
       	 		<label for="lblDescAltaEdificacion">Descripción: <span class="naranja">*</span></label>
       	 	</td>
       	 	<td align="left"  nowrap="nowrap" colspan="3">
				<s:textarea name="lcTramiteDto.lcEdificacionAlta.descripcion" id="descAltaEdificacion" disabled="false"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';"  rows="4" cols="60"
				onpaste="return comprobarTamanioTextArea(event, 600);"
				onkeypress="return comprobarTamanioTextArea(event, 600);"/>
			</td>	
       	 </tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Aparcamiento Edificio</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblDotacional">Dotacional: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="dotacional" name="lcTramiteDto.lcEdificacionAlta.dotacionalEnEdificio" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="LibreDisposicion">Libre Disposición: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="libreDisposicion" name="lcTramiteDto.lcEdificacionAlta.libreDisposicionEnEdificio" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
         </tr>   
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Aparcamiento Superficie</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblDotacional">Dotacional: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="dotacional" name="lcTramiteDto.lcEdificacionAlta.dotacionalEnSuperficie" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="LibreDisposicion">Libre Disposición: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="libreDisposicion" name="lcTramiteDto.lcEdificacionAlta.libreDisposicionEnSuperficie" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
         </tr>   
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Aparcamiento Discapacitados</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblDotacional">Dotacional: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="dotacional" name="lcTramiteDto.lcEdificacionAlta.dotacionalDispacitados" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="LibreDisposicion">Libre Disposición: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="libreDisposicion" name="lcTramiteDto.lcEdificacionAlta.libreDisposicionDiscapacitados" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
         </tr>   
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Proyecto Garaje Aparcamiento Alta</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSuperaSeisMil">Supera 6000: </label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcEdificacionAlta.superaSeisMil" id="superaSeisMil" onkeypress="this.onClick"/>
       		</td>
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSuperaDoceMil">Supera 12000: </label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcEdificacionAlta.superaDoceMil" id="superaDoceMil" onkeypress="this.onClick"/>
       		</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblEspecialUrbanistico">Plan Especial Urbanístico: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="planEspUrban" name="lcTramiteDto.lcEdificacionAlta.expPlanEspecialUrbanistico" size="20" maxlength="14"/></td>
         </tr>   
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Resumen Edificación Viviendas</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
 		<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Bajo Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.numUnidadesBajoRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Bajo Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.supConstruidaBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Bajo Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.supComputableBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
      	<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Sobre Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.numUnidadesSobreRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Sobre Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.supConstruidaSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Sobre Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionVivienda.supComputableSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
    </table>
    <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Resumen Edificación Locales</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
 		<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Bajo Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.numUnidadesBajoRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Bajo Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.supConstruidaBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Bajo Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.supComputableBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
      	<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Sobre Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.numUnidadesSobreRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Sobre Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.supConstruidaSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Sobre Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionLocal.supComputableSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
    </table>
    <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Resumen Edificación Garajes</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
 		<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Bajo Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.numUnidadesBajoRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Bajo Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.supConstruidaBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Bajo Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.supComputableBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
      	<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Sobre Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.numUnidadesSobreRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Sobre Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.supConstruidaSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Sobre Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionGaraje.supComputableSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
    </table>
    <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Resumen Edificación Trasteros</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
 		<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Bajo Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.numUnidadesBajoRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Bajo Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.supConstruidaBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Bajo Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.supComputableBajoRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
      	<tr> 	
		 	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Sobre Rasante Unidades: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.numUnidadesSobreRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
           	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Sobre Rasante Sup Construida: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.supConstruidaSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Sobre Rasante Sup Computable: <span class="naranja">*</span></label></td>
      		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcResumenEdificacionTrastero.supComputableSobreRasante" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      	</tr>
    </table>
	<div id="divListadoInfoEdificioAlta">
		<jsp:include page="listInfoEdificioAltaLic.jsp" flush="false"></jsp:include>
	</div>
	
	<s:if test="lcTramiteDto.numExpediente">
		<br/>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
			<div class="acciones center">
				<input type="button" class="boton" name="bAniadirEdificioAlta" id="idAniadirEdificioAlta" value="Añadir edificio" onClick="javascript:mostrarDivInfoEdificioAltaLic();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
		<br/>
	</s:if>
	
	<s:if test="mostrarDivInfoEdificioAltaLicencia">
		<div id="divInfoEdificioAlta">
			<jsp:include page="infoEdificioAltaLic.jsp" flush="false"></jsp:include>
		</div>
	</s:if>

</div>