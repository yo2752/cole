<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular"><span class="titulo">
						Resumen de tasas
					</span></td>
				</tr>
			</table>
		</div>	
		<s:form method="post" id="formData" name="formData" action="buscarResumenTasa.action">
			<div id="busqueda">					
				<table cellSpacing="3" class="tablaformbasica" cellPadding="0" width="100%">
					<tr>
						<td align="right" nowrap="nowrap" width="14%">
        					<label for="TipoTasa">Tipo de Tasa:</label>
        				</td>
        				<td align="left" width="2%">
        				</td>
        				<td align="left">
        					<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTasa()"
									headerKey="-1"
			           				headerValue=""
									name="tipoTasa" 
									listKey="valorEnum" listValue="nombreEnum"
									id="TipoTasa"
									onblur="this.className='input2';" 
       								onfocus="this.className='inputfocus';"/>
        				</td>
        			</tr>
        			<tr>
        				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
	        				<td align="right" nowrap="nowrap" width="14%">
	        					<label for="NumeroColegiado">N&uacute;mero de colegiado:</label>        				
	        				</td>
	        				<td align="left" width="2%">
        					</td>
        					<td align="left">
	        					<s:textfield name="numColegiado" id="NumeroColegiado" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"></s:textfield>
	        				</td>
        				</s:if>
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
									<s:textfield name="fechaAlta.diaInicio" id="diaAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="fechaAlta.mesInicio" id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="fechaAlta.anioInicio" id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" HIDEFOCUS title="Seleccionar fecha">
				    				<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    				</a>
								</td>
								<td width="2%"></td>
							</tr></TABLE>
						</td>
						<td align="left"><TABLE WIDTH=100%>
							<tr>
						<td align="left"><label for="diaAltaFin">hasta:</label></td>
						<td>
							<s:textfield name="fechaAlta.diaFin" id="diaAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="fechaAlta.mesFin" id="mesAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td>
							<s:textfield name="fechaAlta.anioFin" id="anioAltaFin"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td>
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" HIDEFOCUS title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		   			    	</a>
						</td>
						
							</tr></TABLE></td>
					</tr>
				</table>
				<div id="botonBusqueda">
				<table width="100%">
					<tr>
						<td>
							<s:submit value="Buscar" cssClass="boton"/>			
						</td>
					</tr>
				</table>
				</div>
			</div>		 <%-- /Div recuadro de búsqueda --%>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>
			
	<div id="resultado" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		<%@include file="../../includes/erroresYMensajes.jspf" %>
	</div>
	<display:table name="listaResumenTasa" excludedParams="*"
					requestURI="navegarResumenTasa.action"
					class="tablacoin"
					uid="tablaResumenTasa"
					summary="Resumen de Tasas"
					cellspacing="0" cellpadding="0">
		<display:column property="tipo_tasa" title="Tipo Tasa"
				defaultorder="descending" 
				style="width:4%"/>
		<display:column property="total" title="Total"
				defaultorder="descending" 
				style="width:4%"/>
		<display:column property="asignadas" title="Asignadas"
				defaultorder="descending" 
				style="width:4%"/>
		<display:column property="desasignadas" title="Desasignadas"
				defaultorder="descending" 
				style="width:4%"/>
	</display:table>
	<input type="hidden" name="resultadosPorPagina"/>
    </s:form>
    