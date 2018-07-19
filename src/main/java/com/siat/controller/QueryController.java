package com.siat.controller;

import com.siat.entity.SparqlResultBean;
import com.siat.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(
        tags = "/api/query",
        description = "SPARQL查询相关接口"
)
@Controller
@RequestMapping(path="api/query")
public class QueryController extends BaseController {

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @ApiOperation(
            value = "执行SPARQL",
            notes = "执行SPARQL语句，返回查询结果",
            httpMethod = "GET"
    )
    @RequestMapping(path = "query_sparql", method = RequestMethod.GET)
    public ResponseEntity<SparqlResultBean> querySparql(@RequestParam(value = "sparql") String SPARQL) {
        SparqlResultBean r = queryService.querySparql(SPARQL);
        return ResponseEntity.ok(r);
    }

    @ApiOperation(
            value = "查询URI标签",
            notes = "查询URI标签，返回查询结果",
            httpMethod = "GET"
    )
    @RequestMapping(path = "query_uri_label", method = RequestMethod.GET)
    public ResponseEntity<SparqlResultBean> queryUriLabel(
            @RequestParam(value = "uri") String URI
    ) {
        String language = request.getHeader("Accept-Language");
        SparqlResultBean r = queryService.queryUriLabel(URI, language);
        return ResponseEntity.ok(r);
    }

    @ApiOperation(
            value = "查询URI信息",
            notes = "查询URI信息，返回查询结果",
            httpMethod = "GET"
    )
    @RequestMapping(path = "query_uri", method = RequestMethod.GET)
    public ResponseEntity<SparqlResultBean> queryUri(
            @RequestParam(value = "uri") String URI
    ) {
        String language = request.getHeader("Accept-Language");
        SparqlResultBean r = queryService.queryUri(URI, language);
        return ResponseEntity.ok(r);
    }

}