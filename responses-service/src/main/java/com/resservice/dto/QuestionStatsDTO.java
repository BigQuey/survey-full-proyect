package com.resservice.dto;

import java.util.List;
import java.util.Map;

public record QuestionStatsDTO(
	    Long questionId,
	    String questionText,
	    String questionType,
	    Map<String, Long> answerCounts, 
	    List<String> textResponses 
	) {}
