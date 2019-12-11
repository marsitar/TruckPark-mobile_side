package com.example.truckpark.view.functionality.mop;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.service.mopdata.RequestMopDataService;

public class FoundMopData extends AppCompatActivity {
    public static final String MOPNAME = "mopname";
    public static final String MOPID = "mopid";
    private String mopId;
    private Mop mop;
    private TextView mopContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_mop_data);
        Intent intent = getIntent();

        String messageText = intent.getStringExtra(MOPNAME);

        this.mopId = intent.getStringExtra(MOPID);

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////////////////////////////////////////////////////////////

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        Mop mop = requestMopDataService.getMopById(mopId);
        TextView mopName = (TextView) findViewById(R.id.mopname);
        mopName.setText(mop.getIdentificationName());

        TextView mopContent = (TextView) findViewById(R.id.mopcontent);



        String organization = String.format("☐ ODDZIAL: %s\n", mop.getExtendedMopData().getOrganization());
        String place = String.format("☐ MIEJSCOWOSC: %s\n", mop.getPlace());
        String identificationData = String.format("☐ DANE IDENTYFIKACYJNE: %s\n", mop.getIdentificationName());
        String category = String.format("☐ KATEGORIA: %s\n", mop.getCategory());
        String coordinates = String.format("☐ WSPOLRZEDNE: %f X\n %f Y", mop.getCoordinate().getX(), mop.getCoordinate().getY());

        SpannableStringBuilder organizationBold = getSpannableStringBuilder(organization, 0, 9);
        SpannableStringBuilder placeBold = getSpannableStringBuilder(place, 0, 13);
        SpannableStringBuilder identificationDataBold = getSpannableStringBuilder(identificationData, 0, 22);
        SpannableStringBuilder categoryBold = getSpannableStringBuilder(category, 0, 12);
        SpannableStringBuilder coordinatesBold = getSpannableStringBuilder(coordinates, 0, 12);

        mopContent.setText(organizationBold
                .append(placeBold)
                .append(identificationDataBold)
                .append(categoryBold)
                .append(coordinatesBold));


    }

    public void printMopData(View view) {
        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        String organization = String.format("☐ ODDZIAL: %s\n", mop.getExtendedMopData().getOrganization());
        String place = String.format("☐ MIEJSCOWOSC: %s\n", mop.getPlace());
        String identificationData = String.format("☐ DANE IDENTYFIKACYJNE: %s\n", mop.getIdentificationName());
        String category = String.format("☐ KATEGORIA: %s\n", mop.getCategory());
        String coordinates = String.format("☐ WSPOLRZEDNE: %f X\n %f Y", mop.getCoordinate().getX(), mop.getCoordinate().getY());

        SpannableStringBuilder organizationBold = getSpannableStringBuilder(organization, 0, 9);
        SpannableStringBuilder placeBold = getSpannableStringBuilder(place, 0, 13);
        SpannableStringBuilder identificationDataBold = getSpannableStringBuilder(identificationData, 0, 22);
        SpannableStringBuilder categoryBold = getSpannableStringBuilder(category, 0, 12);
        SpannableStringBuilder coordinatesBold = getSpannableStringBuilder(coordinates, 0, 12);

        mopContent.setText(organizationBold
                .append(placeBold)
                .append(identificationDataBold)
                .append(categoryBold)
                .append(coordinatesBold));
    }

    public void printRoadData(View view) {

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);


        String roadClass = String.format("☐ KLASA TECHNICZNA: %s\n", mop.getExtendedMopData().getRoadClass());
        String roadNumber = String.format("☐ NUMER: %s\n", mop.getRoadNumber());
        String direction = String.format("☐ KIERUNEK: %s\n", mop.getExtendedMopData().getDirection());

        SpannableStringBuilder roadClassBold = getSpannableStringBuilder(roadClass, 0, 19);
        SpannableStringBuilder roadNumberBold = getSpannableStringBuilder(roadNumber, 0, 8);
        SpannableStringBuilder directionBold = getSpannableStringBuilder(direction, 0, 11);

        mopContent.setText(roadClassBold
                .append(roadNumberBold)
                .append(directionBold));
    }

    public void printPlaceNumber(View view) {

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        String truckPlaces = String.format("☐ MIEJSCA CIEZAROWE OGOLEM: %d\n", mop.getTruckPlaces());
        String freeTruckPlaces = String.format("☐ WOLNE MIEJSCA CIEZAROWE: %d\n\n", mop.getTruckPlaces() - mop.getOccupiedTruckPlaces());
        String passengerPlaces = String.format("☐ MIEJSCA OSOBOWE OGOLEM: %d\n\n", mop.getExtendedMopData().getPassengerPlaces());
        String coachPlaces = String.format("☐ MIEJSCA AUTOBUSOWE: %d", mop.getExtendedMopData().getCoachPlaces());

        SpannableStringBuilder truckPlacesBold = getSpannableStringBuilder(truckPlaces, 0, 27);
        SpannableStringBuilder freeTruckPlacesBold = getSpannableStringBuilder(freeTruckPlaces, 0, 25);
        SpannableStringBuilder passengerPlacesBold = getSpannableStringBuilder(passengerPlaces, 0, 25);
        SpannableStringBuilder coachPlacesBold = getSpannableStringBuilder(coachPlaces, 0, 21);

        mopContent.setText(truckPlacesBold
                .append(freeTruckPlacesBold)
                .append(passengerPlacesBold)
                .append(coachPlacesBold));
    }

    public void printFacilitiesAndSecurity(View view) {

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        String isGuarded = String.format("☐ STRZEZONY: %s\n", mop.getExtendedMopData().getGuarded().booleanValue() ? "✔" : "✗");
        String isFenced = String.format("☐ OGRODZONY: %s\n", mop.getExtendedMopData().getFenced().booleanValue() ? "✔" : "✗");
        String isSecurityCamera = String.format("☐ MONITORING WIDEO: %s\n", mop.getExtendedMopData().getSecurityCamera().booleanValue() ? "✔" : "✗");
        String isPetroleum = String.format("☐ STACJA BENZYNOWA: %s\n", mop.getExtendedMopData().getPetroleum().booleanValue() ? "✔" : "✗");
        String isRestaurant = String.format("☐ RESTAURACJA: %s\n", mop.getExtendedMopData().getRestaurant().booleanValue() ? "✔" : "✗");
        String isPlaceToStay = String.format("☐ MIEJSCA NOCLEGOWE: %s\n", mop.getExtendedMopData().getPlaceToStay().booleanValue() ? "✔" : "✗");
        String isToilet = String.format("☐ TOALETA: %s\n", mop.getExtendedMopData().getToilet().booleanValue() ? "✔" : "✗");
        String isCarwash = String.format("☐ MYJNIA SAMOCHODOWA: %s\n", mop.getExtendedMopData().getCarwash().booleanValue() ? "✔" : "✗");
        String isWorkshop = String.format("☐ WARSZTAT: %s\n", mop.getExtendedMopData().getWorkshop().booleanValue() ? "✔" : "✗");
        String isLighting = String.format("☐ OSWIETLENIE: %s\n", mop.getExtendedMopData().getLighting().booleanValue() ? "✔" : "✗");
        String isElectricCharger = String.format("☐ LADOWARKA ELEKTRYCZNA: %s\n", mop.getExtendedMopData().getElectricCharger().booleanValue() ? "✔" : "✗");
        String isDangerousCargo = String.format("☐ NIEBEZPIECZNE LADUNKI: %s\n", mop.getExtendedMopData().getDangerousCargo().booleanValue() ? "✔" : "✗");

        SpannableStringBuilder isGuardedBold = getSpannableStringBuilder(isGuarded, 0, 12);
        SpannableStringBuilder isFencedBold = getSpannableStringBuilder(isFenced, 0, 12);
        SpannableStringBuilder isSecurityCameraBold = getSpannableStringBuilder(isSecurityCamera, 0, 19);
        SpannableStringBuilder isPetroleumBold = getSpannableStringBuilder(isPetroleum, 0, 19);
        SpannableStringBuilder isRestaurantBold = getSpannableStringBuilder(isRestaurant, 0, 14);
        SpannableStringBuilder isPlaceToStayBold = getSpannableStringBuilder(isPlaceToStay, 0, 20);
        SpannableStringBuilder isToiletBold = getSpannableStringBuilder(isToilet, 0, 10);
        SpannableStringBuilder isCarwashBold = getSpannableStringBuilder(isCarwash, 0, 21);
        SpannableStringBuilder isWorkshopBold = getSpannableStringBuilder(isWorkshop, 0, 11);
        SpannableStringBuilder isLightingBold = getSpannableStringBuilder(isLighting, 0, 14);
        SpannableStringBuilder isElectricChargerBold = getSpannableStringBuilder(isElectricCharger, 0, 24);
        SpannableStringBuilder isDangerousCargoBold = getSpannableStringBuilder(isDangerousCargo, 0, 23);

        mopContent.setText(isGuardedBold
                .append(isFencedBold)
                .append(isSecurityCameraBold)
                .append(isPetroleumBold)
                .append(isRestaurantBold)
                .append(isPlaceToStayBold)
                .append(isToiletBold)
                .append(isCarwashBold)
                .append(isWorkshopBold)
                .append(isLightingBold)
                .append(isElectricChargerBold)
                .append(isDangerousCargoBold));
    }

    public void printOrganizationData(View view) {

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        String organizationInCharge = String.format("☐ IMIE I NAZWISKO/NAZWA: %s\n", mop.getExtendedMopData().getOrganizationInCharge());
        String organizationInChargePhone = String.format("☐ NUMER TELEFONU: %s\n", mop.getExtendedMopData().getOrganizationInChargePhone());
        String organizationInChargeEmail = String.format("☐ ADRES EMAIL: %s\n", mop.getExtendedMopData().getOrganizationInChargeEmail());

        SpannableStringBuilder organizationInChargeBold = getSpannableStringBuilder(organizationInCharge, 0, 24);
        SpannableStringBuilder organizationInChargePhoneBold = getSpannableStringBuilder(organizationInChargePhone, 0, 17);
        SpannableStringBuilder organizationInChargeEmailBold = getSpannableStringBuilder(organizationInChargeEmail, 0, 14);

        mopContent.setText(organizationInChargeBold
                .append(organizationInChargePhoneBold)
                .append(organizationInChargeEmailBold));
    }


    private SpannableStringBuilder getSpannableStringBuilder(String stringToBeBold, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringToBeBold);
        spannableStringBuilder.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }
}
