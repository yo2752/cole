<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table class="subtitulo" align="left" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
		<td>Nombramientos</td>
		<td align="right">
			<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
		</td>
	</tr>
</table>
&nbsp;

<s:if test="tramiteRegistro.nombramientos != null &&  tramiteRegistro.nombramientos.size>0">
	<div id="busqueda" class="busqueda">
		<table border="0" class="tablaformbasicaTC">
			<tr>
				<td colspan="2">
					<div id="resultado" style="width: 100%; background-color: transparent;">
						<table class="subtitulo" cellSpacing="0" style="width: 97%;">
							<tr>
								<td style="width: 100%; text-align: center;">Nombramientos acordados</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="divScroll">
						<display:table name="tramiteRegistro.nombramientos" excludedParams="*" class="tablacoin" uid="row" summary="Listado de Nombramientos" cellspacing="0" cellpadding="0">
							<display:column property="sociedadCargo.personaCargo.nombre" title="Nombre" style="width:1%;text-align:left;" />
							<display:column property="sociedadCargo.personaCargo.apellido1RazonSocial" title="Apellido1" style="width:1%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.apellido2" title="Apellido2" style="width:1%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.nif" title="Nif" style="width:1%;text-align:left;"/>
							<display:column title="Nombrado" style="width:10%;text-align:left;">
								<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.codigoCargo)}" />
							</display:column>
			  				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
								<display:column title="Eliminar" style="width:1%;">
			  						<a onclick="" href="javascript:eliminarAcuerdo('${row.idAcuerdo}', '${row.sociedadCargo.personaCargo.nif}', '${row.codigoCargo}')">Eliminar</a>
			  					</display:column>
			  				</s:if>
			  				<display:column style="width:1%;">
		  						<img onclick="consultaEvolucionPersona('${row.sociedadCargo.personaCargo.nif}','<s:property value="tramiteRegistro.numColegiado"/>');" 
									style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución">
			  				</display:column>
						</display:table>
					</div>	
				</td>
			</tr>
		</table>
	</div>
</s:if>
<s:else>
	<table width="95%" class="tablaformbasicaTC">
		<tr>
			<td>
				<span>No existen nombramientos</span>
			</td>
		</tr>
	</table>	
</s:else>
&nbsp;

<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">

<table border="0" class="tablaformbasicaTC" >
<s:hidden name="tramiteRegistro.acuerdoNombramiento.idAcuerdo"></s:hidden>
	<tr>
		<td colspan="6">
			<div id="resultado" style="width: 100%; background-color: transparent;">
				<table class="subtitulo" cellSpacing="0" style="width: 97%;">
					<tr>
						<td style="width: 100%; text-align: center;">Alta Nombramientos</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="cargoNom">Cargo:<span class="naranja">*</span></label>
		</td>
		<td align="left" nowrap="nowrap" colspan="3">
			<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoCargos()" id="cargoNom" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
				name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.codigoCargo" listValue="descCargo" listKey="codigoCargo" title="Cargo del certificante"
				headerKey="" headerValue="seleccionar" />
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="nombreNom">Nombre<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.personaCargo.nombre" id="nombreNom" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="apellido1Nom">Apellido 1<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.personaCargo.apellido1RazonSocial" id="apellido1Nom" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="apellido2Nom">Apellido 2:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.personaCargo.apellido2" id="apellido2Nom" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="nifNom">Nif<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="1">
			<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.personaCargo.nif" id="nifNom" onblur="this.className='input2';this.value=this.value.toUpperCase();" onfocus="this.className='inputfocus';" size="11" maxlength="9" style="text-align:left;" onkeyup="this.value=this.value.toUpperCase()"/>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="email">Correo Electrónico:<span class="naranja">*</span></label>
		</td>
		<td align="left" nowrap="nowrap" colspan="3">
			<s:textfield  name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.personaCargo.correoElectronico" id="emailNom" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"
				onfocus="this.className='inputfocus';" maxlength="57" size="64"/>
		</td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="duracionCargo">Indefinido<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:select label="Nombramiento indefinido:" name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.indefinido" id="indefinido" list="#{'':'-','S':'Si','N':'No'}"
				required="true" onchange="javascript:mostrarDuraciones();" />
		</td>
		<td align="right" nowrap="nowrap">
			<label for="aceptacion">Aceptaci&oacute;n:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getAceptacion()" id="aceptacion" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" name="tramiteRegistro.acuerdoNombramiento.aceptacion" listValue="nombreEnum"	listKey="valorEnum"
				title="Cargo del certificante"	headerKey="" headerValue="-"/>
		</td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
