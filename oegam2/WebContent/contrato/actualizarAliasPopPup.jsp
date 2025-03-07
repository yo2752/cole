<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script src="js/contrato.js" type="text/javascript"></script>

	<div class="popup formularios">
		<div id="busqueda">
			<table width="100%" >
				<tr>
					<td class="tabular" align="left">
						<span class="titulo">ACTUALIZAR ALIAS</span>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelAlias">Alias:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="alias" id="idAliasPopPup" size="15" maxlength="20" 
								onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label for="labelFechaPopPup" >Fecha caducidad </label>
					</td>
					<td>
						<table width="40%">
							<tr>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.diaInicio" id="diaFechaCaducidadIniPopPup" onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.mesInicio" id="mesFechaCaducidadIniPopPup" onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" readonly="false" size="2" maxlength="2"/>
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.anioInicio" id="anioFechaCaducidadIniPopPup" onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" readonly="false" size="5" maxlength="4"/>
								</td>
								<td align="left">
										<a href="javascript:;" onClick="if(self.fechaAlias)fechaAlias.fPopCalendarSplit(anioFechaCaducidadIniPopPup, mesFechaCaducidadIniPopPup, diaFechaCaducidadIniPopPup);resizeMeFechaPopUp();return false;" 
		    								title="Seleccionar fecha">
		    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" 
		    								width="15" height="16" border="0" alt="Calendario"/>
		    							</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
		</div>
	</div>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js:fechaAlias" 
		id="gToday:normal:agenda.js:fechaAlias" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>

