package com.parent.aoptest.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Map;

@Table(name = "tb_info")
public class RequestInfo {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String methodName;       //类

    private String className;   //类名

    private String params;      //参数字符串

    private String results;     //返回结果字符串

    @Transient
    private Map<String, String> paramsMap; //参数以及值

    @Transient
    private Map<String, String> resultsMap;    //返回值信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public Map<String, String> getResultsMap() {
        return resultsMap;
    }

    public void setResultsMap(Map<String, String> resultsMap) {
        this.resultsMap = resultsMap;
    }
}
