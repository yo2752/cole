<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcInfoLocal.idInfoLocal"/>
	<s:hidden name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.idDireccion"/>
	<s:hidden name="lcTramiteDto.lcInfoLocal.lcEpigrafeDto.idEpigrafe"/>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Local</td>
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
				<span class="titulo">Informaci�n Local Licencia </span>
			</td>
		</tr>
	</table>
      <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	<tr>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="localizacion">Localizaci�n: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="localizacion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcInfoLocal.localizacion"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tiposLocal()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Localizaci�n"/>
			</td>
       	</tr>
       	<tr>	
       		<td width="25%" align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codLocal">C�digo Local: </label></td>
       		<td width="25%" align="left" nowrap="nowrap"><s:textfield id="codLocal" name="lcTramiteDto.lcInfoLocal.codLocal" size="18" maxlength="8"/></td>
       		<td width="25%" align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="nombreAgrupacion">Nombre Agrupaci�n: </label></td>
       		<td width="25%" align="left" nowrap="nowrap"><s:textfield id="nombreAgrupacion" name="lcTramiteDto.lcInfoLocal.nombreAgrupacion" size="18" maxlength="50"/></td>
       	</tr>
       	<tr>	
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="planta">Planta: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="planta" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcInfoLocal.planta"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Planta"/>
			</td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numLocal">N�mero Local: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numLocal" name="lcTramiteDto.lcInfoLocal.numLocal" size="18" maxlength="5"/></td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="ecalera">Escalera: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="ecalera" name="lcTramiteDto.lcInfoLocal.escalera" size="18" maxlength="4"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="puerta">Puerta: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="puerta" name="lcTramiteDto.lcInfoLocal.puerta" size="18" maxlength="4"/></td>
       	</tr>
       	<tr>	
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="referenciaCatastral">Referencia Catastral: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="referenciaCatastral" name="lcTramiteDto.lcInfoLocal.referenciaCatastral" size="18" maxlength="20"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="superficieUtilLocal">Superficie �til Local: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="superficieUtilLocal" name="lcTramiteDto.lcInfoLocal.superficieUtilLocal" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
       	</tr>
       	<tr>	
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="superficieUtilUsoPublico">Superficie �til Uso P�blico: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="superficieUtilUsoPublico" name="lcTramiteDto.lcInfoLocal.superficieUtilUsoPublico" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="presupuestoObraActividad">Presupuesto Obra Actividad: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="presupuestoObraActividad" name="lcTramiteDto.lcInfoLocal.presupuestoObraActividad" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="potenciaNominal">Potencia Nominal: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="potenciaNominal" name="lcTramiteDto.lcInfoLocal.potenciaNominal" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="kwa">KWA: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="kwa" name="lcTramiteDto.lcInfoLocal.kwa" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
       	</tr>
       	<tr>	
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="descripcionActividad">Descripci�n Actividad: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="descripcionActividad" name="lcTramiteDto.lcInfoLocal.descripcionActividad" size="18" maxlength="600"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="solicitaRotulo">Solicita R�tulo: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcInfoLocal.solicitaRotulo" id="solicitaRotulo" onkeypress="this.onClick"/>
       		</td>
       </tr>
       	<tr>		
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="rotuloSolicitado">R�tulo Solicitado: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="rotuloSolicitado" name="lcTramiteDto.lcInfoLocal.rotuloSolicitado" size="18" maxlength="70"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="actividadAnterior">Actividad Anterior: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="actividadAnterior" name="lcTramiteDto.lcInfoLocal.actividadAnterior" size="18" maxlength="70"/></td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="expedienteActividadAnterior">Expediente Actividad Anterior: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="expedienteActividadAnterior" name="lcTramiteDto.lcInfoLocal.expedienteActividadAnterior" size="18" maxlength="14"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="sujetaAOtros">Sujeta a Otros: <span class="naranja">*</span></label></td>
   			<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcInfoLocal.sujetaAOtros" id="sujetaAOtros" onkeypress="this.onClick"/>
       		</td>
   		</tr>
       	<tr>	
   			<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="descOtros">Descripci�n Otros: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="descOtros" name="lcTramiteDto.lcInfoLocal.descOtros" size="18" maxlength="600"/></td>
       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="idLocal">Id. Local: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="idLocal" name="lcTramiteDto.lcInfoLocal.idLocal" size="18" maxlength="17" onkeypress="return soloNumeroDecimal(event, this, '17', '0')"/></td>
       	</tr>
     </table>
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci�n Local</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr> 	
				<td align="left" nowrap="nowrap" width="25%" style="vertical-align:middle"><label for="accesoPrincipalIgual">Acceso Principal Igual: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap">
	       			<s:checkbox name="lcTramiteDto.lcInfoLocal.accesoPrincipalIgual" id="accesoPrincipalIgual" onclick="javascript:ckeckAccesoPrincipal('accesoPrincipalIgual', 'divAccesoPrincipalIgual');" onkeypress="this.onClick"/>
	       		</td>
	       		
	       	</tr>
	</table>
	<div id="divAccesoPrincipalIgual">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	<tr> 	
				<td width="25%" align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="claseVia">Tipo V�a:<span class="naranja">*</span></label></td>
				<td align="left" width="25%" nowrap="nowrap">
	   				<s:select id="claseViaDirLocal" name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.tipoVia" 
	   					headerKey="" headerValue="Seleccione Tipo V�a" 
	   					listKey="codigo" listValue="descripcion"
	   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
	   			</td>
	       		<td width="25%" align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="nombreVia">Nombre V�a:<span class="naranja">*</span></label></td>
	       		<td width="25%" align="left" nowrap="nowrap"><s:textfield id="nombreVia" name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.nombreVia" size="18" maxlength="48"/></td>
	        </tr>
	       	<tr>   		
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="tipoNumeracion">Tipo Numeraci�n:<span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap">
	   				<s:select id="tipoNumeracionDirLocal" name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.tipoNumeracion" 
	   					headerKey="" headerValue="Seleccione Tipo V�a" 
	   					listKey="codigo" listValue="descripcion"
	   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
	   			</td>	
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="numeroCalle">N�mero Calle:<span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="numeroCalle" name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.numCalle" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
	       	</tr>
	       	<tr>
	       	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="codDireccion">C�digo Direcci�n: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="codDireccion" name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.codDireccion" size="18" maxlength="8" onkeypress="return soloNumeroDecimal(event, this, '8', '0')"/></td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="claseDireccion">Clase Direcci�n: </label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="claseDireccion" name="lcTramiteDto.lcInfoLocal.lcDirInfoLocal.claseDireccion" size="18" maxlength="1"/></td>
	       	</tr>
	      </table>
      </div>
	
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Ep�grafe Licencia </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%" style="vertical-align:middle"><label for="seccion">Secci�n: </label></td>
       		<td align="left" nowrap="nowrap"><s:textfield id="seccion" name="lcTramiteDto.lcInfoLocal.lcEpigrafeDto.seccion" size="18" maxlength="1"/></td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" width="25%" style="vertical-align:middle"><label for="epigrafe">Ep�grafe: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="epigrafe" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.lcInfoLocal.lcEpigrafeDto.epigrafe"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().epigrafes()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Ep�grafe"/>
			</td>
       	</tr>
     </table>
     
   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Datos Ep�grafe Licencia</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="listEpigrafes.jsp" />
        	</td>
        </tr>
	</table>
</div>
