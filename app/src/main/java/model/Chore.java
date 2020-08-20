package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Chore {

    private String mChore;
    private String mAddr;
    private String mLongDesc;
    private String mPrice;
    private String mEmail;

    public static final String CHORE = "chore";
    public static final String ADDRESS = "address";
    public static final String LONGDESC = "longdesc";
    public static final String PRICE = "price";
    public static final String EMAiL = "email";

    public Chore(String theChore, String theAddress, String theLongdesc, String thePrice, String theEmail) {
        this.mChore = theChore;
        this.mAddr = theAddress;
        this.mLongDesc = theLongdesc;
        this.mPrice = thePrice;
        this.mEmail = theEmail;
    }

    public static List<Chore> parseChoreJson(String choreJson) throws JSONException {
        List<Chore> choreList = new ArrayList<>();
        if (choreJson != null) {

            JSONArray arr = new JSONArray(choreJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Chore chore = new Chore(obj.getString(Chore.CHORE), obj.getString(Chore.ADDRESS)
                        , obj.getString(Chore.LONGDESC)
                        , obj.getString(Chore.PRICE), obj.getString(Equipment.EMAiL));
                choreList.add(chore);
            }

        }

        return  choreList;

    }

    public String getmChore() {
        return mChore;
    }

    public String getmAddr() {
        return mAddr;
    }

    public String getmLongDesc() {
        return mLongDesc;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmEmail() {
        return mEmail;
    }
}
