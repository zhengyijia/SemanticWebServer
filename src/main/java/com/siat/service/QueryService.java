package com.siat.service;

import com.siat.entity.QueryEntity.SparqlResultBean;

public interface QueryService {

    // 执行SPARQL语句，返回查询结果
    SparqlResultBean querySparql(String SPARQL);
    // 根据uri查询对象标签
    SparqlResultBean queryUriLabel(String URI);
    // 根据uri查询对象详细信息 ?p ?p_label ?o ?o_label
    SparqlResultBean queryUri(String URI);

}
