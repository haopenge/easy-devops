package com.easy.api.mapper;

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

import com.easy.api.domain.entity.GrayProjectEntity;
import com.easy.api.domain.entity.GrayProjectEntityExample.Criteria;
import com.easy.api.domain.entity.GrayProjectEntityExample.Criterion;
import com.easy.api.domain.entity.GrayProjectEntityExample;
import java.util.List;
import java.util.Map;

public class GrayProjectEntitySqlProvider {

    public String countByExample(GrayProjectEntityExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("gray_project");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(GrayProjectEntityExample example) {
        BEGIN();
        DELETE_FROM("gray_project");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(GrayProjectEntity record) {
        BEGIN();
        INSERT_INTO("gray_project");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGrayEnvId() != null) {
            VALUES("gray_env_id", "#{grayEnvId,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubProjectPath() != null) {
            VALUES("sub_project_path", "#{subProjectPath,jdbcType=VARCHAR}");
        }
        
        if (record.getBranch() != null) {
            VALUES("branch", "#{branch,jdbcType=VARCHAR}");
        }
        
        if (record.getCloneUrl() != null) {
            VALUES("clone_url", "#{cloneUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            VALUES("`status`", "#{status,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String selectByExample(GrayProjectEntityExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("create_time");
        SELECT("update_time");
        SELECT("gray_env_id");
        SELECT("description");
        SELECT("`name`");
        SELECT("sub_project_path");
        SELECT("branch");
        SELECT("clone_url");
        SELECT("`status`");
        FROM("gray_project");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        GrayProjectEntity record = (GrayProjectEntity) parameter.get("record");
        GrayProjectEntityExample example = (GrayProjectEntityExample) parameter.get("example");
        
        BEGIN();
        UPDATE("gray_project");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGrayEnvId() != null) {
            SET("gray_env_id = #{record.grayEnvId,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            SET("description = #{record.description,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            SET("`name` = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubProjectPath() != null) {
            SET("sub_project_path = #{record.subProjectPath,jdbcType=VARCHAR}");
        }
        
        if (record.getBranch() != null) {
            SET("branch = #{record.branch,jdbcType=VARCHAR}");
        }
        
        if (record.getCloneUrl() != null) {
            SET("clone_url = #{record.cloneUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("`status` = #{record.status,jdbcType=INTEGER}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("gray_project");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("gray_env_id = #{record.grayEnvId,jdbcType=INTEGER}");
        SET("description = #{record.description,jdbcType=VARCHAR}");
        SET("`name` = #{record.name,jdbcType=VARCHAR}");
        SET("sub_project_path = #{record.subProjectPath,jdbcType=VARCHAR}");
        SET("branch = #{record.branch,jdbcType=VARCHAR}");
        SET("clone_url = #{record.cloneUrl,jdbcType=VARCHAR}");
        SET("`status` = #{record.status,jdbcType=INTEGER}");
        
        GrayProjectEntityExample example = (GrayProjectEntityExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(GrayProjectEntity record) {
        BEGIN();
        UPDATE("gray_project");
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGrayEnvId() != null) {
            SET("gray_env_id = #{grayEnvId,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubProjectPath() != null) {
            SET("sub_project_path = #{subProjectPath,jdbcType=VARCHAR}");
        }
        
        if (record.getBranch() != null) {
            SET("branch = #{branch,jdbcType=VARCHAR}");
        }
        
        if (record.getCloneUrl() != null) {
            SET("clone_url = #{cloneUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("`status` = #{status,jdbcType=INTEGER}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(GrayProjectEntityExample example, boolean includeExamplePhrase) {
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