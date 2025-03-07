
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="formularios">
	
<%-- <script src="js/tasas/tasas.js" type="text/javascript"></script> --%>
	
<s:form method="post" id="formData" name="formData">
	<div class="nav" style="margin-left: 0.3em">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Compra de tasas de DGT</span>
				</td>
			</tr>
		</table>
	</div>
   
   	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la compra</td>
		</tr>
	</table>
	
   <table cellSpacing="3" class="tablaformbasica" cellPadding="0" summary="Datos de la compra">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tasasSeleccionadas">Tasas seleccionadas:</label>	
						<s:textfield name="tasasSolicitadasBean.cantidad" 
						id="idtasasSeleccionadas" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						size="60" maxlength="100"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="totalPagar">Total a pagar:</label>	
						<s:textfield name="tasasSolicitadasBean.importe" 
						id="totalPagar" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						size="60" maxlength="100"/>
				</td>				
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="cuentaCorriente">Cuenta corriente</label>	
						<s:textfield name="tasasSolicitadasBean.numeroCuenta" 
						id="cuentaCorriente" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						size="60" maxlength="100"/>
				</td>
			</tr>
	</table>
	
	<table class="acciones">
	<tr>
		<td>
			<div>	
			    <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" 
			    	onClick="return volver();" onKeyPress="this.onClick" />
			    <input type="button" class="boton" name="bSeleccionarFormaPago" id="idBFormaPago" value="Seleccionar Forma Pago" 
					onClick="return seleccionarFormaPago();" onKeyPress="this.onClick" />
			</div>
		</td>
	</tr>
</table>
	
</s:form>
			
</div>

<script type="text/javascript">

function volver(){
	return cancelarForm("inicio${action}.action");
}

function seleccionarFormaPago() {
	
	document.formData.action="";
	document.formData.submit();
	return true;
}

</script>
