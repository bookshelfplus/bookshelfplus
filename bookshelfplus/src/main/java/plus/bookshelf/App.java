package plus.bookshelf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println("backend service started successfully.");
    }

    @RequestMapping("/")
    public String Home() {
        return "backend service is running.";
    }
}
