package org.acme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
public class DTUPayResource {

    //List<String> cids = new ArrayList<>();
    List<HashMap> transactions = new ArrayList<>();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(HashMap t) {
        if (!t.get("cid").equals("cid1")) {
            return Response.status(400).header("error", "customer with id " + t.get("cid") + " is unknown").build();
        }
        int am = Integer.parseInt((String) t.get("amount"));
        HashMap m = new HashMap();
        m.put("amount", am);
        m.put("cid", (String) t.get("cid"));
        m.put("mid", (String) t.get("mid"));
        transactions.add(m);
        return Response.status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HashMap> getList() {
        return transactions;
    }


}

