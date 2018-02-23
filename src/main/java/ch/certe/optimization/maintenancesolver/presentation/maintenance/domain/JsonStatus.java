/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.domain;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class JsonStatus {

    protected boolean solvingState;
    protected Date startup;
    protected long timeSpend;
    protected long bestScore;
    protected long solveStartTime;

    public JsonStatus(boolean solvingState, Date startup, long timeSpend, long bestScore, long solveStartTime) {
        this.solvingState = solvingState;
        this.startup = startup;
        this.timeSpend = timeSpend;
        this.bestScore = bestScore;
        this.solveStartTime = solveStartTime;
    }

    public boolean isSolvingState() {
        return solvingState;
    }

    public void setSolvingState(boolean solvingState) {
        this.solvingState = solvingState;
    }

    public Date getStartup() {
        return startup;
    }

    public void setStartup(Date startup) {
        this.startup = startup;
    }

    public long getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(long timeSpend) {
        this.timeSpend = timeSpend;
    }

    public long getBestScore() {
        return bestScore;
    }

    public void setBestScore(long bestScore) {
        this.bestScore = bestScore;
    }

    public long getSolveStartTime() {
        return solveStartTime;
    }

    public void setSolveStartTime(long solveStartTime) {
        this.solveStartTime = solveStartTime;
    }
    
    

}
