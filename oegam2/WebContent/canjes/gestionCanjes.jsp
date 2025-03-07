<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
    <title>Gestión de Canjes</title>
    <script src="js/tabs.js" type="text/javascript"></script>
    <script src="js/validaciones.js" type="text/javascript"></script>
    <script src="js/genericas.js" type="text/javascript"></script>
    <script src="js/general.js" type="text/javascript"></script>
    <script src="js/trafico/comunes.js" type="text/javascript"></script>
    <style type="text/css">
 		 input:-webkit-autofill,
        input:-webkit-autofill:hover,
        input:-webkit-autofill:focus,
        input:-webkit-autofill:active {
            transition: background-color 5000s ease-in-out 0s;
            -webkit-text-fill-color: #000 !important;
            background-color: #fff !important;
        }

        .form-control {
            background-color: #fff !important;
            color: #000;
        }
        .hidden {
            display: none;
        }
        .containerCanjes {
            width: 90%;
            max-width: 1200px;
            margin: 20px;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        
        .nav .titulo {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        
        form {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        #formularioCanje{
        	clear: both;
		    margin-top: 0px;
		    padding-bottom: 6px;
		    margin-left: 0;
		    overflow: hidden;
		    width: 77em;
		    margin-right: auto;
		    padding-top: 0;
        }
       .tablaformbasicaCanje {
		    margin-top: 0px;
		    margin-left: 1em;
		    width: 53em;
		    margin-right: auto;
		    height: 20px;
		    text-decoration: none;
		    padding-left: 1.5em;
		}

        .formgroupcanjes {
            flex: 1;
            min-width: 18em;
        }
        
        form .formgroupcanjes label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        
        form .formgroupcanjes input {
            width: 90%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        
        form .formgroupcanjes input:focus {
            border-color: #007BFF;
        }
        
        form .form-actions {
            width: 100%;
            display: flex;
            justify-content: flex-end;
        }
        
        form .form-actions input {
            padding: 10px 20px;
            border: none;
            background-color: #007BFF;
            color: #fff;
            border-radius: 4px;
            cursor: pointer;
        }
        
        form .form-actions input:hover {
            background-color: #0056b3;
        }
        
        table#idTablaCanje {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        table#idTablaCanje th td {
            border: 1px solid #ddd;
              padding: 10px;
            text-align: left;
            background-color: #f2f2f2;
        }
        

        
        .acciones {
            width: 100%;
            margin-top: 20px;
        }

        .acciones td {
            text-align: center;
        }

        .boton {
            
            border: none;
            background-color: #007BFF;
            color: black;
            border-radius: 4px;
            cursor: pointer;
        }
           idTablaCanje, th, td .boton{
            border: 1px solid #ddd;
        }

        .boton:hover {
            background-color: #0056b3;
        }

        span#checksigla3PaisProcedencia {
            position: relative;
            top: -6em;
            left: 8em;
        }
        #mensajeErrores {
        color: red;
        margin-right: 53em;
  	    padding: 1em;
        margin-top: 10px;
        font-family:tahoma, arial, sans-serif; 
        font-size:11px; 
        font-weight:bold;"
   		}
   
   		.enlaceZip{
   		position: relative;
   		top: 1em;
   		left: 19em;
  		width: 48%;
   		}
   		.error {
            color: red;
            display: none;
        }
    </style>
</head>
<body>
<div class="containerCanjes">
    <div class="nav">
        <span class="titulo">Gestión de canjes</span>
    </div>
<div id="mensajeErrores" >

