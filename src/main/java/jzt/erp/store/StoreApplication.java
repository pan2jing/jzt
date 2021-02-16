package jzt.erp.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;


/*import oracle.jdbc.driver.OracleDriver;*/
@SpringBootApplication
@MapperScan("jzt.erp.store.model")
public class StoreApplication {



    public static void main(String[] args) {
        System.out.println("ddd");
        SpringApplication.run(StoreApplication.class, args);




        Date date = new Date();
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, "SdpNTAdapter");
        //        S

        /*
        @RequestMapping(value = "/city", method = RequestMethod.GET)
        public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
            return cityService.findCityByName(cityName);
        }


        @RequestMapping("/hello")
        public String sayHello() {
            return "Hello,World!";
        }
        */




    }


}
