package com.example.policy.strategy;

import com.example.policy.dto.PolizaDTO;
import com.example.policy.model.Poliza;

public interface PolicyStrategy {
    void savePolicy(PolizaDTO polizaDTO, Poliza poliza);
}
