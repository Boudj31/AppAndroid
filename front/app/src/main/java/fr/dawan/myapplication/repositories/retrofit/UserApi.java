package fr.dawan.myapplication.repositories.retrofit;

import java.util.List;

import fr.dawan.myapplication.entities.retrofit.LoginDto;
import fr.dawan.myapplication.entities.retrofit.LoginResponseDto;
import fr.dawan.myapplication.entities.retrofit.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/login")
    Call<LoginResponseDto> connect(@Body LoginDto loginDto);


    @Multipart
    @POST("/api/users/save")
    Call<User> addUser(@Part MultipartBody.Part file, @Part("user")RequestBody userJson);

    @GET("/api/users/image/{id}")
    Call<ResponseBody> getUserImage(@Header("Authorization") String authToken, @Path("id") Long id);

    @GET("/api/users")
    Call<List<User>> getAllUsers(@Header("Authorization") String authToken);

    @DELETE("/api/users/{id}")
    Call<Long> deleteUser(@Header("Authorization") String authToken, @Path("id") long id);
}
