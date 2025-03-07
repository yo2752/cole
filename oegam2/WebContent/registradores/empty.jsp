<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- <link rel="shortcut icon" href="img/logo.ico"> -->
		<link rel="shortcut icon" href="img/favIcon2.ico">
		<title><tiles:insertAttribute name="titulo" ignore="true" /></title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="Pragma" content="no-cache">
    	<meta http-equiv="Cache-Control" content="no-cache">
    	<meta http-equiv="Expires" content="-1">
	</head>
	<body>
		<!-- Página utilizada para 'dirigir' el return de los action cuando lo siguiente
			que hace el flujo de la aplicación es cargar el applet viafirma (control fuera de struts2) -->
	</body>
</html>