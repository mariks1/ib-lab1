package khasanshin.iblab1.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import khasanshin.iblab1.utils.HtmlEscapingStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonHtmlEscapeConfig {

    @Bean
    public Module htmlEscapeModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new HtmlEscapingStringSerializer());
        return module;
    }
}
