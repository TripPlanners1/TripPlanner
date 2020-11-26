package classes;

import javax.persistence.*;

@Entity
@Table(name = "Place")
public class Place {
    private Integer placeID;
    private City city;
    private Integer placeInfoID;
    private Integer placeType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Integer id) {
        this.placeID = id;
    }

    public Integer getPlaceInfoID() {
        return placeInfoID;
    }

    public void setPlaceInfoID(int placeInfoId) {
        this.placeInfoID = placeInfoId;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityID", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
