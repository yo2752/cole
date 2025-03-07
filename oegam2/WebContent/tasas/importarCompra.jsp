<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/genericas.js" type="text/javascript"></script>

<s:form method="post" name="formData" id="formData" enctype="multipart/form-data">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Importar compra de tasas DGT</span></td>
			</tr>
		</table>
	</div>
	<%@include file="../includes/erroresMasMensajes.jspf"%>
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Seleccione el fichero a importar</td>
		</tr>
	</table>	
	<div id="busqueda">
		<table class="tablaformbasica">
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<tr>
					<td  align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="contrato">Contrato:</label>
					</td>
					<td align="left">
							<s:select id="contrato"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:308px"
								value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal()"
								name="resumenCompraBean.idContrato"></s:select>
					</td>
				</tr>
			</s:if>
			<tr>
				<td  align="left" nowrap="nowrap" style="width:10%;">
					<label for=ficheroTasas>Fichero de tasas:</label>
				</td> 
				<td align="left">
					<s:file id="ficheroTasas" size="50" name="ficheroTasas"
						value="ficheroTasas" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" style="width:10%;">
					<label for=ficheroJustificante>Fichero de justificante de pago:</label>
				</td> 
				<td align="left">
					<s:file id="ficheroJustificante" size="50" name="ficheroJustificante"
						onchange="fileSelected(this)"
						value="ficheroJustificante" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" />
				</td>
			</tr>
		</table>
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del pago</td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td  align="left" nowrap="nowrap">
					<label for="refPropia">Referencia propia:</label>
				</td>
				<td align="left">
					<s:textfield size="22" maxlength="50" id="refPropia" name="resumenCompraBean.refPropia" />
				</td>
			</tr>
			<tr>
				<td  align="left" nowrap="nowrap" >
					<label for="numJustificante791">N&uacute;m. justificante 791:</label>
				</td>
				<td align="left">
					<s:textfield size="22" maxlength="13" name="resumenCompraBean.numJustificante791"  id="numJustificante791" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="nrc">NRC:</label>
				</td>
				<td align="left">
					<s:textfield size="22" maxlength="22" name="resumenCompraBean.nrc"  id="nrc" />
				</td>
			</tr>
		</table>
	</div>
	<div id="botonesImportarTasas">
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Enviar"  onkeypress="this.onClick" onclick="enviarImportacionCompra()"/>			
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
</s:form>
<script>
	function fileSelected(file) {
		var justificante791 = nrc = '';
		if (file.value.length > 0) {
			var fileName = file.files[0].name;
			if (fileName.includes(".")) {
				var name = fileName.substring(0, fileName.lastIndexOf("."));
				if (name.length == 31) {
					justificante791 = name.substring(9, 22);
					nrc = name.substring(9, 31);
				}
			}
		}
		$("#numJustificante791").val(justificante791);
		$("#nrc").val(nrc);
	}

	function enviarImportacionCompra() {
		if ($("#ficheroTasas").val().length < 1) {
			alert("Debe seleccionar un fichero de tasas");
			return;
		}
		var nJustificante971 = $("#numJustificante791").val();
		if (nJustificante971.length != 13) {
			alert("Número de justificante 791 no válido");
			return;
		}
		var nrc = $("#nrc").val();
		if (nrc.length != 22) {
			alert("NRC no válido");
			return;
		}
		if (!nrc.startsWith(nJustificante971)) {
			alert("El número de justificante 791 no concuerda con el NRC");
			return;
		}
		var fjustificante791 = fnrc = '';
		var fileName = $("#ficheroTasas")[0].files[0].name;
		if (fileName.includes(".") && ".txt" == fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) {
			var name = fileName.substring(0, fileName.lastIndexOf("."));
			if (name.length == 31) {
				fjustificante791 = name.substring(9, 22);
				fnrc = name.substring(9, 31);
			}
		} else {
			alert("La importacion de compra de tasas necesita el fichero de tasas con extensión .txt");
			return;
		}

		/* Mantis 23689: Por requisitos se eliminan estas comprobaciones 
		if (nJustificante971 != fjustificante791 ) {
			if (!confirm("El numero de justificante aportado no coincide con el del fichero, ¿desea continuar de todos modos?")) {
				return;
			}
		}

		if (nrc != fnrc ) {
			if (!confirm("El NRC aportado no coincide con el del fichero, ¿desea continuar de todos modos?")) {
				return;
			}
		}
		
		*/

		if ($("#ficheroJustificante").val().length > 0) {
			var fileNameJustificante = $("#ficheroJustificante")[0].files[0].name;
			if (fileNameJustificante.includes(".") && ".pdf" == fileNameJustificante.substring(fileNameJustificante.lastIndexOf(".")).toLowerCase()) {
				if (fileName.substring(0, fileName.lastIndexOf(".")) != fileNameJustificante.substring(0, fileNameJustificante.lastIndexOf("."))) {
					/* Mantis 23689: Por requisitos se eliminan estas comprobaciones 
					if (!confirm("El nombre de los ficheros de tasas y justificante del pago no coinciden, ¿desea continuar de todos modos?")) {
						return false;
					}
				*/
				}
			} else {
				alert("El fichero con el justificante del pago debe ser un .pdf");
				return;
			}
		}
		doPost('formData', 'realizarImportarCompra${action}.action');
	}
</script>
