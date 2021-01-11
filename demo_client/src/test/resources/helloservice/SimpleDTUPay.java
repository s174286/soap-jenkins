package helloservice;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleDTUPay {

    WebTarget baseUrl;

    public SimpleDTUPay() {
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/");
    }

    public boolean pay(int amount, String cid, String mid) {

        Transaction t = new Transaction(amount, cid, mid);
        HashMap m = new HashMap();
        m.put("amount", String.valueOf(amount));
        m.put("cid", cid);
        m.put("mid", mid);
        Response response = baseUrl.path("transactions").request().post(Entity.entity(m, MediaType.APPLICATION_JSON));
        if(response.getStatus() != 200) {
            throw new RuntimeException(response.getHeaderString("error"));
        }
        return response.getStatus() == 200;
    }

    public List<HashMap> transactions() {
        return baseUrl.path("transactions").request().get(new GenericType<List<HashMap>>(){});
    }
}
