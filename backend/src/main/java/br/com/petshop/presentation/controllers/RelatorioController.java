package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.RelatorioDTO;
import br.com.petshop.application.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller para operações relacionadas a Relatórios
 * 
 * @author Yan Werlley
 */
@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    
    @Autowired
    private RelatorioService relatorioService;
    
    /**
     * Gera um relatório baseado no tipo e período
     */
    @GetMapping("/{tipoRelatorio}")
    public ResponseEntity<RelatorioDTO> gerarRelatorio(
            @PathVariable RelatorioDTO.TipoRelatorio tipoRelatorio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String filtroAdicional) {
        
        RelatorioDTO relatorio = relatorioService.gerarRelatorio(tipoRelatorio, dataInicio, dataFim, filtroAdicional);
        return ResponseEntity.ok(relatorio);
    }
    
    /**
     * Exporta um relatório para um formato específico
     */
    @GetMapping("/{tipoRelatorio}/exportar")
    public ResponseEntity<byte[]> exportarRelatorio(
            @PathVariable RelatorioDTO.TipoRelatorio tipoRelatorio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String filtroAdicional,
            @RequestParam(defaultValue = "PDF") String formato) {
        
        RelatorioDTO relatorio = relatorioService.gerarRelatorio(tipoRelatorio, dataInicio, dataFim, filtroAdicional);
        byte[] conteudoArquivo = relatorioService.exportarRelatorio(relatorio, formato);
        
        // Define o tipo de conteúdo e nome do arquivo baseado no formato
        String contentType;
        String filename;
        
        switch (formato.toUpperCase()) {
            case "PDF":
                contentType = MediaType.APPLICATION_PDF_VALUE;
                filename = "relatorio_" + tipoRelatorio.name().toLowerCase() + ".pdf";
                break;
            case "EXCEL":
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                filename = "relatorio_" + tipoRelatorio.name().toLowerCase() + ".xlsx";
                break;
            case "CSV":
                contentType = "text/csv";
                filename = "relatorio_" + tipoRelatorio.name().toLowerCase() + ".csv";
                break;
            default:
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                filename = "relatorio_" + tipoRelatorio.name().toLowerCase() + ".bin";
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", filename);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(conteudoArquivo);
    }
}
