package com.example.letsdive.authorization.ui.sign;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.data.dto.UserDto;
import com.example.letsdive.authorization.domain.GetUserByIdUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.sign.CreateUserUseCase;
import com.example.letsdive.authorization.domain.sign.IsUserExistUseCase;
import com.example.letsdive.authorization.domain.sign.LoginUserUseCase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignViewModel extends ViewModel {
    private final State INIT_STATE = new State(R.string.title_init, R.string.button_init, false);
    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<>(
            INIT_STATE
    );
    public final LiveData<State> stateLiveData = mutableStateLiveData;

    private final MutableLiveData<String> mutableErrorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = mutableErrorLiveData;

    private final MutableLiveData<FullUserEntity> mutableOpenLiveData = new MutableLiveData<>();
    public final LiveData<FullUserEntity> openLiveData = mutableOpenLiveData;

    /* UseCases */
    private final IsUserExistUseCase isUserExistUseCase = new IsUserExistUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final CreateUserUseCase createUserUseCase = new CreateUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    private final LoginUserUseCase loginUserUseCase = new LoginUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    /* UseCases */

    @Nullable
    private String username = null;
    @Nullable
    private String password = null;

    private boolean userCheckCompleted = false;
    private boolean isNewAccount = false;

    public void changeLogin(@NonNull String username) {
        this.username = username;
        if (userCheckCompleted) {
            userCheckCompleted = false;
            mutableStateLiveData.postValue(INIT_STATE);
        }
    }

    public void changePassword(@NonNull String password) {
        this.password = password;
    }

    public void confirm() {
        if (userCheckCompleted) {
            checkAuth();
        } else {
            checkUserExist();
        }

    }

    private void checkAuth() {
        final String currentUsername = username;
        final String currentPassword = password;
        if (currentPassword == null || currentPassword.isEmpty()) {
            mutableErrorLiveData.postValue("Password cannot be null");
            return;
        }
        if (currentUsername == null || currentUsername.isEmpty()) {
            mutableErrorLiveData.postValue("Login cannot be null");
            return;
        }
        if (isNewAccount) {
            createUserUseCase.execute(currentUsername, currentPassword, status -> {
                if (status.getStatusCode() == 200 && status.getErrors() == null) {
                    loginUser(currentUsername, currentPassword);
                } else {
                    mutableErrorLiveData.postValue("Something wrong");
                }
            });
        } else {
            loginUser(currentUsername, currentPassword);
        }
    }

    private void loginUser(@NonNull final String currentUsername ,@NonNull final String currentPassword) {
        loginUserUseCase.execute(currentUsername, currentPassword, status -> {
            if (status.getStatusCode() == 200 && status.getErrors() == null) {
                mutableOpenLiveData.postValue(status.getValue());
            } else {
                mutableErrorLiveData.postValue("Wrong password");
            }
        });
    }

    private void checkUserExist() {
        final String currentUsername = username;
        if (currentUsername == null || currentUsername.isEmpty()) {
            mutableErrorLiveData.postValue("Login cannot be null");
            return;
        }
        isUserExistUseCase.execute(currentUsername, status -> {
            if (status.getValue() == null || status.getErrors() != null) {
                mutableErrorLiveData.postValue("Something wrong. Try later =(");
                return;
            }
            userCheckCompleted = true;
            isNewAccount = !status.getValue();
            if(isNewAccount) {
                mutableStateLiveData.postValue(
                        new State(R.string.title_user_new, R.string.button_user_new, true)
                );
            } else {
                mutableStateLiveData.postValue(
                        new State(R.string.title_user_exist, R.string.button_user_exist, true)
                );
            }
        });
    }

    public class State {
        @StringRes
        private final int title;

        @StringRes
        private final int button;

        private final boolean isPasswordEnabled;

        public State(int title, int button, boolean isPasswordEnabled) {
            this.title = title;
            this.button = button;
            this.isPasswordEnabled = isPasswordEnabled;
        }

        public int getTitle() {
            return title;
        }

        public int getButton() {
            return button;
        }

        public boolean isPasswordEnabled() {
            return isPasswordEnabled;
        }
    }
}