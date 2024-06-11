package com.example.letsdive.authorization.ui.main_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.GetUsersListUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.ui.user_recycler.UserAdapter;
import com.example.letsdive.authorization.ui.utils.SpacingItemDecorator;
import com.example.letsdive.databinding.FragmentFriendsBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;
    private final FullUserEntity user;

    private final Context context;

    private final GetUsersListUseCase getUsersListUseCase = new GetUsersListUseCase(
            UserRepositoryImpl.getInstance()
    );

    public FriendsFragment(Context context, FullUserEntity user) {
        super(R.layout.fragment_friends);
        this.user = user;
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFriendsBinding.bind(view);

        getUsersListUseCase.execute(
                listStatus -> {
                    List<ItemUserEntity> users = listStatus.getValue();
                    for (ItemUserEntity item : users) {
                        if (item.getId().equals(user.getId())) {
                            users.remove(item);
                            break;
                        }
                    }
                    UserAdapter adapter = new UserAdapter(users, context, user);
                    SpacingItemDecorator itemDecorator = new SpacingItemDecorator(35, 35, 35);
                    binding.rvUsers.addItemDecoration(itemDecorator);
                    binding.rvUsers.setAdapter(adapter);
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
