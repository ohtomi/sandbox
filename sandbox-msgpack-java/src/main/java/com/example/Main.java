package com.example;

import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

  private Main() {
  }

  private void basicUsage() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
    PojoContainer orig = new PojoContainer(1, "ohtomi", new PojoItem(11, "kenichi"));
    byte[] bytes = objectMapper.writeValueAsBytes(orig);
    PojoContainer value = objectMapper.readValue(bytes, PojoContainer.class);

    System.out.println("Recap: basicUsage");
    System.out.println("\t" + orig);
    System.out.println("\t" + value);
    System.out.println();
  }

  private void request() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
    Request req = new Request();
    req.setType(1);
    req.setId(2);
    req.setMethod("foo");
    req.setArgs(new Object[] { 1, 2.0, new PojoContainer(1, "ohtomi", new PojoItem(11, "kenichi")) });
    byte[] bytes = objectMapper.writeValueAsBytes(req);
    Request req2 = objectMapper.readValue(bytes, Request.class);

    Object[] args = req2.getArgs();
    PojoContainer pojo = objectMapper.convertValue(args[2], PojoContainer.class);

    System.out.println("Recap: request");
    System.out.println("\t" + req.getClass() + "\t" + req);
    System.out.println("\t" + req2.getClass() + "\t" + req2);
    for (int i = 0; i < args.length; i++) {
      System.out.println("\t\t" + "[" + i + "]:" + args[i].getClass() + "\t" + args[i]);
    }
    System.out.println("\t\t" + "[pojo]:" + pojo.getClass() + "\t" + pojo);
    System.out.println();
  }

  private void response() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
    Response res = new Response();
    res.setType(1);
    res.setId(2);
    res.setError(new UserException("timeout", "3ms", new PojoItem(11, "kenichi")));
    res.setResult(new PojoContainer(1, "ohtomi", new PojoItem(11, "kenichi")));
    byte[] bytes = objectMapper.writeValueAsBytes(res);
    Response res2 = objectMapper.readValue(bytes, Response.class);

    Object error = res2.getError();
    // ObjectMapper cannot convert error with class of RemoteException, it needs the class of concrete java type.
    UserException ex = objectMapper.convertValue(error, UserException.class);

    Object result = res2.getResult();
    // ObjectMapper can convert result with class of PojoContainer, which has some user-defined java type.
    PojoContainer pojo = objectMapper.convertValue(result, PojoContainer.class);

    System.out.println("Recap: response");
    System.out.println("\t" + res.getClass() + "\t" + res);
    System.out.println("\t" + res2.getClass() + "\t" + res2);
    if (error != null) {
      System.out.println("\t\t" + "[error]:" + error.getClass() + "\t" + error);
      System.out.println("\t\t" + "[exception]:" + ex.getClass() + "\t" + ex);
    } else {
      System.out.println("\t\t" + "[error]: <null>");
    }
    if (result != null) {
      System.out.println("\t\t" + "[result]:" + result.getClass() + "\t" + result);
      System.out.println("\t\t" + "[pojo]:" + pojo.getClass() + "\t" + pojo);
    } else {
      System.out.println("\t\t" + "[result]: <null>");
    }
    System.out.println();
  }

  public static void main(String... args) {
    try {
      Main app = new Main();
      app.basicUsage();
      app.request();
      app.response();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
