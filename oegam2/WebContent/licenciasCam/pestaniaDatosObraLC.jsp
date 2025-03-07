<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcObra.idDatosObra"/>
	<s:hidden name="lcTramiteDto.lcObra.parteAutonoma.idParteAutonoma"/>
	<s:hidden name="lcTramiteDto.lcObra.tipoObra" />

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Obra</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td  align="right">
					<img src="img/history.png" alt="ver evoluci�n" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');" title="Ver evoluci�n"/> 					
				</td>
			</s:if>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Obra</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoObraLc">Tipo de obra: <span class="naranja">*</span></label></td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="tipoObra" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcObra.tipoObraPantalla"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoObra()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Obra"/>
			</td>
       	</tr> 
    </table>
    <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="listTiposObra.jsp" />
        	</td>
        </tr>
	</table>
	<br>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoObraLc">Ocupaci�n v�a p�blica:</label></td>
			<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcObra.ocupacionViaPublica" id="ocuViaPub" onkeypress="this.onClick"/>
       		</td>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="presupuestoLc">Presupuesto Total: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="presupuestoTotal" name="lcTramiteDto.lcObra.presupuestoTotal" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
         </tr> 
       	 <tr> 
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="superficieLc">Superficie afectada: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="superficieAfectada" name="lcTramiteDto.lcObra.superficieAfectada" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
         </tr> 
       	 <tr>  
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="andamioLc">Andamios: </label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcObra.andamios" id="andamios" onkeypress="this.onClick" onclick="javascript:tratarCampoSegunCheck('andamios','duracionAndamios');"/>
       		</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="duracionAndamiosLc">Meses duraci�n andamios: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="duracionAndamios" name="lcTramiteDto.lcObra.duracionAndamioMeses" size="5" maxlength="4" disabled="true" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td>
		</tr> 
       	<tr> 
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="vallaLc">Vallas: </label></td>
			<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcObra.vallas" id="vallas" onkeypress="this.onClick" onclick="javascript:tratarCampoSegunCheck('vallas','duracionValla');"/>
       		</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="duracionVallaLc">Meses duraci�n vallas: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="duracionValla" name="lcTramiteDto.lcObra.duracionVallaMeses" size="5" maxlength="4" disabled="true" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td>  
        </tr> 
       	<tr>    
            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="instGruaLc">Instalaci�n gr�as: </label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcObra.instalacionGrua" id="instGrua" onkeypress="this.onClick" onclick="javascript:tratarCampoSegunCheck('instGrua','duracionGrua');"/>
       		</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="duracionGruaLc">Meses duraci�n gr�a: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="duracionGrua" name="lcTramiteDto.lcObra.duracionGruaMeses" size="5" maxlength="4" disabled="true" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td>
		</tr> 
       	 <tr> 
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="contenedorLc">Contenedor v�a p�blica: </label></td>
            <td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcObra.contenedorViaPublica" id="contenedor" onkeypress="this.onClick" onclick="javascript:tratarCampoSegunCheck('contenedor','duracionContenedor');"/>
       		</td>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="duracionContenedorLc">Meses duraci�n contenedor: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="duracionContenedor" name="lcTramiteDto.lcObra.duracionContenedorMeses" size="5" maxlength="4" disabled="true" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td> 
		</tr> 
       	<tr>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="plazoInicio">Plazo inicio: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="plazoInicio" name="lcTramiteDto.lcObra.plazoInicio" size="5" maxlength="3" onkeypress="return soloNumeroDecimal(event, this, '3', '0')"/></td>
			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="plazoEjecucion">Plazo ejecuci�n: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="plazoEjecucion" name="lcTramiteDto.lcObra.plazoEjecucion" size="5" maxlength="3" onkeypress="return soloNumeroDecimal(event, this, '3', '0')"/></td>
         </tr> 
		 <tr>
         	<td align="left"><br><br><br>
				<label for="labelRespuesta">Descripci�n Obra: <span class="naranja">*</span></label>
			</td>
			<td align="left"  nowrap="nowrap" colspan="3">
				<s:textarea name="lcTramiteDto.lcObra.descripcionObra" id="descripcionObra" disabled="false"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';"  rows="4" cols="60"
				onpaste="return comprobarTamanioTextArea(event, 600);"
				onkeypress="return comprobarTamanioTextArea(event, 600);"/>
			</td>	
         </tr> 
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Parte Aut�noma</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap" style="vertical-align:middle"><label for="numeroLcPAutonoma">N�mero: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numeroPAutonoma" name="lcTramiteDto.lcObra.parteAutonoma.numero" size="3" maxlength="3" onkeypress="return soloNumeroDecimal(event, this, '3', '0')"/></td>
         </tr>
       	 <tr > 
            <td align="left" nowrap="nowrap" style="vertical-align:middle"><label for="superficieLcPAutonoma">Descripci�n: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield name="lcTramiteDto.lcObra.parteAutonoma.descripcion" id="descripcion" size="100" maxlength="100"/>
			</td>
         </tr>   
	</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista partes aut�nomas</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="listPartesAutonomas.jsp" />
        	</td>
        </tr>
	</table>
</div>
