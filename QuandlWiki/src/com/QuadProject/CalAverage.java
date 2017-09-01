package com.QuadProject;

import com.QuadProject.PRICES;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.text.DecimalFormat;
import java.io.IOException;

public class CalAverage {

	String TotalCOF = "", avgValue = "";

	public String getAverage(String ticker, String month, int mode)
			throws JsonParseException, JsonMappingException, IOException {//Calculating Average Monthly Open and Close prices for each security for each month of data in the data set.

		Double AvgOpen = 0.00, AvgClose = 0.00, TotalOpen = 0.00, TotalClose = 0.00;
		String StrOpen = "", StrClose = "", strWholeData = "";

		URL jsonUrl = new URL(
				"https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?date.gte=20170101&date.lt=20170701&ticker=COF,MSFT,GOOGL&qopts.columns=ticker,date,open,close,volume&api_key=s-GMZ_xkw6CrkGYUWs1p");
		PRICES prices = null;
		ObjectMapper mapper = new ObjectMapper();
		prices = mapper.readValue(jsonUrl, PRICES.class);
		
		int count = 0;
		for (int i = 0; i < prices.getDatatable().getData().size(); i++) {
			strWholeData += prices.getDatatable().getData().get(i) + "\n";

			if (prices.getDatatable().getData().get(i).subList(0, 1).toString().equals(ticker)) {
				if (prices.getDatatable().getData().get(i).subList(1, 2).toString().contains(month))

				{
					StrOpen = prices.getDatatable().getData().get(i).subList(2, 3).toString();
					TotalOpen += Double.parseDouble(StrOpen.substring(1, StrOpen.length() - 1));

					StrClose = prices.getDatatable().getData().get(i).subList(3, 4).toString();
					TotalClose += Double.parseDouble(StrClose.substring(1, StrClose.length() - 1));
					count++;

				}
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		AvgOpen = TotalOpen / count;  //Calculating average open price 
		AvgClose = TotalClose / count; //Calculating average close price
		
		avgValue = "{\"month\":\"" + month + "\",\"average_open\":\"" + df.format(AvgOpen) + "\",\"average_close\":\""
				+ df.format(AvgClose);

		switch (mode) {
		case 0:
			return avgValue; //Returning the formatted string showing Average Monthly Open and Close prices for each security for each month of data in the data set.
		case 1:
			return strWholeData; //Returning pricing data from the Quandl WIKI for a given set of securities and date range
		default:
			return "";
		}

	}

}
