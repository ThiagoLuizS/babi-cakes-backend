package br.com.babicakesbackend.config;

import br.com.babicakesbackend.models.mapper.CategoryMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {

    @Bean
    @Primary
    public CategoryMapperImpl categoryMapper() {
        return new CategoryMapperImpl();
    }

}
