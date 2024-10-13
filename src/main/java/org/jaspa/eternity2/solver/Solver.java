package org.jaspa.eternity2.solver;

import org.jaspa.eternity2.dao.EternityDAO;

public interface Solver {

	/**
	 * Perform the solve operation for this solver
	 */
	public void solve();

	/**
	 * Get the EternityDAO used for the new generation
	 */
	public EternityDAO getNewGenDAO();

	/**
	 * Get the EternityDAO used for the old generation
	 */
	public EternityDAO getOldGenDAO();

}