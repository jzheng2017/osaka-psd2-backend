import API.DTO.BankToken;
import API.ING.Service.INGAccountService;
import API.RABO.Service.RabobankService;

public class MainTest {

    public static void main(String[] args) {
        INGAccountService service = new INGAccountService();
        BankToken newToken = service.refresh("eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIiwia2lkIjoidHN0LWVjOGY3YjQyLTkzNDYtNDQ1Yi04ZjNkLTc2ODBlZmJmZDA1OSJ9..JAMILvK-_w96UxOnJ5Afrw.OGtplSm45FCnkEGObSzGOmbdfs1D22MpRLQgdMsZb14aGd2Syo7n_wfoUuvhea5n5kd1IiTCmUJoAk8A9hpZG-fDJAJOLiMt14GyD537_PziZJhCKmcz3ld2Ac6n6r1d3XJEDOufGHS6DsfjXmUJ52OlhX3bmcvA1PhRIPLB5HHxvT3K1wIky5pQZIFlqfxHorc8wYSXbs4jrA-Tdf69j3d_urZRjmxlRmMWa7Ee2ju0ptG203wHALLPVVumhkcRmZ89ViX3_UYD-bvpfiTHtfmpMQgGfzP-TCQMkgY47JHG1QpRnFqfjr3PfmyJhx_DXJ6EiXjDoM_PddAbEx4_69IycUCpoAtaHlQtqz0UzNMQpNCsW-9-Ang7zbaG9ElL_S22wKUjWKje1lYlZ4_PYR_AZGTUjfE-ZBs8KZPVfAOIJnHOoQY44daRMcNiSDs1V7bhsa0IGQECDrbtYh14CwaHQ1SGGK97fN3Ht_reLgweCtwACa-ZaR5aaShw013m6RgmUcZvxKudXiTixtgyzIhYH7Hp_eJ-YE7luJjglGtCMbV9oWtNe04y9-hgqj-d45EMTC3Dz8GMQlJVhnMbRilo7Is02CEpoLhaepKJbL5GYtEdaUUwmuhUbZ9YBQri6EnUdjq4ihJDQp9X5PD2ohlXnTvIcyJ5-H60y7AR4v8Y3kb6iJ_O912RdaWwun3MeNhmKcfZHotCV8jqaF8umJbkhFR8T1coO-MBX9NacAJETQi6cMgTJG6RilYeABjZ-1sEYOvhuWTmTlfEVasbc6T0vnk7X-0YkE17KCL7LMfMQ8WBaW2Tn7Kmpn5vmWmqftnjp3NvfcWaz1PX56abOWq60QQMcyuqQMp-_mB-jEDknsVnGI03cpX9HVLVTva4Mxm_cX6eWOMlytWkl_Wvnty--5H2vMg1rWoEm2OzYnxWzRmTuE8denq0vAjPWcEZtHRgvbVT1lzoFHyZLHXruXf0xgK0eyvrkzfLT-zqcHpSJ4DjBkSGarXfAg6ZDI5O5nnBcM3xSjk4thoAH27lnKDrgrpCWbmQ8NAq2nns6RpBfiiowuqWGxDgON13I9RhJSWYoO_ueW_ivQ2TBmWgkgpG7mIMHSlRMbOTMR00Pl4EFEyiljD_MW6_3q9u-JrPbXAW0G6jOhtlnDc42v-fmrqZFnZM30_7T4T5Irb12zsL_OfULD5mSdSitlwbqy1iuv7zO8BU5LmDAWTh-xRSjWwcTPIn4LY3rWn8eIFJjegQDNyUzxS8niBLNMiHGt3saD_csxztRuiUUOicgATFfZ_VyxK886xLyFpusjQUUv6r-gZwuVMUZVhbOKqdwcF7z3NPKIKyUiJxDYaBrFjIfL11HjfH8A2oZHPWtQVxwg-FLhRmSB78SHVLKXK5Dm7jRaxcESwmiYbke21EpCIR14MR6f6HclAlFFz1kfb5u9qcN0fnY8DrrvVGQZ1LCzM1bE0XUk9xiE5q4CDzC13qKMvsWKQTDL6BNINibY3VDf-o7kKvskDSbjacYbHCR3Qpqrnx2bTDt9IsrW9DXPWU3nvAe5FBqO1RUD8lR5WIAnEvM5FWBJS8ORdPKZiGJfokc_n4xxGvJeazxzPYe6Y3t14ftzXCFep4HPOt7mdhoj84zbIET2_BEcaWQXlFZHzJGharjsLQUIFUlnwnc83g4b1SObwRNhpyNa1cP1F6oBfICdqmRiVJ7HUU5fAWrsF6TQ3_hGDTe-_PNesuARvZbtqsFWw-vw2sSdfDaRUtUCmbR9h9p502Y_wC-liDME5iNQLjP_8XqcwN7mioXqeTxxBNVQ4Ulxg7G0IUW-XXQqW4YwpaNo7rY92d2MUIlERm.LNkMzJgL3-ljgKss8omNJYxENnHmGNNt1B3g2EdMuDg");
        System.out.println(newToken.getAccessToken());
        System.out.println(newToken.getRefreshToken());
    }
}
