<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	
		<table class="subtitulo" align="left" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Sociedad del tr&aacute;mite</td>
				<s:if test="tramiteRegistro.sociedad.nif != null && tramiteRegistro.numColegiado != null">
					<td align="right">
						<img onclick="consultaEvolucionPersona('<s:property value="tramiteRegistro.sociedad.nif"/>','<s:property value="tramiteRegistro.numColegiado"/>');" 
							style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución"> 
					</td>
				</s:if>
			</tr>
		</table>
	
	
		<div id="busqueda" class="busqueda">
			<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
				<tr>
				  <td align="right" nowrap="nowrap">
					<label for="cif">CIF<span class="naranja">*</span>:</label>
				  </td>
				  <td>
					<table  cellSpacing="0">
              	 		<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="tramiteRegistro.sociedad.nif" id="cifSoc" onblur="this.className='input2';this.value=this.value.toUpperCase();"
									onfocus="this.className='inputfocus';" size="10" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" />
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
									<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
											onclick="javascript:abrirVentanaBusquedaSociedades();" />
								</s:if>
							</td>	
						</tr>
					 </table>				
				  </td>
					
					<td align="right" nowrap="nowrap">
						<label for="denominacionSocial">Denominaci&oacute;n social<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap" colspan="5">
						<s:textfield name="tramiteRegistro.sociedad.apellido1RazonSocial" id="denominacionSocialSoc" onkeyup="this.value=this.value.toUpperCase()" 
							onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="54" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">&nbsp;</td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">
						<label for="ius">Identificador &uacute;nico de sujeto (IUS):</label>
					</td>
					<td align="left" nowrap="nowrap">
							<s:textfield name="tramiteRegistro.sociedad.ius" id="iusSoc" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="19" maxlength="15"
								onkeypress="return validarNumerosEnteros(event)"/>
					</td>
					<td align="right" nowrap="nowrap">
						<label for="seccion">Secci&oacute;n:</label>
					</td>
					<td align="left" nowrap="nowrap">
							<s:textfield name="tramiteRegistro.sociedad.seccion" id="seccionSoc" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="3" maxlength="3" onkeypress="return validarNumerosEnteros(event)"/>
					</td>
					<td align="right" nowrap="nowrap">
						<label for="hoja">Hoja:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="tramiteRegistro.sociedad.hoja" id="hojaSoc" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="6" maxlength="6" onkeypress="return validarNumerosEnteros(event)"/>
					</td>
					<td align="right" nowrap="nowrap">
						<label for="hojaBis">Hoja bis:</label>
					</td>
					<td align="left" nowrap="nowrap">
							<s:select label="Hoja bis:" name="tramiteRegistro.sociedad.hojaBis" id="hojaBisSoc" 
								list="#{'':'-','SI':'SI', 'NO':'NO'}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								required="true"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">&nbsp;</td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap">
						<label for="tipoPersona">Tipo persona:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select label="Tipo:" name="tramiteRegistro.sociedad.subtipo" id="tipoPersona"
							list="#{'':'-', 'PUBLICA':'Jurídica pública', 'PRIVADA':'Jurídica privada'}"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" required="true"/>
					</td>
					<td align="right" nowrap="nowrap">
						<label for="email">Correo Electrónico:</label>
					</td>
					<td align="left" nowrap="nowrap" colspan="5">
						<s:textfield  name="tramiteRegistro.sociedad.correoElectronico" id="emailSoc" onkeyup="this.value=this.value.toUpperCase()" 
						onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" maxlength="57" size="54"/>
					</td>
				</tr>
				<tr>
					<td colspan="9">&nbsp;</td>
				</tr>
			</table>
		</div>
		

		<div id="busqueda" style="width: 100%">
			<%@include file="registro.jsp" %>
		</div>
