package com.reposervice.dto;

public record DashboardStatsDTO(
	    long totalSurveys,
	    long totalUsers,
	    long totalResponses
	) {}