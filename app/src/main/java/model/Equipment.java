package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipment implements Serializable {

    private String mEquipmentEquipment;
    private String mEquipmentShortDesc;
    private String mEquipmentPrice;
    private String mEquipmentEmail;

    public static final String EQUIPMENT = "equipmentname";
    public static final String SHORT_DESC = "shortdesc";
    public static final String PRICE = "price";
    public static final String EMAiL = "email";

    public Equipment(String theEquipment, String theShortDesc, String thePrice, String theEmail) {
        this.mEquipmentEquipment = theEquipment;
        this.mEquipmentShortDesc = theShortDesc;
        this.mEquipmentPrice = thePrice;
        this.mEquipmentEmail = theEmail;
    }

    public static List<Equipment> parseEquipmentJson(String equipmentJson) throws JSONException {
        List<Equipment> equipmentList = new ArrayList<>();
        if (equipmentJson != null) {

            JSONArray arr = new JSONArray(equipmentJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Equipment equipment = new Equipment(obj.getString(Equipment.EQUIPMENT), obj.getString(Equipment.SHORT_DESC)
                        , obj.getString(Equipment.PRICE), obj.getString(Equipment.EMAiL));
                equipmentList.add(equipment);
            }

        }

        return  equipmentList;

    }



    public String getmEquipmentEquipment() {
        return mEquipmentEquipment;
    }

    public String getmEquipmentShortDesc() {
        return mEquipmentShortDesc;
    }

    public String getmEquipmentPrice() {
        return mEquipmentPrice;
    }

    public String getmEquipmentEmail() {
        return mEquipmentEmail;
    }

    public void setmEquipmentEquipment(String mEquipmentEquipment) {
        this.mEquipmentEquipment = mEquipmentEquipment;
    }

    public void setmEquipmentShortDesc(String mEquipmentShortDesc) {
        this.mEquipmentShortDesc = mEquipmentShortDesc;
    }

    public void setmEquipmentPrice(String mEquipmentPrice) {
        this.mEquipmentPrice = mEquipmentPrice;
    }

    public void setmEquipmentEmail(String mEquipmentEmail) {
        this.mEquipmentEmail = mEquipmentEmail;
    }
}