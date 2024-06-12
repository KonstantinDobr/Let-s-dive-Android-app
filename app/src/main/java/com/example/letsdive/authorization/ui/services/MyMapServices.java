package com.example.letsdive.authorization.ui.services;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.PlaceRepositoryImpl;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.RelationshipRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.AddPlaceToUserUseCase;
import com.example.letsdive.authorization.domain.AddRecordToUserUseCase;
import com.example.letsdive.authorization.domain.DeletePlaceFromUserUseCase;
import com.example.letsdive.authorization.domain.DeleteRecordFromUserUseCase;
import com.example.letsdive.authorization.domain.GetUserByIdUseCase;
import com.example.letsdive.authorization.domain.GetUserByUsernameUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.UserRelationshipEntity;
import com.example.letsdive.authorization.domain.place.AddPlaceUseCase;
import com.example.letsdive.authorization.domain.place.DeletePlaceByIdUseCase;
import com.example.letsdive.authorization.domain.place.GetByPlaceNameUseCase;
import com.example.letsdive.authorization.domain.place.UpdatePlaceUseCase;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.domain.record.DeleteRecordByIdUseCase;
import com.example.letsdive.authorization.domain.record.GetRecordByIdUseCase;
import com.example.letsdive.authorization.domain.record.UpdateRecordUseCase;
import com.example.letsdive.authorization.domain.relationship.GetByFirstIdUseCase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MyMapServices implements OnMapReadyCallback {

    private final Context context;
    private final FullUserEntity user;
    private List<Pair<String, PlaceEntity>> friendPlaces = new ArrayList<>();

    private final AddPlaceUseCase addPlaceUseCase = new AddPlaceUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final AddPlaceToUserUseCase addPlaceToUserUseCase = new AddPlaceToUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final UpdatePlaceUseCase updatePlaceUseCase = new UpdatePlaceUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final UpdateRecordUseCase updateRecordUseCase = new UpdateRecordUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final GetByPlaceNameUseCase getByPlaceNameUseCase = new GetByPlaceNameUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final GetRecordByIdUseCase getRecordByIdUseCase = new GetRecordByIdUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final DeletePlaceFromUserUseCase deletePlaceFromUserUseCase = new DeletePlaceFromUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final DeletePlaceByIdUseCase deletePlaceByIdUseCase = new DeletePlaceByIdUseCase(
            PlaceRepositoryImpl.getInstance()
    );
    private final DeleteRecordFromUserUseCase deleteRecordFromUserUseCase = new DeleteRecordFromUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final DeleteRecordByIdUseCase deleteRecordByIdUseCase = new DeleteRecordByIdUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final GetByFirstIdUseCase getByFirstIdUseCase = new GetByFirstIdUseCase(
            RelationshipRepositoryImpl.getInstance()
    );
    private final GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final AddRecordUseCase addRecordUseCase = new AddRecordUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final AddRecordToUserUseCase addRecordToUserUseCase = new AddRecordToUserUseCase(
            UserRepositoryImpl.getInstance()
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
            Objects.requireNonNull(googleMap.addMarker(new MarkerOptions().
                            position(new LatLng(place.getLatitude(), place.getLongitude()))
                    ))
                    .setTitle(place.getPlaceName());
        }

        getByFirstIdUseCase.execute(
                user.getId(),
                setStatus -> {
                    Set<UserRelationshipEntity> relationshipEntities = setStatus.getValue();
                    if (relationshipEntities != null && !relationshipEntities.isEmpty()) {
                        for (UserRelationshipEntity relationship : relationshipEntities) {
                            getByFirstIdUseCase.execute(
                                    relationship.getSecondId(),
                                    setStatus1 -> {
                                        Set<UserRelationshipEntity> relationshipEntities1 = setStatus1.getValue();
                                        if (relationshipEntities1 != null && !relationshipEntities1.isEmpty()) {
                                            for (UserRelationshipEntity relationship1 : relationshipEntities1) {
                                                if (relationship1.getSecondId().equals(user.getId())) {
                                                    getUserByIdUseCase.execute(
                                                            relationship1.getFirstId(),
                                                            userEntityStatus -> {
                                                                FullUserEntity friend = userEntityStatus.getValue();
                                                                for (PlaceEntity place : friend.getPlaces()) {
                                                                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude()));
//                                                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.friends));
                                                                    markerOptions.title(place.getPlaceName());
                                                                    friendPlaces.add(new Pair<>(friend.getId(), place));
                                                                    googleMap.addMarker(markerOptions);
                                                                }
                                                            }
                                                    );
                                                }
                                            }
                                        }
                                    }
                            );
                        }
                    }
                }
                );

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
        EditText depthEditText = (EditText) dialogWindow.findViewById(R.id.et_depth);
        EditText infoEditText = (EditText) dialogWindow.findViewById(R.id.et_info);

        Button confirmButton = (Button) dialogWindow.findViewById(R.id.btn_confirm);
        Button cancelButton = (Button) dialogWindow.findViewById(R.id.btn_cancel);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (placeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Place name cannot be null", Toast.LENGTH_SHORT).show();
                } else if (depthEditText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Depth cannot be null", Toast.LENGTH_SHORT).show();
                } else if (infoEditText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Information cannot be null", Toast.LENGTH_SHORT).show();
                } else {
                        getUserByIdUseCase.execute(
                                user.getId(),
                                userEntityStatus -> {
                                    boolean new_Name = true;
                                    for (RecordEntity record : userEntityStatus.getValue().getRecords()) {
                                        if (record.getPlaceName().equals(placeEditText.getText().toString())) {
                                            Toast.makeText(context, "Marker with same name is already exist", Toast.LENGTH_SHORT).show();
                                            new_Name = false;
                                            break;
                                        }
                                    }
                                    if (new_Name) {
                                        try {
                                            long depth = Long.valueOf(depthEditText.getText().toString());
                                            if (depth <= 0) {
                                                Toast.makeText(context, "Depth must be greater than zero", Toast.LENGTH_SHORT).show();
                                            } else {
                                                addRecordUseCase.execute(
                                                        placeEditText.getText().toString(),
                                                        "",
                                                        "",
                                                        "",
                                                        infoEditText.getText().toString(),
                                                        depth,
                                                        recordEntityStatus -> {
                                                            RecordEntity record = recordEntityStatus.getValue();
                                                            addRecordToUserUseCase.execute(
                                                                    user.getId(),
                                                                    record.getId(),
                                                                    recordEntityStatus1 -> {
                                                                        addPlaceUseCase.execute(
                                                                                placeEditText.getText().toString(),
                                                                                infoEditText.getText().toString(),
                                                                                latitude,
                                                                                longitude,
                                                                                recordEntityStatus.getValue().getId(),
                                                                                recordEntityStatus.getValue().getDepth(),
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
                                                                    }
                                                            );
                                                        }
                                                );
                                                dialogWindow.dismiss();
                                            }
                                        } catch (RuntimeException e) {
                                            Toast.makeText(context, "Depth must be integer", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                        );
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
        getUserByIdUseCase.execute(
                user.getId(),
                userEntityStatus2 -> {
                    boolean is_this_user = false;
                    for (PlaceEntity placeEntity : userEntityStatus2.getValue().getPlaces()) {
                        if (placeEntity.getPlaceName().equals(inputMarker.getTitle())) {
                            is_this_user = true;
                            getByPlaceNameUseCase.execute(
                                    inputMarker.getTitle(),
                                    user.getId(),
                                    placeEntityStatus -> {
                                        PlaceEntity place = placeEntityStatus.getValue();
                                        final Dialog dialogWindow = new Dialog(context);
                                        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialogWindow.setCancelable(true);
                                        dialogWindow.setContentView(R.layout.dialog_place_info_window);

                                        EditText placeEditText = (EditText) dialogWindow.findViewById(R.id.et_place);
                                        EditText depthEditText = (EditText) dialogWindow.findViewById(R.id.et_depth);
                                        EditText infoEditText = (EditText) dialogWindow.findViewById(R.id.et_info);

                                        placeEditText.setText(place.getPlaceName());
                                        depthEditText.setText(String.valueOf(place.getDepth()));
                                        infoEditText.setText(place.getInformation());

                                        placeEditText.setFocusable(false);
                                        placeEditText.setFocusableInTouchMode(false);
                                        placeEditText.setClickable(false);

                                        depthEditText.setFocusable(false);
                                        depthEditText.setFocusableInTouchMode(false);
                                        depthEditText.setClickable(false);

                                        infoEditText.setFocusable(false);
                                        infoEditText.setFocusableInTouchMode(false);
                                        infoEditText.setClickable(false);

                                        Button changeButton = (Button) dialogWindow.findViewById(R.id.btn_change);
                                        Button deleteButton = (Button) dialogWindow.findViewById(R.id.btn_delete);
                                        changeButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                getUserByIdUseCase.execute(
                                                        user.getId(),
                                                        userEntityStatus -> {
                                                            FullUserEntity mainUser = userEntityStatus.getValue();
                                                            Set<PlaceEntity> placeEntities = mainUser.getPlaces();

                                                            boolean is_yours = false;

                                                            for (PlaceEntity place1 : placeEntities) {
                                                                if (place.getId().equals(place1.getId())) {
                                                                    is_yours = true;
                                                                    if (changeButton.getText().equals("Изменить")) {

                                                                        placeEditText.setFocusable(true);
                                                                        placeEditText.setFocusableInTouchMode(true);
                                                                        placeEditText.setClickable(true);

                                                                        depthEditText.setFocusable(true);
                                                                        depthEditText.setFocusableInTouchMode(true);
                                                                        depthEditText.setClickable(true);

                                                                        infoEditText.setFocusable(true);
                                                                        infoEditText.setFocusableInTouchMode(true);
                                                                        infoEditText.setClickable(true);

                                                                        changeButton.setText("Сохранить");
                                                                    } else {

                                                                        String placeNameText = placeEditText.getText().toString();
                                                                        String infoText = infoEditText.getText().toString();
                                                                        String depthText = depthEditText.getText().toString();

                                                                        if (placeNameText.isEmpty()) {
                                                                            Toast.makeText(context, "Place name cannot be null", Toast.LENGTH_SHORT).show();
                                                                        } else if (depthText.isEmpty()) {
                                                                            Toast.makeText(context, "Depth cannot be null", Toast.LENGTH_SHORT).show();
                                                                        } else if (infoText.isEmpty()) {
                                                                            Toast.makeText(context, "Information cannot be null", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            boolean new_Name = true;
                                                                            for (RecordEntity record : userEntityStatus.getValue().getRecords()) {
                                                                                if (record.getPlaceName().equals(placeEditText.getText().toString()) && !record.getId().equals(place.getRecordId())) {
                                                                                    Toast.makeText(context, "Marker with same name is already exist", Toast.LENGTH_SHORT).show();
                                                                                    new_Name = false;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            if (new_Name) {
                                                                                try {
                                                                                    long depth = Long.valueOf(depthEditText.getText().toString());
                                                                                    if (depth <= 0) {
                                                                                        Toast.makeText(context, "Depth must be greater than zero", Toast.LENGTH_SHORT).show();
                                                                                    } else {
                                                                                        getRecordByIdUseCase.execute(
                                                                                                place.getRecordId(),
                                                                                                recordEntityStatus -> {
                                                                                                    if (recordEntityStatus.getValue() == null) return;
                                                                                                    RecordEntity oldRecord = recordEntityStatus.getValue();
                                                                                                    updateRecordUseCase.execute(
                                                                                                            place.getRecordId(),
                                                                                                            placeNameText,
                                                                                                            oldRecord.getDate(),
                                                                                                            oldRecord.getStartDate(),
                                                                                                            oldRecord.getEndDate(),
                                                                                                            infoText,
                                                                                                            depth,
                                                                                                            recordEntityStatus1 -> {

                                                                                                            }
                                                                                                    );
                                                                                                }
                                                                                        );

                                                                                        updatePlaceUseCase.execute(
                                                                                                place.getId(),
                                                                                                placeNameText,
                                                                                                infoText,
                                                                                                place.getLatitude(),
                                                                                                place.getLongitude(),
                                                                                                place.getRecordId(),
                                                                                                depth,
                                                                                                placeEntityStatus -> {
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
                                                                                } catch (RuntimeException e) {
                                                                                    Toast.makeText(context, "Depth must be integer", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            if (!is_yours) {
                                                                Toast.makeText(context, "It is not your record", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                );



                                            }
                                        });

                                        deleteButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                getUserByIdUseCase.execute(
                                                        user.getId(),
                                                        userEntityStatus1 -> {
                                                            FullUserEntity mainUser = userEntityStatus1.getValue();
                                                            Set<PlaceEntity> placeEntities = mainUser.getPlaces();

                                                            boolean is_yours = false;

                                                            for (PlaceEntity place1 : placeEntities) {
                                                                if (place1.getId().equals(place.getId())) {
                                                                    is_yours = true;
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
                                                                        deleteRecordFromUserUseCase.execute(
                                                                                user.getId(),
                                                                                place.getRecordId(),
                                                                                userEntityStatus -> {}
                                                                        );

                                                                        deleteRecordByIdUseCase.execute(
                                                                                place.getRecordId(),
                                                                                voidStatus -> {}
                                                                        );

                                                                        inputMarker.setVisible(false);
                                                                        inputMarker.remove();

                                                                        dialogWindow.dismiss();
                                                                    }
                                                                }
                                                            }

                                                            if (!is_yours) {
                                                                Toast.makeText(context, "It is not your record", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                );



                                            }
                                        });

                                        dialogWindow.show();
                                    });
                        }
                    }
                    if (!is_this_user) {
                        for (Pair<String, PlaceEntity> friendPlacePair : friendPlaces) {
                            String friendId = friendPlacePair.first;
                            PlaceEntity friendPlace = friendPlacePair.second;
                            if (friendPlace.getPlaceName().equals(inputMarker.getTitle())) {
                                getByPlaceNameUseCase.execute(
                                        inputMarker.getTitle(),
                                        friendId,
                                        placeEntityStatus -> {
                                            PlaceEntity place = placeEntityStatus.getValue();
                                            final Dialog dialogWindow = new Dialog(context);
                                            dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialogWindow.setCancelable(true);
                                            dialogWindow.setContentView(R.layout.dialog_place_info_window);

                                            EditText placeEditText = (EditText) dialogWindow.findViewById(R.id.et_place);
                                            EditText depthEditText = (EditText) dialogWindow.findViewById(R.id.et_depth);
                                            EditText infoEditText = (EditText) dialogWindow.findViewById(R.id.et_info);

                                            placeEditText.setText(place.getPlaceName());
                                            depthEditText.setText(String.valueOf(place.getDepth()));
                                            infoEditText.setText(place.getInformation());

                                            placeEditText.setFocusable(false);
                                            placeEditText.setFocusableInTouchMode(false);
                                            placeEditText.setClickable(false);

                                            depthEditText.setFocusable(false);
                                            depthEditText.setFocusableInTouchMode(false);
                                            depthEditText.setClickable(false);

                                            infoEditText.setFocusable(false);
                                            infoEditText.setFocusableInTouchMode(false);
                                            infoEditText.setClickable(false);

                                            Button changeButton = (Button) dialogWindow.findViewById(R.id.btn_change);
                                            Button deleteButton = (Button) dialogWindow.findViewById(R.id.btn_delete);
                                            changeButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    getUserByIdUseCase.execute(
                                                            user.getId(),
                                                            userEntityStatus -> {
                                                                FullUserEntity mainUser = userEntityStatus.getValue();
                                                                Set<PlaceEntity> placeEntities = mainUser.getPlaces();

                                                                boolean is_yours = false;

                                                                for (PlaceEntity place1 : placeEntities) {
                                                                    if (place.getId().equals(place1.getId())) {
                                                                        is_yours = true;
                                                                        if (changeButton.getText().equals("Изменить")) {

                                                                            placeEditText.setFocusable(true);
                                                                            placeEditText.setFocusableInTouchMode(true);
                                                                            placeEditText.setClickable(true);

                                                                            depthEditText.setFocusable(true);
                                                                            depthEditText.setFocusableInTouchMode(true);
                                                                            depthEditText.setClickable(true);

                                                                            infoEditText.setFocusable(true);
                                                                            infoEditText.setFocusableInTouchMode(true);
                                                                            infoEditText.setClickable(true);

                                                                            changeButton.setText("Сохранить");
                                                                        } else {

                                                                            String placeNameText = placeEditText.getText().toString();
                                                                            String infoText = infoEditText.getText().toString();
                                                                            String depthText = depthEditText.getText().toString();

                                                                            if (placeNameText.isEmpty()) {
                                                                                Toast.makeText(context, "Place name cannot be null", Toast.LENGTH_SHORT).show();
                                                                            } else if (depthText.isEmpty()) {
                                                                                Toast.makeText(context, "Depth cannot be null", Toast.LENGTH_SHORT).show();
                                                                            } else if (infoText.isEmpty()) {
                                                                                Toast.makeText(context, "Information cannot be null", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                boolean new_Name = true;
                                                                                for (RecordEntity record : userEntityStatus.getValue().getRecords()) {
                                                                                    if (record.getPlaceName().equals(placeEditText.getText().toString()) && !record.getId().equals(place.getRecordId())) {
                                                                                        Toast.makeText(context, "Marker with same name is already exist", Toast.LENGTH_SHORT).show();
                                                                                        new_Name = false;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                if (new_Name) {
                                                                                    try {
                                                                                        long depth = Long.valueOf(depthEditText.getText().toString());
                                                                                        if (depth <= 0) {
                                                                                            Toast.makeText(context, "Depth must be greater than zero", Toast.LENGTH_SHORT).show();
                                                                                        } else {
                                                                                            getRecordByIdUseCase.execute(
                                                                                                    place.getRecordId(),
                                                                                                    recordEntityStatus -> {
                                                                                                        RecordEntity oldRecord = recordEntityStatus.getValue();
                                                                                                        updateRecordUseCase.execute(
                                                                                                                place.getRecordId(),
                                                                                                                placeNameText,
                                                                                                                oldRecord.getDate(),
                                                                                                                oldRecord.getStartDate(),
                                                                                                                oldRecord.getEndDate(),
                                                                                                                infoText,
                                                                                                                depth,
                                                                                                                recordEntityStatus1 -> {

                                                                                                                }
                                                                                                        );
                                                                                                    }
                                                                                            );

                                                                                            updatePlaceUseCase.execute(
                                                                                                    place.getId(),
                                                                                                    placeNameText,
                                                                                                    infoText,
                                                                                                    place.getLatitude(),
                                                                                                    place.getLongitude(),
                                                                                                    place.getRecordId(),
                                                                                                    depth,
                                                                                                    placeEntityStatus -> {
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
                                                                                    } catch (RuntimeException e) {
                                                                                        Toast.makeText(context, "Depth must be integer", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                if (!is_yours) {
                                                                    Toast.makeText(context, "It is not your record", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                    );



                                                }
                                            });

                                            deleteButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    getUserByIdUseCase.execute(
                                                            user.getId(),
                                                            userEntityStatus1 -> {
                                                                FullUserEntity mainUser = userEntityStatus1.getValue();
                                                                Set<PlaceEntity> placeEntities = mainUser.getPlaces();

                                                                boolean is_yours = false;

                                                                for (PlaceEntity place1 : placeEntities) {
                                                                    if (place1.getId().equals(place.getId())) {
                                                                        is_yours = true;
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
                                                                            deleteRecordFromUserUseCase.execute(
                                                                                    user.getId(),
                                                                                    place.getRecordId(),
                                                                                    userEntityStatus -> {}
                                                                            );

                                                                            deleteRecordByIdUseCase.execute(
                                                                                    place.getRecordId(),
                                                                                    voidStatus -> {}
                                                                            );

                                                                            inputMarker.setVisible(false);
                                                                            inputMarker.remove();

                                                                            dialogWindow.dismiss();
                                                                        }
                                                                    }
                                                                }

                                                                if (!is_yours) {
                                                                    Toast.makeText(context, "It is not your record", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                    );



                                                }
                                            });

                                            dialogWindow.show();
                                        });
                            }
                        }
                    }
                }
        );
    }
}
