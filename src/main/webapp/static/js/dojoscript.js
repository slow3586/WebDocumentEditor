define([
        "dojo/_base/kernel",
        "dojo/dom", 
        "dojo/on", 
        "dojo/_base/window", 
        "dojo/store/Memory",
        "dijit/tree/ObjectStoreModel", 
        "dijit/Tree", 
        "dijit/layout/TabContainer", 
        "dijit/layout/ContentPane", 
        "dijit/layout/BorderContainer",
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
], function(
        kernel, 
        dom, 
        on, 
        win,
        Memory, 
        ObjectStoreModel, 
        Tree, 
        TabContainer, 
        ContentPane, 
        BorderContainer, 
        MenuBar,
        PopupMenuBarItem,
        Menu, 
        MenuItem,
        DropDownMenu, 
        Toolbar, 
        Button, 
        array, 
        Editor,
        AlwaysShowToolbar,
        Dialog, 
        ValidationTextBox,
        request, 
        OnDemandGrid, 
        RequestMemory, 
        Rest
){

    function createBorderContainer(){
        //Init
        kernel.global.mainBorderContainer = new BorderContainer({style: "height: 100%; width:100%;", liveSplitters:true, gutter:true});
        
        createMainMenuBar();
        
        //Main tab container
        kernel.global.mainTabContainer = new TabContainer({region: "center"});
        kernel.global.mainBorderContainer.addChild(kernel.global.mainTabContainer);

        createTreeMenu();
        
        //Startup
        document.body.appendChild(kernel.global.mainBorderContainer.domNode);
        kernel.global.mainBorderContainer.startup();
    };

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
        
        kernel.global.mainBorderContainer.addChild(menu);
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
        
        tree.onClick = function(item, node, event){
            if(item.id==='all_assignments' && !kernel.global.allAssignmentsTabOpen)
                openAllAssignmentsTab();
            if(item.id==='my_assignments')
                openAllAssignmentsTab();
            if(item.id==='for_me_assignments')
                openAllAssignmentsTab();
            if(item.id==='organizations')
                openAllAssignmentsTab();
            if(item.id==='employees')
                openAllAssignmentsTab();
        }
        
        kernel.global.mainBorderContainer.addChild(tree);
    }

    function openAllAssignmentsTab(){  
        if(kernel.global.allAssignmentsTabOpen) return;
        
        var tab = new ContentPane({
            title: "All assignments",
            closable: true,
            onClose: function(){
                kernel.global.allAssignmentsTabOpen = false;
            }
        });
        tab.addChild(new Button({
            label: "Add assignment",
            onClick: function(){openEditAssignmentTab()}
        }));
        tab.addChild(new Button({
            label: "Edit assignment",
            onClick: function(){openEditAssignmentTab()}
        }));
        tab.addChild(new Button({
            label: "Delete assignment",
            onClick: function(){openEditAssignmentTab()}
        }));
        
        var grid = createAllAssignmentsGrid();
        grid.on('.dgrid-row:click', function (event) {
            kernel.global.mainTabContainerSelectedRow = grid.row(event);
        });
        grid.on('.dgrid-row:dblclick', function (event) {
            openEditAssignmentTab(grid.row(event));
        });
        tab.addChild(grid);
        kernel.global.mainTabContainer.addChild(tab);
        
        grid.startup();
    }
    
    function openDeleteAssignmentDialog(){
        if(kernel.global.mainTabContainerSelectedRow===undefined) return;
    }

    function openEditAssignmentTab(row){
        if(row===undefined){
            if(kernel.global.mainTabContainerSelectedRow===undefined) return;
            row = kernel.global.mainTabContainerSelectedRow;
        }
        var tab = new ContentPane({title: "Edit assignment: "+row.data.topic, closable:true});
        
        var cp = new ContentPane({content:"Topic:"});
        var tb = new TextBox();
        cp.addChild(tb);
        tab.addChild(cp);
        
        var cp = new ContentPane({content:"Text:"});
        var tb = new TextBox();
        cp.addChild(tb);
        tab.addChild(cp);
        
        var cp = new ContentPane({content:"Text:"});
        var tb = new TextBox();
        cp.addChild(tb);
        tab.addChild(cp);
        
        tab.addChild(new Button({
            label: "Save",
            onClick: function(){}
        }));
        
        kernel.global.mainTabContainer.addChild(tab);
    }
    
    function createAllAssignmentsGrid(){
        var assignmentData = new RequestMemory({ target: 'api/assignment/find_all' });
        var grid = new OnDemandGrid({
            collection: assignmentData,
            columns: [{ field: 'id', label: 'ID'},
                { field: 'topic', label: 'Topic' },
                { field: 'text', label: 'Text'},
                { field: 'author', label: 'Author', 
                    formatter: function (author) {
                            return author.lastname + " " + author.firstname;
                    }
                }],
            loadingMessage: 'Loading data...',
            noDataMessage: 'No results found.'
        });
        return grid;
    }
      
    createBorderContainer();
    
});