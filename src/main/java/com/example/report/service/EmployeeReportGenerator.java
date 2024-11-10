package com.example.report.service;

import com.example.employee.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeReportGenerator {

    public byte[] exportToPdf(List<Employee> employees) throws JRException , FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(employees));
    }

    private JasperPrint getReport(List<Employee> employees) throws JRException, FileNotFoundException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("employeData", new JRBeanCollectionDataSource(employees));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager
                                                            .compileReport(ResourceUtils.getFile("classpath:reports/employeeReport.jrxml")
                                                                    .getAbsolutePath()), parameters, new JREmptyDataSource());
        return report;
    }
}
