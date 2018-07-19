package com.siat.service;

import com.siat.util.SparqlUtil;
import org.apache.jena.riot.RDFFormat;
import org.springframework.stereotype.Service;

@Service
public class OntologiesService {

    /**
     * 查询URI信息
     * @param URI URI
     * @param accept header的Accept字段
     * @return 对应格式字符串
     */
    public String queryUriFormat(String URI, String accept) {
        accept = accept.toLowerCase();
        if (accept.contains("rdf+xml"))                                   // application/rdf+xml
            return SparqlUtil.queryUriFormat(URI, RDFFormat.RDFXML);
        if (accept.contains("text/turtle"))                               // text/turtle
            return SparqlUtil.queryUriFormat(URI, RDFFormat.TURTLE);
        if (accept.contains("ld+json"))                                   // application/ld+json
            return SparqlUtil.queryUriFormat(URI, RDFFormat.JSONLD);

        return "Error: No RDF format specified. ";
    }

}
