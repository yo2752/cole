<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="-1"/>
	<link rel="shortcut icon" href="img/favIcon2.ico"/>
	<script src="javascript/general.js" type="text/javascript"></script>
	<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen"/>
	<link rel="stylesheet" type="text/css" media="all" href="css/xtree.css"/>
	<link rel="stylesheet" type="text/css" href="css/content-assist.css" media="all" />
	<link rel="stylesheet" type="text/css" media="all" href="css/global.css"/> 
	<title>OEGAM - Colegio Oficial de Gestores Administrativos de Madrid</title>
</head>

<body>

	<div class="cabecera_tiles">
        <%@include file="/plantillas/cabecera.jspf" %>
    </div>
            
    <div class="fondo_menu">
		<%@include file="/plantillas/menu_der.jspf" %>
	</div>
	 
    <div class="menu_tiles">
		<%@include file="/plantillas/vacio.jspf" %>
	</div>        
    <div class="principal_tiles">
    	<div class="menu_cuerpo">
		    <div class="cuerpo_tiles">
	    		<%@include file="/plantillas/mensaje.jspf" %>
	        </div>
	        <div class="pie_tiles">
	        	<%@include file="/plantillas/pie.jspf" %>
	        </div>
	    </div>
	    <div class="menu_der_tiles">
			<%@include file="/plantillas/menu_der.jspf" %>
		</div>
     </div>
</body>
</html>
