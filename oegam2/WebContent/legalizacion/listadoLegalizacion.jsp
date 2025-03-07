<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/legalizacionBotones.js" type="text/javascript"></script>

<script type="text/javascript">
<!--
	//-->
	var ventanaEstados;
	//Función que se ejecuta cuando se pulsa 'Cambiar Estados' en la consulta de trámites de tráfico
	function abrirVentanaSeleccionEstados() {
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}
		ventanaEstados = window
				.open(
						'AbrirPopUpLegalizacion.action',
						'popup',
						'width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
	}

	function invokeCambiarEstadoLegalizacion(estado) {

		ventanaEstados.close();
		doPostWithChecked(
				"cambiarEstadoConsultaLegalizacion.action?cambioEstado="
						+ estado, null);

	}
</script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<s:set var="isMinisterio" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMinisterio()}"></s:set>
<s:set var="visible" value="none"></s:set>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo"> Citas
					Legalización Documentación </span></td>
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
							<td align="left"><label for="diaMatrIni">Cita
									legalización desde: </label></td>

							<td><s:textfield
									name="beanCriterios.fechaFiltradoLegalizacion.diaInicio"
									id="diaInicioLegalizacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoLegalizacion.mesInicio"
									id="mesInicioLegalizacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoLegalizacion.anioInicio"
									id="anioInicioLegalizacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioLegalizacion, document.formData.mesInicioLegalizacion, document.formData.diaInicioLegalizacion);return false;"
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
							<td align="left"><label for="diaMatrIni">Cita
									legalización Hasta: </label></td>

							<td><s:textfield
									name="beanCriterios.fechaFiltradoLegalizacion.diaFin"
									id="diaFinLegalizacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoLegalizacion.mesFin"
									id="mesFinLegalizacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaFiltradoLegalizacion.anioFin"
									id="anioFinLegalizacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinLegalizacion, document.formData.mesFinLegalizacion, document.formData.diaFinLegalizacion);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>



			<tr>
				<td align="left"><label for="diaMatrIni">Tipo
						Documento: </label></td>

				<td><s:select
						list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getTiposDocumentos()"
						name="beanCriterios.tipoDocumento" id="tipoDocumento" headerKey=""
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" /></td>
				<s:if test="%{#isAdmin==true || #isMinisterio==true}">
					<td align="left"><label for="diaMatrIni">Fichero
							Adjunto: </label></td>

					<td><s:select
							list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getTipoSiNo()"
							name="beanCriterios.ficheroAdjunto" id="ficheroAdjunto" headerKey="-1"
							headerValue="Cualquier Tipo" listKey="valorEnum"
							listValue="nombreEnum" /></td>
				</s:if>
			</tr>
			<tr>
				<s:if test="%{#isMinisterio==true}">
					<td align="left"><label for="diaMatrIni">Solicitado: </label>
					</td>

					<td><s:select
							list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getTipoSiNo()"
							name="beanCriterios.solicitado" id="solicitado" headerKey="-1"
							headerValue="Cualquier Tipo" listKey="valorEnum"
							listValue="nombreEnum" /></td>
				</s:if>
				<td align="left"><label for="diaMatrIni">Nombre\Apellido:
				</label></td>

				<td><s:textfield name="beanCriterios.nombre" id="nombre" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="50"
						maxlength="60" /></td>
			</tr>
			<tr>
				<s:if test="%{#isAdmin==true || #isMinisterio==true}">
					<td align="left"><label for="diaMatrIni">Num
							Colegiado: </label></td>
					<td><s:textfield name="beanCriterios.numColegiado" id="numColegiado"
							align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="false" size="15"
							maxlength="4" /></td>
				</s:if>
				<td align="left"><label for="diaMatrIni">Referencia: </label></td>
				<td><s:textfield name="beanCriterios.referencia" id="referencia"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="50"
						maxlength="38" /></td>
			</tr>
			<tr>
			<s:if test="%{#isAdmin==true || #isMinisterio==false}">
					<td align="left"><label for="diaMatrIni">Estado
							Petición: </label></td>
					<td><s:select
							list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getEstadoPeticion()"
							name="beanCriterios.estadoPeticion" id="estadoPeticion" headerKey="-1"
							headerValue="Cualquier Estado" listKey="valorEnum"
							listValue="nombreEnum" /></td>
			</s:if>
			<s:if test="%{#isAdmin==true}">
					<td align="left"><label for="diaMatrIni">Anulado: </label>
					</td>

					<td><s:select
							list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getTipoSiNo()"
							name="beanCriterios.estado" id="estado" headerKey="-1"
							headerValue="Cualquier Tipo" listKey="valorEnum"
							listValue="nombreEnum" />
				</s:if>
			</tr>	
			<tr>
				<td align="left"><label for="forClaseDocumento">Clase
						Documento: </label></td>

				<td><s:select
						list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getClaseDocumento()"
						name="beanCriterios.claseDocumento" id="claseDocumento" headerKey=""
						headerValue="Cualquier Clase" listKey="valorEnum"
						listValue="nombreEnum" /></td>
						
				<td align="left"><label for="forPais">Pais: </label></td>
				<td><s:textfield name="beanCriterios.pais" id="pais"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="30"
						maxlength="30" /></td>
			</tr>	
		</table>
		</div>
		<div class="centrado espArriba">
			<table align="center">
				<tr>
					<td>
						<input type="button" class="boton" name="bListarPeticiones"
							id="bListarPeticiones" value="Buscar" onkeypress="this.onClick"
							onclick="return obtenerListaPeticiones();" />
					</td>
					<td>
						<input type="button" value="Limpiar campos" class="botonMasGrande" onclick="return limpiarCamposConsultaLegalizacion();"/>
					</td>
				</tr>
			</table>
		</div>
		<s:if test="%{lista.getFullListSize()>0}">
			<div>
				<s:if test="%{#isAdmin==true || #isMinisterio==false}">
					<input type="button" class="boton" name="bSolicitarMasiva"
						id="bSolicitarMasiva" value="Modificar Cita"
						onkeypress="this.onClick" onclick="mostrarDiv();" />
				</s:if>
			</div>
		</s:if>

		<div id="legalizacionMasiva" style="display: none;">
			<table class="tablaformbasica" cellspacing="3" cellpadding="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<table>
							<tr>
								<td align="left"><label for="diaMatrIni">Cita
										legalización: </label></td>

								<td><s:textfield name="legDto.fechaLegalizacion.dia"
										id="diaFinLegalizacionMasiva"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>
								<td>/</td>
								<td><s:textfield name="legDto.fechaLegalizacion.mes"
										id="mesFinLegalizacionMasiva"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>
								<td>/</td>
								<td><s:textfield name="legDto.fechaLegalizacion.anio"
										id="anioFinLegalizacionMasiva"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="5" maxlength="4" /></td>
								<td><a href="javascript:;"
									onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinLegalizacionMasiva, document.formData.mesFinLegalizacionMasiva, document.formData.diaFinLegalizacionMasiva);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"
										align="left" src="img/ico_calendario.gif" width="15"
										height="16" border="0" alt="Calendario" />
								</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td nowrap="nowrap" width="30%" align="left"
						style="vertical-align: middle"><input type="button"
						value="Aceptar" class="boton"
						onclick="solicitarLegalizacionesMasivas()" /> &nbsp; <input
						type="button" value="Cancelar" class="boton"
						onclick="ocultarDiv()" /></td>
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
										onchange="return cambiarElementosPorPaginaConsulta('navegarConsultaLegalizacion.action', 'displayTagDiv', this.value);"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
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
		<display:table name="lista" excludedParams="*" sort="list"
			requestURI="navegar${action}" class="tablacoin"
			uid="tablaConsultaLegalizacion" summary="Listado de Peticiones"
			cellspacing="0" cellpadding="0" decorator="${decorator}">
			
			<display:column title="Tipo doc" property="tipoDocumento"/>
			<display:column title="Nombre" property="nombre" sortable="true" headerClass="sortable" defaultorder="descending" />
			<display:column title="Referencia" property="referencia" sortable="true" headerClass="sortable" defaultorder="descending" />
			<display:column title="Cita Legalizacion" property="fechaLegalizacion" sortable="true" headerClass="sortable" defaultorder="descending" format="{0,date,dd/MM/yyyy}" />
			<s:if test="%{#isAdmin==true || #isMinisterio==true}">
				<display:column title="Solicitado" property="solicitado" sortable="true" headerClass="sortable" defaultorder="descending"/>
			</s:if>
			<display:column title="Documento" property="ficheroAdjunto"/>
			<s:if test="%{#isAdmin==true || #isMinisterio==true}">
				<display:column property="numColegiado" title="Num colegiado" sortable="true" headerClass="sortable" defaultorder="descending" />
			</s:if>
			<s:if test="%{#isAdmin==true || #isMinisterio==false}">
				<display:column title="Estado" sortable="true" property="estado" headerClass="sortable" defaultorder="descending" />
			</s:if>	
			<s:if test="%{#isAdmin==true}">
				<display:column property="orden" title="Orden" sortable="true" headerClass="sortable" sortProperty="orden" defaultorder="descending" />
			</s:if>
			<display:column title="Clase" property="claseDocumento"/>
			<display:column title="Pais" property="pais"/>
			<display:column
				title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksPeticiones);' onKeyPress='this.onClick'/>"
					style="width:1%"  property="checkbox" />

		</display:table>
	</div>

		<div>
			<s:if test="%{lista.getFullListSize()>0}">
				<br />
				<table align="center">
					<tr>
						<s:if test="%{#isMinisterio==false || #isAdmin==true}">
							<td><input type="button" class="boton" name="bBajaPeticiones"
								id="bBajaPeticiones" value="Borrar petición"
								onkeypress="this.onClick" onclick="return borraPeticion(this);" />
							</td>
						</s:if>
		
						<s:if test="%{#isMinisterio==true && #isAdmin==false}">
							<td><input type="button" class="botonMasGrande"
								name="bSolicitudInformación" id="bSolicitudInformacion"
								value="Solicitar documentación" onkeypress="this.onClick"
								onclick="return solicitarDocumentacion(this);" /></td>
						</s:if>
						<s:if test="%{#isAdmin==true}">
							<td><input type="button" class="boton"
								name="cambiarEstadoPeticiones" id="cambiarEstadoPeticiones"
								value="Cambiar Estado" onkeypress="this.onClick"
								onclick="javascript:abrirVentanaSeleccionEstados();" /></td>
						</s:if>
					</tr>
				</table>
			</s:if>
			<table align="center">
				<s:if test="%{#isAdmin==true || #isMinisterio==false}">
					<tr>
						<td><input type="button" class="boton"
								name="blistadoLegal" id="idBListadoLegal"
								value="Listado" onkeypress="this.onClick"
								onclick="javascript:obtenerListado();" /></td>
						<td align="left"><label for="diaMatrIni">Fecha de
								legalización: </label></td>
	
						<td><s:textfield
								name="fechaListado.dia"
								id="diaFechaListado" align="left"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="false" size="2"
								maxlength="2" /></td>
						<td>/</td>
						<td><s:textfield
								name="fechaListado.mes"
								id="mesFechaListado" align="left"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="false" size="2"
								maxlength="2" /></td>
						<td>/</td>
						<td><s:textfield
								name="fechaListado.anio"
								id="anioFechaListado" align="left"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" readonly="false" size="5"
								maxlength="4" /></td>
						<td><a href="javascript:;"
							onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaListado, document.formData.mesFechaListado, document.formData.diaFechaListado);return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="left" src="img/ico_calendario.gif" width="15" height="16"
								border="0" alt="Calendario" />
						</a></td>
						<s:if test="%{#isAdmin==true}">
							<td>
								<label>Num Colegiado</label>
							</td>
							<td>
								<s:textfield  id="numColegiado" name ="numColegiado" maxlength="4" />
							</td>
						</s:if>
					</tr>
				</s:if>
			</table>
		</div>
	</div>
</s:form>