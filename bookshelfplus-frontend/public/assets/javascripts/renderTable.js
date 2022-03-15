function renderTable({
    tableId = "",
    data,
    renderTableHead = true,
}) {
    var tbodyHtml = "";
    var theadHtml = "";
    if (!data) return null;
    if (!data.length) return null;

    if (Array.isArray(data[0])) {
        // 是数组
        // 如果元素是数组 ["a", "b", "c"]，则数组的第一项作为表头

        // 生成表格填充内容
        for (var i = renderTableHead ? 1 : 0; i < data.length; i++) {
            tbodyHtml += "<tr>";
            for (var cell in data[i]) {
                tbodyHtml += "<td>" + cell + "</td>";
            }
            tbodyHtml += "</tr>";
        }

        // 生成表头内容 (使用第一条数据)
        if (renderTableHead) {
            theadHtml += "<tr>";
            for (var cell in data[0]) {
                theadHtml += "<th>" + cell + "</th>";
            }
            theadHtml += "</tr>";
        }

    } else if (typeof data === "object") {
        // 是字典
        // 如果元素是字典 {a: "a", b: "b"}，则字典的key作为表头

        // 生成表格填充内容
        for (var i = 0; i < data.length; i++) {
            tbodyHtml += "<tr>";
            // for (var key in data[0]) {
            for (var key in data[i]) {
                tbodyHtml += "<td>" + data[i][key] + "</td>";
            }
            tbodyHtml += "</tr>";
        }

        // 生成表头内容 (以第一条数据的key为准)
        if (renderTableHead) {
            theadHtml += "<tr>";
            for (var key in data[0]) {
                theadHtml += "<th>" + key + "</th>";
            }
            theadHtml += "</tr>";
        }
    } else {
        throw new DOMException("Failed to render table: data is not array or dictionary.");
    }

    // 获取table
    var table = document.getElementById(tableId);
    if (!table) {
        table = document.createElement("table");
        table.id = tableId;
        // document.body.appendChild(oldScriptDom);
    }
    table.innerHTML = '';

    // 获取tbody & 填充tbody
    var tbody = document.createElement("tbody");
    tbody.innerHTML = tbodyHtml;

    if (!!renderTableHead) {
        var thead = document.createElement("thead");
        thead.innerHTML = theadHtml;
        table.appendChild(thead);
    }

    // 填充table
    table.appendChild(tbody);
    return table;
}

// console.clear();

// var data1 = [
//     { a: "a1", b: "b1", c: "c1", },
//     { c: "c2", a: "a2", b: "b2", },
//     { a: "a3", c: "c3", b: "b3", }
// ];

// var data2 = [
//     ["a1", "b1", "c1"],
//     ["a1", "b1", "c1"],
//     ["a1", "b1", "c1"],
// ];

// var table1 = renderTable({
//     tableId: "table1",
//     data: data1,
//     renderTableHead: true,
// });
// document.body.appendChild(table1);

// var table2 = renderTable({
//     tableId: "table2",
//     data: data2,
//     renderTableHead: true,
// });
// document.body.appendChild(table2);

// var table3 = renderTable({
//     tableId: "table3",
//     data: data1,
//     renderTableHead: false,
// });
// document.body.appendChild(table3);

// var table4 = renderTable({
//     tableId: "table4",
//     data: data2,
//     renderTableHead: false,
// });
// document.body.appendChild(table4);

// $('table').css('border', '1px solid red');

/*
表格结构：
<table>
    <thead>
        <th>
            <td>表头</td>
        </th>
    </thead>
    <tbody>
        <tr>
            <td>表体</td>
        </tr>
    </tbody>
    <tfoot>
        <tr>
            <td>表脚</td>
        </tr>
    </tfoot>
</table>

*/