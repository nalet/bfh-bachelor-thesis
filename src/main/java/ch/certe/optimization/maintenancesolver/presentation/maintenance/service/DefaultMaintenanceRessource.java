/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.service;

import ch.certe.optimization.maintenancesolver.presentation.maintenance.cdi.MaintenanceSolverManager;
import ch.certe.optimization.maintenancesolver.presentation.maintenance.domain.JsonMaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.presentation.maintenance.domain.JsonMessage;
import ch.certe.optimization.maintenancesolver.presentation.maintenance.domain.JsonStatus;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 *
 * @author nalet
 */
public class DefaultMaintenanceRessource implements MaintenanceRessource {

    @Inject
    private MaintenanceSolverManager solverManager;

    @Context
    private HttpServletRequest request;

    @Override
    public String getSolution() {
        return this.solverManager.getJsonSolution();
    }

    @Override
    public JsonStatus getStatus() {
        return new JsonStatus(this.solverManager.isSolvingState(), this.solverManager.getStartup(), this.solverManager.getTimeSpend(), this.solverManager.getScore(), this.solverManager.getSolveStartTime());
    }

    @Override
    public JsonMessage solve() {
        boolean success = solverManager.solve();
        return new JsonMessage(success ? "Solving started." : "Solver was already running.");
    }

    @Override
    public JsonMessage terminateEarly() {
        boolean success = this.solverManager.terminateEarly();
        return new JsonMessage(success ? "Solver terminating early." : "Solver was already terminated.");
    }

    @Override
    public JsonMaintenanceSchedule getLastSolution() {
        MaintenanceSchedule maintenanceSchedule = this.solverManager.getLastSolution();
        if (maintenanceSchedule != null) {
            return new JsonMaintenanceSchedule(maintenanceSchedule);
        }
        return new JsonMaintenanceSchedule();
    }

    @Override
    public String getSapSolution() {
        return this.solverManager.getSapSolution();
    }

    @Override
    public JsonMessage reset() {
        boolean success = this.solverManager.terminateEarly();
        this.solverManager.reset();
        return new JsonMessage(success ? "Reset success" : "Error happend");
    }

}
