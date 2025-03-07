<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/actualizacionMF/actualizacionMF.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta Actualizacion Marcas-Fabricantes</span></td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="right"><label for="activo">Estado:</label></td>
				<td>
					<s:select name="filterBean.estado" id="estado"
						list="@org.gestoresmadrid.core.actualizacionMF.model.enumerados.ActualizacionMFEnum@values()"
						listKey="valorEnum" listValue="nombreEnum"
						headerKey="" headerValue="Todos" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<tr>
				<td align="right"><label>Fecha de alta:</label></td>
				<td colspan="3">
					<table width="100%">
						<tr>
							<td align="right">Desde:</td>
							<td>
								<s:textfield name="filterBean.fechaAlta.diaInicio"
									id="diaInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="filterBean.fechaAlta.mesInicio"
									id="mesInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="filterBean.fechaAlta.anioInicio"
									id="anioInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" />
							</td>
							<td>
								<a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;"
								title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
							<td align="right">Hasta:</td>
							<td><s:textfield name="filterBean.fechaAlta.diaFin"
									id="diaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="filterBean.fechaAlta.mesFin"
									id="mesFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="filterBean.fechaAlta.anioFin"
									id="anioFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right"><label>Fecha Fin Actualización:</label></td>
				<td colspan="3">
					<table width="100%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="filterBean.fechaFin.diaInicio"
									id="diaInicioSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="filterBean.fechaFin.mesInicio"
									id="mesInicioSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="filterBean.fechaFin.anioInicio"
									id="anioInicioSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioSancion, document.formData.mesInicioSancion, document.formData.diaInicioSancion);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="filterBean.fechaFin.diaFin"
									id="diaFinSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="filterBean.fechaFin.mesFin"
									id="mesFinSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="filterBean.fechaFin.anioFin"
									id="anioFinSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinSancion, document.formData.mesFinSancion, document.formData.diaFinSancion);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Consultar" onkeypress="this.onClick"
					onclick="consultaActualizacionMF();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					id="bLimpiar" value="Limpiar"
					onclick="" /></td>
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
								for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right"><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaMF.action', 'displayTagDiv', this.value);" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>

		<display:table name="lista" excludedParams="*"
			requestURI="navegarConsultaMF.action" class="tablacoin"
			uid="tablaConsultas" summary="Listado de Vehículos" cellspacing="0"
			cellpadding="0" sort="external">

			<display:column property="fechaAlta" title="Fecha Alta"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy HH.mm.ss}" />
				
			<display:column property="fechaFin"
				title="Fecha Fin" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%"
				format="{0,date,dd/MM/yyyy HH.mm.ss}" />

			<display:column property="resultado"
				title="Resultado" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%"/>

			<display:column property="ficheroSubido"
				title="Fichero Actualizacion" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%"/>

			<display:column property="ficheroResultado"
				title="Fichero Resultado" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%"/>

			<display:column title="Estado"
							sortable="true"
							headerClass="sortable"
							defaultorder="descending"
							style="width:4%">
				<s:property 
						value="%{@org.gestoresmadrid.core.actualizacionMF.model.enumerados.ActualizacionMFEnum@convertirTexto(#attr.tablaConsultas.estado)}" />
			</display:column>

			<display:column
				title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksActualizacionMF);'
				onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksActualizacionMF"
							value='<s:property value="#attr.tablaConsultas.idActualizacion"/>' />
						</td>
					</tr>
				</table>
			</display:column>

		</display:table>

	</div>
	<div class="acciones center">
		<s:if test="%{lista.getFullListSize()>0 }">
			<input type="button" value="Comenzar Actualización" class="boton" onkeypress="this.onClick" onclick="realizarActualizacion();" />
			<input type="button" value="Descargar Ficheros" class="boton" onkeypress="this.onClick" onclick="descargarFicheros();" />
		</s:if>
	</div>
</s:form>