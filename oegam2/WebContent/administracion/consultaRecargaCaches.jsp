<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script type="text/javascript">
	function bloqueaBotones () {
		$("#bCombos").attr("disabled", "disabled").css("color", "#6E6E6E");
		$("#bGrupos").attr("disabled", "disabled").css("color", "#6E6E6E");
	}

	function recargarCombos () {
		if (confirm("¿Está seguro de que desea refrescar los combos guardados en caché?")) {
			bloqueaBotones();
			$('#formData').attr("action", "refrescarCombos_RecargaCaches.action").trigger("submit");
		}
	}

	function recargarGrupos () {
		if (confirm("¿Está seguro de que desea refrescar los grupos y datos sensibles almacenados en caché?")) {
			bloqueaBotones();
			$('#formData').attr("action", "refrescarGrupos_RecargaCaches.action").trigger("submit");
		}
	}

	function peticionDatosSensibles() {
		if (confirm("¿Está seguro de que desea crear una peticion para refrescar los datos sensibles de todos los frontales?")) {
			bloqueaBotones();
			$('#formData').attr("action", "peticionDatosSensibles_RecargaCaches.action").trigger("submit");
		}
	}

	function peticionCombos() {
		if (confirm("¿Está seguro de que desea crear una peticion para refrescar los combos de todos los frontales?")) {
			bloqueaBotones();
			$('#formData').attr("action", "peticionCombos_RecargaCaches.action").trigger("submit");
		}
	}
</script>

<div class="nav">
	<table cellSpacing="0" cellPadding="5" width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Recarga de cachés</span></td>
		</tr>
	</table>
</div>

	<%@include file="../includes/erroresYMensajes.jspf" %>

	<div id="resultado" style="width:95%; margin:.5em 0 .5em 1em; padding:1em; font-family:tahoma, arial, sans-serif; color:#333; font-size:1.2em; font-weight:bold; text-align:justify; background:#e7e7e7;">
		Recuerde que la recarga de cachés se realiza
		a nivel de instancia. Debe ejecutar la recarga en cada frontal para
		que la actualización sea completa.
	</div>

	<div id="busqueda">
		<div id="botonBusqueda">
			<s:form id="formData" name="formData" action="#">
			<table width="100%" align="center">
				<tr align="center">
						<td>
							<input type="button" class="botonMasGrande" name="bCombos" id="bCombos" value="Refrescar combos" onkeypress="this.onClick" onclick="recargarCombos();" />
						</td>
						<td>
							<input type="button" class="botonMasGrande" name="bGrupos" id="bGrupos" value="Datos sensibles y grupos" onkeypress="this.onClick" onclick="recargarGrupos();" />
						</td>
				</tr>
				<tr>
					<td>
						<input type="submit" class="botonMasGrande" name="bPeticion" id="bGrupos" value="Peticion combos"  onkeypress="this.onClick" onclick="peticionCombos();"/>
					</td>
					<td>
						<input type="submit" class="botonMasGrande" name="bPeticion" id="bGrupos" value="Peticion datos sensibles"  onkeypress="this.onClick" onclick="peticionDatosSensibles();"/>
					</td>
				</tr>
			</table>
			</s:form>
		</div>
	</div>

<div class="" id="AltaJPT"
	style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Comprobar bastidor Dato Sensible</td>
			</tr>
		</table>

		<table width="100%" border="0" cellspacing="3" cellpadding="0"
			class="tablaformbasica">

			<tr>
				<td>
					<table style="width: 100%;">
						<tr>
							<td style="vertical-align: middle; padding-right: 2em; width: 30%; float:left !important">
								<s:form action="checkBastidor_RecargaCaches.action" style="width: auto !important">
									Bastidor: <s:textfield name="bastidorCheck"></s:textfield>
									<s:submit value="Comprobar"></s:submit>
								</s:form>
							</td>
						</tr>
						<tr>
							<td>
								<s:if test="bastidorEnBBDD != null">
									<span>Existe en Base de Datos 
										<s:if test="bastidorEnBBDD.estado == 1"> <b>(Activo)</b></s:if>
										<s:else> <b>(Inactivo)</b></s:else>
									</span>
								</s:if> 
								<s:else>
									<span>No Existe en Base de Datos</span>
								</s:else>
							</td>
						</tr>
						<tr>
							<td>
								<s:if test="existeBastidorEnCache">
									<span>Existe en Caché</span>
								</s:if> <s:else>
									<span>No Existe en Caché</span>
								</s:else>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>