<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/estadisticaImpresion/estadisticaImpresion.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Estadísticas Impresión</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Busqueda:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="fechaBusqueda.diaInicio" id="diaFechaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="fechaBusqueda.mesInicio" id="mesFechaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="fechaBusqueda.anioInicio" id="anioFechaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaIni, document.formData.mesFechaIni, document.formData.diaFechaIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="fechaBusqueda.diaFin" id="diaFechaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="fechaBusqueda.mesFin" id="mesFechaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="fechaBusqueda.anioFin" id="anioFechaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaFin, document.formData.mesFechaFin, document.formData.diaFechaFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Jefatura:</label>
					</td>
					<td align="left" nowrap="nowrap">	
						<s:select id="jefaturaProvincial" onblur="this.className='input2';" disabled="%{flagDisabled}"
							onfocus="this.className='inputfocus';" name="jefatura"
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getJefaturasTrafico()"
							headerKey="-1" headerValue="Seleccione Jefatura Provincial" listKey="jefatura_provincial" listValue="descripcion"/>					
					</td>
					<td align="left" nowrap="nowrap">
							<label for="labelContrato">Tipo Documento:</label>
					</td>
					<td  align="left">
						<s:select id="idTipoDocumento" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getTiposDocumentos()"
							onblur="this.className='input2';" headerValue="Seleccione Tipo Doc." onfocus="this.className='inputfocus';" listKey="valorEnum" headerKey="" 
							listValue="nombreEnum" cssStyle="width:220px" name="tipoDocumento"
						/>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bGenerar" id="bGenerarExcel" value="Generar Excel"  onkeypress="this.onClick" onclick="return generar();"/>		
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarFormulario();" value="Limpiar"/>		
		</div>
	</s:form>
</div>
