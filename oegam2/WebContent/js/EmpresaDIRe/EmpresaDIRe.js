var Autoriza = "eyJhbGciOiJSUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FQSV9SRVNUIl0sInVzZXJuYW1lIjoiMDg5NDg5ODNNIiwiaWF0IjoxNTM1NDUwMzI2LCJleHAiOjE1MzU0ODYzMjZ9.OKufEuGGmP44IlxmaoYeLWIiYU2zD8cqxwF2Q8V5K9Tw1d2zDmMqbzelgFQ2i0krWkdqlzLNIfZAl1oBs6zT-dF8kinwoCpCrpSXaKQrPvq1kb-MzPN_ZX9UY7TTavYTSmjVUvbmu6-aU260y_QCizIFuozeoPca7XaHlAFAr8vMrhlmXmf9ajXa5Fp1db7N1V1s5aTyrl_VSlG5b1Et2NxvRMH0dl1bOf_2LPts4tqSTaCh2EqX51dxA7nRiU1xtECSl3VG9No-ER6Q-NhSB6vg3a8ldUKkxMEFw8a-L5pXl7kHadujdkjgGuZ04HVjYr4Aq_6VAon2AD0Fp1NdRg";

var certificado = "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";



function buscarConsultaDIRe(){

		document.formData.action = "buscarConsultaEmpresaDIRe.action";
		document.formData.submit();
	}
	

function limpiarConsultaDIRe_Pantalla(){
	$("#idNumExpediente").val("");
	$("#nif").val("");
	$("#codigoDire").val("");
	$("#nombre").val("");
	$("#diaInicio").val("");
	$("#mesInicio").val("");
	$("#anioInicio").val("");
	$("#diaFinInicio").val("");
	$("#mesFinInicio").val("");
	$("#anioFinInicio").val("");
	$("#diaInicio2").val("");
	$("#mesInicio2").val("");
	$("#anioInicio2").val("");
	$("#diaFinInicio2").val("");
	$("#mesFinInicio2").val("");
	$("#anioFinInicio2").val("");
}


function abrirEvolucionAltaCayc(idNumExpediente, divDestino) {
	return abrirEvolucionCayc($("#" + idNumExpediente).val(), divDestino);
}

function aniadeContacto(idNumExpediente, divDestino) {
	
	return abrirAltaContacto($("#" + idNumExpediente).val(),
			"divEmergente_Contacto");

}
function aniadeAdministrador(idNumExpediente, divDestino) {
	return abrirAltaAdministrador($("#" + idNumExpediente).val(),
			"divEmergente_Administrador");

}
function abrirAltaContacto(numExpediente, destino) {
	var $dest = $("#divEmergente_Contacto");
	var $idForm = $("#formData");

	var pestania = obtenerPestaniaSeleccionada();

	var String_HTML = '';
	String_HTML = String_HTML
			+ '<table cellSpacing="1" class="tablaformbasica" cellPadding="0" align="left">';
	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">Nombre:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text" name="Contacto_nombre" /></td>';
	String_HTML = String_HTML + '</tr>';

	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">Apellido 1:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text"  name="Contacto_apellido1" /></td>';
	String_HTML = String_HTML + '</tr>';
	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">Apellido 2:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text"  name="Contacto_apellido2" /></td>';
	String_HTML = String_HTML + '</tr>';

	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">Descripcion:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text" name="Contacto_descripcion" /></td>';
	String_HTML = String_HTML + '</tr>';
	String_HTML = String_HTML + '</table>';


	$dest.empty().append(String_HTML).dialog(
			{
				appendTo : $idForm,
				modal : true,
				width : 850,
				height : 190,
				buttons : {
					Actualizar : function() {

						$("#formData").attr(
								"action",
								"alta_contactoAltaEmpresaDIRe.action#"
										+ pestania).trigger("submit");

					},
					Salir : function() {
						$(this).dialog("close");
					}
				}
			});

}




