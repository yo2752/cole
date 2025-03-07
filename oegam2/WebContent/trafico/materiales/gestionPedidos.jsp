<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<style>
	.hidden { display: none; }
</style>

<!--  //TODO MPC. Cambio IVTM. Este jsp es el bueno -->
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<script src="js/trafico/materiales/gestionPedidos.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Gestión de	Pedidos</span></td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
	<%@include file="../../../includes/erroresYMensajes.jspf" %>
	
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelinventarioId">Pedido inventario:</label>
				</td>
				<td  align="left">
					<s:textfield name="pedidosFilterBean.pedidoId"
								 type="number" 
								 id="labelinventarioId" 
					             size="10" maxlength="10"
					             onkeypress="return isNumber(event)" 
					             onblur="this.className='input';" 
						         onfocus="this.className='inputfocus';"/>
				</td>
				
				<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelMateriales">Material:</label>
				</td>
				<td style="width: 25%;">
					<s:select id="labelMateriales"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getComboMateriales()"
						headerKey=""
				   		headerValue="Seleccione Material"
						name="pedidosFilterBean.materialId" 
						listKey="materialId" 
						listValue="nombre"/>	
				</td>
				
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelcodigoInicial">Código Inicial:</label>
				</td>
				<td  align="left">
					<s:textfield name="pedidosFilterBean.codigoInicial" id="labelcodigoInicial" 
					             size="25" maxlength="25" 
					             onblur="this.className='input';" 
								 onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelcodigoFinal">Código Final:</label>
				</td>
				<td  align="left">
					<s:textfield name="pedidosFilterBean.codigoFinal" id="labelcodigoFinal" 
					             size="25" maxlength="25" 
					             onblur="this.className='input';" 
								 onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
	       	<tr>
       			<td align="left" nowrap="nowrap">
	       			<label for="labelEstado">Estado:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<s:select 
						list="@org.gestoresmadrid.oegam2comun.trafico.materiales.utiles.enumerados.UtilesEstadosPedido@getEstadoPedido()"
						id="labelEstado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="pedidosFilterBean.estado"
						headerKey=""
				   		headerValue="Seleccione un estado"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Estado"
						disabled="false"/>
       			</td>
       			
       			<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelJefaturaProvJpt">Jefatura Provincial: </label>
				</td>
				<td  align="left" style="width: 25%;">
					<s:select id="labelJefaturaProvJpt"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getJefaturasJPTEnums()"
						headerKey=""
				   		headerValue="Seleccione Jefatura Provincial"
						name="pedidosFilterBean.jefaturaProvincial" 
						listKey="jefatura" 
						listValue="descripcion"/>	
				</td>
       			
       		</tr>
		</table>
	
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha">Fecha de Alta:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="pedidosFilterBean.fecha.diaInicio" id="diaFechaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="pedidosFilterBean.fecha.mesInicio" id="mesFechaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="pedidosFilterBean.fecha.anioInicio" id="anioFechaIni"
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
								<s:textfield name="pedidosFilterBean.fecha.diaFin" id="diaFechaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="pedidosFilterBean.fecha.mesFin" id="mesFechaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="pedidosFilterBean.fecha.anioFin" id="anioFechaFin"
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
		</table>

		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Buscar" onkeypress="this.onClick"
					onclick="javascript:buscarGestionPedidos();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					onclick="javascript:limpiarGestionPedidos();" value="Limpiar" />
				</td>
			</tr>
		</table>

		<div id="resultado"
			style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
				</tr>
			</table>
		</div>

		<s:if
			test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarGestionPedidos.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>

		<%@include file="../../includes/erroresYMensajes.jspf"%>

		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*"
				requestURI="navegarGestionPedidos.action" class="tablanotifi"
				uid="tablaGestionPedidos" summary="Gestion de Pedidos" id="tablaGestionPedidos"
				cellspacing="0" cellpadding="0" sort="external" decorator="${decorator}">

				<display:column property="pedidoId" title="Id" class="hidden" headerClass="hidden" />

			    <display:column title="Pedido" style="width:1%">
	                    <a href="#" onclick="abrirEvolucion(<s:property value="#attr.tablaGestionPedidos.pedidoId"/>,'divEmergenteConsultaDetallePedido', 'cargarDetallePedido.action');">
	                    	<s:property value="#attr.tablaGestionPedidos.pedidoId"/>
	                    </a>
			    </display:column>

				<display:column property="materialVO" title="Material" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%" />

				<display:column property="unidades" title="Unidades" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%" />

				<display:column property="codigoInicial" title="Inicial"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:1%" />

				<display:column property="codigoFinal" title="Final" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%" />

				<display:column property="jefaturaProvincial" title="Jefatura"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:1%" />

				<display:column property="estado" title="Estado" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%" />

				<display:column property="fecha" title="Fecha" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%"
					format="{0, date, dd/MM/yyyy}" />

				<display:column property="pedidoPermisosEntregado" class="hidden" headerClass="hidden" />
				<display:column property="pedidoEntregado"         class="hidden" headerClass="hidden" />
				<display:column property="solicitarPedido"         class="hidden" headerClass="hidden" />
				<display:column property="entregarPedido"          class="hidden" headerClass="hidden" />
				
				<display:column	style="width:5%">
					<img src="img/history.png" alt="Ver Evolución"
					     style="margin-right: 10px; height: 20px; width:15px; cursor: pointer"
					     onclick="abrirEvolucion(<s:property value="#attr.tablaGestionPedidos.pedidoId" />,'divEmergenteConsultaEvolutivoPedido', 'cargarEvolucionPedido.action');"
					     title="Ver Evolución" />
					     
					<s:if test="%{#attr.tablaGestionPedidos.pedidoEntregado}">
						<img src="img/genlicense.png" alt="Ver Evolución"
						     style="margin-right: 10px; height: 20px; width:15px; cursor: pointer"
						     onclick="rellenarSeriales(<s:property value="#attr.tablaGestionPedidos.pedidoId" />,
						                               <s:property value="#attr.tablaGestionPedidos.unidades" />);"
						     title="Añadir Seriales" />
					</s:if>
					<s:if test="%{#attr.tablaGestionPedidos.pedidoPermisosEntregado}">
	  					<img src="img/package_icon.png" alt="Gestionar Paquetes"
	  					     style="margin-right: 10px; height: 20px; width: 15px; cursor: pointer;"
					     	 onclick="abrirPaquetes(<s:property value="#attr.tablaGestionPedidos.pedidoId" />,'divEmergenteConsultaPaquetesPedido', 'formData','cargarPaquetesPedido.action');"
	  						 title="Gestionar Paquetes"/>
					</s:if>
					
				</display:column>

				<display:column	style="width:1%" title="Sincro.">
					<center>
						<input type="checkbox" name="IdActualizaciones" 
						    id="check_sin__<s:property value="#attr.tablaGestionPedidos.pedidoId" />" 
							value='<s:property value="#attr.tablaGestionPedidos.pedidoId" />' />
					</center>							
				</display:column>

				<display:column	style="width:1%" title="Solic.">
					<center>
						<s:if test="%{#attr.tablaGestionPedidos.solicitarPedido}">
							<input type="checkbox" name="IdSolicitudes" 
							    id="check_sol_<s:property value="#attr.tablaGestionPedidos.pedidoId" />" 
								value='<s:property value="#attr.tablaGestionPedidos.pedidoId" />' />
						</s:if>
					</center>							
				</display:column>