</div>
<div id="mensajes"></div>
    <s:form action="anadirCanjeGestCanjes.action" method="post" id="formularioCanje">
    <s:if test="hasActionErrors() || hasActionMessages()">
		<div id="divError">
			<table class="tablaformbasicaCanje" width="100%">
				<tr>
					<td align="left">
						<s:if test="hasActionErrors()">
							<!--<s:actionerror cssStyle="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;"/>-->
							<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
								<s:iterator value="actionErrors">
									<li><span><s:property /></span></li>							
								</s:iterator>
							</ul>
						</s:if>		
						<s:if test="hasActionMessages()">
							<!--<s:actionmessage cssStyle="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;"/>-->						
							<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
								<s:iterator value="actionMessages">
									<li><span><s:property /></span></li>							
								</s:iterator>
							</ul>
						</s:if>
					</td>
				</tr>
			</table>
		</div>
	</s:if>
        <div class="formgroupcanjes">
            <label for="dninie">DNI/NIE:</label>
            <s:textfield name="canjesDto.dninie" cssClass="form-control" maxlength="9"/>
        </div>
        <div class="formgroupcanjes">
            <label for="nombreapell">Nombre y Apellidos:</label>
            <s:textfield name="canjesDto.nombreapell" cssClass="form-control" maxlength="30"/>
        </div>
        <div class="formgroupcanjes">
            <label for="pais">País:</label>
            <s:textfield name="canjesDto.pais" cssClass="form-control" id="sigla3PaisProcedencia" maxlength="20"/>
            <span id="checksigla3PaisProcedencia"></span>
            <s:hidden id="sigla3" name="tramiteTrafMatrDto.vehiculoDto.procedencia"/>
        </div>
        <div class="formgroupcanjes">
            <label for="fechaNac">Fecha de Nacimiento:</label>
            <s:textfield name="canjesDto.fechaNac" cssClass="form-control" maxlength="10" oninput="validarFormatoFecha(this,'mensajeErrorFN')"/>
       	    <div id="mensajeErrorFN" class="error">El formato debe ser dd/MM/aaaa</div>
        </div>
        <div class="formgroupcanjes">
            <label for="numCarnet">Número de Carnet:</label>
            <s:textfield name="canjesDto.numCarnet" cssClass="form-control" maxlength="10"/>
        </div>
        <div class="formgroupcanjes">
            <label for="categorias">Categorías:</label>
            <s:textfield name="canjesDto.categorias" cssClass="form-control" maxlength="10"/>
        </div>
        <div class="formgroupcanjes">
            <label for="fechaExp">Fecha 1ª Expedición:</label>
            <s:textfield name="canjesDto.fechaExp" cssClass="form-control" maxlength="10" oninput="validarFormatoFecha(this,'mensajeErrorFE')"/>
             <div id="mensajeErrorFE" class="error">El formato debe ser dd/MM/aaaa</div>
        </div>
        <div class="formgroupcanjes">
            <label for="lugarExp">Lugar de Expedición:</label>
            <s:textfield name="canjesDto.lugarExp" cssClass="form-control" maxlength="20"/>
        </div>
            <s:iterator value="listado" status="status">
            <s:hidden name="listado[%{#status.index}].dninie" value="%{dninie}"/>
            <s:hidden name="listado[%{#status.index}].nombreapell" value="%{nombreapell}"/>
             <s:hidden name="listado[%{#status.index}].pais" value="%{pais}"/>
            <s:hidden name="listado[%{#status.index}].fechaNac" value="%{fechaNac}"/>
             <s:hidden name="listado[%{#status.index}].numCarnet" value="%{numCarnet}"/>
            <s:hidden name="listado[%{#status.index}].categorias" value="%{categorias}"/>
             <s:hidden name="listado[%{#status.index}].fechaExp" value="%{fechaExp}"/>
              <s:hidden name="listado[%{#status.index}].lugarExp" value="%{lugarExp}"/>
          
        </s:iterator>
        <div class="form-actions">
            <s:submit value="Añadir Canje" cssClass="btn btn-primary" onclick="anadirCanje(); return false;"/>
        </div>
    </s:form>
     <div id="listaCanjesContainer">
    <h2>Lista de Canjes</h2>
        <table id="idTablaCanje">
        <thead>
            <tr>
                <th>DNI/NIE</th>
                <th>Nombre y Apellidos</th>
                <th>País</th>
                <th>Fecha de Nacimiento</th>
                <th>Número de Carnet</th>
                <th>Categorías</th>
                <th>Fecha 1ª Expedición</th>
                <th>Lugar de Expedición</th>
            </tr>
         </thead>
          <tbody>
            <s:iterator value="listado">
                <tr>
                    <td><s:property value="dninie"/></td>
                    <td><s:property value="nombreapell"/></td>
                    <td><s:property value="pais"/></td>
                    <td><s:property value="fechaNac"/></td>
                    <td><s:property value="numCarnet"/></td>
                    <td><s:property value="categorias"/></td>
                    <td><s:property value="fechaExp"/></td>
                    <td><s:property value="lugarExp"/></td>
                    <td>
						<img id="idEliminarCanje" src="img/eliminar.png" alt="Eliminar" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
								onclick="javascript:eliminarRegistro(this);" title="Eliminar"/>
					</td>
                </tr>
            </s:iterator>
          </tbody>
        </table>
     <table class="acciones">
        <tr>
            <td>
               <input type="button" class="boton" name="bImprimir" id="idBotonImprimir" value="Imprimir" onClick="imprimirListado();" />
            </td>
            <td>
                <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volverCanjes();" onKeyPress="this.onClick"/>
            </td>
        </tr>
    </table>
    </div>
</div>

<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	    checkTableRows();
	});

	
