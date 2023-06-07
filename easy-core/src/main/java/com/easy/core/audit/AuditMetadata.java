package com.easy.core.audit;

import com.easy.core.audit.anno.CreateTime;
import com.easy.core.audit.anno.Creator;
import com.easy.core.audit.anno.Modifier;
import com.easy.core.audit.anno.ModifyTime;
import com.easy.core.audit.anno.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 审计用元数据信息.
 *
 * @author liupenghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditMetadata implements Serializable {
    /**
     * 主键注解字段
     */
    private AnnoField primaryKeyField;

    /**
     * 创建者注解字段列表
     */
    private List<AnnoField> creatorFields;

    /**
     * 更新者注解字段列表
     */
    private List<AnnoField> modifierFields;

    /**
     * 创建时间注解字段列表
     */
    private List<AnnoField> createTimeFields;

    /**
     * 更新时间注解字段列表
     */
    private List<AnnoField> modifyTimeFields;

    /**
     * 获取注解属性map
     *
     * @return 注解属性map
     */
    public Map<Class<? extends Annotation>, List<AnnoField>> getAnnoFieldMap() {
        Map<Class<? extends Annotation>, List<AnnoField>> annoFieldMap = new HashMap<>();
        if (Objects.nonNull(primaryKeyField)) {
            annoFieldMap.put(PrimaryKey.class, Collections.singletonList(primaryKeyField));
        }
        annoFieldMap.put(Creator.class, creatorFields);
        annoFieldMap.put(Modifier.class, modifierFields);
        annoFieldMap.put(CreateTime.class, createTimeFields);
        annoFieldMap.put(ModifyTime.class, modifyTimeFields);
        return annoFieldMap;
    }
}
