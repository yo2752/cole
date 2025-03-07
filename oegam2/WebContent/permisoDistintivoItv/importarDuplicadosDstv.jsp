<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/distintivoDgt.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Importación de Duplicados de Distintivos</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td align="left">
							<s:select id="idContratoDupDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="idContrato"
								/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="idContrato"/>
				</s:else>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFichero">Fichero:</label>
					</td>
					<td>
						<s:file id="idFicheroDupDstv" size="50" name="fichero" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bImportarDupDstv" id="idBImportarDupDstv" value="Importar" onkeypress="this.onClick" onclick="javascript:importarDupDstv();"/>			
		</div>
		<s:if test="%{resumen != null}">
			<div align="center">
				<table class="subtitulo" cellSpacing="0" cellspacing="0">
					<tr>
						<td>Resumen Importacion Duplicados Distintivos:</td>
					</tr>
				</table>
				<table style="width:100%;font-size:11px;" class="tablacoin" summary="Resumen de la importacion" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
					<tr>
						<th></th>
						<th>Guardados</th>
						<th>Fallidos </th>
					</tr>
					<tr>
						<td></td>
						<td>
							<s:label style="color:green;" value="%{resumen.numOk}"/>
						</td>
						<td>
							<s:label style="color:red;" value="%{resumen.numError}"/>
						</td>
					</tr>
					<tr>
						<td style="font-weight:bold;">
							TOTAL
						</td>
						<td>
							<s:label style="color:green" value="%{resumen.numOk}"/>
							<s:if test="%{resumen.numOk != null && resumen.numOk != 0}">
								<img id="despValidadoOkImpor" alt="Mostrar" src="img/plus.gif" onclick="return mostrarBloqueOKImportacion();" />
							</s:if>
						</td>
						<td>
							<s:label style="color:red" value="%{resumen.numError}"/>
							<s:if test="%{resumen.numError != null && resumen.numError != 0}">
								<img id="despFallidosKoImpor" alt="Mostrar" src="img/plus.gif" onclick="return mostrarBloqueKOImportacion();" />
							</s:if>
						</td>
					</tr>
				</table>
				<div id="bloqueOkImportacion" style="display:none;width:100%;font-size:11px;">
					<table class="subtitulo" cellSpacing="0" style="width:100%;">
						<tr>
							<td style="width:100%;text-align:center;">Detalle Importadas</td>
						</tr>
					</table>
					<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left">
						<s:iterator value="resumen.listaOks">
							<li><span style="text-align: left;"><s:property /></span></li>
						</s:iterator>
					</ul>
				</div>
				<div id="bloqueKoImportacion" style="display:none;width:100%;font-size:11px;">
					<table class="subtitulo" cellSpacing="0" style="width:100%;">
						<tr>
							<td style="width:100%;text-align:center;">Detalle Fallidas</td>
						</tr>
					</table>
					<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left;">
						<s:iterator value="resumen.listaKO">
							<li><span><s:property /></span></li>
						</s:iterator>
					</ul>
				</div>
			</div>
		</s:if>
	</s:form>
</div>