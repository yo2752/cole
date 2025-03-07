<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<s:hidden name="lcTramiteDto.lcDatosSuministros.idDatosSuministros"/>

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Climatizaci�n</td>
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
				<span class="titulo">Equipos Instalados Relevantes </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%" style="vertical-align:middle"><label for="hayInstalacionesRelevantes">Hay Instalaciones Relevantes:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.hayInstalacionesRelevantes" id="hayInstalacionesRelevantes" onkeypress="this.onClick" onclick="javascript:tratarDivSegunCheck('hayInstalacionesRelevantes','divEquiposInstaladosRelevantes');"/>
       		</td>
       	</tr>
   	</table>
	<div id="divEquiposInstaladosRelevantes">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equiposInstRadioactivas">Equipos Instalados Radioactivos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="equiposInstRadioactivas" name="lcTramiteDto.lcDatosSuministros.equiposInstRadioactivas" size="5" maxlength="2"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for=equiposRayosUVA>Equipos Rayos UVA: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="equiposRayosUVA" name="lcTramiteDto.lcDatosSuministros.equiposRayosUva" size="5" maxlength="2"/></td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equiposRayosLaser">Equipos Rayos L�ser: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="equiposRayosLaser" name="lcTramiteDto.lcDatosSuministros.equiposRayosLaser" size="5" maxlength="2"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaEquiposRayosLaser">Potencia Equipos Rayos L�ser: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaEquiposRayosLaser" name="lcTramiteDto.lcDatosSuministros.potenciaEquiposRayosLaser" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equiposAudiovisuales">Equipos Audiovisuales: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="equiposAudiovisuales" name="lcTramiteDto.lcDatosSuministros.equiposAudiovisuales" size="18" maxlength="2"/></td>
				<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaTotal">Potencia Total: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaTotal" name="lcTramiteDto.lcDatosSuministros.potenciaTotal" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr> 	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCentroTrans">Potencia Centro Transformador: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCentroTrans" name="lcTramiteDto.lcDatosSuministros.potenciaCentroTransformador" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	     </table> 
	</div>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Ventilaci�n Forzada </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="ventilacionForzada">Ventilaci�n Forzada:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.ventilacionForzada" id="ventilacionForzada" onkeypress="this.onClick" onclick="javascript:tratarDivSegunCheck('ventilacionForzada','divVentilacionForzada');"/>
       		</td>
       	</tr>
   	</table>
	<div id="divVentilacionForzada">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	<tr> 	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="caudal">Caudal: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="caudal" name="lcTramiteDto.lcDatosSuministros.caudal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '2', '2')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="evacuacionFachada">Evacuaci�n Fachada: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="evacuacionFachada" name="lcTramiteDto.lcDatosSuministros.evacuacionFachada" size="5" maxlength="1"/></td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="evacuacionCubierta">Evacuaci�n Cubierta: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="evacuacionCubierta" name="lcTramiteDto.lcDatosSuministros.evacuacionCubierta" size="5" maxlength="1"/></td>
	       	</tr>
	     </table>
	 </div>
	 
	 
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Agua Caliente Sanitaria </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="aguaCalienteSanitaria">Agua caliente sanitaria:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.aguaCalienteSanitaria" id="aguaCalienteSanitaria" onkeypress="this.onClick" onclick="javascript:tratarDivSegunCheck('aguaCalienteSanitaria','divAguaCalienteSanitaria');"/>
       		</td>
       	</tr>
   	</table>
	<div id="divAguaCalienteSanitaria">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
				
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="tipoCombustible">Tipo Combustible: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
					<s:select id="tipoCombustibleAguaC" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcDatosSuministros.tipoCombustibleAguaC"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoCombustible()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Combustible"/>
				</td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaAguaCaliente">Potencia Agua Caliente: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaAguaCaliente" name="lcTramiteDto.lcDatosSuministros.potenciaAguaCaliente" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
		    </tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="acumuladorCalor">Acumulador Calor: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="acumuladorCalor" name="lcTramiteDto.lcDatosSuministros.acumuladorCalorAguaC" size="18" maxlength="1"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaAcumulador">Potencia Acumulador: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaAcumulador" name="lcTramiteDto.lcDatosSuministros.potenciaAcumuladorAguaC" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	     </table>
	 </div>
	 
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Instalaciones Calefacci�n </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="instalacionesCalefaccion">Instalaciones Calefacci�n:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.instalacionesCalefaccion" id="instalacionesCalefaccion" onkeypress="this.onClick" onclick="javascript:tratarDivSegunCheck('instalacionesCalefaccion','divInstalacionesCalefaccion');"/>
       		</td>
       	</tr>
   	</table>
   	
	<div id="divInstalacionesCalefaccion">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="tipoCombustible">Tipo Combustible: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
					<s:select id="tipoCombustibleCalefcc" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcDatosSuministros.tipoCombustibleCalefcc"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoCombustible()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Combustible"/>
				</td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalefaccion">Potencia Calefacci�n: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalefaccion" name="lcTramiteDto.lcDatosSuministros.potenciaCalefaccion" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr>		
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalorInstGen">Potencia Calor�fica Instalaci�n General: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalorInstGen" name="lcTramiteDto.lcDatosSuministros.potenciaCalorInstGenCalefcc" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>

	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equipoCentralizadoLocal">Equipo Centralizado Local: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.equipoCentralizadoLocalCalefcc" id="equipoCentralizadoLocal" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalorEqCentr">Potencia Calor�fica Equipo Centralizado: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalorEqCentrCalefcc" name="lcTramiteDto.lcDatosSuministros.potenciaCalorEqCentrCalefcc" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="instalacionesGeneralEdificio">Instalaciones General Edificio: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.instalacionesGeneralEdificioCalefcc" id="instalacionesGeneralEdificioCalefcc" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	   	</table>
	</div>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Climatizaci�n Aire Acondicionado </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="climatizacionAc">Climatizaci�n aire acondicionado:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.climatizacionAc" id="climatizacionAc" onkeypress="this.onClick" onclick="javascript:tratarDivSegunCheck('climatizacionAc','divClimatizacionAireAcondicionado');"/>
       		</td>
       	</tr>
   	</table>
	<div id="divClimatizacionAireAcondicionado">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="instalacionesGeneralEdificio">Instalaciones General Edificio: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.instalacionesGeneralEdificio" id="instalacionesGeneralEdificio" onkeypress="this.onClick"/>
	       		</td>
	       
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalorInstGen">Potencia Calor�fica Instalaci�n General: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalorInstGen" name="lcTramiteDto.lcDatosSuministros.potenciaCalorInstGen" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaFrigoInstGen">Potencia Frigor�fica Instalaci�n General: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaFrigoInstGen" name="lcTramiteDto.lcDatosSuministros.potenciaFrigoInstGen" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equipoCentralizadoLocal">Equipo Centralizado Local: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.equipoCentralizadoLocal" id="equipoCentralizadoLocal" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalorEqCentr">Potencia Calor�fica Equipo Centralizado: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalorEqCentr" name="lcTramiteDto.lcDatosSuministros.potenciaCalorEqCentr" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaFrigoEqCentr">Potencia Frigor�fica Equipo Centralizado: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaFrigoEqCentr" name="lcTramiteDto.lcDatosSuministros.potenciaFrigoEqCentr" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="hayEquiposAutonomos">Hay Equipos Aut�nomos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.hayEquiposAutonomos" id="hayEquiposAutonomos" onkeypress="this.onClick"/>
	       		</td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equiposAutonomos">Equipos Aut�nomos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="equiposAutonomos" name="lcTramiteDto.lcDatosSuministros.equiposAutonomos" size="18" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaFrigoEqAutonomos">Potencia Frigor�fica Equipos Aut�nomos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaFrigoEqAutonomos" name="lcTramiteDto.lcDatosSuministros.potenciaFrigoEqAutonomos" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="hayEnfriadoresEvaporativos">Hay Enfriadores Evaporativos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.hayEnfriadoresEvaporativos" id="hayEnfriadoresEvaporativos" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="enfriadoresEvaporativos">Enfriadores Evaporativos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="enfriadoresEvaporativos" name="lcTramiteDto.lcDatosSuministros.enfriadoresEvaporativos" size="18" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalorEnfriadores">Potencia Calor�fica Enfriadores: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalorEnfriadores" name="lcTramiteDto.lcDatosSuministros.potenciaCalorEnfriadores" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaFrigoEnfriadores">Potencia Frigor�fica Enfriadores: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaFrigoEnfriadores" name="lcTramiteDto.lcDatosSuministros.potenciaFrigoEnfriadores" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       		
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="condensacionSalidaFachada">Condensaci�n Aire Salida Fachada: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.condensacionSalidaFachada" id="condensacionSalidaFachada" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="caudalSalidaFachada">Caudal Salida Fachada: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="caudalSalidaFachada" name="lcTramiteDto.lcDatosSuministros.caudalSalidaFachada" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '2', '2')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="condensacionSalidaChimenea">Condensaci�n Aire Salida Chimenea: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.condensacionSalidaChimenea" id="condensacionSalidaChimenea" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="caudalSalidaCubierta">Caudal Salida Cubierta: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="caudalSalidaCubierta" name="lcTramiteDto.lcDatosSuministros.caudalSalidaCubierta" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '2', '2')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="condensadorCubierta">Condensador Situado Cubierta: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.condensadorCubierta" id="condensadorCubierta" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="caudalConsdensadorCubierta">Caudal Condensador Cubierta: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="caudalCondensadorCubierta" name="lcTramiteDto.lcDatosSuministros.caudalCondensadorCubierta" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '2', '2')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="torreRefrigeracion">Torre Refrigeraci�n: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="llcTramiteDto.lcDatosSuministros.torreRefirgeracion" id="torreRefrigeracion" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="numTorresRefrigeracion">N�mero Torres Refrigeraci�n: </label></td>
	   			<td align="left" nowrap="nowrap" width="25%"><s:textfield id="numTorresRefrigeracion" name="lcTramiteDto.lcDatosSuministros.numTorresRefrigeracion" size="18" maxlength="2" onkeypress="return soloNumeroDecimal(event, this, '2', '0')"/></td>
	   			<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="potenciaCalorEqAutonomos">Potencia Calor�fica Equipos Aut�nomos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="potenciaCalorEqAutonomos" name="lcTramiteDto.lcDatosSuministros.potenciaCalorEqAutonomos" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	 	</table>
	 </div>
	 
     <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Captaci�n Energ�a Solar </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" >
       	 <tr> 	
			<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="captEnergiaSolar">Captaci�n Energ�a Solar:</label></td>
       		<td align="left" nowrap="nowrap">
       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.captEnergiaSolar" id="captEnergiaSolar" onkeypress="this.onClick" onclick="javascript:tratarDivSegunCheck('captEnergiaSolar','divCaptacionEnergiaSolar');"/>
       		</td>
       	</tr>
   	</table>
	<div id="divCaptacionEnergiaSolar">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equiposUsosTermico">Equipos Uso T�rmico: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="equiposUsosTermico" name="lcTramiteDto.lcDatosSuministros.equiposUsosTermico" size="3" maxlength="2"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="numEquiposUsoTermico">N�mero Equipos Uso T�rmico: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="numEquiposUsoTermico" name="lcTramiteDto.lcDatosSuministros.numEquiposUsoTermico" size="3" maxlength="3" onkeypress="return soloNumeroDecimal(event, this, '3', '0')"/></td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="superfCaptEqUsoTermico">Superficie Captaci�n Equipos Uso T�rmico: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="superfCaptEqUsoTermico" name="lcTramiteDto.lcDatosSuministros.superfCaptEqUsoTermico" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="equipoFotovoltaico">Equipos Fotovoltaicos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%">
	       			<s:checkbox name="lcTramiteDto.lcDatosSuministros.equipoFotovoltaico" id="equipoFotovoltaico" onkeypress="this.onClick"/>
	       		</td>
	       	</tr>
	       	<tr>		
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="numEquiposUsoFotovoltaico">N�mero Equipos Fotovoltaicos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="numEquiposUsoFotovoltaico" name="lcTramiteDto.lcDatosSuministros.numEquiposUsoFotovoltaico" size="3" maxlength="3" onkeypress="return soloNumeroDecimal(event, this, '3', '0')"/></td>
	       		<td align="left" nowrap="nowrap" width="25%"  style="vertical-align:middle"><label for="superfCaptEqUsoFotovoltaico">Superficie Captaci�n Equipos Fotovoltaicos: </label></td>
	       		<td align="left" nowrap="nowrap" width="25%"><s:textfield id="superfCaptEqUsoFotovoltaico" name="lcTramiteDto.lcDatosSuministros.superfCaptEqUsoFotovoltaico" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '8', '2')"/></td>
	       	</tr>
	 	</table>
	 </div>
</div>
