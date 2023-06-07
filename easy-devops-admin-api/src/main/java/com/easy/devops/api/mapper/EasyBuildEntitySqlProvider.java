package com.easy.devops.api.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.easy.devops.api.domain.entity.EasyBuildEntity;
import com.easy.devops.api.domain.entity.EasyBuildEntityExample.Criteria;
import com.easy.devops.api.domain.entity.EasyBuildEntityExample.Criterion;
import com.easy.devops.api.domain.entity.EasyBuildEntityExample;
import java.util.List;
import java.util.Map;

public class EasyBuildEntitySqlProvider {

    public String countByExample(EasyBuildEntityExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("easy_build");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(EasyBuildEntityExample example) {
        BEGIN();
        DELETE_FROM("easy_build");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(EasyBuildEntity record) {
        BEGIN();
        INSERT_INTO("easy_build");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }

        if (record.getEasyRepositoryId() != null) {
            VALUES("easy_repository_id", "#{easyRepositoryId,jdbcType=INTEGER}");
        }

        if (record.getOrder() != null) {
            VALUES("order", "#{order,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            VALUES("type", "#{type,jdbcType=INTEGER}");
        }

        if (record.getContent() != null) {
            VALUES("content", "#{content,jdbcType=LONGVARCHAR}");
        }

        return SQL();
    }

    public String selectByExampleWithBLOBs(EasyBuildEntityExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("create_time");
        SELECT("update_time");
        SELECT("easy_repository_id");
        SELECT("order");
        SELECT("type");
        SELECT("content");
        FROM("easy_build");
        applyWhere(example, false);

        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }

        return SQL();
    }

    public String selectByExample(EasyBuildEntityExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("create_time");
        SELECT("update_time");
        SELECT("easy_repository_id");
        SELECT("order");
        SELECT("type");
        FROM("easy_build");
        applyWhere(example, false);

        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }

        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        EasyBuildEntity record = (EasyBuildEntity) parameter.get("record");
        EasyBuildEntityExample example = (EasyBuildEntityExample) parameter.get("example");

        BEGIN();
        UPDATE("easy_build");

        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }

        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }

        if (record.getEasyRepositoryId() != null) {
            SET("easy_repository_id = #{record.easyRepositoryId,jdbcType=INTEGER}");
        }

        if (record.getOrder() != null) {
            SET("order = #{record.order,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            SET("type = #{record.type,jdbcType=INTEGER}");
        }

        if (record.getContent() != null) {
            SET("content = #{record.content,jdbcType=LONGVARCHAR}");
        }

        applyWhere(example, true);
        return SQL();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("easy_build");

        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("easy_repository_id = #{record.easyRepositoryId,jdbcType=INTEGER}");
        SET("order = #{record.order,jdbcType=INTEGER}");
        SET("type = #{record.type,jdbcType=INTEGER}");
        SET("content = #{record.content,jdbcType=LONGVARCHAR}");

        EasyBuildEntityExample example = (EasyBuildEntityExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("easy_build");

        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("easy_repository_id = #{record.easyRepositoryId,jdbcType=INTEGER}");
        SET("order = #{record.order,jdbcType=INTEGER}");
        SET("type = #{record.type,jdbcType=INTEGER}");

        EasyBuildEntityExample example = (EasyBuildEntityExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(EasyBuildEntity record) {
        BEGIN();
        UPDATE("easy_build");

        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }

        if (record.getEasyRepositoryId() != null) {
            SET("easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER}");
        }

        if (record.getOrder() != null) {
            SET("order = #{order,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            SET("type = #{type,jdbcType=INTEGER}");
        }

        if (record.getContent() != null) {
            SET("content = #{content,jdbcType=LONGVARCHAR}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }

    protected void applyWhere(EasyBuildEntityExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }

        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }

        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }

                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }

                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }

        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}
