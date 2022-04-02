// 将分类按照层级关系进行处理
function generateCategoryHierarchy(data) {
    var maxRenderLevel = 5; // 最多渲染几层
    var datas = []; // 用于保存每个层级的数组， [ [ level 1 ], [ level 2 ], ... ]
    for (let i = 0; i < maxRenderLevel; i++)
        datas.push([]);

    for (let i = 0; i < data.length; i++) {
        const cate = data[i];
        if (!cate) continue; // 分类对象为空
        if (cate.level > maxRenderLevel) continue; // 分类的层级大于最大渲染层级
        datas[cate.level - 1].push(cate);

        // 顺便为每个分类添加 children 字段
        cate.children = [];
    }

    // 从最低的层级开始渲染，因为最低层级无子分类，所以略过
    for (let i = maxRenderLevel - 1 - 1; i >= 0; i--) {
        var parentLevel = i;
        var childLevel = i + 1;
        for (let parentIndex = 0; parentIndex < datas[parentLevel].length; parentIndex++) {
            const parentCategory = datas[parentLevel][parentIndex];
            for (let childIndex = 0; childIndex < datas[childLevel].length; childIndex++) {
                const childCategory = datas[childLevel][childIndex];
                // console.log(parentCategory, childCategory);
                if (parentCategory.id === childCategory.parentId) {
                    parentCategory.children.push(childCategory);
                }
            }
        }
    }

    // 对元素进行排序
    function sortChildren(categoryList) {
        function compare(property) {
            return function (a, b) {
                var value1 = a[property];
                var value2 = b[property];
                return value1 - value2;
            }
        }

        function sortCategoryList(categoryList) {
            // console.log("categoryList", categoryList);
            categoryList.sort(compare("order"));
            for (let i = 0; i < categoryList.length; i++)
                sortCategoryList(categoryList[i].children);
            // console.log(Array.isArray(categoryList));
        }

        sortCategoryList(categoryList);
    }
    sortChildren(datas[0]);

    // console.log(datas);
    // console.log(datas[0]);

    return datas[0];
}