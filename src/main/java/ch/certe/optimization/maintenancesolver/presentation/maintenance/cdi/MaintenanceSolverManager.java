/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.cdi;

import ch.certe.optimization.maintenancesolver.product.app.ProductConsole;
import ch.certe.optimization.maintenancesolver.product.domain.Interval;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObject;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import ch.certe.optimization.maintenancesolver.product.presistence.ProductGenerator;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.core.impl.score.director.ScoreDirector;

/**
 *
 * @author nalet
 */
@ApplicationScoped
public class MaintenanceSolverManager {

    private static final String SOLVER_CONFIG = "ch/certe/optimization/maintenancesolver/product/solver/maintenanceScheduleSolverConfig.xml";

    private SolverFactory<MaintenanceSchedule> solverFactory;

    private ExecutorService executor;

    private MaintenanceSchedule solution;
    private MaintenanceSchedule lastSolution;
    private Solver<MaintenanceSchedule> solver;

    private Date startup;
    private boolean solvingState = false;

    private long solveStartTime = 0;

    private ScoreDirector<MaintenanceSchedule> scoreDirector;

    @PostConstruct
    public synchronized void postConstruct() {
        this.startup = new Date();
        solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        // Always terminate a solver after 2 minutes
        solverFactory.getSolverConfig().setTerminationConfig(new TerminationConfig().withMinutesSpentLimit(2L));
        this.solver = this.solverFactory.buildSolver();
        this.scoreDirector = this.solver.getScoreDirectorFactory().buildScoreDirector();

        executor = Executors.newWorkStealingPool(); // Only 2 because the other examples have their own Executor
    }

    @PreDestroy
    public synchronized void destroy() {
        this.solver.terminateEarly();
        executor.shutdown();
    }

    public synchronized MaintenanceSchedule retrieveOrCreateSolution() {
        if (this.solution == null) {
            this.solution = new ProductGenerator().createProduct(50, 1);
        }
        return this.solution;
    }

    public synchronized boolean solve() {
        if (this.solvingState == false) {
            this.solvingState = true;
        } else {
            return false;
        }

        this.solveStartTime = System.currentTimeMillis();

        executor.submit(() -> {
            this.solution = this.solver.solve(this.retrieveOrCreateSolution());
            this.finishing();
        });
        return true;
    }

    private synchronized void finishing() {
        this.solvingState = false;
        this.solveStartTime = 0;
        this.lastSolution = this.solution;
    }

    public synchronized boolean terminateEarly() {
        if (solver != null) {
            this.solver.terminateEarly();
            return true;
        } else {
            return false;
        }
    }

    public synchronized Date getStartup() {
        return startup;
    }

    public synchronized boolean isSolvingState() {
        return solvingState;
    }

    public synchronized long getTimeSpend() {
        try {
            if (this.solver != null) {
                return this.solver.getTimeMillisSpent();
            }
        } catch (NullPointerException e) {

        }
        return 0;
    }

    public synchronized long getScore() {
        if (this.solvingState) {
            this.solver.getBestScore();
        }
        return 0;
    }

    public synchronized long getSolveStartTime() {
        if (this.solveStartTime == 0) {
            return 0;
        }
        return System.currentTimeMillis() - this.solveStartTime;
    }

    public synchronized MaintenanceSchedule getLastSolution() {
        return lastSolution;
    }

    public String getJsonSolution() {
        if (this.solvingState) {
            return MaintenanceSolverManager.toJson(this.solver.getBestSolution());
        }
        return MaintenanceSolverManager.toJson(this.solution);
    }

    public String getSapSolution() {
        if (this.solvingState) {
            return ProductConsole.toDisplayString(this.solver.getBestSolution());
        }
        return ProductConsole.toDisplayString(this.solution);
    }

    public static synchronized String toJson(MaintenanceSchedule solution) {
        if (solution == null) {
            return "{}";
        }
        StringBuilder displayString = new StringBuilder();
        displayString.append("{\"maintenanceObjects\":[");
        for (MaintenanceObject maintenanceObject : solution.getMaintenanceObjects()) {
            displayString.append("{\"name\":\"" + maintenanceObject.getName() + "\",");
            displayString.append("\"intervals\":[");
            for (Interval interval : maintenanceObject.getIntervals()) {
                displayString.append("{\"period\":{\"years\":" + interval.getPeriod().getYears() + ",\"months\":" + interval.getPeriod().getMonths() + ",\"days\":" + interval.getPeriod().getDays() + "},");
                displayString.append("\"tasks\":[");
                for (Task tempTask : interval.getTasks()) {
                    Task task = null;
                    for (Task t : solution.getTasks()) {
                        if (t.getId() == tempTask.getId()) {
                            task = t;
                        }
                    }
                    displayString.append("{\"taskType\":\"" + task.getTaskType().getType() + "\",");
                    displayString.append(task.getPlanDate().toString() + "},");
                }
                displayString.deleteCharAt(displayString.length() - 1);
                displayString.append("]},");
            }
            displayString.deleteCharAt(displayString.length() - 1);
            displayString.append("]},");
        }
        displayString.deleteCharAt(displayString.length() - 1);
        displayString.append("]}");
        return displayString.toString();
    }

    public synchronized boolean reset() {
        try {
            this.solution = null;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
