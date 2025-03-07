<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Modelo 600</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Modelo 600</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExpediente">Num. Expediente.</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumExpediente" name="modeloDto.numExpediente"  onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="20" maxlength="20" disabled="true"/>
			</td>
			<s:if test="%{modeloDto.estado != null}">
				<td align="left" nowrap="nowrap">
					<label for="labelEstado">Estado: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getEstadoModelos()" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="-1" headerValue="Seleccione Estado" 
						name="modeloDto.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoModelos" disabled="true"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left">
				<label for="labelFechaDevengo">Fecha Devengo: </label>
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="idDiaDevengo" name="modeloDto.fechaDevengo.dia"
								onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event);"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="idMesDevengo" name="modeloDto.fechaDevengo.mes" 
								onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td>/</td>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="idAnioDevengo" name="modeloDto.fechaDevengo.anio"
								onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioDevengo, document.formData.idMesDevengo, document.formData.idDiaDevengo);return false;" title="Seleccionar fecha Devengo">
								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaOficina">Provincia Oficina Liquid.<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaProvincias()" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Provincia"
						name="modeloDto.oficinaLiquidadora.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaOficinaLiquid" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelOficinaLiq">Oficina Liquidadora<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select id="idOficinaLiquidadora"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="modeloDto.oficinaLiquidadora.id"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaOficinasLiquidadoras(modeloDto)"
					headerKey=""
					headerValue="Seleccione Oficina Liquidadora"
					listKey="id"
					listValue="nombreOficinaLiq" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExpediente">Ref. Propia.</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idReferenciaPropia" name="modeloDto.referenciaPropia" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" size="20" maxlength="20" />
			</td>
		</tr>
	</table>
</div>