package com.swp.project.service;

import com.swp.project.dto.response.StandardIndexResponse;

import java.util.List;

public interface IStandardIndexService {
    List<StandardIndexResponse> getALLStandardIndex();
    List<StandardIndexResponse> getStandardIndexStoredProcedure();
}
