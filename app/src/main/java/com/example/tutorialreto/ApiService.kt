package com.example.tutorialreto

interface ApiService {

    // POST: enviamos las credenciales en el cuerpo (@Body)
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // GET protegido: el token viaja en la cabecera (@Header)
    @GET("auth/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): Response<UserResponse>
}