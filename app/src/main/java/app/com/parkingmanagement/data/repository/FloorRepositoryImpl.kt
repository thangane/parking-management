package app.com.parkingmanagement.data.repository

import app.com.parkingmanagement.data.source.RetrofitService
import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.model.Vacant
import app.com.parkingmanagement.domain.repository.FloorRepository
import io.reactivex.Single

class FloorRepositoryImpl(
    private val retrofitService: RetrofitService
):FloorRepository {

    override fun getFloors(): Single<List<Floor>> {
        return retrofitService.getFloors()
    }

    override fun addFloor(floor: Floor): Single<Boolean> {
        return retrofitService.addFloor(floor)
    }

    override fun deleteFloor(id: String): Single<Boolean> {
        return retrofitService.deleteFloor(id)
    }

    override fun updateFloor(floor: Floor): Single<Boolean> {
        return retrofitService.updateFloor(floor.id,floor)
    }

    override fun getFloorVacancy(entryTime: String): Single<List<Vacant>> {
        return retrofitService.getVacancyDetails(entryTime)
    }

    override fun getSettings(): Single<Boolean> {
        return retrofitService.getSettings()
    }
}