<%-- <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> --%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c2"  %>
<%@taglib uri="/struts-tags" prefix="s"%>

<div class="dynamicMenu" id="dynamicMenu">

	<h3 class="ga"><span>Applications</span></h3>

	<menu:useMenuDisplayer name="CSSListMenu" bundle="global" repository="miArbol">
		<c2:forEach var="menu" items="${miArbol.topMenus}" >
			<menu-el:displayMenu name="${menu.name}"/>
		</c2:forEach>
	</menu:useMenuDisplayer>
	
	<s:if test="recientes!=null && recientes.size() > 0">
	
		<h3 class="recent"><span>Recent</span></h3>
		
		<ul class="menuList">
			<s:iterator value="recientes">
				<li><a href="<s:property value="url" />"><s:property value="desc_funcion" /></a></li>
			</s:iterator>
		</ul>
		
	</s:if>
	
	<h3 class="support"><span>Need help?</span></h3>
	<div class="contacto">
		<p style="font-size:1em;">Contact our <strong>Help Center</strong></p>
		<p style="font-size:2em;">91 541 12 13 <span style="font-size:.7em">(ext. 5)</span></p>
		<p style="font-size:1em;">or email:</p>
		<p style="font-size:1.2em;"><a href="mailto:informatica@gestoresmadrid.org" title="Contact Help Center">informatica@gestoresmadrid.org</a></p>
		<div style="font-size:1em;" id="remoteAssist">
			<a href="javascript:showRemoteAssist();" title="Request remote assistance when prompted by staff">Request remote assistance <br /> when prompted by staff</a>
			<div id="remoteAssistCont">
				<ul>
					<li><a href="https://www.ntrsupport.com/cau001" title="CAU 001" target="_blank"><img src="img/menu/remoteAssist_1.png" alt="CAU 001" /></a></li>
					<li><a href="https://www.ntrsupport.com/cau002" title="CAU 002" target="_blank"><img src="img/menu/remoteAssist_2.png" alt="CAU 002" /></a></li>
					<li><a href="https://www.ntrsupport.com/cau003" title="CAU 003" target="_blank"><img src="img/menu/remoteAssist_3.png" alt="CAU 003" /></a></li>
				</ul>
				<div class="clear">&nbsp;</div>
				<p><a href="https://eu.ntrsupport.com/nv/inquiero/components/ntrexe/default.asp?own=89116&ubd=0&beta=1&lang=es" title="Descargue el cliente" target="_blank">Download client</a></p>
			</div>
		</div>
	</div>

</div>
<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>

