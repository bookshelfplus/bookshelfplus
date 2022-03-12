package plus.bookshelf.Common.Enum.plus.bookshelf.TencentCloud.COS;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cos")
@PropertySource(value = "cos.properties")
public class CosProperties {
    // @Value("${cos.accessKey}")
    private String accessKey;

    // @Value("${cos.secretKey}")
    private String secretKey;

    // @Value("${cos.regionName}")
    private String regionName;

    // @Value("${cos.bucketName}")
    private String bucketName;

    // @Value("${cos.keyName}")
    private String keyName;
}
