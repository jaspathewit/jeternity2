/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2;

import org.jaspa.eternity2.dao.EternityDAO;
import org.jaspa.eternity2.solver.Solver;
import org.jaspa.eternity2.solver.SolverImpl;

/**
 * Main class of the Eternity2 puzzle Solver
 */
public class Jeternity2 {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {

        Solver solver = new SolverImpl();

        try {
            solver.solve();
        } finally {
            EternityDAO currentDAO = solver.getNewGenDAO();
            if (currentDAO != null) {
                // EternityDAO.getInstance().commit();
                while (!currentDAO.close(false))
                    ;
            }
        }

    }

}