<script type="text/javascript">
	function fadeInColors(){
		// Fondo del li a granate
		/*
		$(this).css({
			background : '#4f0a1a'
		});
		*/
		// Texto del enlace a blanco
		$(this).find('a').first().css({
			color : '#ffffff'
		});

		fadeIn();
		
	}
	
	function fadeIn(){

		// this es el li sobre el que se ha hecho mouseover
		var li = $(this);

		// Desactivamos los otros men�s padres
		$('.menuList').each(function(){
			var parentMenu = li.closest('.menuList');
			if($(this).html()!=parentMenu.html()){
				fadeOutAll(parentMenu);
			}
		});
		$(li).addClass("hover");
		$(li).find("a").first().removeClass('bermellon');
		var parent = li.closest("ul");
		// Buscamos al padre (ul) y los hermanos del li
		var brothers = parent.children("li");
		brothers.each(function(){
			var brother = $(this);
			// Buscamos los hijos del hermano
			//alert(brother!=li);
			if(brother.html()!=li.html()){

				brother.removeClass("hover");
				if (parent.attr('class') != 'menuList') {
					brother.find("a").first().addClass('bermellon');
				}
				var cousins = brother.children("ul");
				// Rompemos la animaci�n de los primos
				cousins.each(function(){
					var cousin = $(this);
					cousin.stop().fadeIn(500);
					cousin.stop().fadeOut(0);
				});
			}
		});

		var ul = li.find("ul").first();
		if (ul.css("display") == 'none') {
			// Animando de izquierda a derecha
			ul.css({left: ul.outerWidth() - 100})
			.animate({left: 200, opacity: "toggle"}, 500);
		}
	}

	function fadeOutColors(){
		if(!$(this).attr('class').contains('hover')) {
			// Se quita el fondo del li
			$(this).css({
				background : 'none'
			});
			// El texto vuelve a granate
			$(this).find('a').first().css({
				color : '#4f0a1a'
			});
		}

		fadeOut();
		
	}
	
	function fadeOut(){
		var parent = $(this).closest("ul");
		if ( parent.attr('class') == 'menuList' && $("#dynamicMenu").find('.menuList').first().html() != parent.html()){
			$(this).removeClass("hover");
		}
	}

	function showRemoteAssist(){
		// Aparece el submen� si lo tiene
		var div = $('#remoteAssistCont');
		var parent = $('#remoteAssist');
		if($(div).attr('class')!='remoteAssistOn'){
			$(div).fadeIn(200).addClass('remoteAssistOn');
			$(parent).addClass('colored');
		} else {
			$(div).fadeOut(200).removeClass('remoteAssistOn');
			$(parent).removeClass('colored');
		}
	}

	function fadeOutAll(notMe) {
		var uls = $('#dynamicMenu ul li').find('ul');
		var lis = $("li.hover");
		if ($(notMe).length > 0) {
			uls = jQuery.grep(uls, function(a){
				return $(a).closest(".menuList").html() != notMe.html();
				});
			lis = jQuery.grep(lis, function(a){
				return $(a).closest(".menuList").html() != notMe.html();
				});
		}
		$(uls).each(function(){
			var ul = $(this);
			ul.fadeOut();
		});
		$(lis).each(function(){
			$(this).removeClass("hover");
			if ($(this).closest("ul").attr('class') != 'menuList') {
				$(this).find("a").first().addClass('bermellon');
			}
		});
	}

	function isInMenu(element) {
		return $(element).closest(".menuList").length > 0;
	}

	$(document).ready(function(){
		
		// CSS Est�ndar de cada lista (oculta, tama�o, etc...)
		$('#dynamicMenu ul li').find('ul').css({
			display : 'none',
			position : 'absolute',
			top : '-1em',
			left : '15em',
			minWidth : '18em',
			background : '#f7f7f7',
			border : '1px solid #000',
			borderRadius :'8px',
			'z-index' : '99998'
		});

		// Est�ndar de color de enlace para todos a partir del segundo nivel
		$('#dynamicMenu ul li ul li').find('a').addClass('bermellon');

		// Efecto mousover del primer nivel del �rbol
		$('#dynamicMenu ul li').hover(fadeIn, fadeOut);

		$('#dynamicMenu .menubar').find('a').css({
			fontWeight : 'normal'
		});
		$('#dynamicMenu .menubar').each(function(){
			var menubar = $(this);
			$(menubar).find('a').first().css({
				fontWeight : 'bold'
			});
		});

		$(document).on("click", function(event) {
				if (!isInMenu(event.target)) {
					fadeOutAll(null);
				}
			});
		
	});
</script>
<s:if test="%{#isAdmin==true}">
	<script type="text/javascript">
			$(document).keydown(function(e) {
		    	if(e.altKey) { 
				    switch (e.keyCode) {
				    					    
				        case 84: // ALT + T - consulta de tr�mites
				        	document.location='inicioConsultaTramiteTrafico.action';
				            break;
				        case 82: // ALT + R - Carga de cr�dito
				            document.location='listaColegiados.action';
				            break;
				        case 65: // ALT + A - Carga de tasas
				            document.location='inicioImportarTasas.action';
				            break;
				        case 67: // ALT + C - contratos
				            document.location='inicioContratoNuevo.action';
				            break;
				        case 86: // ALT + V -  consulta de veh�culos
				            document.location='inicioConsultaVehiculo.action';
				            break;
				        case 80: // ALT + P -  consulta de personas
				            document.location='inicioConsultaPersona.action';
				            break;
				        case 83: // ALT + S -  consulta solcitudes de cola
				            document.location='inicioPeticionesPendientes.action';
				            break;
				        case 88: // ALT + X -  consulta de justificantes profesionales
				            document.location='inicioJustificantesProfesionales.action';
				        break;				            
				    }
				}
			    return;
		    });
	</script>
</s:if>