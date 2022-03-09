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
        'dgrid/OnDemandGrid',
        'dstore/RequestMemory',
        'dstore/Rest',
    "dojo/domReady!"
], function(dom, on, win, Memory, ObjectStoreModel, Tree, TabContainer, ContentPane, BorderContainer, MenuBar, PopupMenuBarItem, Menu, MenuItem, DropDownMenu, Toolbar, Button, array, Editor, AlwaysShowToolbar, Dialog, ValidationTextBox, request, OnDemandGrid, RequestMemory, Rest){
    var bc = new BorderContainer({style: "height: 100%; width:100%;", liveSplitters:true, gutter:true});
	
    var pMenuBar = new MenuBar({region: "top"});
    var pSubMenu = new DropDownMenu({});
    pSubMenu.addChild(new MenuItem({
        label: "English"
    }));
    pSubMenu.addChild(new MenuItem({
        label: "Russian"
    }));
    pMenuBar.addChild(new PopupMenuBarItem({
        label: "Language",
        popup: pSubMenu
    }));
    bc.addChild(pMenuBar);
	
    var tabContainer = new TabContainer({region: "center"});
    bc.addChild(tabContainer);
	
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
        getIconClass: function( item, opened){
                return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
        },
        getChildren: function(object){
            return this.query({parent: object.id});
        }
    });
    var myModel = new ObjectStoreModel({
        store: myStore,
        query: {id: 'root'},
        mayHaveChildren: function(item){
            return item.type=='folder';
        }
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
 
    var griddata = new RequestMemory({ target: 'api/assignment/find_all' });
 
    griddata.fetch().forEach(function (object) {
        //console.log(object);
    });
    var grid = new OnDemandGrid({
        collection: griddata,
        columns: {
            id: 'ID',
            topic: 'Topic'
        },
        loadingMessage: 'Loading data...',
        noDataMessage: 'No results found.'
    });
    grid.on('dgrid-error', function(event) {
        //console.log(event+" "+event.error.message);
    });
    grid.on('.dgrid-row:click', function (event) {
        var row = grid.row(event);
        console.log('Row clicked:', row);
        var atab = new ContentPane({title: "Assignment: "+row.data.topic, closable:true});
        tabContainer.addChild(atab);
    });
    grid.on('dgrid-refresh-complete', function(event) {
        //console.log(event+" "+event.error.message);
    });
    var tab1 = new ContentPane({title: "tab 1"});
    tab1.addChild(grid);
    tabContainer.addChild(tab1);
    document.body.appendChild(bc.domNode);
    bc.startup();
    grid.startup();
});