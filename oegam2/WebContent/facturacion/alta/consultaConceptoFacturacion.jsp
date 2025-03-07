<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/facturacionBotones.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" action="GestionFacturar">	



	<!-- Se hace referencia a la parte de errores de los mensajes -->
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Consulta de Concepto</td>				
			</tr>
		</table>
	
		<s:hidden name="datosConcepto.conceptoTodoArray" id="idConceptoTodoArray"/>
		<s:hidden name="datosConcepto.conceptoIdBorrar"  id="idConceptoIdBorrar"/>
		<s:hidden name="datosConcepto.conceptoColBorrar" id="idConceptoColBorrar"/>
		<s:hidden name="datosConcepto.numColegiado"      id="idNumColegiado"/>

 		<display:table name="lFacturaConcepto" excludedParams="*"
				class="tablacoin"
				uid="tablaConsultaConceptos"
				summary="Listado de conceptos"
				cellspacing="0" cellpadding="0">
					
			 <display:column property="numColegiado" title="Numero de colegiado"
					sortable="false" headerClass="sortable"
					defaultorder="descending" 
					style="width:4%"/>
			<display:column property="idColegiadoConcepto" title="Id del concepto"
					sortable="false" headerClass="sortable"
					defaultorder="descending" 
					style="width:4%"/>
			<display:column property="nombreColegiadoConcepto" title="Descripcion del concepto"
					sortable="false" headerClass="sortable"
					defaultorder="descending" 
					style="width:4%"/>
			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaConcepto);' onKeyPress='this.onClick'/>" 
				style="width:1%; padding-right: 5px; " >
				<input type="checkbox" name="listaChecksConsultaConcepto" id="check<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>-<s:property value="attr.tablaConsultaConceptos.nombreColegiadoConcepto"/>" 
					value='<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>' />
				<input type="hidden" id="colegiadoConceptoHidden<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>" 
					value="<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>"/>	
				<input type="hidden" id="descriptionHidden<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>" 
					value="<s:property value="#attr.tablaConsultaConceptos.nombreColegiadoConcepto"/>"/>
				<input type="hidden" id="numColegiadoHidden<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>" 
					value="<s:property value="#attr.tablaConsultaConceptos.numColegiado"/>"
					onClick="return seleccionarConceptosCheck();"/>
	 		</display:column>	
		</display:table>
				&nbsp;
		<input class="boton" type="button" id="idEliminar" name="bEliminar" value="Eliminar" onClick="eliminar();return false;" onKeyPress="this.onClick" />
		&nbsp;
		 <input class="boton" type="button" id="idAnadir" name="bAnadir" value="A&ntilde;adir" onClick="muestraOcultaTabla();"/>
		&nbsp;
			 <input class="boton" type="button" id="idModificar" name="bModificar" value="Modificar" onClick="muestraOcultaTablaEdicion
			 	('<s:property value="#attr.tablaConsultaConceptos.idColegiadoConcepto"/>','<s:property value="#attr.tablaConsultaConceptos.nombreColegiadoConcepto"/>');"/>
		&nbsp;
		<div id="idDiv" style="display: none">
			<table class="tablaformbasica" id="mitablaconceptocolegiado" cellSpacing="0" cellspacing="0" align="left" border="0">
			<tr>    							
				<td align="center" style="width: 30%">			       	
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Número Colegiado:</label></p>
			    </td>
			    <td align="center" style="width: 30%">
			    	<p style="font-size:1.2 em; color: #000000; align:center"><label>Identificador Concepto:</label></p> 
			    </td>
			    <td align="center" style="width: 30%">
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Nombre Concepto:</label></p>
			    </td>		    		    			    		    
			    <td align="center" style="width: 10%">
			       	<label></label> 
			    </td>
		  	</tr>
 		  	<tr> <td> &nbsp;</td> </tr>
		  	<tr>
		  		<td>
		  			<input type="text" id="idNumColegiado" disabled="disabled" name="idNumColegiado" class="inputfocus">
		  		</td>
		  		<td>
		  			<input type="text" id="idColegiadoConcepto" disabled="disabled" name="idColegiadoConcepto" class="inputfocus">
		  		</td>
		  		<td>
		  			<input type="text" id="nombreColegiadoConcepto" name="nombreColegiadoConcepto">
		  		</td>
		  	</tr> 
		 </table> 
 		&nbsp; 
	 	<table align="center">
			<tr>
				<td>
					<input class="boton" type="button" id="bGuardar" name="bGuardar" value="Guardar" onClick="saveConcepto();"/> 
				</td>
				<td>
				 	<input class="boton" type="button" id="bCancelar" name="bCancelar" value="Cancelar" onClick="muestraOcultaTabla();"/> 
				</td>
			</tr>
		</table>
		<%-- 	<%@include file="altaConceptoColegiado.jsp" %> --%>
		</div> 
		&nbsp;
		<div id="idDivModificacion" style="display: none">
			<table class="tablaformbasica" id="mitablaconceptocolegiado" cellSpacing="0" cellspacing="0" align="left" border="0">
			<tr>    							
				<td align="center" style="width: 30%">			       	
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Número Colegiado:</label></p>
			    </td>
			    <td align="center" style="width: 30%">
			    	<p style="font-size:1.2 em; color: #000000; align:center"><label>Identificador Concepto:</label></p> 
			    </td>
			    <td align="center" style="width: 30%">
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Nombre Concepto:</label></p>
			    </td>		    		    			    		    
			    <td align="center" style="width: 10%">
			       	<label></label> 
			    </td>
		  	</tr>
 		  	<tr> <td> &nbsp;</td> </tr>
		  	<tr>
		  		<td>
		  			<input type="text" id="idNumColegiado" disabled="disabled" name="idNumColegiado" class="inputfocus">
		  		</td>
		  		<td>
		  			<input type="text" id="idColegiadoConcepto" disabled="disabled" name="idColegiadoConcepto" class="inputfocus">
		  		</td>
		  		<td>
		  			<input type="text" id="nombreColegiadoConcepto" name="nombreColegiadoConcepto">
		  		</td>
		  	</tr> 
		 </table> 
 		&nbsp; 
	 	<table align="center">
			<tr>
				<td>
					<input class="boton" type="button" id="bGuardar" name="bGuardar" value="Guardar" onClick="saveConcepto();"/> 
				</td>
				<td>
				 	<input class="boton" type="button" id="bCancelar" name="bCancelar" value="Cancelar" onClick="muestraOcultaTabla();"/> 
				</td>
			</tr>
		</table>
		<%-- 	<%@include file="altaConceptoColegiado.jsp" %> --%>
		</div> 
	
	<%-- 	<table class="tablaformbasica" id="mitablaconceptocolegiado" cellSpacing="0" cellspacing="0" align="left" border="0">
			<tr>    							
				<td align="center" style="width: 30%">			       	
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Número Colegiado:</label></p>
			    </td>
			    <td align="center" style="width: 30%">
			    	<p style="font-size:1.2 em; color: #000000; align:center"><label>Identificador Concepto:</label></p> 
			    </td>
			    <td align="center" style="width: 30%">
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Nombre Concepto:</label></p>
			    </td>		    		    			    		    
			    <td align="center" style="width: 10%">
			       	<label></label> 
			    </td>
		  	</tr>
			<tr> 
		    	<td align="left" style="width: 30%">
					<s:textfield name="datosConcepto.numColegiado0" id="idNumColegiado0" size="6" maxlength="6" style="color:#6E6E6E"/>
				</td> 
				<td align="left" style="width: 30%">
					<s:textfield name="datosConcepto.idConcepto0" id="idConcepto0" size="9" maxlength="9"  />
				</td>
				<td align="left" style="width: 30%">
					<s:textfield name="datosConcepto.nombreConcepto0" id="idNombre0" size="40" maxlength="100"  onKeyUp = "upperMe('idNombre0')" onfocus="limpiarTextfield('idNombre0')" onblur="comprobarTextfield('idNombre0')" />					
				</td>
				<td align="left" style="width: 10%">	 
			    	<a onclick="addRowConcepto('mitablaconceptocolegiado');"> <img width="15" height="16" border="0" src="img/plus.gif">  </a> 		    
					<a onclick="delRowConcepto('mitablaconceptocolegiado');"> <img width="15" height="16" border="0" src="img/minus.gif"> </a>
				</td>			     
		     </tr>
		</table> 
		
		<table align="center">
			<tr> <td> &nbsp;</td> </tr>
			<tr>
				<td>
					<input type="button" class="boton" id="idBotonGuardar" name="bGuardar" value="Guardar Concepto" onClick="return validarGuardarConceptoFactura(this);" onKeyPress="this.onClick"/>
				</td>
			</tr>		
		</table>--%>
		
		<BR/>
		<BR/>
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Consulta de Clave</td>				
			</tr>
		</table>
		
		<table class="tablaformbasica" id="mitablaconceptocolegiado" cellSpacing="0" cellspacing="0" align="left" border="0">
			<tr> 
		    	<td align="left" style="width: 20%">
					<p style="font-size:1.2 em; color: #000000">Introduzca la clave: </p>
				</td> 
				<td align="left" style="width: 80%">
					<s:password name="claveColegiado" id="idClaveColegiado" size="10" maxlength="10"  />
				</td>						     
		     </tr>
		</table>
		<table align="center">
			<tr> <td> &nbsp;</td> </tr>
			<tr>
				<td>
					<input type="button" class="boton" id="idBotonGuardar" name="bGuardar" value="Guardar Clave" onClick="return validarGuardarClaveFactura(this);" onKeyPress="this.onClick"/>
				</td>
			</tr>		
		</table>
	</s:form >
	
	<script>
		function cargarConceptosFacturacion() {
			var miCadenaConcepto  = document.getElementById("idConceptoTodoArray").value;
			//alert('miCadenaConcepto: ' + miCadenaConcepto);
			var separadorConcepto = "*";

			// DRC@30-08-2012 Recoge los valores de la cadena Concepto y elimina en caso de que existan los guiones que sobren
			if (miCadenaConcepto.substring(0, 1) == separadorConcepto) {
				miCadenaConcepto = miCadenaConcepto.substring(1, miCadenaConcepto.length);
			}
			if (miCadenaConcepto.substring(miCadenaConcepto.length-1, miCadenaConcepto.length) == separadorConcepto) {
				miCadenaConcepto = miCadenaConcepto.substring(0, miCadenaConcepto.length-1);
			}

			// DRC@30-08-2012 Recorre los valores de Coneptos
			if (miCadenaConcepto != null && miCadenaConcepto != "") {
				var elemento    = miCadenaConcepto.split(separadorConcepto);
				var tamConcepto = elemento.length / 3;
				var elem = 0;

				tamTablaConcepto(tamConcepto, 'mitablaconceptocolegiado', elemento);
				for (var i = 0; i <= tamConcepto; i++) { 				
					var a = elem;
					var b = elem+1;
					var c = elem+2;
//alert('2.- Vuelta: ' + i + ' (' + elemento[a] + ', ' + elemento[b] + ', ' + elemento[c]);
					if (elemento[a] != null) {
						document.getElementById("idNumColegiado" + i).value = elemento[a];
						document.getElementById("idConcepto" + i).value     = elemento[b];
						document.getElementById("idNombre" + i).value       = elemento[c];
						elem = elem + 3;
					}
				}			
			}			
		}

		// DRC@30-08-2012 Comprueba el numero de Concepto que existen en Base de datos, para cargarlos correctamente
		// FALTA@Creo que solo sirve para saber el numero de filas pero no para cargar los datos 
		function tamTablaConcepto (tamanyo, tableId, elemento) {
			var table = document.getElementById(tableId); 
			var pos = table.rows.length;
			var limit = tamanyo -1;
			var fila = limit;

			for (var i = 0; i < limit; i++) {
				var row = table.insertRow(pos);
				var cell_0 = row.insertCell(0);
				var cell_1 = row.insertCell(1);
				var cell_2 = row.insertCell(2);
				var cell_3 = row.insertCell(3);

				// Celda Cero 
				var idCell0      = "idNumColegiado" + fila;
				
				var cell_0_html  = '<s:textfield name="datosConcepto.numColegiado" id="'+idCell0+'" size="6" maxlength="6" readOnly="true" style="color:#6E6E6E"/>';																															  			
				cell_0.innerHTML = cell_0_html;
				
				// Celda Uno 
				var idCell1      = "idConcepto" + fila;
				var cell_1_html  = '<s:textfield name="datosConcepto.idConcepto" id="'+idCell1+'" size="9" maxlength="9" />';																															  			
				cell_1.innerHTML = cell_1_html;

				// Celda Dos
				var idCell2      = "idNombre" + fila;
				var cell_2_html  = '<s:textfield name="datosConcepto.nombreConcepto" id="'+idCell2+'" size="40" maxlength="100" onKeyUp="upperMe(\"'+idCell2+'\")"/>';
				cell_2.innerHTML = cell_2_html;	
//alert('ideCell0: ' + idCell0 + ' idCell2: ' + idCell2);
				// Celda Tres
				cell_3.innerHTML = '';				
							
				fila--;
			}
		}

		// ********************************** CONCEPTO **********************************
		// DRC@04-07-2012 Funcion que se utiliza para borrar (en caso de que sea posible) una fila de los Suplidos	
		function delRowConcepto(tableId) {
			var table = document.getElementById(tableId);
			var pos = table.rows.length;
			//alert('pos: ' + pos);			
			if ( pos > 2) {
				--pos;
				var delCol = document.getElementById("idNumColegiado" + (pos-1)).value;
				var delId  = document.getElementById("idConcepto" + (pos-1)).value;
				document.getElementById("idConceptoColBorrar").value += "'" + delCol + "', ";
				document.getElementById("idConceptoIdBorrar").value += "'" + delId + "', ";				
				table.deleteRow(pos);				
			} 
			var a = document.getElementById("idConceptoIdBorrar").value;
			var b = document.getElementById("idConceptoColBorrar").value;
		}

		function addRowConcepto(tableId) {
			var table = document.getElementById(tableId); 

			var pos = table.rows.length;

			// DRC@05-07-2012 Se podria utilizar par limitar el numero de Gastos
			//if ( pos < 11) { 
				var row = table.insertRow(pos);
				var cell_0 = row.insertCell(0); 
				var cell_1 = row.insertCell(1); 
				var cell_2 = row.insertCell(2);
				var cell_3 = row.insertCell(3);
				
				// Celda Cero 
				var idCell0      = "idNumColegiado" + (pos-1);
				var idCell0Value = document.getElementById("idNumColegiado0").value;
				var cell_0_html  = '<s:textfield name="datosConcepto.numColegiado" id="'+idCell0+'" value="'+idCell0Value+'" size="6" maxlength="6" readOnly="true" style="color:#6E6E6E"/>';																															  			
				cell_0.innerHTML = cell_0_html;
				
				// Celda Uno 
				var idCell1      = "idConcepto" + (pos-1);
				var cell_1_html  = '<s:textfield name="datosConcepto.idConcepto" id="'+idCell1+'" value="'+pos+'" size="9" maxlength="9"  />';																															  			
				cell_1.innerHTML = cell_1_html;

				// Celda Dos
				var idCell2      = "idNombre" + (pos-1);
				var cell_2_html  = '<s:textfield name="datosConcepto.nombreConcepto" id="'+idCell2+'" value="" size="40" maxlength="100" onKeyUp="upperMe(\"'+idCell2+'\")" />';
				cell_2.innerHTML = cell_2_html;

				// Celda Tres
				cell_3.innerHTML = '';		
			//}
		}

		function validarGuardarConceptoFactura(){	
			//alert('validarGuardarConceptoFactura');
			// ---------------------------------------------------------------------------------
			// Conceptos
			var table = document.getElementById("mitablaconceptocolegiado");
			var pos   = table.rows.length - 1;
			var miCadenaConcepto = "";		
			var idNumColegiado = "";
			var idConcepto     = 0; 
			var idNombre       = "";

			for (var i = 0; i < pos; i++) {
				idNumColegiado = "";
				idConcepto     = 0; 
				idNombre       = "";			
//alert('vuelta: ' + i + ' Position: ' + pos);
				if (document.getElementById("idNumColegiado" + i).value != null)
					idNumColegiado = document.getElementById("idNumColegiado" + i).value;
				else 
					idNumColegiado = 0;
				
				if (document.getElementById("idConcepto" + i).value != null) {
					idConcepto = document.getElementById("idConcepto" + i).value;
				} else {
					alert("Identificador de Concepto No Valido.");
					return false;
				}
				
				if (idConcepto.indexOf("*") > -1) {
					alert("Caracter No Valido");				
					document.getElementById("idConcepto" + i).value = idConcepto.substring(0, idConcepto.length-1);
					return false;
				}
				
				if (document.getElementById("idNombre" + i).value != null) {
					idNombre = document.getElementById("idNombre" + i).value;
				} else {
					alert("Nombre de Concepto No Valido.");
					return false;
				}

				miCadenaConcepto += "*" + idNumColegiado + "*" + idConcepto + "*" + idNombre;
			}
			document.getElementById("idConceptoTodoArray").value                = miCadenaConcepto;
			document.getElementsByName("datosConcepto.conceptoTodoArray").value = miCadenaConcepto;
			
			document.formData.action="altaConceptoGestionFacturar.action";
			document.formData.submit();
		}

		function validarGuardarClaveFactura(){
			if (document.getElementById("idClaveColegiado").value == null || document.getElementById("idClaveColegiado").value == "") {
				alert('El campo clave no puede estar vacio.');
				return false;				
			} else {
				/* var clave = document.getElementById("idClaveColegiado").value; */
				document.getElementById("formData").action="altaClaveGestionFacturar.action";
/* 				document.getElementById("formData").action="metodoDePruebaGestionFacturar.action"; */
				document.getElementById("formData").submit();
			}
		}

		function upperMe(id) { 
		    document.getElementById(id).value = document.getElementById(id).value.toUpperCase(); 
		}
		
		function numChecked() {
			var checks = document.getElementsByName("listaChecksConsultaConcepto");	
			var numChecked = 0;
			for(var i=0; i<checks.length; i++) {
				if(checks[i].checked) numChecked++;
			}
			return numChecked;
		}
		
		function eliminar() {
			if(numChecked() == 0) {
				alert("Debe seleccionar algun concepto");
				return false;
			}
			var checks = document.getElementsByName("listaChecksConsultaConcepto");
			var codigos = "";
			var i = 0;
			while(checks[i] != null) {
				if(checks[i].checked) {
					//Pasamos al action todos los id de los checks marcados, separados por guiones
					if(codigos==""){
						codigos += checks[i].value;
					}else{
						codigos += "-"+checks[i].value;
					}
				}
				i++;
			}
			if (confirm("¿Está seguro de que desea eliminar los conceptos?")){
				document.forms[0].action="eliminarDescripcionDeConceptosGestionFacturar.action?NumsFactura="+codigos;
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
		
		function muestraOcultaTabla(){
			var mostrar = document.getElementById("idDiv");
			if (mostrar.style.display == "block"){
				mostrar.style.display = "none";
			}else{
				mostrar.style.display = "block";
			}
		}
		
		function muestraOcultaTablaEdicion(idConcepto, idNumColegiado){
			var mostrar = document.getElementById("idDiv");
		
			if (mostrar.style.display == "block"){
				mostrar.style.display = "none";
			}else{
				mostrar.style.display = "block";
				var checks = document.getElementsByName("listaChecksConsultaConcepto");
				var i = 0;
				while(checks[i] != null) {
					if (checks[i].checked){
						document.getElementById("idColegiadoConcepto").value = document.getElementById("colegiadoConceptoHidden"+checks[i].value).value;
						document.getElementById("nombreColegiadoConcepto").value = document.getElementById("descriptionHidden"+checks[i].value).value;
					}
					i++;
				}
			}
		}
		
		function saveConcepto(){
			descripcion = document.getElementById("nombreColegiadoConcepto").value;
			idConcepto = document.getElementById("idColegiadoConcepto").value;
			
			document.forms[0].action="guardarDescripcionDeConceptosGestionFacturar.action?DescripcionConcepto="+descripcion+'&idConceptoAltaModificacion='+idConcepto;
			document.forms[0].submit();
			return true;
		}

	</script>
	
	<script type="text/javascript">setTimeout("cargarConceptosFacturacion()", 1);</script>