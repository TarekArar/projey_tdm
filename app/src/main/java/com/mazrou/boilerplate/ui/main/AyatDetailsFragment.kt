package com.mazrou.boilerplate.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mazrou.boilerplate.R
import kotlinx.android.synthetic.main.ayat_layout.view.*
import kotlinx.android.synthetic.main.fragment_ayat_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class AyatDetailsFragment : BaseMainFragment(R.layout.fragment_ayat_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }
    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner ,{
            it.selectedAyat?.let {
                    surah_name_txt_view.text = "سورة${it.surahName}"
                 ayat_txt_view.text = it.text + " (${it.ayatNumber})"
            }
        })
    }


}