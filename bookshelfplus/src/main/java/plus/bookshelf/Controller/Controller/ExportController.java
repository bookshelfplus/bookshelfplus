package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Dao.DO.BookDO;
import plus.bookshelf.Dao.DO.CategoryDO;
import plus.bookshelf.Dao.DO.FileDO;
import plus.bookshelf.Dao.DO.FileObjectDO;
import plus.bookshelf.Dao.Mapper.BookDOMapper;
import plus.bookshelf.Dao.Mapper.CategoryDOMapper;
import plus.bookshelf.Dao.Mapper.FileDOMapper;
import plus.bookshelf.Dao.Mapper.FileObjectDOMapper;
import plus.bookshelf.Service.Impl.UserServiceImpl;
import plus.bookshelf.Service.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "导出数据")
@Controller("export")
@RequestMapping("/export")
public class ExportController extends BaseController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    CategoryDOMapper categoryDOMapper;

    @Data
    private class CategoryNode {
        public CategoryDO current;
        public List<BookNode> children;
        public List<CategoryNode> subCategory;

        public CategoryNode(CategoryDO current) {
            this.current = current;
            this.children = new ArrayList<>();
            this.subCategory = new ArrayList<>();
        }
    }

    private List<CategoryNode> convertToCategoryNode(CategoryDO[] categoryDOList) {
        List<CategoryNode> categoryNodeList = new ArrayList<>();
        for (CategoryDO categoryDO : categoryDOList) {
            CategoryNode categoryNode = new CategoryNode(categoryDO);
            categoryNodeList.add(categoryNode);
        }
        return categoryNodeList;
    }

    @Autowired
    BookDOMapper bookDOMapper;

    @Data
    private class BookNode {
        public BookDO current;
        public List<FileNode> children;

        public BookNode(BookDO current) {
            this.current = current;
            this.children = new ArrayList<>();
        }
    }

    private List<BookNode> convertToBookNode(BookDO[] bookDOList) {
        List<BookNode> bookNodeList = new ArrayList<>();
        for (BookDO bookDO : bookDOList) {
            BookNode bookNode = new BookNode(bookDO);
            bookNodeList.add(bookNode);
        }
        return bookNodeList;
    }

    @Autowired
    FileDOMapper fileDOMapper;

    @Data
    private class FileNode {
        public FileDO current;
        public List<FileObjectNode> children;

        public FileNode(FileDO current) {
            this.current = current;
            this.children = new ArrayList<>();
        }
    }

    private List<FileNode> convertToFileNode(FileDO[] fileDOList) {
        List<FileNode> fileNodeList = new ArrayList<>();
        for (FileDO fileDO : fileDOList) {
            FileNode fileNode = new FileNode(fileDO);
            fileNodeList.add(fileNode);
        }
        return fileNodeList;
    }

    @Autowired
    FileObjectDOMapper fileObjectDOMapper;

    @Data
    private class FileObjectNode {
        public FileObjectDO current;

        public FileObjectNode(FileObjectDO current) {
            this.current = current;
        }
    }

    private List<FileObjectNode> convertToFileObjectNode(FileObjectDO[] fileObjectDOList) {
        List<FileObjectNode> fileObjectNodeList = new ArrayList<>();
        for (FileObjectDO fileObjectDO : fileObjectDOList) {
            FileObjectNode fileObjectNode = new FileObjectNode(fileObjectDO);
            fileObjectNodeList.add(fileObjectNode);
        }
        return fileObjectNodeList;
    }

    /**
     * 导出为 Markdown
     *
     * @param token 用户 token
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "【管理员】导出系统数据", notes = "将系统中的所有数据导出为markdown")
    @RequestMapping(value = "markdown", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType deleteBook(@RequestParam(value = "token", required = false) String token) throws BusinessException {

        // 查询出系统中所有的分类、书籍、文件、文件对象数据
        CategoryDO[] allCategorys = categoryDOMapper.selectAll();
        BookDO[] allBooks = bookDOMapper.selectAll();
        FileDO[] allFiles = fileDOMapper.selectAll();
        FileObjectDO[] allFileObjects = fileObjectDOMapper.selectAll();

        // 转换为 Node 对象，并保存在列表中，便于接下来的遍历
        List<CategoryNode> categoryNodeList = convertToCategoryNode(allCategorys);
        List<BookNode> bookNodeList = convertToBookNode(allBooks);
        List<FileNode> fileNodeList = convertToFileNode(allFiles);
        List<FileObjectNode> fileObjectNodeList = convertToFileObjectNode(allFileObjects);

        // 基本思路：
        // 先不管分类的层级，将 文件对象挂到文件下，文件挂到书籍下，书籍挂到分类下
        // 然后在形成分类的层级结构

        // 将文件对象挂到文件上去
        for (FileObjectNode fileObjectNode : fileObjectNodeList) {
            for (FileNode fileNode : fileNodeList) {
                if (fileObjectNode.current.getFileId().equals(fileNode.current.getId())) {
                    fileNode.children.add(fileObjectNode);
                    break;
                }
            }
        }

        // 将文件挂到书籍上去
        for (FileNode fileNode : fileNodeList) {
            for (BookNode bookNode : bookNodeList) {
                if (fileNode.current.getBookId().equals(bookNode.current.getId())) {
                    bookNode.children.add(fileNode);
                }
            }
        }

        // 将书籍挂到分类上去
        for (BookNode bookNode : bookNodeList) {
            for (CategoryNode categoryNode : categoryNodeList) {
                if (bookNode.current.getCategoryId().equals(categoryNode.current.getId())) {
                    categoryNode.children.add(bookNode);
                }
            }
        }

        // 创建一个 Category 父分类，用于保存所有的一级分类
        // 思路：每遍历到一个节点，就将其保存在 rootCategoryNodeList 中
        List<CategoryNode> rootCategoryNodeList = new ArrayList<>();

        // 再创建一个虚拟的顶级分类，用于保存所有的一级分类
        CategoryNode rootCategoryNode = new CategoryNode(new CategoryDO());
        rootCategoryNode.subCategory = categoryNodeList;

        // 分类形成树状结构
        for (CategoryNode categoryNode : categoryNodeList) {
            for (CategoryNode parentCategoryNode : categoryNodeList) {
                if (categoryNode.current.getParentId().equals(parentCategoryNode.current.getId())) {
                    parentCategoryNode.subCategory.add(categoryNode);

                    // 将子节点从 rootCategoryNodeList 中移除
                    if (rootCategoryNodeList.contains(categoryNode)) {
                        rootCategoryNodeList.remove(categoryNode);
                    }

                    // 将父节点添加到 rootCategoryNodeList 中
                    if (!rootCategoryNodeList.contains(parentCategoryNode)) {
                        rootCategoryNodeList.add(parentCategoryNode);
                    }
                }
            }
        }

        // 打印出这个复杂的树状结构
        traverseCategory(rootCategoryNode, 1);

        StringBuilder markdown = new StringBuilder();

        // 形成 markdown 格式的文件

        // 添加 markdown 头部以及尾部

        // 返回 markdown 文件
        return CommonReturnType.create(null);
    }

    // 递归打印出分类树状结构
    private void traverseCategory(CategoryNode parentCategoryNode, Integer level) {

        // 如果这个分类为空，则直接返回
        if (parentCategoryNode == null) {
            return;
        }

        // 如果这个分类下有书籍，则遍历打印书籍信息
        traverseBook(parentCategoryNode, level);

        // 遍历父级分类的孩子们
        for (CategoryNode categoryNode : parentCategoryNode.subCategory) {
            // 打印出当前子分类的名称
            System.out.println(getSpace(level - 1) + "[" + level + "级分类] \t" + categoryNode.current.getName());
            // 如果当前子分类还有子分类，则递归他
            traverseCategory(categoryNode, level + 1);
        }
    }

    // 递归打印出一本书的书籍信息
    private void traverseBook(CategoryNode categoryNode, Integer level) {
        // 如果这个分类为空，则直接返回
        if (categoryNode == null) {
            return;
        }
        List<BookNode> bookNodeList = categoryNode.children;
        // 遍历打印出书籍信息
        for (BookNode bookNode : bookNodeList) {
            // 打印出当前书籍的名称
            System.out.println(getSpace(level - 1) + "\t[书籍] " + bookNode.current.getId() + "\t" + bookNode.current.getBookName());
            for (FileNode fileNode : bookNode.children) {
                // 打印出当前书籍的文件信息
                System.out.println(getSpace(level - 1) + "\t\t[文件] " + fileNode.current.getId() + "\t" + fileNode.current.getFileName());
                for (FileObjectNode fileObjectNode : fileNode.children) {
                    // 打印出当前书籍的文件对象信息
                    System.out.println(getSpace(level - 1) + "\t\t\t[文件对象] " + fileObjectNode.current.getId() + "\t" + fileObjectNode.current.getFileName());
                }
            }
        }
    }

    public String getSpace(Integer count) {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < count; i++) {
            space.append("\t");
        }
        return space.toString();
    }
}
