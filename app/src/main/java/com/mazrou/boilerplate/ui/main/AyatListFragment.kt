package com.mazrou.boilerplate.ui.main


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazrou.boilerplate.R
import com.mazrou.boilerplate.model.ui.Ayat
import kotlinx.android.synthetic.main.fragment_ayat_list.*

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class AyatListFragment : BaseMainFragment(R.layout.fragment_ayat_list) ,SearchListAdapter.Interaction{

    private lateinit var listAdapter: AyatListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = AyatListAdapter(this)
        initView ()
        subscribeObservers()
    }

    private fun initView (){
        ayat_list_recycler_view.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = listAdapter
        }
    }
    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner ,{
            it.ayatRacineList?.let {
                Log.e("TAG " , it.toString())
                listAdapter.submitList(it)
            }
        })
    }

    override fun onItemClicked(item: Any, index: Int) {
        when(item ){
            is Ayat -> {
                viewModel.setAyat(item)
                findNavController().navigate(R.id.to_ayat_details)
            }
        }
    }


}