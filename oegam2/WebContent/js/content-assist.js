
var tx_utils={
    walkArray:function(b,e,c){
        if(c){
            for(var d=0,a=b.length;d<a;d++){
                if(e.call(b[d],d,b[d])===false){
                    break;
                }
            }
        }
        else{
            for(var d=b.length-1;d>=0;d--){
                if(e.call(b[d],d,b[d])===false){
                    break;
                }
            }
        }
    }
    ,trim:function(a){
        return(a||"").replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"");
    }
    ,hasClass:function(b,c){
        c=" "+c+" ";
		var a=b.className;
		return a&&(" "+a+" ").indexOf(c)>=0;
    }
    ,toggleClass:function(a,b){
        if(this.hasClass(a,b)){
            this.removeClass(a,b);
        }
        else{
            this.addClass(a,b);
        }
    }
    ,addClass:function(c,d){
        var a=[],b=this;
		this.walkArray(d.split(/\s+/g),function(e,f){
            if(f&&!b.hasClass(c,f)){
                a.push(f);
            }
        }
        );if(a.length){
            c.className+=(c.className?" ":"")+a.join(" ");
        }
    }
    ,removeClass:function(b,c){
        var a=b.className||"";
		this.walkArray(c.split(/\s+/g),function(d,e){
            a=a.replace(new RegExp("\\b"+e+"\\b"),"");
        }
        );
		b.className=this.trim(a);
    }
    ,emptyElement:function(a){
        while(a.firstChild){
            a.removeChild(a.firstChild);
        }
    }
    ,addEvent:function(e,d,c){
        var a=d.split(/\s+/);
		for(var b=0;b<a.length;b++){
            if(e.addEventListener){
                e.addEventListener(a[b],c,false);
            }
            else{
                if(e.attachEvent){
                    e.attachEvent("on"+a[b],c);
                }
            }
        }
    }
    ,removeEvent:function(e,d,c){
        var a=d.split(/\s+/);for(var b=0;b<a.length;b++){
            if(e.removeEventListener){
                e.removeEventListener(a[b],c,false);
            }
            else{
                if(e.detachEvent){
                    e.detachEvent("on"+a[b],c);
                }
            }
        }
    }
    ,normalizeEvent:function(a){
        if(!a||!a.target){
            a=window.event;a.target=a.srcElement;a.stopPropagation=function(){
                this.cancelBubble=true;
            }
            ;a.preventDefault=function(){
                this.returnValue=false;
            }
            ;
        }
        return a;
    }
    ,createElement:function(a,c){
        var b=document.createElement("div");if(c){
            b.className=c;
        }
        return b;
    }
    ,setCSS:function(d,g){
        if(!d){
            return;
        }
        var c=[],a={
            "line-height":1,"z-index":1,opacity:1
        }
        ;for(var f in g){
            if(g.hasOwnProperty(f)){
                var b=f.replace(/([A-Z])/g,"-$1").toLowerCase(),e=g[f];c.push(b+":"+((typeof(e)=="number"&&!(b in a))?e+"px":e));                
                var b=f.replace(/([A-Z])/g,"-$1").toLowerCase(),e=g[f];c.push(b+":"+((typeof(e)=="number"&&!(b in a))?e+"px":e));
            }
        }
        d.style.cssText+=";"+c.join(";");
    }
}
;var EventDispatcher=function(){
}
;EventDispatcher.prototype={
    buildListenerChain:function(){
        if(!this.listenerChain){
            this.listenerChain={
            }
            ;
        }
        if(!this.onlyOnceChain){
            this.onlyOnceChain={
            }
            ;
        }
    }
    ,addEventListener:function(c,e,d){
        if(!e instanceof Function){
            throw new Error("Listener isn't a function");
        }
        this.buildListenerChain();
		var b=d?this.onlyOnceChain:this.listenerChain;
		c=typeof(c)=="string"?c.split(" "):c;for(var a=0;a<c.length;a++){
            if(!b[c[a]]){
                b[c[a]]=[e];
            }
            else{
                b[c[a]].push(e);
            }
        }
    }
    ,hasEventListener:function(a){
        return(typeof this.listenerChain[a]!="undefined"||typeof this.onlyOnceChain[a]!="undefined");
    }
    ,removeEventListener:function(d,e){
        if(!this.hasEventListener(d)){
            return false;
        }
        var f=[this.listenerChain,this.onlyOnceChain];for(var c=0;c<f.length;c++){
            var a=f[c][d];
			for(var b=0;b<a.length;b++){
                if(a[b]==e){
                    a.splice(b,1);
                }
            }
        }
        return true;
    }
    ,dispatchEvent:function(g,e){
        this.buildListenerChain();
		if(!this.hasEventListener(g)){
            return false;
        }
        var h=[this.listenerChain,this.onlyOnceChain],b=new CustomEvent(g,this,e);
		for(var d=0;d<h.length;d++){
            var a=h[d][g];if(a){
                for(var f=0,c=a.length;f<c;f++){
                    a[f](b);
                }
            }
        }
        if(this.onlyOnceChain[g]){
            delete this.onlyOnceChain[g];
        }
        return true;
    }
}
;function CustomEvent(a,c,b){
    this.type=a;this.target=c;if(typeof(b)!="undefined"){
        this.data=b;
    }
}
(function(){
    var f=document.createElement("textarea");
	f.value="\n";var a=f.value;
	var k=!f.currentStyle;
	f=null;
	var m=document.defaultView&&document.defaultView.getComputedStyle,o=("font-family,font-size,line-height,text-indent,"+"padding-top,padding-right,padding-bottom,padding-left,"+"border-left-width,border-right-width,border-left-style,border-right-style").split(",");function i(u,w){
        var p=u.length,v=0,q=p-1;for(var s=w-1;s>0;s--){
            var t=u.charAt(s);if(t=="\n"||t=="\r"){
                v=s+1;break;
            }
        }
        for(var r=w;r<p;r++){
            var t=u.charAt(r);if(t=="\n"||t=="\r"){
                q=r;break;
            }
        }
        return{
            start:v,end:q
        }
        ;
    }
    function g(p){
        return k?p:p.replace(/\s/g,"&nbsp;&shy;");
    }
    function d(s,r){
        var p=(s||"").replace(/\r\n/g,"\n").replace(/\n\r/g,"\n").split("\n");if(r){
            for(var q=p.length;q>=0;q--){
                if(!trim(p[q])){
                    p.splice(q,1);
                }                
            }            
        }
        return p;
    }
    function l(u,v){
        var q=u.split(a),p=0,s=a.length;for(var t=0,r=q.length;t<r;t++){
            p+=q[t].length;if(t<r-1){
                p+=s;
            }
            if(v<p){
                return t;
            }            
        }
        return -1;
    }
    function b(p,r){
        var q=document.createElement(p);if(r){
            q.className=r;
        }
        return q;
    }
    function j(p){
        return p.replace(/\-(\w)/g,function(r,q){
            return q.toUpperCase();
        }
        );
    }
    function h(E,H,r){
        var u,z={
        }
        ,B,p,F=H instanceof Array;var s=/^-?\d+(?:px)?$/i,w=/^-?\d(?:\.\d+)?/,v=/\d$/,G,q;var A=F?H:[H];
		for(var C=0,y=A.length;C<y;C++){
            B=A[C];p=j(B);if(!r&&E.style[p]){
                z[B]=z[p]=E.style[p];
            }
            else{
                if(m){
                    if(!u){
                        u=window.getComputedStyle(E,"");
                    }
                    z[B]=z[p]=u&&u.getPropertyValue(B);
                }
                else{
                    if(E.currentStyle){
                        G=E.currentStyle[B]||E.currentStyle[p];
						var D=E.style||E;
						if(!s.test(G)&&w.test(G)){
                            var t=D.left,x=E.runtimeStyle.left;
							E.runtimeStyle.left=E.currentStyle.left;
							q=v.test(G)?"em":"";
							D.left=p==="fontSize"?"1em":(G+q||0);
							G=D.pixelLeft+"px";
							D.left=t;
							E.runtimeStyle.left=x;
                        }
                        z[B]=z[p]=G;
                    }
                    
                }
                
            }
            
        }
        return F?z:z[j(H)];
    }
    function c(t,w){
        if(!t){
            return;
        }
        var s=[],q={
            "line-height":1,"z-index":1,opacity:1
        }
        ;for(var v in w){
            if(w.hasOwnProperty(v)){
                var r=v.replace(/([A-Z])/g,"-$1").toLowerCase(),u=w[v];
                s.push(r+":"+((typeof(u)=="number"&&!(r in q))?u+"px":u));
            }
        }
        t.style.cssText+=";"+s.join(";");
    }
    function n(p){
        var s=b("div","tx-ca-measurer"),r=h(p,o);
		for(var q=0;q<o.length;q++){
            var t=o[q];s.style[j(t)]=r[t];
        }
        p.parentNode.appendChild(s);return s;
    }
    function e(p){
        this.measurer=p;this.width=p.offsetWidth;this.lines=[];
    }
    e.prototype={
        getLineOffset:function(q,w){
            if(!q){
                return 0;
            }
            var r=this.measurer,x=r.offsetWidth!=this.width,y=d(w),p=h(r,["padding-top","padding-bottom"]),s=parseFloat(p["padding-top"])+parseFloat(p["padding-bottom"]),z,t=0;
			for(var u=0,v=Math.min(y.length,q);u<v;u++){
                z=y[u];
				if(x||!this.lines[u]||this.lines[u].text!==z){
                    r.innerHTML=g(z||"&nbsp;");this.lines[u]={
                        text:z,height:r.offsetHeight-s
                    }
                    ;
                }
                t+=this.lines[u].height;
            }
            this.width=r.offsetWidth;return t;
        }
        ,reset:function(){
            this.lines=[];
        }
    }
    ;TextViewer=function(q){
        this.textarea=q;
		this.measurer=n(q);
		this.updateMeasurerSize();
		this.dispatcher=new EventDispatcher();
		this.line_cacher=new e(this.measurer);
		var s=-1,p=null;
		var r=this;
		tx_utils.addEvent(q,"change keyup",function(t){
            var u=q.value;if(u.length!=s||u!==p){
                r.dispatcher.dispatchEvent("modify");s=u.length;p=u;
            }
        }
        );tx_utils.addEvent(window,"focus resize",function(t){
            r.updateMeasurerSize();
        }
        );
    }
    ;TextViewer.prototype={
        getSelectionRange:function(){
            if("selectionStart" in this.textarea){
                return{
                    start:this.textarea.selectionStart,end:this.textarea.selectionEnd
                }
                ;
            }
            else{
                if(document.selection){
                    this.textarea.focus();
					var p=document.selection.createRange();if(p===null){
                        return{
                            start:0,end:this.getContent().length
                        }
                        ;
                    }
                    var q=this.textarea.createTextRange();
					var r=q.duplicate();
					q.moveToBookmark(p.getBookmark());
					r.setEndPoint("EndToStart",q);
					return{
                        start:r.text.length,end:r.text.length+p.text.length
                    }
                    ;
                }
                else{
                    return null;
                }
            }
        }
        ,setSelectionRange:function(s,p){
            if(typeof(p)=="undefined"){
                p=s;
            }
            var r=this.textarea;
            if("setSelectionRange" in r){
                r.setSelectionRange(s,p);
            }
            else{
                if("createTextRange" in r){
                    var q=r.createTextRange();
					q.collapse(true);
					q.moveStart("character",s);
					q.moveEnd("character",p-s);
					q.select();
                }
            }
        }
        ,getCaretPos:function(){
            var p=this.getSelectionRange();
			return p?p.start:null;
        }
        ,setCaretPos:function(p){
            this.setSelectionRange(p);
        }
        ,getContent:function(){
            return this.textarea.value;
        }
        ,updateMeasurerSize:function(){
            var q=h(this.getElement(),["padding-left","padding-right","border-left-width","border-right-width"]),p=parseInt(q["padding-left"])+parseInt(q["padding-right"])+parseInt(q["border-left-width"])+parseInt(q["border-right-width"]);
            if (this.textarea.offsetWidth) this.measurer.style.width=(this.textarea.offsetWidth-p)+"px";
            else this.measurer.style.width="0px";
        }
        ,getCharacterCoords:function(v){
            var u=this.getContent(),q=i(u,v);
			this.measurer.innerHTML=g(u.substring(q.start,v))+"<i>"+(this.getChar(v)||".")+"</i>";var p=this.measurer.getElementsByTagName("i")[0],r={
                x:p.offsetLeft,y:p.offsetTop
            }
            ;
			var t=d(u.substring(0,q.start)).length;t=Math.max(0,t-1);
			var s=this.line_cacher.getLineOffset(t,u);
			tx_utils.emptyElement(this.measurer);
			return{
                x:r.x,y:r.y+s
            }
            ;
        }
        ,getAbsoluteCharacterCoords:function(p){
            var q=this.getCharacterCoords(p);
			return{
                x:this.textarea.offsetLeft+q.x-this.textarea.scrollLeft,y:this.textarea.offsetTop+q.y-this.textarea.scrollTop
            }
            ;
        }
        ,getChar:function(p){
            return this.getContent().charAt(p);
        }
        ,getElement:function(){
            return this.textarea;
        }
        ,replaceText:function(t,u,q){
            var r=(typeof u!="undefined"),p=(typeof q!="undefined"),s=this.getContent();
			if(!r&&!p){
                u=0;q=s.length;
            }
            else{
                if(!p){
                    q=u;
                }
            }
            this.textarea.value=s.substring(0,u)+t+s.substring(q);
        }
        ,addEvent:function(t,s){
            var q=t.split(/\s+/),u=this.getElement();
			for(var r=0,p=q.length;r<p;r++){
                if(q[r].toLowerCase()=="modify"){
                    this.dispatcher.addEventListener("modify",s);
                }
                else{
                    tx_utils.addEvent(u,t,s);
                }
            }
        }
        ,removeEvent:function(t,s){
            var q=t.split(/\s+/),u=this.getElement();
			for(var r=0,p=q.length;r<p;r++){
                if(q[r].toLowerCase()=="modify"){
                    this.dispatcher.removeEventListener("modify",s);
                }
                else{
                    tx_utils.removeEvent(u,t,s);
                }
            }
        }
    }
    ;
}
)();function ContentAssist(f,d,j){
	this.viewer=f;
	this.options={
        visible_items:10
    }
    ;this.setProcessor(d);
	this.setOptions(j);
	this.popup=tx_utils.createElement("div","tx-ca-popup");
	this.popup_content=tx_utils.createElement("div","tx-ca-popup-content");
	this.additional_info=tx_utils.createElement("div","tx-ca-additional-info");
	this.popup.appendChild(this.popup_content);
	this.popup.appendChild(this.additional_info);
	f.getElement().parentNode.appendChild(this.popup);
	this.processor=null;this.is_visible=false;
	this.last_proposals=[];
	this.is_hover_locked=false,this.hover_lock_timeout=null;
	this.selected_class="tx-proposal-selected";
	this.selected_proposal=0;
	if(d){
        this.setProcessor(d);
    }
    var c=this.popup,h=this.popup_content,g=this;
	this.hidePopup();
	f.addEvent("modify",function(k){
        g.showContentAssist(k);
    }
    );
	var i=!!window.opera,b=/mac\s+os/i.test(navigator.userAgent),a=i&&b;function e(k){
        if(k.preventDefault){
            k.preventDefault();
        }
        else{
            k.returnValue=false;
        }
        
    }
    f.addEvent(i?"keypress":"keydown",function(k){
        k=tx_utils.normalizeEvent(k);if(g.is_visible){
            switch(k.keyCode){
                case 38:g.selectProposal(Math.max(g.selected_proposal-1,0));
						g.lockHover();k.preventDefault();
						break;
				case 40:g.selectProposal(Math.min(g.selected_proposal+1,h.childNodes.length-1));
						g.lockHover();k.preventDefault();
						break;
				case 13:g.applyProposal(g.selected_proposal);
						g.hidePopup();
						k.preventDefault();
						break;
				case 27:g.hidePopup();
						k.preventDefault();
						break;
            }
            
        }
        else{
            if(k.keyCode==32&&(k.ctrlKey&&!a||k.metaKey&&a||k.altKey)){
                g.showContentAssist();
				k.preventDefault();
            }
        }
    }
    );
	f.addEvent("blur",function(){
        setTimeout(function(){
            g.hidePopup();
        }
        ,200);
    }
    );
	tx_utils.addEvent(h,"mouseover",function(l){
        if(g.is_hover_locked){
            return;
        }
        l=tx_utils.normalizeEvent(l);
		var m=g.searchProposal(l.target);
		if(m){
            var k=g.findProposalIx(m);
			if(k!=-1){
                g.selectProposal(k,true);
            }
        }
    }
    );
	tx_utils.addEvent(h,"click",function(l){
        l=tx_utils.normalizeEvent(l);
		var m=g.searchProposal(l.target);
		if(m){
            var k=g.findProposalIx(m);
			if(k!=-1){
                g.applyProposal(k);g.hidePopup();
            }
        }
    }
    );
	tx_utils.addEvent(h,"mousedown",function(k){
        k=tx_utils.normalizeEvent(k);
		k.preventDefault();
    }
    );
	tx_utils.addEvent(this.additional_info,"scroll",function(k){
        g.hideAdditionalInfo();
    }
    );
}
ContentAssist.prototype={
    setProcessor:function(a){
        this.processor=a;
    }
    ,setOptions:function(a){
        if(a){
            for(var b in this.options){
                if(this.options.hasOwnProperty(b)){
                    if(b in a){
                        this.options[b]=a[b];
                    }
                }
            }
        }
    }
    ,searchProposal:function(a){
        do{
            if(tx_utils.hasClass(a,"tx-proposal")){
                break;
            }
        }
        while(a=a.parentNode);return a;
    }
    ,findProposalIx:function(c){
        var a=-1,e=c.parentNode.childNodes;
		for(var d=0,b=e.length;d<b;d++){
            if(e[d]==c){
                a=d;
				break;
            }
        }
        return a;
    }
    ,applyProposal:function(a){
        if(this.popup_content.childNodes[a]){
            this.last_proposals[a].apply(this.viewer);
        }
    }
    ,showPopup:function(a,c){
        this.popup.style.display="block";this.popup.style.top=c+"px";
		var b=this.viewer.getElement();

		a += 5;

		this.popup.style.left=a+"px";
		this.is_visible=true;
		this.lockHover();
    }
    ,hidePopup:function(){
        this.popup.style.display="none";
		this.hideAdditionalInfo();
		this.is_visible=false;
    }
    ,lockHover:function(){
        if(this.hover_lock_timeout){
            clearTimeout(this.hover_lock_timeout);
        }
        this.is_hover_locked=true;var a=this;setTimeout(function(){
            a.is_hover_locked=false;
        }
        ,100);
    }
    ,showContentAssist:function(){
        if(this.processor){
            var f=this.processor.computeCompletionProposals(this.viewer,this.viewer.getCaretPos());if(f){
                var h=0,a=0,d=0;
				this.popup.style.display="block";
				tx_utils.emptyElement(this.popup_content);
				for(var c=0,b=f.length;c<b;c++){
                    var g=f[c].toHtml();
					this.popup_content.appendChild(g);
					h=f[c].offset;
					if(this.options.visible_items>0&&c<this.options.visible_items){
                        a+=g.offsetHeight;
                    }
                    d+=g.offsetHeight;
                }
                if(d>a){
                    tx_utils.addClass(this.popup,"tx-ca-popup-overflow");
                }
                else{
                    tx_utils.removeClass(this.popup,"tx-ca-popup-overflow");
                }
                var e=this.viewer.getAbsoluteCharacterCoords(h);
				this.showPopup(e.x,e.y);
				this.popup_content.style.height=a?a+"px":"auto";
				this.last_proposals=f;
				this.selected_proposal=0;
				this.selectProposal(this.selected_proposal);
            }
            else{
                this.hidePopup();
            }
        }
    }
    ,showAdditionalInfo:function(a){
        var b=this.last_proposals[a];
		if(b&&b.getAdditionalInfo()){
            var e=this.popup_content.childNodes[a],c=this.additional_info;
			c.innerHTML=b.getAdditionalInfo();
			tx_utils.removeClass(c,"tx-ca-additional-info-left");
			tx_utils.setCSS(c,{
                display:"block",top:e.offsetTop-this.popup_content.scrollTop
            }
            );
			var d=this.viewer.getElement();
			if(c.offsetLeft+c.offsetWidth+this.popup.offsetLeft>d.offsetLeft+d.offsetWidth){
                tx_utils.addClass(c,"tx-ca-additional-info-left");
            }
        }
    }
    ,hideAdditionalInfo:function(){
        tx_utils.setCSS(this.additional_info,{
            display:"none"
        }
        );
    }
    ,selectProposal:function(d,g){
        if(this.popup_content.childNodes[this.selected_proposal]){
            tx_utils.removeClass(this.popup_content.childNodes[this.selected_proposal],this.selected_class);
        }
        if(this.popup_content.childNodes[d]){
            var e=this.popup_content.childNodes[d];
			tx_utils.addClass(e,this.selected_class);
			if(!g){
                var f=e.offsetTop,c=e.offsetHeight,b=this.popup_content.scrollTop,a=this.popup_content.offsetHeight;
				if(f<b){
                    this.popup_content.scrollTop=f;
                }
                else{
                    if(f+c>b+a){
                        this.popup_content.scrollTop=f+c-a;
                    }
                }
            }
            this.showAdditionalInfo(d);
        }
        this.selected_proposal=d;
    }
    
}
;function ContentAssistProcessor(a,d){
	if (d!=undefined) {
		this.setMayusculas(d);
	}
    if(a){
        this.setWords(a);
    }
    
}
ContentAssistProcessor.prototype={
    computeCompletionProposals:function(d,a){
        var k=a-1,g="",f="";
		while(k>=0&&this.isAllowedChar(f=d.getChar(k))){
            g=f+g;k--;
        }
		if (this.mayusculas) {
			g = g.toUpperCase();
		}
        var e=a;
		while(e<1000&&this.isAllowedChar(d.getChar(e))){
            e++;
        }
        var b=null,h=this.suggestWords(g);        
		if(h.length){
            b=[];
			for(var c=0,l=h.length;c<l;c++){
                var m=h[c];
				var j=this.completitionProposalFactory(m,a-g.length,e-k-1,a-g.length+m.length);
				b.push(j);
            }
        }
        return b;
    }
    ,completitionProposalFactory:function(d,c,a,b){
        return new CompletionProposal(d,c,a,b);
    }
    ,getActivationChars:function() {
        return "abcdefghijklmnopqrstuvwxyz!@$";
    }
    ,isAllowedChar:function(a){
        a=String(a);
		if(!a){
			if (" "==a) return true;
            return false;
        }
		if (" "==a) return true;
        var b = /[\s,\!\?\#%\^\$\{\}<>]/;
		return !b.test(a);
    }
	,setMayusculas:function(m){
		this.mayusculas = m;
	}
    ,setWords:function(e){
        var c={};

		for(var b=0,a=e.length;b<a;b++) {
            var d = e[b].charAt(0);
            if(!(d in c)){
                c[d]=[];
            }
            c[d].push(e[b]);
        }
        this.words={
        }
        ;this.words=c;
    }
    ,setKeys:function(e,k){
        var c={
        }
        ;
		for(var b=0,a=e.length;b<a;b++){
            var d=e[b].charAt(0);if(!(d in c)){
                c[d]=[];
            }
            c[d].push(k[b]);
        }
        this.keys={
        }
        ;this.keys=c;
    }
    ,setKeys:function(e,k){
        var c={
        }
        ;
		for(var b=0,a=e.length;b<a;b++){
            var d=e[b].charAt(0);if(!(d in c)){
                c[d]=[];
            }
            c[d].push(k[b]);
        }
        this.keys={
        }
        ;this.keys=c;
    }
    ,suggestWords:function(f){
        f=String(f);
		var a=[];
		if(f&&this.words){
            var d=f.charAt(0),c=f.length;
			if(d in this.words){
                var h=this.words[d];
				for(var e=0,b=h.length;e<b;e++){
                    var g=h[e];
					if(g.indexOf(f)===0&&g.length>=c){
                        a.push(g);
                    }
                }
            }
        }
        return a;
    }
}
;function CompletionProposal(d,c,a,b){
    this.str=d;
	this.offset=c;
	this.len=a;
	this.cursor=b;
}
CompletionProposal.prototype={
    getDisplayString:function(){
        return this.str;
    }
    ,getAdditionalInfo:function(){
        return"";
    }
    ,apply:function(a){
        a.replaceText(this.str,this.offset,this.offset+this.len);
		a.setCaretPos(this.cursor);
    }
    ,toString:function(){
        return this.str;
    }
    ,toHtml:function(){
        var a=document.createElement("div");
		a.className="tx-proposal";
		a.appendChild(document.createTextNode(this.getDisplayString()));
		return a;
    }
}
;function BasicContentAssist (a,c,b,d) {
    this.viewer=new TextViewer(a);
	this.processor=new ContentAssistProcessor(c,d);
	this.content_assist=new ContentAssist(this.viewer,this.processor,b);
}