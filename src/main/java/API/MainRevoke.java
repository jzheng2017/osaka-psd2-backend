package API;

import API.Banks.Rabobank.RabobankClient;
import API.Services.UserService;

public class MainRevoke {

    public static void main(String[] args) {
        RabobankClient service = new RabobankClient();
        String token = "AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllsbMmPBukL9xiZBCOw6CTvm-EwBhaEfSNlezxv1vXV3eQ0k_tKL6XuFsfsGXo42X-hZrhXYOaxJJEowhr7JI8RoZy_FrurWnkKwuia-w-I6qo5m0jO8vGnQMfK8xWmu-HL8ORVvGFzSprM80w1oKBS2ucb-MxhyT8s-73e__428PFkoKgdXkZy1GkZ-oKjsSVrtRxIskzbQGb7_4dxkbwt4DUunIK1FHY-rlvm5K7eKc";
        service.getConsentId(token);
    }
}
