package com.example.letsdive.authorization.ui.services;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.PlaceRepositoryImpl;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.AddPlaceToUserUseCase;
import com.example.letsdive.authorization.domain.DeletePlaceFromUserUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.place.AddPlaceUseCase;
import com.example.letsdive.authorization.domain.place.DeletePlaceByIdUseCase;
import com.example.letsdive.authorization.domain.place.GetByPlaceNameUseCase;
import com.example.letsdive.authorization.domain.place.UpdatePlaceUseCase;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Set;

public class MyMapServices implements OnMapReadyCallback {

    private final Context context;
    private final FullUserEntity user;

    private final AddPlaceUseCase addPlaceUseCase = new AddPlaceUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final AddPlaceToUserUseCase addPlaceToUserUseCase = new AddPlaceToUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final UpdatePlaceUseCase updatePlaceUseCase = new UpdatePlaceUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final GetByPlaceNameUseCase getByPlaceNameUseCase = new GetByPlaceNameUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final DeletePlaceFromUserUseCase deletePlaceFromUserUseCase = new DeletePlaceFromUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final DeletePlaceByIdUseCase deletePlaceByIdUseCase = new DeletePlaceByIdUseCase(
            PlaceRepositoryImpl.getInstance()
    );


    public MyMapServices(Context context, FullUserEntity user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                openInputDialog(latLng.latitude, latLng.longitude, googleMap);
            }
        });

        for (PlaceEntity place : user.getPlaces()) {
            googleMap.addMarker(new MarkerOptions().
                    position(new LatLng(place.getLatitude(), place.getLongitude())))
                    .setTitle(place.getPlaceName());
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                openEditDialog(marker, googleMap);
                return false;
            }
        });
    }

    private void openInputDialog(double latitude, double longitude, GoogleMap googleMap) {
        final Dialog dialogWindow = new Dialog(context);
        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setCancelable(true);
        dialogWindow.setContentView(R.layout.dialog_place_window);

        EditText placeEditText = (EditText) dialogWindow.findViewById(R.id.et_place);
        EditText infoEditText = (EditText) dialogWindow.findViewById(R.id.et_info);

        Button confirmButton = (Button) dialogWindow.findViewById(R.id.btn_confirm);
        Button cancelButton = (Button) dialogWindow.findViewById(R.id.btn_cancel);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (placeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Place name cannot be null", Toast.LENGTH_SHORT).show();
                } else if (infoEditText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Information cannot be null", Toast.LENGTH_SHORT).show();
                } else {
                    addPlaceUseCase.execute(
                            placeEditText.getText().toString(),
                            infoEditText.getText().toString(),
                            latitude,
                            longitude,
                            placeEntityStatus -> {
                                PlaceEntity place = placeEntityStatus.getValue();
                                googleMap.addMarker(new MarkerOptions().
                                                position(new LatLng(place.getLatitude(), place.getLongitude())))
                                        .setTitle(place.getPlaceName());
                                addPlaceToUserUseCase.execute(
                                        user.getId(),
                                        place.getId(),
                                        fullUserEntityStatus -> {
                                    Set<PlaceEntity> places = user.getPlaces();
                                    places.add(place);
                                });
                            }
                    );
                    dialogWindow.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindow.dismiss();
            }
        });

        dialogWindow.show();
    }

    private void openEditDialog(Marker inputMarker, GoogleMap googleMap) {
        getByPlaceNameUseCase.execute(
                inputMarker.getTitle(),
                placeEntityStatus -> {
                    PlaceEntity place = placeEntityStatus.getValue();
                    final Dialog dialogWindow = new Dialog(context);
                    dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogWindow.setCancelable(true);
                    dialogWindow.setContentView(R.layout.dialog_place_info_window);

                    EditText placeEditText = (EditText) dialogWindow.findViewById(R.id.et_place);
                    EditText infoEditText = (EditText) dialogWindow.findViewById(R.id.et_info);

                    placeEditText.setText(place.getPlaceName());
                    infoEditText.setText(place.getInformation());

                    placeEditText.setFocusable(false);
                    placeEditText.setFocusableInTouchMode(false);
                    placeEditText.setClickable(false);

                    infoEditText.setFocusable(false);
                    infoEditText.setFocusableInTouchMode(false);
                    infoEditText.setClickable(false);

                    Button changeButton = (Button) dialogWindow.findViewById(R.id.btn_change);
                    Button deleteButton = (Button) dialogWindow.findViewById(R.id.btn_delete);
                    changeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (changeButton.getText().equals("Изменить")) {

                                placeEditText.setFocusable(true);
                                placeEditText.setFocusableInTouchMode(true);
                                placeEditText.setClickable(true);

                                infoEditText.setFocusable(true);
                                infoEditText.setFocusableInTouchMode(true);
                                infoEditText.setClickable(true);

                                changeButton.setText("Сохранить");
                            } else {

                                String placeNameText = placeEditText.getText().toString();
                                String infoText = infoEditText.getText().toString();

                                if (placeNameText.isEmpty()) {
                                    Toast.makeText(context, "Place name cannot be null", Toast.LENGTH_SHORT).show();
                                } else if (infoText.isEmpty()) {
                                    Toast.makeText(context, "Information cannot be null", Toast.LENGTH_SHORT).show();
                                } else {

                                    updatePlaceUseCase.execute(
                                            place.getId(),
                                            placeNameText,
                                            infoText,
                                            place.getLatitude(),
                                            place.getLongitude(),
                                            placeEntityStatus -> {
                                                PlaceEntity newPlace = placeEntityStatus.getValue();
                                                inputMarker.setVisible(false);
                                                inputMarker.remove();
                                                googleMap.addMarker(new MarkerOptions().
                                                                position(new LatLng(place.getLatitude(), place.getLongitude())))
                                                        .setTitle(placeNameText);
                                            }
                                    );

                                    changeButton.setText("Изменить");
                                    dialogWindow.dismiss();
                                }
                            }
                        }
                    });

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (changeButton.getText().equals("Изменить")) {
                                deletePlaceFromUserUseCase.execute(
                                        user.getId(),
                                        place.getId(),
                                        userEntityStatus -> {}
                                        );

                                deletePlaceByIdUseCase.execute(
                                        place.getId(),
                                        placeEntityStatus -> {}
                                );

                                inputMarker.setVisible(false);
                                inputMarker.remove();

                                dialogWindow.dismiss();
                            }

                        }
                    });

                    dialogWindow.show();
                });
    }
}