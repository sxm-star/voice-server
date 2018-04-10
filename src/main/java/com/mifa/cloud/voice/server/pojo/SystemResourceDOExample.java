package com.mifa.cloud.voice.server.pojo;

import java.util.ArrayList;
import java.util.List;

public class SystemResourceDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public SystemResourceDOExample() {
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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Long value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Long value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Long value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Long value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Long value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Long value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Long> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Long> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Long value1, Long value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Long value1, Long value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNull() {
            addCriterion("resource_name is null");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNotNull() {
            addCriterion("resource_name is not null");
            return (Criteria) this;
        }

        public Criteria andResourceNameEqualTo(String value) {
            addCriterion("resource_name =", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotEqualTo(String value) {
            addCriterion("resource_name <>", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThan(String value) {
            addCriterion("resource_name >", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("resource_name >=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThan(String value) {
            addCriterion("resource_name <", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThanOrEqualTo(String value) {
            addCriterion("resource_name <=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLike(String value) {
            addCriterion("resource_name like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotLike(String value) {
            addCriterion("resource_name not like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameIn(List<String> values) {
            addCriterion("resource_name in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotIn(List<String> values) {
            addCriterion("resource_name not in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameBetween(String value1, String value2) {
            addCriterion("resource_name between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotBetween(String value1, String value2) {
            addCriterion("resource_name not between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIsNull() {
            addCriterion("resource_url is null");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIsNotNull() {
            addCriterion("resource_url is not null");
            return (Criteria) this;
        }

        public Criteria andResourceUrlEqualTo(String value) {
            addCriterion("resource_url =", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotEqualTo(String value) {
            addCriterion("resource_url <>", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlGreaterThan(String value) {
            addCriterion("resource_url >", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("resource_url >=", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLessThan(String value) {
            addCriterion("resource_url <", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLessThanOrEqualTo(String value) {
            addCriterion("resource_url <=", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLike(String value) {
            addCriterion("resource_url like", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotLike(String value) {
            addCriterion("resource_url not like", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIn(List<String> values) {
            addCriterion("resource_url in", values, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotIn(List<String> values) {
            addCriterion("resource_url not in", values, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlBetween(String value1, String value2) {
            addCriterion("resource_url between", value1, value2, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotBetween(String value1, String value2) {
            addCriterion("resource_url not between", value1, value2, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeEqualTo(Integer value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(Integer value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(Integer value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(Integer value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<Integer> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<Integer> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(Integer value1, Integer value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceIconIsNull() {
            addCriterion("resource_icon is null");
            return (Criteria) this;
        }

        public Criteria andResourceIconIsNotNull() {
            addCriterion("resource_icon is not null");
            return (Criteria) this;
        }

        public Criteria andResourceIconEqualTo(String value) {
            addCriterion("resource_icon =", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotEqualTo(String value) {
            addCriterion("resource_icon <>", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconGreaterThan(String value) {
            addCriterion("resource_icon >", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconGreaterThanOrEqualTo(String value) {
            addCriterion("resource_icon >=", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconLessThan(String value) {
            addCriterion("resource_icon <", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconLessThanOrEqualTo(String value) {
            addCriterion("resource_icon <=", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconLike(String value) {
            addCriterion("resource_icon like", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotLike(String value) {
            addCriterion("resource_icon not like", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconIn(List<String> values) {
            addCriterion("resource_icon in", values, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotIn(List<String> values) {
            addCriterion("resource_icon not in", values, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconBetween(String value1, String value2) {
            addCriterion("resource_icon between", value1, value2, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotBetween(String value1, String value2) {
            addCriterion("resource_icon not between", value1, value2, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceOrderIsNull() {
            addCriterion("resource_order is null");
            return (Criteria) this;
        }

        public Criteria andResourceOrderIsNotNull() {
            addCriterion("resource_order is not null");
            return (Criteria) this;
        }

        public Criteria andResourceOrderEqualTo(Integer value) {
            addCriterion("resource_order =", value, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNotEqualTo(Integer value) {
            addCriterion("resource_order <>", value, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderGreaterThan(Integer value) {
            addCriterion("resource_order >", value, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_order >=", value, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderLessThan(Integer value) {
            addCriterion("resource_order <", value, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderLessThanOrEqualTo(Integer value) {
            addCriterion("resource_order <=", value, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderIn(List<Integer> values) {
            addCriterion("resource_order in", values, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNotIn(List<Integer> values) {
            addCriterion("resource_order not in", values, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderBetween(Integer value1, Integer value2) {
            addCriterion("resource_order between", value1, value2, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_order not between", value1, value2, "resourceOrder");
            return (Criteria) this;
        }

        public Criteria andResourceStatusIsNull() {
            addCriterion("resource_status is null");
            return (Criteria) this;
        }

        public Criteria andResourceStatusIsNotNull() {
            addCriterion("resource_status is not null");
            return (Criteria) this;
        }

        public Criteria andResourceStatusEqualTo(Integer value) {
            addCriterion("resource_status =", value, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusNotEqualTo(Integer value) {
            addCriterion("resource_status <>", value, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusGreaterThan(Integer value) {
            addCriterion("resource_status >", value, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_status >=", value, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusLessThan(Integer value) {
            addCriterion("resource_status <", value, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("resource_status <=", value, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusIn(List<Integer> values) {
            addCriterion("resource_status in", values, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusNotIn(List<Integer> values) {
            addCriterion("resource_status not in", values, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusBetween(Integer value1, Integer value2) {
            addCriterion("resource_status between", value1, value2, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_status not between", value1, value2, "resourceStatus");
            return (Criteria) this;
        }

        public Criteria andResourceGroupIsNull() {
            addCriterion("resource_group is null");
            return (Criteria) this;
        }

        public Criteria andResourceGroupIsNotNull() {
            addCriterion("resource_group is not null");
            return (Criteria) this;
        }

        public Criteria andResourceGroupEqualTo(Integer value) {
            addCriterion("resource_group =", value, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupNotEqualTo(Integer value) {
            addCriterion("resource_group <>", value, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupGreaterThan(Integer value) {
            addCriterion("resource_group >", value, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_group >=", value, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupLessThan(Integer value) {
            addCriterion("resource_group <", value, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupLessThanOrEqualTo(Integer value) {
            addCriterion("resource_group <=", value, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupIn(List<Integer> values) {
            addCriterion("resource_group in", values, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupNotIn(List<Integer> values) {
            addCriterion("resource_group not in", values, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupBetween(Integer value1, Integer value2) {
            addCriterion("resource_group between", value1, value2, "resourceGroup");
            return (Criteria) this;
        }

        public Criteria andResourceGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_group not between", value1, value2, "resourceGroup");
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