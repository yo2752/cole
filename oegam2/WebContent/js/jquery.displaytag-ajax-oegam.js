/*
 * Copyright (c) 2010 CompuCloud Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Author: Jevon Gill
 */
(function($){

    $.fn.displayTagAjax = function() {
        var links = new Array();
        var container = this;
        addItemsToArray(this.find("table .sortable a"),links);
        addItemsToArray(this.find(".pagelinks a"),links);
        addItemsToArray(this.find(".totalresults a"),links);
        var waiter = this.find(":hidden:first");
        
        $.each(links,function()
            {
                var url = $(this).attr("href");
                addClickEvent(container, this,url, waiter);
                $(this).removeAttr("href");
            }
        );
        return true;
    };

  function addClickEvent(ctx, element,url, waiter){
        $(element).click(
            function(){
            	if (waiter.length){
                	waiter.css("display", "block");
            	} else {
                	loading("loadingImage");	
            	}
                jQuery.ajax(
                {
                    url: url,
                    success: function(data){
                       filteredResponse =  $(data).find(this.selector);
                       if(filteredResponse.size() == 1){
                            $(this).html(filteredResponse);
                       }else{
                    	// ha ocurrido un error indeterminado e inesperado, porque espera un div determinado... y no viene, así que muestra la pantalla completa
                    	   var newDoc = document.open("text/html", "replace");
                    	   newDoc.write(data);
                    	   newDoc.close();
                       }
                       $(this).displayTagAjax();
                    } ,
                    data: ({"time":new Date().getTime()}),
                    context: ctx
                });
            }
        );
        $(element).css("cursor","pointer");
        
    }

   function addItemsToArray(items,array){
        $.each(items,function()
            {
                    array.push(this);
            }
        );        
    }
   
})(jQuery);

function cambiarElementosPorPaginaConsulta(url, displayTagDivId, resultados) {
	var $url = url;
	if ($url.indexOf("?")<0) {
		$url += "?";
	} else {
		$url += "&";
	}
	$url += "resultadosPorPagina="+resultados;
	var ctx = $("#"+displayTagDivId);
	ajaxReloadDiv(ctx, displayTagDivId, $url);
}

/**
 * 
 * @param ctx
 * @param displayTagDivId
 * @param url
 */
function ajaxReloadDiv(ctx, displayTagDivId, url) {
	var $displayTag = $("#"+displayTagDivId);
	loading("loadingImage");
    jQuery.ajax(
    {
        url: url,
        success: function(data){
           filteredResponse =  $(data).find($("#"+displayTagDivId).selector);
           if(filteredResponse.size() == 1){
        	   $displayTag.html(filteredResponse);
           }else{
        	   // ha ocurrido un error indeterminado e inesperado, porque espera un div determinado... y no viene, así que muestra la pantalla completa
        	   var newDoc = document.open("text/html", "replace");
        	   newDoc.write(data);
        	   newDoc.close();
           }
           $displayTag.displayTagAjax();
        } ,
        data: ({"time":new Date().getTime()}),
        context: ctx
    }).always(function() {
		unloading("loadingImage");
	});
}

function doPostWithTarget(displayTagDivId, url, nodisable) {
	var status = [];
	var colors = [];
	var botones = [];
	if (nodisable == null || typeof nodisable === "undefined"  || !nodisable) {
		botones = $("input[type=button]");
		$.each(botones, function (i){
			status.push($(this).prop("disabled"));
			colors.push($(this).css("color"));
		});
		botones.attr("disabled", "disabled").css("color", "#6E6E6E");
	}
	var $displayTag = $("#" + displayTagDivId);
	loading("loadingImage");
	$.post(url, function(data) {
        filteredResponse =  $(data).find($("#"+displayTagDivId).selector);
        if(filteredResponse.size() == 1){
	   		$.each(botones, function (i){
	   			$(this).prop("disabled", status[i]);
	   			$(this).css("color", colors[i]);
	   		});
     	   $displayTag.html(filteredResponse);
        }else{
     	   // ha ocurrido un error indeterminado e inesperado, porque espera un div determinado... y no viene, así que muestra la pantalla completa
     	   var newDoc = document.open("text/html", "replace");
     	   newDoc.write(data);
     	   newDoc.close();
        }
	}).always(function() {
		unloading("loadingImage");
	});
}

/**
 * Realiza un submit del formulario y deshabilita los botones
 * 
 * @param formID
 *            id del formulario que se envia
 * @param url
 *            url a la que se realiza el post
 * @param nodisable
 * 			true si no se quiere deshabilitar los botones (ej. descarga), nulo o false si se quiere deshabilitar
 */
function doPost(formID, url, nodisable) {
	if (nodisable == null || typeof nodisable === "undefined"  || !nodisable) {
		$("input[type=button]").attr("disabled", "disabled").css("color", "#6E6E6E");
	}
	$('#'+formID).attr("action", url).trigger("submit");
}

