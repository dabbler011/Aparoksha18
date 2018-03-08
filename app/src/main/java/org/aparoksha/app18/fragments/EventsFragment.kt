package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_events.*
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.CategoryAdapter
import org.aparoksha.app18.viewModels.EventsViewModel
import org.aparoksha.app18.adapters.EventsAdapter
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.AppDB
import org.aparoksha.app18.utils.isNetworkConnectionAvailable
import org.aparoksha.app18.utils.showAlert

/**
 * Created by akshat on 7/3/18.
 */

class EventsFragment: Fragment() {

    private lateinit var eventViewModel: EventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_events,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel = EventsViewModel.create(activity.application)

        val appDB = AppDB.getInstance(context)
        if (isNetworkConnectionAvailable(activity)) eventViewModel.getEvents(appDB,true)
        else eventViewModel.getEvents(appDB,false)

        categoryRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val adapter = CategoryAdapter(context)
        categoryRecyclerView.adapter = adapter

        eventViewModel.events.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {
                    val list: MutableList<Event> = it as MutableList<Event>
                    list.sortBy { it.timestamp }

                    adapter.updateEvents(list)
                }
            }
        })

        eventViewModel.empty.observe(this, Observer {
            it?.let {
                if(it) showAlert(activity)
            }
        })
    }
}