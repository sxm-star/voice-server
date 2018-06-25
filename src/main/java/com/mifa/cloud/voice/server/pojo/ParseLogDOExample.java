package com.mifa.cloud.voice.server.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ParseLogDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ParseLogDOExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParseFileIdIsNull() {
            addCriterion("parse_file_id is null");
            return (Criteria) this;
        }

        public Criteria andParseFileIdIsNotNull() {
            addCriterion("parse_file_id is not null");
            return (Criteria) this;
        }

        public Criteria andParseFileIdEqualTo(Long value) {
            addCriterion("parse_file_id =", value, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdNotEqualTo(Long value) {
            addCriterion("parse_file_id <>", value, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdGreaterThan(Long value) {
            addCriterion("parse_file_id >", value, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parse_file_id >=", value, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdLessThan(Long value) {
            addCriterion("parse_file_id <", value, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdLessThanOrEqualTo(Long value) {
            addCriterion("parse_file_id <=", value, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdIn(List<Long> values) {
            addCriterion("parse_file_id in", values, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdNotIn(List<Long> values) {
            addCriterion("parse_file_id not in", values, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdBetween(Long value1, Long value2) {
            addCriterion("parse_file_id between", value1, value2, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileIdNotBetween(Long value1, Long value2) {
            addCriterion("parse_file_id not between", value1, value2, "parseFileId");
            return (Criteria) this;
        }

        public Criteria andParseFileNameIsNull() {
            addCriterion("parse_file_name is null");
            return (Criteria) this;
        }

        public Criteria andParseFileNameIsNotNull() {
            addCriterion("parse_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andParseFileNameEqualTo(String value) {
            addCriterion("parse_file_name =", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameNotEqualTo(String value) {
            addCriterion("parse_file_name <>", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameGreaterThan(String value) {
            addCriterion("parse_file_name >", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("parse_file_name >=", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameLessThan(String value) {
            addCriterion("parse_file_name <", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameLessThanOrEqualTo(String value) {
            addCriterion("parse_file_name <=", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameLike(String value) {
            addCriterion("parse_file_name like", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameNotLike(String value) {
            addCriterion("parse_file_name not like", value, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameIn(List<String> values) {
            addCriterion("parse_file_name in", values, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameNotIn(List<String> values) {
            addCriterion("parse_file_name not in", values, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameBetween(String value1, String value2) {
            addCriterion("parse_file_name between", value1, value2, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andParseFileNameNotBetween(String value1, String value2) {
            addCriterion("parse_file_name not between", value1, value2, "parseFileName");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andParseStatusIsNull() {
            addCriterion("parse_status is null");
            return (Criteria) this;
        }

        public Criteria andParseStatusIsNotNull() {
            addCriterion("parse_status is not null");
            return (Criteria) this;
        }

        public Criteria andParseStatusEqualTo(String value) {
            addCriterion("parse_status =", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusNotEqualTo(String value) {
            addCriterion("parse_status <>", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusGreaterThan(String value) {
            addCriterion("parse_status >", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusGreaterThanOrEqualTo(String value) {
            addCriterion("parse_status >=", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusLessThan(String value) {
            addCriterion("parse_status <", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusLessThanOrEqualTo(String value) {
            addCriterion("parse_status <=", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusLike(String value) {
            addCriterion("parse_status like", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusNotLike(String value) {
            addCriterion("parse_status not like", value, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusIn(List<String> values) {
            addCriterion("parse_status in", values, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusNotIn(List<String> values) {
            addCriterion("parse_status not in", values, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusBetween(String value1, String value2) {
            addCriterion("parse_status between", value1, value2, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseStatusNotBetween(String value1, String value2) {
            addCriterion("parse_status not between", value1, value2, "parseStatus");
            return (Criteria) this;
        }

        public Criteria andParseTimeIsNull() {
            addCriterion("parse_time is null");
            return (Criteria) this;
        }

        public Criteria andParseTimeIsNotNull() {
            addCriterion("parse_time is not null");
            return (Criteria) this;
        }

        public Criteria andParseTimeEqualTo(Date value) {
            addCriterion("parse_time =", value, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeNotEqualTo(Date value) {
            addCriterion("parse_time <>", value, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeGreaterThan(Date value) {
            addCriterion("parse_time >", value, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("parse_time >=", value, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeLessThan(Date value) {
            addCriterion("parse_time <", value, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeLessThanOrEqualTo(Date value) {
            addCriterion("parse_time <=", value, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeIn(List<Date> values) {
            addCriterion("parse_time in", values, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeNotIn(List<Date> values) {
            addCriterion("parse_time not in", values, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeBetween(Date value1, Date value2) {
            addCriterion("parse_time between", value1, value2, "parseTime");
            return (Criteria) this;
        }

        public Criteria andParseTimeNotBetween(Date value1, Date value2) {
            addCriterion("parse_time not between", value1, value2, "parseTime");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("updated_at =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNull() {
            addCriterion("updated_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNotNull() {
            addCriterion("updated_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByEqualTo(String value) {
            addCriterion("updated_by =", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotEqualTo(String value) {
            addCriterion("updated_by <>", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThan(String value) {
            addCriterion("updated_by >", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThanOrEqualTo(String value) {
            addCriterion("updated_by >=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThan(String value) {
            addCriterion("updated_by <", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThanOrEqualTo(String value) {
            addCriterion("updated_by <=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLike(String value) {
            addCriterion("updated_by like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotLike(String value) {
            addCriterion("updated_by not like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIn(List<String> values) {
            addCriterion("updated_by in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotIn(List<String> values) {
            addCriterion("updated_by not in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByBetween(String value1, String value2) {
            addCriterion("updated_by between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotBetween(String value1, String value2) {
            addCriterion("updated_by not between", value1, value2, "updatedBy");
            return (Criteria) this;
        }
    }

    /**
     */
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