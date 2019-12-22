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
import com.example.truckpark.domain.json.mopapi.ExtendedMopData;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.domain.json.positionapi.Coordinate;
import com.example.truckpark.repository.CurrentMops;

import java.util.Optional;

public class FoundMopData extends AppCompatActivity {

    public static final String MOPID = "mopid";
    private String mopId;
    private Mop foundMop;
    private TextView mopContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_mop_data);
        Intent intent = getIntent();

        this.mopId = intent.getStringExtra(MOPID);

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////////////////////////////////////////////////////////////

        foundMop = CurrentMops.getCurrentMopsInstance().getCurrentMopsList()
            .stream()
            .filter(mop -> mop.getId().equals(mopId))
            .findFirst()
            .orElse(null);
        TextView mopName = (TextView) findViewById(R.id.mopname);
        mopName.setText(foundMop.getIdentificationName());

        TextView mopContent = (TextView) findViewById(R.id.mopcontent);

        printMopData();

    }

    public void printMopData(View view) {
        printMopData();
    }

    public void printMopData() {
//        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
//        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        String organizationText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganization)
                .orElse(null);
        String organization = String.format("☐ ODDZIAL: %s\n", organizationText);

        String placeText = Optional.ofNullable(foundMop)
                .map(Mop::getPlace)
                .orElse(null);
        String place = String.format("☐ MIEJSCOWOSC: %s\n", placeText);

        String identificationDataText = Optional.ofNullable(foundMop)
                .map(Mop::getIdentificationName)
                .orElse(null);
        String identificationData = String.format("☐ DANE IDENTYFIKACYJNE: %s\n", identificationDataText);

        String categoryText = Optional.ofNullable(foundMop)
                .map(Mop::getCategory)
                .orElse(null);
        String category = String.format("☐ KATEGORIA: %s\n", categoryText);

        Double coordinateX = Optional.ofNullable(foundMop)
                .map(Mop::getCoordinate)
                .map(Coordinate::getX)
                .orElse(null);
        Double coordinateY = Optional.ofNullable(foundMop)
                .map(Mop::getCoordinate)
                .map(Coordinate::getY)
                .orElse(null);
        String coordinates = String.format("☐ WSPOLRZEDNE: %f X\n %f Y", coordinateX, coordinateY);

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

//        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
//        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);


        String roadClassText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getRoadClass)
                .orElse(null);
        String roadClass = String.format("☐ KLASA TECHNICZNA: %s\n", roadClassText);

        String roadNumberText = Optional.ofNullable(foundMop)
                .map(Mop::getRoadNumber)
                .orElse(null);
        String roadNumber = String.format("☐ NUMER: %s\n", roadNumberText);

        String directionText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getDirection)
                .orElse(null);
        String direction = String.format("☐ KIERUNEK: %s\n", directionText);

        SpannableStringBuilder roadClassBold = getSpannableStringBuilder(roadClass, 0, 19);
        SpannableStringBuilder roadNumberBold = getSpannableStringBuilder(roadNumber, 0, 8);
        SpannableStringBuilder directionBold = getSpannableStringBuilder(direction, 0, 11);

        mopContent.setText(roadClassBold
                .append(roadNumberBold)
                .append(directionBold));
    }

    public void printPlaceNumber(View view) {

//        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
//        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        Integer truckPlacesNumber = Optional.ofNullable(foundMop)
                .map(Mop::getTruckPlaces)
                .orElse(null);
        String truckPlaces = String.format("☐ MIEJSCA CIEZAROWE OGOLEM: %d\n", truckPlacesNumber);

        Integer occupiedTruckPlaces = Optional.ofNullable(foundMop)
                .map(Mop::getOccupiedTruckPlaces)
                .orElse(null);
        String freeTruckPlaces = String.format("☐ WOLNE MIEJSCA CIEZAROWE: %d\n\n", truckPlacesNumber - occupiedTruckPlaces);

        Integer passengerPlacesNumber = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getPassengerPlaces)
                .orElse(null);
        String passengerPlaces = String.format("☐ MIEJSCA OSOBOWE OGOLEM: %d\n\n", passengerPlacesNumber);

        Integer coachPlacesNumber = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getCoachPlaces)
                .orElse(null);
        String coachPlaces = String.format("☐ MIEJSCA AUTOBUSOWE: %d", coachPlacesNumber);

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

//        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
//        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        Boolean isGuardedBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getGuarded)
                .orElse(false);
        String isGuarded = String.format("☐ STRZEZONY: %s\n", isGuardedBoolean ? "✔" : "✗");

        Boolean isFencedBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getFenced)
                .orElse(false);
        String isFenced = String.format("☐ OGRODZONY: %s\n", isFencedBoolean ? "✔" : "✗");

        Boolean isMonitoringBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getSecurityCamera)
                .orElse(false);
        String isSecurityCamera = String.format("☐ MONITORING WIDEO: %s\n", isMonitoringBoolean ? "✔" : "✗");

        Boolean isPetroleumBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getPetroleum)
                .orElse(false);
        String isPetroleum = String.format("☐ STACJA BENZYNOWA: %s\n", isPetroleumBoolean ? "✔" : "✗");

        boolean isRestaurantBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getRestaurant)
                .orElse(false);
        String isRestaurant = String.format("☐ RESTAURACJA: %s\n", isRestaurantBoolean ? "✔" : "✗");

        boolean isPlaceToStayBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getPlaceToStay)
                .orElse(false);
        String isPlaceToStay = String.format("☐ MIEJSCA NOCLEGOWE: %s\n", isPlaceToStayBoolean ? "✔" : "✗");

        boolean isToiletBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getToilet)
                .orElse(false);
        String isToilet = String.format("☐ TOALETA: %s\n", isToiletBoolean ? "✔" : "✗");

        boolean isCarwashBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getCarwash)
                .orElse(false);
        String isCarwash = String.format("☐ MYJNIA SAMOCHODOWA: %s\n", isCarwashBoolean ? "✔" : "✗");

        boolean isWorkshopBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getWorkshop)
                .orElse(false);
        String isWorkshop = String.format("☐ WARSZTAT: %s\n", isWorkshopBoolean ? "✔" : "✗");

        boolean isLightingBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getLighting)
                .orElse(false);
        String isLighting = String.format("☐ OSWIETLENIE: %s\n", isLightingBoolean ? "✔" : "✗");

        boolean isElectricChargerBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getElectricCharger)
                .orElse(false);
        String isElectricCharger = String.format("☐ LADOWARKA ELEKTRYCZNA: %s\n", isElectricChargerBoolean ? "✔" : "✗");

        boolean isDangerousCargoBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getDangerousCargo)
                .orElse(false);
        String isDangerousCargo = String.format("☐ NIEBEZPIECZNE LADUNKI: %s\n", isDangerousCargoBoolean ? "✔" : "✗");

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

//        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
//        mop = requestMopDataService.getMopById(this.mopId);
        mopContent = (TextView) findViewById(R.id.mopcontent);

        String organizationInChargeText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganizationInCharge)
                .orElse(null);
        String organizationInCharge = String.format("☐ IMIE I NAZWISKO/NAZWA: %s\n", organizationInChargeText);


        String organizationInChargePhoneText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganizationInChargePhone)
                .orElse(null);
        String organizationInChargePhone = String.format("☐ NUMER TELEFONU: %s\n", organizationInChargePhoneText);

        String organizationInChargeEmailText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganizationInChargeEmail)
                .orElse(null);
        String organizationInChargeEmail = String.format("☐ ADRES EMAIL: %s\n", organizationInChargeEmailText);

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
