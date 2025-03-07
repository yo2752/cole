<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.displaytag-ajax-oegam.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/sanciones/sancionBotones.js" type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<s:set var="visible" value="none"></s:set>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo"> Consulta Sanción</span></td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf"%>
<s:form method="post" id="formData" name="formData">

	<div class="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaMatrIni">Motivo: </label>
				</td>
				<td><s:select
						list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getMotivos()"
						name="sancionesFilterBean.motivo" id="motivo" headerKey=""
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" /></td>
								<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaMatrIni">NIF / CIF:</label>
				</td>

				<td><s:textfield name="sancionesFilterBean.dni" id="dni" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						onkeypress="return validarMatricula(event)"
     					onChange="return validarMatricula_alPegar(event)" 
     					onmousemove="return validarMatricula_alPegar(event)" 
						readonly="false" size="10"
						maxlength="10" />
				</td>	
			</tr>
			<s:if test="%{#isAdmin==true}">
				<tr>	
				
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="diaMatrIni">Estado Sanción: </label>
					</td>
					<td><s:select
							list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getEstadoSancion()"
							name="sancionesFilterBean.estadoSancion" id="estadoSancion" headerKey=""
							headerValue="Cualquier Estado" listKey="valorEnum"
							listValue="nombreEnum" /></td>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="diaMatrIni">Núm. Colegiado: </label>
					</td>
					<td><s:textfield name="sancionesFilterBean.numColegiado" id="numColegiado"
							align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="false" size="4"
							maxlength="4" /></td>
				
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaMatrIni">Nombre:</label>
				</td>

				<td><s:textfield name="sancionesFilterBean.nombre" id="nombre" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="25"
						maxlength="25" /></td>
						
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaMatrIni">Apellidos/Razón Social:</label>
				</td>

				<td><s:textfield name="sancionesFilterBean.apellidos" id="apellidos" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="40"
						maxlength="50" />
				</td>
						
			</tr>
			<tr>
				<td colspan="2">
					<table>
						<tr>
							<td align="left" nowrap="nowrap" style="vertical-align: middle;">
								<label for="diaMatrIni">Fecha Presentación desde: </label>
							</td>

							<td><s:textfield
									name="sancionesFilterBean.fechaPresentacion.diaInicio"
									id="diaInicioPresentacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td style="vertical-align: middle;">/</td>
							<td><s:textfield
									name="sancionesFilterBean.fechaPresentacion.mesInicio"
									id="mesInicioPresentacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td style="vertical-align: middle;">/</td>
							<td><s:textfield
									name="sancionesFilterBean.fechaPresentacion.anioInicio"
									id="anioInicioPresentacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioPresentacion, document.formData.mesInicioPresentacion, document.formData.diaInicioPresentacion);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
				<td colspan="2" align="left">
					<table>
						<tr>
							<td align="left" nowrap="nowrap" style="vertical-align: middle;">
								<label for="diaMatrIni">Fecha Presentación Hasta: </label>
							</td>

							<td><s:textfield
									name="sancionesFilterBean.fechaPresentacion.diaFin"
									id="diaFinPresentacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td style="vertical-align: middle;">/</td>
							<td><s:textfield
									name="sancionesFilterBean.fechaPresentacion.mesFin"
									id="mesFinPresentacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td style="vertical-align: middle;">/</td>
							<td><s:textfield
									name="sancionesFilterBean.fechaPresentacion.anioFin"
									id="anioFinPresentacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinPresentacion, document.formData.mesFinPresentacion, document.formData.diaFinPresentacion);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div class="centrado espArriba">
			<table align="center">
				<tr>
					<td>
						<input type="button" class="boton" name="bListarSanciones"
							id="bListarSanciones" value="Buscar" onkeypress="this.onClick"
							onclick="return obtenerListaSanciones();" />
					</td>
					<td>
						<input type="button" value="Limpiar campos" class="botonMasGrande" onclick="return limpiarCamposConsultaSancion();"/>
					</td>
				</tr>
			</table>
		</div>

	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
										title="Resultados por página"
										onchange="return cambiarElementosPorPaginaConsultaSancion();"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*"
			requestURI="navegarConsultaSancionNuevo" class="tablacoin"
			uid="tablaConsultaSancion" summary="Listado de Sanciones"
			cellspacing="0" cellpadding="0">
			
			<display:column property="nombre" title="Nombre" sortable="true"
				headerClass="sortable" defaultorder="descending" paramId="nombre" />

			<display:column property="apellidos" title="Apellidos/Razón Social" sortable="true"
				headerClass="sortable" defaultorder="descending" paramId="apellidos" />
				
				<display:column property="dni" title="NIF / CIF" sortable="true"
				headerClass="sortable" defaultorder="descending" paramId="dni" />

			<display:column property="fechaPresentacion"
				title="Fecha Pres." sortable="true" headerClass="sortable"
				defaultorder="descending" paramId="fechaPresentacion" />
				
			<display:column title="Motivo" sortable="true"
					headerClass="sortable" defaultorder="descending" >
					<s:property
						value="%{@org.icogam.sanciones.Utiles.Motivo@convertir(#attr.tablaConsultaSancion.motivo).nombreEnum}" />
				</display:column>

			<s:if test="%{#isAdmin==true}">
			<display:column title="Estado" sortable="true"
					headerClass="sortable" defaultorder="descending" >
					<s:property
						value="%{@org.icogam.sanciones.Utiles.EstadoSancion@convertir(#attr.tablaConsultaSancion.estadoSancion).nombreEnum}" />
				</display:column>
			</s:if>	
			<s:if test="%{#isAdmin==true}">
				<display:column property="numColegiado" title="Colegiado" sortable="true"
					headerClass="sortable" defaultorder="descending" 
					paramId="numColegiado" />
			</s:if>	
			<display:column
				title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksSanciones);' 
			onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksSanciones" id="idSancion"
							value='<s:property value="#attr.tablaConsultaSancion.idSancion"/>' />
						</td>
					</tr>
				</table>
			</display:column>

		</display:table>
	</div>
	
	<s:if test="%{lista.getFullListSize()>0}">
		<div>
			<br />
			<table align="center">
				<tr>
						<td><input type="button" class="boton" name="bBajaSanciones"
							id="bBajaSanciones" value="Borrar Sanción"
							onkeypress="this.onClick" onclick="return borraSancion(this);" />
						</td>
					<s:if test="%{#isAdmin==true}">
						<td><input type="button" class="boton"
							name="cambiarEstadoSanciones" id="cambiarEstadoSanciones"
							value="Cambiar Estado" onkeypress="this.onClick"
							onclick="javascript:abrirVentanaSeleccionEstados();" /></td>
					</s:if>
					<td>
						<input type="button" class="boton" name="bModificarSanciones"
							id="bModificarSanciones" value="Modificar"
							onkeypress="this.onClick" onclick="return modificarSancion(this);" />
							</td>
				</tr>
			</table>
			<table align="center">
					<tr>
						<td><input type="button" class="boton" name="bListadoSanciones"
							id="bListadoSanciones" value="Imprimir Listados"
							onkeypress="this.onClick" onclick="return listadoSancion(this);" />
						</td>
	
					</tr>
			</table>
		</div>
	</s:if>
	</div>
</s:form>