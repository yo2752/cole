<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/pegatinasBotones.js" type="text/javascript"></script>

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">PEDIR STOCK DISTINTIVOS MEDIOAMBIENTALES</span>
				</td>
			</tr>
		</table>
	</div>
	<s:hidden id ="idJefaturaJpt" name="jefaturaJpt" value="%{jefaturaJpt}"/>
	<s:form  method="post" id="formData2" name="formData2">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tipo">Tipo:</label>
						<s:select
							list="@org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas@getTipoPegatinasPedirStock()"
							id="tipo"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="pegatinasStockPeticionesBean.tipo"
							listValue="nombreEnum"
							listKey="valorEnum"
							title="Estado"
							disabled="false"
							id="tipoPegatina"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="numPegatinas">Número de distintivos:<span class="naranja">*</span></label>
					<s:textfield id="numPegatinas" name="pegatinasStockPeticionesBean.numPegatinas"  onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="5"/><span class="naranja"> (Múltiplo de 150)*</span>
				</td>
			</tr>
		</table>
	</s:form>

<style>
	.leftButton {
		margin-left: 0px !important;
	}

	.rightButton {
		margin-right: 350px !important;
	}
</style>