package classes;

import javax.persistence.*;

@Entity
@Table (name = "PlaceInformation")
public class PlaceInformation {
    private Integer placeInformationID;
    private String information;
    private String name;
    private String fullAdress;
    private float rating;
    private int reviews;

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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
