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

import com.easy.api.domain.entity.EasyProjectEntity;
import com.easy.api.domain.entity.EasyProjectEntityExample.Criteria;
import com.easy.api.domain.entity.EasyProjectEntityExample.Criterion;
import com.easy.api.domain.entity.EasyProjectEntityExample;
import java.util.List;
import java.util.Map;

public class EasyProjectEntitySqlProvider {

    public String countByExample(EasyProjectEntityExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("easy_project");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(EasyProjectEntityExample example) {
        BEGIN();
        DELETE_FROM("easy_project");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(EasyProjectEntity record) {
        BEGIN();
        INSERT_INTO("easy_project");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEasyEnvId() != null) {
            VALUES("easy_env_id", "#{easyEnvId,jdbcType=INTEGER}");
        }
        
        if (record.getEasyRepositoryId() != null) {
            VALUES("easy_repository_id", "#{easyRepositoryId,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getBuildPath() != null) {
            VALUES("build_path", "#{buildPath,jdbcType=VARCHAR}");
        }
        
        if (record.getBranch() != null) {
            VALUES("branch", "#{branch,jdbcType=VARCHAR}");
        }
        
        if (record.getHashCode() != null) {
            VALUES("hash_code", "#{hashCode,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(EasyProjectEntityExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("create_time");
        SELECT("update_time");
        SELECT("easy_env_id");
        SELECT("easy_repository_id");
        SELECT("name");
        SELECT("build_path");
        SELECT("branch");
        SELECT("hash_code");
        FROM("easy_project");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        EasyProjectEntity record = (EasyProjectEntity) parameter.get("record");
        EasyProjectEntityExample example = (EasyProjectEntityExample) parameter.get("example");
        
        BEGIN();
        UPDATE("easy_project");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEasyEnvId() != null) {
            SET("easy_env_id = #{record.easyEnvId,jdbcType=INTEGER}");
        }
        
        if (record.getEasyRepositoryId() != null) {
            SET("easy_repository_id = #{record.easyRepositoryId,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            SET("name = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getBuildPath() != null) {
            SET("build_path = #{record.buildPath,jdbcType=VARCHAR}");
        }
        
        if (record.getBranch() != null) {
            SET("branch = #{record.branch,jdbcType=VARCHAR}");
        }
        
        if (record.getHashCode() != null) {
            SET("hash_code = #{record.hashCode,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("easy_project");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("easy_env_id = #{record.easyEnvId,jdbcType=INTEGER}");
        SET("easy_repository_id = #{record.easyRepositoryId,jdbcType=INTEGER}");
        SET("name = #{record.name,jdbcType=VARCHAR}");
        SET("build_path = #{record.buildPath,jdbcType=VARCHAR}");
        SET("branch = #{record.branch,jdbcType=VARCHAR}");
        SET("hash_code = #{record.hashCode,jdbcType=VARCHAR}");
        
        EasyProjectEntityExample example = (EasyProjectEntityExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(EasyProjectEntity record) {
        BEGIN();
        UPDATE("easy_project");
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEasyEnvId() != null) {
            SET("easy_env_id = #{easyEnvId,jdbcType=INTEGER}");
        }
        
        if (record.getEasyRepositoryId() != null) {
            SET("easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getBuildPath() != null) {
            SET("build_path = #{buildPath,jdbcType=VARCHAR}");
        }
        
        if (record.getBranch() != null) {
            SET("branch = #{branch,jdbcType=VARCHAR}");
        }
        
        if (record.getHashCode() != null) {
            SET("hash_code = #{hashCode,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(EasyProjectEntityExample example, boolean includeExamplePhrase) {
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