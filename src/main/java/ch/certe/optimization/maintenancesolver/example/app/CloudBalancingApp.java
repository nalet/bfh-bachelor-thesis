/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.certe.optimization.maintenancesolver.example.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import ch.certe.optimization.maintenancesolver.example.domain.CloudBalance;
import ch.certe.optimization.maintenancesolver.example.persistence.CloudBalancingDao;
import ch.certe.optimization.maintenancesolver.example.swingui.CloudBalancingPanel;
import ch.certe.optimization.maintenancesolver.common.app.CommonApp;
import ch.certe.optimization.maintenancesolver.common.persistence.SolutionDao;

/**
 * For an easy example, look at {@link CloudBalancingHelloWorld} instead.
 */
public class CloudBalancingApp extends CommonApp<CloudBalance> {

    public static final String SOLVER_CONFIG
            = "ch/certe/optimization/maintenancesolver/example/solver/cloudBalancingSolverConfig.xml";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new CloudBalancingApp().init();
    }

    public CloudBalancingApp() {
        super("Cloud balancing",
                "Assign processes to computers.\n\n" +
                "Each computer must have enough hardware to run all of its processes.\n" +
                "Each used computer inflicts a maintenance cost.",
                SOLVER_CONFIG,
                CloudBalancingPanel.LOGO_PATH);
    }

    @Override
    protected Solver<CloudBalance> createSolver() {
        SolverFactory<CloudBalance> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        return solverFactory.buildSolver();
    }

    @Override
    protected CloudBalancingPanel createSolutionPanel() {
        return new CloudBalancingPanel();
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new CloudBalancingDao();
    }

}
