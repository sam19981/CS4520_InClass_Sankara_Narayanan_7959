package com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels;

import java.util.ArrayList;

public class NewsCollection {
    public NewsCollection() {
    }

    private String status;
    private int totalResults;
    private ArrayList<News> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<News> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<News> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "NewsCollection{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}
