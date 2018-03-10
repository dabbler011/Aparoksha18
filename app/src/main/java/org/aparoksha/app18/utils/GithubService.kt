package org.aparoksha.app18.utils

import kotlinx.coroutines.experimental.Deferred
import org.aparoksha.app18.models.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by akshat on 10/3/18.
 */

interface GithubService {

    @GET("events.json")
    fun fetchEvents(): Deferred<List<Event>>

}
