package com.siat.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {
 *     "head": {
 *         "vars": []                  // 查询结果标题，如?s ?p ?o 对应["s", "p", "o"]
 *     }
 *     results: {
 *         "bindings": []              // 查询结果内容，如?s ?p ?o 对应
 *                                     // （注意每项可能为节点基类类型，也可能为null）
 *                                     // [
 *                                     //     {"s": {}, "p": {}, "o": {}},
 *                                     //     {"s": {}, "p": {}, "o": {}},
 *                                     //     ...
 *                                     // ]
 *     }
 * }
 */
public class SparqlResultBean {

    private HeadBean head = new HeadBean();
    private ResultsBean results = new ResultsBean();

    public HeadBean getHead() {
        return head;
    }

    public void setHead(HeadBean head) {
        this.head = head;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class HeadBean {
        private List<String> vars = new ArrayList<>();

        public List<String> getVars() {
            return vars;
        }

        public void setVars(List<String> vars) {
            this.vars = vars;
        }

        public void addVars(String var) {
            this.vars.add(var);
        }

    }

    public static class ResultsBean {

        private List<Map<String, BaseValueBean>> bindings = new ArrayList<>();

        public List<Map<String, BaseValueBean>> getBindings() {
            return bindings;
        }

        public void setBindings(List<Map<String, BaseValueBean>> bindings) {
            this.bindings = bindings;
        }

        public void addBindings(Map<String, BaseValueBean> binding) {
            this.bindings.add(binding);
        }

        /**
         * 节点基类
         * {
         *     "type": "",             // 节点类型
         *     "value": ""             // 节点值
         * }
         */
        public abstract static class BaseValueBean extends HashMap<String, String> {

            public BaseValueBean() {
                super();
                setType(null);
                setValue(null);
            }

            public String getType() {
                return this.get("type");
            }

            void setType(String type) {
                this.put("type", type);
            }

            public String getValue() {
                return this.get("value");
            }

            public void setValue(String value) {
                this.put("value", value);
            }
        }

        /**
         * 空节点
         * {
         *     "type": "blank",
         *     "value": ""             // 节点值为空节点ID
         * }
         */
        public static class BlankValueBean extends BaseValueBean {
            public BlankValueBean() {
                super();
                setType("blank");
            }
        }

        // uri数据类型

        /**
         * URI类型节点
         * {
         *     "type": "uri",
         *     "value": "'             // URI值
         * }
         */
        public static class UriValueBean extends BaseValueBean {

            public UriValueBean() {
                super();
                setType("uri");
            }
        }

        /**
         * 指明类型的Literal节点
         * {
         *     "type": "typed-literal",
         *     "value": "",            // literal字面量值
         *     "datatype": ""          // 数据类型URI
         * }
         */
        public static class TypedLiteralValueBean extends BaseValueBean {

            public TypedLiteralValueBean() {
                super();
                setType("typed-literal");
                setDatatype(null);
            }

            public String getDatatype() {
                return this.get("datatype");
            }

            public void setDatatype(String datatype) {
                this.put("datatype", datatype);
            }
        }

        /**
         * 未指明类型的Literal节点
         * {
         *     "type": "literal",
         *     "value": "",            // literal字面量值
         *     "lang": ""              // 语言类型，如"en"、"zh"
         * }
         */
        public static class LiteralValueBean extends BaseValueBean {

            public LiteralValueBean() {
                super();
                setType("literal");
                setLang(null);
            }

            public String getLang() {
                return this.get("lang");
            }

            public void setLang(String lang) {
                this.put("lang", lang);
            }
        }

    }
}