/**
 * Realiza un submit del formulario enviando el listado de chequeados en un unico campo
 * @param formID
 *            id del formulario que se envia
 * @param url
 *            url a la que se realiza el post
 * @param checksnames
 * 			  atributo name de los checks que se envian
 * @param postname
 * 			 name del post en el que se va a enviar el listado, no debe existir ningun campo con este name. 
 * @param max
 * 			numero maximo de checks que puede marcar (null si no hay maximo) 
 * @param min
 * 			numero minimo de checks que puede marcar (null si no hay minimo) 
 * @param nodisable
 * 			true si no se quiere deshabilitar los botones (ej. descarga), nulo o false si se quiere deshabilitar
 */
function doPostWithChecked(formID, url, checksnames, postname, max, min, nodisable){
	var checks =  $("input[name='" + checksnames + "']:checked");
	
	if (max != null && Number(checks.size()) > Number(max)) {
		if (Number(max) == 1) {
			alert("Debe seleccionar un unico item");
		} else {
			alert("No puede seleccionar mas de " + max + " items");
		}
		return false;
	} else if(min != null && (checks.size()) < Number(min)) {
		if (Number(min) == 1) {
			alert("Debe seleccionar alg\u00fan item");
		} else {
			alert("Debe seleccionar al menos " + min + " items");	
		}
		return false;
	}

    var arrayCodigos = new Array();
	checks.each(function() {
        arrayCodigos.push($(this).val());
        
	});

	$("input[name='" + postname + "']").remove();
	var input = $("<input>").attr("type", "hidden").attr("name", postname).val(arrayCodigos);
	$('#'+formID).append($(input));

	doPost(formID, url, nodisable);		
}

/**
 * Saca un popup modal
 * 
 * @param targetDivId
 *            id del div donde se muestra el resultado
 * @param $url
 *            url a la que realizará la llamada post, cuyo resultado se muestra
 *            en targetDivId
 */
function popUpWithChecked(targetDivId, url, checksnames, max, min) {
	var checks =  $("input[name='" + checksnames + "']:checked");
	
	if (max != null && Number(checks.size()) > Number(max)) {
		if (Number(max) == 1) {
			alert("Debe seleccionar un unico item");
		} else {
			alert("No puede seleccionar mas de " + max + " items");
		}
		return false;
	} else if(min != null && (checks.size()) < Number(min)) {
		if (Number(min) == 1) {
			alert("Debe seleccionar alg\u00fan item");
		} else {
			alert("Debe seleccionar al menos " + min + " items");	
		}
		return false;
	}
	popUp(targetDivId, url);
}

/**
 * Saca un popup modal
 * 
 * @param targetDivId
 *            id del div donde se muestra el resultado
 * @param $url
 *            url a la que realizará la llamada post, cuyo resultado se muestra
 *            en targetDivId
 */
function popUp(targetDivId, url) {
	var $dest = $("#" + targetDivId);
	loading("loadingImage");
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

/**
 * Saca un popup modal
 * 
 * @param targetDivId
 *            id del div donde se muestra el resultado
 * @param $url
 *            url a la que realizará la llamada post, cuyo resultado se muestra
 *            en targetDivId
 */
function popUpWithoutCloseButton(targetDivId, url) {
	var $dest = $("#" + targetDivId);
	loading("loadingImage");
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : false,
				resizable: true,
				height: 'auto',
				width: '50%',
				show : {
					effect : "blind",
					duration : 300
				},
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

/**
 * Muestra el icono loading
 * 
 * @param loadingImageId
 *            id del elemento img con el icono de espera
 */
function loading(loadingImageId) {
	var $img = $("#" + loadingImageId);
	if ($img.length) {
		var $parent = $img.parent();
		if ($parent.length && $parent.is("div")) {
			$parent.css("display", "block");
		}
	}
}

/**
 * Oculta el icono loading
 * 
 * @param loadingImageId
 *            id del elemento img con el icono de espera
 */
function unloading(loadingImageId) {
	var $img = $("#" + loadingImageId);
	if ($img.length) {
		var $parent = $img.parent();
		if ($parent.length && $parent.is("div")) {
			$parent.css("display", "none");
		}
	}
}

/**
 * Borra el contenido de los inputs de tipo texto contenidos en el div con id pasado por parametro.
 * Tambien limpia los combos (asegurate de que los combos tienen un ID unico)
 * @param idFiltro
 * 			Identificador de la capa que contiene los campos a borrar
 */
function cleanInputs(idFiltro) {
	$("#" + idFiltro + " input[type=text]").attr("value", "");
	$("#" + idFiltro + " select").each(function () {
		$("#" + this.id + " option:first").attr('selected','selected');
	});
}