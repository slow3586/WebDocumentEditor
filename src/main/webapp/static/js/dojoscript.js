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
    
    function createMainMenuBar(){
        var menu = new MenuBar({region: "top"});
        var subMenu = new DropDownMenu({});
        subMenu.addChild(new MenuItem({
            label: "English"
        }));
        subMenu.addChild(new MenuItem({
            label: "Russian"
        }));
        menu.addChild(new PopupMenuBarItem({
            label: "Language",
            popup: subMenu
        }));
        return menu;
    };
    
    function openEditAssignmentTab(row){
        console.log(JSON.stringify(event));
        console.log('Row clicked:', row);
        var tab = new ContentPane({title: "Assignment: "+row.data.topic, closable:true});
        var namecp = new ContentPane({content:"Name:"});
        var nametb = new ValidationTextBox();
        namecp.addChild(nametb);
        tab.addChild(namecp);
        return tab;
    }
    
    function createBorderContainer(){
        var borderContainer = new BorderContainer({style: "height: 100%; width:100%;", liveSplitters:true, gutter:true});
        borderContainer.addChild(createMainMenuBar());
        var tabContainer = new TabContainer({region: "center"});
        borderContainer.addChild(tabContainer);
        var tree = createTreeMenu();
        tree.onClick = function(item, node, event){
                if(item.id==='all_assignments' && !window.allAssignmentsTabOpen){
                    var grid = createAllAssignmentsGrid();
                    
                    grid.on('.dgrid-row:dblclick', function (event) {
                        tabContainer.addChild(openEditAssignmentTab(grid.row(event)));
                    });
                
                    let tab = openAllAssignmentsTab();
                    tab.addChild(grid);
                    tabContainer.addChild(tab);
                    grid.startup();
                }
                console.log(node+" "+JSON.stringify(item)+" "+event);
            }
        borderContainer.addChild(tree);
        document.body.appendChild(borderContainer.domNode);
        borderContainer.startup();
    };
	
    function createTreeMenu(){
        var treeMenuMemory = new Memory({
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
        
        var treeMenuModel = new ObjectStoreModel({
            store: treeMenuMemory,
            query: {id: 'root'},
            mayHaveChildren: function(item){
                return item.type==='folder';
            }
        });
        
        var tree = new Tree({
            model: treeMenuModel,
            region: "left",
            showRoot: false
        });
        
        return tree;
    }
        
    function createGrid(apitarget, gridcolumns){
        var assignmentData = new RequestMemory({ target: apitarget });
        var grid = new OnDemandGrid({
            collection: assignmentData,
            columns: gridcolumns,
            loadingMessage: 'Loading data...',
            noDataMessage: 'No results found.'
        });
        return grid;
    }
    
    function createAllAssignmentsGrid(){
        var grid = createGrid('api/assignment/find_all', [
                { field: 'id', label: 'ID'},
                { field: 'topic', label: 'Topic' },
                { field: 'text', label: 'Text'},
                { field: 'author', label: 'Author', 
                    formatter: function (author) {
                            return author.lastname + " " + author.firstname;
                    }
                },
            ]);
        return grid;
    }
        
    function openAllAssignmentsTab(){   
        var tab = new ContentPane({
            title: "All assignments",
            onClose: function(){
                window.allAssignmentsTabOpen = false;
            }
        });
        var addbut = new Button({
            label: "Add assignment",
            onClick: function(){
                console.log("add");
            }
        });
        tab.addChild(addbut);
        var editbut = new Button({
            label: "Edit assignment",
            onClick: function(){
                console.log("edit");
            }
        });
        tab.addChild(editbut);
        var delbut = new Button({
            label: "Delete assignment",
            onClick: function(){
                console.log("add");
            }
        });
        tab.addChild(delbut);
        return tab;
    }
    
    createBorderContainer();
    
});