function abrirAltaAdministrador(numExpediente, destino) {
	var $dest = $("#divEmergente_Administrador");
	var $idForm = $("#formData");

	var pestania = obtenerPestaniaSeleccionada();

	var String_HTML = '';
	String_HTML = String_HTML
			+ '<table cellSpacing="1" class="tablaformbasica" cellPadding="0" align="left">';
	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">DNI:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text" name="Administrador_dni" /></td>';
	String_HTML = String_HTML + '</tr>';

	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">Certificado:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text"  name="Administrador_certificado" /></td>';
	String_HTML = String_HTML + '</tr>';
	String_HTML = String_HTML
			+ '<tr> <td align="left" nowrap="nowrap" ><label for="labelnombre">Descripción:</label></td>	';
	String_HTML = String_HTML
			+ ' <td align="left" nowrap="nowrap"><input type="text"  name="Administrador_descripcion" /></td>';
	String_HTML = String_HTML + '</tr>';

	
	String_HTML = String_HTML + '</table>';


	$dest.empty().append(String_HTML).dialog(
			{
				appendTo : $idForm,
				modal : true,
				width : 850,
				height : 190,
				buttons : {
					Actualizar : function() {

						$("#formData").attr(
								"action",
								"alta_administradorAltaEmpresaDIRe.action#"
										+ pestania).trigger("submit");

					},
					Salir : function() {
						$(this).dialog("close");
					}
				}
			});

}


function abrirEvolucionCayc(numExpediente, destino) {

	var $dest = $("#" + destino);
	var inicio = Date.now();
	var fin = Date.now() + 1000 * 60 * 60 * 5;
	var header = {
		"typ" : "JWT",
		"alg" : "RS256",
		"x5c" : [ certificado ]
	};

	var stringifiedHeader = CryptoJS.enc.Utf8.parse(JSON.stringify(header));
	var encodedHeader = base64url(stringifiedHeader);
	var payload = {
		"iat" : 111226192,
		"exp" : 1752221392
	};
	var stringifiedpayload = CryptoJS.enc.Utf8.parse(JSON.stringify(payload));
	var encodedpayload = base64url(stringifiedpayload);
	var signature = encodedHeader + "." + encodedpayload;
	signature = CryptoJS.HmacSHA256(signature, "Clave");
	signature = base64url(signature);

	$
			.ajax({
				type : "GET",

				url : "https://se-api-dire.redsara.es/v1/private/audit",
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Authorization', 'Bearer ' + Autoriza);
					xhr.setRequestHeader('Payload', payload);
					xhr.setRequestHeader('Header', header);
					xhr.setRequestHeader('Signature', signature);
					xhr.setRequestHeader('Accept', 'application/json');
					xhr.setRequestHeader('Content-Type',
							'text/html; charset=iso-8859-1');
				},
				success : function(data) {

					$dest.append().dialog({
						modal : false,

						dialogClass : 'no-close',
						width : 850,
						height : 500,
						buttons : {
							Cerrar : function() {
								$(this).dialog("close");
							}
						}
					});

					var contador = 0;
					var string_html = '<div class="popup formularios" style="width:90%; position:absolute; top:0px;">	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">		<table width="100%">			<tbody><tr>				<td class="tabular" align="left">					<span class="titulo">Evolucion DIRe</span>				</td>			</tr>		</tbody></table>	</div>		<div id="displayTagEvolucionCaycDiv" class="divScroll">		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">			<img id="loadingImage" style="margin-left:auto;margin-right:auto;" src="img/loading.gif">		</div>		<table id="tablaEvolucionCayc" cellpadding="0" summary="Listado Evolucion Consulta " class="tablacoin" cellspacing="0"><thead>';
					string_html = string_html
							+ '<tr><th><a style="cursor: pointer;">Usuario</a></th><th><a style="cursor: pointer;">Tipo Actuacion</a></th><th><a style="cursor: pointer;">Fecha Cambio</a></th></tr></thead>'
					string_html = string_html + '<tbody>';
					for (contador = 0; contador < data.count; contador++) {
						var linea_historico = "";

						try {
							linea_historico = linea_historico + '<tr>';
							linea_historico = "<td> "
									+ data.items[contador].username
									+ "</td><td>"
									+ data.items[contador].activityType.description
									+ "</td><td>" + data.items[contador].date
									+ "</td>";
							linea_historico = linea_historico + '</tr>';
						} catch (err) {

						}
						string_html = string_html + linea_historico

					}
					;
					var string_html = string_html + "</tbody></table></thead>";

					$dest.html(string_html);
				},
				error : function(data) {
					alert("Error al conectar con INE (Direcciones)");

				}
			});

}

