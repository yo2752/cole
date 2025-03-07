var interval;

	function initCabecera(){
		interval = window.setInterval("actualizacion_reloj()", 20000); 
	}

    function obtenerNotificacion(){
    	var notifLabel = document.getElementById("online");
    	var selectDiv = notifLabel.style.color; 
    	var selectDivblink = notifLabel.style.textDecoration;
    	var idUsuario = document.getElementById('idUsuarioSession').value;
   	
    	var url="updateNotificacionesAjax.action?idUsuario="+idUsuario+"&selectedOption="+selectDiv+"&selectDivblink="+selectDivblink;

    	var req = NuevoAjax();
    	req.onreadystatechange = function() {
    		if (req.readyState == 2) {
    			
    		}
    		if (req.readyState == 4) {
    			if (req.status == 200) {
    				var color = req.responseText;
    				notifLabel.style.color = color;
    				if (color=="red") {
    					notifLabel.style.textDecoration = "blink";
    				} else {
    					notifLabel.style.textDecoration = "none";
    				}
    			}
    			if(req.status == 500){	
    				clearInterval(interval);
    			}
    		}

    	};
    	req.open("POST", url, true);
    	req.send(null);
		
	}
    
    function actualizacion_reloj() {
    	//obtenerNotificacion();
    }  