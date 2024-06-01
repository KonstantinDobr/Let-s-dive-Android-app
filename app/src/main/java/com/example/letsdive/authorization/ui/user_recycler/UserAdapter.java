package com.example.letsdive.authorization.ui.user_recycler;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.RelationshipRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.GetUserByUsernameUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.UserRelationshipEntity;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.domain.relationship.AddUserRelationshipUseCase;
import com.example.letsdive.authorization.domain.relationship.GetByFirstIdUseCase;
import com.example.letsdive.databinding.ItemUserBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Set;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<ItemUserEntity> entityList;
    private Context context;

    private final FullUserEntity mainUser;

    private final GetUserByUsernameUseCase getUserByUsernameUseCase = new GetUserByUsernameUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final AddUserRelationshipUseCase addUserRelationshipUseCase = new AddUserRelationshipUseCase(
            RelationshipRepositoryImpl.getInstance()
    );
    private final GetByFirstIdUseCase getByFirstIdUseCase = new GetByFirstIdUseCase(
            RelationshipRepositoryImpl.getInstance()
    );

    public UserAdapter(List<ItemUserEntity> entityList, Context context, FullUserEntity mainUser) {
        this.entityList = entityList;
        this.context = context;
        this.mainUser = mainUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(
                LayoutInflater.from(parent.getContext())
        );

        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(entityList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemUserEntity user = entityList.get(position);

                getByFirstIdUseCase.execute(
                        mainUser.getId(),
                        setStatus -> {
                            Set<UserRelationshipEntity> relationships = setStatus.getValue();
                            boolean is_both = false;
                            if (relationships != null && !relationships.isEmpty()) {
                                for (UserRelationshipEntity relationship : relationships) {
                                    if (relationship.getSecondId().equals(user.getId())) {
                                        getByFirstIdUseCase.execute(
                                                user.getId(),
                                                setStatus1 -> {
                                                    Set<UserRelationshipEntity> relationships1 = setStatus1.getValue();
                                                    boolean is_both1 = false;
                                                    if (relationships1 != null && !relationships1.isEmpty()) {
                                                        for (UserRelationshipEntity relationship1 : relationships1) {
                                                            if (relationship1.getSecondId().equals(mainUser.getId())) {
                                                                openInputDialog(user, "Вы уже друзья");
                                                                is_both1 = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (!is_both1) {
                                                        openInputDialog(user, "Заявка отправлена");
                                                    }
                                                }
                                        );
                                        is_both = true;
                                        break;
                                    }
                                }
                            }

                            if (!is_both) {
                                getByFirstIdUseCase.execute(
                                        user.getId(),
                                        setStatus1 -> {
                                            Set<UserRelationshipEntity> relationships1 = setStatus1.getValue();
                                            boolean is_both1 = false;
                                            if (relationships1 != null && !relationships1.isEmpty()) {
                                                for (UserRelationshipEntity relationship1 : relationships1) {
                                                    if (relationship1.getSecondId().equals(mainUser.getId())) {
                                                        openInputDialog(user, "Принять заявку");
                                                        is_both1 = true;
                                                        break;
                                                    }
                                                }
                                            }

                                            if (!is_both1) {
                                                openInputDialog(user, "Добавить в друзья");
                                            }
                                        }
                                );
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    private void openInputDialog(ItemUserEntity item, String state) {

        getUserByUsernameUseCase.execute(
                item.getUsername(),
                userEntityStatus -> {
                    FullUserEntity user = userEntityStatus.getValue();

                    final Dialog dialogWindow = new Dialog(context);
                    dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogWindow.setCancelable(true);
                    dialogWindow.setContentView(R.layout.dialog_user_card);

                    TextView usernameTextView = (TextView) dialogWindow.findViewById(R.id.tv_username);
                    TextView emailTextView = (TextView) dialogWindow.findViewById(R.id.tv_email);
                    TextView infoTextView = (TextView) dialogWindow.findViewById(R.id.tv_info);

                    usernameTextView.setText(user.getUsername());
                    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                        emailTextView.setText(user.getEmail());
                    }
                    if (user.getInformation() != null && !user.getInformation().isEmpty()) {
                        infoTextView.setText(user.getInformation());
                    }

                    Button addFriendButton = (Button) dialogWindow.findViewById(R.id.btn_addFriend);
                    addFriendButton.setText(state);
                    addFriendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (state.equals("Вы уже друзья")) {
                                Toast.makeText(context, "You are already friends", Toast.LENGTH_SHORT).show();
                            } else if (state.equals("Заявка отправлена")) {
                                Toast.makeText(context, "The application has already been sent", Toast.LENGTH_SHORT).show();
                            } else {
                                addUserRelationshipUseCase.execute(
                                        mainUser.getId(),
                                        user.getId(),
                                        userRelationshipEntityStatus -> {

                                        }
                                );
                                dialogWindow.dismiss();
                            }
                            }
                    });

                    dialogWindow.show();
                });
    }

}
