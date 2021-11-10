package app.com.parkingmanagement.data.source

import app.com.parkingmanagement.domain.model.*
import io.reactivex.Single
import retrofit2.http.*

interface RetrofitService {

    @GET("floors/")
    fun getFloors(): Single<List<Floor>>

    @GET("floors/checkSettings")
    fun getSettings(): Single<Boolean>

    @POST("floor/")
    fun addFloor(@Body floor: Floor): Single<Boolean>

    @PATCH("floor/{id}")
    fun updateFloor(@Path("id") id: String, @Body floor: Floor): Single<Boolean>

    @DELETE("floor/{id}")
    fun deleteFloor(@Path("id") id: String): Single<Boolean>

    @GET("floor/vacant/{entryTime}")
    fun getVacancyDetails(@Path("entryTime") entryTime: String): Single<List<Vacant>>

    @GET("coupon/generate/")
    fun generateCoupons(
        @Query("count") count: String,
        @Query("startAt") startAt: String,
        @Query("endAt") endAt: String
    ): Single<List<Coupon>>

    @GET("coupon/")
    fun getCoupons(): Single<List<Coupon>>

    @PATCH("coupon/invalidate/{id}")
    fun invalidateCoupon(@Path("id") id: String): Single<Boolean>

    @DELETE("coupon/{id}")
    fun deleteCoupon(@Path("id") id: String): Single<Boolean>

    @GET("coupon/use/{code}")
    fun useCoupon(@Path("code") code: String): Single<Coupon>

    @GET("parkingCharges/")
    fun getParkingCharges(): Single<ParkingCharges>

    @POST("parkingCharges/")
    fun updateParkingCharges(@Body parkingCharges: ParkingCharges): Single<ParkingCharges>

    @POST("parkingTicket/checkIfEntryExists/{entryTime}")
    fun checkIfEntryExits(@Path("entryTime") entryTime: String): Single<Boolean>

    @POST("parkingTicket/generate")
    fun generateTicket(@Body data: GenerateTicket): Single<ParkingTicket>

    @DELETE("parkingTicket/{id}")
    fun deleteReservation(@Path("id") id: String): Single<Boolean>

    @GET("parkingTicket/{id}")
    fun getTicketById(@Path("id") id: String): Single<ParkingTicket>

    @GET("parkingTicket/getReservationDetails/{entryTime}")
    fun getReservationDetails(@Path("entryTime") entryTime: String): Single<List<Floor>>

    @POST("parkingTicket/calculatePrice")
    fun calculatePrice(@Body priceCalculation: PriceCalculation): Single<ParkingTicket>

    @POST("transaction/payAmount")
    fun payAmount(@Body data: ParkingTicket): Single<Boolean>

    @GET("transaction/getAllTransaction")
    fun getAllTransaction(): Single<List<ParkingTicket>>
}