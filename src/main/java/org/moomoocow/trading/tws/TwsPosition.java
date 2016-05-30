package org.moomoocow.trading.tws;

import com.ib.client.Contract;

public class TwsPosition {
	private final Contract contract;
	private final int pos; 
	private final double avgCost;
	public TwsPosition(Contract contract, int pos, double avgCost) {
		super();
		this.contract = contract;
		this.pos = pos;
		this.avgCost = avgCost;
	}
	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}
	/**
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}
	/**
	 * @return the avgCost
	 */
	public double getAvgCost() {
		return avgCost;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TwsPosition [\ncontract=" + contract + ", \npos=" + pos + ", \navgCost=" + avgCost + "\n]";
	}
	
	
	
	
}
