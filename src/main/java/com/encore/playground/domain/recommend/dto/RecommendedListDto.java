package com.encore.playground.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedListDto {
    private String jobName;
    private String jobLocation;
    private HashMap<String, String> jobSkills;
}
