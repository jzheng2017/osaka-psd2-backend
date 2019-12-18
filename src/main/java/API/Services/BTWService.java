package API.Services;

import API.DataSource.BTWDao;

import javax.inject.Inject;

public class BTWService {
    private BTWDao btwDao;

    @Inject
    public void setBtwDao(BTWDao btwDao){
        this.btwDao = btwDao;
    }

    public String[] getBTWPercentages(){
        return btwDao.getBTWPercentages();
    }
}
