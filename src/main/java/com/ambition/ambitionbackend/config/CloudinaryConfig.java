import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfwmbsrne",
                "api_key", "229566582211349",
                "api_secret", "CMn6RQKevV4Z86znS1PPhAj6DyI"
        ));
    }
}
