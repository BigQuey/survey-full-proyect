package com.reposervice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reposervice.dto.DashboardStatsDTO;
import com.reposervice.services.StatisticsService;
import com.reposervice.utils.ApiResponse;

@RestController
@RequestMapping("/api/statistics") 
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStats() {
        DashboardStatsDTO stats = statisticsService.getDashboardStatistics();
        ApiResponse<DashboardStatsDTO> response = new ApiResponse<>(true, "Estadísticas del dashboard obtenidas con éxito", stats);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}