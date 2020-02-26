package com.example.truckpark.view.functionality.mop;

import android.content.Intent;
import android.os.Bundle;
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

        foundMop = CurrentMops.getCurrentMopsInstance().getCurrentMopsList()
            .stream()
            .filter(mop -> mop.getId().equals(Long.parseLong(mopId)))
            .findFirst()
            .orElse(null);

        TextView mopName = findViewById(R.id.mopname);
        mopName.setText(foundMop.getIdentificationName());

        mopContent = findViewById(R.id.mopcontent);

        printMopData();

    }

    public void printMopData(View view) {
        printMopData();
    }

    public void printMopData() {

        mopContent = findViewById(R.id.mopcontent);

        String organizationText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganization)
                .orElse("");
        String organizationLabel = getString(R.string.organization_label);
        String organization = String.format("%s: %s\n", organizationLabel, organizationText);

        String placeText = Optional.ofNullable(foundMop)
                .map(Mop::getPlace)
                .orElse("");
        String placeLabel = getString(R.string.place_label);
        String place = String.format("%s: %s\n", placeLabel, placeText);

        String identificationDataText = Optional.ofNullable(foundMop)
                .map(Mop::getIdentificationName)
                .orElse("");
        String identificationDataLabel = getString(R.string.identification_data_label);
        String identificationData = String.format("%s: %s\n", identificationDataLabel, identificationDataText);

        String categoryText = Optional.ofNullable(foundMop)
                .map(Mop::getCategory)
                .orElse("");
        String categoryLabel = getString(R.string.category_label);
        String category = String.format("%s: %s\n", categoryLabel, categoryText);

        Double coordinateX = Optional.ofNullable(foundMop)
                .map(Mop::getCoordinate)
                .map(Coordinate::getX)
                .orElse(0.0);
        Double coordinateY = Optional.ofNullable(foundMop)
                .map(Mop::getCoordinate)
                .map(Coordinate::getY)
                .orElse(0.0);
        String coordinatesLabel = getString(R.string.coordinates_label);
        String coordinates = String.format("%s: %f X\n %f Y", coordinatesLabel, coordinateX, coordinateY);

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

        mopContent = findViewById(R.id.mopcontent);

        String roadClassText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getRoadClass)
                .orElse("");
        String roadClassLabel = getString(R.string.road_class_label);
        String roadClass = String.format("%s: %s\n", roadClassLabel, roadClassText);

        String roadNumberText = Optional.ofNullable(foundMop)
                .map(Mop::getRoadNumber)
                .orElse("");
        String roadNumberLabel = getString(R.string.road_number_label);
        String roadNumber = String.format("%s: %s\n", roadNumberLabel, roadNumberText);

        String directionText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getDirection)
                .orElse("");
        String directionLabel = getString(R.string.direction_label);
        String direction = String.format("%s: %s\n", directionLabel, directionText);

        SpannableStringBuilder roadClassBold = getSpannableStringBuilder(roadClass, 0, 19);
        SpannableStringBuilder roadNumberBold = getSpannableStringBuilder(roadNumber, 0, 8);
        SpannableStringBuilder directionBold = getSpannableStringBuilder(direction, 0, 11);

        mopContent.setText(roadClassBold
                .append(roadNumberBold)
                .append(directionBold));
    }

    public void printPlaceNumber(View view) {

        mopContent = findViewById(R.id.mopcontent);

        Integer truckPlacesNumber = Optional.ofNullable(foundMop)
                .map(Mop::getTruckPlaces)
                .orElse(0);
        String truckPlacesLabel = getString(R.string.truck_places_label);
        String truckPlaces = String.format("%s: %d\n", truckPlacesLabel, truckPlacesNumber);

        Integer occupiedTruckPlaces = Optional.ofNullable(foundMop)
                .map(Mop::getOccupiedTruckPlaces)
                .orElse(0);
        String freeTruckPlacesLabel = getString(R.string.free_truck_places_label);
        String freeTruckPlaces = String.format("%s: %d\n\n", freeTruckPlacesLabel, truckPlacesNumber - occupiedTruckPlaces);

        Integer passengerPlacesNumber = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getPassengerPlaces)
                .orElse(0);
        String passengerPlacesLabel = getString(R.string.passenger_places_label);
        String passengerPlaces = String.format("%s: %d\n\n", passengerPlacesLabel, passengerPlacesNumber);

        Integer coachPlacesNumber = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getCoachPlaces)
                .orElse(0);
        String coachPlacesLabel = getString(R.string.coach_places_label);
        String coachPlaces = String.format("%s: %d", coachPlacesLabel, coachPlacesNumber);

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

        mopContent = findViewById(R.id.mopcontent);

        boolean isGuardedBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getGuarded)
                .orElse(false);
        String isGuardedLabel = getString(R.string.is_guarded_label);
        String isGuarded = String.format("%s: %s\n", isGuardedLabel, isGuardedBoolean ? "✔" : "✗");

        boolean isFencedBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getFenced)
                .orElse(false);
        String isFencedLabel = getString(R.string.is_fenced_label);
        String isFenced = String.format("%s: %s\n", isFencedLabel, isFencedBoolean ? "✔" : "✗");

        boolean isMonitoringBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getSecurityCamera)
                .orElse(false);
        String isMonitoringLabel = getString(R.string.is_monitoring_label);
        String isSecurityCamera = String.format("%s: %s\n", isMonitoringLabel, isMonitoringBoolean ? "✔" : "✗");

        boolean isPetroleumBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getPetroleum)
                .orElse(false);
        String isPetroleumLabel = getString(R.string.is_petroleum_label);
        String isPetroleum = String.format("%s: %s\n", isPetroleumLabel, isPetroleumBoolean ? "✔" : "✗");

        boolean isRestaurantBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getRestaurant)
                .orElse(false);
        String isRestaurantLabel = getString(R.string.is_restaurant_label);
        String isRestaurant = String.format("%s: %s\n", isRestaurantLabel, isRestaurantBoolean ? "✔" : "✗");

        boolean isPlaceToStayBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getPlaceToStay)
                .orElse(false);
        String isPlaceToStayLabel = getString(R.string.is_place_to_stay_label);
        String isPlaceToStay = String.format("%s: %s\n", isPlaceToStayLabel, isPlaceToStayBoolean ? "✔" : "✗");

        boolean isToiletBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getToilet)
                .orElse(false);
        String isToiletLabel = getString(R.string.is_toilet_label);
        String isToilet = String.format("%s: %s\n", isToiletLabel, isToiletBoolean ? "✔" : "✗");

        boolean isCarwashBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getCarwash)
                .orElse(false);
        String isCarwashLabel = getString(R.string.is_carwash_label);
        String isCarwash = String.format("%s: %s\n", isCarwashLabel, isCarwashBoolean ? "✔" : "✗");

        boolean isWorkshopBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getWorkshop)
                .orElse(false);
        String isWorkshopLabel = getString(R.string.is_workshop_label);
        String isWorkshop = String.format("%s: %s\n", isWorkshopLabel, isWorkshopBoolean ? "✔" : "✗");

        boolean isLightingBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getLighting)
                .orElse(false);
        String isLightingLabel = getString(R.string.is_lighting_label);
        String isLighting = String.format("%s: %s\n", isLightingLabel, isLightingBoolean ? "✔" : "✗");

        boolean isElectricChargerBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getElectricCharger)
                .orElse(false);
        String isElectricChargerLabel = getString(R.string.is_electric_charger);
        String isElectricCharger = String.format("%s: %s\n", isElectricChargerLabel, isElectricChargerBoolean ? "✔" : "✗");

        boolean isDangerousCargoBoolean = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getDangerousCargo)
                .orElse(false);
        String isDangerousLabel = getString(R.string.is_dangerous_label);
        String isDangerousCargo = String.format("%s: %s\n", isDangerousLabel, isDangerousCargoBoolean ? "✔" : "✗");

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

        mopContent = findViewById(R.id.mopcontent);

        String organizationInChargeText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganizationInCharge)
                .orElse("");
        String organizationInChargeLabel = getString(R.string.organization_in_charge_label);
        String organizationInCharge = String.format("%s: %s\n", organizationInChargeLabel, organizationInChargeText);


        String organizationInChargePhoneText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganizationInChargePhone)
                .orElse("");
        String organizationInChargePhoneLabel = getString(R.string.organization_in_charge_phone_label);
        String organizationInChargePhone = String.format("%s: %s\n", organizationInChargePhoneLabel, organizationInChargePhoneText);

        String organizationInChargeEmailText = Optional.ofNullable(foundMop)
                .map(Mop::getExtendedMopData)
                .map(ExtendedMopData::getOrganizationInChargeEmail)
                .orElse("");
        String organizationInChargeEmailLabel = getString(R.string.organization_in_charge_email_label);
        String organizationInChargeEmail = String.format("%s: %s\n", organizationInChargeEmailLabel, organizationInChargeEmailText);

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
