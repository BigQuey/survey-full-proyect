package com.reposervice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reposervice.dto.DashboardStatsDTO;
import com.reposervice.feign.ResponseClient;
import com.reposervice.feign.SurveyClient;
import com.reposervice.feign.UserClient;

@Service
public class StatisticsService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private SurveyClient surveyClient;

    @Autowired
    private ResponseClient responseClient;

    public DashboardStatsDTO getDashboardStatistics() {
        // Llama a cada microservicio en paralelo para mayor eficiencia
        long surveyCount = surveyClient.countSurveys();
        long userCount = userClient.countUsers();
        long responseCount = responseClient.countResponses();

        // Ensambla el DTO con los resultados
        return new DashboardStatsDTO(surveyCount, userCount, responseCount);
    }
}