function checkTableRows() {
    var tableBody = document.getElementById('idTablaCanje').getElementsByTagName('tbody')[0];
    var listaCanjesContainer = document.getElementById('listaCanjesContainer');
    var noData = tableBody.rows.length === 0;
    
    if (noData) {
        listaCanjesContainer.classList.add('hidden');
    } else {
        listaCanjesContainer.classList.remove('hidden');
    }
}


    var paisProcedencia = new BasicContentAssist(document.getElementById('sigla3PaisProcedencia'), [], null, true);
    cargarListaPaisesProcedencia(paisProcedencia, 'sigla3PaisProcedencia', 'sigla3');
    
    function limpiarFormulario() {
        $('#formularioCanje')[0].reset();
    }


    var numCanjesAgregados = 0;
    function anadirCanje() {
        var formData = $('#formularioCanje').serialize();
        if (numCanjesAgregados >= 20) {
            alert('Se ha alcanzado el límite máximo de canjes (20). Imprima el listado');
            return; // Detener la función sin enviar la solicitud
        }
        $.ajax({
            url: 'anadirCanjeGestCanjes.action',
            type: 'POST',
            data: formData,
            success: function(response) {
                var newCanjeRow = $(response).find('#idTablaCanje tbody tr');
                if (newCanjeRow.length) {
                    $('#idTablaCanje tbody').append(newCanjeRow);
                    listado = $('#idTablaCanje tbody tr');
                    limpiarFormulario();
                    checkTableRows();
                    numCanjesAgregados++;
        
                }
                var mensajes = $(response).find('#divError').html(); 
                   if (mensajes) {
                       
                       $('#mensajes').html(mensajes);
                   } else {
                       alert('Error al actualizar la lista de canjes.'); 
                   }
                
            },
            error: function() {
                alert('Error al añadir el canje. Por favor, inténtelo de nuevo.');
            }
        });
    }
    
    
    function imprimirListado() {
   	 $("#mensajes").html('');
	 $("#mensajeErrores").html('');
        var listadoCanjes = [];
        var filas = document.querySelectorAll("#idTablaCanje tbody tr");
        filas.forEach(function(fila) {
            var canje = {
                dninie: fila.cells[0].textContent,
                nombreapell: fila.cells[1].textContent,
                pais: fila.cells[2].textContent,
                fechaNac: fila.cells[3].textContent,
                numCarnet: fila.cells[4].textContent,
                categorias: fila.cells[5].textContent,
                fechaExp: fila.cells[6].textContent,
                lugarExp: fila.cells[7].textContent
            };
            listadoCanjes.push(canje);
        });

        fetch('imprimirCanjeGestCanjes.action', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify({ listado: listadoCanjes })
        })
        .then(response => {
        	console.log(response.status);
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
            	 return response.json().then(json => ({ json }));
            } else if (response.ok) {
                const disposition = response.headers.get('content-disposition');
                if (disposition) {
                    const filename = disposition.split('filename=')[1].replace(/"/g, ''); // Obtener el nombre del archivo de la cabecera
                    return response.blob().then(blob => ({ blob, filename }));
                } else {
                	   return response.text().then(text => ({ text }));
                }
            } else {
            	throw new Error('Network response was not ok.');
            }
        })
		.then(result => {
	        if (result.json) {
	            const jsonResponse = result.json;
	            if (jsonResponse.status === 'success') {
	                if (jsonResponse.messages && jsonResponse.messages.length > 0) {
	                    $("#mensajes").html(jsonResponse.messages.join("<br>"));
	                }
	                window.location.reload();
	            } else if (jsonResponse.status === 'error') {
	                if (jsonResponse.messages && jsonResponse.messages.length > 0) {
	                    $("#mensajeErrores").html(jsonResponse.messages.join("<br>"));
	                }
	            }
	        } else if (result.blob) {
	            const blob = result.blob;
	            const filename = result.filename;
	            const url = window.URL.createObjectURL(blob);
	            const a = document.createElement('a');
	            a.href = url;
	            a.download = filename;
	            document.body.appendChild(a);
	            a.click();
	            a.remove();
	            window.location.reload();
	        } else if (result.text) {
	            console.error('Unexpected response:', result.text);
	            $("#mensajeErrores").html('An unexpected response was received from the server.');
	        }
        })
        .catch(error => {
        	   $("#mensajeErrores").html("An error occurred while processing the request.");
            console.error('Error:', error);
        });
    }
   
    	 function validarFormatoFecha(input,errorId) {
    		 var formatoValido = /^\d{2}\/\d{2}\/\d{4}$/;
             var valor = input.value;
             var mensajeError = document.getElementById(errorId);
             if (valor === "") {
                 input.style.borderColor = "";
                 mensajeError.style.display = "none";
             } else if (!formatoValido.test(valor)) {
                 input.style.borderColor = "red";
                 mensajeError.style.display = "block";
             } else {
                 input.style.borderColor = "";
                 mensajeError.style.display = "none";
             }
         }

    function eliminarRegistro(btn) {
      	 $("#mensajes").html('');
    	 $("#mensajeErrores").html('');
    	 var fila = btn.parentNode.parentNode;
    	    var dninie = fila.cells[0].textContent; 
    	    $.ajax({
    	        url: 'eliminarRegistroCanjeGestCanjes.action',
    	        type: 'POST',
    	        data: { dninie: dninie}, 
    	        success: function(response) {
    	            var mensajes = $(response).find('#divError').html(); 
    	            if (mensajes) {
    	                $('#mensajes').html(mensajes);
    	            } else {
    	                $('#mensajes').html("El registro se ha eliminado correctamente."); 
    	            }
    	            fila.parentNode.removeChild(fila);
    	    	    checkTableRows();
    	        },
    	        error: function() {
    	            $('#mensajes').html("Error al eliminar el registro. Por favor, inténtelo de nuevo."); 
    	        }
    	    });
    }
    
</script>

</body>
</html>
