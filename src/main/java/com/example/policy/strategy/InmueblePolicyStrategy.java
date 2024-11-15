package com.example.policy.strategy;

import com.example.policy.dto.PolizaDTO;
import com.example.policy.model.Poliza;
import com.example.policy.model.PolizaInmueble;
import com.example.policy.repository.PolizaInmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("2")
public class InmueblePolicyStrategy implements PolicyStrategy {

    @Autowired
    PolizaInmuebleRepository inmuebleRepository;

    @Override
    public void savePolicy(PolizaDTO polizaDTO, Poliza poliza) {
        PolizaInmueble polizaInmueble = inmuebleRepository.findByPolizaId(poliza.getIdPoliza())
                        .orElse(new PolizaInmueble());

        polizaInmueble.setPoliza(poliza);
        polizaInmueble.setDireccion(polizaDTO.getDireccionInmueble());
        polizaInmueble.setTipoInmueble(polizaDTO.getTipoInmueble());
        inmuebleRepository.save(polizaInmueble);

    }
}
