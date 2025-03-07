<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/estadisticas/estadisticas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<iframe width="174" height="189" name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" 
	frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">ESTADÍSTICAS: Listado de Estadísticas de Usuario</span>
			</td>
		</tr>
	</table>
</div>

<s:if test="%{passValidado=='false' or passValidado=='error'}">
	<s:form method="post" id="formData" name="formData">
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap"><label for="idPassword">Introduzca
						la clave:</label></td>
				<td align="center" nowrap="nowrap"><input type="password"
					autocomplete="off" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idPassword" value=""
					maxlength="40" size="20" name="listadoUsuariosIPBean.password">
				</td>
				<td align="left" nowrap="nowrap" colspan="2"><input
					type="button" class="botonMasGrande" name="bVerificar" id="bVerificar"
					value="Verificar Contraseña" onkeypress="this.onClick"
					onclick="return comprobarPasswordListadosUsuarios();" /></td>
			</tr>
		</table>
	</s:form>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
</s:if>

<s:if test="%{passValidado=='true'}">
	<ol id="toc">
		<li id="usuariosPorIp"><a href="#usuariosPorIp">Usuarios por IP</a></li>
		<li id="usuariosOnline"><a href="#usuariosOnline">Usuarios Online</a></li>
		<li id="usuariosOnlineAgrupados" onclick="listarUsuariosOnlineAgrupadosListadoUsuarios();"><a href="#usuariosOnlineAgrupados">Usuarios Online Agrupados</a></li>
		<li id="usuariosOnlineRepetidos" onclick="listarUsuariosOnlineRepetidosListadoUsuarios();"><a href="#usuariosOnlineRepetidos">Usuarios Online Repetidos</a></li>
		
	</ol>
	
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	
	<s:form method="post" id="formData" name="formData">
	
		<div id="usuariosPorIp" class="contentTabs"> 
			<%@include file="usuariosPorIP.jsp" %>
		</div> 
		
	 	<div id="usuariosOnline" class="contentTabs"> 
			<%@include file="usuariosOnline.jsp" %>
		</div>
		
		<div id="usuariosOnlineAgrupados" class="contentTabs"> 
			<%@include file="usuariosOnlineAgrupados.jsp" %>
		</div>
		
		<div id="usuariosOnlineRepetidos" class="contentTabs"> 
			<%@include file="usuariosOnlineRepetidos.jsp" %>
		</div>
		
		<div id="bloqueLoadingConsultarEst" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
		
	</s:form>
</s:if>
	
<script>

	trimCamposInputs();
	
	$(document).ready(function(){
		$("#idPassword").keypress(function(e) {
		    if(e.which == 13) {
		    	comprobarPasswordListadosUsuarios();
		    }
		});
	});
	
	$( "#usuariosOnlineAgrupados" ).on( "tabsbeforeload", function( event, ui ) {
		activarPestania();
	} );
</script>

	