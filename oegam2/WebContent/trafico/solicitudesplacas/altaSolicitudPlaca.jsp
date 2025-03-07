<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<s:form method="post" id="formData" name="formData">
	<div class="nav" style="margin-left: 0.3em">
		<table style="width:100%">
			<tr>
				<td class="tabular"><span class="titulo">Alta de solicitudes de placas de matrícula</span></td>
			</tr>
		</table>
	</div>

	<div id="divError">
		<s:if test="hasActionMessages() || hasActionErrors()">
			<table class="tablaformbasica" style="width:100%">
				<tr>
					<td align="left">						
						<ul id="mensajesInfo" style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
							<s:if test="hasActionMessages()">
								<s:iterator value="actionMessages">
									<li><span><s:property /></span></li>
								</s:iterator>
							</s:if>
						</ul>							
						<ul id="mensajesError" style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
							<s:if test="hasActionErrors()">
								<s:iterator value="actionErrors">
									<li><span><s:property /></span></li>						
								</s:iterator>
							</s:if>	
						</ul>							
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	<h2> Si desea una sola placa, para un remolque o duplicado, marque el check de la columna I (Individual) </h2>
	<div style="background-color: #f4f4f4;">	
		<table id="tablaAltas">
				<thead>
					<tr>
						<th id="th_bastidor">Bastidor</th>
						<th id="th_matricula">Matrícula</th>
						<th id="th_niftitular">NIF Titular</th>
						<th id="th_tipovehiculo">Tipo de Vehículo</th>
						<th class="misc">#</th>
						<th class="invisible"></th>
						<th id="th_individual">I</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="listaSolicitudes" var="tablaRegistros"
					status="statusMatricula">
					<tr>
											
					 
						<td>
<!-- cambio							<s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].vehiculo.bastidor"
								id="bastidor_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								maxlength="18" />
	-->							
							<s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].bastidor"
								id="bastidor_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								maxlength="18" />
								
								
						</td>
						<td>
						
<!-- cambio							<s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].vehiculo.matricula"
								id="matricula_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);return validarMatricula_alPegar(event)"
								maxlength="10" 
								onkeypress="return validarMatricula(event);" onmousemove="return validarMatricula_alPegar(event)"  />
	-->							
								<s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].matricula"
								id="matricula_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);return validarMatricula_alPegar(event)"
								maxlength="10" 
								onkeypress="return validarMatricula(event);" onmousemove="return validarMatricula_alPegar(event)"  />
								
								
						</td>
						<td>
						<!-- cambio	
							<s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].titular.nif"
								id="nif_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								maxlength="9" />
								-->
							<s:textfield
								name="listaSolicitudes[%{#statusMatricula.index}].nif"
								id="nif_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								maxlength="9" />
								
						</td>
						<td>
						<!-- cambio
							<s:select name="listaSolicitudes[%{#statusMatricula.index}].vehiculo.tipoVehiculoBean.tipoVehiculo"
								id="tipoVehiculo_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T1')"
								headerKey=""
						   		headerValue="Seleccione Tipo" 
								listKey="tipoVehiculo" 
								listValue="descripcion"
								cssStyle="max-width:20em;" />
-->								

							<s:select name="listaSolicitudes[%{#statusMatricula.index}].tipoVehiculo"
								id="tipoVehiculo_%{#statusMatricula.index}"
								onchange="validarContenidoCampos(this, event);"
								list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('T1')"
								headerKey=""
						   		headerValue="Seleccione Tipo" 
								listKey="tipoVehiculo" 
								listValue="descripcion"
								cssStyle="max-width:20em;" />
								
								
								
						</td>
						<td class="misc">
							<a href="#" onclick="reindexRows('tablaAltas', 'add');" title="Añadir solicitud">
								<img src="img/bmas.gif" alt="Añadir matrícula" />
							</a>
							<a href="#" onclick="reindexRows('tablaAltas', 'delete', this);" title="Eliminar solicitud" class="delete">
								<img src="img/bmenos.gif" alt="Eliminar matrícula" />
							</a>							
						</td>
						<td class="invisible">
							<!-- cambio
							<input type="hidden" id="numColegiado_0" name="listaSolicitudes[0].vehiculo.id.numColegiado" value="<s:property value="#session.GestorArbol.usuario.num_colegiado"/>" />
							-->
							<input type="hidden" id="numColegiado_0" name="listaSolicitudes[0].numColegiado" value="<s:property value="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNumColegiadoCabecera()"/>" />
							
						</td>
						<td>
							<s:checkbox name="listaSolicitudes[0].individual" id="individual" label="I"/>
						</td>
					
					</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" value="Alta de solicitudes" class="boton" onclick="validaMatriculasIguales('tablaAltas',this.form.id)" />
			<!--  <input type="reset" value="Restablecer" class="boton" />-->
			<input type="button" value="Limpiar Formulario" onclick="limpiarFormulario(this.form.id);" class="boton" />
		</div>

		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<!--  FIN FORMULARIO DE FILTROS -->

	</s:form>
