<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/datosSensibles.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js"
	src="calendario/ipopeng.htm"
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<!-- Se hace referencia a la parte de errores de los mensajes -->
<%@include file="../../includes/erroresMasMensajes.jspf" %>
<c:set var="myTipo" value="${datosSensiblesBean.tipoAgrupacion}"/>
<s:form method="post" id="formData" name="formData">
	<s:hidden id="grupoUsuario" name="grupoUsuario"/>
	<s:hidden id="flagDisabled" name="flagDisabled"/>
	<s:hidden key="contrato.idContrato"/>
	<s:hidden id="textoLegal" name="propTexto"/>
	<s:hidden id="resultadosPorPagina" name="resultadosPorPagina"/>
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Datos Sensibles</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda" aligN="left">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="labelFecha" >Fecha de Introduccion: </label>
				</td>
				<td>
					<table width="40%">
						<tr>
							<td align="right">
								<label for="labelDesde">desde: </label>
							</td>
							<td align="left">
								<s:textfield name="datosSensiblesBean.fecha.diaInicio" id="diaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="datosSensiblesBean.fecha.mesInicio" id="mesIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="datosSensiblesBean.fecha.anioInicio" id="anioIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="5" maxlength="4"/>
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioIni, document.formData.mesIni, document.formData.diaIni);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td>
					<table width="30%">
						<tr>
							<td align="right">
								<label for="labelHasta">hasta:</label>
							</td>	
							<td align="left">
								<s:textfield name="datosSensiblesBean.fecha.diaFin" id="diaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="datosSensiblesBean.fecha.mesFin" id="mesFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="datosSensiblesBean.fecha.anioFin" id="anioFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="5" maxlength="4"/>
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left">
					<label for="labelIdAgrupacion">Tipo de Dato: </label>
				</td>
				<td align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboDatosSensibles()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							onchange="deshabilitarTipoControl(this)"
							headerKey="-1"
							headerValue="Seleccione Tipo Dato"
							name="datosSensiblesBean.tipoAgrupacion" 
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoAgrupacion"/>
				</td>
				<td>
					<table width="30%">
						<tr>
							<td align="right">
								<label for="labelIdValor">Valor: </label>
							</td>
							<td align="left">
								<s:textfield name="datosSensiblesBean.textoAgrupacion" id="idTextoAgrupacion" onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';" onkeypress="trimCampo(this)" size="17" maxlength="17"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left">
					<label for="labelTipoDatosSensible">Tipo de Control: </label>
				</td>
				<td align="left">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoControlDatoSensible()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
			 				headerValue="Seleccione Tipo Control"
							name="datosSensiblesBean.tipoControl"
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoControl"/>
				</td>
				<td>
					<table width="30%">
						<tr>
							<td align="left" nowrap="nowrap">
								<label >Grupo: </label>
							</td>
							<td align="left">
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
									<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboGrupoUsuarios()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										headerKey="-1"
										headerValue="Seleccione Grupo"
										name="datosSensiblesBean.grupo"
										listKey="codigo" listValue="descripcion"
										id="idGrupo"/>
								</s:if>
								<s:else>
									<s:textfield name="descGrupo" id="idDescGrupo" onblur="this.className='input2';" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getDescripcionGrupo(datosSensiblesBean.grupo)}"
												onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"/>
									<s:hidden id="datosSensiblesBean.grupo" name="datosSensiblesBean.grupo"/>
								</s:else>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left">
					<label for=idEstadoDatoSensible>Estado: </label>
				</td>
				<td align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboEstadoDatoSensible()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
							headerValue="TODOS"
							name="datosSensiblesBean.estado"
							listKey="valorEnum" listValue="nombreEnum"
							id="idEstadoDatoSensible"
							/>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bConsultarDatosSensibles" id="idBotonConsultarDatosSensibles"
			value="Consultar" onClick="return consultaDatosSensibles();"onKeyPress="this.onClick"/>
		<input type="button" class="boton" name="bLimpiar" id="idBotonLimpiarConsulta" value="Limpiar"
			onkeypress="this.onClick" onclick="return limpiarConsultaDatosSensibles();"/>
	</div>
	<br/>
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;">
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
			<s:if test="%{listaConsultaDatosSensibles.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
										onchange="return cambiarElementosPorPaginaConsultaDatosSensibles();"
										/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
			</table>
		</s:if>
	</div>
	<display:table name="listaConsultaDatosSensibles" excludedParams="*"
					requestURI="navegarDatosSensibles.action"
					class="tablacoin"
					uid="tablaConsultaDatosSensibles"
					summary="Listado de Datos Sensibles"
					cellspacing="0" cellpadding="0"
					pagesize="${resultadosPorPagina}"
					partialList="false" size="5"
					>

		<display:column property="campo" sortable="true" headerClass="sortable centro"
			defaultorder="descending" style="width:10%" title="${myTipo}"/>

		<display:column property="fecha" title="Fecha de Introduccion"
			sortable="true" headerClass="sortable centro"
			defaultorder="descending"
			style="width:13%" />

		<display:column property="apellidosNombre" title="Apellidos Nombre"
			sortable="true" headerClass="sortable centro"
			defaultorder="descending"
			style="width:14%" />

		<display:column property="descGrupo" title="Grupo de Usuarios"
			sortable="true" headerClass="sortable centro"
			defaultorder="descending"
			style="width:11%" />

		<display:column property="tiempoRestauracion" title="Caducidad"
			sortable="true" headerClass="sortable centro"
			defaultorder="descending"
			style="width:11%" />

		<display:column property="estado" title="Estado"
			sortable="true" headerClass="sortable centro"
			defaultorder="descending"
			style="width:11%" />

		<s:if test="%{!flagDisabled}">
			<display:column property="tipoControl" title="Tipo Bastidor"
				sortable="true" headerClass="sortable centro"
				defaultorder="descending"
				style="width:11%" />
		</s:if>

		<display:column property="idGrupo" media="none"/>

		<!--El usuario Administrador no podra marcar los Checks, solo consultar	-->
		<display:column title="<input type='checkbox' name='checkAll' onClick='marcarDatosSensibles(this, document.formData.listaChecksDatosSensibles);' onKeyPress='this.onClick'/>"
			style="width:2.8%" >
			<table align="center">
			<tr>
				<td style="border: 0px;">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
						onclick="return abrirEvolucionDatosSensibles('${myTipo},<s:property value="#attr.tablaConsultaDatosSensibles.campo"/>,<s:property value="#attr.tablaConsultaDatosSensibles.idGrupo"/>');" title="Ver evolución"/>
				</td>
				<td style="border: 0px;">
					<input type="checkbox" name="listaChecksDatosSensibles" 
					id="<s:property value="#attr.tablaConsultaDatosSensibles.campo"/>,<s:property value="#attr.tablaConsultaDatosSensibles.idGrupo"/>"
					value='<s:property value="#attr.tablaConsultaDatosSensibles.campo"/>,<s:property value="#attr.tablaConsultaDatosSensibles.idGrupo"/>'
					onClick="return seleccionarDatosSensibles();"/>
				</td>
			</tr>
			</table>

		</display:column>

		<input type="hidden" name="resultadosPorPagina"/>
	</display:table>
	<s:if test="%{(listaConsultaDatosSensibles.getFullListSize()>0)}">
		<div class="acciones center">
			<input type="button" class="boton" name="bActivar"	id="idBotonActivar" value="Activar" onkeypress="this.onClick" onclick="return activarConsultaDatosSensibles();"/>
			<input type="button" class="boton" name="bEliminar"	id="idBotonEliminar" value="Eliminar" onkeypress="this.onClick" onclick="return eliminarConsultaDatosSensibles();"/>
		</div>
		<div id="bloqueLoadingDatosSensibles" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
	</s:if>
</s:form>
<div id="divEmergenteConsultaEvolucion" style="display: none; background: #f4f4f4;"></div>