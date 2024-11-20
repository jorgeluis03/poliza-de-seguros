package com.example.report.service;
import com.example.policy.model.Poliza;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGeneratorService {

    public byte[] exportPolizasToPdf(List<Poliza> polizas) throws JRException , FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportPolizas(polizas));
    }

    public byte[] exportPolizasToXls(List<Poliza> polizas) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReportPolizas(polizas)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        return byteArray.toByteArray();
    }

    private JasperPrint getReportPolizas(List<Poliza> polizas) throws JRException, FileNotFoundException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("polizaData", new JRBeanCollectionDataSource(polizas));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager
                .compileReport(ResourceUtils.getFile("classpath:reports/policesReport.jrxml")
                        .getAbsolutePath()), parameters, new JREmptyDataSource());
        return report;
    }
}
