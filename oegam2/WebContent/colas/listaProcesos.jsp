<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script type="text/javascript">

	function activarCambio(indice) {

		var boton = document.getElementById("btnModificar" + indice);
		if (boton) {
			boton.disabled = false;
		}
	}

	function modificar(indice) {

		document.getElementById("tipoProceso").value = indice;
		document.getElementById("numero").value = document.getElementById("numero" + indice).value;
		document.getElementById("intentos").value = document.getElementById("intentos" + indice).value;
		//document.getElementById("intervalo").value = document.getElementById("intervalo" + indice).value;
		document.getElementById("horaInicio").value = document.getElementById("horaInicio" + indice).value;
		document.getElementById("horaFin").value = document.getElementById("horaFin" + indice).value;

		document.getElementById("tiempoCorrecto").value = document.getElementById("tiempoCorrecto" + indice).value;
		document.getElementById("tiempoRecuperable").value = document.getElementById("tiempoRecuperable" + indice).value;
		document.getElementById("tiempoNoRecuperable").value = document.getElementById("tiempoNoRecuperable" + indice).value;
		document.getElementById("tiempoSinDatos").value = document.getElementById("tiempoSinDatos" + indice).value;

		document.getElementById("formData").action = "modificarProcesos.action";
		document.getElementById("formData").submit();
		return true; 		
	}

	function parar(indice) {
		
		document.getElementById("tipoProceso").value = indice;
		document.getElementById("formData").action = "pararProcesoProcesos.action";
		document.getElementById("formData").submit();
		
		guardarValorPatron();
		
		return true;	
	}

	function activar(indice) {

		var fila = document.getElementById("numero" + indice).parentNode.parentNode;
		if(fila.childNodes[1].innerHTML=='Envío hoja Excel de Bajas' || fila.childNodes[1].innerHTML=='Envío hoja Excel de Duplicado'){
			d = new Date();
			if(d.getHours() >= 9){
				var mensaje = "Son las "+rellenaCerosIzqVar(d.getHours().toString(),2)+":"+rellenaCerosIzqVar(d.getMinutes().toString(),2)+". Si hubiera un fallo en la BBDD o en la validación activar este proceso \n\ra esta hora del día, puede significar un segundo envío. ¿Estás seguro de querer hacerlo?";
				if(!confirm(mensaje)) return false;
			}
		}
		
		document.getElementById("tipoProceso").value = indice;
		document.getElementById("formData").action = "arrancarProcesoProcesos.action";
		document.getElementById("formData").submit();
		
		guardarValorPatron();
		
		return true; 		
	}
	
	function actualizar() {
		document.getElementById("formData").action = "actualizarProcesos.action";
		document.getElementById("formData").submit();
		return true;
	}

	function mostrarVentana(evento, indice, lineas) {

		var targ;
		var e = evento;
		if (!evento) {
			 e = window.event;
		}
		if (e.target){
			targ = e.target;
		} else {
			targ = e.srcElement;
		}	
		document.getElementById("ventana").innerHTML = document.getElementById("proceso" + indice).innerHTML;
		document.getElementById("ventana").style.top = (128 + (24 * indice)) +"px";
		document.getElementById("ventana").style.left = "535px";
		document.getElementById("ventana").style.height = (12 * (lineas + 2)) +"px";
		document.getElementById("ventana").style.fontWeight = "bold";
		document.getElementById("ventana").style.visibility = "visible";
		
	}

	function ocultarVentana() {

		document.getElementById("ventana").style.visibility = "hidden";
		document.getElementById("ventana").innerHTML = "";
		
	}

	//AÑADE CEROS A LA IZQUIERDA A UNA VARIABLE
	function rellenaCerosIzqVar(text, numDigitos){
		if(text.length>0){
			numCeros = numDigitos - text.length;
			for(i=0;i<numCeros;i++){
				text = "0" + text;
			}
			return text;
		}
	}

	// Arranca los procesos de un patrón
	function arrancarPatron(){

		if(document.getElementById('idPatron').value!=""){

			var formulario = document.getElementById('formNodos');
	
			formulario.action = "iniciarListaProcesos.action";
	
			formulario.submit();

		} else {

			alert("Debe seleccionar algún patrón para realizar esta acción");
			
		}
		
	}
	
	// Para los procesos de un patrón
	function pararPatron(){

		if(document.getElementById('idPatron').value!=""){
		
			var formulario = document.getElementById('formNodos');
	
			formulario.action = "pararListaProcesos.action";
	
			formulario.submit();

		} else {
	
			alert("Debe seleccionar algún patrón para realizar esta acción");
			
		}
		
	}
	// Mantis 13692. David Sierra: Guardar el valor del elemento seleccionado en el combo de patrones
	function guardarValorPatron() {
		
		var patron = document.getElementById('idPatron').value;
		var patronSeleccionado = document.getElementById('idPatronSeleccionado');
		patronSeleccionado.value = patron;

		var formulario = document.getElementById('formData');
		formulario.submit();
		
	}
	
	function comprobarPassword() {
		document.formData.action = "comprobarPasswordProcesos.action";
		document.formData.submit();
	}
	
</script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Monitor del
					Gestor de Procesos</span></td>
		</tr>
	</table>
</div>

