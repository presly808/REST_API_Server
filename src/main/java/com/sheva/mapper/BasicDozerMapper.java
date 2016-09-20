package com.sheva.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by vlad on 20.09.16.
 */
@Component
public class BasicDozerMapper {
    private DozerBeanMapper basicMapper = null;

    @PostConstruct
    public void init() {
        basicMapper = new DozerBeanMapper();
        basicMapper.setMappingFiles(Arrays.asList("mapping.xml"));
    }

    public <T> T map(Object bean, Class<T> expectedClass) {
        if(bean == null) {
            return null;
        }
        return basicMapper.map(bean, expectedClass);
    }

    public void map(Object bean, Object resultBean) {
        basicMapper.map(bean, resultBean);
    }
}
