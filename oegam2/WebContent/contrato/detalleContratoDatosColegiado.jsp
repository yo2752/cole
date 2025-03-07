<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

  
<div class="contentTabs" id="DatosdelColegiado" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
	<div class="contenido">		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos de Colegiado</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" summary="Datos usuario principal">
			<tr>
				<td align="left" nowrap="nowrap"><br/>
					<label for="numColegiadoUsuarioPpal">N&uacute;mero Colegiado<span class="naranja">*</span></label>
					<s:if test="contratoDto.colegiadoDto.numColegiado != null && contratoDto.colegiadoDto.numColegiado != ''">
						<s:textfield name="contratoDto.colegiadoDto.numColegiado" id="numColegiadoUsuarioPpal" cssClass="inputview" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield name="contratoDto.colegiadoDto.numColegiado" id="numColegiadoUsuarioPpal" onfocus="this.className='inputfocus';" size="30" maxlength="20"/>
					</s:else>
				</td>
				<td align="left" nowrap="nowrap"><br/>
					<label for="numColegiadoUsuarioPpal">N&uacute;mero Colegiado Nacional</label>	
					<s:if test="contratoDto.colegiadoDto.numColegiadoNacional != null && contratoDto.colegiadoDto.numColegiadoNacional != ''">
						<s:textfield name="contratoDto.colegiadoDto.numColegiadoNacional" id="numColegiadoUsuarioPpal" cssClass="inputview" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield name="contratoDto.colegiadoDto.numColegiadoNacional" id="numColegiadoNacionalUsuarioPpal" onblur="this.className='input2';" size="30" maxlength="20"/>
					</s:else>
				
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><br/>
					<label for="aliasColegiado">Alias<span class="naranja">*</span></label>
					<s:textfield name="contratoDto.colegiadoDto.alias" id="aliasColegiado" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="30" maxlength="20"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nombre">Nombre<span class="naranja">*</span></label>
					<s:textfield name="contratoDto.colegiadoDto.usuario.nombre" id="nombreUsuarioPpal" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
				</td>
				<td  align="left" nowrap="nowrap">
					<label for="nifNieUsuarioPpal">NIF/NIE<span class="naranja">*</span></label>			
					<s:textfield name="contratoDto.colegiadoDto.usuario.nif" id="nifNieUsuarioPpal" 
					onblur="this.className='input2'; calculaLetraDni('nifNieUsuarioPpal');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="apellido1">Apellido1<span class="naranja">*</span></label>
					<s:textfield name="contratoDto.colegiadoDto.usuario.apellido1" id="apellido1UsuarioPpal" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="apellido2">Apellido2<span class="naranja">*</span></label>
					<s:textfield name="contratoDto.colegiadoDto.usuario.apellido2" id="apellido2UsuarioPpal" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="correoElectronicoUsuarioPpal">Correo Electr&oacute;nico<span class="naranja">*</span></label>	
					<s:textfield name="contratoDto.colegiadoDto.usuario.correoElectronico" id="correoElectronicoUsuarioPpal" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="GrupoUsuario">Grupo Usuario Especial</label>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||	@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<s:select list="@trafico.datosSensibles.utiles.UtilesVistaDatosSensibles@getInstance().listarGrupos()"					
							name="contratoDto.colegiadoDto.usuario.idGrupo" listKey="idGrupo" listValue="descGrupo"
							headerKey="-1" headerValue="Ninguno" id="idGrupoUsuario" />
					</s:if>
					<s:else>
						<s:hidden name="contratoDto.colegiadoDto.usuario.idGrupo"/>
						<s:textfield readonly="true" value="%{@trafico.datosSensibles.utiles.UtilesVistaDatosSensibles@getInstance().traducirGrupo(contratoDto.colegiadoDto.usuario.idGrupo)}" />
					</s:else>
				</td>
				<td align="left" nowrap="nowrap">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||	@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
        			<label for="lbMobileGest">Mobile Gest:</label>
	      			<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getEsMobileGest()"
						id="idMobileGest" onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="contratoDto.mobileGest"
						listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue=""/>
				</s:if>
        		</td>
			</tr>

		</table>
		&nbsp;
		&nbsp;
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Gestión de correos por tr&aacute;mites</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" >
			<tr>
				<s:hidden name="contratoDto.correoContratoTramite.idCorreo"/>
			    <td align="left" nowrap="nowrap" style="vertical-align: middle;" width="5%">
        			<label for="registro">Aplicaci&oacute;n:</label>
      			</td>
        		<td align="left" nowrap="nowrap"  width="40%">
	      			<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getListAplicacionesPorContrato(contratoDto.idContrato)"
						id="codigoAplicacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="contratoDto.codigoAplicacion"
						listKey="codigoAplicacion" listValue="descAplicacion" headerKey="" headerValue="TODAS" onchange="buscarTipoTramitePorAplicacion();"/>
        		</td>
        		 <td align="left" nowrap="nowrap" style="vertical-align: middle;" width="5%">
        			<label for="lbTipoTramite">Tipo Tr&aacute;mite:</label>
      			</td>
        		<td align="left" nowrap="nowrap" >
	      			<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getListTipoTramiteConsulta(contratoDto.codigoAplicacion)"
						id="tipoTramite" onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="contratoDto.correoContratoTramite.idTipoTramite"
						listKey="tipoTramite" listValue="descripcion" headerKey="" headerValue="TODOS"
						 onchange="mostrarCamposEnvioCorreoImpresion();"/>
        		</td>
        	</tr>
        </table>
        <div id="divCamposEnvioCorreoImpresion" hidden="true">
	        <table cellSpacing="3" class="tablaformbasica" cellPadding="0" >	
				<tr>
	        		<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="5%">
	        			<label for="lbTipoImpresion">Tipo Impresi&oacute;n:</label>
	        		</td>
					<td align="left" nowrap="nowrap"  width="40%">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoDocumentoImpresiones()"
							name="contratoDto.correoContratoTramite.tipoImpresion" id="tipoImpresion" headerKey=""
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							headerValue="Cualquier Tipo" listKey="valorEnum" listValue="nombreEnum" />
					</td>
	       			<td align="left" nowrap="nowrap" style="vertical-align: middle;" width="5%">
						<label for="enviarCorreoImpresion">Enviar correo impresión:</label>
					</td>
					 <td align="left" nowrap="nowrap" >
							<s:select label="enviarCorreo" name="contratoDto.correoContratoTramite.enviarCorreoImpresion" id="enviarCorreo" 
								list="#{'':'Seleccionar','SI':'SI', 'NO':'NO'}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
		</div>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" >	
			<tr>
			    <td align="left" nowrap="nowrap" style="vertical-align: middle;" width="5%">
        			<label for="correoElectronico">Correo Electr&oacute;nico:</label>
      			</td>
        		<td align="left" nowrap="nowrap" width="80%" colspan="3">
	      			<s:textfield name="contratoDto.correoContratoTramite.correoElectronico" id="correoElectronicoTramite" 
	      			    onfocus="this.className='inputfocus';" onblur="this.className='input2';validaCorreoElectronico(this.value);"  size="80" maxlength="100"/> 
			</tr>
		</table>
		
		<div id="busqueda" class="busqueda">
			<table border="0" class="tablaformbasicaTC">
				<tr>
					<td colspan="2">
						<div id="resultado" style="width: 100%; background-color: transparent;">
							<table class="subtitulo" cellSpacing="0" style="width: 97%;">
								<tr>
									<td style="width: 100%; text-align: center;">Lista de correos por tr&aacute;mites</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">		
						<div id="divTablaCorreosContratoTramites" class="divScroll">
							<display:table name="contratoDto.correosTramites" excludedParams="*" style="width:97%" class="tablacoin" uid="row" summary="Listado de correos por támites" cellspacing="0" cellpadding="0">
								<display:column property="tipoTramite.aplicacion.descAplicacion" title="Aplicación" style="text-align:left;"/>
								<display:column property="tipoTramite.descripcion" title="Trámite" style="text-align:left;"/>
								<display:column property="correoElectronico" title="Correo Electrónico" style="text-align:left;"/>
								<display:column title="Tipo Impresión" >
									<s:property value="%{@org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones@convertirTexto(#attr.row.tipoImpresion)}" />
								</display:column>
								<display:column property="enviarCorreoImpresion" title="Enviar mail impresión" style="text-align:center;"/>
									
				  				<display:column title="Eliminar" style="width:8%;text-align:center;">
				  					<a onclick="" href="javascript:eliminarCorreoContratoTramite(${row.idCorreo})">Eliminar</a>
				  				</display:column>
				  				<display:column title="Modificar" style="width:8%;text-align:center;">
				  					<a onclick="" href="javascript:modificarCorreoContratoTramite(${row.idCorreo})">Modificar</a>
				  				</display:column>
							</display:table>
						</div>	
					</td>
				</tr>
			</table>
		</div>
		&nbsp;
		&nbsp;
		
		<table class="acciones">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td>
						<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return modificacionContrato('DatosdelColegiado');" onKeyPress="this.onClick"/>
						&nbsp;	
					</td>
				</s:if>
				<td align="center">
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volverContrato();" onKeyPress="this.onClick" />&nbsp;                     
			     </td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		mostrarCamposEnvioCorreoImpresion();
	});
</script>