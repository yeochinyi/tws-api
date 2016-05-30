package org.moomoocow.trading.tws;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.UnderComp;

public class TwsMain implements EWrapper {

	private final Logger log = Logger.getLogger(TwsMain.class.getName());

	private EClientSocket eclient;

	private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

	public final static void main(String[] args) {
		// new MyMain();
		TwsMain m = new TwsMain();
		// m.getPositions().stream().forEach(
		// p -> {
		// System.out.println(p);
		// m.subscribe(p.getContract());
		// }
		// );

		List<TwsPosition> poss = m.getPositions();

		Contract c = poss.get(0).getContract();
		c.m_exchange = "Smart";
		System.out.println(c);

		m.getHistoricalData(c).stream().forEach((hd) -> {
			System.out.println(hd);
		});

	}

	public List<HistoricalData> getHistoricalData(Contract c) {

		List<HistoricalData> l = new ArrayList<HistoricalData>();

		CountDownLatch latch = new CountDownLatch(1);
		subscribeHistorical(c, (hd) -> {
			// System.out.println(hd);
			l.add(hd);
			if (hd.getClose() == -1) {
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return l;
	}

	private void reset() {
		eclient = new EClientSocket(this);
		eclient.eConnect("127.0.0.1", 7496, 0);
		log.info("connected=" + eclient.isConnected());
	}

	public TwsMain() {
		reset();
	}

	private List<TwsPosition> positions;

	private CyclicBarrier barrier = new CyclicBarrier(2);

	public List<TwsPosition> getPositions() {

		log.info("getPositions");

		this.positions = new ArrayList<>();

		eclient.reqPositions();
		try {
			this.barrier.await(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			this.barrier.reset();
		}
		return positions;
	}

	private Consumer<HistoricalData> callbackHistorical;

	public void subscribeHistorical(Contract contract, Consumer<HistoricalData> callback) {
		// (int tickerId, Contract contract, String genericTickList, boolean
		// snapshot, List<TagValue> mktDataOptions) {
		// eclient.reqMktData(1, contract, "", true, null);

		// https://www.interactivebrokers.com/en/software/api/apiguide/java/reqhistoricaldata.htm
		// int tickerId, Contract contract,
		// String endDateTime, yyyymmdd hh:mm:ss tmz
		// String durationStr,
		// String barSizeSetting, String whatToShow, int useRTH, int formatDate,
		// List<TagValue> chartOptions
		// eclient.reqHistoricalData(1, contract, endDateTime, durationStr,
		// barSizeSetting, whatToShow, useRTH, formatDate, chartOptions);
		// DateFormat df = new SimpleDateFormat("YYYYMMDD hh:mm:ss");
		// String format = df.format(new Date());

		// TRADES
		// MIDPOINT
		// BID
		// ASK
		// BID_ASK
		// HISTORICAL_VOLATILITY
		// OPTION_IMPLIED_VOLATILITY

		LocalDateTime d = LocalDateTime.now();

		String df = dtFormatter.format(d);
		callbackHistorical = callback;
		eclient.reqHistoricalData(1, contract, df, "1 M", "8 hours", "BID_ASK", 1, 2, null);

	}

	@Override
	public void position(String account, Contract contract, int pos, double avgCost) {
		// log.info("position");
		positions.add(new TwsPosition(contract, pos, avgCost));
	}

	@Override
	public void positionEnd() {
		log.info("positionEnd");
		// this.latch.countDown();
		try {
			this.barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error(Exception e) {
		log.info("error=" + e);
		e.printStackTrace();
		// reset();
	}

	@Override
	public void error(String str) {
		log.info("error=" + str);
		// reset();
	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		log.info("error=" + errorMsg);
		// reset();
	}

	@Override
	public void connectionClosed() {
		log.info("connectionClosed");
		reset();
	}

	@Override
	public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
		log.info("tickPrice");
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		log.info("tickSize");
	}

	@Override
	public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta, double undPrice) {
		log.info("tickOptionComputation");
	}

	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {
		log.info("tickGeneric");

	}

	@Override
	public void tickString(int tickerId, int tickType, String value) {
		log.info("tickString");

	}

	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints,
			double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		log.info("tickEFP");

	}

	@Override
	public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld) {
		log.info("orderStatus");

	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
		log.info("openOrder");

	}

	@Override
	public void openOrderEnd() {
		log.info("openOrderEnd");

	}

	@Override
	public void updateAccountValue(String key, String value, String currency, String accountName) {
		log.info("updateAccountValue");

	}

	@Override
	public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue,
			double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
		log.info("updatePortfolio");

	}

	@Override
	public void updateAccountTime(String timeStamp) {
		log.info("updateAccountTime");

	}

	@Override
	public void accountDownloadEnd(String accountName) {
		log.info("accountDownloadEnd");

	}

	@Override
	public void nextValidId(int orderId) {
		log.info("nextValidId");

	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		log.info("contractDetails");

	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		log.info("bondContractDetails");

	}

	@Override
	public void contractDetailsEnd(int reqId) {
		log.info("contractDetailsEnd");

	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		log.info("execDetails");

	}

	@Override
	public void execDetailsEnd(int reqId) {
		log.info("execDetailsEnd");

	}

	@Override
	public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
		log.info("updateMktDepth");

	}

	@Override
	public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price,
			int size) {
		log.info("updateMktDepthL2");

	}

	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
		log.info("updateNewsBulletin");

	}

	@Override
	public void managedAccounts(String accountsList) {
		log.info("managedAccounts");

	}

	@Override
	public void receiveFA(int faDataType, String xml) {
		log.info("receiveFA");

	}

	@Override
	public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume,
			int count, double WAP, boolean hasGaps) {
		log.info("historicalData");

		HistoricalData h = new HistoricalData.Builder().reqId(reqId).date(date).open(open).high(high).low(low)
				.close(close).volume(volume).count(count).WAP(WAP).hasGaps(hasGaps).build();
		callbackHistorical.accept(h);
	}

	@Override
	public void scannerParameters(String xml) {
		log.info("scannerParameters");

	}

	@Override
	public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		log.info("scannerData");

	}

	@Override
	public void scannerDataEnd(int reqId) {
		log.info("scannerDataEnd");

	}

	@Override
	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume,
			double wap, int count) {
		log.info("realtimeBar");

	}

	@Override
	public void currentTime(long time) {
		log.info("currentTime");

	}

	@Override
	public void fundamentalData(int reqId, String data) {
		log.info("fundamentalData");

	}

	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
		log.info("deltaNeutralValidation");

	}

	@Override
	public void tickSnapshotEnd(int reqId) {
		log.info("tickSnapshotEnd");

	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {
		log.info("marketDataType");

	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {
		log.info("commissionReport");

	}

	@Override
	public void accountSummary(int reqId, String account, String tag, String value, String currency) {
		log.info("accountSummary");

	}

	@Override
	public void accountSummaryEnd(int reqId) {
		log.info("accountSummaryEnd");

	}

	@Override
	public void verifyMessageAPI(String apiData) {
		log.info("verifyMessageAPI");

	}

	@Override
	public void verifyCompleted(boolean isSuccessful, String errorText) {
		log.info("verifyCompleted");

	}

	@Override
	public void displayGroupList(int reqId, String groups) {
		log.info("displayGroupList");

	}

	@Override
	public void displayGroupUpdated(int reqId, String contractInfo) {
		log.info("displayGroupUpdated");

	}

}
