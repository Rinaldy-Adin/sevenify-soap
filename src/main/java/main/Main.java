package main;

import controllers.FollowController;
import repositories.ApiKeysRepository;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:8080/followerservice", new FollowController());
    }
}
