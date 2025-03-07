<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>

<s:form method="post" name="formData" id="formData" enctype="multipart/form-data" action="importarModelos.action">

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular"><span class="titulo">
					Importación de modelos 600 y 601
				</span></td>
			</tr>
		</table>
	</div> <!-- div nav -->

	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Seleccione el fichero a importar</td>
		</tr>
	</table>

	<!-- Seleccionar el archivo -->
	<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
		<tr>
			<td nowrap="nowrap" align="left" width="30%">
				<table align="left" >
					<tr>
						<td width="10"><input type="radio" id="rbModelo600" name="tipoFichero" 
							value="modelo600" checked onchange ="deshabilitarContrato('600');">
						</td>
						<td style="vertical-align:bottom"><label for="dgt">MODELO 600</label></td>
					</tr>
					<tr>
						<td width="10"><input type="radio" id="rbModelo601" name="tipoFichero" 
							value="modelo601" onchange ="deshabilitarContrato('601');">
						</td>
						<td style="vertical-align:bottom"><label for="xml_matriculacion_matw">MODELO 601</label></td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="20%">
				<table align="left" >
					<tr>
						<td align="left"><label>Fichero</label></td>
					</tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<tr>
							<td align="left"><br><label>Contrato:</label></td>
						</tr>
					</s:if>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="50%">
				<table>
					<tr>
						<td>
							<s:file id="fichero" size="50" name="fichero" value="fichero" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
						</td>
					</tr>
					<tr>
						<td>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
								<s:select  list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()" onblur="this.className='input2';" onfocus="this.className='inputfocus';" listKey="codigo" listValue="descripcion" cssStyle="width:250px" name="idContrato"></s:select>
							</s:if>
							<s:else>
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
									<s:select  list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegio(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal())" onblur="this.className='input2';" onfocus="this.className='inputfocus';" listKey="codigo" listValue="descripcion" cssStyle="width:150px" name="idContrato"></s:select>
								</s:if>
							</s:else>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<div id="botonesImportarEtiqueta">
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bEnviar" id="bEnviar" value="Enviar"  onkeypress="this.onClick" onclick="importarModelo()"/>
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
	<div id="resumenTodo" style="display='block';">
	<s:if test="hasActionErrors() || hasActionMessages()">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Resumen de la importación:</td>
				<td><img src="img/impresora.png" style="height: 23px;cursor:pointer;" onclick="imprimirId('resumenTodo',false,null);"></td>
			</tr>
		</table>

		<table style="width:720px;font-size:11px;" class="tablacoin" summary="Resumen de la importación" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
			<tr>
				<th>Tipo de trámite</th>
				<th>Guardados</th>
				<th>No guardados</th>
			</tr>
			<s:iterator value="resumen" var="tramite">
				<tr>
					<s:if test="%{(guardadosBien != 0) || (guardadosMal != 0)}">
					<td style="font-weight:bold;height:auto;">
						<s:property value="tipoTramite"/>
					</td>
				</s:if>
				<s:else>
					<td style="height:auto;">
						<s:property value="tipoTramite"/>
					</td>
				</s:else>
				<s:if test="%{guardadosBien != 0}">
					<td style="color:green;font-weight:bold;height:auto;">
						<table style="width:100%;border:0px;">
							<tr>
								<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosBien"/></td>
								<td style="width:45%;text-align:left;border:0px;height:auto;"><s:if test="%{tipoTramite.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarInfo(this);'></s:if></td>
							</tr>
						</table>
					</td>
				</s:if>
				<s:else>
					<td style="color:green;height:auto;">
						<table style="width:100%;border:0px;">
							<tr>
								<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosBien"/></td>
								<td style="width:45%;text-align:left;border:0px;height:auto;"></td>
							</tr>
						</table>
					</td>
				</s:else>
				<s:if test="%{guardadosMal != 0}">
					<td style="color:red;font-weight:bold;height:auto;">
						<table style="width:100%;border:0px;">
							<tr>
								<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosMal"/></td>
								<td style="width:45%;text-align:left;border:0px;height:auto;"><s:if test="%{tipoTramite.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarError(this);'></s:if></td>
							</tr>
						</table>
					</td>
				</s:if>
				<s:else>
					<td style="color:red;height:auto;">
						<table style="width:100%;border:0px;">
							<tr>
								<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosMal"/></td>
								<td style="width:45%;text-align:left;border:0px;height:auto;"></td>
							</tr>
						</table>
					</td>
				</s:else>
			</tr>
		</s:iterator>
	</table>

	<br>
	<%@include file="../../includes/erroresMasMensajesImp.jspf" %>
	</s:if>
	<s:if test="hasActionErrors() && getTipoFichero()=='dgt'">
		<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
			<tr>
				<td align="left" nowrap="nowrap"  style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
					Puede descargar el fichero con las l&iacute;neas que no se importaron
					haciendo click en el siguiente enlace:
					<s:url var="ficheroDescarga" action="descargarFicheroImportar.action"></s:url>
					<s:a href="%{ficheroDescarga}">Enlace fichero</s:a>
				</td>
			</tr>
		</table>
		</s:if>

		<s:hidden id="tipoFichero" name="tipoFichero"></s:hidden>
	</div>
</s:form>