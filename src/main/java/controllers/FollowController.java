package controllers;

import enums.ApiResponse;
import exceptions.ApiException;
import middlewares.AuthMiddleware;
import middlewares.LoggingMiddleware;
import services.FollowerService;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;

@WebService
public class FollowController {
    private final FollowerService followerService;
    @Resource
    private WebServiceContext wsContext;

    public FollowController() {
        this.followerService = new FollowerService();
    }

    @WebMethod
    public String health(@WebParam(name = "apikey", header = true) String apiKey) throws ApiException {
        MessageContext messageContext = wsContext.getMessageContext();

        LoggingMiddleware.log(messageContext, "Get server status", "/health");

        return ApiResponse.UP.getMessage();
    }

    @WebMethod
    public List<Integer> getCurrentFollowerIds(@WebParam(name = "premiumId") int premiumId, @WebParam(name = "apikey", header = true) String apiKey) {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Get current followers of premium user", "/getcurrentfollowerids");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        return followerService.getCurrentFollowerIds(premiumId);
    }

    @WebMethod
    public List<Integer> getCurrentFollowedIds(@WebParam(name = "followerId") int followerId, @WebParam(name = "apikey", header = true) String apiKey) {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Get premium users who are followed", "/getcurrentfollowedids");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        return followerService.getFollowedIds(followerId);
    }

    @WebMethod
    public List<Integer> getPendingFollowerIds(@WebParam(name = "premiumId") int premiumId, @WebParam(name = "apikey", header = true) String apiKey) throws ApiException {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Get pending followers of premium user", "/getpendingfollowerids");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        return followerService.getPendingFollowerIds(premiumId);
    }

    @WebMethod
    public String unfollow(@WebParam(name = "premiumId") int premiumId, @WebParam(name = "followerId") int followerId, @WebParam(name = "apikey", header = true) String apiKey) throws ApiException {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Unfollow user", "/unfollow");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        followerService.unfollow(premiumId, followerId);

        return ApiResponse.UNFOLLOW_SUCCESS.getMessage();
    }

    @WebMethod
    public String follow(@WebParam(name = "premiumId") int premiumId, @WebParam(name = "followerId") int followerId, @WebParam(name = "apikey", header = true) String apiKey) throws ApiException {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Reject follow request", "/reject");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        followerService.follow(premiumId, followerId);

        return ApiResponse.FOLLOW_SUCCESS.getMessage();
    }

    @WebMethod
    public String accept(@WebParam(name = "premiumId") int premiumId, @WebParam(name = "followerId") int followerId, @WebParam(name = "apikey", header = true) String apiKey) throws ApiException {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Accept follow request", "/accept");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        followerService.accept(premiumId, followerId);

        return ApiResponse.ACCEPT_SUCCESS.getMessage();
    }

    @WebMethod
    public String reject(@WebParam(name = "premiumId") int premiumId, @WebParam(name = "followerId") int followerId, @WebParam(name = "apikey", header = true) String apiKey) throws ApiException {
        MessageContext messageContext = wsContext.getMessageContext();
        LoggingMiddleware.log(messageContext, "Reject follow request", "/reject");

        if (!AuthMiddleware.isAuthenticated(apiKey)) {
            throw new ApiException(ApiResponse.UNAUTHORIZED);
        }

        followerService.reject(premiumId, followerId);

        return ApiResponse.REJECT_SUCCESS.getMessage();
    }
}
