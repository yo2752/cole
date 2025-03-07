<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Resumen">
	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Tr&aacute;mite</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
			</td>
		</tr>
	</table>
	
	
	&nbsp;
	<div id="busqueda" class="busqueda">
		<%@include file="/registradores/resumen/resumenEstado.jsp" %>
	</div>
		
	<div>&nbsp;</div>
	
	<s:if test="tramiteRegistro.estado == 3 || tramiteRegistro.estado == 9 || tramiteRegistro.estado == 15 || tramiteRegistro.estado == 13">
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Identificación CORPME (código usuario abonado o usuario + contraseña)</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenIdentificacionCorpme.jsp" %>	
		</div>
		<div>&nbsp;</div>
	</s:if>
	
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Sociedad</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenSociedad.jsp" %>
		</div>
		<div>&nbsp;</div>
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Lugar y fecha de la certificaci&oacute;n</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenLugarFechaCertif.jsp" %>
		</div>
		<div>&nbsp;</div>
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Certificantes</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenListaCertif.jsp" %>
		</div>
		<div>&nbsp;</div>
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Informaci&oacute;n de la Reuni&oacute;n de la Junta</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenReunionJunta.jsp" %>
		</div>
		<div>&nbsp;</div>
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Acuerdos alcanzados (ceses)</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenCeses.jsp" %>
		</div>
		<div>&nbsp;</div>
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Acuerdos alcanzados (nombramientos)</span>
					</td>
				</tr>
			</table>
		</div>
		<div id="busqueda" class="busqueda">
			<%@include file="/registradores/resumen/resumenNombramientos.jsp" %>
		</div>

		<div>&nbsp;</div>
		<div id="busqueda" class="busqueda">
		<div id="botonBusqueda">
			<table width="100%">
				<tr>
					<td>
						<s:if test="tramiteRegistro.estado == 1">
							<input type="button" value="Validar" class="boton" id="btnValidar" onclick="javascript:doPost('formData', 'validarTramiteTramiteRegistroMd1.action#Resumen', null);"/>
						</s:if>
						<s:if test="tramiteRegistro.estado == 28">
							<input type="button" id="btnFirmar" value="Firmar" class="boton" onclick="construirRm(true, 1);" />
						</s:if>
						&nbsp;
						<s:if test="tramiteRegistro.estado == 3">
							<input type="button" value="Enviar Dpr" class="boton" id="btnEnviarDpr" onclick="construirDpr()" />
						</s:if>
						&nbsp;
						<s:if test="tramiteRegistro.estado == 8 || tramiteRegistro.estado == 10 || tramiteRegistro.estado == 12 || tramiteRegistro.estado == 14 || tramiteRegistro.estado == 17"> 
							<input type="button" id="btnFirmarAcuse" value="Firmar acuse" class="boton" onclick="construirFirmarAcuse('', ''); " />
							&nbsp;
						</s:if>
						<s:if test="tramiteRegistro.estado==18"> 
							<input type="button" id="btnDuplicar" value="Duplicar trámite" class="boton" onclick="duplicar();" />
							&nbsp;
						</s:if>
						<s:if test="tramiteRegistro.estado==15 || tramiteRegistro.estado==13"> 
						<s:if test='!"S".equals(tramiteRegistro.subsanacion)'>
							<input type="button" id="btnSubsanar" value="Subsanar" class="boton" onclick="subsanar();" />
							&nbsp;
						</s:if>
						</s:if>
						<input type="button" value="Ver documento" class="boton" id="btnVerDocumento" onclick="construirRm(false, 1);" />
						&nbsp;
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>