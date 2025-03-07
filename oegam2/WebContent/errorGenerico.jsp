<%@page import="org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean"%>
<%@ page isErrorPage="true" %>
<%@ page import="utilidades.logger.LoggerOegam, java.net.InetAddress, java.text.SimpleDateFormat,utilidades.web.GestorArbol,utilidades.mensajes.Claves,oegam.constantes.ConstantesSession, java.io.StringWriter,java.io.PrintWriter"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set var="opcMenu" value="%{null}"/>

<br/><br/><br/><br/><br/>

	<div class="rbroundbox">
		<div class="rbtop">
			<div></div>
		</div>
		<div class="rbcontent" align="center">
		<br><br>
			<table>
					<tr><td><ul><li>Ha ocurrido un error inesperado, disculpe las molestias. Vuelva a intentarlo m&aacute;s tarde, si el error persiste, pongase en contacto con el administrador del sistema.</li></ul></td>
				<tr>
					<td align="left"><s:actionerror/></td>
				</tr>
			</table>
			<br><br>
			<input type="button" class="boton" name="bReiniciar" id="bReiniciar"
				value="Volver" onClick="javascript:volverRaiz();" onKeyPress="this.onClick" />

			<input type="button" class="boton" name="bEnviar" id="bEnviar"
				value="Notificar error" onClick="javascript:sendMailClientSide();" onKeyPress="this.onClick" />
		</div><!-- /rbcontent -->
			<div class="rbbot">
				<div></div>
			</div>
	</div><!-- /rbroundbox -->
<script type="text/javascript">
function sendMailClientSide() {
	<%
		exception = (Exception)request.getAttribute("exception");
		LoggerOegam.getLogger("file").error("Error generico no controlado", exception);
		StringBuffer sb = new StringBuffer();
		try {
			String numColegiado = "";
			GestorArbol ga = ((GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL));
			if (ga != null) {
				numColegiado = ga.getUsuario().getNum_colegiado();
			} else {
				UsuarioAccesoBean ussarioAcceso = ((UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO));
				if (ussarioAcceso != null) {
					numColegiado = ussarioAcceso.getNumColegiado();
				}
			}
			if (!("").equals(numColegiado) && null != numColegiado) {
				sb.append("Número de colegiado: ").append(numColegiado).append(" %0D%0A");
			}
		} catch (Exception e3) {}
		try {
			String expediente = (String) Claves.getObjetoDeContextoSesion(ConstantesSession.NUM_EXPEDIENTE);

			if (!("").equals(expediente) && null != expediente){
				sb.append("Expediente: ").append(expediente).append(" %0D%0A");	
			}
		}catch(Exception e4){}
		sb.append("Exception message: ").append(exception!=null?exception.getMessage():"none").append(" %0D%0A");
		try{ sb.append("Frontal: ").append(InetAddress.getLocalHost().toString().replaceFirst(".*/", "")).append(" %0D%0A"); }catch(Exception e){}
		sb.append("Timestamp: ").append((new SimpleDateFormat("dd/MM HH:mm:ss")).format(System.currentTimeMillis())).append(" %0D%0A");
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			String stackTraceString = sw.toString();
			sb.append("StackTrace: %0D%0A");
			if (stackTraceString.length()>1200) {
				sb.append(stackTraceString.substring(0, 1200).replaceAll("[\n\r]"," ")).append(" %0D%0A ... %0D%0A");
			} else {
				sb.append(stackTraceString.replaceAll("[\n\r]","")).append(" %0D%0A");
			}
		} catch(Exception e2) {}
	%>
	var dest = "informatica@gestoresmadrid.org";
	var subj = "Error genérico";
	var body = "<%=sb.toString().replaceAll("\"" , "\\'") %>";

	var linkMailto = dest+"?subject="+subj+"&body="+body;
	document.location.href = "mailto:"+linkMailto;
}

function volverRaiz(){
	window.location="/oegam2";
}
</script>