<script type="text/javascript">
	function validarContenidoCampos(campo, e) {

		var errores = $('#divError');
		var mensajesInfo = $('#mensajesInfo');
		var mensajesError = $('#mensajesError');
		mensajesError.html("");

	
		var n = campo.id.indexOf("_");
		
		var nombreCampo = campo.id.substr(0, n);
		
		switch (nombreCampo) {
			case "matricula":
				if(!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>La matrícula no puede estar vacía</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;									
				} else if (!validaAlfaNumerico(campo.value,6)){
					campo.style.color = '#ff0000';
					campo.focus();
					mensajesError.append("<li>La matrícula no es válida</li>");
					mensajesError.css('display', 'block');
					errores.css('display', 'block');
					return false;
				}	
					else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
			case "bastidor":
				if(!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El bastidor no puede estar vacío</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
				   } else if (!validaAlfaNumerico(campo.value,17)){
					campo.style.color = '#ff0000';
					campo.focus();
					mensajesError.append("<li>El bastidor no es válida</li>");
					mensajesError.css('display', 'block');
					errores.css('display', 'block');
					return false;
				   }						
					else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
			case "nif":
				if(!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El nif del titular no puede estar vacío</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else if (!valida_nif_cif_nie(campo)){
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El nif introducido no es válido</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
				
			case "tipoVehiculo":
				if(!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El tipoVehiculo no puede estar vacío</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
				
				
			default:
				break;
	
		}
		
		return true;
	}
	
	
	function validaAlfaNumerico(str,numMinLen)
	{
		
		if(str!=null)
		 {						
		    var lon = str.length;		
		    if(lon<numMinLen)
			  return false;
		 }
	    
	    if( /[^a-zA-Z0-9]/.test( str ) ) {	        
	        return false;
	     }
	     return true;     
	}

	function validarFormulario(idFormulario){

		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				//alert(elementos[i].tagName+" - "+elementos[i].type);
				//alert(validarContenidoCampos(elementos[i], null));
				if(!validarContenidoCampos(elementos[i], null)) {
					return false;
				}
			}
		}

		formulario.action = 'inicioSolicitudSolicitudPlacaslicitudes_AltaSolicitudesPlacas.action';
		formulario.submit();
		
	}

	function limpiarFormulario(idFormulario) {
		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				//alert(elementos[i].tagName+" - "+elementos[i].type);
				elementos[i].value = "";
			}
		}
	}


	/*
	 * Función que añade filas a una tabla cuyo id es especificado por parámetro.
	 * La tabla debe tener un elemento tbody que contiene las filas sobre las que trabajar
	 * y excluye las cabeceras de la tabla.
	 */
	function addRow(idTabla){

		// Inicializamos variables y obtenemos la fila inicial
		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var tr = $(tbody).find('tr').first();
		var numRows = $(tbody).find('tr').length;
		var numCols = $(tr).find('td').length;
		var deleteButton = tr.find('.delete').first();

		deleteButton.css('display', 'inline');

		// Clonamos la fila inicial y borramos el contenido de la nueva
		var newTR = $(tr).clone();
		$(newTR).empty();

		// Se recorren las columnas obtenidas de la fila inicial
		for(var numCol = 1; numCol <= numCols; numCol++){
			var column = $(tr).find('td:nth-child(' + numCol + ')');
			var field;
			if($(column).find('input').length!=0){

				field = $(column).find('input').first();
				
			} else if($(column).find('select').length!=0){

				field = $(column).find('select').first();
				
			} else if($(column).find('button').length!=0){

				field = $(column).find('button').first();
				
			} else {

				field = '';
				
			}

			// Clonamos la columna actual y vaciamos la nueva
			var newColumn = $(column).clone();
			$(newColumn).empty();

			// Se clona el campo de la columna actual (si existe), se le modifica el id y se inserta a la nueva columna
			if(field!=''){
				var idField = $(field).attr('id');
				var idNewField = idField.substr(0, (idField.indexOf("_") + 1)) + numRows;
				
				var newField = $(field).clone();
				$(newField).attr('id', idNewField);

				$(newColumn).append($(newField));
				
			} else {
				$(newColumn).append($(column).html());
			}

			// Se añade la nueva columna a la nueva fila
			$(newTR).append($(newColumn));
			
		}

		// Se añade la nueva fila a la tabla
		$(tbody).append($(newTR));

		deleteButton.css('display', 'none');
		
	}

	function deleteRow(idTabla, remitente){
		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var fila = $(remitente).closest('tr');
		
		if(tbody.find('tr').length > 1 && fila.html()!=tbody.find('tr').first().html()){
			tbody.find(fila).remove();
		} else {
			alert("No se puede eliminar la primera fila");
		}
	}

	function reindexRows(idTabla, accion, remitente){

		if(accion=='add'){
			addRow(idTabla);
		} else {
			deleteRow(idTabla, remitente);
		}

		// Inicializamos variables y obtenemos la fila inicial
		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var numRows = $(tbody).find('tr').length;

		for(var numRow = 1; numRow <= numRows; numRow++){

			var tr = $(tbody).find('tr:nth-child(' + numRow + ')');
			var numCols = $(tbody).find('td').length;
			
			for(var numCol = 1; numCol <= numCols; numCol++){

				var column = $(tr).find('td:nth-child(' + numCol + ')');

				if($(column).find('input').length!=0){

					field = $(column).find('input').first();
					
				} else if($(column).find('select').length!=0){

					field = $(column).find('select').first();
					
				} else if($(column).find('button').length!=0){

					field = $(column).find('button').first();
					
				} else {

					field = '';
					
				}

				if(field!='' && numRow > 1){
					
					var fieldName = $(field).attr('name');
					var fieldId = $(field).attr('id');
					var nameSearchValue = "", idSearchValue = "";
					if(accion=='add'){
						nameSearchValue = "[0]";
						idSearchValue = "_0";
					} else {
						nameSearchValue = "[" + (numRow) + "]";
						idSearchValue = "_" + (numRow);
					}
					var nameNewValue = "[" + (numRow - 1) + "]";
					var idNewValue = "_" + (numRow - 1);
					var newFieldName = fieldName.replace(nameSearchValue, nameNewValue);
					var newFieldId = fieldId.replace(idSearchValue, idNewValue);

					var newField = $(field).clone();
					$(newField).attr('name', newFieldName);
					$(newField).attr('id', newFieldId);

					$(column).empty();
					$(column).append($(newField));
					
				}
				
			}

		}
		
	}


	function validaMatriculasIguales(idTabla, idFormulario){

		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var numRows = $(tbody).find('tr').length;
		

		for (var numRow = 1; numRow <= numRows; numRow ++){
			var tr = $(tbody).find('tr:nth-child(' + numRow + ')')
			var numCols = $(tr).find('td').length;
			
			for(var numCol = 1; numCol <= numCols; numCol++){
				var column = $(tr).find('td:nth-child(' + numCol + ')');
				var field;
				if($(column).find('input').length!=0){
	
					field = $(column).find('input').first();
					
				} else {
	
					field = '';
					
				}
	
				// Se clona el campo de la columna actual (si existe), se le modifica el id y se inserta a la nueva columna
				if(field!=''){
					var idField = $(field).attr('id');
					if (idField.indexOf('matricula') != -1){
						for (var numRowSecond = numRow + 1 ; numRowSecond <= numRows; numRowSecond ++){
							var tr = $(tbody).find('tr:nth-child(' + numRowSecond + ')')
							
							//for(var numColSecond = 1; numColSecond <= numCols; numColSecond++){
								var columnSecond = $(tr).find('td:nth-child(' + numCol + ')');
								var fieldSecond;
								if($(columnSecond).find('input').length!=0){
					
									fieldSecond = $(columnSecond).find('input').first();
									
								} else {
					
									fieldSecond = '';
								}

								if(fieldSecond!=''){
									var idFieldSecond = $(fieldSecond).attr('id');
									if (idFieldSecond.indexOf('matricula') != -1){
										if ($(fieldSecond).val() == $(field).val()){
											alert("No puede haber dos matriculas iguales");
											return false;
										}
									}
								}
							//}
						}
					}
				}
			}
		}

		validarFormulario(idFormulario);
	}


</script>