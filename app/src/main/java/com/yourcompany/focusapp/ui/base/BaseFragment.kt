package com.yourcompany.focusapp.ui.base

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment(layoutId: Int) : Fragment(layoutId)
