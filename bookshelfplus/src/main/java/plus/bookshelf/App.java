package plus.bookshelf;

import com.qcloud.cos.http.HttpMethodName;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.bookshelf.Common.FileManager.QCloudCosUtils;
import plus.bookshelf.Config.QCloudCosConfig;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"plus.bookshelf"})
@RestController
@MapperScan("plus.bookshelf.Dao.Mapper")
// @EnableTransactionManagement // 引入事务管理
public class App {
    public static void main(String[] args) {
        System.out.println("Dreams remain daydreams until they are put into action. \n" +
                "Now, let's start.");

        // 启动SpringBoot项目
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/")
    public String Home() {
        return "首页";
    }

    @Autowired
    QCloudCosConfig qCloudCosConfig;

    @RequestMapping("/cos")
    public String cos() {
        QCloudCosUtils QCloudCosUtils = new QCloudCosUtils(qCloudCosConfig);
        return QCloudCosUtils.getUrl("user-login-token", HttpMethodName.POST, "mydemo.jpg", 5);
    }
}
