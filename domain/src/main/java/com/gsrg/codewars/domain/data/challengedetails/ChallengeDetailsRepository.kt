package com.gsrg.codewars.domain.data.challengedetails

import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.data.BaseRepository
import com.gsrg.codewars.domain.model.ChallengeDetailsResponse
import com.gsrg.codewars.domain.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class ChallengeDetailsRepository
@Inject constructor(
    private val codeWarsApiService: CodeWarsApiService
) : BaseRepository(), IChallengeDetailsRepository {

    override fun getChallengeDetails(challengeId: String): Flow<Result<ChallengeDetailsResponse>> = flow {
        emit(try {
            codeWarsApiService.requestChallengeDetails(challengeId = challengeId).run {
                Timber.tag(this@ChallengeDetailsRepository.TAG()).d("%s -> %s", message(), body())
                if (isSuccessful && body() != null) {
                    Result.Success(data = body()!!)
                } else {
                    takeCareOfError(this)
                }
            }
        } catch (exception: UnknownHostException) {
            Timber.tag(TAG()).e(exception)
            Result.Error(exception, "Something went wrong. Check your internet connection.")
        } catch (exception: Exception) {
            Timber.tag(TAG()).e(exception)
            Result.Error(exception, "Something went wrong.")
        })
    }
}