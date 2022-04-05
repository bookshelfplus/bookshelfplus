async function getControlsProfile() {
    // 获取分类列表
    var categoryOptions = [];

    var responseData = await getRequest("/category/list");
    var axiosData = responseData.data;
    var status = axiosData.status;
    var data = axiosData.data;
    if (status === "success") {
        // console.log(data)
        hierarchyData = generateCategoryHierarchy(data);
        console.log(hierarchyData);
        function render(category) {
            categoryOptions.push({
                "tag": "option",
                "attr": { "value": category.id },
                "innerHTML": `${"&nbsp;".repeat((category.level - 1) * 8)}${category.name}`,
            })
            for (let i = 0; i < category.children.length; i++)
                render(category.children[i]);
        }
        render({ children: hierarchyData });
        categoryOptions.shift(); // 删除数组中的第一个元素
    } else {
        alert(`出错啦！${data.errMsg} (错误码: ${data.errCode}) `);
    }

    console.log(categoryOptions);
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
                "placeholder": "书籍语言",
            },
            "label": {
                "value": "语言",
            },
            "required": true, // 是否必填
            "children": [
                {
                    "tag": "option",
                    "attr": { "value": "Chinese" },
                    "innerHTML": "中文",
                },
                {
                    "tag": "option",
                    "attr": { "value": "English" },
                    "innerHTML": "英文",
                },
                {
                    "tag": "option",
                    "attr": { "value": "" },
                    "innerHTML": "其他",
                },
            ],

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
                "placeholder": "出版社",
            },
            "label": {
                "value": "出版社",
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
                "placeholder": "",
            },
            "label": {
                "value": "来源(版权)",
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
                "value": "书籍分类",
            },
            "required": true, // 是否必填

            // 可选
            "children": categoryOptions,
            // [
            //     {
            //         "tag": "option",
            //         "attr": { "value": "1" },
            //         "innerHTML": "选项1",
            //     },
            //     {
            //         "tag": "option",
            //         "attr": { "value": "2" },
            //         "innerHTML": "选项2",
            //     },
            // ],

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