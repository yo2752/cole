
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenedorAuto">
	 <div class="popup formularios">
		  <div class="container">
	        <span class="tituloAuto">Autorizaciones Expediente</span>
	         <ul class="radioCheck">
	      		<li>
	      			<div class="radioButtonContainer">
	      			<s:iterator value="#attr.listaAutorizados" var="radio">
				         <input  type="radio" id="idTipoAutorizar" class="tipoAutorizar" name="autorizadoSeleccionado" value='<s:property value='#radio.valor' />' title="<s:property value='#radio.tipo' />" />
				         <label for="idTipoAutorizar" class="tipoAutorizarLabel"><s:property value="#radio.tipo" /> (<s:property value="#radio.valor" />)</label><br/>
				     </s:iterator>   
			         </div> 
		    	</li>
	   		 </ul>
	    </div>
	</div> 
	<span class="parpadea text"><strong>*Autorizar trámite para cada uno de los tipos. <br>Comprobar que todos estén a SI para que cambie el estado del trámite.</strong></span>
</div>
<script>
$(document).ready(function(){
    $(".radioButtonContainer .tipoAutorizar").each(function() {
      var tipo = $(this).val();
      if (tipo === "SI") {
        $(this).next(".tipoAutorizarLabel").addClass("si-color");
        $(this).prop("disabled", true);
        $(this).next(".tipoAutorizarLabel").css("pointer-events", "none");
      } else if (tipo === "NO") {
        $(this).next(".tipoAutorizarLabel").addClass("no-color");
        $(this).prop("disabled", false);
        $(this).next(".tipoAutorizarLabel").css("pointer-events", "auto");
      }
    });
  });
</script>
<style>
.contenedorAuto{
	background-color: #9f9f9f3b;
	width: 100%;
    height: 97%;
}
.popup{
	width: 95%;
	 height: 89%;
    position: relative;
    top: 10px;
}
.container{
	width: 95%!important;
    margin: .5em 0 0 1em;
}
.tituloAuto{
    color: black;
    font-size: 1.5em;
    position: relative;
    top: 7px;
    font-weight: bold;
}
.radioCheck {
    list-style: none;
    padding: 0;
}
.radioCheck li {
	    display: inline;

	}

.radioButtonContainer {
    margin-bottom: 20px;
}
.radioButtonContainer input {
	margin-right: 10px; /* Espacio entre radio y valor adicional */
    vertical-align: middle; /* Alinear verticalmente con el contenido */
     position: relative;
     top: 8px;
     left: 10em;

}
.radioButtonContainer label {
	
    vertical-align: middle; /* Alinear verticalmente con el contenido */
     position: absolute;
 	 margin-left: -2em;
    margin-top: -4px;

}

input[type="radio"]:checked + label {
    color: black;
}

input[type="radio"]:checked + label::after {
    content: "\2714";
    font-size: 14px;
    margin-left: 5px;
    color: green;
}

.text {
  font-size:10px;
  font-family:helvetica;
  font-weight:bold;
  color:#71d90b;
  text-transform:math-auto;
}
.parpadea {
  
  animation-name: parpadeo;
  animation-duration: 1s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;

  -webkit-animation-name:parpadeo;
  -webkit-animation-duration: 1s;
  -webkit-animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
}

@-moz-keyframes parpadeo{  
  0% { opacity: 1.0; }
  50% { opacity: 0.0; }
  100% { opacity: 1.0; }
}

@-webkit-keyframes parpadeo {  
  0% { opacity: 1.0; }
  50% { opacity: 0.0; }
   100% { opacity: 1.0; }
}

@keyframes parpadeo {  
  0% { opacity: 1.0; }
   50% { opacity: 0.0; }
  100% { opacity: 1.0; }
} 

.si-color {
  color: green; /* Color para el valor "SI" */
}

.no-color {
  color: red; /* Color para el valor "NO" */
}
</style>
