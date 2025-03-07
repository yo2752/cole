<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/empresaTelematica.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Empresas Telemáticas</span>
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> 
	</iframe>

	<s:form method="post" id="formData" name="formData">
		<input type="hidden" name="id" id="id" value="<s:property value="empresaTelematicaDto.id"/>"/>
 		<s:hidden name="empresaTelematicaDto.numColegiado" id="numColegiado"/>
		<s:hidden name="empresaTelematicaDto.tipoTramite"/>
		<s:hidden name="empresaTelematicaDto.usuarioDto.idUsuario"/>
		<s:hidden name="empresaTelematicaDto.fechaAlta.dia"/>
		<s:hidden name="empresaTelematicaDto.fechaAlta.mes"/>
		<s:hidden name="empresaTelematicaDto.fechaAlta.anio"/>
		<s:hidden name="empresaTelematicaDto.fechaUltModif.dia"/>
		<s:hidden name="empresaTelematicaDto.fechaUltModif.mes"/>
		<s:hidden name="empresaTelematicaDto.fechaUltModif.anio"/>
		<table class="subtitulo" cellSpacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos de Empresa Telemática</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().visualizarResumen(empresaTelematicaDto)}">
					<td style="border: 0px;" align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
							onclick="abrirEvolucionInteve('<s:property value="empresaTelematicaDto.id"/>','divEmergenteEvolucionAltaEmpresaTelematica');" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
		</table>
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
		<div class="contenido">	
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
				<tr>
					<td class="tabular">
						<span class="titulo">Empresa Telemática</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExp">ID:</label>
					</td>
					<td>
						<s:textfield id="idNumExpEmpresaTelematica" name="empresaTelematicaDto.id"  onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="20" maxlength="10" disabled="true"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEmpresa">Empresa:</label>
					</td>
					<td>
						<s:textfield id="idEmpresaEmpresaTelematica" name="empresaTelematicaDto.empresa"  onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td>
						<s:select list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getEstados()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado"
							name="empresaTelematicaDto.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoEmpresaTelematica" disabled="true"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelCif">CIF:</label>
					</td>
					<td>
						<s:textfield id="idCifEmpresaTelematica" name="empresaTelematicaDto.cifEmpresa"  onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCodigoPostal">Código Postal:</label>
					</td>
					<td>
						<s:textfield id="idCodigoPostalTelematica" name="empresaTelematicaDto.codigoPostal"  onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelProvincia">Provincia:</label>
					</td>
					<td>
						<s:select list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getProvincias()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Provincia"
							name="empresaTelematicaDto.provincia" listKey="idProvincia" listValue="nombre" id="idProvinciaEmpresaTelematica"
							onchange="cargarListaMunicipiosCAYC('idProvinciaEmpresaTelematica', 'idMunicipioEmpresaTelematica'); " />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMunicipio">Municipio:</label>
					</td>
					<td>
						<s:select list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getMunicipios(empresaTelematicaDto.provincia)" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Municipio"
							name="empresaTelematicaDto.municipio" listKey="idMunicipio" listValue="nombre" id="idMunicipioEmpresaTelematica"/>
					</td>
				</tr>
				<tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().esAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td>
							<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo"
								headerKey="" listValue="descripcion" cssStyle="width:220px" name="empresaTelematicaDto.idContrato">
							</s:select>
						</td>
					</s:if>
				</tr>
			</table>
		</div>

		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().esEstadoGuardable(empresaTelematicaDto)}">
				<input type="button" class="boton" name="bGuardarEmpresaTelematica" id="idGuardarEmpresaTelematica" value="Guardar" onClick="javascript:guardarEmpresaTelematica();"
					onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().esEstadoConsultable(empresaTelematicaDto)}">
				<input type="button" class="boton" name="bGenerarXmlEmpresaTelematica" id="idGenerarXmlEmpresaTelematica" value="Generar XML" onClick="javascript:generarXmlEmpresaTelematica();"
					onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().esEstadoDescargarXml(empresaTelematicaDto)}">
				<input type="button" class="boton" name="bDescargarXmlEmpresaTelematica" id="idDescargarXmlEmpresaTelematica" value="Descargar XML" onClick="javascript:descargarXML();"
					onKeyPress="this.onClick"/>
			</s:if>

			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().esEstadoImprimir(empresaTelematicaDto)}">
				<input type="button" class="boton" name="bImprimirEmpresaTelematica" id="idImprimirEmpresaTelematica" value="Descargar Informes" onClick="javascript:imprimirEmpresaTelematica();"
					onKeyPress="this.onClick"/>
			</s:if>
			<input type="button" class="boton" name="bVolverEmpresaTelematica" id="idVolverEmpresaTelematica" value="Volver" onClick="javascript:volverEmpresaTelematica();"
				onKeyPress="this.onClick"/>
		</div>
		<s:if test="%{@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().esEstadoDescargarXml(empresaTelematicaDto)}">
			<input type="button" class="boton" name="bImprimirEmpresaTelematica" id="idImprimirEmpresaTelematica" value="Descarga INTEVE" onClick="descargarCliente();"
			 		onKeyPress="this.onClick"/>
		</s:if>
		<div id="divEmergenteEvolucionAltaEmpresaTelematica" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>