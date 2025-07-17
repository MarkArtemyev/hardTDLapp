package com.yourcompany.focusapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yourcompany.focusapp.R
import com.yourcompany.focusapp.ui.base.BaseFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)
}
