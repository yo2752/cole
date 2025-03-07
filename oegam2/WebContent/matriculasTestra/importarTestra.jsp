<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/genericas.js" type="text/javascript"></script>

<s:form method="post" name="formData" id="formData" enctype="multipart/form-data">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Importar Datos TESTRA</span></td>
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
			<tr>
				<td  align="left" nowrap="nowrap" style="width:15%;">
					<label for=ficheroTestra>Fichero TESTRA:</label>
				</td> 
				<td align="left">
					<s:file id="ficheroTestra" size="40" name="ficheroTestra"
						onchange="fileSelected(this)"
						value="ficheroTestra" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" />
				</td>
				<td width="50%" align="center">
					<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Enviar"  onkeypress="this.onClick" onclick="enviarImportacionTestra()"/>			
				</td>
			</tr>
		</table>
	</div>
	<div id="espera">
		<table class="tablaformbasica">
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
	<s:if test="%{listaConsulta != null && !listaConsulta.isEmpty()}">
	<display:table name="listaConsulta" excludedParams="*" class="tablacoin"
			uid="tablaConsultas" summary="Datos a importar" cellspacing="0"
			cellpadding="0">

			<display:column property="dato" title="Dato"
				 maxLength="20"
				style="width:4%" />

			<display:column title="Tipo"
					style="width:2%" >
					<s:if test="%{#attr.tablaConsultas.id == null}">
						<s:select name="%{'listaConsulta['+(#attr.tablaConsultas_rowNum -1)+'].tipo'}"
							list="@org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoConsultaTestraEnum@values()"
							listKey="clave" listValue="descripcion"
							headerKey="" headerValue="Tipo de dato"
							value="%{#attr.tablaConsultas.tipo}"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							/>
					</s:if>
					<s:else>
						<input type="hidden" name='listaConsulta[<s:property value="#attr.tablaConsultas_rowNum -1"/>].tipo' value='<s:property value="#attr.tablaConsultas.tipo"/>'>
						<s:property 
							value="%{@org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoConsultaTestraEnum@convertirTexto(#attr.tablaConsultas.tipo)}" />
					</s:else>
			</display:column>	
			
			<display:column property="correoElectronico" title="Correo electrónico"
				 maxLength="30"
				style="width:4%" />
				

			<display:column
				title="Importar"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;">
						<input type="hidden" name='listaConsulta[<s:property value="#attr.tablaConsultas_rowNum -1"/>].id' value='<s:property value="#attr.tablaConsultas.id"/>'>
						<input type="hidden" name='listaConsulta[<s:property value="#attr.tablaConsultas_rowNum -1"/>].dato' value='<s:property value="#attr.tablaConsultas.dato"/>'>
						<input type="hidden" name='listaConsulta[<s:property value="#attr.tablaConsultas_rowNum -1"/>].correoElectronico' value='<s:property value="#attr.tablaConsultas.correoElectronico"/>'>
					<s:if test="%{#attr.tablaConsultas.id == null}">
						<s:if test="%{#attr.tablaConsultas.activo == 1}">
							<input type="checkbox"
								name="listaConsulta[<s:property value="#attr.tablaConsultas_rowNum -1"/>].activo" checked="checked"
								value='1' />
						</s:if>
						<s:else>
							<input type="checkbox"
								name="listaConsulta[<s:property value="#attr.tablaConsultas_rowNum -1"/>].activo"
								value='1' />
						</s:else>
					</s:if>
						</td>
					</tr>
				</table>
			</display:column>

		</display:table>
		
		<div id="botonesGuardarImportacionTestra">
			<table class="tablaformbasica">
				<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
	
						<td  align="left" nowrap="nowrap" style="width:15%;"> 
							<label for="numColegiado">Num Colegiado:</label>
						</td>
						<td align="left">
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px"
								name="testraViewBean.numColegiado"></s:select>
						</td>
				</s:if>
				<s:else>
					<td width="50%">&nbsp;</td>
				</s:else>
					<td align="center" width="50%">
						<input type="button" class="boton" name="bGuardar" id="bGuardar" value="Guardar"  onkeypress="this.onClick" onclick="altaImportacionTestra()"/>			
					</td>
				</tr>
			</table>
		</div>
	</s:if>
</s:form>
<script>

	function enviarImportacionTestra() {
		var fileName = $("#ficheroTestra")[0].files[0].name;
		if (!fileName.includes(".") || ".txt" != fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) {
			alert("La importacion de datos TESTRA necesita un fichero con extensión .txt");
			return;
		}
		doPost('formData', 'lecturaImportacionConsultaMatriculaTestra.action');
	}


	function altaImportacionTestra() {
		doPost('formData', 'altaImportacionConsultaMatriculaTestra.action');
	}
	
	
	
</script>
