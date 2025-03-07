<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tipoCredito.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script type="text/javascript">
	function guardar() {
		if (!validar()) {
			return false;
		}
		<s:if test="!nuevoTipoCredito">
		if (!confirm("¿Realmente desea modificar el tipo de crédito?")) {
			return false;
		}
		</s:if>
		document.formData.action="guardarTipoCredito.action";
		document.formData.submit();
		return true;
	}
	function cancelar() {
		return cancelarForm("buscarTipoCredito.action");
	}
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				<s:if test="nuevoTipoCredito">Nuevo Tipo de Cr&eacute;dito</s:if>
				<s:else>Modificar Tipo de cr&eacute;dito</s:else>
			</span></td>
		</tr>
	</table>
</div>

<s:form method="post" name="formData" id="formData">
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del tipo de cr&eacute;dito</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	<table width="85%" border="0" cellspacing="0" cellpadding="5" class="tablaformbasica">
		<tr>
			<s:if test="nuevoTipoCredito">
				<td align="left">
					<label for="tipoCredito">Tipo de cr&eacute;dito:<span class="naranja">*</span></label>
					<s:textfield name="tipoCreditoDto.tipoCredito" id="tipoCredito" 
									onblur="this.value=this.value.toUpperCase(); this.className='input2';"
									onfocus="this.className='inputfocus';" size="6" maxlength="4"
									onkeyup="this.value=this.value.toUpperCase()"/>
				</td>
			</s:if>
			<s:else>
				<td align="left">
					<label for="tipoCredito">Tipo de cr&eacute;dito:<span class="naranja">*</span></label>
					<s:textfield name="tipoCreditoDto.tipoCredito" id="tipoCredito"
									 size="6" maxlength="4" readonly="true"/>
				</td>
			</s:else>
		</tr>
		<tr>
			<td align="left">
				<label for="importe">Importe:<span class="naranja">*</span></label>
				<s:textfield name="tipoCreditoDto.importe" id="importe" onblur="this.className='input2';remplazarComas('importe');" onfocus="this.className='inputfocus';" size="18" maxlength="18" />
			</td>
		</tr>
		<tr>
			<td align="left">
				<label for="descripcion">Descripci&oacute;n:<span class="naranja">*</span></label>
				<s:textfield name="tipoCreditoDto.descripcion" id="descripcion" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="100" maxlength="75" onkeyup="this.value=this.value.toUpperCase()"/>
			</td>
		</tr>
		<tr>
			<td align="left">
				<label for="increDecre">Incremental / Decremental:</label>
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboIncreDecre()"
								id="incre_decre"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								name="tipoCreditoDto.increDecre"
								listValue="nombreEnum"
								listKey="valorEnum"
								title="IncreDecre"
								/>
			</td>
		</tr>
	</table>
	<s:if test="!nuevoTipoCredito">

		<s:hidden name="tipoCreditoDto.estado"></s:hidden>
		<s:hidden name="tipoCreditoDto.ordenListado"></s:hidden>

		<table class="subtitulo" cellSpacing="0" cellspacing="0" summary="Tramites">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt="-" /></td>
				<td>Tipos de Tr&aacute;mite Que Usan Los Créditos</td>
			</tr>
		</table>
		<display:table name="listaTiposTramitesCreditos" excludedParams="*"
			class="tablacoin"
			summary="Listado de TramiteCredito" style="width:99%"
			cellspacing="0" cellpadding="0" uid="listaTramites">

			<display:column property="tipoTramite" title="Tipo de Trámite"
				sortable="false" headerClass="sortable"
				defaultorder="descending"/>

			<display:column property="aplicacion.descAplicacion" title="Código de Aplicación"
				sortable="false" headerClass="sortable"
				defaultorder="descending"/>

			<display:column property="descripcion" title="Descripción"
				sortable="false" headerClass="sortable"
				defaultorder="descending" />

			<display:column title="">
				<input type="checkbox" name="checkTramitesAsignados"
							value="${listaTramites.tipoTramite}"
							class="checkbox"
							<s:if test="%{@org.gestoresmadrid.oegamCreditos.view.utiles.UtilesVistaCredito@getInstance().chequearTipoTramite(#attr.listaTramites.tipoTramite, tipoCreditoDto.tipoCredito)}">
								checked=checked
							</s:if>
							>
			</display:column>
		</display:table>
	</s:if>

	<table class="acciones">
		<tr>
			<td>
				<input type="button" class="boton" name="bGuardar" id="bGuardar" value="Guardar" onClick="return guardar();" onKeyPress="this.onClick" />
				&nbsp;
				<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Cancelar" onClick="return cancelar();" onKeyPress="this.onClick" />
			</td>
		</tr>
	</table>
	<s:hidden name="nuevoTipoCredito"></s:hidden>

</s:form>