function getControlsProfile() {
    return [
        {
            // 必填
            "tag": "input",
            "attr": {
                // 可选
                "id": "",
                "name": "", // 用于 POST 提交
                "class": "",
                "style": "",
                "placeholder": "电子书的名称",
            },
            "label": {
                "value": "书本名称",
            },
            "required": true, // 是否必填

            // 可选
            innerHTML: "",

            "validate": function (value) {
                var result = true, msg = "";
                if (validateUtils.isEmpty(value)) {
                    result = false;
                    msg = "不能为空";
                }
                return { result: result, msg: msg };
            }
        },
        {
            // 必填
            "tag": "input",
            "attr": {
                // 可选
                "id": "",
                "name": "", // 用于 POST 提交
                "class": "",
                "style": "",
                "placeholder": "电子书的作者",
            },
            "label": {
                "value": "作者姓名",
            },
            "required": true, // 是否必填

            // 可选
            innerHTML: "",

            "validate": function (value) {
                var result = true, msg = "";
                if (validateUtils.isEmpty(value)) {
                    result = false;
                    msg = "不能为空";
                }
                return { result: result, msg: msg };
            }
        },
        {
            // 必填
            "tag": "textarea",
            "attr": {
                // 可选
                "id": "",
                "name": "", // 用于 POST 提交
                "class": "",
                "style": "height:100px;",
                "placeholder": "电子书的简介",
            },
            "label": {
                "value": "书籍简介",
            },
            "required": false, // 是否必填

            // 可选
            innerHTML: "",

            "validate": function (value) {
                var result = true, msg = "";
                if (validateUtils.isEmpty(value)) {
                    result = false;
                    msg = "不能为空";
                }
                return { result: result, msg: msg };
            }
        },
        {
            // 必填
            "tag": "select",
            "attr": {
                // 可选
                "id": "",
                "name": "", // 用于 POST 提交
                "class": "",
                "style": "",
            },
            "label": {
                "value": "书籍简介",
            },
            "required": true, // 是否必填

            // 可选
            "children": [
                {
                    "tag": "option",
                    "attr": { "value": "1" },
                    "innerHTML": "选项1",
                },
                {
                    "tag": "option",
                    "attr": { "value": "2" },
                    "innerHTML": "选项2",
                },
            ],

            "validate": function (value) {
                var result = true, msg = "";
                if (validateUtils.isEmpty(value)) {
                    result = false;
                    msg = "不能为空";
                }
                return { result: result, msg: msg };
            }
        },
    ];
}

function getSubmitButtonValue() {
    return "提交";
}