<s:if test="%{passValidado=='false' or passValidado=='error'}">
	<s:form method="post" id="formData" name="formData">
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap"><label for="idPassword">Introduzca
						la clave:</label></td>
				<td align="center" nowrap="nowrap"><input type="password"
					autocomplete="off" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idPassword" value=""
					maxlength="40" size="20" name="password">
				</td>
				<td align="left" nowrap="nowrap" colspan="2"><input
					type="button" class="boton" name="bLimpiar" id="bLimpiar"
					value="Verificar Contraseña" onkeypress="this.onClick"
					onclick="return comprobarPassword();" /></td>
			</tr>
		</table>
	</s:form>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
</s:if>

<s:if test="%{passValidado=='true' || passValidado==null}">
	<s:if test="listaProcesos.length > 0">
		<h3 class="formularios">Gestionar por patrones de procesos</h3>
		<s:form id="formNodos" name="formNodos"  action="listaFiltradaProcesos" cssClass="tablacoin">
		
			<s:select id="idPatron" label="lbpatron" onchange="this.form.submit()" headerKey="" headerValue="Seleccionar patrón"
				list="#{'todos':'TODOS','trafico':'TRÁFICO', 'matriculacion':'MATRICULACION', 'transmision':'TRANSMISIÓN',
				'justificantes':'JUSTIFICANTES', 'correo':'ENVÍO DE CORREOS', 'inteve':'INTEVE', 'gdoc':'GESTIÓN DOCUMENTAL', 
				'sensibles':'DATOS SENSIBLES', 'registro':'REGISTRO', 'aeat':'576', 'compratasas':'COMPRA TASAS', 
				'presentacion':'PRESENTACION JPT', 'santander':'SANTANDER'}" 
				name="patron" /> 					
			<input type="button" value="Arrancar patrón" onclick="arrancarPatron()"
				class="boton" />
			<input type="button" value="Parar patrón" onclick="pararPatron()"
				class="boton" />
		
		</s:form>
		
		<div id="procesosAfectados">
			<h4>Procesos que resultarán afectados</h4>
			<p id="listaProcesosAfectados"></p>
		</div>
	</s:if>
	
	<h3 class="formularios">Lista de procesos</h3>
	
	<div>
		<table align="center" style="width: 90%; padding-top: 30px;">
			<tr>
				<td style="font-weight: bold; font-size: 1.1em; text-align: left;">
					&Uacute;ltima actualización&nbsp; ${fechaActualizacion}</td>
			</tr>
		</table>
	</div>
	
	<s:form id="formData" name="formData" action="listaProcesos.action">
	
		<display:table name="listaProcesos" excludedParams="*"
			decorator="colas.decorators.GestorProcesosDecorator"
			list="listaProcesos" requestURI="listaProcesos.action"
			class="tablacoin" summary="Listado de Procesos" id="tabla"
			style="width:90%;margin-left:auto" cellspacing="0" cellpadding="0"
			uid="listaProcesosTable">mostrarVentana
	  
		<display:column property="descripcion" title="Descripci&oacute;n"
				headerClass="centro"
				style="width:30%;text-align:left;padding-left:10px;" />
		<display:column property="estado" title="Estado" headerClass="centro"
				style="width:5%;"/>
			
			<display:column property="accion" title="ACT/DES" style="text-align:center; width:10%;" 
				headerClass="centro" />
			<display:column property="modificar" title="Modificar"	
				style="text-align:center; width:10%;" />
			<display:column property="intentosMax" title="MaxInt"
				headerClass="centro" style="width:5%;" />
			<display:column property="ultimaEjecucion"
				title="&Uacute;ltima ejecución" headerClass="centro"
				style="width:5%;" />
			<display:column property="numeroProcesos" title="Hilos"
				headerClass="centro" style="width:5%;" />
			<display:column property="horaInicio" title="HoraIni"
				headerClass="centro" style="width:5%;" />
			<display:column property="horaFin" title="HoraFin"
				headerClass="centro" style="width:5%;" />
	
			<display:column property="tiempoCorrecto" title="tOk"
				headerClass="centro"  style="width:5%;" />
			<display:column property="tiempoRecuperable" title="tRecup"
				headerClass="centro" style="width:5%;" />
			<display:column property="tiempoNoRecuperable" title="tNoRecup"
				headerClass="centro" style="width:5%;" />
			<display:column property="tiempoSinDatos" title="tNoDatos"
				headerClass="centro" style="width:5%;" />
	
			
	
		</display:table>
		<s:hidden id="tipoProceso" name="tipoProceso" value="" />
		<s:hidden id="numero" name="numero" value="" />
		<s:hidden id="intentos" name="intentos" value="" />
		<s:hidden id="intervalo" name="intervalo" value="" />
		<s:hidden id="horaInicio" name="horaInicio" value="" />
		<s:hidden id="horaFin" name="horaFin" value="" />
		<s:hidden id="orden" name="orden" value="" />
	
		<s:hidden id="tiempoCorrecto" name="tiempoCorrecto" value="" />
		<s:hidden id="tiempoRecuperable" name="tiempoRecuperable" value="" />
		<s:hidden id="tiempoNoRecuperable" name="tiempoNoRecuperable" value="" />
		<s:hidden id="tiempoSinDatos" name="tiempoSinDatos" value="" />	
		<s:hidden id="idPatronSeleccionado" name="patronSeleccionado" />
	
		<table class="acciones">
			<tr>
				<td>
					<div>
						<input type="button" class="boton" name="bActualizar"
							id="bActualizar" value="Actualizar" onClick="actualizar(); guardarValorPatron();" />
					</div>
				</td>
			</tr>
		</table>
	
	</s:form>
</s:if>

<script>

$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPassword();
	    }
	});
});

window.onload=getProcesosAfectados() ; 
</script>
