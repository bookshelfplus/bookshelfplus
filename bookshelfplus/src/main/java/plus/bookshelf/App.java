package plus.bookshelf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = { "plus.bookshelf" })
@RestController
@MapperScan("plus.bookshelf.mapper")
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

    }

    @RequestMapping("/")
    public String Home() {
        return "首页";
    }
}
