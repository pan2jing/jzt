package jzt.erp.store.controller;


import jzt.erp.store.model.City;
import jzt.erp.store.service.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/City")
@RestController
public class CityController {

    @Autowired
    private CityServiceImpl service;

    //http://localhost:8083/City/showCity?cityName=sss
    @RequestMapping("/showCity")
    @ResponseBody
    public City toIndex(@RequestParam(value = "cityName", required = true) String cityName){
        City city = this.service.findCityByName(cityName);
        if (city==null)
        {
            return null;
        }
        else
        {
            return city;
        }
    }

    @RequestMapping("/showCity2")
    @ResponseBody
    public City showCity2(){
        City city = this.service.findCityByName("sss");
        if (city==null)
        {
            return null;


        }
        else
        {
            return city;
        }
    }





}
