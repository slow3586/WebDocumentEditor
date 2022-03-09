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
/*	
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
	*/
       
       
       
	var bc = new BorderContainer({style: "height: 100%; width:100%;", liveSplitters:true, gutter:true});
	
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
/*	
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
*/	
    var tc = new TabContainer({region: "center"});
        /*
    var tab1 = new ContentPane({title: "tab 1"});
	
	var myEditor = new Editor({
		height: '',
		extraPlugins: [AlwaysShowToolbar]
	});
	tab1.addChild( myEditor );
        
    tc.addChild( tab1 );
 * */
    bc.addChild(tc);
	
	var myStore = new Memory({
        data: [
                { id: 'root', name: 'root', root:true, type:'folder'},
                { id: 'organizations_root', name:'Organization structure',  parent: 'root', type:'folder'},
                { id: 'organizations', name:'Organizations',  parent: 'organizations_root', type:'item'},
                { id: 'employees', name:'Employees',  parent: 'organizations_root', type:'item'},
                { id: 'assignments_root', name:'Assignments',  parent: 'root', type:'folder'},
                { id: 'all_assignments', name:'All assignments',  parent: 'assignments_root', type:'item'},
                { id: 'my_assignments', name:'My assignments',  parent: 'assignments_root', type:'item'},
                { id: 'for_me_assignments', name:'Assignments for me',   parent: 'assignments_root', type:'item' },
            ],
            mayHaveChildren: function (item){
                return this.getValue(item, 'type') === 'folder';  
            },
            getIconClass: function( item, opened){
                    return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
            },
            getChildren: function(object){
                return this.query({parent: object.id});
            }
    });

    var myModel = new ObjectStoreModel({
        store: myStore,
        query: {id: 'root'}
        
    });

    var tree = new Tree({
        model: myModel,
        region: "left",
        showRoot: false,
        onClick: function(item, node, event){
            console.log(node+" "+item+" "+event);
        }
    });
    bc.addChild(tree);
    
    var Assignment = function (id, topic, text, executors, author){
        this.id = id;
        this.topic = topic;
        this.text = text;
        this.executors = executors;
        this.author = author;
    };

    Assignment.fromJson = function (json){
        var obj = JSON.parse (json);
        return new Assignment (obj.id, obj.topic, obj.text, obj.executors, obj.author);
    };

    request.get("api/assignment/find_all", {
        handleAs: "json"
    }).then(
        function(data){
            console.log("Hi");
            array.forEach(data, function(item,i){
                console.log(item);
                myStore.add({id:'assignment'+item.id, name:item.topic ,parent:'assignments'});
            });
        },
        function(error){
            console.log("An error occurred: " + error);
        }
    );
    // put the top level widget into the document, and then call startup()
    document.body.appendChild(bc.domNode);
    bc.startup();
});