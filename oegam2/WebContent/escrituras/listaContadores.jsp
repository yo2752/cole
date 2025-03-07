<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/contadoresUso.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script type="text/javascript">
function cambiarElementosPorPagina(){

	document.location.href='navegarHistorico.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Informe de Contadores de Uso</span></td>
		</tr>
	</table>
</div>

<s:form id="formData" name="formData" action="buscarContadoresUso.action">
	<table width="85%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<fieldset>
					<table width="100%" align="center" colspan="5" cellpadding="5">
						<tr>
							<td align="left"><label for="razonSocial">Raz&oacute;n
									social</label></td>
							<td align="left"><s:textfield name="razonSocial"
									id="razon_Social" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="35" maxlength="30" />
							</td>
							<td align="left"><label for="numColegiado">Nº de
									colegiado</label></td>
							<td align="left"><s:textfield name="numColegiado"
									id="num_Colegiado" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" />
							</td>
						</tr>
						<tr>
							<td align="left"><label for="idAplicacion">Aplicación</label>
							</td>
							<td><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboAplicacion()"
									id="idAplicacion" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" name="idAplicacion"
									listValue="nombreEnum" listKey="valorEnum"
									title="tipos de Aplicacion" headerKey="-1" headerValue="TODOS" />
							</td>


						</tr>
						<tr>
							<td colspan="6" align="left">
								<table>
									<tr>
										<td width="22%" align="left"><label for="diaAltaIni">Fecha
												alta:</label></td>
										<td width="10%" align="left"><label for="diaAltaIni">desde:</label></td>
										<td><s:textfield name="fechaAlta.diaInicio"
												id="diaAltaIni" onblur="this.className='input2';"
												onfocus="this.className='inputfocus';" size="2"
												maxlength="2" /></td>
										<td>/</td>
										<td><s:textfield name="fechaAlta.mesInicio"
												id="mesAltaIni" onblur="this.className='input2';"
												onfocus="this.className='inputfocus';" size="2"
												maxlength="2" /></td>
										<td>/</td>
										<td><s:textfield name="fechaAlta.anioInicio"
												id="anioAltaIni" onblur="this.className='input2';"
												onfocus="this.className='inputfocus';" size="5"
												maxlength="4" /></td>
										<td><a href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;"
											HIDEFOCUS title="Seleccionar fecha"> <img
												class="PopcalTrigger" align="absmiddle"
												src="img/ico_calendario.gif" width="15" height="16"
												border="0" alt="Calendario" />
										</a></td>
										<td width="2%"></td>
										<td width="10%" align="left"><label for="diaAltaFin">hasta:</label></td>
										<td><s:textfield name="fechaAlta.diaFin" id="diaAltaFin"
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';" size="2"
												maxlength="2" /></td>
										<td>/</td>
										<td><s:textfield name="fechaAlta.mesFin" id="mesAltaFin"
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';" size="2"
												maxlength="2" /></td>
										<td>/</td>
										<td><s:textfield name="fechaAlta.anioFin"
												id="anioAltaFin" onblur="this.className='input2';"
												onfocus="this.className='inputfocus';" size="5"
												maxlength="4" /></td>
										<td><a href="javascript:;"
											onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;"
											HIDEFOCUS title="Seleccionar fecha"> <img
												class="PopcalTrigger" align="absmiddle"
												src="img/ico_calendario.gif" width="15" height="16"
												border="0" alt="Calendario" />
										</a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</td>
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
     <div id="fieldError">
<s:actionmessage/>									
</div>	
	
<div id="resultado">
	<table class="subtitulo" cellSpacing="0">
		<tr>
			<td>Resultado de la b&uacute;squeda</td>
		</tr>
	</table>
	<s:if test="%{listaContadoresUso.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
								id="idResultadosPorPagina"
								value="resultadosPorPagina"
								title="Resultados por página"
								onchange="javascript:cambiarElementosPorPagina();"
								/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
</div>


<display:table name="listaContadoresUso" excludedParams="*"
		requestURI="navegarHistorico.action"
		class="tablacoin"   
		summary="Listado de Contratos"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				
  
	<display:column property="razonSocial" title="Raz&oacute;n social" sortable="true" headerClass="sortable" defaultorder="descending" style="width:20%"/>
                   
	<display:column property="numColegiado" title="Nº de colegiado" sortable="true" headerClass="sortable" defaultorder="descending"/>
	
	<display:column property="aplicacion" title="Aplicacion" sortable="true" headerClass="sortable" defaultorder="descending"/>
                   
	<display:column property="contador" title="Contador" sortable="true" headerClass="sortable" defaultorder="descending"/>
	
	
</display:table>

<s:hidden key="idHistoricoCreditoContrato"/>

</s:form>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>

