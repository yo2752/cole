<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
<head>

<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Selección de estado</title>

</head>

<body class="popup">	
<s:form method="post" name="formData" id="formData" enctype="multipart/form-data" cssStyle="overflow:hidden;">
	
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular">
						<span class="titulo">Fecha del reconocimiento</span>
					</td>
				</tr>
			</table>
		</div> <!-- div nav -->
						
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Seleccione la fecha efectiva del reconocimiento</td>
			</tr>
		</table>	
		
		<!-- Seleccionar el archivo -->
		<table class="tablaformbasica" cellspacing="3" cellpadding="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<input type="text" name="diaCita" 
									id="diaCita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<input type="text" name="mesCita" 
									id="mesCita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';"								
									size="2" maxlength="2"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<input type="text" name="anioCita" 
									id="anioCita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="4" maxlength="4"/>
							</td>
							
							<td align="left" nowrap="nowrap" width="5%" >
					    		<a href="javascript:;" 
					    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCita, document.formData.mesCita, document.formData.diaCita); resizeMe(); return false;" 
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" 
					    				width="15" height="16" 
					    				border="0" alt="Calendario"/>
					    		</a>
							</td>
							<td align="left" nowrap="nowrap" width="5%">
									<input type="text" name="horaCita" 
										id="horaCita"
										onblur="this.className='input2';" 
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" 								
										size="2" maxlength="2"/>
							</td>
								
							<td width="1%">:</td>
								
							<td align="left" nowrap="nowrap" >
									<input type="text" name="minutoCita" 
										id="minutoCita"
										onblur="this.className='input2';" 
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';"								
										size="2" maxlength="2"/>
							</td>
						</tr>
					</table>
				</td>			
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap" width="30%" align="left" style="vertical-align:middle">
					<input type="button" value="Seleccionar" class="boton" onclick="asistioOtraFecha()"/>
					&nbsp;
					<input type="button" value="Cancelar" class="boton" onclick="javascript:window.close();"/>
				</td>
			</tr>
		</table>		
		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
</s:form>
<script type="text/javascript">
// Función que se ejecuta cuando se selecciona estado en el pop up de cambiar estados de trámites
function asistioOtraFecha(){
	var anyo = document.getElementById("anioCita").value;
	var mes = document.getElementById("mesCita").value;
	var dia = document.getElementById("diaCita").value;
	var hora = document.getElementById("horaCita").value;
	var minuto = document.getElementById("minutoCita").value;
	if (anyo=="" || anyo==null || mes=="" || mes==null || dia=="" || dia==null || hora=="" || hora==null || minuto=="" || minuto==null) {
		alert("Establezca la fecha y hora a la que se realizó el reconocimiento");
		return false;
	}
	var dy= new Date(anyo, mes, dia);
	if (dy.getFullYear()!=anyo || dy.getMonth()!=mes || dy.getDate()!= dia){
		alert("Establezca una fecha correcta");
		return false;
	}
	if (!validaHora(hora+":"+minuto)) {
		alert("Establezca una hora correcta (HH:mm)");
		return false;
	}

	window.opener.invokecambiarEstadosAsistioOtraFecha(anyo, mes, dia, hora, minuto);
}

function resizeMe(){
	var $cal = $("iframe");
	var position = $("#anioCita").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top + 5) + "px"
	});
}


</script>
</body>
</html>	