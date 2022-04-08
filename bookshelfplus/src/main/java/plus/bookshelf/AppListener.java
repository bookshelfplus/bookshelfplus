package plus.bookshelf;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import plus.bookshelf.Common.FileManager.QCloudCosUtils;

@Component
public class AppListener implements CommandLineRunner, DisposableBean {
    //应用启动成功后的回调
    @Override
    public void run(String... args) throws Exception {
        System.out.println("prepare to start ...");
    }

    //应用启动关闭前的回调
    @Override
    public void destroy() throws Exception {
        System.out.println("prepare to close ...");
        QCloudCosUtils.destoryInstance();
        System.out.println("close success ...");
    }
}
