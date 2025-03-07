<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/mandato/mandato.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Mandato</span>
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
		scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> 
	</iframe>
		
	<table class="subtitulo" cellSpacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos Alta Mandato</td>
			</tr>
	</table>
	
	<s:form method="post" id="formData" name="formData">	
		<s:hidden name="mandatoDto.idMandato"/>		
		<s:hidden name="mandatoDto.visible"/>	
		<s:hidden name="mandatoDto.fechaAlta.dia"/>
		<s:hidden name="mandatoDto.fechaAlta.mes"/>
		<s:hidden name="mandatoDto.fechaAlta.anio"/>
			
		<div class="contentTabs" id="DatosMandato" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<div class="contenido">
				<%@include file="../../includes/erroresMasMensajes.jspf"%>
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelCodigoMandato">C&oacute;digo Mandato:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idCodigoMandato" name="mandatoDto.codigoMandato"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="9" readonly="true"/>
						</td>
					</tr>	
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelFechaMandato">Fecha Mandato:<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<table>
								<tr>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaMandato.dia" id="idDiaFechaMandato"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaMandato.mes" id="idMesFechaMandato"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaMandato.anio" id="idAnioFechaMandato"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="4" maxlength="4" />
									</td>
	
									<td align="left" nowrap="nowrap">
										<a href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaMandato, document.formData.idMesFechaMandato, document.formData.idDiaFechaMandato);return false;"
											title="Seleccionar fecha"> <img class="PopcalTrigger"
											align="middle" src="img/ico_calendario.gif" width="15"
											height="16" border="0" alt="Calendario" /></a>
									</td>
								</tr>
							</table>
						</td>
						
						<td align="left" nowrap="nowrap">
							<label for="labelFechaInicio">Fecha Caducidad:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<table>
								<tr>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCaducidad.dia" id="idDiaFechaCaducidad"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCaducidad.mes" id="idMesFechaCaducidad"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCaducidad.anio" id="idAnioFechaCaducidad"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="4" maxlength="4" />
									</td>
	
									<td align="left" nowrap="nowrap">
										<a href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaCaducidad, document.formData.idMesFechaCaducidad, document.formData.idDiaFechaCaducidad);return false;"
											title="Seleccionar fecha"> <img class="PopcalTrigger"
											align="middle" src="img/ico_calendario.gif" width="15"
											height="16" border="0" alt="Calendario" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
					<tr>
						<td class="tabular">
							<span class="titulo">Datos Empresa</span>
						</td>
					</tr>
				</table>
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelCif">CIF:<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idCif" name="mandatoDto.cif"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelNombreEmpresa">Nombre empresa:<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNombreEmpresa" name="mandatoDto.empresa"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="50"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:<span class="naranja">*</span></label>
						</td>
						<td align="left">
							<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo"
								headerKey="" listValue="descripcion" cssStyle="width:220px"
								name="mandatoDto.idContrato" id="idContrato" />
						</td>
					</tr>
				</table>
				<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
					<tr>
						<td class="tabular">
							<span class="titulo">Datos Administrador</span>
						</td>
					</tr>
				</table>
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelDniAdministrador">DNI Administrador:<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idDniAdministrador" name="mandatoDto.dniAdministrador"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelNombreAdministrador">Nombre Administrador:<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNombreAdministrador" name="mandatoDto.nombreAdministrador"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="30"/>
						</td>
					</tr>	
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelFechaCadDniAdmin">Fecha Caducidad DNI Administrador:<span class="naranja">*</span></label>
						</td>
						<td align="left" nowrap="nowrap">
							<table>
								<tr>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCadDniAdmin.dia" id="idDiaFechaCadDniAdmin"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCadDniAdmin.mes" id="idMesFechaCadDniAdmin"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCadDniAdmin.anio" id="idAnioFechaCadDniAdmin"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="4" maxlength="4" />
									</td>
	
									<td align="left" nowrap="nowrap">
										<a href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaCadDniAdmin, document.formData.idMesFechaCadDniAdmin, document.formData.idDiaFechaCadDniAdmin);return false;"
											title="Seleccionar fecha"> <img class="PopcalTrigger"
											align="middle" src="img/ico_calendario.gif" width="15"
											height="16" border="0" alt="Calendario" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>	
				<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
					<tr>
						<td class="tabular">
							<span class="titulo">Datos 2º Administrador</span>
						</td>
					</tr>
				</table>
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelDniAdministrador2">DNI 2º Administrador:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idDniAdministrador2" name="mandatoDto.dniAdministrador2"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="9"/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelNombreAdministrador2">Nombre 2º Administrador:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNombreAdministrador2" name="mandatoDto.nombreAdministrador2"  onblur="this.className='input2';" 
    							onfocus="this.className='inputfocus';" size="20" maxlength="30"/>
						</td>
					</tr>	
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelFechaCadDniAdmin2">Fecha Caducidad DNI 2º Administrador: </label>
						</td>
						<td align="left" nowrap="nowrap">
							<table>
								<tr>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCadDniAdmin2.dia" id="idDiaFechaCadDniAdmin2"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCadDniAdmin2.mes" id="idMesFechaCadDniAdmin2"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%"><s:textfield
										name="mandatoDto.fechaCadDniAdmin2.anio" id="idAnioFechaCadDniAdmin2"
										onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="4" maxlength="4" />
									</td>
	
									<td align="left" nowrap="nowrap">
										<a href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaCadDniAdmin2, document.formData.idMesFechaCadDniAdmin2, document.formData.idDiaFechaCadDniAdmin2);return false;"
											title="Seleccionar fecha"> <img class="PopcalTrigger"
											align="middle" src="img/ico_calendario.gif" width="15"
											height="16" border="0" alt="Calendario" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>		
			 
		&nbsp;
		
		<div class="acciones center">
			<input type="button" class="boton" name="bGuardarMandato" id="idGuardarMandato" value="Guardar" onClick="javascript:guardarMandato();"
		 		onKeyPress="this.onClick"/>
		 	<input type="button" class="boton" name="bVolverConsultaMandato" id="idVolverConsultaMandato" value="Volver" onClick="javascript:volverConsultaMandato();"
		 		onKeyPress="this.onClick"/>	
		</div>
	</s:form>	
</div>
