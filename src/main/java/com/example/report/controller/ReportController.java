package com.example.report.controller;
import com.example.employee.EmployeRepository;
import com.example.report.service.EmployeeReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;


@RestController
@RequestMapping("v1/api/reports")
public class ReportController {

    @Autowired
    private EmployeeReportGenerator employeeReportGenerator;

    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping
    public ResponseEntity<?> exportToPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("employeeReport", "employeeReport.pdf");
        return ResponseEntity.ok().headers(headers).body(employeeReportGenerator.exportToPdf(employeRepository.findAll()));
    }
}
