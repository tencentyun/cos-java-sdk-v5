package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.persistence.CarLocation;
import com.qcloud.cos.model.ciModel.persistence.CarTag;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.model.ciModel.persistence.PlateContent;
import org.xml.sax.Attributes;

import java.util.List;

public class DetectCarHandler extends CIAbstractHandler {
    public DetectCarResponse response = new DetectCarResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        List<CarTag> carTags = response.getCarTags();
        if (in("Response") && "CarTags".equals(name)) {
            carTags.add(new CarTag());
        } else if (in("Response", "CarTags") && "CarLocation".equals(name)) {
            CarTag carTag = carTags.get(carTags.size() - 1);
            carTag.getCarLocations().add(new CarLocation());
        } else if (in("Response", "CarTags") && "CarLocation".equals(name)) {
            CarTag carTag = carTags.get(carTags.size() - 1);
            carTag.getCarLocations().add(new CarLocation());
        } else if (in("Response", "CarTags", "PlateContent") && "PlateLocation".equals(name)) {
            CarTag carTag = carTags.get(carTags.size() - 1);
            PlateContent plateContent = carTag.getPlateContent();
            PlateContent.PlateLocation plateLocation = new PlateContent.PlateLocation();
            plateContent.getPlateLocationList().add(plateLocation);
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response")) {
            if (name.equals("RequestId")) {
                response.setRequestId(getText());
            }
        }

        List<CarTag> carTags = response.getCarTags();
        if (carTags.isEmpty()) {
           return;
        }
        CarTag carTag = carTags.get(carTags.size() - 1);

        if (in("Response", "CarTags")) {
            switch (name) {
                case "Serial":
                    carTag.setSerial(getText());
                    break;
                case "Brand":
                    carTag.setBrand(getText());
                    break;
                case "Type":
                    carTag.setType(getText());
                    break;
                case "Color":
                    carTag.setColor(getText());
                    break;
                case "Confidence":
                    carTag.setConfidence(getText());
                    break;
                case "Year":
                    carTag.setYear(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "CarTags", "CarLocation")) {
            List<CarLocation> carLocations = carTag.getCarLocations();
            if (carLocations.isEmpty()) {
                return;
            }
            CarLocation carLocation = carLocations.get(carLocations.size() - 1);
            switch (name) {
                case "X":
                    carLocation.setX(getText());
                    break;
                case "Y":
                    carLocation.setY(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "CarTags", "CarLocation")) {
            List<CarLocation> carLocations = carTag.getCarLocations();
            if (carLocations.isEmpty()) {
                return;
            }
            CarLocation carLocation = carLocations.get(carLocations.size() - 1);
            switch (name) {
                case "X":
                    carLocation.setX(getText());
                    break;
                case "Y":
                    carLocation.setY(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "CarTags", "PlateContent")) {
            PlateContent plateContent = carTag.getPlateContent();
            switch (name) {
                case "Plate":
                    plateContent.setPlate(getText());
                    break;
                case "Color":
                    plateContent.setColor(getText());
                    break;
                case "Type":
                    plateContent.setType(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "CarTags", "PlateContent", "PlateLocation")) {
            List<PlateContent.PlateLocation> plateLocationList = carTag.getPlateContent().getPlateLocationList();
            if (plateLocationList.isEmpty()) {
                return;
            }
            PlateContent.PlateLocation plateLocation = plateLocationList.get(plateLocationList.size() - 1);
            switch (name) {
                case "X":
                    plateLocation.setX(getText());
                    break;
                case "Y":
                    plateLocation.setY(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public DetectCarResponse getResponse() {
        return response;
    }

    public void setResponse(DetectCarResponse response) {
        this.response = response;
    }
}
