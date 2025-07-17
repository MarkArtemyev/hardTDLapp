package com.yourcompany.focusapp.ui.focus_session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yourcompany.focusapp.R
import com.yourcompany.focusapp.ui.base.BaseFragment

class FocusSessionFragment : BaseFragment(R.layout.fragment_focus_session) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_focus_session, container, false)
}
