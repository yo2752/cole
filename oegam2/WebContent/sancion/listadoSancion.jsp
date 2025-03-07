<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/sancionBotones.js" type="text/javascript"></script>

<script type="text/javascript">

var ventanaEstados;
//Función que se ejecuta cuando se pulsa 'Cambiar Estados' en la consulta de trámites de tráfico
function abrirVentanaSeleccionEstados() {
	if (numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
	ventanaEstados = window
			.open(
					'AbrirPopUpSancion.action',
					'popup',
					'width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

function invokeCambiarEstadoSancion(estado) {

	ventanaEstados.close();
	doPostWithChecked(
			"cambiarEstadoConsultaSancion.action?cambioEstado="
					+ estado, null);

}

</script>

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
				<td colspan="3">
					<table>
						<tr>
							<td align="left"><label for="diaMatrIni">Fecha
									Presentación desde: </label></td>

							<td><s:textfield
									name="beanCriterios.fechaFiltradoPresentacion.diaInicio"
									id="diaInicioPresentacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoPresentacion.mesInicio"
									id="mesInicioPresentacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoPresentacion.anioInicio"
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
				<td colspan="3">
					<table>
						<tr>
							<td align="left"><label for="diaMatrIni">Fecha
									Presentación Hasta: </label></td>

							<td><s:textfield
									name="beanCriterios.fechaFiltradoPresentacion.diaFin"
									id="diaFinPresentacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoPresentacion.mesFin"
									id="mesFinPresentacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoPresentacion.anioFin"
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
			<tr>
				<td align="left"><label for="diaMatrIni">Motivo: </label></td>
				<td><s:select
						list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getMotivos()"
						name="beanCriterios.motivo" id="motivo" headerKey="-1"
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" /></td>
						
				<s:if test="%{#isAdmin==true}">
					<td align="left"><label for="diaMatrIni">Estado
							Sanción: </label></td>
					<td><s:select
							list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getEstadoSancion()"
							name="beanCriterios.estadoSancion" id="estadoSancion" headerKey="-1"
							headerValue="Cualquier Estado" listKey="valorEnum"
							listValue="nombreEnum" /></td>
					<td align="left"><label for="diaMatrIni">Num
							Colegiado: </label></td>
					<td><s:textfield name="beanCriterios.numColegiado" id="numColegiado"
							align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="false" size="10"
							maxlength="25" /></td>
				</s:if>
			</tr>
			<tr>
				<td align="left"><label for="diaMatrIni">Nombre:</label></td>

				<td><s:textfield name="beanCriterios.nombre" id="nombre" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="15"
						maxlength="25" /></td>
						
				<td align="left"><label for="diaMatrIni">Apellidos/Razón Social:</label></td>

				<td><s:textfield name="beanCriterios.apellidos" id="apellidos" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="15"
						maxlength="25" /></td>
						
				<td align="left"><label for="diaMatrIni">NIF / CIF:</label></td>

				<td><s:textfield name="beanCriterios.dni" id="dni" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="15"
						maxlength="25" /></td>
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
										onchange="return cambiarElementosPorPaginaConsulta('navegarConsultaSancion.action', 'displayTagDiv', this.value);"/>
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
			requestURI="navegar${action}" class="tablacoin"
			uid="tablaConsultaSancion" summary="Listado de Sanciones"
			cellspacing="0" cellpadding="0">
			
			<display:column property="nombre" title="Nombre" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="nombre" />

			<display:column property="apellidos" title="Apellidos / Razón Social" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="apellidos" />
				
				<display:column property="dni" title="NIF / CIF" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="dni" />

			<display:column property="fechaPresentacion"
				title="Fecha Presentación" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%;" paramId="fechaPresentacion" />
				
			<display:column title="Motivo" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%;">
					<s:property
						value="%{@org.icogam.sanciones.Utiles.Motivo@convertir(#attr.tablaConsultaSancion.motivo).nombreEnum}" />
				</display:column>

			<s:if test="%{#isAdmin==true}">
			<display:column title="Estado" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%;">
					<s:property
						value="%{@org.icogam.sanciones.Utiles.EstadoSancion@convertir(#attr.tablaConsultaSancion.estadoSancion).nombreEnum}" />
				</display:column>
			</s:if>	
			<s:if test="%{#isAdmin==true}">
				<display:column property="numColegiado" title="Colegiado" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%;"
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