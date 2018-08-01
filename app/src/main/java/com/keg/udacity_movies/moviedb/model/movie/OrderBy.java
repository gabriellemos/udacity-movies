package com.keg.udacity_movies.moviedb.model.movie;

public enum OrderBy {

    POPULARITY_ASC("popularity.asc"), POPULARITY_DESC("popularity.desc"),
    RELEASE_DATE_ASC("release_date.asc"), RELEASE_DATE_DESC("release_date.desc"),
    REVENUE_ASC("revenue.asc"), REVENUE_DESC("revenue.desc"),
    VOTE_AVERAGE_ASC("vote_average.asc"), VOTE_AVERAGE_DESC("vote_average.desc"),
    VOTE_COUNT_ASC("vote_count.asc"), VOTE_COUNT_DESC("vote_count.desc");

    private String sortParameter;

    OrderBy(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public String getSortParameter() {
        return sortParameter;
    }

}
