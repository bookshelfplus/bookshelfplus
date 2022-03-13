const fs = require('fs');
var path = require('path');

function cleanup() {
    var fontminPath = path.join(__dirname, 'public/fontmin');
    if (fs.existsSync(fontminPath)) {
        console.log("Cleaning up temporary files: public/fontmin/*.ttf");
        let files = fs.readdirSync(fontminPath);
        files.forEach((file, index) => {
            if (file.endsWith('.ttf')) {
                let curPath = fontminPath + "/" + file;
                fs.unlinkSync(curPath); //删除文件
                console.log(" | removed file: " + file);
            }
        });
        console.log("Temporary files cleared successfully");
    }
}

cleanup();

module.exports = cleanup;