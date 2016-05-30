package org.moomoocow.trading.tws;

public class HistoricalData {
	private int reqId;
	private String date;
	private double open;
	private double high;
	private double low;
	private double close;
	private int volume;
	private int count;
	private double WAP;
	private boolean hasGaps;

	public HistoricalData() {
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HistoricalData [\nreqId=" + reqId + ", \ndate=" + date + ", \nopen=" + open + ", \nhigh=" + high
				+ ", \nlow=" + low + ", \nclose=" + close + ", \nvolume=" + volume + ", \ncount=" + count + ", \nWAP="
				+ WAP + ", \nhasGaps=" + hasGaps + "\n]";
	}



	/**
	 * @return the reqId
	 */
	public int getReqId() {
		return reqId;
	}

	/**
	 * @param reqId
	 *            the reqId to set
	 */
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the open
	 */
	public double getOpen() {
		return open;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(double open) {
		this.open = open;
	}

	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}

	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}

	/**
	 * @return the close
	 */
	public double getClose() {
		return close;
	}

	/**
	 * @param close
	 *            the close to set
	 */
	public void setClose(double close) {
		this.close = close;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the wAP
	 */
	public double getWAP() {
		return WAP;
	}

	/**
	 * @param wAP
	 *            the wAP to set
	 */
	public void setWAP(double wAP) {
		WAP = wAP;
	}

	/**
	 * @return the hasGaps
	 */
	public boolean isHasGaps() {
		return hasGaps;
	}

	/**
	 * @param hasGaps
	 *            the hasGaps to set
	 */
	public void setHasGaps(boolean hasGaps) {
		this.hasGaps = hasGaps;
	}

	public static class Builder {
		private int reqId;
		private String date;
		private double open;
		private double high;
		private double low;
		private double close;
		private int volume;
		private int count;
		private double WAP;
		private boolean hasGaps;

		public Builder reqId(int reqId) {
			this.reqId = reqId;
			return this;
		}

		public Builder date(String date) {
			this.date = date;
			return this;
		}

		public Builder open(double open) {
			this.open = open;
			return this;
		}

		public Builder high(double high) {
			this.high = high;
			return this;
		}

		public Builder low(double low) {
			this.low = low;
			return this;
		}

		public Builder close(double close) {
			this.close = close;
			return this;
		}

		public Builder volume(int volume) {
			this.volume = volume;
			return this;
		}

		public Builder count(int count) {
			this.count = count;
			return this;
		}

		public Builder WAP(double WAP) {
			this.WAP = WAP;
			return this;
		}

		public Builder hasGaps(boolean hasGaps) {
			this.hasGaps = hasGaps;
			return this;
		}

		public HistoricalData build() {
			return new HistoricalData(this);
		}
	}

	private HistoricalData(Builder builder) {
		this.reqId = builder.reqId;
		this.date = builder.date;
		this.open = builder.open;
		this.high = builder.high;
		this.low = builder.low;
		this.close = builder.close;
		this.volume = builder.volume;
		this.count = builder.count;
		this.WAP = builder.WAP;
		this.hasGaps = builder.hasGaps;
	}
}
