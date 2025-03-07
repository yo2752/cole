
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
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/solicitudes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>

<script type="text/javascript">
</script>



<ol id="toc">
	<li><a href="#Consulta">Consulta de una solicitud de Datos </a></li>    
    <li><a id="pestaniaHistorico" href="#Historico">Histórico </a></li>
</ol>

<s:form method="post" id="formData" name="formData" cssStyle="100%">
	
	<s:hidden name="solicitud.tramiteTrafico.numExpediente"> </s:hidden>
	<s:hidden name="solicitud.tramiteTrafico.vehiculo.idVehiculo"></s:hidden>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div class="contentTabs" id="Consulta"  style="width:100%;">
		<table>
			<s:if test="%{mensajeErrorFormulario!=null}">
				<tr>
					<td colspan="8" class="mensajeTabla">
						<div class="cajaMensaje">
							<ul><li><s:property value="mensajeErrorFormulario"/></li></ul>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="8">&nbsp;</td>
				</tr>
			</s:if>
		</table>
				
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">VEHÍCULO: </span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  class="tablaformbasica" width="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="matricula">Matrícula: </label>
				</td>

				<td>
					<s:textfield name="solicitud.tramiteTrafico.vehiculo.matricula"
						id="matricula" 
						style="text-transform : uppercase"
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="9" maxlength="9" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  ></s:textfield>
				</td>

				<td align="left" nowrap="nowrap" width="10%">
					<label for="bastidor">Nº Bastidor: </label>
				</td>

				<td>
					<s:textfield name="solicitud.tramiteTrafico.vehiculo.bastidor"
						id="bastidor" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="21" maxlength="21"></s:textfield>
				</td>
			</tr>
		</table>
		
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">TASA</span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  class="tablaformbasica" width="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="tipoTasa">Tipo de Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap" >
	               	<label for="tipoTasa">4.1</label>
				</td>

				<td align="left" nowrap="nowrap" width="13%">
	   				<label for="codigoTasa">Código de Tasa: <span class="naranja">*</span></label>
	   			</td>
	   			
	         	<td align="left" nowrap="nowrap">	
					<s:textfield name="solicitud.tramiteTrafico.tasa.codigoTasa"
						id="codigoTasa" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="12" maxlength="12"></s:textfield>			
				</td>	
			</tr>
		</table>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes()}"> 
			<table class="acciones">
				<tr>
					<td  align="center" style="size: 100%; TEXT-ALIGN: center;">											
						<input id="bConsultarSolicitudDatos" 
							type="button" 
							value="Consultar" 
							style="size: 100%; TEXT-ALIGN: center;" 
							onClick="return consultarSolicitud(this);"/>
					</td>						
	
					<td  align="center" style="size: 100%; TEXT-ALIGN: center;">											
						<input id="bHistoricoSolicitudDatos" 
							type="button" 
							value="Histórico" 
							style="size: 100%; TEXT-ALIGN: center;" 
							onClick="return consultarHistorico(this);"/>
					</td>						
				</tr>
		
				<tr>
					<td colspan="2">
						<img id="loadingImage" 
							style="display:none;margin-left:auto;margin-right:auto;" 
							src="img/loading.gif"/>
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	
	<div class="contentTabs" id="Historico"  style="width:100%;">
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">Consulta de Informes: </span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">					
			<tr>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="matricula">Matrícula: </label>
				</td>

				<td>
					<s:textfield name="solicitudHistorico.tramiteTrafico.vehiculo.matricula"
						id="matriculaHistorico" 
						style="text-transform : uppercase"
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="9" maxlength="9"></s:textfield>
				</td>

				<td align="left" nowrap="nowrap" width="10%">
					<label for="bastidor">Nº Bastidor: </label>
				</td>

				<td>
					<s:textfield name="solicitudHistorico.tramiteTrafico.vehiculo.bastidor"
						id="bastidorHistorico" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="21" maxlength="21"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td align="left" nowrap="nowrap" width="13%">
	   				<label for="codigoTasa">Código de Tasa: <span class="naranja">*</span></label>
	   			</td>
	   			
	         	<td align="left" nowrap="nowrap">	
					<s:textfield name="solicitudHistorico.tramiteTrafico.tasa.codigoTasa"
						id="codigoTasaHistorico" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="12" maxlength="12"></s:textfield>			
				</td>
			</tr>
		</table>
		
		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
   					<label>Fecha de búsqueda:</label>
   				</td>

				<td align="left" nowrap="nowrap" width="5%">
					<label for="desde">desde: </label>
				</td>
				
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="fechaBusqueda.diaInicio" 
						id="diaBusquedaIni"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarDia(this,event)"
						size="2" maxlength="2" />
				</td>

				<td width="1%">/</td>

				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="fechaBusqueda.mesInicio" 
						id="mesBusquedaIni"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarMes(this,event)"
						size="2" maxlength="2" />
				</td>

				<td width="1%">/</td>
		
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="fechaBusqueda.anioInicio" 
						id="anioBusquedaIni"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarAnio(this,event)"
						size="5" maxlength="4" />
				</td>
		
				<td align="left" nowrap="nowrap">
    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaIni, document.formData.mesBusquedaIni, document.formData.diaBusquedaIni);return false;" 
    					title="Seleccionar fecha">
    					<img class="PopcalTrigger" 
    						align="left" 
    						src="img/ico_calendario.gif" 
    						width="15" height="16" 
    						border="0" alt="Calendario"/>
    				</a>
				</td>

				<td align="left" align="left" width="5%">
					<label for="diaPresentacionFin">hasta:</label>
				</td>
					
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="fechaBusqueda.diaFin" 
						id="diaBusquedaFin"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarDia(this,event)"
						size="2" maxlength="2" />
				</td>
					
				<td width="1%">/</td>
					
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="fechaBusqueda.mesFin" 
						id="mesBusquedaFin"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarMes(this,event)"
						size="2" maxlength="2" />
				</td>
					
				<td width="1%">/</td>
					
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="fechaBusqueda.anioFin" 
						id="anioBusquedaFin"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarAnio(this,event)"
						size="5" maxlength="4" />
				</td>
					
				<td align="left" nowrap="nowrap">
		   			<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaFin, document.formData.mesBusquedaFin, document.formData.diaBusquedaFin);return false;" 
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
		
		<table class="acciones">
			<tr>
				<td>
					<input type="button" 
						class="boton" 
						name="bBuscarHistorico" 
						id="bBuscarHistorico" 
						value="Buscar"  
						onkeypress="this.onClick" 
						onclick="return buscarHistorico(this);"/>			
				</td>
				<td>
					<input type="reset" 
						class="boton" 
						name="bLimpiar" 
						id="bLimpiar" 
						value="Limpiar"  />			
				</td>
			</tr>
		</table>
			
		
		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>

		<div id="resultado" style="width:100%;background-color:transparent;" >
			<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
				<tr>
					<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
				</tr>
			</table>		

			<s:if test="%{listaConsultaTramiteTrafico.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
				<table width="100%">
					<tr>
						<td align="right">
							<table width="100%">
								<tr>
									<td width="90%" align="right">
										<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
									</td>
	
									<td width="10%" align="right">
										<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											id="idResultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="return cambiarElementosPorPaginaConsultaTrafico();"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
		</div>
		
		<div class="divScroll">
			<display:table name="listaConsultaSolicitud" 
				excludedParams="*" 
				requestURI="navegarConsultaSolicitudDatos.action" 
				class="tablacoin"
				uid="tablaConsulta"
				summary="Listado de Tramites"
				cellspacing="0" cellpadding="0">		
					
				<display:column property="tramiteTrafico.vehiculo.matricula" title="Matrícula"
					sortable="true" headerClass="sortable"
					defaultorder="descending"
					style="width:4%" 
					href="detalleConsultaTramiteTrafico.action"
					paramId="matricula" 	 
					/>
					
				<display:column property="tramiteTrafico.vehiculo.bastidor" title="Bastidor"
					sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="20"
					style="width:4%"/>
				
				<display:column property="tramiteTrafico.tasa.codigoTasa" title="Codigo de Tasa"
					sortable="true" headerClass="sortable"
					defaultorder="descending"
					style="width:4%"/>
							
				<!--
				<display:column property="usuario" title="Usuario"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:4%"/>	
				-->
					
				<display:column property="fechaInforme" title="Fecha"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:4%"/>
				
				<display:column title="resultado" property="Resultado"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%"/>
	
				<display:column title="<input type='checkbox' name='checkAll' 
						onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' 
						onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" 
									name="listaChecksConsultaTramite" 
									id="check<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
									value='<s:property value="#attr.tablaConsultaTramite.numExpediente"/>' />
							</td>
						</tr>
					</table>		
	 			</display:column>
					
				<input type="hidden" name="resultadosPorPagina"/>
			</display:table>
		</div>

		<s:if test="%{(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoTrafico())&&
					  (listaConsultaTramiteTrafico.getFullListSize()>0)}">
			<table class="acciones">		    	
		    	<tr>
		    		<td>
			    		<input class="boton" 
		    				type="button" 
		    				id="bImprimirDocumentosBusquedaSolicitud" 
		    				name="bImprimirDocumentos" 
		    				value="Imprimir" 
		    				onClick="return imprimirDocumentosBusquedaSolicitud(this);" 
		    				onKeyPress="this.onClick" />
		    		</td>
		    	</tr>
		    </table>
    	</s:if>
	</div>
</s:form>

<!-- Scripts que se ejecutan para el funcionamiento de la pantalla -->
	
