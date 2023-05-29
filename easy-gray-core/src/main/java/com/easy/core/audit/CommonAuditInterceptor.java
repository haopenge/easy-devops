package com.easy.core.audit;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.easy.core.audit.anno.CreateTime;
import com.easy.core.audit.anno.Creator;
import com.easy.core.audit.anno.Modifier;
import com.easy.core.audit.anno.ModifyTime;
import com.easy.core.audit.anno.PrimaryKey;
import com.easy.core.exception.BaseEasyException;
import com.easy.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.easy.core.enumx.BaseFailureEnum.AUDIT_ERROR;

/**
 * 实体注解通用拦截器
 *
 * @author liuph
 */
@Slf4j
public class CommonAuditInterceptor {

    /**
     * 默认注解-生成器映射
     */
    private static final Map<Class<? extends Annotation>, Class<? extends CommonGenerator>> defaultGeneratorMap = new HashMap<>();

    /**
     * 处理审计注解
     *
     * @param obj      实体对象
     * @param isCreate 是否为新增
     */
    public static void handler(Object obj, boolean isCreate) {
        if (Objects.isNull(obj)) {
            return;
        }
        // 1. 获取待处理审计注解
        AuditMetadata auditMetadata = AuditUtil.getAuditMetadata(obj.getClass());
        Map<Class<? extends Annotation>, List<AnnoField>> annoFieldMap = auditMetadata.getAnnoFieldMap();
        if (CollectionUtils.isEmpty(annoFieldMap)) {
            return;
        }

        // 2. 处理审计注解
        for (Map.Entry<Class<? extends Annotation>, List<AnnoField>> entry : annoFieldMap.entrySet()) {
            Class<? extends Annotation> loopAnnoClass = entry.getKey();
            List<AnnoField> loopAnnoFields = entry.getValue();

            // 非新增跳过主键生成
            if (!isCreate && Objects.equals(loopAnnoClass, PrimaryKey.class)) {
                continue;
            }
            for (AnnoField loopAnnoField : loopAnnoFields) {
                autowiredField(obj, loopAnnoClass, loopAnnoField, isCreate);
            }
        }
    }

    /**
     * 自动设置属性，注：注解需要有自定生成方法
     *
     * @param obj             待注入对象
     * @param annotationClass 注解class
     * @param annoField       注入属性
     * @param isCreate        是否为新增
     */
    public static void autowiredField(Object obj, Class<? extends Annotation> annotationClass, AnnoField annoField, boolean isCreate) {
        Field field = annoField.getField();
        Object fieldValue = ClassUtil.getFieldValue(obj, field);
        if (Objects.nonNull(fieldValue)) {
            return;
        }
        Class<? extends CommonGenerator> generatorClass = annoField.getGenerator();
        // 根据注解类型，匹配默认生成器
        if (Objects.equals(generatorClass, DefaultCommonGenerator.class)) {
            generatorClass = getDefaultGeneratorClass(annotationClass);
        }
        CommonGenerator<?> generator = SpringUtil.getBean(generatorClass);
        if (Objects.isNull(generator)) {
            return;
        }
        fieldValue = generator.generate(obj, annoField, isCreate);
        boolean success = ClassUtil.setFiledValue(field, obj, fieldValue);
        if (!success) {
            log.error("数据库实体属性设置失败, objName : {} , fieldName : {} ", obj.getClass().getName(), field.getName());
            throw new BaseEasyException(AUDIT_ERROR);
        }
    }

    /**
     * 获取审计注解对应的默认生成器class
     *
     * @param annoClass 注解类
     * @return 默认生成器class
     */
    public static Class<? extends CommonGenerator> getDefaultGeneratorClass(Class<? extends Annotation> annoClass) {
        if (CollectionUtil.isEmpty(defaultGeneratorMap)) {
            // 默认注解-生成器映射初始化
            defaultGeneratorMap.put(CreateTime.class, DefaultTimeGenerator.class);
            defaultGeneratorMap.put(ModifyTime.class, DefaultTimeGenerator.class);
            defaultGeneratorMap.put(Creator.class, DefaultOperatorGenerator.class);
            defaultGeneratorMap.put(Modifier.class, DefaultOperatorGenerator.class);
            defaultGeneratorMap.put(PrimaryKey.class, DefaultPrimaryKeyGenerator.class);
        }
        return defaultGeneratorMap.get(annoClass);
    }
}
