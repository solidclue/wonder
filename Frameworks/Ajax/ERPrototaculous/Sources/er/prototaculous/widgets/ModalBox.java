package er.prototaculous.widgets;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.appserver.ERXResponseRewriter;
import er.extensions.foundation.ERXProperties;

/**
 * Encapsulation of http://www.wildbit.com/labs/modalbox/ (a re-implementation of AjaxModalDialog)  
 * 
 * @property er.prototaculous.useUnobtrusively Support for Unobtrusive Javascript programming.
 *
 * @author mendis
 */
public abstract class ModalBox extends WOComponent {
	private static boolean useUnobtrusively = ERXProperties.booleanForKeyWithDefault("er.prototaculous.useUnobtrusively", false);
	
    /*
     * API or bindings common to light window subcomponents
     */
    public static interface Bindings {
    	public static final String directActionName = "directActionName";
    	public static final String action = "action";
    	public static final String queryDictionary = "queryDictionary";
    	public static final String params = "params";
    	public static final String width = "width";
    	public static final String title = "title";
    	public static final String left = "left";
    }
    
    public ModalBox(WOContext context) {
		super(context);
	}
    
    // accessors
    protected NSArray<String> _options() {
    	NSMutableArray<String> params = new NSMutableArray<String>();
    	
    	if (hasBinding(Bindings.width)) params.add("width: " + width());
    	if (hasBinding(Bindings.left)) params.add("left: " + left());
    	
    	return params.immutableClone();
    }
    
    public String title() {
    	return (String) valueForBinding(Bindings.title);
    }
    
    public String width() {
    	return (String) valueForBinding(Bindings.width);
    }
    
    public String left() {
    	return (String) valueForBinding(Bindings.left);
    }
    
    public String options() {
    	return "{" + _options().componentsJoinedByString(",") + "}";
    }

	// R/R
    @Override
	public void appendToResponse(WOResponse response, WOContext context) {
    	super.appendToResponse(response, context);
    	
    	if (!useUnobtrusively) {
    		ERXResponseRewriter.addScriptResourceInHead(response, context, "Ajax", "prototype.js");
    		ERXResponseRewriter.addScriptResourceInHead(response, context, "Ajax", "scriptaculous.js");
    		ERXResponseRewriter.addScriptResourceInHead(response, context, "Ajax", "modalbox.js");
    		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "Ajax", "modalbox.css");
    	}
    }
}
