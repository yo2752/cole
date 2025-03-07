<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/empresaTelematica.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta Empresas Telemáticas</span></td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
<s:hidden name="valorEstadoCambio" id="valorEstadoCambio" />
<s:hidden name="codSeleccionados" id="codSeleccionados" value=""/>

	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="right"><label for="tipo">Empresa:</label></td>
				<td>
					<s:textfield name="bean.nombreEmpresa" id="empresa"
					align="left" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="25" maxlength="50" />
				</td>
				<td align="right"><label for="dato">CIF: </label></td>
				<td>
					<s:textfield name="bean.cifEmpresa" id="cifEmpresa"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="35" />
				</td>
				<td align="right"><label for="dato">Estado: </label></td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getEstados()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey="-1"
						headerValue="TODOS" name="bean.estado" listKey="valorEnum" listValue="nombreEnum"
						id="idEstado"/>
				</td>
			</tr>
			<tr>
				<td align="right"><label for="activo">Código Postal:</label></td>
				<td>
					<s:textfield name="bean.codigoPostal" id="codigoPostal"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="15" 
						onkeypress="return validarNumerosEnteros(event)"/>
				</td>
				<td align="right"><label for="activo">Provincia:</label></td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getProvincias()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Provincia"
							name="bean.provincia" listKey="idProvincia" listValue="nombre" id="idProvinciaEmpresaTelematica"
							onchange="cargarListaMunicipiosCAYC('idProvinciaEmpresaTelematica', 'idMunicipioEmpresaTelematica');" />
				</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esAdmin()}">
				<td align="right" nowrap="nowrap">
					<label for="labelColegiado">Contrato:</label>
				</td>
				<td>
					<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo"
						headerKey="" listValue="descripcion" cssStyle="width:220px" name="bean.idContrato">
					</s:select>
				</td>
			</s:if>
			</tr>
			<tr>
				<td align="right"><label for="activo">Municipio:</label></td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getMunicipios(bean.provincia)" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Municipio"
							name="bean.municipio" listKey="idMunicipio" listValue="nombre" id="idMunicipioEmpresaTelematica"/>
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
								<label for="labelFechaAltaDesde">Desde:</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaAlta.diaInicio" id="diaAltaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaAlta.mesInicio" id="mesAltaInicio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaAlta.anioInicio" id="anioAltaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaInicio, document.formData.mesAltaInicio, document.formData.diaAltaInicio);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>

				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaHasta">Hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaAlta.diaFin" id="diaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaAlta.mesFin" id="mesAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaAlta.anioFin" id="anioAltaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha">Fecha de Baja:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaDesde">Desde:</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaBaja.diaInicio" id="diaBajaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaBaja.mesInicio" id="mesBajaInicio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaBaja.anioInicio" id="anioBajaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBajaInicio, document.formData.mesBajaInicio, document.formData.diaBajaInicio);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>

				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaHasta">Hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaBaja.diaFin" id="diaBajaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaBaja.mesFin" id="mesBajaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="bean.fechaBaja.anioFin" id="anioBajaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBajaFin, document.formData.mesBajaFin, document.formData.diaBajaFin);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Consultar" onkeypress="this.onClick" onclick="consultaEmpresa();" />
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar"	id="bLimpiar" value="Limpiar" onclick="return limpiarFormularioEmpresa();" />
				</td>
			</tr>
		</table>
	</div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultados de la
					Búsqueda:</td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresYMensajes.jspf"%>
	<s:if
		test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right"><label
								for="idResultadosPorPaginaConsultaEmpresa">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right"><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPaginaConsultaEmpresa" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsultaEmpresa();" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagDivConsultaEmpresa" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>

		<display:table name="lista" excludedParams="*"
			requestURI="navegarConsultaEmpresaTelematica.action" class="tablacoin"
			uid="tablaConsultas" summary="Listado de Empresas" cellspacing="0"
			cellpadding="0" sort="external">

			<display:column property="nombreContrato" title="Contrato"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" sortProperty="numColegiado"/>

			<display:column title="Estado"
				sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" sortProperty="estado">
				<s:property
						value="%{@org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.EstadoEmpresaTelematica@convertirTexto(#attr.tablaConsultas.estado)}" />
			</display:column>

			<display:column property="municipio" title="Municipio"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>

			<display:column property="empresa" title="Razón Social"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>

			<display:column property="cifEmpresa" title="CIF"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>

			<display:column property="codigoPostal" title="Código Postal"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%"/>

			<display:column property="fechaAlta" title="Fecha Alta"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy}" />

			<display:column property="fechaBaja" title="Fecha Baja"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy}" />
			<display:column	title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaEmpresa);'
				onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksConsultaEmpresa"
							value='<s:property value="#attr.tablaConsultas.id"/>' />
						</td>
						<td style="border: 0px;">
							<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
								onclick="abrirDetalle('${tablaConsultas.id}');" title="Ver Detalle"/>
						</td>
					</tr>
				</table>
			</display:column>
		</display:table>

	</div>
	<div class="acciones center">
		<s:if test="%{lista.getFullListSize()>0 }">
			<input type="button" value="Cambiar Estado" id="bCambiarEstado" class="boton" onkeypress="this.onClick" onclick="abrirVentanaSeleccionEstados();" />
		</s:if>
	</div>
</s:form>
<div id="divEmergenteConsultaEmpresaTelematica" style="display: none; background: #f4f4f4;"></div>

<script type="text/javascript">
<!--
	//-->
	var ventanaEstados;
	function abrirVentanaSeleccionEstados() {
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}
		ventanaEstados = window
				.open(
						'abrirPopUpConsultaEmpresaTelematica.action',
						'popup',
						'width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
	}

	function numChecked() {
		var checks = document.getElementsByName("listaChecksConsultaEmpresa");
		var numChecked = 0;
		for(var i=0; i<checks.length; i++) {
			if(checks[i].checked) numChecked++;
		}
		return numChecked;
	}

	function invokeCambiarEstadoEmpresa(estado) {
		ventanaEstados.close();
		var checks = document.getElementsByName("listaChecksConsultaEmpresa");
		var codigos = "";
		var i = 0;
		while(checks[i] != null) {
			if(checks[i].checked) {
				if(codigos==""){
					codigos += checks[i].value;
				}else{
					codigos += "-"+ checks[i].value;
				}
			}
			i++;
		}
		$('#valorEstadoCambio').attr("value","");
		$('#valorEstadoCambio').attr("value",estado);

		$('#codSeleccionados').attr("value","");
		$('#codSeleccionados').attr("value",codigos);

		$('#formData').attr("action","cambiarEstadosConsultaEmpresaTelematica.action");
		$('#formData').submit();
	}

	function cambiarElementosPorPaginaConsultaEmpresa(){
		var $dest = $("#displayTagDivConsultaEmpresa");
		$.ajax({
			url:"navegarConsultaEmpresaTelematica.action",
			data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaEmpresa").val(),
			type:'POST',
			success: function(data){
				filteredResponse =  $(data).find($dest.selector);
				if(filteredResponse.size() == 1){
					$dest.html(filteredResponse);
				}
			},
			error: function(xhr, status) {
				alert('Ha sucedido un error a la hora de cargar las consulta de tramites.');
			}
		});
	}
</script>