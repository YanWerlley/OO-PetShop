package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.DashboardDTO;
import br.com.petshop.application.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Controller para operações relacionadas ao Dashboard
 * 
 * @author Yan Werlley
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    /**
     * Obtém os dados do dashboard
     */
    @GetMapping
    public ResponseEntity<DashboardDTO> obterDadosDashboard() {
        return ResponseEntity.ok(dashboardService.obterDadosDashboard());
    }
    
    /**
     * Obtém os dados do dashboard para um período específico
     */
    @GetMapping("/periodo")
    public ResponseEntity<DashboardDTO> obterDadosDashboardPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(dashboardService.obterDadosDashboardPorPeriodo(dataInicio, dataFim));
    }
}
