define([
        "dojo/_base/declare",
        "dojo/_base/kernel",
        "dijit/layout/ContentPane", 
        "dijit/form/Button",
        "dijit/form/Select",
        "dijit/form/TextBox",
        "dijit/Dialog",
        'dstore/Rest',
        'dstore/SimpleQuery',
        'dstore/Trackable',
        'dgrid/Grid',
        'dgrid/Selection',
        'dgrid/extensions/Pagination',
        "mydojo/assignment_edit"
], function(
        declare, kernel, 
        ContentPane, Button, Select, TextBox, Dialog,
        Rest, SimpleQuery, Trackable, 
        Grid, Selection, Pagination,
        AssignmentEditTab
){

    var assignmentAllTabInstance = null

    var assignmentAllTab = declare("AssignmentAllTab", [ContentPane], {
        title: "All assignments",
        closable: true,
        onClose: function(){
            assignmentAllTabInstance = null;
            return true;
        },
        postCreate : function(){  
            this.addChild(new Button({
                label: "Add",
                onClick: function(){new AssignmentEditTab()}
            }));
            this.addChild(new Button({
                label: "Edit",
                onClick: function(){new AssignmentEditTab({isEditing:true})}
            }));
            this.addChild(new Button({
                label: "Delete",
                onClick: function(){assignmentAllTabInstance.openDeleteAssignmentDialog()}
            }));
            var grid = this.createAllAssignmentsGrid();
            this.addChild(new Button({
                label: "Update",
                onClick: function(){
                    kernel.global.allAssignmentsGrid.refresh();
                }
            }));

            kernel.global.assAssignmentsFilterColumn = "id";
            var searchoptcb = new Select({
                options: [
                        {label:"Id", value:"id", selected:true},
                        {label:"Topic", value:"topic"},
                        {label:"Text", value:"text"},
                        {label:"Author", value:"author_id"}
                    ],
                value: "id",
                onChange: function(newValue){
                    kernel.global.assAssignmentsFilterColumn = newValue;
                    this.filterAllAssignments();
                }
            });
            this.addChild(searchoptcb);
            var searchtb = new TextBox({
                label: "Search",
                onChange: function(newValue){
                    kernel.global.allAssignmentsFilterValue = searchtb.get("value");
                    this.filterAllAssignments();
                },
                onKeyUp: function(event){
                    kernel.global.allAssignmentsFilterValue = searchtb.get("value");
                    this.filterAllAssignments();
                }
            });
            this.addChild(searchtb);

            this.addChild(grid);
            kernel.global.mainTabContainer.addChild(this);
            grid.startup();
        },

        filterAllAssignments : function(){
            var filter = kernel.global.allAssignmentsData.filter({'id':kernel.global.allAssignmentsFilterValue});
            if(kernel.global.assAssignmentsFilterColumn==="topic"){
                filter = kernel.global.allAssignmentsData.filter({'topic':kernel.global.allAssignmentsFilterValue});
            }else if(kernel.global.assAssignmentsFilterColumn==="text"){
                filter = kernel.global.allAssignmentsData.filter({'text':kernel.global.allAssignmentsFilterValue});
            }else if(kernel.global.assAssignmentsFilterColumn==="author_id"){
                filter = kernel.global.allAssignmentsData.filter({'author_id':kernel.global.allAssignmentsFilterValue});
            }
            kernel.global.allAssignmentsGrid.set("collection",filter);
        },

        openDeleteAssignmentDialog : function(){
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
                    kernel.global.allAssignmentsGrid.refresh();
                }
            }));
            dialog.addChild(new Button({
                label: "Cancel",
                onClick: function(){
                    dialog.hide();
                }
            }));

            dialog.show();
        },

        createAllAssignmentsGrid : function(){
            var TrackableRest = declare([Rest, SimpleQuery, Trackable]);
            kernel.global.allAssignmentsData = new TrackableRest({ 
                target: 'api/assignments', 
                sortParam: "order_by",
                rangeStartParam: "from",
                rangeCountParam: "limit"
            });

            kernel.global.allAssignmentsGrid = new (declare([ Grid, Pagination, Selection ]))({
                collection: kernel.global.allAssignmentsData,
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
                new AssignmentEditTab({isEditing:true});
            });
            return kernel.global.allAssignmentsGrid;
        }
    });
    
    return function(){
        if(assignmentAllTabInstance === null){
            assignmentAllTabInstance = new assignmentAllTab();
        }
    }
});