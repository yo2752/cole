<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">					
	<table class="subtitulo" cellSpacing="0" cellspacing="0"  align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del representante</td>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE</span>
			</td>
		</tr>
	</table>	
	<table class="tablaformbasica" border="0">
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="nifConductorHabitual">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
       	    	<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield disabled="%{flagDisabled}" name="embarcacion.representante.nif" 
			       				id="nifRepresentante" onblur="this.className='input2';calculaLetraNIF(this)" 
			       				onfocus="this.className='inputfocus';" onchange="javascript:limpiarRepresentante()"
			       				size="10" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarRepresentante()"/>
							</s:if>
						</td>						
					</tr>
				</table>
       	    </td>
       	    <td align="right" nowrap="nowrap">
     					<label for="labelTipoPersona">Tipo de persona<span class="naranja">*</span>:</label>
     		</td>
       		<td align="left" nowrap="nowrap">
        		<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getComboTipoPersonas()"
					id="idTipoPersonaRepresentante" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="embarcacion.representante.tipoPersona" disabled="true"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-"/>
     		</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="sexoRepresentante">Sexo:</label>
			</td>

			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:select
					list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
					id="sexoRepresentante" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="embarcacion.representante.sexo"
					disabled="%{flagDisabled}" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1"
					headerValue="-" />
			</td>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="apellido1RazonSocialRepresentante">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:textfield
					disabled="%{flagDisabled}"
					name="embarcacion.representante.apellido1RazonSocial"
					id="apellido1Representante" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="100"
					cssStyle="width:20em;" />
			</td>
		</tr>

		<tr>
			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="apellido2Representante">Segundo Apellido: </label>
			</td>

			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:textfield
					disabled="%{flagDisabled}"
					name="embarcacion.representante.apellido2"
					id="apellido2Representante" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="30" maxlength="100" />
			</td>

			<td align="right" nowrap="nowrap" style="vertical-align: middle;">
				<label for="nombreRepresentante">Nombre: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:textfield
					disabled="%{flagDisabled}"
					name="embarcacion.representante.nombre"
					id="nombreRepresentante" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:20em;" />
			</td>

		</tr>
		<tr>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
		</tr>
	</table>
	
</div>		    
