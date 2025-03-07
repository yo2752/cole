
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisoInternacional/permisoInternacional.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Permiso Internacional</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
			</td>
		</tr>
	</table>
</div>

<div>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	
	<ol id="toc">
		<li id="PermisoInternacional"><a href="#PermisoInternacional">Permiso Internacional</a></li>
	    <li id="Titular"><a href="#Titular">Titular</a></li> 
	    <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().puedeGenerarMandato(permisoInternacionalDto)}">
	    	<li id="Mandato"><a href="#Mandato">Mandato</a></li>
	    </s:if>
	    <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esEstadoTramiteAportacionDoc(permisoInternacionalDto)}">
	   		<li id="DocumentacionAportada"><a href="#DocumentacionAportada">Documentación Aportada</a></li>
	   	</s:if> 
	    <s:if test="%{permisoInternacionalDto.numExpediente!=null}">
	    	<li id="Resumen"><a href="#Resumen">Resumen</a></li>  
	    </s:if>
	</ol>
	
	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

		<div class="contentTabs" id="PermisoInternacional" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaPermisoInternacional.jsp" %>
		</div>
		
		<div class="contentTabs" id="Titular" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitularPermInter.jsp" %>
		</div>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().puedeGenerarMandato(permisoInternacionalDto)}">
			<div class="contentTabs" id="Mandato" 	
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaMandato.jsp" %>
			</div>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esEstadoTramiteAportacionDoc(permisoInternacionalDto)}">
			<div class="contentTabs" id="DocumentacionAportada" 	
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaDocAportada.jsp" %>
			</div>
		</s:if>
		
		<s:if test="%{permisoInternacionalDto.numExpediente!=null}">
			<div class="contentTabs" id="Resumen" 	
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaResumenPermInter.jsp" %>
			</div>
		</s:if>
		
		<div id="bloqueBotones" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
		
		<table align="center">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esGuardable(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibGuardarPermisoInternacional" name="bGuardarPermisoInternacional" value="Guardar" onClick="javascript:guardarPermisoInternacional();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().puedeEnviarDeclaracion(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibFirmarDeclPermisoInternacional" name="bFirmarDeclPermisoInternacional" value="Firmar Declaración" onClick="javascript:firmarDeclPermisoInternacional();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esValidable(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibValidarPermisoInternacional" name="bValidarPermisoInternacional" value="Validar" onClick="javascript:validarPermisoInternacional();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esTramitable(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibTramitarPermisoInternacional" name="bTramitarPermisoInternacional" value="Tramitar" onClick="javascript:tramitarPermisoInternacional();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esImprimible(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibImprimirPermisoInternacional" name="bImprimirPermisoInternacional" value="Imprimir" onClick="javascript:imprimirPermisoInternacional();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esEnviableDeclaracionColegiado(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibEnviarDeclColegiado" name="bEnviarDeclColegiado" value="Enviar Declaración" onClick="javascript:enviarDeclaracion();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esEnviableDeclaracionColegio(permisoInternacionalDto)}">
					<td>
						<input class="boton" type="button" id="ibEnviarDeclColegio" name="bEnviarDeclColegio" value="Enviar Decl. Colegio" onClick="javascript:enviarDeclaracion();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<td>
					<input type="button" class="boton" name="bVolverPermisoInternacional" id="ibVolverPermisoInternacional" value="Volver" onClick="javascript:volverPermisoInternacional();" onKeyPress="this.onClick"/>
		 		</td>	
			</tr>
		</table>
		
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" 
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
		
		<s:hidden id="nifBusqueda" name="nifBusqueda"></s:hidden>
		<s:hidden id="idContratoPermInter" name="permisoInternacionalDto.idContrato"></s:hidden>
		<s:hidden id="idPermiso" name="permisoInternacionalDto.idPermiso"/>
		
		<s:hidden name="permisoInternacionalDto.tipoTramite"></s:hidden>
		<s:hidden name="permisoInternacionalDto.doiTipo"></s:hidden>
		<s:hidden id="numColegiadoPI" name="permisoInternacionalDto.numColegiado"></s:hidden>
		<s:hidden name="permisoInternacionalDto.estado"></s:hidden>
		<s:hidden name="permisoInternacionalDto.estadoImpresion"></s:hidden>
		<s:hidden name="permisoInternacionalDto.estadoDeclaracion"></s:hidden>
		<s:hidden name="permisoInternacionalDto.documentacionAportada"></s:hidden>
		<s:hidden name="permisoInternacionalDto.jefatura"></s:hidden>
		
		<s:hidden name="permisoInternacionalDto.fechaUltModif.dia"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaUltModif.mes"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaUltModif.anio"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaUltModif.hora"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaUltModif.minutos"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaUltModif.segundos"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaAlta.dia"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaAlta.mes"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaAlta.anio"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaAlta.hora"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaAlta.minutos"></s:hidden>
		<s:hidden name="permisoInternacionalDto.fechaAlta.segundos"></s:hidden>
	</s:form>
	
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div> 
	
</div> 