function Volver_EmpresaDIRe() {
	window.location.href = "consultaConsultaEmpresaDIRe.action";

}

function guardarEmpresaDIRe() {
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarAltaEmpresaDIRe.action#" + pestania)
			.trigger("submit");
}

function base64url(source) {
	encodedSource = CryptoJS.enc.Base64.stringify(source);

	encodedSource = encodedSource.replace(/=+$/, '');

	encodedSource = encodedSource.replace(/\+/g, '-');
	encodedSource = encodedSource.replace(/\//g, '_');

	return encodedSource;
}

function BuscarDireccion(iddireccion) {
	var texto = iddireccion;
	var inicio = Date.now();
	var fin = Date.now() + 1000 * 60 * 60 * 5;

	var header = {
		"typ" : "JWT",
		"alg" : "RS256",
		"x5c" : [ certificado ]
	};

	var stringifiedHeader = CryptoJS.enc.Utf8.parse(JSON.stringify(header));
	var encodedHeader = base64url(stringifiedHeader);
	var payload = {
		"iat" : 111226192,
		"exp" : 1752221392
	};
	var stringifiedpayload = CryptoJS.enc.Utf8.parse(JSON.stringify(payload));
	var encodedpayload = base64url(stringifiedpayload);

	var signature = encodedHeader + "." + encodedpayload;
	signature = CryptoJS.HmacSHA256(signature, "Clave");
	signature = base64url(signature);

	$.ajax({
		type : "GET",

		url : "https://se-api-dire.redsara.es/v1/private/ine-address?text={"
				+ texto + "}",
		beforeSend : function(xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + Autoriza);
			xhr.setRequestHeader('Payload', payload);
			xhr.setRequestHeader('Header', header);
			xhr.setRequestHeader('Signature', signature);
			xhr.setRequestHeader('Accept', 'application/json');
			xhr.setRequestHeader('Content-Type',
					'text/html; charset=iso-8859-1');
		},
		success : function(data) {
			$("#Select").empty().append('whatever');
			console.log(data);
			if (data.total == 0) {
				$("#Select").append(
						'<option value=' + data.msg + '>' + data.msg
								+ '</option>');
			} else {
				var contador = 0;

				for (contador = 0; contador < data.count; contador++) {
					var direccion_entera = "";

					try {
						direccion_entera = data.items[contador].fullAddr;
					} catch (err) {

					}

					$("#Select").append(
							"<option value=" + direccion_entera + ">"
									+ direccion_entera + "</option>");

				}
				;
			}

		},
		error : function(data) {
			alert("Error al conectar con INE (Direcciones)");

		}
	});

}

function Actualiza_Direccion() {
	$('#iddireccion').val($("#Select option:selected").text());
}

function eliminar(id) {

	$("#Contacto_id").val(id);

	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action",
			"borrar_contactoAltaEmpresaDIRe.action#" + pestania).trigger(
			"submit");
}


function eliminar_administrador(id) {

	$("#Administrador_id").val(id);

	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action",
			"borrar_administradorAltaEmpresaDIRe.action#" + pestania).trigger(
			"submit");
}


