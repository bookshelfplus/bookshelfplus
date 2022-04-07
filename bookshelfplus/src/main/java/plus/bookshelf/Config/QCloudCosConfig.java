package plus.bookshelf.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class QCloudCosConfig {
    @Value("${qcloud.cos.accessKey}")
    private String accessKey;

    @Value("${qcloud.cos.secretKey}")
    private String secretKey;

    @Value("${qcloud.cos.regionName}")
    private String regionName;

    @Value("${qcloud.cos.bucketName}")
    private String bucketName;

    @Value("${qcloud.cos.keyName}")
    private String keyName;
}
