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
<script src="js/consultaDev/consultaDev.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Masiva de Consultas DEV</span>
			</td>
		</tr>
	</table>
</div>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Seleccione el fichero a importar</td>
		</tr>
	</table>
	<table class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap">
					<label for="labelContrato">Contrato:</label>
				</td>
				<td align="left">
					<s:select id="idContratoMasivasDev"
						list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" headerValue="Seleccione Contrato"
						onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
						listValue="descripcion" cssStyle="width:220px"
						name="idContrato">
					</s:select>
				</td>
			</s:if>
			<s:else>
				<s:hidden name="idContrato" id="idContratoMasivasDev"/>
			</s:else>
			<td align="left" nowrap="nowrap">
				<label for="labelFichero">Fichero:</label>
			</td>
			<td  align="left">
				<s:file id="idFicheroAltaMas" size="50" name="fichero"/>
			</td>
		</tr>
		<tr></tr>
	</table>
	&nbsp
	<s:if test="%{resumen != null}">
			<table class="subtitulo" cellSpacing="0" cellspacing="0" >
				<tr>
					<td>Resumen Alta Consultas Masivas Dev:</td>
				</tr>
			</table>
			<table style="width:100%;font-size:11px;" class="tablacoin" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
		   		<tr>
		   			<th></th>
		   			<th>Guardados</th>
		   			<th>Fallidos</th>
				</tr>
				<tr>
					<td style="font-weight:bold;">
						TOTAL
					</td>
					<td>
						<s:label style="color:green" value="%{resumen.numOk}"/>
						<s:if test="%{resumen.numOk != null && resumen.numOk != 0}">
							<img id="despValidado"  alt="Mostrar" src="img/plus.gif" onclick="return mostrarBloqueActualizados();" />
			   			</s:if>
					</td>
					<td>
						<s:label style="color:red" value="%{resumen.numError}"/>
						<s:if test="%{resumen.numError != null && resumen.numError != 0}">
							<img id="despFallidos"  alt="Mostrar" src="img/plus.gif" onclick="return mostrarBloqueFallidos();" />
			   			</s:if>	
					</td>
				</tr>
		 	</table>
			<div id="bloqueActualizados" style="display:none;width:100%;font-size:11px;">
				<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
				<tr>
					<td  style="width:100%;text-align:center;">Detalle de Consultas Dev Guardadas</td>
				</tr>
				</table>						
					<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left">
						<s:iterator value="resumen.listaOk">
							<li><span style="text-align: left;"><s:property /></span></li>	
						</s:iterator>
					</ul>		
			</div>		
			<div id="bloqueFallidos" style="display:none;width:100%;font-size:11px;">
				<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
					<tr>
						<td  style="width:100%;text-align:center;">Detalle de Consultas Dev Fallidas</td>
					</tr>
				</table>	
				<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left;">
					<s:iterator value="resumen.listaErrores">
						<li><span><s:property /></span></li>	
					</s:iterator>
				</ul>
			</div> 
	</s:if>
	<br><br>
	<div class="acciones center">
		<div id="bloqueLoadingMasivaConsultaDev" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
		<input type="button" class="boton" name="bEnviarDev" id="idEnviarDev" value="Enviar" onClick="javascript:altaMasivasConsultasDev();"
 			onKeyPress="this.onClick"/>
	</div>
</s:form>
