<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

<div class="contenido" id="solicitante">	

	<s:hidden name="tramiteRegRbmDto.solicitante.idInterviniente" id="solicitanteCancelacion"/>
	
	<s:hidden name="tramiteRegRbmDto.representanteSolicitante.idInterviniente"/>
	<s:hidden name="tramiteRegRbmDto.representanteSolicitante.notario.codigo"/>
	<s:hidden name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.idDatRegMercantil"/>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Solicitante:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.solicitante.tipoPersona" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         listValue="nombreEnum" listKey="valorXML"
                         id="solicitanteTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="solicitanteNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
              
              <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.solicitante.nif" id="solicitanteNif" size="18" maxlength="9"  onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('solicitanteNif', 'solicitante')"/>
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteNombre" >Nombre</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.solicitante.persona.nombre" id="solicitanteNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="solicitanteApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
        			<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.apellido1RazonSocial" id="solicitanteApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteApellido2" >2do apellido</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.apellido2" id="solicitanteApellido2" size="18" maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.correoElectronico" id="solicitanteCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100" ></s:textfield></td>
            </tr>
    </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del Solicitante:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
        <tr>
              <td align="left" nowrap="nowrap"><label for="solicitantePais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.solicitante.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="solicitantePais"/>
              </td>
        </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="solicitanteAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.solicitante.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="solicitanteSelectProvinciaId"
						 onchange="cargarListaMunicipios(this,'solicitanteSelectMunicipioId','solicitanteHiddenMunicipioId');
						  inicializarTipoVia('solicitanteTipoVia','solicitanteNombreVia', viaSolicitante);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="solicitanteAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="solicitanteSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.solicitante)"
					onchange="javascript:seleccionMunicipio(this, 'solicitante');
					 inicializarTipoVia('solicitanteTipoVia','solicitanteNombreVia', viaSolicitante);"
					name="tramiteRegRbmDto.solicitante.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="solicitanteHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.solicitante.direccion.nombreMunicipio" id="solicitanteHiddenMunicipality" />
               </td>
         </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"> <label for="solicitanteCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.codPostal" id="solicitanteCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
        </tr>
        <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.solicitante.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="solicitanteTipoVia"
                         onchange ="cargarListaNombresVia('solicitanteSelectProvinciaId', 'solicitanteSelectMunicipioId', this, 'solicitanteNombreVia', viaSolicitante);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="solicitanteNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.solicitante.direccion.nombreVia" id="solicitanteNombreVia" size="35" maxlength="100"
               onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="solicitanteNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.numero" id="solicitanteNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.solicitante.direccion.bloque" id="solicitanteBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitantePlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.planta" id="solicitantePlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitantePuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.puerta" id="solicitantePuerta" size="5" maxlength="100"></s:textfield></td>
			
              <td align="left" nowrap="nowrap"><label for="solicitanteEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.escalera" id="solicitanteEscalera" size="5" maxlength="100"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="solicitantePortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.portal" id="solicitantePortal" size="5"></s:textfield></td>
             </tr>
	</table>
	
<%-- 	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Representantes Solicitante</span>
			</td>
		</tr>
	</table>
 --%>
 </div>
<div class="contenido" id="representanteSolicitante">

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Representante:</span>
		</td>
	</tr>
</table>
<iframe width="174"  
	height="189" 
	name="gToday:normal:agenda.js:representSolicitante" 
	id="gToday:normal:agenda.js:representSolicitante" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
	align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
		<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.tipoPersona"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
				headerKey=""
				headerValue="Selecionar Tipo"
				listValue="nombreEnum" 
				listKey="valorXML"
				id="representanteSolicitanteTipoPersona" /></td>

		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
		<td width="35%">
         	 <table  cellSpacing="0">
         	 	<tr>
         	 		<td align="left" nowrap="nowrap">
            			<s:textfield name="tramiteRegRbmDto.representanteSolicitante.nif" id="representanteSolicitanteNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
            		</td>
            		
            		<td align="left" nowrap="nowrap">
						<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
	             			<input type="button" id="idBotonBuscarNifRep" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
								onclick="javascript:buscarIntervinienteAjax('representanteSolicitanteNif', 'representanteSolicitante')" />
						</s:if>
	    			</td>
				</tr>
          	</table>
	    </td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNombre" >Nombre</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.nombre" id="representanteSolicitanteNombre" size="18" maxlength="100"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.apellido1RazonSocial" id="representanteSolicitanteApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteApellido2" >2do apellido</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.apellido2" id="representanteSolicitanteApellido2" size="18" maxlength="100"></s:textfield></td>
	</tr>
	
</table>

<table class="subtitulo" align="left" cellspacing="0">
	<tr>
		<td>Información poder del representante</td>
	</tr>
