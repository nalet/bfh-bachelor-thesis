/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.service;

import ch.certe.optimization.maintenancesolver.presentation.maintenance.domain.JsonMaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.presentation.maintenance.domain.JsonMessage;
import ch.certe.optimization.maintenancesolver.presentation.maintenance.domain.JsonStatus;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author nalet
 */
@Path("maintenance")
public interface MaintenanceRessource {

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    JsonStatus getStatus();

    @GET
    @Path("/solution")
    @Produces(MediaType.APPLICATION_JSON)
    String getSolution();

    @GET
    @Path("/solution/sap")
    @Produces(MediaType.TEXT_PLAIN)
    String getSapSolution();

    @GET
    @Path("/solution/last")
    @Produces(MediaType.APPLICATION_JSON)
    JsonMaintenanceSchedule getLastSolution();

    @GET
    @Path("/solution/solve")
    @Produces(MediaType.APPLICATION_JSON)
    JsonMessage solve();

    @GET
    @Path("/solution/terminateEarly")
    @Produces(MediaType.APPLICATION_JSON)
    JsonMessage terminateEarly();

    @GET
    @Path("/solution/reset")
    @Produces(MediaType.APPLICATION_JSON)
    JsonMessage reset();
}
