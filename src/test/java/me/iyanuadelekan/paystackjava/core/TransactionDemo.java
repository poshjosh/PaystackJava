package me.iyanuadelekan.paystackjava.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.BaseRequest;
import java.util.Collections;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Iyanu Adelekan on 16/04/2017.
 * @// TODO: 16/04/2017 Create library tests
 */
public class TransactionDemo {
    public static void main(String... args) {
        try{
            
            Transactions tx = new Transactions();
            final String ref = Long.toHexString(System.currentTimeMillis());
            System.out.println("Reference: " + ref);
            JSONObject json = tx.initializeTransaction(ref, "100", "posh.bc@gmail.com", null, "http://localhost:8080");    
            System.out.println("Response1: " + json.toString(4));

            tx.verifyTransaction(ref);
            
            JSONObject data = json.getJSONObject("data");
            ApiConnection conn = new ApiConnection(data.getString("authorization_url"));
            
            Map<String, Object> params = Collections.singletonMap("access_code", data.get("access_code"));
            BaseRequest req = conn.connectAndQueryWithGetForResponse(params);
            try{
                HttpResponse<String> response = req.asString();
                if(response == null) {
                    System.out.println("Response2: " + response);
                }else{
                    System.out.println("Response2.Message: " + response.getStatusText());
                    System.out.println("Response2.Status: " + response.getStatus());
                    System.out.println("Response2.Headers: " + response.getHeaders());
                    System.out.println("Response2.Body: " + response.getBody());
                }
            }catch(Exception e) {
                e.printStackTrace();
            }

        }finally{
            ApiConnection.shutDown();
        }
//{
//    "data": {
//        "reference": "17554a6221f",
//        "authorization_url": "https://checkout.paystack.com/x93zwxfltjiw0lt",
//        "access_code": "x93zwxfltjiw0lt"
//    },
//    "message": "Authorization URL created",
//    "status": true
//}
        
// Callback        
// http://localhost:8080/?trxref=17554a6221f&reference=17554a6221f        
    }
}
