package com.encore.playground.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResultDto {
    private String url;
    private ArrayList<String> job_list;
    private String title;
    private String company;
    private ArrayList<String> location;
}
