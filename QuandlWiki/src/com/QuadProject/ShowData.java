package com.QuadProject;

import java.awt.EventQueue;

import javax.swing.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.awt.Color;

public class ShowData {



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowData window = new ShowData();

				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShowData() throws JsonParseException, JsonMappingException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws JsonParseException, JsonMappingException, IOException {

		String[] monthList = { "2017-01", "2017-02", "2017-03", "2017-04", "2017-05", "2017-06" };
		String cofAvg = "", googAvg = "", msftAvg = "";
		CalAverage quandcal = new CalAverage();
		for (int i = 0; i < monthList.length; i++) {

			cofAvg += "\n" + quandcal.getAverage("[COF]", monthList[i], 0) + "}"; //Calling getAverage function for security [COF]
			googAvg += "\n" + quandcal.getAverage("[GOOGL]", monthList[i], 0) + "}"; //Calling getAverage function for security [GOOGL]
			msftAvg += "\n" + quandcal.getAverage("[MSFT]", monthList[i], 0) + "}"; //Calling getAverage function for security [MSFT]
		}
		String strretriveData = "", straverageData = "", strbusyData = "", strBiggestLoser = "";
		strretriveData = "\n\n        RETRIEVED PRICE DATA FOR THE GIVEN SECURITIES AND DATE RANGE  \n     =================================================================\n                                             Secuities Tickers: [COF] [GOOGL] [MSFT] \n                                            Date Range(yyyy-mm): 2017-01 to 2017-06  \n\n"
				+ quandcal.getAverage("", "", 1); //Formatted String containing pricing data from the Quandl WIKI for a given set of securities and date range
		
		straverageData = "\n\n         MONTHWISE AVERAGE OPEN AND AVERAGE CLOSE PRICE\n     ======================================================\n{\"COF\":\""
				+ cofAvg + "}\n\n\"{\"GOOGLE\":\"" + googAvg + "}\n\n\"{\"MSFT\":\"" + msftAvg + "}\n\n"; //Formatted string containing the Average Monthly Open and Close prices for each security for each month of data in the data set.

		BusyBuyBiggestLoser calcdetails = new BusyBuyBiggestLoser();

		strbusyData = calcdetails.CalcBusyDay(); //Formatted string containing busy day of the securities whose volume was more than 10% higher than the security’s average volume
		strBiggestLoser = calcdetails.bigggestLoser(); //Formatted string containing biggest loser details

		JPanel middlePanel = new JPanel();

		JTextArea display = new JTextArea(strretriveData + straverageData + strbusyData + strBiggestLoser + "\n\n", 40,
				45);//Showing whole data in the readable textarea
		display.setBackground(new Color(224, 255, 255));
		display.setForeground(new Color(0, 100, 0));
		display.setEditable(false); // Setting textArea non-editable
		JScrollPane scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		middlePanel.add(scroll); // Adding Textarea in to the middle panel
		JFrame frame = new JFrame();
		frame.getContentPane().add(middlePanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
