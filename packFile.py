# 书栖网 电子书打包工具

# 获取系统命令行参数
import sys
# 获取当前目录
import os
# 压缩文件
import zipfile
# 计算哈希
import hashlib
# 获取文档缩略图
import fitz
# 写入JSON文件
import json

def get_filePath_fileName_fileExt(fileUrl):
    """
    获取文件路径， 文件名， 后缀名
    :param fileUrl:
    :return:
    """
    filepath, tmpfilename = os.path.split(fileUrl)
    shotname, extension = os.path.splitext(tmpfilename)
    return filepath, shotname, extension

def get_fileName(fileUrl):
    """
    获取文件名
    :param fileUrl:
    :return:
    """
    return os.path.basename(fileUrl)

def pack(filename, zipFileName):
    f = zipfile.ZipFile(zipFileName, 'w', zipfile.ZIP_DEFLATED)
    f.write(filename, get_fileName(filename))
    f.write("书栖网 bookshelf.plus.txt")
    f.close()

def CalcSha1(filepath):
  with open(filepath,'rb') as f:
    sha1obj = hashlib.sha1()
    sha1obj.update(f.read())
    hash = sha1obj.hexdigest()
    print(hash)
    return hash

def getCover(file_name, saveName):
    """
    获取封面
    :param filepath:
    :return:
    """
    cover = ""
    try:
        if '.pdf' not in file_name:
            print("此文件非PDF文件")
            return None
        #  打开PDF文件，生成一个对象
        doc = fitz.open(file_name)
        if doc.pageCount > 1:
            page = doc[0]
            rotate = int(0)
            # 每个尺寸的缩放系数为2，这将为我们生成分辨率提高四倍的图像。
            zoom_x = 2.0
            zoom_y = 2.0
            trans = fitz.Matrix(zoom_x, zoom_y).prerotate(rotate)
            pm = page.get_pixmap(matrix=trans, alpha=False)
            # 保存路径
            pm.save(saveName)
            # pm.save('%s.png' % get_filePath_fileName_fileExt(file_name)[1])
            print("完成")
            return doc.pageCount
        else:
            return None
    except Exception as e:
        print(e)

# 字节bytes转化kb\m\g
def formatSize(bytes):
    try:
        bytes = float(bytes)
        kb = bytes / 1024
    except:
        print("传入的字节格式不对")
        return "Error"

    if kb >= 1024:
        M = kb / 1024
        if M >= 1024:
            G = M / 1024
            return "%fG" % (G)
        else:
            return "%fM" % (M)
    else:
        return "%fkb" % (kb)

if __name__=='__main__':
    print("脚本名", sys.argv[0])
    print("当前文件夹", os.getcwd())
    if len(sys.argv) < 2:
        print("请拖入文件！")
        input("按回车键继续...")
        exit
    elif len(sys.argv) > 2:
        print("只能拖入一个文件！")
        input("按回车键继续...")
        exit

    if not os.path.exists("output"):
        os.mkdir("output")

    # 文件名
    filename = sys.argv[1]
    print(get_fileName(filename), filename)
    hash = CalcSha1(filename)

    # 压缩文件
    zipFileName = "output/"+"zipFile.zip"
    pack(filename, zipFileName)
    os.rename(zipFileName, "output/"+hash+".zip")

    # 页数
    pageCount = None
    # 封面
    if '.pdf' in sys.argv[1]:
        # 单个文件尝试获取封面
        pdfFile = sys.argv[1]
        pageCount = getCover(pdfFile, "output/"+hash+".png")

    # 文件大小
    fileSize = os.path.getsize(filename)

    # JSON
    result = {
        'name': get_fileName(filename),
        "pageCount": pageCount,
        'size': fileSize,
        'size_format': formatSize(fileSize)
    }
    with open("output/"+hash+".json", 'w') as f:
        json.dump(result, f)
    input()