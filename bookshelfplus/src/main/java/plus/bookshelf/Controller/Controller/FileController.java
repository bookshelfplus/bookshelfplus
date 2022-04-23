package plus.bookshelf.Controller.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.bookshelf.Common.Error.BusinessErrorCode;
import plus.bookshelf.Common.Error.BusinessException;
import plus.bookshelf.Common.Response.CommonReturnType;
import plus.bookshelf.Config.QCloudCosConfig;
import plus.bookshelf.Controller.VO.FileObjectVO;
import plus.bookshelf.Controller.VO.FileVO;
import plus.bookshelf.Service.Impl.*;
import plus.bookshelf.Service.Model.BookModel;
import plus.bookshelf.Service.Model.FileModel;
import plus.bookshelf.Service.Model.FileObjectModel;
import plus.bookshelf.Service.Model.UserModel;
import plus.bookshelf.Service.Service.CosPresignedUrlGenerateLogService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Api(tags = "文件管理")
@Controller("file")
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    CosPresignedUrlGenerateLogService cosPresignedUrlGenerateLogService;

    @Autowired
    FileObjectServiceImpl fileObjectService;

    @Autowired
    FailureFeedbackServiceImpl failureFeedbackService;

    @Autowired
    VisitorFingerprintLogServiceImpl visitorFingerprintLogService;

    @ApiOperation(value = "书籍下载页面获取文件提供的下载方式", notes = "")
    @RequestMapping(value = "getFileByBookId", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getFileByBookId(@RequestParam(value = "bookId", required = false) Integer bookId) throws BusinessException, InvocationTargetException, IllegalAccessException {

        List<FileModel> fileModels = fileService.getFileByBookId(bookId);
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }

        List<FileObjectModel> fileObjectModels = fileObjectService.getFileObjectByBookId(bookId);
        List<FileObjectVO> fileObjectVOS = new ArrayList<>();
        for (FileObjectModel fileObjectModel : fileObjectModels) {
            FileObjectVO fileObjectVO = FileObjectController.convertFileObjectVOFromModel(fileObjectModel);
            fileObjectVOS.add(fileObjectVO);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("file", fileVOS);
        map.put("fileObject", fileObjectVOS);

        return CommonReturnType.create(map);
    }

    @ApiOperation(value = "通过 fileId 获取文件信息", notes = "")
    @RequestMapping(value = "getFileById", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getFileById(@RequestParam(value = "fileId", required = false) Integer fileId) throws BusinessException, InvocationTargetException, IllegalAccessException {
        FileModel fileModel = fileService.getFileById(fileId);
        FileVO fileVO = convertFileVOFromModel(fileModel);
        return CommonReturnType.create(fileVO);
    }

    @ApiOperation(value = "【管理员】查询文件列表", notes = "查询文件列表")
    @RequestMapping(value = "list", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType list(@RequestParam(value = "token", required = false) String token) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        List<FileModel> fileModels = fileService.list();
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }
        return CommonReturnType.create(fileVOS);
    }

    @ApiOperation(value = "【管理员】查询文件列表（匹配文件哈希）", notes = "查询文件列表，返回文件哈希为空或者相同的文件")
    @RequestMapping(value = "list/MatchfileHashWithNullValue", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType matchfileHashWithNullValue(@RequestParam(value = "token", required = false) String token,
                                                       @RequestParam(value = "fileSha1", required = true) String fileSha1) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        List<FileModel> fileModels = fileService.selectBySha1WithNullValue(fileSha1);
        List<FileVO> fileVOS = new ArrayList<>();
        for (FileModel fileModel : fileModels) {
            FileVO fileVO = convertFileVOFromModel(fileModel);
            fileVOS.add(fileVO);
        }
        return CommonReturnType.create(fileVOS);
    }

    @ApiOperation(value = "【管理员】通过文件SHA1哈希查找文件Id", notes = "查询文件列表，返回文件哈希匹配的文件Id")
    @RequestMapping(value = "getFileByHash", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getFileByHash(@RequestParam(value = "token", required = false) String token,
                                          @RequestParam(value = "fileSha1", required = true) String fileSha1) throws InvocationTargetException, IllegalAccessException, BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        FileModel fileModel = fileService.selectBySha1(fileSha1);
        FileVO fileVO = convertFileVOFromModel(fileModel);
        return CommonReturnType.create(fileVO);
    }

    @Autowired
    BookServiceImpl bookService;

    @ApiOperation(value = "【管理员】将书籍和文件进行绑定", notes = "")
    @RequestMapping(value = "bindBook", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType bindBook(@RequestParam(value = "token", required = false) String token,
                                     @RequestParam(value = "fileId", required = true) Integer fileId,
                                     @RequestParam(value = "bookId", required = true) Integer bookId) throws BusinessException {

        UserModel userModel = userService.getUserByToken(redisTemplate, token);
        if (userModel == null || !Objects.equals(userModel.getGroup(), "ADMIN")) {
            throw new BusinessException(BusinessErrorCode.OPERATION_NOT_ALLOWED, "非管理员用户无权进行此操作");
        }

        if (fileId == null || bookId == null) {
            throw new BusinessException(BusinessErrorCode.PARAMETER_VALIDATION_ERROR);
        }

        FileModel fileModel = fileService.getFileById(fileId);
        if (fileModel == null) {
            throw new BusinessException(BusinessErrorCode.FILE_NOT_EXIST);
        }

        BookModel bookModel = bookService.getBookById(bookId);
        if (bookModel == null) {
            throw new BusinessException(BusinessErrorCode.BOOK_NOT_EXIST);
        }

        fileModel.setBookId(bookModel.getId());
        Integer affectRows = fileService.updateSelective(fileModel);
        if (affectRows > 0) {
            return CommonReturnType.create("success");
        } else {
            throw new BusinessException(BusinessErrorCode.UNKNOWN_ERROR, "绑定失败，未知错误");
        }
    }

    private FileVO convertFileVOFromModel(FileModel fileModel) {
        if (fileModel == null) {
            return null;
        }
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(fileModel, fileVO);
        fileVO.setFileCreateAt(fileModel.getFileCreateAt().getTime());
        fileVO.setFileModifiedAt(fileModel.getFileModifiedAt().getTime());
        return fileVO;
    }
}
