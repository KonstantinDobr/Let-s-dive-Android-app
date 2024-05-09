package com.example.letsdive.authorization.data;

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
import java.util.function.Consumer;

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
                        final String name = user.name;
                        if (id != null && name != null) {
                            result.add(new ItemUserEntity(id, name));
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
                    final String name = user.name;
                    if (resultId != null && name != null) {
                        return new FullUserEntity(
                                resultId,
                                name,
                                user.photoUrl,
                                user.email
                        );
                    } else {
                        return null;
                    }
                }
        ));

    }

    @Override
    public void isExistUser(@NonNull String login, Consumer<Status<Void>> callback) {
        userApi.isExist(login).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void createAccount(@NonNull String login, @NonNull String password, Consumer<Status<Void>> callback) {
        userApi.register(new AccountDto(login, password)).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void login(@NonNull String login, @NonNull String password, Consumer<Status<Void>> callback) {
        credentialsDataSource.updateLogin(login, password);
        userApi = RetrofitFactory.getInstance().getUserApi();
        userApi.login().enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void logout() {
        credentialsDataSource.logout();
    }
}
