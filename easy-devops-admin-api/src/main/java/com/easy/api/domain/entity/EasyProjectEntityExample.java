package com.easy.api.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyProjectEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EasyProjectEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdIsNull() {
            addCriterion("easy_env_id is null");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdIsNotNull() {
            addCriterion("easy_env_id is not null");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdEqualTo(Integer value) {
            addCriterion("easy_env_id =", value, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdNotEqualTo(Integer value) {
            addCriterion("easy_env_id <>", value, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdGreaterThan(Integer value) {
            addCriterion("easy_env_id >", value, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("easy_env_id >=", value, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdLessThan(Integer value) {
            addCriterion("easy_env_id <", value, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdLessThanOrEqualTo(Integer value) {
            addCriterion("easy_env_id <=", value, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdIn(List<Integer> values) {
            addCriterion("easy_env_id in", values, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdNotIn(List<Integer> values) {
            addCriterion("easy_env_id not in", values, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdBetween(Integer value1, Integer value2) {
            addCriterion("easy_env_id between", value1, value2, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyEnvIdNotBetween(Integer value1, Integer value2) {
            addCriterion("easy_env_id not between", value1, value2, "easyEnvId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdIsNull() {
            addCriterion("easy_repository_id is null");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdIsNotNull() {
            addCriterion("easy_repository_id is not null");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdEqualTo(Integer value) {
            addCriterion("easy_repository_id =", value, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdNotEqualTo(Integer value) {
            addCriterion("easy_repository_id <>", value, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdGreaterThan(Integer value) {
            addCriterion("easy_repository_id >", value, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("easy_repository_id >=", value, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdLessThan(Integer value) {
            addCriterion("easy_repository_id <", value, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("easy_repository_id <=", value, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdIn(List<Integer> values) {
            addCriterion("easy_repository_id in", values, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdNotIn(List<Integer> values) {
            addCriterion("easy_repository_id not in", values, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdBetween(Integer value1, Integer value2) {
            addCriterion("easy_repository_id between", value1, value2, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andEasyRepositoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("easy_repository_id not between", value1, value2, "easyRepositoryId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andBuildPathIsNull() {
            addCriterion("build_path is null");
            return (Criteria) this;
        }

        public Criteria andBuildPathIsNotNull() {
            addCriterion("build_path is not null");
            return (Criteria) this;
        }

        public Criteria andBuildPathEqualTo(String value) {
            addCriterion("build_path =", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathNotEqualTo(String value) {
            addCriterion("build_path <>", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathGreaterThan(String value) {
            addCriterion("build_path >", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathGreaterThanOrEqualTo(String value) {
            addCriterion("build_path >=", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathLessThan(String value) {
            addCriterion("build_path <", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathLessThanOrEqualTo(String value) {
            addCriterion("build_path <=", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathLike(String value) {
            addCriterion("build_path like", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathNotLike(String value) {
            addCriterion("build_path not like", value, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathIn(List<String> values) {
            addCriterion("build_path in", values, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathNotIn(List<String> values) {
            addCriterion("build_path not in", values, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathBetween(String value1, String value2) {
            addCriterion("build_path between", value1, value2, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBuildPathNotBetween(String value1, String value2) {
            addCriterion("build_path not between", value1, value2, "buildPath");
            return (Criteria) this;
        }

        public Criteria andBranchIsNull() {
            addCriterion("branch is null");
            return (Criteria) this;
        }

        public Criteria andBranchIsNotNull() {
            addCriterion("branch is not null");
            return (Criteria) this;
        }

        public Criteria andBranchEqualTo(String value) {
            addCriterion("branch =", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotEqualTo(String value) {
            addCriterion("branch <>", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchGreaterThan(String value) {
            addCriterion("branch >", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchGreaterThanOrEqualTo(String value) {
            addCriterion("branch >=", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLessThan(String value) {
            addCriterion("branch <", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLessThanOrEqualTo(String value) {
            addCriterion("branch <=", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLike(String value) {
            addCriterion("branch like", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotLike(String value) {
            addCriterion("branch not like", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchIn(List<String> values) {
            addCriterion("branch in", values, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotIn(List<String> values) {
            addCriterion("branch not in", values, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchBetween(String value1, String value2) {
            addCriterion("branch between", value1, value2, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotBetween(String value1, String value2) {
            addCriterion("branch not between", value1, value2, "branch");
            return (Criteria) this;
        }

        public Criteria andHashCodeIsNull() {
            addCriterion("hash_code is null");
            return (Criteria) this;
        }

        public Criteria andHashCodeIsNotNull() {
            addCriterion("hash_code is not null");
            return (Criteria) this;
        }

        public Criteria andHashCodeEqualTo(String value) {
            addCriterion("hash_code =", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeNotEqualTo(String value) {
            addCriterion("hash_code <>", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeGreaterThan(String value) {
            addCriterion("hash_code >", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeGreaterThanOrEqualTo(String value) {
            addCriterion("hash_code >=", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeLessThan(String value) {
            addCriterion("hash_code <", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeLessThanOrEqualTo(String value) {
            addCriterion("hash_code <=", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeLike(String value) {
            addCriterion("hash_code like", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeNotLike(String value) {
            addCriterion("hash_code not like", value, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeIn(List<String> values) {
            addCriterion("hash_code in", values, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeNotIn(List<String> values) {
            addCriterion("hash_code not in", values, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeBetween(String value1, String value2) {
            addCriterion("hash_code between", value1, value2, "hashCode");
            return (Criteria) this;
        }

        public Criteria andHashCodeNotBetween(String value1, String value2) {
            addCriterion("hash_code not between", value1, value2, "hashCode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}