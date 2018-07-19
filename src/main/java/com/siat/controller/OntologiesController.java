package com.siat.controller;

import com.siat.service.OntologiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping(path="ontologies")
public class OntologiesController extends BaseController {

    // 若请求头Accept字段包含以下字符串，则返回对应格式，否则默认返回html
    private static final String[] RDF_FORMATS = {
            "rdf+xml",            // application/rdf+xml
            "turtle",             // text/turtle
            "ld+json"             // application/ld+json
    };

    private final OntologiesService ontologiesService;

    @Autowired
    public OntologiesController(OntologiesService ontologiesService) {
        this.ontologiesService = ontologiesService;
    }

    // 根据Header的Accept字段返回不同结果
    // 匹配localName不包含“.”字符
    @RequestMapping(value = "/{localName:[^.]+}", method = RequestMethod.GET)
    public String showUriInfo (@PathVariable(value = "localName") String localName) {
        String accept = request.getHeader("Accept");

        if (null == accept)
            return "url_info";

        accept = accept.toLowerCase();
        for (String format: RDF_FORMATS) {
            if (accept.contains(format)) {
                try {
                    PrintWriter writer = response.getWriter();
                    String URI = request.getRequestURL().toString();
                    writer.write(ontologiesService.queryUriFormat(URI, accept));
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        return "uri_info";
    }

}
