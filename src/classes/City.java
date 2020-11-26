package classes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "City")
public class City {
    private Integer cityID;
    private String cityName;
    private String cityInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer id) {
        this.cityID = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }
}
