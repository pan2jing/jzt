package jzt.erp.store.service;

import jzt.erp.store.model.City;
import jzt.erp.store.model.CityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
//@Service
@Service("CityService")
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityDao cityDao;

    public City findCityByName(String cityName) {
        return cityDao.findByName(cityName);
    }

    public City findCityByName2(String cityName) {
        LOGGER.info("CityServiceImpl.findCityByName2() : 从缓存中获取了城市 >> " + cityName);
        return cityDao.findByName2(cityName);
    }

}
