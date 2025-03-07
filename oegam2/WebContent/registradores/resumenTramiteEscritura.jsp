<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="resumen">

	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Datos de escritura</td>
				<s:if test="tramiteRegistro.idTramiteRegistro">
					<td  align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
		</tbody>
	</table>
	
	<table class="nav" cellspacing="1" cellpadding="5" width="100%">
		<tr>
			<td class="tabular" style=""><span class="titulo">Tr&aacute;mite</span></td>
			
		</tr>
	</table>
	
	
	<div id="busqueda" style="width: 100%;">
		<%@include file="/registradores/resumen/resumenEstadoEnvioEscrituras.jsp" %>
	</div>
		
	<div>&nbsp;</div>
		
	<s:if test="tramiteRegistro.formaPago == 1">
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Forma de pago por cargo a cuenta</span></td>
				</tr>
			</table>
		</div>
		
		<div id="busqueda" style="width: 100%;">
			<%@include file="/registradores/resumen/resumenCargoCuenta.jsp" %>
		</div>
	</s:if> 
	
	<s:if test="tramiteRegistro.formaPago == 2">
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Forma de pago por transferencia</span></td>
				</tr>
			</table>
		</div>
	</s:if>
	
	<s:if test="tramiteRegistro.formaPago == 3">
		<div class="nav">
			<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
				<tr>
					<td class="tabular" style="">
						<span class="titulo">Identificaci&oacute;n Corpme (código usuario abonado o usuario + contraseña)</span></td>
				</tr>
			</table>
		</div>
		
		<div id="busqueda" style="width: 100%;">
			<%@include file="/registradores/resumen/resumenIdentificacionCorpme.jsp" %>
		</div>
	</s:if>
	
	<div>&nbsp;</div>
		
	<div class="nav">
		<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style="">
					<span class="titulo">Datos requeridos:</span>
				</td>
			</tr>
		</table>
	</div>
		
	<div id="busqueda" style="width: 100%;">
		<%@include file="/registradores/resumen/resumenDatosRequeridos.jsp" %>
	</div>
		
	<div>&nbsp;</div>
		
	<div class="nav">
		<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style="">
					<span class="titulo">Bienes/Inmuebles referenciados:</span>
				</td>
			</tr>
		</table>
	</div>
		
	<div id="busqueda" style="width: 100%;">	
		<%@include file="/registradores/resumen/resumenBienesInmuebles.jsp" %>
	</div>
		
	<div>&nbsp;</div>
				
	<div id="botonBusqueda">
		<table border="0" width="100%">
			<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<s:if test="tramiteRegistro.estado == 1">
							<input type="button" value="Validar" class="boton" id="btnValidar" onclick="javascript:validarTramiteRegistradores('btnValidar','formData', 'validarContratoTramiteRegistroMd6.action#resumen');"/>
						</s:if>
						<s:if test="tramiteRegistro.estado == 28">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEnvioDprRegistradores()}">
							<input type="button" value="Enviar Dpr" class="boton" id="btnEnviarDpr" onclick="construirDpr()" />
							&nbsp;
						</s:if>
						</s:if>
						<s:if test="tramiteRegistro.estado == 8 || tramiteRegistro.estado == 10 || tramiteRegistro.estado == 12 || tramiteRegistro.estado == 14 || tramiteRegistro.estado == 17"> 
							<input type="button" id="btnFirmarAcuse" value="Firmar acuse" class="boton" onclick="construirFirmarAcuse('', ''); " />
							&nbsp;
						</s:if>
						<s:if test="tramiteRegistro.estado==15 || tramiteRegistro.estado==13" >
						<s:if test='!"S".equals(tramiteRegistro.subsanacion)'>
							<input type="button" id="btnSubsanar" value="Subsanar" class="boton" onclick="subsanar();" />
							&nbsp;
						</s:if>
						</s:if>
						<s:if test="tramiteRegistro.estado != null">
							<input type="button" id="btnDuplicar" value="Duplicar trámite" class="boton" onclick="duplicar();" />
						</s:if>
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
				</tr>
			</table>
		</div>
	</div>