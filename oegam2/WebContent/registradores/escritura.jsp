<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div id="Escritura">
	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Datos de escritura</td>
				
				<s:if test="tramiteRegistro.idTramiteRegistro">
					<td  align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
		</tbody>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
					<span class="titulo">Escritura</span>
				</td>
			</tr>
	</table>

		
	<div id="busqueda" class="busqueda">
		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="30%">
					<div>
						<table>
							<tr>
								<td align="left" nowrap="nowrap" width="10%">
									<label for="inmatriculada" >Inmatriculada:</label>
								</td>
							</tr>
							<tr>
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
									<td align="left" nowrap="nowrap" width="10%">
										<s:select id="inmatriculada" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
											name="tramiteRegistro.inmatriculada" onchange="mostrarBien();" 
											list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getInmatriculada()"
											listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="-"/>
									</td>
								</s:if>
								<s:else>
									<td align="left" nowrap="nowrap" width="10%">
										<s:select id="inmatriculada" name="tramiteRegistro.inmatriculada"
											list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getInmatriculada()"
											listKey="valorEnum" listValue="nombreEnum" headerKey="" headerValue="-" disabled="true"/>
									</td>
								</s:else>
							</tr>
						</table>
					</div>								
				</td>
				<td align="left" nowrap="nowrap">
					<div>
						<table>
							<tr>
								<td align="left" nowrap="nowrap" width="70%">
									<label for="tipoOperacion" >Tipo operaci&oacute;n<span class="naranja">*</span>:</label>
								</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap" width="70%" colspan="2">
									<s:select id="tipoOperacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										name="tramiteRegistro.operacion" list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getOperacionesPropiedad()"
										listKey="codigo" listValue="descripcion" headerKey="" headerValue="-" onchange="inscripcionCartaPago();"/>
								</td>
							</tr>
						</table>
					</div>								
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<div>
						<table cellSpacing="3" cellPadding="1">
							<tr>
								<td nowrap="nowrap" colspan="3" align="left" valign="middle">
									<label for="fechaEscritura">Fecha Escritura:<span class="naranja">*</span></label>
								</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">
									<s:textfield name="tramiteRegistro.fechaCertif.dia" id="diaAltaIniEscritura"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left" nowrap="nowrap">/</td>
								<td align="left" nowrap="nowrap">
									<s:textfield name="tramiteRegistro.fechaCertif.mes" id="mesAltaIniEscritura"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left" nowrap="nowrap">/</td>
								<td align="left" nowrap="nowrap">
									<s:textfield name="tramiteRegistro.fechaCertif.anio" id="anioAltaIniEscritura"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left" nowrap="nowrap">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIniEscritura, document.formData.mesAltaIniEscritura, document.formData.diaAltaIniEscritura);return false;" title="Seleccionar fecha" id="fechaAltaEscrituraCalendar">
									   <img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</div>								
				</td>
				<td align="left" nowrap="nowrap" valign="bottom">
					<div>
						<table>
							<tr>
								<td align="left" nowrap="nowrap">
									<label id="referenciaPropia">Referencia Propia:</label>
								</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">
									<s:textfield name="tramiteRegistro.refPropia" size="50" maxlength="50" onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';" id="referenciaPropia"></s:textfield>
								</td>
							</tr>
						</table>
					</div>								
				</td>
			</tr>	
		</table>
		<table class="tablaformbasica" border="0">
			<s:if test="tramiteRegistro.estado == 9">
				<tr>
					<td align="right">
						<s:checkbox name="tramiteRegistro.suspension" id="suspension"/>
					</td>
					<td style="vertical-align: middle;" align="left" colspan="3">
						<label for="primera">Suspendida la calificaci&oacute;n por faltar la presentaci&oacute;n de la liquidaci&oacute;n de impuestos</label>
					</td>					
				</tr>
			</s:if>
			<tr>
				<td align="left" width="150">
					<label for="tipoInscripcion">Tipo de inscripción: <span class="naranja">*</span></label>
				</td>
				<td align="right">
					<s:checkbox name="tramiteRegistro.checkPrimera" id="primera" onkeypress="this.onClick" onclick="seleccionadaPrimera();"/>
				</td>
				<td style="vertical-align: middle;" align="left">
					<label for="primera">Primera entrada en el registro</label>
				</td>
				<td align="right">
					<s:checkbox name="tramiteRegistro.checkSubsanacion" id="subsanacion" onkeypress="this.onClick"  onclick="seleccionadaSubsanacion();" />
				</td>								
				<td style="vertical-align: middle;" align="left">
					<label for="subsanacion">Subsanación de un documento ya presentado</label>
				</td>					
			</tr>
		</table>
		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">
			<tr>
				<td id="datosRegistrales" style="display:none;">
					<fieldset class="DOS">
					<legend class="dos">Identificaci&oacute;n registral de la escritura&nbsp;&nbsp;</legend>
					<table width="100%" border="0">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<label for="iNotarial">Identificaci&oacute;n notarial</label>
						</td>
						<td>
							<s:checkbox name="tramiteRegistro.identificacionNotarial" id="identificacionNotarial" onkeypress="this.onClick" onclick="seleccionadaIdentificacion1();"/>
						</td>
						<td style="vertical-align: middle;" align="right">
							<label for="nEntrada">N&uacute;mero de entrada</label>
						</td>
						<td>
							<s:checkbox name="tramiteRegistro.identificacionNumeroEntrada" id="numeroEntrada" onkeypress="this.onClick"  onclick="seleccionadaIdentificacion2();" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<label class="gris" id="etCodNotario" for="etCodNotario">C&oacute;digo notario:<span class="naranja">*</span></label>
						</td>
						<td>
							<s:textfield name="tramiteRegistro.codigoNotario" id="codigoNotario" disabled="true"
								size="10" maxlength="10" onblur="this.className='input';" onfocus="this.className='inputfocus';" readonly="true"
								onclick="alertCodigoNotario()" />
						</td>
						<td style="vertical-align: middle;" align="right">
							<label id="etNumeroEntrada" class="gris" for="numEntrada">N&uacute;mero<span class="naranja">*</span></label>
						</td>
						<td>
							<s:textfield name="tramiteRegistro.numRegSub" id="teNumeroEntrada" disabled="true" onkeypress="return validarNumeros(event)"
								size="10" maxlength="10" onblur="this.className='input';" onfocus="this.className='inputfocus';" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<label class="gris" id="etCodNotaria" for="codNotaria">C&oacute;digo notar&iacute;a:<span class="naranja">*</span></label>
						</td>
						<td>
							<s:textfield name="tramiteRegistro.codigoNotaria" id="teCodNotaria" disabled="true"
								size="10" maxlength="10" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
						</td>
						<td style="vertical-align: middle;" align="right">
							<label id="etAnyoEntrada" class="gris" for="numEntrada">Año<span class="naranja">*</span></label>
						</td>
						<td>
							<s:textfield name="tramiteRegistro.anioRegSub" id="teAnyoEntrada" disabled="true" onkeypress="return validarNumeros(event)"
								size="5" maxlength="4" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<label class="gris" id="etProtocolo" for="etProtocolo">Protocolo:<span class="naranja">*</span></label>
						</td>
						<td>
							<s:textfield name="tramiteRegistro.protocolo" id="teProtocolo" disabled="true"
								size="10" maxlength="10" onblur="this.className='input';" onfocus="this.className='inputfocus';" />
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<label class="gris" id="etAnyoProtocolo" for="codNotaria">Año Protocolo:<span class="naranja">*</span></label>
						</td>
						<td>
							<s:textfield name="tramiteRegistro.anioProtocolo" id="teAnyoProtocolo" disabled="true"
								size="7" maxlength="4" onblur="this.className='input';" onfocus="this.className='inputfocus';" 
								onkeypress="return validarNumeros(event)"/>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<label class="gris" id="etBusqueda" for="etBusqueda">Búsqueda notario:</label>
						</td>
						<td>
							<input id="botonNotario" name="botonNotario" type="button" disabled="disabled"
								style="cursor:pointer;background-image:url(img/mostrar.gif);height:20px;width:20px;background-repeat:no-repeat;top:0;left:0" 
								onclick="abrirVentanaNotario();"/>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>	
					</table>
				</fieldset>
			</td>
		</tr>
	</table>

</div>

&nbsp;
<div id="botonBusqueda">
	<table width="100%">
		<tr>
			<td>
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
					<input type="button" class="boton" value="Guardar" style="size: 100%; TEXT-ALIGN: center;" onclick="javascript:guardarEscritura();" id="botonGuardarEscritura"/>
				</s:if>
			</td>
			<td>
				<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
			</td>
		</tr>
	</table>
</div>
</div>

<script type="text/javascript">

$(document).ready(function(){
	inscripcionCartaPago();
});

</script>

