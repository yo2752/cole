<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script  type="text/javascript"></script>
	
	
<%@include file="../../includes/erroresMasMensajes.jspf"%>
<s:form method="post" id="formData" name="formData">
	<s:hidden id="idContratoPreferencias" name="contratoPreferencias.idContrato" value="%{contratoPreferencias.idContrato}"/>
	<s:hidden id="documentoBasePreferencias" name="contratoPreferencias.ordenDocbaseYb" value="%{contratoPreferencias.ordenDocbaseYb}"/>
	<div class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Añada un segundo destinatario para las Notificaciones Seguridad Social</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda" align="left">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<s:label for="email">Email:</s:label>
				</td>
				<td align="left">
					<s:textfield id="email" name="contratoPreferencias.otrosDestinatariosSS"  onblur="this.className='input2';" 
				       				onfocus="this.className='inputfocus';" size="40" maxlength="500"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" value="Guardar" class="boton" onclick="guardarPreferencias();" />
		<input type="button" value="Eliminar Destinatarios" class="botonGrande" onclick="eliminarDestinatario();" />
	</div>			
</s:form>


<script type="text/javascript">
	function guardarPreferencias(){
		if (validaEmail()){
			document.getElementById('formData').action = 'guardarDestinatarioPreferenciasSeguridadSocial.action';
			document.getElementById('formData').submit();
		}
	}
	
	function eliminarDestinatario(){
		document.getElementById('formData').action = 'eliminarDestinatarioPreferenciasSeguridadSocial.action';
		document.getElementById('formData').submit();
	}
	
	function validaEmail() {
		var str = document.getElementById("email").value;
	    var re  = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    if (!re.test(str)) {  
	    	alert("El email introducido no es v\u00e1lido.\nPor favor repase los datos.");
			return false;
	    }
	    return true;
	}
	
</script>