</table>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
	<tr>
		<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTipoPersonaNotario" class="small">Tipo de persona<span class="naranja">*</span></label></td>
		<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.notario.tipoPersona"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
				headerValue="Selecionar Tipo"
				headerKey=""
				listValue="nombreEnum" 
				listKey="valorXML"
				id="representanteTipoPersonaNotario" /></td>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioNif" class="small">Nif. notario<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.nif" id="representanteNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioNombre" class="small">Nombre notario<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.nombre" id="representanteNotarioNombre" size="18" maxlength="255"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioApellido1">1er apellido<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.apellido1" id="representanteNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioApellido2" >2do apellido</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.apellido2" id="representanteNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		
		<td align="left" nowrap="nowrap"><label for="representantePlazaNotario" >Plaza<span class="naranja">*</span></label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Plaza o lugar"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.plazaNotario" id="representantePlazaNotario" size="18" maxlength="255"></s:textfield></td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteFecOtorgamiento" class="small">Fecha otorgamiento<span class="naranja">*</span></label></td>
		<td>
			<table style="width:20%">
				<tr>
					<td align="left">
						<s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="this.className='input2'; validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
					</td>
					<td align="left">
						<label class="labelFecha">/</label>
					</td>
					<td align="left">
						<s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="this.className='input2';validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
					</td>
					<td align="left">
						<label class="labelFecha">/</label>
					</td>
					<td align="left">
						<s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="this.className='input2';validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
					</td>
					
					<td align="left" nowrap="nowrap" width="5%" >
			    		<a href="javascript:;" 
			    			onClick="if(self.representSolicitante)representSolicitante.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
			    				title="Seleccionar fecha">
			    			<img class="PopcalTrigger" 
			    				align="left" 
			    				src="img/ico_calendario.gif" 
			    				width="15" height="16" 
			    				border="0" alt="Calendario"/>
			    		</a>
					</td>
				</tr>
			</table>
		</td>
		<td align="left" nowrap="nowrap"><label for="representanteNumProtocolo" class="small">N&uacute;m. protocolo<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.numProtocolo" id="representanteNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
	</tr>
</table>

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Datos del Registro Mercantil del Representante:</span>
		</td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
      <tr>
 			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteRegistoMercantil" class="small">Registro mercantil<span class="naranja">*</span></label></td>
          <td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.codRegistroMercantil" 
                    list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                     listKey="idRegistro"
                     listValue="nombre"
                     headerKey="" 
                     headerValue="Selecionar Tipo"
                     id="representanteSolicitanteRegistoMercantil"/></td>
          
 			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTomo" class="small">Tomo inscrip.<span class="naranja">*</span></label></td>
          <td width="35%"><s:textfield name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.tomo" id="representanteSolicitanteTomo" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
 	  </tr>
      <tr> 
          <td align="left" nowrap="nowrap"><label for="representanteSolicitanteFolio" class="small">Folio inscrip.<span class="naranja">*</span></label></td>
          <td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.folio" id="representanteSolicitanteFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
          <td align="left" nowrap="nowrap"><label for="representanteSolicitanteNumInscripcion" class="small">N&uacute;mero inscrip.<span class="naranja">*</span></label></td>
          <td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.numInscripcion" id="representanteSolicitanteNumInscripcion" size="5" maxlength="255"></s:textfield></td>
      </tr>
</table>

 
<%--  	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteSolicitante('Cancelacion','formData', 'cargarPopUpRepresentanteSolicitanteRegistroCancelacion.action');" id="btnAniadirRepresentanteSolicitanteCancelacion" class="button corporeo"value="Añadir representante" />
				</s:if>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<jsp:include page="representantesSolicitanteList.jsp" flush="true" />
			</td>
		</tr>
	</table>
 --%>	
</div>

<script type="text/javascript">
$( document ).ready(function() {
	
	var solicitanteCancelacion =$('#solicitanteCancelacion').val();
	if(solicitanteCancelacion == null || solicitanteCancelacion == "" || solicitanteCancelacion == 0){
		$("#btnAniadirRepresentanteSolicitanteCancelacion").attr('disabled', true);
		$("#btnAniadirRepresentanteSolicitanteCancelacion").attr('title', "Antes de añadir un representante debe guardar el solicitante");
	}else{
		$("#btnAniadirRepresentanteSolicitanteCancelacion").attr('disabled', false);
	}
	
});

function resizeMe(){
	var $cal = $('iframe:eq(1)',parent.document);
	var position = $("#diaFecOtorgamiento").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top - 100) + "px"
	});
}

var viaSolicitante = new BasicContentAssist(document.getElementById('solicitanteNombreVia'), [], null, true); 
recargarNombreVias('solicitanteSelectProvinciaId', 'solicitanteSelectMunicipioId', 'solicitanteTipoVia','solicitanteNombreVia',viaSolicitante);

</script>


  