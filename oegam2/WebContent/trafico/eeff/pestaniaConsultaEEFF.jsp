<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta EEFF</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Consulta</span>
			</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExp">Num. Expediente: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumExpediente" name="consultaEEFF.numExpediente"  onblur="this.className='input2';" 
       							onfocus="this.className='inputfocus';" size="40" maxlength="32" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelEstado">Estado:</label>
			</td>
			<td  align="left">
					<s:select id="idEstadoConEEFF"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaEEFF@getInstance().getComboEstados()"
						onblur="this.className='input2';" headerValue="Seleccione Estado"
						onfocus="this.className='inputfocus';" listKey="valorEnum" headerKey="" 
						listValue="nombreEnum" cssStyle="width:220px"
						name="consultaEEFF.estado" disabled="true"></s:select>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelCif">FIR Marca: <span class="naranja">*</span></label>
				<img id="info_Fir_Marca" src="img/botonDameInfo.gif"
						onmouseover="dameInfoSinAjax('mostrar', this.id, 'firMarca')" 
						onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idFirMarca" name="consultaEEFF.firMarca"  onblur="this.className='input2';" 
       							onfocus="this.className='inputfocus';" size="40" maxlength="32"/>
			</td>
			<td align="left">
				<label for="labelFirCif">FIR CIF: <span class="naranja">*</span></label>
				<img id="info_Fir_Cif" src="img/botonDameInfo.gif"
					onmouseover="dameInfoSinAjax('mostrar', this.id, 'firCif')" 
					onmouseout="dameInfoSinAjax('ocultar', this.id, '')" class="botonesInfo" />
			</td>
			<td align="left">
				<s:textfield name="consultaEEFF.firCif" id="idFirCif" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" size="16" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelConcesionarioNombre">Nombre Concesionario </label>
			</td>
			<td align="left">
				<s:textfield name="consultaEEFF.nombreRepresentado" id="idNombreRepresentado" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" size="40" maxlength="50"/>
			</td>
			<td align="left">
				<label for="labelConcesionarioCIF">CIF Concesionario: <span class="naranja">*</span></label>
			</td>
			<td align="left">
				<s:textfield name="consultaEEFF.nifRepresentado" id="idNifRepresentado" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" size="16" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNive">NIVE: <span class="naranja">*</span></label>
			</td>
			<td align="left">
				<s:textfield name="consultaEEFF.tarjetaNive" id="idNiveEEFF" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" size="40" maxlength="32"/>
			</td>
			<td align="left">
				<label for="labelBastidor">NºBastidor: <span class="naranja">*</span></label>
			</td>
			<td align="left">
				<s:textfield name="consultaEEFF.tarjetaBastidor" id="idBastidorEEFF" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" size="30" maxlength="21"/>
			</td>
		</tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelContrato">Contrato:</label>
				</td>
				<td  align="left">
						<s:select id="idContrato"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaEEFF@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato"
							onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:220px"
							name="consultaEEFF.contrato.idContrato"></s:select>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden name="consultaEEFF.contrato.idContrato"/>
		</s:else>
	</table>
</div>