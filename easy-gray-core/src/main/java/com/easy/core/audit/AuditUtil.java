package com.easy.core.audit;


import com.easy.core.audit.anno.CreateTime;
import com.easy.core.audit.anno.Creator;
import com.easy.core.audit.anno.Modifier;
import com.easy.core.audit.anno.ModifyTime;
import com.easy.core.audit.anno.PrimaryKey;
import com.easy.core.util.ClassUtil;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 审计专用工具类.
 *
 * @author liupenghao
 */
public final class AuditUtil {
    private AuditUtil() {
    }

    /**
     * 审计注解类列表
     */
    private static final List<Class<? extends Annotation>> ANNO_CLASS_LIST = Arrays.asList(ModifyTime.class, CreateTime.class, PrimaryKey.class, Modifier.class, Creator.class);

    /**
     * 实体类与对应审计信息关联映射
     */
    private static final Map<Class<?>, AuditMetadata> AUDIT_METADATA_MAP = new HashMap<>();

    /**
     * 属性生成器注解key
     */
    private static final String FIELD_GENERATOR_KEY = "generator";

    /**
     * 获取给定类对应的审计元数据信息.
     *
     * @param clazz 待获取字段所属类
     * @return 获取到的给定类对应的审计元数据信息
     */
    public static AuditMetadata getAuditMetadata(Class<?> clazz) {
        if (!AUDIT_METADATA_MAP.containsKey(clazz)) {
            synchronized (clazz) {
                if (!AUDIT_METADATA_MAP.containsKey(clazz)) {
                    Map<Class<? extends Annotation>, List<Field>> annoFieldMap = ClassUtil.getAnnoFieldsMap(clazz, ANNO_CLASS_LIST);
                    AuditMetadata metadata = AuditMetadata.builder()
                            .primaryKeyField(first(parse(annoFieldMap.get(PrimaryKey.class), PrimaryKey.class), false))
                            .creatorFields(parse(annoFieldMap.get(Creator.class), Creator.class))
                            .modifierFields(parse(annoFieldMap.get(Modifier.class), Modifier.class))
                            .createTimeFields(parse(annoFieldMap.get(CreateTime.class), CreateTime.class))
                            .modifyTimeFields(parse(annoFieldMap.get(ModifyTime.class), ModifyTime.class)).build();
                    AUDIT_METADATA_MAP.put(clazz, metadata);
                }
            }
        }
        return AUDIT_METADATA_MAP.get(clazz);
    }

    /**
     * 从给定列表中获取并返回第一个符合条件的元素.
     * <p>列表为空时返回null.</p>
     *
     * @param list    给定的元素列表
     * @param notNull 是否要求获取的元素非空
     * @param <T>     元素类型
     * @return 获取到的列表的第一个符合条件的元素（如果存在的话）
     */
    public static <T> T first(Collection<T> list, boolean notNull) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (notNull) {
            return list.stream().filter(Objects::nonNull).findFirst().orElse(null);
        } else {
            return list.stream().findFirst().orElse(null);
        }
    }

    /**
     * 解析指定注解类的字段列表，构造并返回注解字段元数据信息列表.
     *
     * @param fields    待解析的字段列表
     * @param annoClass 待解析的注解类
     * @return 解析后得到的注解字段元数据信息列表
     */
    private static List<AnnoField> parse(List<Field> fields, Class<? extends Annotation> annoClass) {
        if (CollectionUtils.isEmpty(fields)) {
            return Collections.emptyList();
        }
        List<AnnoField> annoFields = new ArrayList<>(fields.size());
        for (Field loopField : fields) {
            Annotation annotation = loopField.getAnnotation(annoClass);
            Class<CommonGenerator<?>> generatorClass = (Class<CommonGenerator<?>>) ClassUtil.getFieldAnnotationMemberValue(annotation, FIELD_GENERATOR_KEY);
            annoFields.add(new AnnoField(loopField, annotation, generatorClass));
        }
        return annoFields;
    }
}
