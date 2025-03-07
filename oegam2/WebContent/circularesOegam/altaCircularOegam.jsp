<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/circularesOegam.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Circulares OEGAM</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Alta Circular</td>
		</tr>
	</table>
	<s:form method="post" id="formData" name="formData">
		<div class="contenido">	
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
							<label for="labelId">ID:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield id="idCircular" name="circularDto.idCircular"  onblur="this.className='input2';" 
					       			onfocus="this.className='inputfocus';" size="6" maxlength="6" disabled="true"/>
				   	</td>
				   	<td align="left" nowrap="nowrap"  id="textBoxNumCir">
		       			<label for="modelo">Núm Circular: <span class="naranja">*</span></label>
		 			</td>
		 			
		  			<td align="left" nowrap="nowrap">
		      			<s:textfield id="idNumCircular" name="circularDto.numCircular" onblur="this.className='input2';" 
		      				onfocus="this.className='inputfocus';"  size="9" maxlength="9"/>
		   			</td>
			    </tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaI">Fecha Inicio:<span class="naranja">*</span></label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="left">
									<s:textfield name="circularDto.fechaInicio.dia" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="circularDto.fechaInicio.mes" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="circularDto.fechaInicio.anio" id="anioFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIni, document.formData.mesFechaPrtIni, document.formData.diaFechaPrtIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</table>
				<table class="tablaformbasica">
					<tr>
					<td align="left">
						<label for="labelFechaH">Tipo Finalizacion:  <span class="naranja">*</span></label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="left" nowrap="nowrap">
									<label for="labelRepeticiones">Repeticiones:</label>
								</td>
								<td align="left" nowrap="nowrap">
									<s:checkbox name="circularDto.repeticiones" id="idRepeticionesCirc" onblur="this.className='input';" onfocus="this.className='inputfocus';"
									onchange="javascript:cambiarCheckRepe('idRepeticionesCirc','idDias','idFecha','diaFechaPrtFin','mesFechaPrtFin','anioFechaPrtFin','idCalendar');"/>
								</td>
								<td align="left" nowrap="nowrap" >
		       						<label for="modelo">Dias:</label>
		 						</td>
		  						<td align="left" nowrap="nowrap">
		      						<s:textfield id="idDias" name="circularDto.dias" onblur="this.className='input2';" 
		      							onfocus="this.className='inputfocus';"  size="2" maxlength="2" disabled="true" />
		   						</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">
									<label for="labelFecha">Fecha:</label>
								</td>
								<td align="left" nowrap="nowrap">
									<s:checkbox name="circularDto.fecha" id="idFecha" onblur="this.className='input';" onfocus="this.className='inputfocus';"
									onchange="javascript:cambiarCheckFecha('idRepeticionesCirc','idDias','idFecha','diaFechaPrtFin','mesFechaPrtFin','anioFechaPrtFin','idCalendar');"/>
								</td>
								<td align="right">
									<label for="labelFechaF">Fecha fin:</label>
								</td>
								<td align="left">
									<s:textfield name="circularDto.fechaFin.dia" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="circularDto.fechaFin.mes" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="circularDto.fechaFin.anio" id="anioFechaPrtFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" disabled="true"/>
								</td>
								<td align="left">
									<a id="idCalendar"href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFin, document.formData.mesFechaPrtFin, document.formData.diaFechaPrtFin);return false;" 
					  					title="Seleccionar fecha" >
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"  width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
		   			<td align="left" width="150px">
						<label for="labelRespuesta">Texto:<span class="naranja">*</span></label>
					</td>
					<td>
						<table>
							<tr>
								<td align="left"  nowrap="nowrap">
									<s:textarea name="circularDto.texto" id="idTextoCircular" disabled="false"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  rows="15" cols="50"/>
								</td>
							</tr>					
						</table>
					</td>
		   		</tr>
			</table>
			<table class="acciones" width="95%" align="left">
				<tr>
					<td>
						<input type="button" class="boton" name="bGuardarCircular" id="idGuardarCircular" value="Guardar" onClick="javascript:guardaCircularOegam();"
				 			onKeyPress="this.onClick"/>
						<input type="button" class="boton" name="bVolverCircular" id="idVolverCircular" value="Volver" onClick="javascript:volverCircularOegam();"
				 			onKeyPress="this.onClick"/>	
				 	</td>
				</tr>
			</table>
		</div>
	</s:form>
</div>
<style type="text/css">
	textarea {
		border-style: inset;
	  	border-color: silver;
	  	overflow: auto;
	  	outline: none;
	  	resize: none;
	}

	#textBoxNumCir {
   text-align: right;

}


</style>