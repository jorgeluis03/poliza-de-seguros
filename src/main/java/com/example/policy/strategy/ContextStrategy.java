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


    public PolicyStrategy getPolicyStrategy(String tipoPoliza) {
        switch (tipoPoliza) {
            case "Auto":
                return autoPolicyStrategy;
            case "Inmueble":
                return inmueblePolicyStrategy;
            case "Celular":
                return celularPolicyStrategy;
            default:
                return null;
        }
    }
}
