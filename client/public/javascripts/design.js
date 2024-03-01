webix.ready(function () {
    var xml_format = webix.Date.strToDate("%d %m/%yyyy");
    webix.CustomScroll.init();
    var container = webix.ui({
        "rows": [
            {
                "view": "toolbar",
                "css": "webix_dark",
                "padding": {
                    "right": 10,
                    "left": 10
                },
                "elements": [
                    {
                        "view": "label",
                        "label": "Kaggle Dataset"
                    }
                ]
            },
            {
                "rows": [
                    {
                        "view": "datatable",
                        onBeforeLoad: function () {
                            this.showOverlay("Loading...");
                        },
                        onAfterLoad: function () {
                            this.hideOverlay();
                        },
                        on: {
                            "onItemClick": function (id, e, trg) {
                                //id.column - column id
                                //id.row - row id
                                webix.message("Click on row: " + id.row + ", column: " + id.column);
                                showMessage(id.row);
                            }
                        },
                        "select": true,
                        url: 'rest->http://localhost:8080/api/top10?column=PUBLICATIONDATE&sort=asc',
                        "scrollX": false,
                        "borderless": true,
                        "fixedRowHeight": false,
                        "areaselect": false,
                        "scrollY": true,
                        id: "kaggledataset",
                        css: "webix_header_border webix_data_border",
                        resizeColumn: {headerOnly: true},
                        "columns": [
                            {
                                "id": "id",
                                "header": "Id",
                                "fillspace": true,
                                "sort": "string",
                                "hidden": false
                            }, {
                                "id": "book",
                                "header": "Title",
                                "fillspace": true,
                                "sort": "string",
                                "hidden": false
                            },
                            {
                                "id": "author",
                                "header": "Author",
                                "sort": "string",
                                "fillspace": true,
                                "hidden": false
                            },
                            {
                                "id": "publicationDate",
                                "header": "Publication",
                                "format": function (value) {
                                    let drte = new Date(value);
                                    //console.log(drte);
                                    return drte;
                                },
                                "sort": "date"
                            },
                            {
                                "id": "series",
                                "header": "Series",
                                "fillspace": true,
                                "sort": "string"
                            },
                            {
                                "id": "format",
                                "header": "Format",
                                "fillspace": true,
                                "sort": "string"
                            },
                            {
                                "id": "rating",
                                "header": "Rating",
                                "sort": "string"
                            }
                        ]
                    },
                    {
                        "view": "form",
                        "minHeight": 380,
                        "autoheight": false,
                        "elements": [
                            {
                                "view": "textarea",
                                id: 'description',
                                "height": 150,
                                "readonly": true,
                                "label": "description",
                                "labelWidth": 150
                            }
                        ]
                    }
                ]
            }
        ],
        "borderless": 1
    });

    function showMessage(id) {
        var item = $$('kaggledataset').getItem(id);
        $$('description').setValue(item.description);
    }
})
;