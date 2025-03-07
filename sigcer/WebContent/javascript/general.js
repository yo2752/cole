function cerrarVentana(){
    window.close();
}

function irArestart(){
	location.href = "restartApplet";
}

function homePage(){
	window.location.href='about:home';
}

// Solo funciona en IE
function salir(){
	opener = null;
	self.close();
}
