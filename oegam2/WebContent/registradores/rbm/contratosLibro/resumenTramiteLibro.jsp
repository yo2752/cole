<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Resumen">
	
	<div id="busqueda" class="busqueda">
		<%@include file="resumenEstado.jsp" %>
	</div>
	
	<div>&nbsp;</div>
	
	<div class="nav">
		<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style="">
					<span class="titulo">Forma de pago por identificación CORPME (código usuario abonado o usuario + contraseña)</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda" class="busqueda">
		<%@include file="/registradores/resumen/resumenIdentificacionCorpme.jsp" %>	
	</div>
	<div>&nbsp;</div>
	
	<div id="busqueda" class="busqueda">
		<%@include file="resumenSociedad.jsp" %>
	</div>

	<div>&nbsp;</div>
	
	<div id="busqueda" class="busqueda">
		<%@include file="resumenRegistro.jsp" %>
	</div>

	<div>&nbsp;</div>
	
	<div id="busqueda" class="busqueda">
		<div id="botonBusqueda">
			<table width="100%">
				<tr>
					<td>
						<s:if test="tramiteRegistro.estado == 1">
							<input type="button" value="Validar" class="boton" id="btnValidar"  onclick="javascript:validarTramiteRegistradores('btnValidar','formData', 'validarTramiteLibro.action#Resumen');"/>
						</s:if>
						&nbsp;
						<s:if test="tramiteRegistro.estado == 28">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEnvioDprRegistradores()}">
							<input type="button" value="Enviar Dpr" class="boton" id="btnEnviarDpr" onclick="construirDpr()" />
						</s:if>
						</s:if>
						&nbsp;
						<s:if test="tramiteRegistro.estado == 8 || tramiteRegistro.estado == 10 || tramiteRegistro.estado == 12 || tramiteRegistro.estado == 14 || tramiteRegistro.estado == 17"> 
							<input type="button" id="btnFirmarAcuse" value="Firmar acuse" class="boton" onclick="construirFirmarAcuse('', '');" />
						</s:if>
						<%--En trámites de envío de libros y cuentas no se subsana ni se duplica ya que todo depende de los documentos adjuntos --%>
<%-- 						&nbsp;
						<s:if test="tramiteRegistro.estado == 18">
							<input type="button" id="btnDuplicar" value="Duplicar trámite" class="boton" onclick="duplicarRBM();" />
						</s:if>
						&nbsp;
						<s:if test="tramiteRegistro.estado==15 || tramiteRegistro.estado==13"> 
						<s:if test='!"S".equals(tramiteRegistro.subsanacion)'>
							<input type="button" id="btnSubsanar" value="Subsanar" class="boton" onclick="subsanarRBM();" />
						</s:if>
						</s:if> --%>
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>