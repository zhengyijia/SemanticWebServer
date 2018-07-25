package com.siat.serviceImpl;

import com.siat.entity.QueryEntity.SparqlResultBean;
import com.siat.service.QueryService;
import com.siat.util.SparqlUtil;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImpl implements QueryService {

    /**
     * 执行SPARQL语句，返回查询结果
     * @param SPARQL SPARQL语句
     * @return 查询结果
     */
    public SparqlResultBean querySparql(String SPARQL) {

        return SparqlUtil.query(SPARQL);
    }

    /**
     * 根据uri查询对象标签
     * @param URI URI
     * @return 标签信息 SparqlResultBean
     */
    public SparqlResultBean queryUriLabel(String URI) {

        return SparqlUtil.queryUriLabel(URI);
    }

//    /**
//     * 根据uri查询对象标签
//     * @param URI URI
//     * @param language 语言（默认英文）
//     * @return 标签信息 SparqlResultBean
//     */
//    public SparqlResultBean queryUriLabel(String URI, String language) {
//
//        language = null==language ? "en" : language;  // 默认返回英文信息
//
//        if (language.contains("zh"))
//            language = "zh";
//        else
//            language = "en";
//
//        return SparqlUtil.queryUriLabel(URI, language);
//    }

    /**
     * 根据uri查询对象详细信息 ?p ?p_label ?o ?o_label
     * @param URI URI
     * @return 详细信息 ?p ?p_label ?o ?o_label
     */
    public SparqlResultBean queryUri(String URI) {

        return SparqlUtil.queryUri(URI);
    }

//    /**
//     * 根据uri查询对象详细信息 ?p ?p_label ?o ?o_label
//     * @param URI URI
//     * @param language 语言（默认英文）
//     * @return 详细信息 ?p ?p_label ?o ?o_label
//     */
//    public SparqlResultBean queryUri(String URI, String language) {
//
//        language = null==language ? "en" : language;  // 默认返回英文信息
//
//        if (language.contains("zh"))
//            language = "zh";
//        else
//            language = "en";
//
//        return SparqlUtil.queryUri(URI, language);
//    }

}
