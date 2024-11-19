package com.example.report.controller;
import com.example.policy.repository.PolizaRepository;
import com.example.report.service.ReportGeneratorService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("v1/reports")
public class ReportController {

    @Autowired
    private ReportGeneratorService employeeReportGenerator;

    @Autowired
    private PolizaRepository polizaRepository;

    @GetMapping("polizas")
    public ResponseEntity<byte[]> exportPolizasToPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("policesReport", "policesReport.pdf");
        return ResponseEntity.ok().headers(headers).body(employeeReportGenerator.exportPolizasToPdf(polizaRepository.findAll()));
    }
}
