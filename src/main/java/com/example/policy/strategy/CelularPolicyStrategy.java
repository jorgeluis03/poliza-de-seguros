package com.example.policy.strategy;

import com.example.policy.dto.PolizaDTO;
import com.example.policy.model.Poliza;
import com.example.policy.model.PolizaCelular;
import com.example.policy.repository.PolizaCelularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("3")
public class CelularPolicyStrategy implements PolicyStrategy {

    @Autowired
    PolizaCelularRepository celularRepository;

    @Override
    public void savePolicy(PolizaDTO polizaDTO, Poliza poliza) {
        PolizaCelular polizaCelular = new PolizaCelular();
        polizaCelular.setPoliza(poliza);
        polizaCelular.setMarca(polizaDTO.getMarcaCelular());
        polizaCelular.setModelo(polizaDTO.getModeloCelular());
        celularRepository.save(polizaCelular);
    }
}
