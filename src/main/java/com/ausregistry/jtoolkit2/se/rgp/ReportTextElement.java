package com.ausregistry.jtoolkit2.se.rgp;

/**
 * Used to store an element for a domain restore report that has a text element
 * and a language.
 *
 */
public class ReportTextElement {

    private String reportElement;
    private String language;

    public ReportTextElement(String reportElement, String language) {
        this.reportElement = reportElement;
        this.language = language;
    }

    public String getReportElement() {
        return reportElement;
    }

    public void setReportElement(String reportElement) {
        this.reportElement = reportElement;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