<%-- 				<display:column style="width:1%" title="Entre."> --%>
<!-- 					<center> -->
<%-- 						<s:if test="%{#attr.tablaGestionPedidos.entregarPedido}"> --%>
<!-- 							<input type="checkbox" name="IdEntregados"  -->
<%-- 							    id="check_ent_<s:property value="#attr.tablaGestionPedidos.pedidoId" />"  --%>
<%-- 								value='<s:property value="#attr.tablaGestionPedidos.pedidoId" />' /> --%>
<%-- 						</s:if> --%>
<!-- 					</center>							 -->
<%-- 				</display:column> --%>
			</display:table>
		</div>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" 
					       class="boton" 
					       name="bCrearPedido" 
					       id="bCrearPedido" 
					       value="Crear Pedido" 
					       onkeypress="this.onClick" 
					       onclick="self.location.href = 'altaCrearPedido.action'"/>
					<input type="button" 
					       class="boton" 
					       name="bSincronizarConsejo" 
					       id="bSincronizarConsejo" 
					       value="Sincronizar" 
					       onkeypress="this.onClick" 
					       onClick="actualizarInformacionPedido();"/>
					<input type="button" 
					       class="boton" 
					       name="bSolicitarConsejo" 
					       id="bSolicitarConsejo" 
					       value="Solicitar" 
					       onkeypress="this.onClick" 
					       onClick="solicitarPedidos();"/>
					<!-- Espera respuesta consejo					       
					<input type="button" 
					       class="boton" 
					       name="bEntregarConsejo" 
					       id="bEntregarConsejo" 
					       value="Entregar" 
					       onkeypress="this.onClick" 
					       onClick="entregarPedidos();"/>
					 -->					       
				</td>
			</tr>
		</table>
		

	</div>

	<div id="divEmergenteConsultaEvolutivoPedido" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaDetallePedido"   style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaPaquetesPedido"  style="display: none; background: #f4f4f4;"></div>

	<div id="dialog-serial" title="Añadir Codigo Incial y Final">
	
	  <br>
	  <br>
	 
  	  <p class="validateTips">Todos los campos son requeridos.</p>
  	  <form id="idFormSerial">
	  		<table>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelprefijo">Prefijo:</label>
					</td>
					<td  align="left">
						<s:textfield name="prefijo" id="labelprefijo" 
						             size="4" maxlength="4" 
						             onblur="this.className='input';" 
									 onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelcodInicial">Código Inicial:</label>
					</td>
					<td  align="left">
						<s:textfield name="codigoInicial" id="labelcodInicial" 
						             size="15" maxlength="10" 
						             onblur="this.className='input';" 
									 onfocus="this.className='inputfocus';" 
									 onfocusout="rellenarCodigoFinal();"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelcodFinal">Código Final:</label>
					</td>
					<td  align="left">
						<s:textfield name="codigoFinal" id="labelcodFinal" 
						             size="15" maxlength="10" 
						             onblur="this.className='input';" 
									 onfocus="this.className='inputfocus'; "/>
					</td>
				</tr>
	  		</table>
	  </form>
	</div>
	
	<div id="dialog-form" ></div>
	
	<s:if test="hasMsg">
		<div id="dialog-confirm" title="Resultado Alta Incidencia">
			<div id="divMsg">
				<table  >
					<tr>
						<td align="left">
							<s:if test="typeMsg.equals('ERROR')">
								<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
									<s:iterator value="errores" status="errorStatus">
										<li><span><s:property/></span></li>							
									</s:iterator>
								</ul>
							</s:if>		
							<s:if test="typeMsg.equals('OK')">
								<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
									<li><span><s:text name="msg" /></span></li>							
								</ul>
							</s:if>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</s:if>
	
</s:form>

