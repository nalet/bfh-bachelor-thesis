/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package ch.certe.optimization.maintenancesolver.example.optional.realtime;

import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import ch.certe.optimization.maintenancesolver.example.domain.CloudBalance;
import ch.certe.optimization.maintenancesolver.example.domain.CloudProcess;

public class AddProcessProblemFactChange implements ProblemFactChange<CloudBalance> {

    private final CloudProcess process;

    public AddProcessProblemFactChange(CloudProcess process) {
        this.process = process;
    }

    @Override
    public void doChange(ScoreDirector<CloudBalance> scoreDirector) {
        CloudBalance cloudBalance = scoreDirector.getWorkingSolution();
        // Set a unique id on the new process
        long nextProcessId = 0L;
        for (CloudProcess otherProcess : cloudBalance.getProcessList()) {
            if (nextProcessId <= otherProcess.getId()) {
                nextProcessId = otherProcess.getId() + 1L;
            }
        }
        process.setId(nextProcessId);
        // A SolutionCloner clones planning entity lists (such as processList), so no need to clone the processList here
        // Add the planning entity itself
        scoreDirector.beforeEntityAdded(process);
        cloudBalance.getProcessList().add(process);
        scoreDirector.afterEntityAdded(process);
        scoreDirector.triggerVariableListeners();
    }

}
