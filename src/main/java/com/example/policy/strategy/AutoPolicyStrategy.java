package com.example.policy.strategy;

import com.example.policy.dto.PolizaDTO;
import com.example.policy.exception.PolizaNoEncontradaException;
import com.example.policy.model.Poliza;
import com.example.policy.model.PolizaAuto;
import com.example.policy.repository.PolizaAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoPolicyStrategy  implements PolicyStrategy {

    @Autowired
    PolizaAutoRepository autoRepository;

    @Override
    public void savePolicy(PolizaDTO polizaDTO, Poliza poliza) {

        PolizaAuto polizaAuto = autoRepository.findByPolizaId(poliza.getIdPoliza())
                .orElse(new PolizaAuto());

        polizaAuto.setPoliza(poliza);
        polizaAuto.setMarca(polizaDTO.getMarcaAuto());
        polizaAuto.setModelo(polizaDTO.getModeloAuto());
        polizaAuto.setNumeroPlaca(polizaDTO.getNumeroPlaca());
        polizaAuto.setAnio(polizaDTO.getAnioAuto());

        autoRepository.save(polizaAuto);
    }
}
