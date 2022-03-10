define([
        "dojo/_base/kernel",
        'dojo/_base/declare',
        "dojo/dom-class",
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
        "dijit/form/TextBox",
        "dijit/form/ValidationTextBox",
        "dojo/request",
        'dgrid/OnDemandGrid',
        'dstore/RequestMemory',
        'dstore/Rest',
        'dstore/SimpleQuery',
        'dstore/Trackable',
        "dijit/form/Textarea",
        'dgrid/Selection',
        'dgrid/Grid',
        'dgrid/extensions/Pagination',
        "dijit/form/Select",
        "dojo/domReady!"
], function(
        kernel, 
        declare,
        domClass,
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
        TextBox,
        ValidationTextBox,
        request, 
        OnDemandGrid, 
        RequestMemory, 
        Rest,
        SimpleQuery,
        Trackable,
        TextArea,
        Selection,
        Grid,
        Pagination,
        Select
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
            label: "Add",
            onClick: function(){openEditAssignmentTab(false)}
        }));
        tab.addChild(new Button({
            label: "Edit",
            onClick: function(){openEditAssignmentTab(true)}
        }));
        tab.addChild(new Button({
            label: "Delete",
            onClick: function(){openDeleteAssignmentDialog()}
        }));
        var grid = createAllAssignmentsGrid();
        tab.addChild(new Button({
            label: "Update",
            onClick: function(){
                kernel.global.allAssignmentsGrid.refresh();
            }
        }));
        
        var searchoptcb = new Select({
            options: [
                    {label:"Hi0", value:"Al", selected:true},
                    {label:"Hi1", value:"Al1"}
                ],
            value: "id",
            onChange: function(newValue){
                console.log(newValue);
            }
        });
        tab.addChild(searchoptcb);
        var searchtb = new TextBox({
            label: "Search",
            onChange: function(newValue){
                console.log(newValue);
            },
            onKeyUp: function(event){
                console.log("ku "+searchtb.get("value"));
                kernel.global.allAssignmentsGrid.set("collection",
                    kernel.global.assAssignmentsData.filter({'id':searchtb.get("value")})
                );
            }
        });
        tab.addChild(searchtb);
        
        tab.addChild(grid);
        kernel.global.mainTabContainer.addChild(tab);
        grid.startup();
        kernel.global.allAssignmentsTabOpen = true;
    }
    
    function openDeleteAssignmentDialog(){
        if(kernel.global.allAssignmentsGridSelectedRow===undefined) return;
        
        var dialog = new Dialog({
            title: "Delete assignment "+kernel.global.allAssignmentsGridSelectedRow.data.topic,
            content: "Are you sure you want to delete "+kernel.global.allAssignmentsGridSelectedRow.data.topic+"?"
        });
        dialog.addChild(new Button({
            label: "Yes",
            onClick: function(){
                kernel.global.allAssignmentsGrid.collection.remove(kernel.global.allAssignmentsGridSelectedRow.data.id);
                dialog.hide();
            }
        }));
        dialog.addChild(new Button({
            label: "Cancel",
            onClick: function(){
                dialog.hide();
            }
        }));
        
        dialog.show();
    }
    
    function openEditAssignmentTab(isEditing){
        var tab = new ContentPane({title: "New assignment", closable:true});
        
        var tbid = new TextBox({value:"0", disabled:"true"});
        
        var cp = new ContentPane({content:"Topic:"});
        var tbtopic = new TextBox();
        cp.addChild(tbtopic);
        tab.addChild(cp);
    
        var tbauthor_id = new TextBox({disabled:"true"});
    
        cp = new ContentPane({content:"Author:"});
        var tbauthor = new TextBox({disabled:"true"});
        cp.addChild(tbauthor);
        tab.addChild(cp);
        
        cp = new ContentPane({content:"Text:"});
        var tbtext = new TextArea();
        domClass.add(tbtext.domNode, "bigTextBox");
        cp.addChild(tbtext);
        tab.addChild(cp);
        
        if(isEditing){
            var row = kernel.global.allAssignmentsGridSelectedRow;
            tbid.set("value", row.data.id);
            tbtopic.set("value", row.data.topic);
            tbtext.set("value", row.data.text);
            tbauthor.set("value", row.data.author_id);
            tbauthor_id.set("value", row.data.author_id);
            tab.set("title", "Edit assignment: "+row.data.topic);
        }
        
        tab.addChild(new Button({
            label: isEditing ? "Save" : "Create",
            onClick: function(){
                var adata = {
                            topic: tbtopic.get("value"),
                            text: tbtext.get("value"),
                            author_id: "1"//tbauthor_id.get("value")
                        };
                if(isEditing) { adata.id = tbid.get("value"); }
                kernel.global.allAssignmentsGrid.collection.add(adata);
            }
        }));
        
        kernel.global.mainTabContainer.addChild(tab);
    }
    
    function createAllAssignmentsGrid(){
        var TrackableRest = declare([Rest, SimpleQuery, Trackable]);
        kernel.global.assAssignmentsData = new TrackableRest({ 
            target: 'api/assignments', 
            sortParam: "order_by",
            rangeStartParam: "from",
            rangeCountParam: "limit"
        });
        
        var filterQuery = function(item, index, items) {
            var filterString = filter ? filter.get("value") + "" : "";

            // early exists
            if (filterString.length < 2) return true;
            if (!item.Name) return false;

            // compare
            var name = (item.Name + "").toLowerCase();
            if (~name.indexOf(filterString.toLowerCase())) { return true;}

            return false;
        };
        
        //var assignmentData = new RequestMemory({ target: 'api/assignment/find_all' });
        kernel.global.allAssignmentsGrid = new (declare([ Grid, Pagination, Selection ]))({
            collection: kernel.global.assAssignmentsData,
            selectionMode: 'single',
            /*
            maxRowsPerPage: 10,
            minRowsPerPage: 10,
            bufferRows: 10,
            pagingDelay: 100,
             * */
            keepScrollPosition: 'true',
            pagingLinks: 1,
            pagingTextBox: true,
            firstLastArrows: true,
            pageSizeOptions: [10, 20, 30, 40, 50],
            columns: [{ field: 'id', label: 'ID'},
                { field: 'topic', label: 'Topic' },
                { field: 'text', label: 'Text'},
                { field: 'author_id', label: 'Author', 
                    formatter: function (author) {
                            return author;
                    }
                }],
            loadingMessage: 'Loading data...',
            noDataMessage: 'No results found.'
        });
        kernel.global.allAssignmentsGrid.on('dgrid-select', function (event) {
            kernel.global.allAssignmentsGridSelectedRow = kernel.global.allAssignmentsGrid.row(event.rows[0]);
        });
        kernel.global.allAssignmentsGrid.on('dgrid-deselect', function (event) {
            kernel.global.allAssignmentsGridSelectedRow = undefined;
        });
        kernel.global.allAssignmentsGrid.on('.dgrid-row:dblclick', function (event) {
            openEditAssignmentTab(true);
        });
        return kernel.global.allAssignmentsGrid;
    }
      
    createBorderContainer();
    
});