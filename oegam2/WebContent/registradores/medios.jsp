<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
	<tr>
		<td align="center" style="color: #990000">
			<h3></h3>
		</td>
	</tr>
</table>

<div class="nav">
  	<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
		<tr>
			<td class="tabular" style=""><span class="titulo">Medios de Publicaci&oacute;n</span></td>
		</tr>
	</table>
</div>
			
<table border="0" class="tablaformbasicaTC" style="width: 100%">
	<s:hidden id="idMedio" name="medioConvocatoria.medio.idMedio"/>	
	<tr>
		<td align="left" nowrap="nowrap" width="40%">
			<table>
				<tr>
					<td>
						<table>
							<tr>
								<td align="right" nowrap="nowrap" width="2%">
									<label for="buscarMedio">Buscar Medios:</label>
								</td>
								<td align="left" nowrap="nowrap">
									<input id="botonbuscar"  type="button" class="botonbuscar" value="" style="size: 100%; TEXT-ALIGN: center" onclick="abrirVentanaBuscarMedios();"/>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap" width="2%">
									<label></label><br><br>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap" width="2%">
									<label for="tipoMedio">Tipos de Medio<span class="naranja">*</span>:</label>
								</td>
								<td align="left" nowrap="nowrap" width="2%">
									<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTiposMedio()"
										id="tipoMedio" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" name="medioConvocatoria.medio.tipoMedio"
										listValue="nombreEnum" listKey="valorEnum"
										title="Tipos de Medio" headerKey="-1" headerValue="TODOS"/>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap" width="2%">
									<label for="descripcionMedioPublicacion">Descripción<span class="naranja">*</span>:</label>
								</td>
								<td align="left" nowrap="nowrap" width="2%">
									<s:textfield name="medioConvocatoria.medio.descMedio" id="descripcionMed" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="30" maxlength="1500" height="150" />
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap" width="2%">
									<label for="fechaMedioPublicacion">Fecha Convocatoria<span class="naranja">*</span>:</label>
								</td>
								<td align="left" nowrap="nowrap" width="2%">
									<table cellSpacing="3" cellPadding="0" border="0">
										<tr>
											<td>
												<s:textfield name="medioConvocatoria.fechaPublicacion.dia" id="diaMedio"
													onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
													onkeypress="return validarNumerosEnteros(event)"/>
											</td>
											<td>/</td>
											<td>
												<s:textfield name="medioConvocatoria.fechaPublicacion.mes" id="mesMedio"
													onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
													onkeypress="return validarNumerosEnteros(event)"/>
											</td>
											<td>/</td>
											<td>
												<s:textfield name="medioConvocatoria.fechaPublicacion.anio" id="anioMedio"
													onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
													onkeypress="return validarNumerosEnteros(event)"/>
											</td>
											<td>
												<a id="botonFechaReunion" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMedio, document.formData.mesMedio, document.formData.diaMedio);return false;" title="Seleccionar fecha"> 
													<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" /> 
												</a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">	
					<tr>
						<td align="center" nowrap="nowrap">
							<input type="button" id="botonAnadirMedio" value="Añadir" class="botonpeque" onclick="validacionMedio();" />	
							<img id="loading3.3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
						</td>
					</tr>
				</s:if>
			</table>
		</td>
		<td align="center" width="60%">
			<span style="color: #990000;text-align:center;">
				Lista de medios de publicaci&oacute;n 
			</span>
			<br/> 
			<s:if test="tramiteRegistro.reunion.medios != null && tramiteRegistro.reunion.medios.size() > 0">
				<display:table name="tramiteRegistro.reunion.medios" excludedParams="*" class="tablacoin" 
					summary="Lista De Medios De Publicación" cellspacing="0" cellpadding="0" id="row">
						<display:column property="fechaPublicacion" title="Fecha" style="width:20%;text-align:center;" />
						<display:column property="medio.descMedio" title="Descripción" style="width:30%;text-align:center;" />
						<display:column property="medio.tipoMedio" title="Tipo Medio" style="width:30%;text-align:center;" />
						<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		  					<display:column title="Eliminar" style="width:%;">
		  						<a onclick="" href="javascript:eliminarMedio(${row.medio.idMedio})">Eliminar</a>
		  					</display:column>
		  				</s:if>
	  			</display:table>
	    	</s:if>
		</td>
	</tr>
</table>
		