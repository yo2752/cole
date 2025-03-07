
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="formularios">
	
<script src="js/logs/logs.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<s:form method="post" id="formData" name="formData">
	<s:hidden id="tipoMaquinaSeleccionada" name="logsBeanPantalla.ficheros"/>	
	<div class="nav" style="margin-left: 0.3em">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Descarga de ficheros de Log</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda">				
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="fechaInicioDay">Fecha de log</label>
					<label for="fechaInicioMonth"></label> 
					<label for="fechaInicioYear"></label>
				</td>
				<td align="left">
					<table width="20%">
						<tr>
							<td>
								<s:textfield name="logsBeanPantalla.fechaLog.diaInicio" id="fechaInicioDay"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
							</td>
							<td>
								<label>/</label> 
							</td>
							<td>
								<s:textfield name="logsBeanPantalla.fechaLog.mesInicio"
									id="fechaInicioMonth" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>
								<label>/</label> 
							</td>
							<td>
								<s:textfield name="logsBeanPantalla.fechaLog.anioInicio"
									id="fechaInicioYear" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" />
							</td>
							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.fechaInicioYear, document.formData.fechaInicioMonth, document.formData.fechaInicioDay);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15" height="16"
									border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>	
					</table>
				</td>
				<td align="left">
					<label for="maquinaProduccion">Máquina Producción</label>
				</td>
				<td align="left">
					<s:select
						name="logsBeanPantalla.maquinasEnum" id="tipoMaquinaServidor"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboMaquinas()"
						headerKey="" headerValue="Seleccione Máquina"
						listKey="ip" listValue="maquina" 
						onChange="cargarListaFicherosLogs(this,'tipoLogFrontal','tipoMaquinaSeleccionada')"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="tipoFichero">Tipo de fichero</label>
				</td>
				<td align="left">
					<select id="tipoLogFrontal" onchange="seleccionarCampo('tipoMaquinaSeleccionada','tipoLogFrontal');">
						<option value="-1">Cualquier máquina</option>
					</select>
				</td>				
			</tr>
		</table>
		<div class="acciones center">
			<input type="button" value="Descargar" class="boton" onClick="return descargarFichero()" />
		</div>
		<%@include file="../../includes/erroresMasMensajes.jspf"%>
	</div>
</s:form>			
</div>

<script type="text/javascript">

cargarListaFicherosLogs(document.getElementById('tipoMaquinaServidor'),'tipoLogFrontal','tipoMaquinaSeleccionada');

function descargarFichero() {

	var maquina = document.getElementById("tipoMaquinaServidor");
	var fichero = document.getElementById("tipoLogFrontal");

	if (maquina==null || maquina=="" ||fichero=="" || fichero==null){
		alert("Debe seleccionar maquina y fichero a descargar");
		return false;	
	}		
		$("#divError").remove();
		document.formData.action="descargarLogs.action";
		document.formData.submit();
		return true;
}

</script>