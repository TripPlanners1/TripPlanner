package classes;

import javax.persistence.*;

@Entity
@Table (name = "PlaceInformation")
public class PlaceInformation {
    private Integer placeInformationID;
    private String information;
    private String name;
    private String fullAdress;
    private Float rating;
    private Integer reviews;
    private Float Latitude;
    private Float Longitude;

    public PlaceInformation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPlaceInformationID() {
        return placeInformationID;
    }
    public void setPlaceInformationID(Integer id) {
        this.placeInformationID = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        this.Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        this.Longitude = longitude;
    }
}
