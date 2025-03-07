<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Junta">
	
	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Reuni&oacute;n de la junta</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
			</td>
		</tr>
	</table>
	&nbsp;
	
	<div id="busqueda" class="busqueda">
		<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
			<tr>
				<td align="right" nowrap="nowrap" width="10%">
					<label for="tipoReunion">Tipo<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" width="10%">
					<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoReunionJunta()"
						id="tipoReunion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="tramiteRegistro.reunion.tipoReunion"
						listValue="nombreEnum" listKey="valorEnum"
						title="Tipo de reunión" headerKey="" headerValue="-"/>
				</td>
				<td align="right" nowrap="nowrap" width="10%">
					<label for="caracter">Car&aacute;cter:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="6" width="70%">
					<s:select label="Carácter:" name="tramiteRegistro.reunion.caracter" id="caracter"
						list="#{'noSeleccionado':'-','UNIVERSAL':'Universal'}" required="true" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap" width="5%">
					<label for="fecha">Fecha<span class="naranja">*</span>:</label>
				</td>
				<td colspan="3">
					<div>
						<table cellSpacing="3" cellPadding="0" border="0">
							<tr>
								<td>
									<s:textfield name="tramiteRegistro.reunion.fecha.dia" id="dia" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="tramiteRegistro.reunion.fecha.mes" id="mes" onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';" size="2" maxlength="2"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="tramiteRegistro.reunion.fecha.anio" id="anio" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4"
										onkeypress="return validarNumerosEnteros(event)"/>
								</td>
								<td>
									<a id="botonFechaReunion" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anio, document.formData.mes, document.formData.dia);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap" width="2%">
					<label for="lugarReunion">Lugar<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield name="tramiteRegistro.reunion.lugar" id="lugar" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="60" maxlength="55"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
		
		<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
			<div id="botonBusqueda">
				<table width="100%">
					<tr>
						<td width="50%" align="right"> 
							<input type="button" value="Guardar" class="boton" id="botonSeleccionarReunion" onclick="guardarReunion();"/>
						</td>
						<td>
							<img id="loading5Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td width="50%" align="left"> 
							<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposReunionJunta();"/>
						</td>
					</tr>
				</table>
			</div>
			</s:if>
		</div>
</div>
