package com.fittrack.fittrack.controller;

import com.fittrack.fittrack.dto.DashboardResponse;
import com.fittrack.fittrack.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> dashboard(@PathVariable String id) {
        return dashboardService.build(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}