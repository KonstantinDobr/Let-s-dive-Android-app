package com.example.letsdive.authorization.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.AccountDto;
import com.example.letsdive.authorization.data.dto.UserDto;
import com.example.letsdive.authorization.data.network.RetrofitFactory;
import com.example.letsdive.authorization.data.source.CredentialsDataSource;
import com.example.letsdive.authorization.data.source.UserApi;
import com.example.letsdive.authorization.data.utils.CallToConsumer;
import com.example.letsdive.authorization.domain.UserRepository;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.sign.SignUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import retrofit2.Call;

public class UserRepositoryImpl implements UserRepository, SignUserRepository {
    private static UserRepositoryImpl INSTANCE;
    private UserApi userApi = RetrofitFactory.getInstance().getUserApi();
    private final CredentialsDataSource credentialsDataSource = CredentialsDataSource.getInstance();

    private UserRepositoryImpl() {}

    public static synchronized UserRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public void getAllUsers(@NonNull Consumer<Status<List<ItemUserEntity>>> callback) {
        userApi.getAll().enqueue(new CallToConsumer<>(
                callback,
                usersDto -> {
                    ArrayList<ItemUserEntity> result = new ArrayList<>(usersDto.size());
                    for (UserDto user : usersDto) {
                        final String id = user.id;
                        final String username = user.username;
                        if (id != null && username != null) {
                            result.add(new ItemUserEntity(id, username));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void getUser(@NonNull String id, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.getById(id).enqueue(new CallToConsumer<>(
                callback,
                user -> {
                    final String resultId = user.id;
                    final String username = user.username;
                    if (resultId != null && username != null) {
                        return new FullUserEntity(
                                resultId,
                                username,
                                user.photoUrl,
                                user.records
                        );
                    } else {
                        return null;
                    }
                }
        ));

    }

    @Override
    public void updateUser(@NonNull String id, @NonNull String username, @NonNull String photoUrl, @NonNull Set<Long> records, @NonNull Consumer<Status<Void>> callback) {
        userApi.update(id, new UserDto(id, username, photoUrl, records)).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void isExistUser(@NonNull String username, Consumer<Status<Void>> callback) {
        userApi.isExist(username).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void createAccount(@NonNull String username, @NonNull String password, Consumer<Status<Void>> callback) {
        userApi.register(new AccountDto(username, password)).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void login(@NonNull String username, @NonNull String password, Consumer<Status<FullUserEntity>> callback) {
        credentialsDataSource.updateLogin(username, password);
        userApi = RetrofitFactory.getInstance().getUserApi();
        userApi.login().enqueue(new CallToConsumer<>(
                callback,
                user -> {
                    final String resultId = user.id;
                    final String username_login = user.username;
                    if (resultId != null && username_login != null) {
                        return new FullUserEntity(
                                resultId,
                                username_login,
                                user.photoUrl,
                                user.records
                        );
                    } else {
                        return null;
                    }
                }
        ));
    }

    @Override
    public void logout() {
        credentialsDataSource.logout();
    }
}
