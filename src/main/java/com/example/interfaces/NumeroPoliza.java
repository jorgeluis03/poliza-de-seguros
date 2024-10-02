package com.example.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.example.enume.TipoPoliza;

public interface NumeroPoliza {
	default String generatePolicyNumber(TipoPoliza tipoPoliza) {
	    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
	    String uniqueId = UUID.randomUUID().toString().substring(0, 8);
	    return tipoPoliza + date + uniqueId;
	}
}
