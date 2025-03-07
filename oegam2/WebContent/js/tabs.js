var tabsIniciadas = false;

// CSS helper functions
CSS = {
    // Adds a class to an element.
    AddClass: function (e, c) {
        if (!e.className.match(new RegExp("\\b" + c + "\\b", "i")))
            e.className += (e.className ? " " : "") + c;
    },

    // Removes a class from an element.
    RemoveClass: function (e, c) {
        e.className = e.className.replace(new RegExp(" \\b" + c + "\\b|\\b" + c + "\\b ?", "gi"), "");
    }
};

// Functions for handling tabs.
Tabs = {
    // Changes to the tab with the specified ID.
    GoTo: function (contentTabsId, skipReplace) {
        // This variable will be true if a tab for the specified
        // contentTabs ID was found.
		
		// Carlos: Parche para que funcione el cambio de pestañas con la paginación de display tag
		// desde la misma pestaña:
		var tamano = contentTabsId.length;
	    var caracterEspecial = contentTabsId.substring(tamano-1,tamano);
	    
	    if(caracterEspecial=='#'){
        	contentTabsId = contentTabsId.substring(0,tamano-1);
        }
	    
		var foundTab = false;

        // Get the TOC element.
        var toc = document.getElementById("toc");
        if (toc) {
            var lis = toc.getElementsByTagName("li");
            for (var j = 0; j < lis.length; j++) {
                var li = lis[j];

                // Give the current tab link the class "current" and
                // remove the class from any other TOC links.
                var anchors = li.getElementsByTagName("a");
                for (var k = 0; k < anchors.length; k++) {
                    if (anchors[k].hash == "#" + contentTabsId) {
                        CSS.AddClass(li, "current");
                        foundTab = true;
                        break;
                    } else {
                        CSS.RemoveClass(li, "current");
                    }
                }
            }
        }

        // Show the contentTabs with the specified ID.
        var divsToHide = [];
        var divs = document.getElementsByTagName("div");
        for (var i = 0; i < divs.length; i++) {
            var div = divs[i];

            if (div.className.match(/\bcontentTabs\b/i)) {
                if (div.id == "_" + contentTabsId)
                    div.style.display = "block";
                else
                    divsToHide.push(div);
            }
        }

        // Hide the other contentTabs boxes.
        for (var i = 0; i < divsToHide.length; i++)
            divsToHide[i].style.display = "none";

        // Change the address bar.
        if (!skipReplace) window.location.replace("#" + contentTabsId);
        window.scrollTo(0,0);
    },

    OnClickHandler: function (e) {
        // Stop the event (to stop it from scrolling or
        // making an entry in the history).
        if (!e) e = window.event;
        //Ricardo/Carlos Parche para que funcionen los tabs integrados con displayTags
        
        var nombreTab = this.hash.substring(1);
    	var tamano = nombreTab.length;
    	var caracterEspecial = nombreTab.substring(tamano-1,tamano);
        
    	if(document.getElementById("divError")){
    		var divError = document.getElementById("divError");
    		divError.style.display='none';
    	}
    	
        if(caracterEspecial=='#'){
        	e.returnValue = false;
        }
        else{
        	if (e.preventDefault) e.preventDefault(); else e.returnValue = false;
        }
        
        // Get the name of the anchor of the link that was clicked.
        Tabs.GoTo(this.hash.substring(1));
    },

    Init: function () {
    	
    	if (!document.getElementsByTagName) return;

        // Attach an onclick event to all the anchor links on the page.
        var anchors = document.getElementsByTagName("a");
        for (var i = 0; i < anchors.length; i++) {
            var a = anchors[i];
            if (a.hash) a.onclick = Tabs.OnClickHandler;
        }

        var contentTabsId;
        if (window.location.hash) contentTabsId = window.location.hash.substring(1);
        
        
        
        var divs = document.getElementsByTagName("div");
        for (var i = 0; i < divs.length; i++) {
            var div = divs[i];

            if (div.className.match(/\bcontentTabs\b/i)) {
                if (!contentTabsId) contentTabsId = div.id;
                div.id = "_" + div.id;
            }
        }
        
        
        if (contentTabsId) Tabs.GoTo(contentTabsId, true);
        
        tabsIniciadas = true;
    }
};

// Hook up the OnLoad event to the tab initialization function.
window.onload = Tabs.Init;

// Hide the contentTabs while waiting for the onload event to trigger.
var contentTabsId = window.location.hash || "#Introduction";

if (document.createStyleSheet) {
    var style = document.createStyleSheet();
    style.addRule("div.contentTabs", "display: none;");
    style.addRule("div" + contentTabsId, "display: block;");
} else {
    var head = document.getElementsByTagName("head")[0];
    if (head) {
        var style = document.createElement("style");
        style.setAttribute("type", "text/css");
        style.appendChild(document.createTextNode("div.contentTabs { display: none; }"));
		style.appendChild(document.createTextNode("div" + contentTabsId + " { display: block; }"));
        head.appendChild(style);
    }
}