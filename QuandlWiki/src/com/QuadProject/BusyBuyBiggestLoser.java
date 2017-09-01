package com.QuadProject;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BusyBuyBiggestLoser { 

	public String bigggestLoser() throws JsonParseException, JsonMappingException, IOException {//Calculating biggest loser
		URL jsonUrl = new URL(
				"https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?date.gte=20170101&date.lt=20170701&ticker=COF,MSFT,GOOGL&qopts.columns=ticker,date,open,close,volume&api_key=s-GMZ_xkw6CrkGYUWs1p");
		PRICES prices = null;
		ObjectMapper mapper = new ObjectMapper();
		prices = mapper.readValue(jsonUrl, PRICES.class);
		Double openPrice = 0.00, closePrice = 0.00;
		String strOpenprice = "", strClosePrice = "", strLoserData = "";
		int cofCount = 0, googleCount = 0, msftCount = 0;

		for (int i = 0; i < prices.getDatatable().getData().size(); i++) {

			strOpenprice = prices.getDatatable().getData().get(i).subList(2, 3).toString();
			openPrice = Double.parseDouble(strOpenprice.substring(1, strOpenprice.length() - 1));
			strClosePrice = prices.getDatatable().getData().get(i).subList(3, 4).toString();
			closePrice = Double.parseDouble(strClosePrice.substring(1, strClosePrice.length() - 1));
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.format(openPrice);
			df.format(closePrice);
			if (closePrice < openPrice) {//Checking if closing price was lower than the opening price
				if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[COF]"))
					cofCount++;
				else if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[GOOGL]"))
					googleCount++;
				else if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[MSFT]"))
					msftCount++;
			}

		}
		if (Math.max(Math.max(cofCount, googleCount), msftCount) == cofCount)//Checking for the security which was the biggest loser
			strLoserData = "\n\n             BIGGEST LOOSER       \n          ====================\nThe biggest-loser is : COF.\nNumber of days this security’s closing price was lower than that day’s opening price : "
					+ cofCount;
		else if (Math.max(Math.max(cofCount, googleCount), msftCount) == googleCount)
			strLoserData = "\n\n             BIGGEST LOOSER       \n          ====================\nThe biggest-loser is : GOOGL.\nNumber of days this security’s closing price was lower than that day’s opening price : "
					+ googleCount;
		else if (Math.max(Math.max(cofCount, googleCount), msftCount) == msftCount)
			strLoserData = "\n\n             BIGGEST LOOSER       \n          ====================\nThe biggest-loser is : MSFT.\nNumber of days this security’s closing price was lower than that day’s opening price : "
					+ msftCount + "\n\n\n";

		return strLoserData;//Returning the formatted string showing the biggest loser with the days count
	}

	public String CalcBusyDay() throws JsonParseException, JsonMappingException, IOException {//Calculating busy day of the securities whose volume was more than 10% higher than the security’s average volume

		Double totCofvol = 0.00, totGoogVol = 0.00, totMsftVol = 0.00, avgCofVol = 0.00, avgGoogVol = 0.00,avgMsftVol = 0.00;
		String StrCof = "", StrGoogl = "", strMsft = "", strTotalBusyData = "", highCof = "", highGoogl = "",highMsft = "";		
		int countCof = 0, countGoogl = 0, countMsft = 0;

		URL jsonUrl = new URL(
				"https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?date.gte=20170101&date.lt=20170701&ticker=COF,MSFT,GOOGL&qopts.columns=ticker,date,open,close,volume&api_key=s-GMZ_xkw6CrkGYUWs1p");
		PRICES prices = null;
		ObjectMapper mapper = new ObjectMapper();
		prices = mapper.readValue(jsonUrl, PRICES.class);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(1);
		for (int i = 0; i < prices.getDatatable().getData().size(); i++) { //Calculating total volume for each securities

			if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[COF]")) {

				StrCof = prices.getDatatable().getData().get(i).subList(4, 5).toString();
				totCofvol += Double.parseDouble(StrCof.substring(1, StrCof.length() - 1));
				df.format(totCofvol);
				countCof++;

			}

			else if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[GOOGL]")) {

				StrGoogl = prices.getDatatable().getData().get(i).subList(4, 5).toString();
				totGoogVol += Double.parseDouble(StrGoogl.substring(1, StrGoogl.length() - 1));
				df.format(totGoogVol);
				countGoogl++;

			}

			else if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[MSFT]")) {

				strMsft = prices.getDatatable().getData().get(i).subList(4, 5).toString();
				totMsftVol += Double.parseDouble(strMsft.substring(1, strMsft.length() - 1));
				df.format(totMsftVol);
				countMsft++;

			}
		}

		avgCofVol = totCofvol / countCof; //Calculating average volume of security COF
		avgGoogVol = totGoogVol / countGoogl; //Calculating average volume of security GOOGL
		avgMsftVol = totMsftVol / countMsft;  //Calculating average volume of security MSFT
		df.format(avgCofVol);
		df.format(avgGoogVol);
		df.format(avgMsftVol);

		Double doublConf = 0.0, doublGoogl = 0.0, doublMsft = 0.0;

		for (int i = 0; i < prices.getDatatable().getData().size(); i++) {
			if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[COF]")) {

				StrCof = prices.getDatatable().getData().get(i).subList(4, 5).toString();
				doublConf = Double.parseDouble(StrCof.substring(1, StrCof.length() - 1));
				df.format(doublConf);
				if (doublConf > (avgCofVol * 1.1)) {//checking if the volume of the security COF was more than 10% higher than the security’s average volume
					highCof += "\n[COF]     " + prices.getDatatable().getData().get(i).subList(1, 2).toString()
							+ "     " + StrCof + "     [" + df.format(avgCofVol * 1.1) + "]";
				}

			}

			else if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[GOOGL]")) {

				StrGoogl = prices.getDatatable().getData().get(i).subList(4, 5).toString();
				doublGoogl = Double.parseDouble(StrGoogl.substring(1, StrGoogl.length() - 1));
				df.format(doublGoogl);
				if (doublGoogl > (avgGoogVol * 1.1)) {
					highGoogl += "\n[GOOGL]     " + prices.getDatatable().getData().get(i).subList(1, 2).toString()
							+ "     " + StrGoogl + "     [" + df.format(avgGoogVol * 1.1) + "]";
				}

			}

			else if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals("[MSFT]")) {

				strMsft = prices.getDatatable().getData().get(i).subList(4, 5).toString();
				doublMsft = Double.parseDouble(strMsft.substring(1, strMsft.length() - 1));
				df.format(doublMsft);
				if (doublMsft > (avgGoogVol * 1.1)) {
					highMsft += "\n[MSFT]     " + prices.getDatatable().getData().get(i).subList(1, 2).toString()
							+ "     " + strMsft + "     [" + df.format(avgMsftVol * 1.1) + "]";
				}
				totMsftVol += Double.parseDouble(strMsft.substring(1, strMsft.length() - 1));

			}
		}
		strTotalBusyData = "\n\n\n                BUSY DAYS       \n             ===============\n[TICKER]    [DATE]          [VOLUME]        [AVG VOL]\n============================================"
				+ highCof + "\n     " + highGoogl + "\n   " + highMsft;
		return strTotalBusyData;//Returns  ticker symbol, date, and volume for each day where the volume was more than 10% higher than the security’s average volume. Displaying the average volume too.
	}

}
