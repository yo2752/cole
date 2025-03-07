<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/usuario.js" type="text/javascript"></script>
<script type="text/javascript">
var idCreditos = new Array();
var cont = 0;
var req;
function cancelar(){
	return cancelarForm("detalleContratos.action?idContratoSeleccion=${idContrato}");
}
function guardar(){	
	if(!validar(idCreditos, false)) {
		return false;
	}		
	document.formData.action="guardarUsuarioContratos.action";	
	document.formData.submit();
	return true;
}
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Alta del usuario</span></td>
		</tr>
	</table>
</div>
<div id="fieldError">
<s:actionmessage/>									
</div>
<s:form method="post" name="formData" id="formData">
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Alta usuario</td>
	</tr>
</table>

<table width="85%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
<tr>
	<td align="left">
	              <label for="nif">Estado Usuario</label>
        			<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstados()"
								id="estadoUsuario"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								name="estadoUsuario"
								listValue="nombreEnum"
								listKey="valorEnum"
								title="Estado Usuario"
								
								/>
        			
      </td>
	</tr>
	<tr>
		<td align="left">
			<label for="nif">NIF / NIE<span class="naranja">*</span></label>
			<s:textfield name="nifUsuario" id="nif" size="9" maxlength="9" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			<label for="apellidosNombre">Apellidos y Nombre<span class="naranja">*</span></label>
			<s:textfield name="apellidosNombreUsuario" id="apellidosNombre" cssClass="inputview"  size="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			<label for="correoElectronico">Correo electr&oacute;nico<span class="naranja">*</span></label>
			<s:textfield name="correoElectronicoUsuario" id="correoElectronico" cssClass="inputview"  size="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="numColegiado">numero Colegiado</label>
			<s:textfield name="numColegiado"  id="numColegiado" cssClass="inputview" readonly="true" size="4" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	
	
</table>



<table class="acciones">
	<tr>
		<td>
			<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Aceptar" onClick="return guardar();" onKeyPress="this.onClick"/>
			&nbsp;		
			<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Cancelar" onClick="return cancelar();" onKeyPress="this.onClick" />
		</td>
		
	</tr>
</table>
<s:hidden name="cif" id="cif"></s:hidden>
<s:hidden name="idContrato"></s:hidden>
</s:form>

