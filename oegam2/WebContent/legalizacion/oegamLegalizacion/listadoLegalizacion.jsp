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
<script src="js/legalizacion/legalizacionBotones.js" type="text/javascript"></script>

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
						'AbrirPopUpLegalizacionNuevo.action',
						'popup',
						'width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
	}

	function invokeCambiarEstadoLegalizacion(estado) {

		ventanaEstados.close();
		doPostWithChecked(
				"cambiarEstadoConsultaLegalizacionNuevo.action?cambioEstado="
						+ estado, null);

	}
</script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<s:set var="isMinisterio" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMinisterio()}"></s:set>
<s:set var="visible" value="none"></s:set>

<div class="contenido">
	<table class="subtitulo">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Citas Legalización Documentación</td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf"%>
<s:form method="post" id="formData" name="formData">

	<div class="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left"><label for="diaMatrIni">Tipo
						Documento: </label></td>

				<td><s:select
						list="@escrituras.utiles.UtilesVista@getTiposDocumentos()"
						name="legalizacionCitasFilterBean.tipoDocumento" id="tipoDocumento" headerKey=""
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" /></td>
				<s:if test="%{#isAdmin==true || #isMinisterio==true}">
					<td align="left"><label for="diaMatrIni">Fichero
							Adjunto: </label></td>

					<td><s:select
							list="@escrituras.utiles.UtilesVista@getTipoSiNo()"
							name="legalizacionCitasFilterBean.ficheroAdjunto" id="ficheroAdjunto" headerKey=""
							headerValue="Cualquier Tipo" listKey="valorEnum"
							listValue="nombreEnum" /></td>
				</s:if>
			</tr>
			<tr>
				<s:if test="%{#isMinisterio==true}">
					<td align="left"><label for="diaMatrIni">Solicitado: </label>
					</td>

					<td><s:select
							list="@escrituras.utiles.UtilesVista@getTipoSiNo()"
							name="legalizacionCitasFilterBean.solicitado" id="solicitado" headerKey=""
							headerValue="Cualquier Tipo" listKey="valorEnum"
							listValue="nombreEnum" /></td>
				</s:if>

				<td align="left"><label for="diaMatrIni">Referencia: </label></td>
				<td><s:textfield name="legalizacionCitasFilterBean.referencia" id="referencia"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="40"
						maxlength="40" /></td>
			</tr>
			<tr>
				<td align="left"><label for="diaMatrIni">Nombre\Apellido:
				</label></td>
				<td colspan="3"><s:textfield name="legalizacionCitasFilterBean.nombre" id="nombre" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" readonly="false" size="100"
						maxlength="150" /></td>
			</tr>
			<s:if test="%{#isAdmin==true || #isMinisterio==true}">
				<tr>
				
					<td align="left"><label for="diaMatrIni">Núm. Colegiado: </label></td>
					<td><s:textfield name="legalizacionCitasFilterBean.numColegiado" id="numColegiado"
							align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="false" size="15"
							maxlength="4" /></td>
					<td align="left"><label for="diaMatrIni">Contrato: </label></td>
					<td>
						<s:select id="idContrato"
							list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato"
							onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:220px"
							name="legalizacionCitasFilterBean.idContrato"></s:select>
					</td>
				</tr>
			</s:if>
			<tr>
			<s:if test="%{#isAdmin==true || #isMinisterio==false}">
					<td align="left"><label for="diaMatrIni">Estado
							Petición: </label></td>
					<td><s:select
							list="@escrituras.utiles.UtilesVista@getEstadoPeticion()"
							name="legalizacionCitasFilterBean.estadoPeticion" id="estadoPeticion" headerKey=""
							headerValue="Cualquier Estado" listKey="valorEnum"
							listValue="nombreEnum" /></td>
			</s:if>
			<s:if test="%{#isAdmin==true}">
				<td align="left"><label for="diaMatrIni">Anulado: </label>
				</td>

				<td><s:select
						list="@escrituras.utiles.UtilesVista@getTipoNoSi()"
						name="legalizacionCitasFilterBean.estado" id="estado" headerKey=""
						headerValue="Cualquier Tipo" listKey="valorEnum"
						listValue="nombreEnum" />
			</s:if>
			</tr>
			<tr>
				<td align="left"><label for="forClaseDocu">Clase
						Documento: </label></td>

				<td><s:select
						list="@escrituras.utiles.UtilesVista@getClaseDocumento()"
						name="legalizacionCitasFilterBean.claseDocumento" id="claseDocumento" headerKey=""
						headerValue="Cualquier Clase" listKey="valorEnum"
						listValue="nombreEnum" /></td>
				<td align="left"><label for="forPais">País: </label></td>
					<td><s:textfield name="legalizacionCitasFilterBean.pais" id="pais"
							align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" readonly="false" size="30"
							maxlength="30" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<table>
						<tr>
							<td align="left"><label for="diaMatrIni">Cita
									legalización desde: </label></td>

							<td><s:textfield
									name="legalizacionCitasFilterBean.fechaLegalizacion.diaInicio"
									id="diaInicioLegalizacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="legalizacionCitasFilterBean.fechaLegalizacion.mesInicio"
									id="mesInicioLegalizacion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="legalizacionCitasFilterBean.fechaLegalizacion.anioInicio"
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
				<td colspan="2">
					<table>
						<tr>
							<td align="left"><label for="diaMatrIni">Cita
									legalización hasta: </label></td>

							<td><s:textfield
									name="legalizacionCitasFilterBean.fechaLegalizacion.diaFin"
									id="diaFinLegalizacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td style="vertical-align: middle">/</td>
							<td><s:textfield
									name="legalizacionCitasFilterBean.fechaLegalizacion.mesFin"
									id="mesFinLegalizacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td style="vertical-align: middle">/</td>
							<td><s:textfield
									name="legalizacionCitasFilterBean.fechaLegalizacion.anioFin"
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
								<td align="left"><label for="diaMatrIni">Cita legalización: </label></td>

								<td><s:textfield name="legDto.fechaLegalizacion.dia"
										id="diaFinLegalizacionMasiva"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>
								<td style="vertical-align: middle">/</td>
								<td><s:textfield name="legDto.fechaLegalizacion.mes"
										id="mesFinLegalizacionMasiva"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>
								<td style="vertical-align: middle">/</td>
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
										onchange="return cambiarElementosPorPaginaConsulta('navegarConsultaLegalizacionNuevo.action', 'displayTagDiv', this.value);"/>
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
				requestURI="navegarConsultaLegalizacionNuevo.action" class="tablacoin"
				uid="tablaConsultaLegalizacion" summary="Listado de Peticiones"
				cellspacing="0" cellpadding="0" decorator="${decorator}">
				
				<display:column title="Tipo doc" property="tipoDocumento" sortable="true" headerClass="sortable" defaultorder="descending" />
				<display:column title="Nombre" property="nombre" sortable="true" headerClass="sortable" defaultorder="descending" />
				<display:column title="Referencia" property="referencia" sortable="true" headerClass="sortable" defaultorder="descending" />
				<display:column title="Cita Legaliz" property="fechaLegalizacion" sortable="true" headerClass="sortable" defaultorder="descending" format="{0,date,dd/MM/yyyy}" />
				<s:if test="%{#isAdmin==true || #isMinisterio==true}">
					<display:column title="Solic" property="solicitado" sortable="true" headerClass="sortable" defaultorder="descending"/>
				</s:if>
				<display:column title="Fich Adj" property="ficheroAdjunto"/>
				<s:if test="%{#isAdmin==true || #isMinisterio==true}">
					<display:column property="numColegiado" title="Nº coleg" sortable="true" headerClass="sortable" defaultorder="descending" />
				</s:if>
				<s:if test="%{#isAdmin==true || #isMinisterio==false}">
					<display:column title="Estado Petición" sortable="true" property="estado" headerClass="sortable" defaultorder="descending" />
				</s:if>	
				<s:if test="%{#isAdmin==true || #isMinisterio==true}">
					<display:column property="nombreContrato" title="Contrato" sortable="true" headerClass="sortable" sortProperty="orden" defaultorder="descending" />
				</s:if>	
				<s:if test="%{#isAdmin==true}">
					<display:column property="orden" title="Orden" sortable="true" headerClass="sortable" sortProperty="orden" defaultorder="descending" />
				</s:if>
				<display:column property="claseDocumento" title="Clase" sortable="true" headerClass="sortable" defaultorder="descending" />
				<display:column property="pais" title="País" sortable="true" headerClass="sortable" defaultorder="descending" />
				<s:if test="%{#isAdmin==true}">
					<display:column title="Anulado" property="anulado"/>
				</s:if>
				<display:column
					title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksPeticiones);' onKeyPress='this.onClick'/>"
						style="width:1%" >
					<table>
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecksPeticiones" value='<s:property value="#attr.tablaConsultaLegalizacion.idPeticion"/>' />
								<!-- <input type="hidden" name="listaEstadoConsultaTramite" id="estado_<s:property value="#attr.tablaConsultaLegalizacion.idSolicitud"/>" value='<s:property value="#attr.tablaConsultaTramite.estado"/>' /> -->
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
						<s:if test="%{#isMinisterio==false || #isAdmin==true}">
							<td><input type="button" class="boton" name="bBajaPeticiones"
								id="bBajaPeticiones" value="Borrar petición"
								onkeypress="this.onClick" onclick="return borraPeticion(this);" />
							</td>
						</s:if>
		
						<s:if test="%{#isMinisterio==true || #isAdmin==true}">
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
			</div>
		</s:if>
	</div>
</s:form>