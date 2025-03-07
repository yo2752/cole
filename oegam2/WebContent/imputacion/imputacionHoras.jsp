<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/colas.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<%@include file="../../includes/erroresYMensajes.jspf" %>
<s:form method="post" id="formData" name="formData">
	<s:hidden id="resultadosPorPagina" name="resultadosPorPagina"/>
	<div class="subtitulo">
		<table style="width:100%">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td colspan="4">Imputación Horas</td>
			</tr>
		</table>
	</div>
	<div class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular" >
					<span class="titulo">DATOS DEL USUARIO</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="tablaForm" id="dfUsuario" aligN="left" >
		<table class="tablaformbasica">
			<tr>
				<s:if test="esAdmin">
					<td>
						<label for="labelUsuario" >Usuario: </label>
					</td>
					<td>
						<s:select 
							name="imputacionHorasBean.idUsuario" 
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getListaUsuariosColegio()" 
							listKey="idUsuario" 
							listValue="apellidosNombre">
						</s:select>
					</td>
				</s:if>
				<s:else>
					<td align="left">
						<label for="labelUsuario" >Usuario: </label>
					</td>
					<td align="left">
						<s:label value="%{usuario.apellidosNombre}" />
						<s:hidden name="usuario.apellidosNombre" id="descUsuario" />
					</td>
					<td align="left">
						<label for="labelNif" >NIF: </label>
					</td>
					<td align="left">
						<s:label value="%{usuario.nif}" />
						<s:hidden name="usuario.nif" id="descNif" />
					</td>
				</s:else>
			</tr>
		</table>
	</div>
	<div class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular" >
					<span class="titulo">DATOS DE LA IMPUTACIÓN</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="tablaForm" id="dfImputacion" aligN="left" >
		<table class="tablaformbasica">
			<s:if test="!esAdmin" >
				<tr>
					<td  align="left" nowrap="nowrap" style="width:15%;">
						<label for="labelTpImputacion" >Tipo de Imputación: </label>
					</td>
					<td align="left">
						<s:select id="tipoImputacion" 
		       				list="@escrituras.utiles.UtilesVista@getInstance().getTipoImputacion()" 
		       				onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';" 
		       				onChange="showHideDescripcion();"
		       				headerKey="-1"
		       				headerValue="Selecciona Tipo de Imputación"
		       				listKey="idTipoImputacion" 
		       				listValue="descCorta" 
		       				cssStyle="width:170px" 
		 					name="imputacionHorasBean.idTipoImputacion">
		 				</s:select>
					</td>
				</tr>
				<tr id="ulDescripcion" style="display:none">
					<td>
						<label for="labelDescTPImputacion" >Descripcion: </label>
					</td>
					<td>
						<s:textfield name="imputacionHorasBean.descImputacion" id="descTipoImp" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
								 size="50" maxlength="50"/>
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="labelFecha" >Fecha: </label>
				</td>
				<td align="left">
					<table width="30%">
						<tr>
							<td align="left">
								<s:textfield name="imputacionHorasBean.fecha.diaInicio" id="dia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label>/</label>
							</td>
							<td align="left">
								<s:textfield name="imputacionHorasBean.fecha.mesInicio" id="mes" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									readonly="false" size="2" maxlength="2"/>
							</td>
							<td align="left">
								<label>/</label>
							</td>
							<td align="left">
								<s:textfield name="imputacionHorasBean.fecha.anioInicio" id="anio" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									readonly="false" size="4" maxlength="4"/>
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anio, document.formData.mes, document.formData.dia);return false;" 
									title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left"src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				   				</a>
							</td>
						</tr>
					</table>
				</td>
				<s:if test="!esAdmin" >
					<td>
						<label for="labelHora" >Horas: </label>
					</td>
					<td>
						<s:textfield name="imputacionHorasBean.horas" id="horasImputadas" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
							readonly="false" size="2" maxlength="4" />
					</td>
				</s:if>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<s:if test="!esAdmin" >
			<input type="button" value="Guardar" class="boton" onClick="return guardarImputacion();" />
		</s:if>
		<input type="button" value="Consultar" class="boton" onClick="return consultarImputaciones();" />
	</div>	
	<br/>
	<div class="subtitulo">
		<table style="width:100%">
			<tr>
				<td style="width:100%;text-align:center;">Resumen de las Horas Imputadas</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		});
	</script>	
	<div id="displayTagDiv" class="divScroll" style="width:100%;">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*"
		 	requestURI="navegar${action}"
			class="tablacoin"
			uid="tablaImputaciones"
			summary="Listado de Imputaciones"
			cellspacing="0" cellpadding="0"
			decorator="${decorator}"
			varTotals="totalHoras">
			
	 		<display:column property="descImputacion" title="Tipo Imputacion"
				sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%;" />
				
			<display:column sortProperty="fechaImputacion" property="fecha" title="Fecha"
				sortable="true" headerClass="sortable"
				defaultorder="descending" maxLength="15" style="width:4%" />
				
			<display:column property="horas"  title="Horas"
				sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%" total="true"/>
				<s:if test="!esAdmin" >	
					<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaTramite(this, \"listaPorImputacion\");' onKeyPress='this.onClick' />" 
						style="width:1%; padding-right:1.3em" > 	
						<table align="center">
							<tr>
								<td style="border: 0px;">
									<input type="checkbox" name="listaPorImputacion" id="listaPorImputacion" value="<s:property value="#attr.tablaImputaciones.idImputacionHoras"/>" />
								</td>
							</tr>
						</table>
					</display:column>
				</s:if>	
			<display:footer>
				<tr>
					<td style="font-weight: bold;">Total Horas:</td>
					<td></td>
					<td><c:out value="${totalHoras.column3}" /></td>
				</tr>
			</display:footer>
		</display:table>
	</div>
	<s:if test="!esAdmin && lista != null && lista.getFullListSize() > 0">
		<div class="acciones center">
			<input type="button" class="boton" name="bBorrar" id="bBorrar" value="Borrar"  onclick="borrarImputaciones();"/>
		</div>
	</s:if>
</s:form>


<script>
	function guardarImputacion(){
		
		idTipoImputacion = document.getElementById('tipoImputacion').value;

		fechaDia = document.getElementById('dia').value;
		fechaMes = document.getElementById('mes').value;
		fechaAnio = document.getElementById('anio').value;

		horasImputadas = document.getElementById('horasImputadas').value;

		if(idTipoImputacion == null){
			alert("Debe seleccionar un Tipo de Imputación.");
		}else if(fechaDia == null || fechaMes == null || fechaAnio == null){
			alert("Debe rellenar por completo la fecha.");
		}else if(horasImputadas == null){
			alert("Debe de introducir las horas a imputar.");
		}else if(horasImputadas > 8 ){
			alert("Las horas a imputar no pueden ser superior a 8 horas diarias.");
		}else if(horasImputadas <= 0 || isNaN(horasImputadas) ){
			alert("Debe introduccir un número valido");
		}else{
			document.formData.action = "guardarImputacionHoras.action";
			document.formData.submit();
		}
		
		
	}

	function ponerDescripcionCompleta(){
		
		idTipo = document.getElementById('tipoImputacion').value;

		url = "obtenerDescripcionImputacionHoras.action?idTipoImputacion="+idTipo;

		//Hace la llamada a ajax
		if (window.XMLHttpRequest){ // Non-IE browsers
			req = new XMLHttpRequest();
			req.onreadystatechange = rellenarDescripcion;
			req.open("POST", url, true);
			
			req.send(null);
		} else if (window.ActiveXObject) { // IE      
			req = new ActiveXObject("Microsoft.XMLHTTP");
			if (req) {
				req.onreadystatechange = rellenarDescripcion;
				req.open("POST", url, true);
				req.send();
			}
		}
	}

	function rellenarDescripcion(){

		if (req.readyState == 4) { // Complete
			if (req.status == 200) { // OK response
				textToSplit = req.responseText;

				if(textToSplit != "null"){
					document.getElementById('descTipoImp').value = textToSplit;
				}else{
					document.getElementById('descTipoImp').readonly = false;
				}
			}
		}
	}

	function showHideDescripcion(){
		var estado = document.getElementById("tipoImputacion");
		if(estado != null){
			if (estado == 4){
				document.getElementById("ulDescripcion").style.display = "";
			}else{
				document.getElementById("descTipoImp").value = "";
				document.getElementById("ulDescripcion").style.display = 'none';
			}
		}
	}

	function borrarImputaciones(){
		$('#formData').attr("action","borrarImputacionHoras.action");
		$('#formData').submit();
	}

	function consultarImputaciones(){
		$('#formData').attr("action","consultarImputacionHoras.action");
		$('#formData').submit();
	}

	
</script>

<script type="text/javascript">
	showHideDescripcion();
</script>