package com.example.report.service;
import com.example.policy.model.Poliza;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGeneratorService {

    public byte[] exportPolizasToPdf(List<Poliza> polizas) throws JRException , FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportPolizas(polizas));
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
