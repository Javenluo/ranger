
jQuery.extend({
	

    createUploadIframe: function(id, uri)
	{
			//create frame
            var frameId = 'jUploadFrame';
            if($("#"+frameId)){
            	$("#"+frameId).remove();
            }
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
			if(window.ActiveXObject)
			{
                if(typeof uri== 'boolean'){
					iframeHtml += ' src="' + 'javascript:false' + '"';

                }
                else if(typeof uri== 'string'){
					iframeHtml += ' src="' + uri + '"';

                }
			}
			iframeHtml += ' />';
			jQuery(iframeHtml).appendTo(document.body);

            return jQuery('#' + frameId).get(0);			
    },
    createUploadForm: function(id, fileElementName, data)
	{
		//create form	
		var formId = 'jUploadForm';
		if($("#"+formId)) {
			$("#"+formId).remove();
		}
		var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');	
		if(data){
			var flag = false;
			try{
				var d = data;
				if(typeof(d) == "string"){
					d = $.parseJSON(data);
				}
				if(d.constructor != Array){
					if(d != null){
						for(var i in d){
							var strInput = '<input type="hidden" name="' + i + '" value="' + jQuery.encode(d[i]) + '" />';
							jQuery(strInput).appendTo(form);
						}
					} else {
						flag = true;
					}
				} else {
					flag = true;
				}
			} catch(e) {
				flag = true;
			}
			if(flag){
				for(var i in data){
					jQuery('<input type="hidden" name="' + data[i].name + '" value="' + jQuery.encode(data[i].value) + '" />').appendTo(form);
				}
			}
		}	
		if(typeof(fileElementName)=='string'){
			fileElementName=[fileElementName];
		}
		for (var i=0; i<fileElementName.length; i++ )
			{
			var oldElement = jQuery('input[name=' + fileElementName[i] + ']');
			var newElement = jQuery(oldElement).clone();
			jQuery(oldElement).before(jQuery(newElement)[0]);
			jQuery(oldElement).appendTo(form);
			}

		
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');		
		return form;
    },

    ajaxFileUpload: function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime();
		var form = jQuery.createUploadForm(id, s.fileElementName, (typeof(s.data)=='undefined'?false:s.data));
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame';
		var formId = 'jUploadForm';		
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
		{
			jQuery.event.trigger( "ajaxStart" );
		}            
        var requestDone = false;
        // Create the request object
        var xml = {};
        if ( s.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
        // Wait for a response to come back
        var uploadCallback = function(isTimeout){			
			var io = document.getElementById(frameId);
            try{				
				if(io.contentWindow){
					xml.responseText = io.contentWindow.document.body.children[0]?io.contentWindow.document.body.children[0].innerHTML:null;
					if(xml.responseText == null){
						xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
					}
                	xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
					 
				}else if(io.contentDocument){
					xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}						
            }catch(e){
	            jQuery.handleError(s, xml, null, e);
	            jQuery.restoreFile(s.fileElementName);
	            return;
			}
            if ( xml || isTimeout == "timeout"){				
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" ){
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );    
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success ){
                            s.success( data, status );
                        }
    
                        // Fire the global callback
                        if( s.global ){
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                        }
                    } else {
                        jQuery.handleError(s, xml, status);
                        jQuery.restoreFile(s.fileElementName);
                    }
                } catch(e){
                	status = "parsererror";
                    jQuery.handleError(s, xml, status, e);
                    jQuery.restoreFile(s.fileElementName);
                    return;
                }

                // The request was completed
                if( s.global ){
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );
                }

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active ){
                    jQuery.event.trigger( "ajaxStop" );
                }

                // Process result
                if ( s.complete ){
                    s.complete(xml, status);
                }

                jQuery(io).unbind();

                setTimeout(function(){	
                	try {
						jQuery(io).remove();
						jQuery(form).remove();	
					} catch(e) {
						jQuery.handleError(s, xml, null, e);
					}}, 100);

                xml = null;

            }
        };
        // Timeout checker
        if ( s.timeout > 0 ){
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ){
                	uploadCallback( "timeout" );
                }
            }, s.timeout);
        }
        try {

			var form = jQuery('#' + formId);
			jQuery(form).attr('action', s.url);
			jQuery(form).attr('method', 'POST');
			jQuery(form).attr('target', frameId);
            if(form.encoding){
				jQuery(form).attr('encoding', 'multipart/form-data');      			
            }
            else{	
				jQuery(form).attr('enctype', 'multipart/form-data');			
            }			
            jQuery(form).submit();

        } catch(e) {
            jQuery.handleError(s, xml, null, e);
            jQuery.restoreFile(s.fileElementName);
            return;
        }
		
		jQuery('#' + frameId).load(uploadCallback);
        return {abort: function () {}};	

    },
    
    handleError: function( s, xml, status, e) {
    	//If a local callback was specified, fire it
    	var frameId = 'jUploadFrame';
    	var io = document.getElementById(frameId);
    	xml.responseText = io.contentWindow.document.body.innerHTML;
    	if(s.error) {
    		s.error( xml, status, e);
    	}
    	
    	//Fire the global callback
    	if(s.global) {
    		(s.context ? jQuery(s.context) : jQuery.event).trigger("ajaxError", [xml, s, e]);
    	}
    },
    
    //when mistakes, restore the files
    restoreFile: function(fileElementName){
    	var formId = 'jUploadForm';
    	var oldElement = jQuery('input[name=' + fileElementName + ']').not('#'+ formId +' input[name=' + fileElementName + ']');
		var newElement = jQuery('#'+ formId +' input[name=' + fileElementName + ']');
		var i = 0;
		oldElement.each(function(){
			$(this).before(jQuery(newElement)[i++]);
		});
		oldElement.remove();
    },
    
    //特殊转义
    encode : function(value) {
    	if((value != undefined) && (value != null)) {
			var regexp1 = new RegExp("\"", "gm");
			var regexp2 = new RegExp("\'", "gm");
			var regexp3 = new RegExp("<", "gm");
			var regexp4 = new RegExp(">", "gm");
			value = value.replace(regexp1, "&quot;");
			value = value.replace(regexp2, "&#39;");
			value = value.replace(regexp3, "&lt;");
			value = value.replace(regexp4, "&gt;");
    	}
		return value;
	},
    
    uploadHttpData: function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        // If the type is "script", eval it in global context
        if ( type == "script" ){
            jQuery.globalEval( data );
        }
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" ){
//        	data = eval('(' +  data + ')');
        	data = $.parseJSON(data);
        }
        // evaluate scripts within html
        if ( type == "html" ){
        	if(data.indexOf("<div>") != -1){
        		data = data.substring(data.indexOf("<div>")+1, data.lastIndexOf("</div>"));
        	}
        }
        return data;
    }
});

