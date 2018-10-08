package controller;

import java.io.IOException;
import java.text.ParseException;

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
import org.json.JSONException;
import org.json.JSONObject;
 
@RequestScoped
@ManagedBean(name="hello")
public class Hello {
	
	static CloseableHttpClient httpClient = HttpClients.createDefault();
     
    @PostConstruct
    public void init(){
        System.out.println(" Bean executado! ");
    }
     
    public String getMessage(){
    	//System.out.println();
        return sendLiveRequest();
    }
    
    private String sendLiveRequest() {

		//Variáveis auxiliares
		Double from2 = 1.0;
		Double to2 = 1.0;
		String BASE_URL = "http://localhost:8080/CurrencyConverterREST/converter";
		
		// Inicializa o objeto HttpGet com a URL para mandar a requisição para a API
		HttpGet get = new HttpGet(BASE_URL + "/1524584521/2" );
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// Converte a resposta JSON em um objeto equivalente em Java
			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

			//Lança mensagem no console de que o acesso à API foi iniciado
			System.out.println("Live Currency Exchange Rates");
			
			//Variável utilizada para capturar se a requisição à API teve status ou não
			//boolean status = exchangeRates.getBoolean("success");

			/*if (!status) {
				String codigoErro = exchangeRates.getJSONObject("error").getString("code");

				String infoErro = exchangeRates.getJSONObject("error").getString("info");

				System.out.println("API reached its peak of access.");
				System.out.println("Error: " + codigoErro);
				System.out.println("Message: " + infoErro);
				System.exit(0);
			}*/
			
			
			//Valor equivalente à 1 dólar na moeda de origem
			System.out.println("Converting " + "teste " + " in " + ": "
					+ exchangeRates.getString("moedaOrigem"));
			
			return exchangeRates.getString("moedaOrigem").toString();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

}
 
}