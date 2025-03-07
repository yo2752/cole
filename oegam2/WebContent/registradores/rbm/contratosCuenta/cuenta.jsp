<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Cuenta" >

	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Datos cuenta</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  			onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="ejercicioCuenta" >Ejercicio cuenta:<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegistro.ejercicioCuenta" id="ejercicioCuenta" size="18" maxlength="255"></s:textfield></td>

			<td align="left" nowrap="nowrap"><label for="claseCuenta" >Clase cuenta:<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegistro.claseCuenta" id="claseCuenta" size="39" maxlength="255"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="right" nowrap="nowrap" width="5%">
				<label for="referenciaPropia">Referencia Propia:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteRegistro.refPropia" id="referenciaPropia" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="47" maxlength="50"/>
			</td>
			<td colspan="2">
				<table align="left">
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<label for="aplicaIrpf">Aplicar IRPF:</label>
						</td>
						<td align="left" nowrap="nowrap">
								<s:select label="aplicaIrpf" name="tramiteRegistro.aplicarIrpf" id="aplicaIrpf" 
									list="#{'N':'-','S':'SI', 'N':'NO'}" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									required="true" onchange="mostrarIrpf();"/>
						</td>
						
						<td align="left" nowrap="nowrap" id="labelPorcentajeIrpf">
							<label for="porcentajeIrpf">Porcentaje IRPF:<span class="naranja">*</span></label>
						</td>
						<td><s:textfield name="tramiteRegistro.porcentajeIrpf" id="porcentajeIrpf" size="14"  maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"/></td>
					</tr>
				</table>
			</td>
		</tr>	
		
	 </table>
	 
  &nbsp; &nbsp;
  <div class="acciones center">	
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<input type="button" value="Guardar" class="boton" id="botonGuardar" onclick="javascript:doPost('formData', 'guardarCuenta.action\u0023Cuenta');"/>
	</s:if>
  </div>
		
</div>

<script type="text/javascript">
	$(document).ready(function(){
		mostrarIrpf();
	});
</script>

