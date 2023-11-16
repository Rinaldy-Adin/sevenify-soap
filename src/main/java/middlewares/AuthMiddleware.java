package middlewares;

import models.ApiKeysModel;
import repositories.ApiKeysRepository;

public class AuthMiddleware {
    public static boolean isAuthenticated(String apiKey) {
        ApiKeysModel apiKeyModel = ApiKeysRepository.getApiKeyByKey(apiKey);
        return apiKeyModel != null;
    }
}
