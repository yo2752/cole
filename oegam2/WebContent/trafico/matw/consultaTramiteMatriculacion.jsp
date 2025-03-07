<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script  type="text/javascript">  

</script>







<!--<div class="contentTabs" id="ConsultaTramitesMatriculacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">-->

			

  
		<div id="contenido" class="contentTabs" style="display: block;">	
			<div class="nav">
				<table width="100%" >
					<tr>
						<td class="tabular"><span class="titulo">
							Consulta de trámites de matriculación
						</span></td>
					</tr>
				</table>
			</div>	
		<s:form method="post" id="formData" name="formData">
				<!--<div id="mostrarOcultarForm" style="text-align:right;width:720px;">
					<div id="mostrarForm" style="display:none;">
						<a href="javascript:mostrarFormulario(document.getElementById('busqueda'),document.getElementById('mostrarForm'),document.getElementById('ocultarForm'));"><label>Mostrar formulario de búsqueda </label>
						<img width="16" height="16" alt="Mostrar criterios de busqueda" src="img/mostrar.gif" complete="complete"/></a>
					</div>
					<div id="ocultarForm" style="display:block;">
						<a href="javascript:ocultarFormulario(document.getElementById('busqueda'),document.getElementById('ocultarForm'),document.getElementById('mostrarForm'));"><label>Ocultar formulario de búsqueda </label>
						<img width="16" height="16" alt="Mostrar criterios de busqueda" src="img/ocultar.gif" complete="complete"/></a>
					</div>
				</div>	-->
			<div id="busqueda">					
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
					<tr>
        				<td align="left" nowrap="nowrap">
							<label for="estadoTramite">Estado del trámite</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@escrituras.utiles.UtilesVista@getInstance().getComboEstados()"
									headerKey="-1"
			           				headerValue="TODOS"
									name="beanConsultaMatriculacion.estado" 
									listKey="valorEnum" listValue="nombreEnum"
									id="idEstadoTramite"/>
						</td>	        		
	    			        	       			
        				<td align="left" nowrap="nowrap">
        				<label for="dniTitular">NIF Titular</label>
        				</td>
        				<td align="left">
        				<s:textfield name="beanConsultaMatriculacion.nif" id="dniTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
        				</td>		        	
		        	</tr>
		        	
		        	<tr>        	       			
        				<td align="left" nowrap="nowrap">
        				<label for="RefPropia">Referencia Propia</label>        				
        				</td>
        				<td align="left">
        					<s:textfield name="beanConsultaMatriculacion.refPropia" id="ReferenciaPropia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
        				</td>
        				
		        		<td align="left" nowrap="nowrap">
        				<label for="NumeroExpediente">NºExpediente</label>
        				</td>
        				<td align="left">
        				<s:textfield name="beanConsultaMatriculacion.numExpediente" id="NumeroExpediente" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxlength="50"/>
        				</td>
		        	</tr>

					<tr>        	       			
        				<td align="left" nowrap="nowrap">
        				<label for="NumeroBastidor">Nº de Bastidor</label>
        				</td>
        				<td align="left">
        				<s:textfield name="beanConsultaMatriculacion.bastidor" id="NumeroBastidor" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
        				</td>
        				
  		        		<td align="left" nowrap="nowrap">
        				<label for="Matricula">Matrícula</label>
        				</td>
        				<td align="left">
        				<s:textfield name="beanConsultaMatriculacion.matricula" id="Matricula" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
        				</td>
		        	</tr>
		        </table>
		        <table cellSpacing="3" class="tablaformbasica" cellPadding="0">
		        	
		        	<tr>
		        		<td align="right" nowrap="nowrap">
        				<label>Fecha de alta:</label>
        				</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
								<td align="right"><label for="diaAltaIni">desde: </label></td>
								<td>
									<s:textfield name="beanConsultaMatriculacion.fechaAlta.diaInicio" id="diaAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="beanConsultaMatriculacion.fechaAlta.mesInicio" id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="beanConsultaMatriculacion.fechaAlta.anioInicio" id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" title="Seleccionar fecha">
				    				<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    				</a>
								</td>
								<td width="2%"></td>
							</tr></TABLE>
						</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
						<td align="left"><label for="diaAltaFin">hasta:</label></td>
						<td>
							<s:textfield name="beanConsultaMatriculacion.fechaAlta.diaFin" id="diaAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="beanConsultaMatriculacion.fechaAlta.mesFin" id="mesAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="beanConsultaMatriculacion.fechaAlta.anioFin" id="anioAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td>
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		   			    	</a>
						</td>
						
							</tr></TABLE>
					</tr>



					<tr>
		        		<td align="left" nowrap="nowrap">
        				<label>Fecha de presentación:</label>
        				</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
								<td align="right"><label for="diaPresentacionIni">desde: </label></td>
								<td>
									<s:textfield name="beanConsultaMatriculacion.fechaPresentacion.diaInicio" id="diaPresentacionIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="beanConsultaMatriculacion.fechaPresentacion.mesInicio" id="mesPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="beanConsultaMatriculacion.fechaPresentacion.anioInicio" id="anioPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionIni, document.formData.mesPresentacionIni, document.formData.diaPresentacionIni);return false;" title="Seleccionar fecha">
				    				<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    				</a>
								</td>
								<td width="2%"></td>
							</tr></TABLE>
						</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
						<td align="left"><label for="diaPresentacionFin">hasta:</label></td>
						<td>
							<s:textfield name="beanConsultaMatriculacion.fechaPresentacion.diaFin" id="diaPresentacionFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="beanConsultaMatriculacion.fechaPresentacion.mesFin" id="mesPresentacionFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="beanConsultaMatriculacion.fechaPresentacion.anioFin" id="anioPresentacionFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td>
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionFin, document.formData.mesPresentacionFin, document.formData.diaPresentacionFin);return false;" title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		   			    	</a>
						</td>
<!--						</td>-->
							</tr></TABLE>
					</tr>





		        	
				</table>
				<table class="acciones">
					<tr>
						<td>
							<%-- <input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onClick="return buscar();" onKeyPress="this.onClick"/> --%>
							<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar"  onkeypress="this.onClick" onclick="consultaBuscar()"/>			
						</td>
					</tr>
				</table>
			</div>		 <%-- /Div recuadro de búsqueda --%>	
			
			
			
			<div id="resultado">
				<table class="subtitulo" cellSpacing="0" cellspacing="0">
					<tr>
						<td style="width: 100%; text-align: center;">Resultado de la búsqueda</td>
					</tr>
				</table>
			</div>   <%-- /Div resultado --%>
			</s:form>
		</div>
<!--	</div>-->











<s:hidden key="contrato.idContrato"/>


<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>


