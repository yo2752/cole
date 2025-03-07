<%-- <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> --%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c2" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.net.InetAddress" %>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value="css/xtree.css"/>" />
<link rel="stylesheet" type="text/css" href="css/content-assist.css" media="all" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="css/global.css"/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="css/jquery-ui.min.css"/>" />

<script type="text/javascript" src="<c:url value="js/xtree.js"/>"></script>
<script type="text/javascript" src="js/content-assist.js"></script>
<script type="text/javascript" src="<c:url value="js/trafico/comunes.js"/>"></script>
<script type="text/javascript" src="<c:url value="js/notificacion.js"/>"></script>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
<script type="text/javascript" src="js/general.js"></script> 

<s:hidden id="idUsuarioSession" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdUsuarioDeSesion()}"></s:hidden>
<s:hidden id="idContratoSession" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal()}"></s:hidden>

<div id="recuadro">
	<div class="cabecera">
		<div id="logo" style="margin-left:-1px;">
			<img src="img/logogtms-2.jpg" alt="Colegio de Gestores Administrativos de Madrid." />
		</div>
		<div id="fonCab">
		</div>
		<div id="titulo" style="height:108px">
			<div style="float:right;padding:5px;padding-top:15px;background-color:transparent;width:500px;text-align:right;">
				<table style="width:480px; height:15px; background-color:transparent;text-align:right;border:0px;">
					<tr>
						<td colspan="3" style="text-decoration:none;color:white;font-size:1.25em;font-weight:bold;">Oficina Electr&oacute;nica para Gestores Administrativos</td>
					</tr>
					<s:if test="#session.GestorArbol != null || #session.GestorAcceso != null">
					<tr>
						<td colspan="2" align="left"><a style="text-decoration:none;color:yellow;font-size:1.25em;">NIF:<s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNifUsuarioSession()"/></a></td>
						<td colspan="1"><a style="text-decoration:none;color:yellow;font-size:1.25em;"> CIF:<s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getCifContrato()"/></a></td>
					</tr>
					<tr>
						<td colspan="2" align="left"><a style="text-decoration:none;color:yellow;font-size:1.15em;">Razon Social: <s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getRazonSocialContrato()"/></a></td>
						<td colspan="1"><a style="text-decoration:none;color:yellow;font-size:1.25em;"> Nº Colegiado:<s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNumColegiadoCabecera()"/></a></td>
					</tr>
					<tr>
						<td colspan="1" align="left"><a id="online" style="text-decoration:none;color:yellow;font-size:1.25em;" href="<s:url action='notificarRegistrar'/>">Notificaciones</a>
						<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
							<span style="text-decoration:none;color:yellow;font-size:1.15em;margin-left:8em">FRONTAL: <s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIpMaquina()"/></span>
						</s:if>
						</td>
							<td colspan="2"><a style="text-decoration:none;color:yellow;font-size:1.25em;">Última Conexión:<s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getUltimaConexionUsuario()"/><br/>&nbsp;</a></td>
						</tr>
					<tr>
						<td colspan="1" align="left" style="width:290px; text-align:left;font-size:1.15em;color:white;font-weight: bold;">
							<ul style="align:left;text-align:left">
								<li><a href="descargarManualUsuarioOegam.action" title="consultar/descargar manual">Ayuda</a> &nbsp;&nbsp; |</li>
								<li>&nbsp;&nbsp;<a href="mailto:informatica@gestoresmadrid.org" title="Enviar e-mail de contacto">Cont&aacute;ctenos</a> &nbsp;&nbsp;|</li>
								<li>&nbsp;&nbsp;<a href="<s:url action='invalidaCerrarSession.action'/>">Salir</a>|</li>
							</ul>
						</td>
						<td align="left" style="width:40px;vertical-align:center;">
							<a style="text-decoration:none;margin:2px;color:white;">Contratos:&nbsp;&nbsp;</a>
						</td>
						<td align="left" style="width: 195px; background-color: transparent; padding: 0px; margin: 0px; text-align: right">
							<s:if test="%{((@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoCambioContrato())||(#session.administradorComoEmpleadoColegiadoTemporalmente == true))&&
							((@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdUsuarioSessionBigDecimal()!=2)&&(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdUsuarioSessionBigDecimal()!=218))}">
								<s:form id="idFormularioContrato" action="CambioContrato" cssStyle="width:195px;text-align:center;padding:0px;margin:0px;font-size: 0.9em">
									<s:select
										list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getComboContratosAdmin()"
										listKey="codigo" listValue="descripcion"
										cssStyle="width:195px" name="contratoSeleccionado"
										value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoSessionCabecera()"
										onchange="cambioContrato('CambioContrato')" />
								</s:form>
							</s:if>
							<s:else>
								<s:form id="idFormularioContrato" action="Arbol" cssStyle="width:195px;text-align:center;padding:0px;margin:0px;font-size: 0.9em">
									<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getComboContratos()"
											  listKey="codigo" 
											  listValue="descripcion"
											  cssStyle="width:195px"
											  name="contratoSeleccionado"
											  value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoSessionCabecera()"
											  onchange="cambioContrato('Arbol')">
									</s:select>
								</s:form>
							</s:else>
						</td>
					</tr>
					</s:if>
					<s:else>
						<tr>
							<td colspan="3"
								style="text-decoration: none; color: white; font-size: 1.25em; font-weight: bold; height: 200px;"></td>
						</tr>
					</s:else>
				</table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function cambioContrato(action) {
			$("#idFormularioContrato").attr("action", action).submit();
		}
		initCabecera();
	</script>
</div>
<!--
<div id="dialog" style = "background-color:LightGray;">
	<s:hidden id="existeCircular" value="%{#session['circularActiva']}"/>
	<img alt="logo" src="img/logo.gif" class="logoico" /><h1 style="color:DarkRed;" >Circular OEGAM</h1>
	<br/>
	<s:textarea label="Circular" value="%{#session['textoCircular']}" cols="100" rows="41"
	style="font-size:12px; font-type:Calibri;border:0px;resize: none;" readonly="true" />

<script language="javascript" type="text/javascript">
	initDialogo();
</script>
</div>
 -->
<style type="text/css">
	textarea {
		border-color: rgb(169, 169, 169);
		overflow: auto;
		outline: none;
		margin-top: 33px;
	}
	h1 {
		text-justify: inter-word;
		text-align: center;
		margin-top: 48px;
	}
	.logoico  {
		width: 186px;
		height: 85px;
		float: left;
		overflow: auto;
	}
	.ui-widget-content {
		background-color: #D3D3D3;
	}
</style>