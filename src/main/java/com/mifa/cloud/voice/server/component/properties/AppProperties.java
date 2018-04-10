package com.mifa.cloud.voice.server.component.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义参数
 * 
 * @author 宋烜明
 *
 */
@Data
@ConfigurationProperties(prefix = "appConfig")
public class AppProperties {


	private AliyunVoice aliyunVoice;

    private JixinVoice jixinVoice;

	@Data
	public static class AliyunVoice{
		private String accessId;
		private String accessSecret;
		private String voiceUrl;
	}
    @Data
	public static class JixinVoice{
        private String accessId;
        private String authToken;
        private String voiceUrl;
    }


}

