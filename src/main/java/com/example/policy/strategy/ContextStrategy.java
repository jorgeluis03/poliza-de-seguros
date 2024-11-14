package com.example.policy.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContextStrategy {

    @Autowired
    AutoPolicyStrategy autoPolicyStrategy;
    @Autowired
    InmueblePolicyStrategy inmueblePolicyStrategy;
    @Autowired
    CelularPolicyStrategy celularPolicyStrategy;


    public PolicyStrategy getPolicyStrategy(Integer tipoPoliza) {
        switch (tipoPoliza) {
            case 1:
                return autoPolicyStrategy;
            case 2:
                return inmueblePolicyStrategy;
            case 3:
                return celularPolicyStrategy;
            default:
                return null;
        }
    }
}
