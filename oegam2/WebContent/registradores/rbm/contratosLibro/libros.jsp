<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Libro" >
	<s:hidden name="tramiteRegistro.libroRegistro.idLibro" />
	<s:hidden name="tramiteRegistro.libroRegistro.idTramiteRegistro" />


	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Datos libros</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
			</td>
		</tr>
	</table>
		
	<%-- <iframe width="174"  
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
		
			<td align="left" nowrap="nowrap"><label for="nombreLibro" >Nombre libro:<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegistro.libroRegistro.nombreLibro" id="nombreLibro" size="18" maxlength="255"></s:textfield></td>

			<td align="left" nowrap="nowrap"><label for="nombreFichero" >Nombre fichero:<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegistro.libroRegistro.nombreFichero" id="nombreFichero" size="18" maxlength="255"></s:textfield></td>
		  	
		  	
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="numeroLibro" >N&uacute;mero:<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegistro.libroRegistro.numero" id="numeroLibro" size="18" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10')"></s:textfield></td>
		  	
		  	<td align="left" nowrap="nowrap"><label for="fechaApertura">Fecha apertura:<span class="naranja">*</span></label></td>
		  	<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecAperturaLibro.dia" id="diaFecApertura"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecApertura'), document.getElementById('mesFecApertura'), document.getElementById('anioFecApertura'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecAperturaLibro.mes" id="mesFecApertura"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecApertura'), document.getElementById('mesFecApertura'), document.getElementById('anioFecApertura'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecAperturaLibro.anio" id="anioFecApertura"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecApertura'), document.getElementById('mesFecApertura'), document.getElementById('anioFecApertura'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(anioFecApertura, mesFecApertura, diaFecApertura); resizeMe();  return false;" 
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
		</tr>
		
		
		<tr>
			<td align="left" nowrap="nowrap"><label for="fechaCierre">Fecha cierre:<span class="naranja">*</span></label></td>
		  	<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecCierreLibro.dia" id="diaFecCierre"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecCierre'), document.getElementById('mesFecCierre'), document.getElementById('anioFecCierre'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecCierreLibro.mes" id="mesFecCierre" 
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecCierre'), document.getElementById('mesFecCierre'), document.getElementById('anioFecCierre'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecCierreLibro.anio" id="anioFecCierre"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecCierre'), document.getElementById('mesFecCierre'), document.getElementById('anioFecCierre'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(anioFecCierre, mesFecCierre, diaFecCierre); resizeMe();  return false;" 
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
		  	
		  	<td align="left" nowrap="nowrap"><label for="fechaCierreAnterior">Fecha cierre anterior:</label></td>
		  	<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecCierreAnteriorLibro.dia" id="diaFecCierreAnterior"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecCierreAnterior'), document.getElementById('mesFecCierreAnterior'), document.getElementById('anioFecCierreAnterior'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecCierreAnteriorLibro.mes" id="mesFecCierreAnterior"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecCierreAnterior'), document.getElementById('mesFecCierreAnterior'), document.getElementById('anioFecCierreAnterior'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegistro.libroRegistro.fecCierreAnteriorLibro.anio" id="anioFecCierreAnterior"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "this.className='input2';validaInputFecha(document.getElementById('diaFecCierreAnterior'), document.getElementById('mesFecCierreAnterior'), document.getElementById('anioFecCierreAnterior'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(anioFecCierreAnterior, mesFecCierreAnterior, diaFecCierreAnterior); resizeMe();  return false;" 
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
		</tr>
		
	 </table> --%>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista libros</span>
			</td>
		</tr>
	</table>
	 
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="center">
		<tr>
			<td>
				<display:table name="tramiteRegistro.librosRegistro" uid="libros" class="tablacoin" id="libros"
					summary="Listado de libros" cellspacing="0" cellpadding="0" style="width:95%">
					<display:column title="Nombre libro" property="nombreLibro" style="text-align:left;"/>
					<display:column title="Nombre fichero" property="nombreFichero" style="text-align:left;"/>
					<display:column title="Número" property="numero"/>
					<display:column title="Fecha apertura" property="fecApertura" format="{0, date, dd/MM/yyyy}" />
					<display:column title="Fecha cierre" property="fecCierre" format="{0, date, dd/MM/yyyy}" />

<%-- 					<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
						<display:column title="Eliminar" style="width:10%">
							 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este libro?', 'formData', 'borrarLibro.action?identificador=${libros.idLibro}\u0023Libro', null);">
							 Eliminar </a>
						</display:column>
						<display:column style="width:3%">
							<a href="javascript:doPost('formData', 'modificarLibro.action?identificador=${libros.idLibro}\u0023Libro');">
								<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
							</a>
						</display:column>
					</s:if> --%>
				</display:table>
			</td>
		</tr>
	</table>
	<%-- &nbsp;&nbsp;
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<input type="button" value="Guardar" class="boton" id="botonGuardar" onclick="javascript:doPost('formData', 'guardarLibro.action\u0023Libro');"/>
	</s:if> --%>
</div>
