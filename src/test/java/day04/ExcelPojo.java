package day04;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExcelPojo {
    @Excel(name = "序号")
    private int caseid;
    @Excel(name = "用例标题")
    private String title;
    @Excel(name = "请求头")
    private String headers;
    @Excel(name = "请求方式")
    private String method;
    @Excel(name = "接口地址")
    private String url;
    @Excel(name = "参数输入")
    private String params;

    public ExcelPojo() {
    }

    public ExcelPojo(int caseid, String title, String headers, String method, String url, String params) {
        this.caseid = caseid;
        this.title = title;
        this.headers = headers;
        this.method = method;
        this.url = url;
        this.params = params;
    }

    public int getCaseid() {
        return caseid;
    }

    public void setCaseid(int caseid) {
        this.caseid = caseid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ExcelPojo{" +
                "caseid=" + caseid +
                ", title='" + title + '\'' +
                ", headers='" + headers + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
