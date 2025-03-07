<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/ficherosTemporalesBotones.js" type="text/javascript"></script>

<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData">
	<s:hidden id="resultadosPorPagina" name="resultadosPorPagina"/>
	<s:hidden id="idFicheroBorrado" name="ficheroBorrado"/>
	<s:hidden id="flagDisabled" name="flagDisabled"/>
	<div class="nav">
		<table style="width:100%" >
			<tr>
				<td class="tabular">
					<span id="textoTitulo" class="titulo"></span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="labelNombre" >Nombre Fichero: </label>
				</td>
				<td align="left">
					<s:textfield name="ficherosTemporalesBean.nombreFichero" id="idNombreFichero" onblur="this.className='input2';" 
			        					onfocus="this.className='inputfocus';"  size="25"/>
				</td>
				<td>
					<table style="width:80%">
						<tr>
							<td align="left">
								<label for="labelTipoDoc" >Tipo de Fichero: </label>
							</td>
							<td align="left">
								<s:if test="%{flagDisabled}">
									<s:textfield value="%{@org.gestoresmadrid.core.model.enumerados.TipoFicheros@convertirTexto(ficherosTemporalesBean.tipoFichero)}" 
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="25" maxlength="28" readonly="true"/>
									<s:hidden id="hTipoFichero" name="ficherosTemporalesBean.tipoFichero"/>
								</s:if>	
								<s:else>						
									<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaFicherosTemporales@getInstance().getComboTipoDocumentos()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										headerKey="-1"
				           				headerValue="Seleccione Tipo Fichero"
										name="ficherosTemporalesBean.tipoFichero" 
										listKey="valorEnum" listValue="nombreEnum"
										id="idTipoFichero"/>
								</s:else>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha" >Fecha de Alta: </label>
				</td>
				<td>
					<table style="width:40%">
						<tr>
							<td align="right">
								<label for="labelDesde">desde: </label>
							</td>
							<td align="left">
								<s:textfield name="ficherosTemporalesBean.fechaAlta.diaInicio" id="diaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="ficherosTemporalesBean.fechaAlta.mesInicio" id="mesIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="ficherosTemporalesBean.fechaAlta.anioInicio" id="anioIni" onblur="this.className='input2';"
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
					<table style="width:50%">
						<tr>
							<td align="right">
								<label for="labelHasta">hasta:</label>
							</td>	
							<td align="left">
								<s:textfield name="ficherosTemporalesBean.fechaAlta.diaFin" id="diaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="ficherosTemporalesBean.fechaAlta.mesFin" id="mesFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="ficherosTemporalesBean.fechaAlta.anioFin" id="anioFin" onblur="this.className='input2';"
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
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelNumColegiado">Num. Colegiado: </label>
						</td>
						<td align="left">
							<s:textfield name="ficherosTemporalesBean.numColegiado" id="idNumColegiado" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false" size="10" maxlength="25" />
						</td>
					</tr>
				</s:if>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bConsultarFicherosTemporales" id="idBotonConsultarFicherosTemporales" 
		  	value="Consultar" onClick="return consultaFicheros();"onKeyPress="this.onClick"/>	
		<input type="button" class="boton" name="bLimpiar" id="idBotonLimpiarConsulta" value="Limpiar"  
			onkeypress="this.onClick" onclick="return limpiarConsultaFicheros();"/>		
	</div>
	<br/>
	<div id="resultado" style="width:100%;background-color:transparent;">
		<table class="subtitulo" style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table style="width:100%">
				<tr>
					<td align="right">
						<table style="width:100%">
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
										onchange="return cambiarElementosPorPaginaConsultaFicheros();" 
										/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	<display:table name="lista" excludedParams="*"
					requestURI="navegar${action}"
					class="tablacoin"
					uid="tablaConsultaFicherosTemporales"
					summary="Listado de Ficheros"
					cellspacing="0" cellpadding="0"
					pagesize="${resultadosPorPagina}"
					partialList="false" size="5"
					decorator="${decorator}">
					
		<display:column  property="idFicheroTemporal" title="ID" headerClass="sortable centro" style="width:14%" media ="none" paramId="idFicheroTemporal"/>
		
		<display:column property="nombre" title="Nombre" sortable="true" headerClass="sortable centro"
			defaultorder="descending" style="width:14%" />
			
		<display:column property="tipoDocumento" title="Tipo" sortable="true" headerClass="sortable centro"
			defaultorder="descending" style="width:14%" />
			
		<display:column property="fecha" title="Fecha" sortable="true" headerClass="sortable centro"
			defaultorder="descending" style="width:10%" />
			
			<display:column  property="estado" title="Estado" headerClass="sortable centro" style="width:14%" media ="none"/>
			
		<display:column style="width:4%" title="">
			<a onclick="" href="javascript:descargarFichero('${tablaConsultaFicherosTemporales.idFicheroTemporal}', '${tablaConsultaFicherosTemporales.estado}')"> Descargar </a>
		</display:column>
					
	</display:table>		
</s:form>
<script  type="text/javascript">
	mostrarAlertFichero();
	ponerTitulo();
</script>