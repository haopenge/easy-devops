package com.easy.core.audit;

import com.easy.core.util.PrimaryKeyUtil;
import org.springframework.stereotype.Service;


/**
 * 默认主键生成器
 *
 * @author liuph
 */
@Service
public class DefaultPrimaryKeyGenerator implements CommonGenerator<Object> {

    @Override
    public Object generate(Object entity, AnnoField annoField, boolean isCreate) {
        return PrimaryKeyUtil.nextId();
    }

}
