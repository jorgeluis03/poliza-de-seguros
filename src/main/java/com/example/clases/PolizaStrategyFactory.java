package com.example.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.interfaces.PolizaStrategy;
@Component
public class PolizaStrategyFactory {
    @Autowired
    private ApplicationContext applicationContext;

    public PolizaStrategy getStrategy(int idTipoPoliza) {
        switch (idTipoPoliza) {
            case 1:
                return applicationContext.getBean(PolizaAutoStrategy.class);
            case 2:
                return applicationContext.getBean(PolizaInmuebleStrategy.class);
            default:
                throw new IllegalArgumentException("Tipo de póliza no soportado: " + idTipoPoliza);
        }
    }
}