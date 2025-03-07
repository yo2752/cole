<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" type="text/css" href="css/estilosRegistradores.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
<script type="text/javascript" src="js/tabs.js" ></script>
<script type="text/javascript" src="js/registradores/registradores.js"></script>
<script type="text/javascript" src="js/general.js"></script>
<script type="text/javascript" src="js/trafico/comunes.js"></script>
<script type="text/javascript" src="js/trafico/consultaTramites.js"></script>
<script type="text/javascript" src="js/genericas.js"></script>
<script type="text/javascript" src="js/validaciones.js"></script>

<script type="text/javascript">
	function seleccionarCertificante() {
		var marcados="";
		var primero = true;
		$("input:checked[name='certificantesMarcados']").each(function() {
			if (!primero) {
				marcados+="&";
			} else {
				primero = false;
			}
			marcados+=this.name+"="+this.value;
			});
		if (!primero) {
			var numExpediente = document.getElementById('numExpediente').value;
			window.opener.document.formData.action = "seleccionarCertificantesTramiteRegistroMd1.action?"+marcados+"&numExpediente="+numExpediente+"#Certificantes";
			window.opener.document.formData.submit();
		}
		window.close();
	}
</script>

<s:form method="post" id="formData" name="formData">
	<s:hidden id ="numExpediente" name="numExpediente"></s:hidden>
	<s:hidden id ="numColegiado" name="numColegiado"></s:hidden>
	<s:hidden id ="cif" name="cif"></s:hidden>
	<s:hidden id ="tipoTramiteRegistro" name="tipoTramiteRegistro"></s:hidden>

	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	
	<div class="nav">
		<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style=""><span class="titulo">Datos del cese</span></td>
			</tr>
		</table>
	</div>
	<table border="0" class="tablaformbasicaTC" style="width:95%;" >
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="nombreC">Nombre<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="acuerdo.sociedadCargo.personaCargo.nombre" id="nombreC" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
			</td>
			<td align="right" nowrap="nowrap">
				<label for="apellido1C">apellido 1<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="acuerdo.sociedadCargo.personaCargo.apellido1RazonSocial" id="apellido1C" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
			</td>
			<td align="right" nowrap="nowrap">
				<label for="apellido2C">apellido 2:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="acuerdo.sociedadCargo.personaCargo.apellido2" id="apellido2C" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="18" maxlength="18"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<s:textfield name="acuerdo.sociedadCargo.personaCargo.correoElectronico" id="correoElectronico" size="9" maxlength="9" cssStyle="display:none;visible:false;"/>
			</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="tipoDocumento">Nif<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="acuerdo.sociedadCargo.personaCargo.nif" id="nifC" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="12" maxlength="10" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>	
			</td>
			<td align="right" nowrap="nowrap">
				<label for="cargo">Cesa como<span class="naranja">*</span>:</label>
			</td>
			<s:if test="#session.comboCargos!=null">
				<td align="left" nowrap="nowrap" colspan="5">
					<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoCargos()" 
						id="cargoC" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="acuerdo.sociedadCargo.codigoCargo" listValue="descCargo" listKey="codigoCargo" title="Cargo del certificante" headerKey="" headerValue="Seleccionar" />
				</td>
			</s:if>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
	</table>
	<div id="botonBusqueda">
		<table width="100%">
			<tr>
				<td width="50%" align="right">
					<input type="button" class="boton" value="Alta" id="botonAltaCertificante" onclick="validacionAltaCertificante();"/>
				</td>
				<td>
					<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td width="50%" align="left"> 
					<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposCese();"/>
				</td>
			</tr>
		</table>
	</div>
</s:form>
