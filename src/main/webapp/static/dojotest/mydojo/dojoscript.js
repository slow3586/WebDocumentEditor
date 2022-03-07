define([
    "dojo/dom", "dojo/on", "dojo/_base/window", "dojo/store/Memory",
    "dijit/tree/ObjectStoreModel", "dijit/Tree", "dijit/layout/TabContainer", "dijit/layout/ContentPane", "dijit/layout/BorderContainer",
	"dijit/MenuBar",
    "dijit/PopupMenuBarItem",
    "dijit/Menu",
    "dijit/MenuItem",
    "dijit/DropDownMenu",
	"dijit/Toolbar",
    "dijit/form/Button",
    "dojo/_base/array",
	"dijit/Editor",
	"dijit/_editor/plugins/AlwaysShowToolbar",
	"dijit/Dialog", 
	"dijit/form/ValidationTextBox",
	"dojo/request",
    "dojo/domReady!"
], function(dom, on, win, Memory, ObjectStoreModel, Tree, TabContainer, ContentPane, BorderContainer, MenuBar, PopupMenuBarItem, Menu, MenuItem, DropDownMenu, Toolbar, Button, array, Editor, AlwaysShowToolbar, Dialog, ValidationTextBox, request){
	var logcont = new ContentPane({title: "Login pane"});
	var logcontlog = new ContentPane({content: "Login"});
	var logcontpass = new ContentPane({content: "Password"});
	var lognameform = new ValidationTextBox({required:true});
	var logpassform = new ValidationTextBox({required:true});
	logcontlog.addChild(lognameform);
	logcontpass.addChild(logpassform	);
	logcont.addChild(logcontlog);
	logcont.addChild(logcontpass);
	
	var logbut = new Button({label:"Login"});
	logcont.addChild(logbut);
	on(logbut, "click", function(evt){
		request("request.html").then(function(data){
			// do something with handled data
		  }, function(err){
			// handle an error condition
		  }, function(evt){
			// handle a progress event
		  });
	});
	
	var myDialog = new Dialog({
        title: "Login",
        style: "width: 300px",
		closable: false,
		draggable: false
    });
	myDialog.addChild(logcont);
	myDialog.show();
	
	var bc = new BorderContainer({style: "height: 100%; width:100%;"});
	
	var pMenuBar = new MenuBar({region: "top"});
    var pSubMenu = new DropDownMenu({});
    pSubMenu.addChild(new MenuItem({
        label: "File item #1"
    }));
    pSubMenu.addChild(new MenuItem({
        label: "File item #2"
    }));
    pMenuBar.addChild(new PopupMenuBarItem({
        label: "File",
        popup: pSubMenu
    }));
    var pSubMenu2 = new DropDownMenu({});
    pSubMenu2.addChild(new MenuItem({
        label: "Cut",
        iconClass: "dijitEditorIcon dijitEditorIconCut"
    }));
    pSubMenu2.addChild(new MenuItem({
        label: "Copy",
        iconClass: "dijitEditorIcon dijitEditorIconCopy"
    }));
    pSubMenu2.addChild(new MenuItem({
        label: "Paste",
        iconClass: "dijitEditorIcon dijitEditorIconPaste"
    }));
    pMenuBar.addChild(new PopupMenuBarItem({
        label: "Edit",
        popup: pSubMenu2
    }));

	bc.addChild(pMenuBar);
	
	var toolbar = new Toolbar({region:"top"});
    array.forEach(["Cut", "Copy", "Paste"], function(label){
        var button = new Button({
            // note: should always specify a label, for accessibility reasons.
            // Just set showLabel=false if you don't want it to be displayed normally
            label: label,
            showLabel: false,
            iconClass: "dijitEditorIcon dijitEditorIcon"+label
        });
        toolbar.addChild(button);
    });
	bc.addChild(toolbar);
	
    var tc = new TabContainer({region: "center"});
    var tab1 = new ContentPane({title: "tab 1"}),
    tab2 = new ContentPane({title: "tab 2"});
	
	var myEditor = new Editor({
		height: '',
		extraPlugins: [AlwaysShowToolbar]
	});
	tab1.addChild( myEditor );
    tc.addChild( tab1 );
    tc.addChild( tab2 );
    bc.addChild(tc);
	
	var myStore = new Memory({
        data: [
            { id: 'world', name:'The earth', type:'planet', population: '6 billion'},
            { id: 'AF', name:'Africa', type:'continent', population:'900 million', area: '30,221,532 sq km',
                    timezone: '-1 UTC to +4 UTC', parent: 'world'},
                { id: 'EG', name:'Egypt', type:'country', parent: 'AF' },
                { id: 'KE', name:'Kenya', type:'country', parent: 'AF' },
                    { id: 'Nairobi', name:'Nairobi', type:'city', parent: 'KE' },
                    { id: 'Mombasa', name:'Mombasa', type:'city', parent: 'KE' },
                { id: 'SD', name:'Sudan', type:'country', parent: 'AF' },
                    { id: 'Khartoum', name:'Khartoum', type:'city', parent: 'SD' },
            { id: 'AS', name:'Asia', type:'continent', parent: 'world' },
                { id: 'CN', name:'China', type:'country', parent: 'AS' },
                { id: 'IN', name:'India', type:'country', parent: 'AS' },
                { id: 'RU', name:'Russia', type:'country', parent: 'AS' },
                { id: 'MN', name:'Mongolia', type:'country', parent: 'AS' },
            { id: 'OC', name:'Oceania', type:'continent', population:'21 million', parent: 'world'},
            { id: 'EU', name:'Europe', type:'continent', parent: 'world' },
                { id: 'DE', name:'Germany', type:'country', parent: 'EU' },
                { id: 'FR', name:'France', type:'country', parent: 'EU' },
                { id: 'ES', name:'Spain', type:'country', parent: 'EU' },
                { id: 'IT', name:'Italy', type:'country', parent: 'EU' },
            { id: 'NA', name:'North America', type:'continent', parent: 'world' },
            { id: 'SA', name:'South America', type:'continent', parent: 'world' }
        ],
		getIconClass: function( item, opened){
			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
		},
        getChildren: function(object){
            return this.query({parent: object.id});
        }
    });

    var myModel = new ObjectStoreModel({
        store: myStore,
        query: {id: 'world'}
    });

    var tree = new Tree({
        model: myModel,
		region: "left"
    });
	bc.addChild(tree);
    // put the top level widget into the document, and then call startup()
    document.body.appendChild(bc.domNode);
    bc.startup();
});