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

    private Integer limit;

    private Integer offset;

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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
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

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNull() {
            addCriterion("update_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNotNull() {
            addCriterion("update_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtEqualTo(Date value) {
            addCriterion("update_at =", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotEqualTo(Date value) {
            addCriterion("update_at <>", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThan(Date value) {
            addCriterion("update_at >", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("update_at >=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThan(Date value) {
            addCriterion("update_at <", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThanOrEqualTo(Date value) {
            addCriterion("update_at <=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIn(List<Date> values) {
            addCriterion("update_at in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotIn(List<Date> values) {
            addCriterion("update_at not in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtBetween(Date value1, Date value2) {
            addCriterion("update_at between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotBetween(Date value1, Date value2) {
            addCriterion("update_at not between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
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