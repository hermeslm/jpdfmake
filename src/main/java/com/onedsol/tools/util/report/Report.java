package com.onedsol.tools.util.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onedsol.tools.jpdfmake.enums.PageOrientation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A LogoDTO.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private PageOrientation pageOrientation = PageOrientation.portrait;

    private List<Param> params;

    private Map<String, Param> map = new HashMap<>();

    public Report() {
    }

    public Report(String title, List<Param> params) {
        this.title = title;
        this.params = params;
    }

    public Map<String, Param> getMap() {
        return map;
    }

    public void setMap(Map<String, Param> map) {
        this.map = map;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {

        for (Param param : params) {
            map.put(param.getName(), param);
        }

        this.params = params;
    }

    public Param getParam(String name) throws ExceptionParam {
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            throw new ExceptionParam("Report param not found: " + name);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PageOrientation getPageOrientation() {
        return pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }
}
