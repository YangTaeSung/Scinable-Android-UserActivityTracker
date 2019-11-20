if (typeof Scinable !== 'object') {
    Scinable = (function() {
        var cookieEnabled = navigator.cookieEnabled;
        var scinableObject = window.scinableObject || "_scq";
        var _scq = window[scinableObject] || [];
        var Scinable = window.Scinable = {
                accountId: '',
                language: null,
                debug: false,
                domainName: null,
                uid: null,
                vid: null,
                ck: null,
                campaign: '',
                channel: '',
                preVisitDate: null,
                frequency: 0,
                newVisit: 0,
                offline: '',
                pageView: 1,
                visitTime: null,
                customField: {}
        }

        Scinable.Config = {
                language: 'ja',
                host: window._host,
                cvExpire: 1800000,   //30min(30 * 60 * 1000)
                cuExpire: 63072000000,  //2years(2 * 365 * 24 * 60 * 60 * 1000)
                ccExpire: 63072000000,
                czExpire: 63072000000
        }

        Scinable.Param = {
                discard: 'sci_dc',
                refl: 'sci_refl',
                eciCampaign: 'sci_campaign',
                campaign: null,
                channel: 'sci_ch'
        }

        Scinable.regional = [];

        Scinable.regional['en'] = {
                closeText: 'Close'
        }
        Scinable.regional['ja'] = {
                closeText: '閉じる'
        }

        // addEventListener와 attachEvent로 나누는 건 브라우저 차이
        /*
        
        addEventListener는 IE9,파이어폭스,오페라,사파리,크롬
        attachEvent는 IE8 및 오페라
        addEventListener가 존재하면 addEventListener 사용하고
        없으면 attachEvent 사용해라
        
        ex) object.addEventListener('event', function, boolean);
        첫 번째 인자는 click이나 load와 같은 이벤트, 두 번째 인자는 핸들러 함수, 세 번째 인자는 이벤트를 처리하는 방식을 말하는데, 여기에는 케스케이드다운(cascade-down)과 버블업(bubble-up)의 두 가지 방식이 있다. 세 번째 인자에 false값을 주면 이벤트 리스너가 버블업 방식으로 동작하고, true값을 주면 케스케이드다운 방식으로 동작한다.
        
        */
        Scinable.Event = {
                add: (window.addEventListener ?
                    function(element, type, func) {
                          element.addEventListener(type, func, false);
                    } :
                    function(element, type, func) {
                        element.attachEvent('on' + type, func);
                    }
                ),
                //
                stop: function(event) {
                    var evt = event || window.event; // IE Only
                    if (evt.stopPropagation) { // stopPropagation()은 상위버전용
                        evt.stopPropagation();
                    } else {
                        evt.cancelBubble = true; // cncelBubble은 IE Only이다. 이벤트가 다음 대상으로 이동하지 못하게 한다.('버블링'은 이벤트가 내부에서 외부로 이동하는 것을 말하며 IE9 이하에서 유일한 이동방법이다.)
                    }
                }
        }

        /*
        
        Scinable.Request 첫번째 function은 메소드로 만듦.
        getElementsByTagName() 메소드는 지정된 태그 이름을 가진 문서의 모든 요소 콜렉션을 NodeList 오브젝트로
        리턴합니다. NodeList 오브젝트는 노드 콜렉션을 나타냅니다. 노드는 색인 번호로 액세스 할 수 있습니다.
        인덱스는 0에서 시작합니다. -팁- NodeList 객체의 length 속성을 사용하여 지정된 태그 이름을 가진 요소 수를
        결정한 다음 모든 요소를 반복하고 원하는 정보를 추출 할 수 있습니다.
        
        */
        Scinable.Request = function(fullUrl) {
            this.fullUrl = fullUrl;
            this.headLoc = document.getElementsByTagName("head").item(0);
            this.scriptId = 'ScinableId' + Scinable.Request.scriptCounter++;
        }

        Scinable.Request.scriptCounter = 1;
        
        // 사용자가 이전에 사이트를 방문하지 않은 경우 브라우저에는 사이트에 캐시 된 파일이 없으므로 서버에서 모든 파일을 가져옵니다.
        // 사용자가 사이트를 방문한 적이 있는 경우 브라우저는 웹 서버에서 HTML 페이지를 검색하지만 정적 자산(JavaScript,CSS,이미지)에 대해서는 캐시를 참조합니다.
        Scinable.Request.prototype.buildScriptTag = function(browerCache) {
            this.scriptObj = document.createElement("script");
            this.scriptObj.setAttribute("type", "text/javascript");
            this.scriptObj.setAttribute("charset", "utf-8");
            this.scriptObj.setAttribute("id", this.scriptId);
            if(browerCache) {
                this.scriptObj.setAttribute("src", this.fullUrl);
            } else {
                this.scriptObj.setAttribute("src", this.fullUrl + '&_r=' + Math.floor(Math.random() * 0x7fffffff));
            }
        }

        Scinable.Request.prototype.removeScriptTag = function() {
            try {
                this.headLoc.removeChild(this.scriptObj);
            } catch(e){}
        }

        Scinable.Request.prototype.addScriptTag = function() {
            this.headLoc.appendChild(this.scriptObj);
        }

        
        /*

        AJAX : AJAX란 비동기 자바스크립트와 XML (Asynchronous JavaScript And XML)을 말합니다.
        간단히 말하면, 서버와 통신하기 위해 XMLHttpRequest 객체를 사용하는 것을 말합니다. JSON, XML, HTML 그리고
        일반 텍스트 형식 등을 포함한 다양한 포맷을 주고 받을 수 있습니다. AJAX의 강력한 특징은 페이지
        전체를 리프레쉬 하지 않고서도 수행 되는 "비동기성"입니다. 이러한 비동기성을 통해 사용자의
        Event가 있으면 전체 페이지가 아닌 일부분만을 업데이트 할 수 있게 해줍니다.
        AJAX의 주요 두가지 특징 : 페이지 새로고침 없이 서버에 요청, 서버로부터 데이터를 받고 작업을 수행
        
        페이지 새로고침 없이 서버에 요청해주는부분 안드로이드에서 이 기능을 어떻게 구현할 것이냐.

        */
        Scinable.Ajax = {
            xmlHttp:null,
            callback:null
        }

        
        /*

        XMLHttpRequest(XHR) 객체는 웹 브라우저가 서버와 상호작용하기 위하여 사용됩니다.
        전체 페이지의 새로고침없이도 URL 로부터 데이터를 받아올 수 있습니다. 웹브라우저가 백그라운드에서 서버와 계속 통신합니다.
        이는 웹 페이지가 사용자가 하고 있는 것을 방해하지 않으면서 페이지의 일부를 업데이트할 수 있도록 해줍니다.
        XMLHttpRequest 는 AJAX 프로그래밍에 주로 사용됩니다.
        XMLHttpRequest 는 이름으로만 봐서는 XML 만 받아올 수 있을 것 같아 보이지만,
        모든 종류의 데이터를 받아오는데 사용할 수 있습니다. 또한 HTTP 이외의 프로토콜도 지원합니다(file 과 ftp 포함).

        현재 대부분의 주요 웹 브라우저는 XMLHttpRequest 객체를 내장하고 있습니다.
        이러한 XMLHttpRequest 객체를 생성하는 방법은 브라우저의 종류에 따라 다음과 같이 나눠집니다.
        1. XMLHttpRequest 객체를 이용한 방법
        2. ActiveXObject 객체를 이용한 방법
        익스플로러 7과 그 이상의 버전, 크롬, 파이어폭스, 사파리, 오페라에서는 XMLHttpRequest 객체를 사용합니다.
        하지만 익스플로러의 구형 버전인 5와 6 버전에서는 ActiveXObject 객체를 사용해야 합니다.

        // XMLHttpRequest() 생성자는 XMLHttpRequest 를 초기화합니다. 다른 모든 메소드 호출 이전에 호출되어야 합니다.
        // XMLHttpRequest.onreadystatechange : readyState 어트리뷰트가 변경될 때마다 호출되는 이벤트핸들러입니다.
        // XMLHttpRequest.readyState : readyState == 4 은 작업이 완전히 완료됨을 나타냅니다.
        // XMLHttpRequest.open(method, url) : get 또는 post 메소드 및 url을 지정하여 요청을 엽니다.
        // XMLHttpRequest.send() : 요청을 보냅니다. 요청이 비동기인 경우(기본값), 이 메소드는 요청이 보내진 즉시 반환합니다.


        Older Browsers (IE5 and IE6)
        Old versions of Internet Explorer (5/6) use an ActiveX object instead of the XMLHttpRequest object:

        * variable = new ActiveXObject("Microsoft.XMLHTTP"); *
        To handle IE5 and IE6, check if the browser supports the XMLHttpRequest object, or else create an ActiveX object:

        < Example >
        if (window.XMLHttpRequest) {
          // code for modern browsers
           xmlhttp = new XMLHttpRequest();
        } else {
          // code for old IE browsers
          xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }


        // XMLHttpRequest.responseText : 응답받은 데이터를 스트링으로 리턴한다.
        // callback : 콜백 함수의 동작 방식은 일종의 식당 자리 예약과 같습니다.
        일반적으로 맛집을 가면 사람이 많아 자리가 없습니다. 그래서 대기자 명단에 이름을 쓴 다음에 자리가 날 때까지 주변 식당을 돌아다니죠.
        만약 식당에서 자리가 생기면 전화로 자리가 났다고 연락이 옵니다. 그 전화를 받는 시점이 여기서의 콜백 함수가 호출되는 시점과 같습니다.
        손님 입장에서는 자리가 날 때까지 식당에서 기다리지 않고 근처 가게에서 잠깐 쇼핑을 할 수도 있고 아니면
        다른 식당 자리를 알아볼 수도 있습니다.

        즉 responseText로 받은 스트링 데이터를 jsonObject에 저장한 후 callback으로 jsonObject를 돌려놓기

        */
        // 위의 Example 코드와 비슷한 내용. 브라우저 실행에 관련한 내용인 듯
        Scinable.Ajax.createXMLHttp = function(){
            var request;
            try {
                request = new XMLHttpRequest();
            } catch (trymicrosoft) {
                try {
                    request =  new ActiveXObject("Msxml2.XMLHTTP");
                } catch (othermicrosoft) {
                    try {
                        request = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (failed) {
                        request = null;
                    }
                }
            }
            return request;
        }

        Scinable.Ajax.createObject = function(){
            if(Scinable.Ajax.xmlHttp.readyState == 4){
                if(Scinable.Ajax.xmlHttp.status == 200){
                    eval("var jsonObject = " + Scinable.Ajax.xmlHttp.responseText);
                    eval(Scinable.Ajax.callback + "(jsonObject)");
                }else{
                    Scinable.Ajax.xmlHttp.onreadystatechage = null;
                }
            }
        }

        Scinable.Ajax.execute = function(url, fncname){
            this.callback = fncname;
            if(url != null){
                try{
                    this.xmlHttp = this.createXMLHttp();
                    if(this.xmlHttp){
                        this.xmlHttp.onreadystatechange = this.createObject;
                        this.xmlHttp.open("GET", url);
                        this.xmlHttp.send("");
                    }else{
                        if(Scinable.debug) {
                            console.log("cannot create xmlHttp.");
                        }
                    }
                }catch(e){
                    if(Scinable.debug) {
                        console.log("exception at using xmlHttp." + e.getMessage);
                    }
                }
            }
        }

        Scinable.Ranking = {}

        Scinable.Ranking.Config = {
                uri: '/rank',
                url: '',
                limit: '10',
                callback: 'getRanking',
                cache: true,
                cacheTime: '60'
        }

        Scinable.Ranking.callback = function(result) {
            if(result != null) {
                eval(Scinable.Ranking.Config.callback + "(result)");
            }
        }

        Scinable.Suggest = {
            tag: '',
            param: ''
        }

        Scinable.Search = {}

        Scinable.Search.Config = {
                uri: '/search',
                url: '',
                limit: '10',
                callback: 'getSearch',
                cache: true
        }

        Scinable.Search.callback = function(result) {
            if(result != null) {
                eval(Scinable.Search.Config.callback + "(result)");
            }
        }

        Scinable.Realtime = {}

        Scinable.Realtime.Config = {
                uri: '/realtime',
                callback: 'getRealtime'
        }

        Scinable.Realtime.callback = function(result) {
            if(result != null) {
                eval(Scinable.Realtime.Config.callback + "(result)");
            }
        }

        /*************************************************************************************************** suggest **********************************************************************************/
        // suggest에 HTML 나와 일단 뭔지 코드 봐보고 안드로이드에서 구현해야 하는 건지 살펴보기
        
        /* The factory method that creates suggestion object.*/
        // Suggestion object id
        var $$sgid$$ = 1;
        function createSuggest() {
            var suggest = {
                    input: undefined,
                    button: undefined,
                    div: undefined,
                    oldText: '',
                    inputText: '',
                    cline : 0,
                    tline : 0,
                    iline : 0,
                    edited : false,
                    req: null
            }
            
            // Assign suggestion object to window object because event handlers need to call it in global scope.
            // 이벤트 핸들러가 전역에서 호출되어야하기 때문에 suggestion 객체를 윈도우 객체에 할당하라.
            var suggestionObjectPostfix = "Suggest" + ($$sgid$$++);
            window.Scinable[suggestionObjectPostfix] = suggest; // 전역객체 Scinable 배열에 suggest객체를 할당한다?
            var suggestionObjectName = "Scinable." + suggestionObjectPostfix;

            suggest.Config = {
                    item : false,
                    input: 'q',
                    button: 'btnSearch',
                    uri: '/scinable/suggest',
                    url: '',
                    callback: suggestionObjectName + '.get',
                    cache: true,
                    close: false,
                    border: '1px solid #808080',
                    background: '#FFFFFF',
                    fontSize: '14',
                    color: '#000000',
                    scrollColor: '#E3EDF5',
                    highlightColor: '#0066CC',
                    padding: '3',
                    zindex: '100000',
                    top: undefined,
                    topBoost: 0,
                    width: undefined,
                    widthBoost: -5,
                    adjustCss: false
            }

            suggest.createArea = function() {
                // Document.createElement() 메서드는 지정한 tagName의 HTML 요소를 만들어 반환합니다. 
                var o = document.createElement("div");
                o.className = '_sc_suggest';

                o.style.visibility= 'hidden';
                o.style.height= 'auto';
                o.style.position= 'absolute';
                o.style.border= suggest.Config.border;
                o.style.background= suggest.Config.background;
                o.style.overflow = 'auto';
                o.style.padding = 0;

                o.style.zIndex= suggest.Config.zindex;
                o.style.fontSize= suggest.Config.fontSize;
                o.style.color= suggest.Config.color;
                o.style.textAlign= 'left';
                o.style.display = 'inline';

                suggest.input.parentNode.appendChild(o);
                suggest.div = o;
            }

            suggest.changeCaptureEvent = function() {
                var newText = suggest.input.value;
                if(newText != suggest.oldText && suggest.edited) {
                    suggest.call('k');
                    suggest.oldText = newText;
                    suggest.inputText = newText;
                }
                //items suggest start
                else if(suggest.Config.item && newText != suggest.inputText) {
                    suggest.call('i');
                    suggest.inputText = newText;
                }
                //items suggest end

                setTimeout(suggest.changeCaptureEvent, 500);
            }

            suggest.keydown = function(e) {
                var keyCode
                if(window.event != null) {
                    keyCode = e.keyCode;
                } else if(e.which) { // Netscape/Firefox/Opera
                    keyCode = e.which;
                }

                if(keyCode != null) {
                    if(keyCode ==  9)   return;     //Tab
                    if(keyCode == 16)   return;     //Shift
                    if(keyCode == 17)   return;     //Ctrl
                    if(keyCode == 18)   return;     //Alt
                    if(keyCode == 45)   return;     //Ins
                    if(keyCode == 32)   return;     //Space
                    if(keyCode == 33)   return;     //PgUp
                    if(keyCode == 34)   return;     //PgDn
                    if(keyCode == 35)   return;     //End
                    if(keyCode == 36)   return;     //Home
                    if(keyCode == 37)   return;     //←
                    if(keyCode == 39)   return;     //→
                    if(keyCode == 240)  return;    //Caps

                    if(keyCode == 13)  {  //Enter
                        if (suggest.button == null || typeof(suggest.button)=="undefined") {
                            ;
                        } else if (suggest.button.click){
                            if(suggest.button.type != 'submit') {
                                suggest.button.click();
                            }
                        }
                        return;
                    }

                    if(keyCode == 38) { //↑
                        if(document.getElementById("_astr0") != null) {
                            suggest.cline--;
                            if(suggest.cline < 1) suggest.cline = suggest.tline;
                            setTimeout(suggest.select, 10);
                        }
                        return;
                    }

                    if(keyCode == 40) { //↓
                        if(document.getElementById("_astr0") != null) {
                            suggest.cline++;
                            if(suggest.cline > suggest.tline) suggest.cline = 1;
                            setTimeout(suggest.select, 10);
                        }
                        return;
                    }

                    suggest.edited = true;
                }
            }

            suggest.call = function(t) {
                if(t == 'k') {
                    suggest.cline = 0;
                    suggest.tline = 0;
                }
                var qtext = Scinable.Util.trim(suggest.input.value);
                if(qtext != '') {
                    suggest.req = new Scinable.Request(suggest.Config.url + '&aid=' + Scinable.accountId + '&q=' + encodeURIComponent(qtext) + '&t=' + t + '&i=' + suggest.Config.item + Scinable.Suggest.tag + Scinable.Suggest.param);
                    suggest.req.buildScriptTag(suggest.Config.cache);
                    suggest.req.addScriptTag();
                } else {
                    suggest.remove();
                }
            }

            suggest.getItem = function(result) {
                var s = [];
                if(result.items) {
                    var ic= result.items.length;
                    if(ic > 0) {
                        suggest.iline = ic;
                        s.push(suggest.Config.TableStart);
                        for(i=0; i<ic; ++i) {
                            s.push("<tr id=_astr" + (suggest.tline + i) + " onclick=Scinable.Util.go('");s.push(result.items[i].url);s.push("') onmouseover=" + suggestionObjectName + ".over(" + (suggest.tline + i) + ") onmouseout=" + suggestionObjectName + ".out(" + (suggest.tline + i) + ")><td height=55 width='55' valign='middle' style='padding:" + suggest.Config.padding + ";' >");
                            s.push("<img src='");s.push(result.items[i].img);s.push("' width=50 height=50 border=0 />");
                            s.push("</td><td valign='middle'>");
                            s.push(result.items[i].title);
                            s.push("</td></tr>");
                        }
                        s.push(suggest.Config.TableEnd);
                    }
                }
                return s.join("");
            }

            suggest.get = function(result) {
                if(result) {
                    if(result.type && result.type == 'i') {
                        var el = document.getElementById("_sitm");
                        if(el) {
                            el.innerHTML = suggest.getItem(result);
                        }
                    } else {
                        if(result.response.length == 0 && result.items.length == 0) {
                            suggest.remove();
                        } else {
                            suggest.setOffsets();
                            suggest.tline = result.response.length;
                            var s = [];

                            //keyword suggest start
                            s.push("<div class=\'_sc_suggest_keyword\'>")
                            s.push(suggest.Config.TableStart);
                            for(i=0; i<suggest.tline; ++i) {
                                s.push('<tr id=_astr' + i + ' onclick=' + suggestionObjectName + '.click(' + i + ') onmouseover=' + suggestionObjectName + '.over(' + i + ') onmouseout=' + suggestionObjectName + '.out(' + i + ')><td align=left style=\'padding:' + suggest.Config.padding + ';\' >');
                                s.push('<input type=hidden id=_astw' + i + ' value="' + Scinable.Util.removeBTag(result.response[i].words) + '">');
                                if(suggest.Config.adjustCss) {
                                    s.push(Scinable.Util.adjustCss(result.response[i].words, suggest));
                                } else {
                                    s.push(result.response[i].words);
                                }
                                s.push('</td></tr>');
                            }
                            s.push(suggest.Config.TableClose);
                            s.push(suggest.Config.TableEnd);
                            s.push("</div>");
                            //keyword suggest end

                            //item suggest start
                            s.push("<div class='_sc_suggest_item' id='_sitm'>");
                            s.push(suggest.getItem(result));
                            s.push("</div>");
                            //item suggest end

                            suggest.div.innerHTML = s.join("");
                            suggest.div.style.visibility = "visible";
                        }
                    }
                }

                if(suggest.req) {
                    suggest.req.removeScriptTag();
                }
            }

            suggest.over = function(c_l) {
                var tl = suggest.tline + suggest.iline;
                for(var i=0;i<tl;i++) {
                    if(document.getElementById("_astr" + i) != null) {
                        if(i == c_l) {
                            document.getElementById("_astr" + i).style.backgroundColor=suggest.Config.scrollColor;
                        } else {
                            document.getElementById("_astr" + i).style.backgroundColor="";
                        }
                    }
                }
            }

            suggest.out = function(c_l) {
                if(document.getElementById("_astr" + i) != null) {
                    document.getElementById("_astr" + i).style.backgroundColor="";
                }
            }

            suggest.select = function() {
                suggest.edited = false;
                var c_l = suggest.cline-1;
                for(var i=0;i<suggest.tline;i++) {
                    if(i == c_l) {
                        if(document.getElementById("_astr" + i) != null) {
                            document.getElementById("_astr" + i).style.backgroundColor=suggest.Config.scrollColor;
                            suggest.change(i);
                        }
                    } else {
                        if(document.getElementById("_astr" + i) != null) {
                            document.getElementById("_astr" + i).style.backgroundColor="";
                        }
                    }
                }
            }

            suggest.click = function(i) {
                this.change(i);
                if (suggest.button == null || typeof(suggest.button)=="undefined") {
                    ;
                } else if (suggest.button.click){
                    suggest.button.click();
                }
            }

            suggest.change = function(i) {
                suggest.edited = false;
                suggest.cline = i+1;
                suggest.input.value = document.getElementById("_astw" + i).value;
            }

            suggest.remove = function() {
                suggest.div.style.visibility = "hidden";
            }

            suggest.setOffsets = function() {
                suggest.div.style.top = suggest.Config.top + 'px';
                suggest.div.style.width = suggest.Config.width + 'px';
                if(Scinable.Util.msie < 10) {
                    suggest.div.style.left = 0;
                } else {
                    suggest.div.style.left = suggest.input.offsetLeft + 'px';
                }
            }

            suggest.start = function() {
                /*
                    getElementById () 메소드는 지정된 값을 가진 ID 속성을 가진 요소를 리턴합니다.
                    이 방법은 HTML DOM에서 가장 일반적인 방법 중 하나이며 문서의 요소를 조작하거나 정보를 얻을 때마다 거의 사용됩니다.
                    지정된 ID를 가진 요소가 없으면 null을 반환 합니다.
                    ID는 페이지 내에서 고유해야합니다. 그러나 지정된 ID를 가진 둘 이상의 요소가 존재하는 경우 getElementById () 메소드는 소스 코드에서 첫 번째 요소를 리턴합니다.
                */
              suggest.input = document.getElementById(suggest.Config.input);
              if(suggest.input) {
                suggest.input.setAttribute('autocomplete','off');
                suggest.button = document.getElementById(suggest.Config.button);
                suggest.createArea();
                suggest.changeCaptureEvent();

                //Event
                Scinable.Event.add(suggest.input ,'keydown', suggest.keydown);
                Scinable.Event.add(window ,'resize', suggest.setOffsets);
                Scinable.Event.add(document ,'click', suggest.remove);

                //Style
                suggest.Config.TableStart = '<table width=100% cellpadding=0 cellspacing=0 border=0 style=\'font-size:' + suggest.Config.fontSize + ';color:' + suggest.Config.color + ';\'>';

                var closeText = Scinable.regional[Scinable.Config.language].closeText;
                suggest.Config.TableClose = suggest.Config.close?'<tr onclick=\'Scinable.Event.stop(event)\'><td width=70% align=left></td><td width=30% align=right><a href=\'javascript:' + suggestionObjectName + '.remove();\' style=\'font-size:14px;color:#808080;\'>' + closeText + '</a></td></tr>':'';
                suggest.Config.TableEnd = '</table>';
                suggest.Config.FontStart = '<font color=\'' + suggest.Config.highlightColor + '\'>';
                suggest.Config.FontEnd = '</font>';

                if (typeof(suggest.Config.width)=='undefined') {
                    suggest.Config.width = Scinable.Util.elementWidth(suggest.input) + suggest.Config.widthBoost;
                }

                if (typeof(suggest.Config.top)=='undefined') {
                    suggest.Config.top = suggest.input.offsetHeight +  suggest.Config.topBoost;
                }

                //url
                if(Scinable.Config.host != null) {
                    suggest.Config.url = (document.location.protocol=="https:"?"https://":"http://") + Scinable.Config.host + suggest.Config.uri + '?callback=' + suggest.Config.callback;
                }
              }
            }
            return suggest;
        }

        
        /*************************************************************************************************** Util *************************************************************************************/
        // 사용자 정보, 쿠키 설정?
        Scinable.Util = {
                /*
                    자바스크립트를 이용해서 특정 URL로 접속했을 때 다른 URL로 이동시킬 수 있습니다,
                    ex) window.location.href = 'http://www.abc.com/';
                    (안드로이드에서는 액티비티의 변환이 이루어져야 됨)
                    location.href는 객체의 속성이며, loaction.replace()는 메서드(함수)로 작동된다.
                    href는 페이지를 이동하는 것이기 때문에 뒤로가기 버튼을 누른경우 이전 페이지로 이동이 가능하지만,
                    replace는 현재 페이지를 새로운 페이지로 덮어 씌우기 때문에 이전 페이지로 이동이 불가능하다.
                    href는 일반적인 페이지 이동시 이용을 하면 되고,
                    replace의 경우는 이전페이지로 접근이 필요없는경우 보안상 덮어씌우는 것도 괜찮을듯 하다.
                */
                go : function(val) {
                    location.href = val;
                },
                encodeURI : function(val) { // URL 인코딩은, URL 스트링에 있는 텍스트를, 모든브라우저에서 똑바로 전송하기 위해 존재한다.
                /*    URI Decode & Encode 
                    encodeURI() : 일반 문자열을 퍼센트 인코딩된 문자열로 변환
                    decodeURI() : 인코딩된 문자열을 일반 문자열로 변환

                    우리가 검색창에 검색을 하던, 혹은 다른 액션을 취하던 URL을 입력 시, 아래와 같이 
                    문자를 encode하여 데이터를 전송하게 됩니다.
                    예 : hello zum! => hello+zum%21
                    예 : 아이유 => %EC%95%84%EC%9D%B4%EC%9C%A0

                    URL 인코딩은,
                    URL 스트링에 있는 텍스트를, 모든브라우저에서 똑바로 전송하기 위해 존재한다.
                    인터넷에서의 URL 은 ASCII 문자열을 이용해서만 전송될 수 있는데, 그렇지 않게 전송한 경우, 브라우저의 특성에 따라, 
                    question mark(?), ampersand(&), 슬래쉬(/), 공백문자 같은 특수문자의 경우, 잘리거나 (의도치 않게) 변형이 될 수 있다.
                    그래서, 이런 특수문자는, 인코딩이 되는 것이 좋다. ASCII 에 포함되지 않는 문자들(한글, 일본어 등등)은 더더욱 
                    encoding 이 필요하다. 인코딩은 %octet 형태로 만들어 주는 것이다. 
                    (예 : # 는 %23) - 영어로 escapted octet 이라 부른다. */
                    return encodeURIComponent(val);
                },
                // 브라우저의 버전을 리턴하는 함수.
                msie : (function() {
                    var ver = navigator.appVersion.toLowerCase();
                    return (ver.indexOf('msie')>-1)?parseInt(ver.replace(/.*msie[ ]/,'').match(/^[0-9]+/)):undefined;
                })(),
                readScriptFile : function(src) {
                    // createXMLHttp : XMLHttpRequest() 객체를 생성.
                    // 정상적으로 생성되면 객체를 리턴. 아래의 함수에서 객체가 존재하면 open, send함
                    var xh = Scinable.Ajax.createXMLHttp();
                    if (!xh) {
                        console.log("couldn't create XMLHttpRequest.");
                        return;
                    }
                    xh.onreadystatechange = function() {
                        if (xh.readyState == 4) {
                            if (xh.status != 200) {
                                console.log("Error : status is not 200." + xh.status)
                                return;
                            }
                            (0, eval)(xh.responseText);
                        }
                    };
                    xh.open("GET", src, true);
                    xh.send();
                },
                elementWidth: function(element) {
                    // HTMLElement.offsetWidth 읽기 전용 속성은 정수로 요소의 레이아웃 폭을 돌려줍니다.
                    // var intElemOffsetWidth = element.offsetWidth;
                    if(Scinable.Util.msie < 10){
                        return element.offsetWidth;
                    } else {
                        return element.clientWidth;
                    }
                },
                trim : function(value) {
                    return value.replace(/^\s+/g, "");
                },
                defaultString : function(value) {
                    return (value || '');
                },
                adjustCss : function(val, suggest) {
                    val = val.replace(/<B>/ig, suggest.Config.FontStart);
                    val = val.replace(/<\/B>/ig, suggest.Config.FontEnd);
                    return val;
                },
                removeBTag : function (str) {
                    return str.replace(/<\/*B>/ig,"");
                },
                htmlEscape : (function(){
                    var map = {"<":"&lt;", ">":"&gt;", "&":"&amp;", "'":"&#39;", "\"":"&quot;", " ":"&nbsp;"};
                    var replaceStr = function(s){ return map[s]; };
                    return function(str) { return str.replace(/<|>|&|'|"|\s/g, replaceStr); };
                })(),
                paramValues: null,
                getQueryString : function(){
                    var result = {};
                    if(1 < window.location.search.length){
                        var query = window.location.search.substring(1);
                        var parameters = query.split('&');
                        for(var i=0; i < parameters.length; i++) {
                            try {
                                var element = parameters[i].split('=');
                                var paramName = decodeURIComponent(element[0]);
                                var paramValue = decodeURIComponent(element[1]);
                                result[paramName] = paramValue;
                            } catch (e) {}
                        }
                    }
                    return result;
                },
                getQueryArray : function(p){
                    var sp = [];
                    for (var name in p) {
                        var v = p[name];
                        if(Array.isArray(v)) {
                            for(var i = 0; i < v.length; i++) {
                               sp.push(name + "=" + encodeURIComponent(v[i]));
                            }
                        } else {
                            sp.push(name + "=" + encodeURIComponent(v));
                        }
                    }
                    return sp;
                },

                today : function() {
                    var date = new Date();
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();

                    if (m < 10) m = "0" + m;
                    if (d < 10) d = "0" + d;

                    return y.toString() + m.toString() + d.toString();
                },
                getParameter : function(name) {
                    if(Scinable.Util.paramValues == null) {
                        Scinable.Util.paramValues = Scinable.Util.getQueryString();
                    }
                    var _param = Scinable.Util.paramValues[name];
                    if(_param ) {
                        return _param;
                    } else {
                        return '';
                    }
                },
                getCookie : function(name){
                    if(cookieEnabled) {
                        var start = document.cookie.indexOf(name + "=");
                        var len = start + name.length + 1;
                        if ((!start) && (name != document.cookie.substring(0, name.length))) return '';
                        if (start == -1) return '';
                        var end = document.cookie.indexOf(";",len);
                        if (end == -1) end = document.cookie.length;
                        return unescape(document.cookie.substring(len, end));
                    } else {
                        return '';
                    }
                },
                setCookie : function(name, value, expires) { //1 day = 24 * 60 * 60 * 1000
                    if(cookieEnabled) {
                        var str = name + "=" + escape(value) + ";";
                        if(Scinable.domainName) {
                            str += " domain=" + Scinable.domainName + ";";
                        }
                        str += " path=/";
                        if (expires) {
                            var nowtime = new Date().getTime();
                            expires = new Date(nowtime + expires);
                            expires = expires.toGMTString();
                            str += "; expires=" + expires;
                        }
                        document.cookie = str;
                    }
                },
                createUUID : function() {
                    return Math.round(2147483647 * Math.random());
                },
                getVid : function() {
                    if(Scinable.offline) return 0;

                    if (Scinable.vid) {
                        return Scinable.vid;
                    } else {
                        if(cookieEnabled) {
                            this.getUid();

                            var cv = Scinable.Util.getCookie("___cv");
                            var cvArr = [];
                            var cookieCampaign = '';
                            var cookieChannel = '';

                            if(cv) {
                                cvArr = cv.split('.');

                                if(cvArr.length > 2) {
                                    cookieCampaign = cvArr[2];
                                }
                                if(cvArr.length > 5) {
                                    cookieChannel = cvArr[5];
                                }
                            }

                            var sciCampaign;

                            if(Scinable.campaign) {
                                sciCampaign = Scinable.campaign;
                            } else {
                                sciCampaign = Scinable.Util.getParameter(Scinable.Param.eciCampaign);
                                if(sciCampaign == '' && Scinable.Param.campaign != null) {
                                    sciCampaign = Scinable.Util.getParameter(Scinable.Param.campaign);
                                }
                            }

                            if(sciCampaign) {
                                if(sciCampaign != cookieCampaign) {
                                    cv = null; //new visit
                                }
                                Scinable.campaign = sciCampaign;
                            } else {
                                if(cookieCampaign) {
                                    Scinable.campaign = cookieCampaign;
                                }
                            }

                            var sciChannel;
                            if(Scinable.channel) {
                                sciChannel = Scinable.channel;
                            } else {
                                sciChannel = Scinable.Util.getParameter(Scinable.Param.channel);
                            }

                            if(sciChannel) {
                                if(sciChannel != cookieChannel) {
                                    cv = null; //new visit
                                }
                                Scinable.channel = sciChannel;
                            } else {
                                if(cookieChannel) {
                                    Scinable.channel = cookieChannel;
                                }
                            }

                            //////////////////
                            //new visit
                            //////////////////
                            if (!cv) {
                                //update cu(visit date, frequency)
                                Scinable.frequency = parseInt(Scinable.frequency) + 1;
                                Scinable.Util.setCU(Scinable.uid, this.today(), Scinable.frequency);

                                //create cv
                                Scinable.vid = Scinable.Util.createUUID();
                                Scinable.visitTime = new Date().getTime();
                                Scinable.pageView = 1;
                                Scinable.newVisit = 1;

                                cv = Scinable.vid + "." + Scinable.preVisitDate + "." + sciCampaign + "." + Scinable.visitTime + ".1." + sciChannel;
                            } else {
                                Scinable.vid = cvArr[0];

                                if(cvArr.length < 5) { //old version
                                    Scinable.visitTime = new Date().getTime();
                                    Scinable.pageView = 1;
                                } else {
                                    Scinable.visitTime = cvArr[3];
                                    Scinable.pageView = parseInt(cvArr[4]) + 1;
                                }

                                cv = Scinable.vid + "." + Scinable.preVisitDate + "." + cookieCampaign + "." + Scinable.visitTime + "." + Scinable.pageView + "." + cookieChannel;
                            }

                            Scinable.Util.setCookie("___cv", cv, Scinable.Config.cvExpire);

                            return Scinable.vid;
                        } else {
                            //disabled cookie, no campaign measurement
                            return Scinable.Util.createUUID();
                        }
                    }
                },
                getUid : function() {
                    if(Scinable.offline) return 0;

                    if (Scinable.uid) {
                        return Scinable.uid;
                    } else {
                        if(cookieEnabled) {
                            var cuArr = this.getCU();

                            Scinable.uid = cuArr[0];
                            Scinable.preVisitDate = cuArr[1];
                            Scinable.frequency = cuArr[2];

                            return Scinable.uid;
                        } else {
                            Scinable.uid = this.createUUID();
                            Scinable.preVisitDate = this.today();
                            Scinable.frequency = "0";

                            return Scinable.uid;
                        }
                    }
                },
                getCU : function() {
                    var needSet = false;

                    var cuArr = [];
                    var cu = Scinable.Util.getCookie("___cu");
                    if (cu) {
                        cuArr = cu.split('.');
                        if(cuArr.length == 3) {
                            if(!this.isNumeric(cuArr[0])) {
                                needSet = true;
                                cuArr[0] = this.createUUID();
                            }
                            if(!this.isNumeric(cuArr[1])) {
                                needSet = true;
                                cuArr[1] = this.today();
                            }
                            if(!this.isNumeric(cuArr[2])) {
                                needSet = true;
                                cuArr[2] = "1";
                            }
                        } else {
                            cuArr = [];
                            needSet = true;
                            cuArr.push(this.createUUID());
                            cuArr.push(this.today());
                            cuArr.push("1");
                        }
                    } else {
                        needSet = true;
                        cuArr.push(this.createUUID());
                        cuArr.push(this.today());
                        cuArr.push("0");
                    }

                    if(needSet) {
                        this.setCU(cuArr[0],cuArr[1],cuArr[2]);
                    }

                    return cuArr;
                },
                setCU : function(uid, cday, freq) {
                    if(!this.isNumeric(uid)) {
                        uid = Scinable.Util.createUUID();
                    }
                    if(!this.isNumeric(cday)) {
                        cday = this.today();
                    }
                    if(!this.isNumeric(freq)) {
                        freq = '1';
                    }

                    var cu = uid + "." + cday + "." + freq;

                    Scinable.Util.setCookie("___cu", cu, Scinable.Config.cuExpire);

                    return cu;
                },
                getCk : function() {
                    if (Scinable.ck) {
                        return Scinable.ck;
                    } else {
                        var ck='';
                        var cc = Scinable.Util.getCookie("___cc");
                        var carr = cc.split('.');
                        if(carr.length==4) {
                            ck = carr[0];
                        }
                        Scinable.ck = ck;
                        return ck;
                    }
                },
                isNumeric : function(val) {
                    return !isNaN(parseFloat(val)) && isFinite(val);
                },
                getAge : function(birthday){
                    var today = new Date();
                    today = today.getFullYear()*10000+today.getMonth()*100+100+today.getDate();
                    return(Math.floor((today-birthday)/10000));
                },
                getAgeGroupKey : function(birthday) {
                    if(!Scinable.Util.isNumeric(birthday)) return 0;

                    var age = Scinable.Util.getAge(birthday);
                    if(age < 20){
                        return 2;
                    } else if(age < 30){
                        return 3;
                    } else if(age < 40){
                        return 4;
                    } else if(age < 50){
                        return 5;
                    } else if(age < 60){
                        return 6;
                    } else if(age < 70){
                        return 7;
                    } else if(age < 80){
                        return 8;
                    } else {
                        return 9;
                    }
                },
                setR : function(cname, val, expire) {
                    var c = Scinable.Util.getCookie(cname);
                    if(c) {
                        var cs = c.split(".");
                        if(cs.length > 10) {
                            cs.shift();
                        }
                        cs.push(val);
                        c = cs.join('.');
                    } else {
                        c = val;
                    }
                    Scinable.Util.setCookie(cname, c, expire);
                },
                setViewItem : function(skey, msize, val) {
                    if(window.localStorage !== undefined){
                        var c = localStorage.getItem(skey);
                        if(c) {
                            var cs = c.split(".");
                            for(var i=0; i<cs.length; i++) {
                                if(val == cs[i]) {
                                    cs.splice(i, 1);
                                }
                            }
                            if(cs.length >= msize) {
                                cs.shift();
                            }
                            cs.push(val);
                            c = cs.join('.');
                        } else {
                            c = val;
                        }
                        localStorage.setItem(skey, c);
                    }
                },
                getLs : function(skey) {
                    if(window.localStorage !== undefined){
                        return localStorage.getItem(skey);
                    } else {
                        return null;
                    }
                },
                closeWindow : function(el){
                      return (elem=Scinable.Util.closest(el, '.sci-window')).parentNode.removeChild(elem);
                },
                closest : function(node, selector) {
                    return (node.closest || function(_selector) {
                        do {
                            if ((node.matches || node.msMatchesSelector).call(node, _selector)) {
                                return node;
                            }
                            node = node.parentElement || node.parentNode;
                        } while (node !== null && node.nodeType === 1);

                        return null;
                    }).call(node, selector);
                   }
        }

//      WEB_PUSH
        Scinable.WebPush = {}

        Scinable.WebPush.Config = {
                host: null,
                webPushUri: '/webpush',
                cache: false
        }

//      LINE
        Scinable.Line = {}

        Scinable.Line.Config = {
                host: null,
                lineUri: '/line',
                cache: false
        }

//		RECOMMEND
        Scinable.Recommend = {
                index: null,
                View: '1',
                Order: '2'
        }

        Scinable.Recommend.Config = {
                host: null,
                accessUri: '/recommend/access',
                recommendUri: '/recommend',
                rankUri: '/recommend/rank',
                limit: '10',
                paramItemCount: 2,
                callback: '_getRecommend',
                cache: true
        }

        Scinable.Recommend.callback = function(result) {
            if(result != null) {
                window[Scinable.Recommend.Config.callback](result);
            }
        }

        window._getRecommend = function(result) {
            var id = result.renderId;
            var html = result.html;

            var e = document.getElementById(id);
            e.innerHTML = html;

            var div = document.createElement("div");
            div.innerHTML = html;
            var scripts = div.getElementsByTagName("script");

            for (var len = scripts.length, i = 0; i < len; i++) {
                var script = scripts[i];
                if (script.src) {
                    Scinable.Util.readScriptFile(script.src);
                } else {
                    (0, eval)(script.innerHTML)
                }
            }
        };

//		OLAP
        Scinable.Trans = {
                req: null,
                type: 'C',
                order: [],
                items: [],
                member: [],
                claim: []
        }

        Scinable.Trans.Config = {
                host: null
        }

        Scinable.getOrderData = function() {
            if(!Scinable.debug) {
                var cz = Scinable.Util.getCookie("___cz");
                if(Scinable.Trans.order[0] == cz) {
                    return '';
                } else {
                    Scinable.Util.setCookie("___cz", Scinable.Trans.order[0], Scinable.Config.czExpire);
                }
            }

            var arr = [];
            for (var i = 0; i < Scinable.Trans.items.length; ++i) {
                var rowArr = [Scinable.Trans.type];
                arr.push(rowArr.concat(Scinable.Trans.order,Scinable.Trans.items[i]));
            }

            var jstr = '{"type":"order", "data":' + JSON.stringify(arr) + '}';

            if(Scinable.debug) {
                console.log(jstr);
            }

            Scinable.Trans.order = [];
            Scinable.Trans.items = [];
            return jstr;
        }

        Scinable.getMemberData = function() {
            if(!Scinable.debug) {
                if(Scinable.Trans.type == 'C') {
                    var cz = Scinable.Util.getCookie("___cz");
                    if(Scinable.Trans.member[0] == cz) {
                        return;
                    } else {
                        Scinable.Util.setCookie("___cz", Scinable.Trans.member[0], Scinable.Config.czExpire);
                    }
                }
            }

            var arr = [];
            var rowArr = [Scinable.Trans.type];
            arr.push(rowArr.concat(Scinable.Trans.member));

            var jstr = '{"type":"member", "data":' + JSON.stringify(arr) + '}';

            if(Scinable.debug) {
                console.log(jstr);
            }

            Scinable.Trans.member = [];
            return jstr;
        }

        Scinable.getClaimData = function() {
            var arr = [];
            for (var i = 0; i < Scinable.Trans.claim.length; ++i) {
                var rowArr = [Scinable.Trans.type];
                arr.push(rowArr.concat(Scinable.Trans.claim[i]));
            }

            var jstr = '{"type":"claim", "data":' + JSON.stringify(arr) + '}';

            if(Scinable.debug) {
                console.log(jstr);
            }

            Scinable.Trans.claim = [];
            return jstr;
        }


//		Access
        Scinable.Access = {
                req: null,
                url: null,
                title: null,
                id: '',	//id
                groupId: '', //group id
                type: '',
                cgk: '', //conversion goal key
                cgv: '', //conversion goal value
                cgc: '' //conversion goal custom,
        }

        Scinable.Access.Config = {
                uri: '/access',
                cache: false,
                callback: '_getPromotion'
        }

        Scinable.Access.callback = function(result) {
            if(result != null) {
                window[Scinable.Access.Config.callback](result);
            }
        }

        window._getPromotion = function(result) {
            for(var idx = 0 ; idx < result.length; idx++) {
                var promotion = result[idx]; // result는 배열, 위의 for문에서 result.length는 배열의 길이를 반환
                var id = promotion.renderId;
                var rk = promotion.rk;
                var dr = promotion.dr;
                var html = promotion.html;

                if(id) {
                    var e = document.getElementById(id);
                    if(e) {
                       e.innerHTML = html;
                    } else {
                       return;
                    }
                } else {
                    var e = document.createElement("div");
                    document.body.appendChild(e);
                    e.innerHTML = html;
                }

                if(dr == '2') {
                    Scinable.Util.setR("___cup", rk, Scinable.Config.cuExpire);
                } else if(dr == '1') {
                    Scinable.Util.setR("___cvp", rk, Scinable.Config.cvExpire);
                }

                var div = document.createElement("div");
                div.innerHTML = html;
                var scripts = div.getElementsByTagName("script");

                for (var len = scripts.length, i = 0; i < len; i++) {
                    var script = scripts[i];
                    if (script.src) {
                        Scinable.Util.readScriptFile(script.src);
                    } else {
                        (0, eval)(script.innerHTML)
                    }
                }
            }
        };

        Scinable.getFreq = function() {
            if (Scinable.frequency) {
                return Scinable.frequency;
            } else {
                Scinable.Util.getVid();
                if(Scinable.frequency) {
                    return Scinable.frequency;
                } else {
                    return 1;
                }
            }
        }

        Scinable.getPreVisitDate = function() {
            if(Scinable.preVisitDate) {
                return Scinable.preVisitDate;
            } else {
                Scinable.Util.getVid();
                if(Scinable.preVisitDate) {
                    return Scinable.preVisitDate;
                } else {
                    return Scinable.Util.today();
                }
            }
        }

        Scinable.getPageUrl = function() {
            if(Scinable.Access.url) {
                return Scinable.Access.url;
            } else {
                // window.location.pathname속성은 현재 페이지의 경로 이름을 반환합니다.
                // 현재 페이지가 안드로이드에선 액티비티
                // window.location.search속성은 현재 페이지 URL 검색 정보
                // 검색정보도 URL의 한 부분, 즉 현재 페이지의 URL을 리턴하는 듯.
                return window.location.pathname + window.location.search;
            }
        }

        Scinable.getPageTitle = function() {
            if(Scinable.Access.title) {
                return Scinable.Access.title;
            } else {
                return Scinable.Util.defaultString(document.title);
            }
        }

        // document.referrer : 링크를 통해 현재 페이지로 이동 시킨, 전 페이지의 URI 정보를 반환.
        // 페이지로 바로 접근하였을 경우 이 값은 빈 문자열을 반환함.(링크를 통해서 온것이 아닌, 예를 들면, 북마크를 통해서 이동했을 경우). 
        // 문자열만을 반환하기 때문에, 참조 페이지(referring page)에 대한 DOM 액세스가 제공되지 않음. 
        // 일단 넘김.
        Scinable.getReferrer = function() {
            var referrer = '';
            try {
                referrer = window.top.document.referrer; 
            } catch (e) {
                if (window.parent) {
                    try {
                        referrer = window.parent.document.referrer;
                    } catch (e2) {
                        referrer = '';
                    }
                }
            }
            if (referrer === '') {
                referrer = document.referrer;
            }
            return referrer;
        }

        Scinable.getLang = function() {
            if (Scinable.language) {
                return encodeURIComponent(Scinable.language);
            } else {
                return navigator.language;
                // NavigatorLanguage.language 읽기 전용 속성은 사용자의 기본 언어 (일반적으로 브라우저 UI의 언어)를 나타내는 문자열을 반환합니다.
            }
        }

        function Tracker() {
            var _setDebug = function(p) {
                Scinable.debug = p;
            }

            var _setAccount = function(p) {
                Scinable.accountId = p;
            }

            var _setDomainName = function(p) {
                if(p.split(".").length-1 == 1) {
                    Scinable.domainName = "." + p;
                } else {
                    Scinable.domainName = p;
                }
            }

            var _setLanguage = function(p) {
                Scinable.language = p;
            }

            var _setSessionCookieTimeout = function(timeout) {
                Scinable.Config.cvExpire = timeout;
                Scinable.Config.czExpire = timeout;
            }

            var _setVisitorCookieTimeout = function(timeout) {
                Scinable.Config.cuExpire = timeout;
                Scinable.Config.ccExpire = timeout;
            }

            //access
            var _trackPageview = function() {
                if(!cookieEnabled) {
                    return;
                }

                var vid = Scinable.Util.getVid();
                var uid = Scinable.Util.getUid();

                var ck='', ak='0', gk='0', cl='';
                var cc = Scinable.Util.getCookie("___cc");
                var carr = cc.split('.');
                if(carr.length==4) {
                    ck = carr[0];
                    ak = carr[1];
                    gk = carr[2];
                    cl = carr[3];
                }

                var json = "";
                if(Scinable.Trans.order.length > 0) {
                    json = Scinable.getOrderData();
                } else if(Scinable.Trans.member.length > 0) {
                    json = Scinable.getMemberData();
                } else if(Scinable.Trans.claim.length > 0) {
                    json = Scinable.getClaimData();
                }


                var sdt = parseInt((new Date().getTime() - Scinable.visitTime) / 1000);

                var urlarr = [
                           document.location.protocol=="https:"?"https://":"http://",
                                           Scinable.Config.host,
                                           Scinable.Access.Config.uri,
                                           '?vid=', vid,
                                           '&uid=', uid,
                                           '&ua=', encodeURIComponent(navigator.userAgent),
                                           '&p=', encodeURIComponent(Scinable.getPageUrl()),
                                           '&dt=', encodeURIComponent(Scinable.getPageTitle()),
                                           '&cgk=', Scinable.Access.cgk,
                                           '&cgv=', Scinable.Access.cgv,
                                           '&cgc=', encodeURIComponent(Scinable.Access.cgc),
                                           '&pid=', encodeURIComponent(Scinable.Access.id),
                                           '&pgid=', encodeURIComponent(Scinable.Access.groupId),
                                           '&pat=', encodeURIComponent(Scinable.Access.type),
                                           '&sr=', window.screen.width + 'x' + window.screen.height,
                                           '&la=', Scinable.getLang(),
                                           '&fr=', Scinable.getFreq(),
                                           '&pv=', Scinable.getPreVisitDate(),
                                           '&ref=', encodeURIComponent(Scinable.getReferrer()),
                                           '&cid=', Scinable.campaign,
                                           '&ck=', encodeURIComponent(ck),
                                           '&ak=', ak,
                                           '&gk=', gk,
                                           '&cl=', encodeURIComponent(cl),
                                           '&nv=', Scinable.newVisit,
                                           '&at=', Scinable.offline,
                                           '&cc=', encodeURIComponent(cc),
                                           '&aid=', Scinable.accountId,
                                           '&jd=', encodeURIComponent(json),
                                           '&eid=', Scinable.channel,
                                           '&spv=', Scinable.pageView,
                                           '&sdt=', sdt,
                                           '&vp=','.', Scinable.Util.getCookie("___cvp"),'.',
                                           '&up=','.', Scinable.Util.getCookie("___cup"),'.',
                                           '&vc=','.', Scinable.Util.getCookie("___cvc"),'.'
                                           ];

                for(key in Scinable.customField){
                    var cp = Scinable.customField[key];
                    if(cp) {
                        urlarr.push('&cp', key, '=', encodeURIComponent(cp));
                    }
                }

                var url = urlarr.join("");

                if(Scinable.debug) {
                    console.log(url + " (length:" + url.length + ")");
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(Scinable.Access.Config.cache);
                req.addScriptTag();
            }
            _trackPageview.needsDomReady = true;

            var _trackCustom = function() {
                if(!cookieEnabled) return;

                var ck = Scinable.Util.getCk();
                if(!ck) return;

                var url = [
                      document.location.protocol=="https:"?"https://":"http://",
                      Scinable.Config.host,
                      Scinable.Access.Config.uri,
                      '?vid=', Scinable.Util.getVid(),
                      '&uid=', Scinable.Util.getUid(),
                      '&ua=', encodeURIComponent(navigator.userAgent),
                      '&p=', encodeURIComponent(Scinable.getPageUrl()),
                      '&ck=', encodeURIComponent(ck),
                      '&nv=', Scinable.newVisit,
                      '&aid=', Scinable.accountId,
                      '&at=', 'custom'
                ];

                var jo = arguments[0];

                url.push('&ct' + "=" + jo["action"]); //callType

                for (i = 1; i < 4; i += 1) {
                    var tv = jo[i];
                    if(tv) {
                        url.push('&t' + i + "=" + encodeURIComponent(tv));
                    }
                }

                url = url.join("");

                if(Scinable.debug) {
                    console.log(url + " (length:" + url.length + ")");
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(Scinable.Access.Config.cache);
                req.addScriptTag();
                req.removeScriptTag();
            }

            var eventCnt = 0;
            var _trackEvent = function() {
                if(!cookieEnabled) return;

                if(++eventCnt > 10) return;

                var url = [
                      document.location.protocol=="https:"?"https://":"http://",
                      Scinable.Config.host,
                      Scinable.Access.Config.uri,
                      '?vid=', Scinable.Util.getVid(),
                      '&uid=', Scinable.Util.getUid(),
                      '&ua=', encodeURIComponent(navigator.userAgent),
                      '&p=', encodeURIComponent(Scinable.getPageUrl()),
                      '&dt=', encodeURIComponent(Scinable.getPageTitle()),
                      '&nv=', Scinable.newVisit,
                      //'&hn=', encodeURIComponent(document.domain),
                      '&aid=', Scinable.accountId,
                      '&at=', 'event'
                ];

                for (i = 0; i < arguments.length; i += 1) {
                    url.push('&e' + i + "=" + encodeURIComponent(arguments[i]));
                }

                url = url.join("");

                if(Scinable.debug) {
                    console.log(url + " (length:" + url.length + ")");
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(Scinable.Access.Config.cache);
                req.addScriptTag();
                req.removeScriptTag();
            }

            var _trackCart = function() {
                var a = [];
                a.push('cart')
                for (i = 0; i < arguments.length; i += 1) {
                    a.push(arguments[i]);
                }
                sendItems(a);
            }

            var _trackFavorite = function() {
                var a = [];
                a.push('favorite')
                for (i = 0; i < arguments.length; i += 1) {
                    a.push(arguments[i]);
                }
                sendItems(a);
            }

            var sendItems = function(arg) {
                if(!cookieEnabled) return;

                var ck = Scinable.Util.getCk();
                if(!ck) return;

                var url = [
                      document.location.protocol=="https:"?"https://":"http://",
                      Scinable.Config.host,
                      Scinable.Access.Config.uri,
                      '?vid=', Scinable.Util.getVid(),
                      '&uid=', Scinable.Util.getUid(),
                      '&ua=', encodeURIComponent(navigator.userAgent),
                      '&p=', encodeURIComponent(Scinable.getPageUrl()),
                      '&ck=', encodeURIComponent(ck),
                      '&nv=', Scinable.newVisit,
                      '&aid=', Scinable.accountId
                ];

                url.push('&at' + "=" + arg[0]); //cart, favorite

                if(arg.length == 2) {
                    var jo = arg[1];

                    url.push('&ct' + "=" + jo["action"]); //callType

                    var arr = jo["items"];
                    var items = [];
                    items.push(encodeURIComponent(arr[0]));
                    for (i = 1; i < arr.length; i += 1) {
                        items.push(";");
                        items.push(encodeURIComponent(arr[i]));
                    }
                    url.push('&its' + "=" + items.join(""));

                    var type = jo["type"];
                    if(type) {
                        url.push('&tp' + "=" + type);
                    }
                } else {
                    url.push('&ct' + "=" + arg[1]); //callType

                    var items = [];
                    items.push(encodeURIComponent(arg[2]));
                    for (i = 3; i < arg.length; i += 1) {
                        items.push(";");
                        items.push(encodeURIComponent(arg[i]));
                    }
                    url.push('&its' + "=" + items.join(""));
                }

                url = url.join("");

                if(Scinable.debug) {
                    console.log(url + " (length:" + url.length + ")");
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(Scinable.Access.Config.cache);
                req.addScriptTag();
                req.removeScriptTag();
            }

            var _setCampaignParameter = function(p) {
                Scinable.Param.campaign = p;
            }

            var _setOffline = function() {
                Scinable.offline = 'off';
            }

            var _setCampaign = function(p) {
                Scinable.campaign = p;
            }

            var _setPageUrl = function(p) {
                Scinable.Access.url = p
            }

            var _setPageTitle = function(p) {
                Scinable.Access.title = p
            }

            var _setPage = function(p) {
                for(var k in p){
                   Scinable.Access[k] = p[k];
                }
            }

            var _setCustomVar = function() {
                var p = arguments;
                if(cookieEnabled) {
                    var con = '';
                    for (var i = 0; i < p.length; ++i) {
                        if(i==0) {
                            con = p[i];
                        } else if(i==1) {
                            con = con + '.' + Scinable.Util.getAgeGroupKey(p[i]);
                        } else {
                            con = con + '.' + p[i];
                        }
                    }
                    Scinable.Util.setCookie("___cc", con, Scinable.Config.ccExpire);
                }
            }

            var _setConversion = function() {
                var p = arguments;
                if(p.length == 1) {
                    Scinable.Access.cgk = p[0];
                    Scinable.Access.cgv = 1;
                } else if(p.length == 2) {
                    Scinable.Access.cgk = p[0];
                    Scinable.Access.cgv = p[1];
                } else if(p.length == 3) {
                    Scinable.Access.cgk = p[0];
                    Scinable.Access.cgv = p[1];

                    var jo = p[2];
                    var arr = [];
                    for(i=1; i<6; i++) {
                        if(jo && jo[i]) {
                            arr.push(jo[i]);
                        } else {
                            arr.push('');
                        }

                    }
                    Scinable.Access.cgc = arr.join(";");
                }
                Scinable.Util.setR("___cvc", p[0], Scinable.Config.cvExpire);
            }

            var _setCustomField = function() {
                var p = arguments;
                if(p.length == 2) {
                    Scinable.customField[p[0]] =p[1];
                }
            }

            //webpush
            var _webPush = function(args) {
                var doNothing = function() {};

                // args
                // type : "requestAndSubscribe"|"request"|"custom"
                var _type = args.type || "requestAndSubscribe";
                var _customerKey = args.customerKey;
                var _publicKey;

                var _onDenied = args.onDenied || doNothing;
                var _onUnsupportedBrowser = args.onUnsupportedBrowser || _onDenied;

                var _onSubscribed = args.onSubscribed || doNothing;
                var _onAlready = args.onAlready || _onSubscribed;

                var _onInit = args.onInit || doNothing;

                if (!("serviceWorker" in navigator)) {
                    _onUnsupportedBrowser();
                    return;
                }
                if (!("showNotification" in ServiceWorkerRegistration.prototype)) {
                    _onUnsupportedBrowser();
                    return;
                }
                if (!("PushManager" in window)) {
                    _onUnsupportedBrowser();
                    return;
                }
                if (Notification.permission === "denied") {
                    _onDenied();
                    return;
                }

                // Get public key.
                fetch(createUrl({ callType: "P" }), { mode: "cors" })
                    .then(function(res) {
                        // Convert to json.
                        return res.json();
                    })
                    .then(function(json) {
                        _publicKey = decodeBase64URL(json.pk);
                        navigator.serviceWorker.register("scwp.js").then(function(serviceWorkerRegistration) {
                            serviceWorkerRegistration.pushManager.getSubscription()
                                .then(function(subscription) {
                                    if (subscription && subscription.endpoint) {
                                        // Already subscribed?
                                        _onAlready(subscription);
                                    } else {
                                        // Is new?
                                        if (_type === "request" || _type === "requestAndSubscribe") {
                                            request()
                                                .then(
                                                    function() {
                                                        if (_type === "requestAndSubscribe") {
                                                            subscribe();
                                                        }
                                                    }
                                                );
                                        }
                                    }
                                });
                        });
                        // Initialize
                        _onInit({
                            subscribe: function() {
                                request(true)
                                    .then(subscribe);
                            },
                            unsubscribe: unsubscribe
                        });
                    });

                function decodeBase64URL(str) {
                    var dec = atob(str.replace(/\-/g, "+").replace(/_/g, "/"));
                    var buffer = new Uint8Array(dec.length);
                    for (var i = 0 ; i < dec.length ; i++) {
                        buffer[i] = dec.charCodeAt(i);
                    }
                    return buffer;
                }

                function encodeBase64URL(buffer) {
                    return btoa(String.fromCharCode.apply(null, new Uint8Array(buffer)))
                        .replace(/\+/g, "-")
                        .replace(/\//g, "_")
                        .replace(/=+$/, "");
                }
                // resolveIfNotDenied = true(Call resolve in both cases 1)already granted and 2)newly granted)
                function request(resolveIfNotDenied) {
                    return new Promise(function(resolve) {
                        navigator.serviceWorker.ready.then(function() {
                            // Already granted?
                            if (Notification.permission === "granted") {
                                if (resolveIfNotDenied) {
                                    resolve && resolve();
                                }
                                return;
                            }
                            // Newly granted?
                            Notification.requestPermission(function(permission) {
                                if (permission === "denied") {
                                    _onDenied();
                                } else {
                                    resolve && resolve();
                                }
                            });
                        }).catch(function(err) {
                            console.log(err);
                        });
                    });
                }
                function createUrl(args) {
                    var callType = args.callType;
                    var endpoint = args.endpoint;
                    var browserKey = args.browserKey;
                    var auth = args.auth;
                    var url1 = [
                       (document.location.protocol == "https:") ? "https://" : "http://",
                       (Scinable.WebPush.Config.host) ? Scinable.WebPush.Config.host : Scinable.Config.host,
                       Scinable.WebPush.Config.webPushUri,
                       '?ct=', callType,
                    ];
                    var url2 = (callType === "P") ? [] : [
                        '&ck=', encodeURIComponent(_customerKey),
                        '&ua=', encodeURIComponent(navigator.userAgent),
                        '&ep=', endpoint,
                        '&bk=', browserKey,
                        '&ah=', auth,
                        '&aid=', Scinable.accountId
                    ];
                    var url = url1.concat(url2).join("");

                    if (Scinable.debug) {
                        console.log("callType : " + callType);
                        console.log("endpoint : " + endpoint);
                        console.log("browserKey : " + browserKey);
                        console.log("auth : " + auth);
                        console.log(url);
                    }
                    return url;
                }
                function send(args) {
                    var url = createUrl(args);
                    var req = new Scinable.Request(url);
                    req.buildScriptTag(true);
                    req.addScriptTag();
                    req.removeScriptTag();
                }
                function subscribe() {
                    navigator.serviceWorker.ready.then(function(serviceWorkerRegistration) {
                        serviceWorkerRegistration.pushManager.subscribe({
                            userVisibleOnly: true,
                            applicationServerKey: _publicKey
                        }).then(function(subscription) {
                            var endpoint = subscription.endpoint;
                            var browserKey = encodeBase64URL(subscription.getKey("p256dh"));
                            var auth = encodeBase64URL(subscription.getKey("auth"));
                            send({
                                callType: "S",
                                endpoint: endpoint,
                                browserKey: browserKey,
                                auth: auth
                            });
                            _onSubscribed();
                            return;
                        })
                        .catch(function(e) {
                            if (Notification.permission === "denied") {
                                console.log("Permission for Notifications was denied");
                            } else {
                                console.log("Unable to subscribe to push.", e);
                            }
                        });
                    }).catch(function(err) {
                        console.log(err);
                    });
                }
                function unsubscribe() {
                    navigator.serviceWorker.ready.then(function(serviceWorkerRegistration) {
                        serviceWorkerRegistration.pushManager.getSubscription()
                            .then(
                                function(pushSubscription) {
                                    if (!pushSubscription) {
                                        return;
                                    }
                                    pushSubscription.unsubscribe()
                                        .then(function(subscription) {
                                            var endpoint = pushSubscription.endpoint;
                                            var browserKey = encodeBase64URL(pushSubscription.getKey("p256dh"));
                                            var auth = encodeBase64URL(pushSubscription.getKey("auth"));
                                            send({
                                                callType: "U",
                                                endpoint: endpoint,
                                                browserKey: browserKey,
                                                auth: auth
                                            });
                                        })
                                        .catch(function(e) {
                                            console.log("Unsubscription error: ", e);
                                        });
                                })
                            .catch(function(e) {
                                console.log("Error thrown while unsubscribing from push messaging.", e);
                            });
                      });
                }
            };
            _webPush.needsDomReady = true;

            // line
            var _line = function(args) {
                // args
                // type : "prepare"|"complete"
                args = args || {};
                var _type = args.type || "prepare";
                var _customerKey = args.customerKey;

                if (_type === "prepare") {
                    function getParameter(name) {
                        var query = window.location.search.substring(1);
                        var params = query.split("&");
                        for (var i = 0; i < params.length; i++) {
                            var pair = params[i].split("=");
                            if (decodeURIComponent(pair[0]) === name) {
                                return decodeURIComponent(pair[1]);
                            }
                        }
                   }
                   var linkToken = getParameter("sci_lt");
                   if (linkToken) {
                       window.sessionStorage.setItem("sci_lt", linkToken);
                   }
                   return;
                }

                if (_type === "complete") {
                    var linkToken = sessionStorage.getItem("sci_lt");
                    sessionStorage.removeItem("sci_lt");
                    if (linkToken) {
                        var url = [
                            (document.location.protocol == "https:") ? "https://" : "http://",
                            (Scinable.Line.Config.host) ? Scinable.Line.Config.host : Scinable.Config.host,
                            Scinable.Line.Config.lineUri,
                            '?ct=link&linkToken=', linkToken, '&ck=', _customerKey, '&aid=', Scinable.accountId
                         ].join("");
                        fetch(url, { mode: "cors" })
                            .then(function(res) {
                                // Convert to json.
                                return res.json();
                             })
                            .then(function(json) {
                                var linkUrl = json.linkUrl
                                if (linkUrl) {
                                    location.href = linkUrl;
                                }
                             });
                    }
                    return;
                }
            };
            _line.needsDomReady = true;

            //olap
            var toArr = function(arg) {
                return Array.prototype.slice.call(arg);
            }

            var _addTrans = function() {
                Scinable.Trans.type = 'C';

                for(i=0; i<8; i++){
                    Scinable.Trans.order.push(arguments[i]);
                }

                var jo;
                if(arguments.length = 9) {
                    jo = arguments[8];
                }
                for(i=1; i<6; i++) {
                    if(jo && jo[i]) {
                        Scinable.Trans.order.push(jo[i]);
                    } else {
                        Scinable.Trans.order.push('');
                    }
                }
            }

            var _addItem = function() {
                var r = [];

                for(i=0; i<10; i++){
                    r.push(arguments[i]);
                }

                var jo;
                if(arguments.length = 11) {
                    jo = arguments[10];
                }

                for(i=1; i<6; i++) {
                    if(jo && jo[i]) {
                        r.push(jo[i]);
                    } else {
                        r.push('');
                    }
                }

                var gk;
                if(arguments.length = 12) {
                    gk = arguments[11];
                }

                if(gk) {
                    r.push(gk);
                } else {
                    r.push('');
                }

                Scinable.Trans.items.push(r);
            }

            var _addMember = function() {
                Scinable.Trans.type = 'C';

                for(i=0; i<9; i++){
                    Scinable.Trans.member.push(arguments[i]);
                }

                Scinable.Trans.member.push('1'); //valid_yn

                var jo;
                if(arguments.length = 10) {
                    jo = arguments[9];
                }
                for(i=1; i<6; i++) {
                    if(jo && jo[i]) {
                        Scinable.Trans.member.push(jo[i]);
                    } else {
                        Scinable.Trans.member.push('');
                    }
                }
            }

            var _updateMember = function() {
                Scinable.Trans.type = 'U';

                for(i=0; i<9; i++){
                    Scinable.Trans.member.push(arguments[i]);
                }

                Scinable.Trans.member.push('1'); //valid_yn

                var jo;
                if(arguments.length = 10) {
                    jo = arguments[9];
                }
                for(i=1; i<6; i++) {
                    if(jo && jo[i]) {
                        Scinable.Trans.member.push(jo[i]);
                    } else {
                        Scinable.Trans.member.push('');
                    }
                }
            }

            var _cancelMember = function() {
                Scinable.Trans.type = 'W';
                Scinable.Trans.member.push(arguments[0]);
            }

            var _addClaim = function() {
                Scinable.Trans.type = 'C';

                var r = [];

                for(i=0; i<12; i++){
                    r.push(arguments[i]);
                }
                var jo;
                if(arguments.length = 13) {
                    jo = arguments[12];
                }
                for(i=1; i<6; i++) {
                    if(jo && jo[i]) {
                        r.push(jo[i]);
                    } else {
                        r.push('');
                    }
                }

                Scinable.Trans.claim.push(r);
            }

            //search
            var _suggest = function(p) {
                var sg = createSuggest();
                for (var name in p) {
                    sg.Config[name] = p[name];
                }
                sg.start();
            }
            _suggest.needsDomReady = true;

            var _setTag = function(p) {
                Scinable.Suggest.tag = "&tag=" + encodeURIComponent(p);
            }

            var _setSuggetParam = function(p) {
                var sp = Scinable.Util.getQueryArray(p);
                Scinable.Suggest.param = "&" + sp.join("&");
            }

            var _keyword = function() {
                Scinable.Ranking.Config.url = (document.location.protocol=="https:"?"https://":"http://") + Scinable.Config.host + Scinable.Ranking.Config.uri + '?callback=' + 'Scinable.Ranking.callback' + '&aid=' + Scinable.accountId + '&pl=' + Scinable.Ranking.Config.limit + '&c=' + Scinable.Ranking.Config.cacheTime;
                var req = new Scinable.Request(Scinable.Ranking.Config.url);
                req.buildScriptTag(Scinable.Ranking.Config.cache);
                req.addScriptTag();
                req.removeScriptTag();
            }
            _keyword.needsDomReady = true;

            var _rank = function(p) {
                var sp = Scinable.Util.getQueryArray(p);
                var limit = p["limit"];
                if(limit) {
                    Scinable.Recommend.Config.limit = limit;
                }

                var callback = 'Scinable.Recommend.callback';
                if(p["callback"]) {
                    callback = p["callback"];
                }

                var t = 'html';
                if(p["t"]) {
                    t = p["t"];
                }

                var url = [
                           (document.location.protocol == "https:") ? "https://" : "http://",
                           (Scinable.Recommend.Config.host) ? Scinable.Recommend.Config.host : Scinable.Config.host,
                           Scinable.Recommend.Config.rankUri,
                           '?callback=' + callback,
                           '&t=' + t,
                           '&aid=', Scinable.accountId,
                           '&pl=', Scinable.Recommend.Config.limit,
                           '&', sp.join('&')
                ].join("");

                if(Scinable.debug) {
                    console.log(url);
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(true);
                req.addScriptTag();
                req.removeScriptTag();
            }
            _rank.needsDomReady = true;

            var _search = function(p) {
                var sp = Scinable.Util.getQueryArray(p);
                Scinable.Search.Config.url = (document.location.protocol=="https:"?"https://":"http://") + Scinable.Config.host + Scinable.Search.Config.uri + '?callback=' + 'Scinable.Search.callback' + '&aid=' + Scinable.accountId + "&" + sp.join("&");

                if(Scinable.debug) {
                    console.log(Scinable.Search.Config.url);
                }

                var req = new Scinable.Request(Scinable.Search.Config.url);
                req.buildScriptTag(Scinable.Search.Config.cache);
                req.addScriptTag();
                req.removeScriptTag();
            }
            _search.needsDomReady = true;

            var _realtime = function(p) {
                var sp = Scinable.Util.getQueryArray(p);
                var url = [
                           document.location.protocol=="https:"?"https://":"http://",
                           Scinable.Config.host,
                           Scinable.Realtime.Config.uri,
                           '?vid=', Scinable.Util.getVid(),
                           '&uid=', Scinable.Util.getUid(),
                           '&p=', encodeURIComponent(Scinable.getPageUrl()),
                           '&aid=', Scinable.accountId,
                           '&callback=', 'Scinable.Realtime.callback',
                           '&', sp.join("&")
                ].join("");

                var req = new Scinable.Request(url);
                req.buildScriptTag(false);
                req.addScriptTag();
                req.removeScriptTag();

                var req = new Scinable.Request(Scinable.Realtime.Config.url);
            }
            _realtime.needsDomReady = true;

            var _trackRecommend = function(p) {
                var discard = Scinable.Util.getParameter(Scinable.Param.discard);
                if(discard == '1') {
                    return;
                }

                var index = p["index"];
                if(index == null) {
                    if(Scinable.debug) {
                        console.log('index is not set');
                    }
                    return;
                }

                var itemParams = [];
                var items = p["items"];
                if(items != null) {
                    for (var i=0; i<items.length; ++i) {
                        itemParams.push("&item=" + encodeURIComponent(items[i]));
                        Scinable.Util.setViewItem('___sci_item_' + index, 10, items[i]);
                    }
                }

                var url = [
                           document.location.protocol=="https:"?"https://":"http://",
                                   Scinable.Recommend.Config.host?Scinable.Recommend.Config.host:Scinable.Config.host,
                                           Scinable.Recommend.Config.accessUri,
                                           '?vid=', Scinable.Util.getVid(),
                                           '&uid=', Scinable.Util.getUid(),
                                           '&ua=', encodeURIComponent(navigator.userAgent),
                                           '&index=', p["index"],
                                           '&refl=', Scinable.Util.getParameter(Scinable.Param.refl),
                                           '&ref=', encodeURIComponent(Scinable.getReferrer()),
                                           '&aid=', Scinable.accountId,
                                           itemParams.join("")
                                           ].join("");

                if(Scinable.debug) {
                    console.log(url + " (length:" + url.length + ")");
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(true);
                req.addScriptTag();
            }

            var _recommend = function(p) {
                var index = p["index"];
                if(index == null) {
                    if(Scinable.debug) {
                        console.log('index is not set');
                    }
                    return;
                }

                var tmpId = p["tmpId"];
                if (tmpId == null) {
                    if (Scinable.debug) {
                        console.log('tmpId is not set.');
                    }
                    return;
                }

                var renderId = p["renderId"];
                if (renderId == null) {
                    if (Scinable.debug) {
                        console.log("renderId is not  set.");
                    }
                    return;
                }

                var parms = [];
                for(key in p){
                    if(key == 'items') {
                        var items = p[key];
                        if(items != null) {
                            for (var i=0; i<items.length; ++i) {
                               parms.push('&item=' + encodeURIComponent(items[i]));
                            }
                        }
                    } else if(key == 'xitems') {
                        var items = p[key];
                        if(items != null) {
                           for (var i=0; i<items.length; ++i) {
                              parms.push('&xitem=' + encodeURIComponent(items[i]));
                           }
                        }
                    } else if(key == 'limit'){

                    } else {
                        var v = p[key];
                        if(Array.isArray(v)) {
                            for(var i = 0; i < v.length; i++) {
                                parms.push('&' + key + "=" + encodeURIComponent(v[i]));
                            }
                        } else {
                            parms.push('&' + key + "=" + encodeURIComponent(v));
                        }
                    }
                }

                if(p["items"] == null) {
                    var val = Scinable.Util.getLs('___sci_item_' + index);
                    if(val != null) {
                        var items = val.split(".");
                        var maxIndex = items.length - 1;
                        for (var i=0; i<items.length; ++i) {
                           if(i < Scinable.Recommend.Config.paramItemCount) {
                               parms.push('&item=' + encodeURIComponent(items[maxIndex - i]));
                           }
                        }
                    } else {
                        return false;
                    }
                }

                var limit = p["limit"];
                if(limit) {
                    Scinable.Recommend.Config.limit = limit;
                }

                var callback = 'Scinable.Recommend.callback';
                if(p["callback"]) {
                    callback = p["callback"];
                }

                var t = 'html';
                if(p["t"]) {
                    t = p["t"];
                }

                var url = [
                               (document.location.protocol == "https:") ? "https://" : "http://",
                               (Scinable.Recommend.Config.host) ? Scinable.Recommend.Config.host : Scinable.Config.host,
                               Scinable.Recommend.Config.recommendUri,
                               '?callback=' + callback,
                               '&t=' + t,
                               '&aid=', Scinable.accountId,
                               '&pl=', Scinable.Recommend.Config.limit,
                               parms.join("")
                ].join("");

                if (Scinable.debug) {
                    console.log(url);
                }

                var req = new Scinable.Request(url);
                req.buildScriptTag(Scinable.Recommend.Config.cache);
                req.addScriptTag();
            }
            _recommend.needsDomReady = true;

            return {
                _setDebug : _setDebug,
                _setAccount : _setAccount,
                _setDomainName : _setDomainName,
                _setLanguage : _setLanguage,
                _setSessionCookieTimeout : _setSessionCookieTimeout,
                _setVisitorCookieTimeout : _setVisitorCookieTimeout,
                _setCampaign : _setCampaign,
                _setCampaignParameter : _setCampaignParameter,
                _setOffline : _setOffline,
                _setPageUrl : _setPageUrl,
                _setPageTitle : _setPageTitle,
                _setPage : _setPage,
                _setCustomVar : _setCustomVar,
                _setConversion : _setConversion,
                _setCustomField: _setCustomField,
                _addTrans : _addTrans,
                _addItem : _addItem,
                _addMember : _addMember,
                _updateMember : _updateMember,
                _cancelMember : _cancelMember,
                _addClaim : _addClaim,
                _trackCustom : _trackCustom,
                _trackEvent : _trackEvent,
                _trackCart : _trackCart,
                _trackFavorite:_trackFavorite,
                _trackPageview : _trackPageview,
                _trackRecommend : _trackRecommend,
                _suggest : _suggest,
                _setTag : _setTag,
                _setSuggetParam : _setSuggetParam,
                _search : _search,
                _realtime : _realtime,
                _keyword : _keyword,
                _recommend : _recommend,
                _rank : _rank,
                _webPush: _webPush,
                _line: _line
            }
        }

        function isString(property) {
            return typeof property === 'string' || property instanceof String;
        }

        function apply() {
            var i, f, parameterArray, wrapper;
            for (i = 0; i < arguments.length; i += 1) {
                parameterArray = arguments[i];

                f = parameterArray.shift();
                if (isString(f)) {
                    wrapper = function() {
                        asyncTracker[f].apply(asyncTracker, parameterArray);
                    };
                } else {
                    wrapper = function() {
                        f.apply(asyncTracker, parameterArray);
                    };
                }
                if (f.needsDomReady) {
                    if(document.addEventListener){
                        document.addEventListener("DOMContentLoaded" , wrapper , false) ;
                    } else if(window.ActiveXObject){
                        var ScrollCheck = function(){
                            try {
                                document.documentElement.doScroll("left");
                            } catch(e) {
                                setTimeout(ScrollCheck, 1);
                                return;
                            }
                            wrapper();
                        }
                        ScrollCheck();
                    }
                } else {
                    wrapper();
                }
            }
        }

        var asyncTracker = new Tracker();

        for (iterator = 0; iterator < _scq.length; iterator++) {
            if (_scq[iterator]) {
                apply(_scq[iterator]);
                delete _scq[iterator];
            }
        }

        function TrackerProxy() {
            return {
                push: apply
            };
        }

        window[scinableObject] = new TrackerProxy();

        return Scinable;
    })()

    if (window.scOnLoad) {
        window.scOnLoad();
    }
}
