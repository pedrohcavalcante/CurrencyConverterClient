package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import domain.ObjectJSON;

@RequestScoped
@ManagedBean(name = "hello")
public class Hello {

	static CloseableHttpClient httpClient = HttpClients.createDefault();
	private String currency1;
	private String currency2;
	private Double valueToConvert;
	private String isOneToAll = "Nao";
	private String valor = "";
	private List<ObjectJSON> objetosJSON;

	private List<String> allCurrencies = new ArrayList<>();
	String[] currencies = { "DKK", "NOK", "SEK", "CZK", "GBP", "TRY", "INR", "IDR", "PKR", "THB", "USD", "AUD", "CAD",
			"SGD", "HKD", "TWD", "NZD", "EUR", "HUF", "CHF", "JPY", "ILS", "CLP", "PHP", "MXN", "ZAR", "BRL", "MYR",
			"RUB", "KRW", "CNY", "PLN" };

	@PostConstruct
	public void init() {
		System.out.println(" Bean executado! ");
		for (String a : currencies) {
			allCurrencies.add(a);
		}
		objetosJSON = new ArrayList<>();
	}

	public String getMessage() {
		// System.out.println();
		return sendLiveRequest();
	}

	public String getCurrency1() {
		return currency1;
	}

	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	public String getCurrency2() {
		return currency2;
	}

	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	public List<String> getAllCurrencies() {
		return allCurrencies;
	}

	/*
	 * public void setAllCurrencies(List<String> allCurrencies) { this.allCurrencies
	 * = allCurrencies; }
	 */

	public Double getValueToConvert() {
		return valueToConvert;
	}

	public void setValueToConvert(Double valueToConvert) {
		this.valueToConvert = valueToConvert;
	}

	public String getIsOneToAll() {
		return isOneToAll;
	}

	public void setIsOneToAll(String isOneToAll) {
		this.isOneToAll = isOneToAll;
	}

	public String converter() {
		if (isOneToAll.equals("Sim")) {
			System.out.println("chamou sim");
			valor = "";
			sendLiveRequestForAll();
		} else {
			System.out.println("chamou nao");
			valor = sendLiveRequest();
		}
		return "index.xhtml";
	}

	private String sendLiveRequest() {

		// Variáveis auxiliares
		Double from2 = 1.0;
		Double to2 = 1.0;
		String BASE_URL = "http://localhost:8080/CurrencyConverterREST/converter";

		// Inicializa o objeto HttpGet com a URL para mandar a requisição para a API
		HttpGet get = new HttpGet(BASE_URL + "/" + currency1 + "/" + currency2 + "/" + valueToConvert);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// Converte a resposta JSON em um objeto equivalente em Java
			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

			// Lança mensagem no console de que o acesso à API foi iniciado
			System.out.println("Live Currency Exchange Rates");

			// Variável utilizada para capturar se a requisição à API teve status ou não
			// boolean status = exchangeRates.getBoolean("success");

			/*
			 * if (!status) { String codigoErro =
			 * exchangeRates.getJSONObject("error").getString("code");
			 * 
			 * String infoErro = exchangeRates.getJSONObject("error").getString("info");
			 * 
			 * System.out.println("API reached its peak of access.");
			 * System.out.println("Error: " + codigoErro); System.out.println("Message: " +
			 * infoErro); System.exit(0); }
			 */

			// Valor equivalente à 1 dólar na moeda de origem
			System.out.println("Converting " + valueToConvert + currency1 + " in ALL" + ": "
					+ exchangeRates.getString("moedaOrigem"));

			return Double.toString(exchangeRates.getDouble("valor"));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String sendLiveRequestForAll() {

		// Variáveis auxiliares
		Double from2 = 1.0;
		Double to2 = 1.0;
		String BASE_URL = "http://localhost:8080/CurrencyConverterREST/converter";

		// Inicializa o objeto HttpGet com a URL para mandar a requisição para a API
		HttpGet get = new HttpGet(BASE_URL + "/" + currency1 + "/" + valueToConvert);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// Converte a resposta JSON em um objeto equivalente em Java
			JSONArray exchangeArray = new JSONArray(EntityUtils.toString(entity));
			/* JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity)); */

			// Lança mensagem no console de que o acesso à API foi iniciado
			System.out.println("Live Currency Exchange Rates");

			// Variável utilizada para capturar se a requisição à API teve status ou não
			// boolean status = exchangeRates.getBoolean("success");

			/*
			 * if (!status) { String codigoErro =
			 * exchangeRates.getJSONObject("error").getString("code");
			 * 
			 * String infoErro = exchangeRates.getJSONObject("error").getString("info");
			 * 
			 * System.out.println("API reached its peak of access.");
			 * System.out.println("Error: " + codigoErro); System.out.println("Message: " +
			 * infoErro); System.exit(0); }
			 */

			// Valor equivalente à 1 dólar na moeda de origem
			for (int i = 0; i < exchangeArray.length(); i++) {
				JSONObject tempObj = new JSONObject();
				tempObj = (JSONObject) exchangeArray.get(i);
				ObjectJSON obj = new ObjectJSON();
				obj.setMoedaDestino(tempObj.getString("moedaOrigem"));
				obj.setMoedaOrigem(tempObj.getString("moedaDestino"));
				obj.setValor(tempObj.getDouble("valor"));
				objetosJSON.add(obj);
			}

			// TODO fazer isso imprimir uma table

			return "";

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<ObjectJSON> getObjetosJSON() {
		return objetosJSON;
	}

	public void setObjetosJSON(List<ObjectJSON> objetosJSON) {
		this.objetosJSON = objetosJSON;
	}

}