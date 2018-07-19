package com.siat.util;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.siat.entity.SparqlResultBean;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SparqlUtil {

    /**
     * 执行SPARQL语句
     * @param SPARQL SPARQL语句
     * @return 查询结果 SparqlResultBean
     */
    public static SparqlResultBean query(String SPARQL) {

        logSparql(SPARQL);

        VirtGraph virtGraph = new VirtGraph(
                CommonProperties.GRAPH_NAME,
                CommonProperties.URL_HOSTLIST,
                CommonProperties.USER,
                CommonProperties.PASSWORD
        );
        SparqlResultBean resultBean = new SparqlResultBean();

        try (VirtuosoQueryExecution vqeUri = VirtuosoQueryExecutionFactory.create(SPARQL, virtGraph)) {

            ResultSet resultSet = vqeUri.execSelect();
            ResultSetRewindable resultSetRewindable = ResultSetFactory.copyResults(resultSet);

            // 打印查询结果
            ResultSetFormatter.out(System.out, resultSetRewindable);
            resultSetRewindable.reset();

            // 数据格式化
            resultBean.getHead().setVars(resultSetRewindable.getResultVars());
            resultBean.getHead().getVars().remove("graph");

            while (resultSetRewindable.hasNext()) {
                QuerySolution solution = resultSetRewindable.nextSolution();
                Map<String, SparqlResultBean.ResultsBean.BaseValueBean> rowData = new HashMap<>();
                resultBean.getResults().addBindings(rowData);

                for (String var : resultBean.getHead().getVars()) {
                    RDFNode rdfNode = solution.get(var);
                    rowData.put(var, parseNode(rdfNode));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            resultBean = null;
        } finally {
            virtGraph.close();
        }

        return resultBean;
    }

    /**
     * 查询URI信息
     * @param URI URI
     * @param lang 对应语言
     * @return 查询结果（literal返回对应语言项） SparqlResultBean
     */
    public static SparqlResultBean queryUri(String URI, String lang) {
        String SPARQL = "select ?p ?p_label ?o ?o_label\n" +
                "where {\n" +
                "    <" + URI + "> ?p ?o.\n" +
                "    ?p rdfs:label ?p_label.\n" +
                "    FILTER(lang(?p_label)=\"" + lang + "\").\n" +
                "    OPTIONAL {\n" +
                "        ?o rdfs:label ?o_label.\n" +
                "        FILTER(lang(?o_label)=\"" + lang + "\").\n" +
                "    }\n" +
                "}";

        return query(SPARQL);
    }

    /**
     * 查询URI信息
     * @param URI URI
     * @param format 对应格式（"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE", "TURTLE", (and "TTL") and "N3"）
     * @return 对应格式字符串
     */
    public static String queryUriFormat(String URI, RDFFormat format) {
        String SPARQL = "DESCRIBE <" + URI + ">";
        logSparql(SPARQL);

        VirtGraph virtGraph = new VirtGraph(
                CommonProperties.GRAPH_NAME,
                CommonProperties.URL_HOSTLIST,
                CommonProperties.USER,
                CommonProperties.PASSWORD
        );
        String result = null;

        try (VirtuosoQueryExecution vqeUri = VirtuosoQueryExecutionFactory.create(SPARQL, virtGraph)) {

            Model model = vqeUri.execDescribe();

            // 转换成RDF格式输出
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            RDFDataMgr.write(baos, model, format);
//            model.write(baos, format);
            result = baos.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            virtGraph.close();
        }

        return result;
    }

    /**
     * 查询URI对应rdfs:label
     * @param URI URI
     * @param lang 对应语言
     * @return 查询结果 SparqlResultBean
     */
    public static SparqlResultBean queryUriLabel(String URI, String lang) {
        String SPARQL = "select ?o\n" +
                "where {\n" +
                "    <" + URI + "> rdfs:label ?o. \n" +
                "    FILTER(lang(?o) = \"" + lang + "\").\n" +
                "}";

        return query(SPARQL);
    }

    // 打印SPARQL语句
    private static void logSparql(String SPARQL) {
        LogUtil.i(
                "\n===========================================================================================" +
                        "\nEXEC SPARQL:\n" +
                        "--------------\n" +
                        SPARQL +
                        "\n===========================================================================================\n"
        );
    }

    // RDFNode类型
    private enum RDFNodeType {
        BLANK_NODE, URI_NODE, TYPED_LITERAL_NODE, LITERAL_NODE
    }

    // 获取RDFNode类型
    private static RDFNodeType getNodeType(RDFNode rdfNode) {
        if (null == rdfNode)
            return null;

        if (rdfNode.isURIResource())
            return RDFNodeType.URI_NODE;

        if (rdfNode.isResource())
            return RDFNodeType.BLANK_NODE;

        return null==rdfNode.asLiteral().getDatatypeURI() ?
                RDFNodeType.LITERAL_NODE :
                RDFNodeType.TYPED_LITERAL_NODE;
    }

    // 将RDFNode格式化为ValueBean
    private static SparqlResultBean.ResultsBean.BaseValueBean parseNode(RDFNode rdfNode) {
        if (null == rdfNode)
            return null;

        switch (getNodeType(rdfNode)) {
            case BLANK_NODE:
                SparqlResultBean.ResultsBean.BlankValueBean blankValueBean
                        = new SparqlResultBean.ResultsBean.BlankValueBean();
                blankValueBean.setValue(rdfNode.asResource().getId().toString());
                return blankValueBean;
            case URI_NODE:
                SparqlResultBean.ResultsBean.UriValueBean uriValueBean
                        = new SparqlResultBean.ResultsBean.UriValueBean();
                uriValueBean.setValue(rdfNode.asResource().getURI());
                return uriValueBean;
            case TYPED_LITERAL_NODE:
                SparqlResultBean.ResultsBean.TypedLiteralValueBean typedLiteralValueBean
                        = new SparqlResultBean.ResultsBean.TypedLiteralValueBean();
                typedLiteralValueBean.setDatatype(rdfNode.asLiteral().getDatatypeURI());
                typedLiteralValueBean.setValue(rdfNode.asLiteral().getString());
                return typedLiteralValueBean;
            case LITERAL_NODE:
                SparqlResultBean.ResultsBean.LiteralValueBean literalValueBean
                        = new SparqlResultBean.ResultsBean.LiteralValueBean();
                literalValueBean.setLang(rdfNode.asLiteral().getLanguage());
                literalValueBean.setValue(rdfNode.asLiteral().getString());
                return literalValueBean;
        }

        return null;
    }

}
