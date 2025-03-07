<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/mandato/mandato.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Códigos Mandatos</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCodigoMandato">C&oacutedigo Mandato:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="mandato.codigoMandato" id="idCodigoMandato" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha Mandato:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaMandatoDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="mandato.fechaMandato.diaInicio" id="diaFechaMandatoIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="mandato.fechaMandato.mesInicio" id="mesFechaMandatoIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="mandato.fechaMandato.anioInicio" id="anioFechaMandatoIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaMandatoIni, document.formData.mesFechaMandatoIni, document.formData.diaFechaMandatoIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha Mandato:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaMandatoHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="mandato.fechaMandato.diaFin" id="diaFechaMandatoFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="mandato.fechaMandato.mesFin" id="mesFechaMandatoFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="mandato.fechaMandato.anioFin" id="anioFechaMandatoFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaMandatoFin, document.formData.mesFechaMandatoFin, document.formData.diaFechaMandatoFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">CIF:</label>
					</td>
					<td  align="left">
						<s:textfield name="mandato.cif" id="idCif" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Nombre Empresa:</label>
					</td>
					<td  align="left">
						<s:textfield name="mandato.empresa" id="idEmpresa" size="50" maxlength="50" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelContrato">Contrato:</label>
					</td>
					<td align="left">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo"
							headerKey="" listValue="descripcion" cssStyle="width:220px"
							name="mandato.idContrato" id="idContrato" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">DNI Administrador:</label>
					</td>
					<td  align="left">
						<s:textfield name="mandato.dniAdministrador" id="idDniAdministrador" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Nombre Administrador:</label>
					</td>
					<td  align="left">
						<s:textfield name="mandato.nombreAdministrador" id="idNombreAdministrador" size="50" maxlength="50" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">DNI 2º Administrador:</label>
					</td>
					<td  align="left">
						<s:textfield name="mandato.dniAdministrador2" id="idDniAdministrador2" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Nombre 2º Administrador:</label>
					</td>
					<td  align="left">
						<s:textfield name="mandato.nombreAdministrador2" id="idNombreAdministrador2" size="50" maxlength="50" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsultaMandatos" id="idBConsultaMandatos" value="Consultar"  onkeypress="this.onClick" onclick="return buscarConsultaMandatos();"/>			
			<input type="button" class="boton" name="bLimpiarMandatos" id="idBLimpiarMandatos" onclick="javascript:limpiarConsultaMandatos();" value="Limpiar"/>		
		</div>
		<br/>
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
								<td width="90%" align="right">
									<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPaginaConsultaMandatos" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaMandatos();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaMandatos" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaMandatos" requestURI= "navegarConsultaMandatos.action"
				id="tablaConsultaMandatos" summary="Listado de Mandatos" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="idMandato" media="none" /> 

				<display:column property="codigoMandato" title="C&oacutedigo Mandato" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%"/>
					
				<display:column property="fechaMandato" title="Fec. Mand." sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" format="{0,date,dd/MM/yyyy}"/>	
				
				<display:column property="cif" title="CIF" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>	
				
				<display:column property="empresa" title="Nom. Empresa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>	
					
				<display:column property="dniAdministrador" title="DNI Admin." sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>
					
				<display:column property="nombreAdministrador" title="Nom. Admin." sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>
				
				<display:column property="numColegiado" title="Num. Colegiado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
									onclick="abrirEvolucionMandatos(<s:property value="#attr.tablaConsultaMandatos.idMandato"/>,'divEmergenteEvolucionMandato');"
									title="Ver evolución" />
							</td>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaMandatos.idMandato"/>"
									value='<s:property value="#attr.tablaConsultaMandatos.idMandato"/>' />
							</td>
						</tr>
					</table>
				</display:column>
				
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaMandatos" style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bEliminarMandatos" id="idBEliminarMandatos" value="Eliminar" 
					onClick="javascript:eliminarMandatos();" onKeyPress="this.onClick" />
	
	 			 <input type="button" class="boton" name="bModificarMandatos" id="idBModificarMandatos" value="Modificar Mandato"
						onClick="javascript:modificarMandatos();" onKeyPress="this.onClick" />
						
				 <input type="button" class="boton" name="bAltaMandatos" id="idBAltaMandatos" value="Alta Mandato"
						onClick="javascript:altaMandato();" onKeyPress="this.onClick" />		
						
			</div>
			
			<div id="divEmergenteEvolucionMandato" style="display: none; background: #f4f4f4;"></div>
		</s:if>
	</s:form>
</div>