</table>

<div id="temporal" style="width:100%;display:none">
	<table border="0" class="tablaformbasicaTC">
		<tr>
			<td align="left" colspan="2">
				Seleccione la forma de especificar la duraci&oacute;n del nombramiento:
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
			<table border="0">
					<tr>
						<s:radio list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTiposDuracion()"
							listValue="nombreEnum" listKey="valorEnum" labelposition="left" theme="verticalradio"
							name="duracion" id="duracion" onchange="javascript:mostrarCajaDuracion();" 	cssClass="textLeft"  >
						</s:radio>
					</tr>  	
				</table>
			</td>
			<td width="75%">
				<div id="tipoDuracion1" style="display:none">
					<table>
						<tr>
							<td align="right" nowrap="nowrap">
								<label for="meses">Meses:</label>
							</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.meses" id="meses" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="3" maxlength="2" onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td width="5%">&nbsp;</td>
						</tr>
					</table>
				</div>
				<div id="tipoDuracion2" style="display:none">
					<table>
						<tr>
							<td align="right" nowrap="nowrap">
								<label for="anyos">Años:</label>
							</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.anios" id="anios" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="3" maxlength="2" onkeypress="return validarNumerosEnteros(event)" />
							</td>
						</tr>
					</table>
				</div>
				<div id="tipoDuracion3" style="display:none">
					<table cellSpacing="3" cellPadding="0" border="0">
						<tr>
							<td align="right">
								<label for="inicio">Inicio:</label>
							</td>
							<td>
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.fechaInicio.dia" id="diaInicio" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
									onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.fechaInicio.mes" id="mesInicio" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
									onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.fechaInicio.anio" id="anioInicio" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
									onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td>
								<a id="botonFechaInicio" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
							<td width="10%">&nbsp;</td>
							<td align="right">
								<label for="fin">Fin:</label>
							</td>
							<td>
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.fechaFin.dia" id="diaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
									onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.fechaFin.mes" id="mesFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
									onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="tramiteRegistro.acuerdoNombramiento.sociedadCargo.fechaFin.anio" id="anioFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
									onkeypress="return validarNumerosEnteros(event)" />
							</td>
							<td>
								<a id="botonFechaFin" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>
</div>

<table border="0" class="tablaformbasicaTC" >
	<tr>
		<td align="left" nowrap="nowrap" style="width:20px;">
			<label for="circunstanciasPersonales">Circunstancias personales:</label>
		</td>
		<td align="left" nowrap="nowrap" style="width:20px;">
			<label for="facultadesDelegadas">Facultades delegadas:</label>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<s:textarea name="tramiteRegistro.acuerdoNombramiento.circunstancia" id="circunstanciasPersonales" onblur="this.className='input2';" onfocus="this.className='inputfocus';" cols="40" rows="3"/>
		</td>
		<td align="left" nowrap="nowrap">
			<s:textarea name="tramiteRegistro.acuerdoNombramiento.facultades" id="facultadesDelegadas" onblur="this.className='input2';" onfocus="this.className='inputfocus';" cols="40" rows="3"/>
		</td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
</table>

<s:if test="indefinido == 'N' && diaInicio != ''">
	<script type="text/javascript">mostrarIntervaloFechas();</script>
</s:if>
<s:if test="indefinido == 'N' && (meses != '' || anios != '')">
	<script type="text/javascript">mostrarMesesAnios();</script>
</s:if>

	<div id="botonBusqueda">
		<table width="100%" class="tablaformbasicaTC">
			<tr>
				<td> 
					<input type="button" id="botonAltaNombramiento" value="Alta/Modificación" class="boton" onclick="javascript:validacionNombramiento();" />
				</td>
				<td>
					<img id="loading7Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<td>  
					<input type="button" value="Buscar" class="boton" onclick="javascript:abrirVentanaBusquedaCargos('nombramiento');" />
				</td>
				<td>
					<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposNombramiento();"/>
				</td>
			</tr>
		</table>
	</div>
</s:if>