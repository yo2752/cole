<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" theme="simple" action="Favoritos.action">

<div class="nav" style="margin-left: 0.3em">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Favoritos</span>
				</td>
			</tr>
		</table>
</div>

<div id="busqueda">	

<table class="tablaformbasica">
	<tr>
		<td align="left" nowrap="nowrap"
			style="padding-top: .75em; vertical-align: middle"><label
			for="idFrontal">Módulo: </label>
		</td>			
		<td align="left" style="padding-top: .5em; vertical-align: middle">
			<s:select list="listaModulos" id="idModulo" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" headerKey=""
				listKey="name" listValue="title"
				headerValue="Seleccione un módulo" name="favoritosBean.codigoAplicacion"
				onChange="cargarListaDescFuncion(this.value)"/>
		</td>
		
		<td align="left" nowrap="nowrap"
			style="padding-top: .75em; vertical-align: middle"><label
			for="idFrontal">Lista de páginas: </label>
		</td>			
		<td align="left" style="padding-top: .5em; vertical-align: middle">
			<select id="idFunciones" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" name="favoritosBean.codigoFuncion"
				<option value="-1">Seleccione una opción</option>>
			</select>
		</td>
	<tr>
</table>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

</div>

<display:table name="listaFavoritos" excludedParams="*"
			list="listaFavoritos"
			requestURI="inicioFavoritos.action" class="tablacoin"
			uid="tablaConsultaFavoritos"
			summary="Listado de Favoritos"
			cellspacing="0" cellpadding="0"
			pagesize="6">
			
	<display:column property="funcion.id.codigoAplicacion" title="Módulo" 
			sortable="true" headerClass="sortable" 
 			defaultorder="descending" maxLength="12"	 
			style="width:1%"  />   
					
	<display:column property="funcion.descFuncion" title="Favorito" 
			sortable="true" headerClass="sortable" 
 			defaultorder="descending" maxLength="50"	 
			style="width:1%"  />   
		
	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksFavoritos);' onKeyPress='this.onClick'/>" 
			style="width:1%" >
			
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="listaChecksFavoritos" id="idFavorito" value='<s:property value="#attr.tablaConsultaFavoritos.funcion.id.codigoFuncion"/>,<s:property value="#attr.tablaConsultaFavoritos.idFavorito"/>' />
			</td>
		</tr>
		</table>		

 	</display:column>
				
	<input type="hidden" name="resultadosPorPagina"/>
	
</display:table>


<table class="acciones">
		<tr>
			<td>
				<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return guardarFavorito();" onKeyPress="this.onClick"/>
				&nbsp;	
			</td>
			<td align="center">
				<input type="button" class="boton" name="bEliminar" id="idEliminar" value="Eliminar" onClick=" return eliminarFavorito();" onKeyPress="this.onClick" />&nbsp;                     
	     	</td>
	    </tr>
</table>
		

</s:form>

<script type="text/javascript">

// Cargar por Ajax los elementos del combo Lista de paginas en funcion del elemento seleccionado en el combo Modulo
function cargarListaDescFuncion(codigoAplicacion){
	obtenerListaDescFuncion(codigoAplicacion);
}

// Generar la llamada Ajax a traves de la clase TraficoAjax
function obtenerListaDescFuncion(codigoAplicacion) {
	
	var url = "recuperarDescFuncionTraficoAjax.action?codigoAplicacion="+codigoAplicacion;
	
	var req_generico_descripcion = NuevoAjax();
	//Hace la llamada a Ajax
	req_generico_descripcion.onreadystatechange = function () {
		rellenarListaDescripcion(req_generico_descripcion, codigoAplicacion);
	}
	req_generico_descripcion.open("POST", url, true);
	req_generico_descripcion.send(null);
}

// Se rellena el combo combo Lista de paginas  
function rellenarListaDescripcion(req_generico_descripcion, codigoAplicacion){

	 if (req_generico_descripcion.readyState == 4) { // Complete
         if (req_generico_descripcion.status == 200) { // OK response
               var textToSplit = req_generico_descripcion.responseText;
               //Split the document
               var indicesvalores = textToSplit.split("[#]");
               var indices = indicesvalores[0].split("||");
              
               var desplegable = document.getElementById('idFunciones');
              
               // Se vacia el desplegable
               desplegable.options.length = 0;
              
               desplegable.options[0] = new Option("Seleccione una opción", "");
               for(var i = 0; i < indices.length; i++){
                   var claveValor = indices[i].split(";");
                   desplegable.options[(i + 1)] = new Option(claveValor[1], claveValor[0]);
               }              
         }
   }
}

// Se llama al metodo Guardar
function guardarFavorito() {	
	document.formData.action="guardarFavoritos.action";
	document.formData.submit();
}
// Se eliminan registros de la tabla
function eliminarFavorito() {
	// Confirmacion de la eliminacion
	if (numChecked() == 0) {
		alert("Debe seleccionar algún registro de la tabla");
		return false;
	}
		
	var checks = document.getElementsByName("listaChecksFavoritos");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	
	document.formData.action="eliminarFavoritos.action?idFavorito=" + codigos;
	document.formData.submit();
	return true;
}

function numChecked() {
	var checks = document.getElementsByName("listaChecksFavoritos");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

$( document ).ready(function() {
	if ($("#idModulo").val() != "") {
		cargarListaDescFuncion($("#idModulo").val());
	}
});
</script>


