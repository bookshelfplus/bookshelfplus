import os
from PIL import Image

lengths = [
    16, # 微博开放平台 应用图标
    18,
    24,
    32,
    48,
    64,
    80, # 微博开放平台 应用图标
    100, # QQ互联管理中心 网站图标
    120, # 微博开放平台 应用图标
    128,
    256,
    400,
    512,
    1000,
    1024,
    2000
]
# fileExtension = [ 'png', 'jpg', 'ico' ]

outputDir = "./output"
if not os.path.isdir(outputDir):
    os.mkdir(outputDir) # 指定的目录不存在

pic = Image.open('书栖网.png')
# pic = Image.open('logo-origin.svg')
for length in lengths:
    newpic = pic.resize((length, length),Image.ANTIALIAS)
    print (newpic)
    fileName = './output/logo_'+str(length)+'x'+str(length) # 包含文件名，不包含文件后缀的相对路径
    newpic.save(fileName+'.png')
    newpic.save(fileName+'.ico')

    newWhitebgPic = Image.new("RGBA", newpic.size, "WHITE")
    newWhitebgPic.paste(newpic, (0, 0), newpic)
    newWhitebgPic.convert('RGB').save(fileName+'.jpg', "JPEG")
