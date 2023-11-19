package main;

import controllers.FollowController;
import repositories.ApiKeysRepository;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server running");
        Endpoint.publish("http://0.0.0.0:7000/followerservice", new FollowController());
    }
}
